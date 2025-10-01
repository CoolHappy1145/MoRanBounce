package org.spongepowered.asm.mixin.injection.invoke;

import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/ModifyArgsInjector.class */
public class ModifyArgsInjector extends InvokeInjector {
    private final ArgsClassGenerator argsClassGenerator;

    public ModifyArgsInjector(InjectionInfo injectionInfo) {
        super(injectionInfo, "@ModifyArgs");
        this.argsClassGenerator = (ArgsClassGenerator) injectionInfo.getContext().getExtensions().getGenerator(ArgsClassGenerator.class);
    }

    @Override // org.spongepowered.asm.mixin.injection.invoke.InvokeInjector
    protected void checkTarget(Target target) {
        checkTargetModifiers(target, false);
    }

    @Override // org.spongepowered.asm.mixin.injection.invoke.InvokeInjector, org.spongepowered.asm.mixin.injection.code.Injector
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        checkTargetForNode(target, injectionNode, InjectionPoint.RestrictTargetLevel.ALLOW_ALL);
        super.inject(target, injectionNode);
    }

    @Override // org.spongepowered.asm.mixin.injection.invoke.InvokeInjector
    protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode injectionNode) {
        MethodInsnNode methodInsnNode = (MethodInsnNode) injectionNode.getCurrentTarget();
        Type[] argumentTypes = Type.getArgumentTypes(methodInsnNode.desc);
        if (argumentTypes.length == 0) {
            throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " targets a method invocation " + methodInsnNode.name + methodInsnNode.desc + " with no arguments!");
        }
        String name = this.argsClassGenerator.getArgsClass(methodInsnNode.desc, this.info.getContext().getMixin()).getName();
        boolean zVerifyTarget = verifyTarget(target);
        InsnList insnList = new InsnList();
        Target.Extension extensionAdd = target.extendStack().add(1);
        packArgs(insnList, name, methodInsnNode);
        if (zVerifyTarget) {
            extensionAdd.add(target.arguments);
            Bytecode.loadArgs(target.arguments, insnList, target.isStatic ? 0 : 1);
        }
        invokeHandler(insnList);
        unpackArgs(insnList, name, argumentTypes);
        extensionAdd.apply();
        target.insns.insertBefore(methodInsnNode, insnList);
    }

    private boolean verifyTarget(Target target) {
        String str = String.format("(L%s;)V", ArgsClassGenerator.ARGS_REF);
        if (!this.methodNode.desc.equals(str)) {
            String str2 = String.format("(L%s;%s", ArgsClassGenerator.ARGS_REF, Bytecode.changeDescriptorReturnType(target.method.desc, "V").substring(1));
            if (this.methodNode.desc.equals(str2)) {
                return true;
            }
            throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " has an invalid signature " + this.methodNode.desc + ", expected " + str + " or " + str2);
        }
        return false;
    }

    private void packArgs(InsnList insnList, String str, MethodInsnNode methodInsnNode) {
        insnList.add(new MethodInsnNode(SyslogAppender.LOG_LOCAL7, str, "of", Bytecode.changeDescriptorReturnType(methodInsnNode.desc, "L" + str + ";"), false));
        insnList.add(new InsnNode(89));
        if (!this.isStatic) {
            insnList.add(new VarInsnNode(25, 0));
            insnList.add(new InsnNode(95));
        }
    }

    private void unpackArgs(InsnList insnList, String str, Type[] typeArr) {
        for (int i = 0; i < typeArr.length; i++) {
            if (i < typeArr.length - 1) {
                insnList.add(new InsnNode(89));
            }
            insnList.add(new MethodInsnNode(Typography.paragraph, str, ArgsClassGenerator.GETTER_PREFIX + i, "()" + typeArr[i].getDescriptor(), false));
            if (i < typeArr.length - 1) {
                if (typeArr[i].getSize() == 1) {
                    insnList.add(new InsnNode(95));
                } else {
                    insnList.add(new InsnNode(93));
                    insnList.add(new InsnNode(88));
                }
            }
        }
    }
}
