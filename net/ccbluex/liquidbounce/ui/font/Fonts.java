package net.ccbluex.liquidbounce.p005ui.font;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/font/Fonts.class */
public class Fonts extends MinecraftInstance {

    @FontDetails(fontName = "Minecraft Font")
    public static final IFontRenderer minecraftFont = f157mc.getFontRendererObj();
    private static final HashMap CUSTOM_FONT_RENDERERS = new HashMap();

    @FontDetails(fontName = "Roboto Medium", fontSize = OPCode.BEGIN_BUF)
    public static IFontRenderer font35;

    @FontDetails(fontName = "Roboto Medium", fontSize = 40)
    public static IFontRenderer font20;

    @FontDetails(fontName = "Roboto Medium", fontSize = 20)
    public static IFontRenderer font30;

    @FontDetails(fontName = "Roboto Medium", fontSize = 30)
    public static IFontRenderer font40;

    @FontDetails(fontName = "Roboto Bold", fontSize = 180)
    public static IFontRenderer fontBold180;

    @FontDetails(fontName = "Roboto Bold", fontSize = 40)
    public static IFontRenderer fontBold40;

    @FontDetails(fontName = "Roboto BoldItalic", fontSize = OPCode.BEGIN_BUF)
    public static IFontRenderer BoldItalic35;

    @FontDetails(fontName = "Roboto Wqy", fontSize = OPCode.BEGIN_BUF)
    public static IFontRenderer wqy35;

    @FontDetails(fontName = "Roboto Wqy", fontSize = 36)
    public static IFontRenderer wqy36;

    @FontDetails(fontName = "Roboto Wqy", fontSize = 30)
    public static IFontRenderer wqy30;

    @FontDetails(fontName = "Roboto Comfortaa", fontSize = OPCode.BEGIN_BUF)
    public static IFontRenderer Comfortaa35;

    @FontDetails(fontName = "Roboto Sfui", fontSize = OPCode.BEGIN_BUF)
    public static IFontRenderer Sfui35;

    @FontDetails(fontName = "Roboto Sfui", fontSize = 40)
    public static IFontRenderer Sfui40;

    @FontDetails(fontName = "Roboto Sfui", fontSize = 24)
    public static IFontRenderer Sfui24;

    public static void loadFonts() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        ClientUtils.getLogger().info("Loading Fonts.");
        downloadFonts();
        font35 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Medium.ttf", 35)));
        wqy35 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Wqy.ttf", 35)));
        wqy30 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Wqy.ttf", 30)));
        Sfui35 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Sfui.ttf", 35)));
        Sfui40 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Sfui.ttf", 40)));
        Sfui24 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Sfui.ttf", 24)));
        wqy36 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Wqy.ttf", 36)));
        font40 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Medium.ttf", 40)));
        font30 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Medium.ttf", 30)));
        font20 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Medium.ttf", 20)));
        fontBold180 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Bold.ttf", 180)));
        fontBold40 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Bold.ttf", 40)));
        BoldItalic35 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-BoldItalic.ttf", 35)));
        Comfortaa35 = classProvider.wrapFontRenderer(new GameFontRenderer(getFont("Roboto-Comfortaa.ttf", 35)));
        try {
            CUSTOM_FONT_RENDERERS.clear();
            File file = new File(LiquidBounce.fileManager.fontsDir, "fonts.json");
            if (file.exists()) {
                JsonArray jsonArray = new JsonParser().parse(new BufferedReader(new FileReader(file)));
                if (jsonArray instanceof JsonNull) {
                    return;
                }
                Iterator it = jsonArray.iterator();
                while (it.hasNext()) {
                    JsonObject jsonObject = (JsonElement) it.next();
                    if (jsonObject instanceof JsonNull) {
                        return;
                    }
                    JsonObject jsonObject2 = jsonObject;
                    Font font = getFont(jsonObject2.get("fontFile").getAsString(), jsonObject2.get("fontSize").getAsInt());
                    CUSTOM_FONT_RENDERERS.put(new FontInfo(font), classProvider.wrapFontRenderer(new GameFontRenderer(font)));
                }
            } else {
                file.createNewFile();
                PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.println(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonArray()));
                printWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ClientUtils.getLogger().info("Loaded Fonts. (" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms)");
    }

