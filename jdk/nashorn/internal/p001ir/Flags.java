package jdk.nashorn.internal.p001ir;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/Flags.class */
public interface Flags {
    int getFlags();

    boolean getFlag(int i);

    LexicalContextNode clearFlag(LexicalContext lexicalContext, int i);

    LexicalContextNode setFlag(LexicalContext lexicalContext, int i);

    LexicalContextNode setFlags(LexicalContext lexicalContext, int i);
}
