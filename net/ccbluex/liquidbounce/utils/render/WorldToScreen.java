package net.ccbluex.liquidbounce.utils.render;

import java.nio.FloatBuffer;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/WorldToScreen.class */
public class WorldToScreen {
    public static Matrix4f getMatrix(int i) {
        FloatBuffer floatBufferCreateFloatBuffer = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(i, floatBufferCreateFloatBuffer);
        return new Matrix4f().load(floatBufferCreateFloatBuffer);
    }

    public static Vector2f worldToScreen(Vector3f vector3f, int i, int i2) {
        return worldToScreen(vector3f, getMatrix(SGL.GL_MODELVIEW_MATRIX), getMatrix(2983), i, i2);
    }

    public static Vector2f worldToScreen(Vector3f vector3f, Matrix4f matrix4f, Matrix4f matrix4f2, int i, int i2) {
        Vector4f vector4fMultiply = multiply(multiply(new Vector4f(vector3f.x, vector3f.y, vector3f.z, 1.0f), matrix4f), matrix4f2);
        Vector3f vector3f2 = new Vector3f(vector4fMultiply.x / vector4fMultiply.w, vector4fMultiply.y / vector4fMultiply.w, vector4fMultiply.z / vector4fMultiply.w);
        float f = ((vector3f2.x + 1.0f) / 2.0f) * i;
        float f2 = ((1.0f - vector3f2.y) / 2.0f) * i2;
        if (vector3f2.z < -1.0d || vector3f2.z > 1.0d) {
            return null;
        }
        return new Vector2f(f, f2);
    }

    public static Vector4f multiply(Vector4f vector4f, Matrix4f matrix4f) {
        return new Vector4f((vector4f.x * matrix4f.m00) + (vector4f.y * matrix4f.m10) + (vector4f.z * matrix4f.m20) + (vector4f.w * matrix4f.m30), (vector4f.x * matrix4f.m01) + (vector4f.y * matrix4f.m11) + (vector4f.z * matrix4f.m21) + (vector4f.w * matrix4f.m31), (vector4f.x * matrix4f.m02) + (vector4f.y * matrix4f.m12) + (vector4f.z * matrix4f.m22) + (vector4f.w * matrix4f.m32), (vector4f.x * matrix4f.m03) + (vector4f.y * matrix4f.m13) + (vector4f.z * matrix4f.m23) + (vector4f.w * matrix4f.m33));
    }
}
