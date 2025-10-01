package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.text.Typography;
import org.apache.log4j.spi.LocationInfo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.struct.MemberRef;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.spongepowered.asm.mixin.throwables.ClassMetadataNotFoundException;
import org.spongepowered.asm.mixin.transformer.ActivityStack;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ClassSignature;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinTargetContext.class */
public class MixinTargetContext extends ClassContext implements IMixinContext {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final MixinInfo mixin;
    private final ClassNode classNode;
    private final TargetClassContext targetClass;
    private final String sessionId;
    private final ClassInfo targetClassInfo;
    private final boolean inheritsFromMixin;
    private final boolean detachedSuper;
    private final SourceMap.File stratum;
    protected final ActivityStack activities = new ActivityStack(null);
    private final BiMap innerClasses = HashBiMap.create();
    private final List shadowMethods = new ArrayList();
    private final Map shadowFields = new LinkedHashMap();
    private final List mergedMethods = new ArrayList();
    private final InjectorGroupInfo.Map injectorGroups = new InjectorGroupInfo.Map();
    private final List injectors = new ArrayList();
    private final List accessors = new ArrayList();
    private int minRequiredClassVersion = MixinEnvironment.CompatibilityLevel.JAVA_6.classVersion();

    MixinTargetContext(MixinInfo mixinInfo, ClassNode classNode, TargetClassContext targetClassContext) {
        this.mixin = mixinInfo;
        this.classNode = classNode;
        this.targetClass = targetClassContext;
        this.targetClassInfo = targetClassContext.getClassInfo();
        this.stratum = targetClassContext.getSourceMap().addFile(this.classNode);
        this.inheritsFromMixin = mixinInfo.getClassInfo().hasMixinInHierarchy() || this.targetClassInfo.hasMixinTargetInHierarchy();
        this.detachedSuper = !this.classNode.superName.equals(getTarget().getClassNode().superName);
        this.sessionId = targetClassContext.getSessionId();
        requireVersion(classNode.version);
        InnerClassGenerator innerClassGenerator = (InnerClassGenerator) targetClassContext.getExtensions().getGenerator(InnerClassGenerator.class);
        for (String str : this.mixin.getInnerClasses()) {
            this.innerClasses.put(str, innerClassGenerator.registerInnerClass(this.mixin, str, this));
        }
    }

    void addShadowMethod(MethodNode methodNode) {
        this.shadowMethods.add(methodNode);
    }

    void addShadowField(FieldNode fieldNode, ClassInfo.Field field) {
        this.shadowFields.put(fieldNode, field);
    }

    void addAccessorMethod(MethodNode methodNode, Class cls) {
        this.accessors.add(AccessorInfo.m53of(this, methodNode, cls));
    }

    void addMixinMethod(MethodNode methodNode) {
        Annotations.setVisible(methodNode, MixinMerged.class, new Object[]{MixinLaunchPlugin.NAME, getClassName()});
        getTarget().addMixinMethod(methodNode);
    }

    void methodMerged(MethodNode methodNode) {
        this.mergedMethods.add(methodNode);
        this.targetClassInfo.addMethod(methodNode);
        getTarget().methodMerged(methodNode);
        Annotations.setVisible(methodNode, MixinMerged.class, new Object[]{MixinLaunchPlugin.NAME, getClassName(), "priority", Integer.valueOf(getPriority()), "sessionId", this.sessionId});
    }

    public String toString() {
        return this.mixin.toString();
    }

