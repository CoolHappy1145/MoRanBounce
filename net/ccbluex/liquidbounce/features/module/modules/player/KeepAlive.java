package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/KeepAlive;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "runOnce", "", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "KeepAlive", description = "Tries to prevent you from dying.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/KeepAlive.class */
public final class KeepAlive extends Module {

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"/heal", "Soup"}, "/heal");
    private boolean runOnce;

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        int iFindItem;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (thePlayer.isDead() || thePlayer.getHealth() <= 0.0f) {
                if (this.runOnce) {
                    return;
                }
                String str = (String) this.modeValue.get();
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case 3536375:
                        if (lowerCase.equals("soup") && (iFindItem = InventoryUtils.findItem(36, 45, MinecraftInstance.classProvider.getItemEnum(ItemType.MUSHROOM_STEW))) != -1) {
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(iFindItem - 36));
                            IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                            thePlayer.getInventory().getStackInSlot(iFindItem);
                            netHandler.addToSendQueue(WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(WEnumHand.MAIN_HAND));
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
                            break;
                        }
                        break;
                    case 46603927:
                        if (lowerCase.equals("/heal")) {
                            thePlayer.sendChatMessage("/heal");
                            break;
                        }
                        break;
                }
                this.runOnce = true;
                return;
            }
            this.runOnce = false;
        }
    }
}
