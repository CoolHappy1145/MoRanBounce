package org.spongepowered.asm.launch;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import java.util.EnumSet;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/IClassProcessor.class */
public interface IClassProcessor {
    EnumSet handlesClass(Type type, boolean z, String str);

    boolean processClass(ILaunchPluginService.Phase phase, ClassNode classNode, Type type, String str);
}
