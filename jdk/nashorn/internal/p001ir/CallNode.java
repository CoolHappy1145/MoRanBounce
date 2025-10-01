package jdk.nashorn.internal.p001ir;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Ignore;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/CallNode.class */
public final class CallNode extends LexicalContextExpression implements Optimistic {
    private static final long serialVersionUID = 1;
    private final Expression function;
    private final List args;
    private static final int IS_NEW = 1;
    private static final int IS_APPLY_TO_CALL = 2;
    private final int flags;
    private final int lineNumber;
    private final int programPoint;
    private final Type optimisticType;

    @Ignore
    private final EvalArgs evalArgs;

    @Override // jdk.nashorn.internal.p001ir.LexicalContextExpression, jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Optimistic setProgramPoint(int i) {
        return setProgramPoint(i);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/CallNode$EvalArgs.class */
    public static class EvalArgs implements Serializable {
        private static final long serialVersionUID = 1;
        private final List args;
        private final String location;

        public EvalArgs(List list, String str) {
            this.args = list;
            this.location = str;
        }

        public List getArgs() {
            return Collections.unmodifiableList(this.args);
        }

        private EvalArgs setArgs(List list) {
            if (this.args == list) {
                return this;
            }
            return new EvalArgs(list, this.location);
        }

        public String getLocation() {
            return this.location;
        }
    }

    public CallNode(int i, long j, int i2, Expression expression, List list, boolean z) {
        super(j, i2);
        this.function = expression;
        this.args = list;
        this.flags = z ? 1 : 0;
        this.evalArgs = null;
        this.lineNumber = i;
        this.programPoint = -1;
        this.optimisticType = null;
    }

    private CallNode(CallNode callNode, Expression expression, List list, int i, Type type, EvalArgs evalArgs, int i2) {
        super(callNode);
        this.lineNumber = callNode.lineNumber;
        this.function = expression;
        this.args = list;
        this.flags = i;
        this.evalArgs = evalArgs;
        this.programPoint = i2;
        this.optimisticType = type;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return this.optimisticType == null ? Type.OBJECT : this.optimisticType;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Optimistic setType(Type type) {
        if (this.optimisticType == type) {
            return this;
        }
        return new CallNode(this, this.function, this.args, this.flags, type, this.evalArgs, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterCallNode(this)) {
            CallNode callNode = (CallNode) nodeVisitor.leaveCallNode(setFunction((Expression) this.function.accept(nodeVisitor)).setArgs(Node.accept(nodeVisitor, this.args)).setEvalArgs(this.evalArgs == null ? null : this.evalArgs.setArgs(Node.accept(nodeVisitor, this.evalArgs.getArgs()))));
            if (this != callNode) {
                return (Node) Node.replaceInLexicalContext(lexicalContext, this, callNode);
            }
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        if (z) {
            optimisticTypeToString(sb);
        }
        StringBuilder sb2 = new StringBuilder();
        this.function.toString(sb2, z);
        if (isApplyToCall()) {
            sb.append(sb2.toString().replace("apply", "[apply => call]"));
        } else {
            sb.append((CharSequence) sb2);
        }
        sb.append('(');
        boolean z2 = true;
        for (Node node : this.args) {
            if (!z2) {
                sb.append(", ");
            } else {
                z2 = false;
            }
            node.toString(sb, z);
        }
        sb.append(')');
    }

    public List getArgs() {
        return Collections.unmodifiableList(this.args);
    }

    public CallNode setArgs(List list) {
        if (this.args == list) {
            return this;
        }
        return new CallNode(this, this.function, list, this.flags, this.optimisticType, this.evalArgs, this.programPoint);
    }

    public EvalArgs getEvalArgs() {
        return this.evalArgs;
    }

    public CallNode setEvalArgs(EvalArgs evalArgs) {
        if (this.evalArgs == evalArgs) {
            return this;
        }
        return new CallNode(this, this.function, this.args, this.flags, this.optimisticType, evalArgs, this.programPoint);
    }

    public boolean isEval() {
        return this.evalArgs != null;
    }

    public boolean isApplyToCall() {
        return (this.flags & 2) != 0;
    }

    public CallNode setIsApplyToCall() {
        return setFlags(this.flags | 2);
    }

    public Expression getFunction() {
        return this.function;
    }

    public CallNode setFunction(Expression expression) {
        if (this.function == expression) {
            return this;
        }
        return new CallNode(this, expression, this.args, this.flags, this.optimisticType, this.evalArgs, this.programPoint);
    }

    public boolean isNew() {
        return (this.flags & 1) != 0;
    }

    private CallNode setFlags(int i) {
        if (this.flags == i) {
            return this;
        }
        return new CallNode(this, this.function, this.args, i, this.optimisticType, this.evalArgs, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public int getProgramPoint() {
        return this.programPoint;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public CallNode setProgramPoint(int i) {
        if (this.programPoint == i) {
            return this;
        }
        return new CallNode(this, this.function, this.args, this.flags, this.optimisticType, this.evalArgs, i);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostOptimisticType() {
        return Type.INT;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostPessimisticType() {
        return Type.OBJECT;
    }
}
