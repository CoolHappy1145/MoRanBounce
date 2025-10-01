package net.ccbluex.liquidbounce.p005ui.client.hud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0006\u0010\n\u001a\u00020\u0006J\u0006\u0010\u000b\u001a\u00020\u0003R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/Config;", "", "config", "", "(Ljava/lang/String;)V", "hud", "Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/HUD;)V", "jsonArray", "Lcom/google/gson/JsonArray;", "toHUD", "toJson", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/Config.class */
public final class Config {
    private JsonArray jsonArray;

    public Config(@NotNull String config) {
        Intrinsics.checkParameterIsNotNull(config, "config");
        this.jsonArray = new JsonArray();
        Object objFromJson = new Gson().fromJson(config, JsonArray.class);
        Intrinsics.checkExpressionValueIsNotNull(objFromJson, "Gson().fromJson(config, JsonArray::class.java)");
        this.jsonArray = (JsonArray) objFromJson;
    }

    public Config(@NotNull HUD hud) {
        Intrinsics.checkParameterIsNotNull(hud, "hud");
        this.jsonArray = new JsonArray();
        for (Element element : hud.getElements()) {
            JsonElement jsonObject = new JsonObject();
            jsonObject.addProperty("Type", element.getName());
            jsonObject.addProperty("X", Double.valueOf(element.getX()));
            jsonObject.addProperty("Y", Double.valueOf(element.getY()));
            jsonObject.addProperty("Scale", Float.valueOf(element.getScale()));
            jsonObject.addProperty("HorizontalFacing", element.getSide().getHorizontal().getSideName());
            jsonObject.addProperty("VerticalFacing", element.getSide().getVertical().getSideName());
            for (Value value : element.getValues()) {
                jsonObject.add(value.getName(), value.mo1758toJson());
            }
            this.jsonArray.add(jsonObject);
        }
    }

    @NotNull
    public final String toJson() {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(this.jsonArray);
        Intrinsics.checkExpressionValueIsNotNull(json, "GsonBuilder().setPrettyP\u2026reate().toJson(jsonArray)");
        return json;
    }

    @NotNull
    public final HUD toHUD() throws IllegalAccessException, InstantiationException {
        boolean z;
        Object obj;
        HUD hud = new HUD();
        Iterator it = this.jsonArray.iterator();
        while (it.hasNext()) {
            JsonObject jsonObject = (JsonElement) it.next();
            if ((jsonObject instanceof JsonObject) && jsonObject.has("Type")) {
                JsonElement jsonElement = jsonObject.get("Type");
                Intrinsics.checkExpressionValueIsNotNull(jsonElement, "jsonObject[\"Type\"]");
                String asString = jsonElement.getAsString();
                Class[] elements = HUD.Companion.getElements();
                int length = elements.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        Class cls = elements[i];
                        if (Intrinsics.areEqual(((ElementInfo) cls.getAnnotation(ElementInfo.class)).name(), asString)) {
                            Element element = (Element) cls.newInstance();
                            Intrinsics.checkExpressionValueIsNotNull(jsonObject.get("X"), "jsonObject[\"X\"]");
                            element.setX(r1.getAsInt());
                            Intrinsics.checkExpressionValueIsNotNull(jsonObject.get("Y"), "jsonObject[\"Y\"]");
                            element.setY(r1.getAsInt());
                            JsonElement jsonElement2 = jsonObject.get("Scale");
                            Intrinsics.checkExpressionValueIsNotNull(jsonElement2, "jsonObject[\"Scale\"]");
                            element.setScale(jsonElement2.getAsFloat());
                            Side.Horizontal.Companion companion = Side.Horizontal.Companion;
                            JsonElement jsonElement3 = jsonObject.get("HorizontalFacing");
                            Intrinsics.checkExpressionValueIsNotNull(jsonElement3, "jsonObject[\"HorizontalFacing\"]");
                            String asString2 = jsonElement3.getAsString();
                            Intrinsics.checkExpressionValueIsNotNull(asString2, "jsonObject[\"HorizontalFacing\"].asString");
                            Side.Horizontal byName = companion.getByName(asString2);
                            if (byName == null) {
                                Intrinsics.throwNpe();
                            }
                            Side.Vertical.Companion companion2 = Side.Vertical.Companion;
                            JsonElement jsonElement4 = jsonObject.get("VerticalFacing");
                            Intrinsics.checkExpressionValueIsNotNull(jsonElement4, "jsonObject[\"VerticalFacing\"]");
                            String asString3 = jsonElement4.getAsString();
                            Intrinsics.checkExpressionValueIsNotNull(asString3, "jsonObject[\"VerticalFacing\"].asString");
                            Side.Vertical byName2 = companion2.getByName(asString3);
                            if (byName2 == null) {
                                Intrinsics.throwNpe();
                            }
                            element.setSide(new Side(byName, byName2));
                            for (Value value : element.getValues()) {
                                if (jsonObject.has(value.getName())) {
                                    JsonElement jsonElement5 = jsonObject.get(value.getName());
                                    Intrinsics.checkExpressionValueIsNotNull(jsonElement5, "jsonObject[value.name]");
                                    value.fromJson(jsonElement5);
                                }
                            }
                            if (jsonObject.has("font")) {
                                Iterator it2 = element.getValues().iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        obj = null;
                                        break;
                                    }
                                    Object next = it2.next();
                                    if (((Value) next) instanceof FontValue) {
                                        obj = next;
                                        break;
                                    }
                                }
                                Value value2 = (Value) obj;
                                if (value2 != null) {
                                    JsonElement jsonElement6 = jsonObject.get("font");
                                    Intrinsics.checkExpressionValueIsNotNull(jsonElement6, "jsonObject[\"font\"]");
                                    value2.fromJson(jsonElement6);
                                }
                            }
                            Intrinsics.checkExpressionValueIsNotNull(element, "element");
                            hud.addElement(element);
                        } else {
                            i++;
                        }
                    }
                }
            }
        }
        for (Class cls2 : HUD.Companion.getElements()) {
            if (((ElementInfo) cls2.getAnnotation(ElementInfo.class)).force()) {
                List<Element> elements2 = hud.getElements();
                if (!(elements2 instanceof Collection) || !elements2.isEmpty()) {
                    Iterator<T> it3 = elements2.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            z = true;
                            break;
                        }
                        if (Intrinsics.areEqual(((Element) it3.next()).getClass(), cls2)) {
                            z = false;
                            break;
                        }
                    }
                } else {
                    z = true;
                }
                if (z) {
                    Object objNewInstance = cls2.newInstance();
                    Intrinsics.checkExpressionValueIsNotNull(objNewInstance, "elementClass.newInstance()");
                    hud.addElement((Element) objNewInstance);
                }
            }
        }
        return hud;
    }
}
