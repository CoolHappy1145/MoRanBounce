package net.ccbluex.liquidbounce.chat.packet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00072\u000e\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R:\u0010\u0004\u001a.\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/PacketSerializer;", "Lcom/google/gson/JsonSerializer;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "()V", "packetRegistry", "Ljava/util/HashMap;", Constants.CLASS_DESC, "", "Lkotlin/collections/HashMap;", "registerPacket", "", "packetName", "packetClass", "serialize", "Lcom/google/gson/JsonElement;", "src", "typeOfSrc", "Ljava/lang/reflect/Type;", "context", "Lcom/google/gson/JsonSerializationContext;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/packet/PacketSerializer.class */
public final class PacketSerializer implements JsonSerializer {
    private final HashMap packetRegistry = new HashMap();

    public JsonElement serialize(Object obj, Type type, JsonSerializationContext jsonSerializationContext) {
        return serialize((Packet) obj, type, jsonSerializationContext);
    }

    public final void registerPacket(@NotNull String packetName, @NotNull Class packetClass) {
        Intrinsics.checkParameterIsNotNull(packetName, "packetName");
        Intrinsics.checkParameterIsNotNull(packetClass, "packetClass");
        this.packetRegistry.put(packetClass, packetName);
    }

    @NotNull
    public JsonElement serialize(@NotNull Packet src, @NotNull Type typeOfSrc, @NotNull JsonSerializationContext context) throws SecurityException {
        boolean z;
        Intrinsics.checkParameterIsNotNull(src, "src");
        Intrinsics.checkParameterIsNotNull(typeOfSrc, "typeOfSrc");
        Intrinsics.checkParameterIsNotNull(context, "context");
        Object orDefault = this.packetRegistry.getOrDefault(src.getClass(), Constants.SIDE_UNKNOWN);
        Intrinsics.checkExpressionValueIsNotNull(orDefault, "packetRegistry.getOrDefa\u2026src.javaClass, \"UNKNOWN\")");
        String str = (String) orDefault;
        Constructor<?>[] constructors = src.getClass().getConstructors();
        Intrinsics.checkExpressionValueIsNotNull(constructors, "src.javaClass.constructors");
        int length = constructors.length;
        int i = 0;
        while (true) {
            if (i < length) {
                Constructor<?> it = constructors[i];
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                if (it.getParameterCount() != 0) {
                    z = false;
                    break;
                }
                i++;
            } else {
                z = true;
                break;
            }
        }
        JsonElement jsonTree = new Gson().toJsonTree(new SerializedPacket(str, z ? null : src));
        Intrinsics.checkExpressionValueIsNotNull(jsonTree, "Gson().toJsonTree(serializedPacket)");
        return jsonTree;
    }
}
