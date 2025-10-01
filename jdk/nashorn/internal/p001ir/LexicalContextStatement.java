package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/LexicalContextStatement.class */
abstract class LexicalContextStatement extends Statement implements LexicalContextNode {
    private static final long serialVersionUID = 1;

    protected LexicalContextStatement(int i, long j, int i2) {
        super(i, j, i2);
    }

    protected LexicalContextStatement(LexicalContextStatement lexicalContextStatement) {
        super(lexicalContextStatement);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return LexicalContextNode.Acceptor.accept(this, nodeVisitor);
    }
}
