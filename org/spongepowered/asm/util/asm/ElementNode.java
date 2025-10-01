package org.spongepowered.asm.util.asm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/* loaded from: L-out.jar:org/spongepowered/asm/util/asm/ElementNode.class */
public abstract class ElementNode {
    private final ClassNode owner;

    public abstract String getName();

    public abstract String getDesc();

    public abstract String getSignature();

    public abstract Object get();

    /* loaded from: L-out.jar:org/spongepowered/asm/util/asm/ElementNode$ElementNodeMethod.class */
    static class ElementNodeMethod extends ElementNode {
        private MethodNode method;

        @Override // org.spongepowered.asm.util.asm.ElementNode
        public Object get() {
            return get();
        }

        ElementNodeMethod(ClassNode classNode, MethodNode methodNode) {
            super(classNode);
            this.method = methodNode;
        }

        public MethodNode getMethod() {
            return this.method;
        }

        @Override // org.spongepowered.asm.util.asm.ElementNode
        public String getName() {
            return this.method.name;
        }

        @Override // org.spongepowered.asm.util.asm.ElementNode
        public String getDesc() {
            return this.method.desc;
        }

        @Override // org.spongepowered.asm.util.asm.ElementNode
        public String getSignature() {
            return this.method.signature;
        }

        @Override // org.spongepowered.asm.util.asm.ElementNode
        public MethodNode get() {
            return this.method;
        }

        public String toString() {
            return String.format("MethodElement[%s%s]", this.method.name, this.method.desc);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/asm/ElementNode$ElementNodeField.class */
    static class ElementNodeField extends ElementNode {
        private FieldNode field;

        @Override // org.spongepowered.asm.util.asm.ElementNode
        public Object get() {
            return get();
        }

        ElementNodeField(ClassNode classNode, FieldNode fieldNode) {
            super(classNode);
            this.field = fieldNode;
        }

        public FieldNode getField() {
            return this.field;
        }

        @Override // org.spongepowered.asm.util.asm.ElementNode
        public String getName() {
            return this.field.name;
        }

        @Override // org.spongepowered.asm.util.asm.ElementNode
        public String getDesc() {
            return this.field.desc;
        }

        @Override // org.spongepowered.asm.util.asm.ElementNode
        public String getSignature() {
            return this.field.signature;
        }

        @Override // org.spongepowered.asm.util.asm.ElementNode
        public FieldNode get() {
            return this.field;
        }

        public String toString() {
            return String.format("FieldElement[%s:%s]", this.field.name, this.field.desc);
        }
    }

    protected ElementNode(ClassNode classNode) {
        this.owner = classNode;
    }

    public ClassNode getOwner() {
        return this.owner;
    }

    public String getOwnerName() {
        if (this.owner != null) {
            return this.owner.name;
        }
        return null;
    }

    /* renamed from: of */
    public static ElementNode m78of(ClassNode classNode, MethodNode methodNode) {
        return new ElementNodeMethod(classNode, methodNode);
    }

    /* renamed from: of */
    public static ElementNode m79of(ClassNode classNode, FieldNode fieldNode) {
        return new ElementNodeField(classNode, fieldNode);
    }

    /* renamed from: of */
    public static ElementNode m80of(ClassNode classNode, Object obj) {
        if (obj instanceof MethodNode) {
            return new ElementNodeMethod(classNode, (MethodNode) obj);
        }
        if (obj instanceof FieldNode) {
            return new ElementNodeField(classNode, (FieldNode) obj);
        }
        throw new IllegalArgumentException("Could not create ElementNode for unknown node type: " + obj.getClass().getName());
    }

    public static List listOf(ClassNode classNode, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(m80of(classNode, it.next()));
        }
        return arrayList;
    }

    public static List fieldList(ClassNode classNode) {
        ArrayList arrayList = new ArrayList();
        Iterator it = classNode.fields.iterator();
        while (it.hasNext()) {
            arrayList.add(new ElementNodeField(classNode, (FieldNode) it.next()));
        }
        return arrayList;
    }

    public static List methodList(ClassNode classNode) {
        ArrayList arrayList = new ArrayList();
        Iterator it = classNode.methods.iterator();
        while (it.hasNext()) {
            arrayList.add(new ElementNodeMethod(classNode, (MethodNode) it.next()));
        }
        return arrayList;
    }
}
