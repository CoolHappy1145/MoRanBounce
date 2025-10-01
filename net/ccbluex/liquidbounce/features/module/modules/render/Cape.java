package net.ccbluex.liquidbounce.features.module.modules.render;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\ufffd\ufffd2\u00020\u0001:\u0001\u000eB\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Cape;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "styleValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getStyleValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "getCapeLocation", "Lnet/minecraft/util/ResourceLocation;", PropertyDescriptor.VALUE, "CapeStyle", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Cape", description = "LiquidBounce+ capes.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/Cape.class */
public final class Cape extends Module {

    @NotNull
    private final ListValue styleValue = new ListValue("Style", new String[]{"Dark", "Astolfo", "Astolfo2", "Sunny", "Staff", "Target", "Wyy", "PowerX", "Azrael", "Flux", "LiquidBounce", "Light", "Novoline", "YuZhiWen", "Special1", "Special2"}, "Dark");

    @NotNull
    public final ListValue getStyleValue() {
        return this.styleValue;
    }

    @NotNull
    public final ResourceLocation getCapeLocation(@NotNull String value) {
        ResourceLocation location;
        Intrinsics.checkParameterIsNotNull(value, "value");
        try {
            String upperCase = value.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
            location = CapeStyle.valueOf(upperCase).getLocation();
        } catch (IllegalArgumentException unused) {
            location = CapeStyle.DARK.getLocation();
        }
        return location;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0014\b\u0086\u0001\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016\u00a8\u0006\u0017"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Cape$CapeStyle;", "", "location", "Lnet/minecraft/util/ResourceLocation;", "(Ljava/lang/String;ILnet/minecraft/util/ResourceLocation;)V", "getLocation", "()Lnet/minecraft/util/ResourceLocation;", "DARK", "ASTOLFO", "ASTOLFO2", "STAFF", "YUZHIWEN", "LIGHT", "SUNNY", "WYY", "POWERX", "AZRAEL", "TARGET", "FLUX", "LIQUIDBOUNCE", "NOVOLINE", "SPECIAL1", "SPECIAL2", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/Cape$CapeStyle.class */
    public enum CapeStyle {
        DARK(new ResourceLocation("liquidbounce/capes/dark.png")),
        ASTOLFO(new ResourceLocation("liquidbounce/capes/astolfo.png")),
        ASTOLFO2(new ResourceLocation("liquidbounce/capes/astolfo2.png")),
        STAFF(new ResourceLocation("liquidbounce/capes/staff.png")),
        YUZHIWEN(new ResourceLocation("liquidbounce/capes/Yuzhiwen.png")),
        LIGHT(new ResourceLocation("liquidbounce/capes/light.png")),
        SUNNY(new ResourceLocation("liquidbounce/capes/Sunny.png")),
        WYY(new ResourceLocation("liquidbounce/capes/Wyy.png")),
        POWERX(new ResourceLocation("liquidbounce/capes/PowerX.png")),
        AZRAEL(new ResourceLocation("liquidbounce/capes/azrael.png")),
        TARGET(new ResourceLocation("liquidbounce/capes/Target.png")),
        FLUX(new ResourceLocation("liquidbounce/capes/Flux.png")),
        LIQUIDBOUNCE(new ResourceLocation("liquidbounce/capes/LiquidBounce.png")),
        NOVOLINE(new ResourceLocation("liquidbounce/capes/Novoline.png")),
        SPECIAL1(new ResourceLocation("liquidbounce/capes/special1.png")),
        SPECIAL2(new ResourceLocation("liquidbounce/capes/special2.png"));


        @NotNull
        private final ResourceLocation location;

        @NotNull
        public final ResourceLocation getLocation() {
            return this.location;
        }

        CapeStyle(ResourceLocation resourceLocation) {
            this.location = resourceLocation;
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.styleValue.get();
    }
}
