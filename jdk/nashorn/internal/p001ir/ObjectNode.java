package jdk.nashorn.internal.p001ir;

import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/ObjectNode.class */
public final class ObjectNode extends Expression implements LexicalContextNode, Splittable {
    private static final long serialVersionUID = 1;
    private final List elements;
    private final List splitRanges;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ObjectNode.class.desiredAssertionStatus();
    }

    public ObjectNode(long j, int i, List list) {
        super(j, i);
        this.elements = list;
        this.splitRanges = null;
        if (!$assertionsDisabled && !(list instanceof RandomAccess)) {
            throw new AssertionError("Splitting requires random access lists");
        }
    }

    private ObjectNode(ObjectNode objectNode, List list, List list2) {
        super(objectNode);
        this.elements = list;
        this.splitRanges = list2;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return LexicalContextNode.Acceptor.accept(this, nodeVisitor);
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterObjectNode(this)) {
            return nodeVisitor.leaveObjectNode(setElements(lexicalContext, Node.accept(nodeVisitor, this.elements)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return Type.OBJECT;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append('{');
        if (!this.elements.isEmpty()) {
            sb.append(' ');
            boolean z2 = true;
            for (Node node : this.elements) {
                if (!z2) {
                    sb.append(", ");
                }
                z2 = false;
                node.toString(sb, z);
            }
            sb.append(' ');
        }
        sb.append('}');
    }

    public List getElements() {
        return Collections.unmodifiableList(this.elements);
    }

    private ObjectNode setElements(LexicalContext lexicalContext, List list) {
        if (this.elements == list) {
            return this;
        }
        return (ObjectNode) Node.replaceInLexicalContext(lexicalContext, this, new ObjectNode(this, list, this.splitRanges));
    }

    public ObjectNode setSplitRanges(LexicalContext lexicalContext, List list) {
        if (this.splitRanges == list) {
            return this;
        }
        return (ObjectNode) Node.replaceInLexicalContext(lexicalContext, this, new ObjectNode(this, this.elements, list));
    }

    @Override // jdk.nashorn.internal.p001ir.Splittable
    public List getSplitRanges() {
        if (this.splitRanges == null) {
            return null;
        }
        return Collections.unmodifiableList(this.splitRanges);
    }
}
