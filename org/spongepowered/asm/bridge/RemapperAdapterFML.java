package org.spongepowered.asm.bridge;

import java.lang.reflect.Method;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

/* loaded from: L-out.jar:org/spongepowered/asm/bridge/RemapperAdapterFML.class */
public final class RemapperAdapterFML extends RemapperAdapter {
    private static final String DEOBFUSCATING_REMAPPER_CLASS = "fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
    private static final String DEOBFUSCATING_REMAPPER_CLASS_FORGE = "net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
    private static final String DEOBFUSCATING_REMAPPER_CLASS_LEGACY = "cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper";
    private static final String INSTANCE_FIELD = "INSTANCE";
    private static final String UNMAP_METHOD = "unmap";
    private final Method mdUnmap;

    private RemapperAdapterFML(Remapper remapper, Method method) {
        super(remapper);
        this.logger.info("Initialised Mixin FML Remapper Adapter with {}", new Object[]{remapper});
        this.mdUnmap = method;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper, org.spongepowered.asm.util.ObfuscationUtil.IClassRemapper
    public String unmap(String str) {
        try {
            return this.mdUnmap.invoke(this.remapper, str).toString();
        } catch (Exception unused) {
            return str;
        }
    }

    public static IRemapper create() throws NoSuchFieldException {
        try {
            Class fMLDeobfuscatingRemapper = getFMLDeobfuscatingRemapper();
            return new RemapperAdapterFML((Remapper) fMLDeobfuscatingRemapper.getDeclaredField(INSTANCE_FIELD).get(null), fMLDeobfuscatingRemapper.getDeclaredMethod(UNMAP_METHOD, String.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Class getFMLDeobfuscatingRemapper() throws ClassNotFoundException {
        try {
            return Class.forName(DEOBFUSCATING_REMAPPER_CLASS_FORGE);
        } catch (ClassNotFoundException unused) {
            return Class.forName(DEOBFUSCATING_REMAPPER_CLASS_LEGACY);
        }
    }
}
