package jdk.nashorn.internal.runtime.regexp.joni;

import java.lang.ref.WeakReference;
import jdk.nashorn.internal.runtime.regexp.joni.constants.StackType;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/StackMachine.class */
abstract class StackMachine extends Matcher implements StackType {
    protected static final int INVALID_INDEX = -1;
    protected StackEntry[] stack;
    protected int stk;
    protected final int[] repeatStk;
    protected final int memStartStk;
    protected final int memEndStk;
    static final ThreadLocal stacks = new ThreadLocal() { // from class: jdk.nashorn.internal.runtime.regexp.joni.StackMachine.1
        @Override // java.lang.ThreadLocal
        protected Object initialValue() {
            return initialValue();
        }

        @Override // java.lang.ThreadLocal
        protected WeakReference initialValue() {
            return new WeakReference(StackMachine.allocateStack());
        }
    };

    protected StackMachine(Regex regex, char[] cArr, int i, int i2) {
        super(regex, cArr, i, i2);
        this.stack = regex.stackNeeded ? fetchStack() : null;
        int i3 = regex.numRepeat + (regex.numMem << 1);
        this.repeatStk = i3 > 0 ? new int[i3] : null;
        this.memStartStk = regex.numRepeat - 1;
        this.memEndStk = this.memStartStk + regex.numMem;
    }

    private static StackEntry[] allocateStack() {
        StackEntry[] stackEntryArr = new StackEntry[64];
        stackEntryArr[0] = new StackEntry();
        return stackEntryArr;
    }

    private void doubleStack() {
        StackEntry[] stackEntryArr = new StackEntry[this.stack.length << 1];
        System.arraycopy(this.stack, 0, stackEntryArr, 0, this.stack.length);
        this.stack = stackEntryArr;
    }

    private static StackEntry[] fetchStack() {
        StackEntry[] stackEntryArr = (StackEntry[]) ((WeakReference) stacks.get()).get();
        if (stackEntryArr == null) {
            StackEntry[] stackEntryArrAllocateStack = allocateStack();
            stackEntryArr = stackEntryArrAllocateStack;
            stacks.set(new WeakReference(stackEntryArrAllocateStack));
        }
        return stackEntryArr;
    }

    protected final void init() {
        if (this.stack != null) {
            pushEnsured(1, this.regex.codeLength - 1);
        }
        if (this.repeatStk != null) {
            for (int i = 1; i <= this.regex.numMem; i++) {
                int[] iArr = this.repeatStk;
                int i2 = i + this.memStartStk;
                this.repeatStk[i + this.memEndStk] = -1;
                iArr[i2] = -1;
            }
        }
    }

    protected final StackEntry ensure1() {
        if (this.stk >= this.stack.length) {
            doubleStack();
        }
        StackEntry stackEntry = this.stack[this.stk];
        if (stackEntry == null) {
            StackEntry[] stackEntryArr = this.stack;
            int i = this.stk;
            StackEntry stackEntry2 = new StackEntry();
            stackEntry = stackEntry2;
            stackEntryArr[i] = stackEntry2;
        }
        return stackEntry;
    }

    protected final void pushType(int i) {
        ensure1().type = i;
        this.stk++;
    }

    private void push(int i, int i2, int i3, int i4) {
        StackEntry stackEntryEnsure1 = ensure1();
        stackEntryEnsure1.type = i;
        stackEntryEnsure1.setStatePCode(i2);
        stackEntryEnsure1.setStatePStr(i3);
        stackEntryEnsure1.setStatePStrPrev(i4);
        this.stk++;
    }

    protected final void pushEnsured(int i, int i2) {
        StackEntry stackEntry = this.stack[this.stk];
        stackEntry.type = i;
        stackEntry.setStatePCode(i2);
        this.stk++;
    }

    protected final void pushAlt(int i, int i2, int i3) {
        push(1, i, i2, i3);
    }

    protected final void pushPos(int i, int i2) {
        push(StackType.POS, -1, i, i2);
    }

    protected final void pushPosNot(int i, int i2, int i3) {
        push(3, i, i2, i3);
    }

    protected final void pushStopBT() {
        pushType(StackType.STOP_BT);
    }

    protected final void pushLookBehindNot(int i, int i2, int i3) {
        push(2, i, i2, i3);
    }

    protected final void pushRepeat(int i, int i2) {
        StackEntry stackEntryEnsure1 = ensure1();
        stackEntryEnsure1.type = StackType.REPEAT;
        stackEntryEnsure1.setRepeatNum(i);
        stackEntryEnsure1.setRepeatPCode(i2);
        stackEntryEnsure1.setRepeatCount(0);
        this.stk++;
    }

