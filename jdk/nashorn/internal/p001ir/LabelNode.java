package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/LabelNode.class */
public final class LabelNode extends LexicalContextStatement implements JoinPredecessor {
    private static final long serialVersionUID = 1;
    private final String labelName;
    private final Block body;
    private final LocalVariableConversion localVariableConversion;

    @Override // jdk.nashorn.internal.p001ir.LexicalContextStatement, jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return setLocalVariableConversion(lexicalContext, localVariableConversion);
    }

    public LabelNode(int i, long j, int i2, String str, Block block) {
        super(i, j, i2);
        this.labelName = str;
        this.body = block;
        this.localVariableConversion = null;
    }

    private LabelNode(LabelNode labelNode, String str, Block block, LocalVariableConversion localVariableConversion) {
        super(labelNode);
        this.labelName = str;
        this.body = block;
        this.localVariableConversion = localVariableConversion;
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return this.body.isTerminal();
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterLabelNode(this)) {
            return nodeVisitor.leaveLabelNode(setBody(lexicalContext, (Block) this.body.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append(this.labelName).append(':');
    }

    public Block getBody() {
        return this.body;
    }

    public LabelNode setBody(LexicalContext lexicalContext, Block block) {
        if (this.body == block) {
            return this;
        }
        return (LabelNode) Node.replaceInLexicalContext(lexicalContext, this, new LabelNode(this, this.labelName, block, this.localVariableConversion));
    }

    public String getLabelName() {
        return this.labelName;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.localVariableConversion;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LabelNode setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        if (this.localVariableConversion == localVariableConversion) {
            return this;
        }
        return (LabelNode) Node.replaceInLexicalContext(lexicalContext, this, new LabelNode(this, this.labelName, this.body, localVariableConversion));
    }
}
