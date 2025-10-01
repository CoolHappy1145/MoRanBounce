package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007\u00a8\u0006\b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/AutoBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoBreak", description = "Automatically breaks the block you are looking at.", category = ModuleCategory.WORLD)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/AutoBreak.class */
public final class AutoBreak extends Module {
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.f157mc.getObjectMouseOver() != null) {
            IMovingObjectPosition objectMouseOver = MinecraftInstance.f157mc.getObjectMouseOver();
            if (objectMouseOver == null) {
                Intrinsics.throwNpe();
            }
            if (objectMouseOver.getBlockPos() == null || MinecraftInstance.f157mc.getTheWorld() == null) {
                return;
            }
            IKeyBinding keyBindAttack = MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack();
            if (MinecraftInstance.f157mc.getTheWorld() == null) {
                Intrinsics.throwNpe();
            }
            IMovingObjectPosition objectMouseOver2 = MinecraftInstance.f157mc.getObjectMouseOver();
            if (objectMouseOver2 == null) {
                Intrinsics.throwNpe();
            }
            if (objectMouseOver2.getBlockPos() == null) {
                Intrinsics.throwNpe();
            }
            keyBindAttack.setPressed(!Intrinsics.areEqual(r1.getBlockState(r2).getBlock(), MinecraftInstance.classProvider.getBlockEnum(BlockType.AIR)));
        }
    }

    public void onDisable() {
        if (!MinecraftInstance.f157mc.getGameSettings().isKeyDown(MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack())) {
            MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack().setPressed(false);
        }
    }
}
