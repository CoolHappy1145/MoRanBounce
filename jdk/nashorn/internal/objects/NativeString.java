package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.OptimisticBuiltins;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.NashornGuards;
import jdk.nashorn.internal.runtime.linker.PrimitiveLookup;
import kotlin.jvm.internal.CharCompanionObject;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeString.class */
public final class NativeString extends ScriptObject implements OptimisticBuiltins {
    private final CharSequence value;
    static final MethodHandle WRAPFILTER;
    private static final MethodHandle PROTOFILTER;
    private static PropertyMap $nasgenmap$;
    static final boolean $assertionsDisabled;

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeString$Constructor.class */
    final class Constructor extends ScriptFunction {
        private Object fromCharCode;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$fromCharCode() {
            return this.fromCharCode;
        }

        public void S$fromCharCode(Object obj) {
            this.fromCharCode = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeString.Constructor.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeString$Constructor.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:168)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            Caused by: java.lang.ArrayIndexOutOfBoundsException
            */
        Constructor() {
            /*
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeString.Constructor.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeString$Constructor.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeString.Constructor.<init>():void");
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeString$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object toString;
        private Object valueOf;
        private Object charAt;
        private Object charCodeAt;
        private Object concat;
        private Object indexOf;
        private Object lastIndexOf;
        private Object localeCompare;
        private Object match;
        private Object replace;
        private Object search;
        private Object slice;
        private Object split;
        private Object substr;
        private Object substring;
        private Object toLowerCase;
        private Object toLocaleLowerCase;
        private Object toUpperCase;
        private Object toLocaleUpperCase;
        private Object trim;
        private Object trimLeft;
        private Object trimRight;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        public Object G$valueOf() {
            return this.valueOf;
        }

        public void S$valueOf(Object obj) {
            this.valueOf = obj;
        }

        public Object G$charAt() {
            return this.charAt;
        }

        public void S$charAt(Object obj) {
            this.charAt = obj;
        }

        public Object G$charCodeAt() {
            return this.charCodeAt;
        }

        public void S$charCodeAt(Object obj) {
            this.charCodeAt = obj;
        }

        public Object G$concat() {
            return this.concat;
        }

        public void S$concat(Object obj) {
            this.concat = obj;
        }

        public Object G$indexOf() {
            return this.indexOf;
        }

        public void S$indexOf(Object obj) {
            this.indexOf = obj;
        }

        public Object G$lastIndexOf() {
            return this.lastIndexOf;
        }

        public void S$lastIndexOf(Object obj) {
            this.lastIndexOf = obj;
        }

        public Object G$localeCompare() {
            return this.localeCompare;
        }

        public void S$localeCompare(Object obj) {
            this.localeCompare = obj;
        }

        public Object G$match() {
            return this.match;
        }

        public void S$match(Object obj) {
            this.match = obj;
        }

        public Object G$replace() {
            return this.replace;
        }

        public void S$replace(Object obj) {
            this.replace = obj;
        }

        public Object G$search() {
            return this.search;
        }

        public void S$search(Object obj) {
            this.search = obj;
        }

        public Object G$slice() {
            return this.slice;
        }

        public void S$slice(Object obj) {
            this.slice = obj;
        }

        public Object G$split() {
            return this.split;
        }

        public void S$split(Object obj) {
            this.split = obj;
        }

        public Object G$substr() {
            return this.substr;
        }

        public void S$substr(Object obj) {
            this.substr = obj;
        }

        public Object G$substring() {
            return this.substring;
        }

        public void S$substring(Object obj) {
            this.substring = obj;
        }

        public Object G$toLowerCase() {
            return this.toLowerCase;
        }

        public void S$toLowerCase(Object obj) {
            this.toLowerCase = obj;
        }

        public Object G$toLocaleLowerCase() {
            return this.toLocaleLowerCase;
        }

        public void S$toLocaleLowerCase(Object obj) {
            this.toLocaleLowerCase = obj;
        }

        public Object G$toUpperCase() {
            return this.toUpperCase;
        }

        public void S$toUpperCase(Object obj) {
            this.toUpperCase = obj;
        }

        public Object G$toLocaleUpperCase() {
            return this.toLocaleUpperCase;
        }

        public void S$toLocaleUpperCase(Object obj) {
            this.toLocaleUpperCase = obj;
        }

        public Object G$trim() {
            return this.trim;
        }

        public void S$trim(Object obj) {
            this.trim = obj;
        }

        public Object G$trimLeft() {
            return this.trimLeft;
        }

        public void S$trimLeft(Object obj) {
            this.trimLeft = obj;
        }

        public Object G$trimRight() {
            return this.trimRight;
        }

        public void S$trimRight(Object obj) {
            this.trimRight = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeString.Prototype.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeString$Prototype.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:168)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            Caused by: java.lang.ArrayIndexOutOfBoundsException
            */
        Prototype() {
            /*
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeString.Prototype.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeString$Prototype.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeString.Prototype.<init>():void");
        }
    }

    /*  JADX ERROR: Failed to decode insn: 0x000D: CONST
        jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
        	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        */
    public static void $clinit$() {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = r0
            r2 = 1
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "length"
            r3 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r4 = -1
            if (r4 <= 0) goto LB_46ed
            if (r2 < r3) goto L214
            jdk.nashorn.internal.runtime.PropertyMap r0 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r0)
            jdk.nashorn.internal.objects.NativeString.$nasgenmap$ = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeString.$clinit$():void");
    }

    /* JADX WARN: Failed to check method for inline after forced processjdk.nashorn.internal.objects.NativeString.$clinit$():void */
    static {
        $assertionsDisabled = !NativeString.class.desiredAssertionStatus();
        WRAPFILTER = findOwnMH("wrapFilter", Lookup.f31MH.type(NativeString.class, new Class[]{Object.class}));
        PROTOFILTER = findOwnMH("protoFilter", Lookup.f31MH.type(Object.class, new Class[]{Object.class}));
        $clinit$();
    }

    private NativeString(CharSequence charSequence) {
        this(charSequence, Global.instance());
    }

    NativeString(CharSequence charSequence, Global global) {
        this(charSequence, global.getStringPrototype(), $nasgenmap$);
    }

    private NativeString(CharSequence charSequence, ScriptObject scriptObject, PropertyMap propertyMap) {
        super(scriptObject, propertyMap);
        if (!$assertionsDisabled) {
            if (!((charSequence instanceof String) || (charSequence instanceof ConsString))) {
                throw new AssertionError();
            }
        }
        this.value = charSequence;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String safeToString() {
        return "[String " + toString() + "]";
    }

    public String toString() {
        return getStringValue();
    }

    public boolean equals(Object obj) {
        if (obj instanceof NativeString) {
            return getStringValue().equals(((NativeString) obj).getStringValue());
        }
        return false;
    }

    public int hashCode() {
        return getStringValue().hashCode();
    }

    private String getStringValue() {
        return this.value instanceof String ? (String) this.value : this.value.toString();
    }

    private CharSequence getValue() {
        return this.value;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getLength() {
        return Integer.valueOf(this.value.length());
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findGetMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest, String str) {
        if ("length".equals(callSiteDescriptor.getNameToken(2)) && "getMethod".equals(str)) {
            return null;
        }
        return super.findGetMethod(callSiteDescriptor, linkRequest, str);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findGetIndexMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        Object receiver = linkRequest.getReceiver();
        if (callSiteDescriptor.getMethodType().returnType() == Object.class) {
            if ((receiver instanceof String) || (receiver instanceof ConsString)) {
                try {
                    return new GuardedInvocation(Lookup.f31MH.findStatic(MethodHandles.lookup(), NativeString.class, PropertyDescriptor.GET, callSiteDescriptor.getMethodType()), NashornGuards.getStringGuard());
                } catch (MethodHandleFactory.LookupException unused) {
                }
            }
        }
        return super.findGetIndexMethod(callSiteDescriptor, linkRequest);
    }

    private static Object get(Object obj, Object obj2) {
        CharSequence charSequence = JSType.toCharSequence(obj);
        Object primitive = JSType.toPrimitive(obj2, String.class);
        int arrayIndex = ArrayIndex.getArrayIndex(primitive);
        if (arrayIndex >= 0 && arrayIndex < charSequence.length()) {
            return String.valueOf(charSequence.charAt(arrayIndex));
        }
        return ((ScriptObject) Global.toObject(obj)).get(primitive);
    }

    private static Object get(Object obj, double d) {
        if (((double) ((int) d)) == d) {
            return get(obj, (int) d);
        }
        return ((ScriptObject) Global.toObject(obj)).get(d);
    }

    private static Object get(Object obj, long j) {
        CharSequence charSequence = JSType.toCharSequence(obj);
        if (j >= 0 && j < charSequence.length()) {
            return String.valueOf(charSequence.charAt((int) j));
        }
        return ((ScriptObject) Global.toObject(obj)).get(j);
    }

    private static Object get(Object obj, int i) {
        CharSequence charSequence = JSType.toCharSequence(obj);
        if (i >= 0 && i < charSequence.length()) {
            return String.valueOf(charSequence.charAt(i));
        }
        return ((ScriptObject) Global.toObject(obj)).get(i);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(Object obj) {
        Object primitive = JSType.toPrimitive(obj, String.class);
        int arrayIndex = ArrayIndex.getArrayIndex(primitive);
        if (arrayIndex >= 0 && arrayIndex < this.value.length()) {
            return String.valueOf(this.value.charAt(arrayIndex));
        }
        return super.get(primitive);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(double d) {
        if (((double) ((int) d)) == d) {
            return get((int) d);
        }
        return super.get(d);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(int i) {
        if (i >= 0 && i < this.value.length()) {
            return String.valueOf(this.value.charAt(i));
        }
        return super.get(i);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(Object obj, int i) {
        return JSType.toInt32MaybeOptimistic(get(obj), i);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(double d, int i) {
        return JSType.toInt32MaybeOptimistic(get(d), i);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(int i, int i2) {
        return JSType.toInt32MaybeOptimistic(get(i), i2);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(Object obj, int i) {
        return JSType.toNumberMaybeOptimistic(get(obj), i);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(double d, int i) {
        return JSType.toNumberMaybeOptimistic(get(d), i);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(int i, int i2) {
        return JSType.toNumberMaybeOptimistic(get(i), i2);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(Object obj) {
        Object primitive = JSType.toPrimitive(obj, String.class);
        return isValidStringIndex(ArrayIndex.getArrayIndex(primitive)) || super.has(primitive);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(int i) {
        return isValidStringIndex(i) || super.has(i);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(double d) {
        return isValidStringIndex(ArrayIndex.getArrayIndex(d)) || super.has(d);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(Object obj) {
        Object primitive = JSType.toPrimitive(obj, String.class);
        return isValidStringIndex(ArrayIndex.getArrayIndex(primitive)) || super.hasOwnProperty(primitive);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(int i) {
        return isValidStringIndex(i) || super.hasOwnProperty(i);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean hasOwnProperty(double d) {
        return isValidStringIndex(ArrayIndex.getArrayIndex(d)) || super.hasOwnProperty(d);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(int i, boolean z) {
        if (checkDeleteIndex(i, z)) {
            return false;
        }
        return super.delete(i, z);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(double d, boolean z) {
        if (checkDeleteIndex(ArrayIndex.getArrayIndex(d), z)) {
            return false;
        }
        return super.delete(d, z);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(Object obj, boolean z) {
        Object primitive = JSType.toPrimitive(obj, String.class);
        if (checkDeleteIndex(ArrayIndex.getArrayIndex(primitive), z)) {
            return false;
        }
        return super.delete(primitive, z);
    }

    private boolean checkDeleteIndex(int i, boolean z) {
        if (isValidStringIndex(i)) {
            if (z) {
                throw ECMAErrors.typeError("cant.delete.property", new String[]{Integer.toString(i), ScriptRuntime.safeToString(this)});
            }
            return true;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getOwnPropertyDescriptor(String str) {
        int arrayIndex = ArrayIndex.getArrayIndex(str);
        if (arrayIndex >= 0 && arrayIndex < this.value.length()) {
            return Global.instance().newDataDescriptor(String.valueOf(this.value.charAt(arrayIndex)), false, true, false);
        }
        return super.getOwnPropertyDescriptor(str);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected String[] getOwnKeys(boolean z, Set set) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.value.length(); i++) {
            arrayList.add(JSType.toString(i));
        }
        arrayList.addAll(Arrays.asList(super.getOwnKeys(z, set)));
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static Object length(Object obj) {
        return Integer.valueOf(getCharSequence(obj).length());
    }

    public static String fromCharCode(Object obj, Object[] objArr) {
        char[] cArr = new char[objArr.length];
        int i = 0;
        for (Object obj2 : objArr) {
            int i2 = i;
            i++;
            cArr[i2] = (char) JSType.toUint16(obj2);
        }
        return new String(cArr);
    }

    public static Object fromCharCode(Object obj, Object obj2) {
        if (obj2 instanceof Integer) {
            return fromCharCode(obj, ((Integer) obj2).intValue());
        }
        return Character.toString((char) JSType.toUint16(obj2));
    }

    public static String fromCharCode(Object obj, int i) {
        return Character.toString((char) (i & CharCompanionObject.MAX_VALUE));
    }

    public static Object fromCharCode(Object obj, int i, int i2) {
        return Character.toString((char) (i & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (i2 & CharCompanionObject.MAX_VALUE));
    }

    public static Object fromCharCode(Object obj, int i, int i2, int i3) {
        return Character.toString((char) (i & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (i2 & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (i3 & CharCompanionObject.MAX_VALUE));
    }

    public static String fromCharCode(Object obj, int i, int i2, int i3, int i4) {
        return Character.toString((char) (i & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (i2 & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (i3 & CharCompanionObject.MAX_VALUE)) + Character.toString((char) (i4 & CharCompanionObject.MAX_VALUE));
    }

    public static String fromCharCode(Object obj, double d) {
        return Character.toString((char) JSType.toUint16(d));
    }

    public static String toString(Object obj) {
        return getString(obj);
    }

    public static String valueOf(Object obj) {
        return getString(obj);
    }

    public static String charAt(Object obj, Object obj2) {
        return charAtImpl(checkObjectToString(obj), JSType.toInteger(obj2));
    }

    public static String charAt(Object obj, double d) {
        return charAt(obj, (int) d);
    }

    public static String charAt(Object obj, int i) {
        return charAtImpl(checkObjectToString(obj), i);
    }

    private static String charAtImpl(String str, int i) {
        return (i < 0 || i >= str.length()) ? "" : String.valueOf(str.charAt(i));
    }

    private static int getValidChar(Object obj, int i) {
        try {
            return ((CharSequence) obj).charAt(i);
        } catch (IndexOutOfBoundsException unused) {
            throw new ClassCastException();
        }
    }

    public static double charCodeAt(Object obj, Object obj2) {
        String strCheckObjectToString = checkObjectToString(obj);
        int integer = JSType.toInteger(obj2);
        if (integer < 0 || integer >= strCheckObjectToString.length()) {
            return Double.NaN;
        }
        return strCheckObjectToString.charAt(integer);
    }

    public static int charCodeAt(Object obj, double d) {
        return charCodeAt(obj, (int) d);
    }

    public static int charCodeAt(Object obj, long j) {
        return charCodeAt(obj, (int) j);
    }

    public static int charCodeAt(Object obj, int i) {
        return getValidChar(obj, i);
    }

    public static Object concat(Object obj, Object[] objArr) {
        CharSequence charSequenceCheckObjectToString = checkObjectToString(obj);
        if (objArr != null) {
            for (Object obj2 : objArr) {
                charSequenceCheckObjectToString = new ConsString(charSequenceCheckObjectToString, JSType.toCharSequence(obj2));
            }
        }
        return charSequenceCheckObjectToString;
    }

    public static int indexOf(Object obj, Object obj2, Object obj3) {
        return checkObjectToString(obj).indexOf(JSType.toString(obj2), JSType.toInteger(obj3));
    }

    public static int indexOf(Object obj, Object obj2) {
        return indexOf(obj, obj2, 0);
    }

    public static int indexOf(Object obj, Object obj2, double d) {
        return indexOf(obj, obj2, (int) d);
    }

    public static int indexOf(Object obj, Object obj2, int i) {
        return checkObjectToString(obj).indexOf(JSType.toString(obj2), i);
    }

    public static int lastIndexOf(Object obj, Object obj2, Object obj3) {
        int i;
        String strCheckObjectToString = checkObjectToString(obj);
        String string = JSType.toString(obj2);
        int length = strCheckObjectToString.length();
        if (obj3 == ScriptRuntime.UNDEFINED) {
            i = length;
        } else {
            double number = JSType.toNumber(obj3);
            i = Double.isNaN(number) ? length : (int) number;
            if (i < 0) {
                i = 0;
            } else if (i > length) {
                i = length;
            }
        }
        return strCheckObjectToString.lastIndexOf(string, i);
    }

    public static double localeCompare(Object obj, Object obj2) {
        String strCheckObjectToString = checkObjectToString(obj);
        Collator collator = Collator.getInstance(Global.getEnv()._locale);
        collator.setStrength(3);
        collator.setDecomposition(1);
        return collator.compare(strCheckObjectToString, JSType.toString(obj2));
    }

    public static ScriptObject match(Object obj, Object obj2) {
        NativeRegExp regExp;
        int i;
        String strCheckObjectToString = checkObjectToString(obj);
        if (obj2 == ScriptRuntime.UNDEFINED) {
            regExp = new NativeRegExp("");
        } else {
            regExp = Global.toRegExp(obj2);
        }
        if (!regExp.getGlobal()) {
            return regExp.exec(strCheckObjectToString);
        }
        regExp.setLastIndex(0);
        int i2 = 0;
        ArrayList arrayList = new ArrayList();
        while (true) {
            NativeRegExpExecResult nativeRegExpExecResultExec = regExp.exec(strCheckObjectToString);
            if (nativeRegExpExecResultExec == null) {
                break;
            }
            int lastIndex = regExp.getLastIndex();
            if (lastIndex == i2) {
                regExp.setLastIndex(lastIndex + 1);
                i = lastIndex + 1;
            } else {
                i = lastIndex;
            }
            i2 = i;
            arrayList.add(nativeRegExpExecResultExec.get(0));
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new NativeArray(arrayList.toArray());
    }

    public static String replace(Object obj, Object obj2, Object obj3) {
        NativeRegExp nativeRegExpFlatRegExp;
        String strCheckObjectToString = checkObjectToString(obj);
        if (obj2 instanceof NativeRegExp) {
            nativeRegExpFlatRegExp = (NativeRegExp) obj2;
        } else {
            nativeRegExpFlatRegExp = NativeRegExp.flatRegExp(JSType.toString(obj2));
        }
        if (Bootstrap.isCallable(obj3)) {
            return nativeRegExpFlatRegExp.replace(strCheckObjectToString, "", obj3);
        }
        return nativeRegExpFlatRegExp.replace(strCheckObjectToString, JSType.toString(obj3), null);
    }

    public static int search(Object obj, Object obj2) {
        return Global.toRegExp(obj2 == ScriptRuntime.UNDEFINED ? "" : obj2).search(checkObjectToString(obj));
    }

    public static String slice(Object obj, Object obj2, Object obj3) {
        String strCheckObjectToString = checkObjectToString(obj);
        if (obj3 == ScriptRuntime.UNDEFINED) {
            return slice((Object) strCheckObjectToString, JSType.toInteger(obj2));
        }
        return slice((Object) strCheckObjectToString, JSType.toInteger(obj2), JSType.toInteger(obj3));
    }

    public static String slice(Object obj, int i) {
        String strCheckObjectToString = checkObjectToString(obj);
        return strCheckObjectToString.substring(i < 0 ? Math.max(strCheckObjectToString.length() + i, 0) : Math.min(i, strCheckObjectToString.length()));
    }

    public static String slice(Object obj, double d) {
        return slice(obj, (int) d);
    }

    public static String slice(Object obj, int i, int i2) {
        String strCheckObjectToString = checkObjectToString(obj);
        int length = strCheckObjectToString.length();
        int iMax = i < 0 ? Math.max(length + i, 0) : Math.min(i, length);
        int iMax2 = i2 < 0 ? Math.max(length + i2, 0) : Math.min(i2, length);
        return strCheckObjectToString.substring(Math.min(iMax, iMax2), iMax2);
    }

    public static String slice(Object obj, double d, double d2) {
        return slice(obj, (int) d, (int) d2);
    }

    public static ScriptObject split(Object obj, Object obj2, Object obj3) {
        String strCheckObjectToString = checkObjectToString(obj);
        long uint32 = obj3 == ScriptRuntime.UNDEFINED ? JSType.MAX_UINT : JSType.toUint32(obj3);
        if (obj2 == ScriptRuntime.UNDEFINED) {
            return uint32 == 0 ? new NativeArray() : new NativeArray(new Object[]{strCheckObjectToString});
        }
        if (obj2 instanceof NativeRegExp) {
            return ((NativeRegExp) obj2).split(strCheckObjectToString, uint32);
        }
        return splitString(strCheckObjectToString, JSType.toString(obj2), uint32);
    }

    private static ScriptObject splitString(String str, String str2, long j) {
        int iIndexOf;
        if (str2.isEmpty()) {
            int iMin = (int) Math.min(str.length(), j);
            Object[] objArr = new Object[iMin];
            for (int i = 0; i < iMin; i++) {
                objArr[i] = String.valueOf(str.charAt(i));
            }
            return new NativeArray(objArr);
        }
        LinkedList linkedList = new LinkedList();
        int length = str.length();
        int length2 = str2.length();
        int i2 = 0;
        int i3 = 0;
        while (i2 < length && i3 < j && (iIndexOf = str.indexOf(str2, i2)) != -1) {
            linkedList.add(str.substring(i2, iIndexOf));
            i3++;
            i2 = iIndexOf + length2;
        }
        if (i2 <= length && i3 < j) {
            linkedList.add(str.substring(i2));
        }
        return new NativeArray(linkedList.toArray());
    }

    public static String substr(Object obj, Object obj2, Object obj3) {
        String string = JSType.toString(obj);
        int length = string.length();
        int integer = JSType.toInteger(obj2);
        if (integer < 0) {
            integer = Math.max(integer + length, 0);
        }
        int iMin = Math.min(Math.max(obj3 == ScriptRuntime.UNDEFINED ? Integer.MAX_VALUE : JSType.toInteger(obj3), 0), length - integer);
        return iMin <= 0 ? "" : string.substring(integer, integer + iMin);
    }

    public static String substring(Object obj, Object obj2, Object obj3) {
        String strCheckObjectToString = checkObjectToString(obj);
        if (obj3 == ScriptRuntime.UNDEFINED) {
            return substring((Object) strCheckObjectToString, JSType.toInteger(obj2));
        }
        return substring((Object) strCheckObjectToString, JSType.toInteger(obj2), JSType.toInteger(obj3));
    }

    public static String substring(Object obj, int i) {
        String strCheckObjectToString = checkObjectToString(obj);
        if (i < 0) {
            return strCheckObjectToString;
        }
        if (i >= strCheckObjectToString.length()) {
            return "";
        }
        return strCheckObjectToString.substring(i);
    }

    public static String substring(Object obj, double d) {
        return substring(obj, (int) d);
    }

    public static String substring(Object obj, int i, int i2) {
        String strCheckObjectToString = checkObjectToString(obj);
        int length = strCheckObjectToString.length();
        int i3 = i < 0 ? 0 : i > length ? length : i;
        int i4 = i2 < 0 ? 0 : i2 > length ? length : i2;
        if (i3 < i4) {
            return strCheckObjectToString.substring(i3, i4);
        }
        return strCheckObjectToString.substring(i4, i3);
    }

    public static String substring(Object obj, double d, double d2) {
        return substring(obj, (int) d, (int) d2);
    }

    public static String toLowerCase(Object obj) {
        return checkObjectToString(obj).toLowerCase(Locale.ROOT);
    }

    public static String toLocaleLowerCase(Object obj) {
        return checkObjectToString(obj).toLowerCase(Global.getEnv()._locale);
    }

    public static String toUpperCase(Object obj) {
        return checkObjectToString(obj).toUpperCase(Locale.ROOT);
    }

    public static String toLocaleUpperCase(Object obj) {
        return checkObjectToString(obj).toUpperCase(Global.getEnv()._locale);
    }

    public static String trim(Object obj) {
        String strCheckObjectToString = checkObjectToString(obj);
        int i = 0;
        int length = strCheckObjectToString.length() - 1;
        while (i <= length && ScriptRuntime.isJSWhitespace(strCheckObjectToString.charAt(i))) {
            i++;
        }
        while (length > i && ScriptRuntime.isJSWhitespace(strCheckObjectToString.charAt(length))) {
            length--;
        }
        return strCheckObjectToString.substring(i, length + 1);
    }

    public static String trimLeft(Object obj) {
        String strCheckObjectToString = checkObjectToString(obj);
        int i = 0;
        int length = strCheckObjectToString.length() - 1;
        while (i <= length && ScriptRuntime.isJSWhitespace(strCheckObjectToString.charAt(i))) {
            i++;
        }
        return strCheckObjectToString.substring(i, length + 1);
    }

    public static String trimRight(Object obj) {
        String strCheckObjectToString = checkObjectToString(obj);
        int length = strCheckObjectToString.length() - 1;
        while (length >= 0 && ScriptRuntime.isJSWhitespace(strCheckObjectToString.charAt(length))) {
            length--;
        }
        return strCheckObjectToString.substring(0, length + 1);
    }

    private static ScriptObject newObj(CharSequence charSequence) {
        return new NativeString(charSequence);
    }

    public static Object constructor(boolean z, Object obj, Object[] objArr) {
        CharSequence charSequence = objArr.length > 0 ? JSType.toCharSequence(objArr[0]) : "";
        return z ? newObj(charSequence) : charSequence.toString();
    }

    public static Object constructor(boolean z, Object obj) {
        return z ? newObj("") : "";
    }

    public static Object constructor(boolean z, Object obj, Object obj2) {
        CharSequence charSequence = JSType.toCharSequence(obj2);
        return z ? newObj(charSequence) : charSequence.toString();
    }

    public static Object constructor(boolean z, Object obj, int i) {
        String string = Integer.toString(i);
        return z ? newObj(string) : string;
    }

    public static Object constructor(boolean z, Object obj, long j) {
        String string = Long.toString(j);
        return z ? newObj(string) : string;
    }

    public static Object constructor(boolean z, Object obj, double d) {
        String string = JSType.toString(d);
        return z ? newObj(string) : string;
    }

    public static Object constructor(boolean z, Object obj, boolean z2) {
        String string = Boolean.toString(z2);
        return z ? newObj(string) : string;
    }

    public static GuardedInvocation lookupPrimitive(LinkRequest linkRequest, Object obj) {
        return PrimitiveLookup.lookupPrimitive(linkRequest, NashornGuards.getStringGuard(), new NativeString((CharSequence) obj), WRAPFILTER, PROTOFILTER);
    }

    private static NativeString wrapFilter(Object obj) {
        return new NativeString((CharSequence) obj);
    }

    private static Object protoFilter(Object obj) {
        return Global.instance().getStringPrototype();
    }

    private static CharSequence getCharSequence(Object obj) {
        if ((obj instanceof String) || (obj instanceof ConsString)) {
            return (CharSequence) obj;
        }
        if (obj instanceof NativeString) {
            return ((NativeString) obj).getValue();
        }
        if (obj != null && obj == Global.instance().getStringPrototype()) {
            return "";
        }
        throw ECMAErrors.typeError("not.a.string", new String[]{ScriptRuntime.safeToString(obj)});
    }

    private static String getString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof ConsString) {
            return obj.toString();
        }
        if (obj instanceof NativeString) {
            return ((NativeString) obj).getStringValue();
        }
        if (obj != null && obj == Global.instance().getStringPrototype()) {
            return "";
        }
        throw ECMAErrors.typeError("not.a.string", new String[]{ScriptRuntime.safeToString(obj)});
    }

    private static String checkObjectToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof ConsString) {
            return obj.toString();
        }
        Global.checkObjectCoercible(obj);
        return JSType.toString(obj);
    }

    private boolean isValidStringIndex(int i) {
        return i >= 0 && i < this.value.length();
    }

    private static MethodHandle findOwnMH(String str, MethodType methodType) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), NativeString.class, str, methodType);
    }

    @Override // jdk.nashorn.internal.runtime.OptimisticBuiltins
    public SpecializedFunction.LinkLogic getLinkLogic(Class cls) {
        if (cls != CharCodeAtLinkLogic.class) {
            return null;
        }
        return CharCodeAtLinkLogic.INSTANCE;
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeString$CharCodeAtLinkLogic.class */
    private static final class CharCodeAtLinkLogic extends SpecializedFunction.LinkLogic {
        private static final CharCodeAtLinkLogic INSTANCE = new CharCodeAtLinkLogic();

        private CharCodeAtLinkLogic() {
        }

        @Override // jdk.nashorn.internal.objects.annotations.SpecializedFunction.LinkLogic
        public boolean canLink(Object obj, CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
            try {
                CharSequence charSequence = (CharSequence) obj;
                int integer = JSType.toInteger(linkRequest.getArguments()[2]);
                if (integer >= 0) {
                    if (integer < charSequence.length()) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException | IndexOutOfBoundsException unused) {
                return false;
            }
        }
    }
}
