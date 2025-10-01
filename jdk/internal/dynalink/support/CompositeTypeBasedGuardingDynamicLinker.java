package jdk.internal.dynalink.support;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/CompositeTypeBasedGuardingDynamicLinker.class */
public class CompositeTypeBasedGuardingDynamicLinker implements TypeBasedGuardingDynamicLinker, Serializable {
    private static final long serialVersionUID = 1;
    private final ClassValue classToLinker;

    /* loaded from: L-out.jar:jdk/internal/dynalink/support/CompositeTypeBasedGuardingDynamicLinker$ClassToLinker.class */
    private static class ClassToLinker extends ClassValue {
        private static final List NO_LINKER = Collections.emptyList();
        private final TypeBasedGuardingDynamicLinker[] linkers;
        private final List[] singletonLinkers;

        @Override // java.lang.ClassValue
        protected Object computeValue(Class cls) {
            return computeValue(cls);
        }

        ClassToLinker(TypeBasedGuardingDynamicLinker[] typeBasedGuardingDynamicLinkerArr) {
            this.linkers = typeBasedGuardingDynamicLinkerArr;
            this.singletonLinkers = new List[typeBasedGuardingDynamicLinkerArr.length];
            for (int i = 0; i < typeBasedGuardingDynamicLinkerArr.length; i++) {
                this.singletonLinkers[i] = Collections.singletonList(typeBasedGuardingDynamicLinkerArr[i]);
            }
        }

        @Override // java.lang.ClassValue
        protected List computeValue(Class cls) {
            List linkedList = NO_LINKER;
            for (int i = 0; i < this.linkers.length; i++) {
                TypeBasedGuardingDynamicLinker typeBasedGuardingDynamicLinker = this.linkers[i];
                if (typeBasedGuardingDynamicLinker.canLinkType(cls)) {
                    switch (linkedList.size()) {
                        case 0:
                            linkedList = this.singletonLinkers[i];
                            continue;
                        case 1:
                            linkedList = new LinkedList(linkedList);
                            break;
                    }
                    linkedList.add(typeBasedGuardingDynamicLinker);
                }
            }
            return linkedList;
        }
    }

    public CompositeTypeBasedGuardingDynamicLinker(Iterable iterable) {
        LinkedList linkedList = new LinkedList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            linkedList.add((TypeBasedGuardingDynamicLinker) it.next());
        }
        this.classToLinker = new ClassToLinker((TypeBasedGuardingDynamicLinker[]) linkedList.toArray(new TypeBasedGuardingDynamicLinker[linkedList.size()]));
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class cls) {
        return !((List) this.classToLinker.get(cls)).isEmpty();
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        Object receiver = linkRequest.getReceiver();
        if (receiver == null) {
            return null;
        }
        Iterator it = ((List) this.classToLinker.get(receiver.getClass())).iterator();
        while (it.hasNext()) {
            GuardedInvocation guardedInvocation = ((TypeBasedGuardingDynamicLinker) it.next()).getGuardedInvocation(linkRequest, linkerServices);
            if (guardedInvocation != null) {
                return guardedInvocation;
            }
        }
        return null;
    }

    public static List optimize(Iterable iterable) {
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            GuardingDynamicLinker guardingDynamicLinker = (GuardingDynamicLinker) it.next();
            if (guardingDynamicLinker instanceof TypeBasedGuardingDynamicLinker) {
                linkedList2.add((TypeBasedGuardingDynamicLinker) guardingDynamicLinker);
            } else {
                addTypeBased(linkedList, linkedList2);
                linkedList.add(guardingDynamicLinker);
            }
        }
        addTypeBased(linkedList, linkedList2);
        return linkedList;
    }

    private static void addTypeBased(List list, List list2) {
        switch (list2.size()) {
            case 0:
                break;
            case 1:
                list.addAll(list2);
                list2.clear();
                break;
            default:
                list.add(new CompositeTypeBasedGuardingDynamicLinker(list2));
                list2.clear();
                break;
        }
    }
}
