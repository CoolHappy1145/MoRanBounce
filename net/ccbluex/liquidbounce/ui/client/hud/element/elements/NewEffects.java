package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.HanaBiColors;
import net.ccbluex.liquidbounce.utils.Skid.PotionData;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.Skid.Translate;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

@ElementInfo(name = "NewEffects")
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/NewEffects.class */
public class NewEffects extends Element {
    private final Map potionMap = new HashMap();

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    public Border drawElement() throws NumberFormatException {
        PotionData potionData;
        int i;
        int i2;
        double d;
        GlStateManager.func_179094_E();
        int i3 = 0;
        for (IPotionEffect iPotionEffect : f157mc.getThePlayer().getActivePotionEffects()) {
            IPotion potionById = functions.getPotionById(iPotionEffect.getPotionID());
            String i18n = functions.formatI18n(potionById.getName(), new String[0]);
            if (this.potionMap.containsKey(potionById) && ((PotionData) this.potionMap.get(potionById)).level == iPotionEffect.getAmplifier()) {
                potionData = (PotionData) this.potionMap.get(potionById);
            } else {
                Map map = this.potionMap;
                PotionData potionData2 = new PotionData(potionById, new Translate(0.0f, (-40.0f) + i3), iPotionEffect.getAmplifier());
                potionData = potionData2;
                map.put(potionById, potionData2);
            }
            boolean z = true;
            Iterator it = f157mc.getThePlayer().getActivePotionEffects().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((IPotionEffect) it.next()).getAmplifier() == potionData.level) {
                    z = false;
                    break;
                }
            }
            if (z) {
                this.potionMap.remove(potionById);
            }
            try {
                i = Integer.parseInt(iPotionEffect.getDurationString().split(CallSiteDescriptor.TOKEN_DELIMITER)[0]);
                i2 = Integer.parseInt(iPotionEffect.getDurationString().split(CallSiteDescriptor.TOKEN_DELIMITER)[1]);
            } catch (Exception unused) {
                i = 100;
                i2 = 1000;
            }
            int i4 = (i * 60) + i2;
            if (potionData.getMaxTimer() == 0 || i4 > potionData.getMaxTimer()) {
                potionData.maxTimer = i4;
            }
            float maxTimer = i4 >= 0.0d ? (float) ((i4 / potionData.getMaxTimer()) * 100.0d) : 0.0f;
            int iRound = Math.round(potionData.translate.getY() + 5.0f);
            float fMax = Math.max(maxTimer, 2.0f);
            potionData.translate.interpolate(0.0f, i3, 0.1d);
            PotionData potionData3 = potionData;
            double animationX = potionData.getAnimationX();
            double d2 = 1.2f * fMax;
            float fMax2 = (float) (0.01d * Math.max(10.0f, Math.abs(potionData.animationX - (1.2f * fMax)) * 15.0f) * 0.3f);
            if (animationX < d2) {
                if (animationX + fMax2 < d2) {
                    d = animationX + fMax2;
                } else {
                    d = d2;
                }
            } else if (animationX - fMax2 > d2) {
                d = animationX - fMax2;
            } else {
                d = d2;
            }
            potionData3.animationX = (float) d;
            RenderUtils.drawRectPotion(0.0f, potionData.translate.getY(), 120.0f, potionData.translate.getY() + 30.0f, ClientUtils.reAlpha(HanaBiColors.GREY.f156c, 0.1f));
            RenderUtils.drawRectPotion(0.0f, potionData.translate.getY(), potionData.animationX, potionData.translate.getY() + 30.0f, ClientUtils.reAlpha(new Color(34, 24, 20).brighter().getRGB(), 0.3f));
            RenderUtils.drawShadow(0, Math.round(potionData.translate.getY()), 120, 30);
            float y = potionData.translate.getY() + 13.0f;
            Fonts.Sfui35.drawString(i18n + " " + intToRomanByGreedy(iPotionEffect.getAmplifier() + 1), 29.0f, y - f157mc.getFontRendererObj().getFontHeight(), ClientUtils.reAlpha(HanaBiColors.WHITE.f156c, 0.8f));
            Fonts.font35.drawString(iPotionEffect.getDurationString(), 29.0f, y + 4.0f, ClientUtils.reAlpha(new Color(SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD).getRGB(), 0.5f));
            if (potionById.getHasStatusIcon()) {
                GlStateManager.func_179094_E();
                GL11.glDisable(SGL.GL_DEPTH_TEST);
                GL11.glEnable(SGL.GL_BLEND);
                GL11.glDepthMask(false);
                OpenGlHelper.func_148821_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                int statusIconIndex = potionById.getStatusIconIndex();
                f157mc.getTextureManager().bindTexture(classProvider.createResourceLocation("textures/gui/container/inventory.png"));
                mc2.field_71456_v.func_175174_a(6.0f, iRound + 40, (statusIconIndex % 8) * 18, 198 + ((statusIconIndex / 8) * 18), 18, 18);
                GL11.glDepthMask(true);
                GL11.glDisable(SGL.GL_BLEND);
                GL11.glEnable(SGL.GL_DEPTH_TEST);
                GlStateManager.func_179121_F();
            }
            i3 -= 35;
        }
        GlStateManager.func_179121_F();
        return new Border(0.0f, 0.0f, 120.0f, 30.0f);
    }

    private String intToRomanByGreedy(int i) {
        int[] iArr = {1000, 900, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] strArr = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < iArr.length && i >= 0; i2++) {
            while (iArr[i2] <= i) {
                i -= iArr[i2];
                sb.append(strArr[i2]);
            }
        }
        return sb.toString();
    }
}
