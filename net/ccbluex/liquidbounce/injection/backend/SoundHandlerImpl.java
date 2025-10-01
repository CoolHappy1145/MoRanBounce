package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.audio.ISoundHandler;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0007\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/SoundHandlerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/audio/ISoundHandler;", "wrapped", "Lnet/minecraft/client/audio/SoundHandler;", "(Lnet/minecraft/client/audio/SoundHandler;)V", "getWrapped", "()Lnet/minecraft/client/audio/SoundHandler;", "equals", "", "other", "", "playSound", "", "name", "", "pitch", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/SoundHandlerImpl.class */
public final class SoundHandlerImpl implements ISoundHandler {

    @NotNull
    private final SoundHandler wrapped;

    @NotNull
    public final SoundHandler getWrapped() {
        return this.wrapped;
    }

    public SoundHandlerImpl(@NotNull SoundHandler wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.audio.ISoundHandler
    public void playSound(@NotNull String name, float f) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.wrapped.func_147682_a(PositionedSoundRecord.func_194007_a(new SoundEvent(new ResourceLocation(name)), f, 1.0f));
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof SoundHandlerImpl) && Intrinsics.areEqual(((SoundHandlerImpl) obj).wrapped, this.wrapped);
    }
}
