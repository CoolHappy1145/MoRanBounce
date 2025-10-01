package jdk.nashorn.internal.codegen;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.debug.ASTWriter;
import jdk.nashorn.internal.p001ir.debug.PrintVisitor;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.CodeInstaller;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.logging.DebugLogger;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase.class */
abstract class CompilationPhase {
    static final CompilationPhase CONSTANT_FOLDING_PHASE;
    static final CompilationPhase LOWERING_PHASE;
    static final CompilationPhase APPLY_SPECIALIZATION_PHASE;
    static final CompilationPhase SPLITTING_PHASE;
    static final CompilationPhase PROGRAM_POINT_PHASE;
    static final CompilationPhase CACHE_AST_PHASE;
    static final CompilationPhase SYMBOL_ASSIGNMENT_PHASE;
    static final CompilationPhase SCOPE_DEPTH_COMPUTATION_PHASE;
    static final CompilationPhase DECLARE_LOCAL_SYMBOLS_PHASE;
    static final CompilationPhase OPTIMISTIC_TYPE_ASSIGNMENT_PHASE;
    static final CompilationPhase LOCAL_VARIABLE_TYPE_CALCULATION_PHASE;
    static final CompilationPhase REUSE_COMPILE_UNITS_PHASE;
    static final CompilationPhase REINITIALIZE_CACHED;
    static final CompilationPhase BYTECODE_GENERATION_PHASE;
    static final CompilationPhase INSTALL_PHASE;
    private long startTime;
    private long endTime;
    private boolean isFinished;
    static final boolean $assertionsDisabled;

