package jdk.nashorn.internal.p001ir.visitor;

import jdk.nashorn.internal.p001ir.LexicalContext;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/visitor/SimpleNodeVisitor.class */
public abstract class SimpleNodeVisitor extends NodeVisitor {
    public SimpleNodeVisitor() {
        super(new LexicalContext());
    }
}
