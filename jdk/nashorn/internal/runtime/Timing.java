package jdk.nashorn.internal.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.function.Supplier;
import jdk.nashorn.internal.codegen.CompileUnit;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "time")
/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Timing.class */
public final class Timing implements Loggable {
    private DebugLogger log;
    private TimeSupplier timeSupplier;
    private final boolean isEnabled;
    private final long startTime = System.nanoTime();
    private static final String LOGGER_NAME;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Timing.class.desiredAssertionStatus();
        LOGGER_NAME = ((Logger) Timing.class.getAnnotation(Logger.class)).name();
    }

    public Timing(boolean z) {
        this.isEnabled = z;
    }

    public String getLogInfo() {
        if ($assertionsDisabled || isEnabled()) {
            return this.timeSupplier.get();
        }
        throw new AssertionError();
    }

    public String[] getLogInfoLines() {
        if ($assertionsDisabled || isEnabled()) {
            return this.timeSupplier.getStrings();
        }
        throw new AssertionError();
    }

    boolean isEnabled() {
        return this.isEnabled;
    }

    public void accumulateTime(String str, long j) {
        if (isEnabled()) {
            ensureInitialized(Context.getContextTrusted());
            this.timeSupplier.accumulateTime(str, j);
        }
    }

    private DebugLogger ensureInitialized(Context context) {
        if (isEnabled() && this.log == null) {
            this.log = initLogger(context);
            if (this.log.isEnabled()) {
                this.timeSupplier = new TimeSupplier(this);
                Runtime.getRuntime().addShutdownHook(new Thread(this) { // from class: jdk.nashorn.internal.runtime.Timing.1
                    final Timing this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() throws IOException {
                        StringBuilder sb = new StringBuilder();
                        for (String str : this.this$0.timeSupplier.getStrings()) {
                            sb.append('[').append(Timing.getLoggerName()).append("] ").append(str).append('\n');
                        }
                        System.err.print(sb);
                    }
                });
            }
        }
        return this.log;
    }

    static String getLoggerName() {
        return LOGGER_NAME;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return context.getLogger(getClass());
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    public static String toMillisPrint(long j) {
        return Long.toString(TimeUnit.NANOSECONDS.toMillis(j));
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Timing$TimeSupplier.class */
    final class TimeSupplier implements Supplier {
        private final Map timings = new ConcurrentHashMap();
        private final LinkedBlockingQueue orderedTimingNames = new LinkedBlockingQueue();
        private final Function newTimingCreator = new Function(this) { // from class: jdk.nashorn.internal.runtime.Timing.TimeSupplier.1
            final TimeSupplier this$1;

            {
                this.this$1 = this;
            }

            @Override // java.util.function.Function
            public Object apply(Object obj) {
                return apply((String) obj);
            }

            public LongAdder apply(String str) {
                this.this$1.orderedTimingNames.add(str);
                return new LongAdder();
            }
        };
        final Timing this$0;

        TimeSupplier(Timing timing) {
            this.this$0 = timing;
        }

        @Override // java.util.function.Supplier
        public Object get() {
            return get();
        }

        String[] getStrings() throws IOException {
            ArrayList arrayList = new ArrayList();
            BufferedReader bufferedReader = new BufferedReader(new StringReader(get()));
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        arrayList.add(line);
                    } else {
                        return (String[]) arrayList.toArray(new String[arrayList.size()]);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override // java.util.function.Supplier
        public String get() {
            long jNanoTime = System.nanoTime();
            long j = 0;
            int iMax = 0;
            int iMax2 = 0;
            for (Map.Entry entry : this.timings.entrySet()) {
                iMax = Math.max(iMax, ((String) entry.getKey()).length());
                iMax2 = Math.max(iMax2, Timing.toMillisPrint(((LongAdder) entry.getValue()).longValue()).length());
            }
            int i = iMax + 1;
            StringBuilder sb = new StringBuilder();
            sb.append("Accumulated compilation phase timings:\n\n");
            Iterator it = this.orderedTimingNames.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                int length = sb.length();
                sb.append(str);
                int length2 = sb.length() - length;
                while (true) {
                    int i2 = length2;
                    length2++;
                    if (i2 >= i) {
                        break;
                    }
                    sb.append(' ');
                }
                long jLongValue = ((LongAdder) this.timings.get(str)).longValue();
                String millisPrint = Timing.toMillisPrint(jLongValue);
                int length3 = millisPrint.length();
                for (int i3 = 0; i3 < iMax2 - length3; i3++) {
                    sb.append(' ');
                }
                sb.append(millisPrint).append(" ms\n");
                j += jLongValue;
            }
            long j2 = jNanoTime - this.this$0.startTime;
            sb.append('\n');
            sb.append("Total runtime: ").append(Timing.toMillisPrint(j2)).append(" ms (Non-runtime: ").append(Timing.toMillisPrint(j)).append(" ms [").append((int) ((j * 100.0d) / j2)).append("%])");
            sb.append("\n\nEmitted compile units: ").append(CompileUnit.getEmittedUnitCount());
            return sb.toString();
        }

        private void accumulateTime(String str, long j) {
            ((LongAdder) this.timings.computeIfAbsent(str, this.newTimingCreator)).add(j);
        }
    }
}
