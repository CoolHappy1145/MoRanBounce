package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/FastStairs;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canJump", "", "longJumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "walkingDown", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "FastStairs", description = "Allows you to climb up stairs faster.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/FastStairs.class */
public final class FastStairs extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Step", "NCP", "AAC3.1.0", "AAC3.3.6", "AAC3.3.13"}, "NCP");
    private final BoolValue longJumpValue = new BoolValue("LongJump", false);
    private boolean canJump;
    private boolean walkingDown;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        double d;
        double d2;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || !MovementUtils.isMoving()) {
            return;
        }
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Speed.class);
        if (module == null) {
            Intrinsics.throwNpe();
        }
        if (module.getState()) {
            return;
        }
        if (thePlayer.getFallDistance() > 0.0f && !this.walkingDown) {
            this.walkingDown = true;
        } else if (thePlayer.getPosY() > thePlayer.getPrevChasingPosY()) {
            this.walkingDown = false;
        }
        String str = (String) this.modeValue.get();
        if (!thePlayer.getOnGround()) {
            return;
        }
        WBlockPos wBlockPos = new WBlockPos(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ());
        if (MinecraftInstance.classProvider.isBlockStairs(BlockUtils.getBlock(wBlockPos)) && !this.walkingDown) {
            thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.5d, thePlayer.getPosZ());
            if (StringsKt.equals(str, "NCP", true)) {
                d2 = 1.4d;
            } else if (StringsKt.equals(str, "AAC3.1.0", true)) {
                d2 = 1.5d;
            } else {
                d2 = StringsKt.equals(str, "AAC3.3.13", true) ? 1.2d : 1.0d;
            }
            double d3 = d2;
            thePlayer.setMotionX(thePlayer.getMotionX() * d3);
            thePlayer.setMotionZ(thePlayer.getMotionZ() * d3);
        }
        if (MinecraftInstance.classProvider.isBlockStairs(BlockUtils.getBlock(wBlockPos.down()))) {
            if (this.walkingDown) {
                if (StringsKt.equals(str, "NCP", true)) {
                    thePlayer.setMotionY(-1.0d);
                    return;
                } else {
                    if (StringsKt.equals(str, "AAC3.3.13", true)) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - 0.014d);
                        return;
                    }
                    return;
                }
            }
            if (StringsKt.equals(str, "NCP", true) || StringsKt.equals(str, "AAC3.1.0", true)) {
                d = 1.3d;
            } else if (StringsKt.equals(str, "AAC3.3.6", true)) {
                d = 1.48d;
            } else {
                d = StringsKt.equals(str, "AAC3.3.13", true) ? 1.52d : 1.3d;
            }
            double d4 = d;
            thePlayer.setMotionX(thePlayer.getMotionX() * d4);
            thePlayer.setMotionZ(thePlayer.getMotionZ() * d4);
            this.canJump = true;
            return;
        }
        if (StringsKt.startsWith(str, "AAC", true) && this.canJump) {
            if (((Boolean) this.longJumpValue.get()).booleanValue()) {
                thePlayer.jump();
                thePlayer.setMotionX(thePlayer.getMotionX() * 1.35d);
                thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.35d);
            }
            this.canJump = false;
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
