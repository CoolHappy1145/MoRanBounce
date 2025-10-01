package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/IndexNode.class */
public final class IndexNode extends BaseNode {
    private static final long serialVersionUID = 1;
    private final Expression index;

    @Override // jdk.nashorn.internal.p001ir.BaseNode
    public BaseNode setIsFunction() {
        return setIsFunction();
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Optimistic setType(Type type) {
        return setType(type);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Optimistic setProgramPoint(int i) {
        return setProgramPoint(i);
    }

    public IndexNode(long j, int i, Expression expression, Expression expression2) {
        super(j, i, expression, false);
        this.index = expression2;
    }

    private IndexNode(IndexNode indexNode, Expression expression, Expression expression2, boolean z, Type type, int i) {
        super(indexNode, expression, z, type, i);
        this.index = expression2;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterIndexNode(this)) {
            return nodeVisitor.leaveIndexNode(setBase((Expression) this.base.accept(nodeVisitor)).setIndex((Expression) this.index.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        boolean zNeedsParens = tokenType().needsParens(this.base.tokenType(), true);
        if (zNeedsParens) {
            sb.append('(');
        }
        if (z) {
            optimisticTypeToString(sb);
        }
        this.base.toString(sb, z);
        if (zNeedsParens) {
            sb.append(')');
        }
        sb.append('[');
        this.index.toString(sb, z);
        sb.append(']');
    }

    public Expression getIndex() {
        return this.index;
    }

    private IndexNode setBase(Expression expression) {
        if (this.base == expression) {
            return this;
        }
        return new IndexNode(this, expression, this.index, isFunction(), this.type, this.programPoint);
    }

    public IndexNode setIndex(Expression expression) {
        if (this.index == expression) {
            return this;
        }
        return new IndexNode(this, this.base, expression, isFunction(), this.type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public IndexNode setType(Type type) {
        if (this.type == type) {
            return this;
        }
        return new IndexNode(this, this.base, this.index, isFunction(), type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.BaseNode
    public IndexNode setIsFunction() {
        if (isFunction()) {
            return this;
        }
        return new IndexNode(this, this.base, this.index, true, this.type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public IndexNode setProgramPoint(int i) {
        if (this.programPoint == i) {
            return this;
        }
        return new IndexNode(this, this.base, this.index, isFunction(), this.type, i);
    }
}
