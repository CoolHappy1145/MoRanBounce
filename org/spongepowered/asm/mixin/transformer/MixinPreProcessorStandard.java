package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.util.Iterator;
import org.apache.log4j.spi.LocationInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ActivityStack;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinPreProcessorException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.throwables.SyntheticBridgeException;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinPreProcessorStandard.class */
class MixinPreProcessorStandard {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    protected final MixinInfo mixin;
    protected final MixinInfo.MixinClassNode classNode;
    protected final MixinEnvironment env;
    protected final Profiler profiler = MixinEnvironment.getProfiler();
    protected final ActivityStack activities = new ActivityStack();
    private final boolean verboseLogging;
    private final boolean strictUnique;
    private boolean prepared;
    private boolean attached;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinPreProcessorStandard$SpecialMethod.class */
    enum SpecialMethod {
        MERGE(true),
        OVERWRITE(true, Overwrite.class),
        SHADOW(false, Shadow.class),
        ACCESSOR(false, Accessor.class),
        INVOKER(false, Invoker.class);

        final boolean isOverwrite;
        final Class annotation;
        final String description;

        SpecialMethod(boolean z, Class cls) {
            this.isOverwrite = z;
            this.annotation = cls;
            this.description = "@" + Bytecode.getSimpleName(cls);
        }

        SpecialMethod(boolean z) {
            this.isOverwrite = z;
            this.annotation = null;
            this.description = "overwrite";
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.description;
        }
    }

    MixinPreProcessorStandard(MixinInfo mixin, MixinInfo.MixinClassNode classNode) {
        this.mixin = mixin;
        this.classNode = classNode;
        this.env = mixin.getParent().getEnvironment();
        this.verboseLogging = this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
        this.strictUnique = this.env.getOption(MixinEnvironment.Option.DEBUG_UNIQUE);
    }

