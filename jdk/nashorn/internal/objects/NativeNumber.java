package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.NashornGuards;
import jdk.nashorn.internal.runtime.linker.PrimitiveLookup;
import jdk.nashorn.tools.Shell;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeNumber.class */
public final class NativeNumber extends ScriptObject {
    static final MethodHandle WRAPFILTER;
    private static final MethodHandle PROTOFILTER;
    private final double value;
    private static PropertyMap $nasgenmap$;
    static final boolean $assertionsDisabled;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeNumber$Constructor.class */
    final class Constructor extends ScriptFunction {
        private static final PropertyMap $nasgenmap$ = null;

        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        Constructor() {
            /*
                r6 = this;
                r0 = r6
                java.lang.String r1 = "Number"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r8 = r1
                r1 = 0
                r-3.<init>(r-2, r-1, r0, r1)
                r-3 = r6
                jdk.nashorn.internal.objects.NativeNumber$Prototype r-2 = new jdk.nashorn.internal.objects.NativeNumber$Prototype
                r-1 = r-2
                r-1.<init>()
                r-1 = r-2
                r0 = r6
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-1, r0)
                r-3.setPrototype(r-2)
                r-3 = r6
                r-2 = 1
                r-3.setArity(r-2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeNumber.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeNumber$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object toFixed;
        private Object toExponential;
        private Object toPrecision;
        private Object toString;
        private Object toLocaleString;
        private Object valueOf;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$toFixed() {
            return this.toFixed;
        }

        public void S$toFixed(Object obj) {
            this.toFixed = obj;
        }

        public Object G$toExponential() {
            return this.toExponential;
        }

        public void S$toExponential(Object obj) {
            this.toExponential = obj;
        }

        public Object G$toPrecision() {
            return this.toPrecision;
        }

        public void S$toPrecision(Object obj) {
            this.toPrecision = obj;
        }

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        public Object G$toLocaleString() {
            return this.toLocaleString;
        }

        public void S$toLocaleString(Object obj) {
            this.toLocaleString = obj;
        }

        public Object G$valueOf() {
            return this.valueOf;
        }

        public void S$valueOf(Object obj) {
            this.valueOf = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeNumber$Prototype.class
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
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeNumber$Prototype.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeNumber.Prototype.<init>():void");
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        $assertionsDisabled = !NativeNumber.class.desiredAssertionStatus();
        WRAPFILTER = findOwnMH("wrapFilter", Lookup.f31MH.type(NativeNumber.class, new Class[]{Object.class}));
        PROTOFILTER = findOwnMH("protoFilter", Lookup.f31MH.type(Object.class, new Class[]{Object.class}));
        $clinit$();
    }

    private NativeNumber(double d, ScriptObject scriptObject, PropertyMap propertyMap) {
        super(scriptObject, propertyMap);
        this.value = d;
    }

    NativeNumber(double d, Global global) {
        this(d, global.getNumberPrototype(), $nasgenmap$);
    }

    private NativeNumber(double d) {
        this(d, Global.instance());
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String safeToString() {
        return "[Number " + toString() + "]";
    }

    public String toString() {
        return Double.toString(getValue());
    }

    public double getValue() {
        return doubleValue();
    }

    public double doubleValue() {
        return this.value;
    }

    public static Object constructor(boolean z, Object obj, Object[] objArr) {
        double number = objArr.length > 0 ? JSType.toNumber(objArr[0]) : 0.0d;
        return z ? new NativeNumber(number) : Double.valueOf(number);
    }

    public static String toFixed(Object obj, Object obj2) {
        return toFixed(obj, JSType.toInteger(obj2));
    }

    public static String toFixed(Object obj, int i) {
        if (i < 0 || i > 20) {
            throw ECMAErrors.rangeError("invalid.fraction.digits", new String[]{"toFixed"});
        }
        double numberValue = getNumberValue(obj);
        if (Double.isNaN(numberValue)) {
            return "NaN";
        }
        if (Math.abs(numberValue) >= 1.0E21d) {
            return JSType.toString(numberValue);
        }
        NumberFormat numberInstance = NumberFormat.getNumberInstance(Locale.US);
        numberInstance.setMinimumFractionDigits(i);
        numberInstance.setMaximumFractionDigits(i);
        numberInstance.setGroupingUsed(false);
        numberInstance.setRoundingMode(RoundingMode.HALF_UP);
        return numberInstance.format(numberValue);
    }

    public static String toExponential(Object obj, Object obj2) {
        double numberValue = getNumberValue(obj);
        boolean z = obj2 == ScriptRuntime.UNDEFINED;
        int integer = z ? 16 : JSType.toInteger(obj2);
        if (Double.isNaN(numberValue)) {
            return "NaN";
        }
        if (Double.isInfinite(numberValue)) {
            return numberValue > 0.0d ? "Infinity" : "-Infinity";
        }
        if (obj2 != ScriptRuntime.UNDEFINED && (integer < 0 || integer > 20)) {
            throw ECMAErrors.rangeError("invalid.fraction.digits", new String[]{"toExponential"});
        }
        return fixExponent(String.format(Locale.US, "%1." + integer + "e", Double.valueOf(numberValue)), z);
    }

    public static String toPrecision(Object obj, Object obj2) {
        double numberValue = getNumberValue(obj);
        if (obj2 == ScriptRuntime.UNDEFINED) {
            return JSType.toString(numberValue);
        }
        return toPrecision(numberValue, JSType.toInteger(obj2));
    }

    public static String toPrecision(Object obj, int i) {
        return toPrecision(getNumberValue(obj), i);
    }

    private static String toPrecision(double d, int i) {
        if (Double.isNaN(d)) {
            return "NaN";
        }
        if (Double.isInfinite(d)) {
            return d > 0.0d ? "Infinity" : "-Infinity";
        }
        if (i < 1 || i > 21) {
            throw ECMAErrors.rangeError("invalid.precision", new String[0]);
        }
        return (d != 0.0d || i > 1) ? fixExponent(String.format(Locale.US, "%." + i + "g", Double.valueOf(d)), false) : "0";
    }

    public static String toString(Object obj, Object obj2) {
        int integer;
        if (obj2 != ScriptRuntime.UNDEFINED && (integer = JSType.toInteger(obj2)) != 10) {
            if (integer < 2 || integer > 36) {
                throw ECMAErrors.rangeError("invalid.radix", new String[0]);
            }
            return JSType.toString(getNumberValue(obj), integer);
        }
        return JSType.toString(getNumberValue(obj));
    }

    public static String toLocaleString(Object obj) {
        return JSType.toString(getNumberValue(obj));
    }

    public static double valueOf(Object obj) {
        return getNumberValue(obj);
    }

    public static GuardedInvocation lookupPrimitive(LinkRequest linkRequest, Object obj) {
        return PrimitiveLookup.lookupPrimitive(linkRequest, NashornGuards.getNumberGuard(), new NativeNumber(((Number) obj).doubleValue()), WRAPFILTER, PROTOFILTER);
    }

    private static NativeNumber wrapFilter(Object obj) {
        return new NativeNumber(((Number) obj).doubleValue());
    }

    private static Object protoFilter(Object obj) {
        return Global.instance().getNumberPrototype();
    }

    private static double getNumberValue(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        if (obj instanceof NativeNumber) {
            return ((NativeNumber) obj).getValue();
        }
        if (obj != null && obj == Global.instance().getNumberPrototype()) {
            return 0.0d;
        }
        throw ECMAErrors.typeError("not.a.number", new String[]{ScriptRuntime.safeToString(obj)});
    }

    private static String fixExponent(String str, boolean z) {
        int iIndexOf = str.indexOf(Shell.COMPILATION_ERROR);
        if (iIndexOf < 1) {
            return str;
        }
        int i = str.charAt(iIndexOf + 2) == '0' ? 3 : 2;
        int i2 = iIndexOf;
        if (z) {
            if (!$assertionsDisabled && i2 <= 0) {
                throw new AssertionError();
            }
            char cCharAt = str.charAt(i2 - 1);
            while (true) {
                char c = cCharAt;
                if (i2 <= 1 || !(c == '0' || c == '.')) {
                    break;
                }
                i2--;
                cCharAt = str.charAt(i2 - 1);
            }
        }
        if (i2 < iIndexOf || i == 3) {
            return str.substring(0, i2) + str.substring(iIndexOf, iIndexOf + 2) + str.substring(iIndexOf + i);
        }
        return str;
    }

    private static MethodHandle findOwnMH(String str, MethodType methodType) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), NativeNumber.class, str, methodType);
    }
}
