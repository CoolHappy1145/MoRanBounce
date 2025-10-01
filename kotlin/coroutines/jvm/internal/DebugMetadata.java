package kotlin.coroutines.jvm.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.annotation.AnnotationTarget;
import kotlin.jvm.JvmName;

@Target({ElementType.TYPE})
@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0015\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0011\b\u0081\u0002\u0018\ufffd\ufffd2\u00020\u0001B\\\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005R\u0011\u0010\r\u001a\u00020\u00058\u0007\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u000b\u001a\u00020\u00078\u0007\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u00078\u0007\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0011R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t8\u0007\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\f\u001a\u00020\u00058\u0007\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u00058\u0007\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u000fR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t8\u0007\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u00038\u0007\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001a"}, m27d2 = {"Lkotlin/coroutines/jvm/internal/DebugMetadata;", "", "version", "", "sourceFile", "", "lineNumbers", "", "localNames", "", "spilled", "indexToLabel", "methodName", "className", "c", "()Ljava/lang/String;", "i", "()[I", "l", "n", "()[Ljava/lang/String;", "m", "f", "s", "v", "()I", "kotlin-stdlib"})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.CLASS})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: L-out.jar:kotlin/coroutines/jvm/internal/DebugMetadata.class */
public @interface DebugMetadata {
    @JvmName(name = "v")
    /* renamed from: v */
    int m33v() default 1;

    @JvmName(name = "f")
    /* renamed from: f */
    String m34f() default "";

    @JvmName(name = "l")
    /* renamed from: l */
    int[] m35l() default {};

    @JvmName(name = "n")
    /* renamed from: n */
    String[] m36n() default {};

    @JvmName(name = "s")
    /* renamed from: s */
    String[] m37s() default {};

    @JvmName(name = "i")
    /* renamed from: i */
    int[] m38i() default {};

    @JvmName(name = "m")
    /* renamed from: m */
    String m39m() default "";

    @JvmName(name = "c")
    /* renamed from: c */
    String m40c() default "";
}
