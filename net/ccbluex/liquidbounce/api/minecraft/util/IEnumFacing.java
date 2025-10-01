package net.ccbluex.liquidbounce.api.minecraft.util;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0006\bf\u0018\ufffd\ufffd2\u00020\u0001J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0010H&J\b\u0010\u0012\u001a\u00020\u0010H&J\b\u0010\u0013\u001a\u00020\u0010H&J\b\u0010\u0014\u001a\u00020\u0010H&J\b\u0010\u0015\u001a\u00020\u0010H&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0012\u0010\f\u001a\u00020\ufffd\ufffdX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "", "axisOrdinal", "", "getAxisOrdinal", "()I", "directionVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "getDirectionVec", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "index", "getIndex", "opposite", "getOpposite", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "isDown", "", "isEast", "isNorth", "isSouth", "isUp", "isWest", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing.class */
public interface IEnumFacing {
    boolean isNorth();

    boolean isDown();

    boolean isSouth();

    boolean isEast();

    boolean isWest();

    boolean isUp();

    @NotNull
    IEnumFacing getOpposite();

    @NotNull
    WVec3i getDirectionVec();

    int getAxisOrdinal();

    int getIndex();
}
