package com.thealtening;

import com.thealtening.utilities.ReflectionUtility;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/* loaded from: L-out.jar:com/thealtening/AltService.class */
public class AltService {
    private final ReflectionUtility userAuthentication = new ReflectionUtility("com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");
    private final ReflectionUtility minecraftSession = new ReflectionUtility("com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService");
    private EnumAltService currentService;

    public void switchService(EnumAltService enumAltService) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (this.currentService == enumAltService) {
            return;
        }
        reflectionFields(enumAltService.hostname);
        this.currentService = enumAltService;
    }

    private void reflectionFields(String str) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        HashMap map = new HashMap();
        String str2 = str.contains("thealtening") ? "http" : "https";
        map.put("ROUTE_AUTHENTICATE", constantURL(str2 + "://authserver." + str + ".com/authenticate"));
        map.put("ROUTE_INVALIDATE", constantURL(str2 + "://authserver" + str + "com/invalidate"));
        map.put("ROUTE_REFRESH", constantURL(str2 + "://authserver." + str + ".com/refresh"));
        map.put("ROUTE_VALIDATE", constantURL(str2 + "://authserver." + str + ".com/validate"));
        map.put("ROUTE_SIGNOUT", constantURL(str2 + "://authserver." + str + ".com/signout"));
        map.forEach(this::lambda$reflectionFields$0);
        this.userAuthentication.setStaticField("BASE_URL", str2 + "://authserver." + str + ".com/");
        this.minecraftSession.setStaticField("BASE_URL", str2 + "://sessionserver." + str + ".com/session/minecraft/");
        this.minecraftSession.setStaticField("JOIN_URL", constantURL(str2 + "://sessionserver." + str + ".com/session/minecraft/join"));
        this.minecraftSession.setStaticField("CHECK_URL", constantURL(str2 + "://sessionserver." + str + ".com/session/minecraft/hasJoined"));
        this.minecraftSession.setStaticField("WHITELISTED_DOMAINS", new String[]{".minecraft.net", ".mojang.com", ".thealtening.com"});
    }

    private void lambda$reflectionFields$0(String str, URL url) {
        try {
            this.userAuthentication.setStaticField(str, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private URL constantURL(String str) {
        try {
            return new URL(str);
        } catch (MalformedURLException e) {
            throw new Error("Couldn't create constant for " + str, e);
        }
    }

    public EnumAltService getCurrentService() {
        if (this.currentService == null) {
            this.currentService = EnumAltService.MOJANG;
        }
        return this.currentService;
    }

    /* loaded from: L-out.jar:com/thealtening/AltService$EnumAltService.class */
    public enum EnumAltService {
        MOJANG("mojang"),
        THEALTENING("thealtening");

        String hostname;

        EnumAltService(String str) {
            this.hostname = str;
        }
    }
}
