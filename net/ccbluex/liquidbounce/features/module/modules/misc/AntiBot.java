package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketAnimation;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd .2\u00020\u0001:\u0001.B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010#\u001a\u00020$H\u0002J\u0010\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020'H\u0007J\b\u0010(\u001a\u00020$H\u0016J\u0010\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020+H\u0007J\u0012\u0010,\u001a\u00020$2\b\u0010*\u001a\u0004\u0018\u00010-H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0013X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010!\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\"\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006/"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AntiBot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "air", "", "", "airValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "armorValue", "colorValue", "derpValue", "duplicateInTabValue", "duplicateInWorldValue", "entityIDValue", "ground", "groundValue", "healthValue", "hitted", "invalidGround", "", "invalidGroundValue", "invisible", "livingTimeTicksValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "livingTimeValue", "needHitValue", "pingValue", "playerName", "", "swing", "swingValue", "tabModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tabValue", "wasInvisibleValue", "clearAll", "", "onAttack", "e", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "Companion", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AntiBot", description = "Prevents KillAura from attacking AntiCheat bots.", category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AntiBot.class */
public final class AntiBot extends Module {
    private final BoolValue tabValue = new BoolValue("Tab", true);
    private final ListValue tabModeValue = new ListValue("TabMode", new String[]{"Equals", "Contains"}, "Contains");
    private final BoolValue entityIDValue = new BoolValue("EntityID", true);
    private final BoolValue colorValue = new BoolValue("Color", false);
    private final BoolValue livingTimeValue = new BoolValue("LivingTime", false);
    private final IntegerValue livingTimeTicksValue = new IntegerValue("LivingTimeTicks", 40, 1, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD);
    private final BoolValue groundValue = new BoolValue("Ground", true);
    private final BoolValue airValue = new BoolValue("Air", false);
    private final BoolValue invalidGroundValue = new BoolValue("InvalidGround", true);
    private final BoolValue swingValue = new BoolValue("Swing", false);
    private final BoolValue healthValue = new BoolValue("Health", false);
    private final BoolValue derpValue = new BoolValue("Derp", true);
    private final BoolValue wasInvisibleValue = new BoolValue("WasInvisible", false);
    private final BoolValue armorValue = new BoolValue("Armor", false);
    private final BoolValue pingValue = new BoolValue("Ping", false);
    private final BoolValue needHitValue = new BoolValue("NeedHit", false);
    private final BoolValue duplicateInWorldValue = new BoolValue("DuplicateInWorld", false);
    private final BoolValue duplicateInTabValue = new BoolValue("DuplicateInTab", false);
    private final List ground = new ArrayList();
    private final List air = new ArrayList();
    private final Map invalidGround = new HashMap();
    private final List swing = new ArrayList();
    private final List invisible = new ArrayList();
    private final List hitted = new ArrayList();
    private final List playerName = new ArrayList();
    public static final Companion Companion = new Companion(null);

    @JvmStatic
    public static final boolean isBot(@NotNull IEntityLivingBase iEntityLivingBase) {
        return Companion.isBot(iEntityLivingBase);
    }

