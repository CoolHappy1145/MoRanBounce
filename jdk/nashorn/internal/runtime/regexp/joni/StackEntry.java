package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/StackEntry.class */
final class StackEntry {
    int type;

    /* renamed from: E1 */
    private int f73E1;

    /* renamed from: E2 */
    private int f74E2;

    /* renamed from: E3 */
    private int f75E3;

    /* renamed from: E4 */
    private int f76E4;

    StackEntry() {
    }

    void setStatePCode(int i) {
        this.f73E1 = i;
    }

    int getStatePCode() {
        return this.f73E1;
    }

    void setStatePStr(int i) {
        this.f74E2 = i;
    }

    int getStatePStr() {
        return this.f74E2;
    }

    void setStatePStrPrev(int i) {
        this.f75E3 = i;
    }

    int getStatePStrPrev() {
        return this.f75E3;
    }

    void setStateCheck(int i) {
        this.f76E4 = i;
    }

    int getStateCheck() {
        return this.f76E4;
    }

    void setRepeatCount(int i) {
        this.f73E1 = i;
    }

    int getRepeatCount() {
        return this.f73E1;
    }

    void decreaseRepeatCount() {
        this.f73E1--;
    }

    void increaseRepeatCount() {
        this.f73E1++;
    }

    void setRepeatPCode(int i) {
        this.f74E2 = i;
    }

    int getRepeatPCode() {
        return this.f74E2;
    }

    void setRepeatNum(int i) {
        this.f75E3 = i;
    }

    int getRepeatNum() {
        return this.f75E3;
    }

    void setSi(int i) {
        this.f73E1 = i;
    }

    int getSi() {
        return this.f73E1;
    }

    void setMemNum(int i) {
        this.f73E1 = i;
    }

    int getMemNum() {
        return this.f73E1;
    }

    void setMemPstr(int i) {
        this.f74E2 = i;
    }

    int getMemPStr() {
        return this.f74E2;
    }

    void setMemStart(int i) {
        this.f75E3 = i;
    }

    int getMemStart() {
        return this.f75E3;
    }

    void setMemEnd(int i) {
        this.f76E4 = i;
    }

    int getMemEnd() {
        return this.f76E4;
    }

    void setNullCheckNum(int i) {
        this.f73E1 = i;
    }

    int getNullCheckNum() {
        return this.f73E1;
    }

    void setNullCheckPStr(int i) {
        this.f74E2 = i;
    }

    int getNullCheckPStr() {
        return this.f74E2;
    }

    void setCallFrameRetAddr(int i) {
        this.f73E1 = i;
    }

    int getCallFrameRetAddr() {
        return this.f73E1;
    }

    void setCallFrameNum(int i) {
        this.f74E2 = i;
    }

    int getCallFrameNum() {
        return this.f74E2;
    }

    void setCallFramePStr(int i) {
        this.f75E3 = i;
    }

    int getCallFramePStr() {
        return this.f75E3;
    }
}
