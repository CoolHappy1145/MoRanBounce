package kotlin.internal;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;

@Target({})
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\ufffd\ufffd\b\u0081\u0002\u0018\ufffd\ufffd2\u00020\u0001B\ufffd\ufffd\u00a8\u0006\u0002"}, m27d2 = {"Lkotlin/internal/OnlyInputTypes;", "", "kotlin-stdlib"})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.TYPE_PARAMETER})
@Retention(AnnotationRetention.BINARY)
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
/* loaded from: L-out.jar:kotlin/internal/OnlyInputTypes.class */
public @interface OnlyInputTypes {
}
