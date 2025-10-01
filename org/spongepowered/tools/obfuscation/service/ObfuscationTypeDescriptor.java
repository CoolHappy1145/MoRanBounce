package org.spongepowered.tools.obfuscation.service;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/service/ObfuscationTypeDescriptor.class */
public class ObfuscationTypeDescriptor {
    private final String key;
    private final String inputFileArgName;
    private final String extraInputFilesArgName;
    private final String outFileArgName;
    private final Class environmentType;

    public ObfuscationTypeDescriptor(String str, String str2, String str3, Class cls) {
        this(str, str2, null, str3, cls);
    }

    public ObfuscationTypeDescriptor(String str, String str2, String str3, String str4, Class cls) {
        this.key = str;
        this.inputFileArgName = str2;
        this.extraInputFilesArgName = str3;
        this.outFileArgName = str4;
        this.environmentType = cls;
    }

    public final String getKey() {
        return this.key;
    }

    public String getInputFileOption() {
        return this.inputFileArgName;
    }

    public String getExtraInputFilesOption() {
        return this.extraInputFilesArgName;
    }

    public String getOutputFileOption() {
        return this.outFileArgName;
    }

    public Class getEnvironmentType() {
        return this.environmentType;
    }
}
