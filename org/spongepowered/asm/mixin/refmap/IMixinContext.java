package org.spongepowered.asm.mixin.refmap;

import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/refmap/IMixinContext.class */
public interface IMixinContext {
    IMixinInfo getMixin();

    Extensions getExtensions();

    String getClassName();

    String getClassRef();

    String getTargetClassRef();

    IReferenceMapper getReferenceMapper();

    boolean getOption(MixinEnvironment.Option option);

    int getPriority();

    Target getTargetMethod(MethodNode methodNode);
}
