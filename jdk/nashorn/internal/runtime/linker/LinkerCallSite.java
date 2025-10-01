package jdk.nashorn.internal.runtime.linker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.ChainedCallSite;
import jdk.internal.dynalink.DynamicLinker;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/LinkerCallSite.class */
public class LinkerCallSite extends ChainedCallSite {
    public static final int ARGLIMIT = 250;
    private int catchInvalidations;
    private static LongAdder count;
    private static LongAdder missCount;
    private static final String PROFILEFILE = Options.getStringProperty("nashorn.profilefile", "NashornProfile.txt");
    private static final MethodHandle INCREASE_MISS_COUNTER = Lookup.f31MH.findStatic(MethodHandles.lookup(), LinkerCallSite.class, "increaseMissCount", Lookup.f31MH.type(Object.class, new Class[]{String.class, Object.class}));
    private static final MethodHandle ON_CATCH_INVALIDATION = Lookup.f31MH.findStatic(MethodHandles.lookup(), LinkerCallSite.class, "onCatchInvalidation", Lookup.f31MH.type(ChainedCallSite.class, new Class[]{LinkerCallSite.class}));
    private static final HashMap missCounts = new HashMap();

    /* renamed from: r */
    private static final Random f61r = new Random();
    private static final int missSamplingPercentage = Options.getIntProperty("nashorn.tcs.miss.samplePercent", 1);

    static {
        if (Context.DEBUG) {
            count = new LongAdder();
            missCount = new LongAdder();
        }
    }

    LinkerCallSite(NashornCallSiteDescriptor nashornCallSiteDescriptor) {
        super(nashornCallSiteDescriptor);
        if (Context.DEBUG) {
            count.increment();
        }
    }

    @Override // jdk.internal.dynalink.ChainedCallSite
    protected MethodHandle getPruneCatches() {
        return Lookup.f31MH.filterArguments(super.getPruneCatches(), 0, new MethodHandle[]{ON_CATCH_INVALIDATION});
    }

    private static ChainedCallSite onCatchInvalidation(LinkerCallSite linkerCallSite) {
        linkerCallSite.catchInvalidations++;
        return linkerCallSite;
    }

    public static int getCatchInvalidationCount(Object obj) {
        if (obj instanceof LinkerCallSite) {
            return ((LinkerCallSite) obj).catchInvalidations;
        }
        return 0;
    }

    static LinkerCallSite newLinkerCallSite(MethodHandles.Lookup lookup, String str, MethodType methodType, int i) {
        NashornCallSiteDescriptor nashornCallSiteDescriptor = NashornCallSiteDescriptor.get(lookup, str, methodType, i);
        if (nashornCallSiteDescriptor.isProfile()) {
            return ProfilingLinkerCallSite.newProfilingLinkerCallSite(nashornCallSiteDescriptor);
        }
        if (nashornCallSiteDescriptor.isTrace()) {
            return new TracingLinkerCallSite(nashornCallSiteDescriptor);
        }
        return new LinkerCallSite(nashornCallSiteDescriptor);
    }

    public String toString() {
        return getDescriptor().toString();
    }

    public NashornCallSiteDescriptor getNashornDescriptor() {
        return (NashornCallSiteDescriptor) getDescriptor();
    }

    @Override // jdk.internal.dynalink.ChainedCallSite, jdk.internal.dynalink.RelinkableCallSite
    public void relink(GuardedInvocation guardedInvocation, MethodHandle methodHandle) {
        super.relink(guardedInvocation, getDebuggingRelink(methodHandle));
    }

    @Override // jdk.internal.dynalink.ChainedCallSite, jdk.internal.dynalink.RelinkableCallSite
    public void resetAndRelink(GuardedInvocation guardedInvocation, MethodHandle methodHandle) {
        super.resetAndRelink(guardedInvocation, getDebuggingRelink(methodHandle));
    }

    private MethodHandle getDebuggingRelink(MethodHandle methodHandle) {
        return Context.DEBUG ? Lookup.f31MH.filterArguments(methodHandle, 0, new MethodHandle[]{getIncreaseMissCounter(methodHandle.type().parameterType(0))}) : methodHandle;
    }

