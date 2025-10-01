package org.apache.log4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.config.PropertySetter;
import org.apache.log4j.helpers.FileWatchdog;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.p007or.RendererMap;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.OptionHandler;
import org.apache.log4j.spi.RendererSupport;
import org.apache.log4j.spi.ThrowableRenderer;
import org.apache.log4j.spi.ThrowableRendererSupport;

/* loaded from: L-out.jar:org/apache/log4j/PropertyConfigurator.class */
public class PropertyConfigurator implements Configurator {
    private LoggerRepository repository;
    static final String CATEGORY_PREFIX = "log4j.category.";
    static final String LOGGER_PREFIX = "log4j.logger.";
    static final String FACTORY_PREFIX = "log4j.factory";
    static final String ADDITIVITY_PREFIX = "log4j.additivity.";
    static final String ROOT_CATEGORY_PREFIX = "log4j.rootCategory";
    static final String ROOT_LOGGER_PREFIX = "log4j.rootLogger";
    static final String APPENDER_PREFIX = "log4j.appender.";
    static final String RENDERER_PREFIX = "log4j.renderer.";
    static final String THRESHOLD_PREFIX = "log4j.threshold";
    private static final String THROWABLE_RENDERER_PREFIX = "log4j.throwableRenderer";
    private static final String LOGGER_REF = "logger-ref";
    private static final String ROOT_REF = "root-ref";
    private static final String APPENDER_REF_TAG = "appender-ref";
    public static final String LOGGER_FACTORY_KEY = "log4j.loggerFactory";
    private static final String RESET_KEY = "log4j.reset";
    private static final String INTERNAL_ROOT_NAME = "root";
    static Class class$org$apache$log4j$spi$LoggerFactory;
    static Class class$org$apache$log4j$spi$ThrowableRenderer;
    static Class class$org$apache$log4j$Appender;
    static Class class$org$apache$log4j$Layout;
    static Class class$org$apache$log4j$spi$ErrorHandler;
    static Class class$org$apache$log4j$spi$Filter;
    protected Hashtable registry = new Hashtable(11);
    protected LoggerFactory loggerFactory = new DefaultCategoryFactory();

