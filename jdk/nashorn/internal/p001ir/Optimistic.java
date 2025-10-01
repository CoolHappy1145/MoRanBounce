package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/Optimistic.class */
public interface Optimistic {
    int getProgramPoint();

    Optimistic setProgramPoint(int i);

    boolean canBeOptimistic();

    Type getMostOptimisticType();

    Type getMostPessimisticType();

    Optimistic setType(Type type);
}
