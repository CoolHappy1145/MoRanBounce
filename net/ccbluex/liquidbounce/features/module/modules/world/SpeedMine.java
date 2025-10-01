package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.INetworkManager;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dH\u0003R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/SpeedMine;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getBlockPos", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "setBlockPos", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;)V", "breakSpeedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "bzs", "", "bzx", "", "facing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "e", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "SpeedMine", category = ModuleCategory.WORLD, description = "fag")
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/SpeedMine.class */
public final class SpeedMine extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Hypixel", "Packet", "NewPacket", "NewPacket2"}, "NewPacket");
    private final FloatValue breakSpeedValue = new FloatValue("BreakSpeed", 1.2f, 1.0f, 1.5f);
    private boolean bzs;
    private float bzx;

    @Nullable
    private WBlockPos blockPos;
    private IEnumFacing facing;

    @Nullable
    public final WBlockPos getBlockPos() {
        return this.blockPos;
    }

    public final void setBlockPos(@Nullable WBlockPos wBlockPos) {
        this.blockPos = wBlockPos;
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (Intrinsics.areEqual((String) this.modeValue.get(), "Hypixel") && MinecraftInstance.classProvider.isCPacketPlayerDigging(event.getPacket()) && !MinecraftInstance.f157mc.getPlayerController().extendedReach() && MinecraftInstance.f157mc.getPlayerController() != null) {
            ICPacketPlayerDigging iCPacketPlayerDiggingAsCPacketPlayerDigging = event.getPacket().asCPacketPlayerDigging();
            if (iCPacketPlayerDiggingAsCPacketPlayerDigging.asCPacketPlayerDigging().getAction() == ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK) {
                this.bzs = true;
                this.blockPos = iCPacketPlayerDiggingAsCPacketPlayerDigging.getPosition();
                this.facing = iCPacketPlayerDiggingAsCPacketPlayerDigging.getFacing();
                this.bzx = 0.0f;
                return;
            }
            if (iCPacketPlayerDiggingAsCPacketPlayerDigging.getAction() == ICPacketPlayerDigging.WAction.ABORT_DESTROY_BLOCK || iCPacketPlayerDiggingAsCPacketPlayerDigging.getAction() == ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK) {
                this.bzs = false;
                this.blockPos = (WBlockPos) null;
                this.facing = (IEnumFacing) null;
            }
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @EventTarget
    private final void onUpdate(UpdateEvent updateEvent) {
        String str = (String) this.modeValue.get();
        switch (str.hashCode()) {
            case -1911998296:
                if (str.equals("Packet")) {
                    float curBlockDamageMP = MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP();
                    if (curBlockDamageMP >= 0.1f && curBlockDamageMP <= 0.19f) {
                        IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
                        playerController.setCurBlockDamageMP(playerController.getCurBlockDamageMP() + 0.1f);
                    }
                    float curBlockDamageMP2 = MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP();
                    if (curBlockDamageMP2 >= 0.4f && curBlockDamageMP2 <= 0.49f) {
                        IPlayerControllerMP playerController2 = MinecraftInstance.f157mc.getPlayerController();
                        playerController2.setCurBlockDamageMP(playerController2.getCurBlockDamageMP() + 0.1f);
                    }
                    float curBlockDamageMP3 = MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP();
                    if (curBlockDamageMP3 >= 0.8f && curBlockDamageMP3 <= 0.89f) {
                        IPlayerControllerMP playerController3 = MinecraftInstance.f157mc.getPlayerController();
                        playerController3.setCurBlockDamageMP(playerController3.getCurBlockDamageMP() + 0.9f);
                        break;
                    }
                }
                break;
            case -1248403467:
                if (str.equals("Hypixel")) {
                    if (MinecraftInstance.f157mc.getPlayerController().extendedReach()) {
                        MinecraftInstance.f157mc.getPlayerController().setBlockHitDelay(0);
                        break;
                    } else if (this.bzs) {
                        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                        if (theWorld == null) {
                            Intrinsics.throwNpe();
                        }
                        WBlockPos wBlockPos = this.blockPos;
                        if (wBlockPos == null) {
                            Intrinsics.throwNpe();
                        }
                        IBlock block = theWorld.getBlockState(wBlockPos).getBlock();
                        float f = this.bzx;
                        if (MinecraftInstance.f157mc.getThePlayer() == null) {
                            Intrinsics.throwNpe();
                        }
                        IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                        if (theWorld2 == null) {
                            Intrinsics.throwNpe();
                        }
                        IWorldClient iWorldClient = theWorld2;
                        if (this.blockPos == null) {
                            Intrinsics.throwNpe();
                        }
                        this.bzx = f + ((float) (block.getPlayerRelativeBlockHardness(r3, iWorldClient, r5) * ((Number) this.breakSpeedValue.get()).doubleValue()));
                        if (this.bzx >= 1.0f) {
                            IWorldClient theWorld3 = MinecraftInstance.f157mc.getTheWorld();
                            if (theWorld3 == null) {
                                Intrinsics.throwNpe();
                            }
                            WBlockPos wBlockPos2 = this.blockPos;
                            Block block2 = Blocks.field_150350_a;
                            Intrinsics.checkExpressionValueIsNotNull(block2, "Blocks.AIR");
                            theWorld3.setBlockState(wBlockPos2, block2.func_176223_P(), 11);
                            INetworkManager networkManager = MinecraftInstance.f157mc.getNetHandler().getNetworkManager();
                            IClassProvider iClassProvider = MinecraftInstance.classProvider;
                            ICPacketPlayerDigging.WAction wAction = ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK;
                            WBlockPos wBlockPos3 = this.blockPos;
                            if (wBlockPos3 == null) {
                                Intrinsics.throwNpe();
                            }
                            IEnumFacing iEnumFacing = this.facing;
                            if (iEnumFacing == null) {
                                Intrinsics.throwNpe();
                            }
                            networkManager.sendPacket(iClassProvider.createCPacketPlayerDigging(wAction, wBlockPos3, iEnumFacing));
                            this.bzx = 0.0f;
                            this.bzs = false;
                            break;
                        }
                    }
                }
                break;
            case 1304381992:
                if (str.equals("NewPacket")) {
                    if (MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP() == 0.1f) {
                        IPlayerControllerMP playerController4 = MinecraftInstance.f157mc.getPlayerController();
                        playerController4.setCurBlockDamageMP(playerController4.getCurBlockDamageMP() + 0.1f);
                    }
                    if (MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP() == 0.4f) {
                        IPlayerControllerMP playerController5 = MinecraftInstance.f157mc.getPlayerController();
                        playerController5.setCurBlockDamageMP(playerController5.getCurBlockDamageMP() + 0.1f);
                    }
                    if (MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP() == 0.7f) {
                        IPlayerControllerMP playerController6 = MinecraftInstance.f157mc.getPlayerController();
                        playerController6.setCurBlockDamageMP(playerController6.getCurBlockDamageMP() + 0.1f);
                        break;
                    }
                }
                break;
            case 1781136138:
                if (str.equals("NewPacket2")) {
                    if (MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP() == 0.2f) {
                        IPlayerControllerMP playerController7 = MinecraftInstance.f157mc.getPlayerController();
                        playerController7.setCurBlockDamageMP(playerController7.getCurBlockDamageMP() + 0.1f);
                    }
                    if (MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP() == 0.4f) {
                        IPlayerControllerMP playerController8 = MinecraftInstance.f157mc.getPlayerController();
                        playerController8.setCurBlockDamageMP(playerController8.getCurBlockDamageMP() + 0.1f);
                    }
                    if (MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP() == 0.6f) {
                        IPlayerControllerMP playerController9 = MinecraftInstance.f157mc.getPlayerController();
                        playerController9.setCurBlockDamageMP(playerController9.getCurBlockDamageMP() + 0.1f);
                    }
                    if (MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP() == 0.8f) {
                        IPlayerControllerMP playerController10 = MinecraftInstance.f157mc.getPlayerController();
                        playerController10.setCurBlockDamageMP(playerController10.getCurBlockDamageMP() + 0.2f);
                        break;
                    }
                }
                break;
        }
    }
}
