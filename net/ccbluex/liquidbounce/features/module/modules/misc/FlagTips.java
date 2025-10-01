package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u001d\u001a\u00020\u0017J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020$H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\n\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\u0006\"\u0004\b\f\u0010\bR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00178VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u001b\u0010\u0006\"\u0004\b\u001c\u0010\b\u00a8\u0006%"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/FlagTips;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aa", "", "getAa", "()I", "setAa", "(I)V", "flag", "packets", "getPackets", "setPackets", "packettips", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "resetflagnumbertips", "started", "", "getStarted", "()Z", "setStarted", "(Z)V", "tag", "", "getTag", "()Ljava/lang/String;", "time", "getTime", "setTime", "getPacket", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "FlagTips", description = "You Flag Tips.", category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/FlagTips.class */
public final class FlagTips extends Module {
    private BoolValue packettips = new BoolValue("PacketTips", false);
    private BoolValue resetflagnumbertips = new BoolValue("ResetFlagNumberTips", false);
    private int flag;
    private int time;

    /* renamed from: aa */
    private int f125aa;
    private int packets;
    private boolean started;

    public final int getTime() {
        return this.time;
    }

    public final void setTime(int i) {
        this.time = i;
    }

    public final int getAa() {
        return this.f125aa;
    }

    public final void setAa(int i) {
        this.f125aa = i;
    }

    public final int getPackets() {
        return this.packets;
    }

    public final void setPackets(int i) {
        this.packets = i;
    }

    public final boolean getStarted() {
        return this.started;
    }

    public final void setStarted(boolean z) {
        this.started = z;
    }

    public void onEnable() {
        this.started = false;
        this.flag = 0;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.time++;
        if (this.packets > 999 && ((Boolean) this.packettips.get()).booleanValue()) {
            ClientUtils.displayChatMessage("\u00a7b[\u00a7bCoolSenseTips]\u00a7d\u60a8\u7684\u53d1\u5305\u901f\u5ea6\u8fc7\u5feb!,\u8bf7\u8c03\u6574\u53d1\u5305\u529f\u80fd");
        }
    }

    @NotNull
    public final String getPacket() {
        if (this.started) {
            if (this.packets < 999) {
                return String.valueOf(this.packets);
            }
            return "\u00a7d" + this.packets;
        }
        return "0";
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isSPacketPlayerPosLook(packet)) {
            this.flag++;
            ClientUtils.displayChatMessage("\u00a7b[\u00a7bCoolSenseTips]\u00a7d\u60a8\u5df2Flag\u6b21\u6570:" + this.flag + ",\u76ee\u524d\u53d1\u5305\u901f\u5ea6:" + getPacket() + "/s");
        }
        if (MinecraftInstance.classProvider.isSPacketChat(packet) && ((Boolean) this.resetflagnumbertips.get()).booleanValue()) {
            String strFunc_150260_c = packet.asSPacketChat().getChatComponent().func_150260_c();
            Intrinsics.checkExpressionValueIsNotNull(strFunc_150260_c, "packet.asSPacketChat().c\u2026Component.unformattedText");
            if (StringsKt.contains$default((CharSequence) strFunc_150260_c, (CharSequence) "\u6e38\u620f\u5f00\u59cb ...", false, 2, (Object) null)) {
                this.flag = 0;
            }
        }
        this.f125aa++;
        if (this.time >= 20) {
            this.time = 0;
            this.packets = this.f125aa;
            this.f125aa = 0;
            this.started = true;
        }
    }

    @Nullable
    public String getTag() {
        return "Packet:" + getPacket() + "/s";
    }
}
