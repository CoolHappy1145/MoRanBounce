package jdk.nashorn.internal.runtime.arrays;

import java.util.Iterator;
import java.util.List;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.nodes.MethodNode.getBasicBlocks()" is null
    	at jadx.core.dex.visitors.kotlin.ProcessKotlinInternals.processMth(ProcessKotlinInternals.java:93)
    	at jadx.core.dex.visitors.kotlin.ProcessKotlinInternals.visit(ProcessKotlinInternals.java:84)
    */
/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/ArrayLikeIterator.class */
public abstract class ArrayLikeIterator implements Iterator {
    protected long index = 0;
    protected final boolean includeUndefined;

    /*  JADX ERROR: Failed to decode insn: 0x0005: MOVE_MULTI
        java.lang.ArrayIndexOutOfBoundsException: arraycopy: source index -1 out of bounds for object array[8]
        	at java.base/java.lang.System.arraycopy(Native Method)
        	at jadx.plugins.input.java.data.code.StackState.insert(StackState.java:52)
        	at jadx.plugins.input.java.data.code.CodeDecodeState.insert(CodeDecodeState.java:137)
        	at jadx.plugins.input.java.data.code.JavaInsnsRegister.dup2x1(JavaInsnsRegister.java:313)
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
    protected long bumpIndex() {
        /*
            r8 = this;
            r0 = r8
            r1 = r0
            long r1 = r1.index
            // decode failed: arraycopy: source index -1 out of bounds for object array[8]
            r2 = 1
            long r1 = r1 + r2
            r0.index = r1
            return r-1
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator.bumpIndex():long");
    }

    public abstract long getLength();

    ArrayLikeIterator(boolean z) {
        this.includeUndefined = z;
    }

    public long nextIndex() {
        return this.index;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

    public static ArrayLikeIterator arrayLikeIterator(Object obj) {
        return arrayLikeIterator(obj, false);
    }

    public static ArrayLikeIterator reverseArrayLikeIterator(Object obj) {
        return reverseArrayLikeIterator(obj, false);
    }

    public static ArrayLikeIterator arrayLikeIterator(Object obj, boolean z) {
        if (ScriptObject.isArray(obj)) {
            return new ScriptArrayIterator((ScriptObject) obj, z);
        }
        Object scriptObject = JSType.toScriptObject(obj);
        if (scriptObject instanceof ScriptObject) {
            return new ScriptObjectIterator((ScriptObject) scriptObject, z);
        }
        if (scriptObject instanceof JSObject) {
            return new JSObjectIterator((JSObject) scriptObject, z);
        }
        if (scriptObject instanceof List) {
            return new JavaListIterator((List) scriptObject, z);
        }
        if (scriptObject != null && scriptObject.getClass().isArray()) {
            return new JavaArrayIterator(scriptObject, z);
        }
        return new EmptyArrayLikeIterator();
    }

    public static ArrayLikeIterator reverseArrayLikeIterator(Object obj, boolean z) {
        if (ScriptObject.isArray(obj)) {
            return new ReverseScriptArrayIterator((ScriptObject) obj, z);
        }
        Object scriptObject = JSType.toScriptObject(obj);
        if (scriptObject instanceof ScriptObject) {
            return new ReverseScriptObjectIterator((ScriptObject) scriptObject, z);
        }
        if (scriptObject instanceof JSObject) {
            return new ReverseJSObjectIterator((JSObject) scriptObject, z);
        }
        if (scriptObject instanceof List) {
            return new ReverseJavaListIterator((List) scriptObject, z);
        }
        if (scriptObject != null && scriptObject.getClass().isArray()) {
            return new ReverseJavaArrayIterator(scriptObject, z);
        }
        return new EmptyArrayLikeIterator();
    }
}
