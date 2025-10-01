package org.spongepowered.asm.service.modlauncher;

import cpw.mods.gross.Java9ClassLoaderUtil;
import cpw.mods.modlauncher.Launcher;
import java.net.URL;
import org.spongepowered.asm.service.IClassProvider;

/* loaded from: L-out.jar:org/spongepowered/asm/service/modlauncher/ModLauncherClassProvider.class */
class ModLauncherClassProvider implements IClassProvider {
    ModLauncherClassProvider() {
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    @Deprecated
    public URL[] getClassPath() {
        return Java9ClassLoaderUtil.getSystemClassPathURLs();
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    public Class findClass(String str) {
        return Class.forName(str, true, Thread.currentThread().getContextClassLoader());
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    public Class findClass(String str, boolean z) {
        return Class.forName(str, z, Thread.currentThread().getContextClassLoader());
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    public Class findAgentClass(String str, boolean z) {
        return Class.forName(str, z, Launcher.class.getClassLoader());
    }
}
