package org.spongepowered.asm.mixin.injection.code;

import com.google.common.collect.ObjectArrays;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/code/Injector.class */
public abstract class Injector {
    protected static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    protected InjectionInfo info;
    protected final String annotationType;
    protected final ClassNode classNode;
    protected final MethodNode methodNode;
    protected final Type[] methodArgs;
    protected final Type returnType;
    protected final boolean isStatic;

    protected abstract void inject(Target target, InjectionNodes.InjectionNode injectionNode);

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/code/Injector$TargetNode.class */
    public static final class TargetNode {
        final AbstractInsnNode insn;
        final Set nominators = new HashSet();

        TargetNode(AbstractInsnNode abstractInsnNode) {
            this.insn = abstractInsnNode;
        }

        public AbstractInsnNode getNode() {
            return this.insn;
        }

        public Set getNominators() {
            return Collections.unmodifiableSet(this.nominators);
        }

        public boolean equals(Object obj) {
            return obj != null && obj.getClass() == TargetNode.class && ((TargetNode) obj).insn == this.insn;
        }

        public int hashCode() {
            return this.insn.hashCode();
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/code/Injector$InjectorData.class */
    public static class InjectorData {
        public final Target target;
        public String description;
        public boolean allowCoerceArgs;
        public int captureTargetArgs;
        public boolean coerceReturnType;

        public InjectorData(Target target) {
            this(target, InjectionInfo.DEFAULT_PREFIX);
        }

        public InjectorData(Target target, String str) {
            this(target, str, true);
        }

        public InjectorData(Target target, String str, boolean z) {
            this.captureTargetArgs = 0;
            this.coerceReturnType = false;
            this.target = target;
            this.description = str;
            this.allowCoerceArgs = z;
        }

        public String toString() {
            return this.description;
        }
    }

    public Injector(InjectionInfo info, String annotationType) {
        this.info = info;
        this.annotationType = annotationType;
        this.classNode = info.getClassNode();
        this.methodNode = info.getMethod();
        this.methodArgs = Type.getArgumentTypes(this.methodNode.desc);
        this.returnType = Type.getReturnType(this.methodNode.desc);
        this.isStatic = Bytecode.isStatic(this.methodNode);
    }

    public String toString() {
        return String.format("%s::%s", this.classNode.name, this.info.getMethodName());
    }

    public final List<InjectionNodes.InjectionNode> find(InjectorTarget injectorTarget, List<InjectionPoint> injectionPoints) {
        sanityCheck(injectorTarget.getTarget(), injectionPoints);
        List<InjectionNodes.InjectionNode> myNodes = new ArrayList<>();
        for (TargetNode node : findTargetNodes(injectorTarget, injectionPoints)) {
            addTargetNode(injectorTarget.getTarget(), myNodes, node.insn, node.nominators);
        }
        return myNodes;
    }

    protected void addTargetNode(Target target, List<InjectionNodes.InjectionNode> myNodes, AbstractInsnNode node, Set<InjectionPoint> nominators) {
        myNodes.add(target.addInjectionNode(node));
    }

    public final void inject(Target target, List<InjectionNodes.InjectionNode> nodes) {
        for (InjectionNodes.InjectionNode node : nodes) {
            if (node.isRemoved()) {
                if (this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                    logger.warn("Target node for {} was removed by a previous injector in {}", new Object[]{this.info, target});
                }
            } else {
                inject(target, node);
            }
        }
        Iterator<InjectionNodes.InjectionNode> it = nodes.iterator();
        while (it.hasNext()) {
            postInject(target, it.next());
        }
    }

    private Collection<TargetNode> findTargetNodes(InjectorTarget injectorTarget, List<InjectionPoint> injectionPoints) {
        IMixinContext mixin = this.info.getContext();
        MethodNode method = injectorTarget.getMethod();
        Map<Integer, TargetNode> targetNodes = new TreeMap<>();
        Collection<AbstractInsnNode> nodes = new ArrayList<>(32);
        for (InjectionPoint injectionPoint : injectionPoints) {
            nodes.clear();
            if (injectorTarget.isMerged() && !mixin.getClassName().equals(injectorTarget.getMergedBy()) && !injectionPoint.checkPriority(injectorTarget.getMergedPriority(), mixin.getPriority())) {
                throw new InvalidInjectionException(this.info, String.format("%s on %s with priority %d cannot inject into %s merged by %s with priority %d", injectionPoint, this, Integer.valueOf(mixin.getPriority()), injectorTarget, injectorTarget.getMergedBy(), Integer.valueOf(injectorTarget.getMergedPriority())));
            }
            if (findTargetNodes(method, injectionPoint, injectorTarget.getSlice(injectionPoint), nodes)) {
                for (AbstractInsnNode insn : nodes) {
                    Integer key = Integer.valueOf(method.instructions.indexOf(insn));
                    TargetNode targetNode = targetNodes.get(key);
                    if (targetNode == null) {
                        targetNode = new TargetNode(insn);
                        targetNodes.put(key, targetNode);
                    }
                    targetNode.nominators.add(injectionPoint);
                }
            }
        }
        return targetNodes.values();
    }

    protected boolean findTargetNodes(MethodNode into, InjectionPoint injectionPoint, InsnList insns, Collection<AbstractInsnNode> nodes) {
        return injectionPoint.find(into.desc, insns, nodes);
    }

    protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
        if (target.classNode != this.classNode) {
            throw new InvalidInjectionException(this.info, "Target class does not match injector class in " + this);
        }
    }

