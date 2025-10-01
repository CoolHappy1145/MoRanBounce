package org.spongepowered.asm.mixin.injection.callback;

import org.objectweb.asm.Type;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable.class */
public class CallbackInfoReturnable extends CallbackInfo {
    private Object returnValue;

    public CallbackInfoReturnable(String str, boolean z) {
        super(str, z);
        this.returnValue = null;
    }

    public CallbackInfoReturnable(String str, boolean z, Object obj) {
        super(str, z);
        this.returnValue = obj;
    }

    public CallbackInfoReturnable(String str, boolean z, byte b) {
        super(str, z);
        this.returnValue = Byte.valueOf(b);
    }

    public CallbackInfoReturnable(String str, boolean z, char c) {
        super(str, z);
        this.returnValue = Character.valueOf(c);
    }

    public CallbackInfoReturnable(String str, boolean z, double d) {
        super(str, z);
        this.returnValue = Double.valueOf(d);
    }

    public CallbackInfoReturnable(String str, boolean z, float f) {
        super(str, z);
        this.returnValue = Float.valueOf(f);
    }

    public CallbackInfoReturnable(String str, boolean z, int i) {
        super(str, z);
        this.returnValue = Integer.valueOf(i);
    }

    public CallbackInfoReturnable(String str, boolean z, long j) {
        super(str, z);
        this.returnValue = Long.valueOf(j);
    }

    public CallbackInfoReturnable(String str, boolean z, short s) {
        super(str, z);
        this.returnValue = Short.valueOf(s);
    }

    public CallbackInfoReturnable(String str, boolean z, boolean z2) {
        super(str, z);
        this.returnValue = Boolean.valueOf(z2);
    }

    public void setReturnValue(Object obj) {
        super.cancel();
        this.returnValue = obj;
    }

    public Object getReturnValue() {
        return this.returnValue;
    }

    public byte getReturnValueB() {
        if (this.returnValue == null) {
            return (byte) 0;
        }
        return ((Byte) this.returnValue).byteValue();
    }

    public char getReturnValueC() {
        if (this.returnValue == null) {
            return (char) 0;
        }
        return ((Character) this.returnValue).charValue();
    }

    public double getReturnValueD() {
        if (this.returnValue == null) {
            return 0.0d;
        }
        return ((Double) this.returnValue).doubleValue();
    }

    public float getReturnValueF() {
        if (this.returnValue == null) {
            return 0.0f;
        }
        return ((Float) this.returnValue).floatValue();
    }

    public int getReturnValueI() {
        if (this.returnValue == null) {
            return 0;
        }
        return ((Integer) this.returnValue).intValue();
    }

    public long getReturnValueJ() {
        if (this.returnValue == null) {
            return 0L;
        }
        return ((Long) this.returnValue).longValue();
    }

    public short getReturnValueS() {
        if (this.returnValue == null) {
            return (short) 0;
        }
        return ((Short) this.returnValue).shortValue();
    }

    public boolean getReturnValueZ() {
        if (this.returnValue == null) {
            return false;
        }
        return ((Boolean) this.returnValue).booleanValue();
    }

    static String getReturnAccessor(Type type) {
        if (type.getSort() == 10 || type.getSort() == 9) {
            return "getReturnValue";
        }
        return String.format("getReturnValue%s", type.getDescriptor());
    }

    static String getReturnDescriptor(Type type) {
        if (type.getSort() == 10 || type.getSort() == 9) {
            return String.format("()%s", Constants.OBJECT_DESC);
        }
        return String.format("()%s", type.getDescriptor());
    }
}
