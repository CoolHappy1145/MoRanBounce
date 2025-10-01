package org.spongepowered.tools.obfuscation.mapping.fg3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/fg3/MappingMethodLazy.class */
public class MappingMethodLazy extends MappingMethod {
    private static final Pattern PATTERN_CLASSNAME = Pattern.compile("L([^;]+);");
    private final String originalDesc;
    private final IMappingProvider mappingProvider;
    private String newDesc;

    public MappingMethodLazy(String str, String str2, String str3, IMappingProvider iMappingProvider) {
        super(str, str2, "{" + str3 + "}");
        this.originalDesc = str3;
        this.mappingProvider = iMappingProvider;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public String getDesc() {
        if (this.newDesc == null) {
            this.newDesc = generateDescriptor();
        }
        return this.newDesc;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod
    public String toString() {
        String desc = getDesc();
        Object[] objArr = new Object[3];
        objArr[0] = getName();
        objArr[1] = desc != null ? " " : "";
        objArr[2] = desc != null ? desc : "";
        return String.format("%s%s%s", objArr);
    }

    private String generateDescriptor() {
        StringBuffer stringBuffer = new StringBuffer();
        Matcher matcher = PATTERN_CLASSNAME.matcher(this.originalDesc);
        while (matcher.find()) {
            String classMapping = this.mappingProvider.getClassMapping(matcher.group(1));
            if (classMapping != null) {
                matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement("L" + classMapping + ";"));
            } else {
                matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement("L" + matcher.group(1) + ";"));
            }
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }
}
