package net.ccbluex.liquidbounce.chat.packet;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J$\u0010\t\u001a\u0004\u0018\u00010\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00062\u000e\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0007R:\u0010\u0004\u001a.\u0012\u0004\u0012\u00020\u0006\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00070\u0005j\u0016\u0012\u0004\u0012\u00020\u0006\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0007`\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/PacketDeserializer;", "Lcom/google/gson/JsonDeserializer;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "()V", "packetRegistry", "Ljava/util/HashMap;", "", Constants.CLASS_DESC, "Lkotlin/collections/HashMap;", "deserialize", "json", "Lcom/google/gson/JsonElement;", "typeOfT", "Ljava/lang/reflect/Type;", "context", "Lcom/google/gson/JsonDeserializationContext;", "registerPacket", "", "packetName", "packetClass", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/packet/PacketDeserializer.class */
public final class PacketDeserializer implements JsonDeserializer {
    private final HashMap packetRegistry = new HashMap();

    /* renamed from: deserialize, reason: collision with other method in class */
    public Object m1561deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        return deserialize(jsonElement, type, jsonDeserializationContext);
    }

    public final void registerPacket(@NotNull String packetName, @NotNull Class packetClass) {
        Intrinsics.checkParameterIsNotNull(packetName, "packetName");
        Intrinsics.checkParameterIsNotNull(packetClass, "packetClass");
        this.packetRegistry.put(packetName, packetClass);
    }

    @Nullable
    public Packet deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @Nullable JsonDeserializationContext jsonDeserializationContext) {
        Intrinsics.checkParameterIsNotNull(json, "json");
        Intrinsics.checkParameterIsNotNull(typeOfT, "typeOfT");
        JsonObject asJsonObject = json.getAsJsonObject();
        JsonElement jsonElement = asJsonObject.get("m");
        Intrinsics.checkExpressionValueIsNotNull(jsonElement, "packetObject.get(\"m\")");
        String asString = jsonElement.getAsString();
        if (!this.packetRegistry.containsKey(asString)) {
            return null;
        }
        if (!asJsonObject.has("c")) {
            asJsonObject.add("c", new JsonObject());
        }
        return (Packet) new Gson().fromJson(asJsonObject.get("c"), (Class) this.packetRegistry.get(asString));
    }
}
