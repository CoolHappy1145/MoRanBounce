package net.ccbluex.liquidbounce.features.module.modules.movement.speeds;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u000bH\u0016J\b\u0010\r\u001a\u00020\u000bH&J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u000bH\u0016J\b\u0010\u0012\u001a\u00020\u000bH&R\u0011\u0010\u0005\u001a\u00020\u00068F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "modeName", "", "(Ljava/lang/String;)V", "isActive", "", "()Z", "getModeName", "()Ljava/lang/String;", "onDisable", "", "onEnable", "onMotion", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onTick", "onUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode.class */
public abstract class SpeedMode extends MinecraftInstance {

    @NotNull
    private final String modeName;

    public abstract void onMotion();

    public abstract void onUpdate();

    public abstract void onMove(@NotNull MoveEvent moveEvent);

    @NotNull
    public final String getModeName() {
        return this.modeName;
    }

    public SpeedMode(@NotNull String modeName) {
        Intrinsics.checkParameterIsNotNull(modeName, "modeName");
        this.modeName = modeName;
    }

    public final boolean isActive() {
        Speed speed = (Speed) LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
        if (speed != null) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (!thePlayer.isSneaking() && speed.getState() && Intrinsics.areEqual((String) speed.getModeValue().get(), this.modeName)) {
                return true;
            }
        }
        return false;
    }
}
