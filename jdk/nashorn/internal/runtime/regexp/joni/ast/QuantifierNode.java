package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;
import org.apache.log4j.spi.LocationInfo;
import org.slf4j.Marker;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/QuantifierNode.class */
public final class QuantifierNode extends StateNode {
    public Node target;
    public int lower;
    public int upper;
    public boolean greedy = true;
    public int targetEmptyInfo = 0;
    public Node headExact;
    public Node nextHeadExact;
    public boolean isRefered;
    private static final ReduceType[][] REDUCE_TABLE = {new ReduceType[]{ReduceType.DEL, ReduceType.A, ReduceType.A, ReduceType.QQ, ReduceType.AQ, ReduceType.ASIS}, new ReduceType[]{ReduceType.DEL, ReduceType.DEL, ReduceType.DEL, ReduceType.P_QQ, ReduceType.P_QQ, ReduceType.DEL}, new ReduceType[]{ReduceType.A, ReduceType.A, ReduceType.DEL, ReduceType.ASIS, ReduceType.P_QQ, ReduceType.DEL}, new ReduceType[]{ReduceType.DEL, ReduceType.AQ, ReduceType.AQ, ReduceType.DEL, ReduceType.AQ, ReduceType.AQ}, new ReduceType[]{ReduceType.DEL, ReduceType.DEL, ReduceType.DEL, ReduceType.DEL, ReduceType.DEL, ReduceType.DEL}, new ReduceType[]{ReduceType.ASIS, ReduceType.PQ_Q, ReduceType.DEL, ReduceType.AQ, ReduceType.AQ, ReduceType.DEL}};
    private static final String[] PopularQStr = {LocationInfo.f204NA, Marker.ANY_MARKER, Marker.ANY_NON_NULL_MARKER, "??", "*?", "+?"};
    private static final String[] ReduceQStr = {"", "", Marker.ANY_MARKER, "*?", "??", "+ and ??", "+? and ?"};
    public static final int REPEAT_INFINITE = -1;

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/QuantifierNode$ReduceType.class */
    enum ReduceType {
        ASIS,
        DEL,
        A,
        AQ,
        QQ,
        P_QQ,
        PQ_Q
    }

