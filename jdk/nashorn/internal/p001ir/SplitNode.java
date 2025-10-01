package jdk.nashorn.internal.p001ir;

import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import jdk.nashorn.internal.codegen.CompileUnit;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/SplitNode.class */
public class SplitNode extends LexicalContextStatement implements CompileUnitHolder {
    private static final long serialVersionUID = 1;
    private final String name;
    private final CompileUnit compileUnit;
    private final Block body;

    @Override // jdk.nashorn.internal.p001ir.LexicalContextStatement, jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    public SplitNode(String str, Block block, CompileUnit compileUnit) {
        super(block.getFirstStatementLineNumber(), block.getToken(), block.getFinish());
        this.name = str;
        this.body = block;
        this.compileUnit = compileUnit;
    }

    private SplitNode(SplitNode splitNode, Block block, CompileUnit compileUnit) {
        super(splitNode);
        this.name = splitNode.name;
        this.body = block;
        this.compileUnit = compileUnit;
    }

    public Block getBody() {
        return this.body;
    }

    private SplitNode setBody(LexicalContext lexicalContext, Block block) {
        if (this.body == block) {
            return this;
        }
        return (SplitNode) Node.replaceInLexicalContext(lexicalContext, this, new SplitNode(this, block, this.compileUnit));
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterSplitNode(this)) {
            return nodeVisitor.leaveSplitNode(setBody(lexicalContext, (Block) this.body.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append("<split>(");
        sb.append(this.compileUnit.getClass().getSimpleName());
        sb.append(") ");
        this.body.toString(sb, z);
    }

    public String getName() {
        return this.name;
    }

    @Override // jdk.nashorn.internal.p001ir.CompileUnitHolder
    public CompileUnit getCompileUnit() {
        return this.compileUnit;
    }

    public SplitNode setCompileUnit(LexicalContext lexicalContext, CompileUnit compileUnit) {
        if (this.compileUnit == compileUnit) {
            return this;
        }
        return (SplitNode) Node.replaceInLexicalContext(lexicalContext, this, new SplitNode(this, this.body, compileUnit));
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws NotSerializableException {
        throw new NotSerializableException(getClass().getName());
    }
}
