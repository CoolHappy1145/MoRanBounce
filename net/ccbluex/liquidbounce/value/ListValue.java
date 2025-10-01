package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Arrays;
import java.util.function.Predicate;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0016\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B#\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u0002H\u0016J\u0013\u0010\u000f\u001a\u00020\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0002H\u0086\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/value/ListValue;", "Lnet/ccbluex/liquidbounce/value/Value;", "", "name", "values", "", PropertyDescriptor.VALUE, "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)V", "openList", "", "getValues", "()[Ljava/lang/String;", "[Ljava/lang/String;", "changeValue", "", "contains", "string", "fromJson", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/value/ListValue.class */
public class ListValue extends Value {

    @JvmField
    public boolean openList;

    @NotNull
    private final String[] values;

    @Override // net.ccbluex.liquidbounce.value.Value
    public void changeValue(Object obj) {
        changeValue((String) obj);
    }

    @Override // net.ccbluex.liquidbounce.value.Value
    /* renamed from: toJson */
    public JsonElement mo1758toJson() {
        return toJson();
    }

    @NotNull
    public final String[] getValues() {
        return this.values;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ListValue(@NotNull String name, @NotNull String[] values, @NotNull String value) {
        super(name, value);
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(values, "values");
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.values = values;
        setValue(value);
    }

    public final boolean contains(@Nullable String str) {
        return Arrays.stream(this.values).anyMatch(new Predicate(str) { // from class: net.ccbluex.liquidbounce.value.ListValue.contains.1
            final String $string;

            {
                this.$string = str;
            }

            @Override // java.util.function.Predicate
            public boolean test(Object obj) {
                return test((String) obj);
            }

            public final boolean test(@NotNull String s) {
                Intrinsics.checkParameterIsNotNull(s, "s");
                return StringsKt.equals(s, this.$string, true);
            }
        });
    }

    public void changeValue(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        for (String str : this.values) {
            if (StringsKt.equals(str, value, true)) {
                setValue(str);
                return;
            }
        }
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
            changeValue(asString);
        }
    }
}
