package jdk.nashorn.internal.runtime.linker;

import java.util.Objects;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JavaSuperAdapter.class */
class JavaSuperAdapter {
    private final Object adapter;

    JavaSuperAdapter(Object obj) {
        this.adapter = Objects.requireNonNull(obj);
    }

    public Object getAdapter() {
        return this.adapter;
    }
}
