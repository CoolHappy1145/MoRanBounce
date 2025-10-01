package net.ccbluex.liquidbounce.value;

import com.google.gson.JsonElement;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00028\ufffd\ufffdH\u0016\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H&J\u000b\u0010\u0013\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\nJ\u001d\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00028\ufffd\ufffd2\u0006\u0010\u0016\u001a\u00028\ufffd\ufffdH\u0014\u00a2\u0006\u0002\u0010\u0017J\u001d\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00028\ufffd\ufffd2\u0006\u0010\u0016\u001a\u00028\ufffd\ufffdH\u0014\u00a2\u0006\u0002\u0010\u0017J\u0013\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\fJ\n\u0010\u001a\u001a\u0004\u0018\u00010\u0012H&R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\u0005\u001a\u00028\ufffd\ufffdX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\r\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u001b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/value/Value;", "T", "", "name", "", PropertyDescriptor.VALUE, "(Ljava/lang/String;Ljava/lang/Object;)V", "getName", "()Ljava/lang/String;", "getValue", "()Ljava/lang/Object;", "setValue", "(Ljava/lang/Object;)V", Constants.OBJECT_DESC, "changeValue", "", "fromJson", "element", "Lcom/google/gson/JsonElement;", PropertyDescriptor.GET, "onChange", "oldValue", "newValue", "(Ljava/lang/Object;Ljava/lang/Object;)V", "onChanged", PropertyDescriptor.SET, "toJson", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/value/Value.class */
public abstract class Value {

    @NotNull
    private final String name;
    private Object value;

    @Nullable
    /* renamed from: toJson */
    public abstract JsonElement mo1758toJson();

    public abstract void fromJson(@NotNull JsonElement jsonElement);

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final Object getValue() {
        return this.value;
    }

    public final void setValue(Object obj) {
        this.value = obj;
    }

    public Value(@NotNull String name, Object obj) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.name = name;
        this.value = obj;
    }

    public final void set(Object obj) {
        if (Intrinsics.areEqual(obj, this.value)) {
            return;
        }
        Object obj2 = get();
        try {
            changeValue(obj);
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
        } catch (Exception e) {
            ClientUtils.getLogger().error("[ValueSystem (" + this.name + ")]: " + e.getClass().getName() + " (" + e.getMessage() + ") [" + obj2 + " >> " + obj + ']');
        }
    }

    public final Object get() {
        return this.value;
    }

    public void changeValue(Object obj) {
        this.value = obj;
    }
}
