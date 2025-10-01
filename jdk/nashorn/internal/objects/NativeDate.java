package jdk.nashorn.internal.objects;

import java.util.Collections;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import jdk.nashorn.internal.parser.DateParser;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;
import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeDate.class */
public final class NativeDate extends ScriptObject {
    private static final String INVALID_DATE = "Invalid Date";
    private static final int YEAR = 0;
    private static final int MONTH = 1;
    private static final int DAY = 2;
    private static final int HOUR = 3;
    private static final int MINUTE = 4;
    private static final int SECOND = 5;
    private static final int MILLISECOND = 6;
    private static final int FORMAT_DATE_TIME = 0;
    private static final int FORMAT_DATE = 1;
    private static final int FORMAT_TIME = 2;
    private static final int FORMAT_LOCAL_DATE_TIME = 3;
    private static final int FORMAT_LOCAL_DATE = 4;
    private static final int FORMAT_LOCAL_TIME = 5;
    private static final int hoursPerDay = 24;
    private static final int minutesPerHour = 60;
    private static final int secondsPerMinute = 60;
    private static final int msPerSecond = 1000;
    private static final int msPerMinute = 60000;
    private static final double msPerHour = 3600000.0d;
    private static final double msPerDay = 8.64E7d;
    private static int[][] firstDayInMonth;
    private static String[] weekDays;
    private static String[] months;
    private static final Object TO_ISO_STRING;
    private double time;
    private final TimeZone timezone;
    private static PropertyMap $nasgenmap$;
    static final boolean $assertionsDisabled;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeDate$Constructor.class */
    final class Constructor extends ScriptFunction {
        private Object parse;
        private Object UTC;
        private Object now;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$parse() {
            return this.parse;
        }

        public void S$parse(Object obj) {
            this.parse = obj;
        }

        public Object G$UTC() {
            return this.UTC;
        }

        public void S$UTC(Object obj) {
            this.UTC = obj;
        }

        public Object G$now() {
            return this.now;
        }

