package jdk.nashorn.internal.runtime.options;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/options/KeyValueOption.class */
public class KeyValueOption extends Option {
    protected Map map;

    KeyValueOption(String str) {
        super(str);
        initialize();
    }

    Map getValues() {
        return Collections.unmodifiableMap(this.map);
    }

    public boolean hasValue(String str) {
        return (this.map == null || this.map.get(str) == null) ? false : true;
    }

    String getValue(String str) {
        if (this.map == null) {
            return null;
        }
        String str2 = (String) this.map.get(str);
        if ("".equals(str2)) {
            return null;
        }
        return str2;
    }

    private void initialize() {
        if (getValue() == null) {
            return;
        }
        this.map = new LinkedHashMap();
        StringTokenizer stringTokenizer = new StringTokenizer((String) getValue(), ",");
        while (stringTokenizer.hasMoreElements()) {
            String strNextToken = stringTokenizer.nextToken();
            String[] strArrSplit = strNextToken.split(CallSiteDescriptor.TOKEN_DELIMITER);
            if (strArrSplit.length == 1) {
                this.map.put(strArrSplit[0], "");
            } else if (strArrSplit.length == 2) {
                this.map.put(strArrSplit[0], strArrSplit[1]);
            } else {
                throw new IllegalArgumentException(strNextToken);
            }
        }
    }
}
