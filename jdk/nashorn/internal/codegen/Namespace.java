package jdk.nashorn.internal.codegen;

import java.util.HashMap;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/Namespace.class */
public class Namespace {
    private final Namespace parent;
    private final HashMap directory;

    public Namespace() {
        this(null);
    }

    public Namespace(Namespace namespace) {
        this.parent = namespace;
        this.directory = new HashMap();
    }

    public Namespace getParent() {
        return this.parent;
    }

    public String uniqueName(String str) {
        String strSubstring = str.length() > 32768 ? str.substring(0, 32768) : str;
        Namespace parent = this;
        while (true) {
            Namespace namespace = parent;
            if (namespace != null) {
                HashMap map = namespace.directory;
                Integer num = (Integer) map.get(strSubstring);
                if (num == null) {
                    parent = namespace.getParent();
                } else {
                    int iIntValue = num.intValue() + 1;
                    map.put(strSubstring, Integer.valueOf(iIntValue));
                    return strSubstring + CompilerConstants.ID_FUNCTION_SEPARATOR.symbolName() + iIntValue;
                }
            } else {
                this.directory.put(strSubstring, 0);
                return strSubstring;
            }
        }
    }

    public String toString() {
        return this.directory.toString();
    }
}
