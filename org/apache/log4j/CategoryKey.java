package org.apache.log4j;

/* loaded from: L-out.jar:org/apache/log4j/CategoryKey.class */
class CategoryKey {
    String name;
    int hashCache;
    static Class class$org$apache$log4j$CategoryKey;

    CategoryKey(String str) {
        this.name = str;
        this.hashCache = str.hashCode();
    }

    public final int hashCode() {
        return this.hashCache;
    }

    public final boolean equals(Object obj) throws Throwable {
        Class<?> clsClass$;
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (class$org$apache$log4j$CategoryKey == null) {
            clsClass$ = class$("org.apache.log4j.CategoryKey");
            class$org$apache$log4j$CategoryKey = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$CategoryKey;
        }
        if (clsClass$ == obj.getClass()) {
            return this.name.equals(((CategoryKey) obj).name);
        }
        return false;
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }
}
