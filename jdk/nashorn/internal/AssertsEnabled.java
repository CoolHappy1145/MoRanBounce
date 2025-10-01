package jdk.nashorn.internal;

/* loaded from: L-out.jar:jdk/nashorn/internal/AssertsEnabled.class */
public final class AssertsEnabled {
    private static boolean assertsEnabled;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !AssertsEnabled.class.desiredAssertionStatus();
        assertsEnabled = false;
        if (!$assertionsDisabled) {
            assertsEnabled = true;
        }
    }

    public static boolean assertsEnabled() {
        return assertsEnabled;
    }
}
