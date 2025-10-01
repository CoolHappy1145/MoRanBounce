package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.lbplus.ColorMixer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.features.module.modules.combat.Aimbot;
import net.ccbluex.liquidbounce.features.module.modules.combat.AntiFireBall;
import net.ccbluex.liquidbounce.features.module.modules.combat.AuraKeepSprint;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoArmor;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoBow;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoLeave;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoPot;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoSoup;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoWeapon;
import net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot;
import net.ccbluex.liquidbounce.features.module.modules.combat.Criticals;
import net.ccbluex.liquidbounce.features.module.modules.combat.FastBow;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitBox;
import net.ccbluex.liquidbounce.features.module.modules.combat.Ignite;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.combat.SuperKnockback;
import net.ccbluex.liquidbounce.features.module.modules.combat.TNTBlock;
import net.ccbluex.liquidbounce.features.module.modules.combat.TeleportHit;
import net.ccbluex.liquidbounce.features.module.modules.combat.Trigger;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AntiHunger;
import net.ccbluex.liquidbounce.features.module.modules.exploit.BedGodMode;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Clip;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ConsoleSpammer;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Damage;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Derp;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ForceUnicodeChat;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Ghost;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GhostHand;
import net.ccbluex.liquidbounce.features.module.modules.exploit.GodMode;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ItemTeleport;
import net.ccbluex.liquidbounce.features.module.modules.exploit.KeepContainer;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Kick;
import net.ccbluex.liquidbounce.features.module.modules.exploit.MoreCarry;
import net.ccbluex.liquidbounce.features.module.modules.exploit.MultiActions;
import net.ccbluex.liquidbounce.features.module.modules.exploit.NoPitchLimit;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Paralyze;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Phase;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PingSpoof;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Plugins;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PortalMenu;
import net.ccbluex.liquidbounce.features.module.modules.exploit.ServerCrasher;
import net.ccbluex.liquidbounce.features.module.modules.exploit.SkinDerp;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Teleport;
import net.ccbluex.liquidbounce.features.module.modules.exploit.VehicleOneHit;
import net.ccbluex.liquidbounce.features.module.modules.hyt.AutoL;
import net.ccbluex.liquidbounce.features.module.modules.hyt.AutoLobby;
import net.ccbluex.liquidbounce.features.module.modules.hyt.HytBypass;
import net.ccbluex.liquidbounce.features.module.modules.hyt.HytGetName;
import net.ccbluex.liquidbounce.features.module.modules.hyt.NoC03;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.AtAllProvider;
import net.ccbluex.liquidbounce.features.module.modules.misc.ComponentOnHover;
import net.ccbluex.liquidbounce.features.module.modules.misc.CustomSet;
import net.ccbluex.liquidbounce.features.module.modules.misc.CustomTitle;
import net.ccbluex.liquidbounce.features.module.modules.misc.FlagTips;
import net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat;
import net.ccbluex.liquidbounce.features.module.modules.misc.MidClick;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.misc.NoRotateSet;
import net.ccbluex.liquidbounce.features.module.modules.misc.ResourcePackSpoof;
import net.ccbluex.liquidbounce.features.module.modules.misc.Spammer;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.features.module.modules.movement.AirJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.AirLadder;
import net.ccbluex.liquidbounce.features.module.modules.movement.AutoWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.BlockWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.BufferSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.BugUp;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastClimb;
import net.ccbluex.liquidbounce.features.module.modules.movement.FastStairs;
import net.ccbluex.liquidbounce.features.module.modules.movement.Fly;
import net.ccbluex.liquidbounce.features.module.modules.movement.Freeze;
import net.ccbluex.liquidbounce.features.module.modules.movement.HighJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.IceSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.InventoryMove;
import net.ccbluex.liquidbounce.features.module.modules.movement.LadderJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.LongJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoClip;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoJumpDelay;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoWeb;
import net.ccbluex.liquidbounce.features.module.modules.movement.Parkour;
import net.ccbluex.liquidbounce.features.module.modules.movement.PerfectHorseJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.ReverseStep;
import net.ccbluex.liquidbounce.features.module.modules.movement.SafeWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.SlimeJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sneak;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.module.modules.movement.Step;
import net.ccbluex.liquidbounce.features.module.modules.movement.Strafe;
import net.ccbluex.liquidbounce.features.module.modules.movement.WallClimb;
import net.ccbluex.liquidbounce.features.module.modules.movement.WaterSpeed;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiAFK;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiCactus;
import net.ccbluex.liquidbounce.features.module.modules.player.AntiFall;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoFish;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoHead;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoRespawn;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoTool;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.player.Eagle;
import net.ccbluex.liquidbounce.features.module.modules.player.FastUse;
import net.ccbluex.liquidbounce.features.module.modules.player.InventoryCleaner;
import net.ccbluex.liquidbounce.features.module.modules.player.KeepAlive;
import net.ccbluex.liquidbounce.features.module.modules.player.NoFall;
import net.ccbluex.liquidbounce.features.module.modules.player.PotionSaver;
import net.ccbluex.liquidbounce.features.module.modules.player.Reach;
import net.ccbluex.liquidbounce.features.module.modules.player.Regen;
import net.ccbluex.liquidbounce.features.module.modules.player.Zoot;
import net.ccbluex.liquidbounce.features.module.modules.render.Ambience;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.BlockESP;
import net.ccbluex.liquidbounce.features.module.modules.render.BlockOverlay;
import net.ccbluex.liquidbounce.features.module.modules.render.Breadcrumbs;
import net.ccbluex.liquidbounce.features.module.modules.render.CameraClip;
import net.ccbluex.liquidbounce.features.module.modules.render.Cape;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.features.module.modules.render.EnchantEffect;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.features.module.modules.render.Fullbright;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.features.module.modules.render.ItemESP;
import net.ccbluex.liquidbounce.features.module.modules.render.NameTags;
import net.ccbluex.liquidbounce.features.module.modules.render.NoBob;
import net.ccbluex.liquidbounce.features.module.modules.render.NoFOV;
import net.ccbluex.liquidbounce.features.module.modules.render.NoHurtCam;
import net.ccbluex.liquidbounce.features.module.modules.render.NoScoreboard;
import net.ccbluex.liquidbounce.features.module.modules.render.NoSwing;
import net.ccbluex.liquidbounce.features.module.modules.render.PotionRender;
import net.ccbluex.liquidbounce.features.module.modules.render.Projectiles;
import net.ccbluex.liquidbounce.features.module.modules.render.ProphuntESP;
import net.ccbluex.liquidbounce.features.module.modules.render.Rotations;
import net.ccbluex.liquidbounce.features.module.modules.render.StorageESP;
import net.ccbluex.liquidbounce.features.module.modules.render.TNTESP;
import net.ccbluex.liquidbounce.features.module.modules.render.Tracers;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.ccbluex.liquidbounce.features.module.modules.render.XRay;
import net.ccbluex.liquidbounce.features.module.modules.world.AutoBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestStealer;
import net.ccbluex.liquidbounce.features.module.modules.world.CivBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.FastBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.FastPlace;
import net.ccbluex.liquidbounce.features.module.modules.world.Fucker;
import net.ccbluex.liquidbounce.features.module.modules.world.LightningCheck;
import net.ccbluex.liquidbounce.features.module.modules.world.Liquids;
import net.ccbluex.liquidbounce.features.module.modules.world.NoSlowBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.Nuker;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.world.SpeedMine;
import net.ccbluex.liquidbounce.features.module.modules.world.Timer;
import net.ccbluex.liquidbounce.features.module.modules.world.Tower;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdR\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\ufffd\ufffd\u00a2\u0006\u0002\b\u000fJ\u0015\u0010\u0010\u001a\u00020\u00062\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0086\u0002J\u0012\u0010\u0012\u001a\u00020\u00062\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0005J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001aH\u0003J\u0018\u0010\u001b\u001a\u00020\r2\u000e\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0002J\u0012\u0010\u001b\u001a\u00020\r2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0002J\u000e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006J\u0006\u0010\u001e\u001a\u00020\rJ1\u0010\u001e\u001a\u00020\r2\"\u0010\b\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050\u001f\"\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0007\u00a2\u0006\u0002\u0010 J\u000e\u0010!\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006R2\u0010\u0003\u001a&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\""}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "moduleClassMap", "Ljava/util/HashMap;", Constants.CLASS_DESC, "Lnet/ccbluex/liquidbounce/features/module/Module;", "Lkotlin/collections/HashMap;", "modules", "Ljava/util/TreeSet;", "getModules", "()Ljava/util/TreeSet;", "generateCommand", "", "module", "generateCommand$LiquidSense", PropertyDescriptor.GET, "clazz", "getModule", "moduleClass", "moduleName", "", "handleEvents", "", "onKey", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "registerModule", "cbModule", "", "registerModules", "", "([Ljava/lang/Class;)V", "unregisterModule", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/ModuleManager.class */
public final class ModuleManager implements Listenable {