    final MixinPreProcessorStandard prepare() {
        if (this.prepared) {
            return this;
        }
        this.prepared = true;
        this.activities.clear();
        Profiler.Section prepareTimer = this.profiler.begin("prepare");
        try {
            ActivityStack.Activity activity = this.activities.begin("Prepare method");
            for (MixinInfo.MixinMethodNode mixinMethod : this.classNode.mixinMethods) {
                ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethod);
                ActivityStack.Activity methodActivity = this.activities.begin(mixinMethod.toString());
                prepareMethod(mixinMethod, method);
                methodActivity.end();
            }
            activity.next("Prepare field");
            for (FieldNode mixinField : this.classNode.fields) {
                ActivityStack.Activity fieldActivity = this.activities.begin(String.format("%s:%s", mixinField.name, mixinField.desc));
                prepareField(mixinField);
                fieldActivity.end();
            }
            activity.end();
            prepareTimer.end();
            return this;
        } catch (MixinException ex) {
            throw ex;
        } catch (Exception ex2) {
            throw new MixinPreProcessorException(String.format("Prepare error for %s during activity:", this.mixin), ex2, this.activities);
        }
    }

    protected void prepareMethod(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
        prepareShadow(mixinMethod, method);
        prepareSoftImplements(mixinMethod, method);
    }

    protected void prepareShadow(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
        AnnotationNode shadowAnnotation = Annotations.getVisible(mixinMethod, Shadow.class);
        if (shadowAnnotation == null) {
            return;
        }
        String prefix = (String) Annotations.getValue(shadowAnnotation, "prefix", Shadow.class);
        if (mixinMethod.name.startsWith(prefix)) {
            Annotations.setVisible(mixinMethod, MixinRenamed.class, new Object[]{"originalName", mixinMethod.name});
            String newName = mixinMethod.name.substring(prefix.length());
            mixinMethod.name = method.renameTo(newName);
        }
    }

    protected void prepareSoftImplements(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
        for (InterfaceInfo iface : this.mixin.getSoftImplements()) {
            if (iface.renameMethod(mixinMethod)) {
                method.renameTo(mixinMethod.name);
            }
        }
    }

    protected void prepareField(FieldNode mixinField) {
    }

    final MixinPreProcessorStandard conform(TargetClassContext target) {
        return conform(target.getClassInfo());
    }

    final MixinPreProcessorStandard conform(ClassInfo target) {
        this.activities.clear();
        Profiler.Section conformTimer = this.profiler.begin("conform");
        try {
            for (MixinInfo.MixinMethodNode mixinMethod : this.classNode.mixinMethods) {
                if (mixinMethod.isInjector()) {
                    ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethod, 10);
                    ActivityStack.Activity methodActivity = this.activities.begin("Conform injector %s", new Object[]{mixinMethod});
                    conformInjector(target, mixinMethod, method);
                    methodActivity.end();
                }
            }
            conformTimer.end();
            return this;
        } catch (MixinException ex) {
            throw ex;
        } catch (Exception ex2) {
            throw new MixinPreProcessorException(String.format("Conform error for %s during activity:", this.mixin), ex2, this.activities);
        }
    }

    private void conformInjector(ClassInfo targetClass, MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
        MethodMapper methodMapper = targetClass.getMethodMapper();
        methodMapper.remapHandlerMethod(this.mixin, mixinMethod, method);
    }

    MixinTargetContext createContextFor(TargetClassContext target) {
        MixinTargetContext context = new MixinTargetContext(this.mixin, this.classNode, target);
        conform(target);
        attach(context);
        return context;
    }

    final MixinPreProcessorStandard attach(MixinTargetContext context) {
        if (this.attached) {
            throw new IllegalStateException("Preprocessor was already attached");
        }
        this.attached = true;
        this.activities.clear();
        Profiler.Section attachTimer = this.profiler.begin("attach");
        try {
            Profiler.Section timer = this.profiler.begin("methods");
            ActivityStack.Activity activity = this.activities.begin("Attach method");
            attachMethods(context);
            Profiler.Section timer2 = timer.next("fields");
            activity.next("Attach field");
            attachFields(context);
            Profiler.Section timer3 = timer2.next("transform");
            activity.next("Transform");
            transform(context);
            activity.end();
            timer3.end();
            attachTimer.end();
            return this;
        } catch (MixinException ex) {
            throw ex;
        } catch (Exception ex2) {
            throw new MixinPreProcessorException(String.format("Attach error for %s during activity:", this.mixin), ex2, this.activities);
        }
    }

    protected void attachMethods(MixinTargetContext context) {
        ActivityStack.Activity methodActivity = this.activities.begin(LocationInfo.f204NA);
        Iterator<MixinInfo.MixinMethodNode> iter = this.classNode.mixinMethods.iterator();
        while (iter.hasNext()) {
            MixinInfo.MixinMethodNode mixinMethod = iter.next();
            methodActivity.next(mixinMethod.toString());
            if (!validateMethod(context, mixinMethod)) {
                iter.remove();
            } else if (attachInjectorMethod(context, mixinMethod)) {
                context.addMixinMethod(mixinMethod);
            } else if (attachAccessorMethod(context, mixinMethod)) {
                iter.remove();
            } else if (attachShadowMethod(context, mixinMethod)) {
                context.addShadowMethod(mixinMethod);
                iter.remove();
            } else if (attachOverwriteMethod(context, mixinMethod)) {
                context.addMixinMethod(mixinMethod);
            } else if (attachUniqueMethod(context, mixinMethod)) {
                iter.remove();
            } else {
                attachMethod(context, mixinMethod);
                context.addMixinMethod(mixinMethod);
            }
        }
        methodActivity.end();
    }

    protected boolean validateMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
        return true;
    }

    protected boolean attachInjectorMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
        return mixinMethod.isInjector();
    }

    protected boolean attachAccessorMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
        return attachAccessorMethod(context, mixinMethod, SpecialMethod.ACCESSOR) || attachAccessorMethod(context, mixinMethod, SpecialMethod.INVOKER);
    }

    protected boolean attachAccessorMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
        AnnotationNode annotation = mixinMethod.getVisibleAnnotation(type.annotation);
        if (annotation == null) {
            return false;
        }
        String description = type + " method " + mixinMethod.name;
        ClassInfo.Method method = getSpecialMethod(mixinMethod, type);
        if (MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8) && method.isStatic()) {
            if (this.mixin.getTargets().size() > 1) {
                throw new InvalidAccessorException(context, description + " in multi-target mixin is invalid. Mixin must have exactly 1 target.");
            }
            if (method.isConformed()) {
                mixinMethod.name = method.getName();
            } else {
                String uniqueName = context.getUniqueName(mixinMethod, true);
                logger.log(this.mixin.getLoggingLevel(), "Renaming @{} method {}{} to {} in {}", new Object[]{Bytecode.getSimpleName(annotation), mixinMethod.name, mixinMethod.desc, uniqueName, this.mixin});
                mixinMethod.name = method.conform(uniqueName);
            }
        } else {
            if (!method.isAbstract()) {
                throw new InvalidAccessorException(context, description + " is not abstract");
            }
            if (method.isStatic()) {
                throw new InvalidAccessorException(context, description + " cannot be static");
            }
        }
        context.addAccessorMethod(mixinMethod, type.annotation);
        return true;
    }

    protected boolean attachShadowMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
        return attachSpecialMethod(context, mixinMethod, SpecialMethod.SHADOW);
    }

    protected boolean attachOverwriteMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
        return attachSpecialMethod(context, mixinMethod, SpecialMethod.OVERWRITE);
    }

    protected boolean attachSpecialMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
        AnnotationNode annotation = mixinMethod.getVisibleAnnotation(type.annotation);
        if (annotation == null) {
            return false;
        }
        if (type.isOverwrite) {
            checkMixinNotUnique(mixinMethod, type);
        }
        ClassInfo.Method method = getSpecialMethod(mixinMethod, type);
        MethodNode target = context.findMethod(mixinMethod, annotation);
        if (target == null) {
            if (type.isOverwrite) {
                return false;
            }
            target = context.findRemappedMethod(mixinMethod);
            if (target == null) {
                throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s was not located in the target class %s. %s%s", type, mixinMethod.name, this.mixin, context.getTarget(), context.getReferenceMapper().getStatus(), getDynamicInfo(mixinMethod)));
            }
            mixinMethod.name = method.renameTo(target.name);
        }
        if (Constants.CTOR.equals(target.name)) {
            throw new InvalidMixinException(this.mixin, String.format("Nice try! %s in %s cannot alias a constructor", mixinMethod.name, this.mixin));
        }
        if (!Bytecode.compareFlags(mixinMethod, target, 8)) {
            throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of %s method %s in %s does not match the target", type, mixinMethod.name, this.mixin));
        }
        conformVisibility(context, mixinMethod, type, target);
        if (!target.name.equals(mixinMethod.name)) {
            if (type.isOverwrite && (target.access & 2) == 0) {
                throw new InvalidMixinException(this.mixin, "Non-private method cannot be aliased. Found " + target.name);
            }
            mixinMethod.name = method.renameTo(target.name);
            return true;
        }
        return true;
    }

    private void conformVisibility(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type, MethodNode target) {
        Bytecode.Visibility visTarget = Bytecode.getVisibility(target);
        Bytecode.Visibility visMethod = Bytecode.getVisibility(mixinMethod);
        if (visMethod.ordinal() >= visTarget.ordinal()) {
            if (visTarget == Bytecode.Visibility.PRIVATE && visMethod.ordinal() > Bytecode.Visibility.PRIVATE.ordinal()) {
                context.getTarget().addUpgradedMethod(target);
                return;
            }
            return;
        }
        String message = String.format("%s %s method %s in %s cannot reduce visibiliy of %s target method", visMethod, type, mixinMethod.name, this.mixin, visTarget);
        if (type.isOverwrite && !this.mixin.getParent().conformOverwriteVisibility()) {
            throw new InvalidMixinException(this.mixin, message);
        }
        if (visMethod == Bytecode.Visibility.PRIVATE) {
            if (type.isOverwrite) {
                logger.warn("Static binding violation: {}, visibility will be upgraded.", new Object[]{message});
            }
            context.addUpgradedMethod(mixinMethod);
            Bytecode.setVisibility(mixinMethod, visTarget);
        }
    }

    protected ClassInfo.Method getSpecialMethod(MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
        ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethod, 10);
        checkMethodNotUnique(method, type);
        return method;
    }

    protected void checkMethodNotUnique(ClassInfo.Method method, SpecialMethod type) {
        if (method.isUnique()) {
            throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s cannot be @Unique", type, method.getName(), this.mixin));
        }
    }

    protected void checkMixinNotUnique(MixinInfo.MixinMethodNode mixinMethod, SpecialMethod type) {
        if (this.mixin.isUnique()) {
            throw new InvalidMixinException(this.mixin, String.format("%s method %s found in a @Unique mixin %s", type, mixinMethod.name, this.mixin));
        }
    }

    protected boolean attachUniqueMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
        ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethod, 10);
        if (method == null) {
            return false;
        }
        if (!method.isUnique() && !this.mixin.isUnique() && !method.isSynthetic()) {
            return false;
        }
        boolean synthetic = method.isSynthetic();
        if (synthetic) {
            context.transformDescriptor(mixinMethod);
            method.remapTo(mixinMethod.desc);
        }
        MethodNode target = context.findMethod(mixinMethod, null);
        if (target == null && !synthetic) {
            return false;
        }
        String type = synthetic ? "synthetic" : "@Unique";
        if (Bytecode.getVisibility(mixinMethod).ordinal() < Bytecode.Visibility.PUBLIC.ordinal()) {
            if (method.isConformed()) {
                mixinMethod.name = method.getName();
                return false;
            }
            String uniqueName = context.getUniqueName(mixinMethod, false);
            logger.log(this.mixin.getLoggingLevel(), "Renaming {} method {}{} to {} in {}", new Object[]{type, mixinMethod.name, mixinMethod.desc, uniqueName, this.mixin});
            mixinMethod.name = method.conform(uniqueName);
            return false;
        }
        if (target == null) {
            return false;
        }
        if (this.strictUnique) {
            throw new InvalidMixinException(this.mixin, String.format("Method conflict, %s method %s in %s cannot overwrite %s%s in %s", type, mixinMethod.name, this.mixin, target.name, target.desc, context.getTarget()));
        }
        AnnotationNode unique = Annotations.getVisible(mixinMethod, Unique.class);
        if (unique == null || !((Boolean) Annotations.getValue(unique, "silent", Boolean.FALSE)).booleanValue()) {
            if (Bytecode.hasFlag(mixinMethod, 64)) {
                try {
                    Bytecode.compareBridgeMethods(target, mixinMethod);
                    logger.debug("Discarding sythetic bridge method {} in {} because existing method in {} is compatible", new Object[]{type, mixinMethod.name, this.mixin, context.getTarget()});
                    return true;
                } catch (SyntheticBridgeException ex) {
                    if (this.verboseLogging || this.env.getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                        ex.printAnalysis(context, target, mixinMethod);
                    }
                    throw new InvalidMixinException(this.mixin, ex.getMessage());
                }
            }
            logger.warn("Discarding {} public method {} in {} because it already exists in {}", new Object[]{type, mixinMethod.name, this.mixin, context.getTarget()});
            return true;
        }
        context.addMixinMethod(mixinMethod);
        return true;
    }

    protected void attachMethod(MixinTargetContext context, MixinInfo.MixinMethodNode mixinMethod) {
        ClassInfo.Method method = this.mixin.getClassInfo().findMethod(mixinMethod);
        if (method == null) {
            return;
        }
        ClassInfo.Method parentMethod = this.mixin.getClassInfo().findMethodInHierarchy(mixinMethod, ClassInfo.SearchType.SUPER_CLASSES_ONLY);
        if (parentMethod != null && parentMethod.isRenamed()) {
            mixinMethod.name = method.renameTo(parentMethod.getName());
        }
        MethodNode target = context.findMethod(mixinMethod, null);
        if (target != null) {
            conformVisibility(context, mixinMethod, SpecialMethod.MERGE, target);
        }
    }

    protected void attachFields(MixinTargetContext context) {
        ActivityStack.Activity fieldActivity = this.activities.begin(LocationInfo.f204NA);
        Iterator<FieldNode> iter = this.classNode.getFields().iterator();
        while (iter.hasNext()) {
            FieldNode mixinField = iter.next();
            fieldActivity.next("%s:%s", new Object[]{mixinField.name, mixinField.desc});
            AnnotationNode shadow = Annotations.getVisible(mixinField, Shadow.class);
            boolean isShadow = shadow != null;
            if (!validateField(context, mixinField, shadow)) {
                iter.remove();
            } else {
                ClassInfo.Field field = this.mixin.getClassInfo().findField(mixinField);
                context.transformDescriptor(mixinField);
                field.remapTo(mixinField.desc);
                if (field.isUnique() && isShadow) {
                    throw new InvalidMixinException(this.mixin, String.format("@Shadow field %s cannot be @Unique", mixinField.name));
                }
                FieldNode target = context.findField(mixinField, shadow);
                if (target == null) {
                    if (shadow == null) {
                        continue;
                    } else {
                        target = context.findRemappedField(mixinField);
                        if (target == null) {
                            throw new InvalidMixinException(this.mixin, String.format("@Shadow field %s was not located in the target class %s. %s%s", mixinField.name, context.getTarget(), context.getReferenceMapper().getStatus(), getDynamicInfo(mixinField)));
                        }
                        mixinField.name = field.renameTo(target.name);
                    }
                }
                if (!Bytecode.compareFlags(mixinField, target, 8)) {
                    throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of @Shadow field %s in %s does not match the target", mixinField.name, this.mixin));
                }
                if (field.isUnique()) {
                    if ((mixinField.access & 6) != 0) {
                        String uniqueName = context.getUniqueName(mixinField);
                        logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique field {}{} to {} in {}", new Object[]{mixinField.name, mixinField.desc, uniqueName, this.mixin});
                        mixinField.name = field.renameTo(uniqueName);
                    } else {
                        if (this.strictUnique) {
                            throw new InvalidMixinException(this.mixin, String.format("Field conflict, @Unique field %s in %s cannot overwrite %s%s in %s", mixinField.name, this.mixin, target.name, target.desc, context.getTarget()));
                        }
                        logger.warn("Discarding @Unique public field {} in {} because it already exists in {}. Note that declared FIELD INITIALISERS will NOT be removed!", new Object[]{mixinField.name, this.mixin, context.getTarget()});
                        iter.remove();
                    }
                } else {
                    if (!target.desc.equals(mixinField.desc)) {
                        throw new InvalidMixinException(this.mixin, String.format("The field %s in the target class has a conflicting signature", mixinField.name));
                    }
                    if (!target.name.equals(mixinField.name)) {
                        if ((target.access & 2) == 0 && (target.access & 4096) == 0) {
                            throw new InvalidMixinException(this.mixin, "Non-private field cannot be aliased. Found " + target.name);
                        }
                        mixinField.name = field.renameTo(target.name);
                    }
                    iter.remove();
                    if (isShadow) {
                        boolean isFinal = field.isDecoratedFinal();
                        if (this.verboseLogging && Bytecode.hasFlag(target, 16) != isFinal) {
                            String message = isFinal ? "@Shadow field {}::{} is decorated with @Final but target is not final" : "@Shadow target {}::{} is final but shadow is not decorated with @Final";
                            logger.warn(message, new Object[]{this.mixin, mixinField.name});
                        }
                        context.addShadowField(mixinField, field);
                    }
                }
            }
        }
    }

    protected boolean validateField(MixinTargetContext context, FieldNode field, AnnotationNode shadow) {
        if (Bytecode.isStatic(field) && !Bytecode.hasFlag(field, 2) && !Bytecode.hasFlag(field, 4096) && shadow == null) {
            throw new InvalidMixinException(context, String.format("Mixin %s contains non-private static field %s:%s", context, field.name, field.desc));
        }
        String prefix = (String) Annotations.getValue(shadow, "prefix", Shadow.class);
        if (field.name.startsWith(prefix)) {
            throw new InvalidMixinException(context, String.format("@Shadow field %s.%s has a shadow prefix. This is not allowed.", context, field.name));
        }
        if (Constants.IMAGINARY_SUPER.equals(field.name)) {
            if (field.access != 2) {
                throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must be private and non-final", context, field.name));
            }
            if (!field.desc.equals("L" + this.mixin.getClassRef() + ";")) {
                throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must have the same type as the parent mixin (%s)", context, field.name, this.mixin.getClassName()));
            }
            return false;
        }
        return true;
    }

    protected void transform(MixinTargetContext context) {
        ActivityStack.Activity methodActivity = this.activities.begin("method");
        for (MethodNode mixinMethod : this.classNode.methods) {
            methodActivity.next("Method %s", new Object[]{mixinMethod});
            Iterator<AbstractInsnNode> iter = mixinMethod.instructions.iterator();
            while (iter.hasNext()) {
                AbstractInsnNode insn = iter.next();
                ActivityStack.Activity activity = this.activities.begin(Bytecode.getOpcodeName(insn));
                if (insn instanceof MethodInsnNode) {
                    transformMethod((MethodInsnNode) insn);
                } else if (insn instanceof FieldInsnNode) {
                    transformField((FieldInsnNode) insn);
                }
                activity.end();
            }
        }
        methodActivity.end();
    }

    protected void transformMethod(MethodInsnNode methodNode) {
        ActivityStack.Activity activity = this.activities.begin("%s::%s%s", new Object[]{methodNode.owner, methodNode.name, methodNode.desc});
        Profiler.Section metaTimer = this.profiler.begin("meta");
        ClassInfo owner = ClassInfo.forDescriptor(methodNode.owner, ClassInfo.TypeLookup.DECLARED_TYPE);
        if (owner == null) {
            throw new ClassMetadataNotFoundException(methodNode.owner.replace('/', '.'));
        }
        ClassInfo.Method method = owner.findMethodInHierarchy(methodNode, ClassInfo.SearchType.ALL_CLASSES, 2);
        metaTimer.end();
        if (method != null && method.isRenamed()) {
            methodNode.name = method.getName();
        }
        activity.end();
    }

    protected void transformField(FieldInsnNode fieldNode) {
        ActivityStack.Activity activity = this.activities.begin("%s::%s:%s", new Object[]{fieldNode.owner, fieldNode.name, fieldNode.desc});
        Profiler.Section metaTimer = this.profiler.begin("meta");
        ClassInfo owner = ClassInfo.forDescriptor(fieldNode.owner, ClassInfo.TypeLookup.DECLARED_TYPE);
        if (owner == null) {
            throw new ClassMetadataNotFoundException(fieldNode.owner.replace('/', '.'));
        }
        ClassInfo.Field field = owner.findField(fieldNode, 2);
        metaTimer.end();
        if (field != null && field.isRenamed()) {
            fieldNode.name = field.getName();
        }
        activity.end();
    }

    protected static String getDynamicInfo(MethodNode method) {
        return getDynamicInfo("Method", Annotations.getInvisible(method, Dynamic.class));
    }

    protected static String getDynamicInfo(FieldNode method) {
        return getDynamicInfo("Field", Annotations.getInvisible(method, Dynamic.class));
    }

    private static String getDynamicInfo(String targetType, AnnotationNode annotation) {
        String description = Strings.nullToEmpty((String) Annotations.getValue(annotation));
        Type upstream = (Type) Annotations.getValue(annotation, MixinLaunchPlugin.NAME);
        if (upstream != null) {
            description = String.format("{%s} %s", upstream.getClassName(), description).trim();
        }
        return description.length() > 0 ? String.format(" %s is @Dynamic(%s)", targetType, description) : "";
    }
}
