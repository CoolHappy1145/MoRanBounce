package jdk.nashorn.internal.runtime.regexp;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/RegExpResult.class */
public final class RegExpResult {
    final Object[] groups;
    final int index;
    final String input;

    public RegExpResult(String str, int i, Object[] objArr) {
        this.input = str;
        this.index = i;
        this.groups = objArr;
    }

    public Object[] getGroups() {
        return this.groups;
    }

    public String getInput() {
        return this.input;
    }

    public int getIndex() {
        return this.index;
    }

    public int length() {
        return ((String) this.groups[0]).length();
    }

    public Object getGroup(int i) {
        return (i < 0 || i >= this.groups.length) ? "" : this.groups[i];
    }

    public Object getLastParen() {
        return this.groups.length > 1 ? this.groups[this.groups.length - 1] : "";
    }
}
