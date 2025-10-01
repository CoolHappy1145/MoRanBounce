package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/AccessNode.class */
public final class AccessNode extends BaseNode {
    private static final long serialVersionUID = 1;
    private final String property;

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

    public AccessNode(long j, int i, Expression expression, String str) {
        super(j, i, expression, false);
        this.property = str;
    }

    private AccessNode(AccessNode accessNode, Expression expression, String str, boolean z, Type type, int i) {
        super(accessNode, expression, z, type, i);
        this.property = str;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterAccessNode(this)) {
            return nodeVisitor.leaveAccessNode(setBase((Expression) this.base.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        boolean zNeedsParens = tokenType().needsParens(getBase().tokenType(), true);
        if (z) {
            optimisticTypeToString(sb);
        }
        if (zNeedsParens) {
            sb.append('(');
        }
        this.base.toString(sb, z);
        if (zNeedsParens) {
            sb.append(')');
        }
        sb.append('.');
        sb.append(this.property);
    }

    public String getProperty() {
        return this.property;
    }

    private AccessNode setBase(Expression expression) {
        if (this.base == expression) {
            return this;
        }
        return new AccessNode(this, expression, this.property, isFunction(), this.type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public AccessNode setType(Type type) {
        if (this.type == type) {
            return this;
        }
        return new AccessNode(this, this.base, this.property, isFunction(), type, this.programPoint);
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public AccessNode setProgramPoint(int i) {
        if (this.programPoint == i) {
            return this;
        }
        return new AccessNode(this, this.base, this.property, isFunction(), this.type, i);
    }

    @Override // jdk.nashorn.internal.p001ir.BaseNode
    public AccessNode setIsFunction() {
        if (isFunction()) {
            return this;
        }
        return new AccessNode(this, this.base, this.property, true, this.type, this.programPoint);
    }
}
