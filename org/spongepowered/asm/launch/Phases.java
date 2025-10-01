package org.spongepowered.asm.launch;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import java.util.EnumSet;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/Phases.class */
public final class Phases {
    public static final EnumSet NONE = EnumSet.noneOf(ILaunchPluginService.Phase.class);
    public static final EnumSet BEFORE_ONLY = EnumSet.of(ILaunchPluginService.Phase.BEFORE);
    public static final EnumSet AFTER_ONLY = EnumSet.of(ILaunchPluginService.Phase.AFTER);

    private Phases() {
    }
}
