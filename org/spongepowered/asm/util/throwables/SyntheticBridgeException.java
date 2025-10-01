package org.spongepowered.asm.util.throwables;

import java.util.ListIterator;
import jdk.internal.dynalink.CallSiteDescriptor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.PrettyPrinter;

/* loaded from: L-out.jar:org/spongepowered/asm/util/throwables/SyntheticBridgeException.class */
public class SyntheticBridgeException extends MixinException {
    private static final long serialVersionUID = 1;
    private final Problem problem;
    private final String name;
    private final String desc;
    private final int index;

    /* renamed from: a */
    private final AbstractInsnNode f225a;

    /* renamed from: b */
    private final AbstractInsnNode f226b;

    /* loaded from: L-out.jar:org/spongepowered/asm/util/throwables/SyntheticBridgeException$Problem.class */
    public enum Problem {
        BAD_INSN("Conflicting opcodes %4$s and %5$s at offset %3$d in synthetic bridge method %1$s%2$s"),
        BAD_LOAD("Conflicting variable access at offset %3$d in synthetic bridge method %1$s%2$s"),
        BAD_CAST("Conflicting type cast at offset %3$d in synthetic bridge method %1$s%2$s"),
        BAD_INVOKE_NAME("Conflicting synthetic bridge target method name in synthetic bridge method %1$s%2$s Existing:%6$s Incoming:%7$s"),
        BAD_INVOKE_DESC("Conflicting synthetic bridge target method descriptor in synthetic bridge method %1$s%2$s Existing:%8$s Incoming:%9$s"),
        BAD_LENGTH("Mismatched bridge method length for synthetic bridge method %1$s%2$s unexpected extra opcode at offset %3$d");

        private final String message;

        Problem(String str) {
            this.message = str;
        }

        String getMessage(String str, String str2, int i, AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
            return String.format(this.message, str, str2, Integer.valueOf(i), Bytecode.getOpcodeName(abstractInsnNode), Bytecode.getOpcodeName(abstractInsnNode), getInsnName(abstractInsnNode), getInsnName(abstractInsnNode2), getInsnDesc(abstractInsnNode), getInsnDesc(abstractInsnNode2));
        }

        private static String getInsnName(AbstractInsnNode abstractInsnNode) {
            return abstractInsnNode instanceof MethodInsnNode ? ((MethodInsnNode) abstractInsnNode).name : "";
        }

        private static String getInsnDesc(AbstractInsnNode abstractInsnNode) {
            return abstractInsnNode instanceof MethodInsnNode ? ((MethodInsnNode) abstractInsnNode).desc : "";
        }
    }

    public SyntheticBridgeException(Problem problem, String str, String str2, int i, AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
        super(problem.getMessage(str, str2, i, abstractInsnNode, abstractInsnNode2));
        this.problem = problem;
        this.name = str;
        this.desc = str2;
        this.index = i;
        this.f225a = abstractInsnNode;
        this.f226b = abstractInsnNode2;
    }

    public void printAnalysis(IMixinContext iMixinContext, MethodNode methodNode, MethodNode methodNode2) {
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        prettyPrinter.addWrapped(100, getMessage(), new Object[0]).m76hr();
        prettyPrinter.add().m75kv("Method", this.name + this.desc).m75kv("Problem Type", this.problem).add().m76hr();
        String str = (String) Annotations.getValue(Annotations.getVisible(methodNode, MixinMerged.class), MixinLaunchPlugin.NAME);
        printMethod(prettyPrinter.add("Existing method").add().m75kv("Owner", str != null ? str : iMixinContext.getTargetClassRef().replace('/', '.')).add(), methodNode).m76hr();
        printMethod(prettyPrinter.add("Incoming method").add().m75kv("Owner", iMixinContext.getClassRef().replace('/', '.')).add(), methodNode2).m76hr();
        printProblem(prettyPrinter, iMixinContext, methodNode, methodNode2).print(System.err);
    }

