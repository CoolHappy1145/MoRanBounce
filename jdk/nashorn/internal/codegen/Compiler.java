package jdk.nashorn.internal.codegen;

import java.io.File;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Level;
import jdk.internal.dynalink.support.NameCodec;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.Optimistic;
import jdk.nashorn.internal.p001ir.debug.ClassHistogramElement;
import jdk.nashorn.internal.p001ir.debug.ObjectSizeCalculator;
import jdk.nashorn.internal.runtime.CodeInstaller;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.FunctionInitializer;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "compiler")
/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/Compiler.class */
public final class Compiler implements Loggable {
    public static final String SCRIPTS_PACKAGE = "jdk/nashorn/internal/scripts";
    public static final String OBJECTS_PACKAGE = "jdk/nashorn/internal/objects";
    private final ScriptEnvironment env;
    private final Source source;
    private final String sourceName;
    private final ErrorManager errors;
    private final boolean optimistic;
    private final Map bytecode;
    private final Set compileUnits;
    private final ConstantData constantData;
    private final CodeInstaller installer;
    private final DebugLogger log;
    private final Context context;
    private final TypeMap types;
    private final TypeEvaluator typeEvaluator;
    private final boolean strict;
    private final boolean onDemand;
    private final Map invalidatedProgramPoints;
    private final Object typeInformationFile;
    private final String firstCompileUnitName;
    private final int[] continuationEntryPoints;
    private RecompilableScriptFunctionData compiledFunction;
    private static final int COMPILE_UNIT_NAME_BUFFER_SIZE = 32;
    private static String[] RESERVED_NAMES;
    private final int compilationId;
    private final AtomicInteger nextCompileUnitId;
    private static final AtomicInteger COMPILATION_ID;
    private static final String DANGEROUS_CHARS = "\\/.;:$[]<>";
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Compiler.class.desiredAssertionStatus();
        RESERVED_NAMES = new String[]{CompilerConstants.SCOPE.symbolName(), CompilerConstants.THIS.symbolName(), CompilerConstants.RETURN.symbolName(), CompilerConstants.CALLEE.symbolName(), CompilerConstants.VARARGS.symbolName(), CompilerConstants.ARGUMENTS.symbolName()};
        COMPILATION_ID = new AtomicInteger(0);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/Compiler$CompilationPhases.class */
    public static class CompilationPhases implements Iterable {
        private static final CompilationPhases COMPILE_UPTO_CACHED = new CompilationPhases("Common initial phases", new CompilationPhase[]{CompilationPhase.CONSTANT_FOLDING_PHASE, CompilationPhase.LOWERING_PHASE, CompilationPhase.APPLY_SPECIALIZATION_PHASE, CompilationPhase.SPLITTING_PHASE, CompilationPhase.PROGRAM_POINT_PHASE, CompilationPhase.SYMBOL_ASSIGNMENT_PHASE, CompilationPhase.SCOPE_DEPTH_COMPUTATION_PHASE, CompilationPhase.CACHE_AST_PHASE});
        private static final CompilationPhases COMPILE_CACHED_UPTO_BYTECODE = new CompilationPhases("After common phases, before bytecode generator", new CompilationPhase[]{CompilationPhase.OPTIMISTIC_TYPE_ASSIGNMENT_PHASE, CompilationPhase.LOCAL_VARIABLE_TYPE_CALCULATION_PHASE});
        public static final CompilationPhases RECOMPILE_CACHED_UPTO_BYTECODE = new CompilationPhases("Recompile cached function up to bytecode", CompilationPhase.REINITIALIZE_CACHED, COMPILE_CACHED_UPTO_BYTECODE);
        public static final CompilationPhases GENERATE_BYTECODE_AND_INSTALL = new CompilationPhases("Generate bytecode and install", new CompilationPhase[]{CompilationPhase.BYTECODE_GENERATION_PHASE, CompilationPhase.INSTALL_PHASE});
        public static final CompilationPhases COMPILE_UPTO_BYTECODE = new CompilationPhases("Compile upto bytecode", new CompilationPhases[]{COMPILE_UPTO_CACHED, COMPILE_CACHED_UPTO_BYTECODE});
        public static final CompilationPhases COMPILE_ALL_NO_INSTALL = new CompilationPhases("Compile without install", COMPILE_UPTO_BYTECODE, new CompilationPhase[]{CompilationPhase.BYTECODE_GENERATION_PHASE});
        public static final CompilationPhases COMPILE_ALL = new CompilationPhases("Full eager compilation", new CompilationPhases[]{COMPILE_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL});
        public static final CompilationPhases COMPILE_ALL_CACHED = new CompilationPhases("Eager compilation from serializaed state", new CompilationPhases[]{RECOMPILE_CACHED_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL});
        public static final CompilationPhases GENERATE_BYTECODE_AND_INSTALL_RESTOF = new CompilationPhases("Generate bytecode and install - RestOf method", CompilationPhase.REUSE_COMPILE_UNITS_PHASE, GENERATE_BYTECODE_AND_INSTALL);
        public static final CompilationPhases COMPILE_ALL_RESTOF = new CompilationPhases("Compile all, rest of", new CompilationPhases[]{COMPILE_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL_RESTOF});
        public static final CompilationPhases COMPILE_CACHED_RESTOF = new CompilationPhases("Compile serialized, rest of", new CompilationPhases[]{RECOMPILE_CACHED_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL_RESTOF});
        private final List phases;
        private final String desc;

