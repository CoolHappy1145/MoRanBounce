package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.CloseableKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IWorldRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.vertex.IVertexBuffer;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.MiniMapRegister;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

@ElementInfo(name = "Radar", disableScale = true, priority = 1)
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdJ\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd &2\u00020\u0001:\u0001&B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\"\u001a\u00020\u00152\u0006\u0010#\u001a\u00020\u0018H\u0002J\n\u0010$\u001a\u0004\u0018\u00010%H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001f\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010 \u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010!\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006'"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "(DD)V", "backgroundAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundBlueValue", "backgroundGreenValue", "backgroundRedValue", "borderAlphaValue", "borderBlueValue", "borderGreenValue", "borderRainbowValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "borderRedValue", "borderStrengthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "fovAngleValue", "fovMarkerVertexBuffer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/vertex/IVertexBuffer;", "fovSizeValue", "lastFov", "", "minimapValue", "playerShapeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "playerSizeValue", "rainbowXValue", "rainbowYValue", "sizeValue", "useESPColorsValue", "viewDistanceValue", "createFovIndicator", "angle", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar.class */
public final class Radar extends Element {
    private final FloatValue sizeValue;
    private final FloatValue viewDistanceValue;
    private final ListValue playerShapeValue;
    private final FloatValue playerSizeValue;
    private final BoolValue useESPColorsValue;
    private final FloatValue fovSizeValue;
    private final FloatValue fovAngleValue;
    private final BoolValue minimapValue;
    private final FloatValue rainbowXValue;
    private final FloatValue rainbowYValue;
    private final IntegerValue backgroundRedValue;
    private final IntegerValue backgroundGreenValue;
    private final IntegerValue backgroundBlueValue;
    private final IntegerValue backgroundAlphaValue;
    private final FloatValue borderStrengthValue;
    private final IntegerValue borderRedValue;
    private final IntegerValue borderGreenValue;
    private final IntegerValue borderBlueValue;
    private final IntegerValue borderAlphaValue;
    private final BoolValue borderRainbowValue;
    private IVertexBuffer fovMarkerVertexBuffer;
    private float lastFov;
    public static final Companion Companion = new Companion(null);
    private static final float SQRT_OF_TWO = (float) Math.sqrt(2.0d);

    public Radar() {
        this(0.0d, 0.0d, 3, null);
    }

    public Radar(double d, double d2) {
        super(d, d2, 0.0f, null, 12, null);
        this.sizeValue = new FloatValue("Size", 90.0f, 30.0f, 500.0f);
        this.viewDistanceValue = new FloatValue("View Distance", 4.0f, 0.5f, 32.0f);
        this.playerShapeValue = new ListValue("Player Shape", new String[]{"Triangle", "Rectangle", "Circle"}, "Triangle");
        this.playerSizeValue = new FloatValue("Player Size", 2.0f, 0.5f, 20.0f);
        this.useESPColorsValue = new BoolValue("Use ESP Colors", true);
        this.fovSizeValue = new FloatValue("FOV Size", 10.0f, 0.0f, 50.0f);
        this.fovAngleValue = new FloatValue("FOV Angle", 70.0f, 30.0f, 160.0f);
        this.minimapValue = new BoolValue("Minimap", true);
        this.rainbowXValue = new FloatValue("Rainbow-X", -1000.0f, -2000.0f, 2000.0f);
        this.rainbowYValue = new FloatValue("Rainbow-Y", -1000.0f, -2000.0f, 2000.0f);
        this.backgroundRedValue = new IntegerValue("Background Red", 0, 0, 255);
        this.backgroundGreenValue = new IntegerValue("Background Green", 0, 0, 255);
        this.backgroundBlueValue = new IntegerValue("Background Blue", 0, 0, 255);
        this.backgroundAlphaValue = new IntegerValue("Background Alpha", 50, 0, 255);
        this.borderStrengthValue = new FloatValue("Border Strength", 2.0f, 1.0f, 5.0f);
        this.borderRedValue = new IntegerValue("Border Red", 0, 0, 255);
        this.borderGreenValue = new IntegerValue("Border Green", 0, 0, 255);
        this.borderBlueValue = new IntegerValue("Border Blue", 0, 0, 255);
        this.borderAlphaValue = new IntegerValue("Border Alpha", 150, 0, 255);
        this.borderRainbowValue = new BoolValue("Border Rainbow", false);
    }

