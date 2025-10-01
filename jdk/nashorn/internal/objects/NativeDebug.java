package jdk.nashorn.internal.objects;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyListeners;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.Scope;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.events.RuntimeEvent;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeDebug.class */
public final class NativeDebug extends ScriptObject {
    private static PropertyMap $nasgenmap$;
    private static final String EVENT_QUEUE = "__eventQueue__";
    private static final String EVENT_QUEUE_CAPACITY = "__eventQueueCapacity__";

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeDebug$Constructor.class */
    final class Constructor extends ScriptObject {
        private Object getArrayDataClass;
        private Object getArrayData;
        private Object getContext;
        private Object map;
        private Object identical;
        private Object equalWithoutType;
        private Object diffPropertyMaps;
        private Object getClass;
        private Object equals;
        private Object toJavaString;
        private Object toIdentString;
        private Object getListenerCount;
        private Object dumpCounters;
        private Object getEventQueueCapacity;
        private Object setEventQueueCapacity;
        private Object addRuntimeEvent;
        private Object expandEventQueueCapacity;
        private Object clearRuntimeEvents;
        private Object removeRuntimeEvent;
        private Object getRuntimeEvents;
        private Object getLastRuntimeEvent;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$getArrayDataClass() {
            return this.getArrayDataClass;
        }

        public void S$getArrayDataClass(Object obj) {
            this.getArrayDataClass = obj;
        }

        public Object G$getArrayData() {
            return this.getArrayData;
        }

        public void S$getArrayData(Object obj) {
            this.getArrayData = obj;
        }

        public Object G$getContext() {
            return this.getContext;
        }

        public void S$getContext(Object obj) {
            this.getContext = obj;
        }

        public Object G$map() {
            return this.map;
        }

        public void S$map(Object obj) {
            this.map = obj;
        }

        public Object G$identical() {
            return this.identical;
        }

        public void S$identical(Object obj) {
            this.identical = obj;
        }

        public Object G$equalWithoutType() {
            return this.equalWithoutType;
        }

        public void S$equalWithoutType(Object obj) {
            this.equalWithoutType = obj;
        }

        public Object G$diffPropertyMaps() {
            return this.diffPropertyMaps;
        }

        public void S$diffPropertyMaps(Object obj) {
            this.diffPropertyMaps = obj;
        }

        public Object G$getClass() {
            return this.getClass;
        }

        public void S$getClass(Object obj) {
            this.getClass = obj;
        }

        public Object G$equals() {
            return this.equals;
        }

        public void S$equals(Object obj) {
            this.equals = obj;
        }

        public Object G$toJavaString() {
            return this.toJavaString;
        }

        public void S$toJavaString(Object obj) {
            this.toJavaString = obj;
        }

        public Object G$toIdentString() {
            return this.toIdentString;
        }

        public void S$toIdentString(Object obj) {
            this.toIdentString = obj;
        }

        public Object G$getListenerCount() {
            return this.getListenerCount;
        }

        public void S$getListenerCount(Object obj) {
            this.getListenerCount = obj;
        }

        public Object G$dumpCounters() {
            return this.dumpCounters;
        }

        public void S$dumpCounters(Object obj) {
            this.dumpCounters = obj;
        }

        public Object G$getEventQueueCapacity() {
            return this.getEventQueueCapacity;
        }

        public void S$getEventQueueCapacity(Object obj) {
            this.getEventQueueCapacity = obj;
        }

        public Object G$setEventQueueCapacity() {
            return this.setEventQueueCapacity;
        }

        public void S$setEventQueueCapacity(Object obj) {
            this.setEventQueueCapacity = obj;
        }

        public Object G$addRuntimeEvent() {
            return this.addRuntimeEvent;
        }

        public void S$addRuntimeEvent(Object obj) {
            this.addRuntimeEvent = obj;
        }

        public Object G$expandEventQueueCapacity() {
            return this.expandEventQueueCapacity;
        }

        public void S$expandEventQueueCapacity(Object obj) {
            this.expandEventQueueCapacity = obj;
        }

        public Object G$clearRuntimeEvents() {
            return this.clearRuntimeEvents;
        }

        public void S$clearRuntimeEvents(Object obj) {
            this.clearRuntimeEvents = obj;
        }

        public Object G$removeRuntimeEvent() {
            return this.removeRuntimeEvent;
        }

        public void S$removeRuntimeEvent(Object obj) {
            this.removeRuntimeEvent = obj;
        }

        public Object G$getRuntimeEvents() {
            return this.getRuntimeEvents;
        }

        public void S$getRuntimeEvents(Object obj) {
            this.getRuntimeEvents = obj;
        }

        public Object G$getLastRuntimeEvent() {
            return this.getLastRuntimeEvent;
        }

        public void S$getLastRuntimeEvent(Object obj) {
            this.getLastRuntimeEvent = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeDebug.Constructor.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeDebug$Constructor.class
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
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeDebug.Constructor.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeDebug$Constructor.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeDebug.Constructor.<init>():void");
        }
    }

