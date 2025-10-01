package jdk.nashorn.internal.p001ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.types.ArrayType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Undefined;
import org.apache.log4j.spi.Configurator;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/LiteralNode.class */
public abstract class LiteralNode extends Expression implements PropertyKey {
    private static final long serialVersionUID = 1;
    protected final Object value;
    public static final Object POSTSET_MARKER;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !LiteralNode.class.desiredAssertionStatus();
        POSTSET_MARKER = new Object();
    }

    protected LiteralNode(long j, int i, Object obj) {
        super(j, i);
        this.value = obj;
    }

    protected LiteralNode(LiteralNode literalNode) {
        this(literalNode, literalNode.value);
    }

    protected LiteralNode(LiteralNode literalNode, Object obj) {
        super(literalNode);
        this.value = obj;
    }

    public boolean isNull() {
        return this.value == null;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return Type.typeFor(this.value.getClass());
    }

    @Override // jdk.nashorn.internal.p001ir.PropertyKey
    public String getPropertyName() {
        return JSType.toString(getObject());
    }

    public boolean getBoolean() {
        return JSType.toBoolean(this.value);
    }

    public int getInt32() {
        return JSType.toInt32(this.value);
    }

    public long getUint32() {
        return JSType.toUint32(this.value);
    }

    public long getLong() {
        return JSType.toLong(this.value);
    }

    public double getNumber() {
        return JSType.toNumber(this.value);
    }

    public String getString() {
        return JSType.toString(this.value);
    }

    public Object getObject() {
        return this.value;
    }

    public boolean isString() {
        return this.value instanceof String;
    }

    public boolean isNumeric() {
        return this.value instanceof Number;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterLiteralNode(this)) {
            return nodeVisitor.leaveLiteralNode(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        if (this.value == null) {
            sb.append(Configurator.NULL);
        } else {
            sb.append(this.value.toString());
        }
    }

    public final Object getValue() {
        return this.value;
    }

    private static Expression[] valueToArray(List list) {
        return (Expression[]) list.toArray(new Expression[list.size()]);
    }

    public static LiteralNode newInstance(long j, int i) {
        return new NullLiteralNode(j, i, null);
    }

    public static LiteralNode newInstance(Node node) {
        return new NullLiteralNode(node.getToken(), node.getFinish(), null);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LiteralNode$PrimitiveLiteralNode.class */
    public static class PrimitiveLiteralNode extends LiteralNode {
        private static final long serialVersionUID = 1;

        PrimitiveLiteralNode(long j, int i, Object obj, C01551 c01551) {
            this(j, i, obj);
        }

        PrimitiveLiteralNode(PrimitiveLiteralNode primitiveLiteralNode, C01551 c01551) {
            this(primitiveLiteralNode);
        }

        private PrimitiveLiteralNode(long j, int i, Object obj) {
            super(j, i, obj);
        }

        private PrimitiveLiteralNode(PrimitiveLiteralNode primitiveLiteralNode) {
            super(primitiveLiteralNode);
        }

        public boolean isTrue() {
            return JSType.toBoolean(this.value);
        }

        @Override // jdk.nashorn.internal.p001ir.Expression
        public boolean isAlwaysFalse() {
            return !isTrue();
        }

        @Override // jdk.nashorn.internal.p001ir.Expression
        public boolean isAlwaysTrue() {
            return isTrue();
        }
    }

    @Immutable
    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LiteralNode$BooleanLiteralNode.class */
    private static final class BooleanLiteralNode extends PrimitiveLiteralNode {
        private static final long serialVersionUID = 1;

        BooleanLiteralNode(long j, int i, boolean z, C01551 c01551) {
            this(j, i, z);
        }

        private BooleanLiteralNode(long j, int i, boolean z) {
            super(Token.recast(j, z ? TokenType.TRUE : TokenType.FALSE), i, Boolean.valueOf(z), null);
        }

        private BooleanLiteralNode(BooleanLiteralNode booleanLiteralNode) {
            super(booleanLiteralNode, null);
        }

        @Override // jdk.nashorn.internal.ir.LiteralNode.PrimitiveLiteralNode
        public boolean isTrue() {
            return ((Boolean) this.value).booleanValue();
        }

        @Override // jdk.nashorn.internal.p001ir.LiteralNode, jdk.nashorn.internal.p001ir.Expression
        public Type getType() {
            return Type.BOOLEAN;
        }

        @Override // jdk.nashorn.internal.p001ir.Expression
        public Type getWidestOperationType() {
            return Type.BOOLEAN;
        }
    }

    public static LiteralNode newInstance(long j, int i, boolean z) {
        return new BooleanLiteralNode(j, i, z, null);
    }

    public static LiteralNode newInstance(Node node, boolean z) {
        return new BooleanLiteralNode(node.getToken(), node.getFinish(), z, null);
    }

    @Immutable
    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LiteralNode$NumberLiteralNode.class */
    private static final class NumberLiteralNode extends PrimitiveLiteralNode {
        private static final long serialVersionUID = 1;
        private final Type type;
        static final boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !LiteralNode.class.desiredAssertionStatus();
        }

        NumberLiteralNode(long j, int i, Number number, C01551 c01551) {
            this(j, i, number);
        }

        private NumberLiteralNode(long j, int i, Number number) {
            super(Token.recast(j, TokenType.DECIMAL), i, number, null);
            this.type = numberGetType((Number) this.value);
        }

        private NumberLiteralNode(NumberLiteralNode numberLiteralNode) {
            super(numberLiteralNode, null);
            this.type = numberGetType((Number) this.value);
        }

        private static Type numberGetType(Number number) {
            if (number instanceof Integer) {
                return Type.INT;
            }
            if (number instanceof Double) {
                return Type.NUMBER;
            }
            if ($assertionsDisabled) {
                return null;
            }
            throw new AssertionError();
        }

        @Override // jdk.nashorn.internal.p001ir.LiteralNode, jdk.nashorn.internal.p001ir.Expression
        public Type getType() {
            return this.type;
        }

        @Override // jdk.nashorn.internal.p001ir.Expression
        public Type getWidestOperationType() {
            return getType();
        }
    }

    public static LiteralNode newInstance(long j, int i, Number number) {
        if ($assertionsDisabled || !(number instanceof Long)) {
            return new NumberLiteralNode(j, i, number, null);
        }
        throw new AssertionError();
    }

    public static LiteralNode newInstance(Node node, Number number) {
        return new NumberLiteralNode(node.getToken(), node.getFinish(), number, null);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LiteralNode$UndefinedLiteralNode.class */
    private static class UndefinedLiteralNode extends PrimitiveLiteralNode {
        private static final long serialVersionUID = 1;

        UndefinedLiteralNode(long j, int i, C01551 c01551) {
            this(j, i);
        }

        private UndefinedLiteralNode(long j, int i) {
            super(Token.recast(j, TokenType.OBJECT), i, ScriptRuntime.UNDEFINED, null);
        }

        private UndefinedLiteralNode(UndefinedLiteralNode undefinedLiteralNode) {
            super(undefinedLiteralNode, null);
        }
    }

    public static LiteralNode newInstance(long j, int i, Undefined undefined) {
        return new UndefinedLiteralNode(j, i, null);
    }

    public static LiteralNode newInstance(Node node, Undefined undefined) {
        return new UndefinedLiteralNode(node.getToken(), node.getFinish(), null);
    }

    @Immutable
    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LiteralNode$StringLiteralNode.class */
    private static class StringLiteralNode extends PrimitiveLiteralNode {
        private static final long serialVersionUID = 1;

        StringLiteralNode(long j, int i, String str, C01551 c01551) {
            this(j, i, str);
        }

        private StringLiteralNode(long j, int i, String str) {
            super(Token.recast(j, TokenType.STRING), i, str, null);
        }

        private StringLiteralNode(StringLiteralNode stringLiteralNode) {
            super(stringLiteralNode, null);
        }

        @Override // jdk.nashorn.internal.p001ir.LiteralNode, jdk.nashorn.internal.p001ir.Node
        public void toString(StringBuilder sb, boolean z) {
            sb.append('\"');
            sb.append((String) this.value);
            sb.append('\"');
        }
    }

    public static LiteralNode newInstance(long j, int i, String str) {
        return new StringLiteralNode(j, i, str, null);
    }

    public static LiteralNode newInstance(Node node, String str) {
        return new StringLiteralNode(node.getToken(), node.getFinish(), str, null);
    }

    @Immutable
    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LiteralNode$LexerTokenLiteralNode.class */
    private static class LexerTokenLiteralNode extends LiteralNode {
        private static final long serialVersionUID = 1;

        LexerTokenLiteralNode(long j, int i, Lexer.LexerToken lexerToken, C01551 c01551) {
            this(j, i, lexerToken);
        }

        private LexerTokenLiteralNode(long j, int i, Lexer.LexerToken lexerToken) {
            super(Token.recast(j, TokenType.STRING), i, lexerToken);
        }

        private LexerTokenLiteralNode(LexerTokenLiteralNode lexerTokenLiteralNode) {
            super(lexerTokenLiteralNode);
        }

        @Override // jdk.nashorn.internal.p001ir.LiteralNode, jdk.nashorn.internal.p001ir.Expression
        public Type getType() {
            return Type.OBJECT;
        }

        @Override // jdk.nashorn.internal.p001ir.LiteralNode, jdk.nashorn.internal.p001ir.Node
        public void toString(StringBuilder sb, boolean z) {
            sb.append(((Lexer.LexerToken) this.value).toString());
        }
    }

    public static LiteralNode newInstance(long j, int i, Lexer.LexerToken lexerToken) {
        return new LexerTokenLiteralNode(j, i, lexerToken, null);
    }

    public static LiteralNode newInstance(Node node, Lexer.LexerToken lexerToken) {
        return new LexerTokenLiteralNode(node.getToken(), node.getFinish(), lexerToken, null);
    }

    public static Object objectAsConstant(Object obj) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof Number) || (obj instanceof String) || (obj instanceof Boolean)) {
            return obj;
        }
        if (obj instanceof LiteralNode) {
            return objectAsConstant(((LiteralNode) obj).getValue());
        }
        return POSTSET_MARKER;
    }

    public static boolean isConstant(Object obj) {
        return objectAsConstant(obj) != POSTSET_MARKER;
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LiteralNode$NullLiteralNode.class */
    private static final class NullLiteralNode extends PrimitiveLiteralNode {
        private static final long serialVersionUID = 1;

        NullLiteralNode(long j, int i, C01551 c01551) {
            this(j, i);
        }

        private NullLiteralNode(long j, int i) {
            super(Token.recast(j, TokenType.OBJECT), i, null, null);
        }

        @Override // jdk.nashorn.internal.p001ir.LiteralNode, jdk.nashorn.internal.p001ir.Node
        public Node accept(NodeVisitor nodeVisitor) {
            if (nodeVisitor.enterLiteralNode(this)) {
                return nodeVisitor.leaveLiteralNode(this);
            }
            return this;
        }

        @Override // jdk.nashorn.internal.p001ir.LiteralNode, jdk.nashorn.internal.p001ir.Expression
        public Type getType() {
            return Type.OBJECT;
        }

        @Override // jdk.nashorn.internal.p001ir.Expression
        public Type getWidestOperationType() {
            return Type.OBJECT;
        }
    }

    @Immutable
    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LiteralNode$ArrayLiteralNode.class */
    public static final class ArrayLiteralNode extends LiteralNode implements LexicalContextNode, Splittable {
        private static final long serialVersionUID = 1;
        private final Type elementType;
        private final Object presets;
        private final int[] postsets;
        private final List splitRanges;
        static final boolean $assertionsDisabled;

        /* renamed from: initialize, reason: collision with other method in class */
        public LiteralNode m217initialize(LexicalContext lexicalContext) {
            return initialize(lexicalContext);
        }

        static {
            $assertionsDisabled = !LiteralNode.class.desiredAssertionStatus();
        }

        ArrayLiteralNode(ArrayLiteralNode arrayLiteralNode, Expression[] expressionArr, Type type, int[] iArr, Object obj, List list, C01551 c01551) {
            this(arrayLiteralNode, expressionArr, type, iArr, obj, list);
        }

        /* loaded from: L-out.jar:jdk/nashorn/internal/ir/LiteralNode$ArrayLiteralNode$ArrayLiteralInitializer.class */
        private static final class ArrayLiteralInitializer {
            static final /* synthetic */ boolean $assertionsDisabled;

            private ArrayLiteralInitializer() {
            }

            static {
                $assertionsDisabled = !LiteralNode.class.desiredAssertionStatus();
            }

            static ArrayLiteralNode initialize(ArrayLiteralNode node) {
                Type elementType = computeElementType((Expression[]) node.value);
                int[] postsets = computePostsets((Expression[]) node.value);
                Object presets = computePresets((Expression[]) node.value, elementType, postsets);
                return new ArrayLiteralNode(node, (Expression[]) node.value, elementType, postsets, presets, node.splitRanges, null);
            }

            private static Type computeElementType(Expression[] value) {
                Type widestElementType = Type.INT;
                int length = value.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Expression elem = value[i];
                    if (elem == null) {
                        widestElementType = widestElementType.widest(Type.OBJECT);
                        break;
                    }
                    Type type = elem.getType().isUnknown() ? Type.OBJECT : elem.getType();
                    if (type.isBoolean()) {
                        widestElementType = widestElementType.widest(Type.OBJECT);
                        break;
                    }
                    widestElementType = widestElementType.widest(type);
                    if (widestElementType.isObject()) {
                        break;
                    }
                    i++;
                }
                return widestElementType;
            }

            private static int[] computePostsets(Expression[] value) {
                int[] computed = new int[value.length];
                int nComputed = 0;
                for (int i = 0; i < value.length; i++) {
                    Expression element = value[i];
                    if (element == null || !LiteralNode.isConstant(element)) {
                        int i2 = nComputed;
                        nComputed++;
                        computed[i2] = i;
                    }
                }
                return Arrays.copyOf(computed, nComputed);
            }

            private static boolean setArrayElement(int[] array, int i, Object n) {
                if (n instanceof Number) {
                    array[i] = ((Number) n).intValue();
                    return true;
                }
                return false;
            }

            private static boolean setArrayElement(long[] array, int i, Object n) {
                if (n instanceof Number) {
                    array[i] = ((Number) n).longValue();
                    return true;
                }
                return false;
            }

            private static boolean setArrayElement(double[] array, int i, Object n) {
                if (n instanceof Number) {
                    array[i] = ((Number) n).doubleValue();
                    return true;
                }
                return false;
            }

            private static int[] presetIntArray(Expression[] value, int[] postsets) {
                int[] array = new int[value.length];
                int nComputed = 0;
                for (int i = 0; i < value.length; i++) {
                    if (!setArrayElement(array, i, LiteralNode.objectAsConstant(value[i])) && !$assertionsDisabled) {
                        int i2 = nComputed;
                        nComputed++;
                        if (postsets[i2] != i) {
                            throw new AssertionError();
                        }
                    }
                }
                if ($assertionsDisabled || postsets.length == nComputed) {
                    return array;
                }
                throw new AssertionError();
            }

            private static long[] presetLongArray(Expression[] value, int[] postsets) {
                long[] array = new long[value.length];
                int nComputed = 0;
                for (int i = 0; i < value.length; i++) {
                    if (!setArrayElement(array, i, LiteralNode.objectAsConstant(value[i])) && !$assertionsDisabled) {
                        int i2 = nComputed;
                        nComputed++;
                        if (postsets[i2] != i) {
                            throw new AssertionError();
                        }
                    }
                }
                if ($assertionsDisabled || postsets.length == nComputed) {
                    return array;
                }
                throw new AssertionError();
            }

            private static double[] presetDoubleArray(Expression[] value, int[] postsets) {
                double[] array = new double[value.length];
                int nComputed = 0;
                for (int i = 0; i < value.length; i++) {
                    if (!setArrayElement(array, i, LiteralNode.objectAsConstant(value[i])) && !$assertionsDisabled) {
                        int i2 = nComputed;
                        nComputed++;
                        if (postsets[i2] != i) {
                            throw new AssertionError();
                        }
                    }
                }
                if ($assertionsDisabled || postsets.length == nComputed) {
                    return array;
                }
                throw new AssertionError();
            }

            private static Object[] presetObjectArray(Expression[] value, int[] postsets) {
                Object[] array = new Object[value.length];
                int nComputed = 0;
                for (int i = 0; i < value.length; i++) {
                    Expression expression = value[i];
                    if (expression == null) {
                        if ($assertionsDisabled) {
                            continue;
                        } else {
                            int i2 = nComputed;
                            nComputed++;
                            if (postsets[i2] != i) {
                                throw new AssertionError();
                            }
                        }
                    } else {
                        Object element = LiteralNode.objectAsConstant(expression);
                        if (element != LiteralNode.POSTSET_MARKER) {
                            array[i] = element;
                        } else if ($assertionsDisabled) {
                            continue;
                        } else {
                            int i3 = nComputed;
                            nComputed++;
                            if (postsets[i3] != i) {
                                throw new AssertionError();
                            }
                        }
                    }
                }
                if ($assertionsDisabled || postsets.length == nComputed) {
                    return array;
                }
                throw new AssertionError();
            }

            static Object computePresets(Expression[] value, Type elementType, int[] postsets) {
                if (!$assertionsDisabled && elementType.isUnknown()) {
                    throw new AssertionError();
                }
                if (elementType.isInteger()) {
                    return presetIntArray(value, postsets);
                }
                if (elementType.isNumeric()) {
                    return presetDoubleArray(value, postsets);
                }
                return presetObjectArray(value, postsets);
            }
        }

        protected ArrayLiteralNode(long j, int i, Expression[] expressionArr) {
            super(Token.recast(j, TokenType.ARRAY), i, expressionArr);
            this.elementType = Type.UNKNOWN;
            this.presets = null;
            this.postsets = null;
            this.splitRanges = null;
        }

        private ArrayLiteralNode(ArrayLiteralNode arrayLiteralNode, Expression[] expressionArr, Type type, int[] iArr, Object obj, List list) {
            super(arrayLiteralNode, expressionArr);
            this.elementType = type;
            this.postsets = iArr;
            this.presets = obj;
            this.splitRanges = list;
        }

        public List getElementExpressions() {
            return Collections.unmodifiableList(Arrays.asList((Object[]) this.value));
        }

        public ArrayLiteralNode initialize(LexicalContext lexicalContext) {
            return (ArrayLiteralNode) Node.replaceInLexicalContext(lexicalContext, this, ArrayLiteralInitializer.initialize(this));
        }

        public ArrayType getArrayType() {
            return getArrayType(getElementType());
        }

        /*  JADX ERROR: Types fix failed
            java.lang.NullPointerException
            */
        /* JADX WARN: Not initialized variable reg: 4, insn: 0x000c: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:477), block:B:6:0x000b */
        private static jdk.nashorn.internal.codegen.types.ArrayType getArrayType(jdk.nashorn.internal.codegen.types.Type r3) {
            /*
                r0 = r3
                boolean r0 = r0.isInteger()
                if (r0 == 0) goto Lb
                jdk.nashorn.internal.codegen.types.ArrayType r0 = jdk.nashorn.internal.codegen.types.Type.INT_ARRAY
                return r0
            Lb:
                r0 = r3
                r1 = r4
                boolean r1 = r1 instanceof jdk.nashorn.internal.codegen.types.NumericType
                if (r1 == 0) goto L17
                jdk.nashorn.internal.codegen.types.ArrayType r1 = jdk.nashorn.internal.codegen.types.Type.NUMBER_ARRAY
                return r1
            L17:
                jdk.nashorn.internal.codegen.types.ArrayType r1 = jdk.nashorn.internal.codegen.types.Type.OBJECT_ARRAY
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.ir.LiteralNode.ArrayLiteralNode.getArrayType(jdk.nashorn.internal.codegen.types.Type):jdk.nashorn.internal.codegen.types.ArrayType");
        }

        @Override // jdk.nashorn.internal.p001ir.LiteralNode, jdk.nashorn.internal.p001ir.Expression
        public Type getType() {
            return Type.typeFor((Class<?>) NativeArray.class);
        }

        public Type getElementType() {
            if ($assertionsDisabled || !this.elementType.isUnknown()) {
                return this.elementType;
            }
            throw new AssertionError(this + " has elementType=unknown");
        }

        public int[] getPostsets() {
            if ($assertionsDisabled || this.postsets != null) {
                return this.postsets;
            }
            throw new AssertionError(this + " elementType=" + this.elementType + " has no postsets");
        }

        private boolean presetsMatchElementType() {
            if (this.elementType == Type.INT) {
                return this.presets instanceof int[];
            }
            if (this.elementType == Type.NUMBER) {
                return this.presets instanceof double[];
            }
            return this.presets instanceof Object[];
        }

        public Object getPresets() {
            if ($assertionsDisabled || (this.presets != null && presetsMatchElementType())) {
                return this.presets;
            }
            throw new AssertionError(this + " doesn't have presets, or invalid preset type: " + this.presets);
        }

        @Override // jdk.nashorn.internal.p001ir.Splittable
        public List getSplitRanges() {
            if (this.splitRanges == null) {
                return null;
            }
            return Collections.unmodifiableList(this.splitRanges);
        }

        public ArrayLiteralNode setSplitRanges(LexicalContext lexicalContext, List list) {
            if (this.splitRanges == list) {
                return this;
            }
            return (ArrayLiteralNode) Node.replaceInLexicalContext(lexicalContext, this, new ArrayLiteralNode(this, (Expression[]) this.value, this.elementType, this.postsets, this.presets, list));
        }

        @Override // jdk.nashorn.internal.p001ir.LiteralNode, jdk.nashorn.internal.p001ir.Node
        public Node accept(NodeVisitor nodeVisitor) {
            return LexicalContextNode.Acceptor.accept(this, nodeVisitor);
        }

        @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
        public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
            if (nodeVisitor.enterLiteralNode(this)) {
                List listAsList = Arrays.asList((Object[]) this.value);
                List listAccept = Node.accept(nodeVisitor, listAsList);
                return nodeVisitor.leaveLiteralNode(listAsList != listAccept ? setValue(lexicalContext, listAccept) : this);
            }
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private ArrayLiteralNode setValue(LexicalContext lexicalContext, Expression[] expressionArr) {
            if (this.value == expressionArr) {
                return this;
            }
            return (ArrayLiteralNode) Node.replaceInLexicalContext(lexicalContext, this, new ArrayLiteralNode(this, expressionArr, this.elementType, this.postsets, this.presets, this.splitRanges));
        }

        private ArrayLiteralNode setValue(LexicalContext lexicalContext, List list) {
            return setValue(lexicalContext, (Expression[]) list.toArray(new Expression[list.size()]));
        }

        @Override // jdk.nashorn.internal.p001ir.LiteralNode, jdk.nashorn.internal.p001ir.Node
        public void toString(StringBuilder sb, boolean z) {
            sb.append('[');
            boolean z2 = true;
            for (Expression expression : (Expression[]) this.value) {
                if (!z2) {
                    sb.append(',');
                    sb.append(' ');
                }
                if (expression == null) {
                    sb.append("undefined");
                } else {
                    expression.toString(sb, z);
                }
                z2 = false;
            }
            sb.append(']');
        }
    }

    public static LiteralNode newInstance(long j, int i, List list) {
        return new ArrayLiteralNode(j, i, valueToArray(list));
    }

    public static LiteralNode newInstance(Node node, List list) {
        return new ArrayLiteralNode(node.getToken(), node.getFinish(), valueToArray(list));
    }

    public static LiteralNode newInstance(long j, int i, Expression[] expressionArr) {
        return new ArrayLiteralNode(j, i, expressionArr);
    }
}
