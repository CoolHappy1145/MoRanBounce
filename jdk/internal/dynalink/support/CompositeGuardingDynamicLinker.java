package jdk.internal.dynalink.support;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/CompositeGuardingDynamicLinker.class */
public class CompositeGuardingDynamicLinker implements GuardingDynamicLinker, Serializable {
    private static final long serialVersionUID = 1;
    private final GuardingDynamicLinker[] linkers;

    public CompositeGuardingDynamicLinker(Iterable iterable) {
        LinkedList linkedList = new LinkedList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            linkedList.add((GuardingDynamicLinker) it.next());
        }
        this.linkers = (GuardingDynamicLinker[]) linkedList.toArray(new GuardingDynamicLinker[linkedList.size()]);
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        for (GuardingDynamicLinker guardingDynamicLinker : this.linkers) {
            GuardedInvocation guardedInvocation = guardingDynamicLinker.getGuardedInvocation(linkRequest, linkerServices);
            if (guardedInvocation != null) {
                return guardedInvocation;
            }
        }
        return null;
    }
}
