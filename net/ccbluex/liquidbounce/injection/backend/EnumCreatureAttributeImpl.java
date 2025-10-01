package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.entity.IEnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureAttribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/EnumCreatureAttributeImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/IEnumCreatureAttribute;", "wrapped", "Lnet/minecraft/entity/EnumCreatureAttribute;", "(Lnet/minecraft/entity/EnumCreatureAttribute;)V", "getWrapped", "()Lnet/minecraft/entity/EnumCreatureAttribute;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/EnumCreatureAttributeImpl.class */
public final class EnumCreatureAttributeImpl implements IEnumCreatureAttribute {

    @NotNull
    private final EnumCreatureAttribute wrapped;

    @NotNull
    public final EnumCreatureAttribute getWrapped() {
        return this.wrapped;
    }

    public EnumCreatureAttributeImpl(@NotNull EnumCreatureAttribute wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof EnumCreatureAttributeImpl) && ((EnumCreatureAttributeImpl) obj).wrapped == this.wrapped;
    }
}
