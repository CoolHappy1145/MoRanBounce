package org.json;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.tools.Shell;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:org/json/JSONObject.class */
public class JSONObject {
    private final Map map;
    public static final Object NULL = new Null(null);

    /* loaded from: L-out.jar:org/json/JSONObject$Null.class */
    private static final class Null {
        private Null() {
        }

        Null(C05401 c05401) {
            this();
        }
    }

    public JSONObject() {
        this.map = new HashMap();
    }

    public JSONObject(JSONObject jSONObject, String[] strArr) {
        this(strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            try {
                putOnce(strArr[i], jSONObject.opt(strArr[i]));
            } catch (Exception unused) {
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:200)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    public JSONObject(org.json.JSONTokener r5) {
        /*
            r4 = this;
            r0 = r4
            r0.<init>()
            r0 = r5
            char r0 = r0.nextClean()
            r1 = 123(0x7b, float:1.72E-43)
            if (r0 == r1) goto L14
            r0 = r5
            java.lang.String r1 = "A JSONObject text must begin with '{'"
            org.json.JSONException r0 = r0.syntaxError(r1)
            throw r0
        L14:
            r0 = r5
            char r0 = r0.nextClean()
            r6 = r0
            r0 = r6
            switch(r0) {
                case 0: goto L34;
                case 125: goto L3b;
                default: goto L3c;
            }
        L34:
            r0 = r5
            java.lang.String r1 = "A JSONObject text must end with '}'"
            org.json.JSONException r0 = r0.syntaxError(r1)
            throw r0
        L3b:
            return
        L3c:
            r0 = r5
            r0.back()
            r0 = r5
            java.lang.Object r0 = r0.nextValue()
            java.lang.String r0 = r0.toString()
            r7 = r0
            r0 = r5
            char r0 = r0.nextClean()
            r6 = r0
            r0 = r6
            r1 = 58
            if (r0 == r1) goto L5a
            r0 = r5
            java.lang.String r1 = "Expected a ':' after a key"
            org.json.JSONException r0 = r0.syntaxError(r1)
            throw r0
        L5a:
            r0 = r7
            if (r0 == 0) goto L96
            r0 = r4
            r1 = r7
            java.lang.Object r0 = r0.opt(r1)
            if (r0 == 0) goto L83
            r0 = r5
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r2 = r1
            r2.<init>()
            java.lang.String r2 = "Duplicate key \""
            java.lang.StringBuilder r1 = r1.append(r2)
            r2 = r7
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "\""
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            org.json.JSONException r0 = r0.syntaxError(r1)
            throw r0
        L83:
            r0 = r5
            java.lang.Object r0 = r0.nextValue()
            r8 = r0
            r0 = r8
            if (r0 == 0) goto L96
            r0 = r4
            r1 = r7
            r2 = r8
            org.json.JSONObject r0 = r0.put(r1, r2)
        L96:
            r0 = r5
            char r0 = r0.nextClean()
            switch(r0) {
                case 44: goto Lbc;
                case 59: goto Lbc;
                case 125: goto Lcd;
                default: goto Lce;
            }
        Lbc:
            r0 = r5
            char r0 = r0.nextClean()
            r1 = 125(0x7d, float:1.75E-43)
            if (r0 != r1) goto Lc6
            return
        Lc6:
            r0 = r5
            r0.back()
            goto L14
        Lcd:
            return
        Lce:
            r0 = r5
            java.lang.String r1 = "Expected a ',' or '}'"
            org.json.JSONException r0 = r0.syntaxError(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONObject.<init>(org.json.JSONTokener):void");
    }

    public JSONObject(Map map) {
        if (map == null) {
            this.map = new HashMap();
            return;
        }
        this.map = new HashMap(map.size());
        for (Map.Entry entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                this.map.put(String.valueOf(entry.getKey()), wrap(value));
            }
        }
    }

    public JSONObject(Object obj) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        this();
        populateMap(obj);
    }

    public JSONObject(Object obj, String[] strArr) {
        this(strArr.length);
        Class<?> cls = obj.getClass();
        for (String str : strArr) {
            try {
                putOpt(str, cls.getField(str).get(obj));
            } catch (Exception unused) {
            }
        }
    }

    public JSONObject(String str) {
        this(new JSONTokener(str));
    }

    public JSONObject(String str, Locale locale) {
        this();
        ResourceBundle bundle = ResourceBundle.getBundle(str, locale, Thread.currentThread().getContextClassLoader());
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String strNextElement = keys.nextElement();
            if (strNextElement != null) {
                String[] strArrSplit = strNextElement.split("\\.");
                int length = strArrSplit.length - 1;
                JSONObject jSONObject = this;
                for (int i = 0; i < length; i++) {
                    String str2 = strArrSplit[i];
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(str2);
                    if (jSONObjectOptJSONObject == null) {
                        jSONObjectOptJSONObject = new JSONObject();
                        jSONObject.put(str2, jSONObjectOptJSONObject);
                    }
                    jSONObject = jSONObjectOptJSONObject;
                }
                jSONObject.put(strArrSplit[length], bundle.getString(strNextElement));
            }
        }
    }

    protected JSONObject(int i) {
        this.map = new HashMap(i);
    }

    public JSONObject accumulate(String str, Object obj) {
        testValidity(obj);
        Object objOpt = opt(str);
        if (objOpt == null) {
            put(str, obj instanceof JSONArray ? new JSONArray().put(obj) : obj);
        } else if (objOpt instanceof JSONArray) {
            ((JSONArray) objOpt).put(obj);
        } else {
            put(str, new JSONArray().put(objOpt).put(obj));
        }
        return this;
    }

    public JSONObject append(String str, Object obj) {
        testValidity(obj);
        Object objOpt = opt(str);
        if (objOpt == null) {
            put(str, new JSONArray().put(obj));
        } else if (objOpt instanceof JSONArray) {
            put(str, ((JSONArray) objOpt).put(obj));
        } else {
            throw new JSONException("JSONObject[" + str + "] is not a JSONArray.");
        }
        return this;
    }

    public static String doubleToString(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return Configurator.NULL;
        }
        String string = Double.toString(d);
        if (string.indexOf(46) > 0 && string.indexOf(Shell.COMPILATION_ERROR) < 0 && string.indexOf(69) < 0) {
            while (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith(".")) {
                string = string.substring(0, string.length() - 1);
            }
        }
        return string;
    }

    public Object get(String str) {
        if (str == null) {
            throw new JSONException("Null key.");
        }
        Object objOpt = opt(str);
        if (objOpt == null) {
            throw new JSONException("JSONObject[" + quote(str) + "] not found.");
        }
        return objOpt;
    }

    public Enum getEnum(Class cls, String str) {
        Enum enumOptEnum = optEnum(cls, str);
        if (enumOptEnum == null) {
            throw new JSONException("JSONObject[" + quote(str) + "] is not an enum of type " + quote(cls.getSimpleName()) + ".");
        }
        return enumOptEnum;
    }

    public boolean getBoolean(String str) {
        Object obj = get(str);
        if (!obj.equals(Boolean.FALSE)) {
            if ((obj instanceof String) && ((String) obj).equalsIgnoreCase("false")) {
                return false;
            }
            if (!obj.equals(Boolean.TRUE)) {
                if ((obj instanceof String) && ((String) obj).equalsIgnoreCase("true")) {
                    return true;
                }
                throw new JSONException("JSONObject[" + quote(str) + "] is not a Boolean.");
            }
            return true;
        }
        return false;
    }

    public BigInteger getBigInteger(String str) {
        try {
            return new BigInteger(get(str).toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(str) + "] could not be converted to BigInteger.", e);
        }
    }

    public BigDecimal getBigDecimal(String str) {
        Object obj = get(str);
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        try {
            return new BigDecimal(obj.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(str) + "] could not be converted to BigDecimal.", e);
        }
    }

    public double getDouble(String str) {
        Object obj = get(str);
        try {
            return obj instanceof Number ? ((Number) obj).doubleValue() : Double.parseDouble(obj.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(str) + "] is not a number.", e);
        }
    }

    public float getFloat(String str) {
        Object obj = get(str);
        try {
            return obj instanceof Number ? ((Number) obj).floatValue() : Float.parseFloat(obj.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(str) + "] is not a number.", e);
        }
    }

    public Number getNumber(String str) {
        Object obj = get(str);
        try {
            if (obj instanceof Number) {
                return (Number) obj;
            }
            return stringToNumber(obj.toString());
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(str) + "] is not a number.", e);
        }
    }

    public int getInt(String str) {
        Object obj = get(str);
        try {
            return obj instanceof Number ? ((Number) obj).intValue() : Integer.parseInt((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(str) + "] is not an int.", e);
        }
    }

    public JSONArray getJSONArray(String str) {
        Object obj = get(str);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a JSONArray.");
    }

    public JSONObject getJSONObject(String str) {
        Object obj = get(str);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a JSONObject.");
    }

    public long getLong(String str) {
        Object obj = get(str);
        try {
            return obj instanceof Number ? ((Number) obj).longValue() : Long.parseLong((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONObject[" + quote(str) + "] is not a long.", e);
        }
    }

    public static String[] getNames(JSONObject jSONObject) {
        int length = jSONObject.length();
        if (length == 0) {
            return null;
        }
        return (String[]) jSONObject.keySet().toArray(new String[length]);
    }

    public static String[] getNames(Object obj) {
        Field[] fields;
        int length;
        if (obj == null || (length = (fields = obj.getClass().getFields()).length) == 0) {
            return null;
        }
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = fields[i].getName();
        }
        return strArr;
    }

    public String getString(String str) {
        Object obj = get(str);
        if (obj instanceof String) {
            return (String) obj;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] not a string.");
    }

    public boolean has(String str) {
        return this.map.containsKey(str);
    }

    public JSONObject increment(String str) {
        Object objOpt = opt(str);
        if (objOpt == null) {
            put(str, 1);
        } else if (objOpt instanceof BigInteger) {
            put(str, ((BigInteger) objOpt).add(BigInteger.ONE));
        } else if (objOpt instanceof BigDecimal) {
            put(str, ((BigDecimal) objOpt).add(BigDecimal.ONE));
        } else if (objOpt instanceof Integer) {
            put(str, ((Integer) objOpt).intValue() + 1);
        } else if (objOpt instanceof Long) {
            put(str, ((Long) objOpt).longValue() + 1);
        } else if (objOpt instanceof Double) {
            put(str, ((Double) objOpt).doubleValue() + 1.0d);
        } else if (objOpt instanceof Float) {
            put(str, ((Float) objOpt).floatValue() + 1.0f);
        } else {
            throw new JSONException("Unable to increment [" + quote(str) + "].");
        }
        return this;
    }

    public boolean isNull(String str) {
        return NULL.equals(opt(str));
    }

    public Iterator keys() {
        return keySet().iterator();
    }

    public Set keySet() {
        return this.map.keySet();
    }

    protected Set entrySet() {
        return this.map.entrySet();
    }

    public int length() {
        return this.map.size();
    }

    public JSONArray names() {
        if (this.map.isEmpty()) {
            return null;
        }
        return new JSONArray((Collection) this.map.keySet());
    }

    public static String numberToString(Number number) {
        if (number == null) {
            throw new JSONException("Null pointer");
        }
        testValidity(number);
        String string = number.toString();
        if (string.indexOf(46) > 0 && string.indexOf(Shell.COMPILATION_ERROR) < 0 && string.indexOf(69) < 0) {
            while (string.endsWith("0")) {
                string = string.substring(0, string.length() - 1);
            }
            if (string.endsWith(".")) {
                string = string.substring(0, string.length() - 1);
            }
        }
        return string;
    }

    public Object opt(String str) {
        if (str == null) {
            return null;
        }
        return this.map.get(str);
    }

    public Enum optEnum(Class cls, String str) {
        return optEnum(cls, str, null);
    }

    public Enum optEnum(Class cls, String str, Enum r6) {
        try {
            Object objOpt = opt(str);
            if (NULL.equals(objOpt)) {
                return r6;
            }
            if (cls.isAssignableFrom(objOpt.getClass())) {
                return (Enum) objOpt;
            }
            return Enum.valueOf(cls, objOpt.toString());
        } catch (IllegalArgumentException unused) {
            return r6;
        } catch (NullPointerException unused2) {
            return r6;
        }
    }

    public boolean optBoolean(String str) {
        return optBoolean(str, false);
    }

    public boolean optBoolean(String str, boolean z) {
        Object objOpt = opt(str);
        if (NULL.equals(objOpt)) {
            return z;
        }
        if (objOpt instanceof Boolean) {
            return ((Boolean) objOpt).booleanValue();
        }
        try {
            return getBoolean(str);
        } catch (Exception unused) {
            return z;
        }
    }

    public BigDecimal optBigDecimal(String str, BigDecimal bigDecimal) {
        Object objOpt = opt(str);
        if (NULL.equals(objOpt)) {
            return bigDecimal;
        }
        if (objOpt instanceof BigDecimal) {
            return (BigDecimal) objOpt;
        }
        if (objOpt instanceof BigInteger) {
            return new BigDecimal((BigInteger) objOpt);
        }
        if ((objOpt instanceof Double) || (objOpt instanceof Float)) {
            return new BigDecimal(((Number) objOpt).doubleValue());
        }
        if ((objOpt instanceof Long) || (objOpt instanceof Integer) || (objOpt instanceof Short) || (objOpt instanceof Byte)) {
            return new BigDecimal(((Number) objOpt).longValue());
        }
        try {
            return new BigDecimal(objOpt.toString());
        } catch (Exception unused) {
            return bigDecimal;
        }
    }

    public BigInteger optBigInteger(String str, BigInteger bigInteger) {
        Object objOpt = opt(str);
        if (NULL.equals(objOpt)) {
            return bigInteger;
        }
        if (objOpt instanceof BigInteger) {
            return (BigInteger) objOpt;
        }
        if (objOpt instanceof BigDecimal) {
            return ((BigDecimal) objOpt).toBigInteger();
        }
        if ((objOpt instanceof Double) || (objOpt instanceof Float)) {
            return new BigDecimal(((Number) objOpt).doubleValue()).toBigInteger();
        }
        if ((objOpt instanceof Long) || (objOpt instanceof Integer) || (objOpt instanceof Short) || (objOpt instanceof Byte)) {
            return BigInteger.valueOf(((Number) objOpt).longValue());
        }
        try {
            String string = objOpt.toString();
            if (isDecimalNotation(string)) {
                return new BigDecimal(string).toBigInteger();
            }
            return new BigInteger(string);
        } catch (Exception unused) {
            return bigInteger;
        }
    }

    public double optDouble(String str) {
        return optDouble(str, Double.NaN);
    }

    public double optDouble(String str, double d) {
        Object objOpt = opt(str);
        if (NULL.equals(objOpt)) {
            return d;
        }
        if (objOpt instanceof Number) {
            return ((Number) objOpt).doubleValue();
        }
        if (objOpt instanceof String) {
            try {
                return Double.parseDouble((String) objOpt);
            } catch (Exception unused) {
                return d;
            }
        }
        return d;
    }

    public float optFloat(String str) {
        return optFloat(str, Float.NaN);
    }

    public float optFloat(String str, float f) {
        Object objOpt = opt(str);
        if (NULL.equals(objOpt)) {
            return f;
        }
        if (objOpt instanceof Number) {
            return ((Number) objOpt).floatValue();
        }
        if (objOpt instanceof String) {
            try {
                return Float.parseFloat((String) objOpt);
            } catch (Exception unused) {
                return f;
            }
        }
        return f;
    }

    public int optInt(String str) {
        return optInt(str, 0);
    }

    public int optInt(String str, int i) {
        Object objOpt = opt(str);
        if (NULL.equals(objOpt)) {
            return i;
        }
        if (objOpt instanceof Number) {
            return ((Number) objOpt).intValue();
        }
        if (objOpt instanceof String) {
            try {
                return new BigDecimal((String) objOpt).intValue();
            } catch (Exception unused) {
                return i;
            }
        }
        return i;
    }

    public JSONArray optJSONArray(String str) {
        Object objOpt = opt(str);
        if (objOpt instanceof JSONArray) {
            return (JSONArray) objOpt;
        }
        return null;
    }

    public JSONObject optJSONObject(String str) {
        Object objOpt = opt(str);
        if (objOpt instanceof JSONObject) {
            return (JSONObject) objOpt;
        }
        return null;
    }

    public long optLong(String str) {
        return optLong(str, 0L);
    }

    public long optLong(String str, long j) {
        Object objOpt = opt(str);
        if (NULL.equals(objOpt)) {
            return j;
        }
        if (objOpt instanceof Number) {
            return ((Number) objOpt).longValue();
        }
        if (objOpt instanceof String) {
            try {
                return new BigDecimal((String) objOpt).longValue();
            } catch (Exception unused) {
                return j;
            }
        }
        return j;
    }

    public Number optNumber(String str) {
        return optNumber(str, null);
    }

    public Number optNumber(String str, Number number) {
        Object objOpt = opt(str);
        if (NULL.equals(objOpt)) {
            return number;
        }
        if (objOpt instanceof Number) {
            return (Number) objOpt;
        }
        if (objOpt instanceof String) {
            try {
                return stringToNumber((String) objOpt);
            } catch (Exception unused) {
                return number;
            }
        }
        return number;
    }

    public String optString(String str) {
        return optString(str, "");
    }

    public String optString(String str, String str2) {
        Object objOpt = opt(str);
        return NULL.equals(objOpt) ? str2 : objOpt.toString();
    }

    private void populateMap(Object obj) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        String strSubstring;
        Class<?> cls = obj.getClass();
        for (Method method : cls.getClassLoader() != null ? cls.getMethods() : cls.getDeclaredMethods()) {
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && method.getParameterTypes().length == 0 && !method.isBridge() && method.getReturnType() != Void.TYPE) {
                String name = method.getName();
                if (name.startsWith(PropertyDescriptor.GET)) {
                    if (!"getClass".equals(name) && !"getDeclaringClass".equals(name)) {
                        strSubstring = name.substring(3);
                        if (strSubstring.length() <= 0 && Character.isUpperCase(strSubstring.charAt(0))) {
                            if (strSubstring.length() == 1) {
                                strSubstring = strSubstring.toLowerCase(Locale.ROOT);
                            } else if (!Character.isUpperCase(strSubstring.charAt(1))) {
                                strSubstring = strSubstring.substring(0, 1).toLowerCase(Locale.ROOT) + strSubstring.substring(1);
                            }
                            try {
                                Object objInvoke = method.invoke(obj, new Object[0]);
                                if (objInvoke != null) {
                                    this.map.put(strSubstring, wrap(objInvoke));
                                    if (objInvoke instanceof Closeable) {
                                        try {
                                            ((Closeable) objInvoke).close();
                                        } catch (IOException unused) {
                                        }
                                    }
                                }
                            } catch (IllegalAccessException unused2) {
                            } catch (IllegalArgumentException unused3) {
                            } catch (InvocationTargetException unused4) {
                            }
                        }
                    }
                } else if (name.startsWith("is")) {
                    strSubstring = name.substring(2);
                    if (strSubstring.length() <= 0) {
                    }
                }
            }
        }
    }

    public JSONObject put(String str, boolean z) {
        put(str, z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONObject put(String str, Collection collection) {
        put(str, new JSONArray(collection));
        return this;
    }

    public JSONObject put(String str, double d) {
        put(str, Double.valueOf(d));
        return this;
    }

    public JSONObject put(String str, float f) {
        put(str, Float.valueOf(f));
        return this;
    }

    public JSONObject put(String str, int i) {
        put(str, Integer.valueOf(i));
        return this;
    }

    public JSONObject put(String str, long j) {
        put(str, Long.valueOf(j));
        return this;
    }

    public JSONObject put(String str, Map map) {
        put(str, new JSONObject(map));
        return this;
    }

    public JSONObject put(String str, Object obj) {
        if (str == null) {
            throw new NullPointerException("Null key.");
        }
        if (obj != null) {
            testValidity(obj);
            this.map.put(str, obj);
        } else {
            remove(str);
        }
        return this;
    }

    public JSONObject putOnce(String str, Object obj) {
        if (str != null && obj != null) {
            if (opt(str) != null) {
                throw new JSONException("Duplicate key \"" + str + "\"");
            }
            put(str, obj);
        }
        return this;
    }

    public JSONObject putOpt(String str, Object obj) {
        if (str != null && obj != null) {
            put(str, obj);
        }
        return this;
    }

    public Object query(String str) {
        return query(new JSONPointer(str));
    }

    public Object query(JSONPointer jSONPointer) {
        return jSONPointer.queryFrom(this);
    }

    public Object optQuery(String str) {
        return optQuery(new JSONPointer(str));
    }

    public Object optQuery(JSONPointer jSONPointer) {
        try {
            return jSONPointer.queryFrom(this);
        } catch (JSONPointerException unused) {
            return null;
        }
    }

    public static String quote(String str) {
        String string;
        StringWriter stringWriter = new StringWriter();
        synchronized (stringWriter.getBuffer()) {
            try {
                string = quote(str, stringWriter).toString();
            } catch (IOException unused) {
                return "";
            }
        }
        return string;
    }

    public static Writer quote(String str, Writer writer) throws IOException {
        if (str == null || str.length() == 0) {
            writer.write("\"\"");
            return writer;
        }
        char cCharAt = 0;
        int length = str.length();
        writer.write(34);
        for (int i = 0; i < length; i++) {
            char c = cCharAt;
            cCharAt = str.charAt(i);
            switch (cCharAt) {
                case '\b':
                    writer.write("\\b");
                    break;
                case '\t':
                    writer.write("\\t");
                    break;
                case '\n':
                    writer.write("\\n");
                    break;
                case '\f':
                    writer.write("\\f");
                    break;
                case CharacterType.ALNUM /* 13 */:
                    writer.write("\\r");
                    break;
                case '\"':
                case '\\':
                    writer.write(92);
                    writer.write(cCharAt);
                    break;
                case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                    if (c == '<') {
                        writer.write(92);
                    }
                    writer.write(cCharAt);
                    break;
                default:
                    if (cCharAt < ' ' || ((cCharAt >= '\u0080' && cCharAt < '\u00a0') || (cCharAt >= '\u2000' && cCharAt < '\u2100'))) {
                        writer.write("\\u");
                        String hexString = Integer.toHexString(cCharAt);
                        writer.write("0000", 0, 4 - hexString.length());
                        writer.write(hexString);
                        break;
                    } else {
                        writer.write(cCharAt);
                        break;
                    }
                    break;
            }
        }
        writer.write(34);
        return writer;
    }

