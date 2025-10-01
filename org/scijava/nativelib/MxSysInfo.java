package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: L-out.jar:org/scijava/nativelib/MxSysInfo.class */
public class MxSysInfo {
    public static String getMxSysInfo() {
        String property = System.getProperty("mx.sysinfo");
        if (property != null) {
            return property;
        }
        return guessMxSysInfo();
    }

    public static String guessMxSysInfo() {
        String str;
        String property = System.getProperty("os.arch");
        String property2 = System.getProperty("os.name");
        String str2 = "unknown";
        if ("Linux".equals(property2)) {
            try {
                String canonicalPath = new File("/lib/libc.so.6").getCanonicalPath();
                Matcher matcher = Pattern.compile(".*/libc-(\\d+)\\.(\\d+)\\..*").matcher(canonicalPath);
                if (!matcher.matches()) {
                    throw new IOException("libc symlink contains unexpected destination: " + canonicalPath);
                }
                File file = new File("/usr/lib/libstdc++.so.6");
                if (!file.exists()) {
                    file = new File("/usr/lib/libstdc++.so.5");
                }
                String canonicalPath2 = file.getCanonicalPath();
                Matcher matcher2 = Pattern.compile(".*/libstdc\\+\\+\\.so\\.(\\d+)\\.0\\.(\\d+)").matcher(canonicalPath2);
                if (!matcher2.matches()) {
                    throw new IOException("libstdc++ symlink contains unexpected destination: " + canonicalPath2);
                }
                if ("5".equals(matcher2.group(1))) {
                    str = "5";
                } else if ("6".equals(matcher2.group(1))) {
                    if (Integer.parseInt(matcher2.group(2)) < 9) {
                        str = "6";
                    } else {
                        str = "6" + matcher2.group(2);
                    }
                } else {
                    str = matcher2.group(1) + matcher2.group(2);
                }
                str2 = "c" + matcher.group(1) + matcher.group(2) + "cxx" + str;
            } catch (IOException unused) {
                str2 = "unknown";
            }
        }
        return property + "-" + property2 + "-" + str2;
    }
}
