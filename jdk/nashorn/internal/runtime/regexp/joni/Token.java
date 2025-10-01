package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.constants.TokenType;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/Token.class */
final class Token {
    TokenType type;
    boolean escaped;
    int backP;
    private int INT1;
    private int INT2;
    private int INT3;
    private int INT4;

    Token() {
    }

    int getC() {
        return this.INT1;
    }

    void setC(int i) {
        this.INT1 = i;
    }

    int getCode() {
        return this.INT1;
    }

    void setCode(int i) {
        this.INT1 = i;
    }

    int getAnchor() {
        return this.INT1;
    }

    void setAnchor(int i) {
        this.INT1 = i;
    }

    int getRepeatLower() {
        return this.INT1;
    }

    void setRepeatLower(int i) {
        this.INT1 = i;
    }

    int getRepeatUpper() {
        return this.INT2;
    }

    void setRepeatUpper(int i) {
        this.INT2 = i;
    }

    boolean getRepeatGreedy() {
        return this.INT3 != 0;
    }

    void setRepeatGreedy(boolean z) {
        this.INT3 = z ? 1 : 0;
    }

    boolean getRepeatPossessive() {
        return this.INT4 != 0;
    }

    void setRepeatPossessive(boolean z) {
        this.INT4 = z ? 1 : 0;
    }

    int getBackrefRef() {
        return this.INT2;
    }

    void setBackrefRef(int i) {
        this.INT2 = i;
    }

    int getPropCType() {
        return this.INT1;
    }

    void setPropCType(int i) {
        this.INT1 = i;
    }

    boolean getPropNot() {
        return this.INT2 != 0;
    }

    void setPropNot(boolean z) {
        this.INT2 = z ? 1 : 0;
    }
}
