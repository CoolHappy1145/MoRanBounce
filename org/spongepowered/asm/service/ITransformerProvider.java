package org.spongepowered.asm.service;

import java.util.Collection;

/* loaded from: L-out.jar:org/spongepowered/asm/service/ITransformerProvider.class */
public interface ITransformerProvider {
    Collection getTransformers();

    Collection getDelegatedTransformers();

    void addTransformerExclusion(String str);
}
