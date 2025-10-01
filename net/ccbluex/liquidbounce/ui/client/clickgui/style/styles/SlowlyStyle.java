package net.ccbluex.liquidbounce.p005ui.client.clickgui.style.styles;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.log4j.net.SyslogAppender;
import org.lwjgl.input.Mouse;
import org.slf4j.Marker;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/style/styles/SlowlyStyle.class */
public class SlowlyStyle extends Style {
    private boolean mouseDown;
    private boolean rightMouseDown;

    public static float drawSlider(float f, float f2, float f3, int i, int i2, int i3, int i4, int i5, Color color) {
        float fMax = i + ((i3 * (Math.max(f2, Math.min(f, f3)) - f2)) / (f3 - f2));
        RenderUtils.drawRect(i, i2, i + i3, i2 + 2, Integer.MAX_VALUE);
        RenderUtils.drawRect(i, i2, fMax, i2 + 2, color);
        RenderUtils.drawFilledCircle((int) fMax, i2 + 1, 3.0f, color);
        if (i4 >= i && i4 <= i + i3 && i5 >= i2 && i5 <= i2 + 3 && Mouse.isButtonDown(0)) {
            double d = (i4 - i) / (i3 - 3.0d);
            return new BigDecimal(Double.toString(f2 + ((f3 - f2) * (d < 0.0d ? 0.0d : d > 1.0d ? 1.0d : d)))).setScale(2, 4).floatValue();
        }
        return f;
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.style.Style
    public void drawPanel(int i, int i2, Panel panel) {
        RenderUtils.drawBorderedRect(panel.getX(), panel.getY() - 3.0f, panel.getX() + panel.getWidth(), panel.getY() + 17.0f, 3.0f, new Color(42, 57, 79).getRGB(), new Color(42, 57, 79).getRGB());
        if (panel.getFade() > 0) {
            RenderUtils.drawBorderedRect(panel.getX(), panel.getY() + 17.0f, panel.getX() + panel.getWidth(), panel.getY() + 19 + panel.getFade(), 3.0f, new Color(54, 71, 96).getRGB(), new Color(54, 71, 96).getRGB());
            RenderUtils.drawBorderedRect(panel.getX(), panel.getY() + 17 + panel.getFade(), panel.getX() + panel.getWidth(), panel.getY() + 19 + panel.getFade() + 5, 3.0f, new Color(42, 57, 79).getRGB(), new Color(42, 57, 79).getRGB());
        }
        GlStateManager.func_179117_G();
        Fonts.font35.drawString(panel.getName(), (int) (panel.getX() - ((Fonts.font35.getStringWidth("\u00a7f" + StringUtils.func_76338_a(panel.getName())) - 100.0f) / 2.0f)), (panel.getY() + 7) - 3, Color.WHITE.getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.style.Style
    public void drawDescription(int i, int i2, String str) {
        RenderUtils.drawBorderedRect(i + 9, i2, i + Fonts.font35.getStringWidth(str) + 14, i2 + Fonts.font35.getFontHeight() + 3, 3.0f, new Color(42, 57, 79).getRGB(), new Color(42, 57, 79).getRGB());
        GlStateManager.func_179117_G();
        Fonts.font35.drawString(str, i + 12, i2 + (Fonts.font35.getFontHeight() / 2), Color.WHITE.getRGB());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.style.Style
    public void drawButtonElement(int i, int i2, ButtonElement buttonElement) {
        int x = buttonElement.getX() - 1;
        Gui.func_73734_a(buttonElement.getY() - 1, buttonElement.getX() + buttonElement.getWidth() + 1, buttonElement.getY(), buttonElement + 16 + 1, hoverColor(buttonElement.getColor() != Integer.MAX_VALUE ? new Color(7, SyslogAppender.LOG_LOCAL3, 252) : new Color(54, 71, 96), buttonElement.hoverTime).getRGB());
        GlStateManager.func_179117_G();
        Fonts.font35.drawString(buttonElement.getDisplayName(), buttonElement.getX() + 5, buttonElement.getY() + 5, Color.WHITE.getRGB());
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.clickgui.style.Style
    public void drawModuleElement(int i, int i2, ModuleElement moduleElement) throws IllegalAccessException, IllegalArgumentException {
        Gui.func_73734_a(moduleElement.getX() - 1, moduleElement.getY() - 1, moduleElement.getX() + moduleElement.getWidth() + 1, moduleElement.getY() + moduleElement.getHeight() + 1, hoverColor(new Color(54, 71, 96), moduleElement.hoverTime).getRGB());
        Gui.func_73734_a(moduleElement.getX() - 1, moduleElement.getY() - 1, moduleElement.getX() + moduleElement.getWidth() + 1, moduleElement.getY() + moduleElement.getHeight() + 1, hoverColor(new Color(7, SyslogAppender.LOG_LOCAL3, 252, moduleElement.slowlyFade), moduleElement.hoverTime).getRGB());
        GlStateManager.func_179117_G();
        Fonts.font35.drawString(moduleElement.getDisplayName(), moduleElement.getX() + 5, moduleElement.getY() + 5, Color.WHITE.getRGB());
        List<Value> values = moduleElement.getModule().getValues();
        if (!values.isEmpty()) {
            Fonts.font35.drawString(">", (moduleElement.getX() + moduleElement.getWidth()) - 8, moduleElement.getY() + 5, Color.WHITE.getRGB());
            if (moduleElement.isShowSettings()) {
                if (moduleElement.getSettingsWidth() > 0.0f && moduleElement.slowlySettingsYPos > moduleElement.getY() + 6) {
                    RenderUtils.drawBorderedRect(moduleElement.getX() + moduleElement.getWidth() + 4, moduleElement.getY() + 6, moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth(), moduleElement.slowlySettingsYPos + 2, 3.0f, new Color(54, 71, 96).getRGB(), new Color(54, 71, 96).getRGB());
                }
                moduleElement.slowlySettingsYPos = moduleElement.getY() + 6;
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
                        if (i >= moduleElement.getX() + moduleElement.getWidth() + 4 && i <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && i2 >= moduleElement.slowlySettingsYPos && i2 <= moduleElement.slowlySettingsYPos + 12 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                            BoolValue boolValue = (BoolValue) value;
                            boolValue.set(Boolean.valueOf(!((Boolean) boolValue.get()).booleanValue()));
                            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                        }
                        Fonts.font35.drawString(name, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, ((Boolean) ((BoolValue) value).get()).booleanValue() ? Color.WHITE.getRGB() : Integer.MAX_VALUE);
                        moduleElement.slowlySettingsYPos += 11;
                    } else if (value instanceof ListValue) {
                        ListValue listValue = (ListValue) value;
                        String name2 = value.getName();
                        float stringWidth2 = Fonts.font35.getStringWidth(name2);
                        if (moduleElement.getSettingsWidth() < stringWidth2 + 16.0f) {
                            moduleElement.setSettingsWidth(stringWidth2 + 16.0f);
                        }
                        Fonts.font35.drawString(name2, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, 16777215);
                        Fonts.font35.drawString(listValue.openList ? "-" : Marker.ANY_NON_NULL_MARKER, (int) (((moduleElement.getX() + moduleElement.getWidth()) + moduleElement.getSettingsWidth()) - (listValue.openList ? 5 : 6)), moduleElement.slowlySettingsYPos + 2, 16777215);
                        if (i >= moduleElement.getX() + moduleElement.getWidth() + 4 && i <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && i2 >= moduleElement.slowlySettingsYPos && i2 <= moduleElement.slowlySettingsYPos + Fonts.font35.getFontHeight() && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                            listValue.openList = !listValue.openList;
                            f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                        }
                        moduleElement.slowlySettingsYPos += Fonts.font35.getFontHeight() + 1;
                        for (String str : listValue.getValues()) {
                            float stringWidth3 = Fonts.font35.getStringWidth("> " + str);
                            if (moduleElement.getSettingsWidth() < stringWidth3 + 12.0f) {
                                moduleElement.setSettingsWidth(stringWidth3 + 12.0f);
                            }
                            if (listValue.openList) {
                                if (i >= moduleElement.getX() + moduleElement.getWidth() + 4 && i <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && i2 >= moduleElement.slowlySettingsYPos + 2 && i2 <= moduleElement.slowlySettingsYPos + 14 && Mouse.isButtonDown(0) && moduleElement.isntPressed()) {
                                    listValue.set(str);
                                    f157mc.getSoundHandler().playSound("ui.button.click", 1.0f);
                                }
                                GlStateManager.func_179117_G();
                                Fonts.font35.drawString("> " + str, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, (listValue.get() == null || !((String) listValue.get()).equalsIgnoreCase(str)) ? Integer.MAX_VALUE : Color.WHITE.getRGB());
                                moduleElement.slowlySettingsYPos += Fonts.font35.getFontHeight() + 1;
                            }
                        }
                        if (!listValue.openList) {
                            moduleElement.slowlySettingsYPos++;
                        }
                    } else if (value instanceof FloatValue) {
                        FloatValue floatValue = (FloatValue) value;
                        String str2 = value.getName() + "\u00a7f: " + round(((Float) floatValue.get()).floatValue());
                        float stringWidth4 = Fonts.font35.getStringWidth(str2);
                        if (moduleElement.getSettingsWidth() < stringWidth4 + 8.0f) {
                            moduleElement.setSettingsWidth(stringWidth4 + 8.0f);
                        }
                        float fDrawSlider = drawSlider(((Float) floatValue.get()).floatValue(), floatValue.getMinimum(), floatValue.getMaximum(), moduleElement.getX() + moduleElement.getWidth() + 8, moduleElement.slowlySettingsYPos + 14, ((int) moduleElement.getSettingsWidth()) - 12, i, i2, new Color(7, SyslogAppender.LOG_LOCAL3, 252));
                        if (fDrawSlider != ((Float) floatValue.get()).floatValue()) {
                            floatValue.set((Object) Float.valueOf(fDrawSlider));
                        }
                        Fonts.font35.drawString(str2, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 3, 16777215);
                        moduleElement.slowlySettingsYPos += 19;
                    } else if (value instanceof IntegerValue) {
                        IntegerValue integerValue = (IntegerValue) value;
                        String str3 = value.getName() + "\u00a7f: " + (value instanceof BlockValue ? BlockUtils.getBlockName(((Integer) integerValue.get()).intValue()) + " (" + integerValue.get() + ")" : (Serializable) integerValue.get());
                        float stringWidth5 = Fonts.font35.getStringWidth(str3);
                        if (moduleElement.getSettingsWidth() < stringWidth5 + 8.0f) {
                            moduleElement.setSettingsWidth(stringWidth5 + 8.0f);
                        }
                        float fDrawSlider2 = drawSlider(((Integer) integerValue.get()).intValue(), integerValue.getMinimum(), integerValue.getMaximum(), moduleElement.getX() + moduleElement.getWidth() + 8, moduleElement.slowlySettingsYPos + 14, ((int) moduleElement.getSettingsWidth()) - 12, i, i2, new Color(7, SyslogAppender.LOG_LOCAL3, 252));
                        if (fDrawSlider2 != ((Integer) integerValue.get()).intValue()) {
                            integerValue.set((Object) Integer.valueOf((int) fDrawSlider2));
                        }
                        Fonts.font35.drawString(str3, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 3, 16777215);
                        moduleElement.slowlySettingsYPos += 19;
                    } else if (value instanceof FontValue) {
                        FontValue fontValue = (FontValue) value;
                        IFontRenderer iFontRenderer = (IFontRenderer) fontValue.get();
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
                        Fonts.font35.drawString(str4, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 2, Color.WHITE.getRGB());
                        int stringWidth6 = Fonts.font35.getStringWidth(str4);
                        if (moduleElement.getSettingsWidth() < stringWidth6 + 8) {
                            moduleElement.setSettingsWidth(stringWidth6 + 8);
                        }
                        if (((Mouse.isButtonDown(0) && !this.mouseDown) || (Mouse.isButtonDown(1) && !this.rightMouseDown)) && i >= moduleElement.getX() + moduleElement.getWidth() + 4 && i <= moduleElement.getX() + moduleElement.getWidth() + moduleElement.getSettingsWidth() && i2 >= moduleElement.slowlySettingsYPos && i2 <= moduleElement.slowlySettingsYPos + 12) {
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
                        moduleElement.slowlySettingsYPos += 11;
                    } else {
                        String str5 = value.getName() + "\u00a7f: " + value.get();
                        float stringWidth7 = Fonts.font35.getStringWidth(str5);
                        if (moduleElement.getSettingsWidth() < stringWidth7 + 8.0f) {
                            moduleElement.setSettingsWidth(stringWidth7 + 8.0f);
                        }
                        GlStateManager.func_179117_G();
                        Fonts.font35.drawString(str5, moduleElement.getX() + moduleElement.getWidth() + 6, moduleElement.slowlySettingsYPos + 4, 16777215);
                        moduleElement.slowlySettingsYPos += 12;
                    }
                    if (z) {
                        AWTFontRenderer.Companion.setAssumeNonVolatile(true);
                    }
                }
                moduleElement.updatePressed();
                this.mouseDown = Mouse.isButtonDown(0);
                this.rightMouseDown = Mouse.isButtonDown(1);
            }
        }
    }

    private BigDecimal round(float f) {
        return new BigDecimal(Float.toString(f)).setScale(2, 4);
    }

    private Color hoverColor(Color color, int i) {
        return new Color(Math.max(color.getRed() - (i * 2), 0), Math.max(color.getGreen() - (i * 2), 0), Math.max(color.getBlue() - (i * 2), 0), color.getAlpha());
    }
}