        private CompilationPhases(String str, CompilationPhase[] compilationPhaseArr) {
            this(str, Arrays.asList(compilationPhaseArr));
        }

        private CompilationPhases(String str, CompilationPhases compilationPhases, CompilationPhase[] compilationPhaseArr) {
            this(str, concat(compilationPhases.phases, Arrays.asList(compilationPhaseArr)));
        }

        private CompilationPhases(String str, CompilationPhase compilationPhase, CompilationPhases compilationPhases) {
            this(str, concat(Collections.singletonList(compilationPhase), compilationPhases.phases));
        }

        private CompilationPhases(String str, CompilationPhases compilationPhases) {
            this(str, compilationPhases.phases);
        }

        private CompilationPhases(String str, CompilationPhases[] compilationPhasesArr) {
            this(str, concatPhases(compilationPhasesArr));
        }

        private CompilationPhases(String str, List list) {
            this.desc = str;
            this.phases = list;
        }

        private static List concatPhases(CompilationPhases[] compilationPhasesArr) {
            ArrayList arrayList = new ArrayList();
            for (CompilationPhases compilationPhases : compilationPhasesArr) {
                arrayList.addAll(compilationPhases.phases);
            }
            arrayList.trimToSize();
            return arrayList;
        }

        private static List concat(List list, List list2) {
            ArrayList arrayList = new ArrayList(list);
            arrayList.addAll(list2);
            arrayList.trimToSize();
            return arrayList;
        }

        public String toString() {
            return "'" + this.desc + "' " + this.phases.toString();
        }

        boolean contains(CompilationPhase compilationPhase) {
            return this.phases.contains(compilationPhase);
        }

        @Override // java.lang.Iterable
        public Iterator iterator() {
            return this.phases.iterator();
        }

        boolean isRestOfCompilation() {
            return this == COMPILE_ALL_RESTOF || this == GENERATE_BYTECODE_AND_INSTALL_RESTOF || this == COMPILE_CACHED_RESTOF;
        }

        String getDesc() {
            return this.desc;
        }

        String toString(String str) {
            StringBuilder sb = new StringBuilder();
            Iterator it = this.phases.iterator();
            while (it.hasNext()) {
                sb.append(str).append((CompilationPhase) it.next()).append('\n');
            }
            return sb.toString();
        }
    }

    public static Compiler forInitialCompilation(CodeInstaller codeInstaller, Source source, ErrorManager errorManager, boolean z) {
        return new Compiler(codeInstaller.getContext(), codeInstaller, source, errorManager, z);
    }

    public static Compiler forNoInstallerCompilation(Context context, Source source, boolean z) {
        return new Compiler(context, null, source, context.getErrorManager(), z);
    }

    public static Compiler forOnDemandCompilation(CodeInstaller codeInstaller, Source source, boolean z, RecompilableScriptFunctionData recompilableScriptFunctionData, TypeMap typeMap, Map map, Object obj, int[] iArr, ScriptObject scriptObject) {
        Context context = codeInstaller.getContext();
        return new Compiler(context, codeInstaller, source, context.getErrorManager(), z, true, recompilableScriptFunctionData, typeMap, map, obj, iArr, scriptObject);
    }

    private Compiler(Context context, CodeInstaller codeInstaller, Source source, ErrorManager errorManager, boolean z) {
        this(context, codeInstaller, source, errorManager, z, false, null, null, null, null, null, null);
    }

