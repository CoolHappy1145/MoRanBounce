package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.util.AbstractList;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.concurrent.Callable;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ListAdapter.class */
public class ListAdapter extends AbstractList implements RandomAccess, Deque {
    private static final Callable ADD_INVOKER_CREATOR = invokerCreator(Void.TYPE, new Class[]{Object.class, JSObject.class, Object.class});
    private static final Object PUSH = new Object();
    private static final Object UNSHIFT = new Object();
    private static final Callable REMOVE_INVOKER_CREATOR = invokerCreator(Object.class, new Class[]{Object.class, JSObject.class});
    private static final Object POP = new Object();
    private static final Object SHIFT = new Object();
    private static final Object SPLICE_ADD = new Object();
    private static final Callable SPLICE_ADD_INVOKER_CREATOR = invokerCreator(Void.TYPE, new Class[]{Object.class, JSObject.class, Integer.TYPE, Integer.TYPE, Object.class});
    private static final Object SPLICE_REMOVE = new Object();
    private static final Callable SPLICE_REMOVE_INVOKER_CREATOR = invokerCreator(Void.TYPE, new Class[]{Object.class, JSObject.class, Integer.TYPE, Integer.TYPE});
    final JSObject obj;
    private final Global global;

    ListAdapter(JSObject jSObject, Global global) {
        if (global == null) {
            throw new IllegalStateException(ECMAErrors.getMessage("list.adapter.null.global", new String[0]));
        }
        this.obj = jSObject;
        this.global = global;
    }

    public static ListAdapter create(Object obj) {
        Global global = Context.getGlobal();
        return new ListAdapter(getJSObject(obj, global), global);
    }

    private static JSObject getJSObject(Object obj, Global global) {
        if (obj instanceof ScriptObject) {
            return (JSObject) ScriptObjectMirror.wrap(obj, global);
        }
        if (obj instanceof JSObject) {
            return (JSObject) obj;
        }
        throw new IllegalArgumentException("ScriptObject or JSObject expected");
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        checkRange(i);
        return getAt(i);
    }

    private Object getAt(int i) {
        return this.obj.getSlot(i);
    }

    @Override // java.util.AbstractList, java.util.List
    public Object set(int i, Object obj) {
        checkRange(i);
        Object at = getAt(i);
        this.obj.setSlot(i, obj);
        return at;
    }

