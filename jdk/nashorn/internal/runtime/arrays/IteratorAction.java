package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/IteratorAction.class */
public abstract class IteratorAction {
    protected final Object self;
    protected Object thisArg;
    protected final Object callbackfn;
    protected Object result;
    protected long index;
    private final ArrayLikeIterator iter;

    protected abstract boolean forEach(Object obj, double d);

    public IteratorAction(Object obj, Object obj2, Object obj3, Object obj4) {
        this(obj, obj2, obj3, obj4, ArrayLikeIterator.arrayLikeIterator(obj));
    }

    public IteratorAction(Object obj, Object obj2, Object obj3, Object obj4, ArrayLikeIterator arrayLikeIterator) {
        this.self = obj;
        this.callbackfn = obj2;
        this.result = obj4;
        this.iter = arrayLikeIterator;
        this.thisArg = obj3;
    }

    public final Object apply() {
        this.thisArg = (this.thisArg != ScriptRuntime.UNDEFINED || Bootstrap.isStrictCallable(this.callbackfn)) ? this.thisArg : Context.getGlobal();
        ArrayLikeIterator arrayLikeIterator = this.iter;
        ArrayLikeIterator arrayLikeIterator2 = this.iter;
        while (this.iter.hasNext()) {
            Object next = this.iter.next();
            this.index = this.iter.nextIndex() + (0 != 0 ? 1 : -1);
            try {
                if (!forEach(next, this.index)) {
                    return this.result;
                }
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }
        return this.result;
    }
}
