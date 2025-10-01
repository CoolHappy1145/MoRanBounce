package net.ccbluex.liquidbounce.injection.forge;

import net.ccbluex.liquidbounce.injection.transformers.ForgeNetworkTransformer;
import net.ccbluex.liquidbounce.script.remapper.injection.transformers.AbstractJavaLinkerTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/TransformerLoader.class */
public class TransformerLoader implements IFMLLoadingPlugin {
    public String[] getASMTransformerClass() {
        return new String[]{ForgeNetworkTransformer.class.getName(), AbstractJavaLinkerTransformer.class.getName()};
    }
}
