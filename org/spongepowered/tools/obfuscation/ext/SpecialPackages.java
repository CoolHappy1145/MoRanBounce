package org.spongepowered.tools.obfuscation.ext;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/ext/SpecialPackages.class */
public final class SpecialPackages {
    private static final Set suppressWarningsForPackages = new HashSet();

    static {
        addExcludedPackage("java.");
        addExcludedPackage("javax.");
        addExcludedPackage("sun.");
        addExcludedPackage("com.sun.");
    }

    private SpecialPackages() {
    }

    public static final void addExcludedPackage(String str) {
        String strReplace = str.replace('.', '/');
        if (!strReplace.endsWith("/")) {
            strReplace = strReplace + "/";
        }
        suppressWarningsForPackages.add(strReplace);
    }

    public static boolean isExcludedPackage(String str) {
        Iterator it = suppressWarningsForPackages.iterator();
        while (it.hasNext()) {
            if (str.startsWith((String) it.next())) {
                return true;
            }
        }
        return false;
    }
}
