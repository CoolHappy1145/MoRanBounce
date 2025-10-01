package org.spongepowered.asm.mixin.transformer.meta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.CLASS)
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/meta/MixinInner.class */
public @interface MixinInner {
    String mixin();

    String name();
}
