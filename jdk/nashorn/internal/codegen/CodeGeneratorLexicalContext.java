package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import jdk.nashorn.internal.IntDeque;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.tools.Shell;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGeneratorLexicalContext.class */
final class CodeGeneratorLexicalContext extends LexicalContext {
    private int dynamicScopeCount;
    private final Map scopeCalls = new HashMap();
    private final Deque compileUnits = new ArrayDeque();
    private final Deque methodEmitters = new ArrayDeque();
    private final Deque discard = new ArrayDeque();
    private final Deque unwarrantedOptimismHandlers = new ArrayDeque();
    private final Deque slotTypesDescriptors = new ArrayDeque();
    private final IntDeque splitNodes = new IntDeque();
    private int[] nextFreeSlots = new int[16];
    private int nextFreeSlotsSize;
    static final boolean $assertionsDisabled;

    CodeGeneratorLexicalContext() {
    }

    static {
        $assertionsDisabled = !CodeGeneratorLexicalContext.class.desiredAssertionStatus();
    }

    private boolean isWithBoundary(Object obj) {
        return (obj instanceof Block) && !isEmpty() && (peek() instanceof WithNode);
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public LexicalContextNode push(LexicalContextNode lexicalContextNode) {
        if (isWithBoundary(lexicalContextNode)) {
            this.dynamicScopeCount++;
        } else if (lexicalContextNode instanceof FunctionNode) {
            if (((FunctionNode) lexicalContextNode).inDynamicContext()) {
                this.dynamicScopeCount++;
            }
            this.splitNodes.push(0);
        }
        return super.push(lexicalContextNode);
    }

    void enterSplitNode() {
        this.splitNodes.getAndIncrement();
        pushFreeSlots(((MethodEmitter) this.methodEmitters.peek()).getUsedSlotsWithLiveTemporaries());
    }

    void exitSplitNode() {
        int iDecrementAndGet = this.splitNodes.decrementAndGet();
        if (!$assertionsDisabled && iDecrementAndGet < 0) {
            throw new AssertionError();
        }
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContext
    public Node pop(Node node) {
        Node nodePop = super.pop(node);
        if (isWithBoundary(node)) {
            this.dynamicScopeCount--;
            if (!$assertionsDisabled && this.dynamicScopeCount < 0) {
                throw new AssertionError();
            }
        } else if (node instanceof FunctionNode) {
            if (((FunctionNode) node).inDynamicContext()) {
                this.dynamicScopeCount--;
                if (!$assertionsDisabled && this.dynamicScopeCount < 0) {
                    throw new AssertionError();
                }
            }
            if (!$assertionsDisabled && this.splitNodes.peek() != 0) {
                throw new AssertionError();
            }
            this.splitNodes.pop();
        }
        return nodePop;
    }

    boolean inDynamicScope() {
        return this.dynamicScopeCount > 0;
    }

    boolean inSplitNode() {
        return !this.splitNodes.isEmpty() && this.splitNodes.peek() > 0;
    }

    MethodEmitter pushMethodEmitter(MethodEmitter methodEmitter) {
        this.methodEmitters.push(methodEmitter);
        return methodEmitter;
    }

    MethodEmitter popMethodEmitter(MethodEmitter methodEmitter) {
        if (!$assertionsDisabled && this.methodEmitters.peek() != methodEmitter) {
            throw new AssertionError();
        }
        this.methodEmitters.pop();
        if (this.methodEmitters.isEmpty()) {
            return null;
        }
        return (MethodEmitter) this.methodEmitters.peek();
    }

    void pushUnwarrantedOptimismHandlers() {
        this.unwarrantedOptimismHandlers.push(new HashMap());
        this.slotTypesDescriptors.push(new StringBuilder());
    }

    Map getUnwarrantedOptimismHandlers() {
        return (Map) this.unwarrantedOptimismHandlers.peek();
    }

    Map popUnwarrantedOptimismHandlers() {
        this.slotTypesDescriptors.pop();
        return (Map) this.unwarrantedOptimismHandlers.pop();
    }

    CompileUnit pushCompileUnit(CompileUnit compileUnit) {
        this.compileUnits.push(compileUnit);
        return compileUnit;
    }

    CompileUnit popCompileUnit(CompileUnit compileUnit) {
        if (!$assertionsDisabled && this.compileUnits.peek() != compileUnit) {
            throw new AssertionError();
        }
        CompileUnit compileUnit2 = (CompileUnit) this.compileUnits.pop();
        if (!$assertionsDisabled && !compileUnit2.hasCode()) {
            throw new AssertionError("compile unit popped without code");
        }
        compileUnit2.setUsed();
        if (this.compileUnits.isEmpty()) {
            return null;
        }
        return (CompileUnit) this.compileUnits.peek();
    }

    boolean hasCompileUnits() {
        return !this.compileUnits.isEmpty();
    }

    Collection getScopeCalls() {
        return Collections.unmodifiableCollection(this.scopeCalls.values());
    }

    SharedScopeCall getScopeCall(CompileUnit compileUnit, Symbol symbol, Type type, Type type2, Type[] typeArr, int i) {
        SharedScopeCall sharedScopeCall = new SharedScopeCall(symbol, type, type2, typeArr, i);
        if (this.scopeCalls.containsKey(sharedScopeCall)) {
            return (SharedScopeCall) this.scopeCalls.get(sharedScopeCall);
        }
        sharedScopeCall.setClassAndName(compileUnit, getCurrentFunction().uniqueName(":scopeCall"));
        this.scopeCalls.put(sharedScopeCall, sharedScopeCall);
        return sharedScopeCall;
    }

    SharedScopeCall getScopeGet(CompileUnit compileUnit, Symbol symbol, Type type, int i) {
        return getScopeCall(compileUnit, symbol, type, type, null, i);
    }

    void onEnterBlock(Block block) {
        pushFreeSlots(assignSlots(block, isFunctionBody() ? 0 : getUsedSlotCount()));
    }

    private void pushFreeSlots(int i) {
        if (this.nextFreeSlotsSize == this.nextFreeSlots.length) {
            int[] iArr = new int[this.nextFreeSlotsSize * 2];
            System.arraycopy(this.nextFreeSlots, 0, iArr, 0, this.nextFreeSlotsSize);
            this.nextFreeSlots = iArr;
        }
        int[] iArr2 = this.nextFreeSlots;
        int i2 = this.nextFreeSlotsSize;
        this.nextFreeSlotsSize = i2 + 1;
        iArr2[i2] = i;
    }

    int getUsedSlotCount() {
        return this.nextFreeSlots[this.nextFreeSlotsSize - 1];
    }

    void releaseSlots() {
        this.nextFreeSlotsSize--;
        int i = this.nextFreeSlotsSize == 0 ? 0 : this.nextFreeSlots[this.nextFreeSlotsSize - 1];
        if (!this.slotTypesDescriptors.isEmpty()) {
            ((StringBuilder) this.slotTypesDescriptors.peek()).setLength(i);
        }
        ((MethodEmitter) this.methodEmitters.peek()).undefineLocalVariables(i, false);
    }

    private int assignSlots(Block block, int i) {
        int i2 = i;
        MethodEmitter methodEmitter = (MethodEmitter) this.methodEmitters.peek();
        for (Symbol symbol : block.getSymbols()) {
            if (symbol.hasSlot()) {
                symbol.setFirstSlot(i2);
                int iSlotCount = i2 + symbol.slotCount();
                methodEmitter.defineBlockLocalVariable(i2, iSlotCount);
                i2 = iSlotCount;
            }
        }
        return i2;
    }

    static Type getTypeForSlotDescriptor(char c) {
        switch (c) {
            case OPCode.REPEAT_INC_NG_SG /* 65 */:
            case 'a':
                return Type.OBJECT;
            case OPCode.NULL_CHECK_END_MEMST /* 68 */:
            case Shell.COMMANDLINE_ERROR /* 100 */:
                return Type.NUMBER;
            case OPCode.FAIL_POS /* 73 */:
            case 'i':
                return Type.INT;
            case OPCode.PUSH_STOP_BT /* 74 */:
            case 'j':
                return Type.LONG;
            case OPCode.STATE_CHECK_ANYCHAR_ML_STAR /* 85 */:
            case 'u':
                return Type.UNKNOWN;
            default:
                throw new AssertionError();
        }
    }

    void pushDiscard(Expression expression) {
        this.discard.push(expression);
    }

    boolean popDiscardIfCurrent(Expression expression) {
        if (isCurrentDiscard(expression)) {
            this.discard.pop();
            return true;
        }
        return false;
    }

    boolean isCurrentDiscard(Expression expression) {
        return this.discard.peek() == expression;
    }

    int quickSlot(Type type) {
        return ((MethodEmitter) this.methodEmitters.peek()).defineTemporaryLocalVariable(type.getSlots());
    }
}