    private void checkRange(int i) {
        if (i < 0 || i >= size()) {
            throw invalidIndex(i);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque
    public int size() {
        return JSType.toInt32(this.obj.getMember("length"));
    }

    @Override // java.util.Deque
    public final void push(Object obj) {
        addFirst(obj);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
    public final boolean add(Object obj) {
        addLast(obj);
        return true;
    }

    @Override // java.util.Deque
    public final void addFirst(Object obj) {
        try {
            (void) getDynamicInvoker(UNSHIFT, ADD_INVOKER_CREATOR).invokeExact(getFunction("unshift"), this.obj, obj);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override // java.util.Deque
    public final void addLast(Object obj) {
        try {
            (void) getDynamicInvoker(PUSH, ADD_INVOKER_CREATOR).invokeExact(getFunction("push"), this.obj, obj);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        try {
            if (i < 0) {
                throw invalidIndex(i);
            }
            if (i == 0) {
                addFirst(obj);
            } else {
                int size = size();
                if (i < size) {
                    (void) getDynamicInvoker(SPLICE_ADD, SPLICE_ADD_INVOKER_CREATOR).invokeExact(this.obj.getMember("splice"), this.obj, i, 0, obj);
                } else if (i == size) {
                    addLast(obj);
                } else {
                    throw invalidIndex(i);
                }
            }
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private Object getFunction(String str) {
        Object member = this.obj.getMember(str);
        if (!Bootstrap.isCallable(member)) {
            throw new UnsupportedOperationException("The script object doesn't have a function named " + str);
        }
        return member;
    }

    private static IndexOutOfBoundsException invalidIndex(int i) {
        return new IndexOutOfBoundsException(String.valueOf(i));
    }

    @Override // java.util.Deque, java.util.Queue
    public final boolean offer(Object obj) {
        return offerLast(obj);
    }

    @Override // java.util.Deque
    public final boolean offerFirst(Object obj) {
        addFirst(obj);
        return true;
    }

    @Override // java.util.Deque
    public final boolean offerLast(Object obj) {
        addLast(obj);
        return true;
    }

    @Override // java.util.Deque
    public final Object pop() {
        return removeFirst();
    }

    @Override // java.util.Deque, java.util.Queue
    public final Object remove() {
        return removeFirst();
    }

    @Override // java.util.Deque
    public final Object removeFirst() {
        checkNonEmpty();
        return invokeShift();
    }

    @Override // java.util.Deque
    public final Object removeLast() {
        checkNonEmpty();
        return invokePop();
    }

    private void checkNonEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object remove(int i) {
        if (i < 0) {
            throw invalidIndex(i);
        }
        if (i == 0) {
            return invokeShift();
        }
        int size = size() - 1;
        if (i < size) {
            Object obj = get(i);
            invokeSpliceRemove(i, 1);
            return obj;
        }
        if (i == size) {
            return invokePop();
        }
        throw invalidIndex(i);
    }

    private Object invokeShift() {
        try {
            return (Object) getDynamicInvoker(SHIFT, REMOVE_INVOKER_CREATOR).invokeExact(getFunction("shift"), this.obj);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private Object invokePop() {
        try {
            return (Object) getDynamicInvoker(POP, REMOVE_INVOKER_CREATOR).invokeExact(getFunction("pop"), this.obj);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override // java.util.AbstractList
    protected final void removeRange(int i, int i2) {
        invokeSpliceRemove(i, i2 - i);
    }

    private void invokeSpliceRemove(int i, int i2) {
        try {
            (void) getDynamicInvoker(SPLICE_REMOVE, SPLICE_REMOVE_INVOKER_CREATOR).invokeExact(getFunction("splice"), this.obj, i, i2);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override // java.util.Deque, java.util.Queue
    public final Object poll() {
        return pollFirst();
    }

    @Override // java.util.Deque
    public final Object pollFirst() {
        if (isEmpty()) {
            return null;
        }
        return invokeShift();
    }

    @Override // java.util.Deque
    public final Object pollLast() {
        if (isEmpty()) {
            return null;
        }
        return invokePop();
    }

    @Override // java.util.Deque, java.util.Queue
    public final Object peek() {
        return peekFirst();
    }

    @Override // java.util.Deque
    public final Object peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return get(0);
    }

    @Override // java.util.Deque
    public final Object peekLast() {
        if (isEmpty()) {
            return null;
        }
        return get(size() - 1);
    }

    @Override // java.util.Deque, java.util.Queue
    public final Object element() {
        return getFirst();
    }

    @Override // java.util.Deque
    public final Object getFirst() {
        checkNonEmpty();
        return get(0);
    }

    @Override // java.util.Deque
    public final Object getLast() {
        checkNonEmpty();
        return get(size() - 1);
    }

    @Override // java.util.Deque
    public final Iterator descendingIterator() {
        return new Iterator(this, listIterator(size())) { // from class: jdk.nashorn.internal.runtime.ListAdapter.1
            final ListIterator val$it;
            final ListAdapter this$0;

            {
                this.this$0 = this;
                this.val$it = listIterator;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.val$it.hasPrevious();
            }

            @Override // java.util.Iterator
            public Object next() {
                return this.val$it.previous();
            }

            @Override // java.util.Iterator
            public void remove() {
                this.val$it.remove();
            }
        };
    }

    @Override // java.util.Deque
    public final boolean removeFirstOccurrence(Object obj) {
        return removeOccurrence(obj, iterator());
    }

    @Override // java.util.Deque
    public final boolean removeLastOccurrence(Object obj) {
        return removeOccurrence(obj, descendingIterator());
    }

    private static boolean removeOccurrence(Object obj, Iterator it) {
        while (it.hasNext()) {
            if (Objects.equals(obj, it.next())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    private static Callable invokerCreator(Class cls, Class[] clsArr) {
        return new Callable(cls, clsArr) { // from class: jdk.nashorn.internal.runtime.ListAdapter.2
            final Class val$rtype;
            final Class[] val$ptypes;

            {
                this.val$rtype = cls;
                this.val$ptypes = clsArr;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", this.val$rtype, this.val$ptypes);
            }
        };
    }

    private MethodHandle getDynamicInvoker(Object obj, Callable callable) {
        return this.global.getDynamicInvoker(obj, callable);
    }
}
