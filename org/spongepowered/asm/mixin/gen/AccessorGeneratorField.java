package org.spongepowered.asm.mixin.gen;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/AccessorGeneratorField.class */
public abstract class AccessorGeneratorField extends AccessorGenerator {
    protected final FieldNode targetField;
    protected final Type targetType;

    public AccessorGeneratorField(AccessorInfo accessorInfo) {
        super(accessorInfo, Bytecode.isStatic(accessorInfo.getTargetField()));
        this.targetField = accessorInfo.getTargetField();
        this.targetType = accessorInfo.getTargetFieldType();
        checkModifiers();
    }
}
