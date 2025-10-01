package jdk.nashorn.internal.objects;

import java.util.Collections;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeMath.class */
public final class NativeMath extends ScriptObject {
    private static PropertyMap $nasgenmap$;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeMath$Constructor.class */
    final class Constructor extends ScriptObject {
        private Object abs;
        private Object acos;
        private Object asin;
        private Object atan;
        private Object atan2;
        private Object ceil;
        private Object cos;
        private Object exp;
        private Object floor;
        private Object log;
        private Object max;
        private Object min;
        private Object pow;
        private Object random;
        private Object round;
        private Object sin;
        private Object sqrt;
        private Object tan;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$abs() {
            return this.abs;
        }

        public void S$abs(Object obj) {
            this.abs = obj;
        }

        public Object G$acos() {
            return this.acos;
        }

        public void S$acos(Object obj) {
            this.acos = obj;
        }

        public Object G$asin() {
            return this.asin;
        }

        public void S$asin(Object obj) {
            this.asin = obj;
        }

        public Object G$atan() {
            return this.atan;
        }

        public void S$atan(Object obj) {
            this.atan = obj;
        }

        public Object G$atan2() {
            return this.atan2;
        }

        public void S$atan2(Object obj) {
            this.atan2 = obj;
        }

        public Object G$ceil() {
            return this.ceil;
        }

        public void S$ceil(Object obj) {
            this.ceil = obj;
        }

        public Object G$cos() {
            return this.cos;
        }

        public void S$cos(Object obj) {
            this.cos = obj;
        }

        public Object G$exp() {
            return this.exp;
        }

        public void S$exp(Object obj) {
            this.exp = obj;
        }

        public Object G$floor() {
            return this.floor;
        }

        public void S$floor(Object obj) {
            this.floor = obj;
        }

        public Object G$log() {
            return this.log;
        }

        public void S$log(Object obj) {
            this.log = obj;
        }

        public Object G$max() {
            return this.max;
        }

        public void S$max(Object obj) {
            this.max = obj;
        }

        public Object G$min() {
            return this.min;
        }

        public void S$min(Object obj) {
            this.min = obj;
        }

        public Object G$pow() {
            return this.pow;
        }

        public void S$pow(Object obj) {
            this.pow = obj;
        }

        public Object G$random() {
            return this.random;
        }

        public void S$random(Object obj) {
            this.random = obj;
        }

        public Object G$round() {
            return this.round;
        }

        public void S$round(Object obj) {
            this.round = obj;
        }

        public Object G$sin() {
            return this.sin;
        }

        public void S$sin(Object obj) {
            this.sin = obj;
        }

        public Object G$sqrt() {
            return this.sqrt;
        }

        public void S$sqrt(Object obj) {
            this.sqrt = obj;
        }

        public Object G$tan() {
            return this.tan;
        }

