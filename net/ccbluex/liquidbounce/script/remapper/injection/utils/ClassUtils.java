package net.ccbluex.liquidbounce.script.remapper.injection.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0012\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/injection/utils/ClassUtils;", "", "()V", "toBytes", "", "classNode", "Lorg/objectweb/asm/tree/ClassNode;", "toClassNode", "bytes", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/remapper/injection/utils/ClassUtils.class */
public final class ClassUtils {
    public static final ClassUtils INSTANCE = new ClassUtils();

    private ClassUtils() {
    }

    @NotNull
    public final ClassNode toClassNode(@NotNull byte[] bytes) {
        Intrinsics.checkParameterIsNotNull(bytes, "bytes");
        ClassReader classReader = new ClassReader(bytes);
        ClassVisitor classNode = new ClassNode();
        classReader.accept(classNode, 0);
        return classNode;
    }

    @NotNull
    public final byte[] toBytes(@NotNull ClassNode classNode) {
        Intrinsics.checkParameterIsNotNull(classNode, "classNode");
        ClassVisitor classWriter = new ClassWriter(3);
        classNode.accept(classWriter);
        byte[] byteArray = classWriter.toByteArray();
        Intrinsics.checkExpressionValueIsNotNull(byteArray, "classWriter.toByteArray()");
        return byteArray;
    }
}
