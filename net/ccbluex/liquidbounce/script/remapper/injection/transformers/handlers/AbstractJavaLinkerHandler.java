package net.ccbluex.liquidbounce.script.remapper.injection.transformers.handlers;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.script.remapper.Remapper;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u001c\u0010\n\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler;", "", "()V", "addMember", "", "clazz", Constants.CLASS_DESC, "name", "accessibleObject", "Ljava/lang/reflect/AccessibleObject;", "setPropertyGetter", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler.class */
public final class AbstractJavaLinkerHandler {
    public static final AbstractJavaLinkerHandler INSTANCE = new AbstractJavaLinkerHandler();

    private AbstractJavaLinkerHandler() {
    }

    @JvmStatic
    @NotNull
    public static final String addMember(@NotNull Class clazz, @NotNull String name, @NotNull AccessibleObject accessibleObject) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(accessibleObject, "accessibleObject");
        if (!(accessibleObject instanceof Method)) {
            return name;
        }
        Class superclass = clazz;
        while (true) {
            Class cls = superclass;
            if (!(!Intrinsics.areEqual(cls.getName(), "java.lang.Object"))) {
                break;
            }
            Remapper remapper = Remapper.INSTANCE;
            String methodDescriptor = Type.getMethodDescriptor((Method) accessibleObject);
            Intrinsics.checkExpressionValueIsNotNull(methodDescriptor, "Type.getMethodDescriptor(accessibleObject)");
            String strRemapMethod = remapper.remapMethod(cls, name, methodDescriptor);
            if (!Intrinsics.areEqual(strRemapMethod, name)) {
                return strRemapMethod;
            }
            if (cls.getSuperclass() == null) {
                break;
            }
            superclass = cls.getSuperclass();
            Intrinsics.checkExpressionValueIsNotNull(superclass, "currentClass.superclass");
        }
        return name;
    }

    @JvmStatic
    @NotNull
    public static final String addMember(@NotNull Class clazz, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Class superclass = clazz;
        while (true) {
            Class cls = superclass;
            if (!(!Intrinsics.areEqual(cls.getName(), "java.lang.Object"))) {
                break;
            }
            String strRemapField = Remapper.INSTANCE.remapField(cls, name);
            if (!Intrinsics.areEqual(strRemapField, name)) {
                return strRemapField;
            }
            if (cls.getSuperclass() == null) {
                break;
            }
            superclass = cls.getSuperclass();
            Intrinsics.checkExpressionValueIsNotNull(superclass, "currentClass.superclass");
        }
        return name;
    }

    @JvmStatic
    @NotNull
    public static final String setPropertyGetter(@NotNull Class clazz, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Class superclass = clazz;
        while (true) {
            Class cls = superclass;
            if (!(!Intrinsics.areEqual(cls.getName(), "java.lang.Object"))) {
                break;
            }
            String strRemapField = Remapper.INSTANCE.remapField(cls, name);
            if (!Intrinsics.areEqual(strRemapField, name)) {
                return strRemapField;
            }
            if (cls.getSuperclass() == null) {
                break;
            }
            superclass = cls.getSuperclass();
            Intrinsics.checkExpressionValueIsNotNull(superclass, "currentClass.superclass");
        }
        return name;
    }
}
