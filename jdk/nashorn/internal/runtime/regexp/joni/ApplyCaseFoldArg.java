package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ApplyCaseFoldArg.class */
public final class ApplyCaseFoldArg {
    final ScanEnvironment env;

    /* renamed from: cc */
    final CClassNode f63cc;
    ConsAltNode altRoot;
    ConsAltNode tail;

    public ApplyCaseFoldArg(ScanEnvironment scanEnvironment, CClassNode cClassNode) {
        this.env = scanEnvironment;
        this.f63cc = cClassNode;
    }
}
