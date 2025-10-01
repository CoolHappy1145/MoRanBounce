package jdk.nashorn.internal.parser;

/* loaded from: L-out.jar:jdk/nashorn/internal/parser/Scanner.class */
public class Scanner {
    protected final char[] content;
    protected int position;
    protected final int limit;
    protected int line;
    protected char ch0;
    protected char ch1;
    protected char ch2;
    protected char ch3;

    protected Scanner(char[] cArr, int i, int i2, int i3) {
        this.content = cArr;
        this.position = i2;
        this.limit = i2 + i3;
        this.line = i;
        reset(this.position);
    }

    protected Scanner(String str) {
        this(str.toCharArray(), 0, 0, str.length());
    }

    Scanner(Scanner scanner, State state) {
        this.content = scanner.content;
        this.position = state.position;
        this.limit = state.limit;
        this.line = state.line;
        reset(this.position);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/parser/Scanner$State.class */
    static class State {
        public final int position;
        public int limit;
        public final int line;

        State(int i, int i2, int i3) {
            this.position = i;
            this.limit = i2;
            this.line = i3;
        }

        void setLimit(int i) {
            this.limit = i;
        }

        boolean isEmpty() {
            return this.position == this.limit;
        }
    }

    State saveState() {
        return new State(this.position, this.limit, this.line);
    }

    void restoreState(State state) {
        this.position = state.position;
        this.line = state.line;
        reset(this.position);
    }

    protected final boolean atEOF() {
        return this.position == this.limit;
    }

    protected final char charAt(int i) {
        if (i < this.limit) {
            return this.content[i];
        }
        return (char) 0;
    }

    protected final void reset(int i) {
        this.ch0 = charAt(i);
        this.ch1 = charAt(i + 1);
        this.ch2 = charAt(i + 2);
        this.ch3 = charAt(i + 3);
        this.position = i < this.limit ? i : this.limit;
    }

    protected final void skip(int i) {
        if (i == 1 && !atEOF()) {
            this.ch0 = this.ch1;
            this.ch1 = this.ch2;
            this.ch2 = this.ch3;
            this.ch3 = charAt(this.position + 4);
            this.position++;
            return;
        }
        if (i != 0) {
            reset(this.position + i);
        }
    }
}
