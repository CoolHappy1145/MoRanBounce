package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import java.io.Closeable;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.render.shader.Shader;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL20;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\ufffd\ufffd \u001a2\u00020\u00012\u00020\u0002:\u0001\u001aB\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\b\u0010\u0017\u001a\u00020\u0015H\u0016J\b\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u0015H\u0016R\u001e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\rR\u001a\u0010\u0011\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\r\u00a8\u0006\u001b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowShader;", "Lnet/ccbluex/liquidbounce/utils/render/shader/Shader;", "Ljava/io/Closeable;", "()V", "<set-?>", "", "isInUse", "()Z", "offset", "", "getOffset", "()F", "setOffset", "(F)V", "strengthX", "getStrengthX", "setStrengthX", "strengthY", "getStrengthY", "setStrengthY", "close", "", "setupUniforms", "startShader", "stopShader", "updateUniforms", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowShader.class */
public final class RainbowShader extends Shader implements Closeable {
    private boolean isInUse;
    private float strengthX;
    private float strengthY;
    private float offset;
    public static final Companion Companion = new Companion(null);

    @JvmField
    @NotNull
    public static final RainbowShader INSTANCE = new RainbowShader();

    public RainbowShader() {
        super("rainbow_shader.frag");
    }

    public final boolean isInUse() {
        return this.isInUse;
    }

    public final float getStrengthX() {
        return this.strengthX;
    }

    public final void setStrengthX(float f) {
        this.strengthX = f;
    }

    public final float getStrengthY() {
        return this.strengthY;
    }

    public final void setStrengthY(float f) {
        this.strengthY = f;
    }

    public final float getOffset() {
        return this.offset;
    }

    public final void setOffset(float f) {
        this.offset = f;
    }

    @Override // net.ccbluex.liquidbounce.utils.render.shader.Shader
    public void setupUniforms() {
        setupUniform("offset");
        setupUniform("strength");
    }

    @Override // net.ccbluex.liquidbounce.utils.render.shader.Shader
    public void updateUniforms() {
        GL20.glUniform2f(getUniform("strength"), this.strengthX, this.strengthY);
        GL20.glUniform1f(getUniform("offset"), this.offset);
    }

    @Override // net.ccbluex.liquidbounce.utils.render.shader.Shader
    public void startShader() {
        super.startShader();
        this.isInUse = true;
    }

    @Override // net.ccbluex.liquidbounce.utils.render.shader.Shader
    public void stopShader() {
        super.stopShader();
        this.isInUse = false;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.isInUse) {
            stopShader();
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J)\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0086\bR\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowShader$Companion;", "", "()V", "INSTANCE", "Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowShader;", "begin", "enable", "", "x", "", "y", "offset", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowShader$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final RainbowShader begin(boolean z, float f, float f2, float f3) {
            RainbowShader rainbowShader = RainbowShader.INSTANCE;
            if (z) {
                rainbowShader.setStrengthX(f);
                rainbowShader.setStrengthY(f2);
                rainbowShader.setOffset(f3);
                rainbowShader.startShader();
            }
            return rainbowShader;
        }
    }
}
