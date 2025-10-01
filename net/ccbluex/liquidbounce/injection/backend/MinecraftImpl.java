package net.ccbluex.liquidbounce.injection.backend;

import java.io.File;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.audio.ISoundHandler;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.client.particle.IParticleManager;
import net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.ITextureManager;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.IEntityRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.IRenderGlobal;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings;
import net.ccbluex.liquidbounce.api.minecraft.client.shader.IFramebuffer;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.ISession;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.math.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u00da\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010p\u001a\u00020q2\b\u0010r\u001a\u0004\u0018\u00010\u0006H\u0016J\u0013\u0010s\u001a\u00020.2\b\u0010t\u001a\u0004\u0018\u00010uH\u0096\u0002J\b\u0010v\u001a\u00020qH\u0016J\b\u0010w\u001a\u00020qH\u0016J\b\u0010x\u001a\u00020qH\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00128VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0014R\u0014\u0010\u0017\u001a\u00020\u00128VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0014R\u0014\u0010\u0019\u001a\u00020\u001a8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\u00020&8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b'\u0010(R\u0014\u0010)\u001a\u00020*8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020.8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b-\u0010/R\u0014\u00100\u001a\u00020.8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b0\u0010/R\u0014\u00101\u001a\u0002028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b3\u00104R\u0016\u00105\u001a\u0004\u0018\u0001068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b7\u00108R\u0014\u00109\u001a\u00020:8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b;\u0010<R\u0014\u0010=\u001a\u00020>8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b?\u0010@R\u0014\u0010A\u001a\u00020B8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bC\u0010DR\u0014\u0010E\u001a\u00020F8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bG\u0010HR(\u0010K\u001a\u0004\u0018\u00010J2\b\u0010I\u001a\u0004\u0018\u00010J8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bL\u0010M\"\u0004\bN\u0010OR$\u0010P\u001a\u00020\u00122\u0006\u0010I\u001a\u00020\u00128V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bQ\u0010\u0014\"\u0004\bR\u0010SR$\u0010U\u001a\u00020T2\u0006\u0010I\u001a\u00020T8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u0014\u0010Z\u001a\u00020[8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\\\u0010]R\u0014\u0010^\u001a\u00020_8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b`\u0010aR\u0016\u0010b\u001a\u0004\u0018\u00010c8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bd\u0010eR\u0016\u0010f\u001a\u0004\u0018\u00010g8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bh\u0010iR\u0014\u0010j\u001a\u00020k8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bl\u0010mR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\bn\u0010o\u00a8\u0006y"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/MinecraftImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "wrapped", "Lnet/minecraft/client/Minecraft;", "(Lnet/minecraft/client/Minecraft;)V", "currentScreen", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "getCurrentScreen", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "currentServerData", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IServerData;", "getCurrentServerData", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IServerData;", "dataDir", "Ljava/io/File;", "getDataDir", "()Ljava/io/File;", "debugFPS", "", "getDebugFPS", "()I", "displayHeight", "getDisplayHeight", "displayWidth", "getDisplayWidth", "effectRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/particle/IParticleManager;", "getEffectRenderer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/particle/IParticleManager;", "entityRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IEntityRenderer;", "getEntityRenderer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IEntityRenderer;", "fontRendererObj", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "getFontRendererObj", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "framebuffer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IFramebuffer;", "getFramebuffer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IFramebuffer;", "gameSettings", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "getGameSettings", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "isFullScreen", "", "()Z", "isIntegratedServerRunning", "netHandler", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "getNetHandler", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "objectMouseOver", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "getObjectMouseOver", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "playerController", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IPlayerControllerMP;", "getPlayerController", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IPlayerControllerMP;", "renderGlobal", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IRenderGlobal;", "getRenderGlobal", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IRenderGlobal;", "renderItem", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/entity/IRenderItem;", "getRenderItem", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/entity/IRenderItem;", "renderManager", "Lnet/ccbluex/liquidbounce/api/minecraft/renderer/entity/IRenderManager;", "getRenderManager", "()Lnet/ccbluex/liquidbounce/api/minecraft/renderer/entity/IRenderManager;", PropertyDescriptor.VALUE, "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "renderViewEntity", "getRenderViewEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "setRenderViewEntity", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;)V", "rightClickDelayTimer", "getRightClickDelayTimer", "setRightClickDelayTimer", "(I)V", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;", "session", "getSession", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;", "setSession", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;)V", "soundHandler", "Lnet/ccbluex/liquidbounce/api/minecraft/client/audio/ISoundHandler;", "getSoundHandler", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/audio/ISoundHandler;", "textureManager", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/ITextureManager;", "getTextureManager", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/ITextureManager;", "thePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "getThePlayer", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "theWorld", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "getTheWorld", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "timer", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ITimer;", "getTimer", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/ITimer;", "getWrapped", "()Lnet/minecraft/client/Minecraft;", "displayGuiScreen", "", "screen", "equals", "other", "", "rightClickMouse", "shutdown", "toggleFullscreen", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/MinecraftImpl.class */
public final class MinecraftImpl implements IMinecraft {

