package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\b\u0006"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "T", "Lnet/minecraft/entity/EntityLivingBase;", "p1", "Lnet/minecraft/potion/PotionEffect;", "invoke"})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/EntityLivingBaseImpl$activePotionEffects$2.class */
final /* synthetic */ class EntityLivingBaseImpl$activePotionEffects$2 extends FunctionReference implements Function1 {
    public static final EntityLivingBaseImpl$activePotionEffects$2 INSTANCE = new EntityLivingBaseImpl$activePotionEffects$2();

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(PotionEffectImplKt.class, LiquidBounce.CLIENT_NAME);
    }

    EntityLivingBaseImpl$activePotionEffects$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object obj) {
        return invoke((PotionEffect) obj);
    }

    @NotNull
    public final IPotionEffect invoke(@NotNull PotionEffect p1) {
        Intrinsics.checkParameterIsNotNull(p1, "p1");
        return new PotionEffectImpl(p1);
    }
}
