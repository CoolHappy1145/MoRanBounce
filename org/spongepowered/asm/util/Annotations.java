package org.spongepowered.asm.util;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Overwrite;

/* loaded from: L-out.jar:org/spongepowered/asm/util/Annotations.class */
public final class Annotations {
    private static final Class[] MERGEABLE_MIXIN_ANNOTATIONS = {Overwrite.class, Intrinsic.class, Final.class, Debug.class};
    private static Pattern mergeableAnnotationPattern = getMergeableAnnotationPattern();
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);

    private Annotations() {
    }

    public static void setVisible(FieldNode fieldNode, Class cls, Object[] objArr) {
        fieldNode.visibleAnnotations = add(fieldNode.visibleAnnotations, createNode(Type.getDescriptor(cls), objArr));
    }

    public static void setInvisible(FieldNode fieldNode, Class cls, Object[] objArr) {
        fieldNode.invisibleAnnotations = add(fieldNode.invisibleAnnotations, createNode(Type.getDescriptor(cls), objArr));
    }

    public static void setVisible(MethodNode methodNode, Class cls, Object[] objArr) {
        methodNode.visibleAnnotations = add(methodNode.visibleAnnotations, createNode(Type.getDescriptor(cls), objArr));
    }

    public static void setInvisible(MethodNode methodNode, Class cls, Object[] objArr) {
        methodNode.invisibleAnnotations = add(methodNode.invisibleAnnotations, createNode(Type.getDescriptor(cls), objArr));
    }

    private static AnnotationNode createNode(String str, Object[] objArr) {
        AnnotationNode annotationNode = new AnnotationNode(str);
        for (int i = 0; i < objArr.length - 1; i += 2) {
            if (!(objArr[i] instanceof String)) {
                throw new IllegalArgumentException("Annotation keys must be strings, found " + objArr[i].getClass().getSimpleName() + " with " + objArr[i].toString() + " at index " + i + " creating " + str);
            }
            annotationNode.visit((String) objArr[i], objArr[i + 1]);
        }
        return annotationNode;
    }

    private static List add(List list, AnnotationNode annotationNode) {
        if (list == null) {
            list = new ArrayList(1);
        } else {
            list.remove(get(list, annotationNode.desc));
        }
        list.add(annotationNode);
        return list;
    }

    public static AnnotationNode getVisible(FieldNode fieldNode, Class cls) {
        return get(fieldNode.visibleAnnotations, Type.getDescriptor(cls));
    }

    public static AnnotationNode getInvisible(FieldNode fieldNode, Class cls) {
        return get(fieldNode.invisibleAnnotations, Type.getDescriptor(cls));
    }

    public static AnnotationNode getVisible(MethodNode methodNode, Class cls) {
        return get(methodNode.visibleAnnotations, Type.getDescriptor(cls));
    }

    public static AnnotationNode getInvisible(MethodNode methodNode, Class cls) {
        return get(methodNode.invisibleAnnotations, Type.getDescriptor(cls));
    }

    public static AnnotationNode getSingleVisible(MethodNode methodNode, Class[] clsArr) {
        return getSingle(methodNode.visibleAnnotations, clsArr);
    }

    public static AnnotationNode getSingleInvisible(MethodNode methodNode, Class[] clsArr) {
        return getSingle(methodNode.invisibleAnnotations, clsArr);
    }

    public static AnnotationNode getVisible(ClassNode classNode, Class cls) {
        return get(classNode.visibleAnnotations, Type.getDescriptor(cls));
    }

    public static AnnotationNode getInvisible(ClassNode classNode, Class cls) {
        return get(classNode.invisibleAnnotations, Type.getDescriptor(cls));
    }

    public static AnnotationNode getVisibleParameter(MethodNode methodNode, Class cls, int i) {
        if (i < 0) {
            return getVisible(methodNode, cls);
        }
        return getParameter(methodNode.visibleParameterAnnotations, Type.getDescriptor(cls), i);
    }

    public static AnnotationNode getInvisibleParameter(MethodNode methodNode, Class cls, int i) {
        if (i < 0) {
            return getInvisible(methodNode, cls);
        }
        return getParameter(methodNode.invisibleParameterAnnotations, Type.getDescriptor(cls), i);
    }

    public static AnnotationNode getParameter(List[] listArr, String str, int i) {
        if (listArr == null || i < 0 || i >= listArr.length) {
            return null;
        }
        return get(listArr[i], str);
    }

    public static AnnotationNode get(List list, String str) {
        if (list == null) {
            return null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AnnotationNode annotationNode = (AnnotationNode) it.next();
            if (str.equals(annotationNode.desc)) {
                return annotationNode;
            }
        }
        return null;
    }

    private static AnnotationNode getSingle(List list, Class[] clsArr) {
        ArrayList arrayList = new ArrayList();
        for (Class cls : clsArr) {
            AnnotationNode annotationNode = get(list, Type.getDescriptor(cls));
            if (annotationNode != null) {
                arrayList.add(annotationNode);
            }
        }
        int size = arrayList.size();
        if (size > 1) {
            throw new IllegalArgumentException("Conflicting annotations found: " + Lists.transform(arrayList, new Function() { // from class: org.spongepowered.asm.util.Annotations.1
                public Object apply(Object obj) {
                    return apply((AnnotationNode) obj);
                }

                public String apply(AnnotationNode annotationNode2) {
                    return annotationNode2.desc;
                }
            }));
        }
        if (size == 0) {
            return null;
        }
        return (AnnotationNode) arrayList.get(0);
    }

    public static Object getValue(AnnotationNode annotationNode) {
        return getValue(annotationNode, PropertyDescriptor.VALUE);
    }

    public static Object getValue(AnnotationNode annotationNode, String str, Object obj) {
        Object value = getValue(annotationNode, str);
        return value != null ? value : obj;
    }

    public static Object getValue(AnnotationNode annotationNode, String str, Class cls) {
        Preconditions.checkNotNull(cls, "annotationClass cannot be null");
        Object value = getValue(annotationNode, str);
        if (value == null) {
            try {
                value = cls.getDeclaredMethod(str, new Class[0]).getDefaultValue();
            } catch (NoSuchMethodException unused) {
            }
        }
        return value;
    }

    public static Object getValue(AnnotationNode annotationNode, String str) {
        boolean z = false;
        if (annotationNode == null || annotationNode.values == null) {
            return null;
        }
        for (Object obj : annotationNode.values) {
            if (z) {
                return obj;
            }
            if (obj.equals(str)) {
                z = true;
            }
        }
        return null;
    }

    public static Enum getValue(AnnotationNode annotationNode, String str, Class cls, Enum r6) {
        String[] strArr = (String[]) getValue(annotationNode, str);
        if (strArr == null) {
            return r6;
        }
        return toEnumValue(cls, strArr);
    }

    public static List getValue(AnnotationNode annotationNode, String str, boolean z) {
        Object value = getValue(annotationNode, str);
        if (value instanceof List) {
            return (List) value;
        }
        if (value != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(value);
            return arrayList;
        }
        return Collections.emptyList();
    }

    public static List getValue(AnnotationNode annotationNode, String str, boolean z, Class cls) {
        Object value = getValue(annotationNode, str);
        if (value instanceof List) {
            ListIterator listIterator = ((List) value).listIterator();
            while (listIterator.hasNext()) {
                listIterator.set(toEnumValue(cls, (String[]) listIterator.next()));
            }
            return (List) value;
        }
        if (value instanceof String[]) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(toEnumValue(cls, (String[]) value));
            return arrayList;
        }
        return Collections.emptyList();
    }

    public static void setValue(AnnotationNode annotationNode, String str, Object obj) {
        if (annotationNode == null) {
            return;
        }
        int i = 0;
        if (annotationNode.values != null) {
            int i2 = 0;
            while (true) {
                if (i2 >= annotationNode.values.size() - 1) {
                    break;
                }
                if (!str.equals(annotationNode.values.get(i2).toString())) {
                    i2 += 2;
                } else {
                    i = i2 + 1;
                    break;
                }
            }
        } else {
            annotationNode.values = new ArrayList();
        }
        if (i > 0) {
            annotationNode.values.set(i, packValue(obj));
        } else {
            annotationNode.values.add(str);
            annotationNode.values.add(packValue(obj));
        }
    }

    private static Object packValue(Object obj) {
        Class<?> cls = obj.getClass();
        if (cls.isEnum()) {
            return new String[]{Type.getDescriptor(cls), obj.toString()};
        }
        return obj;
    }

    public static void merge(ClassNode classNode, ClassNode classNode2) {
        classNode2.visibleAnnotations = merge(classNode.visibleAnnotations, classNode2.visibleAnnotations, "class", classNode.name);
        classNode2.invisibleAnnotations = merge(classNode.invisibleAnnotations, classNode2.invisibleAnnotations, "class", classNode.name);
    }

    public static void merge(MethodNode methodNode, MethodNode methodNode2) {
        methodNode2.visibleAnnotations = merge(methodNode.visibleAnnotations, methodNode2.visibleAnnotations, "method", methodNode.name);
        methodNode2.invisibleAnnotations = merge(methodNode.invisibleAnnotations, methodNode2.invisibleAnnotations, "method", methodNode.name);
    }

    public static void merge(FieldNode fieldNode, FieldNode fieldNode2) {
        fieldNode2.visibleAnnotations = merge(fieldNode.visibleAnnotations, fieldNode2.visibleAnnotations, "field", fieldNode.name);
        fieldNode2.invisibleAnnotations = merge(fieldNode.invisibleAnnotations, fieldNode2.invisibleAnnotations, "field", fieldNode.name);
    }

    private static List merge(List list, List list2, String str, String str2) {
        if (list == null) {
            return list2;
        }
        if (list2 == null) {
            try {
                list2 = new ArrayList();
            } catch (Exception unused) {
                logger.warn("Exception encountered whilst merging annotations for {} {}", new Object[]{str, str2});
            }
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AnnotationNode annotationNode = (AnnotationNode) it.next();
            if (isMergeableAnnotation(annotationNode)) {
                Iterator it2 = list2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (((AnnotationNode) it2.next()).desc.equals(annotationNode.desc)) {
                        it2.remove();
                        break;
                    }
                }
                list2.add(annotationNode);
            }
        }
        return list2;
    }

    private static boolean isMergeableAnnotation(AnnotationNode annotationNode) {
        if (annotationNode.desc.startsWith("L" + Constants.MIXIN_PACKAGE_REF)) {
            return mergeableAnnotationPattern.matcher(annotationNode.desc).matches();
        }
        return true;
    }

    private static Pattern getMergeableAnnotationPattern() {
        StringBuilder sb = new StringBuilder("^L(");
        for (int i = 0; i < MERGEABLE_MIXIN_ANNOTATIONS.length; i++) {
            if (i > 0) {
                sb.append('|');
            }
            sb.append(MERGEABLE_MIXIN_ANNOTATIONS[i].getName().replace('.', '/'));
        }
        return Pattern.compile(sb.append(");$").toString());
    }

    private static Enum toEnumValue(Class cls, String[] strArr) {
        if (!cls.getName().equals(Type.getType(strArr[0]).getClassName())) {
            throw new IllegalArgumentException("The supplied enum class does not match the stored enum value");
        }
        return Enum.valueOf(cls, strArr[1]);
    }
}
