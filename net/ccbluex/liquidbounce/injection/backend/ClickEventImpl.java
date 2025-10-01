package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.event.IClickEvent;
import net.minecraft.util.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ClickEventImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;", "wrapped", "Lnet/minecraft/util/text/event/ClickEvent;", "(Lnet/minecraft/util/text/event/ClickEvent;)V", "getWrapped", "()Lnet/minecraft/util/text/event/ClickEvent;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ClickEventImpl.class */
public final class ClickEventImpl implements IClickEvent {

    @NotNull
    private final ClickEvent wrapped;

    @NotNull
    public final ClickEvent getWrapped() {
        return this.wrapped;
    }

    public ClickEventImpl(@NotNull ClickEvent wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ClickEventImpl) && Intrinsics.areEqual(((ClickEventImpl) obj).wrapped, this.wrapped);
    }
}
