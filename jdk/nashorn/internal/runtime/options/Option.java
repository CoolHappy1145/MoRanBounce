package jdk.nashorn.internal.runtime.options;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/options/Option.class */
public class Option {
    protected Object value;

    Option(Object obj) {
        this.value = obj;
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        return getValue() + " [" + getValue().getClass() + "]";
    }
}
