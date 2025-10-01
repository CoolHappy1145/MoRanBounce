package net.ccbluex.liquidbounce.script.api;

import java.util.Iterator;
import java.util.List;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\u0010!\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0019\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/script/api/ScriptTab;", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "tabObject", "Ljdk/nashorn/api/scripting/JSObject;", "(Ljdk/nashorn/api/scripting/JSObject;)V", "items", "", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "getItems", "()[Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "[Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "displayAllReleventItems", "", "", "getTabIconItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getTranslatedTabLabel", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/api/ScriptTab.class */
public final class ScriptTab extends WrappedCreativeTabs {

    @NotNull
    private final IItemStack[] items;
    private final JSObject tabObject;

    /* JADX WARN: Illegal instructions before constructor call */
    public ScriptTab(@NotNull JSObject tabObject) {
        Intrinsics.checkParameterIsNotNull(tabObject, "tabObject");
        Object member = tabObject.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        super((String) member);
        this.tabObject = tabObject;
        Object objConvert = ScriptUtils.convert(this.tabObject.getMember("items"), IItemStack[].class);
        if (objConvert == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<net.ccbluex.liquidbounce.api.minecraft.item.IItemStack>");
        }
        this.items = (IItemStack[]) objConvert;
    }

    @NotNull
    public final IItemStack[] getItems() {
        return this.items;
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs
    @NotNull
    public IItem getTabIconItem() {
        IExtractedFunctions functions = WrapperImpl.INSTANCE.getFunctions();
        Object member = this.tabObject.getMember("icon");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        IItem itemByName = functions.getItemByName((String) member);
        if (itemByName == null) {
            Intrinsics.throwNpe();
        }
        return itemByName;
    }

    @NotNull
    public String getTranslatedTabLabel() {
        Object member = this.tabObject.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        return (String) member;
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs
    public void displayAllReleventItems(@NotNull List items) {
        Intrinsics.checkParameterIsNotNull(items, "items");
        Iterator it = items.iterator();
        while (it.hasNext()) {
            items.add((IItemStack) it.next());
        }
    }
}
