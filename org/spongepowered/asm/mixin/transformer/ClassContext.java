package org.spongepowered.asm.mixin.transformer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import kotlin.text.Typography;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.struct.MemberRef;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassContext.class */
abstract class ClassContext {
    private final Set upgradedMethods = new HashSet();

    abstract String getClassRef();

    abstract ClassNode getClassNode();

    abstract ClassInfo getClassInfo();

    ClassContext() {
    }

    void addUpgradedMethod(MethodNode methodNode) {
        ClassInfo.Method methodFindMethod = getClassInfo().findMethod(methodNode);
        if (methodFindMethod == null) {
            throw new IllegalStateException("Meta method for " + methodNode.name + " not located in " + this);
        }
        this.upgradedMethods.add(methodFindMethod);
    }

    protected void upgradeMethods() {
        Iterator it = getClassNode().methods.iterator();
        while (it.hasNext()) {
            upgradeMethod((MethodNode) it.next());
        }
    }

    private void upgradeMethod(MethodNode methodNode) {
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            MethodInsnNode methodInsnNode = (AbstractInsnNode) it.next();
            if (methodInsnNode instanceof MethodInsnNode) {
                MemberRef.Method method = new MemberRef.Method(methodInsnNode);
                if (method.getOwner().equals(getClassRef())) {
                    upgradeMethodRef(methodNode, method, getClassInfo().findMethod(method.getName(), method.getDesc(), 10));
                }
            }
        }
    }

    protected void upgradeMethodRef(MethodNode methodNode, MemberRef memberRef, ClassInfo.Method method) {
        if (memberRef.getOpcode() == 183 && this.upgradedMethods.contains(method)) {
            memberRef.setOpcode(Typography.paragraph);
        }
    }
}
