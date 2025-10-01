package org.spongepowered.asm.mixin.transformer.meta;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/meta/MixinRenamed.class */
public @interface MixinRenamed {
    String originalName();

    boolean isInterfaceMember();
}
