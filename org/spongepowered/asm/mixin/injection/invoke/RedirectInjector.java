package org.spongepowered.asm.mixin.injection.invoke;

import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Ints;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.text.Typography;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
import org.spongepowered.asm.mixin.injection.points.BeforeNew;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/RedirectInjector.class */
public class RedirectInjector extends InvokeInjector {
    private static final String GET_CLASS_METHOD = "getClass";
    private static final String IS_ASSIGNABLE_FROM_METHOD = "isAssignableFrom";
    private static final String NPE = "java/lang/NullPointerException";
    private static final String KEY_NOMINATORS = "nominators";
    private static final String KEY_FUZZ = "fuzz";
    private static final String KEY_OPCODE = "opcode";
    protected Meta meta;
    private Map ctorRedirectors;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/RedirectInjector$Meta.class */
    class Meta {
        public static final String KEY = "redirector";
        final int priority;
        final boolean isFinal;
        final String name;
        final String desc;
        final RedirectInjector this$0;

        public Meta(RedirectInjector redirectInjector, int i, boolean z, String str, String str2) {
            this.this$0 = redirectInjector;
            this.priority = i;
            this.isFinal = z;
            this.name = str;
            this.desc = str2;
        }

        RedirectInjector getOwner() {
            return this.this$0;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/RedirectInjector$ConstructorRedirectData.class */
    static class ConstructorRedirectData {
        public static final String KEY = "ctor";
        boolean wildcard = false;
        int injected = 0;
        InvalidInjectionException lastException;

        ConstructorRedirectData() {
        }

        public void throwOrCollect(InvalidInjectionException invalidInjectionException) {
            if (!this.wildcard) {
                throw invalidInjectionException;
            }
            this.lastException = invalidInjectionException;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/RedirectInjector$RedirectedInvokeData.class */
    static class RedirectedInvokeData extends Injector.InjectorData {
        final MethodInsnNode node;
        final Type returnType;
        final Type[] targetArgs;
        final Type[] handlerArgs;

        RedirectedInvokeData(Target target, MethodInsnNode methodInsnNode) {
            super(target);
            this.node = methodInsnNode;
            this.returnType = Type.getReturnType(methodInsnNode.desc);
            this.targetArgs = Type.getArgumentTypes(methodInsnNode.desc);
            this.handlerArgs = methodInsnNode.getOpcode() == 184 ? this.targetArgs : (Type[]) ObjectArrays.concat(Type.getObjectType(methodInsnNode.owner), this.targetArgs);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/RedirectInjector$RedirectedFieldData.class */
    static class RedirectedFieldData extends Injector.InjectorData {
        final FieldInsnNode node;
        final int opcode;
        final Type owner;
        final Type type;
        final int dimensions;
        final boolean isStatic;
        final boolean isGetter;
        final boolean isSetter;
        Type elementType;
        int extraDimensions;

        RedirectedFieldData(Target target, FieldInsnNode fieldInsnNode) {
            super(target);
            this.extraDimensions = 1;
            this.node = fieldInsnNode;
            this.opcode = fieldInsnNode.getOpcode();
            this.owner = Type.getObjectType(fieldInsnNode.owner);
            this.type = Type.getType(fieldInsnNode.desc);
            this.dimensions = this.type.getSort() == 9 ? this.type.getDimensions() : 0;
            this.isStatic = this.opcode == 178 || this.opcode == 179;
            this.isGetter = this.opcode == 178 || this.opcode == 180;
            this.isSetter = this.opcode == 179 || this.opcode == 181;
            this.description = this.isGetter ? "field getter" : this.isSetter ? "field setter" : InjectionInfo.DEFAULT_PREFIX;
        }

        int getTotalDimensions() {
            return this.dimensions + this.extraDimensions;
        }

        Type[] getArrayArgs(Type[] typeArr) {
            int totalDimensions = getTotalDimensions();
            Type[] typeArr2 = new Type[totalDimensions + typeArr.length];
            int i = 0;
            while (i < typeArr2.length) {
                typeArr2[i] = i == 0 ? this.type : i < totalDimensions ? Type.INT_TYPE : typeArr[totalDimensions - i];
                i++;
            }
            return typeArr2;
        }
    }

    public RedirectInjector(InjectionInfo injectionInfo) {
        this(injectionInfo, "@Redirect");
    }

    protected RedirectInjector(InjectionInfo injectionInfo, String str) {
        super(injectionInfo, str);
        this.ctorRedirectors = new HashMap();
        this.meta = new Meta(this, injectionInfo.getContext().getPriority(), Annotations.getVisible(this.methodNode, Final.class) != null, this.info.toString(), this.methodNode.desc);
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected void addTargetNode(Target target, List list, AbstractInsnNode abstractInsnNode, Set set) {
        Meta meta;
        InjectionNodes.InjectionNode injectionNode = target.getInjectionNode(abstractInsnNode);
        ConstructorRedirectData ctorRedirect = null;
        int fuzzFactor = 8;
        int arrayOpcode = 0;
        if ((abstractInsnNode instanceof MethodInsnNode) && Constants.CTOR.equals(((MethodInsnNode) abstractInsnNode).name)) {
            throw new InvalidInjectionException(this.info, String.format("Illegal %s of constructor specified on %s", this.annotationType, this));
        }
        if (injectionNode != null && (meta = (Meta) injectionNode.getDecoration(Meta.KEY)) != null && meta.getOwner() != this) {
            if (meta.priority >= this.meta.priority) {
                Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[]{this.annotationType, this.info, Integer.valueOf(this.meta.priority), meta.name, Integer.valueOf(meta.priority)});
                return;
            } else if (meta.isFinal) {
                throw new InvalidInjectionException(this.info, String.format("%s conflict: %s failed because target was already remapped by %s", this.annotationType, this, meta.name));
            }
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            InjectionPoint injectionPoint = (InjectionPoint) it.next();
            if (injectionPoint instanceof BeforeNew) {
                ctorRedirect = getCtorRedirect((BeforeNew) injectionPoint);
                ctorRedirect.wildcard = !((BeforeNew) injectionPoint).hasDescriptor();
            } else if (injectionPoint instanceof BeforeFieldAccess) {
                BeforeFieldAccess beforeFieldAccess = (BeforeFieldAccess) injectionPoint;
                fuzzFactor = beforeFieldAccess.getFuzzFactor();
                arrayOpcode = beforeFieldAccess.getArrayOpcode();
            }
        }
        InjectionNodes.InjectionNode injectionNodeAddInjectionNode = target.addInjectionNode(abstractInsnNode);
        injectionNodeAddInjectionNode.decorate(Meta.KEY, this.meta);
        injectionNodeAddInjectionNode.decorate(KEY_NOMINATORS, set);
        if ((abstractInsnNode instanceof TypeInsnNode) && abstractInsnNode.getOpcode() == 187) {
            injectionNodeAddInjectionNode.decorate(ConstructorRedirectData.KEY, ctorRedirect);
        } else {
            injectionNodeAddInjectionNode.decorate(KEY_FUZZ, Integer.valueOf(fuzzFactor));
            injectionNodeAddInjectionNode.decorate(KEY_OPCODE, Integer.valueOf(arrayOpcode));
        }
        list.add(injectionNodeAddInjectionNode);
    }

    private ConstructorRedirectData getCtorRedirect(BeforeNew beforeNew) {
        ConstructorRedirectData constructorRedirectData = (ConstructorRedirectData) this.ctorRedirectors.get(beforeNew);
        if (constructorRedirectData == null) {
            constructorRedirectData = new ConstructorRedirectData();
            this.ctorRedirectors.put(beforeNew, constructorRedirectData);
        }
        return constructorRedirectData;
    }

    @Override // org.spongepowered.asm.mixin.injection.invoke.InvokeInjector, org.spongepowered.asm.mixin.injection.code.Injector
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        if (!preInject(injectionNode)) {
            return;
        }
        if (injectionNode.isReplaced()) {
            throw new UnsupportedOperationException("Redirector target failure for " + this.info);
        }
        if (injectionNode.getCurrentTarget() instanceof MethodInsnNode) {
            checkTargetForNode(target, injectionNode, InjectionPoint.RestrictTargetLevel.ALLOW_ALL);
            injectAtInvoke(target, injectionNode);
            return;
        }
        if (injectionNode.getCurrentTarget() instanceof FieldInsnNode) {
            checkTargetForNode(target, injectionNode, InjectionPoint.RestrictTargetLevel.ALLOW_ALL);
            injectAtFieldAccess(target, injectionNode);
            return;
        }
        if (injectionNode.getCurrentTarget() instanceof TypeInsnNode) {
            int opcode = injectionNode.getCurrentTarget().getOpcode();
            if (opcode == 187) {
                if (!this.isStatic && target.isStatic) {
                    throw new InvalidInjectionException(this.info, String.format("non-static callback method %s has a static target which is not supported", this));
                }
                injectAtConstructor(target, injectionNode);
                return;
            }
            if (opcode == 193) {
                checkTargetModifiers(target, false);
                injectAtInstanceOf(target, injectionNode);
                return;
            }
        }
        throw new InvalidInjectionException(this.info, String.format("%s annotation on is targetting an invalid insn in %s in %s", this.annotationType, target, this));
    }

    protected boolean preInject(InjectionNodes.InjectionNode injectionNode) {
        Meta meta = (Meta) injectionNode.getDecoration(Meta.KEY);
        if (meta.getOwner() != this) {
            Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[]{this.annotationType, this.info, Integer.valueOf(this.meta.priority), meta.name, Integer.valueOf(meta.priority)});
            return false;
        }
        return true;
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected void postInject(Target target, InjectionNodes.InjectionNode injectionNode) {
        super.postInject(target, injectionNode);
        if ((injectionNode.getOriginalTarget() instanceof TypeInsnNode) && injectionNode.getOriginalTarget().getOpcode() == 187) {
            ConstructorRedirectData constructorRedirectData = (ConstructorRedirectData) injectionNode.getDecoration(ConstructorRedirectData.KEY);
            if (constructorRedirectData.wildcard && constructorRedirectData.injected == 0) {
                throw new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", this.annotationType, target), constructorRedirectData.lastException);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v33, types: [int[], int[][]] */
    @Override // org.spongepowered.asm.mixin.injection.invoke.InvokeInjector
    protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode injectionNode) {
        RedirectedInvokeData redirectedInvokeData = new RedirectedInvokeData(target, injectionNode.getCurrentTarget());
        validateParams(redirectedInvokeData, redirectedInvokeData.returnType, redirectedInvokeData.handlerArgs);
        InsnList insnList = new InsnList();
        Target.Extension extensionAdd = target.extendLocals().add(redirectedInvokeData.handlerArgs).add(1);
        Target.Extension extensionAdd2 = target.extendStack().add(1);
        int[] iArrStoreArgs = storeArgs(target, redirectedInvokeData.handlerArgs, insnList, 0);
        if (redirectedInvokeData.captureTargetArgs > 0) {
            int argsSize = Bytecode.getArgsSize(target.arguments, 0, redirectedInvokeData.captureTargetArgs);
            extensionAdd.add(argsSize);
            extensionAdd2.add(argsSize);
            iArrStoreArgs = Ints.concat((int[][]) new int[]{iArrStoreArgs, target.getArgIndices()});
        }
        AbstractInsnNode abstractInsnNodeInvokeHandlerWithArgs = invokeHandlerWithArgs(this.methodArgs, insnList, iArrStoreArgs);
        if (redirectedInvokeData.coerceReturnType && redirectedInvokeData.returnType.getSort() >= 9) {
            insnList.add(new TypeInsnNode(192, redirectedInvokeData.returnType.getInternalName()));
        }
        target.replaceNode(redirectedInvokeData.node, abstractInsnNodeInvokeHandlerWithArgs, insnList);
        extensionAdd.apply();
        extensionAdd2.apply();
    }

    private void injectAtFieldAccess(Target target, InjectionNodes.InjectionNode injectionNode) {
        RedirectedFieldData redirectedFieldData = new RedirectedFieldData(target, injectionNode.getCurrentTarget());
        int dimensions = this.returnType.getSort() == 9 ? this.returnType.getDimensions() : 0;
        if (dimensions > redirectedFieldData.dimensions) {
            throw new InvalidInjectionException(this.info, "Dimensionality of handler method is greater than target array on " + this);
        }
        if (dimensions == 0 && redirectedFieldData.dimensions > 0) {
            injectAtArrayField(redirectedFieldData, ((Integer) injectionNode.getDecoration(KEY_FUZZ)).intValue(), ((Integer) injectionNode.getDecoration(KEY_OPCODE)).intValue());
        } else {
            injectAtScalarField(redirectedFieldData);
        }
    }

    private void injectAtArrayField(RedirectedFieldData redirectedFieldData, int i, int i2) {
        Type elementType = redirectedFieldData.type.getElementType();
        if (redirectedFieldData.opcode != 178 && redirectedFieldData.opcode != 180) {
            throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for array access %s", Bytecode.getOpcodeName(redirectedFieldData.opcode), this.info));
        }
        if (this.returnType.getSort() != 0) {
            if (i2 != 190) {
                i2 = elementType.getOpcode(46);
            }
            injectAtGetArray(redirectedFieldData, BeforeFieldAccess.findArrayNode(redirectedFieldData.target.insns, redirectedFieldData.node, i2, i));
            return;
        }
        injectAtSetArray(redirectedFieldData, BeforeFieldAccess.findArrayNode(redirectedFieldData.target.insns, redirectedFieldData.node, elementType.getOpcode(79), i));
    }

    private void injectAtGetArray(RedirectedFieldData redirectedFieldData, AbstractInsnNode abstractInsnNode) {
        redirectedFieldData.description = "array getter";
        redirectedFieldData.elementType = redirectedFieldData.type.getElementType();
        if (abstractInsnNode != null && abstractInsnNode.getOpcode() == 190) {
            redirectedFieldData.elementType = Type.INT_TYPE;
            redirectedFieldData.extraDimensions = 0;
        }
        validateParams(redirectedFieldData, redirectedFieldData.elementType, redirectedFieldData.getArrayArgs(new Type[0]));
        injectArrayRedirect(redirectedFieldData, abstractInsnNode, "array getter");
    }

    private void injectAtSetArray(RedirectedFieldData redirectedFieldData, AbstractInsnNode abstractInsnNode) {
        redirectedFieldData.description = "array setter";
        Type elementType = redirectedFieldData.type.getElementType();
        int totalDimensions = redirectedFieldData.getTotalDimensions();
        if (checkCoerce(totalDimensions, elementType, String.format("%s array setter method %s from %s", this.annotationType, this, this.info.getContext()), true)) {
            elementType = this.methodArgs[totalDimensions];
        }
        validateParams(redirectedFieldData, Type.VOID_TYPE, redirectedFieldData.getArrayArgs(new Type[]{elementType}));
        injectArrayRedirect(redirectedFieldData, abstractInsnNode, "array setter");
    }

    private void injectArrayRedirect(RedirectedFieldData redirectedFieldData, AbstractInsnNode abstractInsnNode, String str) {
        if (abstractInsnNode == null) {
            throw new InvalidInjectionException(this.info, String.format("Array element %s on %s could not locate a matching %s instruction in %s. %s", this.annotationType, this, str, redirectedFieldData.target, ""));
        }
        Target.Extension extensionExtendStack = redirectedFieldData.target.extendStack();
        if (!this.isStatic) {
            VarInsnNode varInsnNode = new VarInsnNode(25, 0);
            redirectedFieldData.target.insns.insert(redirectedFieldData.node, varInsnNode);
            redirectedFieldData.target.insns.insert(varInsnNode, new InsnNode(95));
            extensionExtendStack.add();
        }
        InsnList insnList = new InsnList();
        if (redirectedFieldData.captureTargetArgs > 0) {
            pushArgs(redirectedFieldData.target.arguments, insnList, redirectedFieldData.target.getArgIndices(), 0, redirectedFieldData.captureTargetArgs, extensionExtendStack);
        }
        extensionExtendStack.apply();
        AbstractInsnNode abstractInsnNodeInvokeHandler = invokeHandler(insnList);
        if (redirectedFieldData.coerceReturnType && redirectedFieldData.type.getSort() >= 9) {
            insnList.add(new TypeInsnNode(192, redirectedFieldData.elementType.getInternalName()));
        }
        redirectedFieldData.target.replaceNode(abstractInsnNode, abstractInsnNodeInvokeHandler, insnList);
    }

    private void injectAtScalarField(RedirectedFieldData redirectedFieldData) {
        AbstractInsnNode abstractInsnNodeInjectAtPutField;
        InsnList insnList = new InsnList();
        if (redirectedFieldData.isGetter) {
            abstractInsnNodeInjectAtPutField = injectAtGetField(redirectedFieldData, insnList);
        } else if (redirectedFieldData.isSetter) {
            abstractInsnNodeInjectAtPutField = injectAtPutField(redirectedFieldData, insnList);
        } else {
            throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for %s", Bytecode.getOpcodeName(redirectedFieldData.opcode), this.info));
        }
        redirectedFieldData.target.replaceNode(redirectedFieldData.node, abstractInsnNodeInjectAtPutField, insnList);
    }

    private AbstractInsnNode injectAtGetField(RedirectedFieldData redirectedFieldData, InsnList insnList) {
        Type type = redirectedFieldData.type;
        Type[] typeArr = new Type[1];
        typeArr[0] = redirectedFieldData.isStatic ? null : redirectedFieldData.owner;
        validateParams(redirectedFieldData, type, typeArr);
        Target.Extension extensionExtendStack = redirectedFieldData.target.extendStack();
        if (!this.isStatic) {
            extensionExtendStack.add();
            insnList.add(new VarInsnNode(25, 0));
            if (!redirectedFieldData.isStatic) {
                insnList.add(new InsnNode(95));
            }
        }
        if (redirectedFieldData.captureTargetArgs > 0) {
            pushArgs(redirectedFieldData.target.arguments, insnList, redirectedFieldData.target.getArgIndices(), 0, redirectedFieldData.captureTargetArgs, extensionExtendStack);
        }
        extensionExtendStack.apply();
        AbstractInsnNode abstractInsnNodeInvokeHandler = invokeHandler(insnList);
        if (redirectedFieldData.coerceReturnType && redirectedFieldData.type.getSort() >= 9) {
            insnList.add(new TypeInsnNode(192, redirectedFieldData.type.getInternalName()));
        }
        return abstractInsnNodeInvokeHandler;
    }

    private AbstractInsnNode injectAtPutField(RedirectedFieldData redirectedFieldData, InsnList insnList) {
        Type type = Type.VOID_TYPE;
        Type[] typeArr = new Type[2];
        typeArr[0] = redirectedFieldData.isStatic ? null : redirectedFieldData.owner;
        typeArr[1] = redirectedFieldData.type;
        validateParams(redirectedFieldData, type, typeArr);
        Target.Extension extensionExtendStack = redirectedFieldData.target.extendStack();
        if (!this.isStatic) {
            if (redirectedFieldData.isStatic) {
                insnList.add(new VarInsnNode(25, 0));
                insnList.add(new InsnNode(95));
            } else {
                extensionExtendStack.add();
                int iAllocateLocals = redirectedFieldData.target.allocateLocals(redirectedFieldData.type.getSize());
                insnList.add(new VarInsnNode(redirectedFieldData.type.getOpcode(54), iAllocateLocals));
                insnList.add(new VarInsnNode(25, 0));
                insnList.add(new InsnNode(95));
                insnList.add(new VarInsnNode(redirectedFieldData.type.getOpcode(21), iAllocateLocals));
            }
        }
        if (redirectedFieldData.captureTargetArgs > 0) {
            pushArgs(redirectedFieldData.target.arguments, insnList, redirectedFieldData.target.getArgIndices(), 0, redirectedFieldData.captureTargetArgs, extensionExtendStack);
        }
        extensionExtendStack.apply();
        return invokeHandler(insnList);
    }

    protected void injectAtConstructor(Target target, InjectionNodes.InjectionNode injectionNode) {
        ConstructorRedirectData constructorRedirectData = (ConstructorRedirectData) injectionNode.getDecoration(ConstructorRedirectData.KEY);
        if (constructorRedirectData == null) {
            throw new InvalidInjectionException(this.info, String.format("%s ctor redirector has no metadata, the injector failed a preprocessing phase", this.annotationType));
        }
        TypeInsnNode currentTarget = injectionNode.getCurrentTarget();
        AbstractInsnNode abstractInsnNode = target.get(target.indexOf((AbstractInsnNode) currentTarget) + 1);
        MethodInsnNode methodInsnNodeFindInitNodeFor = target.findInitNodeFor(currentTarget);
        if (methodInsnNodeFindInitNodeFor == null) {
            constructorRedirectData.throwOrCollect(new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", this.annotationType, target)));
            return;
        }
        boolean z = abstractInsnNode.getOpcode() == 89;
        RedirectedInvokeData redirectedInvokeData = new RedirectedInvokeData(target, methodInsnNodeFindInitNodeFor);
        redirectedInvokeData.description = "factory";
        try {
            validateParams(redirectedInvokeData, Type.getObjectType(currentTarget.desc), redirectedInvokeData.targetArgs);
            if (z) {
                target.removeNode(abstractInsnNode);
            }
            if (this.isStatic) {
                target.removeNode(currentTarget);
            } else {
                target.replaceNode((AbstractInsnNode) currentTarget, (AbstractInsnNode) new VarInsnNode(25, 0));
            }
            Target.Extension extensionExtendStack = target.extendStack();
            InsnList insnList = new InsnList();
            if (redirectedInvokeData.captureTargetArgs > 0) {
                pushArgs(target.arguments, insnList, target.getArgIndices(), 0, redirectedInvokeData.captureTargetArgs, extensionExtendStack);
            }
            invokeHandler(insnList);
            if (redirectedInvokeData.coerceReturnType) {
                insnList.add(new TypeInsnNode(192, currentTarget.desc));
            }
            extensionExtendStack.apply();
            if (z) {
                doNullCheck(insnList, extensionExtendStack, "constructor handler", currentTarget.desc.replace('/', '.'));
            } else {
                insnList.add(new InsnNode(87));
            }
            extensionExtendStack.apply();
            target.replaceNode((AbstractInsnNode) methodInsnNodeFindInitNodeFor, insnList);
            constructorRedirectData.injected++;
        } catch (InvalidInjectionException e) {
            constructorRedirectData.throwOrCollect(e);
        }
    }

    protected void injectAtInstanceOf(Target target, InjectionNodes.InjectionNode injectionNode) {
        injectAtInstanceOf(target, (TypeInsnNode) injectionNode.getCurrentTarget());
    }

    protected void injectAtInstanceOf(Target target, TypeInsnNode typeInsnNode) {
        if (this.returnType.getSort() == 1) {
            redirectInstanceOf(target, typeInsnNode, false);
        } else {
            if (this.returnType.equals(Type.getType(Constants.CLASS_DESC))) {
                redirectInstanceOf(target, typeInsnNode, true);
                return;
            }
            throw new InvalidInjectionException(this.info, String.format("%s on %s has an invalid signature. Found unexpected return type %s. INSTANCEOF handler expects (Ljava/lang/Object;Ljava/lang/Class;)Z or (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Class;", this.annotationType, this, SignaturePrinter.getTypeName(this.returnType)));
        }
    }

    private void redirectInstanceOf(Target target, TypeInsnNode typeInsnNode, boolean z) {
        Target.Extension extensionExtendStack = target.extendStack();
        InsnList insnList = new InsnList();
        Injector.InjectorData injectorData = new Injector.InjectorData(target, "instanceof handler", false);
        validateParams(injectorData, this.returnType, Type.getType(Constants.OBJECT_DESC), Type.getType(Constants.CLASS_DESC));
        if (z) {
            insnList.add(new InsnNode(89));
            extensionExtendStack.add();
        }
        if (!this.isStatic) {
            insnList.add(new VarInsnNode(25, 0));
            insnList.add(new InsnNode(95));
            extensionExtendStack.add();
        }
        insnList.add(new LdcInsnNode(Type.getObjectType(typeInsnNode.desc)));
        extensionExtendStack.add();
        if (injectorData.captureTargetArgs > 0) {
            pushArgs(target.arguments, insnList, target.getArgIndices(), 0, injectorData.captureTargetArgs, extensionExtendStack);
        }
        AbstractInsnNode abstractInsnNodeInvokeHandler = invokeHandler(insnList);
        if (z) {
            doNullCheck(insnList, extensionExtendStack, "instanceof handler", "class type");
            checkIsAssignableFrom(insnList, extensionExtendStack);
        }
        target.replaceNode(typeInsnNode, abstractInsnNodeInvokeHandler, insnList);
        extensionExtendStack.apply();
    }

    private void checkIsAssignableFrom(InsnList insnList, Target.Extension extension) {
        LabelNode labelNode = new LabelNode();
        LabelNode labelNode2 = new LabelNode();
        insnList.add(new InsnNode(95));
        insnList.add(new InsnNode(89));
        extension.add();
        insnList.add(new JumpInsnNode(198, labelNode));
        insnList.add(new MethodInsnNode(Typography.paragraph, Constants.OBJECT, GET_CLASS_METHOD, "()Ljava/lang/Class;", false));
        insnList.add(new MethodInsnNode(Typography.paragraph, Constants.CLASS, IS_ASSIGNABLE_FROM_METHOD, "(Ljava/lang/Class;)Z", false));
        insnList.add(new JumpInsnNode(Typography.section, labelNode2));
        insnList.add(labelNode);
        insnList.add(new InsnNode(87));
        insnList.add(new InsnNode(87));
        insnList.add(new InsnNode(3));
        insnList.add(labelNode2);
        extension.add();
    }

    private void doNullCheck(InsnList insnList, Target.Extension extension, String str, String str2) {
        LabelNode labelNode = new LabelNode();
        insnList.add(new InsnNode(89));
        insnList.add(new JumpInsnNode(199, labelNode));
        throwException(insnList, NPE, String.format("%s %s %s returned null for %s", this.annotationType, str, this, str2));
        insnList.add(labelNode);
        extension.add();
    }
}
