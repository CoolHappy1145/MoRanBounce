package org.spongepowered.asm.mixin.transformer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.net.SyslogAppender;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.MixinConfig;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinProxy;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinPostProcessor.class */
class MixinPostProcessor implements MixinConfig.IListener {
    private final String sessionId;
    private final Set syntheticInnerClasses = new HashSet();
    private final Map accessorMixins = new HashMap();
    private final Set loadable = new HashSet();

    MixinPostProcessor(String str) {
        this.sessionId = str;
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinConfig.IListener
    public void onInit(MixinInfo mixinInfo) {
        Iterator it = mixinInfo.getSyntheticInnerClasses().iterator();
        while (it.hasNext()) {
            registerSyntheticInner(((String) it.next()).replace('/', '.'));
        }
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinConfig.IListener
    public void onPrepare(MixinInfo mixinInfo) {
        String className = mixinInfo.getClassName();
        if (mixinInfo.isLoadable()) {
            registerLoadable(className);
        }
        if (mixinInfo.isAccessor()) {
            registerAccessor(mixinInfo);
        }
    }

    void registerSyntheticInner(String str) {
        this.syntheticInnerClasses.add(str);
    }

    void registerLoadable(String str) {
        this.loadable.add(str);
    }

    void registerAccessor(MixinInfo mixinInfo) {
        registerLoadable(mixinInfo.getClassName());
        this.accessorMixins.put(mixinInfo.getClassName(), mixinInfo);
    }

    boolean canProcess(String str) {
        return this.syntheticInnerClasses.contains(str) || this.loadable.contains(str);
    }

    public boolean processClass(String str, ClassNode classNode) {
        if (this.syntheticInnerClasses.contains(str)) {
            processSyntheticInner(classNode);
            return true;
        }
        if (this.accessorMixins.containsKey(str)) {
            return processAccessor(classNode, (MixinInfo) this.accessorMixins.get(str));
        }
        return false;
    }

    private void processSyntheticInner(ClassNode classNode) {
        classNode.access |= 1;
        for (FieldNode fieldNode : classNode.fields) {
            if ((fieldNode.access & 6) == 0) {
                fieldNode.access |= 1;
            }
        }
        for (MethodNode methodNode : classNode.methods) {
            if ((methodNode.access & 6) == 0) {
                methodNode.access |= 1;
            }
        }
    }

    private boolean processAccessor(ClassNode classNode, MixinInfo mixinInfo) {
        if (!MixinEnvironment.getCompatibilityLevel().supports(1)) {
            return false;
        }
        boolean z = false;
        MixinInfo.MixinClassNode classNode2 = mixinInfo.getClassNode(0);
        ClassInfo classInfo = (ClassInfo) mixinInfo.getTargets().get(0);
        for (MixinInfo.MixinMethodNode mixinMethodNode : classNode2.mixinMethods) {
            if (Bytecode.hasFlag(mixinMethodNode, 8)) {
                AnnotationNode visibleAnnotation = mixinMethodNode.getVisibleAnnotation(Accessor.class);
                AnnotationNode visibleAnnotation2 = mixinMethodNode.getVisibleAnnotation(Invoker.class);
                if (visibleAnnotation != null || visibleAnnotation2 != null) {
                    createProxy(mixinMethodNode, classInfo, getAccessorMethod(mixinInfo, mixinMethodNode, classInfo));
                    Annotations.setVisible(mixinMethodNode, MixinProxy.class, new Object[]{"sessionId", this.sessionId});
                    classNode.methods.add(mixinMethodNode);
                    z = true;
                }
            }
        }
        if (!z) {
            return false;
        }
        Bytecode.replace(classNode2, classNode);
        return true;
    }

    private ClassInfo.Method getAccessorMethod(MixinInfo mixinInfo, MethodNode methodNode, ClassInfo classInfo) {
        ClassInfo.Method methodFindMethod = mixinInfo.getClassInfo().findMethod(methodNode, 10);
        if (!methodFindMethod.isConformed()) {
            methodFindMethod.conform(classInfo.getMethodMapper().getUniqueName(methodNode, this.sessionId, true));
        }
        return methodFindMethod;
    }

    private static void createProxy(MethodNode methodNode, ClassInfo classInfo, ClassInfo.Method method) {
        methodNode.access |= 4096;
        methodNode.instructions.clear();
        Type[] argumentTypes = Type.getArgumentTypes(methodNode.desc);
        Type returnType = Type.getReturnType(methodNode.desc);
        Bytecode.loadArgs(argumentTypes, methodNode.instructions, 0);
        methodNode.instructions.add(new MethodInsnNode(SyslogAppender.LOG_LOCAL7, classInfo.getName(), method.getName(), methodNode.desc, false));
        methodNode.instructions.add(new InsnNode(returnType.getOpcode(172)));
        methodNode.maxStack = Bytecode.getFirstNonArgLocalIndex(argumentTypes, false);
        methodNode.maxLocals = 0;
    }
}
