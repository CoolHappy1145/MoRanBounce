package jdk.nashorn.internal.runtime;

import jdk.nashorn.internal.objects.annotations.SpecializedFunction;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/OptimisticBuiltins.class */
public interface OptimisticBuiltins {
    SpecializedFunction.LinkLogic getLinkLogic(Class cls);

    boolean hasPerInstanceAssumptions();
}
