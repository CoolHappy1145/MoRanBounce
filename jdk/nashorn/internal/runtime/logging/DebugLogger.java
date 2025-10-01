package jdk.nashorn.internal.runtime.logging;

import java.io.PrintWriter;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.LoggingPermission;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.events.RuntimeEvent;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/logging/DebugLogger.class */
public final class DebugLogger {
    public static final DebugLogger DISABLED_LOGGER;
    private final java.util.logging.Logger logger;
    private final boolean isEnabled;
    private int indent;
    private static final int INDENT_SPACE = 4;
    private final boolean isQuiet;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !DebugLogger.class.desiredAssertionStatus();
        DISABLED_LOGGER = new DebugLogger("disabled", Level.OFF, false);
    }

    public DebugLogger(String str, Level level, boolean z) {
        this.logger = instantiateLogger(str, level);
        this.isQuiet = z;
        if (!$assertionsDisabled && this.logger == null) {
            throw new AssertionError();
        }
        this.isEnabled = getLevel() != Level.OFF;
    }

    private static java.util.logging.Logger instantiateLogger(String str, Level level) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(str);
        AccessController.doPrivileged(new PrivilegedAction(logger, level) { // from class: jdk.nashorn.internal.runtime.logging.DebugLogger.1
            final java.util.logging.Logger val$logger;
            final Level val$level;

            {
                this.val$logger = logger;
                this.val$level = level;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public Void run() throws SecurityException {
                for (Handler handler : this.val$logger.getHandlers()) {
                    this.val$logger.removeHandler(handler);
                }
                this.val$logger.setLevel(this.val$level);
                this.val$logger.setUseParentHandlers(false);
                ConsoleHandler consoleHandler = new ConsoleHandler();
                consoleHandler.setFormatter(new Formatter(this) { // from class: jdk.nashorn.internal.runtime.logging.DebugLogger.1.1
                    final C02621 this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.util.logging.Formatter
                    public String format(LogRecord logRecord) {
                        StringBuilder sb = new StringBuilder();
                        sb.append('[').append(logRecord.getLoggerName()).append("] ").append(logRecord.getMessage()).append('\n');
                        return sb.toString();
                    }
                });
                this.val$logger.addHandler(consoleHandler);
                consoleHandler.setLevel(this.val$level);
                return null;
            }
        }, createLoggerControlAccCtxt());
        return logger;
    }

    public Level getLevel() {
        return this.logger.getLevel() == null ? Level.OFF : this.logger.getLevel();
    }

    public PrintWriter getOutputStream() {
        return Context.getCurrentErr();
    }

    public static String quote(String str) {
        if (str.isEmpty()) {
            return "''";
        }
        char cCharAt = 0;
        char cCharAt2 = 0;
        char c = 0;
        if (str.startsWith("\\") || str.startsWith("\"")) {
            cCharAt = str.charAt(0);
        }
        if (str.endsWith("\\") || str.endsWith("\"")) {
            cCharAt2 = str.charAt(str.length() - 1);
        }
        if (cCharAt == 0 || cCharAt2 == 0) {
            c = cCharAt == 0 ? cCharAt2 : cCharAt;
        }
        if (c == 0) {
            c = '\'';
        }
        return (cCharAt == 0 ? c : cCharAt) + str + (cCharAt2 == 0 ? c : cCharAt2);
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public static boolean isEnabled(DebugLogger debugLogger) {
        return debugLogger != null && debugLogger.isEnabled();
    }

    public void indent(int i) {
        if (this.isEnabled) {
            this.indent += i * 4;
        }
    }

    public void indent() {
        this.indent += 4;
    }

    public void unindent() {
        this.indent -= 4;
        if (this.indent < 0) {
            this.indent = 0;
        }
    }

    private static void logEvent(RuntimeEvent runtimeEvent) {
        if (runtimeEvent != null) {
            Global global = Context.getGlobal();
            if (global.has("Debug")) {
                ScriptObject scriptObject = (ScriptObject) global.get("Debug");
                ScriptRuntime.apply((ScriptFunction) scriptObject.get("addRuntimeEvent"), scriptObject, runtimeEvent);
            }
        }
    }

    public boolean isLoggable(Level level) {
        return this.logger.isLoggable(level);
    }

    public void finest(String str) {
        log(Level.FINEST, str);
    }

    public void finest(RuntimeEvent runtimeEvent, String str) {
        finest(str);
        logEvent(runtimeEvent);
    }

    public void finest(Object[] objArr) {
        log(Level.FINEST, objArr);
    }

    public void finest(RuntimeEvent runtimeEvent, Object[] objArr) {
        finest(objArr);
        logEvent(runtimeEvent);
    }

    public void finer(String str) {
        log(Level.FINER, str);
    }

    public void finer(RuntimeEvent runtimeEvent, String str) {
        finer(str);
        logEvent(runtimeEvent);
    }

    public void finer(Object[] objArr) {
        log(Level.FINER, objArr);
    }

    public void finer(RuntimeEvent runtimeEvent, Object[] objArr) {
        finer(objArr);
        logEvent(runtimeEvent);
    }

    public void fine(String str) {
        log(Level.FINE, str);
    }

    public void fine(RuntimeEvent runtimeEvent, String str) {
        fine(str);
        logEvent(runtimeEvent);
    }

    public void fine(Object[] objArr) {
        log(Level.FINE, objArr);
    }

    public void fine(RuntimeEvent runtimeEvent, Object[] objArr) {
        fine(objArr);
        logEvent(runtimeEvent);
    }

    public void config(String str) {
        log(Level.CONFIG, str);
    }

    public void config(RuntimeEvent runtimeEvent, String str) {
        config(str);
        logEvent(runtimeEvent);
    }

    public void config(Object[] objArr) {
        log(Level.CONFIG, objArr);
    }

    public void config(RuntimeEvent runtimeEvent, Object[] objArr) {
        config(objArr);
        logEvent(runtimeEvent);
    }

    public void info(String str) {
        log(Level.INFO, str);
    }

    public void info(RuntimeEvent runtimeEvent, String str) {
        info(str);
        logEvent(runtimeEvent);
    }

    public void info(Object[] objArr) {
        log(Level.INFO, objArr);
    }

    public void info(RuntimeEvent runtimeEvent, Object[] objArr) {
        info(objArr);
        logEvent(runtimeEvent);
    }

    public void warning(String str) {
        log(Level.WARNING, str);
    }

    public void warning(RuntimeEvent runtimeEvent, String str) {
        warning(str);
        logEvent(runtimeEvent);
    }

    public void warning(Object[] objArr) {
        log(Level.WARNING, objArr);
    }

    public void warning(RuntimeEvent runtimeEvent, Object[] objArr) {
        warning(objArr);
        logEvent(runtimeEvent);
    }

    public void severe(String str) {
        log(Level.SEVERE, str);
    }

    public void severe(RuntimeEvent runtimeEvent, String str) {
        severe(str);
        logEvent(runtimeEvent);
    }

    public void severe(Object[] objArr) {
        log(Level.SEVERE, objArr);
    }

    public void severe(RuntimeEvent runtimeEvent, Object[] objArr) {
        severe(objArr);
        logEvent(runtimeEvent);
    }

    public void log(Level level, String str) {
        if (this.isEnabled && !this.isQuiet && this.logger.isLoggable(level)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.indent; i++) {
                sb.append(' ');
            }
            sb.append(str);
            this.logger.log(level, sb.toString());
        }
    }

    public void log(Level level, Object[] objArr) {
        if (this.isEnabled && !this.isQuiet && this.logger.isLoggable(level)) {
            StringBuilder sb = new StringBuilder();
            for (Object obj : objArr) {
                sb.append(obj);
            }
            log(level, sb.toString());
        }
    }

    private static AccessControlContext createLoggerControlAccCtxt() {
        Permissions permissions = new Permissions();
        permissions.add(new LoggingPermission("control", null));
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissions)});
    }
}
