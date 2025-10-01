package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.Skid.Translate;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0016\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0019"}, m27d2 = {"Lnet/ccbluex/liquidbounce/value/IntegerValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "", PropertyDescriptor.VALUE, "minimum", "maximum", "(Ljava/lang/String;III)V", "getMaximum", "()I", "getMinimum", "translate", "Lnet/ccbluex/liquidbounce/utils/Skid/Translate;", "getTranslate", "()Lnet/ccbluex/liquidbounce/utils/Skid/Translate;", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", PropertyDescriptor.SET, "newValue", "", "toJson", "Lcom/google/gson/JsonPrimitive;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/value/IntegerValue.class */
public class IntegerValue extends Value {

    @NotNull
    private final Translate translate;
    private final int minimum;
    private final int maximum;

    @Override // net.ccbluex.liquidbounce.value.Value
    /* renamed from: toJson */
    public JsonElement mo1758toJson() {
        return toJson();
    }

    public final int getMinimum() {
        return this.minimum;
    }

    public final int getMaximum() {
        return this.maximum;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IntegerValue(@NotNull String name, int i, int i2, int i3) {
        super(name, Integer.valueOf(i));
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.minimum = i2;
        this.maximum = i3;
        this.translate = new Translate(0.0f, 0.0f);
    }

    public IntegerValue(String str, int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, (i4 & 4) != 0 ? 0 : i2, (i4 & 8) != 0 ? Integer.MAX_VALUE : i3);
    }

    @NotNull
    public final Translate getTranslate() {
        return this.translate;
    }

    public final void set(@NotNull Number newValue) {
        Intrinsics.checkParameterIsNotNull(newValue, "newValue");
        set((Object) Integer.valueOf(newValue.intValue()));
    }

    @NotNull
    public JsonPrimitive toJson() {
        return new JsonPrimitive((Number) getValue());
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (element.isJsonPrimitive()) {
            setValue(Integer.valueOf(element.getAsInt()));
        }
    }
}
