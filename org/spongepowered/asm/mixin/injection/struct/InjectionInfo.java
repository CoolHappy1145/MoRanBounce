package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.tools.Diagnostic;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.ISliceContext;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.code.InjectorTarget;
import org.spongepowered.asm.mixin.injection.code.MethodSlice;
import org.spongepowered.asm.mixin.injection.code.MethodSlices;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.InvalidSelectorException;
import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.throwables.MixinError;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.asm.ASM;
import org.spongepowered.asm.util.asm.ElementNode;
import org.spongepowered.asm.util.asm.MethodNodeEx;
import org.spongepowered.asm.util.logging.MessageRouter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/InjectionInfo.class */
public abstract class InjectionInfo extends SpecialMethodInfo implements ISliceContext {
    public static final String DEFAULT_PREFIX = "handler";
    private static Map registry = new LinkedHashMap();
    private static Class[] registeredAnnotations = new Class[0];
    protected final boolean isStatic;
    protected final Deque targets;
    protected final MethodSlices slices;
    protected final String atKey;
    protected final List injectionPoints;
    protected final Map targetNodes;
    protected int targetCount;
    protected Injector injector;
    protected InjectorGroupInfo group;
    private final List injectedMethods;
    private int expectedCallbackCount;
    private int requiredCallbackCount;
    private int maxCallbackCount;
    private int injectedCallbackCount;

    @java.lang.annotation.Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/InjectionInfo$AnnotationType.class */
    public @interface AnnotationType {
        Class value();
    }

    @java.lang.annotation.Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/InjectionInfo$HandlerPrefix.class */
    public @interface HandlerPrefix {
        String value();
    }

    protected abstract Injector parseInjector(AnnotationNode annotationNode);

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/InjectionInfo$InjectorEntry.class */
    static class InjectorEntry {
        final Class annotationType;
        final Class type;
        final Constructor ctor;
        final String simpleName;
        final String prefix;

        InjectorEntry(Class cls, Class cls2) {
            this.annotationType = cls;
            this.type = cls2;
            this.ctor = cls2.getDeclaredConstructor(MixinTargetContext.class, MethodNode.class, AnnotationNode.class);
            this.simpleName = cls.getSimpleName() + ";";
            HandlerPrefix handlerPrefix = (HandlerPrefix) cls2.getAnnotation(HandlerPrefix.class);
            this.prefix = handlerPrefix != null ? handlerPrefix.value() : InjectionInfo.DEFAULT_PREFIX;
        }

