package org.spongepowered.asm.mixin.gen;

import java.util.Iterator;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.asm.ElementNode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/InvokerInfo.class */
class InvokerInfo extends AccessorInfo {
    InvokerInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        super(mixinTargetContext, methodNode, Invoker.class);
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorInfo
    protected AccessorInfo.AccessorType initType() {
        if (this.specifiedName != null) {
            return initType(this.mixin.getReferenceMapper().remap(this.mixin.getClassRef(), this.specifiedName).replace('.', '/'), this.mixin.getTargetClassRef());
        }
        AccessorInfo.AccessorName accessorNameM55of = AccessorInfo.AccessorName.m55of(this.method.name, false);
        if (accessorNameM55of != null) {
            Iterator it = AccessorInfo.AccessorType.OBJECT_FACTORY.getExpectedPrefixes().iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals(accessorNameM55of.prefix)) {
                    return initType(accessorNameM55of.name, this.mixin.getTargetClassInfo().getSimpleName());
                }
            }
        }
        return AccessorInfo.AccessorType.METHOD_PROXY;
    }

    private AccessorInfo.AccessorType initType(String str, String str2) {
        if (Constants.CTOR.equals(str) || str2.equals(str)) {
            if (!this.returnType.equals(this.mixin.getTargetClassInfo().getType())) {
                throw new InvalidAccessorException(this.mixin, String.format("%s appears to have an invalid return type. %s requires matching return type. Found %s expected %s", this, AccessorInfo.AccessorType.OBJECT_FACTORY, Bytecode.getSimpleName(this.returnType), this.mixin.getTargetClassInfo().getSimpleName()));
            }
            if (!this.isStatic) {
                throw new InvalidAccessorException(this.mixin, String.format("%s for %s must be static", this, AccessorInfo.AccessorType.OBJECT_FACTORY, Bytecode.getSimpleName(this.returnType)));
            }
            return AccessorInfo.AccessorType.OBJECT_FACTORY;
        }
        return AccessorInfo.AccessorType.METHOD_PROXY;
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorInfo
    protected ITargetSelector initTarget() {
        if (this.type == AccessorInfo.AccessorType.OBJECT_FACTORY) {
            return new MemberInfo(Constants.CTOR, (String) null, Bytecode.changeDescriptorReturnType(this.method.desc, "V"));
        }
        return new MemberInfo(getTargetName(this.specifiedName), (String) null, this.method.desc);
    }

    @Override // org.spongepowered.asm.mixin.gen.AccessorInfo
    public void locate() {
        this.targetMethod = findTargetMethod();
    }

    private MethodNode findTargetMethod() {
        String name;
        try {
            return (MethodNode) TargetSelector.run(this.target.configure(new String[]{"orphan"}), ElementNode.methodList(this.classNode)).getSingleResult(true);
        } catch (IllegalStateException e) {
            String str = e.getMessage() + " matching " + this.target + " in " + this.classNode.name + " for " + this;
            if (this.type == AccessorInfo.AccessorType.METHOD_PROXY && this.specifiedName != null && (this.target instanceof ITargetSelectorByName) && (name = ((ITargetSelectorByName) this.target).getName()) != null && (name.contains(".") || name.contains("/"))) {
                throw new InvalidAccessorException(this, "Invalid factory invoker failed to match the target class. " + str);
            }
            throw new InvalidAccessorException(this, str);
        }
    }
}
