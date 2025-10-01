package net.ccbluex.liquidbounce.cape;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.render.WIImageBuffer;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/cape/CapeAPI;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "capeService", "Lnet/ccbluex/liquidbounce/cape/CapeService;", "hasCapeService", "", "loadCape", "Lnet/ccbluex/liquidbounce/cape/CapeInfo;", "uuid", "Ljava/util/UUID;", "registerCapeService", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/cape/CapeAPI.class */
public final class CapeAPI extends MinecraftInstance {
    private static CapeService capeService;
    public static final CapeAPI INSTANCE = new CapeAPI();

    private CapeAPI() {
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public final void registerCapeService() {
        JsonElement jsonElement = new JsonParser().parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/capes.json"));
        Intrinsics.checkExpressionValueIsNotNull(jsonElement, "JsonParser()\n           \u2026IENT_CLOUD}/capes.json\"))");
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        JsonElement jsonElement2 = asJsonObject.get("serviceType");
        Intrinsics.checkExpressionValueIsNotNull(jsonElement2, "jsonObject.get(\"serviceType\")");
        String serviceType = jsonElement2.getAsString();
        Intrinsics.checkExpressionValueIsNotNull(serviceType, "serviceType");
        if (serviceType == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = serviceType.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case 96794:
                if (lowerCase.equals("api")) {
                    JsonElement jsonElement3 = asJsonObject.get("api");
                    Intrinsics.checkExpressionValueIsNotNull(jsonElement3, "jsonObject.get(\"api\")");
                    JsonElement jsonElement4 = jsonElement3.getAsJsonObject().get("url");
                    Intrinsics.checkExpressionValueIsNotNull(jsonElement4, "jsonObject.get(\"api\").asJsonObject.get(\"url\")");
                    String url = jsonElement4.getAsString();
                    Intrinsics.checkExpressionValueIsNotNull(url, "url");
                    capeService = new ServiceAPI(url);
                    ClientUtils.getLogger().info("Registered " + url + " as '" + serviceType + "' service type.");
                    break;
                }
                break;
            case 3322014:
                if (lowerCase.equals("list")) {
                    HashMap map = new HashMap();
                    JsonElement jsonElement5 = asJsonObject.get("users");
                    Intrinsics.checkExpressionValueIsNotNull(jsonElement5, "jsonObject.get(\"users\")");
                    for (Map.Entry entry : jsonElement5.getAsJsonObject().entrySet()) {
                        String key = (String) entry.getKey();
                        JsonElement value = (JsonElement) entry.getValue();
                        Intrinsics.checkExpressionValueIsNotNull(key, "key");
                        Intrinsics.checkExpressionValueIsNotNull(value, "value");
                        String asString = value.getAsString();
                        Intrinsics.checkExpressionValueIsNotNull(asString, "value.asString");
                        map.put(key, asString);
                        ClientUtils.getLogger().info("Loaded user cape for '" + key + "'.");
                    }
                    capeService = new ServiceList(map);
                    ClientUtils.getLogger().info("Registered '" + serviceType + "' service type.");
                    break;
                }
                break;
        }
        ClientUtils.getLogger().info("Loaded.");
    }

    @Nullable
    public final CapeInfo loadCape(@NotNull UUID uuid) {
        String cape;
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        CapeService capeService2 = capeService;
        if (capeService2 == null || (cape = capeService2.getCape(uuid)) == null) {
            return null;
        }
        IClassProvider classProvider = LiquidBounce.INSTANCE.getWrapper().getClassProvider();
        Object[] objArr = {uuid.toString()};
        String str = String.format("capes/%s.png", Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.String.format(this, *args)");
        IResourceLocation iResourceLocationCreateResourceLocation = classProvider.createResourceLocation(str);
        final CapeInfo capeInfo = new CapeInfo(iResourceLocationCreateResourceLocation, false, 2, null);
        MinecraftInstance.f157mc.getTextureManager().loadTexture(iResourceLocationCreateResourceLocation, LiquidBounce.INSTANCE.getWrapper().getClassProvider().createThreadDownloadImageData(null, cape, null, new WIImageBuffer(capeInfo) { // from class: net.ccbluex.liquidbounce.cape.CapeAPI$loadCape$threadDownloadImageData$1
            final CapeInfo $capeInfo;

            {
                this.$capeInfo = capeInfo;
            }

            @Override // net.ccbluex.liquidbounce.api.minecraft.client.render.WIImageBuffer
            public void skinAvailable() {
                this.$capeInfo.setCapeAvailable(true);
            }
        }));
        return capeInfo;
    }

    public final boolean hasCapeService() {
        return capeService != null;
    }
}
