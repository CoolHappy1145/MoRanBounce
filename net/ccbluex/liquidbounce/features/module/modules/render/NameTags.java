package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0018\u0010!\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00020\r`\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006#"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/NameTags;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "borderValue", "clearNamesValue", "distanceValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "hackerValue", "hackers", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "healthValue", "jelloAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "jelloColorValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "pingValue", "scaleValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getPlayerName", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "isHacker", "", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "renderNameTag", "tag", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "NameTags", description = "Name Render", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/NameTags.class */
public final class NameTags extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Simple", "Liquid", "Jello"}, "Simple");
    private final BoolValue healthValue = new BoolValue("Health", true);
    private final BoolValue pingValue = new BoolValue("Ping", true);
    private final BoolValue distanceValue = new BoolValue("Distance", false);
    private final BoolValue armorValue = new BoolValue("Armor", true);
    private final BoolValue clearNamesValue = new BoolValue("ClearNames", true);
    private final FontValue fontValue;
    private final BoolValue borderValue;
    private final BoolValue hackerValue;
    private final BoolValue jelloColorValue;
    private final IntegerValue jelloAlphaValue;
    private final FloatValue scaleValue;
    private final ArrayList hackers;

    public NameTags() {
        IFontRenderer iFontRenderer = Fonts.font40;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.font40");
        this.fontValue = new FontValue("Font", iFontRenderer);
        this.borderValue = new BoolValue("Border", true);
        this.hackerValue = new BoolValue("Hacker", true);
        this.jelloColorValue = new BoolValue("JelloHPColor", true);
        this.jelloAlphaValue = new IntegerValue("JelloAlpha", 170, 0, 255);
        this.scaleValue = new FloatValue("Scale", 1.0f, 1.0f, 4.0f);
        this.hackers = new ArrayList();
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        String unformattedText;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        for (IEntity iEntity : theWorld.getLoadedEntityList()) {
            if (EntityUtils.isSelected(iEntity, false)) {
                IEntityLivingBase iEntityLivingBaseAsEntityLivingBase = iEntity.asEntityLivingBase();
                StringBuilder sbAppend = new StringBuilder().append((((Boolean) this.hackerValue.get()).booleanValue() && isHacker(iEntity.asEntityLivingBase())) ? "\u00a7c" : "").append((this.modeValue.equals("Liquid") || !AntiBot.Companion.isBot(iEntity.asEntityLivingBase())) ? "" : "\u00a7e");
                if (((Boolean) this.clearNamesValue.get()).booleanValue()) {
                    unformattedText = iEntity.getName();
                } else {
                    IIChatComponent displayName = iEntity.getDisplayName();
                    if (displayName == null) {
                        Intrinsics.throwNpe();
                    }
                    unformattedText = displayName.getUnformattedText();
                }
                renderNameTag(iEntityLivingBaseAsEntityLivingBase, sbAppend.append(unformattedText).toString());
            }
        }
    }

    private final String getPlayerName(IEntityLivingBase iEntityLivingBase) {
        String str;
        IIChatComponent displayName = iEntityLivingBase.getDisplayName();
        if (displayName == null) {
            Intrinsics.throwNpe();
        }
        String formattedText = displayName.getFormattedText();
        String str2 = "";
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Teams.class);
        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.misc.Teams");
        }
        Teams teams = (Teams) module;
        if (LiquidBounce.INSTANCE.getFileManager().friendsConfig.isFriend(iEntityLivingBase.getName())) {
            str2 = "\u00a7b[Friend] ";
        }
        if (teams.isInYourTeam(iEntityLivingBase)) {
            str2 = str2 + "\u00a7a[TEAM] ";
        }
        if (AntiBot.Companion.isBot(iEntityLivingBase)) {
            str2 = str2 + "\u00a7e[BOT] ";
        }
        if (!AntiBot.Companion.isBot(iEntityLivingBase) && !teams.isInYourTeam(iEntityLivingBase)) {
            if (LiquidBounce.INSTANCE.getFileManager().friendsConfig.isFriend(iEntityLivingBase.getName())) {
                str = "\u00a7b[Friend] \u00a7c";
            } else {
                str = "\u00a7c";
            }
            str2 = str;
        }
        return formattedText + str2;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final void renderNameTag(IEntityLivingBase iEntityLivingBase, String str) {
        String string;
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        GL11.glPushMatrix();
        IRenderManager renderManager = MinecraftInstance.f157mc.getRenderManager();
        ITimer timer = MinecraftInstance.f157mc.getTimer();
        GL11.glTranslated((iEntityLivingBase.getLastTickPosX() + ((iEntityLivingBase.getPosX() - iEntityLivingBase.getLastTickPosX()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosX(), ((iEntityLivingBase.getLastTickPosY() + ((iEntityLivingBase.getPosY() - iEntityLivingBase.getLastTickPosY()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosY()) + iEntityLivingBase.getEyeHeight() + 0.55d, (iEntityLivingBase.getLastTickPosZ() + ((iEntityLivingBase.getPosZ() - iEntityLivingBase.getLastTickPosZ()) * timer.getRenderPartialTicks())) - renderManager.getRenderPosZ());
        GL11.glRotatef(-MinecraftInstance.f157mc.getRenderManager().getPlayerViewY(), 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(MinecraftInstance.f157mc.getRenderManager().getPlayerViewX(), 1.0f, 0.0f, 0.0f);
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        float distanceToEntity = thePlayer.getDistanceToEntity(iEntityLivingBase) / 4.0f;
        if (distanceToEntity < 1.0f) {
            distanceToEntity = 1.0f;
        }
        float fFloatValue = (distanceToEntity / 150.0f) * ((Number) this.scaleValue.get()).floatValue();
        RenderUtils.disableGlCap(new int[]{2896, SGL.GL_DEPTH_TEST});
        RenderUtils.enableGlCap(SGL.GL_BLEND);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        String str2 = (String) this.modeValue.get();
        if (str2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str2.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1102567108:
                if (lowerCase.equals("liquid")) {
                    boolean zIsBot = AntiBot.Companion.isBot(iEntityLivingBase);
                    String str3 = zIsBot ? "\u00a73" : iEntityLivingBase.isInvisible() ? "\u00a76" : iEntityLivingBase.isSneaking() ? "\u00a74" : "\u00a77";
                    int ping = PlayerExtensionKt.getPing(iEntityLivingBase);
                    if (((Boolean) this.distanceValue.get()).booleanValue()) {
                        StringBuilder sbAppend = new StringBuilder().append("\u00a77 [\u00a7a");
                        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        string = sbAppend.append(MathKt.roundToInt(thePlayer2.getDistanceToEntity(iEntityLivingBase))).append("\u00a77]").toString();
                    } else {
                        string = "";
                    }
                    String str4 = string + ((((Boolean) this.pingValue.get()).booleanValue() && MinecraftInstance.classProvider.isEntityPlayer(iEntityLivingBase)) ? (ping > 200 ? "\u00a7c" : ping > 100 ? "\u00a7e" : "\u00a7a") + ping + "ms \u00a77" : "") + str3 + str + (((Boolean) this.healthValue.get()).booleanValue() ? "\u00a77 [\u00a7f" + ((int) iEntityLivingBase.getHealth()) + "\u00a7c\u2764\u00a77]" : "") + (zIsBot ? " \u00a77[\u00a76\u00a7lBot\u00a77]" : "");
                    GL11.glScalef(-fFloatValue, -fFloatValue, fFloatValue);
                    int stringWidth = iFontRenderer.getStringWidth(str4) / 2;
                    if (((Boolean) this.borderValue.get()).booleanValue()) {
                        RenderUtils.drawBorderedRect((-stringWidth) - 2.0f, -2.0f, stringWidth + 4.0f, iFontRenderer.getFontHeight() + 2.0f, 2.0f, new Color(255, 255, 255, 90).getRGB(), Integer.MIN_VALUE);
                    } else {
                        RenderUtils.drawRect((-stringWidth) - 2.0f, -2.0f, stringWidth + 4.0f, iFontRenderer.getFontHeight() + 2.0f, Integer.MIN_VALUE);
                    }
                    iFontRenderer.drawString(str4, 1.0f + (-stringWidth), Intrinsics.areEqual(iFontRenderer, Fonts.minecraftFont) ? 1.0f : 1.5f, 16777215, true);
                    if (((Boolean) this.armorValue.get()).booleanValue() && MinecraftInstance.classProvider.isEntityPlayer(iEntityLivingBase)) {
                        for (int i = 0; i <= 4; i++) {
                            if (iEntityLivingBase.getEquipmentInSlot(i) != null) {
                                MinecraftInstance.f157mc.getRenderItem().setZLevel(-147.0f);
                                IRenderItem renderItem = MinecraftInstance.f157mc.getRenderItem();
                                IItemStack equipmentInSlot = iEntityLivingBase.getEquipmentInSlot(i);
                                if (equipmentInSlot == null) {
                                    Intrinsics.throwNpe();
                                }
                                renderItem.renderItemAndEffectIntoGUI(equipmentInSlot, (-50) + (i * 20), -22);
                            }
                        }
                        GlStateManager.func_179141_d();
                        GlStateManager.func_179084_k();
                        GlStateManager.func_179098_w();
                        break;
                    }
                }
                break;
            case -902286926:
                if (lowerCase.equals("simple")) {
                    float fCoerceAtMost = RangesKt.coerceAtMost(iEntityLivingBase.getHealth() / iEntityLivingBase.getMaxHealth(), 1.0f);
                    int iCoerceAtLeast = RangesKt.coerceAtLeast(iFontRenderer.getStringWidth(str), 30) / 2;
                    float f = (iCoerceAtLeast * 2) + 12.0f;
                    GL11.glScalef((-fFloatValue) * 2.0f, (-fFloatValue) * 2.0f, fFloatValue * 2.0f);
                    RenderUtils.drawRect((-iCoerceAtLeast) - 6.0f, (-iFontRenderer.getFontHeight()) * 1.7f, iCoerceAtLeast + 6.0f, -2.0f, new Color(0, 0, 0, ((Number) this.jelloAlphaValue.get()).intValue()));
                    RenderUtils.drawRect((-iCoerceAtLeast) - 6.0f, -2.0f, ((-iCoerceAtLeast) - 6.0f) + (f * fCoerceAtMost), 0.0f, ColorUtils.healthColor(iEntityLivingBase.getHealth(), iEntityLivingBase.getMaxHealth(), ((Number) this.jelloAlphaValue.get()).intValue()));
                    RenderUtils.drawRect(((-iCoerceAtLeast) - 6.0f) + (f * fCoerceAtMost), -2.0f, iCoerceAtLeast + 6.0f, 0.0f, new Color(0, 0, 0, ((Number) this.jelloAlphaValue.get()).intValue()));
                    Color color = Color.WHITE;
                    Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
                    iFontRenderer.drawString(str, (int) ((-iFontRenderer.getStringWidth(str)) * 0.5f), (int) ((-iFontRenderer.getFontHeight()) * 1.4f), color.getRGB());
                    break;
                }
                break;
            case 101009364:
                if (lowerCase.equals("jello")) {
                    Color color2 = new Color(255, 255, 255, ((Number) this.jelloAlphaValue.get()).intValue());
                    IIChatComponent displayName = iEntityLivingBase.getDisplayName();
                    if (displayName == null) {
                        Intrinsics.throwNpe();
                    }
                    String unformattedText = displayName.getUnformattedText();
                    if (((Boolean) this.jelloColorValue.get()).booleanValue() && StringsKt.startsWith$default(unformattedText, "\u00a7", false, 2, (Object) null)) {
                        if (unformattedText == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String strSubstring = unformattedText.substring(1, 2);
                        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        color2 = ColorUtils.colorCode(strSubstring, ((Number) this.jelloAlphaValue.get()).intValue());
                    }
                    Color color3 = new Color(50, 50, 50, ((Number) this.jelloAlphaValue.get()).intValue());
                    int stringWidth2 = iFontRenderer.getStringWidth(str) / 2;
                    float f2 = (stringWidth2 + 4.0f) - ((-stringWidth2) - 4.0f);
                    float health = iEntityLivingBase.getHealth() / iEntityLivingBase.getMaxHealth();
                    GL11.glScalef((-fFloatValue) * 2.0f, (-fFloatValue) * 2.0f, fFloatValue * 2.0f);
                    RenderUtils.drawRect((-stringWidth2) - 4.0f, (-iFontRenderer.getFontHeight()) * 3.0f, stringWidth2 + 4.0f, -3.0f, color3);
                    if (health > 1.0f) {
                        health = 1.0f;
                    }
                    RenderUtils.drawRect((-stringWidth2) - 4.0f, -3.0f, ((-stringWidth2) - 4.0f) + (f2 * health), 1.0f, color2);
                    RenderUtils.drawRect(((-stringWidth2) - 4.0f) + (f2 * health), -3.0f, stringWidth2 + 4.0f, 1.0f, color3);
                    int i2 = ((-iFontRenderer.getFontHeight()) * 2) - 4;
                    Color color4 = Color.WHITE;
                    Intrinsics.checkExpressionValueIsNotNull(color4, "Color.WHITE");
                    iFontRenderer.drawString(str, -stringWidth2, i2, color4.getRGB());
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                    int i3 = (-iFontRenderer.getFontHeight()) * 2;
                    Color color5 = Color.WHITE;
                    Intrinsics.checkExpressionValueIsNotNull(color5, "Color.WHITE");
                    iFontRenderer.drawString("Health: " + ((int) iEntityLivingBase.getHealth()), (-stringWidth2) * 2, i3, color5.getRGB());
                    break;
                }
                break;
        }
        RenderUtils.resetCaps();
        GlStateManager.func_179117_G();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    public final boolean isHacker(@NotNull IEntityLivingBase entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        if (MinecraftInstance.classProvider.isEntityPlayer(entity)) {
            return CollectionsKt.contains(this.hackers, entity.getName());
        }
        return false;
    }
}
