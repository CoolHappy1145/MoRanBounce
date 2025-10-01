package org.spongepowered.asm.mixin.gen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/Invoker.class */
public @interface Invoker {
    String value() default "";

    boolean remap() default true;
}
