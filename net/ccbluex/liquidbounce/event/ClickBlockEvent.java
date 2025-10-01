package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\ufffd\ufffd2\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "clickedBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "WEnumFacing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;)V", "getWEnumFacing", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getClickedBlock", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/ClickBlockEvent.class */
public final class ClickBlockEvent extends Event {

    @Nullable
    private final WBlockPos clickedBlock;

    @Nullable
    private final IEnumFacing WEnumFacing;

    @Nullable
    public final WBlockPos getClickedBlock() {
        return this.clickedBlock;
    }

    @Nullable
    public final IEnumFacing getWEnumFacing() {
        return this.WEnumFacing;
    }

    public ClickBlockEvent(@Nullable WBlockPos wBlockPos, @Nullable IEnumFacing iEnumFacing) {
        this.clickedBlock = wBlockPos;
        this.WEnumFacing = iEnumFacing;
    }
}
