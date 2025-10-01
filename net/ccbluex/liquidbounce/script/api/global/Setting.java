package net.ccbluex.liquidbounce.script.api.global;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/script/api/global/Setting;", "", "()V", "block", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "settingInfo", "Ljdk/nashorn/api/scripting/JSObject;", "boolean", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "float", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "integer", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "list", "Lnet/ccbluex/liquidbounce/value/ListValue;", "text", "Lnet/ccbluex/liquidbounce/value/TextValue;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/api/global/Setting.class */
public final class Setting {
    public static final Setting INSTANCE = new Setting();

    private Setting() {
    }

    @JvmStatic
    @NotNull
    /* renamed from: boolean, reason: not valid java name */
    public static final BoolValue m1673boolean(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String str = (String) member;
        Object member2 = settingInfo.getMember("default");
        if (member2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Boolean");
        }
        return new BoolValue(str, ((Boolean) member2).booleanValue());
    }

    @JvmStatic
    @NotNull
    public static final IntegerValue integer(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String str = (String) member;
        Object member2 = settingInfo.getMember("default");
        if (member2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        int iIntValue = ((Number) member2).intValue();
        Object member3 = settingInfo.getMember("min");
        if (member3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        int iIntValue2 = ((Number) member3).intValue();
        Object member4 = settingInfo.getMember("max");
        if (member4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        return new IntegerValue(str, iIntValue, iIntValue2, ((Number) member4).intValue());
    }

    @JvmStatic
    @NotNull
    /* renamed from: float, reason: not valid java name */
    public static final FloatValue m1674float(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String str = (String) member;
        Object member2 = settingInfo.getMember("default");
        if (member2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        float fFloatValue = ((Number) member2).floatValue();
        Object member3 = settingInfo.getMember("min");
        if (member3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        float fFloatValue2 = ((Number) member3).floatValue();
        Object member4 = settingInfo.getMember("max");
        if (member4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        return new FloatValue(str, fFloatValue, fFloatValue2, ((Number) member4).floatValue());
    }

    @JvmStatic
    @NotNull
    public static final TextValue text(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String str = (String) member;
        Object member2 = settingInfo.getMember("default");
        if (member2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        return new TextValue(str, (String) member2);
    }

    @JvmStatic
    @NotNull
    public static final BlockValue block(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String str = (String) member;
        Object member2 = settingInfo.getMember("default");
        if (member2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Number");
        }
        return new BlockValue(str, ((Number) member2).intValue());
    }

    @JvmStatic
    @NotNull
    public static final ListValue list(@NotNull JSObject settingInfo) {
        Intrinsics.checkParameterIsNotNull(settingInfo, "settingInfo");
        Object member = settingInfo.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String str = (String) member;
        Object objConvert = ScriptUtils.convert(settingInfo.getMember("values"), String[].class);
        if (objConvert == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
        }
        String[] strArr = (String[]) objConvert;
        Object member2 = settingInfo.getMember("default");
        if (member2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        return new ListValue(str, strArr, (String) member2);
    }
}
