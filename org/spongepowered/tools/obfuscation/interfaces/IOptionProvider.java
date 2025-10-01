package org.spongepowered.tools.obfuscation.interfaces;

import java.util.List;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/interfaces/IOptionProvider.class */
public interface IOptionProvider {
    String getOption(String str);

    String getOption(String str, String str2);

    boolean getOption(String str, boolean z);

    List getOptions(String str);
}
