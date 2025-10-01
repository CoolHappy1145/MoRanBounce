package jdk.nashorn.internal.p001ir;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/Assignment.class */
public interface Assignment {
    Expression getAssignmentDest();

    Expression getAssignmentSource();

    Node setAssignmentDest(Expression expression);
}
