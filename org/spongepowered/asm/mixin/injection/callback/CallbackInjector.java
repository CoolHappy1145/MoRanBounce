package org.spongepowered.asm.mixin.injection.callback;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jdk.internal.dynalink.CallSiteDescriptor;
import kotlin.jvm.internal.ShortCompanionObject;
import kotlin.text.Typography;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/callback/CallbackInjector.class */
public class CallbackInjector extends Injector {
    private final boolean cancellable;
    private final LocalCapture localCapture;
    private final String identifier;
    private final Map ids;
    private int totalInjections;
    private int callbackInfoVar;
    private String lastId;
    private String lastDesc;
    private Target lastTarget;
    private String callbackInfoClass;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/callback/CallbackInjector$Callback.class */
    private class Callback extends InsnList {
        private final MethodNode handler;
        private final AbstractInsnNode head;
        final Target target;
        final InjectionNodes.InjectionNode node;
        final LocalVariableNode[] locals;
        final Type[] localTypes;
        final int frameSize;
        final int extraArgs;
        final boolean canCaptureLocals;
        final boolean isAtReturn;
        final String desc;
        final String descl;
        final String[] argNames;
        Target.Extension ctor;
        Target.Extension invoke;
        private int marshalVar = -1;
        private boolean captureArgs = true;

        Callback(MethodNode handler, Target target, InjectionNodes.InjectionNode node, LocalVariableNode[] locals, boolean captureLocals) {
            this.handler = handler;
            this.target = target;
            this.head = target.insns.getFirst();
            this.node = node;
            this.locals = locals;
            this.localTypes = locals != null ? new Type[locals.length] : null;
            this.frameSize = Bytecode.getFirstNonArgLocalIndex(target.arguments, !target.isStatic);
            List<String> argNames = null;
            if (locals != null) {
                int baseArgIndex = CallbackInjector.this.isStatic() ? 0 : 1;
                argNames = new ArrayList<>();
                for (int l = 0; l <= locals.length; l++) {
                    if (l == this.frameSize) {
                        argNames.add(target.returnType == Type.VOID_TYPE ? "ci" : "cir");
                    }
                    if (l < locals.length && locals[l] != null) {
                        this.localTypes[l] = Type.getType(locals[l].desc);
                        if (l >= baseArgIndex) {
                            argNames.add(CallbackInjector.meltSnowman(l, locals[l].name));
                        }
                    }
                }
            }
            Type[] handlerArgs = Type.getArgumentTypes(this.handler.desc);
            this.extraArgs = Math.max(0, (handlerArgs.length - target.arguments.length) - 1);
            this.argNames = argNames != null ? (String[]) argNames.toArray(new String[argNames.size()]) : null;
            this.canCaptureLocals = captureLocals && locals != null && locals.length > this.frameSize;
            this.isAtReturn = (this.node.getCurrentTarget() instanceof InsnNode) && isValueReturnOpcode(this.node.getCurrentTarget().getOpcode());
            this.desc = target.getCallbackDescriptor(this.localTypes, target.arguments);
            this.descl = target.getCallbackDescriptor(true, this.localTypes, target.arguments, this.frameSize, this.extraArgs);
            this.invoke = target.extendStack();
            this.ctor = target.extendStack();
            this.invoke.add(target.arguments.length);
            if (this.canCaptureLocals) {
                this.invoke.add(this.localTypes.length - this.frameSize);
            }
        }

        private boolean isValueReturnOpcode(int opcode) {
            return opcode >= 172 && opcode < 177;
        }

        String getDescriptor() {
            return this.canCaptureLocals ? this.descl : this.desc;
        }

        String getDescriptorWithAllLocals() {
            return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, ShortCompanionObject.MAX_VALUE);
        }

        String getCallbackInfoConstructorDescriptor() {
            return this.isAtReturn ? CallbackInfo.getConstructorDescriptor(this.target.returnType) : CallbackInfo.getConstructorDescriptor();
        }

        void add(AbstractInsnNode insn, boolean ctorStack, boolean invokeStack) {
            add(insn, ctorStack, invokeStack, false);
        }

        void add(AbstractInsnNode insn, boolean ctorStack, boolean invokeStack, boolean head) {
            if (head) {
                this.target.insns.insertBefore(this.head, insn);
            } else {
                add(insn);
            }
            if (ctorStack) {
                this.ctor.add();
            }
            if (invokeStack) {
                this.invoke.add();
            }
        }

