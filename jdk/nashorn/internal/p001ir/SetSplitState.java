package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.runtime.Scope;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/SetSplitState.class */
public final class SetSplitState extends Statement {
    private static final long serialVersionUID = 1;
    private final int state;

    public SetSplitState(int i, int i2) {
        super(i2, 0L, 0);
        this.state = i;
    }

    public int getState() {
        return this.state;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.enterSetSplitState(this) ? nodeVisitor.leaveSetSplitState(this) : this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append(CompilerConstants.SCOPE.symbolName()).append('.').append(Scope.SET_SPLIT_STATE.name()).append('(').append(this.state).append(");");
    }
}
