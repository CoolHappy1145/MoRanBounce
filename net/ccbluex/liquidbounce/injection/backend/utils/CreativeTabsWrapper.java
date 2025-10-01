package net.ccbluex.liquidbounce.injection.backend.utils;

import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs;
import net.ccbluex.liquidbounce.api.util.WrappedMutableList;
import net.ccbluex.liquidbounce.injection.backend.ItemImpl;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\b\u0010\u000f\u001a\u00020\u0005H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/utils/CreativeTabsWrapper;", "Lnet/minecraft/creativetab/CreativeTabs;", "wrapped", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "name", "", "(Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;Ljava/lang/String;)V", "getWrapped", "()Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "displayAllRelevantItems", "", "items", "Lnet/minecraft/util/NonNullList;", "Lnet/minecraft/item/ItemStack;", "getTabIconItem", "getTranslatedTabLabel", "hasSearchBar", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/utils/CreativeTabsWrapper.class */
public final class CreativeTabsWrapper extends CreativeTabs {

    @NotNull
    private final WrappedCreativeTabs wrapped;

    @NotNull
    public final WrappedCreativeTabs getWrapped() {
        return this.wrapped;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CreativeTabsWrapper(@NotNull WrappedCreativeTabs wrapped, @NotNull String name) {
        super(name);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.wrapped = wrapped;
    }

    @NotNull
    public ItemStack func_78016_d() {
        IItem tabIconItem = this.wrapped.getTabIconItem();
        if (tabIconItem == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.ItemImpl<*>");
        }
        return new ItemStack(((ItemImpl) tabIconItem).getWrapped());
    }

    public void func_78018_a(@NotNull NonNullList items) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        this.wrapped.displayAllReleventItems(new WrappedMutableList((List) items, CreativeTabsWrapper$displayAllRelevantItems$1.INSTANCE, CreativeTabsWrapper$displayAllRelevantItems$2.INSTANCE));
    }

    @NotNull
    public String func_78024_c() {
        WrappedCreativeTabs wrappedCreativeTabs = this.wrapped;
        return "asdf";
    }

    public boolean hasSearchBar() {
        WrappedCreativeTabs wrappedCreativeTabs = this.wrapped;
        return true;
    }
}
