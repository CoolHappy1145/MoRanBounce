package org.spongepowered.asm.util.perf;

import com.google.common.base.Joiner;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.transformer.ActivityStack;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.PrettyPrinter;

/* loaded from: L-out.jar:org/spongepowered/asm/util/perf/Profiler.class */
public final class Profiler {
    private static final String METRONOME_AGENT_CLASS = "org.spongepowered.metronome.Agent";
    public static final int ROOT = 1;
    public static final int FINE = 2;
    private final Map<String, Section> sections = new TreeMap();
    private final List<String> phases = new ArrayList();
    private final Deque<Section> stack = new LinkedList();
    private boolean active;

    /* loaded from: L-out.jar:org/spongepowered/asm/util/perf/Profiler$Section.class */
    public class Section {
        static final String SEPARATOR_ROOT = " -> ";
        static final String SEPARATOR_CHILD = ".";
        private final String name;
        private boolean root;
        private boolean fine;
        protected boolean invalidated;
        private String info;
        final Profiler this$0;

        Section(Profiler profiler, String str) {
            this.this$0 = profiler;
            this.name = str;
            this.info = str;
        }

        Section invalidate() {
            this.invalidated = true;
            return this;
        }

        Section setRoot(boolean z) {
            this.root = z;
            return this;
        }

        public boolean isRoot() {
            return this.root;
        }

        Section setFine(boolean z) {
            this.fine = z;
            return this;
        }

        public boolean isFine() {
            return this.fine;
        }

        public String getName() {
            return this.name;
        }

        public String getBaseName() {
            return this.name;
        }

        public void setInfo(String str) {
            this.info = str;
        }

        public String getInfo() {
            return this.info;
        }

        public Section end() {
            if (!this.invalidated) {
                this.this$0.end(this);
            }
            return this;
        }

        public Section next(String str) {
            end();
            return this.this$0.begin(str);
        }

