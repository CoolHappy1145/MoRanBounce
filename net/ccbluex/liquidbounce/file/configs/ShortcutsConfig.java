package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.FilesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.shortcuts.Shortcut;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0014J\b\u0010\u0007\u001a\u00020\u0006H\u0014\u00a8\u0006\b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/file/configs/ShortcutsConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "loadConfig", "", "saveConfig", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/configs/ShortcutsConfig.class */
public final class ShortcutsConfig extends FileConfig {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutsConfig(@NotNull File file) {
        super(file);
        Intrinsics.checkParameterIsNotNull(file, "file");
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void loadConfig() {
        String asString;
        JsonArray asJsonArray;
        String asString2;
        Iterable asJsonArray2;
        Command command;
        JsonParser jsonParser = new JsonParser();
        File file = getFile();
        Intrinsics.checkExpressionValueIsNotNull(file, "file");
        JsonArray jsonArray = jsonParser.parse(FilesKt.readText$default(file, null, 1, null));
        if (jsonArray instanceof JsonArray) {
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JsonObject jsonObject = (JsonElement) it.next();
                if (jsonObject instanceof JsonObject) {
                    JsonElement jsonElement = jsonObject.get("name");
                    if (jsonElement != null && (asString = jsonElement.getAsString()) != null) {
                        JsonElement jsonElement2 = jsonObject.get("script");
                        if (jsonElement2 != null && (asJsonArray = jsonElement2.getAsJsonArray()) != null) {
                            ArrayList arrayList = new ArrayList();
                            Iterator it2 = asJsonArray.iterator();
                            while (it2.hasNext()) {
                                JsonObject jsonObject2 = (JsonElement) it2.next();
                                if (jsonObject2 instanceof JsonObject) {
                                    JsonElement jsonElement3 = jsonObject2.get("name");
                                    if (jsonElement3 != null && (asString2 = jsonElement3.getAsString()) != null) {
                                        JsonElement jsonElement4 = jsonObject2.get("arguments");
                                        if (jsonElement4 != null && (asJsonArray2 = jsonElement4.getAsJsonArray()) != null && (command = LiquidBounce.INSTANCE.getCommandManager().getCommand(asString2)) != null) {
                                            Iterable<JsonElement> iterable = asJsonArray2;
                                            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                                            for (JsonElement it3 : iterable) {
                                                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                                                arrayList2.add(it3.getAsString());
                                            }
                                            Object[] array = arrayList2.toArray(new String[0]);
                                            if (array == null) {
                                                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                                            }
                                            arrayList.add(new Pair(command, array));
                                        }
                                    }
                                }
                            }
                            LiquidBounce.INSTANCE.getCommandManager().registerCommand(new Shortcut(asString, arrayList));
                        }
                    }
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void saveConfig() {
        JsonElement jsonArray = new JsonArray();
        for (Command command : LiquidBounce.INSTANCE.getCommandManager().getCommands()) {
            if (command instanceof Shortcut) {
                JsonElement jsonObject = new JsonObject();
                jsonObject.addProperty("name", command.getCommand());
                JsonElement jsonArray2 = new JsonArray();
                for (Pair pair : ((Shortcut) command).getScript()) {
                    JsonElement jsonObject2 = new JsonObject();
                    jsonObject2.addProperty("name", ((Command) pair.getFirst()).getCommand());
                    JsonElement jsonArray3 = new JsonArray();
                    for (String str : (String[]) pair.getSecond()) {
                        jsonArray3.add(str);
                    }
                    jsonObject2.add("arguments", jsonArray3);
                    jsonArray2.add(jsonObject2);
                }
                jsonObject.add("script", jsonArray2);
                jsonArray.add(jsonObject);
            }
        }
        File file = getFile();
        Intrinsics.checkExpressionValueIsNotNull(file, "file");
        String json = FileManager.PRETTY_GSON.toJson(jsonArray);
        Intrinsics.checkExpressionValueIsNotNull(json, "FileManager.PRETTY_GSON.toJson(jsonArray)");
        FilesKt.writeText$default(file, json, null, 2, null);
    }
}
