package org.spongepowered.asm.mixin.transformer.ext;

import java.util.List;
import org.spongepowered.asm.service.ISyntheticClassRegistry;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ext/IExtensionRegistry.class */
public interface IExtensionRegistry {
    List getExtensions();

    List getActiveExtensions();

    IExtension getExtension(Class cls);

    ISyntheticClassRegistry getSyntheticClassRegistry();
}
