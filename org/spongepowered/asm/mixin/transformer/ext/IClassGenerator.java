package org.spongepowered.asm.mixin.transformer.ext;

import org.objectweb.asm.tree.ClassNode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ext/IClassGenerator.class */
public interface IClassGenerator {
    String getName();

    boolean generate(String str, ClassNode classNode);
}
