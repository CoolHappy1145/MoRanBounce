package jdk.internal.dynalink.linker;

/* loaded from: L-out.jar:jdk/internal/dynalink/linker/GuardedTypeConversion.class */
public class GuardedTypeConversion {
    private final GuardedInvocation conversionInvocation;
    private final boolean cacheable;

    public GuardedTypeConversion(GuardedInvocation guardedInvocation, boolean z) {
        this.conversionInvocation = guardedInvocation;
        this.cacheable = z;
    }

    public GuardedInvocation getConversionInvocation() {
        return this.conversionInvocation;
    }

    public boolean isCacheable() {
        return this.cacheable;
    }
}
