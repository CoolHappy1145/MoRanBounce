package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0012\u0010\u000f\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0010H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/ItemESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "ItemESP", description = "Allows you to see items through walls.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/ItemESP.class */
public final class ItemESP extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "ShaderOutline"}, "Box");
    private final IntegerValue colorRedValue = new IntegerValue("R", 0, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 255, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 0, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", true);

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent render3DEvent) {
        if (StringsKt.equals((String) this.modeValue.get(), "Box", true)) {
            Color colorRainbow = ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue());
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            for (IEntity iEntity : theWorld.getLoadedEntityList()) {
                if (MinecraftInstance.classProvider.isEntityItem(iEntity) || MinecraftInstance.classProvider.isEntityArrow(iEntity)) {
                    RenderUtils.drawEntityBox(iEntity, colorRainbow, true);
                }
            }
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (StringsKt.equals((String) this.modeValue.get(), "ShaderOutline", true)) {
            OutlineShader.OUTLINE_SHADER.startDraw(event.getPartialTicks());
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            for (IEntity iEntity : theWorld.getLoadedEntityList()) {
                if (MinecraftInstance.classProvider.isEntityItem(iEntity) || MinecraftInstance.classProvider.isEntityArrow(iEntity)) {
                    MinecraftInstance.f157mc.getRenderManager().renderEntityStatic(iEntity, event.getPartialTicks(), true);
                }
            }
            OutlineShader.OUTLINE_SHADER.stopDraw(((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue()), 1.0f, 1.0f);
        }
    }
}
