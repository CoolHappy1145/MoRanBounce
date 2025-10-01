package org.spongepowered.asm.transformers;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.service.ILegacyClassTransformer;

/* loaded from: L-out.jar:org/spongepowered/asm/transformers/TreeTransformer.class */
public abstract class TreeTransformer implements ILegacyClassTransformer {
    private ClassReader classReader;
    private ClassNode classNode;

    protected final ClassNode readClass(byte[] bArr) {
        return readClass(bArr, true);
    }

    protected final ClassNode readClass(byte[] bArr, boolean z) {
        ClassReader classReader = new ClassReader(bArr);
        if (z) {
            this.classReader = classReader;
        }
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 8);
        return classNode;
    }

    protected final byte[] writeClass(ClassNode classNode) {
        if (this.classReader != null && this.classNode == classNode) {
            this.classNode = null;
            MixinClassWriter mixinClassWriter = new MixinClassWriter(this.classReader, 3);
            this.classReader = null;
            classNode.accept(mixinClassWriter);
            return mixinClassWriter.toByteArray();
        }
        this.classNode = null;
        MixinClassWriter mixinClassWriter2 = new MixinClassWriter(3);
        classNode.accept(mixinClassWriter2);
        return mixinClassWriter2.toByteArray();
    }
}
