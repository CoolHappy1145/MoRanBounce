package jdk.nashorn.internal.p001ir;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.Debug;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/LexicalContext.class */
public class LexicalContext {
    private LexicalContextNode[] stack = new LexicalContextNode[16];
    private int[] flags = new int[16];

    /* renamed from: sp */
    private int f18sp;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !LexicalContext.class.desiredAssertionStatus();
    }

    public void setFlag(LexicalContextNode lexicalContextNode, int i) {
        if (i != 0) {
            if (!$assertionsDisabled && i == 1 && (lexicalContextNode instanceof Block)) {
                throw new AssertionError();
            }
            for (int i2 = this.f18sp - 1; i2 >= 0; i2--) {
                if (this.stack[i2] == lexicalContextNode) {
                    int[] iArr = this.flags;
                    int i3 = i2;
                    iArr[i3] = iArr[i3] | i;
                    return;
                }
            }
        }
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
    }

    public void setBlockNeedsScope(Block block) {
        for (int i = this.f18sp - 1; i >= 0; i--) {
            if (this.stack[i] == block) {
                int[] iArr = this.flags;
                int i2 = i;
                iArr[i2] = iArr[i2] | 1;
                for (int i3 = i - 1; i3 >= 0; i3--) {
                    if (this.stack[i3] instanceof FunctionNode) {
                        int[] iArr2 = this.flags;
                        int i4 = i3;
                        iArr2[i4] = iArr2[i4] | 128;
                        return;
                    }
                }
            }
        }
        if (!$assertionsDisabled) {
            throw new AssertionError();
        }
    }

    public int getFlags(LexicalContextNode lexicalContextNode) {
        for (int i = this.f18sp - 1; i >= 0; i--) {
            if (this.stack[i] == lexicalContextNode) {
                return this.flags[i];
            }
        }
        throw new AssertionError("flag node not on context stack");
    }

    public Block getFunctionBody(FunctionNode functionNode) {
        for (int i = this.f18sp - 1; i >= 0; i--) {
            if (this.stack[i] == functionNode) {
                return (Block) this.stack[i + 1];
            }
        }
        throw new AssertionError(functionNode.getName() + " not on context stack");
    }

    public Iterator getAllNodes() {
        return new NodeIterator(this, LexicalContextNode.class);
    }

    public FunctionNode getOutermostFunction() {
        return (FunctionNode) this.stack[0];
    }

    public LexicalContextNode push(LexicalContextNode lexicalContextNode) {
        if (!$assertionsDisabled && contains(lexicalContextNode)) {
            throw new AssertionError();
        }
        if (this.f18sp == this.stack.length) {
            LexicalContextNode[] lexicalContextNodeArr = new LexicalContextNode[this.f18sp * 2];
            System.arraycopy(this.stack, 0, lexicalContextNodeArr, 0, this.f18sp);
            this.stack = lexicalContextNodeArr;
            int[] iArr = new int[this.f18sp * 2];
            System.arraycopy(this.flags, 0, iArr, 0, this.f18sp);
            this.flags = iArr;
        }
        this.stack[this.f18sp] = lexicalContextNode;
        this.flags[this.f18sp] = 0;
        this.f18sp++;
        return lexicalContextNode;
    }

    public boolean isEmpty() {
        return this.f18sp == 0;
    }

    public int size() {
        return this.f18sp;
    }

    public Node pop(Node node) {
        this.f18sp--;
        Object obj = this.stack[this.f18sp];
        this.stack[this.f18sp] = null;
        if (obj instanceof Flags) {
            return (Node) ((Flags) obj).setFlag(this, this.flags[this.f18sp]);
        }
        return (Node) obj;
    }

    public LexicalContextNode applyTopFlags(LexicalContextNode lexicalContextNode) {
        if ($assertionsDisabled || lexicalContextNode == peek()) {
            return ((Flags) lexicalContextNode).setFlag(this, this.flags[this.f18sp - 1]);
        }
        throw new AssertionError();
    }

    public LexicalContextNode peek() {
        return this.stack[this.f18sp - 1];
    }

    public boolean contains(LexicalContextNode lexicalContextNode) {
        for (int i = 0; i < this.f18sp; i++) {
            if (this.stack[i] == lexicalContextNode) {
                return true;
            }
        }
        return false;
    }

    public LexicalContextNode replace(LexicalContextNode lexicalContextNode, LexicalContextNode lexicalContextNode2) {
        int i = this.f18sp - 1;
        while (true) {
            if (i < 0) {
                break;
            }
            if (this.stack[i] != lexicalContextNode) {
                i--;
            } else {
                if (!$assertionsDisabled && i != this.f18sp - 1) {
                    throw new AssertionError("violation of contract - we always expect to find the replacement node on top of the lexical context stack: " + lexicalContextNode2 + " has " + this.stack[i + 1].getClass() + " above it");
                }
                this.stack[i] = lexicalContextNode2;
            }
        }
        return lexicalContextNode2;
    }

    public Iterator getBlocks() {
        return new NodeIterator(this, Block.class);
    }

    public Iterator getFunctions() {
        return new NodeIterator(this, FunctionNode.class);
    }

    public Block getParentBlock() {
        NodeIterator nodeIterator = new NodeIterator(this, Block.class, getCurrentFunction());
        nodeIterator.next();
        if (nodeIterator.hasNext()) {
            return (Block) nodeIterator.next();
        }
        return null;
    }

    public LabelNode getCurrentBlockLabelNode() {
        if (!$assertionsDisabled && !(this.stack[this.f18sp - 1] instanceof Block)) {
            throw new AssertionError();
        }
        if (this.f18sp < 2) {
            return null;
        }
        LexicalContextNode lexicalContextNode = this.stack[this.f18sp - 2];
        if (lexicalContextNode instanceof LabelNode) {
            return (LabelNode) lexicalContextNode;
        }
        return null;
    }

    public Iterator getAncestorBlocks(Block block) {
        Iterator blocks = getBlocks();
        while (blocks.hasNext()) {
            if (block == ((Block) blocks.next())) {
                return blocks;
            }
        }
        throw new AssertionError("Block is not on the current lexical context stack");
    }

    public Iterator getBlocks(Block block) {
        return new Iterator(this, getAncestorBlocks(block), block) { // from class: jdk.nashorn.internal.ir.LexicalContext.1
            boolean blockReturned = false;
            final Iterator val$iter;
            final Block val$block;
            final LexicalContext this$0;

            {
                this.this$0 = this;
                this.val$iter = it;
                this.val$block = block;
            }

            @Override // java.util.Iterator
            public Object next() {
                return next();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.val$iter.hasNext() || !this.blockReturned;
            }

            @Override // java.util.Iterator
            public Block next() {
                if (this.blockReturned) {
                    return (Block) this.val$iter.next();
                }
                this.blockReturned = true;
                return this.val$block;
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public FunctionNode getFunction(Block block) {
        NodeIterator nodeIterator = new NodeIterator(this, LexicalContextNode.class);
        while (nodeIterator.hasNext()) {
            if (((LexicalContextNode) nodeIterator.next()) == block) {
                while (nodeIterator.hasNext()) {
                    LexicalContextNode lexicalContextNode = (LexicalContextNode) nodeIterator.next();
                    if (lexicalContextNode instanceof FunctionNode) {
                        return (FunctionNode) lexicalContextNode;
                    }
                }
            }
        }
        if ($assertionsDisabled) {
            return null;
        }
        throw new AssertionError();
    }

    public Block getCurrentBlock() {
        return (Block) getBlocks().next();
    }

    public FunctionNode getCurrentFunction() {
        for (int i = this.f18sp - 1; i >= 0; i--) {
            if (this.stack[i] instanceof FunctionNode) {
                return (FunctionNode) this.stack[i];
            }
        }
        return null;
    }

    public Block getDefiningBlock(Symbol symbol) {
        String name = symbol.getName();
        Iterator blocks = getBlocks();
        while (blocks.hasNext()) {
            Block block = (Block) blocks.next();
            if (block.getExistingSymbol(name) == symbol) {
                return block;
            }
        }
        throw new AssertionError("Couldn't find symbol " + name + " in the context");
    }

    public FunctionNode getDefiningFunction(Symbol symbol) {
        String name = symbol.getName();
        NodeIterator nodeIterator = new NodeIterator(this, LexicalContextNode.class);
        while (nodeIterator.hasNext()) {
            LexicalContextNode lexicalContextNode = (LexicalContextNode) nodeIterator.next();
            if ((lexicalContextNode instanceof Block) && ((Block) lexicalContextNode).getExistingSymbol(name) == symbol) {
                while (nodeIterator.hasNext()) {
                    LexicalContextNode lexicalContextNode2 = (LexicalContextNode) nodeIterator.next();
                    if (lexicalContextNode2 instanceof FunctionNode) {
                        return (FunctionNode) lexicalContextNode2;
                    }
                }
                throw new AssertionError("Defining block for symbol " + name + " has no function in the context");
            }
        }
        throw new AssertionError("Couldn't find symbol " + name + " in the context");
    }

    public boolean isFunctionBody() {
        return getParentBlock() == null;
    }

    public boolean isSplitBody() {
        return this.f18sp >= 2 && (this.stack[this.f18sp - 1] instanceof Block) && (this.stack[this.f18sp - 2] instanceof SplitNode);
    }

    public FunctionNode getParentFunction(FunctionNode functionNode) {
        NodeIterator nodeIterator = new NodeIterator(this, FunctionNode.class);
        while (nodeIterator.hasNext()) {
            if (((FunctionNode) nodeIterator.next()) == functionNode) {
                if (nodeIterator.hasNext()) {
                    return (FunctionNode) nodeIterator.next();
                }
                return null;
            }
        }
        if ($assertionsDisabled) {
            return null;
        }
        throw new AssertionError();
    }

    public int getScopeNestingLevelTo(LexicalContextNode lexicalContextNode) {
        LexicalContextNode lexicalContextNode2;
        if (!$assertionsDisabled && lexicalContextNode == null) {
            throw new AssertionError();
        }
        int i = 0;
        Iterator allNodes = getAllNodes();
        while (allNodes.hasNext() && (lexicalContextNode2 = (LexicalContextNode) allNodes.next()) != lexicalContextNode) {
            if (!$assertionsDisabled && (lexicalContextNode2 instanceof FunctionNode)) {
                throw new AssertionError();
            }
            if ((lexicalContextNode2 instanceof WithNode) || ((lexicalContextNode2 instanceof Block) && ((Block) lexicalContextNode2).needsScope())) {
                i++;
            }
        }
        return i;
    }

    private BreakableNode getBreakable() {
        NodeIterator nodeIterator = new NodeIterator(this, BreakableNode.class, getCurrentFunction());
        while (nodeIterator.hasNext()) {
            BreakableNode breakableNode = (BreakableNode) nodeIterator.next();
            if (breakableNode.isBreakableWithoutLabel()) {
                return breakableNode;
            }
        }
        return null;
    }

    public boolean inLoop() {
        return getCurrentLoop() != null;
    }

    public LoopNode getCurrentLoop() {
        NodeIterator nodeIterator = new NodeIterator(this, LoopNode.class, getCurrentFunction());
        if (nodeIterator.hasNext()) {
            return (LoopNode) nodeIterator.next();
        }
        return null;
    }

    public BreakableNode getBreakable(String str) {
        if (str != null) {
            LabelNode labelNodeFindLabel = findLabel(str);
            if (labelNodeFindLabel != null) {
                BreakableNode breakableNode = null;
                NodeIterator nodeIterator = new NodeIterator(this, BreakableNode.class, labelNodeFindLabel);
                while (nodeIterator.hasNext()) {
                    breakableNode = (BreakableNode) nodeIterator.next();
                }
                return breakableNode;
            }
            return null;
        }
        return getBreakable();
    }

    private LoopNode getContinueTo() {
        return getCurrentLoop();
    }

    public LoopNode getContinueTo(String str) {
        if (str != null) {
            LabelNode labelNodeFindLabel = findLabel(str);
            if (labelNodeFindLabel != null) {
                LoopNode loopNode = null;
                NodeIterator nodeIterator = new NodeIterator(this, LoopNode.class, labelNodeFindLabel);
                while (nodeIterator.hasNext()) {
                    loopNode = (LoopNode) nodeIterator.next();
                }
                return loopNode;
            }
            return null;
        }
        return getContinueTo();
    }

    public Block getInlinedFinally(String str) {
        NodeIterator nodeIterator = new NodeIterator(this, TryNode.class);
        while (nodeIterator.hasNext()) {
            Block inlinedFinally = ((TryNode) nodeIterator.next()).getInlinedFinally(str);
            if (inlinedFinally != null) {
                return inlinedFinally;
            }
        }
        return null;
    }

    public TryNode getTryNodeForInlinedFinally(String str) {
        NodeIterator nodeIterator = new NodeIterator(this, TryNode.class);
        while (nodeIterator.hasNext()) {
            TryNode tryNode = (TryNode) nodeIterator.next();
            if (tryNode.getInlinedFinally(str) != null) {
                return tryNode;
            }
        }
        return null;
    }

    public LabelNode findLabel(String str) {
        NodeIterator nodeIterator = new NodeIterator(this, LabelNode.class, getCurrentFunction());
        while (nodeIterator.hasNext()) {
            LabelNode labelNode = (LabelNode) nodeIterator.next();
            if (labelNode.getLabelName().equals(str)) {
                return labelNode;
            }
        }
        return null;
    }

    public boolean isExternalTarget(SplitNode splitNode, BreakableNode breakableNode) {
        int i = this.f18sp;
        while (true) {
            int i2 = i;
            i--;
            if (i2 > 0) {
                LexicalContextNode lexicalContextNode = this.stack[i];
                if (lexicalContextNode == splitNode) {
                    return true;
                }
                if (lexicalContextNode == breakableNode) {
                    return false;
                }
                if (lexicalContextNode instanceof TryNode) {
                    Iterator it = ((TryNode) lexicalContextNode).getInlinedFinallies().iterator();
                    while (it.hasNext()) {
                        if (TryNode.getLabelledInlinedFinallyBlock((Block) it.next()) == breakableNode) {
                            return false;
                        }
                    }
                }
            } else {
                throw new AssertionError(breakableNode + " was expected in lexical context " + this + " but wasn't");
            }
        }
    }

    public boolean inUnprotectedSwitchContext() {
        for (int i = this.f18sp; i > 0; i--) {
            if (this.stack[i] instanceof Block) {
                return this.stack[i - 1] instanceof SwitchNode;
            }
        }
        return false;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ ");
        for (int i = 0; i < this.f18sp; i++) {
            LexicalContextNode lexicalContextNode = this.stack[i];
            stringBuffer.append(lexicalContextNode.getClass().getSimpleName());
            stringBuffer.append('@');
            stringBuffer.append(Debug.m11id(lexicalContextNode));
            stringBuffer.append(':');
            if (lexicalContextNode instanceof FunctionNode) {
                FunctionNode functionNode = (FunctionNode) lexicalContextNode;
                String string = functionNode.getSource().toString();
                if (string.contains(File.pathSeparator)) {
                    string = string.substring(string.lastIndexOf(File.pathSeparator));
                }
                stringBuffer.append((string + ' ') + functionNode.getLineNumber());
            }
            stringBuffer.append(' ');
        }
        stringBuffer.append(" ==> ]");
        return stringBuffer.toString();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LexicalContext$NodeIterator.class */
    private class NodeIterator implements Iterator {
        private int index;
        private LexicalContextNode next;
        private final Class clazz;
        private LexicalContextNode until;
        final LexicalContext this$0;

        @Override // java.util.Iterator
        public Object next() {
            return next();
        }

        NodeIterator(LexicalContext lexicalContext, Class cls) {
            this(lexicalContext, cls, null);
        }

        NodeIterator(LexicalContext lexicalContext, Class cls, LexicalContextNode lexicalContextNode) {
            this.this$0 = lexicalContext;
            this.index = lexicalContext.f18sp - 1;
            this.clazz = cls;
            this.until = lexicalContextNode;
            this.next = findNext();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next != null;
        }

        @Override // java.util.Iterator
        public LexicalContextNode next() {
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            LexicalContextNode lexicalContextNode = this.next;
            this.next = findNext();
            return lexicalContextNode;
        }

        private LexicalContextNode findNext() {
            LexicalContextNode lexicalContextNode;
            for (int i = this.index; i >= 0 && (lexicalContextNode = this.this$0.stack[i]) != this.until; i--) {
                if (this.clazz.isAssignableFrom(lexicalContextNode.getClass())) {
                    this.index = i - 1;
                    return lexicalContextNode;
                }
            }
            return null;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