    private PrettyPrinter printMethod(PrettyPrinter prettyPrinter, MethodNode methodNode) {
        int i = 0;
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            prettyPrinter.m75kv(i == this.index ? ">>>>" : "", Bytecode.describeNode((AbstractInsnNode) it.next()));
            i++;
        }
        return prettyPrinter.add();
    }

    private PrettyPrinter printProblem(PrettyPrinter prettyPrinter, IMixinContext iMixinContext, MethodNode methodNode, MethodNode methodNode2) {
        Type objectType = Type.getObjectType(iMixinContext.getTargetClassRef());
        prettyPrinter.add("Analysis").add();
        switch (C05761.f227x62485f2e[this.problem.ordinal()]) {
            case 1:
                prettyPrinter.add("The bridge methods are not compatible because they contain incompatible opcodes");
                prettyPrinter.add("at index " + this.index + CallSiteDescriptor.TOKEN_DELIMITER).add();
                prettyPrinter.m75kv("Existing opcode: %s", Bytecode.getOpcodeName(this.f225a));
                prettyPrinter.m75kv("Incoming opcode: %s", Bytecode.getOpcodeName(this.f226b)).add();
                prettyPrinter.add("This implies that the bridge methods are from different interfaces. This problem");
                prettyPrinter.add("may not be resolvable without changing the base interfaces.").add();
                break;
            case 2:
                prettyPrinter.add("The bridge methods are not compatible because they contain different variables at");
                prettyPrinter.add("opcode index " + this.index + ".").add();
                ListIterator it = methodNode.instructions.iterator();
                ListIterator it2 = methodNode2.instructions.iterator();
                Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc);
                Type[] argumentTypes2 = Type.getArgumentTypes(methodNode2.desc);
                int i = 0;
                while (it.hasNext() && it2.hasNext()) {
                    VarInsnNode varInsnNode = (AbstractInsnNode) it.next();
                    VarInsnNode varInsnNode2 = (AbstractInsnNode) it2.next();
                    if ((varInsnNode instanceof VarInsnNode) && (varInsnNode2 instanceof VarInsnNode)) {
                        VarInsnNode varInsnNode3 = varInsnNode;
                        VarInsnNode varInsnNode4 = varInsnNode2;
                        Type type = varInsnNode3.var > 0 ? argumentTypes[varInsnNode3.var - 1] : objectType;
                        Type type2 = varInsnNode4.var > 0 ? argumentTypes2[varInsnNode4.var - 1] : objectType;
                        prettyPrinter.m74kv("Target " + i, "%8s %-2d %s", new Object[]{Bytecode.getOpcodeName((AbstractInsnNode) varInsnNode3), Integer.valueOf(varInsnNode3.var), type});
                        prettyPrinter.m74kv("Incoming " + i, "%8s %-2d %s", new Object[]{Bytecode.getOpcodeName((AbstractInsnNode) varInsnNode4), Integer.valueOf(varInsnNode4.var), type2});
                        if (type.equals(type2)) {
                            prettyPrinter.m74kv("", "Types match: %s", new Object[]{type});
                        } else if (type.getSort() != type2.getSort()) {
                            prettyPrinter.m75kv("", "Types are incompatible");
                        } else if (type.getSort() == 10) {
                            prettyPrinter.m74kv("", "Common supertype: %s", new Object[]{ClassInfo.getCommonSuperClassOrInterface(type, type2)});
                        }
                        prettyPrinter.add();
                    }
                    i++;
                }
                prettyPrinter.add("Since this probably means that the methods come from different interfaces, you");
                prettyPrinter.add("may have a \"multiple inheritance\" problem, it may not be possible to implement");
                prettyPrinter.add("both root interfaces");
                break;
            case 3:
                prettyPrinter.add("Incompatible CHECKCAST encountered at opcode " + this.index + ", this could indicate that the bridge");
                prettyPrinter.add("is casting down for contravariant generic types. It may be possible to coalesce the");
                prettyPrinter.add("bridges by adjusting the types in the target method.").add();
                Type objectType2 = Type.getObjectType(this.f225a.desc);
                Type objectType3 = Type.getObjectType(this.f226b.desc);
                prettyPrinter.m75kv("Target type", objectType2);
                prettyPrinter.m75kv("Incoming type", objectType3);
                prettyPrinter.m75kv("Common supertype", ClassInfo.getCommonSuperClassOrInterface(objectType2, objectType3)).add();
                break;
            case 4:
                prettyPrinter.add("Incompatible invocation targets in synthetic bridge. This is extremely unusual");
                prettyPrinter.add("and implies that a remapping transformer has incorrectly remapped a method. This");
                prettyPrinter.add("is an unrecoverable error.");
                break;
            case 5:
                MethodInsnNode methodInsnNode = this.f225a;
                MethodInsnNode methodInsnNode2 = this.f226b;
                Type[] argumentTypes3 = Type.getArgumentTypes(methodInsnNode.desc);
                Type[] argumentTypes4 = Type.getArgumentTypes(methodInsnNode2.desc);
                if (argumentTypes3.length != argumentTypes4.length) {
                    int length = Type.getArgumentTypes(methodNode.desc).length;
                    String str = argumentTypes3.length == length ? "The TARGET" : argumentTypes4.length == length ? " The INCOMING" : "NEITHER";
                    prettyPrinter.add("Mismatched invocation descriptors in synthetic bridge implies that a remapping");
                    prettyPrinter.add("transformer has incorrectly coalesced a bridge method with a conflicting name.");
                    prettyPrinter.add("Overlapping bridge methods should always have the same number of arguments, yet");
                    prettyPrinter.add("the target method has %d arguments, the incoming method has %d. This is an", new Object[]{Integer.valueOf(argumentTypes3.length), Integer.valueOf(argumentTypes4.length)});
                    prettyPrinter.add("unrecoverable error. %s method has the expected arg count of %d", new Object[]{str, Integer.valueOf(length)});
                    break;
                } else {
                    Type returnType = Type.getReturnType(methodInsnNode.desc);
                    Type returnType2 = Type.getReturnType(methodInsnNode2.desc);
                    prettyPrinter.add("Incompatible invocation descriptors in synthetic bridge implies that generified");
                    prettyPrinter.add("types are incompatible over one or more generic superclasses or interfaces. It may");
                    prettyPrinter.add("be possible to adjust the generic types on implemented members to rectify this");
                    prettyPrinter.add("problem by coalescing the appropriate generic types.").add();
                    printTypeComparison(prettyPrinter, "return type", returnType, returnType2);
                    for (int i2 = 0; i2 < argumentTypes3.length; i2++) {
                        printTypeComparison(prettyPrinter, "arg " + i2, argumentTypes3[i2], argumentTypes4[i2]);
                    }
                    break;
                }
            case 6:
                prettyPrinter.add("Mismatched bridge method length implies the bridge methods are incompatible");
                prettyPrinter.add("and may originate from different superinterfaces. This is an unrecoverable");
                prettyPrinter.add("error.").add();
                break;
        }
        return prettyPrinter;
    }

    /* renamed from: org.spongepowered.asm.util.throwables.SyntheticBridgeException$1 */
    /* loaded from: L-out.jar:org/spongepowered/asm/util/throwables/SyntheticBridgeException$1.class */
    static /* synthetic */ class C05761 {

        /* renamed from: $SwitchMap$org$spongepowered$asm$util$throwables$SyntheticBridgeException$Problem */
        static final int[] f227x62485f2e = new int[Problem.values().length];

        static {
            try {
                f227x62485f2e[Problem.BAD_INSN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f227x62485f2e[Problem.BAD_LOAD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f227x62485f2e[Problem.BAD_CAST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f227x62485f2e[Problem.BAD_INVOKE_NAME.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f227x62485f2e[Problem.BAD_INVOKE_DESC.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f227x62485f2e[Problem.BAD_LENGTH.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private PrettyPrinter printTypeComparison(PrettyPrinter prettyPrinter, String str, Type type, Type type2) {
        prettyPrinter.m74kv("Target " + str, "%s", new Object[]{type});
        prettyPrinter.m74kv("Incoming " + str, "%s", new Object[]{type2});
        if (type.equals(type2)) {
            prettyPrinter.m74kv("Analysis", "Types match: %s", new Object[]{type});
        } else if (type.getSort() != type2.getSort()) {
            prettyPrinter.m75kv("Analysis", "Types are incompatible");
        } else if (type.getSort() == 10) {
            prettyPrinter.m74kv("Analysis", "Common supertype: L%s;", new Object[]{ClassInfo.getCommonSuperClassOrInterface(type, type2)});
        }
        return prettyPrinter.add();
    }
}
