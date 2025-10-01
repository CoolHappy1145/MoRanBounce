package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import kotlin.text.Typography;
import org.apache.log4j.spi.LocationInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.throwables.MixinError;
import org.spongepowered.asm.mixin.transformer.ActivityStack;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinApplicatorException;
import org.spongepowered.asm.service.IMixinAuditTrail;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.perf.Profiler;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorStandard.class */
class MixinApplicatorStandard {
    protected static final List CONSTRAINED_ANNOTATIONS = ImmutableList.of(Overwrite.class, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
    protected static final int[] INITIALISER_OPCODE_BLACKLIST = {Typography.plusMinus, 21, 22, 23, 24, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 81, 82, 83, 84, 85, 86};
    protected final TargetClassContext context;
    protected final String targetName;
    protected final ClassNode targetClass;
    protected final ClassInfo targetClassInfo;
    protected final IMixinAuditTrail auditTrail;
    protected final boolean mergeSignatures;
    protected final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    protected final Profiler profiler = MixinEnvironment.getProfiler();
    protected final ActivityStack activities = new ActivityStack();

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorStandard$ApplicatorPass.class */
    enum ApplicatorPass {
        MAIN,
        PREINJECT,
        INJECT
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorStandard$InitialiserInjectionMode.class */
    enum InitialiserInjectionMode {
        DEFAULT,
        SAFE
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorStandard$Range.class */
    class Range {
        final int start;
        final int end;
        final int marker;
        final MixinApplicatorStandard this$0;

        Range(MixinApplicatorStandard mixinApplicatorStandard, int i, int i2, int i3) {
            this.this$0 = mixinApplicatorStandard;
            this.start = i;
            this.end = i2;
            this.marker = i3;
        }

        boolean isValid() {
            return (this.start == 0 || this.end == 0 || this.end < this.start) ? false : true;
        }

        boolean contains(int i) {
            return i >= this.start && i <= this.end;
        }

        boolean excludes(int i) {
            return i < this.start || i > this.end;
        }

        public String toString() {
            return String.format("Range[%d-%d,%d,valid=%s)", Integer.valueOf(this.start), Integer.valueOf(this.end), Integer.valueOf(this.marker), Boolean.valueOf(isValid()));
        }
    }

    MixinApplicatorStandard(TargetClassContext targetClassContext) {
        this.context = targetClassContext;
        this.targetName = targetClassContext.getClassName();
        this.targetClass = targetClassContext.getClassNode();
        this.targetClassInfo = targetClassContext.getClassInfo();
        this.mergeSignatures = ((ExtensionClassExporter) targetClassContext.getExtensions().getExtension(ExtensionClassExporter.class)).isDecompilerActive() && MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES);
        this.auditTrail = MixinService.getService().getAuditTrail();
    }

    final void apply(SortedSet sortedSet) {
        ArrayList<MixinTargetContext> arrayList = new ArrayList();
        Iterator it = sortedSet.iterator();
        while (it.hasNext()) {
            MixinInfo mixinInfo = (MixinInfo) it.next();
            try {
                this.logger.log(mixinInfo.getLoggingLevel(), "Mixing {} from {} into {}", new Object[]{mixinInfo.getName(), mixinInfo.getParent(), this.targetName});
                arrayList.add(mixinInfo.createContextFor(this.context));
                if (this.auditTrail != null) {
                    this.auditTrail.onApply(this.targetName, mixinInfo.toString());
                }
            } catch (InvalidMixinException e) {
                if (mixinInfo.isRequired()) {
                    throw e;
                }
                this.context.addSuppressed(e);
                it.remove();
            }
        }
        MixinTargetContext mixinTargetContext = null;
        this.activities.clear();
        try {
            ActivityStack.Activity activityBegin = this.activities.begin("PreApply Phase");
            ActivityStack.Activity activityBegin2 = this.activities.begin("Mixin");
            for (MixinTargetContext mixinTargetContext2 : arrayList) {
                activityBegin2.next(mixinTargetContext2.toString());
                mixinTargetContext2.preApply(this.targetName, this.targetClass);
            }
            activityBegin2.end();
            for (ApplicatorPass applicatorPass : ApplicatorPass.values()) {
                activityBegin.next("%s Applicator Phase", new Object[]{applicatorPass});
                Profiler.Section sectionBegin = this.profiler.begin("pass", applicatorPass.name().toLowerCase(Locale.ROOT));
                ActivityStack.Activity activityBegin3 = this.activities.begin("Mixin");
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    mixinTargetContext = (MixinTargetContext) it2.next();
                    activityBegin3.next(mixinTargetContext.toString());
                    try {
                        applyMixin(mixinTargetContext, applicatorPass);
                    } catch (InvalidMixinException e2) {
                        if (mixinTargetContext.isRequired()) {
                            throw e2;
                        }
                        this.context.addSuppressed(e2);
                        it2.remove();
                    }
                }
                activityBegin3.end();
                sectionBegin.end();
            }
            activityBegin.next("PostApply Phase");
            ActivityStack.Activity activityBegin4 = this.activities.begin("Mixin");
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                mixinTargetContext = (MixinTargetContext) it3.next();
                activityBegin4.next(mixinTargetContext.toString());
                try {
                    mixinTargetContext.postApply(this.targetName, this.targetClass);
                } catch (InvalidMixinException e3) {
                    if (mixinTargetContext.isRequired()) {
                        throw e3;
                    }
                    this.context.addSuppressed(e3);
                    it3.remove();
                }
            }
            activityBegin.end();
            applySourceMap(this.context);
            this.context.processDebugTasks();
        } catch (InvalidMixinException e4) {
            e4.prepend(this.activities);
            throw e4;
        } catch (Exception e5) {
            throw new MixinApplicatorException(mixinTargetContext, "Unexpecteded " + e5.getClass().getSimpleName() + " whilst applying the mixin class:", e5, this.activities);
        }
    }

