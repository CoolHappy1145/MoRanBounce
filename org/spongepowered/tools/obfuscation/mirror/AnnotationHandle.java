package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/AnnotationHandle.class */
public final class AnnotationHandle {
    public static final AnnotationHandle MISSING = new AnnotationHandle(null);
    private final AnnotationMirror annotation;

    private AnnotationHandle(AnnotationMirror annotationMirror) {
        this.annotation = annotationMirror;
    }

    public AnnotationMirror asMirror() {
        return this.annotation;
    }

    public boolean exists() {
        return this.annotation != null;
    }

    public String toString() {
        if (this.annotation == null) {
            return "@{UnknownAnnotation}";
        }
        return "@" + this.annotation.getAnnotationType().asElement().getSimpleName();
    }

    public Object getValue(String str, Object obj) {
        if (this.annotation == null) {
            return obj;
        }
        AnnotationValue annotationValue = getAnnotationValue(str);
        if (!(obj instanceof Enum) || annotationValue == null) {
            return annotationValue != null ? annotationValue.getValue() : obj;
        }
        VariableElement variableElement = (VariableElement) annotationValue.getValue();
        if (variableElement == null) {
            return obj;
        }
        return Enum.valueOf(obj.getClass(), variableElement.getSimpleName().toString());
    }

    public Object getValue() {
        return getValue(PropertyDescriptor.VALUE, null);
    }

    public Object getValue(String str) {
        return getValue(str, null);
    }

    public boolean getBoolean(String str, boolean z) {
        return ((Boolean) getValue(str, Boolean.valueOf(z))).booleanValue();
    }

    public AnnotationHandle getAnnotation(String str) {
        Object value = getValue(str);
        if (value instanceof AnnotationMirror) {
            return m81of((AnnotationMirror) value);
        }
        if (value instanceof AnnotationValue) {
            Object value2 = ((AnnotationValue) value).getValue();
            if (value2 instanceof AnnotationMirror) {
                return m81of((AnnotationMirror) value2);
            }
            return null;
        }
        return null;
    }

    public List getList() {
        return getList(PropertyDescriptor.VALUE);
    }

    public List getList(String str) {
        return unwrapAnnotationValueList((List) getValue(str, Collections.emptyList()));
    }

    public List getAnnotationList(String str) {
        Object value = getValue(str, null);
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof AnnotationMirror) {
            return ImmutableList.of(m81of((AnnotationMirror) value));
        }
        List list = (List) value;
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new AnnotationHandle((AnnotationMirror) ((AnnotationValue) it.next()).getValue()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    protected AnnotationValue getAnnotationValue(String str) {
        for (ExecutableElement executableElement : this.annotation.getElementValues().keySet()) {
            if (executableElement.getSimpleName().contentEquals(str)) {
                return (AnnotationValue) this.annotation.getElementValues().get(executableElement);
            }
        }
        return null;
    }

    protected static List unwrapAnnotationValueList(List list) {
        if (list == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((AnnotationValue) it.next()).getValue());
        }
        return arrayList;
    }

    protected static AnnotationMirror getAnnotation(Element element, Class cls) {
        List<AnnotationMirror> annotationMirrors;
        if (element == null || (annotationMirrors = element.getAnnotationMirrors()) == null) {
            return null;
        }
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            TypeElement typeElementAsElement = annotationMirror.getAnnotationType().asElement();
            if ((typeElementAsElement instanceof TypeElement) && typeElementAsElement.getQualifiedName().contentEquals(cls.getName())) {
                return annotationMirror;
            }
        }
        return null;
    }

    /* renamed from: of */
    public static AnnotationHandle m81of(AnnotationMirror annotationMirror) {
        return new AnnotationHandle(annotationMirror);
    }

    /* renamed from: of */
    public static AnnotationHandle m82of(Element element, Class cls) {
        return new AnnotationHandle(getAnnotation(element, cls));
    }
}
