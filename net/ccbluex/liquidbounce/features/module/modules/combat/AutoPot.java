package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Iterator;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemPotion;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdH\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fH\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001c"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoPot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "groundDistanceValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "healthValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "openInventoryValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "potion", "", "simulateInventory", "tag", "", "getTag", "()Ljava/lang/String;", "findPotion", "startSlot", "endSlot", "onMotion", "", "motionEvent", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoPot", description = "Automatically throws healing potions.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoPot.class */
public final class AutoPot extends Module {
    private final FloatValue healthValue = new FloatValue("Health", 15.0f, 1.0f, 20.0f);
    private final IntegerValue delayValue = new IntegerValue("Delay", SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD, 1000);
    private final BoolValue openInventoryValue = new BoolValue("OpenInv", false);
    private final BoolValue simulateInventory = new BoolValue("SimulateInventory", true);
    private final FloatValue groundDistanceValue = new FloatValue("GroundDistance", 2.0f, 0.0f, 5.0f);
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Normal", "Jump", "Port"}, "Normal");
    private final MSTimer msTimer = new MSTimer();
    private int potion = -1;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoPot$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[EventState.values().length];

        static {
            $EnumSwitchMapping$0[EventState.PRE.ordinal()] = 1;
            $EnumSwitchMapping$0[EventState.POST.ordinal()] = 2;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @EventTarget
    public final void onMotion(@NotNull MotionEvent motionEvent) {
        IEntityPlayerSP thePlayer;
        Intrinsics.checkParameterIsNotNull(motionEvent, "motionEvent");
        if (!this.msTimer.hasTimePassed(((Number) this.delayValue.get()).intValue()) || MinecraftInstance.f157mc.getPlayerController().isInCreativeMode() || (thePlayer = MinecraftInstance.f157mc.getThePlayer()) == null) {
            return;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[motionEvent.getEventState().ordinal()]) {
            case 1:
                int iFindPotion = findPotion(36, 45);
                if (thePlayer.getHealth() <= ((Number) this.healthValue.get()).floatValue() && iFindPotion != -1) {
                    if (thePlayer.getOnGround()) {
                        String str = (String) this.modeValue.get();
                        if (str != null) {
                            String lowerCase = str.toLowerCase();
                            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                            switch (lowerCase.hashCode()) {
                                case 3273774:
                                    if (lowerCase.equals("jump")) {
                                        thePlayer.jump();
                                        break;
                                    }
                                    break;
                                case 3446913:
                                    if (lowerCase.equals("port")) {
                                        thePlayer.moveEntity(0.0d, 0.42d, 0.0d);
                                        break;
                                    }
                                    break;
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                    }
                    FallingPlayer.CollisionResult collisionResultFindCollision = new FallingPlayer(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), thePlayer.getMotionX(), thePlayer.getMotionY(), thePlayer.getMotionZ(), thePlayer.getRotationYaw(), thePlayer.getMoveStrafing(), thePlayer.getMoveForward()).findCollision(20);
                    if (thePlayer.getPosY() - ((collisionResultFindCollision != null ? collisionResultFindCollision.getPos() : null) != null ? r23.getY() : 0) >= ((Number) this.groundDistanceValue.get()).doubleValue()) {
                        return;
                    }
                    this.potion = iFindPotion;
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(this.potion - 36));
                    if (thePlayer.getRotationPitch() <= 80.0f) {
                        RotationUtils.setTargetRotation(new Rotation(thePlayer.getRotationYaw(), RandomUtils.INSTANCE.nextFloat(80.0f, 90.0f)));
                        return;
                    }
                    return;
                }
                int iFindPotion2 = findPotion(9, 36);
                if (iFindPotion2 != -1 && InventoryUtils.hasSpaceHotbar()) {
                    if (((Boolean) this.openInventoryValue.get()).booleanValue() && !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.f157mc.getCurrentScreen())) {
                        return;
                    }
                    boolean z = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.f157mc.getCurrentScreen()) && ((Boolean) this.simulateInventory.get()).booleanValue();
                    if (z) {
                        IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                        IClassProvider classProvider = WrapperImpl.INSTANCE.getClassProvider();
                        IEntityPlayerSP thePlayer2 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();
                        if (thePlayer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        netHandler.addToSendQueue(classProvider.createCPacketEntityAction(thePlayer2, ICPacketEntityAction.WAction.OPEN_INVENTORY));
                    }
                    MinecraftInstance.f157mc.getPlayerController().windowClick(0, iFindPotion2, 0, 1, thePlayer);
                    if (z) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketCloseWindow());
                    }
                    this.msTimer.reset();
                    return;
                }
                return;
            case 2:
                if (this.potion >= 0 && RotationUtils.serverRotation.getPitch() >= 75.0f) {
                    if (thePlayer.getInventory().getStackInSlot(this.potion) != null) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(WrapperImpl.INSTANCE.getClassProvider().createCPacketTryUseItem(WEnumHand.MAIN_HAND));
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
                        this.msTimer.reset();
                    }
                    this.potion = -1;
                    return;
                }
                return;
            default:
                return;
        }
    }

    private final int findPotion(int i, int i2) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        for (int i3 = i; i3 < i2; i3++) {
            IItemStack stack = thePlayer.getInventoryContainer().getSlot(i3).getStack();
            if (stack != null && MinecraftInstance.classProvider.isItemPotion(stack.getItem()) && stack.isSplash()) {
                IItem item = stack.getItem();
                if (item == null) {
                    Intrinsics.throwNpe();
                }
                IItemPotion iItemPotionAsItemPotion = item.asItemPotion();
                Iterator it = iItemPotionAsItemPotion.getEffects(stack).iterator();
                while (it.hasNext()) {
                    if (((IPotionEffect) it.next()).getPotionID() == MinecraftInstance.classProvider.getPotionEnum(PotionType.HEAL).getId()) {
                        return i3;
                    }
                }
                if (thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.REGENERATION))) {
                    continue;
                } else {
                    Iterator it2 = iItemPotionAsItemPotion.getEffects(stack).iterator();
                    while (it2.hasNext()) {
                        if (((IPotionEffect) it2.next()).getPotionID() == MinecraftInstance.classProvider.getPotionEnum(PotionType.REGENERATION).getId()) {
                            return i3;
                        }
                    }
                }
            }
        }
        return -1;
    }

    @Nullable
    public String getTag() {
        return String.valueOf(((Number) this.healthValue.get()).floatValue());
    }
}
