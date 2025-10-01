package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.jvm.internal.ShortCompanionObject;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/Target.class */
public class Target implements Comparable, Iterable {
    public final ClassNode classNode;
    public final MethodNode method;
    public final InsnList insns;
    public final boolean isStatic;
    public final boolean isCtor;
    public final Type[] arguments;
    public final Type returnType;
    private final int maxStack;
    private final int maxLocals;
    private final InjectionNodes injectionNodes = new InjectionNodes();
    private String callbackInfoClass;
    private String callbackDescriptor;
    private int[] argIndices;
    private List argMapVars;
    private LabelNode start;
    private LabelNode end;
    private Bytecode.DelegateInitialiser delegateInitialiser;

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareTo((Target) obj);
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/Target$Extension.class */
    public class Extension {
        private final boolean locals;
        private int size;
        final Target this$0;

        Extension(Target target, boolean z) {
            this.this$0 = target;
            this.locals = z;
        }

        public Extension add() {
            this.size++;
            return this;
        }

        public Extension add(int i) {
            this.size += i;
            return this;
        }

        public Extension add(Type[] typeArr) {
            return add(Bytecode.getArgsSize(typeArr));
        }

        public Extension set(int i) {
            this.size = i;
            return this;
        }

        public int get() {
            return this.size;
        }

        public void apply() {
            if (this.locals) {
                this.this$0.extendLocalsBy(this.size);
            } else {
                this.this$0.extendStackBy(this.size);
            }
            this.size = 0;
        }
    }

    public Target(ClassNode classNode, MethodNode methodNode) {
        this.classNode = classNode;
        this.method = methodNode;
        this.insns = methodNode.instructions;
        this.isStatic = Bytecode.isStatic(methodNode);
        this.isCtor = methodNode.name.equals(Constants.CTOR);
        this.arguments = Type.getArgumentTypes(methodNode.desc);
        this.returnType = Type.getReturnType(methodNode.desc);
        this.maxStack = methodNode.maxStack;
        this.maxLocals = methodNode.maxLocals;
    }

    public InjectionNodes.InjectionNode addInjectionNode(AbstractInsnNode abstractInsnNode) {
        return this.injectionNodes.add(abstractInsnNode);
    }

    public InjectionNodes.InjectionNode getInjectionNode(AbstractInsnNode abstractInsnNode) {
        return this.injectionNodes.get(abstractInsnNode);
    }

    public int getMaxLocals() {
        return this.maxLocals;
    }

    public int getMaxStack() {
        return this.maxStack;
    }

    public int getCurrentMaxLocals() {
        return this.method.maxLocals;
    }

    public int getCurrentMaxStack() {
        return this.method.maxStack;
    }

    public int allocateLocal() {
        return allocateLocals(1);
    }

    public int allocateLocals(int i) {
        int i2 = this.method.maxLocals;
        this.method.maxLocals += i;
        return i2;
    }

    public Extension extendLocals() {
        return new Extension(this, true);
    }

    public Extension extendStack() {
        return new Extension(this, false);
    }

    void extendLocalsBy(int i) {
        setMaxLocals(this.maxLocals + i);
    }

    private void setMaxLocals(int i) {
        if (i > this.method.maxLocals) {
            this.method.maxLocals = i;
        }
    }

    void extendStackBy(int i) {
        setMaxStack(this.maxStack + i);
    }

    private void setMaxStack(int i) {
        if (i > this.method.maxStack) {
            this.method.maxStack = i;
        }
    }

    public int[] generateArgMap(Type[] typeArr, int i) {
        if (this.argMapVars == null) {
            this.argMapVars = new ArrayList();
        }
        int[] iArr = new int[typeArr.length];
        int i2 = 0;
        for (int i3 = i; i3 < typeArr.length; i3++) {
            int size = typeArr[i3].getSize();
            iArr[i3] = allocateArgMapLocal(i2, size);
            i2 += size;
        }
        return iArr;
    }

    private int allocateArgMapLocal(int i, int i2) {
        if (i >= this.argMapVars.size()) {
            int iAllocateLocals = allocateLocals(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                this.argMapVars.add(Integer.valueOf(iAllocateLocals + i3));
            }
            return iAllocateLocals;
        }
        int iIntValue = ((Integer) this.argMapVars.get(i)).intValue();
        if (i2 > 1 && i + i2 > this.argMapVars.size()) {
            int iAllocateLocals2 = allocateLocals(1);
            if (iAllocateLocals2 == iIntValue + 1) {
                this.argMapVars.add(Integer.valueOf(iAllocateLocals2));
                return iIntValue;
            }
            this.argMapVars.set(i, Integer.valueOf(iAllocateLocals2));
            this.argMapVars.add(Integer.valueOf(allocateLocals(1)));
            return iAllocateLocals2;
        }
        return iIntValue;
    }

    public int[] getArgIndices() {
        if (this.argIndices == null) {
            this.argIndices = calcArgIndices(this.isStatic ? 0 : 1);
        }
        return this.argIndices;
    }

    private int[] calcArgIndices(int i) {
        int[] iArr = new int[this.arguments.length];
        for (int i2 = 0; i2 < this.arguments.length; i2++) {
            iArr[i2] = i;
            i += this.arguments[i2].getSize();
        }
        return iArr;
    }