        void inject() {
            this.target.insertBefore(this.node, this);
            this.invoke.apply();
            this.ctor.apply();
        }

        boolean checkDescriptor(String desc) {
            if (getDescriptor().equals(desc)) {
                return true;
            }
            if (this.target.getSimpleCallbackDescriptor().equals(desc) && !this.canCaptureLocals) {
                this.captureArgs = false;
                return true;
            }
            Type[] inTypes = Type.getArgumentTypes(desc);
            Type[] myTypes = Type.getArgumentTypes(this.descl);
            if (inTypes.length != myTypes.length) {
                return false;
            }
            for (int arg = 0; arg < myTypes.length; arg++) {
                Type type = inTypes[arg];
                if (!type.equals(myTypes[arg]) && (type.getSort() == 9 || Annotations.getInvisibleParameter(this.handler, Coerce.class, arg) == null || !Injector.canCoerce(inTypes[arg], myTypes[arg]))) {
                    return false;
                }
            }
            return true;
        }

        boolean captureArgs() {
            return this.captureArgs;
        }

        int marshalVar() {
            if (this.marshalVar < 0) {
                this.marshalVar = this.target.allocateLocal();
            }
            return this.marshalVar;
        }
    }

    public CallbackInjector(InjectionInfo injectionInfo, boolean z, LocalCapture localCapture, String str) {
        super(injectionInfo, "@Inject");
        this.ids = new HashMap();
        this.totalInjections = 0;
        this.callbackInfoVar = -1;
        this.cancellable = z;
        this.localCapture = localCapture;
        this.identifier = str;
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected void sanityCheck(Target target, List list) {
        super.sanityCheck(target, list);
        checkTargetModifiers(target, true);
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected void addTargetNode(Target target, List list, AbstractInsnNode abstractInsnNode, Set set) {
        InjectionNodes.InjectionNode injectionNodeAddInjectionNode = target.addInjectionNode(abstractInsnNode);
        Iterator it = set.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            InjectionPoint injectionPoint = (InjectionPoint) it.next();
            try {
                checkTargetForNode(target, injectionNodeAddInjectionNode, injectionPoint.getTargetRestriction(this.info));
                String id = injectionPoint.getId();
                if (!Strings.isNullOrEmpty(id)) {
                    String str = (String) this.ids.get(Integer.valueOf(injectionNodeAddInjectionNode.getId()));
                    if (str != null && !str.equals(id)) {
                        Injector.logger.warn("Conflicting id for {} insn in {}, found id {} on {}, previously defined as {}", new Object[]{Bytecode.getOpcodeName(abstractInsnNode), target.toString(), id, this.info, str});
                        break;
                    }
                    this.ids.put(Integer.valueOf(injectionNodeAddInjectionNode.getId()), id);
                }
            } catch (InvalidInjectionException e) {
                throw new InvalidInjectionException(this.info, String.format("%s selector %s", injectionPoint, e.getMessage()));
            }
        }
        list.add(injectionNodeAddInjectionNode);
        this.totalInjections++;
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        LocalVariableNode[] localsAt = null;
        if (this.localCapture.isCaptureLocals() || this.localCapture.isPrintLocals()) {
            localsAt = Locals.getLocalsAt(this.classNode, target.method, injectionNode.getCurrentTarget());
            for (int i = 0; i < localsAt.length; i++) {
                if (localsAt[i] != null && localsAt[i].desc != null && localsAt[i].desc.startsWith("Lorg/spongepowered/asm/mixin/injection/callback/")) {
                    localsAt[i] = null;
                }
            }
        }
        inject(new Callback(this.methodNode, target, injectionNode, localsAt, this.localCapture.isCaptureLocals()));
    }

    private void inject(Callback callback) {
        if (this.localCapture.isPrintLocals()) {
            printLocals(callback);
            this.info.addCallbackInvocation(this.methodNode);
            return;
        }
        MethodNode methodNodeGenerateErrorMethod = this.methodNode;
        if (!callback.checkDescriptor(this.methodNode.desc)) {
            if (this.info.getTargets().size() > 1) {
                return;
            }
            if (callback.canCaptureLocals) {
                MethodNode methodNodeFindMethod = Bytecode.findMethod(this.classNode, this.methodNode.name, callback.getDescriptor());
                if (methodNodeFindMethod != null && Annotations.getVisible(methodNodeFindMethod, Surrogate.class) != null) {
                    methodNodeGenerateErrorMethod = methodNodeFindMethod;
                } else {
                    String strGenerateBadLVTMessage = generateBadLVTMessage(callback);
                    switch (C05641.f216xe7c6cf67[this.localCapture.ordinal()]) {
                        case 1:
                            Injector.logger.error("Injection error: {}", new Object[]{strGenerateBadLVTMessage});
                            methodNodeGenerateErrorMethod = generateErrorMethod(callback, "org/spongepowered/asm/mixin/injection/throwables/InjectionError", strGenerateBadLVTMessage);
                            break;
                        case 2:
                            Injector.logger.warn("Injection warning: {}", new Object[]{strGenerateBadLVTMessage});
                            return;
                        default:
                            Injector.logger.error("Critical injection failure: {}", new Object[]{strGenerateBadLVTMessage});
                            throw new InjectionError(strGenerateBadLVTMessage);
                    }
                }
            } else {
                if (callback.checkDescriptor(this.methodNode.desc.replace("Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;", "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;"))) {
                    throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! CallbackInfoReturnable is required!");
                }
                MethodNode methodNodeFindMethod2 = Bytecode.findMethod(this.classNode, this.methodNode.name, callback.getDescriptor());
                if (methodNodeFindMethod2 != null && Annotations.getVisible(methodNodeFindMethod2, Surrogate.class) != null) {
                    methodNodeGenerateErrorMethod = methodNodeFindMethod2;
                } else {
                    throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! Expected " + callback.getDescriptor() + " but found " + this.methodNode.desc);
                }
            }
        }
        dupReturnValue(callback);
        if (this.cancellable || this.totalInjections > 1) {
            createCallbackInfo(callback, true);
        }
        invokeCallback(callback, methodNodeGenerateErrorMethod);
        injectCancellationCode(callback);
        callback.inject();
        InjectionInfo injectionInfo = this.info;
        Target target = callback.target;
    }

    /* renamed from: org.spongepowered.asm.mixin.injection.callback.CallbackInjector$1 */
    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/callback/CallbackInjector$1.class */
    static /* synthetic */ class C05641 {

        /* renamed from: $SwitchMap$org$spongepowered$asm$mixin$injection$callback$LocalCapture */
        static final int[] f216xe7c6cf67 = new int[LocalCapture.values().length];

        static {
            try {
                f216xe7c6cf67[LocalCapture.CAPTURE_FAILEXCEPTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f216xe7c6cf67[LocalCapture.CAPTURE_FAILSOFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private String generateBadLVTMessage(Callback callback) {
        int iIndexOf = callback.target.indexOf(callback.node);
        List listSummariseLocals = summariseLocals(this.methodNode.desc, callback.target.arguments.length + 1);
        List listSummariseLocals2 = summariseLocals(callback.getDescriptor(), callback.frameSize + (callback.target.isStatic ? 1 : 0));
        if (listSummariseLocals.equals(listSummariseLocals2)) {
            return String.format("Invalid descriptor on %s! Expected %s but found %s", this.info, callback.getDescriptor(), this.methodNode.desc);
        }
        return String.format("LVT in %s has incompatible changes at opcode %d in callback %s.\nExpected: %s\n   Found: %s", callback.target, Integer.valueOf(iIndexOf), this, listSummariseLocals, listSummariseLocals2);
    }

    private MethodNode generateErrorMethod(Callback callback, String str, String str2) {
        MethodNode methodNodeAddMethod = this.info.addMethod(this.methodNode.access, this.methodNode.name + "$missing", callback.getDescriptor());
        methodNodeAddMethod.maxLocals = Bytecode.getFirstNonArgLocalIndex(Type.getArgumentTypes(callback.getDescriptor()), !this.isStatic);
        methodNodeAddMethod.maxStack = 3;
        InsnList insnList = methodNodeAddMethod.instructions;
        insnList.add(new TypeInsnNode(Typography.rightGuillemete, str));
        insnList.add(new InsnNode(89));
        insnList.add(new LdcInsnNode(str2));
        insnList.add(new MethodInsnNode(Typography.middleDot, str, Constants.CTOR, "(Ljava/lang/String;)V", false));
        insnList.add(new InsnNode(191));
        return methodNodeAddMethod;
    }

    private void printLocals(Callback callback) {
        Type[] argumentTypes = Type.getArgumentTypes(callback.getDescriptorWithAllLocals());
        SignaturePrinter signaturePrinter = new SignaturePrinter(callback.target.method, callback.argNames);
        SignaturePrinter signaturePrinter2 = new SignaturePrinter(this.info.getMethodName(), callback.target.returnType, argumentTypes, callback.argNames);
        signaturePrinter2.setModifiers(this.methodNode);
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        prettyPrinter.m75kv("Target Class", this.classNode.name.replace('/', '.'));
        prettyPrinter.m75kv("Target Method", signaturePrinter);
        prettyPrinter.m75kv("Target Max LOCALS", Integer.valueOf(callback.target.getMaxLocals()));
        prettyPrinter.m75kv("Initial Frame Size", Integer.valueOf(callback.frameSize));
        prettyPrinter.m75kv("Callback Name", this.info.getMethodName());
        prettyPrinter.m74kv("Instruction", "%s %s", new Object[]{callback.node.getClass().getSimpleName(), Bytecode.getOpcodeName(callback.node.getCurrentTarget().getOpcode())});
        prettyPrinter.m76hr();
        if (callback.locals.length > callback.frameSize) {
            prettyPrinter.add("  %s  %20s  %s", new Object[]{"LOCAL", "TYPE", "NAME"});
            int i = 0;
            while (i < callback.locals.length) {
                String str = i == callback.frameSize ? ">" : " ";
                if (callback.locals[i] != null) {
                    Object[] objArr = new Object[5];
                    objArr[0] = str;
                    objArr[1] = Integer.valueOf(i);
                    objArr[2] = SignaturePrinter.getTypeName(callback.localTypes[i], false);
                    objArr[3] = meltSnowman(i, callback.locals[i].name);
                    objArr[4] = i >= callback.frameSize ? "<capture>" : "";
                    prettyPrinter.add("%s [%3d]  %20s  %-50s %s", objArr);
                } else {
                    boolean z = i > 0 && callback.localTypes[i - 1] != null && callback.localTypes[i - 1].getSize() > 1;
                    Object[] objArr2 = new Object[3];
                    objArr2[0] = str;
                    objArr2[1] = Integer.valueOf(i);
                    objArr2[2] = z ? "<top>" : "-";
                    prettyPrinter.add("%s [%3d]  %20s", objArr2);
                }
                i++;
            }
            prettyPrinter.m76hr();
        }
        prettyPrinter.add().add("/**").add(" * Expected callback signature").add(" * /");
        prettyPrinter.add("%s {", new Object[]{signaturePrinter2});
        prettyPrinter.add("    // Method body").add("}").add().print(System.err);
    }

    private void createCallbackInfo(Callback callback, boolean z) {
        if (callback.target != this.lastTarget) {
            this.lastId = null;
            this.lastDesc = null;
        }
        this.lastTarget = callback.target;
        String identifier = getIdentifier(callback);
        String callbackInfoConstructorDescriptor = callback.getCallbackInfoConstructorDescriptor();
        if (identifier.equals(this.lastId) && callbackInfoConstructorDescriptor.equals(this.lastDesc) && !callback.isAtReturn && !this.cancellable) {
            return;
        }
        instanceCallbackInfo(callback, identifier, callbackInfoConstructorDescriptor, z);
    }

    private void loadOrCreateCallbackInfo(Callback callback) {
        if (this.cancellable || this.totalInjections > 1) {
            callback.add(new VarInsnNode(25, this.callbackInfoVar), false, true);
        } else {
            createCallbackInfo(callback, false);
        }
    }

    private void dupReturnValue(Callback callback) {
        if (!callback.isAtReturn) {
            return;
        }
        callback.add(new InsnNode(callback.target.returnType.getSize() == 1 ? 89 : 92));
        callback.add(new VarInsnNode(callback.target.returnType.getOpcode(54), callback.marshalVar()));
    }

    protected void instanceCallbackInfo(Callback callback, String str, String str2, boolean z) {
        this.lastId = str;
        this.lastDesc = str2;
        this.callbackInfoVar = callback.marshalVar();
        this.callbackInfoClass = callback.target.getCallbackInfoClass();
        boolean z2 = z && this.totalInjections > 1 && !callback.isAtReturn && !this.cancellable;
        callback.add(new TypeInsnNode(Typography.rightGuillemete, this.callbackInfoClass), true, !z, z2);
        callback.add(new InsnNode(89), true, true, z2);
        callback.add(new LdcInsnNode(str), true, !z, z2);
        callback.add(new InsnNode(this.cancellable ? 4 : 3), true, !z, z2);
        if (callback.isAtReturn) {
            callback.add(new VarInsnNode(callback.target.returnType.getOpcode(21), callback.marshalVar()), true, !z);
            callback.add(new MethodInsnNode(Typography.middleDot, this.callbackInfoClass, Constants.CTOR, str2, false));
        } else {
            callback.add(new MethodInsnNode(Typography.middleDot, this.callbackInfoClass, Constants.CTOR, str2, false), false, false, z2);
        }
        if (z) {
            callback.target.addLocalVariable(this.callbackInfoVar, "callbackInfo" + this.callbackInfoVar, "L" + this.callbackInfoClass + ";");
            callback.add(new VarInsnNode(58, this.callbackInfoVar), false, false, z2);
        }
    }

    private void invokeCallback(Callback callback, MethodNode methodNode) {
        if (!this.isStatic) {
            callback.add(new VarInsnNode(25, 0), false, true);
        }
        if (callback.captureArgs()) {
            Bytecode.loadArgs(callback.target.arguments, callback, this.isStatic ? 0 : 1, -1);
        }
        loadOrCreateCallbackInfo(callback);
        if (callback.canCaptureLocals) {
            Locals.loadLocals(callback.localTypes, callback, callback.frameSize, callback.extraArgs);
        }
        invokeHandler(callback, methodNode);
    }

    private String getIdentifier(Callback callback) {
        String str = Strings.isNullOrEmpty(this.identifier) ? callback.target.method.name : this.identifier;
        String str2 = (String) this.ids.get(Integer.valueOf(callback.node.getId()));
        return str + (Strings.isNullOrEmpty(str2) ? "" : CallSiteDescriptor.TOKEN_DELIMITER + str2);
    }

    protected void injectCancellationCode(Callback callback) {
        if (!this.cancellable) {
            return;
        }
        callback.add(new VarInsnNode(25, this.callbackInfoVar));
        callback.add(new MethodInsnNode(Typography.paragraph, this.callbackInfoClass, "isCancelled", "()Z", false));
        LabelNode labelNode = new LabelNode();
        callback.add(new JumpInsnNode(153, labelNode));
        injectReturnCode(callback);
        callback.add(labelNode);
    }

    protected void injectReturnCode(Callback callback) {
        if (callback.target.returnType.equals(Type.VOID_TYPE)) {
            callback.add(new InsnNode(Typography.plusMinus));
            return;
        }
        callback.add(new VarInsnNode(25, callback.marshalVar()));
        callback.add(new MethodInsnNode(Typography.paragraph, this.callbackInfoClass, CallbackInfoReturnable.getReturnAccessor(callback.target.returnType), CallbackInfoReturnable.getReturnDescriptor(callback.target.returnType), false));
        if (callback.target.returnType.getSort() >= 9) {
            callback.add(new TypeInsnNode(192, callback.target.returnType.getInternalName()));
        }
        callback.add(new InsnNode(callback.target.returnType.getOpcode(172)));
    }

    protected boolean isStatic() {
        return this.isStatic;
    }

    private static List summariseLocals(String str, int i) {
        return summariseLocals(Type.getArgumentTypes(str), i);
    }

    private static List summariseLocals(Type[] typeArr, int i) {
        ArrayList arrayList = new ArrayList();
        if (typeArr != null) {
            while (i < typeArr.length) {
                if (typeArr[i] != null) {
                    arrayList.add(typeArr[i].toString());
                }
                i++;
            }
        }
        return arrayList;
    }

    static String meltSnowman(int i, String str) {
        return (str == null || '\u2603' != str.charAt(0)) ? str : "var" + i;
    }
}
