package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/PropertyNode.class */
public final class PropertyNode extends Node {
    private static final long serialVersionUID = 1;
    private final PropertyKey key;
    private final Expression value;
    private final FunctionNode getter;
    private final FunctionNode setter;

    public PropertyNode(long j, int i, PropertyKey propertyKey, Expression expression, FunctionNode functionNode, FunctionNode functionNode2) {
        super(j, i);
        this.key = propertyKey;
        this.value = expression;
        this.getter = functionNode;
        this.setter = functionNode2;
    }

    private PropertyNode(PropertyNode propertyNode, PropertyKey propertyKey, Expression expression, FunctionNode functionNode, FunctionNode functionNode2) {
        super(propertyNode);
        this.key = propertyKey;
        this.value = expression;
        this.getter = functionNode;
        this.setter = functionNode2;
    }

    public String getKeyName() {
        return this.key.getPropertyName();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterPropertyNode(this)) {
            return nodeVisitor.leavePropertyNode(setKey((PropertyKey) ((Node) this.key).accept(nodeVisitor)).setValue(this.value == null ? null : (Expression) this.value.accept(nodeVisitor)).setGetter(this.getter == null ? null : (FunctionNode) this.getter.accept(nodeVisitor)).setSetter(this.setter == null ? null : (FunctionNode) this.setter.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        if ((this.value instanceof FunctionNode) && ((FunctionNode) this.value).getIdent() != null) {
            this.value.toString(sb);
        }
        if (this.value != null) {
            ((Node) this.key).toString(sb, z);
            sb.append(": ");
            this.value.toString(sb, z);
        }
        if (this.getter != null) {
            sb.append(' ');
            this.getter.toString(sb, z);
        }
        if (this.setter != null) {
            sb.append(' ');
            this.setter.toString(sb, z);
        }
    }

    public FunctionNode getGetter() {
        return this.getter;
    }

    public PropertyNode setGetter(FunctionNode functionNode) {
        if (this.getter == functionNode) {
            return this;
        }
        return new PropertyNode(this, this.key, this.value, functionNode, this.setter);
    }

    public Expression getKey() {
        return (Expression) this.key;
    }

    private PropertyNode setKey(PropertyKey propertyKey) {
        if (this.key == propertyKey) {
            return this;
        }
        return new PropertyNode(this, propertyKey, this.value, this.getter, this.setter);
    }

    public FunctionNode getSetter() {
        return this.setter;
    }

    public PropertyNode setSetter(FunctionNode functionNode) {
        if (this.setter == functionNode) {
            return this;
        }
        return new PropertyNode(this, this.key, this.value, this.getter, functionNode);
    }

    public Expression getValue() {
        return this.value;
    }

    public PropertyNode setValue(Expression expression) {
        if (this.value == expression) {
            return this;
        }
        return new PropertyNode(this, this.key, expression, this.getter, this.setter);
    }
}