    private Compiler(Context context, CodeInstaller codeInstaller, Source source, ErrorManager errorManager, boolean z, boolean z2, RecompilableScriptFunctionData recompilableScriptFunctionData, TypeMap typeMap, Map map, Object obj, int[] iArr, ScriptObject scriptObject) {
        this.compilationId = COMPILATION_ID.getAndIncrement();
        this.nextCompileUnitId = new AtomicInteger(0);
        this.context = context;
        this.env = context.getEnv();
        this.installer = codeInstaller;
        this.constantData = new ConstantData();
        this.compileUnits = CompileUnit.createCompileUnitSet();
        this.bytecode = new LinkedHashMap();
        this.log = initLogger(context);
        this.source = source;
        this.errors = errorManager;
        this.sourceName = FunctionNode.getSourceName(source);
        this.onDemand = z2;
        this.compiledFunction = recompilableScriptFunctionData;
        this.types = typeMap;
        this.invalidatedProgramPoints = map == null ? new HashMap() : map;
        this.typeInformationFile = obj;
        this.continuationEntryPoints = iArr == null ? null : (int[]) iArr.clone();
        this.typeEvaluator = new TypeEvaluator(this, scriptObject);
        this.firstCompileUnitName = firstCompileUnitName();
        this.strict = z;
        this.optimistic = this.env._optimistic_types;
    }

    private String safeSourceName() {
        String name = new File(this.source.getName()).getName();
        int iLastIndexOf = name.lastIndexOf(".js");
        if (iLastIndexOf != -1) {
            name = name.substring(0, iLastIndexOf);
        }
        String strReplace = name.replace('.', '_').replace('-', '_');
        if (!this.env._loader_per_compile) {
            strReplace = strReplace + this.installer.getUniqueScriptId();
        }
        String strReplaceDangerChars = this.env._verify_code ? replaceDangerChars(strReplace) : NameCodec.encode(strReplace);
        return strReplaceDangerChars != null ? strReplaceDangerChars : strReplace;
    }

