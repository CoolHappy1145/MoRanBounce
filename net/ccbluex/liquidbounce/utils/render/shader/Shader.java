package net.ccbluex.liquidbounce.utils.render.shader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/shader/Shader.class */
public abstract class Shader extends MinecraftInstance {
    private int program;
    private Map uniformsMap;

    public abstract void setupUniforms();

    public abstract void updateUniforms();

    public Shader(String str) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/assets/minecraft/liquidbounce/shader/vertex.vert");
            int iCreateShader = createShader(IOUtils.toString(resourceAsStream), 35633);
            IOUtils.closeQuietly(resourceAsStream);
            InputStream resourceAsStream2 = getClass().getResourceAsStream("/assets/minecraft/liquidbounce/shader/fragment/" + str);
            int iCreateShader2 = createShader(IOUtils.toString(resourceAsStream2), 35632);
            IOUtils.closeQuietly(resourceAsStream2);
            if (iCreateShader == 0 || iCreateShader2 == 0) {
                return;
            }
            this.program = ARBShaderObjects.glCreateProgramObjectARB();
            if (this.program == 0) {
                return;
            }
            ARBShaderObjects.glAttachObjectARB(this.program, iCreateShader);
            ARBShaderObjects.glAttachObjectARB(this.program, iCreateShader2);
            ARBShaderObjects.glLinkProgramARB(this.program);
            ARBShaderObjects.glValidateProgramARB(this.program);
            ClientUtils.getLogger().info("[Shader] Successfully loaded: " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startShader() {
        GL11.glPushMatrix();
        GL20.glUseProgram(this.program);
        if (this.uniformsMap == null) {
            this.uniformsMap = new HashMap();
            setupUniforms();
        }
        updateUniforms();
    }

    public void stopShader() {
        GL20.glUseProgram(0);
        GL11.glPopMatrix();
    }

    private int createShader(String str, int i) throws Exception {
        int iGlCreateShaderObjectARB = 0;
        try {
            iGlCreateShaderObjectARB = ARBShaderObjects.glCreateShaderObjectARB(i);
            if (iGlCreateShaderObjectARB == 0) {
                return 0;
            }
            ARBShaderObjects.glShaderSourceARB(iGlCreateShaderObjectARB, str);
            ARBShaderObjects.glCompileShaderARB(iGlCreateShaderObjectARB);
            if (ARBShaderObjects.glGetObjectParameteriARB(iGlCreateShaderObjectARB, 35713) == 0) {
                throw new RuntimeException("Error creating shader: " + getLogInfo(iGlCreateShaderObjectARB));
            }
            return iGlCreateShaderObjectARB;
        } catch (Exception e) {
            ARBShaderObjects.glDeleteObjectARB(iGlCreateShaderObjectARB);
            throw e;
        }
    }

    private String getLogInfo(int i) {
        return ARBShaderObjects.glGetInfoLogARB(i, ARBShaderObjects.glGetObjectParameteriARB(i, 35716));
    }

    public void setUniform(String str, int i) {
        this.uniformsMap.put(str, Integer.valueOf(i));
    }

    public void setupUniform(String str) {
        setUniform(str, GL20.glGetUniformLocation(this.program, str));
    }

    public int getUniform(String str) {
        return ((Integer) this.uniformsMap.get(str)).intValue();
    }

    public int getProgramId() {
        return this.program;
    }
}
