package jdk.nashorn.internal.runtime.regexp;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import jdk.nashorn.internal.runtime.options.Options;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/RegExpFactory.class */
public class RegExpFactory {
    private static final RegExpFactory instance;
    private static final String JDK = "jdk";
    private static final String JONI = "joni";
    private static final Map REGEXP_CACHE = Collections.synchronizedMap(new WeakHashMap());

    static {
        String stringProperty;
        stringProperty = Options.getStringProperty("nashorn.regexp.impl", JONI);
        switch (stringProperty) {
            case "joni":
                instance = new JoniRegExp.Factory();
                return;
            case "jdk":
                instance = new RegExpFactory();
                return;
            default:
                instance = null;
                throw new InternalError("Unsupported RegExp factory: " + stringProperty);
        }
    }

    public RegExp compile(String str, String str2) {
        return new JdkRegExp(str, str2);
    }

    public static RegExp create(String str, String str2) {
        String str3 = str + "/" + str2;
        RegExp regExpCompile = (RegExp) REGEXP_CACHE.get(str3);
        if (regExpCompile == null) {
            regExpCompile = instance.compile(str, str2);
            REGEXP_CACHE.put(str3, regExpCompile);
        }
        return regExpCompile;
    }

    public static void validate(String str, String str2) {
        create(str, str2);
    }

    public static boolean usesJavaUtilRegex() {
        return instance != null && instance.getClass() == RegExpFactory.class;
    }
}