    public Object remove(String str) {
        return this.map.remove(str);
    }

    public boolean similar(Object obj) {
        try {
            if (!(obj instanceof JSONObject) || !keySet().equals(((JSONObject) obj).keySet())) {
                return false;
            }
            for (Map.Entry entry : entrySet()) {
                String str = (String) entry.getKey();
                Object value = entry.getValue();
                Object obj2 = ((JSONObject) obj).get(str);
                if (value == obj2) {
                    return true;
                }
                if (value == null) {
                    return false;
                }
                if (value instanceof JSONObject) {
                    if (!((JSONObject) value).similar(obj2)) {
                        return false;
                    }
                } else if (value instanceof JSONArray) {
                    if (!((JSONArray) value).similar(obj2)) {
                        return false;
                    }
                } else if (!value.equals(obj2)) {
                    return false;
                }
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    protected static boolean isDecimalNotation(String str) {
        return str.indexOf(46) > -1 || str.indexOf(Shell.COMPILATION_ERROR) > -1 || str.indexOf(69) > -1 || "-0".equals(str);
    }

    protected static Number stringToNumber(String str) throws NumberFormatException {
        char cCharAt = str.charAt(0);
        if ((cCharAt >= '0' && cCharAt <= '9') || cCharAt == '-') {
            if (isDecimalNotation(str)) {
                if (str.length() > 14) {
                    return new BigDecimal(str);
                }
                Double dValueOf = Double.valueOf(str);
                if (dValueOf.isInfinite() || dValueOf.isNaN()) {
                    return new BigDecimal(str);
                }
                return dValueOf;
            }
            BigInteger bigInteger = new BigInteger(str);
            if (bigInteger.bitLength() <= 31) {
                return Integer.valueOf(bigInteger.intValue());
            }
            if (bigInteger.bitLength() <= 63) {
                return Long.valueOf(bigInteger.longValue());
            }
            return bigInteger;
        }
        throw new NumberFormatException("val [" + str + "] is not a valid number.");
    }

    public static Object stringToValue(String str) throws NumberFormatException {
        if (str.equals("")) {
            return str;
        }
        if (str.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (str.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Configurator.NULL)) {
            return NULL;
        }
        char cCharAt = str.charAt(0);
        if ((cCharAt >= '0' && cCharAt <= '9') || cCharAt == '-') {
            try {
                if (isDecimalNotation(str)) {
                    Double dValueOf = Double.valueOf(str);
                    if (!dValueOf.isInfinite() && !dValueOf.isNaN()) {
                        return dValueOf;
                    }
                } else {
                    Long lValueOf = Long.valueOf(str);
                    if (str.equals(lValueOf.toString())) {
                        if (lValueOf.longValue() == lValueOf.intValue()) {
                            return Integer.valueOf(lValueOf.intValue());
                        }
                        return lValueOf;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return str;
    }

    public static void testValidity(Object obj) {
        if (obj != null) {
            if (obj instanceof Double) {
                if (((Double) obj).isInfinite() || ((Double) obj).isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
            } else if (obj instanceof Float) {
                if (((Float) obj).isInfinite() || ((Float) obj).isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
            }
        }
    }

    public JSONArray toJSONArray(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            jSONArray2.put(opt(jSONArray.getString(i)));
        }
        return jSONArray2;
    }

    public String toString() {
        try {
            return toString(0);
        } catch (Exception unused) {
            return null;
        }
    }

    public String toString(int i) {
        String string;
        StringWriter stringWriter = new StringWriter();
        synchronized (stringWriter.getBuffer()) {
            string = write(stringWriter, i, 0).toString();
        }
        return string;
    }

    public static String valueToString(Object obj) {
        if (obj == null || obj.equals(null)) {
            return Configurator.NULL;
        }
        if (obj instanceof JSONString) {
            try {
                String jSONString = ((JSONString) obj).toJSONString();
                if (jSONString instanceof String) {
                    return jSONString;
                }
                throw new JSONException("Bad value from toJSONString: " + ((Object) jSONString));
            } catch (Exception e) {
                throw new JSONException(e);
            }
        }
        if (obj instanceof Number) {
            String strNumberToString = numberToString((Number) obj);
            try {
                new BigDecimal(strNumberToString);
                return strNumberToString;
            } catch (NumberFormatException unused) {
                return quote(strNumberToString);
            }
        }
        if ((obj instanceof Boolean) || (obj instanceof JSONObject) || (obj instanceof JSONArray)) {
            return obj.toString();
        }
        if (obj instanceof Map) {
            return new JSONObject((Map) obj).toString();
        }
        if (obj instanceof Collection) {
            return new JSONArray((Collection) obj).toString();
        }
        if (obj.getClass().isArray()) {
            return new JSONArray(obj).toString();
        }
        if (obj instanceof Enum) {
            return quote(((Enum) obj).name());
        }
        return quote(obj.toString());
    }

    public static Object wrap(Object obj) {
        try {
            if (obj == null) {
                return NULL;
            }
            if ((obj instanceof JSONObject) || (obj instanceof JSONArray) || NULL.equals(obj) || (obj instanceof JSONString) || (obj instanceof Byte) || (obj instanceof Character) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || (obj instanceof Boolean) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof String) || (obj instanceof BigInteger) || (obj instanceof BigDecimal) || (obj instanceof Enum)) {
                return obj;
            }
            if (obj instanceof Collection) {
                return new JSONArray((Collection) obj);
            }
            if (obj.getClass().isArray()) {
                return new JSONArray(obj);
            }
            if (obj instanceof Map) {
                return new JSONObject((Map) obj);
            }
            Package r0 = obj.getClass().getPackage();
            String name = r0 != null ? r0.getName() : "";
            if (name.startsWith("java.") || name.startsWith("javax.") || obj.getClass().getClassLoader() == null) {
                return obj.toString();
            }
            return new JSONObject(obj);
        } catch (Exception unused) {
            return null;
        }
    }

    public Writer write(Writer writer) {
        return write(writer, 0, 0);
    }

    static final Writer writeValue(Writer writer, Object obj, int i, int i2) throws IOException {
        if (obj == null || obj.equals(null)) {
            writer.write(Configurator.NULL);
        } else if (obj instanceof JSONString) {
            try {
                String jSONString = ((JSONString) obj).toJSONString();
                writer.write(jSONString != null ? jSONString.toString() : quote(obj.toString()));
            } catch (Exception e) {
                throw new JSONException(e);
            }
        } else if (obj instanceof Number) {
            String strNumberToString = numberToString((Number) obj);
            try {
                new BigDecimal(strNumberToString);
                writer.write(strNumberToString);
            } catch (NumberFormatException unused) {
                quote(strNumberToString, writer);
            }
        } else if (obj instanceof Boolean) {
            writer.write(obj.toString());
        } else if (obj instanceof Enum) {
            writer.write(quote(((Enum) obj).name()));
        } else if (obj instanceof JSONObject) {
            ((JSONObject) obj).write(writer, i, i2);
        } else if (obj instanceof JSONArray) {
            ((JSONArray) obj).write(writer, i, i2);
        } else if (obj instanceof Map) {
            new JSONObject((Map) obj).write(writer, i, i2);
        } else if (obj instanceof Collection) {
            new JSONArray((Collection) obj).write(writer, i, i2);
        } else if (obj.getClass().isArray()) {
            new JSONArray(obj).write(writer, i, i2);
        } else {
            quote(obj.toString(), writer);
        }
        return writer;
    }

    static final void indent(Writer writer, int i) throws IOException {
        for (int i2 = 0; i2 < i; i2++) {
            writer.write(32);
        }
    }

    public Writer write(Writer writer, int i, int i2) throws IOException {
        try {
            boolean z = false;
            int length = length();
            writer.write(123);
            if (length == 1) {
                Map.Entry entry = (Map.Entry) entrySet().iterator().next();
                String str = (String) entry.getKey();
                writer.write(quote(str));
                writer.write(58);
                if (i > 0) {
                    writer.write(32);
                }
                try {
                    writeValue(writer, entry.getValue(), i, i2);
                } catch (Exception e) {
                    throw new JSONException("Unable to write JSONObject value for key: " + str, e);
                }
            } else if (length != 0) {
                int i3 = i2 + i;
                for (Map.Entry entry2 : entrySet()) {
                    if (z) {
                        writer.write(44);
                    }
                    if (i > 0) {
                        writer.write(10);
                    }
                    indent(writer, i3);
                    String str2 = (String) entry2.getKey();
                    writer.write(quote(str2));
                    writer.write(58);
                    if (i > 0) {
                        writer.write(32);
                    }
                    try {
                        writeValue(writer, entry2.getValue(), i, i3);
                        z = true;
                    } catch (Exception e2) {
                        throw new JSONException("Unable to write JSONObject value for key: " + str2, e2);
                    }
                }
                if (i > 0) {
                    writer.write(10);
                }
                indent(writer, i2);
            }
            writer.write(125);
            return writer;
        } catch (IOException e3) {
            throw new JSONException(e3);
        }
    }

    public Map toMap() {
        Object value;
        HashMap map = new HashMap();
        for (Map.Entry entry : entrySet()) {
            if (entry.getValue() == null || NULL.equals(entry.getValue())) {
                value = null;
            } else if (entry.getValue() instanceof JSONObject) {
                value = ((JSONObject) entry.getValue()).toMap();
            } else if (entry.getValue() instanceof JSONArray) {
                value = ((JSONArray) entry.getValue()).toList();
            } else {
                value = entry.getValue();
            }
            map.put(entry.getKey(), value);
        }
        return map;
    }
}
