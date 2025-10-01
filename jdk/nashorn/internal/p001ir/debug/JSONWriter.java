package jdk.nashorn.internal.p001ir.debug;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockStatement;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.ContinueNode;
import jdk.nashorn.internal.p001ir.EmptyNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.JoinPredecessorExpression;
import jdk.nashorn.internal.p001ir.LabelNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ObjectNode;
import jdk.nashorn.internal.p001ir.PropertyNode;
import jdk.nashorn.internal.p001ir.ReturnNode;
import jdk.nashorn.internal.p001ir.RuntimeNode;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.TernaryNode;
import jdk.nashorn.internal.p001ir.ThrowNode;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WhileNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import org.apache.log4j.spi.Configurator;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/JSONWriter.class */
public final class JSONWriter extends SimpleNodeVisitor {
    private final StringBuilder buf = new StringBuilder();
    private final boolean includeLocation;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JSONWriter.class.desiredAssertionStatus();
    }

    public static String parse(Context context, String str, String str2, boolean z) {
        Parser parser = new Parser(context.getEnv(), Source.sourceFor(str2, str), new Context.ThrowErrorManager(), context.getEnv()._strict, context.getLogger(Parser.class));
        JSONWriter jSONWriter = new JSONWriter(z);
        try {
            parser.parse().accept(jSONWriter);
            return jSONWriter.getString();
        } catch (ParserException e) {
            e.throwAsEcmaException();
            return null;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterJoinPredecessorExpression(JoinPredecessorExpression joinPredecessorExpression) {
        Expression expression = joinPredecessorExpression.getExpression();
        if (expression != null) {
            expression.accept(this);
            return false;
        }
        nullValue();
        return false;
    }

    protected boolean enterDefault(Node node) {
        objectStart();
        location(node);
        return true;
    }

    private boolean leave() {
        objectEnd();
        return false;
    }

    protected Node leaveDefault(Node node) {
        objectEnd();
        return null;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterAccessNode(AccessNode accessNode) {
        enterDefault(accessNode);
        type("MemberExpression");
        comma();
        property("object");
        accessNode.getBase().accept(this);
        comma();
        property("property", accessNode.getProperty());
        comma();
        property("computed", false);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlock(Block block) {
        enterDefault(block);
        type("BlockStatement");
        comma();
        array("body", block.getStatements());
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBinaryNode(BinaryNode binaryNode) {
        String str;
        enterDefault(binaryNode);
        if (binaryNode.isAssignment()) {
            str = "AssignmentExpression";
        } else if (binaryNode.isLogical()) {
            str = "LogicalExpression";
        } else {
            str = "BinaryExpression";
        }
        type(str);
        comma();
        property("operator", binaryNode.tokenType().getName());
        comma();
        property("left");
        binaryNode.lhs().accept(this);
        comma();
        property("right");
        binaryNode.rhs().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBreakNode(BreakNode breakNode) {
        enterDefault(breakNode);
        type("BreakStatement");
        comma();
        String labelName = breakNode.getLabelName();
        if (labelName != null) {
            property("label", labelName);
        } else {
            property("label");
            nullValue();
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCallNode(CallNode callNode) {
        enterDefault(callNode);
        type("CallExpression");
        comma();
        property("callee");
        callNode.getFunction().accept(this);
        comma();
        array("arguments", callNode.getArgs());
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCaseNode(CaseNode caseNode) {
        enterDefault(caseNode);
        type("SwitchCase");
        comma();
        Expression test = caseNode.getTest();
        property("test");
        if (test != null) {
            test.accept(this);
        } else {
            nullValue();
        }
        comma();
        array("consequent", caseNode.getBody().getStatements());
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCatchNode(CatchNode catchNode) {
        enterDefault(catchNode);
        type("CatchClause");
        comma();
        property("param");
        catchNode.getException().accept(this);
        comma();
        Expression exceptionCondition = catchNode.getExceptionCondition();
        if (exceptionCondition != null) {
            property("guard");
            exceptionCondition.accept(this);
            comma();
        }
        property("body");
        catchNode.getBody().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterContinueNode(ContinueNode continueNode) {
        enterDefault(continueNode);
        type("ContinueStatement");
        comma();
        String labelName = continueNode.getLabelName();
        if (labelName != null) {
            property("label", labelName);
        } else {
            property("label");
            nullValue();
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterEmptyNode(EmptyNode emptyNode) {
        enterDefault(emptyNode);
        type("EmptyStatement");
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
        Expression expression = expressionStatement.getExpression();
        if (expression instanceof RuntimeNode) {
            expression.accept(this);
            return false;
        }
        enterDefault(expressionStatement);
        type("ExpressionStatement");
        comma();
        property("expression");
        expression.accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlockStatement(BlockStatement blockStatement) {
        enterDefault(blockStatement);
        type("BlockStatement");
        comma();
        property("block");
        blockStatement.getBlock().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterForNode(ForNode forNode) {
        enterDefault(forNode);
        if (forNode.isForIn() || (forNode.isForEach() && forNode.getInit() != null)) {
            type("ForInStatement");
            comma();
            Expression init = forNode.getInit();
            if (!$assertionsDisabled && init == null) {
                throw new AssertionError();
            }
            property("left");
            init.accept(this);
            comma();
            JoinPredecessorExpression modify = forNode.getModify();
            if (!$assertionsDisabled && modify == null) {
                throw new AssertionError();
            }
            property("right");
            modify.accept(this);
            comma();
            property("body");
            forNode.getBody().accept(this);
            comma();
            property("each", forNode.isForEach());
        } else {
            type("ForStatement");
            comma();
            Expression init2 = forNode.getInit();
            property("init");
            if (init2 != null) {
                init2.accept(this);
            } else {
                nullValue();
            }
            comma();
            JoinPredecessorExpression test = forNode.getTest();
            property("test");
            if (test != null) {
                test.accept(this);
            } else {
                nullValue();
            }
            comma();
            JoinPredecessorExpression modify2 = forNode.getModify();
            property("update");
            if (modify2 != null) {
                modify2.accept(this);
            } else {
                nullValue();
            }
            comma();
            property("body");
            forNode.getBody().accept(this);
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        String str;
        if (functionNode.isProgram()) {
            return emitProgram(functionNode);
        }
        enterDefault(functionNode);
        if (functionNode.isDeclared()) {
            str = "FunctionDeclaration";
        } else {
            str = "FunctionExpression";
        }
        type(str);
        comma();
        property("id");
        FunctionNode.Kind kind = functionNode.getKind();
        if (functionNode.isAnonymous() || kind == FunctionNode.Kind.GETTER || kind == FunctionNode.Kind.SETTER) {
            nullValue();
        } else {
            functionNode.getIdent().accept(this);
        }
        comma();
        array("params", functionNode.getParameters());
        comma();
        arrayStart("defaults");
        arrayEnd();
        comma();
        property("rest");
        nullValue();
        comma();
        property("body");
        functionNode.getBody().accept(this);
        comma();
        property("generator", false);
        comma();
        property("expression", false);
        return leave();
    }

    private boolean emitProgram(FunctionNode functionNode) {
        enterDefault(functionNode);
        type("Program");
        comma();
        List statements = functionNode.getBody().getStatements();
        int size = statements.size();
        int i = 0;
        arrayStart("body");
        Iterator it = statements.iterator();
        while (it.hasNext()) {
            ((Node) it.next()).accept(this);
            if (i != size - 1) {
                comma();
            }
            i++;
        }
        arrayEnd();
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIdentNode(IdentNode identNode) {
        enterDefault(identNode);
        if ("this".equals(identNode.getName())) {
            type("ThisExpression");
        } else {
            type("Identifier");
            comma();
            property("name", identNode.getName());
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIfNode(IfNode ifNode) {
        enterDefault(ifNode);
        type("IfStatement");
        comma();
        property("test");
        ifNode.getTest().accept(this);
        comma();
        property("consequent");
        ifNode.getPass().accept(this);
        Block fail = ifNode.getFail();
        comma();
        property("alternate");
        if (fail != null) {
            fail.accept(this);
        } else {
            nullValue();
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIndexNode(IndexNode indexNode) {
        enterDefault(indexNode);
        type("MemberExpression");
        comma();
        property("object");
        indexNode.getBase().accept(this);
        comma();
        property("property");
        indexNode.getIndex().accept(this);
        comma();
        property("computed", true);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterLabelNode(LabelNode labelNode) {
        enterDefault(labelNode);
        type("LabeledStatement");
        comma();
        property("label", labelNode.getLabelName());
        comma();
        property("body");
        labelNode.getBody().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterLiteralNode(LiteralNode literalNode) {
        enterDefault(literalNode);
        if (literalNode instanceof LiteralNode.ArrayLiteralNode) {
            type("ArrayExpression");
            comma();
            array("elements", ((LiteralNode.ArrayLiteralNode) literalNode).getElementExpressions());
        } else {
            type("Literal");
            comma();
            property(PropertyDescriptor.VALUE);
            Object value = literalNode.getValue();
            if (value instanceof Lexer.RegexToken) {
                Lexer.RegexToken regexToken = (Lexer.RegexToken) value;
                this.buf.append(quote('/' + regexToken.getExpression() + '/' + regexToken.getOptions()));
            } else {
                String string = literalNode.getString();
                this.buf.append(literalNode.isString() ? quote(ArgsClassGenerator.GETTER_PREFIX + string) : string);
            }
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterObjectNode(ObjectNode objectNode) {
        enterDefault(objectNode);
        type("ObjectExpression");
        comma();
        array("properties", objectNode.getElements());
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterPropertyNode(PropertyNode propertyNode) {
        Expression key = propertyNode.getKey();
        Expression value = propertyNode.getValue();
        if (value != null) {
            objectStart();
            location(propertyNode);
            property("key");
            key.accept(this);
            comma();
            property(PropertyDescriptor.VALUE);
            value.accept(this);
            comma();
            property("kind", "init");
            objectEnd();
            return false;
        }
        FunctionNode getter = propertyNode.getGetter();
        if (getter != null) {
            objectStart();
            location(propertyNode);
            property("key");
            key.accept(this);
            comma();
            property(PropertyDescriptor.VALUE);
            getter.accept(this);
            comma();
            property("kind", PropertyDescriptor.GET);
            objectEnd();
        }
        FunctionNode setter = propertyNode.getSetter();
        if (setter != null) {
            if (getter != null) {
                comma();
            }
            objectStart();
            location(propertyNode);
            property("key");
            key.accept(this);
            comma();
            property(PropertyDescriptor.VALUE);
            setter.accept(this);
            comma();
            property("kind", PropertyDescriptor.SET);
            objectEnd();
            return false;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterReturnNode(ReturnNode returnNode) {
        enterDefault(returnNode);
        type("ReturnStatement");
        comma();
        Expression expression = returnNode.getExpression();
        property("argument");
        if (expression != null) {
            expression.accept(this);
        } else {
            nullValue();
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterRuntimeNode(RuntimeNode runtimeNode) {
        if (runtimeNode.getRequest() == RuntimeNode.Request.DEBUGGER) {
            enterDefault(runtimeNode);
            type("DebuggerStatement");
            return leave();
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSwitchNode(SwitchNode switchNode) {
        enterDefault(switchNode);
        type("SwitchStatement");
        comma();
        property("discriminant");
        switchNode.getExpression().accept(this);
        comma();
        array("cases", switchNode.getCases());
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterTernaryNode(TernaryNode ternaryNode) {
        enterDefault(ternaryNode);
        type("ConditionalExpression");
        comma();
        property("test");
        ternaryNode.getTest().accept(this);
        comma();
        property("consequent");
        ternaryNode.getTrueExpression().accept(this);
        comma();
        property("alternate");
        ternaryNode.getFalseExpression().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterThrowNode(ThrowNode throwNode) {
        enterDefault(throwNode);
        type("ThrowStatement");
        comma();
        property("argument");
        throwNode.getExpression().accept(this);
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterTryNode(TryNode tryNode) {
        enterDefault(tryNode);
        type("TryStatement");
        comma();
        property("block");
        tryNode.getBody().accept(this);
        comma();
        List catches = tryNode.getCatches();
        ArrayList arrayList = new ArrayList();
        CatchNode catchNode = null;
        if (catches != null) {
            Iterator it = catches.iterator();
            while (it.hasNext()) {
                CatchNode catchNode2 = (CatchNode) ((Node) it.next());
                if (catchNode2.getExceptionCondition() != null) {
                    arrayList.add(catchNode2);
                } else {
                    if (!$assertionsDisabled && catchNode != null) {
                        throw new AssertionError("too many unguarded?");
                    }
                    catchNode = catchNode2;
                }
            }
        }
        array("guardedHandlers", arrayList);
        comma();
        property(InjectionInfo.DEFAULT_PREFIX);
        if (catchNode != null) {
            catchNode.accept(this);
        } else {
            nullValue();
        }
        comma();
        property("finalizer");
        Block finallyBody = tryNode.getFinallyBody();
        if (finallyBody != null) {
            finallyBody.accept(this);
        } else {
            nullValue();
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterUnaryNode(UnaryNode unaryNode) {
        boolean z;
        String name;
        enterDefault(unaryNode);
        TokenType tokenType = unaryNode.tokenType();
        if (tokenType == TokenType.NEW) {
            type("NewExpression");
            comma();
            CallNode callNode = (CallNode) unaryNode.getExpression();
            property("callee");
            callNode.getFunction().accept(this);
            comma();
            array("arguments", callNode.getArgs());
        } else {
            switch (C01621.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tokenType.ordinal()]) {
                case 1:
                    z = false;
                    name = "++";
                    break;
                case 2:
                    z = false;
                    name = "--";
                    break;
                case 3:
                    name = "++";
                    z = true;
                    break;
                case 4:
                    name = "--";
                    z = true;
                    break;
                default:
                    z = true;
                    name = tokenType.getName();
                    break;
            }
            type(unaryNode.isAssignment() ? "UpdateExpression" : "UnaryExpression");
            comma();
            property("operator", name);
            comma();
            property("prefix", z);
            comma();
            property("argument");
            unaryNode.getExpression().accept(this);
        }
        return leave();
    }

    /* renamed from: jdk.nashorn.internal.ir.debug.JSONWriter$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/JSONWriter$1.class */
    static /* synthetic */ class C01621 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.INCPOSTFIX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DECPOSTFIX.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.INCPREFIX.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DECPREFIX.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        Expression init = varNode.getInit();
        if ((init instanceof FunctionNode) && ((FunctionNode) init).isDeclared()) {
            init.accept(this);
            return false;
        }
        enterDefault(varNode);
        type("VariableDeclaration");
        comma();
        arrayStart("declarations");
        objectStart();
        location(varNode.getName());
        type("VariableDeclarator");
        comma();
        property("id");
        varNode.getName().accept(this);
        comma();
        property("init");
        if (init != null) {
            init.accept(this);
        } else {
            nullValue();
        }
        objectEnd();
        arrayEnd();
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWhileNode(WhileNode whileNode) {
        enterDefault(whileNode);
        type(whileNode.isDoWhile() ? "DoWhileStatement" : "WhileStatement");
        comma();
        if (whileNode.isDoWhile()) {
            property("body");
            whileNode.getBody().accept(this);
            comma();
            property("test");
            whileNode.getTest().accept(this);
        } else {
            property("test");
            whileNode.getTest().accept(this);
            comma();
            property("body");
            whileNode.getBody().accept(this);
        }
        return leave();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWithNode(WithNode withNode) {
        enterDefault(withNode);
        type("WithStatement");
        comma();
        property("object");
        withNode.getExpression().accept(this);
        comma();
        property("body");
        withNode.getBody().accept(this);
        return leave();
    }

    private JSONWriter(boolean z) {
        this.includeLocation = z;
    }

    private String getString() {
        return this.buf.toString();
    }

    private void property(String str, String str2, boolean z) {
        this.buf.append('\"');
        this.buf.append(str);
        this.buf.append("\":");
        if (str2 != null) {
            if (z) {
                this.buf.append('\"');
            }
            this.buf.append(str2);
            if (z) {
                this.buf.append('\"');
            }
        }
    }

    private void property(String str, String str2) {
        property(str, str2, true);
    }

    private void property(String str, boolean z) {
        property(str, Boolean.toString(z), false);
    }

    private void property(String str, int i) {
        property(str, Integer.toString(i), false);
    }

    private void property(String str) {
        property(str, (String) null);
    }

    private void type(String str) {
        property("type", str);
    }

    private void objectStart(String str) {
        this.buf.append('\"');
        this.buf.append(str);
        this.buf.append("\":{");
    }

    private void objectStart() {
        this.buf.append('{');
    }

    private void objectEnd() {
        this.buf.append('}');
    }

    private void array(String str, List list) {
        int size = list.size();
        int i = 0;
        arrayStart(str);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Node node = (Node) it.next();
            if (node != null) {
                node.accept(this);
            } else {
                nullValue();
            }
            if (i != size - 1) {
                comma();
            }
            i++;
        }
        arrayEnd();
    }

    private void arrayStart(String str) {
        this.buf.append('\"');
        this.buf.append(str);
        this.buf.append('\"');
        this.buf.append(':');
        this.buf.append('[');
    }

    private void arrayEnd() {
        this.buf.append(']');
    }

    private void comma() {
        this.buf.append(',');
    }

    private void nullValue() {
        this.buf.append(Configurator.NULL);
    }

    private void location(Node node) {
        if (this.includeLocation) {
            objectStart("loc");
            Source source = this.f30lc.getCurrentFunction().getSource();
            property("source", source.getName());
            comma();
            objectStart("start");
            int start = node.getStart();
            property("line", source.getLine(start));
            comma();
            property("column", source.getColumn(start));
            objectEnd();
            comma();
            objectStart(AsmConstants.END);
            int finish = node.getFinish();
            property("line", source.getLine(finish));
            comma();
            property("column", source.getColumn(finish));
            objectEnd();
            objectEnd();
            comma();
        }
    }

    private static String quote(String str) {
        return JSONParser.quote(str);
    }
}
