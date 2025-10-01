package org.spongepowered.asm.mixin.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.service.ILegacyClassTransformer;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/Proxy.class */
public final class Proxy implements IClassTransformer, ILegacyClassTransformer {
    private static List proxies = new ArrayList();
    private static MixinTransformer transformer = new MixinTransformer();
    private boolean isActive;

    public Proxy() {
        this.isActive = true;
        Iterator it = proxies.iterator();
        while (it.hasNext()) {
            ((Proxy) it.next()).isActive = false;
        }
        proxies.add(this);
        LogManager.getLogger(MixinLaunchPlugin.NAME).debug("Adding new mixin transformer proxy #{}", new Object[]{Integer.valueOf(proxies.size())});
    }

    public byte[] transform(String str, String str2, byte[] bArr) {
        if (this.isActive) {
            return transformer.transformClassBytes(str, str2, bArr);
        }
        return bArr;
    }

    @Override // org.spongepowered.asm.service.ITransformer
    public String getName() {
        return getClass().getName();
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer, org.spongepowered.asm.mixin.transformer.IMixinTransformer
    public byte[] transformClassBytes(String str, String str2, byte[] bArr) {
        if (this.isActive) {
            return transformer.transformClassBytes(str, str2, bArr);
        }
        return bArr;
    }
}
