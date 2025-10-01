package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import io.netty.buffer.Unpooled;
import java.net.URI;
import java.net.URISyntaxException;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EntityMovementEvent;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.injection.backend.EntityImplKt;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetHandlerPlayClient.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/network/MixinNetHandlerPlayClient.class */
public abstract class MixinNetHandlerPlayClient {

    @Shadow
    public int field_147304_c;

    @Shadow
    @Final
    private NetworkManager field_147302_e;

    @Shadow
    private Minecraft field_147299_f;

    @Shadow
    private WorldClient field_147300_g;

    @Inject(method = {"handleResourcePack"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void handleResourcePack(SPacketResourcePackSend sPacketResourcePackSend, CallbackInfo callbackInfo) throws URISyntaxException {
        String strFunc_179783_a = sPacketResourcePackSend.func_179783_a();
        sPacketResourcePackSend.func_179784_b();
        try {
            String scheme = new URI(strFunc_179783_a).getScheme();
            boolean zEquals = "level".equals(scheme);
            if (!"http".equals(scheme) && !"https".equals(scheme) && !zEquals) {
                throw new URISyntaxException(strFunc_179783_a, "Wrong protocol");
            }
            if (zEquals && (strFunc_179783_a.contains("..") || !strFunc_179783_a.endsWith("/resources.zip"))) {
                throw new URISyntaxException(strFunc_179783_a, "Invalid levelstorage resourcepack path");
            }
        } catch (URISyntaxException e) {
            ClientUtils.getLogger().error("Failed to handle resource pack", e);
            this.field_147302_e.func_179290_a(new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"handleJoinGame"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void handleJoinGameWithAntiForge(SPacketJoinGame sPacketJoinGame, CallbackInfo callbackInfo) {
        if (!AntiForge.enabled || !AntiForge.blockFML || Minecraft.func_71410_x().func_71387_A()) {
            return;
        }
        PacketThreadUtil.func_180031_a(sPacketJoinGame, (NetHandlerPlayClient) this, this.field_147299_f);
        this.field_147299_f.field_71442_b = new PlayerControllerMP(this.field_147299_f, (NetHandlerPlayClient) this);
        this.field_147300_g = new WorldClient((NetHandlerPlayClient) this, new WorldSettings(0L, sPacketJoinGame.func_149198_e(), false, sPacketJoinGame.func_149195_d(), sPacketJoinGame.func_149196_i()), sPacketJoinGame.func_149194_f(), sPacketJoinGame.func_149192_g(), this.field_147299_f.field_71424_I);
        this.field_147299_f.field_71474_y.field_74318_M = sPacketJoinGame.func_149192_g();
        this.field_147299_f.func_71403_a(this.field_147300_g);
        this.field_147299_f.field_71439_g.field_71093_bK = sPacketJoinGame.func_149194_f();
        this.field_147299_f.func_147108_a(new GuiDownloadTerrain());
        this.field_147299_f.field_71439_g.func_145769_d(sPacketJoinGame.func_149197_c());
        this.field_147304_c = sPacketJoinGame.func_149193_h();
        this.field_147299_f.field_71439_g.func_175150_k(sPacketJoinGame.func_179744_h());
        this.field_147299_f.field_71442_b.func_78746_a(sPacketJoinGame.func_149198_e());
        this.field_147299_f.field_71474_y.func_82879_c();
        this.field_147302_e.func_179290_a(new CPacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).func_180714_a(ClientBrandRetriever.getClientModName())));
        callbackInfo.cancel();
    }

    @Inject(method = {"handleEntityMovement"}, m59at = {@InterfaceC0563At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;onGround:Z")})
    private void handleEntityMovementEvent(SPacketEntity sPacketEntity, CallbackInfo callbackInfo) {
        Entity entityFunc_149065_a = sPacketEntity.func_149065_a(this.field_147300_g);
        if (entityFunc_149065_a != null) {
            LiquidBounce.eventManager.callEvent(new EntityMovementEvent(EntityImplKt.wrap(entityFunc_149065_a)));
        }
    }
}
