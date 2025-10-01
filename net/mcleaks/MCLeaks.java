package net.mcleaks;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/mcleaks/MCLeaks.class */
public class MCLeaks {
    private static Session session;
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static final Gson gson = new Gson();

    public static boolean isAltActive() {
        return session != null;
    }

    public static Session getSession() {
        return session;
    }

    public static void refresh(Session session2) {
        session = session2;
    }

    public static void remove() {
        session = null;
    }

    public static void redeem(String str, Callback callback) {
        EXECUTOR_SERVICE.execute(() -> {
            lambda$redeem$0(r1, r2);
        });
    }

    private static void lambda$redeem$0(String str, Callback callback) throws IOException {
        URLConnection uRLConnectionPreparePostRequest = preparePostRequest("{\"token\":\"" + str + "\"}");
        if (uRLConnectionPreparePostRequest == null) {
            callback.done("An error occurred! [R1]");
            return;
        }
        Object result = getResult(uRLConnectionPreparePostRequest);
        if (result instanceof String) {
            callback.done(result);
            return;
        }
        JsonObject jsonObject = (JsonObject) result;
        if (jsonObject == null) {
            return;
        }
        if (!jsonObject.has("mcname") || !jsonObject.has("session")) {
            callback.done("An error occurred! [R2]");
        } else {
            callback.done(new RedeemResponse(jsonObject.get("mcname").getAsString(), jsonObject.get("session").getAsString()));
        }
    }

    private static URLConnection preparePostRequest(String str) throws IOException {
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL("https://auth.mcleaks.net/v1/redeem").openConnection();
            httpsURLConnection.setConnectTimeout(10000);
            httpsURLConnection.setReadTimeout(10000);
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201");
            httpsURLConnection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
            dataOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
            dataOutputStream.flush();
            dataOutputStream.close();
            return httpsURLConnection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object getResult(URLConnection uRLConnection) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
            bufferedReader.close();
            JsonElement jsonElement = (JsonElement) gson.fromJson(sb.toString(), JsonElement.class);
            if (!jsonElement.isJsonObject() || !jsonElement.getAsJsonObject().has("success")) {
                return "An error occurred! [G1]";
            }
            if (!jsonElement.getAsJsonObject().get("success").getAsBoolean()) {
                return jsonElement.getAsJsonObject().has("errorMessage") ? jsonElement.getAsJsonObject().get("errorMessage").getAsString() : "An error occurred! [G4]";
            }
            if (!jsonElement.getAsJsonObject().has("result")) {
                return "An error occurred! [G3]";
            }
            if (jsonElement.getAsJsonObject().get("result").isJsonObject()) {
                return jsonElement.getAsJsonObject().get("result").getAsJsonObject();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred! [G2]";
        }
    }
}
