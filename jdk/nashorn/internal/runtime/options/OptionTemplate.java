package jdk.nashorn.internal.runtime.options;

import java.util.Locale;
import java.util.TimeZone;
import jdk.nashorn.internal.runtime.QuotedStringTokenizer;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/options/OptionTemplate.class */
public final class OptionTemplate implements Comparable {
    private final String resource;
    private final String key;
    private final boolean isHelp;
    private final boolean isXHelp;
    private String name;
    private String shortName;
    private String params;
    private String type;
    private String defaultValue;
    private String dependency;
    private String conflict;
    private boolean isUndocumented;
    private String description;
    private boolean valueNextArg;
    private static final int LINE_BREAK = 64;

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareTo((OptionTemplate) obj);
    }

    OptionTemplate(String str, String str2, String str3, boolean z, boolean z2) {
        this.resource = str;
        this.key = str2;
        this.isHelp = z;
        this.isXHelp = z2;
        parse(str3);
    }

    public boolean isHelp() {
        return this.isHelp;
    }

    public boolean isXHelp() {
        return this.isXHelp;
    }

    public String getResource() {
        return this.resource;
    }

    public String getType() {
        return this.type;
    }

    public String getKey() {
        return this.key;
    }

    public String getDefaultValue() {
        switch (getType()) {
            case "boolean":
                if (this.defaultValue == null) {
                    this.defaultValue = "false";
                    break;
                }
                break;
            case "integer":
                if (this.defaultValue == null) {
                    this.defaultValue = "0";
                    break;
                }
                break;
            case "timezone":
                this.defaultValue = TimeZone.getDefault().getID();
                break;
            case "locale":
                this.defaultValue = Locale.getDefault().toLanguageTag();
                break;
        }
        return this.defaultValue;
    }

    public String getDependency() {
        return this.dependency;
    }

    public String getConflict() {
        return this.conflict;
    }

    public boolean isUndocumented() {
        return this.isUndocumented;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isValueNextArg() {
        return this.valueNextArg;
    }

    private static String strip(String str, char c, char c2) {
        int length = str.length();
        if (length > 1 && str.charAt(0) == c && str.charAt(length - 1) == c2) {
            return str.substring(1, length - 1);
        }
        return null;
    }

    private void parse(String str) {
        String strNextToken;
        String strNextToken2;
        try {
            QuotedStringTokenizer quotedStringTokenizer = new QuotedStringTokenizer(strip(str.trim(), '{', '}'), ",");
            while (quotedStringTokenizer.hasMoreTokens()) {
                QuotedStringTokenizer quotedStringTokenizer2 = new QuotedStringTokenizer(quotedStringTokenizer.nextToken(), "=");
                strNextToken = quotedStringTokenizer2.nextToken();
                strNextToken2 = quotedStringTokenizer2.nextToken();
                switch (strNextToken) {
                    case "is_undocumented":
                        this.isUndocumented = Boolean.parseBoolean(strNextToken2);
                        break;
                    case "name":
                        if (!strNextToken2.startsWith("-")) {
                            throw new IllegalArgumentException(strNextToken2);
                        }
                        this.name = strNextToken2;
                        break;
                    case "short_name":
                        if (!strNextToken2.startsWith("-")) {
                            throw new IllegalArgumentException(strNextToken2);
                        }
                        this.shortName = strNextToken2;
                        break;
                    case "desc":
                        this.description = strNextToken2;
                        break;
                    case "params":
                        this.params = strNextToken2;
                        break;
                    case "type":
                        this.type = strNextToken2.toLowerCase(Locale.ENGLISH);
                        break;
                    case "default":
                        this.defaultValue = strNextToken2;
                        break;
                    case "dependency":
                        this.dependency = strNextToken2;
                        break;
                    case "conflict":
                        this.conflict = strNextToken2;
                        break;
                    case "value_next_arg":
                        this.valueNextArg = Boolean.parseBoolean(strNextToken2);
                        break;
                    default:
                        throw new IllegalArgumentException(strNextToken);
                }
            }
            if (this.type == null) {
                this.type = "boolean";
            }
            if (this.params == null && "boolean".equals(this.type)) {
                this.params = "[true|false]";
            }
            if (this.name == null && this.shortName == null) {
                throw new IllegalArgumentException(str);
            }
        } catch (Exception unused) {
            throw new IllegalArgumentException(str);
        }
    }

    boolean nameMatches(String str) {
        return str.equals(this.shortName) || str.equals(this.name);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('\t');
        if (this.shortName != null) {
            sb.append(this.shortName);
            if (this.name != null) {
                sb.append(", ");
            }
        }
        if (this.name != null) {
            sb.append(this.name);
        }
        if (this.description != null) {
            int length = sb.length();
            sb.append(' ');
            sb.append('(');
            int i = 0;
            for (char c : this.description.toCharArray()) {
                sb.append(c);
                i++;
                if (i >= 64 && Character.isWhitespace(c)) {
                    i = 0;
                    sb.append("\n\t");
                    for (int i2 = 0; i2 < length; i2++) {
                        sb.append(' ');
                    }
                }
            }
            sb.append(')');
        }
        if (this.params != null) {
            sb.append('\n');
            sb.append('\t');
            sb.append('\t');
            sb.append(Options.getMsg("nashorn.options.param", new String[0])).append(": ");
            sb.append(this.params);
            sb.append("   ");
            if (getDefaultValue() != null) {
                sb.append(Options.getMsg("nashorn.options.default", new String[0])).append(": ");
                sb.append(getDefaultValue());
            }
        }
        return sb.toString();
    }

    public int compareTo(OptionTemplate optionTemplate) {
        return getKey().compareTo(optionTemplate.getKey());
    }
}
