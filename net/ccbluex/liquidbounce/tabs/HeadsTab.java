package net.ccbluex.liquidbounce.tabs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0007H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/tabs/HeadsTab;", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "()V", "heads", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "displayAllReleventItems", "", "itemList", "", "getTabIconItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getTranslatedTabLabel", "", "hasSearchBar", "", "loadHeads", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/tabs/HeadsTab.class */
public final class HeadsTab extends WrappedCreativeTabs {
    private final ArrayList heads;

    public HeadsTab() {
        super("Heads");
        this.heads = new ArrayList();
        getRepresentedType().setBackgroundImageName("item_search.png");
        loadHeads();
    }

    private final void loadHeads() {
        try {
            ClientUtils.getLogger().info("Loading heads...");
            JsonElement headsConfiguration = new JsonParser().parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/heads.json"));
            Intrinsics.checkExpressionValueIsNotNull(headsConfiguration, "headsConfiguration");
            if (headsConfiguration.isJsonObject()) {
                JsonObject asJsonObject = headsConfiguration.getAsJsonObject();
                JsonElement jsonElement = asJsonObject.get("enabled");
                Intrinsics.checkExpressionValueIsNotNull(jsonElement, "headsConf.get(\"enabled\")");
                if (jsonElement.getAsBoolean()) {
                    JsonElement jsonElement2 = asJsonObject.get("url");
                    Intrinsics.checkExpressionValueIsNotNull(jsonElement2, "headsConf.get(\"url\")");
                    String url = jsonElement2.getAsString();
                    ClientUtils.getLogger().info("Loading heads from " + url + "...");
                    JsonParser jsonParser = new JsonParser();
                    Intrinsics.checkExpressionValueIsNotNull(url, "url");
                    JsonElement headsElement = jsonParser.parse(HttpUtils.get(url));
                    Intrinsics.checkExpressionValueIsNotNull(headsElement, "headsElement");
                    if (!headsElement.isJsonObject()) {
                        ClientUtils.getLogger().error("Something is wrong, the heads json is not a JsonObject!");
                        return;
                    }
                    Iterator it = headsElement.getAsJsonObject().entrySet().iterator();
                    while (it.hasNext()) {
                        JsonElement value = (JsonElement) ((Map.Entry) it.next()).getValue();
                        Intrinsics.checkExpressionValueIsNotNull(value, "value");
                        JsonObject asJsonObject2 = value.getAsJsonObject();
                        ArrayList arrayList = this.heads;
                        StringBuilder sbAppend = new StringBuilder().append("skull 1 3 {display:{Name:\"");
                        JsonElement jsonElement3 = asJsonObject2.get("name");
                        Intrinsics.checkExpressionValueIsNotNull(jsonElement3, "headElement.get(\"name\")");
                        StringBuilder sbAppend2 = sbAppend.append(jsonElement3.getAsString()).append("\"},SkullOwner:{Id:\"");
                        JsonElement jsonElement4 = asJsonObject2.get("uuid");
                        Intrinsics.checkExpressionValueIsNotNull(jsonElement4, "headElement.get(\"uuid\")");
                        StringBuilder sbAppend3 = sbAppend2.append(jsonElement4.getAsString()).append("\",Properties:{textures:[{Value:\"");
                        JsonElement jsonElement5 = asJsonObject2.get(PropertyDescriptor.VALUE);
                        Intrinsics.checkExpressionValueIsNotNull(jsonElement5, "headElement.get(\"value\")");
                        arrayList.add(ItemUtils.createItem(sbAppend3.append(jsonElement5.getAsString()).append("\"}]}}}").toString()));
                    }
                    ClientUtils.getLogger().info("Loaded " + this.heads.size() + " heads from HeadDB.");
                    return;
                }
                ClientUtils.getLogger().info("Heads are disabled.");
            }
        } catch (Exception e) {
            ClientUtils.getLogger().error("Error while reading heads.", e);
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs
    public void displayAllReleventItems(@NotNull List itemList) {
        Intrinsics.checkParameterIsNotNull(itemList, "itemList");
        itemList.addAll(this.heads);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs
    @NotNull
    public IItem getTabIconItem() {
        return WrapperImpl.INSTANCE.getClassProvider().getItemEnum(ItemType.SKULL);
    }
}
