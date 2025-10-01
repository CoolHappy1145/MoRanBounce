package kotlin.jvm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.annotation.AnnotationTarget;

@Target({ElementType.METHOD})
@SinceKotlin(version = "1.2")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\ufffd\ufffd\b\u0087\u0002\u0018\ufffd\ufffd2\u00020\u0001B\ufffd\ufffd\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0007\n\u0005\b\u0091(0\u0001\u00a8\u0006\u0002"}, m27d2 = {"Lkotlin/jvm/JvmDefault;", "", "kotlin-stdlib"})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: L-out.jar:kotlin/jvm/JvmDefault.class */
public @interface JvmDefault {
}