    public void doConfigure(String str, LoggerRepository loggerRepository) throws Throwable {
        Properties properties = new Properties();
        FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = new FileInputStream(str);
                properties.load(fileInputStream);
                fileInputStream.close();
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (InterruptedIOException unused) {
                        Thread.currentThread().interrupt();
                    } catch (Throwable unused2) {
                    }
                }
                doConfigure(properties, loggerRepository);
            } catch (Exception e) {
                if ((e instanceof InterruptedIOException) || (e instanceof InterruptedException)) {
                    Thread.currentThread().interrupt();
                }
                LogLog.error(new StringBuffer().append("Could not read configuration file [").append(str).append("].").toString(), e);
                LogLog.error(new StringBuffer().append("Ignoring configuration file [").append(str).append("].").toString());
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (InterruptedIOException unused3) {
                        Thread.currentThread().interrupt();
                    } catch (Throwable unused4) {
                    }
                }
            }
        } catch (Throwable th) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (InterruptedIOException unused5) {
                    Thread.currentThread().interrupt();
                } catch (Throwable unused6) {
                }
            }
            throw th;
        }
    }

    public static void configure(String str) throws Throwable {
        new PropertyConfigurator().doConfigure(str, LogManager.getLoggerRepository());
    }

    public static void configure(URL url) throws Throwable {
        new PropertyConfigurator().doConfigure(url, LogManager.getLoggerRepository());
    }

    public static void configure(InputStream inputStream) throws Throwable {
        new PropertyConfigurator().doConfigure(inputStream, LogManager.getLoggerRepository());
    }

    public static void configure(Properties properties) throws Throwable {
        new PropertyConfigurator().doConfigure(properties, LogManager.getLoggerRepository());
    }

    public static void configureAndWatch(String str) {
        configureAndWatch(str, FileWatchdog.DEFAULT_DELAY);
    }

    public static void configureAndWatch(String str, long j) {
        PropertyWatchdog propertyWatchdog = new PropertyWatchdog(str);
        propertyWatchdog.setDelay(j);
        propertyWatchdog.start();
    }

    public void doConfigure(Properties properties, LoggerRepository loggerRepository) throws Throwable {
        this.repository = loggerRepository;
        String property = properties.getProperty(LogLog.DEBUG_KEY);
        if (property == null) {
            property = properties.getProperty(LogLog.CONFIG_DEBUG_KEY);
            if (property != null) {
                LogLog.warn("[log4j.configDebug] is deprecated. Use [log4j.debug] instead.");
            }
        }
        if (property != null) {
            LogLog.setInternalDebugging(OptionConverter.toBoolean(property, true));
        }
        String property2 = properties.getProperty(RESET_KEY);
        if (property2 != null && OptionConverter.toBoolean(property2, false)) {
            loggerRepository.resetConfiguration();
        }
        String strFindAndSubst = OptionConverter.findAndSubst(THRESHOLD_PREFIX, properties);
        if (strFindAndSubst != null) {
            loggerRepository.setThreshold(OptionConverter.toLevel(strFindAndSubst, Level.ALL));
            LogLog.debug(new StringBuffer().append("Hierarchy threshold set to [").append(loggerRepository.getThreshold()).append("].").toString());
        }
        configureRootCategory(properties, loggerRepository);
        configureLoggerFactory(properties);
        parseCatsAndRenderers(properties, loggerRepository);
        LogLog.debug("Finished configuring.");
        this.registry.clear();
    }

    @Override // org.apache.log4j.spi.Configurator
    public void doConfigure(InputStream inputStream, LoggerRepository loggerRepository) throws Throwable {
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            doConfigure(properties, loggerRepository);
        } catch (IOException e) {
            if (e instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            LogLog.error(new StringBuffer().append("Could not read configuration file from InputStream [").append(inputStream).append("].").toString(), e);
            LogLog.error(new StringBuffer().append("Ignoring configuration InputStream [").append(inputStream).append("].").toString());
        }
    }

    @Override // org.apache.log4j.spi.Configurator
    public void doConfigure(URL url, LoggerRepository loggerRepository) throws Throwable {
        Properties properties = new Properties();
        LogLog.debug(new StringBuffer().append("Reading configuration from URL ").append(url).toString());
        InputStream inputStream = null;
        try {
            try {
                URLConnection uRLConnectionOpenConnection = url.openConnection();
                uRLConnectionOpenConnection.setUseCaches(false);
                inputStream = uRLConnectionOpenConnection.getInputStream();
                properties.load(inputStream);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (InterruptedIOException unused) {
                        Thread.currentThread().interrupt();
                    } catch (IOException unused2) {
                    } catch (RuntimeException unused3) {
                    }
                }
                doConfigure(properties, loggerRepository);
            } catch (Exception e) {
                if ((e instanceof InterruptedIOException) || (e instanceof InterruptedException)) {
                    Thread.currentThread().interrupt();
                }
                LogLog.error(new StringBuffer().append("Could not read configuration file from URL [").append(url).append("].").toString(), e);
                LogLog.error(new StringBuffer().append("Ignoring configuration file [").append(url).append("].").toString());
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (InterruptedIOException unused4) {
                        Thread.currentThread().interrupt();
                    } catch (IOException unused5) {
                    } catch (RuntimeException unused6) {
                    }
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (InterruptedIOException unused7) {
                    Thread.currentThread().interrupt();
                } catch (IOException unused8) {
                } catch (RuntimeException unused9) {
                }
            }
            throw th;
        }
    }

    protected void configureLoggerFactory(Properties properties) throws Throwable {
        Class clsClass$;
        String strFindAndSubst = OptionConverter.findAndSubst(LOGGER_FACTORY_KEY, properties);
        if (strFindAndSubst != null) {
            LogLog.debug(new StringBuffer().append("Setting category factory to [").append(strFindAndSubst).append("].").toString());
            if (class$org$apache$log4j$spi$LoggerFactory == null) {
                clsClass$ = class$("org.apache.log4j.spi.LoggerFactory");
                class$org$apache$log4j$spi$LoggerFactory = clsClass$;
            } else {
                clsClass$ = class$org$apache$log4j$spi$LoggerFactory;
            }
            this.loggerFactory = (LoggerFactory) OptionConverter.instantiateByClassName(strFindAndSubst, clsClass$, this.loggerFactory);
            PropertySetter.setProperties(this.loggerFactory, properties, "log4j.factory.");
        }
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    void configureRootCategory(Properties properties, LoggerRepository loggerRepository) {
        String str = ROOT_LOGGER_PREFIX;
        String strFindAndSubst = OptionConverter.findAndSubst(ROOT_LOGGER_PREFIX, properties);
        if (strFindAndSubst == null) {
            strFindAndSubst = OptionConverter.findAndSubst(ROOT_CATEGORY_PREFIX, properties);
            str = ROOT_CATEGORY_PREFIX;
        }
        if (strFindAndSubst == null) {
            LogLog.debug("Could not find root logger information. Is this OK?");
            return;
        }
        Logger rootLogger = loggerRepository.getRootLogger();
        synchronized (rootLogger) {
            parseCategory(properties, rootLogger, str, INTERNAL_ROOT_NAME, strFindAndSubst);
        }
    }

    protected void parseCatsAndRenderers(Properties properties, LoggerRepository loggerRepository) throws Throwable {
        Class clsClass$;
        Enumeration<?> enumerationPropertyNames = properties.propertyNames();
        while (enumerationPropertyNames.hasMoreElements()) {
            String str = (String) enumerationPropertyNames.nextElement();
            if (str.startsWith(CATEGORY_PREFIX) || str.startsWith(LOGGER_PREFIX)) {
                String strSubstring = null;
                if (str.startsWith(CATEGORY_PREFIX)) {
                    strSubstring = str.substring(15);
                } else if (str.startsWith(LOGGER_PREFIX)) {
                    strSubstring = str.substring(13);
                }
                String strFindAndSubst = OptionConverter.findAndSubst(str, properties);
                Logger logger = loggerRepository.getLogger(strSubstring, this.loggerFactory);
                synchronized (logger) {
                    parseCategory(properties, logger, str, strSubstring, strFindAndSubst);
                    parseAdditivityForLogger(properties, logger, strSubstring);
                }
            } else if (str.startsWith(RENDERER_PREFIX)) {
                String strSubstring2 = str.substring(15);
                String strFindAndSubst2 = OptionConverter.findAndSubst(str, properties);
                if (loggerRepository instanceof RendererSupport) {
                    RendererMap.addRenderer((RendererSupport) loggerRepository, strSubstring2, strFindAndSubst2);
                }
            } else if (str.equals(THROWABLE_RENDERER_PREFIX) && (loggerRepository instanceof ThrowableRendererSupport)) {
                if (class$org$apache$log4j$spi$ThrowableRenderer == null) {
                    clsClass$ = class$("org.apache.log4j.spi.ThrowableRenderer");
                    class$org$apache$log4j$spi$ThrowableRenderer = clsClass$;
                } else {
                    clsClass$ = class$org$apache$log4j$spi$ThrowableRenderer;
                }
                ThrowableRenderer throwableRenderer = (ThrowableRenderer) OptionConverter.instantiateByKey(properties, THROWABLE_RENDERER_PREFIX, clsClass$, null);
                if (throwableRenderer == null) {
                    LogLog.error("Could not instantiate throwableRenderer.");
                } else {
                    new PropertySetter(throwableRenderer).setProperties(properties, "log4j.throwableRenderer.");
                    ((ThrowableRendererSupport) loggerRepository).setThrowableRenderer(throwableRenderer);
                }
            }
        }
    }

    void parseAdditivityForLogger(Properties properties, Logger logger, String str) {
        String strFindAndSubst = OptionConverter.findAndSubst(new StringBuffer().append(ADDITIVITY_PREFIX).append(str).toString(), properties);
        LogLog.debug(new StringBuffer().append("Handling log4j.additivity.").append(str).append("=[").append(strFindAndSubst).append("]").toString());
        if (strFindAndSubst != null && !strFindAndSubst.equals("")) {
            boolean z = OptionConverter.toBoolean(strFindAndSubst, true);
            LogLog.debug(new StringBuffer().append("Setting additivity for \"").append(str).append("\" to ").append(z).toString());
            logger.setAdditivity(z);
        }
    }

    void parseCategory(Properties properties, Logger logger, String str, String str2, String str3) throws Throwable {
        LogLog.debug(new StringBuffer().append("Parsing for [").append(str2).append("] with value=[").append(str3).append("].").toString());
        StringTokenizer stringTokenizer = new StringTokenizer(str3, ",");
        if (!str3.startsWith(",") && !str3.equals("")) {
            if (!stringTokenizer.hasMoreTokens()) {
                return;
            }
            String strNextToken = stringTokenizer.nextToken();
            LogLog.debug(new StringBuffer().append("Level token is [").append(strNextToken).append("].").toString());
            if (Configurator.INHERITED.equalsIgnoreCase(strNextToken) || Configurator.NULL.equalsIgnoreCase(strNextToken)) {
                if (str2.equals(INTERNAL_ROOT_NAME)) {
                    LogLog.warn("The root logger cannot be set to null.");
                } else {
                    logger.setLevel(null);
                }
            } else {
                logger.setLevel(OptionConverter.toLevel(strNextToken, Level.DEBUG));
            }
            LogLog.debug(new StringBuffer().append("Category ").append(str2).append(" set to ").append(logger.getLevel()).toString());
        }
        logger.removeAllAppenders();
        while (stringTokenizer.hasMoreTokens()) {
            String strTrim = stringTokenizer.nextToken().trim();
            if (strTrim != null && !strTrim.equals(",")) {
                LogLog.debug(new StringBuffer().append("Parsing appender named \"").append(strTrim).append("\".").toString());
                Appender appender = parseAppender(properties, strTrim);
                if (appender != null) {
                    logger.addAppender(appender);
                }
            }
        }
    }

    Appender parseAppender(Properties properties, String str) throws Throwable {
        Class clsClass$;
        Class clsClass$2;
        Class clsClass$3;
        Appender appenderRegistryGet = registryGet(str);
        if (appenderRegistryGet != null) {
            LogLog.debug(new StringBuffer().append("Appender \"").append(str).append("\" was already parsed.").toString());
            return appenderRegistryGet;
        }
        String string = new StringBuffer().append(APPENDER_PREFIX).append(str).toString();
        String string2 = new StringBuffer().append(string).append(".layout").toString();
        if (class$org$apache$log4j$Appender == null) {
            clsClass$ = class$("org.apache.log4j.Appender");
            class$org$apache$log4j$Appender = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$Appender;
        }
        Appender appender = (Appender) OptionConverter.instantiateByKey(properties, string, clsClass$, null);
        if (appender == null) {
            LogLog.error(new StringBuffer().append("Could not instantiate appender named \"").append(str).append("\".").toString());
            return null;
        }
        appender.setName(str);
        if (appender instanceof OptionHandler) {
            if (appender.requiresLayout()) {
                if (class$org$apache$log4j$Layout == null) {
                    clsClass$3 = class$("org.apache.log4j.Layout");
                    class$org$apache$log4j$Layout = clsClass$3;
                } else {
                    clsClass$3 = class$org$apache$log4j$Layout;
                }
                Layout layout = (Layout) OptionConverter.instantiateByKey(properties, string2, clsClass$3, null);
                if (layout != null) {
                    appender.setLayout(layout);
                    LogLog.debug(new StringBuffer().append("Parsing layout options for \"").append(str).append("\".").toString());
                    PropertySetter.setProperties(layout, properties, new StringBuffer().append(string2).append(".").toString());
                    LogLog.debug(new StringBuffer().append("End of parsing for \"").append(str).append("\".").toString());
                }
            }
            String string3 = new StringBuffer().append(string).append(".errorhandler").toString();
            if (OptionConverter.findAndSubst(string3, properties) != null) {
                if (class$org$apache$log4j$spi$ErrorHandler == null) {
                    clsClass$2 = class$("org.apache.log4j.spi.ErrorHandler");
                    class$org$apache$log4j$spi$ErrorHandler = clsClass$2;
                } else {
                    clsClass$2 = class$org$apache$log4j$spi$ErrorHandler;
                }
                ErrorHandler errorHandler = (ErrorHandler) OptionConverter.instantiateByKey(properties, string3, clsClass$2, null);
                if (errorHandler != null) {
                    appender.setErrorHandler(errorHandler);
                    LogLog.debug(new StringBuffer().append("Parsing errorhandler options for \"").append(str).append("\".").toString());
                    parseErrorHandler(errorHandler, string3, properties, this.repository);
                    Properties properties2 = new Properties();
                    String[] strArr = {new StringBuffer().append(string3).append(".").append(ROOT_REF).toString(), new StringBuffer().append(string3).append(".").append(LOGGER_REF).toString(), new StringBuffer().append(string3).append(".").append(APPENDER_REF_TAG).toString()};
                    for (Map.Entry entry : properties.entrySet()) {
                        int i = 0;
                        while (i < strArr.length && !strArr[i].equals(entry.getKey())) {
                            i++;
                        }
                        if (i == strArr.length) {
                            properties2.put(entry.getKey(), entry.getValue());
                        }
                    }
                    PropertySetter.setProperties(errorHandler, properties2, new StringBuffer().append(string3).append(".").toString());
                    LogLog.debug(new StringBuffer().append("End of errorhandler parsing for \"").append(str).append("\".").toString());
                }
            }
            PropertySetter.setProperties(appender, properties, new StringBuffer().append(string).append(".").toString());
            LogLog.debug(new StringBuffer().append("Parsed \"").append(str).append("\" options.").toString());
        }
        parseAppenderFilters(properties, str, appender);
        registryPut(appender);
        return appender;
    }

    private void parseErrorHandler(ErrorHandler errorHandler, String str, Properties properties, LoggerRepository loggerRepository) {
        Appender appender;
        if (OptionConverter.toBoolean(OptionConverter.findAndSubst(new StringBuffer().append(str).append(ROOT_REF).toString(), properties), false)) {
            errorHandler.setLogger(loggerRepository.getRootLogger());
        }
        String strFindAndSubst = OptionConverter.findAndSubst(new StringBuffer().append(str).append(LOGGER_REF).toString(), properties);
        if (strFindAndSubst != null) {
            errorHandler.setLogger(this.loggerFactory == null ? loggerRepository.getLogger(strFindAndSubst) : loggerRepository.getLogger(strFindAndSubst, this.loggerFactory));
        }
        String strFindAndSubst2 = OptionConverter.findAndSubst(new StringBuffer().append(str).append(APPENDER_REF_TAG).toString(), properties);
        if (strFindAndSubst2 != null && (appender = parseAppender(properties, strFindAndSubst2)) != null) {
            errorHandler.setBackupAppender(appender);
        }
    }

    void parseAppenderFilters(Properties properties, String str, Appender appender) throws Throwable {
        Class clsClass$;
        String string = new StringBuffer().append(APPENDER_PREFIX).append(str).append(".filter.").toString();
        int length = string.length();
        Hashtable hashtable = new Hashtable();
        Enumeration enumerationKeys = properties.keys();
        String strSubstring = "";
        while (enumerationKeys.hasMoreElements()) {
            String str2 = (String) enumerationKeys.nextElement();
            if (str2.startsWith(string)) {
                int iIndexOf = str2.indexOf(46, length);
                String strSubstring2 = str2;
                if (iIndexOf != -1) {
                    strSubstring2 = str2.substring(0, iIndexOf);
                    strSubstring = str2.substring(iIndexOf + 1);
                }
                Vector vector = (Vector) hashtable.get(strSubstring2);
                if (vector == null) {
                    vector = new Vector();
                    hashtable.put(strSubstring2, vector);
                }
                if (iIndexOf != -1) {
                    vector.add(new NameValue(strSubstring, OptionConverter.findAndSubst(str2, properties)));
                }
            }
        }
        SortedKeyEnumeration sortedKeyEnumeration = new SortedKeyEnumeration(hashtable);
        while (sortedKeyEnumeration.hasMoreElements()) {
            String str3 = (String) sortedKeyEnumeration.nextElement();
            String property = properties.getProperty(str3);
            if (property != null) {
                LogLog.debug(new StringBuffer().append("Filter key: [").append(str3).append("] class: [").append(properties.getProperty(str3)).append("] props: ").append(hashtable.get(str3)).toString());
                if (class$org$apache$log4j$spi$Filter == null) {
                    clsClass$ = class$("org.apache.log4j.spi.Filter");
                    class$org$apache$log4j$spi$Filter = clsClass$;
                } else {
                    clsClass$ = class$org$apache$log4j$spi$Filter;
                }
                Filter filter = (Filter) OptionConverter.instantiateByClassName(property, clsClass$, null);
                if (filter != null) {
                    PropertySetter propertySetter = new PropertySetter(filter);
                    Enumeration enumerationElements = ((Vector) hashtable.get(str3)).elements();
                    while (enumerationElements.hasMoreElements()) {
                        NameValue nameValue = (NameValue) enumerationElements.nextElement();
                        propertySetter.setProperty(nameValue.key, nameValue.value);
                    }
                    propertySetter.activate();
                    LogLog.debug(new StringBuffer().append("Adding filter of type [").append(filter.getClass()).append("] to appender named [").append(appender.getName()).append("].").toString());
                    appender.addFilter(filter);
                }
            } else {
                LogLog.warn(new StringBuffer().append("Missing class definition for filter: [").append(str3).append("]").toString());
            }
        }
    }

    void registryPut(Appender appender) {
        this.registry.put(appender.getName(), appender);
    }

    Appender registryGet(String str) {
        return (Appender) this.registry.get(str);
    }
}
