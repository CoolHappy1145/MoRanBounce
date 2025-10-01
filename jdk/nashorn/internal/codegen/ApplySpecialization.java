package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodType;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

@Logger(name = "apply2call")
/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ApplySpecialization.class */
public final class ApplySpecialization extends SimpleNodeVisitor implements Loggable {
    private static final boolean USE_APPLY2CALL;
    private final DebugLogger log;
    private final Compiler compiler;
    private final Set changed = new HashSet();
    private final Deque explodedArguments = new ArrayDeque();
    private final Deque callSiteTypes = new ArrayDeque();
    private static final String ARGUMENTS;
    private static final AppliesFoundException HAS_APPLIES;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ApplySpecialization.class.desiredAssertionStatus();
        USE_APPLY2CALL = Options.getBooleanProperty("nashorn.apply2call", true);
        ARGUMENTS = CompilerConstants.ARGUMENTS_VAR.symbolName();
        HAS_APPLIES = new AppliesFoundException();
    }

    public ApplySpecialization(Compiler compiler) {
        this.compiler = compiler;
        this.log = initLogger(compiler.getContext());
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return context.getLogger(getClass());
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ApplySpecialization$TransformFailedException.class */
    private static class TransformFailedException extends RuntimeException {
        TransformFailedException(FunctionNode functionNode, String str) {
            super(ApplySpecialization.massageURL(functionNode.getSource().getURL()) + '.' + functionNode.getName() + " => " + str, null, false, false);
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ApplySpecialization$AppliesFoundException.class */
    private static class AppliesFoundException extends RuntimeException {
        AppliesFoundException() {
            super("applies_found", null, false, false);
        }
    }

    private boolean hasApplies(FunctionNode functionNode) {
        try {
            functionNode.accept(new SimpleNodeVisitor(this, functionNode) { // from class: jdk.nashorn.internal.codegen.ApplySpecialization.1
                final FunctionNode val$functionNode;
                final ApplySpecialization this$0;

                {
                    this.this$0 = this;
                    this.val$functionNode = functionNode;
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterFunctionNode(FunctionNode functionNode2) {
                    return functionNode2 == this.val$functionNode;
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterCallNode(CallNode callNode) {
                    if (ApplySpecialization.isApply(callNode)) {
                        throw ApplySpecialization.HAS_APPLIES;
                    }
                    return true;
                }
            });
            this.log.fine(new Object[]{"There are no applies in ", DebugLogger.quote(functionNode.getName()), " - nothing to do."});
            return false;
        } catch (AppliesFoundException unused) {
            return true;
        }
    }

    private static void checkValidTransform(FunctionNode functionNode) {
        functionNode.accept(new SimpleNodeVisitor(new ArrayDeque(), new HashSet(), functionNode) { // from class: jdk.nashorn.internal.codegen.ApplySpecialization.2
            final Deque val$stack;
            final Set val$argumentsFound;
            final FunctionNode val$functionNode;

            {
                this.val$stack = deque;
                this.val$argumentsFound = set;
                this.val$functionNode = functionNode;
            }

            private boolean isCurrentArg(Expression expression) {
                return !this.val$stack.isEmpty() && ((Set) this.val$stack.peek()).contains(expression);
            }

            private boolean isArguments(Expression expression) {
                if ((expression instanceof IdentNode) && ApplySpecialization.ARGUMENTS.equals(((IdentNode) expression).getName())) {
                    this.val$argumentsFound.add(expression);
                    return true;
                }
                return false;
            }

            private boolean isParam(String str) {
                Iterator it = this.val$functionNode.getParameters().iterator();
                while (it.hasNext()) {
                    if (((IdentNode) it.next()).getName().equals(str)) {
                        return true;
                    }
                }
                return false;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveIdentNode(IdentNode identNode) {
                if (isParam(identNode.getName())) {
                    throw new TransformFailedException(this.f30lc.getCurrentFunction(), "parameter: " + identNode.getName());
                }
                if (isArguments(identNode) && !isCurrentArg(identNode)) {
                    throw new TransformFailedException(this.f30lc.getCurrentFunction(), "is 'arguments': " + identNode.getName());
                }
                return identNode;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterCallNode(CallNode callNode) {
                HashSet hashSet = new HashSet();
                if (ApplySpecialization.isApply(callNode)) {
                    List args = callNode.getArgs();
                    if (args.size() != 2 || !isArguments((Expression) args.get(args.size() - 1))) {
                        throw new TransformFailedException(this.f30lc.getCurrentFunction(), "argument pattern not matched: " + args);
                    }
                    hashSet.addAll(callNode.getArgs());
                }
                this.val$stack.push(hashSet);
                return true;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveCallNode(CallNode callNode) {
                this.val$stack.pop();
                return callNode;
            }
        });
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCallNode(CallNode callNode) {
        return !this.explodedArguments.isEmpty();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCallNode(CallNode callNode) {
        List list = (List) this.explodedArguments.peek();
        if (isApply(callNode)) {
            ArrayList arrayList = new ArrayList();
            for (Expression expression : callNode.getArgs()) {
                if ((expression instanceof IdentNode) && ARGUMENTS.equals(((IdentNode) expression).getName())) {
                    arrayList.addAll(list);
                } else {
                    arrayList.add(expression);
                }
            }
            this.changed.add(Integer.valueOf(this.f30lc.getCurrentFunction().getId()));
            CallNode isApplyToCall = callNode.setArgs(arrayList).setIsApplyToCall();
            if (this.log.isEnabled()) {
                this.log.fine(new Object[]{"Transformed ", callNode, " from apply to call => ", isApplyToCall, " in ", DebugLogger.quote(this.f30lc.getCurrentFunction().getName())});
            }
            return isApplyToCall;
        }
        return callNode;
    }

    private void pushExplodedArgs(FunctionNode functionNode) {
        int i = 0;
        MethodType callSiteType = this.compiler.getCallSiteType(functionNode);
        if (callSiteType == null) {
            throw new TransformFailedException(this.f30lc.getCurrentFunction(), "No callsite type");
        }
        if (!$assertionsDisabled && callSiteType.parameterType(callSiteType.parameterCount() - 1) == Object[].class) {
            throw new AssertionError("error vararg callsite passed to apply2call " + functionNode.getName() + " " + callSiteType);
        }
        if (this.compiler.getTypeMap().needsCallee()) {
            i = 0 + 1;
        }
        int i2 = i + 1;
        if (!$assertionsDisabled && functionNode.getNumOfParams() != 0) {
            throw new AssertionError("apply2call on function with named paramaters!");
        }
        ArrayList arrayList = new ArrayList();
        long jParameterCount = callSiteType.parameterCount() - i2;
        for (int i3 = 0; i3 < jParameterCount; i3++) {
            arrayList.add(new IdentNode(functionNode.getToken(), functionNode.getFinish(), CompilerConstants.EXPLODED_ARGUMENT_PREFIX.symbolName() + i3));
        }
        this.callSiteTypes.push(callSiteType);
        this.explodedArguments.push(arrayList);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        if (!USE_APPLY2CALL || !this.compiler.isOnDemandCompilation() || !functionNode.needsArguments() || functionNode.hasEval() || functionNode.getNumOfParams() != 0) {
            return false;
        }
        if (!Global.isBuiltinFunctionPrototypeApply()) {
            this.log.fine("Apply transform disabled: apply/call overridden");
            if ($assertionsDisabled || !Global.isBuiltinFunctionPrototypeCall()) {
                return false;
            }
            throw new AssertionError("call and apply should have the same SwitchPoint");
        }
        if (!hasApplies(functionNode)) {
            return false;
        }
        if (this.log.isEnabled()) {
            this.log.info(new Object[]{"Trying to specialize apply to call in '", functionNode.getName(), "' params=", functionNode.getParameters(), " id=", Integer.valueOf(functionNode.getId()), " source=", massageURL(functionNode.getSource().getURL())});
        }
        try {
            checkValidTransform(functionNode);
            pushExplodedArgs(functionNode);
            return true;
        } catch (TransformFailedException e) {
            this.log.info(new Object[]{"Failure: ", e.getMessage()});
            return false;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        FunctionNode parameters = functionNode;
        String name = parameters.getName();
        if (this.changed.contains(Integer.valueOf(parameters.getId()))) {
            parameters = parameters.clearFlag(this.f30lc, 8).setFlag(this.f30lc, 4096).setParameters(this.f30lc, (List) this.explodedArguments.peek());
            if (this.log.isEnabled()) {
                this.log.info(new Object[]{"Success: ", massageURL(parameters.getSource().getURL()), '.', name, "' id=", Integer.valueOf(parameters.getId()), " params=", this.callSiteTypes.peek()});
            }
        }
        this.callSiteTypes.pop();
        this.explodedArguments.pop();
        return parameters;
    }

    private static boolean isApply(CallNode callNode) {
        Expression function = callNode.getFunction();
        return (function instanceof AccessNode) && "apply".equals(((AccessNode) function).getProperty());
    }

    private static String massageURL(URL url) {
        if (url == null) {
            return "<null>";
        }
        String string = url.toString();
        int iLastIndexOf = string.lastIndexOf(47);
        if (iLastIndexOf == -1) {
            return string;
        }
        return string.substring(iLastIndexOf + 1);
    }
}