    public Radar(double d, double d2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 5.0d : d, (i & 2) != 0 ? 130.0d : d2);
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0005"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar$Companion;", "", "()V", "SQRT_OF_TWO", "", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Radar$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        MiniMapRegister.INSTANCE.updateChunks();
        float fFloatValue = ((Number) this.fovAngleValue.get()).floatValue();
        if (this.lastFov != fFloatValue || this.fovMarkerVertexBuffer == null) {
            IVertexBuffer iVertexBuffer = this.fovMarkerVertexBuffer;
            if (iVertexBuffer != null) {
                iVertexBuffer.deleteGlBuffers();
                Unit unit = Unit.INSTANCE;
            }
            this.fovMarkerVertexBuffer = createFovIndicator(fFloatValue);
            this.lastFov = fFloatValue;
        }
        IEntity renderViewEntity = MinecraftInstance.f157mc.getRenderViewEntity();
        if (renderViewEntity == null) {
            Intrinsics.throwNpe();
        }
        float fFloatValue2 = ((Number) this.sizeValue.get()).floatValue();
        if (!((Boolean) this.minimapValue.get()).booleanValue()) {
            RenderUtils.drawRect(0.0f, 0.0f, fFloatValue2, fFloatValue2, new Color(((Number) this.backgroundRedValue.get()).intValue(), ((Number) this.backgroundGreenValue.get()).intValue(), ((Number) this.backgroundBlueValue.get()).intValue(), ((Number) this.backgroundAlphaValue.get()).intValue()).getRGB());
        }
        float fFloatValue3 = ((Number) this.viewDistanceValue.get()).floatValue() * 16.0f;
        double dFloatValue = (fFloatValue3 + ((Number) this.fovSizeValue.get()).floatValue()) * (fFloatValue3 + ((Number) this.fovSizeValue.get()).floatValue());
        float f = fFloatValue2 / 2.0f;
        RenderUtils.makeScissorBox((float) getX(), (float) getY(), ((float) getX()) + ((float) Math.ceil(fFloatValue2)), ((float) getY()) + ((float) Math.ceil(fFloatValue2)));
        GL11.glEnable(SGL.GL_SCISSOR_TEST);
        GL11.glPushMatrix();
        GL11.glTranslatef(f, f, 0.0f);
        GL11.glRotatef(renderViewEntity.getRotationYaw(), 0.0f, 0.0f, -1.0f);
        GL11.glEnable(SGL.GL_BLEND);
        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (((Boolean) this.minimapValue.get()).booleanValue()) {
            GL11.glEnable(SGL.GL_TEXTURE_2D);
            float fFloatValue4 = fFloatValue2 / ((Number) this.viewDistanceValue.get()).floatValue();
            int iMax = Math.max(1, (int) Math.ceil(SQRT_OF_TWO * ((Number) this.viewDistanceValue.get()).floatValue() * 0.5f));
            double posX = renderViewEntity.getPosX() / 16.0d;
            double posZ = renderViewEntity.getPosZ() / 16.0d;
            int i = -iMax;
            if (i <= iMax) {
                while (true) {
                    int i2 = -iMax;
                    if (i2 <= iMax) {
                        while (true) {
                            MiniMapRegister.MiniMapTexture chunkTextureAt = MiniMapRegister.INSTANCE.getChunkTextureAt(((int) Math.floor(posX)) + i, ((int) Math.floor(posZ)) + i2);
                            if (chunkTextureAt != null) {
                                double d = fFloatValue4;
                                double dFloor = (((posX - ((long) Math.floor(posX))) - 1.0d) - i) * d;
                                double dFloor2 = (((posZ - ((long) Math.floor(posZ))) - 1.0d) - i2) * d;
                                MinecraftInstance.classProvider.getGlStateManager().bindTexture(chunkTextureAt.getTexture().func_110552_b());
                                GL11.glBegin(7);
                                GL11.glTexCoord2f(0.0f, 0.0f);
                                GL11.glVertex2d(dFloor, dFloor2);
                                GL11.glTexCoord2f(0.0f, 1.0f);
                                GL11.glVertex2d(dFloor, dFloor2 + fFloatValue4);
                                GL11.glTexCoord2f(1.0f, 1.0f);
                                GL11.glVertex2d(dFloor + fFloatValue4, dFloor2 + fFloatValue4);
                                GL11.glTexCoord2f(1.0f, 0.0f);
                                GL11.glVertex2d(dFloor + fFloatValue4, dFloor2);
                                GL11.glEnd();
                            }
                            if (i2 == iMax) {
                                break;
                            }
                            i2++;
                        }
                    }
                    if (i == iMax) {
                        break;
                    }
                    i++;
                }
            }
            MinecraftInstance.classProvider.getGlStateManager().bindTexture(0);
            GL11.glDisable(SGL.GL_TEXTURE_2D);
        }
        GL11.glDisable(SGL.GL_TEXTURE_2D);
        GL11.glEnable(SGL.GL_LINE_SMOOTH);
        boolean zEquals = StringsKt.equals((String) this.playerShapeValue.get(), "triangle", true);
        boolean zEquals2 = StringsKt.equals((String) this.playerShapeValue.get(), "circle", true);
        ITessellator tessellatorInstance = MinecraftInstance.classProvider.getTessellatorInstance();
        IWorldRenderer worldRenderer = tessellatorInstance.getWorldRenderer();
        if (zEquals2) {
            GL11.glEnable(SGL.GL_POINT_SMOOTH);
        }
        float fFloatValue5 = ((Number) this.playerSizeValue.get()).floatValue();
        GL11.glEnable(SGL.GL_POLYGON_SMOOTH);
        if (zEquals) {
            fFloatValue5 *= 2.0f;
        } else {
            worldRenderer.begin(0, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
            GL11.glPointSize(fFloatValue5);
        }
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        for (IEntity iEntity : theWorld.getLoadedEntityList()) {
            if ((!Intrinsics.areEqual(iEntity, MinecraftInstance.f157mc.getThePlayer())) && EntityUtils.isSelected(iEntity, false)) {
                Vector2f vector2f = new Vector2f((float) (renderViewEntity.getPosX() - iEntity.getPosX()), (float) (renderViewEntity.getPosZ() - iEntity.getPosZ()));
                if (dFloatValue >= vector2f.lengthSquared()) {
                    boolean z = zEquals || ((Number) this.fovSizeValue.get()).floatValue() > 0.0f;
                    if (z) {
                        GL11.glPushMatrix();
                        GL11.glTranslatef((vector2f.x / fFloatValue3) * fFloatValue2, (vector2f.y / fFloatValue3) * fFloatValue2, 0.0f);
                        GL11.glRotatef(iEntity.getRotationYaw(), 0.0f, 0.0f, 1.0f);
                    }
                    if (((Number) this.fovSizeValue.get()).floatValue() > 0.0f) {
                        GL11.glPushMatrix();
                        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                        float fFloatValue6 = (((Number) this.fovSizeValue.get()).floatValue() / fFloatValue3) * fFloatValue2;
                        GL11.glScalef(fFloatValue6, fFloatValue6, fFloatValue6);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, ((Boolean) this.minimapValue.get()).booleanValue() ? 0.75f : 0.25f);
                        IVertexBuffer iVertexBuffer2 = this.fovMarkerVertexBuffer;
                        if (iVertexBuffer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        iVertexBuffer2.bindBuffer();
                        GL11.glEnableClientState(32884);
                        GL11.glVertexPointer(3, 5126, 12, 0L);
                        iVertexBuffer2.drawArrays(6);
                        iVertexBuffer2.unbindBuffer();
                        GL11.glDisableClientState(32884);
                        GL11.glPopMatrix();
                    }
                    if (zEquals) {
                        if (((Boolean) this.useESPColorsValue.get()).booleanValue()) {
                            Module module = LiquidBounce.INSTANCE.getModuleManager().get(ESP.class);
                            if (module == null) {
                                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.ESP");
                            }
                            Color color = ((ESP) module).getColor(iEntity);
                            GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
                        } else {
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        }
                        GL11.glBegin(4);
                        GL11.glVertex2f((-fFloatValue5) * 0.25f, fFloatValue5 * 0.5f);
                        GL11.glVertex2f(fFloatValue5 * 0.25f, fFloatValue5 * 0.5f);
                        GL11.glVertex2f(0.0f, (-fFloatValue5) * 0.5f);
                        GL11.glEnd();
                    } else {
                        Module module2 = LiquidBounce.INSTANCE.getModuleManager().get(ESP.class);
                        if (module2 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.ESP");
                        }
                        Color color2 = ((ESP) module2).getColor(iEntity);
                        worldRenderer.pos((vector2f.x / fFloatValue3) * fFloatValue2, (vector2f.y / fFloatValue3) * fFloatValue2, 0.0d).color(color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, 1.0f).endVertex();
                    }
                    if (z) {
                        GL11.glPopMatrix();
                    }
                } else {
                    continue;
                }
            }
        }
        if (!zEquals) {
            tessellatorInstance.draw();
        }
        if (zEquals2) {
            GL11.glDisable(SGL.GL_POINT_SMOOTH);
        }
        GL11.glDisable(SGL.GL_POLYGON_SMOOTH);
        GL11.glEnable(SGL.GL_TEXTURE_2D);
        GL11.glDisable(SGL.GL_BLEND);
        GL11.glDisable(SGL.GL_LINE_SMOOTH);
        GL11.glDisable(SGL.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
        RainbowShader.Companion companion = RainbowShader.Companion;
        boolean zBooleanValue = ((Boolean) this.borderRainbowValue.get()).booleanValue();
        float fFloatValue7 = ((Number) this.rainbowXValue.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowXValue.get()).floatValue();
        float fFloatValue8 = ((Number) this.rainbowYValue.get()).floatValue() == 0.0f ? 0.0f : 1.0f / ((Number) this.rainbowYValue.get()).floatValue();
        float fCurrentTimeMillis = (System.currentTimeMillis() % 10000) / 10000.0f;
        RainbowShader rainbowShader = RainbowShader.INSTANCE;
        if (zBooleanValue) {
            rainbowShader.setStrengthX(fFloatValue7);
            rainbowShader.setStrengthY(fFloatValue8);
            rainbowShader.setOffset(fCurrentTimeMillis);
            rainbowShader.startShader();
        }
        RainbowShader rainbowShader2 = rainbowShader;
        Throwable th = (Throwable) null;
        try {
            RenderUtils.drawBorder(0.0f, 0.0f, fFloatValue2, fFloatValue2, ((Number) this.borderStrengthValue.get()).floatValue(), new Color(((Number) this.borderRedValue.get()).intValue(), ((Number) this.borderGreenValue.get()).intValue(), ((Number) this.borderBlueValue.get()).intValue(), ((Number) this.borderAlphaValue.get()).intValue()).getRGB());
            GL11.glEnable(SGL.GL_BLEND);
            GL11.glDisable(SGL.GL_TEXTURE_2D);
            GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(SGL.GL_LINE_SMOOTH);
            RenderUtils.glColor(((Number) this.borderRedValue.get()).intValue(), ((Number) this.borderGreenValue.get()).intValue(), ((Number) this.borderBlueValue.get()).intValue(), ((Number) this.borderAlphaValue.get()).intValue());
            GL11.glLineWidth(((Number) this.borderStrengthValue.get()).floatValue());
            GL11.glBegin(1);
            GL11.glVertex2f(f, 0.0f);
            GL11.glVertex2f(f, fFloatValue2);
            GL11.glVertex2f(0.0f, f);
            GL11.glVertex2f(fFloatValue2, f);
            GL11.glEnd();
            GL11.glEnable(SGL.GL_TEXTURE_2D);
            GL11.glDisable(SGL.GL_BLEND);
            GL11.glDisable(SGL.GL_LINE_SMOOTH);
            Unit unit2 = Unit.INSTANCE;
            CloseableKt.closeFinally(rainbowShader2, th);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            return new Border(0.0f, 0.0f, fFloatValue2, fFloatValue2);
        } catch (Throwable th2) {
            throw th2;
        }
    }

    private final IVertexBuffer createFovIndicator(float f) {
        IWorldRenderer worldRenderer = MinecraftInstance.classProvider.getTessellatorInstance().getWorldRenderer();
        worldRenderer.begin(6, MinecraftInstance.classProvider.getVertexFormatEnum(WDefaultVertexFormats.POSITION));
        float f2 = ((90.0f - (f * 0.5f)) / 180.0f) * 3.1415927f;
        worldRenderer.pos(0.0d, 0.0d, 0.0d).endVertex();
        for (float f3 = ((90.0f + (f * 0.5f)) / 180.0f) * 3.1415927f; f3 >= f2; f3 -= 0.15f) {
            worldRenderer.pos(((float) Math.cos(f3)) * 1.0d, ((float) Math.sin(f3)) * 1.0d, 0.0d).endVertex();
        }
        IVertexBuffer iVertexBufferCreateSafeVertexBuffer = MinecraftInstance.classProvider.createSafeVertexBuffer(worldRenderer.getVertexFormat());
        worldRenderer.finishDrawing();
        worldRenderer.reset();
        iVertexBufferCreateSafeVertexBuffer.bufferData(worldRenderer.getByteBuffer());
        return iVertexBufferCreateSafeVertexBuffer;
    }
}
