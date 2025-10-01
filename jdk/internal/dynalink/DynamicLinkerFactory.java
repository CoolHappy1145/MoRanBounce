package jdk.internal.dynalink;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.MethodHandleTransformer;
import jdk.internal.dynalink.linker.MethodTypeConversionStrategy;
import jdk.internal.dynalink.support.AutoDiscovery;
import jdk.internal.dynalink.support.BottomGuardingDynamicLinker;
import jdk.internal.dynalink.support.ClassLoaderGetterContextProvider;
import jdk.internal.dynalink.support.CompositeGuardingDynamicLinker;
import jdk.internal.dynalink.support.CompositeTypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.DefaultPrelinkFilter;
import jdk.internal.dynalink.support.LinkerServicesImpl;
import jdk.internal.dynalink.support.TypeConverterFactory;

/* loaded from: L-out.jar:jdk/internal/dynalink/DynamicLinkerFactory.class */
public class DynamicLinkerFactory {
    public static final int DEFAULT_UNSTABLE_RELINK_THRESHOLD = 8;
    private ClassLoader classLoader;
    private List prioritizedLinkers;
    private List fallbackLinkers;
    private GuardedInvocationFilter prelinkFilter;
    private MethodTypeConversionStrategy autoConversionStrategy;
    private MethodHandleTransformer internalObjectsFilter;
    private boolean classLoaderExplicitlySet = false;
    private int runtimeContextArgCount = 0;
    private boolean syncOnRelink = false;
    private int unstableRelinkThreshold = 8;

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.classLoaderExplicitlySet = true;
    }

    public void setPrioritizedLinkers(List list) {
        this.prioritizedLinkers = list == null ? null : new ArrayList(list);
    }

    public void setPrioritizedLinkers(GuardingDynamicLinker[] guardingDynamicLinkerArr) {
        setPrioritizedLinkers(Arrays.asList(guardingDynamicLinkerArr));
    }

    public void setPrioritizedLinker(GuardingDynamicLinker guardingDynamicLinker) {
        if (guardingDynamicLinker == null) {
            throw new IllegalArgumentException("prioritizedLinker == null");
        }
        this.prioritizedLinkers = Collections.singletonList(guardingDynamicLinker);
    }

    public void setFallbackLinkers(List list) {
        this.fallbackLinkers = list == null ? null : new ArrayList(list);
    }

    public void setFallbackLinkers(GuardingDynamicLinker[] guardingDynamicLinkerArr) {
        setFallbackLinkers(Arrays.asList(guardingDynamicLinkerArr));
    }

    public void setRuntimeContextArgCount(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("runtimeContextArgCount < 0");
        }
        this.runtimeContextArgCount = i;
    }

    public void setSyncOnRelink(boolean z) {
        this.syncOnRelink = z;
    }

    public void setUnstableRelinkThreshold(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("unstableRelinkThreshold < 0");
        }
        this.unstableRelinkThreshold = i;
    }

    public void setPrelinkFilter(GuardedInvocationFilter guardedInvocationFilter) {
        this.prelinkFilter = guardedInvocationFilter;
    }

    public void setAutoConversionStrategy(MethodTypeConversionStrategy methodTypeConversionStrategy) {
        this.autoConversionStrategy = methodTypeConversionStrategy;
    }

    public void setInternalObjectsFilter(MethodHandleTransformer methodHandleTransformer) {
        this.internalObjectsFilter = methodHandleTransformer;
    }

    public DynamicLinker createLinker() {
        GuardingDynamicLinker compositeGuardingDynamicLinker;
        if (this.prioritizedLinkers == null) {
            this.prioritizedLinkers = Collections.emptyList();
        }
        if (this.fallbackLinkers == null) {
            this.fallbackLinkers = Collections.singletonList(new BeansLinker());
        }
        HashSet hashSet = new HashSet();
        addClasses(hashSet, this.prioritizedLinkers);
        addClasses(hashSet, this.fallbackLinkers);
        List<GuardingDynamicLinker> listLoadLinkers = AutoDiscovery.loadLinkers(this.classLoaderExplicitlySet ? this.classLoader : getThreadContextClassLoader());
        ArrayList<GuardingDynamicLinker> arrayList = new ArrayList(this.prioritizedLinkers.size() + listLoadLinkers.size() + this.fallbackLinkers.size());
        arrayList.addAll(this.prioritizedLinkers);
        for (GuardingDynamicLinker guardingDynamicLinker : listLoadLinkers) {
            if (!hashSet.contains(guardingDynamicLinker.getClass())) {
                arrayList.add(guardingDynamicLinker);
            }
        }
        arrayList.addAll(this.fallbackLinkers);
        List listOptimize = CompositeTypeBasedGuardingDynamicLinker.optimize(arrayList);
        switch (arrayList.size()) {
            case 0:
                compositeGuardingDynamicLinker = BottomGuardingDynamicLinker.INSTANCE;
                break;
            case 1:
                compositeGuardingDynamicLinker = (GuardingDynamicLinker) listOptimize.get(0);
                break;
            default:
                compositeGuardingDynamicLinker = new CompositeGuardingDynamicLinker(listOptimize);
                break;
        }
        LinkedList linkedList = new LinkedList();
        for (GuardingDynamicLinker guardingDynamicLinker2 : arrayList) {
            if (guardingDynamicLinker2 instanceof GuardingTypeConverterFactory) {
                linkedList.add((GuardingTypeConverterFactory) guardingDynamicLinker2);
            }
        }
        if (this.prelinkFilter == null) {
            this.prelinkFilter = new DefaultPrelinkFilter();
        }
        return new DynamicLinker(new LinkerServicesImpl(new TypeConverterFactory(linkedList, this.autoConversionStrategy), compositeGuardingDynamicLinker, this.internalObjectsFilter), this.prelinkFilter, this.runtimeContextArgCount, this.syncOnRelink, this.unstableRelinkThreshold);
    }

    private static ClassLoader getThreadContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() { // from class: jdk.internal.dynalink.DynamicLinkerFactory.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        }, ClassLoaderGetterContextProvider.GET_CLASS_LOADER_CONTEXT);
    }

    private static void addClasses(Set set, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            set.add(((GuardingDynamicLinker) it.next()).getClass());
        }
    }
}
