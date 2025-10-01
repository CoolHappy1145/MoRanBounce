package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.linker.NashornGuards;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/WithObject.class */
public final class WithObject extends Scope {
    private static final MethodHandle WITHEXPRESSIONGUARD = findOwnMH("withExpressionGuard", Boolean.TYPE, new Class[]{Object.class, PropertyMap.class, SwitchPoint[].class});
    private static final MethodHandle WITHEXPRESSIONFILTER = findOwnMH("withFilterExpression", Object.class, new Class[]{Object.class});
    private static final MethodHandle WITHSCOPEFILTER = findOwnMH("withFilterScope", Object.class, new Class[]{Object.class});
    private static final MethodHandle BIND_TO_EXPRESSION_OBJ = findOwnMH("bindToExpression", Object.class, new Class[]{Object.class, Object.class});
    private static final MethodHandle BIND_TO_EXPRESSION_FN = findOwnMH("bindToExpression", Object.class, new Class[]{ScriptFunction.class, Object.class});
    private final ScriptObject expression;

    WithObject(ScriptObject scriptObject, ScriptObject scriptObject2) {
        super(scriptObject, null);
        this.expression = scriptObject2;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(Object obj, boolean z) {
        ScriptObject scriptObject = this.expression;
        String string = JSType.toString(obj);
        if (scriptObject.findProperty(string, true) != null) {
            return scriptObject.delete(string, z);
        }
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation lookup(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        boolean z;
        String nameToken;
        String str;
        if (linkRequest.isCallSiteUnstable()) {
            return super.lookup(callSiteDescriptor, linkRequest);
        }
        NashornCallSiteDescriptor nashornCallSiteDescriptor = (NashornCallSiteDescriptor) callSiteDescriptor;
        FindProperty findProperty = null;
        GuardedInvocation guardedInvocationNoSuchProperty = null;
        if (callSiteDescriptor.getNameTokenCount() > 2) {
            z = true;
            nameToken = callSiteDescriptor.getNameToken(2);
        } else {
            z = false;
            nameToken = null;
        }
        ScriptObject scriptObject = this.expression;
        if (z) {
            findProperty = scriptObject.findProperty(nameToken, true);
        }
        if (findProperty != null) {
            guardedInvocationNoSuchProperty = scriptObject.lookup(callSiteDescriptor, linkRequest);
            if (guardedInvocationNoSuchProperty != null) {
                return fixExpressionCallSite(nashornCallSiteDescriptor, guardedInvocationNoSuchProperty);
            }
        }
        ScriptObject proto = getProto();
        if (z) {
            findProperty = proto.findProperty(nameToken, true);
        }
        if (findProperty != null) {
            return fixScopeCallSite(proto.lookup(callSiteDescriptor, linkRequest), nameToken, findProperty.getOwner());
        }
        if (scriptObject != null) {
            String str2 = (String) CallSiteDescriptorFactory.tokenizeOperators(callSiteDescriptor).get(0);
            switch (str2) {
                case "callMethod":
                    throw new AssertionError();
                case "getMethod":
                    str = ScriptObject.NO_SUCH_METHOD_NAME;
                    break;
                case "getProp":
                case "getElem":
                    str = ScriptObject.NO_SUCH_PROPERTY_NAME;
                    break;
                default:
                    str = null;
                    break;
            }
            if (str != null && scriptObject.findProperty(str, true) != null) {
                switch (str2) {
                    case "getMethod":
                        guardedInvocationNoSuchProperty = scriptObject.noSuchMethod(callSiteDescriptor, linkRequest);
                        break;
                    case "getProp":
                    case "getElem":
                        guardedInvocationNoSuchProperty = scriptObject.noSuchProperty(callSiteDescriptor, linkRequest);
                        break;
                }
            }
            if (guardedInvocationNoSuchProperty != null) {
                return fixExpressionCallSite(nashornCallSiteDescriptor, guardedInvocationNoSuchProperty);
            }
        }
        GuardedInvocation guardedInvocationLookup = proto.lookup(callSiteDescriptor, linkRequest);
        if (guardedInvocationLookup != null) {
            return fixScopeCallSite(guardedInvocationLookup, nameToken, null);
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected FindProperty findProperty(String str, boolean z, ScriptObject scriptObject) {
        FindProperty findProperty = this.expression.findProperty(str, true, this.expression);
        if (findProperty != null) {
            return findProperty;
        }
        return super.findProperty(str, z, scriptObject);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected Object invokeNoSuchProperty(String str, boolean z, int i) {
        FindProperty findProperty = this.expression.findProperty(ScriptObject.NO_SUCH_PROPERTY_NAME, true);
        if (findProperty != null) {
            Object objectValue = findProperty.getObjectValue();
            if (objectValue instanceof ScriptFunction) {
                ScriptFunction scriptFunction = (ScriptFunction) objectValue;
                return ScriptRuntime.apply(scriptFunction, (z && scriptFunction.isStrict()) ? ScriptRuntime.UNDEFINED : this.expression, str);
            }
        }
        return getProto().invokeNoSuchProperty(str, z, i);
    }

    @Override // jdk.nashorn.internal.runtime.Scope
    public void setSplitState(int i) {
        ((Scope) getNonWithParent()).setSplitState(i);
    }

    @Override // jdk.nashorn.internal.runtime.Scope
    public int getSplitState() {
        return ((Scope) getNonWithParent()).getSplitState();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public void addBoundProperties(ScriptObject scriptObject, Property[] propertyArr) {
        getNonWithParent().addBoundProperties(scriptObject, propertyArr);
    }

    private ScriptObject getNonWithParent() {
        ScriptObject scriptObject;
        ScriptObject proto = getProto();
        while (true) {
            scriptObject = proto;
            if (scriptObject == null || !(scriptObject instanceof WithObject)) {
                break;
            }
            proto = scriptObject.getProto();
        }
        return scriptObject;
    }

    private static GuardedInvocation fixReceiverType(GuardedInvocation guardedInvocation, MethodHandle methodHandle) {
        return guardedInvocation.asType(guardedInvocation.getInvocation().type().changeParameterType(0, methodHandle.type().returnType()));
    }

    private static GuardedInvocation fixExpressionCallSite(NashornCallSiteDescriptor nashornCallSiteDescriptor, GuardedInvocation guardedInvocation) {
        if (!"getMethod".equals(nashornCallSiteDescriptor.getFirstOperator())) {
            return fixReceiverType(guardedInvocation, WITHEXPRESSIONFILTER).filterArguments(0, new MethodHandle[]{WITHEXPRESSIONFILTER});
        }
        MethodHandle invocation = guardedInvocation.getInvocation();
        MethodType methodTypeType = invocation.type();
        boolean zIsAssignableFrom = ScriptFunction.class.isAssignableFrom(methodTypeType.returnType());
        return guardedInvocation.replaceMethods(Lookup.f31MH.foldArguments(zIsAssignableFrom ? BIND_TO_EXPRESSION_FN : BIND_TO_EXPRESSION_OBJ, filterReceiver(invocation.asType(methodTypeType.changeReturnType(zIsAssignableFrom ? ScriptFunction.class : Object.class).changeParameterType(0, Object.class)), WITHEXPRESSIONFILTER)), filterGuardReceiver(guardedInvocation, WITHEXPRESSIONFILTER));
    }

    private GuardedInvocation fixScopeCallSite(GuardedInvocation guardedInvocation, String str, ScriptObject scriptObject) {
        GuardedInvocation guardedInvocationFixReceiverType = fixReceiverType(guardedInvocation, WITHSCOPEFILTER);
        return guardedInvocation.replaceMethods(filterReceiver(guardedInvocationFixReceiverType.getInvocation(), WITHSCOPEFILTER), NashornGuards.combineGuards(expressionGuard(str, scriptObject), filterGuardReceiver(guardedInvocationFixReceiverType, WITHSCOPEFILTER)));
    }

    private static MethodHandle filterGuardReceiver(GuardedInvocation guardedInvocation, MethodHandle methodHandle) {
        MethodHandle guard = guardedInvocation.getGuard();
        if (guard == null) {
            return null;
        }
        Class<?> clsParameterType = guard.type().parameterType(0);
        return filterReceiver(guard, Lookup.f31MH.asType(methodHandle, methodHandle.type().changeParameterType(0, clsParameterType).changeReturnType(clsParameterType)));
    }

    private static MethodHandle filterReceiver(MethodHandle methodHandle, MethodHandle methodHandle2) {
        return Lookup.f31MH.filterArguments(methodHandle, 0, new MethodHandle[]{methodHandle2.asType(methodHandle2.type().changeReturnType(methodHandle.type().parameterType(0)))});
    }

    public static Object withFilterExpression(Object obj) {
        return ((WithObject) obj).expression;
    }

    private static Object bindToExpression(Object obj, Object obj2) {
        if (obj instanceof ScriptFunction) {
            return bindToExpression((ScriptFunction) obj, obj2);
        }
        if (obj instanceof ScriptObjectMirror) {
            ScriptObjectMirror scriptObjectMirror = (ScriptObjectMirror) obj;
            if (scriptObjectMirror.isFunction()) {
                return new AbstractJSObject(scriptObjectMirror, obj2) { // from class: jdk.nashorn.internal.runtime.WithObject.1
                    final ScriptObjectMirror val$mirror;
                    final Object val$receiver;

                    {
                        this.val$mirror = scriptObjectMirror;
                        this.val$receiver = obj2;
                    }

                    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
                    public Object call(Object obj3, Object[] objArr) {
                        return this.val$mirror.call(WithObject.withFilterExpression(this.val$receiver), objArr);
                    }
                };
            }
        }
        return obj;
    }

    private static Object bindToExpression(ScriptFunction scriptFunction, Object obj) {
        return scriptFunction.createBound(withFilterExpression(obj), ScriptRuntime.EMPTY_ARRAY);
    }

    private MethodHandle expressionGuard(String str, ScriptObject scriptObject) {
        return Lookup.f31MH.insertArguments(WITHEXPRESSIONGUARD, 1, new Object[]{this.expression.getMap(), this.expression.getProtoSwitchPoints(str, scriptObject)});
    }

    private static boolean withExpressionGuard(Object obj, PropertyMap propertyMap, SwitchPoint[] switchPointArr) {
        return ((WithObject) obj).expression.getMap() == propertyMap && !hasBeenInvalidated(switchPointArr);
    }

    private static boolean hasBeenInvalidated(SwitchPoint[] switchPointArr) {
        if (switchPointArr != null) {
            for (SwitchPoint switchPoint : switchPointArr) {
                if (switchPoint.hasBeenInvalidated()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static Object withFilterScope(Object obj) {
        return ((WithObject) obj).getProto();
    }

    public ScriptObject getExpression() {
        return this.expression;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), WithObject.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}
