package jdk.nashorn.internal.codegen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "scopedepths")
/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/FindScopeDepths.class */
final class FindScopeDepths extends SimpleNodeVisitor implements Loggable {
    private final Compiler compiler;
    private final Map fnIdToNestedFunctions = new HashMap();
    private final Map externalSymbolDepths = new HashMap();
    private final Map internalSymbols = new HashMap();
    private final Set withBodies = new HashSet();
    private final DebugLogger log;
    private int dynamicScopeCount;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !FindScopeDepths.class.desiredAssertionStatus();
    }

    FindScopeDepths(Compiler compiler) {
        this.compiler = compiler;
        this.log = initLogger(compiler.getContext());
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return context.getLogger(getClass());
    }

    static int findScopesToStart(LexicalContext lexicalContext, FunctionNode functionNode, Block block) {
        Block blockFindBodyBlock = findBodyBlock(lexicalContext, functionNode, block);
        Iterator blocks = lexicalContext.getBlocks(block);
        Block block2 = (Block) blocks.next();
        int i = 0;
        while (true) {
            if (block2.needsScope()) {
                i++;
            }
            if (block2 != blockFindBodyBlock) {
                block2 = (Block) blocks.next();
            } else {
                return i;
            }
        }
    }

    static int findInternalDepth(LexicalContext lexicalContext, FunctionNode functionNode, Block block, Symbol symbol) {
        Block blockFindBodyBlock = findBodyBlock(lexicalContext, functionNode, block);
        Iterator blocks = lexicalContext.getBlocks(block);
        int i = 0;
        for (Block block2 = (Block) blocks.next(); !definedInBlock(block2, symbol); block2 = (Block) blocks.next()) {
            if (block2.needsScope()) {
                i++;
            }
            if (block2 == blockFindBodyBlock) {
                return -1;
            }
        }
        return i;
    }

    private static boolean definedInBlock(Block block, Symbol symbol) {
        if (!symbol.isGlobal()) {
            return block.getExistingSymbol(symbol.getName()) == symbol;
        }
        if (block.isGlobalScope()) {
            return true;
        }
        return false;
    }

    static Block findBodyBlock(LexicalContext lexicalContext, FunctionNode functionNode, Block block) {
        Iterator blocks = lexicalContext.getBlocks(block);
        while (blocks.hasNext()) {
            Block block2 = (Block) blocks.next();
            if (functionNode.getBody() == block2) {
                return block2;
            }
        }
        return null;
    }

    private static Block findGlobalBlock(LexicalContext lexicalContext, Block block) {
        Iterator blocks = lexicalContext.getBlocks(block);
        Block block2 = null;
        while (true) {
            Block block3 = block2;
            if (blocks.hasNext()) {
                block2 = (Block) blocks.next();
            } else {
                return block3;
            }
        }
    }

    private static boolean isDynamicScopeBoundary(FunctionNode functionNode) {
        return functionNode.needsDynamicScope();
    }

    private boolean isDynamicScopeBoundary(Block block) {
        return this.withBodies.contains(block);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        if (this.compiler.isOnDemandCompilation()) {
            return true;
        }
        if (isDynamicScopeBoundary(functionNode)) {
            increaseDynamicScopeCount(functionNode);
        }
        int id = functionNode.getId();
        if (((Map) this.fnIdToNestedFunctions.get(Integer.valueOf(id))) == null) {
            this.fnIdToNestedFunctions.put(Integer.valueOf(id), new HashMap());
            return true;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        String name = functionNode.getName();
        FunctionNode inDynamicContext = functionNode;
        if (this.compiler.isOnDemandCompilation()) {
            RecompilableScriptFunctionData scriptFunctionData = this.compiler.getScriptFunctionData(inDynamicContext.getId());
            if (scriptFunctionData.inDynamicContext()) {
                this.log.fine(new Object[]{"Reviving scriptfunction ", DebugLogger.quote(name), " as defined in previous (now lost) dynamic scope."});
                inDynamicContext = inDynamicContext.setInDynamicContext(this.f30lc);
            }
            if (inDynamicContext == this.f30lc.getOutermostFunction() && !inDynamicContext.hasApplyToCallSpecialization()) {
                scriptFunctionData.setCachedAst(inDynamicContext);
            }
            return inDynamicContext;
        }
        if (inDynamicScope()) {
            this.log.fine(new Object[]{"Tagging ", DebugLogger.quote(name), " as defined in dynamic scope"});
            inDynamicContext = inDynamicContext.setInDynamicContext(this.f30lc);
        }
        int id = inDynamicContext.getId();
        Map map = (Map) this.fnIdToNestedFunctions.remove(Integer.valueOf(id));
        if (!$assertionsDisabled && map == null) {
            throw new AssertionError();
        }
        RecompilableScriptFunctionData recompilableScriptFunctionData = new RecompilableScriptFunctionData(inDynamicContext, this.compiler.getCodeInstaller(), ObjectClassGenerator.createAllocationStrategy(inDynamicContext.getThisProperties(), this.compiler.getContext().useDualFields()), map, (Map) this.externalSymbolDepths.get(Integer.valueOf(id)), (Set) this.internalSymbols.get(Integer.valueOf(id)));
        if (this.f30lc.getOutermostFunction() != inDynamicContext) {
            FunctionNode parentFunction = this.f30lc.getParentFunction(inDynamicContext);
            if (parentFunction != null) {
                ((Map) this.fnIdToNestedFunctions.get(Integer.valueOf(parentFunction.getId()))).put(Integer.valueOf(id), recompilableScriptFunctionData);
            }
        } else {
            this.compiler.setData(recompilableScriptFunctionData);
        }
        if (isDynamicScopeBoundary(functionNode)) {
            decreaseDynamicScopeCount(functionNode);
        }
        return inDynamicContext;
    }

    private boolean inDynamicScope() {
        return this.dynamicScopeCount > 0;
    }

    private void increaseDynamicScopeCount(Node node) {
        if (!$assertionsDisabled && this.dynamicScopeCount < 0) {
            throw new AssertionError();
        }
        this.dynamicScopeCount++;
        if (this.log.isEnabled()) {
            this.log.finest(new Object[]{DebugLogger.quote(this.f30lc.getCurrentFunction().getName()), " ++dynamicScopeCount = ", Integer.valueOf(this.dynamicScopeCount), " at: ", node, node.getClass()});
        }
    }

    private void decreaseDynamicScopeCount(Node node) {
        this.dynamicScopeCount--;
        if (!$assertionsDisabled && this.dynamicScopeCount < 0) {
            throw new AssertionError();
        }
        if (this.log.isEnabled()) {
            this.log.finest(new Object[]{DebugLogger.quote(this.f30lc.getCurrentFunction().getName()), " --dynamicScopeCount = ", Integer.valueOf(this.dynamicScopeCount), " at: ", node, node.getClass()});
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWithNode(WithNode withNode) {
        this.withBodies.add(withNode.getBody());
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlock(Block block) {
        if (this.compiler.isOnDemandCompilation()) {
            return true;
        }
        if (isDynamicScopeBoundary(block)) {
            increaseDynamicScopeCount(block);
        }
        if (!this.f30lc.isFunctionBody()) {
            return true;
        }
        FunctionNode currentFunction = this.f30lc.getCurrentFunction();
        HashSet<Symbol> hashSet = new HashSet();
        block.accept(new SimpleNodeVisitor(this, hashSet) { // from class: jdk.nashorn.internal.codegen.FindScopeDepths.1
            final Set val$symbols;
            final FindScopeDepths this$0;

            {
                this.this$0 = this;
                this.val$symbols = hashSet;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterIdentNode(IdentNode identNode) {
                Symbol symbol = identNode.getSymbol();
                if (symbol != null && symbol.isScope()) {
                    this.val$symbols.add(symbol);
                    return true;
                }
                return true;
            }
        });
        HashMap map = new HashMap();
        Block blockFindGlobalBlock = findGlobalBlock(this.f30lc, block);
        Block blockFindBodyBlock = findBodyBlock(this.f30lc, currentFunction, block);
        if (!$assertionsDisabled && blockFindGlobalBlock == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && blockFindBodyBlock == null) {
            throw new AssertionError();
        }
        for (Symbol symbol : hashSet) {
            int iFindInternalDepth = findInternalDepth(this.f30lc, currentFunction, block, symbol);
            boolean z = iFindInternalDepth >= 0;
            if (z) {
                map.put(symbol.getName(), Integer.valueOf(iFindInternalDepth));
            }
            if (!z) {
                int i = 0;
                Iterator ancestorBlocks = this.f30lc.getAncestorBlocks(blockFindBodyBlock);
                while (true) {
                    if (ancestorBlocks.hasNext()) {
                        Block block2 = (Block) ancestorBlocks.next();
                        if (definedInBlock(block2, symbol)) {
                            addExternalSymbol(currentFunction, symbol, i);
                            break;
                        }
                        if (block2.needsScope()) {
                            i++;
                        }
                    }
                }
            }
        }
        addInternalSymbols(currentFunction, map.keySet());
        if (this.log.isEnabled()) {
            this.log.info(currentFunction.getName() + " internals=" + map + " externals=" + this.externalSymbolDepths.get(Integer.valueOf(currentFunction.getId())));
            return true;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBlock(Block block) {
        if (this.compiler.isOnDemandCompilation()) {
            return block;
        }
        if (isDynamicScopeBoundary(block)) {
            decreaseDynamicScopeCount(block);
        }
        return block;
    }

    private void addInternalSymbols(FunctionNode functionNode, Set set) {
        int id = functionNode.getId();
        if (!$assertionsDisabled && this.internalSymbols.get(Integer.valueOf(id)) != null && !((Set) this.internalSymbols.get(Integer.valueOf(id))).equals(set)) {
            throw new AssertionError();
        }
        this.internalSymbols.put(Integer.valueOf(id), set);
    }

    private void addExternalSymbol(FunctionNode functionNode, Symbol symbol, int i) {
        int id = functionNode.getId();
        Map map = (Map) this.externalSymbolDepths.get(Integer.valueOf(id));
        if (map == null) {
            map = new HashMap();
            this.externalSymbolDepths.put(Integer.valueOf(id), map);
        }
        map.put(symbol.getName(), Integer.valueOf(i));
    }
}
