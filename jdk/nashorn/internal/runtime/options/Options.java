package jdk.nashorn.internal.runtime.options;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.PropertyPermission;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import jdk.nashorn.internal.runtime.QuotedStringTokenizer;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/options/Options.class */
public final class Options {
    private static final AccessControlContext READ_PROPERTY_ACC_CTXT;
    private final String resource;
    private final PrintWriter err;
    private final List files;
    private final List arguments;
    private final TreeMap options;
    private static final String NASHORN_ARGS_PREPEND_PROPERTY = "nashorn.args.prepend";
    private static final String NASHORN_ARGS_PROPERTY = "nashorn.args";
    private static final String MESSAGES_RESOURCE = "jdk.nashorn.internal.runtime.resources.Options";
    private static ResourceBundle bundle;
    private static HashMap usage;
    private static Collection validOptions;
    private static OptionTemplate helpOptionTemplate;
    private static OptionTemplate definePropTemplate;
    private static String definePropPrefix;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Options.class.desiredAssertionStatus();
        READ_PROPERTY_ACC_CTXT = createPropertyReadAccCtxt();
        bundle = ResourceBundle.getBundle(MESSAGES_RESOURCE, Locale.getDefault());
        validOptions = new TreeSet();
        usage = new HashMap();
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String strNextElement = keys.nextElement();
            StringTokenizer stringTokenizer = new StringTokenizer(strNextElement, ".");
            String strNextToken = null;
            String strNextToken2 = null;
            if (stringTokenizer.countTokens() > 0) {
                strNextToken = stringTokenizer.nextToken();
            }
            if (stringTokenizer.countTokens() > 0) {
                strNextToken2 = stringTokenizer.nextToken();
            }
            if ("option".equals(strNextToken2)) {
                String string = null;
                String string2 = null;
                String string3 = null;
                try {
                    string = bundle.getString(strNextToken + ".options.help.key");
                    string2 = bundle.getString(strNextToken + ".options.xhelp.key");
                    string3 = bundle.getString(strNextToken + ".options.D.key");
                } catch (MissingResourceException unused) {
                }
                boolean zEquals = strNextElement.equals(string);
                OptionTemplate optionTemplate = new OptionTemplate(strNextToken, strNextElement, bundle.getString(strNextElement), zEquals, strNextElement.equals(string2));
                validOptions.add(optionTemplate);
                if (zEquals) {
                    helpOptionTemplate = optionTemplate;
                }
                if (strNextElement.equals(string3)) {
                    definePropPrefix = optionTemplate.getName();
                    definePropTemplate = optionTemplate;
                }
            } else if (strNextToken != null && "options".equals(strNextToken2)) {
                usage.put(strNextToken, bundle.getObject(strNextElement));
            }
        }
    }

    private static AccessControlContext createPropertyReadAccCtxt() {
        Permissions permissions = new Permissions();
        permissions.add(new PropertyPermission("nashorn.*", "read"));
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissions)});
    }

    public Options(String str) {
        this(str, new PrintWriter((OutputStream) System.err, true));
    }

    public Options(String str, PrintWriter printWriter) {
        this.resource = str;
        this.err = printWriter;
        this.files = new ArrayList();
        this.arguments = new ArrayList();
        this.options = new TreeMap();
        for (OptionTemplate optionTemplate : validOptions) {
            if (optionTemplate.getDefaultValue() != null) {
                String stringProperty = getStringProperty(optionTemplate.getKey(), null);
                if (stringProperty != null) {
                    set(optionTemplate.getKey(), createOption(optionTemplate, stringProperty));
                } else if (optionTemplate.getDefaultValue() != null) {
                    set(optionTemplate.getKey(), createOption(optionTemplate, optionTemplate.getDefaultValue()));
                }
            }
        }
    }

    public String getResource() {
        return this.resource;
    }

    public String toString() {
        return this.options.toString();
    }

    private static void checkPropertyName(String str) {
        if (!((String) Objects.requireNonNull(str)).startsWith("nashorn.")) {
            throw new IllegalArgumentException(str);
        }
    }

    public static boolean getBooleanProperty(String str, Boolean bool) {
        checkPropertyName(str);
        return ((Boolean) AccessController.doPrivileged(new PrivilegedAction(str, bool) { // from class: jdk.nashorn.internal.runtime.options.Options.1
            final String val$name;
            final Boolean val$defValue;

            {
                this.val$name = str;
                this.val$defValue = bool;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public Boolean run() {
                try {
                    String property = System.getProperty(this.val$name);
                    if (property != null || this.val$defValue == null) {
                        return Boolean.valueOf((property == null || "false".equalsIgnoreCase(property)) ? false : true);
                    }
                    return this.val$defValue;
                } catch (SecurityException unused) {
                    return false;
                }
            }
        }, READ_PROPERTY_ACC_CTXT)).booleanValue();
    }

    public static boolean getBooleanProperty(String str) {
        return getBooleanProperty(str, null);
    }

    public static String getStringProperty(String str, String str2) {
        checkPropertyName(str);
        return (String) AccessController.doPrivileged(new PrivilegedAction(str, str2) { // from class: jdk.nashorn.internal.runtime.options.Options.2
            final String val$name;
            final String val$defValue;

            {
                this.val$name = str;
                this.val$defValue = str2;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public String run() {
                try {
                    return System.getProperty(this.val$name, this.val$defValue);
                } catch (SecurityException unused) {
                    return this.val$defValue;
                }
            }
        }, READ_PROPERTY_ACC_CTXT);
    }

    public static int getIntProperty(String str, int i) {
        checkPropertyName(str);
        return ((Integer) AccessController.doPrivileged(new PrivilegedAction(str, i) { // from class: jdk.nashorn.internal.runtime.options.Options.3
            final String val$name;
            final int val$defValue;

            {
                this.val$name = str;
                this.val$defValue = i;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public Integer run() {
                try {
                    return Integer.getInteger(this.val$name, this.val$defValue);
                } catch (SecurityException unused) {
                    return Integer.valueOf(this.val$defValue);
                }
            }
        }, READ_PROPERTY_ACC_CTXT)).intValue();
    }

    public Option get(String str) {
        return (Option) this.options.get(key(str));
    }

    public boolean getBoolean(String str) {
        Option option = get(str);
        if (option != null) {
            return ((Boolean) option.getValue()).booleanValue();
        }
        return false;
    }

    public int getInteger(String str) {
        Option option = get(str);
        if (option != null) {
            return ((Integer) option.getValue()).intValue();
        }
        return 0;
    }

    public String getString(String str) {
        String str2;
        Option option = get(str);
        if (option != null && (str2 = (String) option.getValue()) != null) {
            return str2.intern();
        }
        return null;
    }

    public void set(String str, Option option) {
        this.options.put(key(str), option);
    }

    public void set(String str, boolean z) {
        set(str, new Option(Boolean.valueOf(z)));
    }

    public void set(String str, String str2) {
        set(str, new Option(str2));
    }

    public List getArguments() {
        return Collections.unmodifiableList(this.arguments);
    }

    public List getFiles() {
        return Collections.unmodifiableList(this.files);
    }

    public static Collection getValidOptions() {
        return Collections.unmodifiableCollection(validOptions);
    }

    private String key(String str) {
        String str2;
        String strSubstring = str;
        while (true) {
            str2 = strSubstring;
            if (!str2.startsWith("-")) {
                break;
            }
            strSubstring = str2.substring(1, str2.length());
        }
        String strReplace = str2.replace("-", ".");
        String str3 = this.resource + ".option.";
        if (strReplace.startsWith(str3)) {
            return strReplace;
        }
        return str3 + strReplace;
    }

    static String getMsg(String str, String[] strArr) {
        try {
            String string = bundle.getString(str);
            if (strArr.length == 0) {
                return string;
            }
            return new MessageFormat(string).format(strArr);
        } catch (MissingResourceException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void displayHelp(IllegalArgumentException illegalArgumentException) {
        if (illegalArgumentException instanceof IllegalOptionException) {
            if (((IllegalOptionException) illegalArgumentException).getTemplate().isXHelp()) {
                displayHelp(true);
                return;
            } else {
                this.err.println(((IllegalOptionException) illegalArgumentException).getTemplate());
                return;
            }
        }
        if (illegalArgumentException != null && illegalArgumentException.getMessage() != null) {
            this.err.println(getMsg("option.error.invalid.option", new String[]{illegalArgumentException.getMessage(), helpOptionTemplate.getShortName(), helpOptionTemplate.getName()}));
            this.err.println();
        } else {
            displayHelp(false);
        }
    }

    public void displayHelp(boolean z) {
        for (OptionTemplate optionTemplate : validOptions) {
            if (z || !optionTemplate.isUndocumented()) {
                if (optionTemplate.getResource().equals(this.resource)) {
                    this.err.println(optionTemplate);
                    this.err.println();
                }
            }
        }
    }

    public void process(String[] strArr) {
        LinkedList linkedList = new LinkedList();
        addSystemProperties(NASHORN_ARGS_PREPEND_PROPERTY, linkedList);
        processArgList(linkedList);
        if (!$assertionsDisabled && !linkedList.isEmpty()) {
            throw new AssertionError();
        }
        Collections.addAll(linkedList, strArr);
        processArgList(linkedList);
        if (!$assertionsDisabled && !linkedList.isEmpty()) {
            throw new AssertionError();
        }
        addSystemProperties(NASHORN_ARGS_PROPERTY, linkedList);
        processArgList(linkedList);
        if (!$assertionsDisabled && !linkedList.isEmpty()) {
            throw new AssertionError();
        }
    }

    private void processArgList(LinkedList linkedList) {
        while (!linkedList.isEmpty()) {
            String str = (String) linkedList.remove(0);
            if (!str.isEmpty()) {
                if ("--".equals(str)) {
                    this.arguments.addAll(linkedList);
                    linkedList.clear();
                } else if (!str.startsWith("-") || str.length() == 1) {
                    this.files.add(str);
                } else if (str.startsWith(definePropPrefix)) {
                    String strSubstring = str.substring(definePropPrefix.length());
                    int iIndexOf = strSubstring.indexOf(61);
                    if (iIndexOf != -1) {
                        System.setProperty(strSubstring.substring(0, iIndexOf), strSubstring.substring(iIndexOf + 1));
                    } else if (!strSubstring.isEmpty()) {
                        System.setProperty(strSubstring, "");
                    } else {
                        throw new IllegalOptionException(definePropTemplate);
                    }
                } else {
                    ParsedArg parsedArg = new ParsedArg(str);
                    if (parsedArg.template.isValueNextArg()) {
                        if (linkedList.isEmpty()) {
                            throw new IllegalOptionException(parsedArg.template);
                        }
                        parsedArg.value = (String) linkedList.remove(0);
                    }
                    if (parsedArg.template.isHelp()) {
                        if (!linkedList.isEmpty()) {
                            throw new IllegalOptionException(new ParsedArg((String) linkedList.get(0)).template);
                        }
                        throw new IllegalArgumentException();
                    }
                    if (parsedArg.template.isXHelp()) {
                        throw new IllegalOptionException(parsedArg.template);
                    }
                    set(parsedArg.template.getKey(), createOption(parsedArg.template, parsedArg.value));
                    if (parsedArg.template.getDependency() != null) {
                        linkedList.addFirst(parsedArg.template.getDependency());
                    }
                }
            }
        }
    }

    private static void addSystemProperties(String str, List list) {
        String stringProperty = getStringProperty(str, null);
        if (stringProperty != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(stringProperty);
            while (stringTokenizer.hasMoreTokens()) {
                list.add(stringTokenizer.nextToken());
            }
        }
    }

    public OptionTemplate getOptionTemplateByKey(String str) {
        String strKey = key(str);
        for (OptionTemplate optionTemplate : validOptions) {
            if (optionTemplate.getKey().equals(strKey)) {
                return optionTemplate;
            }
        }
        throw new IllegalArgumentException(str);
    }

    private static OptionTemplate getOptionTemplateByName(String str) {
        for (OptionTemplate optionTemplate : validOptions) {
            if (optionTemplate.nameMatches(str)) {
                return optionTemplate;
            }
        }
        return null;
    }

    private static Option createOption(OptionTemplate optionTemplate, String str) {
        switch (optionTemplate.getType()) {
            case "string":
                return new Option(str);
            case "timezone":
                return new Option(TimeZone.getTimeZone(str));
            case "locale":
                return new Option(Locale.forLanguageTag(str));
            case "keyvalues":
                return new KeyValueOption(str);
            case "log":
                return new LoggingOption(str);
            case "boolean":
                return new Option(Boolean.valueOf(str != null && Boolean.parseBoolean(str)));
            case "integer":
                try {
                    return new Option(Integer.valueOf(str == null ? 0 : Integer.parseInt(str)));
                } catch (NumberFormatException unused) {
                    throw new IllegalOptionException(optionTemplate);
                }
            case "properties":
                initProps(new KeyValueOption(str));
                return null;
            default:
                throw new IllegalArgumentException(str);
        }
    }

    private static void initProps(KeyValueOption keyValueOption) {
        for (Map.Entry entry : keyValueOption.getValues().entrySet()) {
            System.setProperty((String) entry.getKey(), (String) entry.getValue());
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/options/Options$IllegalOptionException.class */
    private static class IllegalOptionException extends IllegalArgumentException {
        private final OptionTemplate template;

        IllegalOptionException(OptionTemplate optionTemplate) {
            this.template = optionTemplate;
        }

        OptionTemplate getTemplate() {
            return this.template;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/options/Options$ParsedArg.class */
    private static class ParsedArg {
        OptionTemplate template;
        String value;

        ParsedArg(String str) {
            QuotedStringTokenizer quotedStringTokenizer = new QuotedStringTokenizer(str, "=");
            if (!quotedStringTokenizer.hasMoreTokens()) {
                throw new IllegalArgumentException();
            }
            this.template = Options.getOptionTemplateByName(quotedStringTokenizer.nextToken());
            if (this.template == null) {
                throw new IllegalArgumentException(str);
            }
            this.value = "";
            if (quotedStringTokenizer.hasMoreTokens()) {
                while (quotedStringTokenizer.hasMoreTokens()) {
                    this.value += quotedStringTokenizer.nextToken();
                    if (quotedStringTokenizer.hasMoreTokens()) {
                        this.value += ':';
                    }
                }
                return;
            }
            if ("boolean".equals(this.template.getType())) {
                this.value = "true";
            }
        }
    }
}
