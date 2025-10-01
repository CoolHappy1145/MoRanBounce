package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.constants.AnchorType;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/OptAnchorInfo.class */
final class OptAnchorInfo implements AnchorType {
    int leftAnchor;
    int rightAnchor;

    OptAnchorInfo() {
    }

    void clear() {
        this.rightAnchor = 0;
        this.leftAnchor = 0;
    }

    void copy(OptAnchorInfo optAnchorInfo) {
        this.leftAnchor = optAnchorInfo.leftAnchor;
        this.rightAnchor = optAnchorInfo.rightAnchor;
    }

    void concat(OptAnchorInfo optAnchorInfo, OptAnchorInfo optAnchorInfo2, int i, int i2) {
        this.leftAnchor = optAnchorInfo.leftAnchor;
        if (i == 0) {
            this.leftAnchor |= optAnchorInfo2.leftAnchor;
        }
        this.rightAnchor = optAnchorInfo2.rightAnchor;
        if (i2 == 0) {
            this.rightAnchor |= optAnchorInfo.rightAnchor;
        }
    }

    boolean isSet(int i) {
        return ((this.leftAnchor & i) == 0 && (this.rightAnchor & i) == 0) ? false : true;
    }

    void add(int i) {
        if ((i == 8 || i == 16 || i == 32 || i == 1024 || i == 2048) ? false : true) {
            this.leftAnchor |= i;
        } else {
            this.rightAnchor |= i;
        }
    }

    void remove(int i) {
        if ((i == 8 || i == 16 || i == 32 || i == 1024 || i == 2048) ? false : true) {
            this.leftAnchor &= i ^ (-1);
        } else {
            this.rightAnchor &= i ^ (-1);
        }
    }

    void altMerge(OptAnchorInfo optAnchorInfo) {
        this.leftAnchor &= optAnchorInfo.leftAnchor;
        this.rightAnchor &= optAnchorInfo.rightAnchor;
    }

    static String anchorToString(int i) {
        StringBuffer stringBuffer = new StringBuffer("[");
        if ((i & 1) != 0) {
            stringBuffer.append("begin-buf ");
        }
        if ((i & 2) != 0) {
            stringBuffer.append("begin-line ");
        }
        if ((i & 4) != 0) {
            stringBuffer.append("begin-pos ");
        }
        if ((i & 8) != 0) {
            stringBuffer.append("end-buf ");
        }
        if ((i & 16) != 0) {
            stringBuffer.append("semi-end-buf ");
        }
        if ((i & 32) != 0) {
            stringBuffer.append("end-line ");
        }
        if ((i & 16384) != 0) {
            stringBuffer.append("anychar-star ");
        }
        if ((i & 32768) != 0) {
            stringBuffer.append("anychar-star-pl ");
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