    public String getCallbackInfoClass() {
        if (this.callbackInfoClass == null) {
            this.callbackInfoClass = CallbackInfo.getCallInfoClassName(this.returnType);
        }
        return this.callbackInfoClass;
    }

    public String getSimpleCallbackDescriptor() {
        return String.format("(L%s;)V", getCallbackInfoClass());
    }

    public String getCallbackDescriptor(Type[] typeArr, Type[] typeArr2) {
        return getCallbackDescriptor(false, typeArr, typeArr2, 0, ShortCompanionObject.MAX_VALUE);
    }

    public String getCallbackDescriptor(boolean z, Type[] typeArr, Type[] typeArr2, int i, int i2) {
        if (this.callbackDescriptor == null) {
            this.callbackDescriptor = String.format("(%sL%s;)V", this.method.desc.substring(1, this.method.desc.indexOf(41)), getCallbackInfoClass());
        }
        if (!z || typeArr == null) {
            return this.callbackDescriptor;
        }
        StringBuilder sb = new StringBuilder(this.callbackDescriptor.substring(0, this.callbackDescriptor.indexOf(41)));
        for (int i3 = i; i3 < typeArr.length && i2 > 0; i3++) {
            if (typeArr[i3] != null) {
                sb.append(typeArr[i3].getDescriptor());
                i2--;
            }
        }
        return sb.append(")V").toString();
    }

    public String toString() {
        return String.format("%s::%s%s", this.classNode.name, this.method.name, this.method.desc);
    }

    public int compareTo(Target target) {
        if (target == null) {
            return Integer.MAX_VALUE;
        }
        return toString().compareTo(target.toString());
    }

    public int indexOf(InjectionNodes.InjectionNode injectionNode) {
        return this.insns.indexOf(injectionNode.getCurrentTarget());
    }

    public int indexOf(AbstractInsnNode abstractInsnNode) {
        return this.insns.indexOf(abstractInsnNode);
    }

    public AbstractInsnNode get(int i) {
        return this.insns.get(i);
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        return this.insns.iterator();
    }

    public MethodInsnNode findInitNodeFor(TypeInsnNode typeInsnNode) {
        ListIterator it = this.insns.iterator(indexOf((AbstractInsnNode) typeInsnNode));
        while (it.hasNext()) {
            MethodInsnNode methodInsnNode = (AbstractInsnNode) it.next();
            if ((methodInsnNode instanceof MethodInsnNode) && methodInsnNode.getOpcode() == 183) {
                MethodInsnNode methodInsnNode2 = methodInsnNode;
                if (Constants.CTOR.equals(methodInsnNode2.name) && methodInsnNode2.owner.equals(typeInsnNode.desc)) {
                    return methodInsnNode2;
                }
            }
        }
        return null;
    }

    public Bytecode.DelegateInitialiser findDelegateInitNode() {
        if (!this.isCtor) {
            return null;
        }
        if (this.delegateInitialiser == null) {
            this.delegateInitialiser = Bytecode.findDelegateInit(this.method, ClassInfo.forName(this.classNode.name).getSuperName(), this.classNode.name);
        }
        return this.delegateInitialiser;
    }

    public void insertBefore(InjectionNodes.InjectionNode injectionNode, InsnList insnList) {
        this.insns.insertBefore(injectionNode.getCurrentTarget(), insnList);
    }

    public void insertBefore(AbstractInsnNode abstractInsnNode, InsnList insnList) {
        this.insns.insertBefore(abstractInsnNode, insnList);
    }

    public void replaceNode(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
        this.insns.insertBefore(abstractInsnNode, abstractInsnNode2);
        this.insns.remove(abstractInsnNode);
        this.injectionNodes.replace(abstractInsnNode, abstractInsnNode2);
    }

    public void replaceNode(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2, InsnList insnList) {
        this.insns.insertBefore(abstractInsnNode, insnList);
        this.insns.remove(abstractInsnNode);
        this.injectionNodes.replace(abstractInsnNode, abstractInsnNode2);
    }

    public void wrapNode(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2, InsnList insnList, InsnList insnList2) {
        this.insns.insertBefore(abstractInsnNode, insnList);
        this.insns.insert(abstractInsnNode, insnList2);
        this.injectionNodes.replace(abstractInsnNode, abstractInsnNode2);
    }

    public void replaceNode(AbstractInsnNode abstractInsnNode, InsnList insnList) {
        this.insns.insertBefore(abstractInsnNode, insnList);
        removeNode(abstractInsnNode);
    }

    public void removeNode(AbstractInsnNode abstractInsnNode) {
        this.insns.remove(abstractInsnNode);
        this.injectionNodes.remove(abstractInsnNode);
    }

    public void addLocalVariable(int i, String str, String str2) {
        if (this.start == null) {
            this.start = new LabelNode(new Label());
            this.end = new LabelNode(new Label());
            this.insns.insert(this.start);
            this.insns.add(this.end);
        }
        addLocalVariable(i, str, str2, this.start, this.end);
    }

    private void addLocalVariable(int i, String str, String str2, LabelNode labelNode, LabelNode labelNode2) {
        if (this.method.localVariables == null) {
            this.method.localVariables = new ArrayList();
        }
        this.method.localVariables.add(new LocalVariableNode(str, str2, (String) null, labelNode, labelNode2, i));
    }
}
