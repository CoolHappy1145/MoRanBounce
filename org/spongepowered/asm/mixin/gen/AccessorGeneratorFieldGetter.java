package org.spongepowered.asm.mixin.gen;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/AccessorGeneratorFieldGetter.class */
public class AccessorGeneratorFieldGetter extends AccessorGeneratorField {
    public AccessorGeneratorFieldGetter(AccessorInfo accessorInfo) {
        super(accessorInfo);
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorGenerator
    public MethodNode generate() {
        MethodNode methodNodeCreateMethod = createMethod(this.targetType.getSize(), this.targetType.getSize());
        if (!this.targetIsStatic) {
            methodNodeCreateMethod.instructions.add(new VarInsnNode(25, 0));
        }
        methodNodeCreateMethod.instructions.add(new FieldInsnNode(this.targetIsStatic ? 178 : 180, this.info.getClassNode().name, this.targetField.name, this.targetField.desc));
        methodNodeCreateMethod.instructions.add(new InsnNode(this.targetType.getOpcode(172)));
        return methodNodeCreateMethod;
    }
}
