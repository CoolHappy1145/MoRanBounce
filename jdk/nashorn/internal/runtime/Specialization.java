package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Specialization.class */
public final class Specialization {

    /* renamed from: mh */
    private final MethodHandle f53mh;
    private final Class linkLogicClass;
    private final boolean isOptimistic;

    public Specialization(MethodHandle methodHandle) {
        this(methodHandle, false);
    }

    public Specialization(MethodHandle methodHandle, boolean z) {
        this(methodHandle, null, z);
    }

    public Specialization(MethodHandle methodHandle, Class cls, boolean z) {
        this.f53mh = methodHandle;
        this.isOptimistic = z;
        if (cls != null) {
            this.linkLogicClass = cls == SpecializedFunction.LinkLogic.Empty.class ? null : cls;
        } else {
            this.linkLogicClass = null;
        }
    }

    public MethodHandle getMethodHandle() {
        return this.f53mh;
    }

    public Class getLinkLogicClass() {
        return this.linkLogicClass;
    }

    public boolean isOptimistic() {
        return this.isOptimistic;
    }
}