    @NotNull
    private final Minecraft wrapped;

    @NotNull
    public final Minecraft getWrapped() {
        return this.wrapped;
    }

    public MinecraftImpl(@NotNull Minecraft wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public IFramebuffer getFramebuffer() {
        Framebuffer framebufferFunc_147110_a = this.wrapped.func_147110_a();
        Intrinsics.checkExpressionValueIsNotNull(framebufferFunc_147110_a, "wrapped.framebuffer");
        return new FramebufferImpl(framebufferFunc_147110_a);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public boolean isFullScreen() {
        return this.wrapped.func_71372_G();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public File getDataDir() {
        File file = this.wrapped.field_71412_D;
        Intrinsics.checkExpressionValueIsNotNull(file, "wrapped.mcDataDir");
        return file;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public int getDebugFPS() {
        return Minecraft.func_175610_ah();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public IRenderGlobal getRenderGlobal() {
        RenderGlobal renderGlobal = this.wrapped.field_71438_f;
        Intrinsics.checkExpressionValueIsNotNull(renderGlobal, "wrapped.renderGlobal");
        return new RenderGlobalImpl(renderGlobal);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public IRenderItem getRenderItem() {
        RenderItem renderItemFunc_175599_af = this.wrapped.func_175599_af();
        Intrinsics.checkExpressionValueIsNotNull(renderItemFunc_175599_af, "wrapped.renderItem");
        return new RenderItemImpl(renderItemFunc_175599_af);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public int getDisplayWidth() {
        return this.wrapped.field_71443_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public int getDisplayHeight() {
        return this.wrapped.field_71440_d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public IEntityRenderer getEntityRenderer() {
        EntityRenderer entityRenderer = this.wrapped.field_71460_t;
        Intrinsics.checkExpressionValueIsNotNull(entityRenderer, "wrapped.entityRenderer");
        return new EntityRendererImpl(entityRenderer);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public int getRightClickDelayTimer() {
        return this.wrapped.field_71467_ac;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public void setRightClickDelayTimer(int i) {
        this.wrapped.field_71467_ac = i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public ISession getSession() {
        Session session = this.wrapped.field_71449_j;
        Intrinsics.checkExpressionValueIsNotNull(session, "wrapped.session");
        return new SessionImpl(session);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public void setSession(@NotNull ISession value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.wrapped.field_71449_j = ((SessionImpl) value).getWrapped();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public ISoundHandler getSoundHandler() {
        SoundHandler soundHandlerFunc_147118_V = this.wrapped.func_147118_V();
        Intrinsics.checkExpressionValueIsNotNull(soundHandlerFunc_147118_V, "wrapped.soundHandler");
        return new SoundHandlerImpl(soundHandlerFunc_147118_V);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @Nullable
    public IMovingObjectPosition getObjectMouseOver() {
        RayTraceResult rayTraceResult = this.wrapped.field_71476_x;
        if (rayTraceResult != null) {
            return new MovingObjectPositionImpl(rayTraceResult);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public ITimer getTimer() {
        Timer timer = this.wrapped.field_71428_T;
        Intrinsics.checkExpressionValueIsNotNull(timer, "wrapped.timer");
        return new TimerImpl(timer);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public IRenderManager getRenderManager() {
        RenderManager renderManagerFunc_175598_ae = this.wrapped.func_175598_ae();
        Intrinsics.checkExpressionValueIsNotNull(renderManagerFunc_175598_ae, "wrapped.renderManager");
        return new RenderManagerImpl(renderManagerFunc_175598_ae);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public IPlayerControllerMP getPlayerController() {
        PlayerControllerMP playerControllerMP = this.wrapped.field_71442_b;
        Intrinsics.checkExpressionValueIsNotNull(playerControllerMP, "wrapped.playerController");
        return new PlayerControllerMPImpl(playerControllerMP);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @Nullable
    public IGuiScreen getCurrentScreen() {
        GuiScreen guiScreen = this.wrapped.field_71462_r;
        if (guiScreen != null) {
            return new GuiScreenImpl(guiScreen);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @Nullable
    public IEntity getRenderViewEntity() {
        Entity entityFunc_175606_aa = this.wrapped.func_175606_aa();
        if (entityFunc_175606_aa != null) {
            return new EntityImpl(entityFunc_175606_aa);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public void setRenderViewEntity(@Nullable IEntity iEntity) {
        Entity wrapped;
        Minecraft minecraft = this.wrapped;
        if (iEntity == null) {
            wrapped = null;
        } else {
            if (iEntity == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.EntityImpl<*>");
            }
            minecraft = minecraft;
            wrapped = ((EntityImpl) iEntity).getWrapped();
        }
        minecraft.func_175607_a(wrapped);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public IINetHandlerPlayClient getNetHandler() {
        NetHandlerPlayClient netHandlerPlayClientFunc_147114_u = this.wrapped.func_147114_u();
        if (netHandlerPlayClientFunc_147114_u == null) {
            Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(netHandlerPlayClientFunc_147114_u, "wrapped.connection!!");
        return new INetHandlerPlayClientImpl(netHandlerPlayClientFunc_147114_u);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @Nullable
    public IWorldClient getTheWorld() {
        WorldClient worldClient = this.wrapped.field_71441_e;
        if (worldClient != null) {
            return new WorldClientImpl(worldClient);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @Nullable
    public IEntityPlayerSP getThePlayer() {
        EntityPlayerSP entityPlayerSP = this.wrapped.field_71439_g;
        if (entityPlayerSP != null) {
            return new EntityPlayerSPImpl(entityPlayerSP);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public ITextureManager getTextureManager() {
        TextureManager textureManagerFunc_110434_K = this.wrapped.func_110434_K();
        Intrinsics.checkExpressionValueIsNotNull(textureManagerFunc_110434_K, "wrapped.textureManager");
        return new TextureManagerImpl(textureManagerFunc_110434_K);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public boolean isIntegratedServerRunning() {
        return this.wrapped.func_71387_A();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @Nullable
    public IServerData getCurrentServerData() {
        ServerData serverDataFunc_147104_D = this.wrapped.func_147104_D();
        if (serverDataFunc_147104_D != null) {
            return new ServerDataImpl(serverDataFunc_147104_D);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public IGameSettings getGameSettings() {
        GameSettings gameSettings = this.wrapped.field_71474_y;
        Intrinsics.checkExpressionValueIsNotNull(gameSettings, "wrapped.gameSettings");
        return new GameSettingsImpl(gameSettings);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public IFontRenderer getFontRendererObj() {
        FontRenderer fontRenderer = this.wrapped.field_71466_p;
        Intrinsics.checkExpressionValueIsNotNull(fontRenderer, "wrapped.fontRenderer");
        return new FontRendererImpl(fontRenderer);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    @NotNull
    public IParticleManager getEffectRenderer() {
        ParticleManager particleManager = this.wrapped.field_71452_i;
        Intrinsics.checkExpressionValueIsNotNull(particleManager, "wrapped.effectRenderer");
        return new ParticleManagerImpl(particleManager);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public void displayGuiScreen(@Nullable IGuiScreen iGuiScreen) {
        GuiScreen guiScreen;
        Minecraft minecraft = this.wrapped;
        if (iGuiScreen == null) {
            guiScreen = null;
        } else {
            if (iGuiScreen == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.GuiScreenImpl<*>");
            }
            minecraft = minecraft;
            guiScreen = (GuiScreen) ((GuiScreenImpl) iGuiScreen).getWrapped();
        }
        minecraft.func_147108_a(guiScreen);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public void rightClickMouse() {
        this.wrapped.func_147121_ag();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public void shutdown() {
        this.wrapped.func_71400_g();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft
    public void toggleFullscreen() {
        this.wrapped.func_71352_k();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof MinecraftImpl) && Intrinsics.areEqual(((MinecraftImpl) obj).wrapped, this.wrapped);
    }
}