        public void S$now(Object obj) {
            this.now = obj;
        }

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
        /*  JADX ERROR: Failed to decode insn: 0x0011: CONST
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
        /*  JADX ERROR: Failed to decode insn: 0x001D: CONST
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
        /*  JADX ERROR: Failed to decode insn: 0x0027: CONST
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
        /*  JADX ERROR: Failed to decode insn: 0x0037: CONST
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
                r12 = this;
                r0 = r12
                java.lang.String r1 = "Date"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r14 = r1
                r1 = 1
                jdk.nashorn.internal.runtime.Specialization[] r1 = new jdk.nashorn.internal.runtime.Specialization[r1]
                r2 = r1
                r3 = 0
                jdk.nashorn.internal.runtime.Specialization r4 = new jdk.nashorn.internal.runtime.Specialization
                r5 = r4
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r3.<init>(r4, r5)
                r0[r1] = r2
                r-5.<init>(r-4, r-3, r-2, r-1)
                r-5 = r12
                java.lang.String r-4 = "parse"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                float r-5 = r-5 - r-4
                r-6.parse = r-5
                r-6 = r12
                java.lang.String r-5 = "UTC"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                float r-6 = r-6 - r-5
                r-5 = r-6
                r-4 = 7
                r-5.setArity(r-4)
                r-7.UTC = r-6
                r-7 = r12
                java.lang.String r-6 = "now"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                float r-7 = r-7 - r-6
                r-8.now = r-7
                r-8 = r12
                jdk.nashorn.internal.objects.NativeDate$Prototype r-7 = new jdk.nashorn.internal.objects.NativeDate$Prototype
                r-6 = r-7
                r-6.<init>()
                r-6 = r-7
                r-5 = r12
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-6, r-5)
                r-8.setPrototype(r-7)
                r-8 = r12
                r-7 = 7
                r-8.setArity(r-7)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeDate.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeDate$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object toString;
        private Object toDateString;
        private Object toTimeString;
        private Object toLocaleString;
        private Object toLocaleDateString;
        private Object toLocaleTimeString;
        private Object valueOf;
        private Object getTime;
        private Object getFullYear;
        private Object getUTCFullYear;
        private Object getYear;
        private Object getMonth;
        private Object getUTCMonth;
        private Object getDate;
        private Object getUTCDate;
        private Object getDay;
        private Object getUTCDay;
        private Object getHours;
        private Object getUTCHours;
        private Object getMinutes;
        private Object getUTCMinutes;
        private Object getSeconds;
        private Object getUTCSeconds;
        private Object getMilliseconds;
        private Object getUTCMilliseconds;
        private Object getTimezoneOffset;
        private Object setTime;
        private Object setMilliseconds;
        private Object setUTCMilliseconds;
        private Object setSeconds;
        private Object setUTCSeconds;
        private Object setMinutes;
        private Object setUTCMinutes;
        private Object setHours;
        private Object setUTCHours;
        private Object setDate;
        private Object setUTCDate;
        private Object setMonth;
        private Object setUTCMonth;
        private Object setFullYear;
        private Object setUTCFullYear;
        private Object setYear;
        private Object toUTCString;
        private Object toGMTString;
        private Object toISOString;
        private Object toJSON;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        public Object G$toDateString() {
            return this.toDateString;
        }

        public void S$toDateString(Object obj) {
            this.toDateString = obj;
        }

        public Object G$toTimeString() {
            return this.toTimeString;
        }

        public void S$toTimeString(Object obj) {
            this.toTimeString = obj;
        }

        public Object G$toLocaleString() {
            return this.toLocaleString;
        }

        public void S$toLocaleString(Object obj) {
            this.toLocaleString = obj;
        }

        public Object G$toLocaleDateString() {
            return this.toLocaleDateString;
        }

        public void S$toLocaleDateString(Object obj) {
            this.toLocaleDateString = obj;
        }

        public Object G$toLocaleTimeString() {
            return this.toLocaleTimeString;
        }

        public void S$toLocaleTimeString(Object obj) {
            this.toLocaleTimeString = obj;
        }

        public Object G$valueOf() {
            return this.valueOf;
        }

        public void S$valueOf(Object obj) {
            this.valueOf = obj;
        }

        public Object G$getTime() {
            return this.getTime;
        }

        public void S$getTime(Object obj) {
            this.getTime = obj;
        }

        public Object G$getFullYear() {
            return this.getFullYear;
        }

        public void S$getFullYear(Object obj) {
            this.getFullYear = obj;
        }

        public Object G$getUTCFullYear() {
            return this.getUTCFullYear;
        }

        public void S$getUTCFullYear(Object obj) {
            this.getUTCFullYear = obj;
        }

        public Object G$getYear() {
            return this.getYear;
        }

        public void S$getYear(Object obj) {
            this.getYear = obj;
        }

        public Object G$getMonth() {
            return this.getMonth;
        }

        public void S$getMonth(Object obj) {
            this.getMonth = obj;
        }

        public Object G$getUTCMonth() {
            return this.getUTCMonth;
        }

        public void S$getUTCMonth(Object obj) {
            this.getUTCMonth = obj;
        }

        public Object G$getDate() {
            return this.getDate;
        }

        public void S$getDate(Object obj) {
            this.getDate = obj;
        }

        public Object G$getUTCDate() {
            return this.getUTCDate;
        }

        public void S$getUTCDate(Object obj) {
            this.getUTCDate = obj;
        }

        public Object G$getDay() {
            return this.getDay;
        }

        public void S$getDay(Object obj) {
            this.getDay = obj;
        }

        public Object G$getUTCDay() {
            return this.getUTCDay;
        }

        public void S$getUTCDay(Object obj) {
            this.getUTCDay = obj;
        }

        public Object G$getHours() {
            return this.getHours;
        }

        public void S$getHours(Object obj) {
            this.getHours = obj;
        }

        public Object G$getUTCHours() {
            return this.getUTCHours;
        }

        public void S$getUTCHours(Object obj) {
            this.getUTCHours = obj;
        }

        public Object G$getMinutes() {
            return this.getMinutes;
        }

        public void S$getMinutes(Object obj) {
            this.getMinutes = obj;
        }

        public Object G$getUTCMinutes() {
            return this.getUTCMinutes;
        }

        public void S$getUTCMinutes(Object obj) {
            this.getUTCMinutes = obj;
        }

        public Object G$getSeconds() {
            return this.getSeconds;
        }

        public void S$getSeconds(Object obj) {
            this.getSeconds = obj;
        }

        public Object G$getUTCSeconds() {
            return this.getUTCSeconds;
        }

        public void S$getUTCSeconds(Object obj) {
            this.getUTCSeconds = obj;
        }

        public Object G$getMilliseconds() {
            return this.getMilliseconds;
        }

        public void S$getMilliseconds(Object obj) {
            this.getMilliseconds = obj;
        }

        public Object G$getUTCMilliseconds() {
            return this.getUTCMilliseconds;
        }

        public void S$getUTCMilliseconds(Object obj) {
            this.getUTCMilliseconds = obj;
        }

        public Object G$getTimezoneOffset() {
            return this.getTimezoneOffset;
        }

        public void S$getTimezoneOffset(Object obj) {
            this.getTimezoneOffset = obj;
        }

        public Object G$setTime() {
            return this.setTime;
        }

        public void S$setTime(Object obj) {
            this.setTime = obj;
        }

        public Object G$setMilliseconds() {
            return this.setMilliseconds;
        }

        public void S$setMilliseconds(Object obj) {
            this.setMilliseconds = obj;
        }

        public Object G$setUTCMilliseconds() {
            return this.setUTCMilliseconds;
        }

        public void S$setUTCMilliseconds(Object obj) {
            this.setUTCMilliseconds = obj;
        }

        public Object G$setSeconds() {
            return this.setSeconds;
        }

        public void S$setSeconds(Object obj) {
            this.setSeconds = obj;
        }

        public Object G$setUTCSeconds() {
            return this.setUTCSeconds;
        }

        public void S$setUTCSeconds(Object obj) {
            this.setUTCSeconds = obj;
        }

        public Object G$setMinutes() {
            return this.setMinutes;
        }

        public void S$setMinutes(Object obj) {
            this.setMinutes = obj;
        }

        public Object G$setUTCMinutes() {
            return this.setUTCMinutes;
        }

        public void S$setUTCMinutes(Object obj) {
            this.setUTCMinutes = obj;
        }

        public Object G$setHours() {
            return this.setHours;
        }

        public void S$setHours(Object obj) {
            this.setHours = obj;
        }

        public Object G$setUTCHours() {
            return this.setUTCHours;
        }

        public void S$setUTCHours(Object obj) {
            this.setUTCHours = obj;
        }

        public Object G$setDate() {
            return this.setDate;
        }

        public void S$setDate(Object obj) {
            this.setDate = obj;
        }

        public Object G$setUTCDate() {
            return this.setUTCDate;
        }

        public void S$setUTCDate(Object obj) {
            this.setUTCDate = obj;
        }

        public Object G$setMonth() {
            return this.setMonth;
        }

        public void S$setMonth(Object obj) {
            this.setMonth = obj;
        }

        public Object G$setUTCMonth() {
            return this.setUTCMonth;
        }

        public void S$setUTCMonth(Object obj) {
            this.setUTCMonth = obj;
        }

        public Object G$setFullYear() {
            return this.setFullYear;
        }

        public void S$setFullYear(Object obj) {
            this.setFullYear = obj;
        }

        public Object G$setUTCFullYear() {
            return this.setUTCFullYear;
        }

        public void S$setUTCFullYear(Object obj) {
            this.setUTCFullYear = obj;
        }

        public Object G$setYear() {
            return this.setYear;
        }

        public void S$setYear(Object obj) {
            this.setYear = obj;
        }

        public Object G$toUTCString() {
            return this.toUTCString;
        }

        public void S$toUTCString(Object obj) {
            this.toUTCString = obj;
        }

        public Object G$toGMTString() {
            return this.toGMTString;
        }

        public void S$toGMTString(Object obj) {
            this.toGMTString = obj;
        }

        public Object G$toISOString() {
            return this.toISOString;
        }

        public void S$toISOString(Object obj) {
            this.toISOString = obj;
        }

        public Object G$toJSON() {
            return this.toJSON;
        }

        public void S$toJSON(Object obj) {
            this.toJSON = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeDate.Prototype.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeDate$Prototype.class
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
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeDate.Prototype.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeDate$Prototype.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeDate.Prototype.<init>():void");
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [int[], int[][]] */
    static {
        $assertionsDisabled = !NativeDate.class.desiredAssertionStatus();
        firstDayInMonth = new int[]{new int[]{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334}, new int[]{0, 31, 60, 91, 121, SyslogAppender.LOG_LOCAL3, Typography.paragraph, 213, 244, 274, 305, 335}};
        weekDays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        TO_ISO_STRING = new Object();
        $clinit$();
    }

