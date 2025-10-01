package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/LexicalContextExpression.class */
abstract class LexicalContextExpression extends Expression implements LexicalContextNode {
    private static final long serialVersionUID = 1;

    LexicalContextExpression(LexicalContextExpression lexicalContextExpression) {
        super(lexicalContextExpression);
    }

    LexicalContextExpression(long j, int i, int i2) {
        super(j, i, i2);
    }

    LexicalContextExpression(long j, int i) {
        super(j, i);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return LexicalContextNode.Acceptor.accept(this, nodeVisitor);
    }
}
