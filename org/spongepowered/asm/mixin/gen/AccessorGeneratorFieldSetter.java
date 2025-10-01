package org.spongepowered.asm.mixin.gen;

import kotlin.text.Typography;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/AccessorGeneratorFieldSetter.class */
public class AccessorGeneratorFieldSetter extends AccessorGeneratorField {
    private boolean mutable;

    public AccessorGeneratorFieldSetter(AccessorInfo accessorInfo) {
        super(accessorInfo);
    }

    public void validate() {
        super.validate();
        this.mutable = this.info.getClassInfo().findMethod(this.info.getMethod()).isDecoratedMutable();
        if (!this.mutable && Bytecode.hasFlag(this.targetField, 16) && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            LogManager.getLogger(MixinLaunchPlugin.NAME).warn("{} for final field {}::{} is not @Mutable", new Object[]{this.info, ((MixinTargetContext) this.info.getContext()).getTarget(), this.targetField.name});
        }
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorGenerator
    public MethodNode generate() {
        if (this.mutable) {
            this.targetField.access &= -17;
        }
        int i = this.targetIsStatic ? 0 : 1;
        MethodNode methodNodeCreateMethod = createMethod(i + this.targetType.getSize(), i + this.targetType.getSize());
        if (!this.targetIsStatic) {
            methodNodeCreateMethod.instructions.add(new VarInsnNode(25, 0));
        }
        methodNodeCreateMethod.instructions.add(new VarInsnNode(this.targetType.getOpcode(21), i));
        methodNodeCreateMethod.instructions.add(new FieldInsnNode(this.targetIsStatic ? 179 : 181, this.info.getClassNode().name, this.targetField.name, this.targetField.desc));
        methodNodeCreateMethod.instructions.add(new InsnNode(Typography.plusMinus));
        return methodNodeCreateMethod;
    }
}
