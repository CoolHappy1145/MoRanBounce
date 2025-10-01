package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.entity.p004ai.attributes.IAttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/AttributeModifierImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/ai/attributes/IAttributeModifier;", "wrapped", "Lnet/minecraft/entity/ai/attributes/AttributeModifier;", "(Lnet/minecraft/entity/ai/attributes/AttributeModifier;)V", "amount", "", "getAmount", "()D", "getWrapped", "()Lnet/minecraft/entity/ai/attributes/AttributeModifier;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/AttributeModifierImpl.class */
public final class AttributeModifierImpl implements IAttributeModifier {

    @NotNull
    private final AttributeModifier wrapped;

    @NotNull
    public final AttributeModifier getWrapped() {
        return this.wrapped;
    }

    public AttributeModifierImpl(@NotNull AttributeModifier wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.p004ai.attributes.IAttributeModifier
    public double getAmount() {
        return this.wrapped.func_111164_d();
    }
}
