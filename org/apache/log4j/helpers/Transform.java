package org.apache.log4j.helpers;

/* loaded from: L-out.jar:org/apache/log4j/helpers/Transform.class */
public class Transform {
    private static final String CDATA_START = "<![CDATA[";
    private static final String CDATA_END = "]]>";
    private static final String CDATA_PSEUDO_END = "]]&gt;";
    private static final String CDATA_EMBEDED_END = "]]>]]&gt;<![CDATA[";

    public static String escapeTags(String str) {
        if (str == null || str.length() == 0 || (str.indexOf(34) == -1 && str.indexOf(38) == -1 && str.indexOf(60) == -1 && str.indexOf(62) == -1)) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt > '>') {
                stringBuffer.append(cCharAt);
            } else if (cCharAt == '<') {
                stringBuffer.append("&lt;");
            } else if (cCharAt == '>') {
                stringBuffer.append("&gt;");
            } else if (cCharAt == '&') {
                stringBuffer.append("&amp;");
            } else if (cCharAt == '\"') {
                stringBuffer.append("&quot;");
            } else {
                stringBuffer.append(cCharAt);
            }
        }
        return stringBuffer.toString();
    }

    public static void appendEscapingCDATA(StringBuffer stringBuffer, String str) {
        if (str != null) {
            int iIndexOf = str.indexOf(CDATA_END);
            if (iIndexOf < 0) {
                stringBuffer.append(str);
                return;
            }
            int i = 0;
            while (iIndexOf > -1) {
                stringBuffer.append(str.substring(i, iIndexOf));
                stringBuffer.append(CDATA_EMBEDED_END);
                i = iIndexOf + 3;
                if (i < str.length()) {
                    iIndexOf = str.indexOf(CDATA_END, i);
                } else {
                    return;
                }
            }
            stringBuffer.append(str.substring(i));
        }
    }
}
