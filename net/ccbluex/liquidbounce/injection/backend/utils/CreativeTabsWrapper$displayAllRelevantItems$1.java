package net.ccbluex.liquidbounce.injection.backend.utils;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.injection.backend.ItemStackImpl;
import net.ccbluex.liquidbounce.injection.backend.ItemStackImplKt;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/item/ItemStack;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "invoke"})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/utils/CreativeTabsWrapper$displayAllRelevantItems$1.class */
final /* synthetic */ class CreativeTabsWrapper$displayAllRelevantItems$1 extends FunctionReference implements Function1 {
    public static final CreativeTabsWrapper$displayAllRelevantItems$1 INSTANCE = new CreativeTabsWrapper$displayAllRelevantItems$1();

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(ItemStackImplKt.class, LiquidBounce.CLIENT_NAME);
    }

    CreativeTabsWrapper$displayAllRelevantItems$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object obj) {
        return invoke((IItemStack) obj);
    }

    @NotNull
    public final ItemStack invoke(@NotNull IItemStack p1) {
        Intrinsics.checkParameterIsNotNull(p1, "p1");
        return ((ItemStackImpl) p1).getWrapped();
    }
}
