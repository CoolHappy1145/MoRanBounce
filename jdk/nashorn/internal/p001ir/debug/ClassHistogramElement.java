package jdk.nashorn.internal.p001ir.debug;

import java.util.Comparator;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/ClassHistogramElement.class */
public final class ClassHistogramElement {
    public static final Comparator COMPARE_INSTANCES = new Comparator() { // from class: jdk.nashorn.internal.ir.debug.ClassHistogramElement.1
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return compare((ClassHistogramElement) obj, (ClassHistogramElement) obj2);
        }

        public int compare(ClassHistogramElement classHistogramElement, ClassHistogramElement classHistogramElement2) {
            return (int) Math.abs(classHistogramElement.instances - classHistogramElement2.instances);
        }
    };
    public static final Comparator COMPARE_BYTES = new Comparator() { // from class: jdk.nashorn.internal.ir.debug.ClassHistogramElement.2
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return compare((ClassHistogramElement) obj, (ClassHistogramElement) obj2);
        }

        public int compare(ClassHistogramElement classHistogramElement, ClassHistogramElement classHistogramElement2) {
            return (int) Math.abs(classHistogramElement.bytes - classHistogramElement2.bytes);
        }
    };
    public static final Comparator COMPARE_CLASSNAMES = new Comparator() { // from class: jdk.nashorn.internal.ir.debug.ClassHistogramElement.3
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return compare((ClassHistogramElement) obj, (ClassHistogramElement) obj2);
        }

        public int compare(ClassHistogramElement classHistogramElement, ClassHistogramElement classHistogramElement2) {
            return classHistogramElement.clazz.getCanonicalName().compareTo(classHistogramElement2.clazz.getCanonicalName());
        }
    };
    private final Class clazz;
    private long instances;
    private long bytes;

    public ClassHistogramElement(Class cls) {
        this.clazz = cls;
    }

    public void addInstance(long j) {
        this.instances++;
        this.bytes += j;
    }

    public long getBytes() {
        return this.bytes;
    }

    public Class getClazz() {
        return this.clazz;
    }

    public long getInstances() {
        return this.instances;
    }

    public String toString() {
        return "ClassHistogramElement[class=" + this.clazz.getCanonicalName() + ", instances=" + this.instances + ", bytes=" + this.bytes + "]";
    }
}
