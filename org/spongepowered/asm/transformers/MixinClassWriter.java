package org.spongepowered.asm.transformers;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

/* loaded from: L-out.jar:org/spongepowered/asm/transformers/MixinClassWriter.class */
public class MixinClassWriter extends ClassWriter {
    public MixinClassWriter(int i) {
        super(i);
    }

    public MixinClassWriter(ClassReader classReader, int i) {
        super(classReader, i);
    }

    protected String getCommonSuperClass(String str, String str2) {
        return ClassInfo.getCommonSuperClass(str, str2).getName();
    }
}
