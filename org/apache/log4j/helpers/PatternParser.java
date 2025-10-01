package org.apache.log4j.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.tools.Shell;
import org.apache.log4j.Layout;
import org.apache.log4j.net.SyslogAppender;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/helpers/PatternParser.class */
public class PatternParser {
    private static final char ESCAPE_CHAR = '%';
    private static final int LITERAL_STATE = 0;
    private static final int CONVERTER_STATE = 1;
    private static final int DOT_STATE = 3;
    private static final int MIN_STATE = 4;
    private static final int MAX_STATE = 5;
    static final int FULL_LOCATION_CONVERTER = 1000;
    static final int METHOD_LOCATION_CONVERTER = 1001;
    static final int CLASS_LOCATION_CONVERTER = 1002;
    static final int LINE_LOCATION_CONVERTER = 1003;
    static final int FILE_LOCATION_CONVERTER = 1004;
    static final int RELATIVE_TIME_CONVERTER = 2000;
    static final int THREAD_CONVERTER = 2001;
    static final int LEVEL_CONVERTER = 2002;
    static final int NDC_CONVERTER = 2003;
    static final int MESSAGE_CONVERTER = 2004;
    protected int patternLength;

    /* renamed from: i */
    protected int f191i;
    PatternConverter head;
    PatternConverter tail;
    protected String pattern;
    static Class class$java$text$DateFormat;
    protected StringBuffer currentLiteral = new StringBuffer(32);
    protected FormattingInfo formattingInfo = new FormattingInfo();
    int state = 0;

    public PatternParser(String str) {
        this.pattern = str;
        this.patternLength = str.length();
    }

    private void addToList(PatternConverter patternConverter) {
        if (this.head == null) {
            this.tail = patternConverter;
            this.head = patternConverter;
        } else {
            this.tail.next = patternConverter;
            this.tail = patternConverter;
        }
    }

    protected String extractOption() {
        int iIndexOf;
        if (this.f191i < this.patternLength && this.pattern.charAt(this.f191i) == '{' && (iIndexOf = this.pattern.indexOf(125, this.f191i)) > this.f191i) {
            String strSubstring = this.pattern.substring(this.f191i + 1, iIndexOf);
            this.f191i = iIndexOf + 1;
            return strSubstring;
        }
        return null;
    }

    protected int extractPrecisionOption() throws NumberFormatException {
        String strExtractOption = extractOption();
        int i = 0;
        if (strExtractOption != null) {
            try {
                i = Integer.parseInt(strExtractOption);
                if (i <= 0) {
                    LogLog.error(new StringBuffer().append("Precision option (").append(strExtractOption).append(") isn't a positive integer.").toString());
                    i = 0;
                }
            } catch (NumberFormatException e) {
                LogLog.error(new StringBuffer().append("Category option \"").append(strExtractOption).append("\" not a decimal integer.").toString(), e);
            }
        }
        return i;
    }

    public PatternConverter parse() throws Throwable {
        this.f191i = 0;
        while (this.f191i < this.patternLength) {
            String str = this.pattern;
            int i = this.f191i;
            this.f191i = i + 1;
            char cCharAt = str.charAt(i);
            switch (this.state) {
                case 0:
                    if (this.f191i == this.patternLength) {
                        this.currentLiteral.append(cCharAt);
                        break;
                    } else if (cCharAt == '%') {
                        switch (this.pattern.charAt(this.f191i)) {
                            case '%':
                                this.currentLiteral.append(cCharAt);
                                this.f191i++;
                                break;
                            case 'n':
                                this.currentLiteral.append(Layout.LINE_SEP);
                                this.f191i++;
                                break;
                            default:
                                if (this.currentLiteral.length() != 0) {
                                    addToList(new LiteralPatternConverter(this.currentLiteral.toString()));
                                }
                                this.currentLiteral.setLength(0);
                                this.currentLiteral.append(cCharAt);
                                this.state = 1;
                                this.formattingInfo.reset();
                                break;
                        }
                    } else {
                        this.currentLiteral.append(cCharAt);
                        break;
                    }
                case 1:
                    this.currentLiteral.append(cCharAt);
                    switch (cCharAt) {
                        case OPCode.BACKREF_MULTI /* 45 */:
                            this.formattingInfo.leftAlign = true;
                            break;
                        case OPCode.BACKREF_MULTI_IC /* 46 */:
                            this.state = 3;
                            break;
                        default:
                            if (cCharAt >= '0' && cCharAt <= '9') {
                                this.formattingInfo.min = cCharAt - '0';
                                this.state = 4;
                                break;
                            } else {
                                finalizeConverter(cCharAt);
                                break;
                            }
                    }
                case 3:
                    this.currentLiteral.append(cCharAt);
                    if (cCharAt >= '0' && cCharAt <= '9') {
                        this.formattingInfo.max = cCharAt - '0';
                        this.state = 5;
                        break;
                    } else {
                        LogLog.error(new StringBuffer().append("Error occured in position ").append(this.f191i).append(".\n Was expecting digit, instead got char \"").append(cCharAt).append("\".").toString());
                        this.state = 0;
                        break;
                    }
                case 4:
                    this.currentLiteral.append(cCharAt);
                    if (cCharAt >= '0' && cCharAt <= '9') {
                        this.formattingInfo.min = (this.formattingInfo.min * 10) + (cCharAt - '0');
                        break;
                    } else if (cCharAt == '.') {
                        this.state = 3;
                        break;
                    } else {
                        finalizeConverter(cCharAt);
                        break;
                    }
                    break;
                case 5:
                    this.currentLiteral.append(cCharAt);
                    if (cCharAt >= '0' && cCharAt <= '9') {
                        this.formattingInfo.max = (this.formattingInfo.max * 10) + (cCharAt - '0');
                        break;
                    } else {
                        finalizeConverter(cCharAt);
                        this.state = 0;
                        break;
                    }
            }
        }
        if (this.currentLiteral.length() != 0) {
            addToList(new LiteralPatternConverter(this.currentLiteral.toString()));
        }
        return this.head;
    }

