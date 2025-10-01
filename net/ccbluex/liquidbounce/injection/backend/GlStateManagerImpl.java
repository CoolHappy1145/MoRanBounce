package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager;
import net.minecraft.client.renderer.GlStateManager;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0014\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0004H\u0016J\b\u0010\b\u001a\u00020\u0004H\u0016J\b\u0010\t\u001a\u00020\u0004H\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016J\b\u0010\f\u001a\u00020\u0004H\u0016J\b\u0010\r\u001a\u00020\u0004H\u0016J\b\u0010\u000e\u001a\u00020\u0004H\u0016J\b\u0010\u000f\u001a\u00020\u0004H\u0016J\b\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0011\u001a\u00020\u0004H\u0016J\b\u0010\u0012\u001a\u00020\u0004H\u0016J\b\u0010\u0013\u001a\u00020\u0004H\u0016J\b\u0010\u0014\u001a\u00020\u0004H\u0016J(\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0006H\u0016\u00a8\u0006\u001a"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/GlStateManagerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IGlStateManager;", "()V", "bindTexture", "", "textureID", "", "disableBlend", "disableCull", "disableLighting", "disableRescaleNormal", "disableTexture2D", "enableAlpha", "enableBlend", "enableColorMaterial", "enableTexture2D", "popAttrib", "popMatrix", "pushAttrib", "pushMatrix", "resetColor", "tryBlendFuncSeparate", "glSrcAlpha", "glOneMinusSrcAlpha", "glOne", "glZero", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/GlStateManagerImpl.class */
public final class GlStateManagerImpl implements IGlStateManager {
    public static final GlStateManagerImpl INSTANCE = new GlStateManagerImpl();

    private GlStateManagerImpl() {
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void bindTexture(int i) {
        GlStateManager.func_179144_i(i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void resetColor() {
        GlStateManager.func_179117_G();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void enableTexture2D() {
        GlStateManager.func_179098_w();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void enableBlend() {
        GlStateManager.func_179147_l();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void tryBlendFuncSeparate(int i, int i2, int i3, int i4) {
        GlStateManager.func_179120_a(i, i2, i3, i4);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void disableTexture2D() {
        GlStateManager.func_179090_x();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void disableBlend() {
        GlStateManager.func_179084_k();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void enableAlpha() {
        GlStateManager.func_179141_d();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void disableLighting() {
        GlStateManager.func_179140_f();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void disableCull() {
        GlStateManager.func_179129_p();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void enableColorMaterial() {
        GlStateManager.func_179142_g();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void disableRescaleNormal() {
        GlStateManager.func_179101_C();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void pushMatrix() {
        GlStateManager.func_179094_E();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void pushAttrib() {
        GlStateManager.func_179123_a();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void popMatrix() {
        GlStateManager.func_179121_F();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager
    public void popAttrib() {
        GlStateManager.func_179099_b();
    }
}
