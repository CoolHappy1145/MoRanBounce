package net.ccbluex.liquidbounce.injection.backend;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemPotion;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\nH\u0016\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ItemPotionImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "Lnet/minecraft/item/ItemPotion;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemPotion;", "wrapped", "(Lnet/minecraft/item/ItemPotion;)V", "getEffects", "", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ItemPotionImpl.class */
public final class ItemPotionImpl extends ItemImpl implements IItemPotion {

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/potion/PotionEffect;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ItemPotionImpl$getEffects$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ItemPotionImpl$getEffects$1.class */
    static final /* synthetic */ class C04441 extends FunctionReference implements Function1 {
        public static final C04441 INSTANCE = new C04441();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(PotionEffectImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04441() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((IPotionEffect) obj);
        }

        @NotNull
        public final PotionEffect invoke(@NotNull IPotionEffect p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((PotionEffectImpl) p1).getWrapped();
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "p1", "Lnet/minecraft/potion/PotionEffect;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ItemPotionImpl$getEffects$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ItemPotionImpl$getEffects$2.class */
    static final /* synthetic */ class C04452 extends FunctionReference implements Function1 {
        public static final C04452 INSTANCE = new C04452();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(PotionEffectImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04452() {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ItemPotionImpl(@NotNull ItemPotion wrapped) {
        super((Item) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemPotion
    @NotNull
    public Collection getEffects(@NotNull IItemStack stack) {
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        return new WrappedCollection(PotionUtils.func_185189_a(((ItemStackImpl) stack).getWrapped()), C04441.INSTANCE, C04452.INSTANCE);
    }
}
