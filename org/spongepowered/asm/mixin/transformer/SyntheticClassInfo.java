package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Preconditions;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.ISyntheticClassInfo;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/SyntheticClassInfo.class */
public abstract class SyntheticClassInfo implements ISyntheticClassInfo {
    protected final IMixinInfo mixin;
    protected final String name;

    protected SyntheticClassInfo(IMixinInfo iMixinInfo, String str) {
        Preconditions.checkNotNull(iMixinInfo, "parent");
        Preconditions.checkNotNull(str, "name");
        this.mixin = iMixinInfo;
        this.name = str.replace('.', '/');
    }

    @Override // org.spongepowered.asm.service.ISyntheticClassInfo
    public final IMixinInfo getMixin() {
        return this.mixin;
    }

    @Override // org.spongepowered.asm.service.ISyntheticClassInfo
    public String getName() {
        return this.name;
    }

    @Override // org.spongepowered.asm.service.ISyntheticClassInfo
    public String getClassName() {
        return this.name.replace('/', '.');
    }
}
