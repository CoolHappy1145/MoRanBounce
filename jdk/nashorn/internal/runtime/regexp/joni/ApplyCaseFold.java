package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ApplyCaseFold.class */
final class ApplyCaseFold {
    static final ApplyCaseFold INSTANCE = new ApplyCaseFold();

    ApplyCaseFold() {
    }

    public static void apply(int i, int i2, Object obj) {
        ApplyCaseFoldArg applyCaseFoldArg = (ApplyCaseFoldArg) obj;
        ScanEnvironment scanEnvironment = applyCaseFoldArg.env;
        CClassNode cClassNode = applyCaseFoldArg.f63cc;
        BitSet bitSet = cClassNode.f78bs;
        boolean zIsCodeInCC = cClassNode.isCodeInCC(i);
        if ((zIsCodeInCC && !cClassNode.isNot()) || (!zIsCodeInCC && cClassNode.isNot())) {
            if (i2 >= 256) {
                cClassNode.addCodeRange(scanEnvironment, i2, i2);
            } else {
                bitSet.set(i2);
            }
        }
    }
}