    public QuantifierNode(int i, int i2, boolean z) {
        this.lower = i;
        this.upper = i2;
        if (z) {
            setByNumber();
        }
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

    public StringNode convertToString(int i) {
        StringNode stringNode = new StringNode();
        stringNode.flag = i;
        stringNode.swap(this);
        return stringNode;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.StateNode, jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String toString(int i) {
        StringBuilder sb = new StringBuilder(super.toString(i));
        sb.append("\n  target: " + pad(this.target, i + 1));
        sb.append("\n  lower: " + this.lower);
        sb.append("\n  upper: " + this.upper);
        sb.append("\n  greedy: " + this.greedy);
        sb.append("\n  targetEmptyInfo: " + this.targetEmptyInfo);
        sb.append("\n  headExact: " + pad(this.headExact, i + 1));
        sb.append("\n  nextHeadExact: " + pad(this.nextHeadExact, i + 1));
        sb.append("\n  isRefered: " + this.isRefered);
        return sb.toString();
    }

    public boolean isAnyCharStar() {
        if (this.greedy) {
            if ((this.upper == -1) && this.target.getType() == 3) {
                return true;
            }
        }
        return false;
    }

    protected int popularNum() {
        if (this.greedy) {
            if (this.lower == 0) {
                if (this.upper == 1) {
                    return 0;
                }
                return this.upper == -1 ? 1 : -1;
            }
            if (this.lower == 1) {
                return this.upper == -1 ? 2 : -1;
            }
            return -1;
        }
        if (this.lower == 0) {
            if (this.upper == 1) {
                return 3;
            }
            return this.upper == -1 ? 4 : -1;
        }
        if (this.lower == 1) {
            return this.upper == -1 ? 5 : -1;
        }
        return -1;
    }

    protected void set(QuantifierNode quantifierNode) {
        setTarget(quantifierNode.target);
        quantifierNode.target = null;
        this.lower = quantifierNode.lower;
        this.upper = quantifierNode.upper;
        this.greedy = quantifierNode.greedy;
        this.targetEmptyInfo = quantifierNode.targetEmptyInfo;
        this.headExact = quantifierNode.headExact;
        this.nextHeadExact = quantifierNode.nextHeadExact;
        this.isRefered = quantifierNode.isRefered;
    }

    public void reduceNestedQuantifier(QuantifierNode quantifierNode) {
        int iPopularNum = popularNum();
        int iPopularNum2 = quantifierNode.popularNum();
        if (iPopularNum < 0 || iPopularNum2 < 0) {
            return;
        }
        switch (C02751.f82x209b3c22[REDUCE_TABLE[iPopularNum2][iPopularNum].ordinal()]) {
            case 1:
                set(quantifierNode);
                break;
            case 2:
                setTarget(quantifierNode.target);
                this.lower = 0;
                this.upper = -1;
                this.greedy = true;
                break;
            case 3:
                setTarget(quantifierNode.target);
                this.lower = 0;
                this.upper = -1;
                this.greedy = false;
                break;
            case 4:
                setTarget(quantifierNode.target);
                this.lower = 0;
                this.upper = 1;
                this.greedy = false;
                break;
            case 5:
                setTarget(quantifierNode);
                this.lower = 0;
                this.upper = 1;
                this.greedy = false;
                quantifierNode.lower = 1;
                quantifierNode.upper = -1;
                quantifierNode.greedy = true;
                return;
            case 6:
                setTarget(quantifierNode);
                this.lower = 0;
                this.upper = 1;
                this.greedy = true;
                quantifierNode.lower = 1;
                quantifierNode.upper = -1;
                quantifierNode.greedy = false;
                return;
            case 7:
                setTarget(quantifierNode);
                return;
        }
        quantifierNode.target = null;
    }

    /* renamed from: jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/QuantifierNode$1.class */
    static /* synthetic */ class C02751 {

        /* renamed from: $SwitchMap$jdk$nashorn$internal$runtime$regexp$joni$ast$QuantifierNode$ReduceType */
        static final int[] f82x209b3c22 = new int[ReduceType.values().length];

        static {
            try {
                f82x209b3c22[ReduceType.DEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f82x209b3c22[ReduceType.A.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f82x209b3c22[ReduceType.AQ.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f82x209b3c22[ReduceType.QQ.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f82x209b3c22[ReduceType.P_QQ.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f82x209b3c22[ReduceType.PQ_Q.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f82x209b3c22[ReduceType.ASIS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public int setQuantifier(Node node, boolean z, ScanEnvironment scanEnvironment, char[] cArr, int i, int i2) {
        StringNode stringNodeSplitLastChar;
        if (this.lower == 1 && this.upper == 1) {
            return 1;
        }
        switch (node.getType()) {
            case 0:
                if (!z) {
                    StringNode stringNode = (StringNode) node;
                    if (stringNode.canBeSplit() && (stringNodeSplitLastChar = stringNode.splitLastChar()) != null) {
                        setTarget(stringNodeSplitLastChar);
                        return 2;
                    }
                }
                break;
            case 5:
                QuantifierNode quantifierNode = (QuantifierNode) node;
                int iPopularNum = popularNum();
                int iPopularNum2 = quantifierNode.popularNum();
                if (iPopularNum2 >= 0) {
                    if (iPopularNum >= 0) {
                        reduceNestedQuantifier(quantifierNode);
                        return 0;
                    }
                    if (iPopularNum2 == 1 || iPopularNum2 == 2) {
                        if (!(this.upper == -1) && this.upper > 1 && this.greedy) {
                            this.upper = this.lower == 0 ? 1 : this.lower;
                            break;
                        }
                    }
                }
                break;
        }
        setTarget(node);
        return 0;
    }
}
