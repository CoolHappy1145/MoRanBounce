package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/entity/Entity;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "invoke"})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldClientImpl$loadedEntityList$1.class */
final /* synthetic */ class WorldClientImpl$loadedEntityList$1 extends FunctionReference implements Function1 {
    public static final WorldClientImpl$loadedEntityList$1 INSTANCE = new WorldClientImpl$loadedEntityList$1();

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(EntityImplKt.class, LiquidBounce.CLIENT_NAME);
    }

    WorldClientImpl$loadedEntityList$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object obj) {
        return invoke((IEntity) obj);
    }

    @NotNull
    public final Entity invoke(@NotNull IEntity p1) {
        Intrinsics.checkParameterIsNotNull(p1, "p1");
        return ((EntityImpl) p1).getWrapped();
    }
}
