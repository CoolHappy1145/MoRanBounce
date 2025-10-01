package jdk.nashorn.internal.p001ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/RuntimeNode.class */
public class RuntimeNode extends Expression {
    private static final long serialVersionUID = 1;
    private final Request request;
    private final List<Expression> args;

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/RuntimeNode$Request.class */
    public enum Request {
        ADD(TokenType.ADD, Type.OBJECT, 2, true),
        DEBUGGER,
        NEW,
        TYPEOF,
        REFERENCE_ERROR,
        DELETE(TokenType.DELETE, Type.BOOLEAN, 1),
        SLOW_DELETE(TokenType.DELETE, Type.BOOLEAN, 1, false),
        FAIL_DELETE(TokenType.DELETE, Type.BOOLEAN, 1, false),
        EQ_STRICT(TokenType.EQ_STRICT, Type.BOOLEAN, 2, true),
        EQ(TokenType.f42EQ, Type.BOOLEAN, 2, true),
        GE(TokenType.f44GE, Type.BOOLEAN, 2, true),
        GT(TokenType.f43GT, Type.BOOLEAN, 2, true),
        IN(TokenType.f48IN, Type.BOOLEAN, 2),
        INSTANCEOF(TokenType.INSTANCEOF, Type.BOOLEAN, 2),
        LE(TokenType.f41LE, Type.BOOLEAN, 2, true),
        LT(TokenType.f40LT, Type.BOOLEAN, 2, true),
        NE_STRICT(TokenType.NE_STRICT, Type.BOOLEAN, 2, true),
        NE(TokenType.f39NE, Type.BOOLEAN, 2, true),
        IS_UNDEFINED(TokenType.EQ_STRICT, Type.BOOLEAN, 2),
        IS_NOT_UNDEFINED(TokenType.NE_STRICT, Type.BOOLEAN, 2);

        private final TokenType tokenType;
        private final Type returnType;
        private final int arity;
        private final boolean canSpecialize;
        static final boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !RuntimeNode.class.desiredAssertionStatus();
        }

        Request() {
            this(TokenType.VOID, Type.OBJECT, 0);
        }

        Request(TokenType tokenType, Type type, int i) {
            this(tokenType, type, i, false);
        }

        Request(TokenType tokenType, Type type, int i, boolean z) {
            this.tokenType = tokenType;
            this.returnType = type;
            this.arity = i;
            this.canSpecialize = z;
        }

        public boolean canSpecialize() {
            return this.canSpecialize;
        }

        public int getArity() {
            return this.arity;
        }

        public Type getReturnType() {
            return this.returnType;
        }

        public TokenType getTokenType() {
            return this.tokenType;
        }

        public String nonStrictName() {
            switch (C01561.$SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[ordinal()]) {
                case 1:
                    return NE.name();
                case 2:
                    return EQ.name();
                default:
                    return name();
            }
        }

        public static Request requestFor(Expression expression) {
            switch (C01561.$SwitchMap$jdk$nashorn$internal$parser$TokenType[expression.tokenType().ordinal()]) {
                case 1:
                    return TYPEOF;
                case 2:
                    return IN;
                case 3:
                    return INSTANCEOF;
                case 4:
                    return EQ_STRICT;
                case 5:
                    return NE_STRICT;
                case 6:
                    return EQ;
                case 7:
                    return NE;
                case 8:
                    return LT;
                case 9:
                    return LE;
                case 10:
                    return GT;
                case 11:
                    return GE;
                default:
                    if ($assertionsDisabled) {
                        return null;
                    }
                    throw new AssertionError();
            }
        }

        public static boolean isUndefinedCheck(Request request) {
            return request == IS_UNDEFINED || request == IS_NOT_UNDEFINED;
        }

        public static boolean isEQ(Request request) {
            return request == EQ || request == EQ_STRICT;
        }

        public static boolean isNE(Request request) {
            return request == NE || request == NE_STRICT;
        }