    protected final void pushRepeatInc(int i) {
        StackEntry stackEntryEnsure1 = ensure1();
        stackEntryEnsure1.type = 768;
        stackEntryEnsure1.setSi(i);
        this.stk++;
    }

    protected final void pushMemStart(int i, int i2) {
        StackEntry stackEntryEnsure1 = ensure1();
        stackEntryEnsure1.type = 256;
        stackEntryEnsure1.setMemNum(i);
        stackEntryEnsure1.setMemPstr(i2);
        stackEntryEnsure1.setMemStart(this.repeatStk[this.memStartStk + i]);
        stackEntryEnsure1.setMemEnd(this.repeatStk[this.memEndStk + i]);
        this.repeatStk[this.memStartStk + i] = this.stk;
        this.repeatStk[this.memEndStk + i] = -1;
        this.stk++;
    }

    protected final void pushMemEnd(int i, int i2) {
        StackEntry stackEntryEnsure1 = ensure1();
        stackEntryEnsure1.type = StackType.MEM_END;
        stackEntryEnsure1.setMemNum(i);
        stackEntryEnsure1.setMemPstr(i2);
        stackEntryEnsure1.setMemStart(this.repeatStk[this.memStartStk + i]);
        stackEntryEnsure1.setMemEnd(this.repeatStk[this.memEndStk + i]);
        this.repeatStk[this.memEndStk + i] = this.stk;
        this.stk++;
    }

    protected final void pushMemEndMark(int i) {
        StackEntry stackEntryEnsure1 = ensure1();
        stackEntryEnsure1.type = StackType.MEM_END_MARK;
        stackEntryEnsure1.setMemNum(i);
        this.stk++;
    }

    protected final int getMemStart(int i) {
        int i2 = 0;
        int i3 = this.stk;
        while (i3 > 0) {
            i3--;
            StackEntry stackEntry = this.stack[i3];
            if ((stackEntry.type & 32768) != 0 && stackEntry.getMemNum() == i) {
                i2++;
            } else if (stackEntry.type == 256 && stackEntry.getMemNum() == i) {
                if (i2 == 0) {
                    break;
                }
                i2--;
            }
        }
        return i3;
    }

    protected final void pushNullCheckStart(int i, int i2) {
        StackEntry stackEntryEnsure1 = ensure1();
        stackEntryEnsure1.type = 12288;
        stackEntryEnsure1.setNullCheckNum(i);
        stackEntryEnsure1.setNullCheckPStr(i2);
        this.stk++;
    }

    protected final void pushNullCheckEnd(int i) {
        StackEntry stackEntryEnsure1 = ensure1();
        stackEntryEnsure1.type = StackType.NULL_CHECK_END;
        stackEntryEnsure1.setNullCheckNum(i);
        this.stk++;
    }

    protected final void popOne() {
        this.stk--;
    }

    protected final StackEntry pop() {
        switch (this.regex.stackPopLevel) {
            case 0:
                return popFree();
            case 1:
                return popMemStart();
            default:
                return popDefault();
        }
    }

    private StackEntry popFree() {
        StackEntry stackEntry;
        do {
            StackEntry[] stackEntryArr = this.stack;
            int i = this.stk - 1;
            this.stk = i;
            stackEntry = stackEntryArr[i];
        } while ((stackEntry.type & 255) == 0);
        return stackEntry;
    }

    private StackEntry popMemStart() {
        while (true) {
            StackEntry[] stackEntryArr = this.stack;
            int i = this.stk - 1;
            this.stk = i;
            StackEntry stackEntry = stackEntryArr[i];
            if ((stackEntry.type & 255) != 0) {
                return stackEntry;
            }
            if (stackEntry.type == 256) {
                this.repeatStk[this.memStartStk + stackEntry.getMemNum()] = stackEntry.getMemStart();
                this.repeatStk[this.memEndStk + stackEntry.getMemNum()] = stackEntry.getMemEnd();
            }
        }
    }

