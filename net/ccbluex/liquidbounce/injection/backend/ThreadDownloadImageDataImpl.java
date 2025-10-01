package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IThreadDownloadImageData;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ThreadDownloadImageDataImpl;", "T", "Lnet/minecraft/client/renderer/ThreadDownloadImageData;", "Lnet/ccbluex/liquidbounce/injection/backend/AbstractTextureImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IThreadDownloadImageData;", "wrapped", "(Lnet/minecraft/client/renderer/ThreadDownloadImageData;)V", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ThreadDownloadImageDataImpl.class */
public final class ThreadDownloadImageDataImpl extends AbstractTextureImpl implements IThreadDownloadImageData {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThreadDownloadImageDataImpl(@NotNull ThreadDownloadImageData wrapped) {
        super((AbstractTexture) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.injection.backend.AbstractTextureImpl
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ThreadDownloadImageDataImpl) && Intrinsics.areEqual(((ThreadDownloadImageDataImpl) obj).getWrapped(), getWrapped());
    }
}
