package net.ccbluex.liquidbounce.script.remapper.injection.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\"\u00020\u0007\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/injection/utils/NodeUtils;", "", "()V", "toNodes", "Lorg/objectweb/asm/tree/InsnList;", "nodes", "", "Lorg/objectweb/asm/tree/AbstractInsnNode;", "([Lorg/objectweb/asm/tree/AbstractInsnNode;)Lorg/objectweb/asm/tree/InsnList;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/remapper/injection/utils/NodeUtils.class */
public final class NodeUtils {
    public static final NodeUtils INSTANCE = new NodeUtils();

    private NodeUtils() {
    }

    @NotNull
    public final InsnList toNodes(@NotNull AbstractInsnNode[] nodes) {
        Intrinsics.checkParameterIsNotNull(nodes, "nodes");
        InsnList insnList = new InsnList();
        for (AbstractInsnNode abstractInsnNode : nodes) {
            insnList.add(abstractInsnNode);
        }
        return insnList;
    }
}
