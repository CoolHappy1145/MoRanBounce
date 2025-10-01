package net.ccbluex.liquidbounce.cape;

import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/cape/ServiceList;", "Lnet/ccbluex/liquidbounce/cape/CapeService;", "users", "", "", "(Ljava/util/Map;)V", "getCape", "uuid", "Ljava/util/UUID;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/cape/ServiceList.class */
public final class ServiceList implements CapeService {
    private final Map users;

    public ServiceList(@NotNull Map users) {
        Intrinsics.checkParameterIsNotNull(users, "users");
        this.users = users;
    }

    @Override // net.ccbluex.liquidbounce.cape.CapeService
    @Nullable
    public String getCape(@NotNull UUID uuid) {
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        Map map = this.users;
        String string = uuid.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "uuid.toString()");
        return (String) map.get(StringsKt.replace$default(string, "-", "", false, 4, (Object) null));
    }
}
