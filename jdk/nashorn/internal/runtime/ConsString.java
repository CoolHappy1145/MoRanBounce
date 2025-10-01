package jdk.nashorn.internal.runtime;

import java.util.ArrayDeque;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ConsString.class */
public final class ConsString implements CharSequence {
    private CharSequence left;
    private CharSequence right;
    private final int length;
    private int state = 0;
    private static final int STATE_NEW = 0;
    private static final int STATE_THRESHOLD = 2;
    private static final int STATE_FLATTENED = -1;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ConsString.class.desiredAssertionStatus();
    }

    public ConsString(CharSequence charSequence, CharSequence charSequence2) {
        if (!$assertionsDisabled) {
            if (!((charSequence instanceof String) || (charSequence instanceof ConsString))) {
                throw new AssertionError();
            }
        }
        if (!$assertionsDisabled) {
            if (!((charSequence2 instanceof String) || (charSequence2 instanceof ConsString))) {
                throw new AssertionError();
            }
        }
        this.left = charSequence;
        this.right = charSequence2;
        this.length = charSequence.length() + charSequence2.length();
        if (this.length < 0) {
            throw new IllegalArgumentException("too big concatenated String");
        }
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return (String) flattened(true);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.length;
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return flattened(true).charAt(i);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return flattened(true).subSequence(i, i2);
    }

    public CharSequence[] getComponents() {
        return new CharSequence[]{this.left, this.right};
    }

    private CharSequence flattened(boolean z) {
        if (this.state != -1) {
            flatten(z);
        }
        return this.left;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void flatten(boolean z) {
        char[] cArr = new char[this.length];
        int length = this.length;
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.addFirst(this.left);
        CharSequence charSequenceFlattened = this.right;
        do {
            if (charSequenceFlattened instanceof ConsString) {
                ConsString consString = (ConsString) charSequenceFlattened;
                if (consString.state == -1) {
                    charSequenceFlattened = consString.flattened(false);
                } else {
                    if (z) {
                        int i = consString.state + 1;
                        consString.state = i;
                        if (i >= 2) {
                        }
                    }
                    arrayDeque.addFirst(consString.left);
                    charSequenceFlattened = consString.right;
                }
            } else {
                String str = (String) charSequenceFlattened;
                length -= str.length();
                str.getChars(0, str.length(), cArr, length);
                charSequenceFlattened = arrayDeque.isEmpty() ? null : (CharSequence) arrayDeque.pollFirst();
            }
        } while (charSequenceFlattened != null);
        this.left = new String(cArr);
        this.right = "";
        this.state = -1;
    }
}
