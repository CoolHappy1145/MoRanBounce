package net.ccbluex.liquidbounce.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0005H\u0007J\u0006\u0010\t\u001a\u00020\u0006R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\n"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/ClassUtils;", "", "()V", "cachedClasses", "", "", "", "hasClass", "className", "hasForge", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/ClassUtils.class */
public final class ClassUtils {
    public static final ClassUtils INSTANCE = new ClassUtils();
    private static final Map cachedClasses = new LinkedHashMap();

    private ClassUtils() {
    }

    @JvmStatic
    public static final boolean hasClass(@NotNull String className) throws ClassNotFoundException {
        boolean z;
        Intrinsics.checkParameterIsNotNull(className, "className");
        if (cachedClasses.containsKey(className)) {
            Object obj = cachedClasses.get(className);
            if (obj == null) {
                Intrinsics.throwNpe();
            }
            return ((Boolean) obj).booleanValue();
        }
        try {
            Class.forName(className);
            cachedClasses.put(className, true);
            z = true;
        } catch (ClassNotFoundException unused) {
            cachedClasses.put(className, false);
            z = false;
        }
        return z;
    }

    public final boolean hasForge() {
        return hasClass("net.minecraftforge.common.MinecraftForge");
    }
}
