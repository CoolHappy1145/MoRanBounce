package jdk.nashorn.internal.runtime.events;

import java.util.logging.Level;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.RewriteException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/events/RecompilationEvent.class */
public final class RecompilationEvent extends RuntimeEvent {
    private final Object returnValue;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !RecompilationEvent.class.desiredAssertionStatus();
    }

    public RecompilationEvent(Level level, RewriteException rewriteException, Object obj) {
        super(level, rewriteException);
        if (!$assertionsDisabled && !Context.getContext().getLogger(RecompilableScriptFunctionData.class).isEnabled()) {
            throw new AssertionError("Unit test/instrumentation purpose only: RecompilationEvent instances should not be created without '--log=recompile', or we will leak memory in the general case");
        }
        this.returnValue = obj;
    }

    public Object getReturnValue() {
        return this.returnValue;
    }
}
