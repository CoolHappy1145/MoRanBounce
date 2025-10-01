package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/LoginCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/LoginCommand.class */
public final class LoginCommand extends Command {
    public LoginCommand() {
        super("login", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        String strLogin;
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length <= 1) {
            chatSyntax("login <username/email> [password]");
            return;
        }
        if (args.length > 2) {
            strLogin = GuiAltManager.login(new MinecraftAccount(args[1], args[2]));
            Intrinsics.checkExpressionValueIsNotNull(strLogin, "GuiAltManager.login(Mine\u2026ccount(args[1], args[2]))");
        } else {
            strLogin = GuiAltManager.login(new MinecraftAccount(args[1]));
            Intrinsics.checkExpressionValueIsNotNull(strLogin, "GuiAltManager.login(MinecraftAccount(args[1]))");
        }
        String str = strLogin;
        chat(str);
        if (!StringsKt.startsWith$default(str, "\u00a7cYour name is now", false, 2, (Object) null) || MinecraftInstance.f157mc.isIntegratedServerRunning()) {
            return;
        }
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        theWorld.sendQuittingDisconnectingPacket();
        ServerUtils.connectToLastServer();
    }
}