    private static InvokeByName getTO_ISO_STRING() {
        return Global.instance().getInvokeByName(TO_ISO_STRING, new Callable() { // from class: jdk.nashorn.internal.objects.NativeDate.1
            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public InvokeByName call() {
                return new InvokeByName("toISOString", ScriptObject.class, Object.class, new Class[]{Object.class});
            }
        });
    }

    private NativeDate(double d, ScriptObject scriptObject, PropertyMap propertyMap) {
        super(scriptObject, propertyMap);
        ScriptEnvironment env = Global.getEnv();
        this.time = d;
        this.timezone = env._timezone;
    }

    NativeDate(double d, ScriptObject scriptObject) {
        this(d, scriptObject, $nasgenmap$);
    }

    NativeDate(double d, Global global) {
        this(d, global.getDatePrototype(), $nasgenmap$);
    }

    private NativeDate(double d) {
        this(d, Global.instance());
    }

    private NativeDate() {
        this(System.currentTimeMillis());
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getDefaultValue(Class cls) {
        return super.getDefaultValue(cls == null ? String.class : cls);
    }

    public static Object construct(boolean z, Object obj) {
        NativeDate nativeDate = new NativeDate();
        return z ? nativeDate : toStringImpl(nativeDate, 0);
    }

    public static Object construct(boolean z, Object obj, Object[] objArr) {
        NativeDate nativeDate;
        double dTimeClip;
        if (!z) {
            return toStringImpl(new NativeDate(), 0);
        }
        switch (objArr.length) {
            case 0:
                nativeDate = new NativeDate();
                break;
            case 1:
                Object primitive = JSType.toPrimitive(objArr[0]);
                if ((primitive instanceof String) || (primitive instanceof ConsString)) {
                    dTimeClip = parseDateString(primitive.toString());
                } else {
                    dTimeClip = timeClip(JSType.toNumber(objArr[0]));
                }
                nativeDate = new NativeDate(dTimeClip);
                break;
            default:
                nativeDate = new NativeDate(0.0d);
                double[] dArrConvertCtorArgs = convertCtorArgs(objArr);
                if (dArrConvertCtorArgs == null) {
                    nativeDate.setTime(Double.NaN);
                    break;
                } else {
                    nativeDate.setTime(timeClip(utc(makeDate(dArrConvertCtorArgs), nativeDate.getTimeZone())));
                    break;
                }
        }
        return nativeDate;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public String safeToString() {
        return "[Date " + (isValidDate() ? toISOStringImpl(this) : INVALID_DATE) + "]";
    }

    public String toString() {
        return isValidDate() ? toString(this).toString() : INVALID_DATE;
    }

    public static double parse(Object obj, Object obj2) {
        return parseDateString(JSType.toString(obj2));
    }

    public static double UTC(Object obj, Object[] objArr) {
        NativeDate nativeDate = new NativeDate(0.0d);
        double[] dArrConvertCtorArgs = convertCtorArgs(objArr);
        double dTimeClip = dArrConvertCtorArgs == null ? Double.NaN : timeClip(makeDate(dArrConvertCtorArgs));
        nativeDate.setTime(dTimeClip);
        return dTimeClip;
    }

    public static double now(Object obj) {
        return System.currentTimeMillis();
    }

    public static String toString(Object obj) {
        return toStringImpl(obj, 0);
    }

    public static String toDateString(Object obj) {
        return toStringImpl(obj, 1);
    }

    public static String toTimeString(Object obj) {
        return toStringImpl(obj, 2);
    }

    public static String toLocaleString(Object obj) {
        return toStringImpl(obj, 3);
    }

    public static String toLocaleDateString(Object obj) {
        return toStringImpl(obj, 4);
    }

    public static String toLocaleTimeString(Object obj) {
        return toStringImpl(obj, 5);
    }

    public static double valueOf(Object obj) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate != null) {
            return nativeDate.getTime();
        }
        return Double.NaN;
    }