    public void onDisable() {
        clearAll();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.f157mc.getThePlayer() == null || MinecraftInstance.f157mc.getTheWorld() == null) {
            return;
        }
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isSPacketEntity(packet)) {
            ISPacketEntity iSPacketEntityAsSPacketEntity = packet.asSPacketEntity();
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            IEntity entity = iSPacketEntityAsSPacketEntity.getEntity(theWorld);
            if (MinecraftInstance.classProvider.isEntityPlayer(entity) && entity != null) {
                if (iSPacketEntityAsSPacketEntity.getOnGround() && !this.ground.contains(Integer.valueOf(entity.getEntityId()))) {
                    this.ground.add(Integer.valueOf(entity.getEntityId()));
                }
                if (!iSPacketEntityAsSPacketEntity.getOnGround() && !this.air.contains(Integer.valueOf(entity.getEntityId()))) {
                    this.air.add(Integer.valueOf(entity.getEntityId()));
                }
                if (!iSPacketEntityAsSPacketEntity.getOnGround()) {
                    int iIntValue = ((Number) this.invalidGround.getOrDefault(Integer.valueOf(entity.getEntityId()), 0)).intValue() / 2;
                    if (iIntValue <= 0) {
                        this.invalidGround.remove(Integer.valueOf(entity.getEntityId()));
                    } else {
                        this.invalidGround.put(Integer.valueOf(entity.getEntityId()), Integer.valueOf(iIntValue));
                    }
                } else if (entity.getPrevPosY() != entity.getPosY()) {
                    this.invalidGround.put(Integer.valueOf(entity.getEntityId()), Integer.valueOf(((Number) this.invalidGround.getOrDefault(Integer.valueOf(entity.getEntityId()), 0)).intValue() + 1));
                }
                if (entity.isInvisible() && !this.invisible.contains(Integer.valueOf(entity.getEntityId()))) {
                    this.invisible.add(Integer.valueOf(entity.getEntityId()));
                }
            }
        }
        if (MinecraftInstance.classProvider.isSPacketAnimation(packet)) {
            ISPacketAnimation iSPacketAnimationAsSPacketAnimation = packet.asSPacketAnimation();
            IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld2 == null) {
                Intrinsics.throwNpe();
            }
            IEntity entityByID = theWorld2.getEntityByID(iSPacketAnimationAsSPacketAnimation.getEntityID());
            if (entityByID == null || !MinecraftInstance.classProvider.isEntityLivingBase(entityByID) || iSPacketAnimationAsSPacketAnimation.getAnimationType() != 0 || this.swing.contains(Integer.valueOf(entityByID.getEntityId()))) {
                return;
            }
            this.swing.add(Integer.valueOf(entityByID.getEntityId()));
        }
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        IEntity targetEntity = e.getTargetEntity();
        if (targetEntity == null || !MinecraftInstance.classProvider.isEntityLivingBase(targetEntity) || this.hitted.contains(Integer.valueOf(targetEntity.getEntityId()))) {
            return;
        }
        this.hitted.add(Integer.valueOf(targetEntity.getEntityId()));
    }

    @EventTarget
    public final void onWorld(@Nullable WorldEvent worldEvent) {
        clearAll();
    }

    private final void clearAll() {
        this.hitted.clear();
        this.swing.clear();
        this.ground.clear();
        this.invalidGround.clear();
        this.invisible.clear();
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AntiBot$Companion;", "", "()V", "isBot", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AntiBot$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final boolean isBot(@NotNull final IEntityLivingBase entity) {
            AntiBot antiBot;
            Intrinsics.checkParameterIsNotNull(entity, "entity");
            if (!MinecraftInstance.classProvider.isEntityPlayer(entity) || (antiBot = (AntiBot) LiquidBounce.INSTANCE.getModuleManager().getModule(AntiBot.class)) == null || !antiBot.getState()) {
                return false;
            }
            if (((Boolean) antiBot.colorValue.get()).booleanValue()) {
                IIChatComponent displayName = entity.getDisplayName();
                if (displayName == null) {
                    Intrinsics.throwNpe();
                }
                if (!StringsKt.contains$default((CharSequence) StringsKt.replace$default(displayName.getFormattedText(), "\u00a7r", "", false, 4, (Object) null), (CharSequence) "\u00a7", false, 2, (Object) null)) {
                    return true;
                }
            }
            if (((Boolean) antiBot.livingTimeValue.get()).booleanValue() && entity.getTicksExisted() < ((Number) antiBot.livingTimeTicksValue.get()).intValue()) {
                return true;
            }
            if (((Boolean) antiBot.groundValue.get()).booleanValue() && !antiBot.ground.contains(Integer.valueOf(entity.getEntityId()))) {
                return true;
            }
            if (((Boolean) antiBot.airValue.get()).booleanValue() && !antiBot.air.contains(Integer.valueOf(entity.getEntityId()))) {
                return true;
            }
            if (((Boolean) antiBot.swingValue.get()).booleanValue() && !antiBot.swing.contains(Integer.valueOf(entity.getEntityId()))) {
                return true;
            }
            if (((Boolean) antiBot.healthValue.get()).booleanValue() && entity.getHealth() > 20.0f) {
                return true;
            }
            if (((Boolean) antiBot.entityIDValue.get()).booleanValue() && (entity.getEntityId() >= 1000000000 || entity.getEntityId() <= -1)) {
                return true;
            }
            if (((Boolean) antiBot.derpValue.get()).booleanValue() && (entity.getRotationPitch() > 90.0f || entity.getRotationPitch() < -90.0f)) {
                return true;
            }
            if (((Boolean) antiBot.wasInvisibleValue.get()).booleanValue() && antiBot.invisible.contains(Integer.valueOf(entity.getEntityId()))) {
                return true;
            }
            if (((Boolean) antiBot.armorValue.get()).booleanValue()) {
                IEntityPlayer iEntityPlayerAsEntityPlayer = entity.asEntityPlayer();
                if (iEntityPlayerAsEntityPlayer.getInventory().getArmorInventory().get(0) == null && iEntityPlayerAsEntityPlayer.getInventory().getArmorInventory().get(1) == null && iEntityPlayerAsEntityPlayer.getInventory().getArmorInventory().get(2) == null && iEntityPlayerAsEntityPlayer.getInventory().getArmorInventory().get(3) == null) {
                    return true;
                }
            }
            if (((Boolean) antiBot.pingValue.get()).booleanValue()) {
                INetworkPlayerInfo playerInfo = MinecraftInstance.f157mc.getNetHandler().getPlayerInfo(entity.asEntityPlayer().getUniqueID());
                if (playerInfo == null) {
                    Intrinsics.throwNpe();
                }
                if (playerInfo.getResponseTime() == 0) {
                    return true;
                }
            }
            if (((Boolean) antiBot.needHitValue.get()).booleanValue() && !antiBot.hitted.contains(Integer.valueOf(entity.getEntityId()))) {
                return true;
            }
            if (((Boolean) antiBot.invalidGroundValue.get()).booleanValue() && ((Number) antiBot.invalidGround.getOrDefault(Integer.valueOf(entity.getEntityId()), 0)).intValue() >= 10) {
                return true;
            }
            if (((Boolean) antiBot.tabValue.get()).booleanValue()) {
                boolean zEquals = StringsKt.equals((String) antiBot.tabModeValue.get(), "Equals", true);
                IIChatComponent displayName2 = entity.getDisplayName();
                if (displayName2 == null) {
                    Intrinsics.throwNpe();
                }
                String strStripColor = ColorUtils.stripColor(displayName2.getFormattedText());
                if (strStripColor != null) {
                    Iterator it = MinecraftInstance.f157mc.getNetHandler().getPlayerInfoMap().iterator();
                    while (it.hasNext()) {
                        String strStripColor2 = ColorUtils.stripColor(EntityUtils.getName((INetworkPlayerInfo) it.next()));
                        if (strStripColor2 != null) {
                            if (zEquals ? Intrinsics.areEqual(strStripColor, strStripColor2) : StringsKt.contains$default((CharSequence) strStripColor, (CharSequence) strStripColor2, false, 2, (Object) null)) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
            if (((Boolean) antiBot.duplicateInWorldValue.get()).booleanValue()) {
                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld == null) {
                    Intrinsics.throwNpe();
                }
                if (theWorld.getLoadedEntityList().stream().filter(new Predicate() { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot$Companion$isBot$1
                    @Override // java.util.function.Predicate
                    public boolean test(Object obj) {
                        return test((IEntity) obj);
                    }

                    public final boolean test(@NotNull IEntity currEntity) {
                        Intrinsics.checkParameterIsNotNull(currEntity, "currEntity");
                        return MinecraftInstance.classProvider.isEntityPlayer(currEntity) && Intrinsics.areEqual(currEntity.asEntityPlayer().getDisplayNameString(), currEntity.asEntityPlayer().getDisplayNameString());
                    }
                }).count() > 1) {
                    return true;
                }
            }
            if (((Boolean) antiBot.duplicateInTabValue.get()).booleanValue() && MinecraftInstance.f157mc.getNetHandler().getPlayerInfoMap().stream().filter(new Predicate(entity) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot$Companion$isBot$2
                final IEntityLivingBase $entity;

                {
                    this.$entity = entity;
                }

                @Override // java.util.function.Predicate
                public boolean test(Object obj) {
                    return test((INetworkPlayerInfo) obj);
                }

                public final boolean test(@Nullable INetworkPlayerInfo iNetworkPlayerInfo) {
                    return Intrinsics.areEqual(this.$entity.getName(), ColorUtils.stripColor(EntityUtils.getName(iNetworkPlayerInfo)));
                }
            }).count() > 1) {
                return true;
            }
            String name = entity.getName();
            if (name == null) {
                Intrinsics.throwNpe();
            }
            if (!(name.length() == 0)) {
                String name2 = entity.getName();
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                if (!Intrinsics.areEqual(name2, thePlayer.getName())) {
                    return false;
                }
            }
            return true;
        }
    }
}
