package jdk.nashorn.internal.runtime.options;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/options/LoggingOption.class */
public class LoggingOption extends KeyValueOption {
    private final Map loggers;

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/options/LoggingOption$LoggerInfo.class */
    public static class LoggerInfo {
        private final Level level;
        private final boolean isQuiet;

        LoggerInfo(Level level, boolean z) {
            this.level = level;
            this.isQuiet = z;
        }

        public Level getLevel() {
            return this.level;
        }

        public boolean isQuiet() {
            return this.isQuiet;
        }
    }

    LoggingOption(String str) throws IllegalArgumentException {
        super(str);
        this.loggers = new HashMap();
        initialize(getValues());
    }

    public Map getLoggers() {
        return Collections.unmodifiableMap(this.loggers);
    }

    private void initialize(Map map) throws IllegalArgumentException {
        Level level;
        boolean z;
        try {
            for (Map.Entry entry : map.entrySet()) {
                String strLastPart = lastPart((String) entry.getKey());
                String upperCase = ((String) entry.getValue()).toUpperCase(Locale.ENGLISH);
                if ("".equals(upperCase)) {
                    level = Level.INFO;
                    z = false;
                } else if ("QUIET".equals(upperCase)) {
                    level = Level.INFO;
                    z = true;
                } else {
                    level = Level.parse(upperCase);
                    z = false;
                }
                this.loggers.put(strLastPart, new LoggerInfo(level, z));
            }
        } catch (IllegalArgumentException | SecurityException e) {
            throw e;
        }
    }

    private static String lastPart(String str) {
        String[] strArrSplit = str.split("\\.");
        if (strArrSplit.length == 0) {
            return str;
        }
        return strArrSplit[strArrSplit.length - 1];
    }
}
