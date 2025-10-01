package net.ccbluex.liquidbounce.features.module.modules.hyt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\t\u001a\u00020\bH\u0016J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0012\u0010\r\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/hyt/HytGetName;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "mode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tips", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "clearAll", "", "onDisable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "HytGetName", description = "fix by cool", category = ModuleCategory.HYT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/hyt/HytGetName.class */
public final class HytGetName extends Module {
    private final ListValue mode = new ListValue("GetNameMode", new String[]{"4V4/1V1", "32/64", "16V16"}, "4V4/1V1");
    private final BoolValue tips = new BoolValue("Tips", true);

    public void onDisable() {
        clearAll();
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0065  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isSPacketChat(packet)) {
            String strFunc_150260_c = packet.asSPacketChat().getChatComponent().func_150260_c();
            Intrinsics.checkExpressionValueIsNotNull(strFunc_150260_c, "packet.asSPacketChat().c\u2026Component.unformattedText");
            if (!StringsKt.contains$default((CharSequence) strFunc_150260_c, (CharSequence) "\u83b7\u5f97\u80dc\u5229!", false, 2, (Object) null)) {
                String strFunc_150260_c2 = packet.asSPacketChat().getChatComponent().func_150260_c();
                Intrinsics.checkExpressionValueIsNotNull(strFunc_150260_c2, "packet.asSPacketChat().c\u2026Component.unformattedText");
                if (StringsKt.contains$default((CharSequence) strFunc_150260_c2, (CharSequence) "\u6e38\u620f\u5f00\u59cb ...", false, 2, (Object) null)) {
                    clearAll();
                }
            }
            String str = (String) this.mode.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1961702257:
                    if (!lowerCase.equals("4v4/1v1")) {
                        return;
                    }
                    break;
                case 46976214:
                    if (lowerCase.equals("16v16")) {
                        Matcher matcher = Pattern.compile("\u51fb\u8d25\u4e86 (.*?)!").matcher(packet.asSPacketChat().getChatComponent().func_150260_c());
                        Matcher matcher2 = Pattern.compile("\u73a9\u5bb6 (.*?)\u6b7b\u4e86\uff01").matcher(packet.asSPacketChat().getChatComponent().func_150260_c());
                        if (matcher.find()) {
                            String strGroup = matcher.group(1);
                            Intrinsics.checkExpressionValueIsNotNull(strGroup, "matcher.group(1)");
                            if (strGroup == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }
                            String string = StringsKt.trim((CharSequence) strGroup).toString();
                            if (!Intrinsics.areEqual(string, "")) {
                                LiquidBounce.INSTANCE.getFileManager().friendsConfig.addFriend(string);
                                if (((Boolean) this.tips.get()).booleanValue()) {
                                    ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lCoolSense\u63d0\u9192\u60a8\u00a78]\u00a7c\u00a7d\u6dfb\u52a0\u65e0\u654c\u4eba\uff1a" + string);
                                }
                                new Thread(new Runnable(this, string) { // from class: net.ccbluex.liquidbounce.features.module.modules.hyt.HytGetName.onPacket.3
                                    final HytGetName this$0;
                                    final String $name;

                                    {
                                        this.this$0 = this;
                                        this.$name = string;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() throws InterruptedException {
                                        try {
                                            Thread.sleep(10000L);
                                            LiquidBounce.INSTANCE.getFileManager().friendsConfig.removeFriend(this.$name);
                                            if (((Boolean) this.this$0.tips.get()).booleanValue()) {
                                                ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lCoolSense\u63d0\u9192\u60a8\u00a78]\u00a7c\u00a7d\u5220\u9664\u65e0\u654c\u4eba\uff1a" + this.$name);
                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        }
                        if (matcher2.find()) {
                            String strGroup2 = matcher2.group(1);
                            Intrinsics.checkExpressionValueIsNotNull(strGroup2, "matcher2.group(1)");
                            if (strGroup2 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }
                            String string2 = StringsKt.trim((CharSequence) strGroup2).toString();
                            if (!Intrinsics.areEqual(string2, "")) {
                                LiquidBounce.INSTANCE.getFileManager().friendsConfig.addFriend(string2);
                                ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lCoolSense\u63d0\u9192\u60a8\u00a78]\u00a7c\u00a7d\u6dfb\u52a0\u65e0\u654c\u4eba\uff1a" + string2);
                                new Thread(new Runnable(string2) { // from class: net.ccbluex.liquidbounce.features.module.modules.hyt.HytGetName.onPacket.4
                                    final String $name;

                                    {
                                        this.$name = string2;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() throws InterruptedException {
                                        try {
                                            Thread.sleep(10000L);
                                            LiquidBounce.INSTANCE.getFileManager().friendsConfig.removeFriend(this.$name);
                                            ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lCoolSense\u63d0\u9192\u60a8\u00a78]\u00a7c\u00a7d\u5220\u9664\u65e0\u654c\u4eba\uff1a" + this.$name);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case 48636014:
                    if (!lowerCase.equals("32/64")) {
                        return;
                    }
                    break;
                default:
                    return;
            }
            Matcher matcher3 = Pattern.compile("\u6740\u6b7b\u4e86 (.*?)\\(").matcher(packet.asSPacketChat().getChatComponent().func_150260_c());
            Matcher matcher4 = Pattern.compile("\u8d77\u5e8a\u6218\u4e89>> (.*?) (\\((((.*?)\u6b7b\u4e86!)))").matcher(packet.asSPacketChat().getChatComponent().func_150260_c());
            if (matcher3.find()) {
                String strGroup3 = matcher3.group(1);
                Intrinsics.checkExpressionValueIsNotNull(strGroup3, "matcher.group(1)");
                if (strGroup3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                String string3 = StringsKt.trim((CharSequence) strGroup3).toString();
                if (!Intrinsics.areEqual(string3, "")) {
                    LiquidBounce.INSTANCE.getFileManager().friendsConfig.addFriend(string3);
                    if (((Boolean) this.tips.get()).booleanValue()) {
                        ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lCoolSense\u63d0\u9192\u60a8\u00a78]\u00a7c\u00a7d\u6dfb\u52a0\u65e0\u654c\u4eba\uff1a" + string3);
                    }
                    new Thread(new Runnable(this, string3) { // from class: net.ccbluex.liquidbounce.features.module.modules.hyt.HytGetName.onPacket.1
                        final HytGetName this$0;
                        final String $name;

                        {
                            this.this$0 = this;
                            this.$name = string3;
                        }

                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            try {
                                Thread.sleep(6000L);
                                LiquidBounce.INSTANCE.getFileManager().friendsConfig.removeFriend(this.$name);
                                if (((Boolean) this.this$0.tips.get()).booleanValue()) {
                                    ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lCoolSense\u63d0\u9192\u60a8\u00a78]\u00a7c\u00a7d\u5220\u9664\u65e0\u654c\u4eba\uff1a" + this.$name);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
            if (matcher4.find()) {
                String strGroup4 = matcher4.group(1);
                Intrinsics.checkExpressionValueIsNotNull(strGroup4, "matcher2.group(1)");
                if (strGroup4 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                String string4 = StringsKt.trim((CharSequence) strGroup4).toString();
                if (!Intrinsics.areEqual(string4, "")) {
                    LiquidBounce.INSTANCE.getFileManager().friendsConfig.addFriend(string4);
                    if (((Boolean) this.tips.get()).booleanValue()) {
                        ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lCoolSense\u63d0\u9192\u60a8\u00a78]\u00a7c\u00a7d\u6dfb\u52a0\u65e0\u654c\u4eba\uff1a" + string4);
                    }
                    new Thread(new Runnable(this, string4) { // from class: net.ccbluex.liquidbounce.features.module.modules.hyt.HytGetName.onPacket.2
                        final HytGetName this$0;
                        final String $name;

                        {
                            this.this$0 = this;
                            this.$name = string4;
                        }

                        @Override // java.lang.Runnable
                        public final void run() throws InterruptedException {
                            try {
                                Thread.sleep(6000L);
                                LiquidBounce.INSTANCE.getFileManager().friendsConfig.removeFriend(this.$name);
                                if (((Boolean) this.this$0.tips.get()).booleanValue()) {
                                    ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lCoolSense\u63d0\u9192\u60a8\u00a78]\u00a7c\u00a7d\u5220\u9664\u65e0\u654c\u4eba\uff1a" + this.$name);
                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        }
    }

    @EventTarget
    public final void onWorld(@Nullable WorldEvent worldEvent) {
        clearAll();
    }

    private final void clearAll() {
        LiquidBounce.INSTANCE.getFileManager().friendsConfig.clearFriends();
    }
}
