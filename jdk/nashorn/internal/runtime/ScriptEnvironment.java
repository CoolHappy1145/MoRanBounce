package jdk.nashorn.internal.runtime;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.logging.Level;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.codegen.Namespace;
import jdk.nashorn.internal.runtime.options.KeyValueOption;
import jdk.nashorn.internal.runtime.options.LoggingOption;
import jdk.nashorn.internal.runtime.options.Option;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ScriptEnvironment.class */
public final class ScriptEnvironment {
    private static final boolean ALLOW_EAGER_COMPILATION_SILENT_OVERRIDE = Options.getBooleanProperty("nashorn.options.allowEagerCompilationSilentOverride", false);
    private final PrintWriter out;
    private final PrintWriter err;
    private final Namespace namespace = new Namespace();
    private final Options options;
    public final int _class_cache_size;
    public final boolean _compile_only;
    public final boolean _const_as_var;
    public final int _callsite_flags;
    public final boolean _debug_lines;
    public final String _dest_dir;
    public final boolean _dump_on_error;
    public final boolean _early_lvalue_error;
    public final boolean _empty_statements;
    public final boolean _fullversion;
    public final boolean _fx;
    public final boolean _global_per_engine;
    public final boolean _es6;
    public static final String COMPILE_ONLY_OPTIMISTIC_ARG = "optimistic";
    public final FunctionStatementBehavior _function_statement;
    public final boolean _lazy_compilation;
    public final boolean _optimistic_types;
    public final boolean _loader_per_compile;
    public final boolean _no_java;
    public final boolean _no_syntax_extensions;
    public final boolean _no_typed_arrays;
    public final boolean _parse_only;
    public final boolean _persistent_cache;
    public final boolean _print_ast;
    public final boolean _print_lower_ast;
    public final boolean _print_code;
    public final String _print_code_dir;
    public final String _print_code_func;
    public final boolean _print_mem_usage;
    public final boolean _print_no_newline;
    public final boolean _print_parse;
    public final boolean _print_lower_parse;
    public final boolean _print_symbols;
    public final boolean _scripting;
    public final boolean _strict;
    public final boolean _version;
    public final boolean _verify_code;
    public final TimeZone _timezone;
    public final Locale _locale;
    public final Map _loggers;
    public final Timing _timing;

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ScriptEnvironment$FunctionStatementBehavior.class */
    public enum FunctionStatementBehavior {
        ACCEPT,
        WARNING,
        ERROR
    }

