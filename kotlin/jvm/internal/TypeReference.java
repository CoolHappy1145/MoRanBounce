package kotlin.jvm.internal;

import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;
import org.apache.log4j.spi.LocationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Marker;
import org.spongepowered.asm.util.Constants;

@SinceKotlin(version = "1.4")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdD\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\u0017\u001a\u00020\u0013H\u0002J\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0016J\f\u0010\u0017\u001a\u00020\u0013*\u00020\u0006H\u0002R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\u0011R\u001c\u0010\u0012\u001a\u00020\u0013*\u0006\u0012\u0002\b\u00030\u00148BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001e"}, m27d2 = {"Lkotlin/jvm/internal/TypeReference;", "Lkotlin/reflect/KType;", "classifier", "Lkotlin/reflect/KClassifier;", "arguments", "", "Lkotlin/reflect/KTypeProjection;", "isMarkedNullable", "", "(Lkotlin/reflect/KClassifier;Ljava/util/List;Z)V", "annotations", "", "getAnnotations", "()Ljava/util/List;", "getArguments", "getClassifier", "()Lkotlin/reflect/KClassifier;", "()Z", "arrayClassName", "", Constants.CLASS_DESC, "getArrayClassName", "(Ljava/lang/Class;)Ljava/lang/String;", "asString", "equals", "other", "", "hashCode", "", "toString", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/jvm/internal/TypeReference.class */
public final class TypeReference implements KType {

    @NotNull
    private final KClassifier classifier;

    @NotNull
    private final List arguments;
    private final boolean isMarkedNullable;

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:kotlin/jvm/internal/TypeReference$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[KVariance.values().length];

        static {
            $EnumSwitchMapping$0[KVariance.INVARIANT.ordinal()] = 1;
            $EnumSwitchMapping$0[KVariance.IN.ordinal()] = 2;
            $EnumSwitchMapping$0[KVariance.OUT.ordinal()] = 3;
        }
    }

    public TypeReference(@NotNull KClassifier classifier, @NotNull List arguments, boolean z) {
        Intrinsics.checkParameterIsNotNull(classifier, "classifier");
        Intrinsics.checkParameterIsNotNull(arguments, "arguments");
        this.classifier = classifier;
        this.arguments = arguments;
        this.isMarkedNullable = z;
    }

    @Override // kotlin.reflect.KType
    @NotNull
    public KClassifier getClassifier() {
        return this.classifier;
    }

    @Override // kotlin.reflect.KType
    @NotNull
    public List getArguments() {
        return this.arguments;
    }

    @Override // kotlin.reflect.KType
    public boolean isMarkedNullable() {
        return this.isMarkedNullable;
    }

    @Override // kotlin.reflect.KAnnotatedElement
    @NotNull
    public List getAnnotations() {
        return CollectionsKt.emptyList();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof TypeReference) && Intrinsics.areEqual(getClassifier(), ((TypeReference) obj).getClassifier()) && Intrinsics.areEqual(getArguments(), ((TypeReference) obj).getArguments()) && isMarkedNullable() == ((TypeReference) obj).isMarkedNullable();
    }

    public int hashCode() {
        return (((getClassifier().hashCode() * 31) + getArguments().hashCode()) * 31) + Boolean.valueOf(isMarkedNullable()).hashCode();
    }

    @NotNull
    public String toString() {
        return asString() + " (Kotlin reflection is not available)";
    }

    private final String asString() {
        String arrayClassName;
        KClassifier classifier = getClassifier();
        if (!(classifier instanceof KClass)) {
            classifier = null;
        }
        KClass kClass = (KClass) classifier;
        Class javaClass = kClass != null ? JvmClassMappingKt.getJavaClass(kClass) : null;
        if (javaClass == null) {
            arrayClassName = getClassifier().toString();
        } else {
            arrayClassName = javaClass.isArray() ? getArrayClassName(javaClass) : javaClass.getName();
        }
        return arrayClassName + (getArguments().isEmpty() ? "" : CollectionsKt.joinToString$default(getArguments(), ", ", "<", ">", 0, null, new Function1(this) { // from class: kotlin.jvm.internal.TypeReference$asString$args$1
            final TypeReference this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((KTypeProjection) obj);
            }

            @NotNull
            public final String invoke(@NotNull KTypeProjection it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return this.this$0.asString(it);
            }
        }, 24, null)) + (isMarkedNullable() ? LocationInfo.f204NA : "");
    }

    private final String getArrayClassName(@NotNull Class cls) {
        return Intrinsics.areEqual(cls, boolean[].class) ? "kotlin.BooleanArray" : Intrinsics.areEqual(cls, char[].class) ? "kotlin.CharArray" : Intrinsics.areEqual(cls, byte[].class) ? "kotlin.ByteArray" : Intrinsics.areEqual(cls, short[].class) ? "kotlin.ShortArray" : Intrinsics.areEqual(cls, int[].class) ? "kotlin.IntArray" : Intrinsics.areEqual(cls, float[].class) ? "kotlin.FloatArray" : Intrinsics.areEqual(cls, long[].class) ? "kotlin.LongArray" : Intrinsics.areEqual(cls, double[].class) ? "kotlin.DoubleArray" : "kotlin.Array";
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final String asString(@NotNull KTypeProjection kTypeProjection) {
        String strValueOf;
        if (kTypeProjection.getVariance() == null) {
            return Marker.ANY_MARKER;
        }
        KType type = kTypeProjection.getType();
        if (!(type instanceof TypeReference)) {
            type = null;
        }
        TypeReference typeReference = (TypeReference) type;
        if (typeReference != null) {
            strValueOf = typeReference.asString();
            if (strValueOf == null) {
                strValueOf = String.valueOf(kTypeProjection.getType());
            }
        }
        String str = strValueOf;
        KVariance variance = kTypeProjection.getVariance();
        if (variance != null) {
            switch (WhenMappings.$EnumSwitchMapping$0[variance.ordinal()]) {
                case 1:
                    return str;
                case 2:
                    return "in " + str;
                case 3:
                    return "out " + str;
            }
        }
        throw new NoWhenBranchMatchedException();
    }
}