    /* renamed from: org.spongepowered.asm.mixin.transformer.MixinApplicatorStandard$1 */
    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorStandard$1.class */
    static /* synthetic */ class C05671 {

        /* renamed from: $SwitchMap$org$spongepowered$asm$mixin$transformer$MixinApplicatorStandard$ApplicatorPass */
        static final int[] f223x4ac7868 = new int[ApplicatorPass.values().length];

        static {
            try {
                f223x4ac7868[ApplicatorPass.MAIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f223x4ac7868[ApplicatorPass.PREINJECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f223x4ac7868[ApplicatorPass.INJECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    protected final void applyMixin(MixinTargetContext mixinTargetContext, ApplicatorPass applicatorPass) {
        ActivityStack.Activity activityBegin = this.activities.begin("Apply");
        switch (C05671.f223x4ac7868[applicatorPass.ordinal()]) {
            case 1:
                activityBegin.next("Apply Signature");
                applySignature(mixinTargetContext);
                activityBegin.next("Apply Interfaces");
                applyInterfaces(mixinTargetContext);
                activityBegin.next("Apply Attributess");
                applyAttributes(mixinTargetContext);
                activityBegin.next("Apply Annotations");
                applyAnnotations(mixinTargetContext);
                activityBegin.next("Apply Fields");
                applyFields(mixinTargetContext);
                activityBegin.next("Apply Methods");
                applyMethods(mixinTargetContext);
                activityBegin.next("Apply Initialisers");
                applyInitialisers(mixinTargetContext);
                break;
            case 2:
                activityBegin.next("Prepare Injections");
                prepareInjections(mixinTargetContext);
                break;
            case 3:
                activityBegin.next("Apply Accessors");
                applyAccessors(mixinTargetContext);
                activityBegin.next("Apply Injections");
                applyInjections(mixinTargetContext);
                break;
            default:
                throw new IllegalStateException("Invalid pass specified " + applicatorPass);
        }
        activityBegin.end();
    }

    protected void applySignature(MixinTargetContext mixinTargetContext) {
        if (this.mergeSignatures) {
            this.context.mergeSignature(mixinTargetContext.getSignature());
        }
    }

    protected void applyInterfaces(MixinTargetContext mixinTargetContext) {
        for (String str : mixinTargetContext.getInterfaces()) {
            if (!this.targetClass.interfaces.contains(str)) {
                this.targetClass.interfaces.add(str);
                this.targetClassInfo.addInterface(str);
            }
        }
    }

    protected void applyAttributes(MixinTargetContext mixinTargetContext) {
        if (mixinTargetContext.shouldSetSourceFile()) {
            this.targetClass.sourceFile = mixinTargetContext.getSourceFile();
        }
        this.targetClass.version = Math.max(this.targetClass.version, mixinTargetContext.getMinRequiredClassVersion());
    }

    protected void applyAnnotations(MixinTargetContext mixinTargetContext) {
        Annotations.merge(mixinTargetContext.getClassNode(), this.targetClass);
    }

    protected void applyFields(MixinTargetContext mixinTargetContext) {
        mergeShadowFields(mixinTargetContext);
        mergeNewFields(mixinTargetContext);
    }

    protected void mergeShadowFields(MixinTargetContext mixinTargetContext) {
        for (Map.Entry entry : mixinTargetContext.getShadowFields()) {
            FieldNode fieldNode = (FieldNode) entry.getKey();
            FieldNode fieldNodeFindTargetField = findTargetField(fieldNode);
            if (fieldNodeFindTargetField != null) {
                Annotations.merge(fieldNode, fieldNodeFindTargetField);
                if (((ClassInfo.Field) entry.getValue()).isDecoratedMutable()) {
                    fieldNodeFindTargetField.access &= -17;
                }
            }
        }
    }

    protected void mergeNewFields(MixinTargetContext mixinTargetContext) {
        for (FieldNode fieldNode : mixinTargetContext.getFields()) {
            if (findTargetField(fieldNode) == null) {
                this.targetClass.fields.add(fieldNode);
                if (fieldNode.signature != null) {
                    if (this.mergeSignatures) {
                        SignatureVisitor remapper = mixinTargetContext.getSignature().getRemapper();
                        new SignatureReader(fieldNode.signature).accept(remapper);
                        fieldNode.signature = remapper.toString();
                    } else {
                        fieldNode.signature = null;
                    }
                }
            }
        }
    }

    protected void applyMethods(MixinTargetContext mixinTargetContext) {
        ActivityStack.Activity activityBegin = this.activities.begin(LocationInfo.f204NA);
        for (MethodNode methodNode : mixinTargetContext.getShadowMethods()) {
            activityBegin.next("@Shadow %s:%s", new Object[]{methodNode.desc, methodNode.name});
            applyShadowMethod(mixinTargetContext, methodNode);
        }
        for (MethodNode methodNode2 : mixinTargetContext.getMethods()) {
            activityBegin.next("%s:%s", new Object[]{methodNode2.desc, methodNode2.name});
            applyNormalMethod(mixinTargetContext, methodNode2);
        }
        activityBegin.end();
    }

    protected void applyShadowMethod(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        MethodNode methodNodeFindTargetMethod = findTargetMethod(methodNode);
        if (methodNodeFindTargetMethod != null) {
            Annotations.merge(methodNode, methodNodeFindTargetMethod);
        }
    }

    protected void applyNormalMethod(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        mixinTargetContext.transformMethod(methodNode);
        if (!methodNode.name.startsWith("<")) {
            checkMethodVisibility(mixinTargetContext, methodNode);
            checkMethodConstraints(mixinTargetContext, methodNode);
            mergeMethod(mixinTargetContext, methodNode);
        } else if (Constants.CLINIT.equals(methodNode.name)) {
            ActivityStack.Activity activityBegin = this.activities.begin("Merge CLINIT insns");
            appendInsns(mixinTargetContext, methodNode);
            activityBegin.end();
        }
    }

    protected void mergeMethod(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        boolean z = Annotations.getVisible(methodNode, Overwrite.class) != null;
        MethodNode methodNodeFindTargetMethod = findTargetMethod(methodNode);
        if (methodNodeFindTargetMethod != null) {
            if (isAlreadyMerged(mixinTargetContext, methodNode, z, methodNodeFindTargetMethod)) {
                return;
            }
            AnnotationNode invisible = Annotations.getInvisible(methodNode, Intrinsic.class);
            if (invisible != null) {
                if (mergeIntrinsic(mixinTargetContext, methodNode, z, methodNodeFindTargetMethod, invisible)) {
                    mixinTargetContext.getTarget().methodMerged(methodNode);
                    return;
                }
            } else {
                if (mixinTargetContext.requireOverwriteAnnotations() && !z) {
                    throw new InvalidMixinException(mixinTargetContext, String.format("%s%s in %s cannot overwrite method in %s because @Overwrite is required by the parent configuration", methodNode.name, methodNode.desc, mixinTargetContext, mixinTargetContext.getTarget().getClassName()));
                }
                this.targetClass.methods.remove(methodNodeFindTargetMethod);
            }
        } else if (z) {
            throw new InvalidMixinException(mixinTargetContext, String.format("Overwrite target \"%s\" was not located in target class %s", methodNode.name, mixinTargetContext.getTargetClassRef()));
        }
        this.targetClass.methods.add(methodNode);
        mixinTargetContext.methodMerged(methodNode);
        if (methodNode.signature != null) {
            if (this.mergeSignatures) {
                SignatureVisitor remapper = mixinTargetContext.getSignature().getRemapper();
                new SignatureReader(methodNode.signature).accept(remapper);
                methodNode.signature = remapper.toString();
                return;
            }
            methodNode.signature = null;
        }
    }

    protected boolean isAlreadyMerged(MixinTargetContext mixinTargetContext, MethodNode methodNode, boolean z, MethodNode methodNode2) {
        AnnotationNode singleVisible;
        AnnotationNode visible = Annotations.getVisible(methodNode2, MixinMerged.class);
        if (visible == null) {
            if (Annotations.getVisible(methodNode2, Final.class) != null) {
                this.logger.warn("Overwrite prohibited for @Final method {} in {}. Skipping method.", new Object[]{methodNode.name, mixinTargetContext});
                return true;
            }
            return false;
        }
        if (!this.context.getSessionId().equals((String) Annotations.getValue(visible, "sessionId"))) {
            throw new ClassFormatError("Invalid @MixinMerged annotation found in" + mixinTargetContext + " at " + methodNode.name + " in " + this.targetClass.name);
        }
        if (Bytecode.hasFlag(methodNode2, 4160) && Bytecode.hasFlag(methodNode, 4160)) {
            if (mixinTargetContext.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                this.logger.warn("Synthetic bridge method clash for {} in {}", new Object[]{methodNode.name, mixinTargetContext});
                return true;
            }
            return true;
        }
        String str = (String) Annotations.getValue(visible, MixinLaunchPlugin.NAME);
        int iIntValue = ((Integer) Annotations.getValue(visible, "priority")).intValue();
        AnnotationNode singleVisible2 = Annotations.getSingleVisible(methodNode, new Class[]{Accessor.class, Invoker.class});
        if (singleVisible2 != null && (singleVisible = Annotations.getSingleVisible(methodNode2, new Class[]{Accessor.class, Invoker.class})) != null) {
            String str2 = (String) Annotations.getValue(singleVisible2, "target");
            String str3 = (String) Annotations.getValue(singleVisible, "target");
            if (str2 == null) {
                throw new MixinError("Encountered undecorated Accessor method in " + mixinTargetContext + " applying to " + this.targetName);
            }
            if (str2.equals(str3)) {
                return true;
            }
            throw new InvalidMixinException(mixinTargetContext, String.format("Incompatible @%s %s (for %s) in %s previously written by %s (for %s)", Bytecode.getSimpleName(singleVisible2), methodNode.name, str2, mixinTargetContext, str, str3));
        }
        if (iIntValue >= mixinTargetContext.getPriority() && !str.equals(mixinTargetContext.getClassName())) {
            this.logger.warn("Method overwrite conflict for {} in {}, previously written by {}. Skipping method.", new Object[]{methodNode.name, mixinTargetContext, str});
            return true;
        }
        if (Annotations.getVisible(methodNode2, Final.class) != null) {
            this.logger.warn("Method overwrite conflict for @Final method {} in {} declared by {}. Skipping method.", new Object[]{methodNode.name, mixinTargetContext, str});
            return true;
        }
        return false;
    }

    protected boolean mergeIntrinsic(MixinTargetContext mixinTargetContext, MethodNode methodNode, boolean z, MethodNode methodNode2, AnnotationNode annotationNode) {
        AnnotationNode visible;
        if (z) {
            throw new InvalidMixinException(mixinTargetContext, "@Intrinsic is not compatible with @Overwrite, remove one of these annotations on " + methodNode.name + " in " + mixinTargetContext);
        }
        String str = methodNode.name + methodNode.desc;
        if (Bytecode.hasFlag(methodNode, 8)) {
            throw new InvalidMixinException(mixinTargetContext, "@Intrinsic method cannot be static, found " + str + " in " + mixinTargetContext);
        }
        if (!Bytecode.hasFlag(methodNode, 4096) && ((visible = Annotations.getVisible(methodNode, MixinRenamed.class)) == null || !((Boolean) Annotations.getValue(visible, "isInterfaceMember", Boolean.FALSE)).booleanValue())) {
            throw new InvalidMixinException(mixinTargetContext, "@Intrinsic method must be prefixed interface method, no rename encountered on " + str + " in " + mixinTargetContext);
        }
        if (!((Boolean) Annotations.getValue(annotationNode, "displace", Boolean.FALSE)).booleanValue()) {
            this.logger.log(mixinTargetContext.getLoggingLevel(), "Skipping Intrinsic mixin method {} for {}", new Object[]{str, mixinTargetContext.getTargetClassRef()});
            return true;
        }
        displaceIntrinsic(mixinTargetContext, methodNode, methodNode2);
        return false;
    }

    protected void displaceIntrinsic(MixinTargetContext mixinTargetContext, MethodNode methodNode, MethodNode methodNode2) {
        String str = "proxy+" + methodNode2.name;
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            MethodInsnNode methodInsnNode = (AbstractInsnNode) it.next();
            if ((methodInsnNode instanceof MethodInsnNode) && methodInsnNode.getOpcode() != 184) {
                MethodInsnNode methodInsnNode2 = methodInsnNode;
                if (methodInsnNode2.owner.equals(this.targetClass.name) && methodInsnNode2.name.equals(methodNode2.name) && methodInsnNode2.desc.equals(methodNode2.desc)) {
                    methodInsnNode2.name = str;
                }
            }
        }
        methodNode2.name = str;
    }

    protected final void appendInsns(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        if (Type.getReturnType(methodNode.desc) != Type.VOID_TYPE) {
            throw new IllegalArgumentException("Attempted to merge insns from a method which does not return void");
        }
        MethodNode methodNodeFindTargetMethod = findTargetMethod(methodNode);
        if (methodNodeFindTargetMethod != null) {
            AbstractInsnNode abstractInsnNodeFindInsn = Bytecode.findInsn(methodNodeFindTargetMethod, Typography.plusMinus);
            if (abstractInsnNodeFindInsn != null) {
                ListIterator it = methodNode.instructions.iterator();
                while (it.hasNext()) {
                    AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
                    if (!(abstractInsnNode instanceof LineNumberNode) && abstractInsnNode.getOpcode() != 177) {
                        methodNodeFindTargetMethod.instructions.insertBefore(abstractInsnNodeFindInsn, abstractInsnNode);
                    }
                }
                methodNodeFindTargetMethod.maxLocals = Math.max(methodNodeFindTargetMethod.maxLocals, methodNode.maxLocals);
                methodNodeFindTargetMethod.maxStack = Math.max(methodNodeFindTargetMethod.maxStack, methodNode.maxStack);
                return;
            }
            return;
        }
        this.targetClass.methods.add(methodNode);
    }

    protected void applyInitialisers(MixinTargetContext mixinTargetContext) {
        Deque initialiser;
        MethodNode constructor = getConstructor(mixinTargetContext);
        if (constructor == null || (initialiser = getInitialiser(mixinTargetContext, constructor)) == null || initialiser.size() == 0) {
            return;
        }
        String superName = this.context.getClassInfo().getSuperName();
        for (MethodNode methodNode : this.targetClass.methods) {
            if (Constants.CTOR.equals(methodNode.name)) {
                Bytecode.DelegateInitialiser delegateInitialiserFindDelegateInit = Bytecode.findDelegateInit(methodNode, superName, this.targetClass.name);
                if (!delegateInitialiserFindDelegateInit.isPresent || delegateInitialiserFindDelegateInit.isSuper) {
                    methodNode.maxStack = Math.max(methodNode.maxStack, constructor.maxStack);
                    injectInitialiser(mixinTargetContext, methodNode, initialiser);
                }
            }
        }
    }

    protected MethodNode getConstructor(MixinTargetContext mixinTargetContext) {
        MethodNode methodNode = null;
        for (MethodNode methodNode2 : mixinTargetContext.getMethods()) {
            if (Constants.CTOR.equals(methodNode2.name) && Bytecode.methodHasLineNumbers(methodNode2)) {
                if (methodNode == null) {
                    methodNode = methodNode2;
                } else {
                    this.logger.warn(String.format("Mixin %s has multiple constructors, %s was selected\n", mixinTargetContext, methodNode.desc));
                }
            }
        }
        return methodNode;
    }

    private Range getConstructorRange(MethodNode methodNode) {
        boolean z = false;
        MethodInsnNode methodInsnNode = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int iIndexOf = -1;
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            MethodInsnNode methodInsnNode2 = (AbstractInsnNode) it.next();
            if (methodInsnNode2 instanceof LineNumberNode) {
                i = ((LineNumberNode) methodInsnNode2).line;
                z = true;
            } else if (methodInsnNode2 instanceof MethodInsnNode) {
                if (methodInsnNode2.getOpcode() == 183 && Constants.CTOR.equals(methodInsnNode2.name) && iIndexOf == -1) {
                    iIndexOf = methodNode.instructions.indexOf(methodInsnNode2);
                    i2 = i;
                }
            } else if (methodInsnNode2.getOpcode() == 181) {
                z = false;
            } else if (methodInsnNode2.getOpcode() == 177) {
                if (z) {
                    i3 = i;
                } else {
                    i3 = i2;
                    methodInsnNode = methodInsnNode2;
                }
            }
        }
        if (methodInsnNode != null) {
            LabelNode labelNode = new LabelNode(new Label());
            methodNode.instructions.insertBefore(methodInsnNode, labelNode);
            methodNode.instructions.insertBefore(methodInsnNode, new LineNumberNode(i2, labelNode));
        }
        return new Range(this, i2, i3, iIndexOf);
    }

    protected final Deque getInitialiser(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        Range constructorRange = getConstructorRange(methodNode);
        if (!constructorRange.isValid()) {
            return null;
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        boolean zExcludes = false;
        int i = -1;
        LabelNode labelNode = null;
        ListIterator it = methodNode.instructions.iterator(constructorRange.marker);
        while (it.hasNext()) {
            LabelNode labelNode2 = (AbstractInsnNode) it.next();
            if (labelNode2 instanceof LineNumberNode) {
                int i2 = ((LineNumberNode) labelNode2).line;
                AbstractInsnNode abstractInsnNode = methodNode.instructions.get(methodNode.instructions.indexOf(labelNode2) + 1);
                if (i2 == constructorRange.end && abstractInsnNode.getOpcode() != 177) {
                    zExcludes = true;
                    i = 177;
                } else {
                    zExcludes = constructorRange.excludes(i2);
                    i = -1;
                }
            } else if (zExcludes) {
                if (labelNode != null) {
                    arrayDeque.add(labelNode);
                    labelNode = null;
                }
                if (labelNode2 instanceof LabelNode) {
                    labelNode = labelNode2;
                } else {
                    int opcode = labelNode2.getOpcode();
                    if (opcode == i) {
                        i = -1;
                    } else {
                        for (int i3 : INITIALISER_OPCODE_BLACKLIST) {
                            if (opcode == i3) {
                                throw new InvalidMixinException(mixinTargetContext, "Cannot handle " + Bytecode.getOpcodeName(opcode) + " opcode (0x" + Integer.toHexString(opcode).toUpperCase(Locale.ROOT) + ") in class initialiser");
                            }
                        }
                        arrayDeque.add(labelNode2);
                    }
                }
            } else {
                continue;
            }
        }
        AbstractInsnNode abstractInsnNode2 = (AbstractInsnNode) arrayDeque.peekLast();
        if (abstractInsnNode2 != null && abstractInsnNode2.getOpcode() != 181) {
            throw new InvalidMixinException(mixinTargetContext, "Could not parse initialiser, expected 0xB5, found 0x" + Integer.toHexString(abstractInsnNode2.getOpcode()) + " in " + mixinTargetContext);
        }
        return arrayDeque;
    }

    protected final void injectInitialiser(MixinTargetContext mixinTargetContext, MethodNode methodNode, Deque deque) {
        Map mapCloneLabels = Bytecode.cloneLabels(methodNode.instructions);
        AbstractInsnNode abstractInsnNodeFindInitialiserInjectionPoint = findInitialiserInjectionPoint(mixinTargetContext, methodNode, deque);
        if (abstractInsnNodeFindInitialiserInjectionPoint == null) {
            this.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[]{methodNode.desc});
            return;
        }
        Iterator it = deque.iterator();
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
            if (!(abstractInsnNode instanceof LabelNode)) {
                if (abstractInsnNode instanceof JumpInsnNode) {
                    throw new InvalidMixinException(mixinTargetContext, "Unsupported JUMP opcode in initialiser in " + mixinTargetContext);
                }
                AbstractInsnNode abstractInsnNodeClone = abstractInsnNode.clone(mapCloneLabels);
                methodNode.instructions.insert(abstractInsnNodeFindInitialiserInjectionPoint, abstractInsnNodeClone);
                abstractInsnNodeFindInitialiserInjectionPoint = abstractInsnNodeClone;
            }
        }
    }

    protected AbstractInsnNode findInitialiserInjectionPoint(MixinTargetContext mixinTargetContext, MethodNode methodNode, Deque deque) {
        HashSet hashSet = new HashSet();
        Iterator it = deque.iterator();
        while (it.hasNext()) {
            FieldInsnNode fieldInsnNode = (AbstractInsnNode) it.next();
            if (fieldInsnNode.getOpcode() == 181) {
                hashSet.add(fieldKey(fieldInsnNode));
            }
        }
        InitialiserInjectionMode initialiserInjectionMode = getInitialiserInjectionMode(mixinTargetContext.getEnvironment());
        String name = this.targetClassInfo.getName();
        String superName = this.targetClassInfo.getSuperName();
        MethodInsnNode methodInsnNode = null;
        ListIterator it2 = methodNode.instructions.iterator();
        while (it2.hasNext()) {
            MethodInsnNode methodInsnNode2 = (AbstractInsnNode) it2.next();
            if (methodInsnNode2.getOpcode() == 183 && Constants.CTOR.equals(methodInsnNode2.name)) {
                String str = methodInsnNode2.owner;
                if (str.equals(name) || str.equals(superName)) {
                    methodInsnNode = methodInsnNode2;
                    if (initialiserInjectionMode == InitialiserInjectionMode.SAFE) {
                        break;
                    }
                }
            } else if (methodInsnNode2.getOpcode() == 181 && initialiserInjectionMode == InitialiserInjectionMode.DEFAULT && hashSet.contains(fieldKey((FieldInsnNode) methodInsnNode2))) {
                methodInsnNode = methodInsnNode2;
            }
        }
        return methodInsnNode;
    }

    private InitialiserInjectionMode getInitialiserInjectionMode(MixinEnvironment mixinEnvironment) {
        String optionValue = mixinEnvironment.getOptionValue(MixinEnvironment.Option.INITIALISER_INJECTION_MODE);
        if (optionValue == null) {
            return InitialiserInjectionMode.DEFAULT;
        }
        try {
            return InitialiserInjectionMode.valueOf(optionValue.toUpperCase(Locale.ROOT));
        } catch (Exception unused) {
            this.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[]{optionValue});
            return InitialiserInjectionMode.DEFAULT;
        }
    }

    private static String fieldKey(FieldInsnNode fieldInsnNode) {
        return String.format("%s:%s", fieldInsnNode.desc, fieldInsnNode.name);
    }

    protected void prepareInjections(MixinTargetContext mixinTargetContext) {
        mixinTargetContext.prepareInjections();
    }

    protected void applyInjections(MixinTargetContext mixinTargetContext) {
        mixinTargetContext.applyInjections();
    }

    protected void applyAccessors(MixinTargetContext mixinTargetContext) {
        for (MethodNode methodNode : mixinTargetContext.generateAccessors()) {
            if (!methodNode.name.startsWith("<")) {
                mergeMethod(mixinTargetContext, methodNode);
            }
        }
    }

    protected void checkMethodVisibility(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        if (Bytecode.hasFlag(methodNode, 8) && !Bytecode.hasFlag(methodNode, 2) && !Bytecode.hasFlag(methodNode, 4096) && Annotations.getVisible(methodNode, Overwrite.class) == null) {
            throw new InvalidMixinException(mixinTargetContext, String.format("Mixin %s contains non-private static method %s", mixinTargetContext, methodNode));
        }
    }

    protected void applySourceMap(TargetClassContext targetClassContext) {
        this.targetClass.sourceDebug = targetClassContext.getSourceMap().toString();
    }

    protected void checkMethodConstraints(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        Iterator it = CONSTRAINED_ANNOTATIONS.iterator();
        while (it.hasNext()) {
            AnnotationNode visible = Annotations.getVisible(methodNode, (Class) it.next());
            if (visible != null) {
                checkConstraints(mixinTargetContext, methodNode, visible);
            }
        }
    }

    protected final void checkConstraints(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        try {
            try {
                ConstraintParser.parse(annotationNode).check(mixinTargetContext.getEnvironment());
            } catch (ConstraintViolationException e) {
                String str = String.format("Constraint violation: %s on %s in %s", e.getMessage(), methodNode, mixinTargetContext);
                this.logger.warn(str);
                if (!mixinTargetContext.getEnvironment().getOption(MixinEnvironment.Option.IGNORE_CONSTRAINTS)) {
                    throw new InvalidMixinException(mixinTargetContext, str, e);
                }
            }
        } catch (InvalidConstraintException e2) {
            throw new InvalidMixinException(mixinTargetContext, e2.getMessage());
        }
    }

    protected final MethodNode findTargetMethod(MethodNode methodNode) {
        for (MethodNode methodNode2 : this.targetClass.methods) {
            if (methodNode2.name.equals(methodNode.name) && methodNode2.desc.equals(methodNode.desc)) {
                return methodNode2;
            }
        }
        return null;
    }

    protected final FieldNode findTargetField(FieldNode fieldNode) {
        for (FieldNode fieldNode2 : this.targetClass.fields) {
            if (fieldNode2.name.equals(fieldNode.name)) {
                return fieldNode2;
            }
        }
        return null;
    }
}
