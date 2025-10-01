package jdk.internal.dynalink.beans;

import java.util.Collections;
import java.util.Map;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/BeanIntrospector.class */
class BeanIntrospector extends FacetIntrospector {
    BeanIntrospector(Class cls) {
        super(cls, true);
    }

    @Override // jdk.internal.dynalink.beans.FacetIntrospector
    Map getInnerClassGetters() {
        return Collections.emptyMap();
    }
}
