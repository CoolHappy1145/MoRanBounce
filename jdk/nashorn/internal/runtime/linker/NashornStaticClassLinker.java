package jdk.nashorn.internal.runtime.linker;

import java.lang.reflect.Modifier;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/NashornStaticClassLinker.class */
final class NashornStaticClassLinker implements TypeBasedGuardingDynamicLinker {
    private static final GuardingDynamicLinker staticClassLinker = BeansLinker.getLinkerForClass(StaticClass.class);

    NashornStaticClassLinker() {
    }

    @Override // jdk.internal.dynalink.linker.GuardingDynamicLinker
    public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) {
        LinkRequest linkRequestWithoutRuntimeContext = linkRequest.withoutRuntimeContext();
        Object receiver = linkRequestWithoutRuntimeContext.getReceiver();
        if (receiver.getClass() != StaticClass.class) {
            return null;
        }
        Class representedClass = ((StaticClass) receiver).getRepresentedClass();
        Bootstrap.checkReflectionAccess(representedClass, true);
        if ("new".equals(linkRequestWithoutRuntimeContext.getCallSiteDescriptor().getNameToken(1))) {
            if (!Modifier.isPublic(representedClass.getModifiers())) {
                throw ECMAErrors.typeError("new.on.nonpublic.javatype", new String[]{representedClass.getName()});
            }
            Context.checkPackageAccess((Class<?>) representedClass);
            if (NashornLinker.isAbstractClass(representedClass)) {
                Object[] arguments = linkRequestWithoutRuntimeContext.getArguments();
                arguments[0] = JavaAdapterFactory.getAdapterClassFor(new Class[]{representedClass}, (ScriptObject) null, linkRequest.getCallSiteDescriptor().getLookup());
                GuardedInvocation guardedInvocationCheckNullConstructor = checkNullConstructor(delegate(linkerServices, linkRequestWithoutRuntimeContext.replaceArguments(linkRequestWithoutRuntimeContext.getCallSiteDescriptor(), arguments)), representedClass);
                return guardedInvocationCheckNullConstructor.replaceMethods(guardedInvocationCheckNullConstructor.getInvocation(), Guards.getIdentityGuard(receiver));
            }
            return checkNullConstructor(delegate(linkerServices, linkRequestWithoutRuntimeContext), representedClass);
        }
        return delegate(linkerServices, linkRequestWithoutRuntimeContext);
    }

    private static GuardedInvocation delegate(LinkerServices linkerServices, LinkRequest linkRequest) {
        return NashornBeansLinker.getGuardedInvocation(staticClassLinker, linkRequest, linkerServices);
    }

    private static GuardedInvocation checkNullConstructor(GuardedInvocation guardedInvocation, Class cls) {
        if (guardedInvocation == null) {
            throw ECMAErrors.typeError("no.constructor.matches.args", new String[]{cls.getName()});
        }
        return guardedInvocation;
    }
}
