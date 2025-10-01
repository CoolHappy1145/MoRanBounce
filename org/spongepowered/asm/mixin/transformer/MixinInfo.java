package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTargetAlreadyLoadedException;
import org.spongepowered.asm.service.IClassTracker;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.asm.ASM;
import org.spongepowered.asm.util.asm.MethodNodeEx;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo.class */
class MixinInfo implements Comparable, IMixinInfo {
    static int mixinOrder = 0;
    private final MixinConfig parent;
    private final String name;
    private final String className;
    private final int priority;
    private final boolean virtual;
    private final List declaredTargets;
    private final int order;
    private final IMixinService service;
    private final PluginHandle plugin;
    private final MixinEnvironment.Phase phase;
    private final ClassInfo info;
    private final SubType type;
    private final boolean strict;
    private State pendingState;
    private State state;
    private final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final Profiler profiler = MixinEnvironment.getProfiler();
    private final List targetClasses = new ArrayList();
    private final List targetClassNames = new ArrayList();

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$Variant.class */
    enum Variant {
        STANDARD,
        INTERFACE,
        ACCESSOR,
        PROXY
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareTo((MixinInfo) obj);
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public ClassNode getClassNode(int i) {
        return getClassNode(i);
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$MixinMethodNode.class */
    class MixinMethodNode extends MethodNodeEx {
        final MixinInfo this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MixinMethodNode(MixinInfo mixinInfo, int i, String str, String str2, String str3, String[] strArr) {
            super(i, str, str2, str3, strArr, mixinInfo);
            this.this$0 = mixinInfo;
        }

        public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object[] objArr) {
            Object[] objArr2 = new Object[objArr.length];
            System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
            this.instructions.add(new InvokeDynamicInsnNode(str, str2, handle, objArr2));
        }

        public boolean isInjector() {
            return getInjectorAnnotation() != null || isSurrogate();
        }

        public boolean isSurrogate() {
            return getVisibleAnnotation(Surrogate.class) != null;
        }

        public boolean isSynthetic() {
            return Bytecode.hasFlag(this, 4096);
        }

        public AnnotationNode getVisibleAnnotation(Class cls) {
            return Annotations.getVisible(this, cls);
        }

        public AnnotationNode getInjectorAnnotation() {
            return InjectionInfo.getInjectorAnnotation(this.this$0, this);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$MixinClassNode.class */
    class MixinClassNode extends ClassNode {
        public final List mixinMethods;
        final MixinInfo this$0;

        MixinClassNode(MixinInfo mixinInfo, MixinInfo mixinInfo2) {
            this(mixinInfo, ASM.API_VERSION);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected MixinClassNode(MixinInfo mixinInfo, int i) {
            super(i);
            this.this$0 = mixinInfo;
            this.mixinMethods = this.methods;
        }

        public MixinInfo getMixin() {
            return this.this$0;
        }

        public List getFields() {
            return new ArrayList(this.fields);
        }

        public MethodVisitor visitMethod(int i, String str, String str2, String str3, String[] strArr) {
            MixinMethodNode mixinMethodNode = new MixinMethodNode(this.this$0, i, str, str2, str3, strArr);
            this.methods.add(mixinMethodNode);
            return mixinMethodNode;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$State.class */
    class State {
        private final ClassNode classNode;
        private final ClassInfo classInfo;
        private boolean detachedSuper;
        private boolean unique;
        protected final Set interfaces;
        protected final List softImplements;
        protected final Set syntheticInnerClasses;
        protected final Set innerClasses;
        protected MixinClassNode validationClassNode;
        final MixinInfo this$0;

        State(MixinInfo mixinInfo, ClassNode classNode) {
            this(mixinInfo, classNode, null);
        }

        State(MixinInfo mixinInfo, ClassNode classNode, ClassInfo classInfo) {
            this.this$0 = mixinInfo;
            this.interfaces = new HashSet();
            this.softImplements = new ArrayList();
            this.syntheticInnerClasses = new HashSet();
            this.innerClasses = new HashSet();
            this.classNode = classNode;
            connect();
            this.classInfo = classInfo != null ? classInfo : ClassInfo.fromClassNode(getValidationClassNode());
        }

        protected void connect() {
            this.validationClassNode = createClassNode(0);
        }

        protected void complete() {
            this.validationClassNode = null;
        }

        ClassInfo getClassInfo() {
            return this.classInfo;
        }

        ClassNode getClassNode() {
            return this.classNode;
        }

        MixinClassNode getValidationClassNode() {
            if (this.validationClassNode == null) {
                throw new IllegalStateException("Attempted a validation task after validation is complete on " + this + " in " + this.this$0);
            }
            return this.validationClassNode;
        }

        boolean isDetachedSuper() {
            return this.detachedSuper;
        }

        boolean isUnique() {
            return this.unique;
        }

        List getSoftImplements() {
            return this.softImplements;
        }

        Set getSyntheticInnerClasses() {
            return this.syntheticInnerClasses;
        }

        Set getInnerClasses() {
            return this.innerClasses;
        }

        Set getInterfaces() {
            return this.interfaces;
        }

        MixinClassNode createClassNode(int i) {
            MixinClassNode mixinClassNode = new MixinClassNode(this.this$0, this.this$0);
            this.classNode.accept(mixinClassNode);
            return mixinClassNode;
        }

        void validate(SubType subType, List list) {
            MixinClassNode validationClassNode = getValidationClassNode();
            MixinPreProcessorStandard mixinPreProcessorStandardPrepare = subType.createPreProcessor(validationClassNode).prepare();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                mixinPreProcessorStandardPrepare.conform((ClassInfo) it.next());
            }
            subType.validate(this, list);
            this.detachedSuper = subType.isDetachedSuper();
            this.unique = Annotations.getVisible(validationClassNode, Unique.class) != null;
            validateInner();
            validateClassVersion();
            validateRemappables(list);
            readImplementations(subType);
            readInnerClasses();
            validateChanges(subType, list);
            complete();
        }

        private void validateInner() {
            if (!this.classInfo.isProbablyStatic()) {
                throw new InvalidMixinException(this.this$0, "Inner class mixin must be declared static");
            }
        }

        private void validateClassVersion() {
            if (this.validationClassNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
                String str = ".";
                for (MixinEnvironment.CompatibilityLevel compatibilityLevel : MixinEnvironment.CompatibilityLevel.values()) {
                    if (compatibilityLevel.classVersion() >= this.validationClassNode.version) {
                        str = String.format(". Mixin requires compatibility level %s or above.", compatibilityLevel.name());
                    }
                }
                throw new InvalidMixinException(this.this$0, "Unsupported mixin class version " + this.validationClassNode.version + str);
            }
        }

        private void validateRemappables(List list) {
            if (list.size() > 1) {
                for (FieldNode fieldNode : this.validationClassNode.fields) {
                    validateRemappable(Shadow.class, fieldNode.name, Annotations.getVisible(fieldNode, Shadow.class));
                }
                for (MethodNode methodNode : this.validationClassNode.methods) {
                    validateRemappable(Shadow.class, methodNode.name, Annotations.getVisible(methodNode, Shadow.class));
                    if (Annotations.getVisible(methodNode, Overwrite.class) != null && ((methodNode.access & 8) == 0 || (methodNode.access & 1) == 0)) {
                        throw new InvalidMixinException(this.this$0, "Found @Overwrite annotation on " + methodNode.name + " in " + this.this$0);
                    }
                }
            }
        }

        private void validateRemappable(Class cls, String str, AnnotationNode annotationNode) {
            if (annotationNode != null && ((Boolean) Annotations.getValue(annotationNode, "remap", Boolean.TRUE)).booleanValue()) {
                throw new InvalidMixinException(this.this$0, "Found a remappable @" + cls.getSimpleName() + " annotation on " + str + " in " + this);
            }
        }

        void readImplementations(SubType subType) {
            List list;
            this.interfaces.addAll(this.validationClassNode.interfaces);
            this.interfaces.addAll(subType.getInterfaces());
            AnnotationNode invisible = Annotations.getInvisible(this.validationClassNode, Implements.class);
            if (invisible == null || (list = (List) Annotations.getValue(invisible)) == null) {
                return;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                InterfaceInfo interfaceInfoFromAnnotation = InterfaceInfo.fromAnnotation(this.this$0, (AnnotationNode) it.next());
                this.softImplements.add(interfaceInfoFromAnnotation);
                this.interfaces.add(interfaceInfoFromAnnotation.getInternalName());
                if (!(this instanceof Reloaded)) {
                    this.classInfo.addInterface(interfaceInfoFromAnnotation.getInternalName());
                }
            }
        }

        void readInnerClasses() {
            for (InnerClassNode innerClassNode : this.validationClassNode.innerClasses) {
                ClassInfo classInfoForName = ClassInfo.forName(innerClassNode.name);
                if ((innerClassNode.outerName != null && innerClassNode.outerName.equals(this.classInfo.getName())) || innerClassNode.name.startsWith(this.validationClassNode.name + ArgsClassGenerator.GETTER_PREFIX)) {
                    if (classInfoForName.isProbablyStatic() && classInfoForName.isSynthetic()) {
                        this.syntheticInnerClasses.add(innerClassNode.name);
                    } else {
                        this.innerClasses.add(innerClassNode.name);
                    }
                }
            }
        }

        protected void validateChanges(SubType subType, List list) {
            subType.createPreProcessor(this.validationClassNode).prepare();
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$Reloaded.class */
    class Reloaded extends State {
        private final State previous;
        final MixinInfo this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        Reloaded(MixinInfo mixinInfo, State state, ClassNode classNode) {
            super(mixinInfo, classNode, state.getClassInfo());
            this.this$0 = mixinInfo;
            this.previous = state;
        }

        @Override // org.spongepowered.asm.mixin.transformer.MixinInfo.State
        protected void validateChanges(SubType subType, List list) {
            if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses)) {
                throw new MixinReloadException(this.this$0, "Cannot change inner classes");
            }
            if (!this.interfaces.equals(this.previous.interfaces)) {
                throw new MixinReloadException(this.this$0, "Cannot change interfaces");
            }
            if (!new HashSet(this.softImplements).equals(new HashSet(this.previous.softImplements))) {
                throw new MixinReloadException(this.this$0, "Cannot change soft interfaces");
            }
            if (!new HashSet(this.this$0.readTargetClasses(this.validationClassNode, true)).equals(new HashSet(list))) {
                throw new MixinReloadException(this.this$0, "Cannot change target classes");
            }
            if (this.this$0.readPriority(this.validationClassNode) != this.this$0.getPriority()) {
                throw new MixinReloadException(this.this$0, "Cannot change mixin priority");
            }
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$SubType.class */
    static abstract class SubType {
        protected final MixinInfo mixin;
        protected final String annotationType;
        protected final boolean targetMustBeInterface;
        protected boolean detached;

        abstract void validate(State state, List list);

        abstract MixinPreProcessorStandard createPreProcessor(MixinClassNode mixinClassNode);

        SubType(MixinInfo mixinInfo, String str, boolean z) {
            this.mixin = mixinInfo;
            this.annotationType = str;
            this.targetMustBeInterface = z;
        }

        Collection getInterfaces() {
            return Collections.emptyList();
        }

        boolean isDetachedSuper() {
            return this.detached;
        }

        void validateTarget(String str, ClassInfo classInfo) {
            boolean zIsInterface = classInfo.isInterface();
            if (zIsInterface != this.targetMustBeInterface) {
                throw new InvalidMixinException(this.mixin, this.annotationType + " target type mismatch: " + str + " is " + (zIsInterface ? "" : "not ") + "an interface in " + this);
            }
        }

        /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$SubType$Standard.class */
        static class Standard extends SubType {
            Standard(MixinInfo mixinInfo) {
                super(mixinInfo, "@Mixin", false);
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinInfo.SubType
            void validate(State state, List list) {
                MixinClassNode validationClassNode = state.getValidationClassNode();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ClassInfo classInfo = (ClassInfo) it.next();
                    if (!((ClassNode) validationClassNode).superName.equals(classInfo.getSuperName())) {
                        if (!classInfo.hasSuperClass(((ClassNode) validationClassNode).superName, ClassInfo.Traversal.SUPER)) {
                            ClassInfo classInfoForName = ClassInfo.forName(((ClassNode) validationClassNode).superName);
                            if (classInfoForName.isMixin()) {
                                for (ClassInfo classInfo2 : classInfoForName.getTargets()) {
                                    if (list.contains(classInfo2)) {
                                        throw new InvalidMixinException(this.mixin, "Illegal hierarchy detected. Derived mixin " + this + " targets the same class " + classInfo2.getClassName() + " as its superclass " + classInfoForName.getClassName());
                                    }
                                }
                            }
                            throw new InvalidMixinException(this.mixin, "Super class '" + ((ClassNode) validationClassNode).superName.replace('/', '.') + "' of " + this.mixin.getName() + " was not found in the hierarchy of target class '" + classInfo + "'");
                        }
                        this.detached = true;
                    }
                }
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinInfo.SubType
            MixinPreProcessorStandard createPreProcessor(MixinClassNode mixinClassNode) {
                return new MixinPreProcessorStandard(this.mixin, mixinClassNode);
            }
        }

        /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$SubType$Interface.class */
        static class Interface extends SubType {
            Interface(MixinInfo mixinInfo) {
                super(mixinInfo, "@Mixin", true);
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinInfo.SubType
            void validate(State state, List list) {
                if (!MixinEnvironment.getCompatibilityLevel().supports(1)) {
                    throw new InvalidMixinException(this.mixin, "Interface mixin not supported in current enviromnment");
                }
                MixinClassNode validationClassNode = state.getValidationClassNode();
                if (!Constants.OBJECT.equals(((ClassNode) validationClassNode).superName)) {
                    throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + ((ClassNode) validationClassNode).superName.replace('/', '.'));
                }
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinInfo.SubType
            MixinPreProcessorStandard createPreProcessor(MixinClassNode mixinClassNode) {
                return new MixinPreProcessorInterface(this.mixin, mixinClassNode);
            }
        }

        /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$SubType$Accessor.class */
        static class Accessor extends SubType {
            private final Collection interfaces;

            Accessor(MixinInfo mixinInfo) {
                super(mixinInfo, "@Mixin", false);
                this.interfaces = new ArrayList();
                this.interfaces.add(mixinInfo.getClassRef());
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinInfo.SubType
            Collection getInterfaces() {
                return this.interfaces;
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinInfo.SubType
            void validateTarget(String str, ClassInfo classInfo) {
                if (classInfo.isInterface() && !MixinEnvironment.getCompatibilityLevel().supports(1)) {
                    throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");
                }
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinInfo.SubType
            void validate(State state, List list) {
                MixinClassNode validationClassNode = state.getValidationClassNode();
                if (!Constants.OBJECT.equals(((ClassNode) validationClassNode).superName)) {
                    throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + ((ClassNode) validationClassNode).superName.replace('/', '.'));
                }
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinInfo.SubType
            MixinPreProcessorStandard createPreProcessor(MixinClassNode mixinClassNode) {
                return new MixinPreProcessorAccessor(this.mixin, mixinClassNode);
            }
        }

        static SubType getTypeFor(MixinInfo mixinInfo) {
            Variant variant = MixinInfo.getVariant(mixinInfo.getClassInfo());
            switch (C05691.f224x64ae1d1f[variant.ordinal()]) {
                case 1:
                    return new Standard(mixinInfo);
                case 2:
                    return new Interface(mixinInfo);
                case 3:
                    return new Accessor(mixinInfo);
                default:
                    throw new IllegalStateException("Unsupported Mixin variant " + variant + " for " + mixinInfo);
            }
        }
    }

    /* renamed from: org.spongepowered.asm.mixin.transformer.MixinInfo$1 */
    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$1.class */
    static /* synthetic */ class C05691 {

        /* renamed from: $SwitchMap$org$spongepowered$asm$mixin$transformer$MixinInfo$Variant */
        static final int[] f224x64ae1d1f = new int[Variant.values().length];

        static {
            try {
                f224x64ae1d1f[Variant.STANDARD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f224x64ae1d1f[Variant.INTERFACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f224x64ae1d1f[Variant.ACCESSOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinInfo$DeclaredTarget.class */
    static final class DeclaredTarget {
        final String name;
        final boolean isPrivate;

        private DeclaredTarget(String str, boolean z) {
            this.name = str;
            this.isPrivate = z;
        }

        public String toString() {
            return this.name;
        }

        /* renamed from: of */
        static DeclaredTarget m68of(Object obj, MixinInfo mixinInfo) {
            if (obj instanceof String) {
                String strRemapClassName = mixinInfo.remapClassName((String) obj);
                if (strRemapClassName != null) {
                    return new DeclaredTarget(strRemapClassName, true);
                }
                return null;
            }
            if (obj instanceof Type) {
                return new DeclaredTarget(((Type) obj).getClassName(), false);
            }
            return null;
        }
    }

    MixinInfo(IMixinService iMixinService, MixinConfig mixinConfig, String str, PluginHandle pluginHandle, boolean z) {
        IClassTracker classTracker;
        int i = mixinOrder;
        mixinOrder = i + 1;
        this.order = i;
        this.service = iMixinService;
        this.parent = mixinConfig;
        this.name = str;
        this.className = mixinConfig.getMixinPackage() + str;
        this.plugin = pluginHandle;
        this.phase = mixinConfig.getEnvironment().getPhase();
        this.strict = mixinConfig.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_TARGETS);
        try {
            this.pendingState = new State(this, loadMixinClass(this.className));
            this.info = this.pendingState.getClassInfo();
            this.type = SubType.getTypeFor(this);
            SubType subType = this.type;
            if (0 == 0 && (classTracker = this.service.getClassTracker()) != null) {
                classTracker.registerInvalidClass(this.className);
            }
            try {
                this.priority = readPriority(this.pendingState.getClassNode());
                this.virtual = readPseudo(this.pendingState.getValidationClassNode());
                this.declaredTargets = readDeclaredTargets(this.pendingState.getValidationClassNode(), z);
            } catch (InvalidMixinException e) {
                throw e;
            } catch (Exception e2) {
                throw new InvalidMixinException(this, e2);
            }
        } catch (InvalidMixinException e3) {
            throw e3;
        } catch (Exception e4) {
            throw new InvalidMixinException(this, e4.getMessage(), e4);
        }
    }

    void parseTargets() {
        try {
            this.targetClasses.addAll(readTargetClasses(this.declaredTargets));
            this.targetClassNames.addAll(Lists.transform(this.targetClasses, Functions.toStringFunction()));
        } catch (InvalidMixinException e) {
            throw e;
        } catch (Exception e2) {
            throw new InvalidMixinException(this, e2);
        }
    }

    void validate() {
        if (this.pendingState == null) {
            throw new IllegalStateException("No pending validation state for " + this);
        }
        try {
            this.pendingState.validate(this.type, this.targetClasses);
            this.state = this.pendingState;
        } finally {
            this.pendingState = null;
        }
    }

    protected List readDeclaredTargets(MixinClassNode mixinClassNode, boolean z) {
        if (mixinClassNode == null) {
            return Collections.emptyList();
        }
        AnnotationNode invisible = Annotations.getInvisible(mixinClassNode, Mixin.class);
        if (invisible == null) {
            throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
        }
        IClassTracker classTracker = this.service.getClassTracker();
        ArrayList arrayList = new ArrayList();
        Iterator it = readTargets(invisible).iterator();
        while (it.hasNext()) {
            DeclaredTarget declaredTargetM68of = DeclaredTarget.m68of(it.next(), this);
            if (declaredTargetM68of != null) {
                if (classTracker != null && classTracker.isClassLoaded(declaredTargetM68of.name) && !isReloading()) {
                    String str = String.format("Critical problem: %s target %s was loaded too early.", this, declaredTargetM68of.name);
                    if (this.parent.isRequired()) {
                        throw new MixinTargetAlreadyLoadedException(this, str, declaredTargetM68of.name);
                    }
                    this.logger.error(str);
                }
                if (shouldApplyMixin(z, declaredTargetM68of.name)) {
                    arrayList.add(declaredTargetM68of);
                }
            }
        }
        return arrayList;
    }

    private Iterable readTargets(AnnotationNode annotationNode) {
        Iterable iterable = (Iterable) Annotations.getValue(annotationNode, PropertyDescriptor.VALUE);
        Iterable iterable2 = (Iterable) Annotations.getValue(annotationNode, "targets");
        if (iterable == null && iterable2 == null) {
            return Collections.emptyList();
        }
        if (iterable == null) {
            return iterable2;
        }
        return iterable2 == null ? iterable : Iterables.concat(iterable, iterable2);
    }

    private boolean shouldApplyMixin(boolean z, String str) {
        Profiler.Section sectionBegin = this.profiler.begin("plugin");
        boolean z2 = z || this.plugin.shouldApplyMixin(str, this.className);
        sectionBegin.end();
        return z2;
    }

    List readTargetClasses(MixinClassNode mixinClassNode, boolean z) {
        return readTargetClasses(readDeclaredTargets(mixinClassNode, z));
    }

    private List readTargetClasses(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ClassInfo targetClass = getTargetClass((DeclaredTarget) it.next());
            if (targetClass != null) {
                arrayList.add(targetClass);
                targetClass.addMixin(this);
            }
        }
        return arrayList;
    }

    private ClassInfo getTargetClass(DeclaredTarget declaredTarget) {
        ClassInfo classInfoForName = ClassInfo.forName(declaredTarget.name);
        if (classInfoForName == null) {
            if (isVirtual()) {
                this.logger.debug("Skipping virtual target {} for {}", new Object[]{declaredTarget.name, this});
                return null;
            }
            handleTargetError(String.format("@Mixin target %s was not found %s", declaredTarget.name, this));
            return null;
        }
        this.type.validateTarget(declaredTarget.name, classInfoForName);
        if (declaredTarget.isPrivate && classInfoForName.isPublic() && !isVirtual()) {
            handleTargetError(String.format("@Mixin target %s is public in %s and should be specified in value", declaredTarget.name, this));
        }
        return classInfoForName;
    }

    private void handleTargetError(String str) {
        if (this.strict) {
            this.logger.error(str);
            throw new InvalidMixinException(this, str);
        }
        this.logger.warn(str);
    }

    protected int readPriority(ClassNode classNode) {
        if (classNode == null) {
            return this.parent.getDefaultMixinPriority();
        }
        AnnotationNode invisible = Annotations.getInvisible(classNode, Mixin.class);
        if (invisible == null) {
            throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
        }
        Integer num = (Integer) Annotations.getValue(invisible, "priority");
        return num == null ? this.parent.getDefaultMixinPriority() : num.intValue();
    }

    protected boolean readPseudo(ClassNode classNode) {
        return Annotations.getInvisible(classNode, Pseudo.class) != null;
    }

    private boolean isReloading() {
        return this.pendingState instanceof Reloaded;
    }

    String remapClassName(String str) {
        return this.parent.remapClassName(getClassRef(), str);
    }

    public boolean hasDeclaredTarget(String str) {
        Iterator it = this.declaredTargets.iterator();
        while (it.hasNext()) {
            if (str.equals(((DeclaredTarget) it.next()).name)) {
                return true;
            }
        }
        return false;
    }

    private State getState() {
        return this.state != null ? this.state : this.pendingState;
    }

    ClassInfo getClassInfo() {
        return this.info;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public IMixinConfig getConfig() {
        return this.parent;
    }

    MixinConfig getParent() {
        return this.parent;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public int getPriority() {
        return this.priority;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public String getName() {
        return this.name;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public String getClassName() {
        return this.className;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public String getClassRef() {
        return getClassInfo().getName();
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public byte[] getClassBytes() {
        throw new RuntimeException("NO");
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public boolean isDetachedSuper() {
        return getState().isDetachedSuper();
    }

    public boolean isUnique() {
        return getState().isUnique();
    }

    public boolean isVirtual() {
        return this.virtual;
    }

    public boolean isAccessor() {
        return this.type instanceof SubType.Accessor;
    }

    public boolean isLoadable() {
        SubType subType = this.type;
        return false;
    }

    public boolean isRequired() {
        return this.parent.isRequired();
    }

    public Level getLoggingLevel() {
        return this.parent.getLoggingLevel();
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public MixinEnvironment.Phase getPhase() {
        return this.phase;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public MixinClassNode getClassNode(int i) {
        return getState().createClassNode(i);
    }

    List getDeclaredTargetClasses() {
        return Collections.unmodifiableList(Lists.transform(this.declaredTargets, Functions.toStringFunction()));
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinInfo
    public List getTargetClasses() {
        return Collections.unmodifiableList(this.targetClassNames);
    }

    List getSoftImplements() {
        return Collections.unmodifiableList(getState().getSoftImplements());
    }

    Set getSyntheticInnerClasses() {
        return Collections.unmodifiableSet(getState().getSyntheticInnerClasses());
    }

    Set getInnerClasses() {
        return Collections.unmodifiableSet(getState().getInnerClasses());
    }

    List getTargets() {
        return Collections.unmodifiableList(this.targetClasses);
    }

    Set getInterfaces() {
        return getState().getInterfaces();
    }

    MixinTargetContext createContextFor(TargetClassContext targetClassContext) {
        MixinClassNode classNode = getClassNode(8);
        Profiler.Section sectionBegin = this.profiler.begin("pre");
        MixinTargetContext mixinTargetContextCreateContextFor = this.type.createPreProcessor(classNode).prepare().createContextFor(targetClassContext);
        sectionBegin.end();
        return mixinTargetContextCreateContextFor;
    }

    private ClassNode loadMixinClass(String str) throws ClassNotFoundException {
        try {
            IClassTracker classTracker = this.service.getClassTracker();
            if (classTracker != null) {
                String classRestrictions = classTracker.getClassRestrictions(str);
                if (classRestrictions.length() > 0) {
                    this.logger.error("Classloader restrictions [{}] encountered loading {}, name: {}", new Object[]{classRestrictions, this, str});
                }
            }
            return this.service.getBytecodeProvider().getClassNode(str, true);
        } catch (IOException e) {
            this.logger.warn("Failed to load mixin {}, the specified mixin will not be applied", new Object[]{str});
            throw new InvalidMixinException(this, "An error was encountered whilst loading the mixin class", e);
        } catch (ClassNotFoundException unused) {
            throw new ClassNotFoundException(String.format("The specified mixin '%s' was not found", str));
        }
    }

    void reloadMixin(ClassNode classNode) {
        if (this.pendingState != null) {
            throw new IllegalStateException("Cannot reload mixin while it is initialising");
        }
        this.pendingState = new Reloaded(this, this.state, classNode);
        validate();
    }

    public int compareTo(MixinInfo mixinInfo) {
        if (mixinInfo == null) {
            return 0;
        }
        if (mixinInfo.priority == this.priority) {
            return this.order - mixinInfo.order;
        }
        return this.priority - mixinInfo.priority;
    }

    public void preApply(String str, ClassNode classNode) {
        if (this.plugin.isAvailable()) {
            Profiler.Section sectionBegin = this.profiler.begin("plugin");
            try {
                this.plugin.preApply(str, classNode, this.className, this);
            } finally {
                sectionBegin.end();
            }
        }
    }

    public void postApply(String str, ClassNode classNode) {
        if (this.plugin.isAvailable()) {
            Profiler.Section sectionBegin = this.profiler.begin("plugin");
            try {
                this.plugin.postApply(str, classNode, this.className, this);
            } finally {
                sectionBegin.end();
            }
        }
        this.parent.postApply(str, classNode);
        this.info.addAppliedMixin(this);
    }

    public String toString() {
        return String.format("%s:%s", this.parent.getName(), this.name);
    }

    static Variant getVariant(ClassNode classNode) {
        return getVariant(ClassInfo.fromClassNode(classNode));
    }

    static Variant getVariant(ClassInfo classInfo) {
        if (!classInfo.isInterface()) {
            return Variant.STANDARD;
        }
        boolean z = false;
        for (ClassInfo.Method method : classInfo.getMethods()) {
            z |= (method.isAccessor() || method.isSynthetic()) ? false : true;
        }
        if (z) {
            return Variant.INTERFACE;
        }
        return Variant.ACCESSOR;
    }
}