    public ScriptEnvironment(Options options, PrintWriter printWriter, PrintWriter printWriter2) {
        this.out = printWriter;
        this.err = printWriter2;
        this.options = options;
        this._class_cache_size = options.getInteger("class.cache.size");
        this._compile_only = options.getBoolean("compile.only");
        this._const_as_var = options.getBoolean("const.as.var");
        this._debug_lines = options.getBoolean("debug.lines");
        this._dest_dir = options.getString("d");
        this._dump_on_error = options.getBoolean("doe");
        this._early_lvalue_error = options.getBoolean("early.lvalue.error");
        this._empty_statements = options.getBoolean("empty.statements");
        this._fullversion = options.getBoolean("fullversion");
        if (options.getBoolean("function.statement.error")) {
            this._function_statement = FunctionStatementBehavior.ERROR;
        } else if (options.getBoolean("function.statement.warning")) {
            this._function_statement = FunctionStatementBehavior.WARNING;
        } else {
            this._function_statement = FunctionStatementBehavior.ACCEPT;
        }
        this._fx = options.getBoolean("fx");
        this._global_per_engine = options.getBoolean("global.per.engine");
        this._optimistic_types = options.getBoolean("optimistic.types");
        boolean z = options.getBoolean("lazy.compilation");
        if (!z && this._optimistic_types) {
            if (!ALLOW_EAGER_COMPILATION_SILENT_OVERRIDE) {
                throw new IllegalStateException(ECMAErrors.getMessage("config.error.eagerCompilationConflictsWithOptimisticTypes", new String[]{options.getOptionTemplateByKey("lazy.compilation").getName(), options.getOptionTemplateByKey("optimistic.types").getName()}));
            }
            this._lazy_compilation = true;
        } else {
            this._lazy_compilation = z;
        }
        this._loader_per_compile = options.getBoolean("loader.per.compile");
        this._no_java = options.getBoolean("no.java");
        this._no_syntax_extensions = options.getBoolean("no.syntax.extensions");
        this._no_typed_arrays = options.getBoolean("no.typed.arrays");
        this._parse_only = options.getBoolean("parse.only");
        this._persistent_cache = options.getBoolean("persistent.code.cache");
        this._print_ast = options.getBoolean("print.ast");
        this._print_lower_ast = options.getBoolean("print.lower.ast");
        this._print_code = options.getString("print.code") != null;
        this._print_mem_usage = options.getBoolean("print.mem.usage");
        this._print_no_newline = options.getBoolean("print.no.newline");
        this._print_parse = options.getBoolean("print.parse");
        this._print_lower_parse = options.getBoolean("print.lower.parse");
        this._print_symbols = options.getBoolean("print.symbols");
        this._scripting = options.getBoolean("scripting");
        this._strict = options.getBoolean("strict");
        this._version = options.getBoolean("version");
        this._verify_code = options.getBoolean("verify.code");
        String string = options.getString("language");
        if (string == null || string.equals("es5")) {
            this._es6 = false;
        } else if (string.equals("es6")) {
            this._es6 = true;
        } else {
            throw new RuntimeException("Unsupported language: " + string);
        }
        String strNextToken = null;
        String strNextToken2 = null;
        String string2 = options.getString("print.code");
        if (string2 != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(string2, ",");
            while (stringTokenizer.hasMoreTokens()) {
                StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), CallSiteDescriptor.TOKEN_DELIMITER);
                while (stringTokenizer2.hasMoreTokens()) {
                    String strNextToken3 = stringTokenizer2.nextToken();
                    if ("dir".equals(strNextToken3)) {
                        strNextToken = stringTokenizer2.nextToken();
                    } else if ("function".equals(strNextToken3)) {
                        strNextToken2 = stringTokenizer2.nextToken();
                    }
                }
            }
        }
        this._print_code_dir = strNextToken;
        this._print_code_func = strNextToken2;
        int i = options.getBoolean("profile.callsites") ? 64 : 0;
        if (options.get("trace.callsites") instanceof KeyValueOption) {
            i |= 128;
            KeyValueOption keyValueOption = (KeyValueOption) options.get("trace.callsites");
            i = keyValueOption.hasValue("miss") ? i | 256 : i;
            i = (keyValueOption.hasValue("enterexit") || (i & 256) == 0) ? i | 512 : i;
            if (keyValueOption.hasValue("objects")) {
                i |= 1024;
            }
        }
        this._callsite_flags = i;
        Option option = options.get("timezone");
        if (option != null) {
            this._timezone = (TimeZone) option.getValue();
        } else {
            this._timezone = TimeZone.getDefault();
        }
        Option option2 = options.get("locale");
        if (option2 != null) {
            this._locale = (Locale) option2.getValue();
        } else {
            this._locale = Locale.getDefault();
        }
        LoggingOption loggingOption = (LoggingOption) options.get("log");
        this._loggers = loggingOption == null ? new HashMap() : loggingOption.getLoggers();
        LoggingOption.LoggerInfo loggerInfo = (LoggingOption.LoggerInfo) this._loggers.get(Timing.getLoggerName());
        this._timing = new Timing((loggerInfo == null || loggerInfo.getLevel() == Level.OFF) ? false : true);
    }

    public PrintWriter getOut() {
        return this.out;
    }

    public PrintWriter getErr() {
        return this.err;
    }

    public Namespace getNamespace() {
        return this.namespace;
    }

    public List getFiles() {
        return this.options.getFiles();
    }

    public List getArguments() {
        return this.options.getArguments();
    }

    public boolean hasLogger(String str) {
        return this._loggers.get(str) != null;
    }

    public boolean isTimingEnabled() {
        if (this._timing != null) {
            return this._timing.isEnabled();
        }
        return false;
    }
}