    private static void downloadFonts() {
        try {
            File file = new File(LiquidBounce.fileManager.fontsDir, "roboto.zip");
            if (!file.exists()) {
                ClientUtils.getLogger().info("Downloading fonts...");
                HttpUtils.download("https://cloud.liquidbounce.net/LiquidBounce/fonts/Roboto.zip", file);
                ClientUtils.getLogger().info("Extract fonts...");
                extractZip(file.getPath(), LiquidBounce.fileManager.fontsDir.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IFontRenderer getFontRenderer(String str, int i) throws IllegalAccessException, IllegalArgumentException {
        for (Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object obj = field.get(null);
                if (obj instanceof IFontRenderer) {
                    FontDetails fontDetails = (FontDetails) field.getAnnotation(FontDetails.class);
                    if (fontDetails.fontName().equals(str) && fontDetails.fontSize() == i) {
                        return (IFontRenderer) obj;
                    }
                } else {
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (IFontRenderer) CUSTOM_FONT_RENDERERS.getOrDefault(new FontInfo(str, i), minecraftFont);
    }

    public static FontInfo getFontDetails(IFontRenderer iFontRenderer) {
        for (Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!field.get(null).equals(iFontRenderer)) {
                continue;
            } else {
                FontDetails fontDetails = (FontDetails) field.getAnnotation(FontDetails.class);
                return new FontInfo(fontDetails.fontName(), fontDetails.fontSize());
            }
        }
        for (Map.Entry entry : CUSTOM_FONT_RENDERERS.entrySet()) {
            if (entry.getValue() == iFontRenderer) {
                return (FontInfo) entry.getKey();
            }
        }
        return null;
    }

    public static List getFonts() throws IllegalAccessException, IllegalArgumentException {
        ArrayList arrayList = new ArrayList();
        for (Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object obj = field.get(null);
                if (obj instanceof IFontRenderer) {
                    arrayList.add((IFontRenderer) obj);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        arrayList.addAll(CUSTOM_FONT_RENDERERS.values());
        return arrayList;
    }

    private static Font getFont(String str, int i) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(LiquidBounce.fileManager.fontsDir, str));
            Font fontDeriveFont = Font.createFont(0, fileInputStream).deriveFont(0, i);
            fileInputStream.close();
            return fontDeriveFont;
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("default", 0, i);
        }
    }

    private static void extractZip(String str, String str2) throws IOException {
        byte[] bArr = new byte[1024];
        try {
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdir();
            }
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(str));
            for (ZipEntry nextEntry = zipInputStream.getNextEntry(); nextEntry != null; nextEntry = zipInputStream.getNextEntry()) {
                File file2 = new File(str2 + File.separator + nextEntry.getName());
                new File(file2.getParent()).mkdirs();
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                while (true) {
                    int i = zipInputStream.read(bArr);
                    if (i > 0) {
                        fileOutputStream.write(bArr, 0, i);
                    }
                }
                fileOutputStream.close();
            }
            zipInputStream.closeEntry();
            zipInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/font/Fonts$FontInfo.class */
    public static class FontInfo {
        private final String name;
        private final int fontSize;

        public FontInfo(String str, int i) {
            this.name = str;
            this.fontSize = i;
        }

        public FontInfo(Font font) {
            this(font.getName(), font.getSize());
        }

        public String getName() {
            return this.name;
        }

        public int getFontSize() {
            return this.fontSize;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            FontInfo fontInfo = (FontInfo) obj;
            if (this.fontSize != fontInfo.fontSize) {
                return false;
            }
            return Objects.equals(this.name, fontInfo.name);
        }

        public int hashCode() {
            return (31 * (this.name != null ? this.name.hashCode() : 0)) + this.fontSize;
        }
    }
}
