package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IAbstractClientPlayer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0016\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/AbstractClientPlayerImpl;", "T", "Lnet/minecraft/client/entity/AbstractClientPlayer;", "Lnet/ccbluex/liquidbounce/injection/backend/EntityPlayerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IAbstractClientPlayer;", "wrapped", "(Lnet/minecraft/client/entity/AbstractClientPlayer;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/AbstractClientPlayerImpl.class */
public class AbstractClientPlayerImpl extends EntityPlayerImpl implements IAbstractClientPlayer {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractClientPlayerImpl(@NotNull AbstractClientPlayer wrapped) {
        super((EntityPlayer) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }
}
