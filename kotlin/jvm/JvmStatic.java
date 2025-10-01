package kotlin.jvm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;
import kotlin.annotation.Retention;

@Target({ElementType.METHOD})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER})
@Retention(AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@MustBeDocumented
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\ufffd\ufffd\b\u0087\u0002\u0018\ufffd\ufffd2\u00020\u0001B\ufffd\ufffd\u00a8\u0006\u0002"}, m27d2 = {"Lkotlin/jvm/JvmStatic;", "", "kotlin-stdlib"})
@Documented
/* loaded from: L-out.jar:kotlin/jvm/JvmStatic.class */
public @interface JvmStatic {
}