    abstract FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode);

    CompilationPhase(C01051 c01051) {
        this();
    }

    static {
        $assertionsDisabled = !CompilationPhase.class.desiredAssertionStatus();
        CONSTANT_FOLDING_PHASE = new ConstantFoldingPhase(null);
        LOWERING_PHASE = new LoweringPhase(null);
        APPLY_SPECIALIZATION_PHASE = new ApplySpecializationPhase(null);
        SPLITTING_PHASE = new SplittingPhase(null);
        PROGRAM_POINT_PHASE = new ProgramPointPhase(null);
        CACHE_AST_PHASE = new CacheAstPhase(null);
        SYMBOL_ASSIGNMENT_PHASE = new SymbolAssignmentPhase(null);
        SCOPE_DEPTH_COMPUTATION_PHASE = new ScopeDepthComputationPhase(null);
        DECLARE_LOCAL_SYMBOLS_PHASE = new DeclareLocalSymbolsPhase(null);
        OPTIMISTIC_TYPE_ASSIGNMENT_PHASE = new OptimisticTypeAssignmentPhase(null);
        LOCAL_VARIABLE_TYPE_CALCULATION_PHASE = new LocalVariableTypeCalculationPhase(null);
        REUSE_COMPILE_UNITS_PHASE = new ReuseCompileUnitsPhase(null);
        REINITIALIZE_CACHED = new ReinitializeCachedPhase(null);
        BYTECODE_GENERATION_PHASE = new BytecodeGenerationPhase(null);
        INSTALL_PHASE = new InstallPhase(null);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$ConstantFoldingPhase.class */
    private static final class ConstantFoldingPhase extends CompilationPhase {
        private ConstantFoldingPhase() {
            super(null);
        }

        ConstantFoldingPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            return CompilationPhase.transformFunction(functionNode, new FoldConstants(compiler));
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$LoweringPhase.class */
    private static final class LoweringPhase extends CompilationPhase {
        private LoweringPhase() {
            super(null);
        }

        LoweringPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            return CompilationPhase.transformFunction(functionNode, new Lower(compiler));
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$ApplySpecializationPhase.class */
    private static final class ApplySpecializationPhase extends CompilationPhase {
        private ApplySpecializationPhase() {
            super(null);
        }

        ApplySpecializationPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            return CompilationPhase.transformFunction(functionNode, new ApplySpecialization(compiler));
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$SplittingPhase.class */
    private static final class SplittingPhase extends CompilationPhase {
        static final boolean $assertionsDisabled;

        private SplittingPhase() {
            super(null);
        }

        SplittingPhase(C01051 c01051) {
            this();
        }

        static {
            $assertionsDisabled = !CompilationPhase.class.desiredAssertionStatus();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            CompileUnit compileUnitAddCompileUnit = compiler.addCompileUnit(0L);
            FunctionNode functionNodeTransformFunction = CompilationPhase.transformFunction(functionNode, new SimpleNodeVisitor(this) { // from class: jdk.nashorn.internal.codegen.CompilationPhase.SplittingPhase.1
                final SplittingPhase this$0;

                {
                    this.this$0 = this;
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public Node leaveLiteralNode(LiteralNode literalNode) {
                    return leaveLiteralNode(literalNode);
                }

                /* JADX WARN: Type inference failed for: r1v1, types: [jdk.nashorn.internal.ir.LexicalContext, jdk.nashorn.internal.ir.LiteralNode] */
                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public LiteralNode leaveLiteralNode(LiteralNode literalNode) {
                    return this.f30lc;
                }
            });
            FunctionNode functionNodeTransformFunction2 = CompilationPhase.transformFunction(new Splitter(compiler, functionNodeTransformFunction, compileUnitAddCompileUnit).split(functionNodeTransformFunction, true), new SplitIntoFunctions(compiler));
            if (!$assertionsDisabled && functionNodeTransformFunction2.getCompileUnit() != compileUnitAddCompileUnit) {
                throw new AssertionError("fn=" + functionNode.getName() + ", fn.compileUnit (" + functionNodeTransformFunction2.getCompileUnit() + ") != " + compileUnitAddCompileUnit);
            }
            if ($assertionsDisabled || functionNodeTransformFunction2.isStrict() == compiler.isStrict()) {
                return functionNodeTransformFunction2;
            }
            throw new AssertionError("functionNode.isStrict() != compiler.isStrict() for " + DebugLogger.quote(functionNodeTransformFunction2.getName()));
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$ProgramPointPhase.class */
    private static final class ProgramPointPhase extends CompilationPhase {
        private ProgramPointPhase() {
            super(null);
        }

        ProgramPointPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            return CompilationPhase.transformFunction(functionNode, new ProgramPoints());
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$CacheAstPhase.class */
    private static final class CacheAstPhase extends CompilationPhase {
        private CacheAstPhase() {
            super(null);
        }

        CacheAstPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            if (!compiler.isOnDemandCompilation()) {
                CompilationPhase.transformFunction(functionNode, new CacheAst(compiler));
            }
            return functionNode;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$SymbolAssignmentPhase.class */
    private static final class SymbolAssignmentPhase extends CompilationPhase {
        private SymbolAssignmentPhase() {
            super(null);
        }

        SymbolAssignmentPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            return CompilationPhase.transformFunction(functionNode, new AssignSymbols(compiler));
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$ScopeDepthComputationPhase.class */
    private static final class ScopeDepthComputationPhase extends CompilationPhase {
        private ScopeDepthComputationPhase() {
            super(null);
        }

        ScopeDepthComputationPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            return CompilationPhase.transformFunction(functionNode, new FindScopeDepths(compiler));
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$DeclareLocalSymbolsPhase.class */
    private static final class DeclareLocalSymbolsPhase extends CompilationPhase {
        private DeclareLocalSymbolsPhase() {
            super(null);
        }

        DeclareLocalSymbolsPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            if (compiler.useOptimisticTypes() && compiler.isOnDemandCompilation()) {
                functionNode.getBody().accept(new SimpleNodeVisitor(this, compiler) { // from class: jdk.nashorn.internal.codegen.CompilationPhase.DeclareLocalSymbolsPhase.1
                    final Compiler val$compiler;
                    final DeclareLocalSymbolsPhase this$0;

                    {
                        this.this$0 = this;
                        this.val$compiler = compiler;
                    }

                    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                    public boolean enterBlock(Block block) {
                        for (Symbol symbol : block.getSymbols()) {
                            if (!symbol.isScope()) {
                                this.val$compiler.declareLocalSymbol(symbol.getName());
                            }
                        }
                        return true;
                    }
                });
            }
            return functionNode;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$OptimisticTypeAssignmentPhase.class */
    private static final class OptimisticTypeAssignmentPhase extends CompilationPhase {
        private OptimisticTypeAssignmentPhase() {
            super(null);
        }

        OptimisticTypeAssignmentPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            if (compiler.useOptimisticTypes()) {
                return CompilationPhase.transformFunction(functionNode, new OptimisticTypesCalculator(compiler));
            }
            return functionNode;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$LocalVariableTypeCalculationPhase.class */
    private static final class LocalVariableTypeCalculationPhase extends CompilationPhase {
        private LocalVariableTypeCalculationPhase() {
            super(null);
        }

        LocalVariableTypeCalculationPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            FunctionNode functionNodeTransformFunction = CompilationPhase.transformFunction(functionNode, new LocalVariableTypesCalculator(compiler));
            ScriptEnvironment scriptEnvironment = compiler.getScriptEnvironment();
            PrintWriter err = scriptEnvironment.getErr();
            if (scriptEnvironment._print_lower_ast || functionNode.getFlag(1048576)) {
                err.println("Lower AST for: " + DebugLogger.quote(functionNodeTransformFunction.getName()));
                err.println(new ASTWriter(functionNodeTransformFunction));
            }
            if (scriptEnvironment._print_lower_parse || functionNode.getFlag(262144)) {
                err.println("Lower AST for: " + DebugLogger.quote(functionNodeTransformFunction.getName()));
                err.println(new PrintVisitor(functionNodeTransformFunction));
            }
            return functionNodeTransformFunction;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$ReuseCompileUnitsPhase.class */
    private static final class ReuseCompileUnitsPhase extends CompilationPhase {
        static final boolean $assertionsDisabled;

        private ReuseCompileUnitsPhase() {
            super(null);
        }

        ReuseCompileUnitsPhase(C01051 c01051) {
            this();
        }

        static {
            $assertionsDisabled = !CompilationPhase.class.desiredAssertionStatus();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            if (!$assertionsDisabled && !compilationPhases.isRestOfCompilation()) {
                throw new AssertionError("reuse compile units currently only used for Rest-Of methods");
            }
            HashMap map = new HashMap();
            Set setCreateCompileUnitSet = CompileUnit.createCompileUnitSet();
            DebugLogger logger = compiler.getLogger();
            logger.fine("Clearing bytecode cache");
            compiler.clearBytecode();
            for (CompileUnit compileUnit : compiler.getCompileUnits()) {
                if (!$assertionsDisabled && map.get(compileUnit) != null) {
                    throw new AssertionError();
                }
                CompileUnit compileUnitCreateNewCompileUnit = CompilationPhase.createNewCompileUnit(compiler, compilationPhases);
                logger.fine(new Object[]{"Creating new compile unit ", compileUnit, " => ", compileUnitCreateNewCompileUnit});
                map.put(compileUnit, compileUnitCreateNewCompileUnit);
                if (!$assertionsDisabled && compileUnitCreateNewCompileUnit == null) {
                    throw new AssertionError();
                }
                setCreateCompileUnitSet.add(compileUnitCreateNewCompileUnit);
            }
            logger.fine("Replacing compile units in Compiler...");
            compiler.replaceCompileUnits(setCreateCompileUnitSet);
            logger.fine("Done");
            return CompilationPhase.transformFunction(functionNode, new ReplaceCompileUnits(this, map) { // from class: jdk.nashorn.internal.codegen.CompilationPhase.ReuseCompileUnitsPhase.1
                final Map val$map;
                final ReuseCompileUnitsPhase this$0;

                {
                    this.this$0 = this;
                    this.val$map = map;
                }

                @Override // jdk.nashorn.internal.codegen.ReplaceCompileUnits
                CompileUnit getReplacement(CompileUnit compileUnit2) {
                    return (CompileUnit) this.val$map.get(compileUnit2);
                }

                /* JADX WARN: Type inference failed for: r1v1, types: [jdk.nashorn.internal.ir.LexicalContext, jdk.nashorn.internal.ir.Node] */
                public Node leaveDefault(Node node) {
                    return this.f30lc;
                }
            });
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$ReinitializeCachedPhase.class */
    private static final class ReinitializeCachedPhase extends CompilationPhase {
        private ReinitializeCachedPhase() {
            super(null);
        }

        ReinitializeCachedPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            Set setCreateCompileUnitSet = CompileUnit.createCompileUnitSet();
            HashMap map = new HashMap();
            createCompileUnit(functionNode.getCompileUnit(), setCreateCompileUnitSet, map, compiler, compilationPhases);
            FunctionNode functionNodeTransformFunction = CompilationPhase.transformFunction(functionNode, new ReplaceCompileUnits(this, map, setCreateCompileUnitSet, compiler, compilationPhases) { // from class: jdk.nashorn.internal.codegen.CompilationPhase.ReinitializeCachedPhase.1
                final Map val$unitMap;
                final Set val$unitSet;
                final Compiler val$compiler;
                final Compiler.CompilationPhases val$phases;
                final ReinitializeCachedPhase this$0;

                {
                    this.this$0 = this;
                    this.val$unitMap = map;
                    this.val$unitSet = setCreateCompileUnitSet;
                    this.val$compiler = compiler;
                    this.val$phases = compilationPhases;
                }

                @Override // jdk.nashorn.internal.codegen.ReplaceCompileUnits
                CompileUnit getReplacement(CompileUnit compileUnit) {
                    CompileUnit compileUnit2 = (CompileUnit) this.val$unitMap.get(compileUnit);
                    if (compileUnit2 == null) {
                        return this.this$0.createCompileUnit(compileUnit, this.val$unitSet, this.val$unitMap, this.val$compiler, this.val$phases);
                    }
                    return compileUnit2;
                }

                @Override // jdk.nashorn.internal.codegen.ReplaceCompileUnits, jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public Node leaveFunctionNode(FunctionNode functionNode2) {
                    return super.leaveFunctionNode(this.val$compiler.getScriptFunctionData(functionNode2.getId()).restoreFlags(this.f30lc, functionNode2));
                }
            });
            compiler.replaceCompileUnits(setCreateCompileUnitSet);
            return functionNodeTransformFunction;
        }

        private CompileUnit createCompileUnit(CompileUnit compileUnit, Set set, Map map, Compiler compiler, Compiler.CompilationPhases compilationPhases) {
            CompileUnit compileUnitCreateNewCompileUnit = CompilationPhase.createNewCompileUnit(compiler, compilationPhases);
            map.put(compileUnit, compileUnitCreateNewCompileUnit);
            set.add(compileUnitCreateNewCompileUnit);
            return compileUnitCreateNewCompileUnit;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$BytecodeGenerationPhase.class */
    private static final class BytecodeGenerationPhase extends CompilationPhase {
        static final boolean $assertionsDisabled;

        private BytecodeGenerationPhase() {
            super(null);
        }

        BytecodeGenerationPhase(C01051 c01051) {
            this();
        }

        static {
            $assertionsDisabled = !CompilationPhase.class.desiredAssertionStatus();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) throws IOException {
            ScriptEnvironment scriptEnvironment = compiler.getScriptEnvironment();
            FunctionNode functionNodeTransformFunction = functionNode;
            functionNode.getCompileUnit().setUsed();
            compiler.getLogger().fine(new Object[]{"Starting bytecode generation for ", DebugLogger.quote(functionNode.getName()), " - restOf=", Boolean.valueOf(compilationPhases.isRestOfCompilation())});
            CodeGenerator codeGenerator = new CodeGenerator(compiler, compilationPhases.isRestOfCompilation() ? compiler.getContinuationEntryPoints() : null);
            try {
                functionNodeTransformFunction = CompilationPhase.transformFunction(functionNodeTransformFunction, codeGenerator);
                codeGenerator.generateScopeCalls();
            } catch (VerifyError e) {
                if (scriptEnvironment._verify_code || scriptEnvironment._print_code) {
                    scriptEnvironment.getErr().println(e.getClass().getSimpleName() + ": " + e.getMessage());
                    if (scriptEnvironment._dump_on_error) {
                        e.printStackTrace(scriptEnvironment.getErr());
                    }
                } else {
                    throw e;
                }
            } catch (Throwable th) {
                throw new AssertionError("Failed generating bytecode for " + functionNode.getSourceName() + CallSiteDescriptor.TOKEN_DELIMITER + codeGenerator.getLastLineNumber(), th);
            }
            for (CompileUnit compileUnit : compiler.getCompileUnits()) {
                ClassEmitter classEmitter = compileUnit.getClassEmitter();
                classEmitter.end();
                if (!compileUnit.isUsed()) {
                    compiler.getLogger().fine(new Object[]{"Skipping unused compile unit ", compileUnit});
                } else {
                    byte[] byteArray = classEmitter.toByteArray();
                    if (!$assertionsDisabled && byteArray == null) {
                        throw new AssertionError();
                    }
                    String unitClassName = compileUnit.getUnitClassName();
                    compiler.addClass(unitClassName, byteArray);
                    CompileUnit.increaseEmitCount();
                    if (scriptEnvironment._verify_code) {
                        compiler.getCodeInstaller().verify(byteArray);
                    }
                    DumpBytecode.dumpBytecode(scriptEnvironment, compiler.getLogger(), byteArray, unitClassName);
                }
            }
            return functionNodeTransformFunction;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationPhase$InstallPhase.class */
    private static final class InstallPhase extends CompilationPhase {
        private InstallPhase() {
            super(null);
        }

        InstallPhase(C01051 c01051) {
            this();
        }

        @Override // jdk.nashorn.internal.codegen.CompilationPhase
        FunctionNode transform(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
            DebugLogger logger = compiler.getLogger();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            boolean z = true;
            Class cls = null;
            long length = 0;
            CodeInstaller codeInstaller = compiler.getCodeInstaller();
            for (Map.Entry entry : compiler.getBytecode().entrySet()) {
                String str = (String) entry.getKey();
                length += r0.length;
                Class clsInstall = codeInstaller.install(str, (byte[]) entry.getValue());
                if (z) {
                    cls = clsInstall;
                    z = false;
                }
                linkedHashMap.put(str, clsInstall);
            }
            if (cls == null) {
                throw new CompilationException("Internal compiler error: root class not found!");
            }
            Object[] array = compiler.getConstantData().toArray();
            codeInstaller.initialize(linkedHashMap.values(), compiler.getSource(), array);
            for (Object obj : array) {
                if (obj instanceof RecompilableScriptFunctionData) {
                    ((RecompilableScriptFunctionData) obj).initTransients(compiler.getSource(), codeInstaller);
                }
            }
            for (CompileUnit compileUnit : compiler.getCompileUnits()) {
                if (compileUnit.isUsed()) {
                    compileUnit.setCode((Class) linkedHashMap.get(compileUnit.getUnitClassName()));
                    compileUnit.initializeFunctionsCode();
                }
            }
            if (logger.isEnabled()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Installed class '").append(cls.getSimpleName()).append('\'').append(" [").append(cls.getName()).append(", size=").append(length).append(" bytes, ").append(compiler.getCompileUnits().size()).append(" compile unit(s)]");
                logger.fine(sb.toString());
            }
            return functionNode.setRootClass(null, cls);
        }
    }

    private CompilationPhase() {
    }

    protected FunctionNode begin(Compiler compiler, FunctionNode functionNode) {
        compiler.getLogger().indent();
        this.startTime = System.nanoTime();
        return functionNode;
    }

    protected FunctionNode end(Compiler compiler, FunctionNode functionNode) {
        compiler.getLogger().unindent();
        this.endTime = System.nanoTime();
        compiler.getScriptEnvironment()._timing.accumulateTime(toString(), this.endTime - this.startTime);
        this.isFinished = true;
        return functionNode;
    }

    boolean isFinished() {
        return this.isFinished;
    }

    long getStartTime() {
        return this.startTime;
    }

    long getEndTime() {
        return this.endTime;
    }

    final FunctionNode apply(Compiler compiler, Compiler.CompilationPhases compilationPhases, FunctionNode functionNode) {
        if ($assertionsDisabled || compilationPhases.contains(this)) {
            return end(compiler, transform(compiler, compilationPhases, begin(compiler, functionNode)));
        }
        throw new AssertionError();
    }

    private static FunctionNode transformFunction(FunctionNode functionNode, NodeVisitor nodeVisitor) {
        return (FunctionNode) functionNode.accept(nodeVisitor);
    }

    private static CompileUnit createNewCompileUnit(Compiler compiler, Compiler.CompilationPhases compilationPhases) {
        StringBuilder sb = new StringBuilder(compiler.nextCompileUnitName());
        if (compilationPhases.isRestOfCompilation()) {
            sb.append("$restOf");
        }
        return compiler.createCompileUnit(sb.toString(), 0L);
    }
}
