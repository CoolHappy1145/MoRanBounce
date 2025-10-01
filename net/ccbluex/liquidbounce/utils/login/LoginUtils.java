package net.ccbluex.liquidbounce.utils.login;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import java.util.Base64;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001:\u0001\fB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\b\u001a\u00020\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0006H\u0007\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/login/LoginUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "login", "Lnet/ccbluex/liquidbounce/utils/login/LoginUtils$LoginResult;", "username", "", "password", "loginCracked", "", "loginSessionId", "sessionId", "LoginResult", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/login/LoginUtils.class */
public final class LoginUtils extends MinecraftInstance {
    public static final LoginUtils INSTANCE = new LoginUtils();

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0001\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/login/LoginUtils$LoginResult;", "", "(Ljava/lang/String;I)V", "WRONG_PASSWORD", "NO_CONTACT", "INVALID_ACCOUNT_DATA", "MIGRATED", "LOGGED", "FAILED_PARSE_TOKEN", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/login/LoginUtils$LoginResult.class */
    public enum LoginResult {
        WRONG_PASSWORD,
        NO_CONTACT,
        INVALID_ACCOUNT_DATA,
        MIGRATED,
        LOGGED,
        FAILED_PARSE_TOKEN
    }

    private LoginUtils() {
    }

    @JvmStatic
    @NotNull
    public static final LoginResult login(@Nullable String str, @Nullable String str2) {
        LoginResult loginResult;
        LoginResult loginResult2;
        YggdrasilUserAuthentication yggdrasilUserAuthenticationCreateUserAuthentication = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        if (yggdrasilUserAuthenticationCreateUserAuthentication == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");
        }
        YggdrasilUserAuthentication yggdrasilUserAuthentication = yggdrasilUserAuthenticationCreateUserAuthentication;
        yggdrasilUserAuthentication.setUsername(str);
        yggdrasilUserAuthentication.setPassword(str2);
        try {
            yggdrasilUserAuthentication.logIn();
            IMinecraft iMinecraft = MinecraftInstance.f157mc;
            IClassProvider iClassProvider = MinecraftInstance.classProvider;
            GameProfile selectedProfile = yggdrasilUserAuthentication.getSelectedProfile();
            Intrinsics.checkExpressionValueIsNotNull(selectedProfile, "userAuthentication.selectedProfile");
            String name = selectedProfile.getName();
            Intrinsics.checkExpressionValueIsNotNull(name, "userAuthentication.selectedProfile.name");
            GameProfile selectedProfile2 = yggdrasilUserAuthentication.getSelectedProfile();
            Intrinsics.checkExpressionValueIsNotNull(selectedProfile2, "userAuthentication.selectedProfile");
            String string = selectedProfile2.getId().toString();
            Intrinsics.checkExpressionValueIsNotNull(string, "userAuthentication.selectedProfile.id.toString()");
            String authenticatedToken = yggdrasilUserAuthentication.getAuthenticatedToken();
            Intrinsics.checkExpressionValueIsNotNull(authenticatedToken, "userAuthentication.authenticatedToken");
            iMinecraft.setSession(iClassProvider.createSession(name, string, authenticatedToken, "mojang"));
            LiquidBounce.INSTANCE.getEventManager().callEvent(new SessionEvent());
            loginResult = LoginResult.LOGGED;
        } catch (AuthenticationException e) {
            String message = e.getMessage();
            if (message == null) {
                Intrinsics.throwNpe();
            }
            if (StringsKt.contains((CharSequence) message, (CharSequence) "invalid username or password.", true)) {
                loginResult2 = LoginResult.INVALID_ACCOUNT_DATA;
            } else {
                loginResult2 = StringsKt.contains((CharSequence) message, (CharSequence) "account migrated", true) ? LoginResult.MIGRATED : LoginResult.NO_CONTACT;
            }
            loginResult = loginResult2;
        } catch (NullPointerException unused) {
            loginResult = LoginResult.WRONG_PASSWORD;
        } catch (AuthenticationUnavailableException unused2) {
            loginResult = LoginResult.NO_CONTACT;
        }
        return loginResult;
    }

    @JvmStatic
    public static final void loginCracked(@Nullable String str) {
        IMinecraft iMinecraft = MinecraftInstance.f157mc;
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        if (str == null) {
            Intrinsics.throwNpe();
        }
        iMinecraft.setSession(iClassProvider.createSession(str, UserUtils.INSTANCE.getUUID(str), "-", "legacy"));
        LiquidBounce.INSTANCE.getEventManager().callEvent(new SessionEvent());
    }

    @JvmStatic
    @NotNull
    public static final LoginResult loginSessionId(@NotNull String sessionId) {
        Intrinsics.checkParameterIsNotNull(sessionId, "sessionId");
        try {
            byte[] bArrDecode = Base64.getDecoder().decode((String) StringsKt.split$default((CharSequence) sessionId, new String[]{"."}, false, 0, 6, (Object) null).get(1));
            Intrinsics.checkExpressionValueIsNotNull(bArrDecode, "Base64.getDecoder().deco\u2026(sessionId.split(\".\")[1])");
            try {
                JsonElement jsonElement = new JsonParser().parse(new String(bArrDecode, Charsets.UTF_8));
                Intrinsics.checkExpressionValueIsNotNull(jsonElement, "JsonParser().parse(decodedSessionData)");
                JsonObject asJsonObject = jsonElement.getAsJsonObject();
                JsonElement jsonElement2 = asJsonObject.get("spr");
                Intrinsics.checkExpressionValueIsNotNull(jsonElement2, "sessionObject.get(\"spr\")");
                String uuid = jsonElement2.getAsString();
                JsonElement jsonElement3 = asJsonObject.get("yggt");
                Intrinsics.checkExpressionValueIsNotNull(jsonElement3, "sessionObject.get(\"yggt\")");
                String accessToken = jsonElement3.getAsString();
                UserUtils userUtils = UserUtils.INSTANCE;
                Intrinsics.checkExpressionValueIsNotNull(accessToken, "accessToken");
                if (!userUtils.isValidToken(accessToken)) {
                    return LoginResult.INVALID_ACCOUNT_DATA;
                }
                UserUtils userUtils2 = UserUtils.INSTANCE;
                Intrinsics.checkExpressionValueIsNotNull(uuid, "uuid");
                String username = userUtils2.getUsername(uuid);
                if (username == null) {
                    return LoginResult.INVALID_ACCOUNT_DATA;
                }
                MinecraftInstance.f157mc.setSession(MinecraftInstance.classProvider.createSession(username, uuid, accessToken, "mojang"));
                LiquidBounce.INSTANCE.getEventManager().callEvent(new SessionEvent());
                return LoginResult.LOGGED;
            } catch (Exception unused) {
                return LoginResult.FAILED_PARSE_TOKEN;
            }
        } catch (Exception unused2) {
            return LoginResult.FAILED_PARSE_TOKEN;
        }
    }
}
