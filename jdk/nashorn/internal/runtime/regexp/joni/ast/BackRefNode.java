package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/BackRefNode.class */
public final class BackRefNode extends StateNode {
    public final int backRef;

    public BackRefNode(int i, ScanEnvironment scanEnvironment) {
        this.backRef = i;
        if (i <= scanEnvironment.numMem && scanEnvironment.memNodes[i] == null) {
            setRecursion();
        }
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.StateNode, jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String toString(int i) {
        StringBuilder sb = new StringBuilder(super.toString(i));
        sb.append("\n  back: ").append(this.backRef);
        return sb.toString();
    }
}
