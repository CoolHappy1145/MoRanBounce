package org.spongepowered.asm.mixin.transformer;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.struct.SourceMap;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.ClassSignature;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/TargetClassContext.class */
final class TargetClassContext extends ClassContext implements ITargetClassContext {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final MixinEnvironment env;
    private final Extensions extensions;
    private final String sessionId;
    private final String className;
    private final ClassNode classNode;
    private final ClassInfo classInfo;
    private final SourceMap sourceMap;
    private final ClassSignature signature;
    private final SortedSet mixins;
    private final Map targetMethods = new HashMap();
    private final Set mixinMethods = new HashSet();
    private final List suppressedExceptions = new ArrayList();
    private boolean applied;
    private boolean forceExport;

    TargetClassContext(MixinEnvironment mixinEnvironment, Extensions extensions, String str, String str2, ClassNode classNode, SortedSet sortedSet) {
        this.env = mixinEnvironment;
        this.extensions = extensions;
        this.sessionId = str;
        this.className = str2;
        this.classNode = classNode;
        this.classInfo = ClassInfo.fromClassNode(classNode);
        this.signature = this.classInfo.getSignature();
        this.mixins = sortedSet;
        this.sourceMap = new SourceMap(classNode.sourceFile);
        this.sourceMap.addFile(this.classNode);
    }

    public String toString() {
        return this.className;
    }

    boolean isApplied() {
        return this.applied;
    }

    boolean isExportForced() {
        return this.forceExport;
    }

    Extensions getExtensions() {
        return this.extensions;
    }

    String getSessionId() {
        return this.sessionId;
    }

    @Override // org.spongepowered.asm.mixin.transformer.ClassContext, org.spongepowered.asm.mixin.refmap.IMixinContext
    String getClassRef() {
        return this.classNode.name;
    }

    String getClassName() {
        return this.className;
    }

    @Override // org.spongepowered.asm.mixin.transformer.ClassContext
    public ClassNode getClassNode() {
        return this.classNode;
    }

    List getMethods() {
        return this.classNode.methods;
    }

    List getFields() {
        return this.classNode.fields;
    }

    @Override // org.spongepowered.asm.mixin.transformer.ClassContext
    public ClassInfo getClassInfo() {
        return this.classInfo;
    }

    SortedSet getMixins() {
        return this.mixins;
    }

    SourceMap getSourceMap() {
        return this.sourceMap;
    }

    void mergeSignature(ClassSignature classSignature) {
        this.signature.merge(classSignature);
    }

    void addMixinMethod(MethodNode methodNode) {
        this.mixinMethods.add(methodNode);
    }

    void methodMerged(MethodNode methodNode) {
        if (!this.mixinMethods.remove(methodNode)) {
            logger.debug("Unexpected: Merged unregistered method {}{} in {}", new Object[]{methodNode.name, methodNode.desc, this});
        }
    }

    MethodNode findMethod(Deque deque, String str) {
        return findAliasedMethod(deque, str, true);
    }

    MethodNode findAliasedMethod(Deque deque, String str) {
        return findAliasedMethod(deque, str, false);
    }

    private MethodNode findAliasedMethod(Deque deque, String str, boolean z) {
        String str2 = (String) deque.poll();
        if (str2 == null) {
            return null;
        }
        for (MethodNode methodNode : this.classNode.methods) {
            if (methodNode.name.equals(str2) && methodNode.desc.equals(str)) {
                return methodNode;
            }
        }
        if (z) {
            for (MethodNode methodNode2 : this.mixinMethods) {
                if (methodNode2.name.equals(str2) && methodNode2.desc.equals(str)) {
                    return methodNode2;
                }
            }
        }
        return findAliasedMethod(deque, str);
    }

    FieldNode findAliasedField(Deque deque, String str) {
        String str2 = (String) deque.poll();
        if (str2 == null) {
            return null;
        }
        for (FieldNode fieldNode : this.classNode.fields) {
            if (fieldNode.name.equals(str2) && fieldNode.desc.equals(str)) {
                return fieldNode;
            }
        }
        return findAliasedField(deque, str);
    }

    Target getTargetMethod(MethodNode methodNode) {
        if (!this.classNode.methods.contains(methodNode)) {
            throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()");
        }
        String str = methodNode.name + methodNode.desc;
        Target target = (Target) this.targetMethods.get(str);
        if (target == null) {
            target = new Target(this.classNode, methodNode);
            this.targetMethods.put(str, target);
        }
        return target;
    }

    void applyMixins() {
        if (this.applied) {
            throw new IllegalStateException("Mixins already applied to target class " + this.className);
        }
        this.applied = true;
        createApplicator().apply(this.mixins);
        applySignature();
        upgradeMethods();
        checkMerges();
    }

    private MixinApplicatorStandard createApplicator() {
        if (this.classInfo.isInterface()) {
            return new MixinApplicatorInterface(this);
        }
        return new MixinApplicatorStandard(this);
    }

    private void applySignature() {
        this.classNode.signature = this.signature.toString();
    }

    private void checkMerges() {
        for (MethodNode methodNode : this.mixinMethods) {
            if (!methodNode.name.startsWith("<")) {
                logger.debug("Unexpected: Registered method {}{} in {} was not merged", new Object[]{methodNode.name, methodNode.desc, this});
            }
        }
    }

    void processDebugTasks() {
        AnnotationNode visible = Annotations.getVisible(this.classNode, Debug.class);
        this.forceExport = visible != null && Boolean.TRUE.equals(Annotations.getValue(visible, "export"));
        if (!this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            return;
        }
        if (visible != null && Boolean.TRUE.equals(Annotations.getValue(visible, "print"))) {
            Bytecode.textify(this.classNode, System.err);
        }
        for (MethodNode methodNode : this.classNode.methods) {
            AnnotationNode visible2 = Annotations.getVisible(methodNode, Debug.class);
            if (visible2 != null && Boolean.TRUE.equals(Annotations.getValue(visible2, "print"))) {
                Bytecode.textify(methodNode, System.err);
            }
        }
    }

    void addSuppressed(InvalidMixinException invalidMixinException) {
        this.suppressedExceptions.add(invalidMixinException);
    }

    List getSuppressedExceptions() {
        return this.suppressedExceptions;
    }
}
