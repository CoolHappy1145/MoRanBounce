package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0016\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/value/TextValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", PropertyDescriptor.VALUE, "(Ljava/lang/String;Ljava/lang/String;)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/value/TextValue.class */
public class TextValue extends Value {
    @Override // net.ccbluex.liquidbounce.value.Value
    /* renamed from: toJson */
    public JsonElement mo1758toJson() {
        return toJson();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextValue(@NotNull String name, @NotNull String value) {
        super(name, value);
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(value, "value");
    }

    @NotNull
    public JsonPrimitive toJson() {
        return new JsonPrimitive((String) getValue());
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (element.isJsonPrimitive()) {
            String asString = element.getAsString();
            Intrinsics.checkExpressionValueIsNotNull(asString, "element.asString");
            setValue(asString);
        }
    }
}
