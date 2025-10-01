package org.spongepowered.asm.mixin.injection.code;

import java.util.ListIterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/code/ReadOnlyInsnList.class */
class ReadOnlyInsnList extends InsnList {
    private InsnList insnList;

    public ReadOnlyInsnList(InsnList insnList) {
        this.insnList = insnList;
    }

    void dispose() {
        this.insnList = null;
    }

    public final void set(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
        throw new UnsupportedOperationException();
    }

    public final void add(AbstractInsnNode abstractInsnNode) {
        throw new UnsupportedOperationException();
    }

    public final void add(InsnList insnList) {
        throw new UnsupportedOperationException();
    }

    public final void insert(AbstractInsnNode abstractInsnNode) {
        throw new UnsupportedOperationException();
    }

    public final void insert(InsnList insnList) {
        throw new UnsupportedOperationException();
    }

    public final void insert(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
        throw new UnsupportedOperationException();
    }

    public final void insert(AbstractInsnNode abstractInsnNode, InsnList insnList) {
        throw new UnsupportedOperationException();
    }

    public final void insertBefore(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
        throw new UnsupportedOperationException();
    }

    public final void insertBefore(AbstractInsnNode abstractInsnNode, InsnList insnList) {
        throw new UnsupportedOperationException();
    }

    public final void remove(AbstractInsnNode abstractInsnNode) {
        throw new UnsupportedOperationException();
    }

    public AbstractInsnNode[] toArray() {
        return this.insnList.toArray();
    }

    public int size() {
        return this.insnList.size();
    }

    public AbstractInsnNode getFirst() {
        return this.insnList.getFirst();
    }

    public AbstractInsnNode getLast() {
        return this.insnList.getLast();
    }

    public AbstractInsnNode get(int i) {
        return this.insnList.get(i);
    }

    public boolean contains(AbstractInsnNode abstractInsnNode) {
        return this.insnList.contains(abstractInsnNode);
    }

    public int indexOf(AbstractInsnNode abstractInsnNode) {
        return this.insnList.indexOf(abstractInsnNode);
    }

    public ListIterator iterator() {
        return this.insnList.iterator();
    }

    public ListIterator iterator(int i) {
        return this.insnList.iterator(i);
    }

    public final void resetLabels() {
        this.insnList.resetLabels();
    }
}
