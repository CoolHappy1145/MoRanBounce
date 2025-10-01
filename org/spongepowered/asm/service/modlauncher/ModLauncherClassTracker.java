package org.spongepowered.asm.service.modlauncher;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.IClassProcessor;
import org.spongepowered.asm.launch.Phases;
import org.spongepowered.asm.service.IClassTracker;

/* loaded from: L-out.jar:org/spongepowered/asm/service/modlauncher/ModLauncherClassTracker.class */
public class ModLauncherClassTracker implements IClassProcessor, IClassTracker {
    private final Set invalidClasses = new HashSet();
    private final Set loadedClasses = new HashSet();

    @Override // org.spongepowered.asm.service.IClassTracker
    public void registerInvalidClass(String str) {
        synchronized (this.invalidClasses) {
            this.invalidClasses.add(str);
        }
    }

    @Override // org.spongepowered.asm.service.IClassTracker
    public boolean isClassLoaded(String str) {
        boolean zContains;
        synchronized (this.loadedClasses) {
            zContains = this.loadedClasses.contains(str);
        }
        return zContains;
    }

    @Override // org.spongepowered.asm.launch.IClassProcessor
    public EnumSet handlesClass(Type type, boolean z, String str) {
        String className = type.getClassName();
        synchronized (this.invalidClasses) {
            if (this.invalidClasses.contains(className)) {
                throw new NoClassDefFoundError(String.format("%s is invalid", className));
            }
        }
        return Phases.AFTER_ONLY;
    }

    @Override // org.spongepowered.asm.launch.IClassProcessor
    public boolean processClass(ILaunchPluginService.Phase phase, ClassNode classNode, Type type, String str) {
        synchronized (this.loadedClasses) {
            this.loadedClasses.add(type.getClassName());
        }
        return false;
    }
}
