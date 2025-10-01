package net.ccbluex.liquidbounce.cape;

import java.util.Arrays;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/cape/ServiceAPI;", "Lnet/ccbluex/liquidbounce/cape/CapeService;", "baseURL", "", "(Ljava/lang/String;)V", "getCape", "uuid", "Ljava/util/UUID;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/cape/ServiceAPI.class */
public final class ServiceAPI implements CapeService {
    private final String baseURL;

    public ServiceAPI(@NotNull String baseURL) {
        Intrinsics.checkParameterIsNotNull(baseURL, "baseURL");
        this.baseURL = baseURL;
    }

    @Override // net.ccbluex.liquidbounce.cape.CapeService
    @NotNull
    public String getCape(@NotNull UUID uuid) {
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {uuid};
        String str = String.format(this.baseURL, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.String.format(format, *args)");
        return str;
    }
}
