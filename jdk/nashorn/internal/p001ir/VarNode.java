package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/VarNode.class */
public final class VarNode extends Statement implements Assignment {
    private static final long serialVersionUID = 1;
    private final IdentNode name;
    private final Expression init;
    private final int flags;
    public static final int IS_LET = 1;
    public static final int IS_CONST = 2;
    public static final int IS_LAST_FUNCTION_DECLARATION = 4;

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Node setAssignmentDest(Expression expression) {
        return setAssignmentDest((IdentNode) expression);
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Expression getAssignmentDest() {
        return getAssignmentDest();
    }

    public VarNode(int i, long j, int i2, IdentNode identNode, Expression expression) {
        this(i, j, i2, identNode, expression, 0);
    }

    private VarNode(VarNode varNode, IdentNode identNode, Expression expression, int i) {
        super(varNode);
        this.name = expression == null ? identNode : identNode.setIsInitializedHere();
        this.init = expression;
        this.flags = i;
    }

    public VarNode(int i, long j, int i2, IdentNode identNode, Expression expression, int i3) {
        super(i, j, i2);
        this.name = expression == null ? identNode : identNode.setIsInitializedHere();
        this.init = expression;
        this.flags = i3;
    }

    public boolean isAssignment() {
        return hasInit();
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public IdentNode getAssignmentDest() {
        if (isAssignment()) {
            return this.name;
        }
        return null;
    }

    public VarNode setAssignmentDest(IdentNode identNode) {
        return setName(identNode);
    }

    @Override // jdk.nashorn.internal.p001ir.Assignment
    public Expression getAssignmentSource() {
        if (isAssignment()) {
            return getInit();
        }
        return null;
    }

    public boolean isBlockScoped() {
        return getFlag(1) || getFlag(2);
    }

    public boolean isLet() {
        return getFlag(1);
    }

    public boolean isConst() {
        return getFlag(2);
    }

    public int getSymbolFlags() {
        if (isLet()) {
            return 18;
        }
        if (isConst()) {
            return 34;
        }
        return 2;
    }

    public boolean hasInit() {
        return this.init != null;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        VarNode varNode;
        if (nodeVisitor.enterVarNode(this)) {
            Expression expression = this.init == null ? null : (Expression) this.init.accept(nodeVisitor);
            IdentNode identNode = (IdentNode) this.name.accept(nodeVisitor);
            if (this.name != identNode || this.init != expression) {
                varNode = new VarNode(this, identNode, expression, this.flags);
            } else {
                varNode = this;
            }
            return nodeVisitor.leaveVarNode(varNode);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append(tokenType().getName()).append(' ');
        this.name.toString(sb, z);
        if (this.init != null) {
            sb.append(" = ");
            this.init.toString(sb, z);
        }
    }

    public Expression getInit() {
        return this.init;
    }

    public VarNode setInit(Expression expression) {
        if (this.init == expression) {
            return this;
        }
        return new VarNode(this, this.name, expression, this.flags);
    }

    public IdentNode getName() {
        return this.name;
    }

    public VarNode setName(IdentNode identNode) {
        if (this.name == identNode) {
            return this;
        }
        return new VarNode(this, identNode, this.init, this.flags);
    }

    private VarNode setFlags(int i) {
        if (this.flags == i) {
            return this;
        }
        return new VarNode(this, this.name, this.init, i);
    }

    public boolean getFlag(int i) {
        return (this.flags & i) == i;
    }

    public VarNode setFlag(int i) {
        return setFlags(this.flags | i);
    }

    public boolean isFunctionDeclaration() {
        return (this.init instanceof FunctionNode) && ((FunctionNode) this.init).isDeclared();
    }
}
