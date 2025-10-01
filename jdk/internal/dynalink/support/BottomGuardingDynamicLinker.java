package jdk.internal.dynalink.support;

import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/BottomGuardingDynamicLinker.class */
public class BottomGuardingDynamicLinker implements TypeBasedGuardingDynamicLinker {
    public static final BottomGuardingDynamicLinker INSTANCE = new BottomGuardingDynamicLinker();

    private BottomGuardingDynamicLinker() {
    }
}