    private static String replaceDangerChars(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (DANGEROUS_CHARS.indexOf(cCharAt) != -1) {
                sb.append('_');
            } else {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    private String firstCompileUnitName() {
        StringBuilder sbAppend = new StringBuilder(SCRIPTS_PACKAGE).append('/').append(CompilerConstants.DEFAULT_SCRIPT_NAME.symbolName()).append('$');
        if (isOnDemandCompilation()) {
            sbAppend.append(RecompilableScriptFunctionData.RECOMPILATION_PREFIX);
        }
        if (this.compilationId > 0) {
            sbAppend.append(this.compilationId).append('$');
        }
        if (this.types != null && this.compiledFunction.getFunctionNodeId() > 0) {
            sbAppend.append(this.compiledFunction.getFunctionNodeId());
            for (Type type : this.types.getParameterTypes(this.compiledFunction.getFunctionNodeId())) {
                sbAppend.append(Type.getShortSignatureDescriptor(type));
            }
            sbAppend.append('$');
        }
        sbAppend.append(safeSourceName());
        return sbAppend.toString();
    }

    void declareLocalSymbol(String str) {
        this.typeEvaluator.declareLocalSymbol(str);
    }

    void setData(RecompilableScriptFunctionData recompilableScriptFunctionData) {
        if (!$assertionsDisabled && this.compiledFunction != null) {
            throw new AssertionError(recompilableScriptFunctionData);
        }
        this.compiledFunction = recompilableScriptFunctionData;
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        boolean z = this.env._optimistic_types;
        return context.getLogger(getClass(), new Consumer(this, this.env._lazy_compilation, z) { // from class: jdk.nashorn.internal.codegen.Compiler.1
            final boolean val$lazyCompilation;
            final boolean val$optimisticTypes;
            final Compiler this$0;

            {
                this.this$0 = this;
                this.val$lazyCompilation = z;
                this.val$optimisticTypes = z;
            }

            @Override // java.util.function.Consumer
            public void accept(Object obj) {
                accept((DebugLogger) obj);
            }

            public void accept(DebugLogger debugLogger) {
                if (!this.val$lazyCompilation) {
                    debugLogger.warning("WARNING: Running with lazy compilation switched off. This is not a default setting.");
                }
                Object[] objArr = new Object[2];
                objArr[0] = "Optimistic types are ";
                objArr[1] = this.val$optimisticTypes ? "ENABLED." : "DISABLED.";
                debugLogger.warning(objArr);
            }
        });
    }

    ScriptEnvironment getScriptEnvironment() {
        return this.env;
    }

    boolean isOnDemandCompilation() {
        return this.onDemand;
    }

    boolean useOptimisticTypes() {
        return this.optimistic;
    }

    Context getContext() {
        return this.context;
    }

    Type getOptimisticType(Optimistic optimistic) {
        return this.typeEvaluator.getOptimisticType(optimistic);
    }

    boolean hasStringPropertyIterator(Expression expression) {
        return this.typeEvaluator.hasStringPropertyIterator(expression);
    }

    void addInvalidatedProgramPoint(int i, Type type) {
        this.invalidatedProgramPoints.put(Integer.valueOf(i), type);
    }

    public Map getInvalidatedProgramPoints() {
        if (this.invalidatedProgramPoints.isEmpty()) {
            return null;
        }
        return new TreeMap(this.invalidatedProgramPoints);
    }

    TypeMap getTypeMap() {
        return this.types;
    }

    MethodType getCallSiteType(FunctionNode functionNode) {
        if (this.types == null || !isOnDemandCompilation()) {
            return null;
        }
        return this.types.getCallSiteType(functionNode);
    }

    Type getParamType(FunctionNode functionNode, int i) {
        if (this.types == null) {
            return null;
        }
        return this.types.get(functionNode, i);
    }

    public FunctionNode compile(FunctionNode functionNode, CompilationPhases compilationPhases) {
        if (this.log.isEnabled()) {
            this.log.info(new Object[]{">> Starting compile job for ", DebugLogger.quote(functionNode.getName()), " phases=", DebugLogger.quote(compilationPhases.getDesc())});
            this.log.indent();
        }
        String strQuote = DebugLogger.quote(functionNode.getName());
        FunctionNode functionNodeApply = functionNode;
        for (String str : RESERVED_NAMES) {
            functionNodeApply.uniqueName(str);
        }
        boolean zIsLoggable = this.log.isLoggable(Level.INFO);
        DebugLogger logger = this.env.isTimingEnabled() ? this.env._timing.getLogger() : null;
        long endTime = 0;
        Iterator it = compilationPhases.iterator();
        while (it.hasNext()) {
            CompilationPhase compilationPhase = (CompilationPhase) it.next();
            this.log.fine(new Object[]{compilationPhase, " starting for ", strQuote});
            try {
                functionNodeApply = compilationPhase.apply(this, compilationPhases, functionNodeApply);
                this.log.fine(new Object[]{compilationPhase, " done for function ", DebugLogger.quote(strQuote)});
                if (this.env._print_mem_usage) {
                    printMemoryUsage(functionNode, compilationPhase.toString());
                }
                endTime += this.env.isTimingEnabled() ? compilationPhase.getEndTime() - compilationPhase.getStartTime() : 0L;
            } catch (ParserException e) {
                this.errors.error(e);
                if (this.env._dump_on_error) {
                    e.printStackTrace(this.env.getErr());
                    return null;
                }
                return null;
            }
        }
        if (this.typeInformationFile != null && !compilationPhases.isRestOfCompilation()) {
            OptimisticTypesPersistence.store(this.typeInformationFile, this.invalidatedProgramPoints);
        }
        this.log.unindent();
        if (zIsLoggable) {
            StringBuilder sb = new StringBuilder("<< Finished compile job for ");
            sb.append(functionNodeApply.getSource()).append(':').append(DebugLogger.quote(functionNodeApply.getName()));
            if (endTime > 0 && logger != null) {
                if (!$assertionsDisabled && !this.env.isTimingEnabled()) {
                    throw new AssertionError();
                }
                sb.append(" in ").append(TimeUnit.NANOSECONDS.toMillis(endTime)).append(" ms");
            }
            this.log.info(new Object[]{sb});
        }
        return functionNodeApply;
    }

    Source getSource() {
        return this.source;
    }

    Map getBytecode() {
        return Collections.unmodifiableMap(this.bytecode);
    }

    void clearBytecode() {
        this.bytecode.clear();
    }

    CompileUnit getFirstCompileUnit() {
        if ($assertionsDisabled || !this.compileUnits.isEmpty()) {
            return (CompileUnit) this.compileUnits.iterator().next();
        }
        throw new AssertionError();
    }

    Set getCompileUnits() {
        return this.compileUnits;
    }

    ConstantData getConstantData() {
        return this.constantData;
    }

    CodeInstaller getCodeInstaller() {
        return this.installer;
    }

    void addClass(String str, byte[] bArr) {
        this.bytecode.put(str, bArr);
    }

    String nextCompileUnitName() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.firstCompileUnitName);
        int andIncrement = this.nextCompileUnitId.getAndIncrement();
        if (andIncrement > 0) {
            sb.append("$cu").append(andIncrement);
        }
        return sb.toString();
    }

