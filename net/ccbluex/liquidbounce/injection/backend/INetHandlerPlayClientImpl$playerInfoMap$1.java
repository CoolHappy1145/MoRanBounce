package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/client/network/NetworkPlayerInfo;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/INetworkPlayerInfo;", "invoke"})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/INetHandlerPlayClientImpl$playerInfoMap$1.class */
final /* synthetic */ class INetHandlerPlayClientImpl$playerInfoMap$1 extends FunctionReference implements Function1 {
    public static final INetHandlerPlayClientImpl$playerInfoMap$1 INSTANCE = new INetHandlerPlayClientImpl$playerInfoMap$1();

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(NetworkPlayerInfoImplKt.class, LiquidBounce.CLIENT_NAME);
    }

    INetHandlerPlayClientImpl$playerInfoMap$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object obj) {
        return invoke((INetworkPlayerInfo) obj);
    }

    @NotNull
    public final NetworkPlayerInfo invoke(@NotNull INetworkPlayerInfo p1) {
        Intrinsics.checkParameterIsNotNull(p1, "p1");
        return ((NetworkPlayerInfoImpl) p1).getWrapped();
    }
}
