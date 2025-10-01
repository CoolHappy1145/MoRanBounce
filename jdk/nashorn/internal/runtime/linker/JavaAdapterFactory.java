package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.support.LinkRequestImpl;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.linker.AdaptationResult;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JavaAdapterFactory.class */
public final class JavaAdapterFactory {
    private static final ProtectionDomain MINIMAL_PERMISSION_DOMAIN;
    private static final AccessControlContext CREATE_ADAPTER_INFO_ACC_CTXT;
    private static final ClassValue ADAPTER_INFO_MAPS;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JavaAdapterFactory.class.desiredAssertionStatus();
        MINIMAL_PERMISSION_DOMAIN = createMinimalPermissionDomain();
        CREATE_ADAPTER_INFO_ACC_CTXT = ClassAndLoader.createPermAccCtxt(new String[]{"createClassLoader", "getClassLoader", "accessDeclaredMembers", "accessClassInPackage.jdk.nashorn.internal.runtime"});
        ADAPTER_INFO_MAPS = new ClassValue() { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterFactory.1
            @Override // java.lang.ClassValue
            protected Object computeValue(Class cls) {
                return computeValue(cls);
            }

            @Override // java.lang.ClassValue
            protected Map computeValue(Class cls) {
                return new HashMap();
            }
        };
    }

    public static StaticClass getAdapterClassFor(Class[] clsArr, ScriptObject scriptObject, MethodHandles.Lookup lookup) {
        return getAdapterClassFor(clsArr, scriptObject, getProtectionDomain(lookup));
    }

    private static StaticClass getAdapterClassFor(Class[] clsArr, ScriptObject scriptObject, ProtectionDomain protectionDomain) {
        if (!$assertionsDisabled && (clsArr == null || clsArr.length <= 0)) {
            throw new AssertionError();
        }
        if (System.getSecurityManager() != null) {
            for (Class cls : clsArr) {
                Context.checkPackageAccess((Class<?>) cls);
                ReflectionCheckLinker.checkReflectionAccess(cls, true);
            }
        }
        return getAdapterInfo(clsArr).getAdapterClass(scriptObject, protectionDomain);
    }

    private static ProtectionDomain getProtectionDomain(MethodHandles.Lookup lookup) {
        if ((lookup.lookupModes() & 2) == 0) {
            return MINIMAL_PERMISSION_DOMAIN;
        }
        return getProtectionDomain(lookup.lookupClass());
    }

    private static ProtectionDomain getProtectionDomain(Class cls) {
        return (ProtectionDomain) AccessController.doPrivileged(new PrivilegedAction(cls) { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterFactory.2
            final Class val$clazz;

            {
                this.val$clazz = cls;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public ProtectionDomain run() {
                return this.val$clazz.getProtectionDomain();
            }
        });
    }

    public static MethodHandle getConstructor(Class cls, Class cls2, MethodHandles.Lookup lookup) {
        StaticClass adapterClassFor = getAdapterClassFor(new Class[]{cls2}, (ScriptObject) null, lookup);
        return Lookup.f31MH.bindTo(Bootstrap.getLinkerServices().getGuardedInvocation(new LinkRequestImpl(NashornCallSiteDescriptor.get(lookup, "dyn:new", MethodType.methodType(cls2, StaticClass.class, cls), 0), null, 0, false, new Object[]{adapterClassFor, null})).getInvocation(), adapterClassFor);
    }

    static boolean isAutoConvertibleFromFunction(Class cls) {
        return getAdapterInfo(new Class[]{cls}).autoConvertibleFromFunction;
    }

    private static AdapterInfo getAdapterInfo(Class[] clsArr) {
        AdapterInfo adapterInfoCreateAdapterInfo;
        ClassAndLoader definingClassAndLoader = ClassAndLoader.getDefiningClassAndLoader(clsArr);
        Map map = (Map) ADAPTER_INFO_MAPS.get(definingClassAndLoader.getRepresentativeClass());
        List listSingletonList = clsArr.length == 1 ? Collections.singletonList(clsArr[0]) : Arrays.asList((Object[]) clsArr.clone());
        synchronized (map) {
            adapterInfoCreateAdapterInfo = (AdapterInfo) map.get(listSingletonList);
            if (adapterInfoCreateAdapterInfo == null) {
                adapterInfoCreateAdapterInfo = createAdapterInfo(clsArr, definingClassAndLoader);
                map.put(listSingletonList, adapterInfoCreateAdapterInfo);
            }
        }
        return adapterInfoCreateAdapterInfo;
    }

    private static AdapterInfo createAdapterInfo(Class[] clsArr, ClassAndLoader classAndLoader) {
        Class cls = null;
        ArrayList arrayList = new ArrayList(clsArr.length);
        for (Class cls2 : clsArr) {
            int modifiers = cls2.getModifiers();
            if (!cls2.isInterface()) {
                if (cls != null) {
                    return new AdapterInfo(AdaptationResult.Outcome.ERROR_MULTIPLE_SUPERCLASSES, cls2.getCanonicalName() + " and " + cls.getCanonicalName());
                }
                if (Modifier.isFinal(modifiers)) {
                    return new AdapterInfo(AdaptationResult.Outcome.ERROR_FINAL_CLASS, cls2.getCanonicalName());
                }
                cls = cls2;
            } else {
                if (arrayList.size() > 65535) {
                    throw new IllegalArgumentException("interface limit exceeded");
                }
                arrayList.add(cls2);
            }
            if (!Modifier.isPublic(modifiers)) {
                return new AdapterInfo(AdaptationResult.Outcome.ERROR_NON_PUBLIC_CLASS, cls2.getCanonicalName());
            }
        }
        return (AdapterInfo) AccessController.doPrivileged(new PrivilegedAction(cls == null ? Object.class : cls, arrayList, classAndLoader, clsArr) { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterFactory.3
            final Class val$effectiveSuperClass;
            final List val$interfaces;
            final ClassAndLoader val$definingClassAndLoader;
            final Class[] val$types;

            {
                this.val$effectiveSuperClass = cls;
                this.val$interfaces = arrayList;
                this.val$definingClassAndLoader = classAndLoader;
                this.val$types = clsArr;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public AdapterInfo run() {
                try {
                    return new AdapterInfo(this.val$effectiveSuperClass, this.val$interfaces, this.val$definingClassAndLoader);
                } catch (RuntimeException e) {
                    return new AdapterInfo(new AdaptationResult(AdaptationResult.Outcome.ERROR_OTHER, new String[]{Arrays.toString(this.val$types), e.toString()}));
                } catch (AdaptationException e2) {
                    return new AdapterInfo(e2.getAdaptationResult());
                }
            }
        }, CREATE_ADAPTER_INFO_ACC_CTXT);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JavaAdapterFactory$AdapterInfo.class */
    private static class AdapterInfo {
        private static final ClassAndLoader SCRIPT_OBJECT_LOADER = new ClassAndLoader(ScriptFunction.class, true);
        private final ClassLoader commonLoader;
        private final JavaAdapterClassLoader classAdapterGenerator;
        private final JavaAdapterClassLoader instanceAdapterGenerator;
        private final Map instanceAdapters;
        final boolean autoConvertibleFromFunction;
        final AdaptationResult adaptationResult;

        AdapterInfo(Class cls, List list, ClassAndLoader classAndLoader) {
            this.instanceAdapters = new ConcurrentHashMap();
            this.commonLoader = findCommonLoader(classAndLoader);
            JavaAdapterBytecodeGenerator javaAdapterBytecodeGenerator = new JavaAdapterBytecodeGenerator(cls, list, this.commonLoader, false);
            this.autoConvertibleFromFunction = javaAdapterBytecodeGenerator.isAutoConvertibleFromFunction();
            this.instanceAdapterGenerator = javaAdapterBytecodeGenerator.createAdapterClassLoader();
            this.classAdapterGenerator = new JavaAdapterBytecodeGenerator(cls, list, this.commonLoader, true).createAdapterClassLoader();
            this.adaptationResult = AdaptationResult.SUCCESSFUL_RESULT;
        }

        AdapterInfo(AdaptationResult.Outcome outcome, String str) {
            this(new AdaptationResult(outcome, new String[]{str}));
        }

        AdapterInfo(AdaptationResult adaptationResult) {
            this.instanceAdapters = new ConcurrentHashMap();
            this.commonLoader = null;
            this.classAdapterGenerator = null;
            this.instanceAdapterGenerator = null;
            this.autoConvertibleFromFunction = false;
            this.adaptationResult = adaptationResult;
        }

        StaticClass getAdapterClass(ScriptObject scriptObject, ProtectionDomain protectionDomain) {
            if (this.adaptationResult.getOutcome() != AdaptationResult.Outcome.SUCCESS) {
                throw this.adaptationResult.typeError();
            }
            return scriptObject == null ? getInstanceAdapterClass(protectionDomain) : getClassAdapterClass(scriptObject, protectionDomain);
        }

        private StaticClass getInstanceAdapterClass(ProtectionDomain protectionDomain) {
            CodeSource codeSource = protectionDomain.getCodeSource();
            if (codeSource == null) {
                codeSource = JavaAdapterFactory.MINIMAL_PERMISSION_DOMAIN.getCodeSource();
            }
            StaticClass staticClass = (StaticClass) this.instanceAdapters.get(codeSource);
            if (staticClass != null) {
                return staticClass;
            }
            StaticClass staticClassGenerateClass = this.instanceAdapterGenerator.generateClass(this.commonLoader, codeSource.equals(JavaAdapterFactory.MINIMAL_PERMISSION_DOMAIN.getCodeSource()) ? JavaAdapterFactory.MINIMAL_PERMISSION_DOMAIN : protectionDomain);
            StaticClass staticClass2 = (StaticClass) this.instanceAdapters.putIfAbsent(codeSource, staticClassGenerateClass);
            return staticClass2 == null ? staticClassGenerateClass : staticClass2;
        }

        private StaticClass getClassAdapterClass(ScriptObject scriptObject, ProtectionDomain protectionDomain) {
            JavaAdapterServices.setClassOverrides(scriptObject);
            try {
                StaticClass staticClassGenerateClass = this.classAdapterGenerator.generateClass(this.commonLoader, protectionDomain);
                JavaAdapterServices.setClassOverrides(null);
                return staticClassGenerateClass;
            } catch (Throwable th) {
                JavaAdapterServices.setClassOverrides(null);
                throw th;
            }
        }

        private static ClassLoader findCommonLoader(ClassAndLoader classAndLoader) throws AdaptationException {
            if (classAndLoader.canSee(SCRIPT_OBJECT_LOADER)) {
                return classAndLoader.getLoader();
            }
            if (SCRIPT_OBJECT_LOADER.canSee(classAndLoader)) {
                return SCRIPT_OBJECT_LOADER.getLoader();
            }
            throw new AdaptationException(AdaptationResult.Outcome.ERROR_NO_COMMON_LOADER, classAndLoader.getRepresentativeClass().getCanonicalName());
        }
    }

    private static ProtectionDomain createMinimalPermissionDomain() {
        Permissions permissions = new Permissions();
        permissions.add(new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.objects"));
        permissions.add(new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime"));
        permissions.add(new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime.linker"));
        return new ProtectionDomain(new CodeSource((URL) null, (CodeSigner[]) null), permissions);
    }
}