    protected final void checkTargetModifiers(Target target, boolean exactMatch) {
        if (exactMatch && target.isStatic != this.isStatic) {
            throw new InvalidInjectionException(this.info, String.format("'static' modifier of handler method does not match target in %s", this));
        }
        if (!exactMatch && !this.isStatic && target.isStatic) {
            throw new InvalidInjectionException(this.info, String.format("non-static callback method %s targets a static method which is not supported", this));
        }
    }

    protected void checkTargetForNode(Target target, InjectionNodes.InjectionNode node, InjectionPoint.RestrictTargetLevel targetLevel) {
        if (target.isCtor) {
            if (targetLevel == InjectionPoint.RestrictTargetLevel.METHODS_ONLY) {
                throw new InvalidInjectionException(this.info, String.format("Found %s targetting a constructor in injector %s", this.annotationType, this));
            }
            Bytecode.DelegateInitialiser superCall = target.findDelegateInitNode();
            if (!superCall.isPresent) {
                throw new InjectionError(String.format("Delegate constructor lookup failed for %s target on %s", this.annotationType, this.info));
            }
            int superCallIndex = target.indexOf((AbstractInsnNode) superCall.insn);
            int targetIndex = target.indexOf(node.getCurrentTarget());
            if (targetIndex <= superCallIndex) {
                if (targetLevel == InjectionPoint.RestrictTargetLevel.CONSTRUCTORS_AFTER_DELEGATE) {
                    throw new InvalidInjectionException(this.info, String.format("Found %s targetting a constructor before %s() in injector %s", this.annotationType, superCall, this));
                }
                if (!this.isStatic) {
                    throw new InvalidInjectionException(this.info, String.format("%s handler before %s() invocation must be static in injector %s", this.annotationType, superCall, this));
                }
                return;
            }
        }
        checkTargetModifiers(target, true);
    }

    protected void postInject(Target target, InjectionNodes.InjectionNode node) {
    }

    protected AbstractInsnNode invokeHandler(InsnList insns) {
        return invokeHandler(insns, this.methodNode);
    }

    protected AbstractInsnNode invokeHandler(InsnList insns, MethodNode handler) {
        boolean isPrivate = (handler.access & 2) != 0;
        int invokeOpcode = this.isStatic ? SyslogAppender.LOG_LOCAL7 : isPrivate ? Typography.middleDot : Typography.paragraph;
        MethodInsnNode insn = new MethodInsnNode(invokeOpcode, this.classNode.name, handler.name, handler.desc, false);
        insns.add(insn);
        this.info.addCallbackInvocation(handler);
        return insn;
    }

    protected AbstractInsnNode invokeHandlerWithArgs(Type[] args, InsnList insns, int[] argMap) {
        return invokeHandlerWithArgs(args, insns, argMap, 0, args.length);
    }

    protected AbstractInsnNode invokeHandlerWithArgs(Type[] args, InsnList insns, int[] argMap, int startArg, int endArg) {
        if (!this.isStatic) {
            insns.add(new VarInsnNode(25, 0));
        }
        pushArgs(args, insns, argMap, startArg, endArg);
        return invokeHandler(insns);
    }

    protected int[] storeArgs(Target target, Type[] args, InsnList insns, int start) {
        int[] argMap = target.generateArgMap(args, start);
        storeArgs(args, insns, argMap, start, args.length);
        return argMap;
    }

    protected void storeArgs(Type[] args, InsnList insns, int[] argMap, int start, int end) {
        for (int arg = end - 1; arg >= start; arg--) {
            insns.add(new VarInsnNode(args[arg].getOpcode(54), argMap[arg]));
        }
    }

    protected void pushArgs(Type[] args, InsnList insns, int[] argMap, int start, int end) {
        pushArgs(args, insns, argMap, start, end, null);
    }

    protected void pushArgs(Type[] args, InsnList insns, int[] argMap, int start, int end, Target.Extension extension) {
        for (int arg = start; arg < end && arg < args.length; arg++) {
            insns.add(new VarInsnNode(args[arg].getOpcode(21), argMap[arg]));
            if (extension != null) {
                extension.add(args[arg].getSize());
            }
        }
    }

