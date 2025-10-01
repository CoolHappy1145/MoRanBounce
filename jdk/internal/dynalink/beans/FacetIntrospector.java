package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import jdk.internal.dynalink.support.Lookup;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/FacetIntrospector.class */
abstract class FacetIntrospector {
    private final Class clazz;
    private final boolean instance;
    private final boolean isRestricted;
    protected final AccessibleMembersLookup membersLookup;

    abstract Map getInnerClassGetters();

    abstract MethodHandle editMethodHandle(MethodHandle methodHandle);

    FacetIntrospector(Class cls, boolean z) {
        this.clazz = cls;
        this.instance = z;
        this.isRestricted = CheckRestrictedPackage.isRestrictedClass(cls);
        this.membersLookup = new AccessibleMembersLookup(cls, z);
    }

    Collection getFields() throws SecurityException {
        if (this.isRestricted) {
            return Collections.emptySet();
        }
        Field[] fields = this.clazz.getFields();
        ArrayList arrayList = new ArrayList(fields.length);
        for (Field field : fields) {
            boolean zIsStatic = Modifier.isStatic(field.getModifiers());
            if ((!zIsStatic || this.clazz == field.getDeclaringClass()) && this.instance != zIsStatic && isAccessible(field)) {
                arrayList.add(field);
            }
        }
        return arrayList;
    }

    boolean isAccessible(Member member) {
        Class<?> declaringClass = member.getDeclaringClass();
        return declaringClass == this.clazz || !CheckRestrictedPackage.isRestrictedClass(declaringClass);
    }

    Collection getMethods() {
        return this.membersLookup.getMethods();
    }

    MethodHandle unreflectGetter(Field field) {
        return editMethodHandle(Lookup.PUBLIC.unreflectGetter(field));
    }

    MethodHandle unreflectSetter(Field field) {
        return editMethodHandle(Lookup.PUBLIC.unreflectSetter(field));
    }
}
