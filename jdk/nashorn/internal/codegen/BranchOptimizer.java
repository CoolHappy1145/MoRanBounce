package jdk.nashorn.internal.codegen;

import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.JoinPredecessorExpression;
import jdk.nashorn.internal.p001ir.LocalVariableConversion;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.parser.TokenType;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/BranchOptimizer.class */
final class BranchOptimizer {
    private final CodeGenerator codegen;
    private final MethodEmitter method;

    BranchOptimizer(CodeGenerator codeGenerator, MethodEmitter methodEmitter) {
        this.codegen = codeGenerator;
        this.method = methodEmitter;
    }

    void execute(Expression expression, Label label, boolean z) {
        branchOptimizer(expression, label, z);
    }

    private void branchOptimizer(UnaryNode unaryNode, Label label, boolean z) {
        if (unaryNode.isTokenType(TokenType.NOT)) {
            branchOptimizer(unaryNode.getExpression(), label, !z);
        } else {
            loadTestAndJump(unaryNode, label, z);
        }
    }

    private void branchOptimizer(BinaryNode binaryNode, Label label, boolean z) {
        Expression expressionLhs = binaryNode.lhs();
        Expression expressionRhs = binaryNode.rhs();
        switch (C00651.$SwitchMap$jdk$nashorn$internal$parser$TokenType[binaryNode.tokenType().ordinal()]) {
            case 1:
                if (z) {
                    Label label2 = new Label("skip");
                    optimizeLogicalOperand(expressionLhs, label2, false, false);
                    optimizeLogicalOperand(expressionRhs, label, true, true);
                    this.method.label(label2);
                    break;
                } else {
                    optimizeLogicalOperand(expressionLhs, label, false, false);
                    optimizeLogicalOperand(expressionRhs, label, false, true);
                    break;
                }
            case 2:
                if (z) {
                    optimizeLogicalOperand(expressionLhs, label, true, false);
                    optimizeLogicalOperand(expressionRhs, label, true, true);
                    break;
                } else {
                    Label label3 = new Label("skip");
                    optimizeLogicalOperand(expressionLhs, label3, true, false);
                    optimizeLogicalOperand(expressionRhs, label, false, true);
                    this.method.label(label3);
                    break;
                }
            case 3:
            case 4:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(z ? Condition.EQ : Condition.NE, true, label);
                break;
            case 5:
            case 6:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(z ? Condition.NE : Condition.EQ, true, label);
                break;
            case 7:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(z ? Condition.GE : Condition.LT, false, label);
                break;
            case 8:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(z ? Condition.GT : Condition.LE, false, label);
                break;
            case 9:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(z ? Condition.LE : Condition.GT, true, label);
                break;
            case 10:
                this.codegen.loadComparisonOperands(binaryNode);
                this.method.conditionalJump(z ? Condition.LT : Condition.GE, true, label);
                break;
            default:
                loadTestAndJump(binaryNode, label, z);
                break;
        }
    }

    /* renamed from: jdk.nashorn.internal.codegen.BranchOptimizer$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/BranchOptimizer$1.class */
    static /* synthetic */ class C00651 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.AND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f45OR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f42EQ.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.EQ_STRICT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f39NE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NE_STRICT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f44GE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f43GT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f41LE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f40LT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    private void optimizeLogicalOperand(Expression expression, Label label, boolean z, boolean z2) {
        JoinPredecessorExpression joinPredecessorExpression = (JoinPredecessorExpression) expression;
        if (LocalVariableConversion.hasLiveConversion(joinPredecessorExpression)) {
            Label label2 = new Label("after");
            branchOptimizer(joinPredecessorExpression.getExpression(), label2, !z);
            this.method.beforeJoinPoint(joinPredecessorExpression);
            this.method._goto(label);
            this.method.label(label2);
            if (z2) {
                this.method.beforeJoinPoint(joinPredecessorExpression);
                return;
            }
            return;
        }
        branchOptimizer(joinPredecessorExpression.getExpression(), label, z);
    }

    private void branchOptimizer(Expression expression, Label label, boolean z) {
        if (expression instanceof BinaryNode) {
            branchOptimizer((BinaryNode) expression, label, z);
        } else if (expression instanceof UnaryNode) {
            branchOptimizer((UnaryNode) expression, label, z);
        } else {
            loadTestAndJump(expression, label, z);
        }
    }

    private void loadTestAndJump(Expression expression, Label label, boolean z) {
        this.codegen.loadExpressionAsBoolean(expression);
        if (z) {
            this.method.ifne(label);
        } else {
            this.method.ifeq(label);
        }
    }
}
