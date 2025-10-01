package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/VClipCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/VClipCommand.class */
public final class VClipCommand extends Command {
    public VClipCommand() {
        super("vclip", new String[0]);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) throws NumberFormatException {
        IEntityPlayerSP ridingEntity;
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length > 1) {
            try {
                double d = Double.parseDouble(args[1]);
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer != null) {
                    if (thePlayer.isRiding()) {
                        ridingEntity = thePlayer.getRidingEntity();
                        if (ridingEntity == null) {
                            Intrinsics.throwNpe();
                        }
                    } else {
                        ridingEntity = thePlayer;
                    }
                    IEntity iEntity = ridingEntity;
                    iEntity.setPosition(iEntity.getPosX(), iEntity.getPosY() + d, iEntity.getPosZ());
                    chat("You were teleported.");
                    return;
                }
                return;
            } catch (NumberFormatException unused) {
                chatSyntaxError();
                return;
            }
        }
        chatSyntax("vclip <value>");
    }
}