        public final String toString() {
            return this.name;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/perf/Profiler$LiveSection.class */
    class LiveSection extends Section {
        private int cursor;
        private long[] times;
        private long start;
        private long time;
        private long markedTime;
        private int count;
        private int markedCount;
        final Profiler this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        LiveSection(Profiler profiler, String str, int i) {
            super(profiler, str);
            this.this$0 = profiler;
            this.cursor = 0;
            this.times = new long[0];
            this.start = 0L;
            this.cursor = i;
        }

        Section start() {
            this.start = System.currentTimeMillis();
            return this;
        }

        protected Section stop() {
            if (this.start > 0) {
                this.time += System.currentTimeMillis() - this.start;
            }
            this.start = 0L;
            this.count++;
            return this;
        }

        @Override // org.spongepowered.asm.util.perf.Profiler.Section
        public Section end() {
            stop();
            if (!this.invalidated) {
                this.this$0.end(this);
            }
            return this;
        }

        void mark() {
            if (this.cursor >= this.times.length) {
                this.times = Arrays.copyOf(this.times, this.cursor + 4);
            }
            this.times[this.cursor] = this.time;
            this.markedTime += this.time;
            this.markedCount += this.count;
            this.time = 0L;
            this.count = 0;
            this.cursor++;
        }

        public long getTime() {
            return this.time;
        }

        public long getTotalTime() {
            return this.time + this.markedTime;
        }

        public double getSeconds() {
            return this.time * 0.001d;
        }

        public double getTotalSeconds() {
            return (this.time + this.markedTime) * 0.001d;
        }

        public long[] getTimes() {
            long[] jArr = new long[this.cursor + 1];
            System.arraycopy(this.times, 0, jArr, 0, Math.min(this.times.length, this.cursor));
            jArr[this.cursor] = this.time;
            return jArr;
        }

        public int getCount() {
            return this.count;
        }

        public int getTotalCount() {
            return this.count + this.markedCount;
        }

        public double getAverageTime() {
            if (this.count > 0) {
                return this.time / this.count;
            }
            return 0.0d;
        }

        public double getTotalAverageTime() {
            if (this.count > 0) {
                return (this.time + this.markedTime) / (this.count + this.markedCount);
            }
            return 0.0d;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/perf/Profiler$SubSection.class */
    class SubSection extends LiveSection {
        private final String baseName;
        private final Section root;
        final Profiler this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        SubSection(Profiler profiler, String str, int i, String str2, Section section) {
            super(profiler, str, i);
            this.this$0 = profiler;
            this.baseName = str2;
            this.root = section;
        }

        @Override // org.spongepowered.asm.util.perf.Profiler.Section
        Section invalidate() {
            this.root.invalidate();
            return super.invalidate();
        }

        @Override // org.spongepowered.asm.util.perf.Profiler.Section
        public String getBaseName() {
            return this.baseName;
        }

        @Override // org.spongepowered.asm.util.perf.Profiler.Section
        public void setInfo(String str) {
            this.root.setInfo(str);
            super.setInfo(str);
        }

        Section getDelegate() {
            return this.root;
        }

        /*  JADX ERROR: Types fix failed
            java.lang.NullPointerException
            */
        /* JADX WARN: Not initialized variable reg: 4, insn: 0x0004: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:181), block:B:2:0x0000 */
        @Override // org.spongepowered.asm.util.perf.Profiler.LiveSection
        org.spongepowered.asm.util.perf.Profiler.Section start() {
            /*
                r3 = this;
                r0 = r3
                org.spongepowered.asm.util.perf.Profiler$Section r0 = r0.root
                r1 = r4
                r1 = r3
                org.spongepowered.asm.util.perf.Profiler$Section r1 = super.start()
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.spongepowered.asm.util.perf.Profiler.SubSection.start():org.spongepowered.asm.util.perf.Profiler$Section");
        }

        /*  JADX ERROR: Types fix failed
            java.lang.NullPointerException
            */
        /* JADX WARN: Not initialized variable reg: 4, insn: 0x0004: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:190), block:B:2:0x0000 */
        @Override // org.spongepowered.asm.util.perf.Profiler.LiveSection, org.spongepowered.asm.util.perf.Profiler.Section
        public org.spongepowered.asm.util.perf.Profiler.Section end() {
            /*
                r3 = this;
                r0 = r3
                org.spongepowered.asm.util.perf.Profiler$Section r0 = r0.root
                r1 = r4
                r1 = r3
                org.spongepowered.asm.util.perf.Profiler$Section r1 = super.end()
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.spongepowered.asm.util.perf.Profiler.SubSection.end():org.spongepowered.asm.util.perf.Profiler$Section");
        }

        @Override // org.spongepowered.asm.util.perf.Profiler.Section
        public Section next(String str) {
            super.stop();
            return this.root.next(str);
        }
    }

    public Profiler() {
        this.phases.add("Initial");
    }

    public void setActive(boolean active) {
        if ((!this.active && active) || !active) {
            reset();
        }
        this.active = active;
    }

    public void reset() {
        for (Section section : this.sections.values()) {
            section.invalidate();
        }
        this.sections.clear();
        this.phases.clear();
        this.phases.add("Initial");
        this.stack.clear();
    }

    public Section get(String name) {
        Section section = this.sections.get(name);
        if (section == null) {
            section = this.active ? new LiveSection(this, name, this.phases.size() - 1) : new Section(this, name);
            this.sections.put(name, section);
        }
        return section;
    }

    private Section getSubSection(String name, String baseName, Section root) {
        Section section = this.sections.get(name);
        if (section == null) {
            section = new SubSection(this, name, this.phases.size() - 1, baseName, root);
            this.sections.put(name, section);
        }
        return section;
    }

    boolean isHead(Section section) {
        return this.stack.peek() == section;
    }

    public Section begin(String... path) {
        return begin(0, path);
    }

    public Section begin(int flags, String... path) {
        return begin(flags, Joiner.on('.').join(path));
    }

    public Section begin(String name) {
        return begin(0, name);
    }

    public Section begin(int flags, String name) {
        boolean root = (flags & 1) != 0;
        boolean fine = (flags & 2) != 0;
        String path = name;
        Section head = this.stack.peek();
        if (head != null) {
            path = head.getName() + (root ? ActivityStack.GLUE_STRING : ".") + path;
            if (head.isRoot() && !root) {
                int pos = head.getName().lastIndexOf(ActivityStack.GLUE_STRING);
                name = (pos > -1 ? head.getName().substring(pos + 4) : head.getName()) + "." + name;
                root = true;
            }
        }
        Section section = get(root ? name : path);
        if (root && head != null && this.active) {
            section = getSubSection(path, head.getName(), section);
        }
        section.setFine(fine).setRoot(root);
        this.stack.push(section);
        return section.start();
    }

    void end(Section section) {
        try {
            Section head = this.stack.pop();
            Section next = head;
            while (next != section) {
                if (next != null || !this.active) {
                    next = this.stack.pop();
                } else {
                    if (head == null) {
                        throw new IllegalStateException("Attempted to pop " + section + " but the stack is empty");
                    }
                    throw new IllegalStateException("Attempted to pop " + section + " which was not in the stack, head was " + head);
                }
            }
        } catch (NoSuchElementException e) {
            if (this.active) {
                throw new IllegalStateException("Attempted to pop " + section + " but the stack is empty");
            }
        }
    }

    public void mark(String phase) {
        long currentPhaseTime = 0;
        for (Section section : this.sections.values()) {
            currentPhaseTime += section.getTime();
        }
        if (currentPhaseTime == 0) {
            int size = this.phases.size();
            this.phases.set(size - 1, phase);
            return;
        }
        this.phases.add(phase);
        for (Section section2 : this.sections.values()) {
            section2.mark();
        }
    }

    public Collection<Section> getSections() {
        return Collections.unmodifiableCollection(this.sections.values());
    }

    public PrettyPrinter printer(boolean includeFine, boolean group) {
        PrettyPrinter printer = new PrettyPrinter();
        int colCount = this.phases.size() + 4;
        int[] columns = {0, 1, 2, colCount - 2, colCount - 1};
        Object[] headers = new Object[colCount * 2];
        int col = 0;
        int i = 0;
        while (true) {
            int pos = i;
            if (col >= colCount) {
                break;
            }
            headers[pos + 1] = PrettyPrinter.Alignment.RIGHT;
            if (col == columns[0]) {
                headers[pos] = (group ? "" : "  ") + "Section";
                headers[pos + 1] = PrettyPrinter.Alignment.LEFT;
            } else if (col == columns[1]) {
                headers[pos] = "    TOTAL";
            } else if (col == columns[3]) {
                headers[pos] = "    Count";
            } else if (col == columns[4]) {
                headers[pos] = "Avg. ";
            } else if (col - columns[2] < this.phases.size()) {
                headers[pos] = this.phases.get(col - columns[2]);
            } else {
                headers[pos] = "";
            }
            col++;
            i = col * 2;
        }
        printer.table(headers).m71th().m76hr().add();
        for (Section section : this.sections.values()) {
            if (!section.isFine() || includeFine) {
                if (!group || section.getDelegate() == section) {
                    printSectionRow(printer, colCount, columns, section, group);
                    if (group) {
                        for (Section subSection : this.sections.values()) {
                            Section delegate = subSection.getDelegate();
                            if (!subSection.isFine() || includeFine) {
                                if (delegate == section && delegate != subSection) {
                                    printSectionRow(printer, colCount, columns, subSection, group);
                                }
                            }
                        }
                    }
                }
            }
        }
        return printer.add();
    }

    private void printSectionRow(PrettyPrinter printer, int colCount, int[] columns, Section section, boolean group) {
        boolean isDelegate = section.getDelegate() != section;
        Object[] values = new Object[colCount];
        int col = 1;
        if (group) {
            values[0] = isDelegate ? "  > " + section.getBaseName() : section.getName();
        } else {
            values[0] = (isDelegate ? "+ " : "  ") + section.getName();
        }
        long[] times = section.getTimes();
        for (long time : times) {
            if (col == columns[1]) {
                int i = col;
                col++;
                values[i] = section.getTotalTime() + " ms";
            }
            if (col >= columns[2] && col < values.length) {
                int i2 = col;
                col++;
                values[i2] = time + " ms";
            }
        }
        values[columns[3]] = Integer.valueOf(section.getTotalCount());
        values[columns[4]] = new DecimalFormat("   ###0.000 ms").format(section.getTotalAverageTime());
        for (int i3 = 0; i3 < values.length; i3++) {
            if (values[i3] == null) {
                values[i3] = "-";
            }
        }
        printer.m73tr(values);
    }

    public void printSummary() {
        DecimalFormat threedp = new DecimalFormat("(###0.000");
        DecimalFormat onedp = new DecimalFormat("(###0.0");
        PrettyPrinter printer = printer(false, false);
        long prepareTime = get("mixin.prepare").getTotalTime();
        long readTime = get("mixin.read").getTotalTime();
        long applyTime = get("mixin.apply").getTotalTime();
        long writeTime = get("mixin.write").getTotalTime();
        long totalMixinTime = get(MixinLaunchPlugin.NAME).getTotalTime();
        long loadTime = get("class.load").getTotalTime();
        long transformTime = get("class.transform").getTotalTime();
        long exportTime = get("mixin.debug.export").getTotalTime();
        long actualTime = ((totalMixinTime - loadTime) - transformTime) - exportTime;
        double timeSliceMixin = (actualTime / totalMixinTime) * 100.0d;
        double timeSliceLoad = (loadTime / totalMixinTime) * 100.0d;
        double timeSliceTransform = (transformTime / totalMixinTime) * 100.0d;
        double timeSliceExport = (exportTime / totalMixinTime) * 100.0d;
        long worstTransformerTime = 0;
        Section worstTransformer = null;
        for (Section section : getSections()) {
            long transformerTime = section.getName().startsWith("class.transform.") ? section.getTotalTime() : 0L;
            if (transformerTime > worstTransformerTime) {
                worstTransformerTime = transformerTime;
                worstTransformer = section;
            }
        }
        printer.m76hr().add("Summary").m76hr().add();
        printer.m74kv("Total mixin time", "%9d ms %12s seconds)", new Object[]{Long.valueOf(totalMixinTime), threedp.format(totalMixinTime * 0.001d)}).add();
        printer.m74kv("Preparing mixins", "%9d ms %12s seconds)", new Object[]{Long.valueOf(prepareTime), threedp.format(prepareTime * 0.001d)});
        printer.m74kv("Reading input", "%9d ms %12s seconds)", new Object[]{Long.valueOf(readTime), threedp.format(readTime * 0.001d)});
        printer.m74kv("Applying mixins", "%9d ms %12s seconds)", new Object[]{Long.valueOf(applyTime), threedp.format(applyTime * 0.001d)});
        printer.m74kv("Writing output", "%9d ms %12s seconds)", new Object[]{Long.valueOf(writeTime), threedp.format(writeTime * 0.001d)}).add();
        printer.m75kv("of which", "");
        printer.m74kv("Time spent loading from disk", "%9d ms %12s seconds)", new Object[]{Long.valueOf(loadTime), threedp.format(loadTime * 0.001d)});
        printer.m74kv("Time spent transforming classes", "%9d ms %12s seconds)", new Object[]{Long.valueOf(transformTime), threedp.format(transformTime * 0.001d)}).add();
        if (worstTransformer != null) {
            printer.m75kv("Worst transformer", worstTransformer.getName());
            printer.m75kv("Class", worstTransformer.getInfo());
            printer.m74kv("Time spent", "%s seconds", new Object[]{Double.valueOf(worstTransformer.getTotalSeconds())});
            printer.m74kv("called", "%d times", new Object[]{Integer.valueOf(worstTransformer.getTotalCount())}).add();
        }
        printer.m74kv("   Time allocation:     Processing mixins", "%9d ms %10s%% of total)", new Object[]{Long.valueOf(actualTime), onedp.format(timeSliceMixin)});
        printer.m74kv("Loading classes", "%9d ms %10s%% of total)", new Object[]{Long.valueOf(loadTime), onedp.format(timeSliceLoad)});
        printer.m74kv("Running transformers", "%9d ms %10s%% of total)", new Object[]{Long.valueOf(transformTime), onedp.format(timeSliceTransform)});
        if (exportTime > 0) {
            printer.m74kv("Exporting classes (debug)", "%9d ms %10s%% of total)", new Object[]{Long.valueOf(exportTime), onedp.format(timeSliceExport)});
        }
        printer.add();
        try {
            Class<?> agent = MixinService.getService().getClassProvider().findAgentClass(METRONOME_AGENT_CLASS, false);
            Method mdGetTimes = agent.getDeclaredMethod("getTimes", new Class[0]);
            Map<String, Long> times = (Map) mdGetTimes.invoke(null, new Object[0]);
            printer.m76hr().add("Transformer Times").m76hr().add();
            int longest = 10;
            Iterator<Map.Entry<String, Long>> it = times.entrySet().iterator();
            while (it.hasNext()) {
                longest = Math.max(longest, it.next().getKey().length());
            }
            for (Map.Entry<String, Long> entry : times.entrySet()) {
                String name = entry.getKey();
                long mixinTime = 0;
                Iterator<Section> it2 = getSections().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    Section section2 = it2.next();
                    if (name.equals(section2.getInfo())) {
                        mixinTime = section2.getTotalTime();
                        break;
                    }
                }
                if (mixinTime > 0) {
                    printer.add("%-" + longest + "s %8s ms %8s ms in mixin)", new Object[]{name, Long.valueOf(entry.getValue().longValue() + mixinTime), "(" + mixinTime});
                } else {
                    printer.add("%-" + longest + "s %8s ms", new Object[]{name, entry.getValue()});
                }
            }
            printer.add();
        } catch (Throwable th) {
        }
        printer.print();
    }
}
