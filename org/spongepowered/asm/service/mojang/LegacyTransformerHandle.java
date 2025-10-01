package org.spongepowered.asm.service.mojang;

import javax.annotation.Resource;
import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.service.ILegacyClassTransformer;

/* loaded from: L-out.jar:org/spongepowered/asm/service/mojang/LegacyTransformerHandle.class */
class LegacyTransformerHandle implements ILegacyClassTransformer {
    private final IClassTransformer transformer;

    LegacyTransformerHandle(IClassTransformer iClassTransformer) {
        this.transformer = iClassTransformer;
    }

    @Override // org.spongepowered.asm.service.ITransformer
    public String getName() {
        return this.transformer.getClass().getName();
    }

    @Override // org.spongepowered.asm.service.ITransformer
    public boolean isDelegationExcluded() {
        return this.transformer.getClass().getAnnotation(Resource.class) != null;
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer, org.spongepowered.asm.mixin.transformer.IMixinTransformer
    public byte[] transformClassBytes(String str, String str2, byte[] bArr) {
        return this.transformer.transform(str, str2, bArr);
    }
}