    static {
        $clinit$();
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    private NativeDebug() {
        throw new UnsupportedOperationException();
    }

    public static Object getArrayDataClass(Object obj, Object obj2) {
        try {
            return ((ScriptObject) obj2).getArray().getClass();
        } catch (ClassCastException unused) {
            return ScriptRuntime.UNDEFINED;
        }
    }

    public static Object getArrayData(Object obj, Object obj2) {
        try {
            return ((ScriptObject) obj2).getArray();
        } catch (ClassCastException unused) {
            return ScriptRuntime.UNDEFINED;
        }
    }

    public static Object getContext(Object obj) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission(Context.NASHORN_GET_CONTEXT));
        }
        return Global.getThisContext();
    }

    public static Object map(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return ((ScriptObject) obj2).getMap();
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object equalWithoutType(Object obj, Object obj2, Object obj3) {
        return Boolean.valueOf(((PropertyMap) obj2).equalsWithoutType((PropertyMap) obj3));
    }

    public static Object diffPropertyMaps(Object obj, Object obj2, Object obj3) {
        return PropertyMap.diff((PropertyMap) obj2, (PropertyMap) obj3);
    }

    public static Object getClass(Object obj, Object obj2) {
        if (obj2 != null) {
            return obj2.getClass();
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static boolean equals(Object obj, Object obj2, Object obj3) {
        return Objects.equals(obj2, obj3);
    }

    public static String toJavaString(Object obj, Object obj2) {
        return Objects.toString(obj2);
    }

    public static String toIdentString(Object obj, Object obj2) {
        if (obj2 == null) {
            return Configurator.NULL;
        }
        return obj2.getClass() + "@" + Integer.toHexString(System.identityHashCode(obj2));
    }

    public static int getListenerCount(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return PropertyListeners.getListenerCount((ScriptObject) obj2);
        }
        return 0;
    }

    public static Object dumpCounters(Object obj) {
        PrintWriter currentErr = Context.getCurrentErr();
        currentErr.println("ScriptObject count " + ScriptObject.getCount());
        currentErr.println("Scope count " + Scope.getScopeCount());
        currentErr.println("ScriptObject listeners added " + PropertyListeners.getListenersAdded());
        currentErr.println("ScriptObject listeners removed " + PropertyListeners.getListenersRemoved());
        currentErr.println("ScriptFunction constructor calls " + ScriptFunction.getConstructorCount());
        currentErr.println("ScriptFunction invokes " + ScriptFunction.getInvokes());
        currentErr.println("ScriptFunction allocations " + ScriptFunction.getAllocations());
        currentErr.println("PropertyMap count " + PropertyMap.getCount());
        currentErr.println("PropertyMap cloned " + PropertyMap.getClonedCount());
        currentErr.println("PropertyMap history hit " + PropertyMap.getHistoryHit());
        currentErr.println("PropertyMap proto invalidations " + PropertyMap.getProtoInvalidations());
        currentErr.println("PropertyMap proto history hit " + PropertyMap.getProtoHistoryHit());
        currentErr.println("PropertyMap setProtoNewMapCount " + PropertyMap.getSetProtoNewMapCount());
        currentErr.println("Callsite count " + LinkerCallSite.getCount());
        currentErr.println("Callsite misses " + LinkerCallSite.getMissCount());
        currentErr.println("Callsite misses by site at " + LinkerCallSite.getMissSamplingPercentage() + "%");
        LinkerCallSite.getMissCounts(currentErr);
        return ScriptRuntime.UNDEFINED;
    }

    public static Object getEventQueueCapacity(Object obj) {
        Integer numValueOf;
        ScriptObject scriptObject = (ScriptObject) obj;
        if (scriptObject.has(EVENT_QUEUE_CAPACITY)) {
            numValueOf = Integer.valueOf(JSType.toInt32(scriptObject.get(EVENT_QUEUE_CAPACITY)));
        } else {
            Integer numValueOf2 = Integer.valueOf(RuntimeEvent.RUNTIME_EVENT_QUEUE_SIZE);
            numValueOf = numValueOf2;
            setEventQueueCapacity(obj, numValueOf2);
        }
        return numValueOf;
    }

    public static void setEventQueueCapacity(Object obj, Object obj2) {
        ((ScriptObject) obj).set(EVENT_QUEUE_CAPACITY, obj2, 2);
    }

    public static void addRuntimeEvent(Object obj, Object obj2) {
        LinkedList eventQueue = getEventQueue(obj);
        int iIntValue = ((Integer) getEventQueueCapacity(obj)).intValue();
        while (eventQueue.size() >= iIntValue) {
            eventQueue.removeFirst();
        }
        eventQueue.addLast((RuntimeEvent) obj2);
    }

    public static void expandEventQueueCapacity(Object obj, Object obj2) {
        LinkedList eventQueue = getEventQueue(obj);
        int int32 = JSType.toInt32(obj2);
        while (eventQueue.size() > int32) {
            eventQueue.removeFirst();
        }
        setEventQueueCapacity(obj, Integer.valueOf(int32));
    }

    public static void clearRuntimeEvents(Object obj) {
        getEventQueue(obj).clear();
    }

    public static void removeRuntimeEvent(Object obj, Object obj2) {
        RuntimeEvent runtimeEvent = (RuntimeEvent) obj2;
        if (!getEventQueue(obj).remove(runtimeEvent)) {
            throw new IllegalStateException("runtime event " + runtimeEvent + " was not in event queue");
        }
    }

    public static Object getRuntimeEvents(Object obj) {
        LinkedList eventQueue = getEventQueue(obj);
        return eventQueue.toArray(new RuntimeEvent[eventQueue.size()]);
    }

    public static Object getLastRuntimeEvent(Object obj) {
        LinkedList eventQueue = getEventQueue(obj);
        if (eventQueue.isEmpty()) {
            return null;
        }
        return (RuntimeEvent) eventQueue.getLast();
    }

    private static LinkedList getEventQueue(Object obj) {
        LinkedList linkedList;
        if (((ScriptObject) obj).has(EVENT_QUEUE)) {
            linkedList = (LinkedList) ((ScriptObject) obj).get(EVENT_QUEUE);
        } else {
            LinkedList linkedList2 = new LinkedList();
            linkedList = linkedList2;
            ((ScriptObject) obj).set(EVENT_QUEUE, linkedList2, 2);
        }
        return linkedList;
    }
}
