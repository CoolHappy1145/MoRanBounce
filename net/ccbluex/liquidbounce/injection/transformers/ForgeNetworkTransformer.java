package net.ccbluex.liquidbounce.injection.transformers;

import kotlin.text.Typography;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.ClassUtils;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.NodeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.log4j.net.SyslogAppender;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer.class */
public class ForgeNetworkTransformer implements IClassTransformer {
    public static boolean returnMethod() {
        return AntiForge.enabled && AntiForge.blockFML && !Minecraft.func_71410_x().func_71387_A();
    }

    public byte[] transform(String str, String str2, byte[] bArr) {
        if (str.equals("net.minecraftforge.fml.common.network.handshake.NetworkDispatcher")) {
            try {
                ClassNode classNode = ClassUtils.INSTANCE.toClassNode(bArr);
                classNode.methods.stream().filter(ForgeNetworkTransformer::lambda$transform$0).forEach(ForgeNetworkTransformer::lambda$transform$1);
                return ClassUtils.INSTANCE.toBytes(classNode);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        if (str.equals("net.minecraftforge.fml.common.network.handshake.HandshakeMessageHandler")) {
            try {
                ClassNode classNode2 = ClassUtils.INSTANCE.toClassNode(bArr);
                classNode2.methods.stream().filter(ForgeNetworkTransformer::lambda$transform$2).forEach(ForgeNetworkTransformer::lambda$transform$3);
                return ClassUtils.INSTANCE.toBytes(classNode2);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
        return bArr;
    }

    private static boolean lambda$transform$0(MethodNode methodNode) {
        return methodNode.name.equals("handleVanilla");
    }

    private static void lambda$transform$1(MethodNode methodNode) {
        AbstractInsnNode labelNode = new LabelNode();
        methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[]{new MethodInsnNode(SyslogAppender.LOG_LOCAL7, "net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false), new JumpInsnNode(153, labelNode), new InsnNode(3), new InsnNode(172), labelNode}));
    }

    private static boolean lambda$transform$2(MethodNode methodNode) {
        return methodNode.name.equals("channelRead0");
    }

    private static void lambda$transform$3(MethodNode methodNode) {
        AbstractInsnNode labelNode = new LabelNode();
        methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new AbstractInsnNode[]{new MethodInsnNode(SyslogAppender.LOG_LOCAL7, "net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false), new JumpInsnNode(153, labelNode), new InsnNode(Typography.plusMinus), labelNode}));
    }
}
