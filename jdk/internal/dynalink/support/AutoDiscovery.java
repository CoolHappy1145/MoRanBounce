package jdk.internal.dynalink.support;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/AutoDiscovery.class */
public class AutoDiscovery {
    private AutoDiscovery() {
    }

    public static List loadLinkers() {
        return getLinkers(ServiceLoader.load(GuardingDynamicLinker.class));
    }

    public static List loadLinkers(ClassLoader classLoader) {
        return getLinkers(ServiceLoader.load(GuardingDynamicLinker.class, classLoader));
    }

    private static List getLinkers(ServiceLoader serviceLoader) {
        LinkedList linkedList = new LinkedList();
        Iterator it = serviceLoader.iterator();
        while (it.hasNext()) {
            linkedList.add(it.next());
        }
        return linkedList;
    }
}
