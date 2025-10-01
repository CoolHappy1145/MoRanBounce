package org.spongepowered.tools.agent;

import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/spongepowered/tools/agent/MixinAgentClassLoader.class */
class MixinAgentClassLoader extends ClassLoader {
    private static final Logger logger = LogManager.getLogger("mixin.agent");
    private Map mixins = new HashMap();
    private Map targets = new HashMap();

    MixinAgentClassLoader() {
    }

    void addMixinClass(String str) {
        logger.debug("Mixin class {} added to class loader", new Object[]{str});
        try {
            byte[] bArrMaterialise = materialise(str);
            Class<?> clsDefineClass = defineClass(str, bArrMaterialise, 0, bArrMaterialise.length);
            clsDefineClass.newInstance();
            this.mixins.put(clsDefineClass, bArrMaterialise);
        } catch (Throwable th) {
            logger.catching(th);
        }
    }

    void addTargetClass(String str, ClassNode classNode) {
        synchronized (this.targets) {
            if (this.targets.containsKey(str)) {
                return;
            }
            try {
                ClassWriter classWriter = new ClassWriter(0);
                classNode.accept(classWriter);
                this.targets.put(str, classWriter.toByteArray());
            } catch (Exception e) {
                logger.error("Error storing original class bytecode for {} in mixin hotswap agent. {}: {}", new Object[]{str, e.getClass().getName(), e.getMessage()});
                logger.debug(e);
            }
        }
    }

    byte[] getFakeMixinBytecode(Class cls) {
        return (byte[]) this.mixins.get(cls);
    }

    byte[] getOriginalTargetBytecode(String str) {
        byte[] bArr;
        synchronized (this.targets) {
            bArr = (byte[]) this.targets.get(str);
        }
        return bArr;
    }

    private byte[] materialise(String str) {
        ClassWriter classWriter = new ClassWriter(3);
        classWriter.visit(MixinEnvironment.getCompatibilityLevel().classVersion(), 1, str.replace('.', '/'), (String) null, Type.getInternalName(Object.class), (String[]) null);
        MethodVisitor methodVisitorVisitMethod = classWriter.visitMethod(1, Constants.CTOR, "()V", (String) null, (String[]) null);
        methodVisitorVisitMethod.visitCode();
        methodVisitorVisitMethod.visitVarInsn(25, 0);
        methodVisitorVisitMethod.visitMethodInsn(Typography.middleDot, Type.getInternalName(Object.class), Constants.CTOR, "()V", false);
        methodVisitorVisitMethod.visitInsn(Typography.plusMinus);
        methodVisitorVisitMethod.visitMaxs(1, 1);
        methodVisitorVisitMethod.visitEnd();
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }
}
