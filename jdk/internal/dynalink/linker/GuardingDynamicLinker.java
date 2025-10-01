package jdk.internal.dynalink.linker;

/* loaded from: L-out.jar:jdk/internal/dynalink/linker/GuardingDynamicLinker.class */
public interface GuardingDynamicLinker {
    GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices);
}
