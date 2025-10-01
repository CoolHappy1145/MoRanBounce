package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.constants.StringType;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/StringNode.class */
public final class StringNode extends Node implements StringType {
    private static final int NODE_STR_MARGIN = 16;
    private static final int NODE_STR_BUF_SIZE = 24;
    public static final StringNode EMPTY = new StringNode(null, Integer.MAX_VALUE, Integer.MAX_VALUE);
    public char[] chars;

    /* renamed from: p */
    public int f86p;
    public int end;
    public int flag;

    public StringNode() {
        this.chars = new char[24];
    }

    public StringNode(char[] cArr, int i, int i2) {
        this.chars = cArr;
        this.f86p = i;
        this.end = i2;
        setShared();
    }

    public StringNode(char c) {
        this();
        char[] cArr = this.chars;
        int i = this.end;
        this.end = i + 1;
        cArr[i] = c;
    }

    public void ensure(int i) {
        int i2 = (this.end - this.f86p) + i;
        if (i2 >= this.chars.length) {
            char[] cArr = new char[i2 + 16];
            System.arraycopy(this.chars, this.f86p, cArr, 0, this.end - this.f86p);
            this.chars = cArr;
        }
    }

    private void modifyEnsure(int i) {
        if (isShared()) {
            char[] cArr = new char[(this.end - this.f86p) + i + 16];
            System.arraycopy(this.chars, this.f86p, cArr, 0, this.end - this.f86p);
            this.chars = cArr;
            this.end -= this.f86p;
            this.f86p = 0;
            clearShared();
            return;
        }
        ensure(i);
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String toString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n  bytes: '");
        for (int i2 = this.f86p; i2 < this.end; i2++) {
            if (this.chars[i2] >= ' ' && this.chars[i2] < '\u007f') {
                sb.append(this.chars[i2]);
            } else {
                sb.append(String.format("[0x%04x]", Integer.valueOf(this.chars[i2])));
            }
        }
        sb.append("'");
        return sb.toString();
    }

    public int length() {
        return this.end - this.f86p;
    }

    public StringNode splitLastChar() {
        StringNode stringNode = null;
        if (this.end > this.f86p) {
            int i = this.f86p;
            int i2 = this.end;
            int i3 = i2 <= i ? -1 : i2 - 1;
            if (i3 != -1 && i3 > this.f86p) {
                stringNode = new StringNode(this.chars, i3, this.end);
                if (isRaw()) {
                    stringNode.setRaw();
                }
                this.end = i3;
            }
        }
        return stringNode;
    }

    public boolean canBeSplit() {
        return this.end > this.f86p && 1 < this.end - this.f86p;
    }

    public void set(char[] cArr, int i, int i2) {
        this.chars = cArr;
        this.f86p = i;
        this.end = i2;
        setShared();
    }

    public void cat(char[] cArr, int i, int i2) {
        int i3 = i2 - i;
        modifyEnsure(i3);
        System.arraycopy(cArr, i, this.chars, this.end, i3);
        this.end += i3;
    }

    public void cat(char c) {
        modifyEnsure(1);
        char[] cArr = this.chars;
        int i = this.end;
        this.end = i + 1;
        cArr[i] = c;
    }

    public void catCode(int i) {
        cat((char) i);
    }

    public void clear() {
        if (this.chars.length > 24) {
            this.chars = new char[24];
        }
        this.flag = 0;
        this.end = 0;
        this.f86p = 0;
    }

    public void setRaw() {
        this.flag |= 1;
    }

    public void clearRaw() {
        this.flag &= -2;
    }

    public boolean isRaw() {
        return (this.flag & 1) != 0;
    }

    public void setAmbig() {
        this.flag |= 2;
    }

    public void clearAmbig() {
        this.flag &= -3;
    }

    public boolean isAmbig() {
        return (this.flag & 2) != 0;
    }

    public void setDontGetOptInfo() {
        this.flag |= 4;
    }

    public void clearDontGetOptInfo() {
        this.flag &= -5;
    }

    public boolean isDontGetOptInfo() {
        return (this.flag & 4) != 0;
    }

    public void setShared() {
        this.flag |= 8;
    }

    public void clearShared() {
        this.flag &= -9;
    }

    public boolean isShared() {
        return (this.flag & 8) != 0;
    }
}
