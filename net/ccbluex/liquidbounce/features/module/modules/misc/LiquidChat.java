package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.event.IClickEvent;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.chat.Client;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientErrorPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientNewJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientPrivateMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientSuccessPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerRequestJWTPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdY\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002*\u0001\n\b\u0007\u0018\ufffd\ufffd  2\u00020\u0001:\u0001 B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0016\u0010\u0010\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006!"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "client", "Lnet/ccbluex/liquidbounce/chat/Client;", "getClient", "()Lnet/ccbluex/liquidbounce/chat/Client;", "connectTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "jwtValue", "net/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat$jwtValue$1", "Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat$jwtValue$1;", "loggedIn", "", "loginThread", "Ljava/lang/Thread;", "urlPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "connect", "", "onDisable", "onSession", "sessionEvent", "Lnet/ccbluex/liquidbounce/event/SessionEvent;", "onUpdate", "updateEvent", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "toChatComponent", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "string", "", "Companion", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "LiquidChat", description = "Allows you to chat with other LiquidBounce users.", category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat.class */
public final class LiquidChat extends Module {
    private final LiquidChat$jwtValue$1 jwtValue;
    private boolean loggedIn;
    private Thread loginThread;
    private final Pattern urlPattern;
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static String jwtToken = "";

    @NotNull
    private final Client client = new Client(this) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat$client$1
        final LiquidChat this$0;

        {
            this.this$0 = this;
        }

        @Override // net.ccbluex.liquidbounce.chat.ClientListener
        public void onConnect() {
            ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a79Connecting to chat server...");
        }

        @Override // net.ccbluex.liquidbounce.chat.ClientListener
        public void onConnected() {
            ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a79Connected to chat server!");
        }

        @Override // net.ccbluex.liquidbounce.chat.ClientListener
        public void onDisconnect() {
            ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a7cDisconnected from chat server!");
        }

