package org.spongepowered.asm.mixin.gen;

import kotlin.text.Typography;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/AccessorGeneratorObjectFactory.class */
public class AccessorGeneratorObjectFactory extends AccessorGeneratorMethodProxy {
    public AccessorGeneratorObjectFactory(AccessorInfo accessorInfo) {
        super(accessorInfo, true);
        if (!accessorInfo.isStatic()) {
            throw new InvalidInjectionException(accessorInfo.getContext(), String.format("%s is invalid. Factory method must be static.", this.info));
        }
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorGeneratorMethodProxy, org.spongepowered.asm.mixin.gen.AccessorGenerator
    public MethodNode generate() {
        int size = this.returnType.getSize();
        int argsSize = Bytecode.getArgsSize(this.argTypes) + (size * 2);
        MethodNode methodNodeCreateMethod = createMethod(argsSize, argsSize);
        String str = this.info.getClassNode().name;
        methodNodeCreateMethod.instructions.add(new TypeInsnNode(Typography.rightGuillemete, str));
        methodNodeCreateMethod.instructions.add(new InsnNode(size == 1 ? 89 : 92));
        Bytecode.loadArgs(this.argTypes, methodNodeCreateMethod.instructions, 0);
        methodNodeCreateMethod.instructions.add(new MethodInsnNode(Typography.middleDot, str, Constants.CTOR, this.targetMethod.desc, false));
        methodNodeCreateMethod.instructions.add(new InsnNode(176));
        return methodNodeCreateMethod;
    }
}
