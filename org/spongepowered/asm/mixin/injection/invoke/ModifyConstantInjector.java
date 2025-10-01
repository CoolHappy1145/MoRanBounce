package org.spongepowered.asm.mixin.injection.invoke;

import org.apache.logging.log4j.Level;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.util.InsnFinder;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/ModifyConstantInjector.class */
public class ModifyConstantInjector extends RedirectInjector {
    private static final int OPCODE_OFFSET = 6;

    public ModifyConstantInjector(InjectionInfo injectionInfo) {
        super(injectionInfo, "@ModifyConstant");
    }

    @Override // org.spongepowered.asm.mixin.injection.invoke.RedirectInjector, org.spongepowered.asm.mixin.injection.invoke.InvokeInjector, org.spongepowered.asm.mixin.injection.code.Injector
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        if (!preInject(injectionNode)) {
            return;
        }
        if (injectionNode.isReplaced()) {
            throw new UnsupportedOperationException("Target failure for " + this.info);
        }
        AbstractInsnNode currentTarget = injectionNode.getCurrentTarget();
        if (currentTarget instanceof TypeInsnNode) {
            checkTargetModifiers(target, false);
            injectTypeConstantModifier(target, (TypeInsnNode) currentTarget);
        } else if (currentTarget instanceof JumpInsnNode) {
            checkTargetModifiers(target, false);
            injectExpandedConstantModifier(target, (JumpInsnNode) currentTarget);
        } else {
            if (Bytecode.isConstant(currentTarget)) {
                checkTargetModifiers(target, false);
                injectConstantModifier(target, currentTarget);
                return;
            }
            throw new InvalidInjectionException(this.info, String.format("%s annotation is targetting an invalid insn in %s in %s", this.annotationType, target, this));
        }
    }

    private void injectTypeConstantModifier(Target target, TypeInsnNode typeInsnNode) {
        int opcode = typeInsnNode.getOpcode();
        if (opcode != 193) {
            throw new InvalidInjectionException(this.info, String.format("%s annotation does not support %s insn in %s in %s", this.annotationType, Bytecode.getOpcodeName(opcode), target, this));
        }
        injectAtInstanceOf(target, typeInsnNode);
    }

    private void injectExpandedConstantModifier(Target target, JumpInsnNode jumpInsnNode) {
        int opcode = jumpInsnNode.getOpcode();
        if (opcode < 155 || opcode > 158) {
            throw new InvalidInjectionException(this.info, String.format("%s annotation selected an invalid opcode %s in %s in %s", this.annotationType, Bytecode.getOpcodeName(opcode), target, this));
        }
        Target.Extension extensionExtendStack = target.extendStack();
        InsnList insnList = new InsnList();
        insnList.add(new InsnNode(3));
        AbstractInsnNode abstractInsnNodeInvokeConstantHandler = invokeConstantHandler(Type.getType("I"), target, extensionExtendStack, insnList, insnList);
        insnList.add(new JumpInsnNode(opcode + 6, jumpInsnNode.label));
        extensionExtendStack.add(1).apply();
        target.replaceNode(jumpInsnNode, abstractInsnNodeInvokeConstantHandler, insnList);
    }

    private void injectConstantModifier(Target target, AbstractInsnNode abstractInsnNode) {
        Type constantType = Bytecode.getConstantType(abstractInsnNode);
        if (constantType.getSort() <= 5 && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            checkNarrowing(target, abstractInsnNode, constantType);
        }
        Target.Extension extensionExtendStack = target.extendStack();
        InsnList insnList = new InsnList();
        InsnList insnList2 = new InsnList();
        AbstractInsnNode abstractInsnNodeInvokeConstantHandler = invokeConstantHandler(constantType, target, extensionExtendStack, insnList, insnList2);
        extensionExtendStack.apply();
        target.wrapNode(abstractInsnNode, abstractInsnNodeInvokeConstantHandler, insnList, insnList2);
    }

    private AbstractInsnNode invokeConstantHandler(Type type, Target target, Target.Extension extension, InsnList insnList, InsnList insnList2) {
        Injector.InjectorData injectorData = new Injector.InjectorData(target, "constant modifier");
        validateParams(injectorData, type, type);
        if (!this.isStatic) {
            insnList.insert(new VarInsnNode(25, 0));
            extension.add();
        }
        if (injectorData.captureTargetArgs > 0) {
            pushArgs(target.arguments, insnList2, target.getArgIndices(), 0, injectorData.captureTargetArgs, extension);
        }
        return invokeHandler(insnList2);
    }

    private void checkNarrowing(Target target, AbstractInsnNode abstractInsnNode, Type type) {
        int i;
        LocalVariableNode localVariableAt;
        FieldInsnNode fieldInsnNodeFindPopInsn = new InsnFinder().findPopInsn(target, abstractInsnNode);
        if (fieldInsnNodeFindPopInsn == null) {
            return;
        }
        if (fieldInsnNodeFindPopInsn instanceof FieldInsnNode) {
            FieldInsnNode fieldInsnNode = fieldInsnNodeFindPopInsn;
            Type type2 = Type.getType(fieldInsnNode.desc);
            checkNarrowing(target, abstractInsnNode, type, type2, target.indexOf((AbstractInsnNode) fieldInsnNodeFindPopInsn), String.format("%s %s %s.%s", Bytecode.getOpcodeName((AbstractInsnNode) fieldInsnNodeFindPopInsn), SignaturePrinter.getTypeName(type2, false), fieldInsnNode.owner.replace('/', '.'), fieldInsnNode.name));
        } else {
            if (fieldInsnNodeFindPopInsn.getOpcode() == 172) {
                checkNarrowing(target, abstractInsnNode, type, target.returnType, target.indexOf((AbstractInsnNode) fieldInsnNodeFindPopInsn), "RETURN " + SignaturePrinter.getTypeName(target.returnType, false));
                return;
            }
            if (fieldInsnNodeFindPopInsn.getOpcode() == 54 && (localVariableAt = Locals.getLocalVariableAt(target.classNode, target.method, (AbstractInsnNode) fieldInsnNodeFindPopInsn, (i = ((VarInsnNode) fieldInsnNodeFindPopInsn).var))) != null && localVariableAt.desc != null) {
                String str = localVariableAt.name != null ? localVariableAt.name : "unnamed";
                Type type3 = Type.getType(localVariableAt.desc);
                checkNarrowing(target, abstractInsnNode, type, type3, target.indexOf((AbstractInsnNode) fieldInsnNodeFindPopInsn), String.format("ISTORE[var=%d] %s %s", Integer.valueOf(i), SignaturePrinter.getTypeName(type3, false), str));
            }
        }
    }

    private void checkNarrowing(Target target, AbstractInsnNode abstractInsnNode, Type type, Type type2, int i, String str) {
        int sort = type.getSort();
        int sort2 = type2.getSort();
        if (sort2 < sort) {
            Injector.logger.log(sort2 == 1 ? Level.ERROR : Level.WARN, "Narrowing conversion of <{}> to <{}> in {} target {} at opcode {} ({}){}", new Object[]{SignaturePrinter.getTypeName(type, false), SignaturePrinter.getTypeName(type2, false), this.info, target, Integer.valueOf(i), str, sort2 == 1 ? ". Implicit conversion to <boolean> can cause nondeterministic (JVM-specific) behaviour!" : ""});
        }
    }
}
