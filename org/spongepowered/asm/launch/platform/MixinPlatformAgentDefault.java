package org.spongepowered.asm.launch.platform;

import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/MixinPlatformAgentDefault.class */
public class MixinPlatformAgentDefault extends MixinPlatformAgentAbstract {
    @Override // org.spongepowered.asm.launch.platform.IMixinPlatformAgent
    public void prepare() {
        String attribute = this.handle.getAttribute(Constants.ManifestAttributes.COMPATIBILITY);
        if (attribute != null) {
            this.manager.setCompatibilityLevel(attribute);
        }
        String attribute2 = this.handle.getAttribute(Constants.ManifestAttributes.MIXINCONFIGS);
        if (attribute2 != null) {
            for (String str : attribute2.split(",")) {
                this.manager.addConfig(str.trim());
            }
        }
        String attribute3 = this.handle.getAttribute(Constants.ManifestAttributes.TOKENPROVIDERS);
        if (attribute3 != null) {
            for (String str2 : attribute3.split(",")) {
                this.manager.addTokenProvider(str2.trim());
            }
        }
        String attribute4 = this.handle.getAttribute(Constants.ManifestAttributes.MIXINCONNECTOR);
        if (attribute4 != null) {
            this.manager.addConnector(attribute4.trim());
        }
    }
}