    public MixinEnvironment getEnvironment() {
        return this.mixin.getParent().getEnvironment();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public boolean getOption(MixinEnvironment.Option option) {
        return getEnvironment().getOption(option);
    }

    @Override // org.spongepowered.asm.mixin.transformer.ClassContext
    public ClassNode getClassNode() {
        return this.classNode;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public String getClassName() {
        return this.mixin.getClassName();
    }

    @Override // org.spongepowered.asm.mixin.transformer.ClassContext, org.spongepowered.asm.mixin.refmap.IMixinContext
    public String getClassRef() {
        return this.mixin.getClassRef();
    }

    public TargetClassContext getTarget() {
        return this.targetClass;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public String getTargetClassRef() {
        return getTarget().getClassRef();
    }

    public ClassNode getTargetClassNode() {
        return getTarget().getClassNode();
    }

    public ClassInfo getTargetClassInfo() {
        return this.targetClassInfo;
    }

    @Override // org.spongepowered.asm.mixin.transformer.ClassContext
    public ClassInfo getClassInfo() {
        return this.mixin.getClassInfo();
    }

    public ClassSignature getSignature() {
        return getClassInfo().getSignature();
    }

    public SourceMap.File getStratum() {
        return this.stratum;
    }

    public int getMinRequiredClassVersion() {
        return this.minRequiredClassVersion;
    }

    public int getDefaultRequiredInjections() {
        return this.mixin.getParent().getDefaultRequiredInjections();
    }

    public String getDefaultInjectorGroup() {
        return this.mixin.getParent().getDefaultInjectorGroup();
    }

    public int getMaxShiftByValue() {
        return this.mixin.getParent().getMaxShiftByValue();
    }

    public InjectorGroupInfo.Map getInjectorGroups() {
        return this.injectorGroups;
    }

    public boolean requireOverwriteAnnotations() {
        return this.mixin.getParent().requireOverwriteAnnotations();
    }

    void transformMethod(MethodNode methodNode) {
        this.activities.clear();
        try {
            ActivityStack.Activity activityBegin = this.activities.begin("Validate");
            validateMethod(methodNode);
            activityBegin.next("Transform Descriptor");
            transformDescriptor(methodNode);
            activityBegin.next("Transform LVT");
            transformLVT(methodNode);
            activityBegin.next("Transform Line Numbers");
            this.stratum.applyOffset(methodNode);
            activityBegin.next("Transform Instructions");
            MethodInsnNode methodInsnNode = null;
            ListIterator it = methodNode.instructions.iterator();
            while (it.hasNext()) {
                MethodInsnNode methodInsnNode2 = (AbstractInsnNode) it.next();
                ActivityStack.Activity activityBegin2 = this.activities.begin(Bytecode.getOpcodeName((AbstractInsnNode) methodInsnNode2) + " ");
                if (methodInsnNode2 instanceof MethodInsnNode) {
                    MethodInsnNode methodInsnNode3 = methodInsnNode2;
                    activityBegin2.append("%s::%s%s", new Object[]{methodInsnNode3.owner, methodInsnNode3.name, methodInsnNode3.desc});
                    transformMethodRef(methodNode, it, new MemberRef.Method(methodInsnNode3));
                } else if (methodInsnNode2 instanceof FieldInsnNode) {
                    FieldInsnNode fieldInsnNode = (FieldInsnNode) methodInsnNode2;
                    activityBegin2.append("%s::%s:%s", new Object[]{fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc});
                    transformFieldRef(methodNode, it, new MemberRef.Field(fieldInsnNode));
                    checkFinal(methodNode, it, fieldInsnNode);
                } else if (methodInsnNode2 instanceof TypeInsnNode) {
                    TypeInsnNode typeInsnNode = (TypeInsnNode) methodInsnNode2;
                    activityBegin2.append(typeInsnNode.desc);
                    transformTypeNode(methodNode, it, typeInsnNode, methodInsnNode);
                } else if (methodInsnNode2 instanceof LdcInsnNode) {
                    transformConstantNode(methodNode, it, (LdcInsnNode) methodInsnNode2);
                } else if (methodInsnNode2 instanceof InvokeDynamicInsnNode) {
                    InvokeDynamicInsnNode invokeDynamicInsnNode = (InvokeDynamicInsnNode) methodInsnNode2;
                    activityBegin2.append("%s %s", new Object[]{invokeDynamicInsnNode.name, invokeDynamicInsnNode.desc});
                    transformInvokeDynamicNode(methodNode, it, invokeDynamicInsnNode);
                }
                methodInsnNode = methodInsnNode2;
                activityBegin2.end();
            }
            activityBegin.end();
        } catch (InvalidMixinException e) {
            e.prepend(this.activities);
            throw e;
        } catch (Exception e2) {
            throw new InvalidMixinException(this, "Unexpecteded " + e2.getClass().getSimpleName() + " whilst transforming the mixin class:", e2, this.activities);
        }
    }

    private void validateMethod(MethodNode methodNode) {
        ClassInfo.Method methodFindMethodInHierarchy;
        if (Annotations.getInvisible(methodNode, SoftOverride.class) != null) {
            if (Bytecode.getVisibility(methodNode) == Bytecode.Visibility.PRIVATE) {
                throw new InvalidMixinException(this, "Mixin method " + methodNode.name + methodNode.desc + " is tagged with @SoftOverride but the method is PRIVATE");
            }
            ClassInfo.Method methodFindMethodInHierarchy2 = this.targetClassInfo.findMethodInHierarchy(methodNode.name, methodNode.desc, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.SUPER);
            if (methodFindMethodInHierarchy2 == null || !methodFindMethodInHierarchy2.isInjected()) {
                throw new InvalidMixinException(this, "Mixin method " + methodNode.name + methodNode.desc + " is tagged with @SoftOverride but no valid method was found in superclasses of " + getTarget().getClassName());
            }
        }
        if (Bytecode.isVirtual(methodNode) && (methodFindMethodInHierarchy = this.targetClassInfo.findMethodInHierarchy(methodNode, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.ALL, 0)) != null && methodFindMethodInHierarchy.isFinal()) {
            throw new InvalidMixinException(this.mixin, String.format("%s%s in %s overrides a final method from %s", methodNode.name, methodNode.desc, this.mixin, methodFindMethodInHierarchy.getOwner().getClassName()));
        }
    }

    private void transformLVT(MethodNode methodNode) {
        if (methodNode.localVariables == null) {
            return;
        }
        ActivityStack.Activity activityBegin = this.activities.begin(LocationInfo.f204NA);
        for (LocalVariableNode localVariableNode : methodNode.localVariables) {
            if (localVariableNode != null && localVariableNode.desc != null) {
                activityBegin.next("var=%s", new Object[]{localVariableNode.name});
                localVariableNode.desc = transformSingleDescriptor(Type.getType(localVariableNode.desc));
            }
        }
        activityBegin.end();
    }

    private void transformMethodRef(MethodNode methodNode, Iterator it, MemberRef memberRef) {
        transformDescriptor(memberRef);
        if (memberRef.getOwner().equals(getClassRef())) {
            memberRef.setOwner(getTarget().getClassRef());
            ClassInfo.Method methodFindMethod = getClassInfo().findMethod(memberRef.getName(), memberRef.getDesc(), 10);
            if (methodFindMethod != null && methodFindMethod.isRenamed() && methodFindMethod.getOriginalName().equals(memberRef.getName()) && (methodFindMethod.isSynthetic() || methodFindMethod.isConformed())) {
                memberRef.setName(methodFindMethod.getName());
            }
            upgradeMethodRef(methodNode, memberRef, methodFindMethod);
            return;
        }
        if (this.innerClasses.containsKey(memberRef.getOwner())) {
            memberRef.setOwner((String) this.innerClasses.get(memberRef.getOwner()));
            memberRef.setDesc(transformMethodDescriptor(memberRef.getDesc()));
        } else if (this.detachedSuper || this.inheritsFromMixin) {
            if (memberRef.getOpcode() == 183) {
                updateStaticBinding(methodNode, memberRef);
            } else if (memberRef.getOpcode() == 182 && ClassInfo.forName(memberRef.getOwner()).isMixin()) {
                updateDynamicBinding(methodNode, memberRef);
            }
        }
    }

    private void transformFieldRef(MethodNode methodNode, Iterator it, MemberRef memberRef) {
        if (Constants.IMAGINARY_SUPER.equals(memberRef.getName())) {
            if (memberRef instanceof MemberRef.Field) {
                processImaginarySuper(methodNode, ((MemberRef.Field) memberRef).insn);
                it.remove();
            } else {
                throw new InvalidMixinException(this.mixin, "Cannot call imaginary super from method handle.");
            }
        }
        transformDescriptor(memberRef);
        if (memberRef.getOwner().equals(getClassRef())) {
            memberRef.setOwner(getTarget().getClassRef());
            ClassInfo.Field fieldFindField = getClassInfo().findField(memberRef.getName(), memberRef.getDesc(), 10);
            if (fieldFindField != null && fieldFindField.isRenamed() && fieldFindField.getOriginalName().equals(memberRef.getName()) && fieldFindField.isStatic()) {
                memberRef.setName(fieldFindField.getName());
                return;
            }
            return;
        }
        ClassInfo classInfoForName = ClassInfo.forName(memberRef.getOwner());
        if (classInfoForName.isMixin()) {
            ClassInfo classInfoFindCorrespondingType = this.targetClassInfo.findCorrespondingType(classInfoForName);
            memberRef.setOwner(classInfoFindCorrespondingType != null ? classInfoFindCorrespondingType.getName() : getTarget().getClassRef());
        }
    }

    private void checkFinal(MethodNode methodNode, Iterator it, FieldInsnNode fieldInsnNode) {
        int opcode;
        if (!fieldInsnNode.owner.equals(getTarget().getClassRef()) || (opcode = fieldInsnNode.getOpcode()) == 180 || opcode == 178) {
            return;
        }
        for (Map.Entry entry : this.shadowFields.entrySet()) {
            FieldNode fieldNode = (FieldNode) entry.getKey();
            if (fieldNode.desc.equals(fieldInsnNode.desc) && fieldNode.name.equals(fieldInsnNode.name)) {
                ClassInfo.Field field = (ClassInfo.Field) entry.getValue();
                if (field.isDecoratedFinal()) {
                    if (field.isDecoratedMutable()) {
                        if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                            logger.warn("Write access to @Mutable @Final field {} in {}::{}", new Object[]{field, this.mixin, methodNode.name});
                            return;
                        }
                        return;
                    } else {
                        if (Constants.CTOR.equals(methodNode.name) || Constants.CLINIT.equals(methodNode.name)) {
                            logger.warn("@Final field {} in {} should be final", new Object[]{field, this.mixin});
                            return;
                        }
                        logger.error("Write access detected to @Final field {} in {}::{}", new Object[]{field, this.mixin, methodNode.name});
                        if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                            throw new InvalidMixinException(this.mixin, "Write access detected to @Final field " + field + " in " + this.mixin + "::" + methodNode.name);
                        }
                        return;
                    }
                }
                return;
            }
        }
    }

    private void transformTypeNode(MethodNode methodNode, Iterator it, TypeInsnNode typeInsnNode, AbstractInsnNode abstractInsnNode) {
        if (typeInsnNode.getOpcode() == 192 && typeInsnNode.desc.equals(getTarget().getClassRef()) && abstractInsnNode.getOpcode() == 25 && ((VarInsnNode) abstractInsnNode).var == 0) {
            it.remove();
            return;
        }
        if (typeInsnNode.desc.equals(getClassRef())) {
            typeInsnNode.desc = getTarget().getClassRef();
        } else {
            String str = (String) this.innerClasses.get(typeInsnNode.desc);
            if (str != null) {
                typeInsnNode.desc = str;
            }
        }
        transformDescriptor(typeInsnNode);
    }

    private void transformConstantNode(MethodNode methodNode, Iterator it, LdcInsnNode ldcInsnNode) {
        ldcInsnNode.cst = transformConstant(methodNode, it, ldcInsnNode.cst);
    }

    private void transformInvokeDynamicNode(MethodNode methodNode, Iterator it, InvokeDynamicInsnNode invokeDynamicInsnNode) {
        requireVersion(51);
        invokeDynamicInsnNode.desc = transformMethodDescriptor(invokeDynamicInsnNode.desc);
        invokeDynamicInsnNode.bsm = transformHandle(methodNode, it, invokeDynamicInsnNode.bsm);
        for (int i = 0; i < invokeDynamicInsnNode.bsmArgs.length; i++) {
            invokeDynamicInsnNode.bsmArgs[i] = transformConstant(methodNode, it, invokeDynamicInsnNode.bsmArgs[i]);
        }
    }

    private Object transformConstant(MethodNode methodNode, Iterator it, Object obj) {
        if (obj instanceof Type) {
            Type type = (Type) obj;
            String strTransformDescriptor = transformDescriptor(type);
            if (!type.toString().equals(strTransformDescriptor)) {
                return Type.getType(strTransformDescriptor);
            }
            return obj;
        }
        if (obj instanceof Handle) {
            return transformHandle(methodNode, it, (Handle) obj);
        }
        return obj;
    }

    private Handle transformHandle(MethodNode methodNode, Iterator it, Handle handle) {
        MemberRef.Handle handle2 = new MemberRef.Handle(handle);
        if (handle2.isField()) {
            transformFieldRef(methodNode, it, handle2);
        } else {
            transformMethodRef(methodNode, it, handle2);
        }
        return handle2.getMethodHandle();
    }

    private void processImaginarySuper(MethodNode methodNode, FieldInsnNode fieldInsnNode) {
        if (fieldInsnNode.getOpcode() != 180) {
            if (Constants.CTOR.equals(methodNode.name)) {
                throw new InvalidMixinException(this, "Illegal imaginary super declaration: field " + fieldInsnNode.name + " must not specify an initialiser");
            }
            throw new InvalidMixinException(this, "Illegal imaginary super access: found " + Bytecode.getOpcodeName(fieldInsnNode.getOpcode()) + " opcode in " + methodNode.name + methodNode.desc);
        }
        if ((methodNode.access & 2) != 0 || (methodNode.access & 8) != 0) {
            throw new InvalidMixinException(this, "Illegal imaginary super access: method " + methodNode.name + methodNode.desc + " is private or static");
        }
        if (Annotations.getInvisible(methodNode, SoftOverride.class) == null) {
            throw new InvalidMixinException(this, "Illegal imaginary super access: method " + methodNode.name + methodNode.desc + " is not decorated with @SoftOverride");
        }
        ListIterator it = methodNode.instructions.iterator(methodNode.instructions.indexOf(fieldInsnNode));
        while (it.hasNext()) {
            MethodInsnNode methodInsnNode = (AbstractInsnNode) it.next();
            if (methodInsnNode instanceof MethodInsnNode) {
                MethodInsnNode methodInsnNode2 = methodInsnNode;
                if (methodInsnNode2.owner.equals(getClassRef()) && methodInsnNode2.name.equals(methodNode.name) && methodInsnNode2.desc.equals(methodNode.desc)) {
                    methodInsnNode2.setOpcode(Typography.middleDot);
                    updateStaticBinding(methodNode, new MemberRef.Method(methodInsnNode2));
                    return;
                }
            }
        }
        throw new InvalidMixinException(this, "Illegal imaginary super access: could not find INVOKE for " + methodNode.name + methodNode.desc);
    }

    private void updateStaticBinding(MethodNode methodNode, MemberRef memberRef) {
        updateBinding(methodNode, memberRef, ClassInfo.Traversal.SUPER);
    }

    private void updateDynamicBinding(MethodNode methodNode, MemberRef memberRef) {
        updateBinding(methodNode, memberRef, ClassInfo.Traversal.ALL);
    }

    private void updateBinding(MethodNode methodNode, MemberRef memberRef, ClassInfo.Traversal traversal) {
        if (Constants.CTOR.equals(methodNode.name) || memberRef.getOwner().equals(getTarget().getClassRef()) || getTarget().getClassRef().startsWith("<")) {
            return;
        }
        ClassInfo.Method methodFindMethodInHierarchy = this.targetClassInfo.findMethodInHierarchy(memberRef.getName(), memberRef.getDesc(), traversal.getSearchType(), traversal);
        if (methodFindMethodInHierarchy != null) {
            if (methodFindMethodInHierarchy.getOwner().isMixin()) {
                throw new InvalidMixinException(this, "Invalid " + memberRef + " in " + this + " resolved " + methodFindMethodInHierarchy.getOwner() + " but is mixin.");
            }
            memberRef.setOwner(methodFindMethodInHierarchy.getImplementor().getName());
        } else if (ClassInfo.forName(memberRef.getOwner()).isMixin()) {
            throw new MixinTransformerError("Error resolving " + memberRef + " in " + this);
        }
    }

    void transformDescriptor(FieldNode fieldNode) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        fieldNode.desc = transformSingleDescriptor(fieldNode.desc, false);
    }

