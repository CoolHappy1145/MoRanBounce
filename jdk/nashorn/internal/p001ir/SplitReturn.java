package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/SplitReturn.class */
public final class SplitReturn extends Statement {
    private static final long serialVersionUID = 1;
    public static final SplitReturn INSTANCE = new SplitReturn();

    private SplitReturn() {
        super(-1, 0L, 0);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return nodeVisitor.enterSplitReturn(this) ? nodeVisitor.leaveSplitReturn(this) : this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append(":splitreturn;");
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
