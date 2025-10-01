package jdk.nashorn.internal.runtime;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/StoredScript.class */
public final class StoredScript implements Serializable {
    private final int compilationId;
    private final String mainClassName;
    private final Map classBytes;
    private final Object[] constants;
    private final Map initializers;
    private static final long serialVersionUID = 2958227232195298340L;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !StoredScript.class.desiredAssertionStatus();
    }

    public StoredScript(int i, String str, Map map, Map map2, Object[] objArr) {
        this.compilationId = i;
        this.mainClassName = str;
        this.classBytes = map;
        this.constants = objArr;
        this.initializers = map2;
    }

    public int getCompilationId() {
        return this.compilationId;
    }

    private Map installClasses(Source source, CodeInstaller codeInstaller) {
        HashMap map = new HashMap();
        map.put(this.mainClassName, codeInstaller.install(this.mainClassName, (byte[]) this.classBytes.get(this.mainClassName)));
        for (Map.Entry entry : this.classBytes.entrySet()) {
            String str = (String) entry.getKey();
            if (!str.equals(this.mainClassName)) {
                map.put(str, codeInstaller.install(str, (byte[]) entry.getValue()));
            }
        }
        codeInstaller.initialize(map.values(), source, this.constants);
        return map;
    }

    FunctionInitializer installFunction(RecompilableScriptFunctionData recompilableScriptFunctionData, CodeInstaller codeInstaller) {
        Map mapInstallClasses = installClasses(recompilableScriptFunctionData.getSource(), codeInstaller);
        if (!$assertionsDisabled && this.initializers == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.initializers.size() != 1) {
            throw new AssertionError();
        }
        FunctionInitializer functionInitializer = (FunctionInitializer) this.initializers.values().iterator().next();
        for (int i = 0; i < this.constants.length; i++) {
            if (this.constants[i] instanceof RecompilableScriptFunctionData) {
                RecompilableScriptFunctionData scriptFunctionData = recompilableScriptFunctionData.getScriptFunctionData(((RecompilableScriptFunctionData) this.constants[i]).getFunctionNodeId());
                if (!$assertionsDisabled && scriptFunctionData == null) {
                    throw new AssertionError();
                }
                scriptFunctionData.initTransients(recompilableScriptFunctionData.getSource(), codeInstaller);
                this.constants[i] = scriptFunctionData;
            }
        }
        functionInitializer.setCode((Class) mapInstallClasses.get(functionInitializer.getClassName()));
        return functionInitializer;
    }

    Class installScript(Source source, CodeInstaller codeInstaller) {
        Map mapInstallClasses = installClasses(source, codeInstaller);
        for (Object obj : this.constants) {
            if (obj instanceof RecompilableScriptFunctionData) {
                RecompilableScriptFunctionData recompilableScriptFunctionData = (RecompilableScriptFunctionData) obj;
                recompilableScriptFunctionData.initTransients(source, codeInstaller);
                FunctionInitializer functionInitializer = (FunctionInitializer) this.initializers.get(Integer.valueOf(recompilableScriptFunctionData.getFunctionNodeId()));
                if (functionInitializer != null) {
                    functionInitializer.setCode((Class) mapInstallClasses.get(functionInitializer.getClassName()));
                    recompilableScriptFunctionData.initializeCode(functionInitializer);
                }
            }
        }
        return (Class) mapInstallClasses.get(this.mainClassName);
    }

    public int hashCode() {
        return (31 * ((31 * this.mainClassName.hashCode()) + this.classBytes.hashCode())) + Arrays.hashCode(this.constants);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StoredScript)) {
            return false;
        }
        StoredScript storedScript = (StoredScript) obj;
        return this.mainClassName.equals(storedScript.mainClassName) && this.classBytes.equals(storedScript.classBytes) && Arrays.equals(this.constants, storedScript.constants);
    }
}