    @NotNull
    private final TreeSet modules = new TreeSet(new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.ModuleManager$modules$1
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return compare((Module) obj, (Module) obj2);
        }

        public final int compare(Module module, Module module2) {
            return module.getName().compareTo(module2.getName());
        }
    });
    private final HashMap moduleClassMap = new HashMap();

    public ModuleManager() {
        LiquidBounce.INSTANCE.getEventManager().registerListener(this);
    }

    @NotNull
    public final TreeSet getModules() {
        return this.modules;
    }

    public final void registerModules() {
        ClientUtils.getLogger().info("[ModuleManager] Loading modules...");
        registerModules(new Class[]{AutoArmor.class, AutoBow.class, AutoLeave.class, AutoPot.class, AutoSoup.class, AutoWeapon.class, BowAimbot.class, Criticals.class, LightningCheck.class, AutoL.class, KillAura.class, Trigger.class, Fly.class, ColorMixer.class, NoC03.class, ClickGUI.class, AuraKeepSprint.class, FlagTips.class, AutoLobby.class, HighJump.class, AutoLobby.class, CustomTitle.class, InventoryMove.class, LiquidWalk.class, SafeWalk.class, WallClimb.class, Strafe.class, AntiFireBall.class, Sprint.class, Teams.class, NoRotateSet.class, AntiBot.class, ChestStealer.class, Scaffold.class, CivBreak.class, Tower.class, FastBreak.class, FastPlace.class, ESP.class, HytGetName.class, HytBypass.class, NoSlow.class, Velocity.class, AntiFall.class, Ambience.class, Speed.class, Tracers.class, NameTags.class, FastUse.class, Teleport.class, Fullbright.class, ItemESP.class, StorageESP.class, Projectiles.class, NoClip.class, Nuker.class, PingSpoof.class, FastClimb.class, CustomSet.class, Step.class, AutoRespawn.class, AutoTool.class, NoWeb.class, Spammer.class, IceSpeed.class, Zoot.class, Regen.class, NoFall.class, Blink.class, NameProtect.class, NoHurtCam.class, Ghost.class, MidClick.class, XRay.class, Timer.class, Sneak.class, SkinDerp.class, Paralyze.class, GhostHand.class, AutoWalk.class, AutoBreak.class, FreeCam.class, Aimbot.class, Eagle.class, HitBox.class, AntiCactus.class, Plugins.class, AntiHunger.class, ConsoleSpammer.class, LongJump.class, Parkour.class, LadderJump.class, FastBow.class, MultiActions.class, AirJump.class, AutoClicker.class, NoBob.class, BlockOverlay.class, NoFriends.class, BlockESP.class, Animations.class, Chams.class, Clip.class, Phase.class, ServerCrasher.class, NoFOV.class, FastStairs.class, Derp.class, ReverseStep.class, TNTBlock.class, InventoryCleaner.class, TrueSight.class, LiquidChat.class, AntiBlind.class, NoSwing.class, BedGodMode.class, BugUp.class, Breadcrumbs.class, AbortBreaking.class, PotionSaver.class, CameraClip.class, WaterSpeed.class, Ignite.class, SlimeJump.class, MoreCarry.class, NoPitchLimit.class, Cape.class, Kick.class, Liquids.class, AtAllProvider.class, AirLadder.class, GodMode.class, TeleportHit.class, ForceUnicodeChat.class, ItemTeleport.class, BufferSpeed.class, SuperKnockback.class, ProphuntESP.class, AutoFish.class, Damage.class, Freeze.class, KeepContainer.class, VehicleOneHit.class, Reach.class, Rotations.class, NoJumpDelay.class, BlockWalk.class, AntiAFK.class, PerfectHorseJump.class, HUD.class, TNTESP.class, ComponentOnHover.class, PotionRender.class, KeepAlive.class, ResourcePackSpoof.class, NoSlowBreak.class, PortalMenu.class, EnchantEffect.class, SpeedMine.class, AutoHead.class});
        registerModule((Module) NoScoreboard.INSTANCE);
        registerModule((Module) Fucker.INSTANCE);
        registerModule((Module) ChestAura.INSTANCE);
        ClientUtils.getLogger().info("[ModuleManager] Loaded " + this.modules.size() + " modules.");
    }

    public final void registerModule(@NotNull Module module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        if (!module.isSupported()) {
            return;
        }
        this.modules.add(module);
        this.moduleClassMap.put(module.getClass(), module);
        generateCommand$LiquidSense(module);
        LiquidBounce.INSTANCE.getEventManager().registerListener(module);
    }

    private final void registerModule(Class cls) {
        try {
            Object objNewInstance = cls.newInstance();
            Intrinsics.checkExpressionValueIsNotNull(objNewInstance, "moduleClass.newInstance()");
            registerModule((Module) objNewInstance);
        } catch (Throwable th) {
            ClientUtils.getLogger().error("Failed to load module: " + cls.getName() + " (" + th.getClass().getName() + ": " + th.getMessage() + ')');
        }
    }

    @SafeVarargs
    public final void registerModules(@NotNull Class[] modules) {
        Intrinsics.checkParameterIsNotNull(modules, "modules");
        for (Class cls : modules) {
            registerModule(cls);
        }
    }

    private final void registerModule(Object obj) throws IllegalAccessException, InstantiationException {
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<out net.ccbluex.liquidbounce.features.module.Module>");
        }
        Object objNewInstance = ((Class) obj).newInstance();
        Intrinsics.checkExpressionValueIsNotNull(objNewInstance, "(cbModule as Class<out Module>).newInstance()");
        registerModule((Module) objNewInstance);
    }

    public final void unregisterModule(@NotNull Module module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        this.modules.remove(module);
        this.moduleClassMap.remove(module.getClass());
        LiquidBounce.INSTANCE.getEventManager().unregisterListener(module);
    }

    public final void generateCommand$LiquidSense(@NotNull Module module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        List values = module.getValues();
        if (values.isEmpty()) {
            return;
        }
        LiquidBounce.INSTANCE.getCommandManager().registerCommand(new ModuleCommand(module, values));
    }

    @NotNull
    public final Module getModule(@NotNull Class moduleClass) {
        Intrinsics.checkParameterIsNotNull(moduleClass, "moduleClass");
        Object obj = this.moduleClassMap.get(moduleClass);
        if (obj == null) {
            Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(obj, "moduleClassMap[moduleClass]!!");
        return (Module) obj;
    }

    @NotNull
    public final Module get(@NotNull Class clazz) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        return getModule(clazz);
    }

    @Nullable
    public final Module getModule(@Nullable String str) {
        Object obj;
        Iterator it = this.modules.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            if (StringsKt.equals(((Module) next).getName(), str, true)) {
                obj = next;
                break;
            }
        }
        return (Module) obj;
    }

    @EventTarget
    private final void onKey(KeyEvent keyEvent) {
        TreeSet treeSet = this.modules;
        ArrayList arrayList = new ArrayList();
        for (Object obj : treeSet) {
            if (((Module) obj).getKeyBind() == keyEvent.getKey()) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Module) it.next()).toggle();
        }
    }
}