        InjectionInfo create(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
            try {
                return (InjectionInfo) this.ctor.newInstance(mixinTargetContext, methodNode, annotationNode);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof MixinException) {
                    throw ((MixinException) cause);
                }
                throw new MixinError("Error initialising injector metaclass [" + this.type + "] for annotation " + annotationNode.desc, cause != null ? cause : e);
            } catch (ReflectiveOperationException e2) {
                throw new MixinError("Failed to instantiate injector metaclass [" + this.type + "] for annotation " + annotationNode.desc, e2);
            }
        }
    }

    static {
        register(CallbackInjectionInfo.class);
        register(ModifyArgInjectionInfo.class);
        register(ModifyArgsInjectionInfo.class);
        register(RedirectInjectionInfo.class);
        register(ModifyVariableInjectionInfo.class);
        register(ModifyConstantInjectionInfo.class);
    }

    protected InjectionInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        this(mixinTargetContext, methodNode, annotationNode, "at");
    }

    protected InjectionInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode, String str) {
        super(mixinTargetContext, methodNode, annotationNode);
        this.targets = new ArrayDeque();
        this.injectionPoints = new ArrayList();
        this.targetNodes = new LinkedHashMap();
        this.targetCount = 0;
        this.injectedMethods = new ArrayList(0);
        this.expectedCallbackCount = 1;
        this.requiredCallbackCount = 0;
        this.maxCallbackCount = Integer.MAX_VALUE;
        this.injectedCallbackCount = 0;
        this.isStatic = Bytecode.isStatic(methodNode);
        this.slices = MethodSlices.parse(this);
        this.atKey = str;
        readAnnotation();
    }

    protected void readAnnotation() {
        if (this.annotation == null) {
            return;
        }
        List injectionPoints = readInjectionPoints();
        parseRequirements();
        findMethods(parseTargets());
        parseInjectionPoints(injectionPoints);
        this.injector = parseInjector(this.annotation);
    }

    protected Set parseTargets() {
        List<String> value = Annotations.getValue(this.annotation, "method", false);
        if (value == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing method name", this.annotationType, this.methodName));
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (String str : value) {
            try {
                linkedHashSet.add(TargetSelector.parseAndValidate(str, this.mixin).attach(this.mixin));
            } catch (InvalidMemberDescriptorException unused) {
                throw new InvalidInjectionException(this, String.format("%s annotation on %s, has invalid target descriptor: \"%s\". %s", this.annotationType, this.methodName, str, this.mixin.getReferenceMapper().getStatus()));
            } catch (TargetNotSupportedException e) {
                throw new InvalidInjectionException(this, String.format("%s annotation on %s specifies a target class '%s', which is not supported", this.annotationType, this.methodName, e.getMessage()));
            } catch (InvalidSelectorException e2) {
                throw new InvalidInjectionException(this, String.format("%s annotation on %s is decorated with an invalid selector: %s", this.annotationType, this.methodName, e2.getMessage()));
            }
        }
        return linkedHashSet;
    }

    protected List readInjectionPoints() {
        List value = Annotations.getValue(this.annotation, this.atKey, false);
        if (value == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing '%s' value(s)", this.annotationType, this.methodName, this.atKey));
        }
        return value;
    }

    protected void parseInjectionPoints(List list) {
        this.injectionPoints.addAll(InjectionPoint.parse(this.mixin, this.method, this.annotation, list));
    }

    protected void parseRequirements() {
        this.group = this.mixin.getInjectorGroups().parseGroup(this.method, this.mixin.getDefaultInjectorGroup()).add(this);
        Integer num = (Integer) Annotations.getValue(this.annotation, "expect");
        if (num != null) {
            this.expectedCallbackCount = num.intValue();
        }
        Integer num2 = (Integer) Annotations.getValue(this.annotation, "require");
        if (num2 != null && num2.intValue() > -1) {
            this.requiredCallbackCount = num2.intValue();
        } else if (this.group.isDefault()) {
            this.requiredCallbackCount = this.mixin.getDefaultRequiredInjections();
        }
        Integer num3 = (Integer) Annotations.getValue(this.annotation, "allow");
        if (num3 != null) {
            this.maxCallbackCount = Math.max(Math.max(this.requiredCallbackCount, 1), num3.intValue());
        }
    }

    public boolean isValid() {
        return this.targets.size() > 0 && this.injectionPoints.size() > 0;
    }

    public void prepare() {
        this.targetNodes.clear();
        Iterator it = this.targets.iterator();
        while (it.hasNext()) {
            Target targetMethod = this.mixin.getTargetMethod((MethodNode) it.next());
            InjectorTarget injectorTarget = new InjectorTarget(this, targetMethod);
            this.targetNodes.put(targetMethod, this.injector.find(injectorTarget, this.injectionPoints));
            injectorTarget.dispose();
        }
    }

    public void inject() {
        for (Map.Entry entry : this.targetNodes.entrySet()) {
            this.injector.inject((Target) entry.getKey(), (List<InjectionNodes.InjectionNode>) entry.getValue());
        }
        this.targets.clear();
    }

    public void postInject() {
        Iterator it = this.injectedMethods.iterator();
        while (it.hasNext()) {
            this.classNode.methods.add((MethodNode) it.next());
        }
        String status = this.mixin.getReferenceMapper().getStatus();
        String dynamicInfo = getDynamicInfo();
        if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.injectedCallbackCount < this.expectedCallbackCount) {
            throw new InvalidInjectionException(this, String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded. Scanned %d target(s). %s%s", "Callback method", this.methodName, this.method.desc, this.mixin, Integer.valueOf(this.expectedCallbackCount), Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.targetCount), status, dynamicInfo));
        }
        if (this.injectedCallbackCount < this.requiredCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded. Scanned %d target(s). %s%s", "Callback method", this.methodName, this.method.desc, this.mixin, Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.requiredCallbackCount), Integer.valueOf(this.targetCount), status, dynamicInfo));
        }
        if (this.injectedCallbackCount > this.maxCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, %d succeeded of %d allowed.%s", "Callback method", this.methodName, this.method.desc, this.mixin, Integer.valueOf(this.injectedCallbackCount), Integer.valueOf(this.maxCallbackCount), dynamicInfo));
        }
    }

    public String toString() {
        return describeInjector(this.mixin, this.annotation, this.method);
    }

    public Collection getTargets() {
        return this.targets;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.spongepowered.asm.mixin.injection.code.ISliceContext
    public MethodSlice getSlice(String str) {
        MethodSlices methodSlices = this.slices;
        return get("");
    }

    public int getInjectedCallbackCount() {
        return this.injectedCallbackCount;
    }

    public MethodNode addMethod(int i, String str, String str2) {
        MethodNode methodNode = new MethodNode(ASM.API_VERSION, i | 4096, str, str2, (String) null, (String[]) null);
        this.injectedMethods.add(methodNode);
        return methodNode;
    }

    public void addCallbackInvocation(MethodNode methodNode) {
        this.injectedCallbackCount++;
    }

    private void findMethods(Set set) {
        this.targets.clear();
        int i = this.mixin.getEnvironment().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? 2 : 1;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ITargetSelector iTargetSelectorConfigure = (ITargetSelector) it.next();
            int matchCount = iTargetSelectorConfigure.getMatchCount();
            int i2 = 0;
            for (int i3 = 0; i3 < i && i2 < 1; i3++) {
                for (MethodNode methodNode : this.classNode.methods) {
                    if (iTargetSelectorConfigure.match(ElementNode.m78of(this.classNode, methodNode)).isExactMatch()) {
                        boolean z = Annotations.getVisible(methodNode, MixinMerged.class) != null;
                        if (matchCount <= 1 || (Bytecode.isStatic(methodNode) == this.isStatic && methodNode != this.method && !z)) {
                            checkTarget(methodNode);
                            this.targets.add(methodNode);
                            i2++;
                            if (i2 >= matchCount) {
                                break;
                            }
                        }
                    }
                }
                iTargetSelectorConfigure = iTargetSelectorConfigure.configure(new String[]{"permissive"});
            }
        }
        this.targetCount = this.targets.size();
        if (this.targetCount > 0) {
            return;
        }
        if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.expectedCallbackCount > 0) {
            throw new InvalidInjectionException(this, String.format("Injection validation failed: %s annotation on %s could not find any targets matching %s in %s. %s%s", this.annotationType, this.methodName, namesOf(set), this.mixin.getTarget(), this.mixin.getReferenceMapper().getStatus(), getDynamicInfo()));
        }
        if (this.requiredCallbackCount > 0) {
            throw new InvalidInjectionException(this, String.format("Critical injection failure: %s annotation on %s could not find any targets matching %s in %s. %s%s", this.annotationType, this.methodName, namesOf(set), this.mixin.getTarget(), this.mixin.getReferenceMapper().getStatus(), getDynamicInfo()));
        }
    }

    private void checkTarget(MethodNode methodNode) {
        AnnotationNode visible = Annotations.getVisible(methodNode, MixinMerged.class);
        if (visible != null && Annotations.getVisible(methodNode, Final.class) != null) {
            throw new InvalidInjectionException(this, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", this, this.classNode.name, methodNode.name, methodNode.desc, Annotations.getValue(visible, MixinLaunchPlugin.NAME)));
        }
    }

    protected String getDynamicInfo() {
        AnnotationNode invisible = Annotations.getInvisible(this.method, Dynamic.class);
        String strNullToEmpty = Strings.nullToEmpty((String) Annotations.getValue(invisible));
        Type type = (Type) Annotations.getValue(invisible, MixinLaunchPlugin.NAME);
        if (type != null) {
            strNullToEmpty = String.format("{%s} %s", type.getClassName(), strNullToEmpty).trim();
        }
        return strNullToEmpty.length() > 0 ? String.format(" Method is @Dynamic(%s)", strNullToEmpty) : "";
    }

    public static InjectionInfo parse(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        AnnotationNode injectorAnnotation = getInjectorAnnotation(mixinTargetContext.getMixin(), methodNode);
        if (injectorAnnotation == null) {
            return null;
        }
        for (InjectorEntry injectorEntry : registry.values()) {
            if (injectorAnnotation.desc.endsWith(injectorEntry.simpleName)) {
                return injectorEntry.create(mixinTargetContext, methodNode, injectorAnnotation);
            }
        }
        return null;
    }

    public static AnnotationNode getInjectorAnnotation(IMixinInfo iMixinInfo, MethodNode methodNode) {
        try {
            return Annotations.getSingleVisible(methodNode, registeredAnnotations);
        } catch (IllegalArgumentException e) {
            throw new InvalidMixinException(iMixinInfo, String.format("Error parsing annotations on %s in %s: %s", methodNode.name, iMixinInfo.getClassName(), e.getMessage()));
        }
    }

    public static String getInjectorPrefix(AnnotationNode annotationNode) {
        if (annotationNode == null) {
            return DEFAULT_PREFIX;
        }
        for (InjectorEntry injectorEntry : registry.values()) {
            if (annotationNode.desc.endsWith(injectorEntry.simpleName)) {
                return injectorEntry.prefix;
            }
        }
        return DEFAULT_PREFIX;
    }

    static String describeInjector(IMixinContext iMixinContext, AnnotationNode annotationNode, MethodNode methodNode) {
        return String.format("%s->@%s::%s%s", iMixinContext.toString(), Bytecode.getSimpleName(annotationNode), MethodNodeEx.getName(methodNode), methodNode.desc);
    }

    private static String namesOf(Collection collection) {
        int i = 0;
        int size = collection.size();
        StringBuilder sb = new StringBuilder();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            ITargetSelector iTargetSelector = (ITargetSelector) it.next();
            if (i > 0) {
                if (i == size - 1) {
                    sb.append(" or ");
                } else {
                    sb.append(", ");
                }
            }
            sb.append('\'').append(iTargetSelector.toString()).append('\'');
            i++;
        }
        return sb.toString();
    }

    public static void register(Class cls) {
        AnnotationType annotationType = (AnnotationType) cls.getAnnotation(AnnotationType.class);
        if (annotationType == null) {
            throw new IllegalArgumentException("Injection info class " + cls + " is not annotated with @AnnotationType");
        }
        try {
            InjectorEntry injectorEntry = new InjectorEntry(annotationType.value(), cls);
            InjectorEntry injectorEntry2 = (InjectorEntry) registry.get(injectorEntry.simpleName);
            if (injectorEntry2 != null) {
                MessageRouter.getMessager().printMessage(Diagnostic.Kind.WARNING, String.format("Overriding InjectionInfo for @%s with %s (previously %s)", annotationType.value().getSimpleName(), cls.getName(), injectorEntry2.type.getName()));
            } else {
                MessageRouter.getMessager().printMessage(Diagnostic.Kind.OTHER, String.format("Registering new injector for @%s with %s", annotationType.value().getSimpleName(), cls.getName()));
            }
            registry.put(injectorEntry.simpleName, injectorEntry);
            ArrayList arrayList = new ArrayList();
            Iterator it = registry.values().iterator();
            while (it.hasNext()) {
                arrayList.add(((InjectorEntry) it.next()).annotationType);
            }
            registeredAnnotations = (Class[]) arrayList.toArray(registeredAnnotations);
        } catch (NoSuchMethodException unused) {
            throw new MixinError("InjectionInfo class " + cls.getName() + " is missing a valid constructor");
        }
    }

    public static Set getRegisteredAnnotations() {
        return ImmutableSet.copyOf(registeredAnnotations);
    }
}
