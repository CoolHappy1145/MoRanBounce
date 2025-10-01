package org.spongepowered.tools.obfuscation;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/SuppressedBy.class */
public enum SuppressedBy {
    CONSTRAINTS("constraints"),
    VISIBILITY("visibility"),
    TARGET("target"),
    MAPPING("mapping"),
    OVERWRITE("overwrite"),
    DEFAULT_PACKAGE("default-package"),
    PUBLIC_TARGET("public-target"),
    RAW_TYPES("rawtypes");

    private final String token;

    SuppressedBy(String str) {
        this.token = str;
    }

    public String getToken() {
        return this.token;
    }
}
