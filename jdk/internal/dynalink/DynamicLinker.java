package jdk.internal.dynalink;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.List;
import java.util.Objects;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.LinkRequestImpl;
import jdk.internal.dynalink.support.Lookup;
import jdk.internal.dynalink.support.RuntimeContextLinkRequestImpl;

/* loaded from: L-out.jar:jdk/internal/dynalink/DynamicLinker.class */
public class DynamicLinker {
    private static final String INITIAL_LINK_CLASS_NAME = "java.lang.invoke.MethodHandleNatives";
    private static final String INITIAL_LINK_METHOD_NAME = "linkCallSite";
    private final LinkerServices linkerServices;
    private final GuardedInvocationFilter prelinkFilter;
    private final int runtimeContextArgCount;
    private final boolean syncOnRelink;
    private final int unstableRelinkThreshold;
    private static final String CLASS_NAME = DynamicLinker.class.getName();
    private static final String RELINK_METHOD_NAME = "relink";
    private static final MethodHandle RELINK = Lookup.findOwnSpecial(MethodHandles.lookup(), RELINK_METHOD_NAME, MethodHandle.class, new Class[]{RelinkableCallSite.class, Integer.TYPE, Object[].class});

    DynamicLinker(LinkerServices linkerServices, GuardedInvocationFilter guardedInvocationFilter, int i, boolean z, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("runtimeContextArgCount < 0");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("unstableRelinkThreshold < 0");
        }
        this.linkerServices = linkerServices;
        this.prelinkFilter = guardedInvocationFilter;
        this.runtimeContextArgCount = i;
        this.syncOnRelink = z;
        this.unstableRelinkThreshold = i2;
    }

    public RelinkableCallSite link(RelinkableCallSite relinkableCallSite) {
        relinkableCallSite.initialize(createRelinkAndInvokeMethod(relinkableCallSite, 0));
        return relinkableCallSite;
    }

    public LinkerServices getLinkerServices() {
        return this.linkerServices;
    }

    private MethodHandle createRelinkAndInvokeMethod(RelinkableCallSite relinkableCallSite, int i) {
        MethodHandle methodHandleInsertArguments = MethodHandles.insertArguments(RELINK, 0, this, relinkableCallSite, Integer.valueOf(i));
        MethodType methodType = relinkableCallSite.getDescriptor().getMethodType();
        return MethodHandles.foldArguments(MethodHandles.exactInvoker(methodType), methodHandleInsertArguments.asCollector(Object[].class, methodType.parameterCount()).asType(methodType.changeReturnType(MethodHandle.class)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x010c A[PHI: r18
  0x010c: PHI (r18v1 int) = (r18v0 int), (r18v0 int), (r18v2 int) binds: [B:26:0x00e2, B:28:0x00eb, B:30:0x00f7] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private MethodHandle relink(RelinkableCallSite relinkableCallSite, int i, Object[] objArr) {
        CallSiteDescriptor descriptor = relinkableCallSite.getDescriptor();
        boolean z = this.unstableRelinkThreshold > 0;
        boolean z2 = z && i >= this.unstableRelinkThreshold;
        LinkRequest linkRequestImpl = this.runtimeContextArgCount == 0 ? new LinkRequestImpl(descriptor, relinkableCallSite, i, z2, objArr) : new RuntimeContextLinkRequestImpl(descriptor, relinkableCallSite, i, z2, objArr, this.runtimeContextArgCount);
        GuardedInvocation guardedInvocation = this.linkerServices.getGuardedInvocation(linkRequestImpl);
        if (guardedInvocation == null) {
            throw new NoSuchDynamicMethodException(descriptor.toString());
        }
        if (this.runtimeContextArgCount > 0) {
            MethodType methodType = descriptor.getMethodType();
            if (guardedInvocation.getInvocation().type().parameterCount() == methodType.parameterCount() - this.runtimeContextArgCount) {
                List<Class<?>> listSubList = methodType.parameterList().subList(1, this.runtimeContextArgCount + 1);
                guardedInvocation.getGuard();
                guardedInvocation = guardedInvocation.dropArguments(1, listSubList);
            }
        }
        GuardedInvocation guardedInvocationFilter = this.prelinkFilter.filter(guardedInvocation, linkRequestImpl, this.linkerServices);
        Objects.requireNonNull(guardedInvocationFilter);
        int i2 = i;
        if (!z || i2 > this.unstableRelinkThreshold) {
            relinkableCallSite.relink(guardedInvocationFilter, createRelinkAndInvokeMethod(relinkableCallSite, i2));
        } else {
            i2++;
            if (i2 == this.unstableRelinkThreshold) {
                relinkableCallSite.resetAndRelink(guardedInvocationFilter, createRelinkAndInvokeMethod(relinkableCallSite, i2));
            }
        }
        if (this.syncOnRelink) {
            MutableCallSite.syncAll(new MutableCallSite[]{(MutableCallSite) relinkableCallSite});
        }
        return guardedInvocationFilter.getInvocation();
    }

    public static StackTraceElement getLinkedCallSiteLocation() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        for (int i = 0; i < stackTrace.length - 1; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            if (isRelinkFrame(stackTraceElement) || isInitialLinkFrame(stackTraceElement)) {
                return stackTrace[i + 1];
            }
        }
        return null;
    }

    @Deprecated
    public static StackTraceElement getRelinkedCallSiteLocation() {
        return getLinkedCallSiteLocation();
    }

    private static boolean isInitialLinkFrame(StackTraceElement stackTraceElement) {
        return testFrame(stackTraceElement, INITIAL_LINK_METHOD_NAME, INITIAL_LINK_CLASS_NAME);
    }

    private static boolean isRelinkFrame(StackTraceElement stackTraceElement) {
        return testFrame(stackTraceElement, RELINK_METHOD_NAME, CLASS_NAME);
    }

    private static boolean testFrame(StackTraceElement stackTraceElement, String str, String str2) {
        return str.equals(stackTraceElement.getMethodName()) && str2.equals(stackTraceElement.getClassName());
    }
}
