package jdk.nashorn.internal.p001ir;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/Node.class */
public abstract class Node implements Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    public static final int NO_LINE_NUMBER = -1;
    public static final long NO_TOKEN = 0;
    public static final int NO_FINISH = 0;
    protected final int start;
    protected int finish;
    private final long token;

    public abstract Node accept(NodeVisitor nodeVisitor);

    public abstract void toString(StringBuilder sb, boolean z);

    public Node(long j, int i) {
        this.token = j;
        this.start = (int) (j >>> 32);
        this.finish = i;
    }

    protected Node(long j, int i, int i2) {
        this.start = i;
        this.finish = i2;
        this.token = j;
    }

    protected Node(Node node) {
        this.token = node.token;
        this.start = node.start;
        this.finish = node.finish;
    }

    public final String toString() {
        return toString(true);
    }

    public final String toString(boolean z) {
        StringBuilder sb = new StringBuilder();
        toString(sb, z);
        return sb.toString();
    }

    public void toString(StringBuilder sb) {
        toString(sb, true);
    }

    public int getFinish() {
        return this.finish;
    }

    public void setFinish(int i) {
        this.finish = i;
    }

    public int getStart() {
        return this.start;
    }

    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final int hashCode() {
        return Long.hashCode(this.token);
    }

    public int position() {
        return (int) (this.token >>> 32);
    }

    public int length() {
        return ((int) this.token) >>> 8;
    }

    public TokenType tokenType() {
        return Token.descType(this.token);
    }

    public boolean isTokenType(TokenType tokenType) {
        return tokenType() == tokenType;
    }

    public long getToken() {
        return this.token;
    }

    static List accept(NodeVisitor nodeVisitor, List list) {
        int size = list.size();
        if (size == 0) {
            return list;
        }
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            Node node = (Node) list.get(i);
            Node nodeAccept = node == null ? null : node.accept(nodeVisitor);
            if (nodeAccept != node) {
                if (arrayList == null) {
                    arrayList = new ArrayList(size);
                    for (int i2 = 0; i2 < i; i2++) {
                        arrayList.add(list.get(i2));
                    }
                }
                arrayList.add(nodeAccept);
            } else if (arrayList != null) {
                arrayList.add(node);
            }
        }
        return arrayList == null ? list : arrayList;
    }

    static LexicalContextNode replaceInLexicalContext(LexicalContext lexicalContext, LexicalContextNode lexicalContextNode, LexicalContextNode lexicalContextNode2) {
        if (lexicalContext != null) {
            lexicalContext.replace(lexicalContextNode, lexicalContextNode2);
        }
        return lexicalContextNode2;
    }
}
