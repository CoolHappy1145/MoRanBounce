package jdk.nashorn.internal.p001ir;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/JoinPredecessor.class */
public interface JoinPredecessor {
    JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion);

    LocalVariableConversion getLocalVariableConversion();
}
