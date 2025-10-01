package org.apache.log4j.pattern;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.LogLog;

/* loaded from: L-out.jar:org/apache/log4j/pattern/PatternParser.class */
public final class PatternParser {
    private static final char ESCAPE_CHAR = '%';
    private static final int LITERAL_STATE = 0;
    private static final int CONVERTER_STATE = 1;
    private static final int DOT_STATE = 3;
    private static final int MIN_STATE = 4;
    private static final int MAX_STATE = 5;
    private static final Map PATTERN_LAYOUT_RULES;
    private static final Map FILENAME_PATTERN_RULES;
    static Class class$org$apache$log4j$pattern$LoggerPatternConverter;
    static Class class$org$apache$log4j$pattern$ClassNamePatternConverter;
    static Class class$org$apache$log4j$pattern$DatePatternConverter;
    static Class class$org$apache$log4j$pattern$FileLocationPatternConverter;
    static Class class$org$apache$log4j$pattern$FullLocationPatternConverter;
    static Class class$org$apache$log4j$pattern$LineLocationPatternConverter;
    static Class class$org$apache$log4j$pattern$MessagePatternConverter;
    static Class class$org$apache$log4j$pattern$LineSeparatorPatternConverter;
    static Class class$org$apache$log4j$pattern$MethodLocationPatternConverter;
    static Class class$org$apache$log4j$pattern$LevelPatternConverter;
    static Class class$org$apache$log4j$pattern$RelativeTimePatternConverter;
    static Class class$org$apache$log4j$pattern$ThreadPatternConverter;
    static Class class$org$apache$log4j$pattern$NDCPatternConverter;
    static Class class$org$apache$log4j$pattern$PropertiesPatternConverter;
    static Class class$org$apache$log4j$pattern$SequenceNumberPatternConverter;

    /* renamed from: class$org$apache$log4j$pattern$ThrowableInformationPatternConverter */
    static Class f201x905a6f24;
    static Class class$org$apache$log4j$pattern$FileDatePatternConverter;
    static Class class$org$apache$log4j$pattern$IntegerPatternConverter;

