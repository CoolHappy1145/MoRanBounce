package jdk.nashorn.internal.objects;

import java.util.Objects;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: L-out.jar:jdk/nashorn/internal/objects/GenericPropertyDescriptor.class */
public final class GenericPropertyDescriptor extends ScriptObject implements PropertyDescriptor {
    public Object configurable;
    public Object enumerable;
    private static PropertyMap $nasgenmap$;

    /* JADX WARN: Failed to check method for inline after forced processjdk.nashorn.internal.objects.GenericPropertyDescriptor.$clinit$():void */
    static {
        $clinit$();
    }

    /*  JADX ERROR: Failed to decode insn: 0x000C: CONST
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
    /*  JADX ERROR: Failed to decode insn: 0x001C: CONST
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
    /*  JADX ERROR: Failed to decode insn: 0x001E: INVOKE_INTERFACE
        java.lang.ArrayIndexOutOfBoundsException: Index -18432 out of bounds for length 196
        	at jadx.plugins.input.java.data.ClassOffsets.getOffsetOfConstEntry(ClassOffsets.java:73)
        	at jadx.plugins.input.java.data.ConstPoolReader.jumpToData(ConstPoolReader.java:258)
        	at jadx.plugins.input.java.data.ConstPoolReader.getMethodRef(ConstPoolReader.java:73)
        	at jadx.plugins.input.java.data.code.JavaInsnData.getIndexAsMethod(JavaInsnData.java:163)
        	at jadx.plugins.input.java.data.code.decoders.InvokeDecoder.decode(InvokeDecoder.java:37)
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
            r2 = 2
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "configurable"
            r3 = 0
            // decode failed: Unsupported constant type: METHOD_HANDLE
            if (r2 != r3) goto LB_47f2
            return r1
            r-1.add(r0)
            r-1 = r-2
            java.lang.String r0 = "enumerable"
            r1 = 0
            // decode failed: Unsupported constant type: METHOD_HANDLE
            // decode failed: null
            jdk.nashorn.internal.runtime.PropertyMap r0 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r0)
            jdk.nashorn.internal.objects.GenericPropertyDescriptor.$nasgenmap$ = r0
            return
            r0 = 0
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.GenericPropertyDescriptor.$clinit$():void");
    }

    public Object G$configurable() {
        return this.configurable;
    }

    public void S$configurable(Object obj) {
        this.configurable = obj;
    }

    public Object G$enumerable() {
        return this.enumerable;
    }

    public void S$enumerable(Object obj) {
        this.enumerable = obj;
    }

    GenericPropertyDescriptor(boolean z, boolean z2, Global global) {
        super(global.getObjectPrototype(), $nasgenmap$);
        this.configurable = Boolean.valueOf(z);
        this.enumerable = Boolean.valueOf(z2);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean isConfigurable() {
        return JSType.toBoolean(this.configurable);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean isEnumerable() {
        return JSType.toBoolean(this.enumerable);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public Object getValue() {
        throw new UnsupportedOperationException(PropertyDescriptor.VALUE);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getGetter() {
        throw new UnsupportedOperationException(PropertyDescriptor.GET);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public ScriptFunction getSetter() {
        throw new UnsupportedOperationException(PropertyDescriptor.SET);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setConfigurable(boolean z) {
        this.configurable = Boolean.valueOf(z);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setEnumerable(boolean z) {
        this.enumerable = Boolean.valueOf(z);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setWritable(boolean z) {
        throw new UnsupportedOperationException("set writable");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setValue(Object obj) {
        throw new UnsupportedOperationException("set value");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setGetter(Object obj) {
        throw new UnsupportedOperationException("set getter");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public void setSetter(Object obj) {
        throw new UnsupportedOperationException("set setter");
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public PropertyDescriptor fillFrom(ScriptObject scriptObject) {
        if (scriptObject.has(PropertyDescriptor.CONFIGURABLE)) {
            this.configurable = Boolean.valueOf(JSType.toBoolean(scriptObject.get(PropertyDescriptor.CONFIGURABLE)));
        } else {
            delete(PropertyDescriptor.CONFIGURABLE, false);
        }
        if (scriptObject.has(PropertyDescriptor.ENUMERABLE)) {
            this.enumerable = Boolean.valueOf(JSType.toBoolean(scriptObject.get(PropertyDescriptor.ENUMERABLE)));
        } else {
            delete(PropertyDescriptor.ENUMERABLE, false);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyDescriptor
    public boolean hasAndEquals(PropertyDescriptor propertyDescriptor) {
        if (has(PropertyDescriptor.CONFIGURABLE) && propertyDescriptor.has(PropertyDescriptor.CONFIGURABLE) && isConfigurable() != propertyDescriptor.isConfigurable()) {
            return false;
        }
        if (has(PropertyDescriptor.ENUMERABLE) && propertyDescriptor.has(PropertyDescriptor.ENUMERABLE) && isEnumerable() != propertyDescriptor.isEnumerable()) {
            return false;
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GenericPropertyDescriptor)) {
            return false;
        }
        GenericPropertyDescriptor genericPropertyDescriptor = (GenericPropertyDescriptor) obj;
        return ScriptRuntime.sameValue(this.configurable, genericPropertyDescriptor.configurable) && ScriptRuntime.sameValue(this.enumerable, genericPropertyDescriptor.enumerable);
    }

    public String toString() {
        return '[' + getClass().getSimpleName() + " {configurable=" + this.configurable + " enumerable=" + this.enumerable + "}]";
    }

    public int hashCode() {
        return (97 * (679 + Objects.hashCode(this.configurable))) + Objects.hashCode(this.enumerable);
    }
}
