package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/LexicalContextNode.class */
public interface LexicalContextNode {
    Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor);

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LexicalContextNode$Acceptor.class */
    public static class Acceptor {
        static Node accept(LexicalContextNode lexicalContextNode, NodeVisitor nodeVisitor) {
            LexicalContext lexicalContext = nodeVisitor.getLexicalContext();
            lexicalContext.push(lexicalContextNode);
            return lexicalContext.pop(lexicalContextNode.accept(lexicalContext, nodeVisitor));
        }
    }
}
