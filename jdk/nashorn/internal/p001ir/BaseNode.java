package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/BaseNode.class */
public abstract class BaseNode extends Expression implements FunctionCall, Optimistic {
    private static final long serialVersionUID = 1;
    protected final Expression base;
    private final boolean isFunction;
    protected final Type type;
    protected final int programPoint;

    public abstract BaseNode setIsFunction();

    public BaseNode(long j, int i, Expression expression, boolean z) {
        super(j, expression.getStart(), i);
        this.base = expression;
        this.isFunction = z;
        this.type = null;
        this.programPoint = -1;
    }

    protected BaseNode(BaseNode baseNode, Expression expression, boolean z, Type type, int i) {
        super(baseNode);
        this.base = expression;
        this.isFunction = z;
        this.type = type;
        this.programPoint = i;
    }

    public Expression getBase() {
        return this.base;
    }

    @Override // jdk.nashorn.internal.p001ir.FunctionCall
    public boolean isFunction() {
        return this.isFunction;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return this.type == null ? getMostPessimisticType() : this.type;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public int getProgramPoint() {
        return this.programPoint;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostOptimisticType() {
        return Type.INT;
    }

    @Override // jdk.nashorn.internal.p001ir.Optimistic
    public Type getMostPessimisticType() {
        return Type.OBJECT;
    }

    public boolean isIndex() {
        return isTokenType(TokenType.LBRACKET);
    }
}
