package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/InjectionNodes.class */
public class InjectionNodes extends ArrayList {
    private static final long serialVersionUID = 1;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/InjectionNodes$InjectionNode.class */
    public static class InjectionNode implements Comparable {
        private static int nextId = 0;

        /* renamed from: id */
        private final int f219id;
        private final AbstractInsnNode originalTarget;
        private AbstractInsnNode currentTarget;
        private Map decorations;

        @Override // java.lang.Comparable
        public int compareTo(Object obj) {
            return compareTo((InjectionNode) obj);
        }

        public InjectionNode(AbstractInsnNode abstractInsnNode) {
            this.originalTarget = abstractInsnNode;
            this.currentTarget = abstractInsnNode;
            int i = nextId;
            nextId = i + 1;
            this.f219id = i;
        }

        public int getId() {
            return this.f219id;
        }

        public AbstractInsnNode getOriginalTarget() {
            return this.originalTarget;
        }

        public AbstractInsnNode getCurrentTarget() {
            return this.currentTarget;
        }

        public InjectionNode replace(AbstractInsnNode abstractInsnNode) {
            this.currentTarget = abstractInsnNode;
            return this;
        }

        public InjectionNode remove() {
            this.currentTarget = null;
            return this;
        }

        public boolean matches(AbstractInsnNode abstractInsnNode) {
            return this.originalTarget == abstractInsnNode || this.currentTarget == abstractInsnNode;
        }

        public boolean isReplaced() {
            return this.originalTarget != this.currentTarget;
        }

        public boolean isRemoved() {
            return this.currentTarget == null;
        }

        public InjectionNode decorate(String str, Object obj) {
            if (this.decorations == null) {
                this.decorations = new HashMap();
            }
            this.decorations.put(str, obj);
            return this;
        }

        public boolean hasDecoration(String str) {
            return (this.decorations == null || this.decorations.get(str) == null) ? false : true;
        }

        public Object getDecoration(String str) {
            if (this.decorations == null) {
                return null;
            }
            return this.decorations.get(str);
        }

        public int compareTo(InjectionNode injectionNode) {
            if (injectionNode == null) {
                return Integer.MAX_VALUE;
            }
            return hashCode() - injectionNode.hashCode();
        }

        public String toString() {
            return String.format("InjectionNode[%s]", Bytecode.describeNode(this.currentTarget).replaceAll("\\s+", " "));
        }
    }

    public InjectionNode add(AbstractInsnNode abstractInsnNode) {
        InjectionNode injectionNode = get(abstractInsnNode);
        if (injectionNode == null) {
            injectionNode = new InjectionNode(abstractInsnNode);
            add((InjectionNodes) injectionNode);
        }
        return injectionNode;
    }

    public InjectionNode get(AbstractInsnNode abstractInsnNode) {
        Iterator it = iterator();
        while (it.hasNext()) {
            InjectionNode injectionNode = (InjectionNode) it.next();
            if (injectionNode.matches(abstractInsnNode)) {
                return injectionNode;
            }
        }
        return null;
    }

    public boolean contains(AbstractInsnNode abstractInsnNode) {
        return get(abstractInsnNode) != null;
    }

    public void replace(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
        InjectionNode injectionNode = get(abstractInsnNode);
        if (injectionNode != null) {
            injectionNode.replace(abstractInsnNode2);
        }
    }

    public void remove(AbstractInsnNode abstractInsnNode) {
        InjectionNode injectionNode = get(abstractInsnNode);
        if (injectionNode != null) {
            injectionNode.remove();
        }
    }
}
