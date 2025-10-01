package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import jdk.internal.dynalink.CallSiteDescriptor;
import net.ccbluex.liquidbounce.injection.backend.ISPacketEncryptionRequestKt;
import net.ccbluex.liquidbounce.injection.backend.NetworkManagerImplKt;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.mcleaks.MCLeaks;
import net.mcleaks.Session;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import net.minecraft.util.CryptManager;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetHandlerLoginClient.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/network/MixinNetHandlerLoginClient.class */
public class MixinNetHandlerLoginClient {

    @Shadow
    @Final
    private NetworkManager field_147393_d;

    @Inject(method = {"handleEncryptionRequest"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void handleEncryptionRequest(SPacketEncryptionRequest sPacketEncryptionRequest, CallbackInfo callbackInfo) throws IOException {
        if (MCLeaks.isAltActive()) {
            SecretKey secretKeyFunc_75890_a = CryptManager.func_75890_a();
            String strFunc_149609_c = sPacketEncryptionRequest.func_149609_c();
            PublicKey publicKeyFunc_149608_d = sPacketEncryptionRequest.func_149608_d();
            String string = new BigInteger(CryptManager.func_75895_a(strFunc_149609_c, publicKeyFunc_149608_d, secretKeyFunc_75890_a)).toString(16);
            Session session = MCLeaks.getSession();
            try {
                String str = "{\"session\":\"" + session.getToken() + "\",\"mcname\":\"" + session.getUsername() + "\",\"serverhash\":\"" + string + "\",\"server\":\"" + (((InetSocketAddress) this.field_147393_d.func_74430_c()).getHostName() + CallSiteDescriptor.TOKEN_DELIMITER + ((InetSocketAddress) this.field_147393_d.func_74430_c()).getPort()) + "\"}";
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://auth.mcleaks.net/v1/joinserver").openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                httpURLConnection.setDoOutput(true);
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
                dataOutputStream.flush();
                dataOutputStream.close();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    } else {
                        sb.append(line);
                    }
                }
                bufferedReader.close();
                JsonElement jsonElement = (JsonElement) new Gson().fromJson(sb.toString(), JsonElement.class);
                if (!jsonElement.isJsonObject() || !jsonElement.getAsJsonObject().has("success")) {
                    this.field_147393_d.func_150718_a(new TextComponentString("Invalid response from MCLeaks API"));
                    callbackInfo.cancel();
                } else {
                    if (!jsonElement.getAsJsonObject().get("success").getAsBoolean()) {
                        String asString = "Received success=false from MCLeaks API";
                        if (jsonElement.getAsJsonObject().has("errorMessage")) {
                            asString = jsonElement.getAsJsonObject().get("errorMessage").getAsString();
                        }
                        this.field_147393_d.func_150718_a(new TextComponentString(asString));
                        callbackInfo.cancel();
                        return;
                    }
                    ClientUtils.sendEncryption(NetworkManagerImplKt.wrap(this.field_147393_d), secretKeyFunc_75890_a, publicKeyFunc_149608_d, ISPacketEncryptionRequestKt.wrap(sPacketEncryptionRequest));
                    callbackInfo.cancel();
                }
            } catch (Exception e) {
                this.field_147393_d.func_150718_a(new TextComponentString("Error whilst contacting MCLeaks API: " + e.toString()));
                callbackInfo.cancel();
            }
        }
    }
}
