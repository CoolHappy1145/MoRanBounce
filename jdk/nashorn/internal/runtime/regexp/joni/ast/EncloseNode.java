package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.Option;
import jdk.nashorn.internal.runtime.regexp.joni.constants.EncloseType;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/EncloseNode.class */
public final class EncloseNode extends StateNode implements EncloseType {
    public final int type;
    public int regNum;
    public int option;
    public Node target;
    public int callAddr;
    public int minLength;
    public int maxLength;
    public int charLength;
    public int optCount;

    public EncloseNode(int i) {
        this.type = i;
        this.callAddr = -1;
    }

    public EncloseNode() {
        this(1);
    }

    public EncloseNode(int i, int i2) {
        this(2);
        this.option = i;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    protected void setChild(Node node) {
        this.target = node;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    protected Node getChild() {
        return this.target;
    }

    public void setTarget(Node node) {
        this.target = node;
        node.parent = this;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.StateNode, jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String toString(int i) {
        StringBuilder sb = new StringBuilder(super.toString(i));
        sb.append("\n  type: " + typeToString());
        sb.append("\n  regNum: " + this.regNum);
        sb.append("\n  option: " + Option.toString(this.option));
        sb.append("\n  target: " + pad(this.target, i + 1));
        sb.append("\n  callAddr: " + this.callAddr);
        sb.append("\n  minLength: " + this.minLength);
        sb.append("\n  maxLength: " + this.maxLength);
        sb.append("\n  charLength: " + this.charLength);
        sb.append("\n  optCount: " + this.optCount);
        return sb.toString();
    }

    public String typeToString() {
        StringBuilder sb = new StringBuilder();
        if (isStopBacktrack()) {
            sb.append("STOP_BACKTRACK ");
        }
        if (isMemory()) {
            sb.append("MEMORY ");
        }
        if (isOption()) {
            sb.append("OPTION ");
        }
        return sb.toString();
    }

    public boolean isMemory() {
        return (this.type & 1) != 0;
    }

    public boolean isOption() {
        return (this.type & 2) != 0;
    }

    public boolean isStopBacktrack() {
        return (this.type & 4) != 0;
    }
}