        public static boolean isStrict(Request request) {
            return request == EQ_STRICT || request == NE_STRICT;
        }

        public static Request reverse(Request request) {
            switch (C01561.$SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[request.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                    return request;
                case 5:
                    return GE;
                case 6:
                    return GT;
                case 7:
                    return LE;
                case 8:
                    return LT;
                default:
                    return null;
            }
        }

        public static Request invert(Request request) {
            switch (C01561.$SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[request.ordinal()]) {
                case 1:
                    return EQ_STRICT;
                case 2:
                    return NE_STRICT;
                case 3:
                    return NE;
                case 4:
                    return EQ;
                case 5:
                    return GT;
                case 6:
                    return GE;
                case 7:
                    return LT;
                case 8:
                    return LE;
                default:
                    return null;
            }
        }

        public static boolean isComparison(Request request) {
            switch (C01561.$SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[request.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    return true;
                default:
                    return false;
            }
        }
    }

    /* renamed from: jdk.nashorn.internal.ir.RuntimeNode$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/RuntimeNode$1.class */
    static /* synthetic */ class C01561 {
        static final int[] $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request;
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.TYPEOF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f48IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.INSTANCEOF.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.EQ_STRICT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NE_STRICT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f42EQ.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f39NE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f40LT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f41LE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f43GT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f44GE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request = new int[Request.values().length];
            try {
                $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[Request.NE_STRICT.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[Request.EQ_STRICT.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[Request.EQ.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[Request.NE.ordinal()] = 4;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[Request.LE.ordinal()] = 5;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[Request.LT.ordinal()] = 6;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[Request.GE.ordinal()] = 7;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[Request.GT.ordinal()] = 8;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[Request.IS_UNDEFINED.ordinal()] = 9;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$ir$RuntimeNode$Request[Request.IS_NOT_UNDEFINED.ordinal()] = 10;
            } catch (NoSuchFieldError unused21) {
            }
        }
    }

    public RuntimeNode(long token, int finish, Request request, List<Expression> args) {
        super(token, finish);
        this.request = request;
        this.args = args;
    }

    private RuntimeNode(RuntimeNode runtimeNode, Request request, List<Expression> args) {
        super(runtimeNode);
        this.request = request;
        this.args = args;
    }

    public RuntimeNode(long token, int finish, Request request, Expression... args) {
        this(token, finish, request, (List<Expression>) Arrays.asList(args));
    }

    public RuntimeNode(Expression parent, Request request, Expression... args) {
        this(parent, request, (List<Expression>) Arrays.asList(args));
    }

    public RuntimeNode(Expression parent, Request request, List<Expression> args) {
        super(parent);
        this.request = request;
        this.args = args;
    }

    public RuntimeNode(UnaryNode parent, Request request) {
        this(parent, request, parent.getExpression());
    }

    public RuntimeNode(BinaryNode parent) {
        this(parent, Request.requestFor(parent), parent.lhs(), parent.rhs());
    }

    public RuntimeNode setRequest(Request request) {
        if (this.request == request) {
            return this;
        }
        return new RuntimeNode(this, request, this.args);
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return this.request.getReturnType();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterRuntimeNode(this)) {
            return visitor.leaveRuntimeNode(setArgs(Node.accept(visitor, this.args)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("ScriptRuntime.");
        sb.append(this.request);
        sb.append('(');
        boolean first = true;
        for (Node arg : this.args) {
            if (!first) {
                sb.append(", ");
            } else {
                first = false;
            }
            arg.toString(sb, printType);
        }
        sb.append(')');
    }

    public List<Expression> getArgs() {
        return Collections.unmodifiableList(this.args);
    }

    public RuntimeNode setArgs(List<Expression> args) {
        if (this.args == args) {
            return this;
        }
        return new RuntimeNode(this, this.request, args);
    }

    public Request getRequest() {
        return this.request;
    }

    public boolean isPrimitive() {
        for (Expression arg : this.args) {
            if (arg.getType().isObject()) {
                return false;
            }
        }
        return true;
    }
}
