package jdk.nashorn.internal.runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodType;
import java.util.Map;
import jdk.nashorn.internal.codegen.CompileUnit;
import jdk.nashorn.internal.codegen.FunctionSignature;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.FunctionNode;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/FunctionInitializer.class */
public final class FunctionInitializer implements Serializable {
    private final String className;
    private final MethodType methodType;
    private final int flags;
    private Map invalidatedProgramPoints;
    private Class code;
    private static final long serialVersionUID = -5420835725902966692L;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !FunctionInitializer.class.desiredAssertionStatus();
    }

    public FunctionInitializer(FunctionNode functionNode) {
        this(functionNode, null);
    }

    public FunctionInitializer(FunctionNode functionNode, Map map) {
        this.className = functionNode.getCompileUnit().getUnitClassName();
        this.methodType = new FunctionSignature(functionNode).getMethodType();
        this.flags = functionNode.getFlags();
        this.invalidatedProgramPoints = map;
        CompileUnit compileUnit = functionNode.getCompileUnit();
        if (compileUnit != null) {
            this.code = compileUnit.getCode();
        }
        if (!$assertionsDisabled && this.className == null) {
            throw new AssertionError();
        }
    }

    public String getClassName() {
        return this.className;
    }

    public MethodType getMethodType() {
        return this.methodType;
    }

    public int getFlags() {
        return this.flags;
    }

    public Class getCode() {
        return this.code;
    }

    void setCode(Class cls) {
        if (this.code != null) {
            throw new IllegalStateException("code already set");
        }
        if (!$assertionsDisabled && !this.className.equals(cls.getTypeName().replace('.', '/'))) {
            throw new AssertionError("unexpected class name");
        }
        this.code = cls;
    }

    public Map getInvalidatedProgramPoints() {
        return this.invalidatedProgramPoints;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Type.writeTypeMap(this.invalidatedProgramPoints, objectOutputStream);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.invalidatedProgramPoints = Type.readTypeMap(objectInputStream);
    }
}
