package net.ccbluex.liquidbounce.p005ui.client.clickgui.style.styles;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.features.module.modules.render.ClickGUI;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.Panel;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ButtonElement;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.elements.ModuleElement;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.style.Style;
import net.ccbluex.liquidbounce.p005ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.p005ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.slf4j.Marker;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/style/styles/NullStyle.class */
public class NullStyle extends Style {
    private boolean mouseDown;
    private boolean rightMouseDown;

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.style.Style
    public void drawPanel(int i, int i2, Panel panel) {
        RenderUtils.drawRect(panel.getX() - 3.0f, panel.getY(), panel.getX() + panel.getWidth() + 3.0f, panel.getY() + 19.0f, ClickGUI.generateColor().getRGB());
        if (panel.getFade() > 0) {
            RenderUtils.drawBorderedRect(panel.getX(), panel.getY() + 19.0f, panel.getX() + panel.getWidth(), panel.getY() + 19 + panel.getFade(), 1.0f, Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
        GlStateManager.func_179117_G();
        Fonts.font35.drawString("\u00a7f" + panel.getName(), (int) (panel.getX() - ((Fonts.font35.getStringWidth("\u00a7f" + StringUtils.func_76338_a(panel.getName())) - 100.0f) / 2.0f)), panel.getY() + 7, Integer.MAX_VALUE);
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.style.Style
    public void drawDescription(int i, int i2, String str) {
        RenderUtils.drawRect(i + 9, i2, i + Fonts.font35.getStringWidth(str) + 14, i2 + Fonts.font35.getFontHeight() + 3, ClickGUI.generateColor().getRGB());
        GlStateManager.func_179117_G();
        Fonts.font35.drawString(str, i + 12, i2 + (Fonts.font35.getFontHeight() / 2), Integer.MAX_VALUE);
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.style.Style
    public void drawButtonElement(int i, int i2, ButtonElement buttonElement) {
        GlStateManager.func_179117_G();
        Fonts.font35.drawString(buttonElement.getDisplayName(), (int) (buttonElement.getX() - ((Fonts.font35.getStringWidth(buttonElement.getDisplayName()) - 100.0f) / 2.0f)), buttonElement.getY() + 6, buttonElement.getColor());
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.style.Style
    public void drawModuleElement(int i, int i2, ModuleElement moduleElement) throws IllegalAccessException, IllegalArgumentException {
        int rgb = ClickGUI.generateColor().getRGB();
        GlStateManager.func_179117_G();
        Fonts.font35.drawString(moduleElement.getDisplayName(), (int) (moduleElement.getX() - ((Fonts.font35.getStringWidth(moduleElement.getDisplayName()) - 100.0f) / 2.0f)), moduleElement.getY() + 6, moduleElement.getModule().getState() ? rgb : Integer.MAX_VALUE);
        List<Value> values = moduleElement.getModule().getValues();
        if (!values.isEmpty()) {
            Fonts.font35.drawString(Marker.ANY_NON_NULL_MARKER, (moduleElement.getX() + moduleElement.getWidth()) - 8, moduleElement.getY() + (moduleElement.getHeight() / 2), Color.WHITE.getRGB());
            if (moduleElement.isShowSettings()) {
                int y = moduleElement.getY() + 4;
                for (Value value : values) {
                    boolean z = value.get() instanceof Number;
                    if (z) {
                        AWTFontRenderer.Companion.setAssumeNonVolatile(false);
                    }
                    if (value instanceof BoolValue) {
                        String name = value.getName();
                        float stringWidth = Fonts.font35.getStringWidth(name);
                        if (moduleElement.getSettingsWidth() < stringWidth + 8.0f) {
                            moduleElement.setSettingsWidth(stringWidth + 8.0f);
                        }
                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, y + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), y + 14, Integer.MIN_VALUE);
                        if (i >= moduleElement.getX() + moduleElement.getWidth() + 4 && i <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && i2 >= y + 2 && i2 <= y + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                            BoolValue boolValue = (BoolValue) value;
                            boolValue.set(Boolean.valueOf(!((Boolean) boolValue.get()).booleanValue()));
                            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                        }
                        GlStateManager.func_179117_G();
                        Fonts.font35.drawString(name, moduleElement.getX() + moduleElement.getWidth() + 6, y + 4, ((Boolean) ((BoolValue) value).get()).booleanValue() ? rgb : Integer.MAX_VALUE);
                        y += 12;
                    } else if (value instanceof ListValue) {
                        ListValue listValue = (ListValue) value;
                        String name2 = value.getName();
                        float stringWidth2 = Fonts.font35.getStringWidth(name2);
                        if (moduleElement.getSettingsWidth() < stringWidth2 + 16.0f) {
                            moduleElement.setSettingsWidth(stringWidth2 + 16.0f);
                        }
                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, y + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), y + 14, Integer.MIN_VALUE);
                        GlStateManager.func_179117_G();
                        Fonts.font35.drawString("\u00a7c" + name2, moduleElement.getX() + moduleElement.getWidth() + 6, y + 4, 16777215);
                        Fonts.font35.drawString(listValue.openList ? "-" : Marker.ANY_NON_NULL_MARKER, (int) (((moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth()) - (listValue.openList ? 5 : 6)), y + 4, 16777215);
                        if (i >= moduleElement.getX() + moduleElement.getWidth() + 4 && i <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && i2 >= y + 2 && i2 <= y + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                            listValue.openList = !listValue.openList;
                            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                        }
                        y += 12;
                        for (String str : listValue.getValues()) {
                            float stringWidth3 = Fonts.font35.getStringWidth(">" + str);
                            if (moduleElement.getSettingsWidth() < stringWidth3 + 12.0f) {
                                moduleElement.setSettingsWidth(stringWidth3 + 12.0f);
                            }
                            if (listValue.openList) {
                                RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, y + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), y + 14, Integer.MIN_VALUE);
                                if (i >= moduleElement.getX() + moduleElement.getWidth() + 4 && i <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && i2 >= y + 2 && i2 <= y + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                    listValue.set(str);
                                    f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                                }
                                GlStateManager.func_179117_G();
                                Fonts.font35.drawString(">", moduleElement.getX() + moduleElement.getWidth() + 6, y + 4, Integer.MAX_VALUE);
                                Fonts.font35.drawString(str, moduleElement.getX() + moduleElement.getWidth() + 14, y + 4, (listValue.get() == null || !((String) listValue.get()).equalsIgnoreCase(str)) ? Integer.MAX_VALUE : rgb);
                                y += 12;
                            }
                        }
                    } else if (value instanceof FloatValue) {
                        FloatValue floatValue = (FloatValue) value;
                        String str2 = value.getName() + "\u00a7f: \u00a7c" + round(((Float) floatValue.get()).floatValue());
                        float stringWidth4 = Fonts.font35.getStringWidth(str2);
                        if (moduleElement.getSettingsWidth() < stringWidth4 + 8.0f) {
                            moduleElement.setSettingsWidth(stringWidth4 + 8.0f);
                        }
                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, y + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), y + 24, Integer.MIN_VALUE);
                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 8, y + 18, ((moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth()) - 4.0f, y + 19, Integer.MAX_VALUE);
                        float x = moduleElement.getX() + moduleElement.getWidth() + (((moduleElement.getSettingsWidth() - 12.0f) * (((Float) floatValue.get()).floatValue() - floatValue.getMinimum())) / (floatValue.getMaximum() - floatValue.getMinimum()));
                        RenderUtils.drawRect(8.0f + x, y + 15, x + 11.0f, y + 21, rgb);
                        if (i >= moduleElement.getX() + moduleElement.getWidth() + 4 && i <= ((moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth()) - 4.0f && i2 >= y + 15 && i2 <= y + 21 && Mouse.isButtonDown(0)) {
                            double x2 = (((i - moduleElement.getX()) - moduleElement.getWidth()) - 8) / (moduleElement.getSettingsWidth() - 12.0f);
                            floatValue.set((Object) Float.valueOf(round((float) (floatValue.getMinimum() + ((floatValue.getMaximum() - floatValue.getMinimum()) * (x2 < 0.0d ? 0.0d : x2 > 1.0d ? 1.0d : x2)))).floatValue()));
                        }
                        GlStateManager.func_179117_G();
                        Fonts.font35.drawString(str2, moduleElement.getX() + moduleElement.getWidth() + 6, y + 4, 16777215);
                        y += 22;
                    } else if (value instanceof IntegerValue) {
                        IntegerValue integerValue = (IntegerValue) value;
                        String str3 = value.getName() + "\u00a7f: \u00a7c" + (value instanceof BlockValue ? BlockUtils.getBlockName(((Integer) integerValue.get()).intValue()) + " (" + integerValue.get() + ")" : (Serializable) integerValue.get());
                        float stringWidth5 = Fonts.font35.getStringWidth(str3);
                        if (moduleElement.getSettingsWidth() < stringWidth5 + 8.0f) {
                            moduleElement.setSettingsWidth(stringWidth5 + 8.0f);
                        }
                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, y + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), y + 24, Integer.MIN_VALUE);
                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 8, y + 18, ((moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth()) - 4.0f, y + 19, Integer.MAX_VALUE);
                        float x3 = moduleElement.getX() + moduleElement.getWidth() + (((moduleElement.getSettingsWidth() - 12.0f) * (((Integer) integerValue.get()).intValue() - integerValue.getMinimum())) / (integerValue.getMaximum() - integerValue.getMinimum()));
                        RenderUtils.drawRect(8.0f + x3, y + 15, x3 + 11.0f, y + 21, rgb);
                        if (i >= moduleElement.getX() + moduleElement.getWidth() + 4 && i <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && i2 >= y + 15 && i2 <= y + 21 && Mouse.isButtonDown(0)) {
                            double x4 = (((i - moduleElement.getX()) - moduleElement.getWidth()) - 8) / (moduleElement.getSettingsWidth() - 12.0f);
                            integerValue.set((Object) Integer.valueOf((int) (integerValue.getMinimum() + ((integerValue.getMaximum() - integerValue.getMinimum()) * (x4 < 0.0d ? 0.0d : x4 > 1.0d ? 1.0d : x4)))));
                        }
                        GlStateManager.func_179117_G();
                        Fonts.font35.drawString(str3, moduleElement.getX() + moduleElement.getWidth() + 6, y + 4, 16777215);
                        y += 22;
                    } else if (value instanceof FontValue) {
                        FontValue fontValue = (FontValue) value;
                        IFontRenderer iFontRenderer = (IFontRenderer) fontValue.get();
                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, y + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), y + 14, Integer.MIN_VALUE);
                        String str4 = "Font: Unknown";
                        if (iFontRenderer.isGameFontRenderer()) {
                            GameFontRenderer gameFontRenderer = iFontRenderer.getGameFontRenderer();
                            str4 = "Font: " + gameFontRenderer.getDefaultFont().getFont().getName() + " - " + gameFontRenderer.getDefaultFont().getFont().getSize();
                        } else if (iFontRenderer == Fonts.minecraftFont) {
                            str4 = "Font: Minecraft";
                        } else {
                            Fonts.FontInfo fontDetails = Fonts.getFontDetails(iFontRenderer);
                            if (fontDetails != null) {
                                str4 = fontDetails.getName() + (fontDetails.getFontSize() != -1 ? " - " + fontDetails.getFontSize() : "");
                            }
                        }
                        Fonts.font35.drawString(str4, moduleElement.getX() + moduleElement.getWidth() + 6, y + 4, Color.WHITE.getRGB());
                        int stringWidth6 = Fonts.font35.getStringWidth(str4);
                        if (moduleElement.getSettingsWidth() < stringWidth6 + 8) {
                            moduleElement.setSettingsWidth(stringWidth6 + 8);
                        }
                        if (((Mouse.isButtonDown(0) && !this.mouseDown) || (Mouse.isButtonDown(1) && !this.rightMouseDown)) && i >= moduleElement.getX() + moduleElement.getWidth() + 4 && i <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && i2 >= y + 4 && i2 <= y + 12) {
                            List fonts = Fonts.getFonts();
                            if (Mouse.isButtonDown(0)) {
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= fonts.size()) {
                                        break;
                                    }
                                    if (!((IFontRenderer) fonts.get(i3)).equals(iFontRenderer)) {
                                        i3++;
                                    } else {
                                        int i4 = i3 + 1;
                                        if (i4 >= fonts.size()) {
                                            i4 = 0;
                                        }
                                        fontValue.set(fonts.get(i4));
                                    }
                                }
                            } else {
                                int size = fonts.size() - 1;
                                while (true) {
                                    if (size < 0) {
                                        break;
                                    }
                                    if (((IFontRenderer) fonts.get(size)).equals(iFontRenderer)) {
                                        int size2 = size - 1;
                                        if (size2 >= fonts.size()) {
                                            size2 = 0;
                                        }
                                        if (size2 < 0) {
                                            size2 = fonts.size() - 1;
                                        }
                                        fontValue.set(fonts.get(size2));
                                    } else {
                                        size--;
                                    }
                                }
                            }
                        }
                        y += 11;
                    } else {
                        String str5 = value.getName() + "\u00a7f: \u00a7c" + value.get();
                        float stringWidth7 = Fonts.font35.getStringWidth(str5);
                        if (moduleElement.getSettingsWidth() < stringWidth7 + 8.0f) {
                            moduleElement.setSettingsWidth(stringWidth7 + 8.0f);
                        }
                        RenderUtils.drawRect(moduleElement.getX() + moduleElement.getWidth() + 4, y + 2, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), y + 14, Integer.MIN_VALUE);
                        GlStateManager.func_179117_G();
                        Fonts.font35.drawString(str5, moduleElement.getX() + moduleElement.getWidth() + 6, y + 4, 16777215);
                        y += 12;
                    }
                    if (z) {
                        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
                    }
                }
                moduleElement.updatePressed();
                this.mouseDown = Mouse.isButtonDown(0);
                this.rightMouseDown = Mouse.isButtonDown(1);
                if (moduleElement.getSettingsWidth() > 0.0f && y > moduleElement.getY() + 4) {
                    RenderUtils.drawBorderedRect(moduleElement.getX() + moduleElement.getWidth() + 4, moduleElement.getY() + 6, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), y + 2, 1.0f, Integer.MIN_VALUE, 0);
                }
            }
        }
    }

    private BigDecimal round(float f) {
        return new BigDecimal(Float.toString(f)).setScale(2, 4);
    }
}