        @Override // net.ccbluex.liquidbounce.chat.ClientListener
        public void onLogon() {
            ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a79Logging in...");
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
        java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
         */
        /* JADX WARN: Removed duplicated region for block: B:76:0x027e  */
        @Override // net.ccbluex.liquidbounce.chat.ClientListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onPacket(@NotNull Packet packet) {
            String message;
            Intrinsics.checkParameterIsNotNull(packet, "packet");
            if (packet instanceof ClientMessagePacket) {
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    ClientUtils.getLogger().info("[LiquidChat] " + ((ClientMessagePacket) packet).getUser().getName() + ": " + ((ClientMessagePacket) packet).getContent());
                    return;
                }
                IIChatComponent iIChatComponentCreateChatComponentText = MinecraftInstance.classProvider.createChatComponentText("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a79" + ((ClientMessagePacket) packet).getUser().getName() + ": ");
                iIChatComponentCreateChatComponentText.appendSibling(this.this$0.toChatComponent(((ClientMessagePacket) packet).getContent()));
                thePlayer.addChatMessage(iIChatComponentCreateChatComponentText);
                return;
            }
            if (!(packet instanceof ClientPrivateMessagePacket)) {
                if (packet instanceof ClientErrorPacket) {
                    String message2 = ((ClientErrorPacket) packet).getMessage();
                    switch (message2.hashCode()) {
                        case -1702222618:
                            if (message2.equals("LoginFailed")) {
                                message = "Login Failed!";
                                break;
                            } else {
                                message = ((ClientErrorPacket) packet).getMessage();
                                break;
                            }
                        case -1510372431:
                            if (message2.equals("NotBanned")) {
                                message = "You are not banned!";
                                break;
                            }
                            break;
                        case -1285959671:
                            if (message2.equals("MessageTooLong")) {
                                message = "Message is too long!";
                                break;
                            }
                            break;
                        case -1045325153:
                            if (message2.equals("AlreadyLoggedIn")) {
                                message = "You are already logged in!";
                                break;
                            }
                            break;
                        case -643429254:
                            if (message2.equals("RateLimited")) {
                                message = "You have been rate limited. Please try again later.";
                                break;
                            }
                            break;
                        case -187442662:
                            if (message2.equals("NotLoggedIn")) {
                                message = "You must be logged in to use the chat! Enable LiquidChat.";
                                break;
                            }
                            break;
                        case -133334702:
                            if (message2.equals("InvalidId")) {
                                message = "The given ID is invalid!";
                                break;
                            }
                            break;
                        case -52595950:
                            if (message2.equals("InvalidCharacter")) {
                                message = "Message contains a non-ASCII character!";
                                break;
                            }
                            break;
                        case 227434454:
                            if (message2.equals("PrivateMessageNotAccepted")) {
                                message = "Private message not accepted!";
                                break;
                            }
                            break;
                        case 248847163:
                            if (message2.equals("NotSupported")) {
                                message = "This method is not supported!";
                                break;
                            }
                            break;
                        case 635054813:
                            if (message2.equals("Internal")) {
                                message = "An internal server error occurred!";
                                break;
                            }
                            break;
                        case 944720037:
                            if (message2.equals("NotPermitted")) {
                                message = "You are missing the required permissions!";
                                break;
                            }
                            break;
                        case 1022838298:
                            if (message2.equals("EmptyMessage")) {
                                message = "You are trying to send an empty message!";
                                break;
                            }
                            break;
                        case 1982491454:
                            if (message2.equals("Banned")) {
                                message = "You are banned!";
                                break;
                            }
                            break;
                        case 1990882921:
                            if (message2.equals("MojangRequestMissing")) {
                                message = "Mojang request missing!";
                                break;
                            }
                            break;
                    }
                    ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a7cError: \u00a77" + message);
                    return;
                }
                if (packet instanceof ClientSuccessPacket) {
                    String reason = ((ClientSuccessPacket) packet).getReason();
                    switch (reason.hashCode()) {
                        case 66543:
                            if (reason.equals("Ban")) {
                                ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a79Successfully banned user!");
                                break;
                            }
                            break;
                        case 73596745:
                            if (reason.equals("Login")) {
                                ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a79Logged in!");
                                ClientUtils.displayChatMessage("====================================");
                                ClientUtils.displayChatMessage("\u00a7c>> \u00a7lLiquidChat");
                                ClientUtils.displayChatMessage("\u00a77Write message: \u00a7a.chat <message>");
                                ClientUtils.displayChatMessage("\u00a77Write private message: \u00a7a.pchat <user> <message>");
                                ClientUtils.displayChatMessage("====================================");
                                setLoggedIn(true);
                                break;
                            }
                            break;
                        case 81873590:
                            if (reason.equals("Unban")) {
                                ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a79Successfully unbanned user!");
                                break;
                            }
                            break;
                    }
                    return;
                }
                if (packet instanceof ClientNewJWTPacket) {
                    LiquidChat.Companion.setJwtToken(((ClientNewJWTPacket) packet).getToken());
                    set(true);
                    this.this$0.setState(false);
                    this.this$0.setState(true);
                    return;
                }
                return;
            }
            ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a7c(P)\u00a79 " + ((ClientPrivateMessagePacket) packet).getUser().getName() + ": \u00a77" + ((ClientPrivateMessagePacket) packet).getContent());
        }

        @Override // net.ccbluex.liquidbounce.chat.ClientListener
        public void onError(@NotNull Throwable cause) {
            Intrinsics.checkParameterIsNotNull(cause, "cause");
            ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a7c\u00a7lError: \u00a77" + cause.getClass().getName() + ": " + cause.getMessage());
        }
    };
    private final MSTimer connectTimer = new MSTimer();

    /* JADX WARN: Type inference failed for: r1v0, types: [net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat$jwtValue$1] */
    public LiquidChat() {
        final String str = "JWT";
        final boolean z = false;
        this.jwtValue = new BoolValue(this, str, z) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat$jwtValue$1
            final LiquidChat this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(boolean z2, boolean z3) {
                if (this.this$0.getState()) {
                    this.this$0.setState(false);
                    this.this$0.setState(true);
                }
            }
        };
        LiquidBounce.INSTANCE.getCommandManager().registerCommand(new Command(this, "chat", new String[]{"lc", "irc"}) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat.1
            final LiquidChat this$0;

            {
                this.this$0 = this;
            }

            @Override // net.ccbluex.liquidbounce.features.command.Command
            public void execute(@NotNull String[] args) {
                Intrinsics.checkParameterIsNotNull(args, "args");
                if (args.length > 1) {
                    if (!this.this$0.getState()) {
                        chat("\u00a7cError: \u00a77LiquidChat is disabled!");
                        return;
                    }
                    if (!this.this$0.getClient().isConnected()) {
                        chat("\u00a7cError: \u00a7LiquidChat is currently not connected to the server!");
                        return;
                    }
                    String message = StringUtils.toCompleteString(args, 1);
                    Client client = this.this$0.getClient();
                    Intrinsics.checkExpressionValueIsNotNull(message, "message");
                    client.sendMessage(message);
                    return;
                }
                chatSyntax("chat <message>");
            }
        });
        LiquidBounce.INSTANCE.getCommandManager().registerCommand(new Command(this, "pchat", new String[]{"privatechat", "lcpm"}) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat.2
            final LiquidChat this$0;

            {
                this.this$0 = this;
            }

            @Override // net.ccbluex.liquidbounce.features.command.Command
            public void execute(@NotNull String[] args) {
                Intrinsics.checkParameterIsNotNull(args, "args");
                if (args.length > 2) {
                    if (!this.this$0.getState()) {
                        chat("\u00a7cError: \u00a77LiquidChat is disabled!");
                        return;
                    }
                    if (!this.this$0.getClient().isConnected()) {
                        chat("\u00a7cError: \u00a7LiquidChat is currently not connected to the server!");
                        return;
                    }
                    String str2 = args[1];
                    String message = StringUtils.toCompleteString(args, 2);
                    Client client = this.this$0.getClient();
                    Intrinsics.checkExpressionValueIsNotNull(message, "message");
                    client.sendPrivateMessage(str2, message);
                    chat("Message was successfully sent.");
                    return;
                }
                chatSyntax("pchat <username> <message>");
            }
        });
        LiquidBounce.INSTANCE.getCommandManager().registerCommand(new Command(this, "chattoken", new String[0]) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat.3
            final LiquidChat this$0;

            {
                this.this$0 = this;
            }

            @Override // net.ccbluex.liquidbounce.features.command.Command
            public void execute(@NotNull String[] args) {
                Intrinsics.checkParameterIsNotNull(args, "args");
                if (args.length > 1) {
                    if (StringsKt.equals(args[1], PropertyDescriptor.SET, true)) {
                        if (args.length > 2) {
                            Companion companion = LiquidChat.Companion;
                            String completeString = StringUtils.toCompleteString(args, 2);
                            Intrinsics.checkExpressionValueIsNotNull(completeString, "StringUtils.toCompleteString(args, 2)");
                            companion.setJwtToken(completeString);
                            set(true);
                            if (this.this$0.getState()) {
                                this.this$0.setState(false);
                                this.this$0.setState(true);
                                return;
                            }
                            return;
                        }
                        chatSyntax("chattoken set <token>");
                        return;
                    }
                    if (StringsKt.equals(args[1], "generate", true)) {
                        if (!this.this$0.getState()) {
                            chat("\u00a7cError: \u00a77LiquidChat is disabled!");
                            return;
                        } else {
                            this.this$0.getClient().sendPacket(new ServerRequestJWTPacket());
                            return;
                        }
                    }
                    if (StringsKt.equals(args[1], "copy", true)) {
                        if (LiquidChat.Companion.getJwtToken().length() == 0) {
                            chat("\u00a7cError: \u00a77No token set! Generate one first using '" + LiquidBounce.INSTANCE.getCommandManager().getPrefix() + "chattoken generate'.");
                            return;
                        }
                        ClipboardOwner stringSelection = new StringSelection(LiquidChat.Companion.getJwtToken());
                        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                        Intrinsics.checkExpressionValueIsNotNull(defaultToolkit, "Toolkit.getDefaultToolkit()");
                        defaultToolkit.getSystemClipboard().setContents((Transferable) stringSelection, stringSelection);
                        chat("\u00a7aCopied to clipboard!");
                        return;
                    }
                    return;
                }
                chatSyntax("chattoken <set/copy/generate>");
            }
        });
        LiquidBounce.INSTANCE.getCommandManager().registerCommand(new Command(this, "chatadmin", new String[0]) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat.4
            final LiquidChat this$0;

            {
                this.this$0 = this;
            }

            @Override // net.ccbluex.liquidbounce.features.command.Command
            public void execute(@NotNull String[] args) {
                Intrinsics.checkParameterIsNotNull(args, "args");
                if (!this.this$0.getState()) {
                    chat("\u00a7cError: \u00a77LiquidChat is disabled!");
                    return;
                }
                if (args.length > 1) {
                    if (StringsKt.equals(args[1], "ban", true)) {
                        if (args.length > 2) {
                            this.this$0.getClient().banUser(args[2]);
                            return;
                        } else {
                            chatSyntax("chatadmin ban <username>");
                            return;
                        }
                    }
                    if (StringsKt.equals(args[1], "unban", true)) {
                        if (args.length > 2) {
                            this.this$0.getClient().unbanUser(args[2]);
                            return;
                        } else {
                            chatSyntax("chatadmin unban <username>");
                            return;
                        }
                    }
                    return;
                }
                chatSyntax("chatadmin <ban/unban>");
            }
        });
        this.urlPattern = Pattern.compile("((?:[a-z0-9]{2,}:\\/\\/)?(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3}|(?:[-\\w_\\.]{1,}\\.[a-z]{2,}?))(?::[0-9]{1,5})?.*?(?=[!\"\u00a7 \n]|$))", 2);
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat$Companion;", "", "()V", "jwtToken", "", "getJwtToken", "()Ljava/lang/String;", "setJwtToken", "(Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final String getJwtToken() {
            return LiquidChat.jwtToken;
        }

        public final void setJwtToken(@NotNull String str) {
            Intrinsics.checkParameterIsNotNull(str, "<set-?>");
            LiquidChat.jwtToken = str;
        }
    }

    @NotNull
    public final Client getClient() {
        return this.client;
    }

    public void onDisable() {
        this.loggedIn = false;
        this.client.disconnect();
    }

    @EventTarget
    public final void onSession(@NotNull SessionEvent sessionEvent) {
        Intrinsics.checkParameterIsNotNull(sessionEvent, "sessionEvent");
        this.client.disconnect();
        connect();
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent updateEvent) {
        Intrinsics.checkParameterIsNotNull(updateEvent, "updateEvent");
        if (this.client.isConnected()) {
            return;
        }
        if (this.loginThread != null) {
            Thread thread = this.loginThread;
            if (thread == null) {
                Intrinsics.throwNpe();
            }
            if (thread.isAlive()) {
                return;
            }
        }
        if (this.connectTimer.hasTimePassed(5000L)) {
            connect();
            this.connectTimer.reset();
        }
    }

    private final void connect() {
        if (this.client.isConnected()) {
            return;
        }
        if (this.loginThread != null) {
            Thread thread = this.loginThread;
            if (thread == null) {
                Intrinsics.throwNpe();
            }
            if (thread.isAlive()) {
                return;
            }
        }
        if (((Boolean) get()).booleanValue()) {
            if (jwtToken.length() == 0) {
                ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a7cError: \u00a77No token provided!");
                setState(false);
                return;
            }
        }
        this.loggedIn = false;
        this.loginThread = ThreadsKt.thread$default(false, false, null, null, 0, new Function0(this) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.LiquidChat.connect.1
            final LiquidChat this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public Object invoke() {
                m1576invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: collision with other method in class */
            public final void m1576invoke() {
                try {
                    this.this$0.getClient().connect();
                    if (((Boolean) get()).booleanValue()) {
                        this.this$0.getClient().loginJWT(LiquidChat.Companion.getJwtToken());
                    } else if (UserUtils.INSTANCE.isValidToken(MinecraftInstance.f157mc.getSession().getToken())) {
                        this.this$0.getClient().loginMojang();
                    }
                } catch (Exception e) {
                    ClientUtils.getLogger().error("LiquidChat error", e);
                    ClientUtils.displayChatMessage("\u00a77[\u00a7a\u00a7lChat\u00a77] \u00a7cError: \u00a77" + e.getClass().getName() + ": " + e.getMessage());
                }
                this.this$0.loginThread = (Thread) null;
            }
        }, 31, null);
    }

    private final IIChatComponent toChatComponent(String str) {
        IIChatComponent iIChatComponentCreateChatComponentText = (IIChatComponent) null;
        Matcher matcher = this.urlPattern.matcher(str);
        int i = 0;
        while (matcher.find()) {
            int iStart = matcher.start();
            int iEnd = matcher.end();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String strSubstring = str.substring(i, iStart);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            if (strSubstring.length() > 0) {
                if (iIChatComponentCreateChatComponentText == null) {
                    iIChatComponentCreateChatComponentText = MinecraftInstance.classProvider.createChatComponentText(strSubstring);
                    iIChatComponentCreateChatComponentText.getChatStyle().setColor(WEnumChatFormatting.GRAY);
                } else {
                    iIChatComponentCreateChatComponentText.appendText(strSubstring);
                }
            }
            i = iEnd;
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String strSubstring2 = str.substring(iStart, iEnd);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            if (new URI(strSubstring2).getScheme() != null) {
                IIChatComponent iIChatComponentCreateChatComponentText2 = MinecraftInstance.classProvider.createChatComponentText(strSubstring2);
                iIChatComponentCreateChatComponentText2.getChatStyle().setChatClickEvent(MinecraftInstance.classProvider.createClickEvent(IClickEvent.WAction.OPEN_URL, strSubstring2));
                iIChatComponentCreateChatComponentText2.getChatStyle().setUnderlined(true);
                iIChatComponentCreateChatComponentText2.getChatStyle().setColor(WEnumChatFormatting.GRAY);
                if (iIChatComponentCreateChatComponentText == null) {
                    iIChatComponentCreateChatComponentText = iIChatComponentCreateChatComponentText2;
                } else {
                    iIChatComponentCreateChatComponentText.appendSibling(iIChatComponentCreateChatComponentText2);
                }
            } else if (iIChatComponentCreateChatComponentText == null) {
                iIChatComponentCreateChatComponentText = MinecraftInstance.classProvider.createChatComponentText(strSubstring2);
                iIChatComponentCreateChatComponentText.getChatStyle().setColor(WEnumChatFormatting.GRAY);
            } else {
                iIChatComponentCreateChatComponentText.appendText(strSubstring2);
            }
        }
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String strSubstring3 = str.substring(i);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring3, "(this as java.lang.String).substring(startIndex)");
        if (iIChatComponentCreateChatComponentText == null) {
            iIChatComponentCreateChatComponentText = MinecraftInstance.classProvider.createChatComponentText(strSubstring3);
            iIChatComponentCreateChatComponentText.getChatStyle().setColor(WEnumChatFormatting.GRAY);
        } else {
            if (strSubstring3.length() > 0) {
                IIChatComponent iIChatComponent = iIChatComponentCreateChatComponentText;
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String strSubstring4 = str.substring(i);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring4, "(this as java.lang.String).substring(startIndex)");
                iIChatComponent.appendText(strSubstring4);
            }
        }
        return iIChatComponentCreateChatComponentText;
    }
}
