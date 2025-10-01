package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0012H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/FastClimb;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "speedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "FastClimb", description = "Allows you to climb up ladders and vines faster.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/FastClimb.class */
public final class FastClimb extends Module {

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Clip", "AAC3.0.0", "AAC3.0.5", "SAAC3.1.2", "AAC3.1.2"}, "Vanilla");
    private final FloatValue speedValue = new FloatValue("Speed", 0.2872f, 0.01f, 5.0f);

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String str = (String) this.modeValue.get();
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (StringsKt.equals(str, "Vanilla", true) && thePlayer.isCollidedHorizontally() && thePlayer.isOnLadder()) {
                event.setY(((Number) this.speedValue.get()).floatValue());
                thePlayer.setMotionY(0.0d);
                return;
            }
            if (StringsKt.equals(str, "AAC3.0.0", true) && thePlayer.isCollidedHorizontally()) {
                double d = 0.0d;
                double d2 = 0.0d;
                IEnumFacing horizontalFacing = thePlayer.getHorizontalFacing();
                if (horizontalFacing.isNorth()) {
                    d2 = -0.99d;
                } else if (horizontalFacing.isEast()) {
                    d = 0.99d;
                } else if (horizontalFacing.isSouth()) {
                    d2 = 0.99d;
                } else if (horizontalFacing.isWest()) {
                    d = -0.99d;
                }
                IBlock block = BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX() + d, thePlayer.getPosY(), thePlayer.getPosZ() + d2));
                if (MinecraftInstance.classProvider.isBlockLadder(block) || MinecraftInstance.classProvider.isBlockVine(block)) {
                    event.setY(0.5d);
                    thePlayer.setMotionY(0.0d);
                    return;
                }
                return;
            }
            if (StringsKt.equals(str, "AAC3.0.5", true) && MinecraftInstance.f157mc.getGameSettings().getKeyBindForward().isKeyDown() && BlockUtils.collideBlockIntersects(thePlayer.getEntityBoundingBox(), new Function1() { // from class: net.ccbluex.liquidbounce.features.module.modules.movement.FastClimb.onMove.1
                @Override // kotlin.jvm.functions.Function1
                public Object invoke(Object obj) {
                    return Boolean.valueOf(invoke((IBlock) obj));
                }

                public final boolean invoke(@Nullable IBlock iBlock) {
                    return MinecraftInstance.classProvider.isBlockLadder(iBlock) || MinecraftInstance.classProvider.isBlockVine(iBlock);
                }
            })) {
                event.setX(0.0d);
                event.setY(0.5d);
                event.setZ(0.0d);
                thePlayer.setMotionX(0.0d);
                thePlayer.setMotionY(0.0d);
                thePlayer.setMotionZ(0.0d);
                return;
            }
            if (StringsKt.equals(str, "SAAC3.1.2", true) && thePlayer.isCollidedHorizontally() && thePlayer.isOnLadder()) {
                event.setY(0.1649d);
                thePlayer.setMotionY(0.0d);
                return;
            }
            if (StringsKt.equals(str, "AAC3.1.2", true) && thePlayer.isCollidedHorizontally() && thePlayer.isOnLadder()) {
                event.setY(0.1699d);
                thePlayer.setMotionY(0.0d);
                return;
            }
            if (StringsKt.equals(str, "Clip", true) && thePlayer.isOnLadder() && MinecraftInstance.f157mc.getGameSettings().getKeyBindForward().isKeyDown()) {
                int posY = (int) thePlayer.getPosY();
                int posY2 = ((int) thePlayer.getPosY()) + 8;
                if (posY <= posY2) {
                    while (MinecraftInstance.classProvider.isBlockLadder(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), posY, thePlayer.getPosZ())))) {
                        thePlayer.setPosition(thePlayer.getPosX(), posY, thePlayer.getPosZ());
                        if (posY == posY2) {
                            return;
                        } else {
                            posY++;
                        }
                    }
                    double d3 = 0.0d;
                    double d4 = 0.0d;
                    IEnumFacing horizontalFacing2 = thePlayer.getHorizontalFacing();
                    if (horizontalFacing2.isNorth()) {
                        d4 = -1.0d;
                    } else if (horizontalFacing2.isEast()) {
                        d3 = 1.0d;
                    } else if (horizontalFacing2.isSouth()) {
                        d4 = 1.0d;
                    } else if (horizontalFacing2.isWest()) {
                        d3 = -1.0d;
                    }
                    thePlayer.setPosition(thePlayer.getPosX() + d3, posY, thePlayer.getPosZ() + d4);
                }
            }
        }
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.f157mc.getThePlayer() != null) {
            if ((MinecraftInstance.classProvider.isBlockLadder(event.getBlock()) || MinecraftInstance.classProvider.isBlockVine(event.getBlock())) && StringsKt.equals((String) this.modeValue.get(), "AAC3.0.5", true)) {
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer.isOnLadder()) {
                    event.setBoundingBox((IAxisAlignedBB) null);
                }
            }
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