    static {
        Class clsClass$;
        Class clsClass$2;
        Class clsClass$3;
        Class clsClass$4;
        Class clsClass$5;
        Class clsClass$6;
        Class clsClass$7;
        Class clsClass$8;
        Class clsClass$9;
        Class clsClass$10;
        Class clsClass$11;
        Class clsClass$12;
        Class clsClass$13;
        Class clsClass$14;
        Class clsClass$15;
        Class clsClass$16;
        Class clsClass$17;
        Class clsClass$18;
        Class clsClass$19;
        Class clsClass$20;
        Class clsClass$21;
        Class clsClass$22;
        Class clsClass$23;
        Class clsClass$24;
        Class clsClass$25;
        Class clsClass$26;
        Class clsClass$27;
        Class clsClass$28;
        Class clsClass$29;
        Class clsClass$30;
        Class clsClass$31;
        Class clsClass$32;
        Class clsClass$33;
        HashMap map = new HashMap(17);
        if (class$org$apache$log4j$pattern$LoggerPatternConverter == null) {
            clsClass$ = class$("org.apache.log4j.pattern.LoggerPatternConverter");
            class$org$apache$log4j$pattern$LoggerPatternConverter = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$pattern$LoggerPatternConverter;
        }
        map.put("c", clsClass$);
        if (class$org$apache$log4j$pattern$LoggerPatternConverter == null) {
            clsClass$2 = class$("org.apache.log4j.pattern.LoggerPatternConverter");
            class$org$apache$log4j$pattern$LoggerPatternConverter = clsClass$2;
        } else {
            clsClass$2 = class$org$apache$log4j$pattern$LoggerPatternConverter;
        }
        map.put("logger", clsClass$2);
        if (class$org$apache$log4j$pattern$ClassNamePatternConverter == null) {
            clsClass$3 = class$("org.apache.log4j.pattern.ClassNamePatternConverter");
            class$org$apache$log4j$pattern$ClassNamePatternConverter = clsClass$3;
        } else {
            clsClass$3 = class$org$apache$log4j$pattern$ClassNamePatternConverter;
        }
        map.put("C", clsClass$3);
        if (class$org$apache$log4j$pattern$ClassNamePatternConverter == null) {
            clsClass$4 = class$("org.apache.log4j.pattern.ClassNamePatternConverter");
            class$org$apache$log4j$pattern$ClassNamePatternConverter = clsClass$4;
        } else {
            clsClass$4 = class$org$apache$log4j$pattern$ClassNamePatternConverter;
        }
        map.put("class", clsClass$4);
        if (class$org$apache$log4j$pattern$DatePatternConverter == null) {
            clsClass$5 = class$("org.apache.log4j.pattern.DatePatternConverter");
            class$org$apache$log4j$pattern$DatePatternConverter = clsClass$5;
        } else {
            clsClass$5 = class$org$apache$log4j$pattern$DatePatternConverter;
        }
        map.put("d", clsClass$5);
        if (class$org$apache$log4j$pattern$DatePatternConverter == null) {
            clsClass$6 = class$("org.apache.log4j.pattern.DatePatternConverter");
            class$org$apache$log4j$pattern$DatePatternConverter = clsClass$6;
        } else {
            clsClass$6 = class$org$apache$log4j$pattern$DatePatternConverter;
        }
        map.put("date", clsClass$6);
        if (class$org$apache$log4j$pattern$FileLocationPatternConverter == null) {
            clsClass$7 = class$("org.apache.log4j.pattern.FileLocationPatternConverter");
            class$org$apache$log4j$pattern$FileLocationPatternConverter = clsClass$7;
        } else {
            clsClass$7 = class$org$apache$log4j$pattern$FileLocationPatternConverter;
        }
        map.put("F", clsClass$7);
        if (class$org$apache$log4j$pattern$FileLocationPatternConverter == null) {
            clsClass$8 = class$("org.apache.log4j.pattern.FileLocationPatternConverter");
            class$org$apache$log4j$pattern$FileLocationPatternConverter = clsClass$8;
        } else {
            clsClass$8 = class$org$apache$log4j$pattern$FileLocationPatternConverter;
        }
        map.put("file", clsClass$8);
        if (class$org$apache$log4j$pattern$FullLocationPatternConverter == null) {
            clsClass$9 = class$("org.apache.log4j.pattern.FullLocationPatternConverter");
            class$org$apache$log4j$pattern$FullLocationPatternConverter = clsClass$9;
        } else {
            clsClass$9 = class$org$apache$log4j$pattern$FullLocationPatternConverter;
        }
        map.put("l", clsClass$9);
        if (class$org$apache$log4j$pattern$LineLocationPatternConverter == null) {
            clsClass$10 = class$("org.apache.log4j.pattern.LineLocationPatternConverter");
            class$org$apache$log4j$pattern$LineLocationPatternConverter = clsClass$10;
        } else {
            clsClass$10 = class$org$apache$log4j$pattern$LineLocationPatternConverter;
        }
        map.put("L", clsClass$10);
        if (class$org$apache$log4j$pattern$LineLocationPatternConverter == null) {
            clsClass$11 = class$("org.apache.log4j.pattern.LineLocationPatternConverter");
            class$org$apache$log4j$pattern$LineLocationPatternConverter = clsClass$11;
        } else {
            clsClass$11 = class$org$apache$log4j$pattern$LineLocationPatternConverter;
        }
        map.put("line", clsClass$11);
        if (class$org$apache$log4j$pattern$MessagePatternConverter == null) {
            clsClass$12 = class$("org.apache.log4j.pattern.MessagePatternConverter");
            class$org$apache$log4j$pattern$MessagePatternConverter = clsClass$12;
        } else {
            clsClass$12 = class$org$apache$log4j$pattern$MessagePatternConverter;
        }
        map.put("m", clsClass$12);
        if (class$org$apache$log4j$pattern$MessagePatternConverter == null) {
            clsClass$13 = class$("org.apache.log4j.pattern.MessagePatternConverter");
            class$org$apache$log4j$pattern$MessagePatternConverter = clsClass$13;
        } else {
            clsClass$13 = class$org$apache$log4j$pattern$MessagePatternConverter;
        }
        map.put("message", clsClass$13);
        if (class$org$apache$log4j$pattern$LineSeparatorPatternConverter == null) {
            clsClass$14 = class$("org.apache.log4j.pattern.LineSeparatorPatternConverter");
            class$org$apache$log4j$pattern$LineSeparatorPatternConverter = clsClass$14;
        } else {
            clsClass$14 = class$org$apache$log4j$pattern$LineSeparatorPatternConverter;
        }
        map.put("n", clsClass$14);
        if (class$org$apache$log4j$pattern$MethodLocationPatternConverter == null) {
            clsClass$15 = class$("org.apache.log4j.pattern.MethodLocationPatternConverter");
            class$org$apache$log4j$pattern$MethodLocationPatternConverter = clsClass$15;
        } else {
            clsClass$15 = class$org$apache$log4j$pattern$MethodLocationPatternConverter;
        }
        map.put("M", clsClass$15);
        if (class$org$apache$log4j$pattern$MethodLocationPatternConverter == null) {
            clsClass$16 = class$("org.apache.log4j.pattern.MethodLocationPatternConverter");
            class$org$apache$log4j$pattern$MethodLocationPatternConverter = clsClass$16;
        } else {
            clsClass$16 = class$org$apache$log4j$pattern$MethodLocationPatternConverter;
        }
        map.put("method", clsClass$16);
        if (class$org$apache$log4j$pattern$LevelPatternConverter == null) {
            clsClass$17 = class$("org.apache.log4j.pattern.LevelPatternConverter");
            class$org$apache$log4j$pattern$LevelPatternConverter = clsClass$17;
        } else {
            clsClass$17 = class$org$apache$log4j$pattern$LevelPatternConverter;
        }
        map.put("p", clsClass$17);
        if (class$org$apache$log4j$pattern$LevelPatternConverter == null) {
            clsClass$18 = class$("org.apache.log4j.pattern.LevelPatternConverter");
            class$org$apache$log4j$pattern$LevelPatternConverter = clsClass$18;
        } else {
            clsClass$18 = class$org$apache$log4j$pattern$LevelPatternConverter;
        }
        map.put("level", clsClass$18);
        if (class$org$apache$log4j$pattern$RelativeTimePatternConverter == null) {
            clsClass$19 = class$("org.apache.log4j.pattern.RelativeTimePatternConverter");
            class$org$apache$log4j$pattern$RelativeTimePatternConverter = clsClass$19;
        } else {
            clsClass$19 = class$org$apache$log4j$pattern$RelativeTimePatternConverter;
        }
        map.put("r", clsClass$19);
        if (class$org$apache$log4j$pattern$RelativeTimePatternConverter == null) {
            clsClass$20 = class$("org.apache.log4j.pattern.RelativeTimePatternConverter");
            class$org$apache$log4j$pattern$RelativeTimePatternConverter = clsClass$20;
        } else {
            clsClass$20 = class$org$apache$log4j$pattern$RelativeTimePatternConverter;
        }
        map.put("relative", clsClass$20);
        if (class$org$apache$log4j$pattern$ThreadPatternConverter == null) {
            clsClass$21 = class$("org.apache.log4j.pattern.ThreadPatternConverter");
            class$org$apache$log4j$pattern$ThreadPatternConverter = clsClass$21;
        } else {
            clsClass$21 = class$org$apache$log4j$pattern$ThreadPatternConverter;
        }
        map.put("t", clsClass$21);
        if (class$org$apache$log4j$pattern$ThreadPatternConverter == null) {
            clsClass$22 = class$("org.apache.log4j.pattern.ThreadPatternConverter");
            class$org$apache$log4j$pattern$ThreadPatternConverter = clsClass$22;
        } else {
            clsClass$22 = class$org$apache$log4j$pattern$ThreadPatternConverter;
        }
        map.put("thread", clsClass$22);
        if (class$org$apache$log4j$pattern$NDCPatternConverter == null) {
            clsClass$23 = class$("org.apache.log4j.pattern.NDCPatternConverter");
            class$org$apache$log4j$pattern$NDCPatternConverter = clsClass$23;
        } else {
            clsClass$23 = class$org$apache$log4j$pattern$NDCPatternConverter;
        }
        map.put("x", clsClass$23);
        if (class$org$apache$log4j$pattern$NDCPatternConverter == null) {
            clsClass$24 = class$("org.apache.log4j.pattern.NDCPatternConverter");
            class$org$apache$log4j$pattern$NDCPatternConverter = clsClass$24;
        } else {
            clsClass$24 = class$org$apache$log4j$pattern$NDCPatternConverter;
        }
        map.put("ndc", clsClass$24);
        if (class$org$apache$log4j$pattern$PropertiesPatternConverter == null) {
            clsClass$25 = class$("org.apache.log4j.pattern.PropertiesPatternConverter");
            class$org$apache$log4j$pattern$PropertiesPatternConverter = clsClass$25;
        } else {
            clsClass$25 = class$org$apache$log4j$pattern$PropertiesPatternConverter;
        }
        map.put("X", clsClass$25);
        if (class$org$apache$log4j$pattern$PropertiesPatternConverter == null) {
            clsClass$26 = class$("org.apache.log4j.pattern.PropertiesPatternConverter");
            class$org$apache$log4j$pattern$PropertiesPatternConverter = clsClass$26;
        } else {
            clsClass$26 = class$org$apache$log4j$pattern$PropertiesPatternConverter;
        }
        map.put("properties", clsClass$26);
        if (class$org$apache$log4j$pattern$SequenceNumberPatternConverter == null) {
            clsClass$27 = class$("org.apache.log4j.pattern.SequenceNumberPatternConverter");
            class$org$apache$log4j$pattern$SequenceNumberPatternConverter = clsClass$27;
        } else {
            clsClass$27 = class$org$apache$log4j$pattern$SequenceNumberPatternConverter;
        }
        map.put("sn", clsClass$27);
        if (class$org$apache$log4j$pattern$SequenceNumberPatternConverter == null) {
            clsClass$28 = class$("org.apache.log4j.pattern.SequenceNumberPatternConverter");
            class$org$apache$log4j$pattern$SequenceNumberPatternConverter = clsClass$28;
        } else {
            clsClass$28 = class$org$apache$log4j$pattern$SequenceNumberPatternConverter;
        }
        map.put("sequenceNumber", clsClass$28);
        if (f201x905a6f24 == null) {
            clsClass$29 = class$("org.apache.log4j.pattern.ThrowableInformationPatternConverter");
            f201x905a6f24 = clsClass$29;
        } else {
            clsClass$29 = f201x905a6f24;
        }
        map.put("throwable", clsClass$29);
        PATTERN_LAYOUT_RULES = new ReadOnlyMap(map);
        HashMap map2 = new HashMap(4);
        if (class$org$apache$log4j$pattern$FileDatePatternConverter == null) {
            clsClass$30 = class$("org.apache.log4j.pattern.FileDatePatternConverter");
            class$org$apache$log4j$pattern$FileDatePatternConverter = clsClass$30;
        } else {
            clsClass$30 = class$org$apache$log4j$pattern$FileDatePatternConverter;
        }
        map2.put("d", clsClass$30);
        if (class$org$apache$log4j$pattern$FileDatePatternConverter == null) {
            clsClass$31 = class$("org.apache.log4j.pattern.FileDatePatternConverter");
            class$org$apache$log4j$pattern$FileDatePatternConverter = clsClass$31;
        } else {
            clsClass$31 = class$org$apache$log4j$pattern$FileDatePatternConverter;
        }
        map2.put("date", clsClass$31);
        if (class$org$apache$log4j$pattern$IntegerPatternConverter == null) {
            clsClass$32 = class$("org.apache.log4j.pattern.IntegerPatternConverter");
            class$org$apache$log4j$pattern$IntegerPatternConverter = clsClass$32;
        } else {
            clsClass$32 = class$org$apache$log4j$pattern$IntegerPatternConverter;
        }
        map2.put("i", clsClass$32);
        if (class$org$apache$log4j$pattern$IntegerPatternConverter == null) {
            clsClass$33 = class$("org.apache.log4j.pattern.IntegerPatternConverter");
            class$org$apache$log4j$pattern$IntegerPatternConverter = clsClass$33;
        } else {
            clsClass$33 = class$org$apache$log4j$pattern$IntegerPatternConverter;
        }
        map2.put("index", clsClass$33);
        FILENAME_PATTERN_RULES = new ReadOnlyMap(map2);
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private PatternParser() {
    }

    public static Map getPatternLayoutRules() {
        return PATTERN_LAYOUT_RULES;
    }

    public static Map getFileNamePatternRules() {
        return FILENAME_PATTERN_RULES;
    }

    private static int extractConverter(char c, String str, int i, StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        stringBuffer.setLength(0);
        if (!Character.isUnicodeIdentifierStart(c)) {
            return i;
        }
        stringBuffer.append(c);
        while (i < str.length() && Character.isUnicodeIdentifierPart(str.charAt(i))) {
            stringBuffer.append(str.charAt(i));
            stringBuffer2.append(str.charAt(i));
            i++;
        }
        return i;
    }

    private static int extractOptions(String str, int i, List list) {
        int iIndexOf;
        while (i < str.length() && str.charAt(i) == '{' && (iIndexOf = str.indexOf(125, i)) != -1) {
            list.add(str.substring(i + 1, iIndexOf));
            i = iIndexOf + 1;
        }
        return i;
    }

    public static void parse(String str, List list, List list2, Map map, Map map2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (str == null) {
            throw new NullPointerException("pattern");
        }
        StringBuffer stringBuffer = new StringBuffer(32);
        int length = str.length();
        boolean z = false;
        int iFinalizeConverter = 0;
        FormattingInfo formattingInfo = FormattingInfo.getDefault();
        while (iFinalizeConverter < length) {
            int i = iFinalizeConverter;
            iFinalizeConverter++;
            char cCharAt = str.charAt(i);
            switch (z) {
                case false:
                    if (iFinalizeConverter == length) {
                        stringBuffer.append(cCharAt);
                        break;
                    } else if (cCharAt == '%') {
                        switch (str.charAt(iFinalizeConverter)) {
                            case '%':
                                stringBuffer.append(cCharAt);
                                iFinalizeConverter++;
                                break;
                            default:
                                if (stringBuffer.length() != 0) {
                                    list.add(new LiteralPatternConverter(stringBuffer.toString()));
                                    list2.add(FormattingInfo.getDefault());
                                }
                                stringBuffer.setLength(0);
                                stringBuffer.append(cCharAt);
                                z = true;
                                formattingInfo = FormattingInfo.getDefault();
                                break;
                        }
                    } else {
                        stringBuffer.append(cCharAt);
                        break;
                    }
                case true:
                    stringBuffer.append(cCharAt);
                    switch (cCharAt) {
                        case OPCode.BACKREF_MULTI /* 45 */:
                            formattingInfo = new FormattingInfo(true, formattingInfo.getMinLength(), formattingInfo.getMaxLength());
                            break;
                        case OPCode.BACKREF_MULTI_IC /* 46 */:
                            z = 3;
                            break;
                        default:
                            if (cCharAt >= '0' && cCharAt <= '9') {
                                formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), cCharAt - '0', formattingInfo.getMaxLength());
                                z = 4;
                                break;
                            } else {
                                iFinalizeConverter = finalizeConverter(cCharAt, str, iFinalizeConverter, stringBuffer, formattingInfo, map, map2, list, list2);
                                z = false;
                                formattingInfo = FormattingInfo.getDefault();
                                stringBuffer.setLength(0);
                                break;
                            }
                    }
                case true:
                    stringBuffer.append(cCharAt);
                    if (cCharAt >= '0' && cCharAt <= '9') {
                        formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), cCharAt - '0');
                        z = 5;
                        break;
                    } else {
                        LogLog.error(new StringBuffer().append("Error occured in position ").append(iFinalizeConverter).append(".\n Was expecting digit, instead got char \"").append(cCharAt).append("\".").toString());
                        z = false;
                        break;
                    }
                case true:
                    stringBuffer.append(cCharAt);
                    if (cCharAt >= '0' && cCharAt <= '9') {
                        formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), (formattingInfo.getMinLength() * 10) + (cCharAt - '0'), formattingInfo.getMaxLength());
                        break;
                    } else if (cCharAt == '.') {
                        z = 3;
                        break;
                    } else {
                        iFinalizeConverter = finalizeConverter(cCharAt, str, iFinalizeConverter, stringBuffer, formattingInfo, map, map2, list, list2);
                        z = false;
                        formattingInfo = FormattingInfo.getDefault();
                        stringBuffer.setLength(0);
                        break;
                    }
                    break;
                case true:
                    stringBuffer.append(cCharAt);
                    if (cCharAt >= '0' && cCharAt <= '9') {
                        formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), (formattingInfo.getMaxLength() * 10) + (cCharAt - '0'));
                        break;
                    } else {
                        iFinalizeConverter = finalizeConverter(cCharAt, str, iFinalizeConverter, stringBuffer, formattingInfo, map, map2, list, list2);
                        z = false;
                        formattingInfo = FormattingInfo.getDefault();
                        stringBuffer.setLength(0);
                        break;
                    }
                    break;
            }
        }
        if (stringBuffer.length() != 0) {
            list.add(new LiteralPatternConverter(stringBuffer.toString()));
            list2.add(FormattingInfo.getDefault());
        }
    }

    private static PatternConverter createConverter(String str, StringBuffer stringBuffer, Map map, Map map2, List list) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class clsLoadClass;
        String strSubstring = str;
        Object obj = null;
        for (int length = str.length(); length > 0 && obj == null; length--) {
            strSubstring = strSubstring.substring(0, length);
            if (map != null) {
                obj = map.get(strSubstring);
            }
            if (obj == null && map2 != null) {
                obj = map2.get(strSubstring);
            }
        }
        if (obj == null) {
            LogLog.error(new StringBuffer().append("Unrecognized format specifier [").append(str).append("]").toString());
            return null;
        }
        if (obj instanceof Class) {
            clsLoadClass = (Class) obj;
        } else if (obj instanceof String) {
            try {
                clsLoadClass = Loader.loadClass((String) obj);
            } catch (ClassNotFoundException e) {
                LogLog.warn(new StringBuffer().append("Class for conversion pattern %").append(strSubstring).append(" not found").toString(), e);
                return null;
            }
        } else {
            LogLog.warn(new StringBuffer().append("Bad map entry for conversion pattern %").append(strSubstring).append(".").toString());
            return null;
        }
        try {
            Object objInvoke = clsLoadClass.getMethod("newInstance", Class.forName("[Ljava.lang.String;")).invoke(null, (String[]) list.toArray(new String[list.size()]));
            if (objInvoke instanceof PatternConverter) {
                stringBuffer.delete(0, stringBuffer.length() - (str.length() - strSubstring.length()));
                return (PatternConverter) objInvoke;
            }
            LogLog.warn(new StringBuffer().append("Class ").append(clsLoadClass.getName()).append(" does not extend PatternConverter.").toString());
            return null;
        } catch (Exception e2) {
            LogLog.error(new StringBuffer().append("Error creating converter for ").append(str).toString(), e2);
            try {
                PatternConverter patternConverter = (PatternConverter) clsLoadClass.newInstance();
                stringBuffer.delete(0, stringBuffer.length() - (str.length() - strSubstring.length()));
                return patternConverter;
            } catch (Exception e3) {
                LogLog.error(new StringBuffer().append("Error creating converter for ").append(str).toString(), e3);
                return null;
            }
        }
    }

    private static int finalizeConverter(char c, String str, int i, StringBuffer stringBuffer, FormattingInfo formattingInfo, Map map, Map map2, List list, List list2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuffer stringBuffer2;
        StringBuffer stringBuffer3 = new StringBuffer();
        int iExtractConverter = extractConverter(c, str, i, stringBuffer3, stringBuffer);
        String string = stringBuffer3.toString();
        ArrayList arrayList = new ArrayList();
        int iExtractOptions = extractOptions(str, iExtractConverter, arrayList);
        PatternConverter patternConverterCreateConverter = createConverter(string, stringBuffer, map, map2, arrayList);
        if (patternConverterCreateConverter == null) {
            if (string == null || string.length() == 0) {
                stringBuffer2 = new StringBuffer("Empty conversion specifier starting at position ");
            } else {
                stringBuffer2 = new StringBuffer("Unrecognized conversion specifier [");
                stringBuffer2.append(string);
                stringBuffer2.append("] starting at position ");
            }
            stringBuffer2.append(Integer.toString(iExtractOptions));
            stringBuffer2.append(" in conversion pattern.");
            LogLog.error(stringBuffer2.toString());
            list.add(new LiteralPatternConverter(stringBuffer.toString()));
            list2.add(FormattingInfo.getDefault());
        } else {
            list.add(patternConverterCreateConverter);
            list2.add(formattingInfo);
            if (stringBuffer.length() > 0) {
                list.add(new LiteralPatternConverter(stringBuffer.toString()));
                list2.add(FormattingInfo.getDefault());
            }
        }
        stringBuffer.setLength(0);
        return iExtractOptions;
    }

    /* loaded from: L-out.jar:org/apache/log4j/pattern/PatternParser$ReadOnlyMap.class */
    private static class ReadOnlyMap implements Map {
        private final Map map;

        public ReadOnlyMap(Map map) {
            this.map = map;
        }

        @Override // java.util.Map
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public boolean containsKey(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            return this.map.containsValue(obj);
        }

        @Override // java.util.Map
        public Set entrySet() {
            return this.map.entrySet();
        }

        @Override // java.util.Map
        public Object get(Object obj) {
            return this.map.get(obj);
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.Map
        public Set keySet() {
            return this.map.keySet();
        }

        @Override // java.util.Map
        public Object put(Object obj, Object obj2) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public void putAll(Map map) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public Object remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Map
        public int size() {
            return this.map.size();
        }

        @Override // java.util.Map
        public Collection values() {
            return this.map.values();
        }
    }
}
