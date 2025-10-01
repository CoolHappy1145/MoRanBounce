package jdk.nashorn.internal.p001ir;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/Block.class */
public class Block extends Node implements BreakableNode, Terminal, Flags {
    private static final long serialVersionUID = 1;
    protected final List statements;
    protected final Map symbols;
    private final Label entryLabel;
    private final Label breakLabel;
    protected final int flags;
    private final LocalVariableConversion conversion;
    public static final int NEEDS_SCOPE = 1;
    public static final int IS_TERMINAL = 4;
    public static final int IS_GLOBAL_SCOPE = 8;
    static final boolean $assertionsDisabled;

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return setLocalVariableConversion(lexicalContext, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public LexicalContextNode setFlags(LexicalContext lexicalContext, int i) {
        return setFlags(lexicalContext, i);
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public LexicalContextNode setFlag(LexicalContext lexicalContext, int i) {
        return setFlag(lexicalContext, i);
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public LexicalContextNode clearFlag(LexicalContext lexicalContext, int i) {
        return clearFlag(lexicalContext, i);
    }

    static {
        $assertionsDisabled = !Block.class.desiredAssertionStatus();
    }

    public Block(long j, int i, Statement[] statementArr) {
        super(j, i);
        this.statements = Arrays.asList(statementArr);
        this.symbols = new LinkedHashMap();
        this.entryLabel = new Label("block_entry");
        this.breakLabel = new Label("block_break");
        int length = statementArr.length;
        this.flags = (length <= 0 || !statementArr[length - 1].hasTerminalFlags()) ? 0 : 4;
        this.conversion = null;
    }

    public Block(long j, int i, List list) {
        this(j, i, (Statement[]) list.toArray(new Statement[list.size()]));
    }

    private Block(Block block, int i, List list, int i2, Map map, LocalVariableConversion localVariableConversion) {
        super(block);
        this.statements = list;
        this.flags = i2;
        this.symbols = new LinkedHashMap(map);
        this.entryLabel = new Label(block.entryLabel);
        this.breakLabel = new Label(block.breakLabel);
        this.finish = i;
        this.conversion = localVariableConversion;
    }

    public boolean isGlobalScope() {
        return getFlag(8);
    }

    public boolean hasSymbols() {
        return !this.symbols.isEmpty();
    }

    public Block replaceSymbols(LexicalContext lexicalContext, Map map) {
        if (this.symbols.isEmpty()) {
            return this;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(this.symbols);
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            Symbol symbol = (Symbol) map.get(entry.getValue());
            if (!$assertionsDisabled && symbol == null) {
                throw new AssertionError("Missing replacement for " + ((String) entry.getKey()));
            }
            entry.setValue(symbol);
        }
        return (Block) Node.replaceInLexicalContext(lexicalContext, this, new Block(this, this.finish, this.statements, this.flags, linkedHashMap, this.conversion));
    }

    public Block copyWithNewSymbols() {
        return new Block(this, this.finish, this.statements, this.flags, new LinkedHashMap(this.symbols), this.conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableNode
    public Node ensureUniqueLabels(LexicalContext lexicalContext) {
        return (Node) Node.replaceInLexicalContext(lexicalContext, this, new Block(this, this.finish, this.statements, this.flags, this.symbols, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterBlock(this)) {
            return nodeVisitor.leaveBlock(setStatements(lexicalContext, Node.accept(nodeVisitor, this.statements)));
        }
        return this;
    }

    public List getSymbols() {
        return this.symbols.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(this.symbols.values()));
    }

    public Symbol getExistingSymbol(String str) {
        return (Symbol) this.symbols.get(str);
    }

    public boolean isCatchBlock() {
        return this.statements.size() == 1 && (this.statements.get(0) instanceof CatchNode);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        Iterator it = this.statements.iterator();
        while (it.hasNext()) {
            ((Node) it.next()).toString(sb, z);
            sb.append(';');
        }
    }

    public boolean printSymbols(PrintWriter printWriter) {
        ArrayList arrayList = new ArrayList(this.symbols.values());
        Collections.sort(arrayList, new Comparator(this) { // from class: jdk.nashorn.internal.ir.Block.1
            final Block this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                return compare((Symbol) obj, (Symbol) obj2);
            }

            public int compare(Symbol symbol, Symbol symbol2) {
                return symbol.getName().compareTo(symbol2.getName());
            }
        });
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Symbol) it.next()).print(printWriter);
        }
        return !arrayList.isEmpty();
    }

    public Block setIsTerminal(LexicalContext lexicalContext, boolean z) {
        return z ? setFlag(lexicalContext, 4) : clearFlag(lexicalContext, 4);
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public int getFlags() {
        return this.flags;
    }

    @Override // jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return getFlag(4);
    }

    public Label getEntryLabel() {
        return this.entryLabel;
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableNode
    public Label getBreakLabel() {
        return this.breakLabel;
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public Block setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        if (this.conversion == localVariableConversion) {
            return this;
        }
        return (Block) Node.replaceInLexicalContext(lexicalContext, this, new Block(this, this.finish, this.statements, this.flags, this.symbols, localVariableConversion));
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }

    public List getStatements() {
        return Collections.unmodifiableList(this.statements);
    }

    public int getStatementCount() {
        return this.statements.size();
    }

    public int getFirstStatementLineNumber() {
        if (this.statements == null || this.statements.isEmpty()) {
            return -1;
        }
        return ((Statement) this.statements.get(0)).getLineNumber();
    }

    public Statement getLastStatement() {
        if (this.statements.isEmpty()) {
            return null;
        }
        return (Statement) this.statements.get(this.statements.size() - 1);
    }

    public Block setStatements(LexicalContext lexicalContext, List list) {
        if (this.statements == list) {
            return this;
        }
        int finish = 0;
        if (!list.isEmpty()) {
            finish = ((Statement) list.get(list.size() - 1)).getFinish();
        }
        return (Block) Node.replaceInLexicalContext(lexicalContext, this, new Block(this, Math.max(this.finish, finish), list, this.flags, this.symbols, this.conversion));
    }

    public void putSymbol(Symbol symbol) {
        this.symbols.put(symbol.getName(), symbol);
    }

    public boolean needsScope() {
        return (this.flags & 1) == 1;
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public Block setFlags(LexicalContext lexicalContext, int i) {
        if (this.flags == i) {
            return this;
        }
        return (Block) Node.replaceInLexicalContext(lexicalContext, this, new Block(this, this.finish, this.statements, i, this.symbols, this.conversion));
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public Block clearFlag(LexicalContext lexicalContext, int i) {
        return setFlags(lexicalContext, this.flags & (i ^ (-1)));
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public Block setFlag(LexicalContext lexicalContext, int i) {
        return setFlags(lexicalContext, this.flags | i);
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public boolean getFlag(int i) {
        return (this.flags & i) == i;
    }

    public Block setNeedsScope(LexicalContext lexicalContext) {
        if (needsScope()) {
            return this;
        }
        return (Block) Node.replaceInLexicalContext(lexicalContext, this, new Block(this, this.finish, this.statements, this.flags | 1, this.symbols, this.conversion));
    }

    public int nextSlot() {
        int iSlotCount = 0;
        for (Symbol symbol : getSymbols()) {
            if (symbol.hasSlot()) {
                iSlotCount += symbol.slotCount();
            }
        }
        return iSlotCount;
    }

    @Override // jdk.nashorn.internal.p001ir.Labels
    public List getLabels() {
        return Collections.unmodifiableList(Arrays.asList(this.entryLabel, this.breakLabel));
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return LexicalContextNode.Acceptor.accept(this, nodeVisitor);
    }
}
