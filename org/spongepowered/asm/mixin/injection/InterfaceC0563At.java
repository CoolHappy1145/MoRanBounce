package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
/* renamed from: org.spongepowered.asm.mixin.injection.At */
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/At.class */
public @interface InterfaceC0563At {

    /* renamed from: org.spongepowered.asm.mixin.injection.At$Shift */
    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/At$Shift.class */
    public enum Shift {
        NONE,
        BEFORE,
        AFTER,
        BY
    }

    /* renamed from: id */
    String m56id() default "";

    String value();

    String slice() default "";

    Shift shift() default Shift.NONE;

    /* renamed from: by */
    int m57by() default 0;

    String[] args() default {};

    String target() default "";

    int ordinal() default -1;

    int opcode() default -1;

    boolean remap() default true;
}
