package org.spongepowered.asm.service;

import org.objectweb.asm.tree.ClassNode;

/* loaded from: L-out.jar:org/spongepowered/asm/service/ITreeClassTransformer.class */
public interface ITreeClassTransformer extends ITransformer {
    boolean transformClassNode(String str, String str2, ClassNode classNode);
}
