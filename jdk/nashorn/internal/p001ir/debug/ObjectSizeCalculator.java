package jdk.nashorn.internal.p001ir.debug;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/ObjectSizeCalculator.class */
public final class ObjectSizeCalculator {
    private final int arrayHeaderSize;
    private final int objectHeaderSize;
    private final int objectPadding;
    private final int referenceSize;
    private final int superclassFieldPadding;
    private final Map classSizeInfos = new IdentityHashMap();
    private final Map alreadyVisited = new IdentityHashMap();
    private final Map histogram = new IdentityHashMap();
    private final Deque pending = new ArrayDeque(16384);
    private long size;
    static Class managementFactory;
    static Class memoryPoolMXBean;
    static Class memoryUsage;
    static Method getMemoryPoolMXBeans;
    static Method getUsage;
    static Method getMax;

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/ObjectSizeCalculator$MemoryLayoutSpecification.class */
    public interface MemoryLayoutSpecification {
        int getArrayHeaderSize();

        int getObjectHeaderSize();

        int getObjectPadding();

        int getReferenceSize();

        int getSuperclassFieldPadding();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/ObjectSizeCalculator$CurrentLayout.class */
    private static class CurrentLayout {
        private static final MemoryLayoutSpecification SPEC = ObjectSizeCalculator.getEffectiveMemoryLayoutSpecification();

        private CurrentLayout() {
        }
    }

    public static long getObjectSize(Object obj) {
        if (obj == null) {
            return 0L;
        }
        return new ObjectSizeCalculator(CurrentLayout.SPEC).calculateObjectSize(obj);
    }

    public ObjectSizeCalculator(MemoryLayoutSpecification memoryLayoutSpecification) {
        Objects.requireNonNull(memoryLayoutSpecification);
        this.arrayHeaderSize = memoryLayoutSpecification.getArrayHeaderSize();
        this.objectHeaderSize = memoryLayoutSpecification.getObjectHeaderSize();
        this.objectPadding = memoryLayoutSpecification.getObjectPadding();
        this.referenceSize = memoryLayoutSpecification.getReferenceSize();
        this.superclassFieldPadding = memoryLayoutSpecification.getSuperclassFieldPadding();
    }

    public long calculateObjectSize(Object obj) {
        this.histogram.clear();
        Object objRemoveFirst = obj;
        while (true) {
            try {
                visit(objRemoveFirst);
                if (this.pending.isEmpty()) {
                    return this.size;
                }
                objRemoveFirst = this.pending.removeFirst();
            } finally {
                this.alreadyVisited.clear();
                this.pending.clear();
                this.size = 0L;
            }
        }
    }

    public List getClassHistogram() {
        return new ArrayList(this.histogram.values());
    }

    private ClassSizeInfo getClassSizeInfo(Class cls) {
        ClassSizeInfo classSizeInfo = (ClassSizeInfo) this.classSizeInfos.get(cls);
        if (classSizeInfo == null) {
            classSizeInfo = new ClassSizeInfo(this, cls);
            this.classSizeInfos.put(cls, classSizeInfo);
        }
        return classSizeInfo;
    }

    private void visit(Object obj) {
        if (this.alreadyVisited.containsKey(obj)) {
            return;
        }
        Class<?> cls = obj.getClass();
        if (cls == ArrayElementsVisitor.class) {
            ((ArrayElementsVisitor) obj).visit(this);
            return;
        }
        this.alreadyVisited.put(obj, obj);
        if (cls.isArray()) {
            visitArray(obj);
        } else {
            getClassSizeInfo(cls).visit(obj, this);
        }
    }

    private void visitArray(Object obj) {
        Class<?> cls = obj.getClass();
        Class<?> componentType = cls.getComponentType();
        int length = Array.getLength(obj);
        if (componentType.isPrimitive()) {
            increaseByArraySize(cls, length, getPrimitiveFieldSize(componentType));
            return;
        }
        increaseByArraySize(cls, length, this.referenceSize);
        switch (length) {
            case 0:
                break;
            case 1:
                enqueue(Array.get(obj, 0));
                break;
            default:
                enqueue(new ArrayElementsVisitor((Object[]) obj));
                break;
        }
    }

