package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Aimbot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "centerValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "clickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "fovValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "jitterValue", "lockValue", "onClickValue", "rangeValue", "turnSpeedValue", "onStrafe", "", "event", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Aimbot", description = "Automatically faces selected entities around you.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/Aimbot.class */
public final class Aimbot extends Module {
    private final FloatValue rangeValue = new FloatValue("Range", 4.4f, 1.0f, 8.0f);
    private final FloatValue turnSpeedValue = new FloatValue("TurnSpeed", 2.0f, 1.0f, 180.0f);
    private final FloatValue fovValue = new FloatValue("FOV", 180.0f, 1.0f, 180.0f);
    private final BoolValue centerValue = new BoolValue("Center", false);
    private final BoolValue lockValue = new BoolValue("Lock", true);
    private final BoolValue onClickValue = new BoolValue("OnClick", false);
    private final BoolValue jitterValue = new BoolValue("Jitter", false);
    private final MSTimer clickTimer = new MSTimer();

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        IEntityPlayerSP thePlayer;
        Object obj;
        Rotation rotation;
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack().isKeyDown()) {
            this.clickTimer.reset();
        }
        if ((((Boolean) this.onClickValue.get()).booleanValue() && this.clickTimer.hasTimePassed(500L)) || (thePlayer = MinecraftInstance.f157mc.getThePlayer()) == null) {
            return;
        }
        float fFloatValue = ((Number) this.rangeValue.get()).floatValue();
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        Collection loadedEntityList = theWorld.getLoadedEntityList();
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : loadedEntityList) {
            IEntity iEntity = (IEntity) obj2;
            if (EntityUtils.isSelected(iEntity, true) && thePlayer.canEntityBeSeen(iEntity) && PlayerExtensionKt.getDistanceToEntityBox(thePlayer, iEntity) <= ((double) fFloatValue) && RotationUtils.getRotationDifference(iEntity) <= ((Number) this.fovValue.get()).doubleValue()) {
                arrayList.add(obj2);
            }
        }
        Iterator it = arrayList.iterator();
        if (it.hasNext()) {
            Object next = it.next();
            if (it.hasNext()) {
                double rotationDifference = RotationUtils.getRotationDifference((IEntity) next);
                do {
                    Object next2 = it.next();
                    double rotationDifference2 = RotationUtils.getRotationDifference((IEntity) next2);
                    if (Double.compare(rotationDifference, rotationDifference2) > 0) {
                        next = next2;
                        rotationDifference = rotationDifference2;
                    }
                } while (it.hasNext());
                obj = next;
            } else {
                obj = next;
            }
        } else {
            obj = null;
        }
        IEntity iEntity2 = (IEntity) obj;
        if (iEntity2 == null) {
            return;
        }
        if (!((Boolean) this.lockValue.get()).booleanValue() && RotationUtils.isFaced(iEntity2, fFloatValue)) {
            return;
        }
        Rotation rotation2 = new Rotation(thePlayer.getRotationYaw(), thePlayer.getRotationPitch());
        if (((Boolean) this.centerValue.get()).booleanValue()) {
            rotation = RotationUtils.toRotation(RotationUtils.getCenter(iEntity2.getEntityBoundingBox()), true);
        } else {
            rotation = RotationUtils.searchCenter(iEntity2.getEntityBoundingBox(), false, false, true, false, fFloatValue).getRotation();
        }
        Rotation rotationLimitAngleChange = RotationUtils.limitAngleChange(rotation2, rotation, (float) (((Number) this.turnSpeedValue.get()).doubleValue() + Math.random()));
        Intrinsics.checkExpressionValueIsNotNull(rotationLimitAngleChange, "RotationUtils.limitAngle\u2026om()).toFloat()\n        )");
        rotationLimitAngleChange.toPlayer(thePlayer);
        if (((Boolean) this.jitterValue.get()).booleanValue()) {
            boolean zNextBoolean = Random.Default.nextBoolean();
            boolean zNextBoolean2 = Random.Default.nextBoolean();
            boolean zNextBoolean3 = Random.Default.nextBoolean();
            boolean zNextBoolean4 = Random.Default.nextBoolean();
            if (zNextBoolean) {
                thePlayer.setRotationYaw(thePlayer.getRotationYaw() + (zNextBoolean3 ? -RandomUtils.INSTANCE.nextFloat(0.0f, 1.0f) : RandomUtils.INSTANCE.nextFloat(0.0f, 1.0f)));
            }
            if (zNextBoolean2) {
                thePlayer.setRotationPitch(thePlayer.getRotationPitch() + (zNextBoolean4 ? -RandomUtils.INSTANCE.nextFloat(0.0f, 1.0f) : RandomUtils.INSTANCE.nextFloat(0.0f, 1.0f)));
                if (thePlayer.getRotationPitch() > 90.0f) {
                    thePlayer.setRotationPitch(90.0f);
                } else if (thePlayer.getRotationPitch() < -90.0f) {
                    thePlayer.setRotationPitch(-90.0f);
                }
            }
        }
    }
}
