package jdk.nashorn.internal.codegen;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompileUnit.class */
public final class CompileUnit implements Comparable, Serializable {
    private static final long serialVersionUID = 1;
    private final String className;
    private ClassEmitter classEmitter;
    private long weight;
    private Class clazz;
    private Map functions = new IdentityHashMap();
    private boolean isUsed;
    private static int emittedUnitCount;

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareTo((CompileUnit) obj);
    }

    CompileUnit(String str, ClassEmitter classEmitter, long j) {
        this.className = str;
        this.weight = j;
        this.classEmitter = classEmitter;
    }

    static Set createCompileUnitSet() {
        return new TreeSet();
    }

    static void increaseEmitCount() {
        emittedUnitCount++;
    }

    public static int getEmittedUnitCount() {
        return emittedUnitCount;
    }

    public boolean isUsed() {
        return this.isUsed;
    }

    public boolean hasCode() {
        return (this.classEmitter.getMethodCount() - this.classEmitter.getInitCount()) - this.classEmitter.getClinitCount() > 0;
    }

    public void setUsed() {
        this.isUsed = true;
    }

    public Class getCode() {
        return this.clazz;
    }

    void setCode(Class cls) {
        this.clazz = (Class) Objects.requireNonNull(cls);
        this.classEmitter = null;
    }

    void addFunctionInitializer(RecompilableScriptFunctionData recompilableScriptFunctionData, FunctionNode functionNode) {
        this.functions.put(functionNode, recompilableScriptFunctionData);
    }

    public boolean isInitializing(RecompilableScriptFunctionData recompilableScriptFunctionData, FunctionNode functionNode) {
        return this.functions.get(functionNode) == recompilableScriptFunctionData;
    }

    void initializeFunctionsCode() {
        for (Map.Entry entry : this.functions.entrySet()) {
            ((RecompilableScriptFunctionData) entry.getValue()).initializeCode((FunctionNode) entry.getKey());
        }
    }

    Collection getFunctionNodes() {
        return Collections.unmodifiableCollection(this.functions.keySet());
    }

    void addWeight(long j) {
        this.weight += j;
    }

    public boolean canHold(long j) {
        return this.weight + j < Splitter.SPLIT_THRESHOLD;
    }

    public ClassEmitter getClassEmitter() {
        return this.classEmitter;
    }

    public String getUnitClassName() {
        return this.className;
    }

    private static String shortName(String str) {
        if (str == null) {
            return null;
        }
        return str.lastIndexOf(47) == -1 ? str : str.substring(str.lastIndexOf(47) + 1);
    }

    public String toString() {
        return "[CompileUnit className=" + shortName(this.className) + " weight=" + this.weight + '/' + Splitter.SPLIT_THRESHOLD + " hasCode=" + (this.classEmitter != null ? this.classEmitter.getMethodNames().toString() : "<anon>") + ']';
    }

    public int compareTo(CompileUnit compileUnit) {
        return this.className.compareTo(compileUnit.className);
    }
}
