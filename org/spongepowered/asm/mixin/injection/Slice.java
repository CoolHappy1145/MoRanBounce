package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/Slice.class */
public @interface Slice {
    /* renamed from: id */
    String m65id() default "";

    InterfaceC0563At from() default @InterfaceC0563At("HEAD");

    /* renamed from: to */
    InterfaceC0563At m66to() default @InterfaceC0563At("TAIL");
}
