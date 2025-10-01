package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/OptEnvironment.class */
final class OptEnvironment {
    final MinMaxLen mmd = new MinMaxLen();
    int options;
    int caseFoldFlag;
    ScanEnvironment scanEnv;

    OptEnvironment() {
    }

    void copy(OptEnvironment optEnvironment) {
        this.mmd.copy(optEnvironment.mmd);
        this.options = optEnvironment.options;
        this.caseFoldFlag = optEnvironment.caseFoldFlag;
        this.scanEnv = optEnvironment.scanEnv;
    }
}
