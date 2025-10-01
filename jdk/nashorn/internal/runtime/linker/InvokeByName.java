package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/InvokeByName.class */
public final class InvokeByName {
    private final String name;
    private final MethodHandle getter;
    private final MethodHandle invoker;

    public InvokeByName(String str, Class cls) {
        this(str, cls, Object.class, new Class[0]);
    }

    public InvokeByName(String str, Class cls, Class cls2, Class[] clsArr) {
        Class[] clsArr2;
        this.name = str;
        this.getter = Bootstrap.createDynamicInvoker("dyn:getMethod|getProp|getElem:" + str, Object.class, new Class[]{cls});
        int length = clsArr.length;
        if (length == 0) {
            clsArr2 = new Class[]{Object.class, cls};
        } else {
            clsArr2 = new Class[length + 2];
            clsArr2[0] = Object.class;
            clsArr2[1] = cls;
            System.arraycopy(clsArr, 0, clsArr2, 2, length);
        }
        this.invoker = Bootstrap.createDynamicInvoker("dyn:call", cls2, clsArr2);
    }

    public String getName() {
        return this.name;
    }

    public MethodHandle getGetter() {
        return this.getter;
    }

    public MethodHandle getInvoker() {
        return this.invoker;
    }
}
