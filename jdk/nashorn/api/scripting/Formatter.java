package jdk.nashorn.api.scripting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.tools.Shell;
import org.apache.log4j.net.SyslogAppender;

/* loaded from: L-out.jar:jdk/nashorn/api/scripting/Formatter.class */
final class Formatter {
    private static final String formatSpecifier = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
    private static final Pattern FS_PATTERN = Pattern.compile(formatSpecifier);

    private Formatter() {
    }

    static String format(String str, Object[] objArr) throws NumberFormatException {
        Matcher matcher = FS_PATTERN.matcher(str);
        int i = 1;
        while (matcher.find()) {
            int iIndex = index(matcher.group(1));
            boolean zIsPreviousArgument = isPreviousArgument(matcher.group(2));
            char cCharAt = matcher.group(6).charAt(0);
            if (iIndex >= 0 && !zIsPreviousArgument && cCharAt != 'n' && cCharAt != '%') {
                if (iIndex == 0) {
                    int i2 = i;
                    i++;
                    iIndex = i2;
                }
                if (iIndex <= objArr.length) {
                    Object obj = objArr[iIndex - 1];
                    if (matcher.group(5) != null) {
                        if (obj instanceof Double) {
                            objArr[iIndex - 1] = Long.valueOf(((Double) obj).longValue());
                        }
                    } else {
                        switch (cCharAt) {
                            case OPCode.REPEAT_INC_NG_SG /* 65 */:
                            case OPCode.NULL_CHECK_END_MEMST_PUSH /* 69 */:
                            case OPCode.POP_POS /* 71 */:
                            case 'a':
                            case Shell.COMPILATION_ERROR /* 101 */:
                            case Shell.RUNTIME_ERROR /* 102 */:
                            case Shell.IO_ERROR /* 103 */:
                                if (!(obj instanceof Integer)) {
                                    break;
                                } else {
                                    objArr[iIndex - 1] = Double.valueOf(((Integer) obj).doubleValue());
                                    break;
                                }
                            case SyslogAppender.LOG_FTP /* 88 */:
                            case Shell.COMMANDLINE_ERROR /* 100 */:
                            case 'o':
                            case 'x':
                                if (obj instanceof Double) {
                                    objArr[iIndex - 1] = Long.valueOf(((Double) obj).longValue());
                                    break;
                                } else if ((obj instanceof String) && ((String) obj).length() > 0) {
                                    objArr[iIndex - 1] = Integer.valueOf(((String) obj).charAt(0));
                                    break;
                                } else {
                                    break;
                                }
                            case 'c':
                                if (obj instanceof Double) {
                                    objArr[iIndex - 1] = Integer.valueOf(((Double) obj).intValue());
                                    break;
                                } else if ((obj instanceof String) && ((String) obj).length() > 0) {
                                    objArr[iIndex - 1] = Integer.valueOf(((String) obj).charAt(0));
                                    break;
                                } else {
                                    break;
                                }
                                break;
                        }
                    }
                }
            }
        }
        return String.format(str, objArr);
    }

    private static int index(String str) throws NumberFormatException {
        int i = -1;
        if (str != null) {
            try {
                i = Integer.parseInt(str.substring(0, str.length() - 1));
            } catch (NumberFormatException unused) {
            }
        } else {
            i = 0;
        }
        return i;
    }

    private static boolean isPreviousArgument(String str) {
        return str != null && str.indexOf(60) >= 0;
    }
}
