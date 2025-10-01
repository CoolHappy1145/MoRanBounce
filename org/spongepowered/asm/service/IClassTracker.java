package org.spongepowered.asm.service;

/* loaded from: L-out.jar:org/spongepowered/asm/service/IClassTracker.class */
public interface IClassTracker {
    void registerInvalidClass(String str);

    boolean isClassLoaded(String str);

    String getClassRestrictions(String str);
}
