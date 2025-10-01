package org.spongepowered.asm.mixin.injection;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal;
import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;
import org.spongepowered.asm.mixin.injection.points.AfterInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeFinalReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
import org.spongepowered.asm.mixin.injection.points.BeforeStringInvoke;
import org.spongepowered.asm.mixin.injection.points.JumpInsnPoint;
import org.spongepowered.asm.mixin.injection.points.MethodHead;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/InjectionPoint.class */
public abstract class InjectionPoint {
    public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
    public static final int MAX_ALLOWED_SHIFT_BY = 5;
    private static Map types = new HashMap();
    private final String slice;
    private final Selector selector;

    /* renamed from: id */
    private final String f215id;

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$AtCode.class */
    public @interface AtCode {
        String value();
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$RestrictTargetLevel.class */
    public enum RestrictTargetLevel {
        METHODS_ONLY,
        CONSTRUCTORS_AFTER_DELEGATE,
        ALLOW_ALL
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$Selector.class */
    public enum Selector {
        FIRST,
        LAST,
        ONE;

        public static final Selector DEFAULT = FIRST;
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$ShiftByViolationBehaviour.class */
    enum ShiftByViolationBehaviour {
        IGNORE,
        WARN,
        ERROR
    }

    public abstract boolean find(String str, InsnList insnList, Collection collection);

    static {
        register(BeforeFieldAccess.class);
        register(BeforeInvoke.class);
        register(BeforeNew.class);
        register(BeforeReturn.class);
        register(BeforeStringInvoke.class);
        register(JumpInsnPoint.class);
        register(MethodHead.class);
        register(AfterInvoke.class);
        register(BeforeLoadLocal.class);
        register(AfterStoreLocal.class);
        register(BeforeFinalReturn.class);
        register(BeforeConstant.class);
    }

    protected InjectionPoint() {
        this("", Selector.DEFAULT, null);
    }

    protected InjectionPoint(InjectionPointData injectionPointData) {
        this(injectionPointData.getSlice(), injectionPointData.getSelector(), injectionPointData.getId());
    }

    public InjectionPoint(String str, Selector selector, String str2) {
        this.slice = str;
        this.selector = selector;
        this.f215id = str2;
    }

    public String getSlice() {
        return this.slice;
    }

    public Selector getSelector() {
        return this.selector;
    }

    public String getId() {
        return this.f215id;
    }

    public RestrictTargetLevel getTargetRestriction(IInjectionPointContext iInjectionPointContext) {
        return RestrictTargetLevel.METHODS_ONLY;
    }

    public String toString() {
        return String.format("@At(\"%s\")", getAtCode());
    }

