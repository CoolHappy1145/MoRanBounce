package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0016\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/value/FontValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "valueName", "", PropertyDescriptor.VALUE, "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/value/FontValue.class */
public final class FontValue extends Value {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FontValue(@NotNull String valueName, @NotNull IFontRenderer value) {
        super(valueName, value);
        Intrinsics.checkParameterIsNotNull(valueName, "valueName");
        Intrinsics.checkParameterIsNotNull(value, "value");
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    @Nullable
    /* renamed from: toJson */
    public JsonElement mo1758toJson() {
        Fonts.FontInfo fontDetails = Fonts.getFontDetails((IFontRenderer) getValue());
        if (fontDetails == null) {
            return null;
        }
        JsonElement jsonObject = new JsonObject();
        jsonObject.addProperty("fontName", fontDetails.getName());
        jsonObject.addProperty("fontSize", Integer.valueOf(fontDetails.getFontSize()));
        return jsonObject;
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    public void fromJson(@NotNull JsonElement element) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (element.isJsonObject()) {
            JsonObject asJsonObject = element.getAsJsonObject();
            JsonElement jsonElement = asJsonObject.get("fontName");
            Intrinsics.checkExpressionValueIsNotNull(jsonElement, "valueObject[\"fontName\"]");
            String asString = jsonElement.getAsString();
            JsonElement jsonElement2 = asJsonObject.get("fontSize");
            Intrinsics.checkExpressionValueIsNotNull(jsonElement2, "valueObject[\"fontSize\"]");
            IFontRenderer fontRenderer = Fonts.getFontRenderer(asString, jsonElement2.getAsInt());
            Intrinsics.checkExpressionValueIsNotNull(fontRenderer, "Fonts.getFontRenderer(va\u2026Object[\"fontSize\"].asInt)");
            setValue(fontRenderer);
        }
    }
}
