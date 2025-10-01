package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.shader.IShaderGroup;
import net.minecraft.client.shader.ShaderGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ShaderGroupImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/shader/IShaderGroup;", "wrapped", "Lnet/minecraft/client/shader/ShaderGroup;", "(Lnet/minecraft/client/shader/ShaderGroup;)V", "shaderGroupName", "", "getShaderGroupName", "()Ljava/lang/String;", "getWrapped", "()Lnet/minecraft/client/shader/ShaderGroup;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ShaderGroupImpl.class */
public final class ShaderGroupImpl implements IShaderGroup {

    @NotNull
    private final ShaderGroup wrapped;

    @NotNull
    public final ShaderGroup getWrapped() {
        return this.wrapped;
    }

    public ShaderGroupImpl(@NotNull ShaderGroup wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.shader.IShaderGroup
    @NotNull
    public String getShaderGroupName() {
        String strFunc_148022_b = this.wrapped.func_148022_b();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_148022_b, "wrapped.shaderGroupName");
        return strFunc_148022_b;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ShaderGroupImpl) && Intrinsics.areEqual(((ShaderGroupImpl) obj).wrapped, this.wrapped);
    }
}
