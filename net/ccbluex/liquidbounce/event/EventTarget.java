package net.ccbluex.liquidbounce.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import net.ccbluex.liquidbounce.LiquidBounce;

@Target({ElementType.METHOD})
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0087\u0002\u0018\ufffd\ufffd2\u00020\u0001B\n\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004\u00a8\u0006\u0005"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/EventTarget;", "", "ignoreCondition", "", "()Z", LiquidBounce.CLIENT_NAME})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER})
@Retention(AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/EventTarget.class */
public @interface EventTarget {
    boolean ignoreCondition() default false;
}