    public static double getTime(Object obj) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate != null) {
            return nativeDate.getTime();
        }
        return Double.NaN;
    }

    public static Object getFullYear(Object obj) {
        return Double.valueOf(getField(obj, 0));
    }

    public static double getUTCFullYear(Object obj) {
        return getUTCField(obj, 0);
    }

    public static double getYear(Object obj) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate == null || !nativeDate.isValidDate()) {
            return Double.NaN;
        }
        return yearFromTime(nativeDate.getLocalTime()) - 1900;
    }

    public static double getMonth(Object obj) {
        return getField(obj, 1);
    }

    public static double getUTCMonth(Object obj) {
        return getUTCField(obj, 1);
    }

    public static double getDate(Object obj) {
        return getField(obj, 2);
    }

    public static double getUTCDate(Object obj) {
        return getUTCField(obj, 2);
    }

    public static double getDay(Object obj) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate == null || !nativeDate.isValidDate()) {
            return Double.NaN;
        }
        return weekDay(nativeDate.getLocalTime());
    }

    public static double getUTCDay(Object obj) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate == null || !nativeDate.isValidDate()) {
            return Double.NaN;
        }
        return weekDay(nativeDate.getTime());
    }

    public static double getHours(Object obj) {
        return getField(obj, 3);
    }

    public static double getUTCHours(Object obj) {
        return getUTCField(obj, 3);
    }

    public static double getMinutes(Object obj) {
        return getField(obj, 4);
    }

    public static double getUTCMinutes(Object obj) {
        return getUTCField(obj, 4);
    }

    public static double getSeconds(Object obj) {
        return getField(obj, 5);
    }

    public static double getUTCSeconds(Object obj) {
        return getUTCField(obj, 5);
    }

    public static double getMilliseconds(Object obj) {
        return getField(obj, 6);
    }

    public static double getUTCMilliseconds(Object obj) {
        return getUTCField(obj, 6);
    }

    public static double getTimezoneOffset(Object obj) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate != null && nativeDate.isValidDate()) {
            return (-nativeDate.getTimeZone().getOffset((long) nativeDate.getTime())) / 60000;
        }
        return Double.NaN;
    }

    public static double setTime(Object obj, Object obj2) {
        NativeDate nativeDate = getNativeDate(obj);
        double dTimeClip = timeClip(JSType.toNumber(obj2));
        nativeDate.setTime(dTimeClip);
        return dTimeClip;
    }

    public static double setMilliseconds(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 6, objArr, true);
        return nativeDate.getTime();
    }

    public static double setUTCMilliseconds(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 6, objArr, false);
        return nativeDate.getTime();
    }

    public static double setSeconds(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 5, objArr, true);
        return nativeDate.getTime();
    }

    public static double setUTCSeconds(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 5, objArr, false);
        return nativeDate.getTime();
    }

    public static double setMinutes(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 4, objArr, true);
        return nativeDate.getTime();
    }

    public static double setUTCMinutes(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 4, objArr, false);
        return nativeDate.getTime();
    }

    public static double setHours(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 3, objArr, true);
        return nativeDate.getTime();
    }

    public static double setUTCHours(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 3, objArr, false);
        return nativeDate.getTime();
    }

    public static double setDate(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 2, objArr, true);
        return nativeDate.getTime();
    }

    public static double setUTCDate(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 2, objArr, false);
        return nativeDate.getTime();
    }

    public static double setMonth(Object obj, Object[] objArr) {
        NativeDate nativeDate = getNativeDate(obj);
        setFields(nativeDate, 1, objArr, true);
        return nativeDate.getTime();
    }

    public static double setUTCMonth(Object obj, Object[] objArr) {
        NativeDate nativeDateEnsureNativeDate = ensureNativeDate(obj);
        setFields(nativeDateEnsureNativeDate, 1, objArr, false);
        return nativeDateEnsureNativeDate.getTime();
    }

    public static double setFullYear(Object obj, Object[] objArr) {
        NativeDate nativeDateEnsureNativeDate = ensureNativeDate(obj);
        if (nativeDateEnsureNativeDate.isValidDate()) {
            setFields(nativeDateEnsureNativeDate, 0, objArr, true);
        } else {
            double[] dArrConvertArgs = convertArgs(objArr, 0.0d, 0, 0, 3);
            if (dArrConvertArgs != null) {
                nativeDateEnsureNativeDate.setTime(timeClip(utc((makeDay(dArrConvertArgs[0], dArrConvertArgs[1], dArrConvertArgs[2]) * msPerDay) + 0.0d, nativeDateEnsureNativeDate.getTimeZone())));
            } else {
                nativeDateEnsureNativeDate.setTime(Double.NaN);
            }
        }
        return nativeDateEnsureNativeDate.getTime();
    }

    public static double setUTCFullYear(Object obj, Object[] objArr) {
        NativeDate nativeDateEnsureNativeDate = ensureNativeDate(obj);
        if (nativeDateEnsureNativeDate.isValidDate()) {
            setFields(nativeDateEnsureNativeDate, 0, objArr, false);
        } else {
            double[] dArrConvertArgs = convertArgs(objArr, 0.0d, 0, 0, 3);
            nativeDateEnsureNativeDate.setTime(timeClip((makeDay(dArrConvertArgs[0], dArrConvertArgs[1], dArrConvertArgs[2]) * msPerDay) + 0.0d));
        }
        return nativeDateEnsureNativeDate.getTime();
    }

    public static double setYear(Object obj, Object obj2) {
        NativeDate nativeDate = getNativeDate(obj);
        if (Double.isNaN(nativeDate.getTime())) {
            nativeDate.setTime(utc(0.0d, nativeDate.getTimeZone()));
        }
        double number = JSType.toNumber(obj2);
        if (Double.isNaN(number)) {
            nativeDate.setTime(Double.NaN);
            return nativeDate.getTime();
        }
        int i = (int) number;
        if (0 <= i && i <= 99) {
            i += 1900;
        }
        setFields(nativeDate, 0, new Object[]{Integer.valueOf(i)}, true);
        return nativeDate.getTime();
    }

    public static String toUTCString(Object obj) {
        return toGMTStringImpl(obj);
    }

    public static String toGMTString(Object obj) {
        return toGMTStringImpl(obj);
    }

    public static String toISOString(Object obj) {
        return toISOStringImpl(obj);
    }

    public static Object toJSON(Object obj, Object obj2) {
        Object object = Global.toObject(obj);
        if (!(object instanceof ScriptObject)) {
            return null;
        }
        ScriptObject scriptObject = (ScriptObject) object;
        Object defaultValue = scriptObject.getDefaultValue(Number.class);
        if (defaultValue instanceof Number) {
            double dDoubleValue = ((Number) defaultValue).doubleValue();
            if (Double.isInfinite(dDoubleValue) || Double.isNaN(dDoubleValue)) {
                return null;
            }
        }
        try {
            try {
                InvokeByName to_iso_string = getTO_ISO_STRING();
                Object objInvokeExact = (Object) to_iso_string.getGetter().invokeExact(scriptObject);
                if (Bootstrap.isCallable(objInvokeExact)) {
                    return (Object) to_iso_string.getInvoker().invokeExact(objInvokeExact, scriptObject, obj2);
                }
                throw ECMAErrors.typeError("not.a.function", new String[]{ScriptRuntime.safeToString(objInvokeExact)});
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        } catch (Error | RuntimeException e) {
            throw e;
        }
    }

    private static double parseDateString(String str) {
        double dUtc;
        DateParser dateParser = new DateParser(str);
        if (dateParser.parse()) {
            Integer[] dateFields = dateParser.getDateFields();
            double dMakeDate = makeDate(dateFields);
            if (dateFields[7] != null) {
                dUtc = dMakeDate - (dateFields[7].intValue() * 60000);
            } else {
                dUtc = utc(dMakeDate, Global.getEnv()._timezone);
            }
            return timeClip(dUtc);
        }
        return Double.NaN;
    }

    private static void zeroPad(StringBuilder sb, int i, int i2) {
        int i3 = 1;
        int i4 = 10;
        while (true) {
            int i5 = i4;
            if (i3 < i2) {
                if (i < i5) {
                    sb.append('0');
                }
                i3++;
                i4 = i5 * 10;
            } else {
                sb.append(i);
                return;
            }
        }
    }

    private static String toStringImpl(Object obj, int i) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate != null && nativeDate.isValidDate()) {
            StringBuilder sb = new StringBuilder(40);
            double localTime = nativeDate.getLocalTime();
            switch (i) {
                case 0:
                case 1:
                case 3:
                    sb.append(weekDays[weekDay(localTime)]).append(' ').append(months[monthFromTime(localTime)]).append(' ');
                    zeroPad(sb, dayFromTime(localTime), 2);
                    sb.append(' ');
                    zeroPad(sb, yearFromTime(localTime), 4);
                    if (i != 1) {
                        sb.append(' ');
                        break;
                    }
                    return sb.toString();
                case 2:
                    break;
                case 4:
                    zeroPad(sb, yearFromTime(localTime), 4);
                    sb.append('-');
                    zeroPad(sb, monthFromTime(localTime) + 1, 2);
                    sb.append('-');
                    zeroPad(sb, dayFromTime(localTime), 2);
                    return sb.toString();
                case 5:
                    zeroPad(sb, hourFromTime(localTime), 2);
                    sb.append(':');
                    zeroPad(sb, minFromTime(localTime), 2);
                    sb.append(':');
                    zeroPad(sb, secFromTime(localTime), 2);
                    return sb.toString();
                default:
                    throw new IllegalArgumentException("format: " + i);
            }
            TimeZone timeZone = nativeDate.getTimeZone();
            int offset = timeZone.getOffset((long) nativeDate.getTime()) / 60000;
            boolean z = offset != timeZone.getRawOffset() / 60000;
            int i2 = ((offset / 60) * 100) + (offset % 60);
            zeroPad(sb, hourFromTime(localTime), 2);
            sb.append(':');
            zeroPad(sb, minFromTime(localTime), 2);
            sb.append(':');
            zeroPad(sb, secFromTime(localTime), 2);
            sb.append(" GMT").append(i2 < 0 ? '-' : '+');
            zeroPad(sb, Math.abs(i2), 4);
            sb.append(" (").append(timeZone.getDisplayName(z, 0, Locale.US)).append(')');
            return sb.toString();
        }
        return INVALID_DATE;
    }

    private static String toGMTStringImpl(Object obj) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate != null && nativeDate.isValidDate()) {
            StringBuilder sb = new StringBuilder(29);
            double time = nativeDate.getTime();
            sb.append(weekDays[weekDay(time)]).append(", ");
            zeroPad(sb, dayFromTime(time), 2);
            sb.append(' ').append(months[monthFromTime(time)]).append(' ');
            zeroPad(sb, yearFromTime(time), 4);
            sb.append(' ');
            zeroPad(sb, hourFromTime(time), 2);
            sb.append(':');
            zeroPad(sb, minFromTime(time), 2);
            sb.append(':');
            zeroPad(sb, secFromTime(time), 2);
            sb.append(" GMT");
            return sb.toString();
        }
        throw ECMAErrors.rangeError("invalid.date", new String[0]);
    }

    private static String toISOStringImpl(Object obj) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate != null && nativeDate.isValidDate()) {
            StringBuilder sb = new StringBuilder(24);
            double time = nativeDate.getTime();
            zeroPad(sb, yearFromTime(time), 4);
            sb.append('-');
            zeroPad(sb, monthFromTime(time) + 1, 2);
            sb.append('-');
            zeroPad(sb, dayFromTime(time), 2);
            sb.append('T');
            zeroPad(sb, hourFromTime(time), 2);
            sb.append(':');
            zeroPad(sb, minFromTime(time), 2);
            sb.append(':');
            zeroPad(sb, secFromTime(time), 2);
            sb.append('.');
            int i = (int) (time % 1000.0d);
            zeroPad(sb, i < 0 ? i + 1000 : i, 3);
            sb.append("Z");
            return sb.toString();
        }
        throw ECMAErrors.rangeError("invalid.date", new String[0]);
    }

    private static double day(double d) {
        return Math.floor(d / msPerDay);
    }

    private static int daysInYear(int i) {
        return i % 4 == 0 && (i % 100 != 0 || i % 400 == 0) ? 366 : 365;
    }

    private static double dayFromYear(double d) {
        return (((365.0d * (d - 1970.0d)) + Math.floor((d - 1969.0d) / 4.0d)) - Math.floor((d - 1901.0d) / 100.0d)) + Math.floor((d - 1601.0d) / 400.0d);
    }

    private static double timeFromYear(int i) {
        return dayFromYear(i) * msPerDay;
    }

    private static int yearFromTime(double d) {
        int iFloor = ((int) Math.floor(d / 3.1556952E10d)) + 1970;
        double dTimeFromYear = timeFromYear(iFloor);
        if (dTimeFromYear > d) {
            iFloor--;
        } else if (dTimeFromYear + (msPerDay * daysInYear(iFloor)) <= d) {
            iFloor++;
        }
        return iFloor;
    }

    private static int dayWithinYear(double d, int i) {
        return (int) (day(d) - dayFromYear(i));
    }

    private static int monthFromTime(double d) {
        int iYearFromTime = yearFromTime(d);
        int iDayWithinYear = dayWithinYear(d, iYearFromTime);
        int[] iArr = firstDayInMonth[iYearFromTime % 4 == 0 && (iYearFromTime % 100 != 0 || iYearFromTime % 400 == 0) ? (char) 1 : (char) 0];
        int i = 0;
        while (i < 11 && iArr[i + 1] <= iDayWithinYear) {
            i++;
        }
        return i;
    }

    private static int dayFromTime(double d) {
        int iYearFromTime = yearFromTime(d);
        int iDayWithinYear = dayWithinYear(d, iYearFromTime);
        int[] iArr = firstDayInMonth[iYearFromTime % 4 == 0 && (iYearFromTime % 100 != 0 || iYearFromTime % 400 == 0) ? (char) 1 : (char) 0];
        int i = 0;
        while (i < 11 && iArr[i + 1] <= iDayWithinYear) {
            i++;
        }
        return (1 + iDayWithinYear) - iArr[i];
    }

    private static int dayFromMonth(int i, int i2) {
        if ($assertionsDisabled || (i >= 0 && i <= 11)) {
            return firstDayInMonth[i2 % 4 == 0 && (i2 % 100 != 0 || i2 % 400 == 0) ? (char) 1 : (char) 0][i];
        }
        throw new AssertionError();
    }

    private static int weekDay(double d) {
        int iDay = ((int) (day(d) + 4.0d)) % 7;
        return iDay < 0 ? iDay + 7 : iDay;
    }

    private static double localTime(double d, TimeZone timeZone) {
        return d + timeZone.getOffset((long) d);
    }

    private static double utc(double d, TimeZone timeZone) {
        return d - timeZone.getOffset((long) (d - timeZone.getRawOffset()));
    }

    private static int hourFromTime(double d) {
        int iFloor = (int) (Math.floor(d / msPerHour) % 24.0d);
        return iFloor < 0 ? iFloor + 24 : iFloor;
    }

    private static int minFromTime(double d) {
        int iFloor = (int) (Math.floor(d / 60000.0d) % 60.0d);
        return iFloor < 0 ? iFloor + 60 : iFloor;
    }

    private static int secFromTime(double d) {
        int iFloor = (int) (Math.floor(d / 1000.0d) % 60.0d);
        return iFloor < 0 ? iFloor + 60 : iFloor;
    }

    private static int valueFromTime(int i, double d) {
        switch (i) {
            case 0:
                return yearFromTime(d);
            case 1:
                return monthFromTime(d);
            case 2:
                return dayFromTime(d);
            case 3:
                return hourFromTime(d);
            case 4:
                return minFromTime(d);
            case 5:
                return secFromTime(d);
            case 6:
                int i2 = (int) (d % 1000.0d);
                return i2 < 0 ? i2 + 1000 : i2;
            default:
                throw new IllegalArgumentException(Integer.toString(i));
        }
    }

    private static double makeDay(double d, double d2, double d3) {
        double dFloor = d + Math.floor(d2 / 12.0d);
        int i = (int) (d2 % 12.0d);
        if (i < 0) {
            i += 12;
        }
        return ((dayFromYear(dFloor) + dayFromMonth(i, (int) dFloor)) + d3) - 1.0d;
    }

    private static double makeDate(Integer[] numArr) {
        return (makeDay(numArr[0].intValue(), numArr[1].intValue(), numArr[2].intValue()) * msPerDay) + (numArr[3].intValue() * msPerHour) + (numArr[4].intValue() * 60000.0d) + (numArr[5].intValue() * 1000.0d) + numArr[6].intValue();
    }

    private static double makeDate(double[] dArr) {
        double dMakeDay = makeDay(dArr[0], dArr[1], dArr[2]) * msPerDay;
        double d = dArr[3];
        double d2 = dArr[4];
        double d3 = dArr[5];
        return dMakeDay + (d * msPerHour) + (d2 * 60000.0d) + (d3 * 1000.0d) + dArr[6];
    }

    private static double[] convertCtorArgs(Object[] objArr) {
        double[] dArr = new double[7];
        boolean z = false;
        int i = 0;
        while (i < dArr.length) {
            if (i < objArr.length) {
                double number = JSType.toNumber(objArr[i]);
                if (Double.isNaN(number) || Double.isInfinite(number)) {
                    z = true;
                }
                dArr[i] = (long) number;
            } else {
                dArr[i] = i == 2 ? 1.0d : 0.0d;
            }
            i++;
        }
        if (0.0d <= dArr[0] && dArr[0] <= 99.0d) {
            dArr[0] = dArr[0] + 1900.0d;
        }
        if (z) {
            return null;
        }
        return dArr;
    }

    private static double[] convertArgs(Object[] objArr, double d, int i, int i2, int i3) {
        double[] dArr = new double[i3];
        boolean z = false;
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            if (i <= i4 && i4 < i + objArr.length) {
                double number = JSType.toNumber(objArr[i4 - i]);
                if (Double.isNaN(number) || Double.isInfinite(number)) {
                    z = true;
                }
                dArr[i4 - i2] = (long) number;
            } else {
                if (i4 == i) {
                    z = true;
                }
                if (!z && !Double.isNaN(d)) {
                    dArr[i4 - i2] = valueFromTime(i4, d);
                }
            }
        }
        if (z) {
            return null;
        }
        return dArr;
    }

    private static double timeClip(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d) || Math.abs(d) > 8.64E15d) {
            return Double.NaN;
        }
        return (long) d;
    }

    private static NativeDate ensureNativeDate(Object obj) {
        return getNativeDate(obj);
    }

    private static NativeDate getNativeDate(Object obj) {
        if (obj instanceof NativeDate) {
            return (NativeDate) obj;
        }
        if (obj != null && obj == Global.instance().getDatePrototype()) {
            return Global.instance().getDefaultDate();
        }
        throw ECMAErrors.typeError("not.a.date", new String[]{ScriptRuntime.safeToString(obj)});
    }

    private static double getField(Object obj, int i) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate == null || !nativeDate.isValidDate()) {
            return Double.NaN;
        }
        return valueFromTime(i, nativeDate.getLocalTime());
    }

    private static double getUTCField(Object obj, int i) {
        NativeDate nativeDate = getNativeDate(obj);
        if (nativeDate == null || !nativeDate.isValidDate()) {
            return Double.NaN;
        }
        return valueFromTime(i, nativeDate.getTime());
    }

    private static void setFields(NativeDate nativeDate, int i, Object[] objArr, boolean z) {
        int i2;
        int i3;
        double dDay;
        double dTimeClip;
        if (i < 3) {
            i2 = 0;
            i3 = 3;
        } else {
            i2 = 3;
            i3 = 4;
        }
        double localTime = z ? nativeDate.getLocalTime() : nativeDate.getTime();
        double[] dArrConvertArgs = convertArgs(objArr, localTime, i, i2, i3);
        if (!nativeDate.isValidDate()) {
            return;
        }
        if (dArrConvertArgs == null) {
            dTimeClip = Double.NaN;
        } else {
            if (i2 == 0) {
                double dMakeDay = makeDay(dArrConvertArgs[0], dArrConvertArgs[1], dArrConvertArgs[2]);
                double d = localTime % msPerDay;
                dDay = (dMakeDay * msPerDay) + (d < 0.0d ? d + msPerDay : d);
            } else {
                dDay = (day(localTime) * msPerDay) + (dArrConvertArgs[0] * msPerHour) + (dArrConvertArgs[1] * 60000.0d) + (dArrConvertArgs[2] * 1000.0d) + dArrConvertArgs[3];
            }
            if (z) {
                dDay = utc(dDay, nativeDate.getTimeZone());
            }
            dTimeClip = timeClip(dDay);
        }
        nativeDate.setTime(dTimeClip);
    }

    private boolean isValidDate() {
        return !Double.isNaN(this.time);
    }

    private double getLocalTime() {
        return localTime(this.time, this.timezone);
    }

    private double getTime() {
        return this.time;
    }

    private void setTime(double d) {
        this.time = d;
    }

    private TimeZone getTimeZone() {
        return this.timezone;
    }
}
