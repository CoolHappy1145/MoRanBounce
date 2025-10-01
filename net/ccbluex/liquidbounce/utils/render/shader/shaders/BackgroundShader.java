package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.Shader;
import org.lwjgl.opengl.GL20;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/shader/shaders/BackgroundShader.class */
public final class BackgroundShader extends Shader {
    public static final BackgroundShader BACKGROUND_SHADER = new BackgroundShader();
    private float time;

    public BackgroundShader() {
        super("background.frag");
    }

    @Override // net.ccbluex.liquidbounce.utils.render.shader.Shader
    public void setupUniforms() {
        setupUniform("iResolution");
        setupUniform("iTime");
    }

    @Override // net.ccbluex.liquidbounce.utils.render.shader.Shader
    public void updateUniforms() {
        IScaledResolution iScaledResolutionCreateScaledResolution = classProvider.createScaledResolution(f157mc);
        int uniform = getUniform("iResolution");
        if (uniform > -1) {
            GL20.glUniform2f(uniform, iScaledResolutionCreateScaledResolution.getScaledWidth() * 2.0f, iScaledResolutionCreateScaledResolution.getScaledHeight() * 2.0f);
        }
        int uniform2 = getUniform("iTime");
        if (uniform2 > -1) {
            GL20.glUniform1f(uniform2, this.time);
        }
        this.time += 0.005f * RenderUtils.deltaTime;
    }
}