    protected final void validateParams(InjectorData injector, Type returnType, Type... args) {
        String strGenerateDescriptor;
        String description = String.format("%s %s method %s from %s", this.annotationType, injector, this, this.info.getContext());
        int argIndex = 0;
        try {
            injector.coerceReturnType = checkCoerce(-1, returnType, description, injector.allowCoerceArgs);
            for (Type arg : args) {
                if (arg != null) {
                    checkCoerce(argIndex, arg, description, injector.allowCoerceArgs);
                    argIndex++;
                }
            }
            if (argIndex == this.methodArgs.length) {
                return;
            }
            int targetArg = 0;
            while (targetArg < injector.target.arguments.length && argIndex < this.methodArgs.length) {
                checkCoerce(argIndex, injector.target.arguments[targetArg], description, true);
                injector.captureTargetArgs++;
                targetArg++;
                argIndex++;
            }
            if (argIndex < this.methodArgs.length) {
                Type[] extraArgs = (Type[]) Arrays.copyOfRange(this.methodArgs, argIndex, this.methodArgs.length);
                throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found %d unexpected additional method arguments: %s", description, Integer.valueOf(this.methodArgs.length - argIndex), new SignaturePrinter(extraArgs).getFormattedArgs()));
            }
        } catch (InvalidInjectionException ex) {
            if (this.methodArgs.length > args.length) {
                strGenerateDescriptor = Bytecode.generateDescriptor(returnType, (Type[]) ObjectArrays.concat(args, injector.target.arguments, Type.class));
            } else {
                strGenerateDescriptor = Bytecode.generateDescriptor(returnType, args);
            }
            String expected = strGenerateDescriptor;
            throw new InvalidInjectionException(this.info, String.format("%s. Handler signature: %s Expected signature: %s", ex.getMessage(), this.methodNode.desc, expected));
        }
    }

    protected final boolean checkCoerce(int index, Type toType, String description, boolean allowCoercion) {
        Type fromType = index < 0 ? this.returnType : this.methodArgs[index];
        if (index >= this.methodArgs.length) {
            throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Not enough arguments: expected argument type %s at index %d", description, SignaturePrinter.getTypeName(toType), Integer.valueOf(index)));
        }
        AnnotationNode coerce = Annotations.getInvisibleParameter(this.methodNode, Coerce.class, index);
        boolean isReturn = index < 0;
        String argType = isReturn ? "return" : "argument";
        Object argIndex = isReturn ? "" : " at index " + index;
        if (fromType.equals(toType)) {
            if (coerce != null && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                logger.info("Possibly-redundant @Coerce on {} {} type{}, {} is identical to {}", new Object[]{description, argType, argIndex, SignaturePrinter.getTypeName(toType), SignaturePrinter.getTypeName(fromType)});
                return false;
            }
            return false;
        }
        if (coerce == null || !allowCoercion) {
            String coerceWarning = coerce != null ? ". @Coerce not allowed here" : "";
            throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found unexpected %s type %s%s, expected %s%s", description, argType, SignaturePrinter.getTypeName(fromType), argIndex, SignaturePrinter.getTypeName(toType), coerceWarning));
        }
        boolean canCoerce = canCoerce(fromType, toType);
        if (!canCoerce) {
            throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Cannot @Coerce %s type %s%s to %s", description, argType, SignaturePrinter.getTypeName(toType), argIndex, SignaturePrinter.getTypeName(fromType)));
        }
        return true;
    }

    protected void throwException(InsnList insns, String exceptionType, String message) {
        insns.add(new TypeInsnNode(Typography.rightGuillemete, exceptionType));
        insns.add(new InsnNode(89));
        insns.add(new LdcInsnNode(message));
        insns.add(new MethodInsnNode(Typography.middleDot, exceptionType, Constants.CTOR, "(Ljava/lang/String;)V", false));
        insns.add(new InsnNode(191));
    }

    public static boolean canCoerce(Type from, Type to) {
        int fromSort = from.getSort();
        int toSort = to.getSort();
        if (fromSort >= 9 && toSort >= 9 && fromSort == toSort) {
            if (fromSort == 9 && from.getDimensions() != to.getDimensions()) {
                return false;
            }
            return canCoerce(ClassInfo.forType(from, ClassInfo.TypeLookup.ELEMENT_TYPE), ClassInfo.forType(to, ClassInfo.TypeLookup.ELEMENT_TYPE));
        }
        return canCoerce(from.getDescriptor(), to.getDescriptor());
    }

    public static boolean canCoerce(String from, String to) {
        if (from.length() > 1 || to.length() > 1) {
            return false;
        }
        return canCoerce(from.charAt(0), to.charAt(0));
    }

    public static boolean canCoerce(char from, char to) {
        return to == 'I' && "IBSCZ".indexOf(from) > -1;
    }

    private static boolean canCoerce(ClassInfo from, ClassInfo to) {
        return (from == null || to == null || (to != from && !to.hasSuperClass(from, ClassInfo.Traversal.ALL, true))) ? false : true;
    }
}