    protected void finalizeConverter(char c) throws Throwable {
        PatternConverter literalPatternConverter;
        Class clsClass$;
        DateFormat simpleDateFormat;
        switch (c) {
            case OPCode.NULL_CHECK_END /* 67 */:
                literalPatternConverter = new ClassNamePatternConverter(this, this.formattingInfo, extractPrecisionOption());
                this.currentLiteral.setLength(0);
                break;
            case OPCode.NULL_CHECK_END_MEMST /* 68 */:
            case OPCode.NULL_CHECK_END_MEMST_PUSH /* 69 */:
            case OPCode.POP_POS /* 71 */:
            case 'H':
            case OPCode.FAIL_POS /* 73 */:
            case OPCode.PUSH_STOP_BT /* 74 */:
            case OPCode.POP_STOP_BT /* 75 */:
            case OPCode.FAIL_LOOK_BEHIND_NOT /* 78 */:
            case OPCode.CALL /* 79 */:
            case 'P':
            case OPCode.STATE_CHECK_PUSH /* 81 */:
            case OPCode.STATE_CHECK_PUSH_OR_JUMP /* 82 */:
            case OPCode.STATE_CHECK /* 83 */:
            case OPCode.STATE_CHECK_ANYCHAR_STAR /* 84 */:
            case OPCode.STATE_CHECK_ANYCHAR_ML_STAR /* 85 */:
            case OPCode.SET_OPTION_PUSH /* 86 */:
            case OPCode.SET_OPTION /* 87 */:
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'a':
            case 'b':
            case Shell.COMPILATION_ERROR /* 101 */:
            case Shell.RUNTIME_ERROR /* 102 */:
            case Shell.IO_ERROR /* 103 */:
            case Shell.INTERNAL_ERROR /* 104 */:
            case 'i':
            case 'j':
            case 'k':
            case 'n':
            case 'o':
            case 'q':
            case 's':
            case 'u':
            case 'v':
            case 'w':
            default:
                LogLog.error(new StringBuffer().append("Unexpected char [").append(c).append("] at position ").append(this.f191i).append(" in conversion patterrn.").toString());
                literalPatternConverter = new LiteralPatternConverter(this.currentLiteral.toString());
                this.currentLiteral.setLength(0);
                break;
            case OPCode.PUSH_POS /* 70 */:
                literalPatternConverter = new LocationPatternConverter(this, this.formattingInfo, FILE_LOCATION_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case OPCode.LOOK_BEHIND /* 76 */:
                literalPatternConverter = new LocationPatternConverter(this, this.formattingInfo, LINE_LOCATION_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case OPCode.PUSH_LOOK_BEHIND_NOT /* 77 */:
                literalPatternConverter = new LocationPatternConverter(this, this.formattingInfo, METHOD_LOCATION_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case SyslogAppender.LOG_FTP /* 88 */:
                literalPatternConverter = new MDCPatternConverter(this.formattingInfo, extractOption());
                this.currentLiteral.setLength(0);
                break;
            case 'c':
                literalPatternConverter = new CategoryPatternConverter(this, this.formattingInfo, extractPrecisionOption());
                this.currentLiteral.setLength(0);
                break;
            case Shell.COMMANDLINE_ERROR /* 100 */:
                String str = AbsoluteTimeDateFormat.ISO8601_DATE_FORMAT;
                String strExtractOption = extractOption();
                if (strExtractOption != null) {
                    str = strExtractOption;
                }
                if (str.equalsIgnoreCase(AbsoluteTimeDateFormat.ISO8601_DATE_FORMAT)) {
                    simpleDateFormat = new ISO8601DateFormat();
                } else if (str.equalsIgnoreCase(AbsoluteTimeDateFormat.ABS_TIME_DATE_FORMAT)) {
                    simpleDateFormat = new AbsoluteTimeDateFormat();
                } else if (str.equalsIgnoreCase(AbsoluteTimeDateFormat.DATE_AND_TIME_DATE_FORMAT)) {
                    simpleDateFormat = new DateTimeDateFormat();
                } else {
                    try {
                        simpleDateFormat = new SimpleDateFormat(str);
                    } catch (IllegalArgumentException e) {
                        LogLog.error(new StringBuffer().append("Could not instantiate SimpleDateFormat with ").append(str).toString(), e);
                        if (class$java$text$DateFormat == null) {
                            clsClass$ = class$("java.text.DateFormat");
                            class$java$text$DateFormat = clsClass$;
                        } else {
                            clsClass$ = class$java$text$DateFormat;
                        }
                        simpleDateFormat = (DateFormat) OptionConverter.instantiateByClassName("org.apache.log4j.helpers.ISO8601DateFormat", clsClass$, null);
                    }
                }
                literalPatternConverter = new DatePatternConverter(this.formattingInfo, simpleDateFormat);
                this.currentLiteral.setLength(0);
                break;
            case 'l':
                literalPatternConverter = new LocationPatternConverter(this, this.formattingInfo, 1000);
                this.currentLiteral.setLength(0);
                break;
            case 'm':
                literalPatternConverter = new BasicPatternConverter(this.formattingInfo, MESSAGE_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 'p':
                literalPatternConverter = new BasicPatternConverter(this.formattingInfo, LEVEL_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 'r':
                literalPatternConverter = new BasicPatternConverter(this.formattingInfo, RELATIVE_TIME_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 't':
                literalPatternConverter = new BasicPatternConverter(this.formattingInfo, THREAD_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
            case 'x':
                literalPatternConverter = new BasicPatternConverter(this.formattingInfo, NDC_CONVERTER);
                this.currentLiteral.setLength(0);
                break;
        }
        addConverter(literalPatternConverter);
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    protected void addConverter(PatternConverter patternConverter) {
        this.currentLiteral.setLength(0);
        addToList(patternConverter);
        this.state = 0;
        this.formattingInfo.reset();
    }

    /* loaded from: L-out.jar:org/apache/log4j/helpers/PatternParser$BasicPatternConverter.class */
    private static class BasicPatternConverter extends PatternConverter {
        int type;

        BasicPatternConverter(FormattingInfo formattingInfo, int i) {
            super(formattingInfo);
            this.type = i;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent loggingEvent) {
            switch (this.type) {
                case PatternParser.RELATIVE_TIME_CONVERTER /* 2000 */:
                    return Long.toString(loggingEvent.timeStamp - LoggingEvent.getStartTime());
                case PatternParser.THREAD_CONVERTER /* 2001 */:
                    return loggingEvent.getThreadName();
                case PatternParser.LEVEL_CONVERTER /* 2002 */:
                    return loggingEvent.getLevel().toString();
                case PatternParser.NDC_CONVERTER /* 2003 */:
                    return loggingEvent.getNDC();
                case PatternParser.MESSAGE_CONVERTER /* 2004 */:
                    return loggingEvent.getRenderedMessage();
                default:
                    return null;
            }
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/helpers/PatternParser$LiteralPatternConverter.class */
    private static class LiteralPatternConverter extends PatternConverter {
        private String literal;

        LiteralPatternConverter(String str) {
            this.literal = str;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public final void format(StringBuffer stringBuffer, LoggingEvent loggingEvent) {
            stringBuffer.append(this.literal);
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent loggingEvent) {
            return this.literal;
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/helpers/PatternParser$DatePatternConverter.class */
    private static class DatePatternConverter extends PatternConverter {

        /* renamed from: df */
        private DateFormat f192df;
        private Date date;

        DatePatternConverter(FormattingInfo formattingInfo, DateFormat dateFormat) {
            super(formattingInfo);
            this.date = new Date();
            this.f192df = dateFormat;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent loggingEvent) {
            this.date.setTime(loggingEvent.timeStamp);
            String str = null;
            try {
                str = this.f192df.format(this.date);
            } catch (Exception e) {
                LogLog.error("Error occured while converting date.", e);
            }
            return str;
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/helpers/PatternParser$MDCPatternConverter.class */
    private static class MDCPatternConverter extends PatternConverter {
        private String key;

        MDCPatternConverter(FormattingInfo formattingInfo, String str) {
            super(formattingInfo);
            this.key = str;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent loggingEvent) {
            if (this.key == null) {
                StringBuffer stringBuffer = new StringBuffer("{");
                Map properties = loggingEvent.getProperties();
                if (properties.size() > 0) {
                    Object[] array = properties.keySet().toArray();
                    Arrays.sort(array);
                    for (int i = 0; i < array.length; i++) {
                        stringBuffer.append('{');
                        stringBuffer.append(array[i]);
                        stringBuffer.append(',');
                        stringBuffer.append(properties.get(array[i]));
                        stringBuffer.append('}');
                    }
                }
                stringBuffer.append('}');
                return stringBuffer.toString();
            }
            Object mdc = loggingEvent.getMDC(this.key);
            if (mdc == null) {
                return null;
            }
            return mdc.toString();
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/helpers/PatternParser$LocationPatternConverter.class */
    private class LocationPatternConverter extends PatternConverter {
        int type;
        private final PatternParser this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        LocationPatternConverter(PatternParser patternParser, FormattingInfo formattingInfo, int i) {
            super(formattingInfo);
            this.this$0 = patternParser;
            this.type = i;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent loggingEvent) {
            LocationInfo locationInformation = loggingEvent.getLocationInformation();
            switch (this.type) {
                case 1000:
                    return locationInformation.fullInfo;
                case PatternParser.METHOD_LOCATION_CONVERTER /* 1001 */:
                    return locationInformation.getMethodName();
                case PatternParser.CLASS_LOCATION_CONVERTER /* 1002 */:
                default:
                    return null;
                case PatternParser.LINE_LOCATION_CONVERTER /* 1003 */:
                    return locationInformation.getLineNumber();
                case PatternParser.FILE_LOCATION_CONVERTER /* 1004 */:
                    return locationInformation.getFileName();
            }
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/helpers/PatternParser$NamedPatternConverter.class */
    private static abstract class NamedPatternConverter extends PatternConverter {
        int precision;

        abstract String getFullyQualifiedName(LoggingEvent loggingEvent);

        NamedPatternConverter(FormattingInfo formattingInfo, int i) {
            super(formattingInfo);
            this.precision = i;
        }

        @Override // org.apache.log4j.helpers.PatternConverter
        public String convert(LoggingEvent loggingEvent) {
            String fullyQualifiedName = getFullyQualifiedName(loggingEvent);
            if (this.precision <= 0) {
                return fullyQualifiedName;
            }
            int length = fullyQualifiedName.length();
            int iLastIndexOf = length - 1;
            for (int i = this.precision; i > 0; i--) {
                iLastIndexOf = fullyQualifiedName.lastIndexOf(46, iLastIndexOf - 1);
                if (iLastIndexOf == -1) {
                    return fullyQualifiedName;
                }
            }
            return fullyQualifiedName.substring(iLastIndexOf + 1, length);
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/helpers/PatternParser$ClassNamePatternConverter.class */
    private class ClassNamePatternConverter extends NamedPatternConverter {
        private final PatternParser this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ClassNamePatternConverter(PatternParser patternParser, FormattingInfo formattingInfo, int i) {
            super(formattingInfo, i);
            this.this$0 = patternParser;
        }

        @Override // org.apache.log4j.helpers.PatternParser.NamedPatternConverter
        String getFullyQualifiedName(LoggingEvent loggingEvent) {
            return loggingEvent.getLocationInformation().getClassName();
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/helpers/PatternParser$CategoryPatternConverter.class */
    private class CategoryPatternConverter extends NamedPatternConverter {
        private final PatternParser this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        CategoryPatternConverter(PatternParser patternParser, FormattingInfo formattingInfo, int i) {
            super(formattingInfo, i);
            this.this$0 = patternParser;
        }

        @Override // org.apache.log4j.helpers.PatternParser.NamedPatternConverter
        String getFullyQualifiedName(LoggingEvent loggingEvent) {
            return loggingEvent.getLoggerName();
        }
    }
}