    private MethodHandle getIncreaseMissCounter(Class cls) {
        MethodHandle methodHandleBindTo = Lookup.f31MH.bindTo(INCREASE_MISS_COUNTER, getDescriptor().getName() + " @ " + getScriptLocation());
        if (cls == Object.class) {
            return methodHandleBindTo;
        }
        return Lookup.f31MH.asType(methodHandleBindTo, methodHandleBindTo.type().changeParameterType(0, (Class<?>) cls).changeReturnType((Class<?>) cls));
    }

    private static String getScriptLocation() {
        StackTraceElement linkedCallSiteLocation = DynamicLinker.getLinkedCallSiteLocation();
        return linkedCallSiteLocation == null ? "unknown location" : linkedCallSiteLocation.getFileName() + CallSiteDescriptor.TOKEN_DELIMITER + linkedCallSiteLocation.getLineNumber();
    }

    public static Object increaseMissCount(String str, Object obj) {
        missCount.increment();
        if (f61r.nextInt(100) < missSamplingPercentage) {
            AtomicInteger atomicInteger = (AtomicInteger) missCounts.get(str);
            if (atomicInteger == null) {
                missCounts.put(str, new AtomicInteger(1));
            } else {
                atomicInteger.incrementAndGet();
            }
        }
        return obj;
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/LinkerCallSite$ProfilingLinkerCallSite.class */
    private static class ProfilingLinkerCallSite extends LinkerCallSite {
        private long startTime;
        private int depth;
        private long totalTime;
        private long hitCount;
        private static LinkedList profileCallSites = null;
        private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
        private static final MethodHandle PROFILEENTRY = Lookup.f31MH.findVirtual(LOOKUP, ProfilingLinkerCallSite.class, "profileEntry", Lookup.f31MH.type(Object.class, new Class[]{Object.class}));
        private static final MethodHandle PROFILEEXIT = Lookup.f31MH.findVirtual(LOOKUP, ProfilingLinkerCallSite.class, "profileExit", Lookup.f31MH.type(Object.class, new Class[]{Object.class}));
        private static final MethodHandle PROFILEVOIDEXIT = Lookup.f31MH.findVirtual(LOOKUP, ProfilingLinkerCallSite.class, "profileVoidExit", Lookup.f31MH.type(Void.TYPE, new Class[0]));

        ProfilingLinkerCallSite(NashornCallSiteDescriptor nashornCallSiteDescriptor) {
            super(nashornCallSiteDescriptor);
        }

        public static ProfilingLinkerCallSite newProfilingLinkerCallSite(NashornCallSiteDescriptor nashornCallSiteDescriptor) {
            if (profileCallSites == null) {
                profileCallSites = new LinkedList();
                Runtime.getRuntime().addShutdownHook(new Thread(new ProfileDumper()));
            }
            ProfilingLinkerCallSite profilingLinkerCallSite = new ProfilingLinkerCallSite(nashornCallSiteDescriptor);
            profileCallSites.add(profilingLinkerCallSite);
            return profilingLinkerCallSite;
        }

        @Override // java.lang.invoke.MutableCallSite, java.lang.invoke.CallSite
        public void setTarget(MethodHandle methodHandle) {
            MethodHandle methodHandleFilterReturnValue;
            MethodType methodTypeType = type();
            boolean z = methodTypeType.returnType() == Void.TYPE;
            Class<?> clsParameterType = methodHandle.type().parameterType(0);
            MethodHandle methodHandleBindTo = Lookup.f31MH.bindTo(PROFILEENTRY, this);
            if (clsParameterType != Object.class) {
                methodHandleBindTo = methodHandleBindTo.asType(MethodType.methodType(clsParameterType, clsParameterType));
            }
            MethodHandle methodHandleFilterArguments = Lookup.f31MH.filterArguments(methodHandle, 0, new MethodHandle[]{methodHandleBindTo});
            if (z) {
                methodHandleFilterReturnValue = Lookup.f31MH.filterReturnValue(methodHandleFilterArguments, Lookup.f31MH.bindTo(PROFILEVOIDEXIT, this));
            } else {
                methodHandleFilterReturnValue = Lookup.f31MH.filterReturnValue(methodHandleFilterArguments, Lookup.f31MH.asType(Lookup.f31MH.bindTo(PROFILEEXIT, this), Lookup.f31MH.type(methodTypeType.returnType(), new Class[]{methodTypeType.returnType()})));
            }
            super.setTarget(methodHandleFilterReturnValue);
        }

        public Object profileEntry(Object obj) {
            if (this.depth == 0) {
                this.startTime = System.nanoTime();
            }
            this.depth++;
            this.hitCount++;
            return obj;
        }

        public Object profileExit(Object obj) {
            this.depth--;
            if (this.depth == 0) {
                this.totalTime += System.nanoTime() - this.startTime;
            }
            return obj;
        }

        public void profileVoidExit() {
            this.depth--;
            if (this.depth == 0) {
                this.totalTime += System.nanoTime() - this.startTime;
            }
        }

        /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/LinkerCallSite$ProfilingLinkerCallSite$ProfileDumper.class */
        static class ProfileDumper implements Runnable {
            ProfileDumper() {
            }

            @Override // java.lang.Runnable
            public void run() {
                PrintWriter currentErr = null;
                boolean z = false;
                try {
                    try {
                        currentErr = new PrintWriter(new FileOutputStream(LinkerCallSite.PROFILEFILE));
                        z = true;
                    } catch (FileNotFoundException unused) {
                        currentErr = Context.getCurrentErr();
                    }
                    dump(currentErr);
                    if (currentErr != null && z) {
                        currentErr.close();
                    }
                } catch (Throwable th) {
                    if (currentErr != null && z) {
                        currentErr.close();
                    }
                    throw th;
                }
            }

            private static void dump(PrintWriter printWriter) {
                int i = 0;
                Iterator it = ProfilingLinkerCallSite.profileCallSites.iterator();
                while (it.hasNext()) {
                    ProfilingLinkerCallSite profilingLinkerCallSite = (ProfilingLinkerCallSite) it.next();
                    int i2 = i;
                    i++;
                    printWriter.println("" + i2 + '\t' + profilingLinkerCallSite.getDescriptor().getName() + '\t' + profilingLinkerCallSite.totalTime + '\t' + profilingLinkerCallSite.hitCount);
                }
            }
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/LinkerCallSite$TracingLinkerCallSite.class */
    private static class TracingLinkerCallSite extends LinkerCallSite {
        private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
        private static final MethodHandle TRACEOBJECT = Lookup.f31MH.findVirtual(LOOKUP, TracingLinkerCallSite.class, "traceObject", Lookup.f31MH.type(Object.class, new Class[]{MethodHandle.class, Object[].class}));
        private static final MethodHandle TRACEVOID = Lookup.f31MH.findVirtual(LOOKUP, TracingLinkerCallSite.class, "traceVoid", Lookup.f31MH.type(Void.TYPE, new Class[]{MethodHandle.class, Object[].class}));
        private static final MethodHandle TRACEMISS = Lookup.f31MH.findVirtual(LOOKUP, TracingLinkerCallSite.class, "traceMiss", Lookup.f31MH.type(Void.TYPE, new Class[]{String.class, Object[].class}));

        TracingLinkerCallSite(NashornCallSiteDescriptor desc) {
            super(desc);
        }

        @Override // java.lang.invoke.MutableCallSite, java.lang.invoke.CallSite
        public void setTarget(MethodHandle newTarget) {
            if (!getNashornDescriptor().isTraceEnterExit()) {
                super.setTarget(newTarget);
                return;
            }
            MethodType type = type();
            boolean isVoid = type.returnType() == Void.TYPE;
            MethodHandle traceMethodHandle = isVoid ? TRACEVOID : TRACEOBJECT;
            super.setTarget(Lookup.f31MH.asType(Lookup.f31MH.asCollector(Lookup.f31MH.bindTo(Lookup.f31MH.bindTo(traceMethodHandle, this), newTarget), Object[].class, type.parameterCount()), type));
        }

        @Override // jdk.internal.dynalink.support.AbstractRelinkableCallSite, jdk.internal.dynalink.RelinkableCallSite
        public void initialize(MethodHandle relinkAndInvoke) {
            super.initialize(getFallbackLoggingRelink(relinkAndInvoke));
        }

        @Override // jdk.nashorn.internal.runtime.linker.LinkerCallSite, jdk.internal.dynalink.ChainedCallSite, jdk.internal.dynalink.RelinkableCallSite
        public void relink(GuardedInvocation invocation, MethodHandle relink) {
            super.relink(invocation, getFallbackLoggingRelink(relink));
        }

        @Override // jdk.nashorn.internal.runtime.linker.LinkerCallSite, jdk.internal.dynalink.ChainedCallSite, jdk.internal.dynalink.RelinkableCallSite
        public void resetAndRelink(GuardedInvocation invocation, MethodHandle relink) {
            super.resetAndRelink(invocation, getFallbackLoggingRelink(relink));
        }

        private MethodHandle getFallbackLoggingRelink(MethodHandle relink) {
            if (!getNashornDescriptor().isTraceMisses()) {
                return relink;
            }
            MethodType type = relink.type();
            return Lookup.f31MH.foldArguments(relink, Lookup.f31MH.asType(Lookup.f31MH.asCollector(Lookup.f31MH.insertArguments(TRACEMISS, 0, new Object[]{this, "MISS " + LinkerCallSite.getScriptLocation() + " "}), Object[].class, type.parameterCount()), type.changeReturnType(Void.TYPE)));
        }

        private void printObject(PrintWriter out, Object arg) {
            if (!getNashornDescriptor().isTraceObjects()) {
                out.print(arg instanceof ScriptObject ? "ScriptObject" : arg);
                return;
            }
            if (arg instanceof ScriptObject) {
                ScriptObject object = (ScriptObject) arg;
                boolean isFirst = true;
                Set<Object> keySet = object.keySet();
                if (keySet.isEmpty()) {
                    out.print(ScriptRuntime.safeToString(arg));
                    return;
                }
                out.print("{ ");
                for (Object key : keySet) {
                    if (!isFirst) {
                        out.print(", ");
                    }
                    out.print(key);
                    out.print(CallSiteDescriptor.TOKEN_DELIMITER);
                    Object value = object.get(key);
                    if (value instanceof ScriptObject) {
                        out.print("...");
                    } else {
                        printObject(out, value);
                    }
                    isFirst = false;
                }
                out.print(" }");
                return;
            }
            out.print(ScriptRuntime.safeToString(arg));
        }

        private void tracePrint(PrintWriter out, String tag, Object[] args, Object result) {
            out.print(Debug.m11id(this) + " TAG " + tag);
            out.print(getDescriptor().getName() + "(");
            if (args.length > 0) {
                printObject(out, args[0]);
                for (int i = 1; i < args.length; i++) {
                    Object arg = args[i];
                    out.print(", ");
                    if (!(arg instanceof ScriptObject) || !((ScriptObject) arg).isScope()) {
                        printObject(out, arg);
                    } else {
                        out.print("SCOPE");
                    }
                }
            }
            out.print(")");
            if (tag.equals("EXIT  ")) {
                out.print(" --> ");
                printObject(out, result);
            }
            out.println();
        }

        public Object traceObject(MethodHandle mh, Object... args) throws Throwable {
            PrintWriter out = Context.getCurrentErr();
            tracePrint(out, "ENTER ", args, null);
            Object result = mh.invokeWithArguments(args);
            tracePrint(out, "EXIT  ", args, result);
            return result;
        }

        public void traceVoid(MethodHandle mh, Object... args) throws Throwable {
            PrintWriter out = Context.getCurrentErr();
            tracePrint(out, "ENTER ", args, null);
            mh.invokeWithArguments(args);
            tracePrint(out, "EXIT  ", args, null);
        }

        public void traceMiss(String desc, Object... args) throws Throwable {
            tracePrint(Context.getCurrentErr(), desc, args, null);
        }
    }

    public static long getCount() {
        return count.longValue();
    }

    public static long getMissCount() {
        return missCount.longValue();
    }

    public static int getMissSamplingPercentage() {
        return missSamplingPercentage;
    }

    public static void getMissCounts(PrintWriter printWriter) {
        ArrayList arrayList = new ArrayList(missCounts.entrySet());
        Collections.sort(arrayList, new Comparator() { // from class: jdk.nashorn.internal.runtime.linker.LinkerCallSite.1
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                return compare((Map.Entry) obj, (Map.Entry) obj2);
            }

            public int compare(Map.Entry entry, Map.Entry entry2) {
                return ((AtomicInteger) entry2.getValue()).get() - ((AtomicInteger) entry.getValue()).get();
            }
        });
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            printWriter.println("  " + ((String) entry.getKey()) + "\t" + ((AtomicInteger) entry.getValue()).get());
        }
    }
}
