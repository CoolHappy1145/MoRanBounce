package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\f\u0018\ufffd\ufffd2\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0002\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0014\u0010\u0012\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/RenderEntityEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "x", "", "y", "z", "entityYaw", "", "partialTicks", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;DDDFF)V", "getEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getEntityYaw", "()F", "getPartialTicks", "getX", "()D", "getY", "getZ", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/RenderEntityEvent.class */
public final class RenderEntityEvent extends Event {

    @NotNull
    private final IEntity entity;

    /* renamed from: x */
    private final double f117x;

    /* renamed from: y */
    private final double f118y;

    /* renamed from: z */
    private final double f119z;
    private final float entityYaw;
    private final float partialTicks;

    @NotNull
    public final IEntity getEntity() {
        return this.entity;
    }

    public final double getX() {
        return this.f117x;
    }

    public final double getY() {
        return this.f118y;
    }

    public final double getZ() {
        return this.f119z;
    }

    public final float getEntityYaw() {
        return this.entityYaw;
    }

    public RenderEntityEvent(@NotNull IEntity entity, double d, double d2, double d3, float f, float f2) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        this.entity = entity;
        this.f117x = d;
        this.f118y = d2;
        this.f119z = d3;
        this.entityYaw = f;
        this.partialTicks = f2;
    }

    public final float getPartialTicks() {
        return this.partialTicks;
    }
}
