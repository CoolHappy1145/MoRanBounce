package kotlin.jvm;

import java.lang.annotation.Annotation;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd,\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001f\u0010\u0018\u001a\u00020\u0019\"\n\b\ufffd\ufffd\u0010\u0002\u0018\u0001*\u00020\r*\u0006\u0012\u0002\b\u00030\u001a\u00a2\u0006\u0002\u0010\u001b\"'\u0010\ufffd\ufffd\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u0003*\u0002H\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"-\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\ufffd\ufffd\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018G\u00a2\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"&\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\r*\u0002H\u00028\u00c6\u0002\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000e\";\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018\u00c7\u0002X\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u000f\u0010\t\u001a\u0004\b\u0010\u0010\u000b\"+\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b\"-\u0010\u0013\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u000b\"+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00078G\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001c"}, m27d2 = {"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "java", Constants.CLASS_DESC, "java$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "javaClass$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"})
@JvmName(name = "JvmClassMappingKt")
/* loaded from: L-out.jar:kotlin/jvm/JvmClassMappingKt.class */
public final class JvmClassMappingKt {
    @JvmName(name = "getJavaClass")
    @NotNull
    public static final Class getJavaClass(@NotNull KClass java) {
        Intrinsics.checkParameterIsNotNull(java, "$this$java");
        Class jClass = ((ClassBasedDeclarationContainer) java).getJClass();
        if (jClass == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
        }
        return jClass;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Nullable
    public static final Class getJavaPrimitiveType(@NotNull KClass javaPrimitiveType) {
        Intrinsics.checkParameterIsNotNull(javaPrimitiveType, "$this$javaPrimitiveType");
        Class jClass = ((ClassBasedDeclarationContainer) javaPrimitiveType).getJClass();
        if (jClass.isPrimitive()) {
            if (jClass == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
            }
            return jClass;
        }
        String name = jClass.getName();
        if (name != null) {
            switch (name.hashCode()) {
                case -2056817302:
                    if (name.equals("java.lang.Integer")) {
                        return Integer.TYPE;
                    }
                    break;
                case -527879800:
                    if (name.equals("java.lang.Float")) {
                        return Float.TYPE;
                    }
                    break;
                case -515992664:
                    if (name.equals("java.lang.Short")) {
                        return Short.TYPE;
                    }
                    break;
                case 155276373:
                    if (name.equals("java.lang.Character")) {
                        return Character.TYPE;
                    }
                    break;
                case 344809556:
                    if (name.equals("java.lang.Boolean")) {
                        return Boolean.TYPE;
                    }
                    break;
                case 398507100:
                    if (name.equals("java.lang.Byte")) {
                        return Byte.TYPE;
                    }
                    break;
                case 398795216:
                    if (name.equals("java.lang.Long")) {
                        return Long.TYPE;
                    }
                    break;
                case 399092968:
                    if (name.equals("java.lang.Void")) {
                        return Void.TYPE;
                    }
                    break;
                case 761287205:
                    if (name.equals("java.lang.Double")) {
                        return Double.TYPE;
                    }
                    break;
            }
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0125  */
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Class getJavaObjectType(@NotNull KClass javaObjectType) {
        Class cls;
        Intrinsics.checkParameterIsNotNull(javaObjectType, "$this$javaObjectType");
        Class jClass = ((ClassBasedDeclarationContainer) javaObjectType).getJClass();
        if (!jClass.isPrimitive()) {
            if (jClass == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
            }
            return jClass;
        }
        String name = jClass.getName();
        if (name != null) {
            switch (name.hashCode()) {
                case -1325958191:
                    if (name.equals("double")) {
                        cls = Double.class;
                        break;
                    }
                    break;
                case 104431:
                    if (name.equals("int")) {
                        cls = Integer.class;
                        break;
                    }
                    break;
                case 3039496:
                    if (name.equals("byte")) {
                        cls = Byte.class;
                        break;
                    }
                    break;
                case 3052374:
                    if (name.equals("char")) {
                        cls = Character.class;
                        break;
                    }
                    break;
                case 3327612:
                    if (name.equals("long")) {
                        cls = Long.class;
                        break;
                    }
                    break;
                case 3625364:
                    if (name.equals("void")) {
                        cls = Void.class;
                        break;
                    }
                    break;
                case 64711720:
                    if (name.equals("boolean")) {
                        cls = Boolean.class;
                        break;
                    }
                    break;
                case 97526364:
                    if (name.equals("float")) {
                        cls = Float.class;
                        break;
                    }
                    break;
                case 109413500:
                    if (name.equals("short")) {
                        cls = Short.class;
                        break;
                    }
                    break;
            }
        } else {
            cls = jClass;
        }
        if (cls == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
        }
        return cls;
    }

    @JvmName(name = "getKotlinClass")
    @NotNull
    public static final KClass getKotlinClass(@NotNull Class kotlin2) {
        Intrinsics.checkParameterIsNotNull(kotlin2, "$this$kotlin");
        return Reflection.getOrCreateKotlinClass(kotlin2);
    }

    @NotNull
    public static final Class getJavaClass(@NotNull Object javaClass) {
        Intrinsics.checkParameterIsNotNull(javaClass, "$this$javaClass");
        Class<?> cls = javaClass.getClass();
        if (cls == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
        }
        return cls;
    }

    @JvmName(name = "getRuntimeClassOfKClassInstance")
    @NotNull
    public static final Class getRuntimeClassOfKClassInstance(@NotNull KClass javaClass) {
        Intrinsics.checkParameterIsNotNull(javaClass, "$this$javaClass");
        Class<?> cls = javaClass.getClass();
        if (cls == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<kotlin.reflect.KClass<T>>");
        }
        return cls;
    }

    public static final boolean isArrayOf(@NotNull Object[] isArrayOf) {
        Intrinsics.checkParameterIsNotNull(isArrayOf, "$this$isArrayOf");
        Intrinsics.reifiedOperationMarker(4, "T");
        return Object.class.isAssignableFrom(isArrayOf.getClass().getComponentType());
    }

    @NotNull
    public static final KClass getAnnotationClass(@NotNull Annotation annotationClass) {
        Intrinsics.checkParameterIsNotNull(annotationClass, "$this$annotationClass");
        Class<? extends Annotation> clsAnnotationType = annotationClass.annotationType();
        Intrinsics.checkExpressionValueIsNotNull(clsAnnotationType, "(this as java.lang.annot\u2026otation).annotationType()");
        KClass kotlinClass = getKotlinClass(clsAnnotationType);
        if (kotlinClass == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.reflect.KClass<out T>");
        }
        return kotlinClass;
    }
}
