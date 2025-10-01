package jdk.nashorn.internal.p001ir;

import java.util.List;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/BlockStatement.class */
public class BlockStatement extends Statement {
    private static final long serialVersionUID = 1;
    private final Block block;

    public BlockStatement(Block block) {
        this(block.getFirstStatementLineNumber(), block);
    }

    public BlockStatement(int i, Block block) {
        super(i, block.getToken(), block.getFinish());
        this.block = block;
    }

    private BlockStatement(BlockStatement blockStatement, Block block) {
        super(blockStatement);
        this.block = block;
    }

    public static BlockStatement createReplacement(Statement statement, List list) {
        return createReplacement(statement, statement.getFinish(), list);
    }

    public static BlockStatement createReplacement(Statement statement, int i, List list) {
        return new BlockStatement(statement.getLineNumber(), new Block(statement.getToken(), i, list));
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return this.block.isTerminal();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterBlockStatement(this)) {
            return nodeVisitor.leaveBlockStatement(setBlock((Block) this.block.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        this.block.toString(sb, z);
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockStatement setBlock(Block block) {
        if (this.block == block) {
            return this;
        }
        return new BlockStatement(this, block);
    }
}
