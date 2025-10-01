package org.spongepowered.asm.service.mojang;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.service.IClassTracker;

/* loaded from: L-out.jar:org/spongepowered/asm/service/mojang/LaunchClassLoaderUtil.class */
final class LaunchClassLoaderUtil implements IClassTracker {
    private static final String CACHED_CLASSES_FIELD = "cachedClasses";
    private static final String INVALID_CLASSES_FIELD = "invalidClasses";
    private static final String CLASS_LOADER_EXCEPTIONS_FIELD = "classLoaderExceptions";
    private static final String TRANSFORMER_EXCEPTIONS_FIELD = "transformerExceptions";
    private final LaunchClassLoader classLoader;
    private final Map cachedClasses;
    private final Set invalidClasses;
    private final Set classLoaderExceptions;
    private final Set transformerExceptions;

    LaunchClassLoaderUtil(LaunchClassLoader launchClassLoader) {
        this.classLoader = launchClassLoader;
        this.cachedClasses = (Map) getField(launchClassLoader, CACHED_CLASSES_FIELD);
        this.invalidClasses = (Set) getField(launchClassLoader, INVALID_CLASSES_FIELD);
        this.classLoaderExceptions = (Set) getField(launchClassLoader, CLASS_LOADER_EXCEPTIONS_FIELD);
        this.transformerExceptions = (Set) getField(launchClassLoader, TRANSFORMER_EXCEPTIONS_FIELD);
    }

    LaunchClassLoader getClassLoader() {
        return this.classLoader;
    }

    @Override // org.spongepowered.asm.service.IClassTracker
    public boolean isClassLoaded(String str) {
        return this.cachedClasses.containsKey(str);
    }

    @Override // org.spongepowered.asm.service.IClassTracker
    public String getClassRestrictions(String str) {
        String str2 = "";
        if (isClassClassLoaderExcluded(str, null)) {
            str2 = "PACKAGE_CLASSLOADER_EXCLUSION";
        }
        if (isClassTransformerExcluded(str, null)) {
            str2 = (str2.length() > 0 ? str2 + "," : "") + "PACKAGE_TRANSFORMER_EXCLUSION";
        }
        return str2;
    }

    boolean isClassExcluded(String str, String str2) {
        return isClassClassLoaderExcluded(str, str2) || isClassTransformerExcluded(str, str2);
    }

    boolean isClassClassLoaderExcluded(String str, String str2) {
        for (String str3 : getClassLoaderExceptions()) {
            if ((str2 != null && str2.startsWith(str3)) || str.startsWith(str3)) {
                return true;
            }
        }
        return false;
    }

    boolean isClassTransformerExcluded(String str, String str2) {
        for (String str3 : getTransformerExceptions()) {
            if ((str2 != null && str2.startsWith(str3)) || str.startsWith(str3)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.spongepowered.asm.service.IClassTracker
    public void registerInvalidClass(String str) {
        if (this.invalidClasses != null) {
            this.invalidClasses.add(str);
        }
    }

    Set getClassLoaderExceptions() {
        if (this.classLoaderExceptions != null) {
            return this.classLoaderExceptions;
        }
        return Collections.emptySet();
    }

    Set getTransformerExceptions() {
        if (this.transformerExceptions != null) {
            return this.transformerExceptions;
        }
        return Collections.emptySet();
    }

    private static Object getField(LaunchClassLoader launchClassLoader, String str) throws NoSuchFieldException {
        try {
            Field declaredField = LaunchClassLoader.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.get(launchClassLoader);
        } catch (Exception unused) {
            return null;
        }
    }
}
