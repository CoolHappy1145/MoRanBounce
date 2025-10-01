package jdk.nashorn.internal.runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import jdk.internal.dynalink.support.NameCodec;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.FunctionSignature;
import jdk.nashorn.internal.codegen.Namespace;
import jdk.nashorn.internal.codegen.OptimisticTypesPersistence;
import jdk.nashorn.internal.codegen.TypeMap;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

@Logger(name = "recompile")
/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/RecompilableScriptFunctionData.class */
public final class RecompilableScriptFunctionData extends ScriptFunctionData implements Loggable {
    public static final String RECOMPILATION_PREFIX = "Recompilation$";
    private static final ExecutorService astSerializerExecutorService;
    private final int functionNodeId;
    private final String functionName;
    private final int lineNumber;
    private Source source;
    private Object cachedAst;
    private final long token;
    private final AllocationStrategy allocationStrategy;
    private final Object endParserState;
    private CodeInstaller installer;
    private final Map nestedFunctions;
    private RecompilableScriptFunctionData parent;
    private final int functionFlags;
    private static final MethodHandles.Lookup LOOKUP;
    private DebugLogger log;
    private final Map externalScopeDepths;
    private final Set internalSymbols;
    private static final long serialVersionUID = 4914839316174633726L;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !RecompilableScriptFunctionData.class.desiredAssertionStatus();
        astSerializerExecutorService = createAstSerializerExecutorService();
        LOOKUP = MethodHandles.lookup();
    }

    public RecompilableScriptFunctionData(FunctionNode functionNode, CodeInstaller codeInstaller, AllocationStrategy allocationStrategy, Map map, Map map2, Set set) {
        super(functionName(functionNode), Math.min(functionNode.getParameters().size(), LinkerCallSite.ARGLIMIT), getDataFlags(functionNode));
        this.functionName = functionNode.getName();
        this.lineNumber = functionNode.getLineNumber();
        this.functionFlags = functionNode.getFlags() | (functionNode.needsCallee() ? 67108864 : 0);
        this.functionNodeId = functionNode.getId();
        this.source = functionNode.getSource();
        this.endParserState = functionNode.getEndParserState();
        this.token = tokenFor(functionNode);
        this.installer = codeInstaller;
        this.allocationStrategy = allocationStrategy;
        this.nestedFunctions = smallMap(map);
        this.externalScopeDepths = smallMap(map2);
        this.internalSymbols = smallSet(new HashSet(set));
        for (RecompilableScriptFunctionData recompilableScriptFunctionData : map.values()) {
            if (!$assertionsDisabled && recompilableScriptFunctionData.getParent() != null) {
                throw new AssertionError();
            }
            recompilableScriptFunctionData.setParent(this);
        }
        createLogger();
    }

    private static Map smallMap(Map map) {
        if (map == null || map.isEmpty()) {
            return Collections.emptyMap();
        }
        if (map.size() == 1) {
            Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
            return Collections.singletonMap(entry.getKey(), entry.getValue());
        }
        return map;
    }

    private static Set smallSet(Set set) {
        if (set == null || set.isEmpty()) {
            return Collections.emptySet();
        }
        if (set.size() == 1) {
            return Collections.singleton(set.iterator().next());
        }
        return set;
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

    public boolean hasInternalSymbol(String str) {
        return this.internalSymbols.contains(str);
    }

    public int getExternalSymbolDepth(String str) {
        Integer num = (Integer) this.externalScopeDepths.get(str);
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }

    public Set getExternalSymbolNames() {
        return Collections.unmodifiableSet(this.externalScopeDepths.keySet());
    }

    public Object getEndParserState() {
        return this.endParserState;
    }

    public RecompilableScriptFunctionData getParent() {
        return this.parent;
    }

    void setParent(RecompilableScriptFunctionData recompilableScriptFunctionData) {
        this.parent = recompilableScriptFunctionData;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    String toSource() {
        if (this.source == null || this.token == 0) {
            return "function " + (this.name == null ? "" : this.name) + "() { [native code] }";
        }
        return this.source.getString((int) (this.token >>> 32), ((int) this.token) >>> 8);
    }

    public void initTransients(Source source, CodeInstaller codeInstaller) {
        if (this.source == null && this.installer == null) {
            this.source = source;
            this.installer = codeInstaller;
        } else if (this.source != source || !this.installer.isCompatibleWith(codeInstaller)) {
            throw new IllegalArgumentException();
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public String toString() {
        return super.toString() + '@' + this.functionNodeId;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public String toStringVerbose() {
        StringBuilder sb = new StringBuilder();
        sb.append("fnId=").append(this.functionNodeId).append(' ');
        if (this.source != null) {
            sb.append(this.source.getName()).append(':').append(this.lineNumber).append(' ');
        }
        return sb.toString() + super.toString();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public String getFunctionName() {
        return this.functionName;
    }

    public boolean inDynamicContext() {
        return getFunctionFlag(65536);
    }

    private static String functionName(FunctionNode functionNode) {
        if (functionNode.isAnonymous()) {
            return "";
        }
        FunctionNode.Kind kind = functionNode.getKind();
        if (kind == FunctionNode.Kind.GETTER || kind == FunctionNode.Kind.SETTER) {
            return NameCodec.decode(functionNode.getIdent().getName()).substring(4);
        }
        return functionNode.getIdent().getName();
    }

    private static long tokenFor(FunctionNode functionNode) {
        int firstToken = (int) (functionNode.getFirstToken() >>> 32);
        long jWithDelimiter = Token.withDelimiter(functionNode.getLastToken());
        return Token.toDesc(TokenType.FUNCTION, firstToken, (((int) (jWithDelimiter >>> 32)) - firstToken) + (Token.descType(jWithDelimiter) == TokenType.EOL ? 0 : ((int) jWithDelimiter) >>> 8));
    }

    private static int getDataFlags(FunctionNode functionNode) {
        int i = 4;
        if (functionNode.isStrict()) {
            i = 5;
        }
        if (functionNode.needsCallee()) {
            i |= 8;
        }
        if (functionNode.usesThis() || functionNode.hasEval()) {
            i |= 16;
        }
        if (functionNode.isVarArg()) {
            i |= 32;
        }
        if (functionNode.getKind() == FunctionNode.Kind.GETTER || functionNode.getKind() == FunctionNode.Kind.SETTER) {
            i |= 64;
        }
        return i;
    }

    PropertyMap getAllocatorMap(ScriptObject scriptObject) {
        return this.allocationStrategy.getAllocatorMap(scriptObject);
    }

    ScriptObject allocate(PropertyMap propertyMap) {
        return this.allocationStrategy.allocate(propertyMap);
    }

    FunctionNode reparse() {
        FunctionNode cachedAst = getCachedAst();
        if (cachedAst == null) {
            int i = (int) (this.token >>> 32);
            Context contextTrusted = Context.getContextTrusted();
            Parser parser = new Parser(contextTrusted.getEnv(), this.source, new Context.ThrowErrorManager(), isStrict(), this.lineNumber - 1, contextTrusted.getLogger(Parser.class));
            if (getFunctionFlag(1)) {
                parser.setFunctionName(this.functionName);
            }
            parser.setReparsedFunction(this);
            FunctionNode functionNode = parser.parse(CompilerConstants.PROGRAM.symbolName(), i, ((int) this.token) >>> 8, isPropertyAccessor());
            return (isProgram() ? functionNode : extractFunctionFromScript(functionNode)).setName(null, this.functionName);
        }
        if ($assertionsDisabled || cachedAst.isCached()) {
            return cachedAst;
        }
        throw new AssertionError();
    }

    private FunctionNode getCachedAst() {
        Object obj = this.cachedAst;
        if (obj instanceof Reference) {
            FunctionNode functionNode = (FunctionNode) ((Reference) obj).get();
            if (functionNode != null) {
                return cloneSymbols(functionNode);
            }
            return null;
        }
        if (obj instanceof SerializedAst) {
            SerializedAst serializedAst = (SerializedAst) obj;
            FunctionNode functionNode2 = (FunctionNode) serializedAst.cachedAst.get();
            if (functionNode2 != null) {
                return cloneSymbols(functionNode2);
            }
            FunctionNode functionNodeDeserialize = deserialize(serializedAst.serializedAst);
            serializedAst.cachedAst = new SoftReference(functionNodeDeserialize);
            return functionNodeDeserialize;
        }
        return null;
    }

    public void setCachedAst(FunctionNode functionNode) {
        if (!$assertionsDisabled && functionNode.getId() != this.functionNodeId) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && (this.cachedAst instanceof SerializedAst)) {
            throw new AssertionError();
        }
        boolean zIsSplit = functionNode.isSplit();
        if (!$assertionsDisabled && zIsSplit && this.cachedAst != null) {
            throw new AssertionError();
        }
        FunctionNode functionNodeCloneSymbols = cloneSymbols(functionNode);
        SoftReference softReference = new SoftReference(functionNodeCloneSymbols);
        this.cachedAst = softReference;
        if (zIsSplit) {
            astSerializerExecutorService.execute(new Runnable(this, functionNodeCloneSymbols, softReference) { // from class: jdk.nashorn.internal.runtime.RecompilableScriptFunctionData.1
                final FunctionNode val$symbolClonedAst;
                final Reference val$ref;
                final RecompilableScriptFunctionData this$0;

                {
                    this.this$0 = this;
                    this.val$symbolClonedAst = functionNodeCloneSymbols;
                    this.val$ref = softReference;
                }

                @Override // java.lang.Runnable
                public void run() {
                    this.this$0.cachedAst = new SerializedAst(this.val$symbolClonedAst, this.val$ref);
                }
            });
        }
    }

    private static ExecutorService createAstSerializerExecutorService() {
        int iMax = Math.max(1, Options.getIntProperty("nashorn.serialize.threads", Runtime.getRuntime().availableProcessors() / 2));
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(iMax, iMax, 1L, TimeUnit.MINUTES, new LinkedBlockingDeque(), new ThreadFactory() { // from class: jdk.nashorn.internal.runtime.RecompilableScriptFunctionData.2
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "Nashorn AST Serializer");
                thread.setDaemon(true);
                thread.setPriority(4);
                return thread;
            }
        });
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/RecompilableScriptFunctionData$SerializedAst.class */
    private static class SerializedAst {
        private final byte[] serializedAst;
        private Reference cachedAst;

        SerializedAst(FunctionNode functionNode, Reference reference) {
            this.serializedAst = AstSerializer.serialize(functionNode);
            this.cachedAst = reference;
        }
    }

    private FunctionNode deserialize(byte[] bArr) {
        ScriptEnvironment env = this.installer.getContext().getEnv();
        Timing timing = env._timing;
        long jNanoTime = System.nanoTime();
        try {
            return AstDeserializer.deserialize(bArr).initializeDeserialized(this.source, new Namespace(env.getNamespace()));
        } finally {
            timing.accumulateTime("'Deserialize'", System.nanoTime() - jNanoTime);
        }
    }

    private FunctionNode cloneSymbols(FunctionNode functionNode) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        final boolean zIsCached = functionNode.isCached();
        final Set setNewSetFromMap = (!functionNode.isSplit() || zIsCached) ? null : Collections.newSetFromMap(new IdentityHashMap());
        FunctionNode body = (FunctionNode) functionNode.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.runtime.RecompilableScriptFunctionData.3
            private Symbol getReplacement(Symbol original) {
                if (original == null) {
                    return null;
                }
                Symbol existingReplacement = (Symbol) identityHashMap.get(original);
                if (existingReplacement != null) {
                    return existingReplacement;
                }
                Symbol newReplacement = original.m225clone();
                identityHashMap.put(original, newReplacement);
                return newReplacement;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveIdentNode(IdentNode identNode) {
                Symbol oldSymbol = identNode.getSymbol();
                if (oldSymbol != null) {
                    Symbol replacement = getReplacement(oldSymbol);
                    return identNode.setSymbol(replacement);
                }
                return identNode;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveForNode(ForNode forNode) {
                return ensureUniqueLabels(forNode.setIterator(this.f30lc, getReplacement(forNode.getIterator())));
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveSwitchNode(SwitchNode switchNode) {
                return ensureUniqueLabels(switchNode.setTag(this.f30lc, getReplacement(switchNode.getTag())));
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveTryNode(TryNode tryNode) {
                return ensureUniqueLabels(tryNode.setException(this.f30lc, getReplacement(tryNode.getException())));
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterBlock(Block block) {
                for (Symbol symbol : block.getSymbols()) {
                    Symbol replacement = getReplacement(symbol);
                    if (setNewSetFromMap != null) {
                        setNewSetFromMap.add(replacement);
                    }
                }
                return true;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveBlock(Block block) {
                return ensureUniqueLabels(block.replaceSymbols(this.f30lc, identityHashMap));
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveFunctionNode(FunctionNode functionNode2) {
                return functionNode2.setParameters(this.f30lc, functionNode2.visitParameters(this));
            }

            protected Node leaveDefault(Node node) {
                return ensureUniqueLabels(node);
            }

            private Node ensureUniqueLabels(Node node) {
                return zIsCached ? node.ensureUniqueLabels(this.f30lc) : node;
            }
        });
        if (setNewSetFromMap != null) {
            Block blockCopyWithNewSymbols = null;
            for (Symbol symbol : identityHashMap.values()) {
                if (!setNewSetFromMap.contains(symbol)) {
                    if (!$assertionsDisabled && !symbol.isScope()) {
                        throw new AssertionError();
                    }
                    if (!$assertionsDisabled && !this.externalScopeDepths.containsKey(symbol.getName())) {
                        throw new AssertionError();
                    }
                    symbol.setFlags((symbol.getFlags() & (-4)) | 1);
                    if (blockCopyWithNewSymbols == null) {
                        blockCopyWithNewSymbols = body.getBody().copyWithNewSymbols();
                        body = body.setBody(null, blockCopyWithNewSymbols);
                    }
                    if (!$assertionsDisabled && blockCopyWithNewSymbols.getExistingSymbol(symbol.getName()) != null) {
                        throw new AssertionError();
                    }
                    blockCopyWithNewSymbols.putSymbol(symbol);
                }
            }
        }
        return body.setCached(null);
    }

    private boolean getFunctionFlag(int i) {
        return (this.functionFlags & i) != 0;
    }

    private boolean isProgram() {
        return getFunctionFlag(8192);
    }

    TypeMap typeMap(MethodType methodType) {
        if (methodType == null || CompiledFunction.isVarArgsType(methodType)) {
            return null;
        }
        return new TypeMap(this.functionNodeId, explicitParams(methodType), needsCallee());
    }

    private static ScriptObject newLocals(ScriptObject scriptObject) {
        ScriptObject scriptObjectNewEmptyInstance = Global.newEmptyInstance();
        scriptObjectNewEmptyInstance.setProto(scriptObject);
        return scriptObjectNewEmptyInstance;
    }

    private Compiler getCompiler(FunctionNode functionNode, MethodType methodType, ScriptObject scriptObject) {
        return getCompiler(functionNode, methodType, newLocals(scriptObject), null, null);
    }

    private CodeInstaller getInstallerForNewCode() {
        ScriptEnvironment env = this.installer.getContext().getEnv();
        return (env._optimistic_types || env._loader_per_compile) ? this.installer.withNewLoader() : this.installer;
    }

    Compiler getCompiler(FunctionNode functionNode, MethodType methodType, ScriptObject scriptObject, Map map, int[] iArr) {
        TypeMap typeMap = typeMap(methodType);
        Object locationDescriptor = OptimisticTypesPersistence.getLocationDescriptor(this.source, this.functionNodeId, typeMap == null ? null : typeMap.getParameterTypes(this.functionNodeId));
        return Compiler.forOnDemandCompilation(getInstallerForNewCode(), functionNode.getSource(), isStrict() | functionNode.isStrict(), this, typeMap, getEffectiveInvalidatedProgramPoints(map, locationDescriptor), locationDescriptor, iArr, scriptObject);
    }

    private static Map getEffectiveInvalidatedProgramPoints(Map map, Object obj) {
        if (map != null) {
            return map;
        }
        Map mapLoad = OptimisticTypesPersistence.load(obj);
        return mapLoad != null ? mapLoad : new TreeMap();
    }

    private FunctionInitializer compileTypeSpecialization(MethodType methodType, ScriptObject scriptObject, boolean z) {
        if (this.log.isEnabled()) {
            this.log.info(new Object[]{"Parameter type specialization of '", this.functionName, "' signature: ", methodType});
        }
        String cacheKey = null;
        if (z && usePersistentCodeCache()) {
            TypeMap typeMap = typeMap(methodType);
            cacheKey = CodeStore.getCacheKey(Integer.valueOf(this.functionNodeId), typeMap == null ? null : typeMap.getParameterTypes(this.functionNodeId));
            CodeInstaller installerForNewCode = getInstallerForNewCode();
            StoredScript storedScriptLoadScript = installerForNewCode.loadScript(this.source, cacheKey);
            if (storedScriptLoadScript != null) {
                Compiler.updateCompilationId(storedScriptLoadScript.getCompilationId());
                return storedScriptLoadScript.installFunction(this, installerForNewCode);
            }
        }
        FunctionNode functionNodeReparse = reparse();
        Compiler compiler = getCompiler(functionNodeReparse, methodType, scriptObject);
        FunctionNode functionNodeCompile = compiler.compile(functionNodeReparse, functionNodeReparse.isCached() ? Compiler.CompilationPhases.COMPILE_ALL_CACHED : Compiler.CompilationPhases.COMPILE_ALL);
        if (z && !functionNodeCompile.hasApplyToCallSpecialization()) {
            compiler.persistClassInfo(cacheKey, functionNodeCompile);
        }
        return new FunctionInitializer(functionNodeCompile, compiler.getInvalidatedProgramPoints());
    }

    boolean usePersistentCodeCache() {
        return this.installer != null && this.installer.getContext().getEnv()._persistent_cache;
    }

    private MethodType explicitParams(MethodType methodType) {
        if (CompiledFunction.isVarArgsType(methodType)) {
            return null;
        }
        MethodType methodTypeDropParameterTypes = methodType.dropParameterTypes(0, 2);
        int iParameterCount = methodTypeDropParameterTypes.parameterCount();
        Class<?>[] clsArrParameterArray = methodTypeDropParameterTypes.parameterArray();
        boolean z = false;
        for (int i = 0; i < clsArrParameterArray.length; i++) {
            Class<?> cls = clsArrParameterArray[i];
            if (!cls.isPrimitive() && cls != Object.class) {
                clsArrParameterArray[i] = Object.class;
                z = true;
            }
        }
        MethodType methodType2 = z ? MethodType.methodType(methodTypeDropParameterTypes.returnType(), clsArrParameterArray) : methodTypeDropParameterTypes;
        if (iParameterCount < getArity()) {
            return methodType2.appendParameterTypes(Collections.nCopies(getArity() - iParameterCount, Object.class));
        }
        return methodType2;
    }

    private FunctionNode extractFunctionFromScript(FunctionNode functionNode) {
        HashSet hashSet = new HashSet();
        functionNode.getBody().accept(new SimpleNodeVisitor(this, hashSet) { // from class: jdk.nashorn.internal.runtime.RecompilableScriptFunctionData.4
            final Set val$fns;
            final RecompilableScriptFunctionData this$0;

            {
                this.this$0 = this;
                this.val$fns = hashSet;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterFunctionNode(FunctionNode functionNode2) {
                this.val$fns.add(functionNode2);
                return false;
            }
        });
        if (!$assertionsDisabled && hashSet.size() != 1) {
            throw new AssertionError("got back more than one method in recompilation");
        }
        FunctionNode functionNode2 = (FunctionNode) hashSet.iterator().next();
        if (!$assertionsDisabled && functionNode2.getId() != this.functionNodeId) {
            throw new AssertionError();
        }
        if (!getFunctionFlag(2) && functionNode2.isDeclared()) {
            return functionNode2.clearFlag((LexicalContext) null, 2);
        }
        return functionNode2;
    }

    private void logLookup(boolean z, MethodType methodType) {
        if (z && this.log.isEnabled()) {
            this.log.info(new Object[]{"Looking up ", DebugLogger.quote(this.functionName), " type=", methodType});
        }
    }

    private MethodHandle lookup(FunctionInitializer functionInitializer, boolean z) {
        MethodType methodType = functionInitializer.getMethodType();
        logLookup(z, methodType);
        return lookupCodeMethod(functionInitializer.getCode(), methodType);
    }

    MethodHandle lookup(FunctionNode functionNode) {
        MethodType methodType = new FunctionSignature(functionNode).getMethodType();
        logLookup(true, methodType);
        return lookupCodeMethod(functionNode.getCompileUnit().getCode(), methodType);
    }

    MethodHandle lookupCodeMethod(Class cls, MethodType methodType) {
        return Lookup.f31MH.findStatic(LOOKUP, cls, this.functionName, methodType);
    }

    public void initializeCode(FunctionNode functionNode) {
        if (!this.code.isEmpty() || functionNode.getId() != this.functionNodeId || !functionNode.getCompileUnit().isInitializing(this, functionNode)) {
            throw new IllegalStateException(this.name);
        }
        addCode(lookup(functionNode), null, null, functionNode.getFlags());
    }

    void initializeCode(FunctionInitializer functionInitializer) {
        addCode(lookup(functionInitializer, true), null, null, functionInitializer.getFlags());
    }

    private CompiledFunction addCode(MethodHandle methodHandle, Map map, MethodType methodType, int i) {
        CompiledFunction compiledFunction = new CompiledFunction(methodHandle, this, map, methodType, i);
        if (!$assertionsDisabled && !noDuplicateCode(compiledFunction)) {
            throw new AssertionError("duplicate code");
        }
        this.code.add(compiledFunction);
        return compiledFunction;
    }

    private CompiledFunction addCode(FunctionInitializer functionInitializer, MethodType methodType) {
        if (isVariableArity()) {
            return addCode(lookup(functionInitializer, true), functionInitializer.getInvalidatedProgramPoints(), methodType, functionInitializer.getFlags());
        }
        MethodType methodTypeType = lookup(functionInitializer, true).type();
        MethodType methodTypeChangeReturnType = (needsCallee(methodTypeType) ? methodType.changeParameterType(0, ScriptFunction.class) : methodType.dropParameterTypes(0, 1)).changeReturnType(methodTypeType.returnType());
        int iParameterCount = methodTypeChangeReturnType.parameterCount();
        int iParameterCount2 = methodTypeType.parameterCount();
        int iMin = Math.min(iParameterCount2, iParameterCount);
        for (int i = 0; i < iMin; i++) {
            Class<?> clsParameterType = methodTypeType.parameterType(i);
            Class<?> clsParameterType2 = methodTypeChangeReturnType.parameterType(i);
            if (clsParameterType != clsParameterType2 && !clsParameterType.isPrimitive() && !clsParameterType2.isPrimitive()) {
                if (!$assertionsDisabled && !clsParameterType.isAssignableFrom(clsParameterType2)) {
                    throw new AssertionError();
                }
                methodTypeChangeReturnType = methodTypeChangeReturnType.changeParameterType(i, clsParameterType);
            }
        }
        if (iParameterCount2 > iParameterCount) {
            methodTypeChangeReturnType = methodTypeChangeReturnType.appendParameterTypes(methodTypeType.parameterList().subList(iParameterCount, iParameterCount2));
        } else if (iParameterCount2 < iParameterCount) {
            methodTypeChangeReturnType = methodTypeChangeReturnType.dropParameterTypes(iParameterCount2, iParameterCount);
        }
        return addCode(lookup(functionInitializer, false).asType(methodTypeChangeReturnType), functionInitializer.getInvalidatedProgramPoints(), methodType, functionInitializer.getFlags());
    }

    public Class getReturnType(MethodType methodType, ScriptObject scriptObject) {
        return getBest(methodType, scriptObject, CompiledFunction.NO_FUNCTIONS).type().returnType();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    CompiledFunction getBest(MethodType methodType, ScriptObject scriptObject, Collection collection, boolean z) {
        if (!$assertionsDisabled && !isValidCallSite(methodType)) {
            throw new AssertionError(methodType);
        }
        CompiledFunction compiledFunctionPickFunction = pickFunction(methodType, false);
        if (compiledFunctionPickFunction == null) {
            compiledFunctionPickFunction = pickFunction(methodType, true);
        }
        if (compiledFunctionPickFunction == null) {
            compiledFunctionPickFunction = addCode(compileTypeSpecialization(methodType, scriptObject, true), methodType);
        }
        if (!$assertionsDisabled && compiledFunctionPickFunction == null) {
            throw new AssertionError();
        }
        if (compiledFunctionPickFunction.isApplyToCall()) {
            CompiledFunction compiledFunctionLookupExactApplyToCall = lookupExactApplyToCall(methodType);
            if (compiledFunctionLookupExactApplyToCall != null) {
                return compiledFunctionLookupExactApplyToCall;
            }
            compiledFunctionPickFunction = addCode(compileTypeSpecialization(methodType, scriptObject, false), methodType);
        }
        return compiledFunctionPickFunction;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    public boolean needsCallee() {
        return getFunctionFlag(67108864);
    }

    public int getFunctionFlags() {
        return this.functionFlags;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    MethodType getGenericType() {
        if (isVariableArity()) {
            return MethodType.genericMethodType(2, true);
        }
        return MethodType.genericMethodType(2 + getArity());
    }

    public int getFunctionNodeId() {
        return this.functionNodeId;
    }

    public Source getSource() {
        return this.source;
    }

    public RecompilableScriptFunctionData getScriptFunctionData(int i) {
        if (i == this.functionNodeId) {
            return this;
        }
        RecompilableScriptFunctionData recompilableScriptFunctionData = this.nestedFunctions == null ? null : (RecompilableScriptFunctionData) this.nestedFunctions.get(Integer.valueOf(i));
        if (recompilableScriptFunctionData != null) {
            return recompilableScriptFunctionData;
        }
        Iterator it = this.nestedFunctions.values().iterator();
        while (it.hasNext()) {
            RecompilableScriptFunctionData scriptFunctionData = ((RecompilableScriptFunctionData) it.next()).getScriptFunctionData(i);
            if (scriptFunctionData != null) {
                return scriptFunctionData;
            }
        }
        return null;
    }

    public boolean isGlobalSymbol(FunctionNode functionNode, String str) {
        RecompilableScriptFunctionData scriptFunctionData = getScriptFunctionData(functionNode.getId());
        if (!$assertionsDisabled && scriptFunctionData == null) {
            throw new AssertionError();
        }
        while (!scriptFunctionData.hasInternalSymbol(str)) {
            scriptFunctionData = scriptFunctionData.getParent();
            if (scriptFunctionData == null) {
                return true;
            }
        }
        return false;
    }

    public FunctionNode restoreFlags(LexicalContext lexicalContext, FunctionNode functionNode) {
        if (!$assertionsDisabled && functionNode.getId() != this.functionNodeId) {
            throw new AssertionError();
        }
        FunctionNode flags = functionNode.setFlags(lexicalContext, this.functionFlags);
        if (flags.hasNestedEval()) {
            if (!$assertionsDisabled && !flags.hasScopeBlock()) {
                throw new AssertionError();
            }
            flags = flags.setBody(lexicalContext, flags.getBody().setNeedsScope(null));
        }
        return flags;
    }

    private boolean noDuplicateCode(CompiledFunction compiledFunction) {
        Iterator it = this.code.iterator();
        while (it.hasNext()) {
            if (((CompiledFunction) it.next()).type().equals(compiledFunction.type())) {
                return false;
            }
        }
        return true;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        createLogger();
    }

    private void createLogger() {
        this.log = initLogger(Context.getContextTrusted());
    }
}
