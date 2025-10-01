package kotlin.internal;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u001e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\"\u0010\b\u001a\u0002H\t\"\n\b\ufffd\ufffd\u0010\t\u0018\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0083\b\u00a2\u0006\u0002\u0010\f\u001a\b\u0010\r\u001a\u00020\u0005H\u0002\"\u0010\u0010\ufffd\ufffd\u001a\u00020\u00018\ufffd\ufffdX\u0081\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000e"}, m27d2 = {"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/internal/PlatformImplementationsKt.class */
public final class PlatformImplementationsKt {

    @JvmField
    @NotNull
    public static final PlatformImplementations IMPLEMENTATIONS;

    static {
        PlatformImplementations platformImplementations;
        int javaVersion = getJavaVersion();
        if (javaVersion >= 65544) {
            Object objNewInstance = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
            Intrinsics.checkExpressionValueIsNotNull(objNewInstance, "Class.forName(\"kotlin.in\u2026entations\").newInstance()");
            if (objNewInstance == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
            }
            platformImplementations = (PlatformImplementations) objNewInstance;
        } else if (javaVersion >= 65543) {
            Object objNewInstance2 = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
            Intrinsics.checkExpressionValueIsNotNull(objNewInstance2, "Class.forName(\"kotlin.in\u2026entations\").newInstance()");
            if (objNewInstance2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
            }
            platformImplementations = (PlatformImplementations) objNewInstance2;
        } else {
            platformImplementations = new PlatformImplementations();
        }
        IMPLEMENTATIONS = platformImplementations;
    }

    @InlineOnly
    private static final Object castToBaseType(Object obj) {
        Intrinsics.reifiedOperationMarker(1, "T");
        return obj;
    }

    private static final int getJavaVersion() {
        int i;
        int i2;
        String property = System.getProperty("java.specification.version");
        if (property == null) {
            return 65542;
        }
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) property, '.', 0, false, 6, (Object) null);
        if (iIndexOf$default < 0) {
            try {
                i = Integer.parseInt(property) * 65536;
            } catch (NumberFormatException unused) {
                i = 65542;
            }
            return i;
        }
        int iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) property, '.', iIndexOf$default + 1, false, 4, (Object) null);
        if (iIndexOf$default2 < 0) {
            iIndexOf$default2 = property.length();
        }
        if (property == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String strSubstring = property.substring(0, iIndexOf$default);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        int i3 = iIndexOf$default + 1;
        if (property == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String strSubstring2 = property.substring(i3, iIndexOf$default2);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        try {
            i2 = (Integer.parseInt(strSubstring) * 65536) + Integer.parseInt(strSubstring2);
        } catch (NumberFormatException unused2) {
            i2 = 65542;
        }
        return i2;
    }

    @SinceKotlin(version = "1.2")
    @PublishedApi
    public static final boolean apiVersionIsAtLeast(int i, int i2, int i3) {
        return KotlinVersion.CURRENT.isAtLeast(i, i2, i3);
    }
}
