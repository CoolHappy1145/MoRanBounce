package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/Option.class */
public class Option {
    public static final int NONE = 0;
    public static final int IGNORECASE = 1;
    public static final int EXTEND = 2;
    public static final int MULTILINE = 4;
    public static final int SINGLELINE = 8;
    public static final int FIND_LONGEST = 16;
    public static final int FIND_NOT_EMPTY = 32;
    public static final int NEGATE_SINGLELINE = 64;
    public static final int DONT_CAPTURE_GROUP = 128;
    public static final int CAPTURE_GROUP = 256;
    public static final int NOTBOL = 512;
    public static final int NOTEOL = 1024;
    public static final int POSIX_REGION = 2048;
    public static final int MAXBIT = 4096;
    public static final int DEFAULT = 0;

    public static String toString(int i) {
        String str = "";
        if ((i & 1) != 0) {
            str = "IGNORECASE ";
        }
        if ((i & 2) != 0) {
            str = str + "EXTEND ";
        }
        if ((i & 4) != 0) {
            str = str + "MULTILINE ";
        }
        if ((i & 8) != 0) {
            str = str + "SINGLELINE ";
        }
        if ((i & 16) != 0) {
            str = str + "FIND_LONGEST ";
        }
        if ((i & 32) != 0) {
            str = str + "FIND_NOT_EMPTY  ";
        }
        if ((i & 64) != 0) {
            str = str + "NEGATE_SINGLELINE ";
        }
        if ((i & 128) != 0) {
            str = str + "DONT_CAPTURE_GROUP ";
        }
        if ((i & 256) != 0) {
            str = str + "CAPTURE_GROUP ";
        }
        if ((i & 512) != 0) {
            str = str + "NOTBOL ";
        }
        if ((i & 1024) != 0) {
            str = str + "NOTEOL ";
        }
        if ((i & 2048) != 0) {
            str = str + "POSIX_REGION ";
        }
        return str;
    }
}
