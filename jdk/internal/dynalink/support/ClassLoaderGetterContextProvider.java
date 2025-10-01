package jdk.internal.dynalink.support;

import java.security.AccessControlContext;
import java.security.Permissions;
import java.security.ProtectionDomain;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/ClassLoaderGetterContextProvider.class */
public class ClassLoaderGetterContextProvider {
    public static final AccessControlContext GET_CLASS_LOADER_CONTEXT;

    static {
        Permissions permissions = new Permissions();
        permissions.add(new RuntimePermission("getClassLoader"));
        GET_CLASS_LOADER_CONTEXT = new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissions)});
    }
}
