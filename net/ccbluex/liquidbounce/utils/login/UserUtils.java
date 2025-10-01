package net.ccbluex.liquidbounce.utils.login;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.CloseableKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004J\u000e\u0010\u000b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0004\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/login/UserUtils;", "", "()V", "getUUID", "", "username", "getUsername", "uuid", "isValidToken", "", "token", "isValidTokenOffline", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/login/UserUtils.class */
public final class UserUtils {
    public static final UserUtils INSTANCE = new UserUtils();

    private UserUtils() {
    }

    public final boolean isValidTokenOffline(@NotNull String token) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        return token.length() >= 32;
    }

    public final boolean isValidToken(@NotNull String token) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        CloseableHttpClient closeableHttpClientCreateDefault = HttpClients.createDefault();
        BasicHeader[] basicHeaderArr = {new BasicHeader("Content-Type", "application/json")};
        HttpPost httpPost = new HttpPost("https://authserver.mojang.com/validate");
        httpPost.setHeaders(basicHeaderArr);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("accessToken", token);
        httpPost.setEntity(new StringEntity(jSONObject.toString()));
        CloseableHttpResponse response = closeableHttpClientCreateDefault.execute(httpPost);
        Intrinsics.checkExpressionValueIsNotNull(response, "response");
        StatusLine statusLine = response.getStatusLine();
        Intrinsics.checkExpressionValueIsNotNull(statusLine, "response.statusLine");
        return statusLine.getStatusCode() == 204;
    }

    @Nullable
    public final String getUsername(@NotNull String uuid) {
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet("https://api.mojang.com/user/profiles/" + uuid + "/names"));
        Intrinsics.checkExpressionValueIsNotNull(response, "response");
        StatusLine statusLine = response.getStatusLine();
        Intrinsics.checkExpressionValueIsNotNull(statusLine, "response.statusLine");
        if (statusLine.getStatusCode() != 200) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(EntityUtils.toString(response.getEntity()));
            return new JSONObject(jSONArray.get(jSONArray.length() - 1).toString()).getString("name");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @NotNull
    public final String getUUID(@NotNull String username) {
        Intrinsics.checkParameterIsNotNull(username, "username");
        try {
            URLConnection uRLConnectionOpenConnection = new URL("https://api.mojang.com/users/profiles/minecraft/" + username).openConnection();
            if (uRLConnectionOpenConnection == null) {
                throw new TypeCastException("null cannot be cast to non-null type javax.net.ssl.HttpsURLConnection");
            }
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uRLConnectionOpenConnection;
            httpsURLConnection.setConnectTimeout(2000);
            httpsURLConnection.setReadTimeout(2000);
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            HttpURLConnection.setFollowRedirects(true);
            httpsURLConnection.setDoOutput(true);
            if (httpsURLConnection.getResponseCode() != 200) {
                return "";
            }
            InputStreamReader inputStreamReader = new InputStreamReader(httpsURLConnection.getInputStream());
            Throwable th = (Throwable) null;
            try {
                JsonElement jsonElement = new JsonParser().parse(inputStreamReader);
                Intrinsics.checkExpressionValueIsNotNull(jsonElement, "jsonElement");
                if (jsonElement.isJsonObject()) {
                    JsonElement jsonElement2 = jsonElement.getAsJsonObject().get("id");
                    Intrinsics.checkExpressionValueIsNotNull(jsonElement2, "jsonElement.asJsonObject.get(\"id\")");
                    String asString = jsonElement2.getAsString();
                    Intrinsics.checkExpressionValueIsNotNull(asString, "jsonElement.asJsonObject.get(\"id\").asString");
                    CloseableKt.closeFinally(inputStreamReader, th);
                    return asString;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(inputStreamReader, th);
                return "";
            } catch (Throwable th2) {
                throw th2;
            }
        } catch (Throwable unused) {
            return "";
        }
    }
}
