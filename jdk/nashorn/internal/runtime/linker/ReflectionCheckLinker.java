package jdk.nashorn.internal.runtime.linker;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/ReflectionCheckLinker.class */
final class ReflectionCheckLinker implements TypeBasedGuardingDynamicLinker {
    private static final Class STATEMENT_CLASS = getBeanClass("Statement");
    private static final Class XMLENCODER_CLASS = getBeanClass("XMLEncoder");
    private static final Class XMLDECODER_CLASS = getBeanClass("XMLDecoder");

    ReflectionCheckLinker() {
    }

    private static Class getBeanClass(String str) {
        try {
            return Class.forName("java.beans." + str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    @Override // jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker
    public boolean canLinkType(Class cls) {
        return isReflectionClass(cls);
    }

    private static boolean isReflectionClass(Class cls) {
        if (cls == Class.class || ClassLoader.class.isAssignableFrom(cls)) {
            return true;
        }
        if (STATEMENT_CLASS == null || !STATEMENT_CLASS.isAssignableFrom(cls)) {
            if (XMLENCODER_CLASS == null || !XMLENCODER_CLASS.isAssignableFrom(cls)) {
                if (XMLDECODER_CLASS != null && XMLDECODER_CLASS.isAssignableFrom(cls)) {
                    return true;
                }
                String name = cls.getName();
                return name.startsWith("java.lang.reflect.") || name.startsWith("java.lang.invoke.");
            }
            return true;
        }
        return true;
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        checkLinkRequest(linkRequest);
        return null;
    }

    private static boolean isReflectiveCheckNeeded(Class cls, boolean z) {
        if (Proxy.class.isAssignableFrom(cls)) {
            if (Proxy.isProxyClass(cls)) {
                return z;
            }
            return true;
        }
        return isReflectionClass(cls);
    }

    static void checkReflectionAccess(Class cls, boolean z) {
        if (Context.getGlobal().getClassFilter() != null && isReflectiveCheckNeeded(cls, z)) {
            throw ECMAErrors.typeError("no.reflection.with.classfilter", new String[0]);
        }
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null && isReflectiveCheckNeeded(cls, z)) {
            checkReflectionPermission(securityManager);
        }
    }

    private static void checkLinkRequest(LinkRequest linkRequest) {
        if (Context.getGlobal().getClassFilter() != null) {
            throw ECMAErrors.typeError("no.reflection.with.classfilter", new String[0]);
        }
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            LinkRequest linkRequestWithoutRuntimeContext = linkRequest.withoutRuntimeContext();
            Object receiver = linkRequestWithoutRuntimeContext.getReceiver();
            if ((receiver instanceof Class) && Modifier.isPublic(((Class) receiver).getModifiers())) {
                CallSiteDescriptor callSiteDescriptor = linkRequestWithoutRuntimeContext.getCallSiteDescriptor();
                if (CallSiteDescriptorFactory.tokenizeOperators(callSiteDescriptor).contains("getProp") && callSiteDescriptor.getNameTokenCount() > 2 && "static".equals(callSiteDescriptor.getNameToken(2)) && Context.isAccessibleClass((Class) receiver) && !isReflectionClass((Class) receiver)) {
                    return;
                }
            }
            checkReflectionPermission(securityManager);
        }
    }

    private static void checkReflectionPermission(SecurityManager securityManager) {
        securityManager.checkPermission(new RuntimePermission(Context.NASHORN_JAVA_REFLECTION));
    }
}
