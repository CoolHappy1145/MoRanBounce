package org.spongepowered.asm.mixin.gen;

import java.util.ArrayList;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.asm.ASM;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/AccessorGenerator.class */
public abstract class AccessorGenerator {
    protected final AccessorInfo info;
    protected final boolean targetIsStatic;

    public abstract MethodNode generate();

    public AccessorGenerator(AccessorInfo accessorInfo, boolean z) {
        this.info = accessorInfo;
        this.targetIsStatic = z;
    }

    protected void checkModifiers() {
        if (this.info.isStatic() && !this.targetIsStatic) {
            IMixinContext context = this.info.getContext();
            Object[] objArr = new Object[2];
            objArr[0] = this.info;
            objArr[1] = this.info.isStatic() ? "" : " not";
            throw new InvalidInjectionException(context, String.format("%s is invalid. Accessor method is%s static but the target is not.", objArr));
        }
    }

    protected final MethodNode createMethod(int i, int i2) {
        MethodNode method = this.info.getMethod();
        MethodNode methodNode = new MethodNode(ASM.API_VERSION, (method.access & (-1025)) | 4096, method.name, method.desc, (String) null, (String[]) null);
        methodNode.visibleAnnotations = new ArrayList();
        methodNode.visibleAnnotations.add(this.info.getAnnotation());
        methodNode.maxLocals = i;
        methodNode.maxStack = i2;
        return methodNode;
    }
}
