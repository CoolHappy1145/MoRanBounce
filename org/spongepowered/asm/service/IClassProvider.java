package org.spongepowered.asm.service;

import java.net.URL;

/* loaded from: L-out.jar:org/spongepowered/asm/service/IClassProvider.class */
public interface IClassProvider {
    @Deprecated
    URL[] getClassPath();

    Class findClass(String str);

    Class findClass(String str, boolean z);

    Class findAgentClass(String str, boolean z);
}