    public void persistClassInfo(String str, FunctionNode functionNode) {
        if (str != null && this.env._persistent_cache) {
            HashMap map = new HashMap();
            if (isOnDemandCompilation()) {
                map.put(Integer.valueOf(functionNode.getId()), new FunctionInitializer(functionNode, getInvalidatedProgramPoints()));
            } else {
                Iterator it = getCompileUnits().iterator();
                while (it.hasNext()) {
                    for (FunctionNode functionNode2 : ((CompileUnit) it.next()).getFunctionNodes()) {
                        map.put(Integer.valueOf(functionNode2.getId()), new FunctionInitializer(functionNode2));
                    }
                }
            }
            this.installer.storeScript(str, this.source, getFirstCompileUnit().getUnitClassName(), this.bytecode, map, this.constantData.toArray(), this.compilationId);
        }
    }

    public static void updateCompilationId(int i) {
        if (i >= COMPILATION_ID.get()) {
            COMPILATION_ID.set(i + 1);
        }
    }

    CompileUnit addCompileUnit(long j) {
        CompileUnit compileUnitCreateCompileUnit = createCompileUnit(j);
        this.compileUnits.add(compileUnitCreateCompileUnit);
        this.log.fine(new Object[]{"Added compile unit ", compileUnitCreateCompileUnit});
        return compileUnitCreateCompileUnit;
    }

    CompileUnit createCompileUnit(String str, long j) {
        ClassEmitter classEmitter = new ClassEmitter(this.context, this.sourceName, str, isStrict());
        CompileUnit compileUnit = new CompileUnit(str, classEmitter, j);
        classEmitter.begin();
        return compileUnit;
    }

    private CompileUnit createCompileUnit(long j) {
        return createCompileUnit(nextCompileUnitName(), j);
    }

    boolean isStrict() {
        return this.strict;
    }

    void replaceCompileUnits(Set set) {
        this.compileUnits.clear();
        this.compileUnits.addAll(set);
    }

    CompileUnit findUnit(long j) {
        for (CompileUnit compileUnit : this.compileUnits) {
            if (compileUnit.canHold(j)) {
                compileUnit.addWeight(j);
                return compileUnit;
            }
        }
        return addCompileUnit(j);
    }

    public static String binaryName(String str) {
        return str.replace('/', '.');
    }

    RecompilableScriptFunctionData getScriptFunctionData(int i) {
        if (!$assertionsDisabled && this.compiledFunction == null) {
            throw new AssertionError();
        }
        RecompilableScriptFunctionData scriptFunctionData = this.compiledFunction.getScriptFunctionData(i);
        if ($assertionsDisabled || scriptFunctionData != null) {
            return scriptFunctionData;
        }
        throw new AssertionError(i);
    }

    boolean isGlobalSymbol(FunctionNode functionNode, String str) {
        return getScriptFunctionData(functionNode.getId()).isGlobalSymbol(functionNode, str);
    }

    int[] getContinuationEntryPoints() {
        return this.continuationEntryPoints;
    }

    Type getInvalidatedProgramPointType(int i) {
        return (Type) this.invalidatedProgramPoints.get(Integer.valueOf(i));
    }

    private void printMemoryUsage(FunctionNode functionNode, String str) {
        if (!this.log.isEnabled()) {
            return;
        }
        this.log.info(new Object[]{str, "finished. Doing IR size calculation..."});
        ObjectSizeCalculator objectSizeCalculator = new ObjectSizeCalculator(ObjectSizeCalculator.getEffectiveMemoryLayoutSpecification());
        objectSizeCalculator.calculateObjectSize(functionNode);
        List<ClassHistogramElement> classHistogram = objectSizeCalculator.getClassHistogram();
        StringBuilder sb = new StringBuilder();
        long jCalculateObjectSize = objectSizeCalculator.calculateObjectSize(functionNode);
        sb.append(str).append(" Total size = ").append((jCalculateObjectSize / 1024) / 1024).append("MB");
        this.log.info(new Object[]{sb});
        Collections.sort(classHistogram, new Comparator(this) { // from class: jdk.nashorn.internal.codegen.Compiler.2
            final Compiler this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                return compare((ClassHistogramElement) obj, (ClassHistogramElement) obj2);
            }

            public int compare(ClassHistogramElement classHistogramElement, ClassHistogramElement classHistogramElement2) {
                long bytes = classHistogramElement.getBytes() - classHistogramElement2.getBytes();
                if (bytes < 0) {
                    return 1;
                }
                if (bytes > 0) {
                    return -1;
                }
                return 0;
            }
        });
        for (ClassHistogramElement classHistogramElement : classHistogram) {
            this.log.info(String.format("    %-48s %10d bytes (%8d instances)", classHistogramElement.getClazz(), Long.valueOf(classHistogramElement.getBytes()), Long.valueOf(classHistogramElement.getInstances())));
            if (classHistogramElement.getBytes() < jCalculateObjectSize / 200) {
                this.log.info("    ...");
                return;
            }
        }
    }
}
