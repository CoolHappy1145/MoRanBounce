package net.ccbluex.liquidbounce.api.minecraft.world;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\f\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\bf\u0018\ufffd\ufffd2\u00020\u0001:\u0001\u0002\u00a8\u0006\u0003"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings;", "", "WGameType", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings.class */
public interface IWorldSettings {

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType;", "", "(Ljava/lang/String;I)V", "NOT_SET", "SURVIVAL", "CREATIVE", "ADVENTUR", "SPECTATOR", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType.class */
    public enum WGameType {
        NOT_SET,
        SURVIVAL,
        CREATIVE,
        ADVENTUR,
        SPECTATOR
    }
}