    private void increaseByArraySize(Class cls, int i, long j) {
        int i2 = this.objectPadding;
        increaseSize(cls, ((((this.arrayHeaderSize + (i * j)) + i2) - 1) / i2) * i2);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/ObjectSizeCalculator$ArrayElementsVisitor.class */
    private static class ArrayElementsVisitor {
        private final Object[] array;

        ArrayElementsVisitor(Object[] objArr) {
            this.array = objArr;
        }

        public void visit(ObjectSizeCalculator objectSizeCalculator) {
            for (Object obj : this.array) {
                if (obj != null) {
                    objectSizeCalculator.visit(obj);
                }
            }
        }
    }

    void enqueue(Object obj) {
        if (obj != null) {
            this.pending.addLast(obj);
        }
    }

    void increaseSize(Class cls, long j) {
        ClassHistogramElement classHistogramElement = (ClassHistogramElement) this.histogram.get(cls);
        if (classHistogramElement == null) {
            classHistogramElement = new ClassHistogramElement(cls);
            this.histogram.put(cls, classHistogramElement);
        }
        classHistogramElement.addInstance(j);
        this.size += j;
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/ObjectSizeCalculator$ClassSizeInfo.class */
    private class ClassSizeInfo {
        private final long objectSize;
        private final long fieldsSize;
        private final Field[] referenceFields;
        final ObjectSizeCalculator this$0;

        public ClassSizeInfo(ObjectSizeCalculator objectSizeCalculator, Class cls) {
            this.this$0 = objectSizeCalculator;
            long primitiveFieldSize = 0;
            LinkedList linkedList = new LinkedList();
            for (Field field : cls.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    Class<?> type = field.getType();
                    if (type.isPrimitive()) {
                        primitiveFieldSize += ObjectSizeCalculator.getPrimitiveFieldSize(type);
                    } else {
                        field.setAccessible(true);
                        linkedList.add(field);
                        primitiveFieldSize += objectSizeCalculator.referenceSize;
                    }
                }
            }
            Class superclass = cls.getSuperclass();
            if (superclass != null) {
                ClassSizeInfo classSizeInfo = objectSizeCalculator.getClassSizeInfo(superclass);
                long j = classSizeInfo.fieldsSize;
                int i = objectSizeCalculator.superclassFieldPadding;
                primitiveFieldSize += (((j + i) - 1) / i) * i;
                linkedList.addAll(Arrays.asList(classSizeInfo.referenceFields));
            }
            this.fieldsSize = primitiveFieldSize;
            int i2 = objectSizeCalculator.objectPadding;
            this.objectSize = ((((objectSizeCalculator.objectHeaderSize + primitiveFieldSize) + i2) - 1) / i2) * i2;
            this.referenceFields = (Field[]) linkedList.toArray(new Field[linkedList.size()]);
        }

        void visit(Object obj, ObjectSizeCalculator objectSizeCalculator) {
            objectSizeCalculator.increaseSize(obj.getClass(), this.objectSize);
            enqueueReferencedObjects(obj, objectSizeCalculator);
        }

        public void enqueueReferencedObjects(Object obj, ObjectSizeCalculator objectSizeCalculator) {
            for (Field field : this.referenceFields) {
                try {
                    objectSizeCalculator.enqueue(field.get(obj));
                } catch (IllegalAccessException e) {
                    AssertionError assertionError = new AssertionError("Unexpected denial of access to " + field);
                    assertionError.initCause(e);
                    throw assertionError;
                }
            }
        }
    }

    private static long getPrimitiveFieldSize(Class cls) {
        if (cls == Boolean.TYPE || cls == Byte.TYPE) {
            return 1L;
        }
        if (cls == Character.TYPE || cls == Short.TYPE) {
            return 2L;
        }
        if (cls == Integer.TYPE || cls == Float.TYPE) {
            return 4L;
        }
        if (cls == Long.TYPE || cls == Double.TYPE) {
            return 8L;
        }
        throw new AssertionError("Encountered unexpected primitive type " + cls.getName());
    }

    static {
        managementFactory = null;
        memoryPoolMXBean = null;
        memoryUsage = null;
        getMemoryPoolMXBeans = null;
        getUsage = null;
        getMax = null;
        try {
            managementFactory = Class.forName("java.lang.management.ManagementFactory");
            memoryPoolMXBean = Class.forName("java.lang.management.MemoryPoolMXBean");
            memoryUsage = Class.forName("java.lang.management.MemoryUsage");
            getMemoryPoolMXBeans = managementFactory.getMethod("getMemoryPoolMXBeans", new Class[0]);
            getUsage = memoryPoolMXBean.getMethod("getUsage", new Class[0]);
            getMax = memoryUsage.getMethod("getMax", new Class[0]);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException unused) {
        }
    }

    public static MemoryLayoutSpecification getEffectiveMemoryLayoutSpecification() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String property = System.getProperty("java.vm.name");
        if (property == null || !property.startsWith("Java HotSpot(TM) ")) {
            throw new UnsupportedOperationException("ObjectSizeCalculator only supported on HotSpot VM");
        }
        String property2 = System.getProperty("sun.arch.data.model");
        if ("32".equals(property2)) {
            return new MemoryLayoutSpecification() { // from class: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.1
            };
        }
        if (!"64".equals(property2)) {
            throw new UnsupportedOperationException("Unrecognized value '" + property2 + "' of sun.arch.data.model system property");
        }
        String property3 = System.getProperty("java.vm.version");
        if (Integer.parseInt(property3.substring(0, property3.indexOf(46))) >= 17) {
            long jLongValue = 0;
            if (getMemoryPoolMXBeans == null) {
                throw new AssertionError("java.lang.management not available in compact 1");
            }
            try {
                Iterator it = ((List) getMemoryPoolMXBeans.invoke(managementFactory, new Object[0])).iterator();
                while (it.hasNext()) {
                    jLongValue += ((Long) getMax.invoke(getUsage.invoke(it.next(), new Object[0]), new Object[0])).longValue();
                }
                if (jLongValue < 32212254720L) {
                    return new MemoryLayoutSpecification() { // from class: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.2
                    };
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
                throw new AssertionError("java.lang.management not available in compact 1");
            }
        }
        return new MemoryLayoutSpecification() { // from class: jdk.nashorn.internal.ir.debug.ObjectSizeCalculator.3
        };
    }
}
