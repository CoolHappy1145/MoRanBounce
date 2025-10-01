package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.List;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/modify/ModifyVariableInjector.class */
public class ModifyVariableInjector extends Injector {
    private final LocalVariableDiscriminator discriminator;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/modify/ModifyVariableInjector$Context.class */
    static class Context extends LocalVariableDiscriminator.Context {
        final InsnList insns;

        public Context(Type type, boolean z, Target target, AbstractInsnNode abstractInsnNode) {
            super(type, z, target, abstractInsnNode);
            this.insns = new InsnList();
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/modify/ModifyVariableInjector$ContextualInjectionPoint.class */
    static abstract class ContextualInjectionPoint extends InjectionPoint {
        protected final IMixinContext context;

        abstract boolean find(Target target, Collection collection);

        ContextualInjectionPoint(IMixinContext iMixinContext) {
            this.context = iMixinContext;
        }

        @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
        public boolean find(String str, InsnList insnList, Collection collection) {
            throw new InvalidInjectionException(this.context, getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
        }
    }

    public ModifyVariableInjector(InjectionInfo injectionInfo, LocalVariableDiscriminator localVariableDiscriminator) {
        super(injectionInfo, "@ModifyVariable");
        this.discriminator = localVariableDiscriminator;
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected boolean findTargetNodes(MethodNode methodNode, InjectionPoint injectionPoint, InsnList insnList, Collection collection) {
        if (injectionPoint instanceof ContextualInjectionPoint) {
            return ((ContextualInjectionPoint) injectionPoint).find(this.info.getContext().getTargetMethod(methodNode), collection);
        }
        return injectionPoint.find(methodNode.desc, insnList, collection);
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected void sanityCheck(Target target, List list) {
        super.sanityCheck(target, list);
        int ordinal = this.discriminator.getOrdinal();
        if (ordinal < -1) {
            throw new InvalidInjectionException(this.info, "Invalid ordinal " + ordinal + " specified in " + this);
        }
        if (this.discriminator.getIndex() == 0 && !target.isStatic) {
            throw new InvalidInjectionException(this.info, "Invalid index 0 specified in non-static variable modifier " + this);
        }
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        if (injectionNode.isReplaced()) {
            throw new InvalidInjectionException(this.info, "Variable modifier target for " + this + " was removed by another injector");
        }
        Context context = new Context(this.returnType, this.discriminator.isArgsOnly(), target, injectionNode.getCurrentTarget());
        if (this.discriminator.printLVT()) {
            printLocals(target, context);
        }
        checkTargetForNode(target, injectionNode, InjectionPoint.RestrictTargetLevel.ALLOW_ALL);
        Injector.InjectorData injectorData = new Injector.InjectorData(target, InjectionInfo.DEFAULT_PREFIX, false);
        validateParams(injectorData, this.returnType, this.returnType);
        Target.Extension extensionExtendStack = target.extendStack();
        try {
            int iFindLocal = this.discriminator.findLocal(context);
            if (iFindLocal > -1) {
                inject(context, injectorData, extensionExtendStack, iFindLocal);
            }
            extensionExtendStack.apply();
            target.insns.insertBefore(context.node, context.insns);
        } catch (InvalidImplicitDiscriminatorException e) {
            if (this.discriminator.printLVT()) {
                this.info.addCallbackInvocation(this.methodNode);
                return;
            }
            throw new InvalidInjectionException(this.info, "Implicit variable modifier injection failed in " + this, e);
        }
    }

    private void printLocals(Target target, Context context) {
        new SignaturePrinter(this.info.getMethodName(), this.returnType, this.methodArgs, new String[]{"var"}).setModifiers(this.methodNode);
        new PrettyPrinter().kvWidth(20).m75kv("Target Class", this.classNode.name.replace('/', '.')).m75kv("Target Method", context.target.method.name).m75kv("Callback Name", this.info.getMethodName()).m75kv("Capture Type", SignaturePrinter.getTypeName(this.returnType, false)).m74kv("Instruction", "[%d] %s %s", new Object[]{Integer.valueOf(target.insns.indexOf(context.node)), context.node.getClass().getSimpleName(), Bytecode.getOpcodeName(context.node.getOpcode())}).m76hr().m75kv("Match mode", this.discriminator.isImplicit(context) ? "IMPLICIT (match single)" : "EXPLICIT (match by criteria)").m75kv("Match ordinal", this.discriminator.getOrdinal() < 0 ? "any" : Integer.valueOf(this.discriminator.getOrdinal())).m75kv("Match index", this.discriminator.getIndex() < context.baseArgIndex ? "any" : Integer.valueOf(this.discriminator.getIndex())).m75kv("Match name(s)", this.discriminator.hasNames() ? this.discriminator.getNames() : "any").m75kv("Args only", Boolean.valueOf(this.discriminator.isArgsOnly())).m76hr().add((PrettyPrinter.IPrettyPrintable) context).print(System.err);
    }

    private void inject(Context context, Injector.InjectorData injectorData, Target.Extension extension, int i) {
        if (!this.isStatic) {
            context.insns.add(new VarInsnNode(25, 0));
            extension.add();
        }
        context.insns.add(new VarInsnNode(this.returnType.getOpcode(21), i));
        extension.add();
        if (injectorData.captureTargetArgs > 0) {
            pushArgs(injectorData.target.arguments, context.insns, injectorData.target.getArgIndices(), 0, injectorData.captureTargetArgs, extension);
        }
        invokeHandler(context.insns);
        context.insns.add(new VarInsnNode(this.returnType.getOpcode(54), i));
    }
}
