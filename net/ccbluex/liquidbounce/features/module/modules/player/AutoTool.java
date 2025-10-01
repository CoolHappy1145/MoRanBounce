package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t\u00a8\u0006\n"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoTool;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "switchSlot", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoTool", description = "Automatically selects the best tool in your inventory to mine a block.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/AutoTool.class */
public final class AutoTool extends Module {
    @EventTarget
    public final void onClick(@NotNull ClickBlockEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos clickedBlock = event.getClickedBlock();
        if (clickedBlock != null) {
            switchSlot(clickedBlock);
        }
    }

    public final void switchSlot(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        float f = 1.0f;
        int i = -1;
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        IIBlockState blockState = theWorld.getBlockState(blockPos);
        for (int i2 = 0; i2 <= 8; i2++) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            IItemStack stackInSlot = thePlayer.getInventory().getStackInSlot(i2);
            if (stackInSlot != null) {
                float strVsBlock = stackInSlot.getStrVsBlock(blockState);
                if (strVsBlock > f) {
                    f = strVsBlock;
                    i = i2;
                }
            }
        }
        if (i != -1) {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            thePlayer2.getInventory().setCurrentItem(i);
        }
    }
}