    void transformDescriptor(MethodNode methodNode) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        methodNode.desc = transformMethodDescriptor(methodNode.desc);
    }

    void transformDescriptor(MemberRef memberRef) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        if (memberRef.isField()) {
            memberRef.setDesc(transformSingleDescriptor(memberRef.getDesc(), false));
        } else {
            memberRef.setDesc(transformMethodDescriptor(memberRef.getDesc()));
        }
    }

    void transformDescriptor(TypeInsnNode typeInsnNode) {
        if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
            return;
        }
        typeInsnNode.desc = transformSingleDescriptor(typeInsnNode.desc, true);
    }

    private String transformDescriptor(Type type) {
        if (type.getSort() == 11) {
            return transformMethodDescriptor(type.getDescriptor());
        }
        return transformSingleDescriptor(type);
    }

    private String transformSingleDescriptor(Type type) {
        if (type.getSort() < 9) {
            return type.toString();
        }
        return transformSingleDescriptor(type.toString(), false);
    }

    private String transformSingleDescriptor(String str, boolean z) {
        ActivityStack.Activity activityBegin = this.activities.begin("desc=%s", new Object[]{str});
        String strSubstring = str;
        while (true) {
            if (!strSubstring.startsWith("[") && !strSubstring.startsWith("L")) {
                break;
            }
            if (strSubstring.startsWith("[")) {
                strSubstring = strSubstring.substring(1);
            } else {
                strSubstring = strSubstring.substring(1, strSubstring.indexOf(";"));
                z = true;
            }
        }
        if (!z) {
            activityBegin.end();
            return str;
        }
        String str2 = (String) this.innerClasses.get(strSubstring);
        if (str2 != null) {
            activityBegin.end();
            return str.replace(strSubstring, str2);
        }
        if (this.innerClasses.inverse().containsKey(strSubstring)) {
            activityBegin.end();
            return str;
        }
        ClassInfo classInfoForName = ClassInfo.forName(strSubstring);
        if (classInfoForName == null) {
            throw new ClassMetadataNotFoundException(strSubstring.replace('/', '.'));
        }
        if (!classInfoForName.isMixin() || classInfoForName.isLoadable()) {
            activityBegin.end();
            return str;
        }
        String strReplace = str.replace(strSubstring, findRealType(classInfoForName).toString());
        activityBegin.end();
        return strReplace;
    }

    private String transformMethodDescriptor(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (Type type : Type.getArgumentTypes(str)) {
            sb.append(transformSingleDescriptor(type));
        }
        return sb.append(')').append(transformSingleDescriptor(Type.getReturnType(str))).toString();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public Target getTargetMethod(MethodNode methodNode) {
        return getTarget().getTargetMethod(methodNode);
    }

    MethodNode findMethod(MethodNode methodNode, AnnotationNode annotationNode) {
        List list;
        LinkedList linkedList = new LinkedList();
        linkedList.add(methodNode.name);
        if (annotationNode != null && (list = (List) Annotations.getValue(annotationNode, "aliases")) != null) {
            linkedList.addAll(list);
        }
        return getTarget().findMethod(linkedList, methodNode.desc);
    }

    MethodNode findRemappedMethod(MethodNode methodNode) {
        String strMapMethodName = getEnvironment().getRemappers().mapMethodName(getTarget().getClassRef(), methodNode.name, methodNode.desc);
        if (strMapMethodName.equals(methodNode.name)) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(strMapMethodName);
        return getTarget().findAliasedMethod(linkedList, methodNode.desc);
    }

    FieldNode findField(FieldNode fieldNode, AnnotationNode annotationNode) {
        List list;
        LinkedList linkedList = new LinkedList();
        linkedList.add(fieldNode.name);
        if (annotationNode != null && (list = (List) Annotations.getValue(annotationNode, "aliases")) != null) {
            linkedList.addAll(list);
        }
        return getTarget().findAliasedField(linkedList, fieldNode.desc);
    }

    FieldNode findRemappedField(FieldNode fieldNode) {
        String strMapFieldName = getEnvironment().getRemappers().mapFieldName(getTarget().getClassRef(), fieldNode.name, fieldNode.desc);
        if (strMapFieldName.equals(fieldNode.name)) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(strMapFieldName);
        return getTarget().findAliasedField(linkedList, fieldNode.desc);
    }

    protected void requireVersion(int i) {
        this.minRequiredClassVersion = Math.max(this.minRequiredClassVersion, i);
        if (i > MixinEnvironment.getCompatibilityLevel().classVersion()) {
            throw new InvalidMixinException(this, "Unsupported mixin class version " + i);
        }
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public Extensions getExtensions() {
        return this.targetClass.getExtensions();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public IMixinInfo getMixin() {
        return this.mixin;
    }

    MixinInfo getInfo() {
        return this.mixin;
    }

    boolean isRequired() {
        return this.mixin.isRequired();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public int getPriority() {
        return this.mixin.getPriority();
    }

    Set getInterfaces() {
        return this.mixin.getInterfaces();
    }

    Collection getShadowMethods() {
        return this.shadowMethods;
    }

    List getMethods() {
        return this.classNode.methods;
    }

    Set getShadowFields() {
        return this.shadowFields.entrySet();
    }

    List getFields() {
        return this.classNode.fields;
    }

    Level getLoggingLevel() {
        return this.mixin.getLoggingLevel();
    }

    boolean shouldSetSourceFile() {
        return this.mixin.getParent().shouldSetSourceFile();
    }

    String getSourceFile() {
        return this.classNode.sourceFile;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IMixinContext
    public IReferenceMapper getReferenceMapper() {
        return this.mixin.getParent().getReferenceMapper();
    }

    void preApply(String str, ClassNode classNode) {
        this.mixin.preApply(str, classNode);
    }

    void postApply(String str, ClassNode classNode) {
        this.activities.clear();
        try {
            ActivityStack.Activity activityBegin = this.activities.begin("Validating Injector Groups");
            this.injectorGroups.validateAll();
            activityBegin.next("Plugin Post-Application");
            this.mixin.postApply(str, classNode);
            activityBegin.end();
        } catch (InjectionValidationException e) {
            throw new InjectionError(String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", e.getGroup(), this.mixin, e.getMessage()));
        } catch (InvalidMixinException e2) {
            e2.prepend(this.activities);
            throw e2;
        } catch (Exception e3) {
            throw new InvalidMixinException(this, "Unexpecteded " + e3.getClass().getSimpleName() + " whilst transforming the mixin class:", e3, this.activities);
        }
    }

    String getUniqueName(MethodNode methodNode, boolean z) {
        return this.targetClassInfo.getMethodMapper().getUniqueName(methodNode, this.sessionId, z);
    }

    String getUniqueName(FieldNode fieldNode) {
        return this.targetClassInfo.getMethodMapper().getUniqueName(fieldNode, this.sessionId);
    }

    void prepareInjections() {
        this.activities.clear();
        try {
            this.injectors.clear();
            ActivityStack.Activity activityBegin = this.activities.begin(LocationInfo.f204NA);
            for (MethodNode methodNode : this.mergedMethods) {
                activityBegin.next("%s%s", new Object[]{methodNode.name, methodNode.desc});
                ActivityStack.Activity activityBegin2 = this.activities.begin("Parse");
                InjectionInfo injectionInfo = InjectionInfo.parse(this, methodNode);
                if (injectionInfo != null) {
                    activityBegin2.next("Validate");
                    if (injectionInfo.isValid()) {
                        activityBegin2.next("Prepare");
                        injectionInfo.prepare();
                        this.injectors.add(injectionInfo);
                    }
                    activityBegin2.next("Undecorate");
                    methodNode.visibleAnnotations.remove(injectionInfo.getAnnotation());
                    activityBegin2.end();
                }
            }
            activityBegin.end();
        } catch (InvalidMixinException e) {
            e.prepend(this.activities);
            throw e;
        } catch (Exception e2) {
            throw new InvalidMixinException(this, "Unexpecteded " + e2.getClass().getSimpleName() + " whilst transforming the mixin class:", e2, this.activities);
        }
    }

    void applyInjections() {
        this.activities.clear();
        try {
            ActivityStack.Activity activityBegin = this.activities.begin("Inject");
            ActivityStack.Activity activityBegin2 = this.activities.begin(LocationInfo.f204NA);
            for (InjectionInfo injectionInfo : this.injectors) {
                activityBegin2.next(injectionInfo.toString());
                injectionInfo.inject();
            }
            activityBegin.next("PostInject");
            ActivityStack.Activity activityBegin3 = this.activities.begin(LocationInfo.f204NA);
            for (InjectionInfo injectionInfo2 : this.injectors) {
                activityBegin3.next(injectionInfo2.toString());
                injectionInfo2.postInject();
            }
            activityBegin.end();
            this.injectors.clear();
        } catch (InvalidMixinException e) {
            e.prepend(this.activities);
            throw e;
        } catch (Exception e2) {
            throw new InvalidMixinException(this, "Unexpecteded " + e2.getClass().getSimpleName() + " whilst transforming the mixin class:", e2, this.activities);
        }
    }

    List generateAccessors() {
        this.activities.clear();
        ArrayList arrayList = new ArrayList();
        try {
            ActivityStack.Activity activityBegin = this.activities.begin("Locate");
            ActivityStack.Activity activityBegin2 = this.activities.begin(LocationInfo.f204NA);
            for (AccessorInfo accessorInfo : this.accessors) {
                activityBegin2.next(accessorInfo.toString());
                accessorInfo.locate();
            }
            activityBegin.next("Validate");
            ActivityStack.Activity activityBegin3 = this.activities.begin(LocationInfo.f204NA);
            for (AccessorInfo accessorInfo2 : this.accessors) {
                activityBegin3.next(accessorInfo2.toString());
                accessorInfo2.validate();
            }
            activityBegin.next("Generate");
            ActivityStack.Activity activityBegin4 = this.activities.begin(LocationInfo.f204NA);
            for (AccessorInfo accessorInfo3 : this.accessors) {
                activityBegin4.next(accessorInfo3.toString());
                MethodNode methodNodeGenerate = accessorInfo3.generate();
                getTarget().addMixinMethod(methodNodeGenerate);
                arrayList.add(methodNodeGenerate);
            }
            activityBegin.end();
            return arrayList;
        } catch (InvalidMixinException e) {
            e.prepend(this.activities);
            throw e;
        } catch (Exception e2) {
            throw new InvalidMixinException(this, "Unexpecteded " + e2.getClass().getSimpleName() + " whilst transforming the mixin class:", e2, this.activities);
        }
    }

    private ClassInfo findRealType(ClassInfo classInfo) {
        if (classInfo == getClassInfo()) {
            return this.targetClassInfo;
        }
        ClassInfo classInfoFindCorrespondingType = this.targetClassInfo.findCorrespondingType(classInfo);
        if (classInfoFindCorrespondingType == null) {
            throw new InvalidMixinException(this, "Resolution error: unable to find corresponding type for " + classInfo + " in hierarchy of " + this.targetClassInfo);
        }
        return classInfoFindCorrespondingType;
    }
}