    protected static AbstractInsnNode nextNode(InsnList insnList, AbstractInsnNode abstractInsnNode) {
        int iIndexOf = insnList.indexOf(abstractInsnNode) + 1;
        if (iIndexOf > 0 && iIndexOf < insnList.size()) {
            return insnList.get(iIndexOf);
        }
        return abstractInsnNode;
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$CompositeInjectionPoint.class */
    static abstract class CompositeInjectionPoint extends InjectionPoint {
        protected final InjectionPoint[] components;

        protected CompositeInjectionPoint(InjectionPoint[] injectionPointArr) {
            if (injectionPointArr == null || injectionPointArr.length < 2) {
                throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
            }
            this.components = injectionPointArr;
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public String toString() {
            return "CompositeInjectionPoint(" + getClass().getSimpleName() + ")[" + Joiner.on(',').join(this.components) + "]";
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$Intersection.class */
    static final class Intersection extends CompositeInjectionPoint {
        public Intersection(InjectionPoint[] injectionPointArr) {
            super(injectionPointArr);
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public boolean find(String str, InsnList insnList, Collection collection) {
            boolean z = false;
            ArrayList[] arrayListArr = (ArrayList[]) Array.newInstance((Class<?>) ArrayList.class, this.components.length);
            for (int i = 0; i < this.components.length; i++) {
                arrayListArr[i] = new ArrayList();
                this.components[i].find(str, insnList, arrayListArr[i]);
            }
            ArrayList arrayList = arrayListArr[0];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                AbstractInsnNode abstractInsnNode = (AbstractInsnNode) arrayList.get(i2);
                for (int i3 = 1; i3 < arrayListArr.length && arrayListArr[i3].contains(abstractInsnNode); i3++) {
                }
                collection.add(abstractInsnNode);
                z = true;
            }
            return z;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$Union.class */
    static final class Union extends CompositeInjectionPoint {
        public Union(InjectionPoint[] injectionPointArr) {
            super(injectionPointArr);
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public boolean find(String str, InsnList insnList, Collection collection) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (int i = 0; i < this.components.length; i++) {
                this.components[i].find(str, insnList, linkedHashSet);
            }
            collection.addAll(linkedHashSet);
            return linkedHashSet.size() > 0;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/InjectionPoint$Shift.class */
    static final class Shift extends InjectionPoint {
        private final InjectionPoint input;
        private final int shift;

        public Shift(InjectionPoint injectionPoint, int i) {
            if (injectionPoint == null) {
                throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
            }
            this.input = injectionPoint;
            this.shift = i;
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public String toString() {
            return "InjectionPoint(" + getClass().getSimpleName() + ")[" + this.input + "]";
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public boolean find(String str, InsnList insnList, Collection collection) {
            List arrayList = collection instanceof List ? (List) collection : new ArrayList(collection);
            this.input.find(str, insnList, collection);
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList.set(i, insnList.get(insnList.indexOf((AbstractInsnNode) arrayList.get(i)) + this.shift));
            }
            if (collection != arrayList) {
                collection.clear();
                collection.addAll(arrayList);
            }
            return collection.size() > 0;
        }
    }

    public static InjectionPoint and(InjectionPoint[] injectionPointArr) {
        return new Intersection(injectionPointArr);
    }

    /* renamed from: or */
    public static InjectionPoint m60or(InjectionPoint[] injectionPointArr) {
        return new Union(injectionPointArr);
    }

    public static InjectionPoint after(InjectionPoint injectionPoint) {
        return new Shift(injectionPoint, 1);
    }

    public static InjectionPoint before(InjectionPoint injectionPoint) {
        return new Shift(injectionPoint, -1);
    }

    public static InjectionPoint shift(InjectionPoint injectionPoint, int i) {
        return new Shift(injectionPoint, i);
    }

    public static List parse(IInjectionPointContext iInjectionPointContext, List list) {
        return parse(iInjectionPointContext.getContext(), iInjectionPointContext.getMethod(), iInjectionPointContext.getAnnotation(), list);
    }

    public static List parse(IMixinContext iMixinContext, MethodNode methodNode, AnnotationNode annotationNode, List list) {
        ImmutableList.Builder builder = ImmutableList.builder();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            InjectionPoint injectionPoint = parse(iMixinContext, methodNode, annotationNode, (AnnotationNode) it.next());
            if (injectionPoint != null) {
                builder.add(injectionPoint);
            }
        }
        return builder.build();
    }

    public static InjectionPoint parse(IInjectionPointContext iInjectionPointContext, InterfaceC0563At interfaceC0563At) {
        return parse(iInjectionPointContext.getContext(), iInjectionPointContext.getMethod(), iInjectionPointContext.getAnnotation(), interfaceC0563At.value(), interfaceC0563At.shift(), interfaceC0563At.m57by(), Arrays.asList(interfaceC0563At.args()), interfaceC0563At.target(), interfaceC0563At.slice(), interfaceC0563At.ordinal(), interfaceC0563At.opcode(), interfaceC0563At.m56id());
    }

    public static InjectionPoint parse(IMixinContext iMixinContext, MethodNode methodNode, AnnotationNode annotationNode, InterfaceC0563At interfaceC0563At) {
        return parse(iMixinContext, methodNode, annotationNode, interfaceC0563At.value(), interfaceC0563At.shift(), interfaceC0563At.m57by(), Arrays.asList(interfaceC0563At.args()), interfaceC0563At.target(), interfaceC0563At.slice(), interfaceC0563At.ordinal(), interfaceC0563At.opcode(), interfaceC0563At.m56id());
    }

    public static InjectionPoint parse(IInjectionPointContext iInjectionPointContext, AnnotationNode annotationNode) {
        return parse(iInjectionPointContext.getContext(), iInjectionPointContext.getMethod(), iInjectionPointContext.getAnnotation(), annotationNode);
    }

    public static InjectionPoint parse(IMixinContext iMixinContext, MethodNode methodNode, AnnotationNode annotationNode, AnnotationNode annotationNode2) {
        String str = (String) Annotations.getValue(annotationNode2, PropertyDescriptor.VALUE);
        ImmutableList immutableListOf = (List) Annotations.getValue(annotationNode2, "args");
        String str2 = (String) Annotations.getValue(annotationNode2, "target", "");
        String str3 = (String) Annotations.getValue(annotationNode2, "slice", "");
        InterfaceC0563At.Shift shift = (InterfaceC0563At.Shift) Annotations.getValue(annotationNode2, "shift", InterfaceC0563At.Shift.class, InterfaceC0563At.Shift.NONE);
        int iIntValue = ((Integer) Annotations.getValue(annotationNode2, "by", (Object) 0)).intValue();
        int iIntValue2 = ((Integer) Annotations.getValue(annotationNode2, "ordinal", (Object) (-1))).intValue();
        int iIntValue3 = ((Integer) Annotations.getValue(annotationNode2, "opcode", (Object) 0)).intValue();
        String str4 = (String) Annotations.getValue(annotationNode2, "id");
        if (immutableListOf == null) {
            immutableListOf = ImmutableList.of();
        }
        return parse(iMixinContext, methodNode, annotationNode, str, shift, iIntValue, immutableListOf, str2, str3, iIntValue2, iIntValue3, str4);
    }

    public static InjectionPoint parse(IMixinContext iMixinContext, MethodNode methodNode, AnnotationNode annotationNode, String str, InterfaceC0563At.Shift shift, int i, List list, String str2, String str3, int i2, int i3, String str4) {
        InjectionPointData injectionPointData = new InjectionPointData(iMixinContext, methodNode, annotationNode, str, list, str2, str3, i2, i3, str4);
        return shift(iMixinContext, methodNode, annotationNode, create(iMixinContext, injectionPointData, findClass(iMixinContext, injectionPointData)), shift, i);
    }

    private static Class findClass(IMixinContext iMixinContext, InjectionPointData injectionPointData) throws ClassNotFoundException {
        String type = injectionPointData.getType();
        Class<?> cls = (Class) types.get(type);
        if (cls == null) {
            if (type.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
                try {
                    cls = Class.forName(type);
                    types.put(type, cls);
                } catch (Exception e) {
                    throw new InvalidInjectionException(iMixinContext, injectionPointData + " could not be loaded or is not a valid InjectionPoint", e);
                }
            } else {
                throw new InvalidInjectionException(iMixinContext, injectionPointData + " is not a valid injection point specifier");
            }
        }
        return cls;
    }

    private static InjectionPoint create(IMixinContext iMixinContext, InjectionPointData injectionPointData, Class cls) throws NoSuchMethodException, SecurityException {
        try {
            Constructor declaredConstructor = cls.getDeclaredConstructor(InjectionPointData.class);
            declaredConstructor.setAccessible(true);
            try {
                return (InjectionPoint) declaredConstructor.newInstance(injectionPointData);
            } catch (Exception e) {
                throw new InvalidInjectionException(iMixinContext, "Error whilst instancing injection point " + cls.getName() + " for " + injectionPointData.getAt(), e);
            }
        } catch (NoSuchMethodException e2) {
            throw new InvalidInjectionException(iMixinContext, cls.getName() + " must contain a constructor which accepts an InjectionPointData", e2);
        }
    }

    private static InjectionPoint shift(IMixinContext iMixinContext, MethodNode methodNode, AnnotationNode annotationNode, InjectionPoint injectionPoint, InterfaceC0563At.Shift shift, int i) {
        if (injectionPoint != null) {
            if (shift == InterfaceC0563At.Shift.BEFORE) {
                return before(injectionPoint);
            }
            if (shift == InterfaceC0563At.Shift.AFTER) {
                return after(injectionPoint);
            }
            if (shift == InterfaceC0563At.Shift.BY) {
                validateByValue(iMixinContext, methodNode, annotationNode, injectionPoint, i);
                return shift(injectionPoint, i);
            }
        }
        return injectionPoint;
    }

    private static void validateByValue(IMixinContext iMixinContext, MethodNode methodNode, AnnotationNode annotationNode, InjectionPoint injectionPoint, int i) {
        ShiftByViolationBehaviour shiftByViolationBehaviour = (ShiftByViolationBehaviour) iMixinContext.getMixin().getConfig().getEnvironment().getOption(MixinEnvironment.Option.SHIFT_BY_VIOLATION_BEHAVIOUR, ShiftByViolationBehaviour.WARN);
        if (shiftByViolationBehaviour == ShiftByViolationBehaviour.IGNORE) {
            return;
        }
        Object obj = "the maximum allowed value: ";
        Object obj2 = "Increase the value of maxShiftBy to suppress this warning.";
        int maxShiftByValue = 0;
        if (iMixinContext instanceof MixinTargetContext) {
            maxShiftByValue = ((MixinTargetContext) iMixinContext).getMaxShiftByValue();
        }
        if (i <= maxShiftByValue) {
            return;
        }
        if (i > 5) {
            obj = "MAX_ALLOWED_SHIFT_BY=";
            obj2 = "You must use an alternate query or a custom injection point.";
            maxShiftByValue = 5;
        }
        String str = String.format("@%s(%s) Shift.BY=%d on %s::%s exceeds %s%d. %s", Bytecode.getSimpleName(annotationNode), injectionPoint, Integer.valueOf(i), iMixinContext, methodNode.name, obj, Integer.valueOf(maxShiftByValue), obj2);
        if (shiftByViolationBehaviour == ShiftByViolationBehaviour.WARN && maxShiftByValue < 5) {
            LogManager.getLogger(MixinLaunchPlugin.NAME).warn(str);
            return;
        }
        throw new InvalidInjectionException(iMixinContext, str);
    }

    protected String getAtCode() {
        AtCode atCode = (AtCode) getClass().getAnnotation(AtCode.class);
        return atCode == null ? getClass().getName() : atCode.value();
    }

    public static void register(Class cls) {
        AtCode atCode = (AtCode) cls.getAnnotation(AtCode.class);
        if (atCode == null) {
            throw new IllegalArgumentException("Injection point class " + cls + " is not annotated with @AtCode");
        }
        Class cls2 = (Class) types.get(atCode.value());
        if (cls2 != null && !cls2.equals(cls)) {
            LogManager.getLogger(MixinLaunchPlugin.NAME).debug("Overriding InjectionPoint {} with {} (previously {})", new Object[]{atCode.value(), cls.getName(), cls2.getName()});
        }
        types.put(atCode.value(), cls);
    }
}
