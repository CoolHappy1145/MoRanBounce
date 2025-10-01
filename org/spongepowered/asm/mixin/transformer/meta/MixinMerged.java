package org.spongepowered.asm.mixin.transformer.meta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/meta/MixinMerged.class */
public @interface MixinMerged {
    String mixin();

    int priority();
}
