package org.spongepowered.asm.mixin.injection.invoke;

import java.util.Arrays;
import java.util.List;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/ModifyArgInjector.class */
public class ModifyArgInjector extends InvokeInjector {
    private final int index;
    private final boolean singleArgMode;

    public ModifyArgInjector(InjectionInfo injectionInfo, int i) {
        super(injectionInfo, "@ModifyArg");
        this.index = i;
        this.singleArgMode = this.methodArgs.length == 1;
    }

    @Override // org.spongepowered.asm.mixin.injection.invoke.InvokeInjector, org.spongepowered.asm.mixin.injection.code.Injector
    protected void sanityCheck(Target target, List list) {
        super.sanityCheck(target, list);
        if (this.singleArgMode && !this.methodArgs[0].equals(this.returnType)) {
            throw new InvalidInjectionException(this.info, "@ModifyArg return type on " + this + " must match the parameter type. ARG=" + this.methodArgs[0] + " RETURN=" + this.returnType);
        }
    }

    @Override // org.spongepowered.asm.mixin.injection.invoke.InvokeInjector
    protected void checkTarget(Target target) {
        if (!this.isStatic && target.isStatic) {
            throw new InvalidInjectionException(this.info, "non-static callback method " + this + " targets a static method which is not supported");
        }
    }

    @Override // org.spongepowered.asm.mixin.injection.invoke.InvokeInjector, org.spongepowered.asm.mixin.injection.code.Injector
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        checkTargetForNode(target, injectionNode, InjectionPoint.RestrictTargetLevel.ALLOW_ALL);
        super.inject(target, injectionNode);
    }

    @Override // org.spongepowered.asm.mixin.injection.invoke.InvokeInjector
    protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode injectionNode) {
        MethodInsnNode currentTarget = injectionNode.getCurrentTarget();
        Type[] argumentTypes = Type.getArgumentTypes(currentTarget.desc);
        int iFindArgIndex = findArgIndex(target, argumentTypes);
        InsnList insnList = new InsnList();
        Target.Extension extensionExtendLocals = target.extendLocals();
        if (this.singleArgMode) {
            injectSingleArgHandler(target, extensionExtendLocals, argumentTypes, iFindArgIndex, insnList);
        } else {
            injectMultiArgHandler(target, extensionExtendLocals, argumentTypes, iFindArgIndex, insnList);
        }
        target.insns.insertBefore(currentTarget, insnList);
        target.extendStack().set(2 - (extensionExtendLocals.get() - 1)).apply();
        extensionExtendLocals.apply();
    }

    private void injectSingleArgHandler(Target target, Target.Extension extension, Type[] typeArr, int i, InsnList insnList) {
        int[] iArrStoreArgs = storeArgs(target, typeArr, insnList, i);
        invokeHandlerWithArgs(typeArr, insnList, iArrStoreArgs, i, i + 1);
        pushArgs(typeArr, insnList, iArrStoreArgs, i + 1, typeArr.length);
        extension.add((iArrStoreArgs[iArrStoreArgs.length - 1] - target.getMaxLocals()) + typeArr[typeArr.length - 1].getSize());
    }

    private void injectMultiArgHandler(Target target, Target.Extension extension, Type[] typeArr, int i, InsnList insnList) {
        if (!Arrays.equals(typeArr, this.methodArgs)) {
            throw new InvalidInjectionException(this.info, "@ModifyArg method " + this + " targets a method with an invalid signature " + Bytecode.getDescriptor(typeArr) + ", expected " + Bytecode.getDescriptor(this.methodArgs));
        }
        int[] iArrStoreArgs = storeArgs(target, typeArr, insnList, 0);
        pushArgs(typeArr, insnList, iArrStoreArgs, 0, i);
        invokeHandlerWithArgs(typeArr, insnList, iArrStoreArgs, 0, typeArr.length);
        pushArgs(typeArr, insnList, iArrStoreArgs, i + 1, typeArr.length);
        extension.add((iArrStoreArgs[iArrStoreArgs.length - 1] - target.getMaxLocals()) + typeArr[typeArr.length - 1].getSize());
    }

    protected int findArgIndex(Target target, Type[] typeArr) {
        if (this.index > -1) {
            if (this.index >= typeArr.length || !typeArr[this.index].equals(this.returnType)) {
                throw new InvalidInjectionException(this.info, "Specified index " + this.index + " for @ModifyArg is invalid for args " + Bytecode.getDescriptor(typeArr) + ", expected " + this.returnType + " on " + this);
            }
            return this.index;
        }
        int i = -1;
        for (int i2 = 0; i2 < typeArr.length; i2++) {
            if (typeArr[i2].equals(this.returnType)) {
                if (i != -1) {
                    throw new InvalidInjectionException(this.info, "Found duplicate args with index [" + i + ", " + i2 + "] matching type " + this.returnType + " for @ModifyArg target " + target + " in " + this + ". Please specify index of desired arg.");
                }
                i = i2;
            }
        }
        if (i == -1) {
            throw new InvalidInjectionException(this.info, "Could not find arg matching type " + this.returnType + " for @ModifyArg target " + target + " in " + this);
        }
        return i;
    }
}
