package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import com.mojang.authlib.GameProfile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import jdk.internal.dynalink.CallSiteDescriptor;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.injection.backend.ServerDataImplKt;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.mcleaks.MCLeaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiConnecting.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiConnecting.class */
public abstract class MixinGuiConnecting extends GuiScreen {

    @Shadow
    @Final
    private static AtomicInteger field_146372_a;

    @Shadow
    @Final
    private static Logger field_146370_f;

    @Shadow
    private NetworkManager field_146371_g;

    @Shadow
    private boolean field_146373_h;

    @Shadow
    @Final
    private GuiScreen field_146374_i;

    @Inject(method = {"connect"}, m59at = {@InterfaceC0563At("HEAD")})
    private void headConnect(String str, int i, CallbackInfo callbackInfo) {
        ServerUtils.serverData = ServerDataImplKt.wrap(new ServerData("", str + CallSiteDescriptor.TOKEN_DELIMITER + i, false));
    }

    @Inject(method = {"connect"}, m59at = {@InterfaceC0563At(value = "NEW", target = "net/minecraft/network/login/client/CPacketLoginStart")}, cancellable = true)
    private void mcLeaks(CallbackInfo callbackInfo) {
        if (MCLeaks.isAltActive()) {
            this.field_146371_g.func_179290_a(new CPacketLoginStart(new GameProfile((UUID) null, MCLeaks.getSession().getUsername())));
            callbackInfo.cancel();
        }
    }

    @Overwrite
    private void func_146367_a(String str, int i) {
        field_146370_f.info("Connecting to " + str + ", " + i);
        new Thread(() -> {
            r2.lambda$connect$0(r3, r4);
        }, "Server Connector #" + field_146372_a.incrementAndGet()).start();
    }

    private void lambda$connect$0(String str, int i) throws UnknownHostException {
        InetAddress byName = null;
        try {
            if (this.field_146373_h) {
                return;
            }
            byName = InetAddress.getByName(str);
            this.field_146371_g = NetworkManager.func_181124_a(byName, i, this.field_146297_k.field_71474_y.func_181148_f());
            this.field_146371_g.func_150719_a(new NetHandlerLoginClient(this.field_146371_g, this.field_146297_k, this.field_146374_i));
            this.field_146371_g.func_179290_a(new C00Handshake(str, i, EnumConnectionState.LOGIN, true));
            this.field_146371_g.func_179290_a(new CPacketLoginStart(MCLeaks.isAltActive() ? new GameProfile((UUID) null, MCLeaks.getSession().getUsername()) : this.field_146297_k.func_110432_I().func_148256_e()));
            LiquidBounce.setPlayTimeStart(System.currentTimeMillis());
        } catch (UnknownHostException e) {
            if (this.field_146373_h) {
                return;
            }
            field_146370_f.error("Couldn't connect to server", e);
            this.field_146297_k.func_147108_a(new GuiDisconnected(this.field_146374_i, "connect.failed", new TextComponentTranslation("disconnect.genericReason", new Object[]{"Unknown host"})));
        } catch (Exception e2) {
            if (this.field_146373_h) {
                return;
            }
            field_146370_f.error("Couldn't connect to server", e2);
            String string = e2.toString();
            if (byName != null) {
                string = string.replaceAll(byName.toString() + CallSiteDescriptor.TOKEN_DELIMITER + i, "");
            }
            this.field_146297_k.func_147108_a(new GuiDisconnected(this.field_146374_i, "connect.failed", new TextComponentTranslation("disconnect.genericReason", new Object[]{string})));
        }
    }

    @Overwrite
    public void func_73863_a(int i, int i2, float f) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
        func_146276_q_();
        RenderUtils.drawLoadingCircle(scaledResolution.func_78326_a() / 2, (scaledResolution.func_78328_b() / 4) + 70);
        String str = "Unknown";
        ServerData serverDataFunc_147104_D = this.field_146297_k.func_147104_D();
        if (serverDataFunc_147104_D != null) {
            str = serverDataFunc_147104_D.field_78845_b;
        }
        Fonts.font40.drawCenteredString("Connecting to", scaledResolution.func_78326_a() / 2, (scaledResolution.func_78328_b() / 4) + 110, 16777215, true);
        Fonts.font35.drawCenteredString(str, scaledResolution.func_78326_a() / 2, (scaledResolution.func_78328_b() / 4) + 120, 5407227, true);
        super.func_73863_a(i, i2, f);
    }
}
