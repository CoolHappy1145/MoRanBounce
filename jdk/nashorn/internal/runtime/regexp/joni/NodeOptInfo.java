package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/NodeOptInfo.class */
public final class NodeOptInfo {
    final MinMaxLen length = new MinMaxLen();
    final OptAnchorInfo anchor = new OptAnchorInfo();
    final OptExactInfo exb = new OptExactInfo();
    final OptExactInfo exm = new OptExactInfo();
    final OptExactInfo expr = new OptExactInfo();
    final OptMapInfo map = new OptMapInfo();

    public void setBoundNode(MinMaxLen minMaxLen) {
        this.exb.mmd.copy(minMaxLen);
        this.expr.mmd.copy(minMaxLen);
        this.map.mmd.copy(minMaxLen);
    }

    public void clear() {
        this.length.clear();
        this.anchor.clear();
        this.exb.clear();
        this.exm.clear();
        this.expr.clear();
        this.map.clear();
    }

    public void copy(NodeOptInfo nodeOptInfo) {
        this.length.copy(nodeOptInfo.length);
        this.anchor.copy(nodeOptInfo.anchor);
        this.exb.copy(nodeOptInfo.exb);
        this.exm.copy(nodeOptInfo.exm);
        this.expr.copy(nodeOptInfo.expr);
        this.map.copy(nodeOptInfo.map);
    }

    public void concatLeftNode(NodeOptInfo nodeOptInfo) {
        OptAnchorInfo optAnchorInfo = new OptAnchorInfo();
        optAnchorInfo.concat(this.anchor, nodeOptInfo.anchor, this.length.max, nodeOptInfo.length.max);
        this.anchor.copy(optAnchorInfo);
        if (nodeOptInfo.exb.length > 0 && this.length.max == 0) {
            optAnchorInfo.concat(this.anchor, nodeOptInfo.exb.anchor, this.length.max, nodeOptInfo.length.max);
            nodeOptInfo.exb.anchor.copy(optAnchorInfo);
        }
        if (nodeOptInfo.map.value > 0 && this.length.max == 0 && nodeOptInfo.map.mmd.max == 0) {
            nodeOptInfo.map.anchor.leftAnchor |= this.anchor.leftAnchor;
        }
        boolean z = this.exb.reachEnd;
        boolean z2 = this.exm.reachEnd;
        if (nodeOptInfo.length.max != 0) {
            OptExactInfo optExactInfo = this.exb;
            this.exm.reachEnd = false;
            optExactInfo.reachEnd = false;
        }
        if (nodeOptInfo.exb.length > 0) {
            if (z) {
                this.exb.concat(nodeOptInfo.exb);
                nodeOptInfo.exb.clear();
            } else if (z2) {
                this.exm.concat(nodeOptInfo.exb);
                nodeOptInfo.exb.clear();
            }
        }
        this.exm.select(nodeOptInfo.exb);
        this.exm.select(nodeOptInfo.exm);
        if (this.expr.length > 0) {
            if (nodeOptInfo.length.max > 0) {
                int i = nodeOptInfo.length.max;
                if (i == Integer.MAX_VALUE) {
                    i = -1;
                }
                if (this.expr.length > i) {
                    this.expr.length = i;
                }
                if (this.expr.mmd.max == 0) {
                    this.exb.select(this.expr);
                } else {
                    this.exm.select(this.expr);
                }
            }
        } else if (nodeOptInfo.expr.length > 0) {
            this.expr.copy(nodeOptInfo.expr);
        }
        this.map.select(nodeOptInfo.map);
        this.length.add(nodeOptInfo.length);
    }

    public void altMerge(NodeOptInfo nodeOptInfo, OptEnvironment optEnvironment) {
        this.anchor.altMerge(nodeOptInfo.anchor);
        this.exb.altMerge(nodeOptInfo.exb, optEnvironment);
        this.exm.altMerge(nodeOptInfo.exm, optEnvironment);
        this.expr.altMerge(nodeOptInfo.expr, optEnvironment);
        this.map.altMerge(nodeOptInfo.map);
        this.length.altMerge(nodeOptInfo.length);
    }

    public void setBound(MinMaxLen minMaxLen) {
        this.exb.mmd.copy(minMaxLen);
        this.expr.mmd.copy(minMaxLen);
        this.map.mmd.copy(minMaxLen);
    }
}
