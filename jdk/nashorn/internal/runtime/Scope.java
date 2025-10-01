package jdk.nashorn.internal.runtime;

import java.util.concurrent.atomic.LongAdder;
import jdk.nashorn.internal.codegen.CompilerConstants;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Scope.class */
public class Scope extends ScriptObject {
    private int splitState;
    private static final LongAdder count;
    public static final CompilerConstants.Call GET_SPLIT_STATE;
    public static final CompilerConstants.Call SET_SPLIT_STATE;

    static {
        count = Context.DEBUG ? new LongAdder() : null;
        GET_SPLIT_STATE = CompilerConstants.virtualCallNoLookup(Scope.class, "getSplitState", Integer.TYPE, new Class[0]);
        SET_SPLIT_STATE = CompilerConstants.virtualCallNoLookup(Scope.class, "setSplitState", Void.TYPE, new Class[]{Integer.TYPE});
    }

    public Scope(PropertyMap propertyMap) {
        super(propertyMap);
        this.splitState = -1;
        incrementCount();
    }

    public Scope(ScriptObject scriptObject, PropertyMap propertyMap) {
        super(scriptObject, propertyMap);
        this.splitState = -1;
        incrementCount();
    }

    public Scope(PropertyMap propertyMap, long[] jArr, Object[] objArr) {
        super(propertyMap, jArr, objArr);
        this.splitState = -1;
        incrementCount();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    boolean hasWithScope() {
        ScriptObject proto = this;
        while (true) {
            ScriptObject scriptObject = proto;
            if (scriptObject != null) {
                if (!(scriptObject instanceof WithObject)) {
                    proto = scriptObject.getProto();
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public int getSplitState() {
        return this.splitState;
    }

    public void setSplitState(int i) {
        this.splitState = i;
    }

    public static long getScopeCount() {
        if (count != null) {
            return count.sum();
        }
        return 0L;
    }

    private static void incrementCount() {
        if (Context.DEBUG) {
            count.increment();
        }
    }
}
