package net.ccbluex.liquidbounce.api.minecraft.client.entity;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovementInput;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H&J\b\u0010$\u001a\u00020!H&J\b\u0010%\u001a\u00020!H&J\b\u0010&\u001a\u00020!H&J\u0010\u0010'\u001a\u00020!2\u0006\u0010(\u001a\u00020)H&J\u0010\u0010*\u001a\u00020!2\u0006\u0010+\u001a\u00020,H&R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0018\u0010\u0015\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0016\u0010\u0005\"\u0004\b\u0017\u0010\u0007R\u0012\u0010\u0018\u001a\u00020\u0019X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0018\u0010\u001c\u001a\u00020\u000fX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001d\u0010\u0010\"\u0004\b\u001e\u0010\u001f\u00a8\u0006-"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IAbstractClientPlayer;", "horseJumpPower", "", "getHorseJumpPower", "()F", "setHorseJumpPower", "(F)V", "horseJumpPowerCounter", "", "getHorseJumpPowerCounter", "()I", "setHorseJumpPowerCounter", "(I)V", "isHandActive", "", "()Z", "movementInput", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovementInput;", "getMovementInput", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovementInput;", "renderArmPitch", "getRenderArmPitch", "setRenderArmPitch", "sendQueue", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "getSendQueue", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "serverSprintState", "getServerSprintState", "setServerSprintState", "(Z)V", "addChatMessage", "", "component", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "closeScreen", "resetActiveHand", "respawnPlayer", "sendChatMessage", "msg", "", "setActiveHandSP", "Hand", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP.class */
public interface IEntityPlayerSP extends IAbstractClientPlayer {
    int getHorseJumpPowerCounter();

    void setHorseJumpPowerCounter(int i);

    float getHorseJumpPower();

    void setHorseJumpPower(float f);

    @NotNull
    IINetHandlerPlayClient getSendQueue();

    @NotNull
    IMovementInput getMovementInput();

    boolean isHandActive();

    boolean getServerSprintState();

    void setServerSprintState(boolean z);

    float getRenderArmPitch();

    void setRenderArmPitch(float f);

    void sendChatMessage(@NotNull String str);

    void respawnPlayer();

    void resetActiveHand();

    void addChatMessage(@NotNull IIChatComponent iIChatComponent);

    void setActiveHandSP(@NotNull WEnumHand wEnumHand);

    void closeScreen();
}
