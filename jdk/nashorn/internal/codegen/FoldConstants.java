package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockStatement;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.EmptyNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.TernaryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;

@Logger(name = "fold")
/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/FoldConstants.class */
final class FoldConstants extends SimpleNodeVisitor implements Loggable {
    private final DebugLogger log;

    FoldConstants(Compiler compiler) {
        this.log = initLogger(compiler.getContext());
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return context.getLogger(getClass());
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveUnaryNode(UnaryNode unaryNode) {
        LiteralNode literalNodeEval = new UnaryNodeConstantEvaluator(unaryNode).eval();
        if (literalNodeEval != null) {
            this.log.info(new Object[]{"Unary constant folded ", unaryNode, " to ", literalNodeEval});
            return literalNodeEval;
        }
        return unaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBinaryNode(BinaryNode binaryNode) {
        LiteralNode literalNodeEval = new BinaryNodeConstantEvaluator(binaryNode).eval();
        if (literalNodeEval != null) {
            this.log.info(new Object[]{"Binary constant folded ", binaryNode, " to ", literalNodeEval});
            return literalNodeEval;
        }
        return binaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIfNode(IfNode ifNode) {
        Expression test = ifNode.getTest();
        if (test instanceof LiteralNode.PrimitiveLiteralNode) {
            boolean zIsTrue = ((LiteralNode.PrimitiveLiteralNode) test).isTrue();
            Block pass = zIsTrue ? ifNode.getPass() : ifNode.getFail();
            Block fail = zIsTrue ? ifNode.getFail() : ifNode.getPass();
            ArrayList arrayList = new ArrayList();
            if (pass != null) {
                arrayList.addAll(pass.getStatements());
            }
            if (fail != null) {
                extractVarNodesFromDeadCode(fail, arrayList);
            }
            if (arrayList.isEmpty()) {
                return new EmptyNode(ifNode);
            }
            return BlockStatement.createReplacement(ifNode, ifNode.getFinish(), arrayList);
        }
        return ifNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveTernaryNode(TernaryNode ternaryNode) {
        Expression test = ternaryNode.getTest();
        if (test instanceof LiteralNode.PrimitiveLiteralNode) {
            return (((LiteralNode.PrimitiveLiteralNode) test).isTrue() ? ternaryNode.getTrueExpression() : ternaryNode.getFalseExpression()).getExpression();
        }
        return ternaryNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveSwitchNode(SwitchNode switchNode) {
        return switchNode.setUniqueInteger(this.f30lc, isUniqueIntegerSwitchNode(switchNode));
    }

    private static boolean isUniqueIntegerSwitchNode(SwitchNode switchNode) {
        HashSet hashSet = new HashSet();
        Iterator it = switchNode.getCases().iterator();
        while (it.hasNext()) {
            Expression test = ((CaseNode) it.next()).getTest();
            if (test != null && !isUniqueIntegerLiteral(test, hashSet)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isUniqueIntegerLiteral(Expression expression, Set set) {
        if (expression instanceof LiteralNode) {
            Object value = ((LiteralNode) expression).getValue();
            if (value instanceof Integer) {
                return set.add((Integer) value);
            }
            return false;
        }
        return false;
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/FoldConstants$ConstantEvaluator.class */
    static abstract class ConstantEvaluator {
        protected Node parent;
        protected final long token;
        protected final int finish;

        protected abstract LiteralNode eval();

        protected ConstantEvaluator(Node node) {
            this.parent = node;
            this.token = node.getToken();
            this.finish = node.getFinish();
        }
    }

    static void extractVarNodesFromDeadCode(Node node, List list) {
        node.accept(new SimpleNodeVisitor(list) { // from class: jdk.nashorn.internal.codegen.FoldConstants.1
            final List val$statements;

            {
                this.val$statements = list;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterVarNode(VarNode varNode) {
                this.val$statements.add(varNode.setInit(null));
                return false;
            }
        });
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/FoldConstants$UnaryNodeConstantEvaluator.class */
    private static class UnaryNodeConstantEvaluator extends ConstantEvaluator {
        UnaryNodeConstantEvaluator(UnaryNode unaryNode) {
            super(unaryNode);
        }

        @Override // jdk.nashorn.internal.codegen.FoldConstants.ConstantEvaluator
        protected LiteralNode eval() {
            LiteralNode literalNodeNewInstance;
            Expression expression = ((UnaryNode) this.parent).getExpression();
            if (!(expression instanceof LiteralNode) || (expression instanceof LiteralNode.ArrayLiteralNode)) {
                return null;
            }
            LiteralNode literalNode = (LiteralNode) expression;
            Type type = literalNode.getType();
            boolean z = type.isInteger() || type.isBoolean();
            switch (C01242.$SwitchMap$jdk$nashorn$internal$parser$TokenType[((UnaryNode) this.parent).tokenType().ordinal()]) {
                case 1:
                    if (z) {
                        literalNodeNewInstance = LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(literalNode.getInt32()));
                        break;
                    } else if (type.isLong()) {
                        literalNodeNewInstance = LiteralNode.newInstance(this.token, this.finish, Long.valueOf(literalNode.getLong()));
                        break;
                    } else {
                        literalNodeNewInstance = LiteralNode.newInstance(this.token, this.finish, Double.valueOf(literalNode.getNumber()));
                        break;
                    }
                case 2:
                    if (z && literalNode.getInt32() != 0) {
                        literalNodeNewInstance = LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(-literalNode.getInt32()));
                        break;
                    } else if (type.isLong() && literalNode.getLong() != 0) {
                        literalNodeNewInstance = LiteralNode.newInstance(this.token, this.finish, Long.valueOf(-literalNode.getLong()));
                        break;
                    } else {
                        literalNodeNewInstance = LiteralNode.newInstance(this.token, this.finish, Double.valueOf(-literalNode.getNumber()));
                        break;
                    }
                    break;
                case 3:
                    literalNodeNewInstance = LiteralNode.newInstance(this.token, this.finish, !literalNode.getBoolean());
                    break;
                case 4:
                    literalNodeNewInstance = LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(literalNode.getInt32() ^ (-1)));
                    break;
                default:
                    return null;
            }
            return literalNodeNewInstance;
        }
    }

    /* renamed from: jdk.nashorn.internal.codegen.FoldConstants$2 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/FoldConstants$2.class */
    static /* synthetic */ class C01242 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.ADD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SUB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NOT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_NOT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DIV.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.MUL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.MOD.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SHR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SAR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.SHL.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_XOR.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_AND.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.BIT_OR.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f44GE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f41LE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f43GT.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f40LT.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f39NE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NE_STRICT.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f42EQ.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.EQ_STRICT.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/FoldConstants$BinaryNodeConstantEvaluator.class */
    private static class BinaryNodeConstantEvaluator extends ConstantEvaluator {
        static final boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !FoldConstants.class.desiredAssertionStatus();
        }

        BinaryNodeConstantEvaluator(BinaryNode binaryNode) {
            super(binaryNode);
        }

        @Override // jdk.nashorn.internal.codegen.FoldConstants.ConstantEvaluator
        protected LiteralNode eval() {
            LiteralNode literalNodeReduceTwoLiterals = reduceTwoLiterals();
            if (literalNodeReduceTwoLiterals != null) {
                return literalNodeReduceTwoLiterals;
            }
            if (0 == 0) {
                return null;
            }
            return null;
        }

        private LiteralNode reduceTwoLiterals() {
            double number;
            if (!(((BinaryNode) this.parent).lhs() instanceof LiteralNode) || !(((BinaryNode) this.parent).rhs() instanceof LiteralNode)) {
                return null;
            }
            LiteralNode literalNode = (LiteralNode) ((BinaryNode) this.parent).lhs();
            LiteralNode literalNode2 = (LiteralNode) ((BinaryNode) this.parent).rhs();
            if ((literalNode instanceof LiteralNode.ArrayLiteralNode) || (literalNode2 instanceof LiteralNode.ArrayLiteralNode)) {
                return null;
            }
            boolean zIsInteger = Type.widest(literalNode.getType(), literalNode2.getType()).isInteger();
            switch (C01242.$SwitchMap$jdk$nashorn$internal$parser$TokenType[((BinaryNode) this.parent).tokenType().ordinal()]) {
                case 1:
                    if (!literalNode.isString() && !literalNode2.isNumeric()) {
                        return null;
                    }
                    if (literalNode2.isString() || literalNode2.isNumeric()) {
                        Object objADD = ScriptRuntime.ADD(literalNode.getObject(), literalNode2.getObject());
                        if (objADD instanceof Number) {
                            number = ((Number) objADD).doubleValue();
                            break;
                        } else {
                            if ($assertionsDisabled || (objADD instanceof CharSequence)) {
                                return LiteralNode.newInstance(this.token, this.finish, objADD.toString());
                            }
                            throw new AssertionError(objADD + " was not a CharSequence, it was a " + objADD.getClass());
                        }
                    } else {
                        return null;
                    }
                    break;
                case 2:
                    number = literalNode.getNumber() - literalNode2.getNumber();
                    break;
                case 3:
                case 4:
                default:
                    return null;
                case 5:
                    number = literalNode.getNumber() / literalNode2.getNumber();
                    break;
                case 6:
                    number = literalNode.getNumber() * literalNode2.getNumber();
                    break;
                case 7:
                    number = literalNode.getNumber() % literalNode2.getNumber();
                    break;
                case 8:
                    return LiteralNode.newInstance(this.token, this.finish, JSType.toNarrowestNumber((literalNode.getInt32() >>> literalNode2.getInt32()) & JSType.MAX_UINT));
                case 9:
                    return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(literalNode.getInt32() >> literalNode2.getInt32()));
                case 10:
                    return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(literalNode.getInt32() << literalNode2.getInt32()));
                case 11:
                    return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(literalNode.getInt32() ^ literalNode2.getInt32()));
                case 12:
                    return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(literalNode.getInt32() & literalNode2.getInt32()));
                case CharacterType.ALNUM /* 13 */:
                    return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf(literalNode.getInt32() | literalNode2.getInt32()));
                case 14:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m19GE(literalNode.getObject(), literalNode2.getObject()));
                case OPCode.EXACTN_IC /* 15 */:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m18LE(literalNode.getObject(), literalNode2.getObject()));
                case 16:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m17GT(literalNode.getObject(), literalNode2.getObject()));
                case OPCode.CCLASS_MB /* 17 */:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m16LT(literalNode.getObject(), literalNode2.getObject()));
                case OPCode.CCLASS_MIX /* 18 */:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m14NE(literalNode.getObject(), literalNode2.getObject()));
                case OPCode.CCLASS_NOT /* 19 */:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.NE_STRICT(literalNode.getObject(), literalNode2.getObject()));
                case 20:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.m13EQ(literalNode.getObject(), literalNode2.getObject()));
                case OPCode.CCLASS_MIX_NOT /* 21 */:
                    return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.EQ_STRICT(literalNode.getObject(), literalNode2.getObject()));
            }
            if (zIsInteger & JSType.isStrictlyRepresentableAsInt(number)) {
                return LiteralNode.newInstance(this.token, this.finish, Integer.valueOf((int) number));
            }
            return LiteralNode.newInstance(this.token, this.finish, Double.valueOf(number));
        }
    }
}
