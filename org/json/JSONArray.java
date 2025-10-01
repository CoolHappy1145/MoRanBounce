package org.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: L-out.jar:org/json/JSONArray.class */
public class JSONArray implements Iterable {
    private final ArrayList myArrayList;

    public JSONArray() {
        this.myArrayList = new ArrayList();
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
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    public JSONArray(org.json.JSONTokener r4) {
        /*
            r3 = this;
            r0 = r3
            r0.<init>()
            r0 = r4
            char r0 = r0.nextClean()
            r1 = 91
            if (r0 == r1) goto L14
            r0 = r4
            java.lang.String r1 = "A JSONArray text must start with '['"
            org.json.JSONException r0 = r0.syntaxError(r1)
            throw r0
        L14:
            r0 = r4
            char r0 = r0.nextClean()
            r1 = 93
            if (r0 == r1) goto L85
            r0 = r4
            r0.back()
        L21:
            r0 = r4
            char r0 = r0.nextClean()
            r1 = 44
            if (r0 != r1) goto L3c
            r0 = r4
            r0.back()
            r0 = r3
            java.util.ArrayList r0 = r0.myArrayList
            java.lang.Object r1 = org.json.JSONObject.NULL
            boolean r0 = r0.add(r1)
            goto L4c
        L3c:
            r0 = r4
            r0.back()
            r0 = r3
            java.util.ArrayList r0 = r0.myArrayList
            r1 = r4
            java.lang.Object r1 = r1.nextValue()
            boolean r0 = r0.add(r1)
        L4c:
            r0 = r4
            char r0 = r0.nextClean()
            switch(r0) {
                case 44: goto L6c;
                case 93: goto L7d;
                default: goto L7e;
            }
        L6c:
            r0 = r4
            char r0 = r0.nextClean()
            r1 = 93
            if (r0 != r1) goto L76
            return
        L76:
            r0 = r4
            r0.back()
            goto L21
        L7d:
            return
        L7e:
            r0 = r4
            java.lang.String r1 = "Expected a ',' or ']'"
            org.json.JSONException r0 = r0.syntaxError(r1)
            throw r0
        L85:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONArray.<init>(org.json.JSONTokener):void");
    }

    public JSONArray(String str) {
        this(new JSONTokener(str));
    }

    public JSONArray(Collection collection) {
        if (collection == null) {
            this.myArrayList = new ArrayList();
            return;
        }
        this.myArrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            this.myArrayList.add(JSONObject.wrap(it.next()));
        }
    }

    public JSONArray(Object obj) {
        this();
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            this.myArrayList.ensureCapacity(length);
            for (int i = 0; i < length; i++) {
                put(JSONObject.wrap(Array.get(obj, i)));
            }
            return;
        }
        throw new JSONException("JSONArray initial value should be a string or collection or array.");
    }

    @Override // java.lang.Iterable
    public Iterator iterator() {
        return this.myArrayList.iterator();
    }

    public Object get(int i) {
        Object objOpt = opt(i);
        if (objOpt == null) {
            throw new JSONException("JSONArray[" + i + "] not found.");
        }
        return objOpt;
    }

    public boolean getBoolean(int i) {
        Object obj = get(i);
        if (!obj.equals(Boolean.FALSE)) {
            if ((obj instanceof String) && ((String) obj).equalsIgnoreCase("false")) {
                return false;
            }
            if (!obj.equals(Boolean.TRUE)) {
                if ((obj instanceof String) && ((String) obj).equalsIgnoreCase("true")) {
                    return true;
                }
                throw new JSONException("JSONArray[" + i + "] is not a boolean.");
            }
            return true;
        }
        return false;
    }

    public double getDouble(int i) {
        Object obj = get(i);
        try {
            return obj instanceof Number ? ((Number) obj).doubleValue() : Double.parseDouble((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONArray[" + i + "] is not a number.", e);
        }
    }

    public float getFloat(int i) {
        Object obj = get(i);
        try {
            return obj instanceof Number ? ((Number) obj).floatValue() : Float.parseFloat(obj.toString());
        } catch (Exception e) {
            throw new JSONException("JSONArray[" + i + "] is not a number.", e);
        }
    }

    public Number getNumber(int i) {
        Object obj = get(i);
        try {
            if (obj instanceof Number) {
                return (Number) obj;
            }
            return JSONObject.stringToNumber(obj.toString());
        } catch (Exception e) {
            throw new JSONException("JSONArray[" + i + "] is not a number.", e);
        }
    }

    public Enum getEnum(Class cls, int i) {
        Enum enumOptEnum = optEnum(cls, i);
        if (enumOptEnum == null) {
            throw new JSONException("JSONArray[" + i + "] is not an enum of type " + JSONObject.quote(cls.getSimpleName()) + ".");
        }
        return enumOptEnum;
    }

    public BigDecimal getBigDecimal(int i) {
        try {
            return new BigDecimal(get(i).toString());
        } catch (Exception e) {
            throw new JSONException("JSONArray[" + i + "] could not convert to BigDecimal.", e);
        }
    }

    public BigInteger getBigInteger(int i) {
        try {
            return new BigInteger(get(i).toString());
        } catch (Exception e) {
            throw new JSONException("JSONArray[" + i + "] could not convert to BigInteger.", e);
        }
    }

    public int getInt(int i) {
        Object obj = get(i);
        try {
            return obj instanceof Number ? ((Number) obj).intValue() : Integer.parseInt((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONArray[" + i + "] is not a number.", e);
        }
    }

    public JSONArray getJSONArray(int i) {
        Object obj = get(i);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        throw new JSONException("JSONArray[" + i + "] is not a JSONArray.");
    }

    public JSONObject getJSONObject(int i) {
        Object obj = get(i);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw new JSONException("JSONArray[" + i + "] is not a JSONObject.");
    }

    public long getLong(int i) {
        Object obj = get(i);
        try {
            return obj instanceof Number ? ((Number) obj).longValue() : Long.parseLong((String) obj);
        } catch (Exception e) {
            throw new JSONException("JSONArray[" + i + "] is not a number.", e);
        }
    }

    public String getString(int i) {
        Object obj = get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        throw new JSONException("JSONArray[" + i + "] not a string.");
    }

    public boolean isNull(int i) {
        return JSONObject.NULL.equals(opt(i));
    }

    public String join(String str) {
        int length = length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(str);
            }
            sb.append(JSONObject.valueToString(this.myArrayList.get(i)));
        }
        return sb.toString();
    }

    public int length() {
        return this.myArrayList.size();
    }

    public Object opt(int i) {
        if (i < 0 || i >= length()) {
            return null;
        }
        return this.myArrayList.get(i);
    }

    public boolean optBoolean(int i) {
        return optBoolean(i, false);
    }

    public boolean optBoolean(int i, boolean z) {
        try {
            return getBoolean(i);
        } catch (Exception unused) {
            return z;
        }
    }

    public double optDouble(int i) {
        return optDouble(i, Double.NaN);
    }

    public double optDouble(int i, double d) {
        Object objOpt = opt(i);
        if (JSONObject.NULL.equals(objOpt)) {
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

    public float optFloat(int i) {
        return optFloat(i, Float.NaN);
    }

    public float optFloat(int i, float f) {
        Object objOpt = opt(i);
        if (JSONObject.NULL.equals(objOpt)) {
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

    public int optInt(int i) {
        return optInt(i, 0);
    }

    public int optInt(int i, int i2) {
        Object objOpt = opt(i);
        if (JSONObject.NULL.equals(objOpt)) {
            return i2;
        }
        if (objOpt instanceof Number) {
            return ((Number) objOpt).intValue();
        }
        if (objOpt instanceof String) {
            try {
                return new BigDecimal(objOpt.toString()).intValue();
            } catch (Exception unused) {
                return i2;
            }
        }
        return i2;
    }

    public Enum optEnum(Class cls, int i) {
        return optEnum(cls, i, null);
    }

    public Enum optEnum(Class cls, int i, Enum r6) {
        try {
            Object objOpt = opt(i);
            if (JSONObject.NULL.equals(objOpt)) {
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

    public BigInteger optBigInteger(int i, BigInteger bigInteger) {
        Object objOpt = opt(i);
        if (JSONObject.NULL.equals(objOpt)) {
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
            if (JSONObject.isDecimalNotation(string)) {
                return new BigDecimal(string).toBigInteger();
            }
            return new BigInteger(string);
        } catch (Exception unused) {
            return bigInteger;
        }
    }

    public BigDecimal optBigDecimal(int i, BigDecimal bigDecimal) {
        Object objOpt = opt(i);
        if (JSONObject.NULL.equals(objOpt)) {
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

    public JSONArray optJSONArray(int i) {
        Object objOpt = opt(i);
        if (objOpt instanceof JSONArray) {
            return (JSONArray) objOpt;
        }
        return null;
    }

    public JSONObject optJSONObject(int i) {
        Object objOpt = opt(i);
        if (objOpt instanceof JSONObject) {
            return (JSONObject) objOpt;
        }
        return null;
    }

    public long optLong(int i) {
        return optLong(i, 0L);
    }

    public long optLong(int i, long j) {
        Object objOpt = opt(i);
        if (JSONObject.NULL.equals(objOpt)) {
            return j;
        }
        if (objOpt instanceof Number) {
            return ((Number) objOpt).longValue();
        }
        if (objOpt instanceof String) {
            try {
                return new BigDecimal(objOpt.toString()).longValue();
            } catch (Exception unused) {
                return j;
            }
        }
        return j;
    }

    public Number optNumber(int i) {
        return optNumber(i, null);
    }

    public Number optNumber(int i, Number number) {
        Object objOpt = opt(i);
        if (JSONObject.NULL.equals(objOpt)) {
            return number;
        }
        if (objOpt instanceof Number) {
            return (Number) objOpt;
        }
        if (objOpt instanceof String) {
            try {
                return JSONObject.stringToNumber((String) objOpt);
            } catch (Exception unused) {
                return number;
            }
        }
        return number;
    }

    public String optString(int i) {
        return optString(i, "");
    }

    public String optString(int i, String str) {
        Object objOpt = opt(i);
        return JSONObject.NULL.equals(objOpt) ? str : objOpt.toString();
    }

    public JSONArray put(boolean z) {
        put(z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONArray put(Collection collection) {
        put(new JSONArray(collection));
        return this;
    }

    public JSONArray put(double d) {
        Double d2 = new Double(d);
        JSONObject.testValidity(d2);
        put(d2);
        return this;
    }

    public JSONArray put(int i) {
        put(new Integer(i));
        return this;
    }

    public JSONArray put(long j) {
        put(new Long(j));
        return this;
    }

    public JSONArray put(Map map) {
        put(new JSONObject(map));
        return this;
    }

    public JSONArray put(Object obj) {
        this.myArrayList.add(obj);
        return this;
    }

    public JSONArray put(int i, boolean z) {
        put(i, z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONArray put(int i, Collection collection) {
        put(i, new JSONArray(collection));
        return this;
    }

    public JSONArray put(int i, double d) {
        put(i, new Double(d));
        return this;
    }

    public JSONArray put(int i, int i2) {
        put(i, new Integer(i2));
        return this;
    }

    public JSONArray put(int i, long j) {
        put(i, new Long(j));
        return this;
    }

    public JSONArray put(int i, Map map) {
        put(i, new JSONObject(map));
        return this;
    }

    public JSONArray put(int i, Object obj) {
        JSONObject.testValidity(obj);
        if (i < 0) {
            throw new JSONException("JSONArray[" + i + "] not found.");
        }
        if (i < length()) {
            this.myArrayList.set(i, obj);
        } else if (i == length()) {
            put(obj);
        } else {
            this.myArrayList.ensureCapacity(i + 1);
            while (i != length()) {
                put(JSONObject.NULL);
            }
            put(obj);
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

    public Object remove(int i) {
        if (i < 0 || i >= length()) {
            return null;
        }
        return this.myArrayList.remove(i);
    }

    public boolean similar(Object obj) {
        int length;
        Object obj2;
        Object obj3;
        if (!(obj instanceof JSONArray) || (length = length()) != ((JSONArray) obj).length()) {
            return false;
        }
        for (int i = 0; i < length && (obj2 = this.myArrayList.get(i)) != (obj3 = ((JSONArray) obj).myArrayList.get(i)); i++) {
            if (obj2 == null) {
                return false;
            }
            if (obj2 instanceof JSONObject) {
                if (!((JSONObject) obj2).similar(obj3)) {
                    return false;
                }
            } else if (obj2 instanceof JSONArray) {
                if (!((JSONArray) obj2).similar(obj3)) {
                    return false;
                }
            } else if (!obj2.equals(obj3)) {
                return false;
            }
        }
        return true;
    }

    public JSONObject toJSONObject(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() == 0 || length() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            jSONObject.put(jSONArray.getString(i), opt(i));
        }
        return jSONObject;
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

    public Writer write(Writer writer) {
        return write(writer, 0, 0);
    }

    public Writer write(Writer writer, int i, int i2) {
        try {
            boolean z = false;
            int length = length();
            writer.write(91);
            if (length == 1) {
                try {
                    JSONObject.writeValue(writer, this.myArrayList.get(0), i, i2);
                } catch (Exception e) {
                    throw new JSONException("Unable to write JSONArray value at index: 0", e);
                }
            } else if (length != 0) {
                int i3 = i2 + i;
                for (int i4 = 0; i4 < length; i4++) {
                    if (z) {
                        writer.write(44);
                    }
                    if (i > 0) {
                        writer.write(10);
                    }
                    JSONObject.indent(writer, i3);
                    try {
                        JSONObject.writeValue(writer, this.myArrayList.get(i4), i, i3);
                        z = true;
                    } catch (Exception e2) {
                        throw new JSONException("Unable to write JSONArray value at index: " + i4, e2);
                    }
                }
                if (i > 0) {
                    writer.write(10);
                }
                JSONObject.indent(writer, i2);
            }
            writer.write(93);
            return writer;
        } catch (IOException e3) {
            throw new JSONException(e3);
        }
    }

    public List toList() {
        ArrayList arrayList = new ArrayList(this.myArrayList.size());
        Iterator it = this.myArrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next == null || JSONObject.NULL.equals(next)) {
                arrayList.add(null);
            } else if (next instanceof JSONArray) {
                arrayList.add(((JSONArray) next).toList());
            } else if (next instanceof JSONObject) {
                arrayList.add(((JSONObject) next).toMap());
            } else {
                arrayList.add(next);
            }
        }
        return arrayList;
    }
}
