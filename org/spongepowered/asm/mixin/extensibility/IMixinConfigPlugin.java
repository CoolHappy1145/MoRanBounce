package org.spongepowered.asm.mixin.extensibility;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/extensibility/IMixinConfigPlugin.class */
public interface IMixinConfigPlugin {
    void onLoad(String str);

    String getRefMapperConfig();

    boolean shouldApplyMixin(String str, String str2);

    void acceptTargets(Set set, Set set2);

    List getMixins();

    void preApply(String str, ClassNode classNode, String str2, IMixinInfo iMixinInfo);

    void postApply(String str, ClassNode classNode, String str2, IMixinInfo iMixinInfo);
}
