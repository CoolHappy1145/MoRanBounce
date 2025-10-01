package jdk.internal.dynalink.beans;

import java.io.Serializable;
import java.util.Objects;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/StaticClass.class */
public class StaticClass implements Serializable {
    private static final ClassValue staticClasses = new ClassValue() { // from class: jdk.internal.dynalink.beans.StaticClass.1
        @Override // java.lang.ClassValue
        protected Object computeValue(Class cls) {
            return computeValue(cls);
        }

        @Override // java.lang.ClassValue
        protected StaticClass computeValue(Class cls) {
            return new StaticClass(cls);
        }
    };
    private static final long serialVersionUID = 1;
    private final Class clazz;

    StaticClass(Class cls) {
        this.clazz = (Class) Objects.requireNonNull(cls);
    }

    public static StaticClass forClass(Class cls) {
        return (StaticClass) staticClasses.get(cls);
    }

    public Class getRepresentedClass() {
        return this.clazz;
    }

    public String toString() {
        return "JavaClassStatics[" + this.clazz.getName() + "]";
    }

    private Object readResolve() {
        return forClass(this.clazz);
    }
}
