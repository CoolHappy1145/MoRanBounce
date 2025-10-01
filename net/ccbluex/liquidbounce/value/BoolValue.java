package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0016\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/value/BoolValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "", PropertyDescriptor.VALUE, "(Ljava/lang/String;Z)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/value/BoolValue.class */
public class BoolValue extends Value {
    @Override // net.ccbluex.liquidbounce.value.Value
    /* renamed from: toJson, reason: collision with other method in class */
    public JsonElement mo1758toJson() {
        return toJson();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BoolValue(@NotNull String name, boolean z) {
        super(name, Boolean.valueOf(z));
        Intrinsics.checkParameterIsNotNull(name, "name");
    }

    @NotNull
    public JsonPrimitive toJson() {
        return new JsonPrimitive((Boolean) getValue());
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (element.isJsonPrimitive()) {
            setValue(Boolean.valueOf(element.getAsBoolean() || StringsKt.equals(element.getAsString(), "true", true)));
        }
    }
}
