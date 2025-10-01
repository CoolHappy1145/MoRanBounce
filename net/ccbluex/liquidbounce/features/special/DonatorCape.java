package net.ccbluex.liquidbounce.features.special;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.sub.GuiDonatorCape;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007\u00a8\u0006\n"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/special/DonatorCape;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "handleEvents", "", "onSession", "", "event", "Lnet/ccbluex/liquidbounce/event/SessionEvent;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/special/DonatorCape.class */
public final class DonatorCape extends MinecraftInstance implements Listenable {
    @EventTarget
    public final void onSession(@NotNull SessionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (GuiDonatorCape.Companion.getCapeEnabled()) {
            if ((GuiDonatorCape.Companion.getTransferCode().length() == 0) || !UserUtils.INSTANCE.isValidTokenOffline(MinecraftInstance.f157mc.getSession().getToken())) {
                return;
            }
            ThreadsKt.thread$default(false, false, null, null, 0, new Function0() { // from class: net.ccbluex.liquidbounce.features.special.DonatorCape.onSession.1
                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    m1610invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m1610invoke() {
                    String str;
                    String playerId = MinecraftInstance.f157mc.getSession().getPlayerId();
                    String username = MinecraftInstance.f157mc.getSession().getUsername();
                    CloseableHttpClient closeableHttpClientCreateDefault = HttpClients.createDefault();
                    BasicHeader[] basicHeaderArr = {new BasicHeader("Content-Type", "application/json"), new BasicHeader("Authorization", GuiDonatorCape.Companion.getTransferCode())};
                    HttpUriRequest httpPatch = new HttpPatch("http://capes.liquidbounce.net/api/v1/cape/self");
                    httpPatch.setHeaders(basicHeaderArr);
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("uuid", playerId);
                    httpPatch.setEntity(new StringEntity(jSONObject.toString()));
                    CloseableHttpResponse response = closeableHttpClientCreateDefault.execute(httpPatch);
                    Intrinsics.checkExpressionValueIsNotNull(response, "response");
                    StatusLine statusLine = response.getStatusLine();
                    Intrinsics.checkExpressionValueIsNotNull(statusLine, "response.statusLine");
                    int statusCode = statusLine.getStatusCode();
                    Logger logger = ClientUtils.getLogger();
                    if (statusCode == 204) {
                        str = "[Donator Cape] Successfully transferred cape to " + playerId + " (" + username + ')';
                    } else {
                        str = "[Donator Cape] Failed to transfer cape (" + statusCode + ')';
                    }
                    logger.info(str);
                }
            }, 31, null);
        }
    }
}