        public void S$tan(Object obj) {
            this.tan = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeMath.Constructor.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeMath$Constructor.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:168)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            Caused by: java.lang.ArrayIndexOutOfBoundsException
            */
        Constructor() {
            /*
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeMath.Constructor.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeMath$Constructor.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeMath.Constructor.<init>():void");
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeMath$Prototype.class */
    final class Prototype extends PrototypeObject {
        Prototype() {
        }
    }

    static {
        $clinit$();
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    private NativeMath() {
        throw new UnsupportedOperationException();
    }

    public static double abs(Object obj, Object obj2) {
        return Math.abs(JSType.toNumber(obj2));
    }

    public static int abs(Object obj, int i) {
        return Math.abs(i);
    }

    public static long abs(Object obj, long j) {
        return Math.abs(j);
    }

    public static double abs(Object obj, double d) {
        return Math.abs(d);
    }

    public static double acos(Object obj, Object obj2) {
        return Math.acos(JSType.toNumber(obj2));
    }

    public static double acos(Object obj, double d) {
        return Math.acos(d);
    }

    public static double asin(Object obj, Object obj2) {
        return Math.asin(JSType.toNumber(obj2));
    }

    public static double asin(Object obj, double d) {
        return Math.asin(d);
    }

    public static double atan(Object obj, Object obj2) {
        return Math.atan(JSType.toNumber(obj2));
    }

    public static double atan(Object obj, double d) {
        return Math.atan(d);
    }

    public static double atan2(Object obj, Object obj2, Object obj3) {
        return Math.atan2(JSType.toNumber(obj2), JSType.toNumber(obj3));
    }

    public static double atan2(Object obj, double d, double d2) {
        return Math.atan2(d, d2);
    }

    public static double ceil(Object obj, Object obj2) {
        return Math.ceil(JSType.toNumber(obj2));
    }

    public static double ceil(Object obj, double d) {
        return Math.ceil(d);
    }

    public static double cos(Object obj, Object obj2) {
        return Math.cos(JSType.toNumber(obj2));
    }

    public static double cos(Object obj, double d) {
        return Math.cos(d);
    }

    public static double exp(Object obj, Object obj2) {
        return Math.exp(JSType.toNumber(obj2));
    }

    public static double floor(Object obj, Object obj2) {
        return Math.floor(JSType.toNumber(obj2));
    }

    public static double floor(Object obj, double d) {
        return Math.floor(d);
    }

    public static double log(Object obj, Object obj2) {
        return Math.log(JSType.toNumber(obj2));
    }

    public static double log(Object obj, double d) {
        return Math.log(d);
    }

    public static double max(Object obj, Object[] objArr) {
        switch (objArr.length) {
            case 0:
                return Double.NEGATIVE_INFINITY;
            case 1:
                return JSType.toNumber(objArr[0]);
            default:
                double number = JSType.toNumber(objArr[0]);
                for (int i = 1; i < objArr.length; i++) {
                    number = Math.max(number, JSType.toNumber(objArr[i]));
                }
                return number;
        }
    }

    public static int max(Object obj, int i, int i2) {
        return Math.max(i, i2);
    }

    public static long max(Object obj, long j, long j2) {
        return Math.max(j, j2);
    }

    public static double max(Object obj, double d, double d2) {
        return Math.max(d, d2);
    }

    public static double max(Object obj, Object obj2, Object obj3) {
        return Math.max(JSType.toNumber(obj2), JSType.toNumber(obj3));
    }

    public static double min(Object obj, Object[] objArr) {
        switch (objArr.length) {
            case 0:
                return Double.POSITIVE_INFINITY;
            case 1:
                return JSType.toNumber(objArr[0]);
            default:
                double number = JSType.toNumber(objArr[0]);
                for (int i = 1; i < objArr.length; i++) {
                    number = Math.min(number, JSType.toNumber(objArr[i]));
                }
                return number;
        }
    }

    public static int min(Object obj, int i, int i2) {
        return Math.min(i, i2);
    }

    public static long min(Object obj, long j, long j2) {
        return Math.min(j, j2);
    }

    public static double min(Object obj, double d, double d2) {
        return Math.min(d, d2);
    }

    public static double min(Object obj, Object obj2, Object obj3) {
        return Math.min(JSType.toNumber(obj2), JSType.toNumber(obj3));
    }

    public static double pow(Object obj, Object obj2, Object obj3) {
        return Math.pow(JSType.toNumber(obj2), JSType.toNumber(obj3));
    }

    public static double pow(Object obj, double d, double d2) {
        return Math.pow(d, d2);
    }

    public static double random(Object obj) {
        return Math.random();
    }

    public static double round(Object obj, Object obj2) {
        double number = JSType.toNumber(obj2);
        if (Math.getExponent(number) >= 52) {
            return number;
        }
        return Math.copySign(Math.floor(number + 0.5d), number);
    }

    public static double sin(Object obj, Object obj2) {
        return Math.sin(JSType.toNumber(obj2));
    }

    public static double sin(Object obj, double d) {
        return Math.sin(d);
    }

    public static double sqrt(Object obj, Object obj2) {
        return Math.sqrt(JSType.toNumber(obj2));
    }

    public static double sqrt(Object obj, double d) {
        return Math.sqrt(d);
    }

    public static double tan(Object obj, Object obj2) {
        return Math.tan(JSType.toNumber(obj2));
    }

    public static double tan(Object obj, double d) {
        return Math.tan(d);
    }
}
