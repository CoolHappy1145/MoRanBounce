package org.spongepowered.asm.mixin.gen;

import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/AccessorGeneratorMethodProxy.class */
public class AccessorGeneratorMethodProxy extends AccessorGenerator {
    protected final MethodNode targetMethod;
    protected final Type[] argTypes;
    protected final Type returnType;

    public AccessorGeneratorMethodProxy(AccessorInfo accessorInfo) {
        super(accessorInfo, Bytecode.isStatic(accessorInfo.getTargetMethod()));
        this.targetMethod = accessorInfo.getTargetMethod();
        this.argTypes = accessorInfo.getArgTypes();
        this.returnType = accessorInfo.getReturnType();
        checkModifiers();
    }

    protected AccessorGeneratorMethodProxy(AccessorInfo accessorInfo, boolean z) {
        super(accessorInfo, z);
        this.targetMethod = accessorInfo.getTargetMethod();
        this.argTypes = accessorInfo.getArgTypes();
        this.returnType = accessorInfo.getReturnType();
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorGenerator
    public MethodNode generate() {
        int argsSize = Bytecode.getArgsSize(this.argTypes) + this.returnType.getSize() + (this.targetIsStatic ? 0 : 1);
        MethodNode methodNodeCreateMethod = createMethod(argsSize, argsSize);
        if (!this.targetIsStatic) {
            methodNodeCreateMethod.instructions.add(new VarInsnNode(25, 0));
        }
        Bytecode.loadArgs(this.argTypes, methodNodeCreateMethod.instructions, this.targetIsStatic ? 0 : 1);
        methodNodeCreateMethod.instructions.add(new MethodInsnNode(this.targetIsStatic ? SyslogAppender.LOG_LOCAL7 : Bytecode.hasFlag(this.targetMethod, 2) ? Typography.middleDot : Typography.paragraph, this.info.getClassNode().name, this.targetMethod.name, this.targetMethod.desc, false));
        methodNodeCreateMethod.instructions.add(new InsnNode(this.returnType.getOpcode(172)));
        return methodNodeCreateMethod;
    }
}
