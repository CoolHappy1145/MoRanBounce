package net.ccbluex.liquidbounce.features.special;

import java.util.Arrays;
import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd \f2\u00020\u00012\u00020\u0002:\u0001\fB\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/special/BungeeCordSpoof;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "getRandomIpPart", "", "handleEvents", "", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/special/BungeeCordSpoof.class */
public final class BungeeCordSpoof extends MinecraftInstance implements Listenable {

    @JvmField
    public static boolean enabled;
    public static final Companion Companion = new Companion(null);
    private static final Random RANDOM = new Random();

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isCPacketHandshake(packet) && enabled && packet.asCPacketHandshake().getRequestedState().isHandshake()) {
            ICPacketHandshake iCPacketHandshakeAsCPacketHandshake = packet.asCPacketHandshake();
            StringBuilder sbAppend = new StringBuilder().append(iCPacketHandshakeAsCPacketHandshake.getIp()).append("\ufffd\ufffd");
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] objArr = {getRandomIpPart(), getRandomIpPart(), getRandomIpPart(), getRandomIpPart()};
            String str = String.format("%d.%d.%d.%d", Arrays.copyOf(objArr, objArr.length));
            Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.String.format(format, *args)");
            iCPacketHandshakeAsCPacketHandshake.setIp(sbAppend.append(str).append("\ufffd\ufffd").append(StringsKt.replace$default(MinecraftInstance.f157mc.getSession().getPlayerId(), "-", "", false, 4, (Object) null)).toString());
        }
    }

    private final String getRandomIpPart() {
        return String.valueOf(RANDOM.nextInt(256));
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0012\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/special/BungeeCordSpoof$Companion;", "", "()V", "RANDOM", "Ljava/util/Random;", "enabled", "", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/special/BungeeCordSpoof$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
