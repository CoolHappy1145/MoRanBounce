package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.Colors;
import net.ccbluex.liquidbounce.utils.HanaBiColors;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "PotionRender", description = "PotionRender", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/PotionRender.class */
public class PotionRender extends Module {
    private final Map potionMaxDurations = new HashMap();
    private final ListValue PotionRenderMod = new ListValue("PotionRenderMod", new String[]{"Vape", "HanaBi"}, "Vape");
    Map timerMap = new HashMap();

    /* renamed from: x */
    private int f127x;

    @EventTarget
    public void onRender2D(Render2DEvent render2DEvent) throws NumberFormatException {
        IScaledResolution iScaledResolutionCreateScaledResolution = classProvider.createScaledResolution(f157mc);
        float scaledWidth = iScaledResolutionCreateScaledResolution.getScaledWidth();
        float scaledHeight = iScaledResolutionCreateScaledResolution.getScaledHeight();
        if (((String) this.PotionRenderMod.get()).equals("Vape")) {
            renderPotionStatusVape((int) scaledWidth, (int) scaledHeight);
        } else {
            renderPotionStatusHanaBi((int) scaledWidth, (int) scaledHeight);
        }
    }

    public void renderPotionStatusVape(int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        this.f127x = 0;
        int size = HUD.f157mc.getThePlayer().getActivePotionEffects().size();
        int i5 = size * (-30);
        if (size != 0) {
            RenderUtils.drawRoundedRect(i - 120, (i2 - 30) + i5, i - 10, i2 - 30, 3.0f, new Color(0, 0, 0, 100).getRGB());
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : this.potionMaxDurations.entrySet()) {
            if (HUD.f157mc.getThePlayer().getActivePotionEffect(functions.getPotionById(((Integer) entry.getKey()).intValue())) == null) {
                arrayList.add(entry.getKey());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.potionMaxDurations.remove(Integer.valueOf(((Integer) it.next()).intValue()));
        }
        for (IPotionEffect iPotionEffect : HUD.f157mc.getThePlayer().getActivePotionEffects()) {
            if (!this.potionMaxDurations.containsKey(Integer.valueOf(iPotionEffect.getPotionID())) || ((Integer) this.potionMaxDurations.get(Integer.valueOf(iPotionEffect.getPotionID()))).intValue() < iPotionEffect.getDuration()) {
                this.potionMaxDurations.put(Integer.valueOf(iPotionEffect.getPotionID()), Integer.valueOf(iPotionEffect.getDuration()));
            }
            IPotion potionById = functions.getPotionById(iPotionEffect.getPotionID());
            String i18n = functions.formatI18n(potionById.getName(), new String[]{Arrays.toString(new Object[0])});
            try {
                i3 = Integer.parseInt(iPotionEffect.getDurationString().split(CallSiteDescriptor.TOKEN_DELIMITER)[0]);
                i4 = Integer.parseInt(iPotionEffect.getDurationString().split(CallSiteDescriptor.TOKEN_DELIMITER)[1]);
            } catch (Exception unused) {
                i3 = 0;
                i4 = 0;
            }
            double d = (i3 * 60) + i4;
            if (!this.timerMap.containsKey(potionById)) {
                this.timerMap.put(potionById, Double.valueOf(d));
            }
            if (((Double) this.timerMap.get(potionById)).doubleValue() == 0.0d || d > ((Double) this.timerMap.get(potionById)).doubleValue()) {
                this.timerMap.replace(potionById, Double.valueOf(d));
            }
            int rgb = Colors.blendColors(new float[]{0.0f, 0.5f, 1.0f}, new Color[]{new Color(LinkerCallSite.ARGLIMIT, 50, 56), new Color(236, 129, 44), new Color(5, 134, 105)}, iPotionEffect.getDuration() / (1.0f * ((Integer) this.potionMaxDurations.get(Integer.valueOf(iPotionEffect.getPotionID()))).intValue())).getRGB();
            int i6 = (int) ((i - 6) * 1.33f);
            int fontHeight = (int) ((((i2 - 52) - HUD.f157mc.getFontRendererObj().getFontHeight()) + this.f127x + 5) * 1.33f);
            float duration = (i - 120) + (110.0f * (iPotionEffect.getDuration() / (1.0f * ((Integer) this.potionMaxDurations.get(Integer.valueOf(iPotionEffect.getPotionID()))).intValue())));
            if (potionById.getHasStatusIcon()) {
                classProvider.getGlStateManager().pushMatrix();
                GL11.glDisable(SGL.GL_DEPTH_TEST);
                GL11.glEnable(SGL.GL_BLEND);
                GL11.glDepthMask(false);
                OpenGlHelper.func_148821_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                int statusIconIndex = potionById.getStatusIconIndex();
                HUD.f157mc.getTextureManager().bindTexture(classProvider.createResourceLocation("textures/gui/container/inventory.png"));
                GlStateManager.func_179139_a(0.75d, 0.75d, 0.75d);
                mc2.field_71456_v.func_73729_b(i6 - 138, fontHeight + 8, (statusIconIndex % 8) * 18, 198 + ((statusIconIndex / 8) * 18), 18, 18);
                GL11.glDepthMask(true);
                GL11.glDisable(SGL.GL_BLEND);
                GL11.glEnable(SGL.GL_DEPTH_TEST);
                GlStateManager.func_179121_F();
            }
            int fontHeight2 = ((i2 - HUD.f157mc.getFontRendererObj().getFontHeight()) + this.f127x) - 38;
            RenderUtils.drawArc(i - 104.75f, fontHeight2 + 2.5f, 10.0d, new Color(22, 28, 15).getRGB(), 0, 360.0d, 3);
            RenderUtils.drawArc(i - 104.75f, fontHeight2 + 2.5f, 10.0d, rgb, 0, 360.0f * (iPotionEffect.getDuration() / (1.0f * ((Integer) this.potionMaxDurations.get(Integer.valueOf(iPotionEffect.getPotionID()))).intValue())), 3);
            Fonts.wqy35.drawString(i18n.replaceAll("\u00a7.", ""), i - 85.0f, (fontHeight2 - HUD.f157mc.getFontRendererObj().getFontHeight()) + 2, -1);
            RenderUtils.drawRect(i - 91.0f, fontHeight2 - 3.0f, i - 89.5f, fontHeight2 + 10.0f, new Color(255, 255, 255, 100).getRGB());
            Fonts.Comfortaa35.drawString(iPotionEffect.getDurationString().replaceAll("\u00a7.", ""), i - 85.0f, fontHeight2 + 3.5f, rgb);
            this.f127x -= 30;
        }
    }

    public void renderPotionStatusHanaBi(int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        this.f127x = 0;
        for (IPotionEffect iPotionEffect : f157mc.getThePlayer().getActivePotionEffects()) {
            IPotion potionById = functions.getPotionById(iPotionEffect.getPotionID());
            String i18n = functions.formatI18n(potionById.getName(), new String[]{Arrays.toString(new Object[0])});
            try {
                i3 = Integer.parseInt(iPotionEffect.getDurationString().split(CallSiteDescriptor.TOKEN_DELIMITER)[0]);
                i4 = Integer.parseInt(iPotionEffect.getDurationString().split(CallSiteDescriptor.TOKEN_DELIMITER)[1]);
            } catch (Exception unused) {
                i3 = 0;
                i4 = 0;
            }
            double d = (i3 * 60) + i4;
            if (!this.timerMap.containsKey(potionById)) {
                this.timerMap.put(potionById, Double.valueOf(d));
            }
            if (((Double) this.timerMap.get(potionById)).doubleValue() == 0.0d || d > ((Double) this.timerMap.get(potionById)).doubleValue()) {
                this.timerMap.replace(potionById, Double.valueOf(d));
            }
            switch (iPotionEffect.getAmplifier()) {
                case 0:
                    i18n = i18n + " I";
                    break;
                case 1:
                    i18n = i18n + " II";
                    break;
                case 2:
                    i18n = i18n + " III";
                    break;
                case 3:
                    i18n = i18n + " IV";
                    break;
                case 4:
                    i18n = i18n + " V";
                    break;
                case 5:
                    i18n = i18n + " VI";
                    break;
                case 6:
                    i18n = i18n + " VII";
                    break;
                case 7:
                    i18n = i18n + " VIII";
                    break;
                case 8:
                    i18n = i18n + " IX";
                    break;
                case 9:
                    i18n = i18n + " X";
                    break;
                case 10:
                    i18n = i18n + " X+";
                    break;
            }
            int i5 = HanaBiColors.WHITE.f156c;
            if (iPotionEffect.getDuration() < 600 && iPotionEffect.getDuration() > 300) {
                int i6 = HanaBiColors.YELLOW.f156c;
            } else if (iPotionEffect.getDuration() < 300) {
                int i7 = HanaBiColors.RED.f156c;
            } else if (iPotionEffect.getDuration() > 600) {
                int i8 = HanaBiColors.WHITE.f156c;
            }
            int i9 = (int) ((i - 6) * 1.33f);
            int fontHeight = (int) ((((i2 - 52) - f157mc.getFontRendererObj().getFontHeight()) + this.f127x + 5) * 1.33f);
            RenderUtils.drawRect(i - 120, (i2 - 60) + this.f127x, i - 10, (i2 - 30) + this.f127x, ClientUtils.reAlpha(HanaBiColors.BLACK.f156c, 0.41f));
            if (potionById.getHasStatusIcon()) {
                GlStateManager.func_179094_E();
                GL11.glDisable(SGL.GL_DEPTH_TEST);
                GL11.glEnable(SGL.GL_BLEND);
                GL11.glDepthMask(false);
                OpenGlHelper.func_148821_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                int statusIconIndex = potionById.getStatusIconIndex();
                f157mc.getTextureManager().bindTexture(classProvider.createResourceLocation("textures/gui/container/inventory.png"));
                GlStateManager.func_179139_a(0.75d, 0.75d, 0.75d);
                mc2.field_71456_v.func_73729_b(i9 - 138, fontHeight + 8, 0 + ((statusIconIndex % 8) * 18), 198 + ((statusIconIndex / 8) * 18), 18, 18);
                GL11.glDepthMask(true);
                GL11.glDisable(SGL.GL_BLEND);
                GL11.glEnable(SGL.GL_DEPTH_TEST);
                GlStateManager.func_179121_F();
            }
            int fontHeight2 = ((i2 - f157mc.getFontRendererObj().getFontHeight()) + this.f127x) - 38;
            Fonts.wqy35.drawString(i18n.replaceAll("\u00a7.", ""), i - 91.0f, (fontHeight2 - f157mc.getFontRendererObj().getFontHeight()) + 1, potionById.getLiquidColor());
            Fonts.wqy35.drawString(iPotionEffect.getDurationString().replaceAll("\u00a7.", ""), i - 91.0f, fontHeight2 + 4, ClientUtils.reAlpha(-1, 0.8f));
            this.f127x -= 35;
        }
    }
}
