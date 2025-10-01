package jdk.nashorn.internal.p001ir;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/OptimisticLexicalContext.class */
public class OptimisticLexicalContext extends LexicalContext {
    private final boolean isEnabled;
    private final Deque optimisticAssumptions = new ArrayDeque();

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/OptimisticLexicalContext$Assumption.class */
    class Assumption {
        Symbol symbol;
        Type type;
        final OptimisticLexicalContext this$0;

        Assumption(OptimisticLexicalContext optimisticLexicalContext, Symbol symbol, Type type) {
            this.this$0 = optimisticLexicalContext;
            this.symbol = symbol;
            this.type = type;
        }

        public String toString() {
            return this.symbol.getName() + "=" + this.type;
        }
    }

    public OptimisticLexicalContext(boolean z) {
        this.isEnabled = z;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void logOptimisticAssumption(Symbol symbol, Type type) {
        if (this.isEnabled) {
            ((List) this.optimisticAssumptions.peek()).add(new Assumption(this, symbol, type));
        }
    }

    public List getOptimisticAssumptions() {
        return Collections.unmodifiableList((List) this.optimisticAssumptions.peek());
    }

    public boolean hasOptimisticAssumptions() {
        return (this.optimisticAssumptions.isEmpty() || getOptimisticAssumptions().isEmpty()) ? false : true;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public LexicalContextNode push(LexicalContextNode lexicalContextNode) {
        if (this.isEnabled && (lexicalContextNode instanceof FunctionNode)) {
            this.optimisticAssumptions.push(new ArrayList());
        }
        return super.push(lexicalContextNode);
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public Node pop(Node node) {
        Node nodePop = super.pop(node);
        if (this.isEnabled && (node instanceof FunctionNode)) {
            this.optimisticAssumptions.pop();
        }
        return nodePop;
    }
}
