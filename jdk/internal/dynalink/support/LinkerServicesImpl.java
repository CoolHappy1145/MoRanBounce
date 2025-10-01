package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.MethodHandleTransformer;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/LinkerServicesImpl.class */
public class LinkerServicesImpl implements LinkerServices {
    private static final RuntimePermission GET_CURRENT_LINK_REQUEST = new RuntimePermission("dynalink.getCurrentLinkRequest");
    private static final ThreadLocal threadLinkRequest = new ThreadLocal();
    private final TypeConverterFactory typeConverterFactory;
    private final GuardingDynamicLinker topLevelLinker;
    private final MethodHandleTransformer internalObjectsFilter;

    public LinkerServicesImpl(TypeConverterFactory typeConverterFactory, GuardingDynamicLinker guardingDynamicLinker, MethodHandleTransformer methodHandleTransformer) {
        this.typeConverterFactory = typeConverterFactory;
        this.topLevelLinker = guardingDynamicLinker;
        this.internalObjectsFilter = methodHandleTransformer;
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public boolean canConvert(Class cls, Class cls2) {
        return this.typeConverterFactory.canConvert(cls, cls2);
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public MethodHandle asType(MethodHandle methodHandle, MethodType methodType) {
        return this.typeConverterFactory.asType(methodHandle, methodType);
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public MethodHandle asTypeLosslessReturn(MethodHandle methodHandle, MethodType methodType) {
        return LinkerServices.Implementation.asTypeLosslessReturn(this, methodHandle, methodType);
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public MethodHandle getTypeConverter(Class cls, Class cls2) {
        return this.typeConverterFactory.getTypeConverter(cls, cls2);
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public ConversionComparator.Comparison compareConversion(Class cls, Class cls2, Class cls3) {
        return this.typeConverterFactory.compareConversion(cls, cls2, cls3);
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest) {
        LinkRequest linkRequest2 = (LinkRequest) threadLinkRequest.get();
        threadLinkRequest.set(linkRequest);
        try {
            GuardedInvocation guardedInvocation = this.topLevelLinker.getGuardedInvocation(linkRequest, this);
            threadLinkRequest.set(linkRequest2);
            return guardedInvocation;
        } catch (Throwable th) {
            threadLinkRequest.set(linkRequest2);
            throw th;
        }
    }

    @Override // jdk.internal.dynalink.linker.LinkerServices
    public MethodHandle filterInternalObjects(MethodHandle methodHandle) {
        return this.internalObjectsFilter != null ? this.internalObjectsFilter.transform(methodHandle) : methodHandle;
    }

    public static LinkRequest getCurrentLinkRequest() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(GET_CURRENT_LINK_REQUEST);
        }
        return (LinkRequest) threadLinkRequest.get();
    }
}
