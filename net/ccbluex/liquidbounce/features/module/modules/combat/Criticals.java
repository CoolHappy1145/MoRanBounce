package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.concurrent.ThreadLocalRandom;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0007J\b\u0010#\u001a\u00020 H\u0016J\u0010\u0010$\u001a\u00020 2\u0006\u0010!\u001a\u00020%H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u0011\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0019\u0010\u0013R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u001b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006&"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Criticals;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attacks", "", "getAttacks", "()I", "setAttacks", "(I)V", "debugValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getDelayValue", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "hurtTimeValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getMsTimer", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "packet", "getPacket", "tag", "", "getTag", "()Ljava/lang/String;", "target", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onEnable", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Criticals", description = "Automatically deals critical hits.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/Criticals.class */
public final class Criticals extends Module {

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"NewPacket", "Packet", "NcpPacket", "GrimTest", "Grim", "VulCanSemi", "AAC5.0.2", "NoGround", "Visual", "Hop", "TPHop", "Jump", "LowJump", "SpartanSemi"}, "packet");

    @NotNull
    private final IntegerValue delayValue = new IntegerValue("Delay", 0, 0, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD);
    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);

    @NotNull
    private final ListValue packet = new ListValue("PacketMode", new String[]{"C04", "C06"}, "C04");
    private final BoolValue debugValue = new BoolValue("DebugMessage", false);

    @NotNull
    private final MSTimer msTimer = new MSTimer();
    private int attacks;
    private int target;

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @NotNull
    public final IntegerValue getDelayValue() {
        return this.delayValue;
    }

    @NotNull
    public final ListValue getPacket() {
        return this.packet;
    }

    @NotNull
    public final MSTimer getMsTimer() {
        return this.msTimer;
    }

    public final int getAttacks() {
        return this.attacks;
    }

    public final void setAttacks(int i) {
        this.attacks = i;
    }

    public void onEnable() {
        if (StringsKt.equals((String) this.modeValue.get(), "NoGround", true)) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            thePlayer.jump();
        }
        this.attacks = 0;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Type inference failed for: r0v36, types: [net.ccbluex.liquidbounce.features.module.modules.combat.Criticals$onAttack$1] */
    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        IEntityPlayerSP thePlayer;
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (!MinecraftInstance.classProvider.isEntityLivingBase(event.getTargetEntity()) || (thePlayer = MinecraftInstance.f157mc.getThePlayer()) == null) {
            return;
        }
        IEntity targetEntity = event.getTargetEntity();
        if (targetEntity == null) {
            Intrinsics.throwNpe();
        }
        IEntityLivingBase iEntityLivingBaseAsEntityLivingBase = targetEntity.asEntityLivingBase();
        this.target = iEntityLivingBaseAsEntityLivingBase.getEntityId();
        if (!thePlayer.getOnGround() || thePlayer.isOnLadder() || thePlayer.isInWeb() || thePlayer.isInWater() || thePlayer.isInLava() || thePlayer.getRidingEntity() != null || iEntityLivingBaseAsEntityLivingBase.getHurtTime() > ((Number) this.hurtTimeValue.get()).intValue() || LiquidBounce.INSTANCE.getModuleManager().get(Fly.class).getState() || !this.msTimer.hasTimePassed(((Number) this.delayValue.get()).intValue())) {
            return;
        }
        double posX = thePlayer.getPosX();
        double posY = thePlayer.getPosY();
        double posZ = thePlayer.getPosZ();
        ?? r0 = new Function4(this, thePlayer) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.Criticals.onAttack.1
            final Criticals this$0;
            final IEntityPlayerSP $thePlayer;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(4);
                this.this$0 = this;
                this.$thePlayer = thePlayer;
            }

            @Override // kotlin.jvm.functions.Function4
            public Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                invoke(((Number) obj).doubleValue(), ((Number) obj2).doubleValue(), ((Number) obj3).doubleValue(), ((Boolean) obj4).booleanValue());
                return Unit.INSTANCE;
            }

            public static void invoke$default(C04071 c04071, double d, double d2, double d3, boolean z, int i, Object obj) {
                if ((i & 1) != 0) {
                    d = 0.0d;
                }
                if ((i & 2) != 0) {
                    d2 = 0.0d;
                }
                if ((i & 4) != 0) {
                    d3 = 0.0d;
                }
                c04071.invoke(d, d2, d3, z);
            }

            public final void invoke(double d, double d2, double d3, boolean z) {
                double posX2 = this.$thePlayer.getPosX() + d;
                double posY2 = this.$thePlayer.getPosY() + d2;
                double posZ2 = this.$thePlayer.getPosZ() + d3;
                if (Intrinsics.areEqual((String) this.this$0.getPacket().get(), "C06")) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosLook(posX2, posY2, posZ2, this.$thePlayer.getRotationYaw(), this.$thePlayer.getRotationPitch(), z));
                } else {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX2, posY2, posZ2, z));
                }
            }
        };
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -995865464:
                if (lowerCase.equals("packet")) {
                    C04071.invoke$default(r0, 0.0d, 0.0625d, 0.0d, true, 5, null);
                    C04071.invoke$default(r0, 0.0d, 0.0d, 0.0d, false, 7, null);
                    C04071.invoke$default(r0, 0.0d, 1.1E-5d, 0.0d, false, 5, null);
                    C04071.invoke$default(r0, 0.0d, 0.0d, 0.0d, false, 7, null);
                    break;
                }
                break;
            case -891664989:
                if (lowerCase.equals("ncppacket")) {
                    C04071.invoke$default(r0, 0.0d, 0.11d, 0.0d, false, 5, null);
                    C04071.invoke$default(r0, 0.0d, 0.1100013579d, 0.0d, false, 5, null);
                    C04071.invoke$default(r0, 0.0d, 1.3579E-6d, 0.0d, false, 5, null);
                    break;
                }
                break;
            case -816216256:
                if (lowerCase.equals("visual")) {
                    thePlayer.onCriticalHit(iEntityLivingBaseAsEntityLivingBase);
                    break;
                }
                break;
            case -684807295:
                if (lowerCase.equals("spartansemi")) {
                    this.attacks++;
                    if (this.attacks > 6) {
                        C04071.invoke$default(r0, 0.0d, 0.02d, 0.0d, false, 5, null);
                        C04071.invoke$default(r0, 0.0d, 0.122d, 0.0d, false, 5, null);
                        this.attacks = 0;
                        break;
                    }
                }
                break;
            case 103497:
                if (lowerCase.equals("hop")) {
                    thePlayer.setMotionY(0.1d);
                    thePlayer.setFallDistance(0.1f);
                    thePlayer.setOnGround(false);
                    break;
                }
                break;
            case 3181391:
                if (lowerCase.equals("grim")) {
                    this.attacks++;
                    if (this.attacks > 2) {
                        C04071.invoke$default(r0, 0.0d, 3.355555521E-11d, 0.0d, false, 5, null);
                        break;
                    }
                }
                break;
            case 3273774:
                if (lowerCase.equals("jump")) {
                    thePlayer.jump();
                    break;
                }
                break;
            case 110568525:
                if (lowerCase.equals("tphop")) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY + 0.02d, posZ, false));
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY + 0.01d, posZ, false));
                    thePlayer.setPosition(posX, posY + 0.01d, posZ);
                    break;
                }
                break;
            case 216546856:
                if (lowerCase.equals("newpacket")) {
                    C04071.invoke$default(r0, 0.0d, 0.05250000001304d, 0.0d, true, 5, null);
                    C04071.invoke$default(r0, 0.0d, 0.00150000001304d, 0.0d, false, 5, null);
                    C04071.invoke$default(r0, 0.0d, 0.01400000001304d, 0.0d, false, 5, null);
                    C04071.invoke$default(r0, 0.0d, 0.00150000001304d, 0.0d, false, 5, null);
                    break;
                }
                break;
            case 327072344:
                if (lowerCase.equals("aac5.0.2")) {
                    C04071.invoke$default(r0, 0.0d, 0.00114514d + ThreadLocalRandom.current().nextDouble(1.0E-7d, 1.0E-8d), 0.0d, false, 5, null);
                    C04071.invoke$default(r0, 0.0d, (-1.4174383E-6d) - ThreadLocalRandom.current().nextDouble(1.0E-7d, 1.0E-8d), 0.0d, false, 5, null);
                    C04071.invoke$default(r0, 0.0d, 0.0014421117d + ThreadLocalRandom.current().nextDouble(1.0E-4d, 1.0E-5d), 0.0d, false, 5, null);
                    C04071.invoke$default(r0, 0.0d, (-1.009977E-6d) + ThreadLocalRandom.current().nextDouble(1.0E-6d, 1.0E-7d), 0.0d, false, 5, null);
                    break;
                }
                break;
            case 327323745:
                if (lowerCase.equals("grimtest")) {
                    this.attacks++;
                    if (this.attacks > 2) {
                        r0.invoke(2.001E-9d, 2.00112E-10d, 2.0E-10d, false);
                        break;
                    }
                }
                break;
            case 357158274:
                if (lowerCase.equals("lowjump")) {
                    thePlayer.setMotionY(0.3425d);
                    break;
                }
                break;
            case 1358083345:
                if (lowerCase.equals("vulcansemi")) {
                    this.attacks++;
                    if (this.attacks > 6) {
                        C04071.invoke$default(r0, 0.0d, 0.2d, 0.0d, false, 5, null);
                        C04071.invoke$default(r0, 0.0d, 0.1216d, 0.0d, false, 5, null);
                        this.attacks = 0;
                        break;
                    }
                }
                break;
        }
        this.msTimer.reset();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isCPacketPlayer(packet) && StringsKt.equals((String) this.modeValue.get(), "NoGround", true)) {
            packet.asCPacketPlayer().setOnGround(false);
        }
        if (MinecraftInstance.classProvider.isSPacketAnimation(packet) && ((Boolean) this.debugValue.get()).booleanValue() && packet.asSPacketAnimation().getAnimationType() == 4 && packet.asSPacketAnimation().getEntityID() == this.target) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
            if (module == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
            }
            IEntityLivingBase target = ((KillAura) module).getTarget();
            if (target == null) {
                Intrinsics.throwNpe();
            }
            ClientUtils.displayChatMessage("\u00a7b[\u00a7bCoolSenseTips]\u00a7f\u89e6\u53d1\u00a7c\u66b4\u51fb\u00a7b(\u00a76\u73a9\u5bb6:\u00a7a" + target.getName() + ')');
        }
    }

    @Nullable
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