    private StackEntry popDefault() {
        while (true) {
            StackEntry[] stackEntryArr = this.stack;
            int i = this.stk - 1;
            this.stk = i;
            StackEntry stackEntry = stackEntryArr[i];
            if ((stackEntry.type & 255) != 0) {
                return stackEntry;
            }
            if (stackEntry.type == 256) {
                this.repeatStk[this.memStartStk + stackEntry.getMemNum()] = stackEntry.getMemStart();
                this.repeatStk[this.memEndStk + stackEntry.getMemNum()] = stackEntry.getMemEnd();
            } else if (stackEntry.type == 768) {
                this.stack[stackEntry.getSi()].decreaseRepeatCount();
            } else if (stackEntry.type == 33280) {
                this.repeatStk[this.memStartStk + stackEntry.getMemNum()] = stackEntry.getMemStart();
                this.repeatStk[this.memEndStk + stackEntry.getMemNum()] = stackEntry.getMemEnd();
            }
        }
    }

    protected final void popTilPosNot() {
        while (true) {
            this.stk--;
            StackEntry stackEntry = this.stack[this.stk];
            if (stackEntry.type != 3) {
                if (stackEntry.type == 256) {
                    this.repeatStk[this.memStartStk + stackEntry.getMemNum()] = stackEntry.getMemStart();
                    this.repeatStk[this.memEndStk + stackEntry.getMemNum()] = stackEntry.getMemStart();
                } else if (stackEntry.type == 768) {
                    this.stack[stackEntry.getSi()].decreaseRepeatCount();
                } else if (stackEntry.type == 33280) {
                    this.repeatStk[this.memStartStk + stackEntry.getMemNum()] = stackEntry.getMemStart();
                    this.repeatStk[this.memEndStk + stackEntry.getMemNum()] = stackEntry.getMemStart();
                }
            } else {
                return;
            }
        }
    }

    protected final void popTilLookBehindNot() {
        while (true) {
            this.stk--;
            StackEntry stackEntry = this.stack[this.stk];
            if (stackEntry.type != 2) {
                if (stackEntry.type == 256) {
                    this.repeatStk[this.memStartStk + stackEntry.getMemNum()] = stackEntry.getMemStart();
                    this.repeatStk[this.memEndStk + stackEntry.getMemNum()] = stackEntry.getMemEnd();
                } else if (stackEntry.type == 768) {
                    this.stack[stackEntry.getSi()].decreaseRepeatCount();
                } else if (stackEntry.type == 33280) {
                    this.repeatStk[this.memStartStk + stackEntry.getMemNum()] = stackEntry.getMemStart();
                    this.repeatStk[this.memEndStk + stackEntry.getMemNum()] = stackEntry.getMemEnd();
                }
            } else {
                return;
            }
        }
    }

    protected final int posEnd() {
        int i = this.stk;
        while (true) {
            i--;
            StackEntry stackEntry = this.stack[i];
            if ((stackEntry.type & StackType.MASK_TO_VOID_TARGET) != 0) {
                stackEntry.type = StackType.VOID;
            } else if (stackEntry.type == 1280) {
                stackEntry.type = StackType.VOID;
                return i;
            }
        }
    }

    protected final void stopBtEnd() {
        int i = this.stk;
        while (true) {
            i--;
            StackEntry stackEntry = this.stack[i];
            if ((stackEntry.type & StackType.MASK_TO_VOID_TARGET) != 0) {
                stackEntry.type = StackType.VOID;
            } else if (stackEntry.type == 1536) {
                stackEntry.type = StackType.VOID;
                return;
            }
        }
    }

    protected final int nullCheck(int i, int i2) {
        StackEntry stackEntry;
        int i3 = this.stk;
        while (true) {
            i3--;
            stackEntry = this.stack[i3];
            if (stackEntry.type == 12288 && stackEntry.getNullCheckNum() == i) {
                break;
            }
        }
        return stackEntry.getNullCheckPStr() == i2 ? 1 : 0;
    }

    protected final int nullCheckMemSt(int i, int i2) {
        return -nullCheck(i, i2);
    }

    protected final int getRepeat(int i) {
        int i2 = 0;
        int i3 = this.stk;
        while (true) {
            i3--;
            StackEntry stackEntry = this.stack[i3];
            if (stackEntry.type == 1792) {
                if (i2 == 0 && stackEntry.getRepeatNum() == i) {
                    return i3;
                }
            } else if (stackEntry.type == 2048) {
                i2--;
            } else if (stackEntry.type == 2304) {
                i2++;
            }
        }
    }

    protected final int sreturn() {
        int i = 0;
        int i2 = this.stk;
        while (true) {
            i2--;
            StackEntry stackEntry = this.stack[i2];
            if (stackEntry.type == 2048) {
                if (i == 0) {
                    return stackEntry.getCallFrameRetAddr();
                }
                i--;
            } else if (stackEntry.type == 2304) {
                i++;
            }
        }
    }
}
