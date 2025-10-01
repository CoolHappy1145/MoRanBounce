package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.Skid.Translate;
import org.jetbrains.annotations.NotNull;

/* compiled from: Value.kt */
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0016\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0019"}, m27d2 = {"Lnet/ccbluex/liquidbounce/value/FloatValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "", PropertyDescriptor.VALUE, "minimum", "maximum", "(Ljava/lang/String;FFF)V", "getMaximum", "()F", "getMinimum", "translate", "Lnet/ccbluex/liquidbounce/utils/Skid/Translate;", "getTranslate", "()Lnet/ccbluex/liquidbounce/utils/Skid/Translate;", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", PropertyDescriptor.SET, "newValue", "", "toJson", "Lcom/google/gson/JsonPrimitive;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/value/FloatValue.class */
public class FloatValue extends Value<Float> {

    @NotNull
    private final Translate translate;
    private final float minimum;
    private final float maximum;

    public final float getMinimum() {
        return this.minimum;
    }

    public final float getMaximum() {
        return this.maximum;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FloatValue(@NotNull String name, float value, float minimum, float maximum) {
        super(name, Float.valueOf(value));
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.minimum = minimum;
        this.maximum = maximum;
        this.translate = new Translate(0.0f, 0.0f);
    }

    public /* synthetic */ FloatValue(String str, float f, float f2, float f3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, f, (i & 4) != 0 ? 0.0f : f2, (i & 8) != 0 ? FloatCompanionObject.INSTANCE.getMAX_VALUE() : f3);
    }

    @NotNull
    public final Translate getTranslate() {
        return this.translate;
    }

    public final void set(@NotNull Number newValue) {
        Intrinsics.checkParameterIsNotNull(newValue, "newValue");
        set((Object) Float.valueOf(newValue.floatValue()));
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    @NotNull
    /* renamed from: toJson, reason: merged with bridge method [inline-methods] */
    public JsonPrimitive mo1758toJson() {
        return new JsonPrimitive((Number) getValue());
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (element.isJsonPrimitive()) {
            setValue(Float.valueOf(element.getAsFloat()));
        }
    }
}
