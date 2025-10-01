package net.ccbluex.liquidbounce.script.remapper.injection.transformers;

import net.ccbluex.liquidbounce.script.remapper.injection.utils.ClassUtils;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.NodeUtils;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.log4j.net.SyslogAppender;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/remapper/injection/transformers/AbstractJavaLinkerTransformer.class */
public class AbstractJavaLinkerTransformer implements IClassTransformer {
    public byte[] transform(String str, String str2, byte[] bArr) {
        if (str.equals("jdk.internal.dynalink.beans.AbstractJavaLinker")) {
            try {
                ClassNode classNode = ClassUtils.INSTANCE.toClassNode(bArr);
                classNode.methods.forEach(AbstractJavaLinkerTransformer::lambda$transform$0);
                return ClassUtils.INSTANCE.toBytes(classNode);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return bArr;
    }

    private static void lambda$transform$0(MethodNode methodNode) {
        switch (methodNode.name + methodNode.desc) {
            case "addMember(Ljava/lang/String;Ljava/lang/reflect/AccessibleObject;Ljava/util/Map;)V":
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[]{new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", Constants.CLASS_DESC), new VarInsnNode(25, 1), new VarInsnNode(25, 2), new MethodInsnNode(SyslogAppender.LOG_LOCAL7, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "addMember", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/reflect/AccessibleObject;)Ljava/lang/String;", false), new VarInsnNode(58, 1)}));
                break;
            case "addMember(Ljava/lang/String;Ljdk/internal/dynalink/beans/SingleDynamicMethod;Ljava/util/Map;)V":
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[]{new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", Constants.CLASS_DESC), new VarInsnNode(25, 1), new MethodInsnNode(SyslogAppender.LOG_LOCAL7, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "addMember", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/String;", false), new VarInsnNode(58, 1)}));
                break;
            case "setPropertyGetter(Ljava/lang/String;Ljdk/internal/dynalink/beans/SingleDynamicMethod;Ljdk/internal/dynalink/beans/GuardedInvocationComponent$ValidationType;)V":
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[]{new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", Constants.CLASS_DESC), new VarInsnNode(25, 1), new MethodInsnNode(SyslogAppender.LOG_LOCAL7, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "setPropertyGetter", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/String;", false), new VarInsnNode(58, 1)}));
                break;
        }
    }
}
