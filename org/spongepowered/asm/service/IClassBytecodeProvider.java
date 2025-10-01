package org.spongepowered.asm.service;

import org.objectweb.asm.tree.ClassNode;

/* loaded from: L-out.jar:org/spongepowered/asm/service/IClassBytecodeProvider.class */
public interface IClassBytecodeProvider {
    ClassNode getClassNode(String str);

    ClassNode getClassNode(String str, boolean z);
}
