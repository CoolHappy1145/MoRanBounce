package jdk.nashorn.internal.p001ir;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/BlockLexicalContext.class */
public class BlockLexicalContext extends LexicalContext {
    private final Deque<List<Statement>> sstack = new ArrayDeque();
    protected Statement lastStatement;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !BlockLexicalContext.class.desiredAssertionStatus();
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public <T extends LexicalContextNode> T push(T t) {
        T t2 = (T) super.push(t);
        if (t instanceof Block) {
            this.sstack.push(new ArrayList());
        }
        return t2;
    }

    protected List<Statement> popStatements() {
        return this.sstack.pop();
    }

    protected Block afterSetStatements(Block block) {
        return block;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public <T extends Node> T pop(T t) {
        T tAfterSetStatements = t;
        if (t instanceof Block) {
            tAfterSetStatements = afterSetStatements(((Block) t).setStatements(this, popStatements()));
            if (!this.sstack.isEmpty()) {
                this.lastStatement = lastStatement(this.sstack.peek());
            }
        }
        return (T) super.pop(tAfterSetStatements);
    }

    public void appendStatement(Statement statement) {
        if (!$assertionsDisabled && statement == null) {
            throw new AssertionError();
        }
        this.sstack.peek().add(statement);
        this.lastStatement = statement;
    }

    public Node prependStatement(Statement statement) {
        if (!$assertionsDisabled && statement == null) {
            throw new AssertionError();
        }
        this.sstack.peek().add(0, statement);
        return statement;
    }

    public void prependStatements(List<Statement> statements) {
        if (!$assertionsDisabled && statements == null) {
            throw new AssertionError();
        }
        this.sstack.peek().addAll(0, statements);
    }

    public Statement getLastStatement() {
        return this.lastStatement;
    }

    private static Statement lastStatement(List<Statement> statements) {
        int s = statements.size();
        if (s == 0) {
            return null;
        }
        return statements.get(s - 1);
    }
}
