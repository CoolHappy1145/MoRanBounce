package jdk.nashorn.internal.p001ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/TryNode.class */
public final class TryNode extends LexicalContextStatement implements JoinPredecessor {
    private static final long serialVersionUID = 1;
    private final Block body;
    private final List catchBlocks;
    private final Block finallyBody;
    private final List inlinedFinallies;
    private final Symbol exception;
    private final LocalVariableConversion conversion;
    static final boolean $assertionsDisabled;

    @Override // jdk.nashorn.internal.p001ir.LexicalContextStatement, jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    static {
        $assertionsDisabled = !TryNode.class.desiredAssertionStatus();
    }

    public TryNode(int i, long j, int i2, Block block, List list, Block block2) {
        super(i, j, i2);
        this.body = block;
        this.catchBlocks = list;
        this.finallyBody = block2;
        this.conversion = null;
        this.inlinedFinallies = Collections.emptyList();
        this.exception = null;
    }

    private TryNode(TryNode tryNode, Block block, List list, Block block2, LocalVariableConversion localVariableConversion, List list2, Symbol symbol) {
        super(tryNode);
        this.body = block;
        this.catchBlocks = list;
        this.finallyBody = block2;
        this.conversion = localVariableConversion;
        this.inlinedFinallies = list2;
        this.exception = symbol;
    }

    public Node ensureUniqueLabels(LexicalContext lexicalContext) {
        return new TryNode(this, this.body, this.catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, this.exception);
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        if (this.body.isTerminal()) {
            Iterator it = getCatchBlocks().iterator();
            while (it.hasNext()) {
                if (!((Block) it.next()).isTerminal()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterTryNode(this)) {
            return nodeVisitor.leaveTryNode(setBody(lexicalContext, (Block) this.body.accept(nodeVisitor)).setFinallyBody(lexicalContext, this.finallyBody == null ? null : (Block) this.finallyBody.accept(nodeVisitor)).setCatchBlocks(lexicalContext, Node.accept(nodeVisitor, this.catchBlocks)).setInlinedFinallies(lexicalContext, Node.accept(nodeVisitor, this.inlinedFinallies)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append("try ");
    }

    public Block getBody() {
        return this.body;
    }

    public TryNode setBody(LexicalContext lexicalContext, Block block) {
        if (this.body == block) {
            return this;
        }
        return (TryNode) Node.replaceInLexicalContext(lexicalContext, this, new TryNode(this, block, this.catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, this.exception));
    }

    public List getCatches() {
        ArrayList arrayList = new ArrayList(this.catchBlocks.size());
        Iterator it = this.catchBlocks.iterator();
        while (it.hasNext()) {
            arrayList.add(getCatchNodeFromBlock((Block) it.next()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static CatchNode getCatchNodeFromBlock(Block block) {
        return (CatchNode) block.getStatements().get(0);
    }

    public List getCatchBlocks() {
        return Collections.unmodifiableList(this.catchBlocks);
    }

    public TryNode setCatchBlocks(LexicalContext lexicalContext, List list) {
        if (this.catchBlocks == list) {
            return this;
        }
        return (TryNode) Node.replaceInLexicalContext(lexicalContext, this, new TryNode(this, this.body, list, this.finallyBody, this.conversion, this.inlinedFinallies, this.exception));
    }

    public Symbol getException() {
        return this.exception;
    }

    public TryNode setException(LexicalContext lexicalContext, Symbol symbol) {
        if (this.exception == symbol) {
            return this;
        }
        return (TryNode) Node.replaceInLexicalContext(lexicalContext, this, new TryNode(this, this.body, this.catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, symbol));
    }

    public Block getFinallyBody() {
        return this.finallyBody;
    }

    public Block getInlinedFinally(String str) {
        Iterator it = this.inlinedFinallies.iterator();
        while (it.hasNext()) {
            LabelNode inlinedFinallyLabelNode = getInlinedFinallyLabelNode((Block) it.next());
            if (inlinedFinallyLabelNode.getLabelName().equals(str)) {
                return inlinedFinallyLabelNode.getBody();
            }
        }
        return null;
    }

    private static LabelNode getInlinedFinallyLabelNode(Block block) {
        return (LabelNode) block.getStatements().get(0);
    }

    public static Block getLabelledInlinedFinallyBlock(Block block) {
        return getInlinedFinallyLabelNode(block).getBody();
    }

    public List getInlinedFinallies() {
        return Collections.unmodifiableList(this.inlinedFinallies);
    }

    public TryNode setFinallyBody(LexicalContext lexicalContext, Block block) {
        if (this.finallyBody == block) {
            return this;
        }
        return (TryNode) Node.replaceInLexicalContext(lexicalContext, this, new TryNode(this, this.body, this.catchBlocks, block, this.conversion, this.inlinedFinallies, this.exception));
    }

    public TryNode setInlinedFinallies(LexicalContext lexicalContext, List list) {
        if (this.inlinedFinallies == list) {
            return this;
        }
        if ($assertionsDisabled || checkInlinedFinallies(list)) {
            return (TryNode) Node.replaceInLexicalContext(lexicalContext, this, new TryNode(this, this.body, this.catchBlocks, this.finallyBody, this.conversion, list, this.exception));
        }
        throw new AssertionError();
    }

    private static boolean checkInlinedFinallies(List list) {
        if (!list.isEmpty()) {
            HashSet hashSet = new HashSet();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Block block = (Block) it.next();
                List statements = block.getStatements();
                if (!$assertionsDisabled && statements.size() != 1) {
                    throw new AssertionError();
                }
                LabelNode inlinedFinallyLabelNode = getInlinedFinallyLabelNode(block);
                if (!$assertionsDisabled && !hashSet.add(inlinedFinallyLabelNode.getLabelName())) {
                    throw new AssertionError();
                }
            }
            return true;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        if (this.conversion == localVariableConversion) {
            return this;
        }
        return new TryNode(this, this.body, this.catchBlocks, this.finallyBody, localVariableConversion, this.inlinedFinallies, this.exception);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }
}
