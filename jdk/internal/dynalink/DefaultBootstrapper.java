package jdk.internal.dynalink;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;

/* loaded from: L-out.jar:jdk/internal/dynalink/DefaultBootstrapper.class */
public class DefaultBootstrapper {
    private static final DynamicLinker dynamicLinker = new DynamicLinkerFactory().createLinker();

    private DefaultBootstrapper() {
    }

    public static CallSite bootstrap(MethodHandles.Lookup lookup, String str, MethodType methodType) {
        return bootstrapInternal(lookup, str, methodType);
    }

    public static CallSite publicBootstrap(MethodHandles.Lookup lookup, String str, MethodType methodType) {
        return bootstrapInternal(MethodHandles.publicLookup(), str, methodType);
    }

    private static CallSite bootstrapInternal(MethodHandles.Lookup lookup, String str, MethodType methodType) {
        return (CallSite) dynamicLinker.link(new MonomorphicCallSite(CallSiteDescriptorFactory.create(lookup, str, methodType)));
    }
}
