package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import java.nio.ByteBuffer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.ccbluex.liquidbounce.features.module.modules.exploit.MultiActions;
import net.ccbluex.liquidbounce.features.module.modules.world.FastPlace;
import net.ccbluex.liquidbounce.injection.backend.EnumFacingImplKt;
import net.ccbluex.liquidbounce.injection.backend.GuiScreenImplKt;
import net.ccbluex.liquidbounce.injection.backend.WorldClientImplKt;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.render.IconUtils;
import net.ccbluex.liquidbounce.utils.render.MiniMapRegister;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.util.Constants;

@Mixin({Minecraft.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/client/MixinMinecraft.class */
public abstract class MixinMinecraft {

    @Shadow
    public GuiScreen field_71462_r;

    @Shadow
    public boolean field_71454_w;

    @Shadow
    public RayTraceResult field_71476_x;

    @Shadow
    public WorldClient field_71441_e;

    @Shadow
    public EntityPlayerSP field_71439_g;

    @Shadow
    public ParticleManager field_71452_i;

    @Shadow
    public PlayerControllerMP field_71442_b;

    @Shadow
    public int field_71443_c;

    @Shadow
    public int field_71440_d;

    @Shadow
    public int field_71467_ac;

    @Shadow
    public GameSettings field_71474_y;

    @Shadow
    private int field_71429_W;
    private long lastFrame = getTime();

    @Inject(method = {"run"}, m59at = {@InterfaceC0563At("HEAD")})
    private void init(CallbackInfo callbackInfo) {
        if (this.field_71443_c < 1067) {
            this.field_71443_c = 1067;
        }
        if (this.field_71440_d < 622) {
            this.field_71440_d = 622;
        }
    }

    @Inject(method = {Constants.CTOR}, m59at = {@InterfaceC0563At("RETURN")})
    private void injectWrapperInitializator(CallbackInfo callbackInfo) {
        LiquidBounce.wrapper = WrapperImpl.INSTANCE;
    }

    @Inject(method = {"init"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal = 2, shift = InterfaceC0563At.Shift.AFTER)})
    private void startGame(CallbackInfo callbackInfo) {
        LiquidBounce.INSTANCE.startClient();
    }

    @Inject(method = {"createDisplay"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", shift = InterfaceC0563At.Shift.AFTER)})
    private void createDisplay(CallbackInfo callbackInfo) {
        Display.setTitle("LiquidSense b1 | 1.12.2 | By AquaVitTeam");
    }

    @Inject(method = {"displayGuiScreen"}, m59at = {@InterfaceC0563At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;", shift = InterfaceC0563At.Shift.AFTER)})
    private void displayGuiScreen(CallbackInfo callbackInfo) {
        if ((this.field_71462_r instanceof GuiMainMenu) || (this.field_71462_r != null && this.field_71462_r.getClass().getName().startsWith("net.labymod") && this.field_71462_r.getClass().getSimpleName().equals("ModGuiMainMenu"))) {
            this.field_71462_r = GuiScreenImplKt.unwrap(LiquidBounce.wrapper.getClassProvider().wrapGuiScreen(new net.ccbluex.liquidbounce.p005ui.client.GuiMainMenu()));
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
            this.field_71462_r.func_146280_a(Minecraft.func_71410_x(), scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
            this.field_71454_w = false;
        }
        LiquidBounce.eventManager.callEvent(new ScreenEvent(this.field_71462_r == null ? null : GuiScreenImplKt.wrap(this.field_71462_r)));
    }

    @Inject(method = {"runGameLoop"}, m59at = {@InterfaceC0563At("HEAD")})
    private void runGameLoop(CallbackInfo callbackInfo) {
        long time = getTime();
        int i = (int) (time - this.lastFrame);
        this.lastFrame = time;
        RenderUtils.deltaTime = i;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    @Inject(method = {"runTick"}, m59at = {@InterfaceC0563At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;joinPlayerCounter:I", shift = InterfaceC0563At.Shift.BEFORE)})
    private void onTick(CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new TickEvent());
    }

    @Inject(method = {"runTickKeyboard"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift = InterfaceC0563At.Shift.AFTER)})
    private void onKey(CallbackInfo callbackInfo) {
        if (Keyboard.getEventKeyState() && this.field_71462_r == null) {
            LiquidBounce.eventManager.callEvent(new KeyEvent(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + '\u0100' : Keyboard.getEventKey()));
        }
    }

    @Inject(method = {"sendClickBlockToController"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/util/math/RayTraceResult;getBlockPos()Lnet/minecraft/util/math/BlockPos;")})
    private void onClickBlock(CallbackInfo callbackInfo) {
        IBlockState iBlockStateFunc_180495_p = this.field_71441_e.func_180495_p(this.field_71476_x.func_178782_a());
        if (this.field_71429_W == 0 && iBlockStateFunc_180495_p.func_177230_c().func_149688_o(iBlockStateFunc_180495_p) != Material.field_151579_a) {
            LiquidBounce.eventManager.callEvent(new ClickBlockEvent(BackendExtentionsKt.wrap(this.field_71476_x.func_178782_a()), EnumFacingImplKt.wrap(this.field_71476_x.field_178784_b)));
        }
    }

    @Inject(method = {"setWindowIcon"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void setWindowIcon(CallbackInfo callbackInfo) {
        ByteBuffer[] favicon;
        if (Util.func_110647_a() != Util.EnumOS.OSX && (favicon = IconUtils.getFavicon()) != null) {
            Display.setIcon(favicon);
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"shutdown"}, m59at = {@InterfaceC0563At("HEAD")})
    private void shutdown(CallbackInfo callbackInfo) {
        try {
            LiquidBounce.INSTANCE.stopClient();
        } catch (Exception unused) {
            System.exit(0);
        }
    }

    @Inject(method = {"clickMouse"}, m59at = {@InterfaceC0563At("HEAD")})
    private void clickMouse(CallbackInfo callbackInfo) {
        CPSCounter.registerClick(CPSCounter.MouseButton.LEFT);
        if (LiquidBounce.moduleManager.getModule(AutoClicker.class).getState()) {
            this.field_71429_W = 0;
        }
    }

    @Inject(method = {"middleClickMouse"}, m59at = {@InterfaceC0563At("HEAD")})
    private void middleClickMouse(CallbackInfo callbackInfo) {
        CPSCounter.registerClick(CPSCounter.MouseButton.MIDDLE);
    }

    @Inject(method = {"rightClickMouse"}, m59at = {@InterfaceC0563At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;rightClickDelayTimer:I", shift = InterfaceC0563At.Shift.AFTER)})
    private void rightClickMouse(CallbackInfo callbackInfo) {
        CPSCounter.registerClick(CPSCounter.MouseButton.RIGHT);
        FastPlace fastPlace = (FastPlace) LiquidBounce.moduleManager.getModule(FastPlace.class);
        if (fastPlace.getState()) {
            this.field_71467_ac = ((Integer) fastPlace.getSpeedValue().get()).intValue();
        }
    }

    @Inject(method = {"loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V"}, m59at = {@InterfaceC0563At("HEAD")})
    private void loadWorld(WorldClient worldClient, String str, CallbackInfo callbackInfo) {
        if (this.field_71441_e != null) {
            MiniMapRegister.INSTANCE.unloadAllChunks();
        }
        LiquidBounce.eventManager.callEvent(new WorldEvent(worldClient == null ? null : WorldClientImplKt.wrap(worldClient)));
    }

    @Overwrite
    private void func_147115_a(boolean z) {
        if (!z) {
            this.field_71429_W = 0;
        }
        if (this.field_71429_W <= 0) {
            if (!this.field_71439_g.func_184587_cr() || LiquidBounce.moduleManager.getModule(MultiActions.class).getState()) {
                if (!z || this.field_71476_x == null || this.field_71476_x.field_72313_a != RayTraceResult.Type.BLOCK) {
                    if (!LiquidBounce.moduleManager.getModule(AbortBreaking.class).getState()) {
                        this.field_71442_b.func_78767_c();
                        return;
                    }
                    return;
                }
                BlockPos blockPosFunc_178782_a = this.field_71476_x.func_178782_a();
                if (this.field_71429_W == 0) {
                    LiquidBounce.eventManager.callEvent(new ClickBlockEvent(BackendExtentionsKt.wrap(blockPosFunc_178782_a), EnumFacingImplKt.wrap(this.field_71476_x.field_178784_b)));
                }
                IBlockState iBlockStateFunc_180495_p = this.field_71441_e.func_180495_p(blockPosFunc_178782_a);
                if (iBlockStateFunc_180495_p.func_177230_c().func_149688_o(iBlockStateFunc_180495_p) != Material.field_151579_a && this.field_71442_b.func_180512_c(blockPosFunc_178782_a, this.field_71476_x.field_178784_b)) {
                    this.field_71452_i.func_180532_a(blockPosFunc_178782_a, this.field_71476_x.field_178784_b);
                    this.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
                }
            }
        }
    }

    @Overwrite
    public int func_90020_K() {
        if (this.field_71441_e != null || this.field_71462_r == null) {
            return this.field_71474_y.field_74350_i;
        }
        return 60;
    }
}
