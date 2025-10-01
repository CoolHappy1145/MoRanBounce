package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovementInput;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdZ\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0016J\b\u0010,\u001a\u00020)H\u0016J\b\u0010-\u001a\u00020)H\u0016J\b\u0010.\u001a\u00020)H\u0016J\u0010\u0010/\u001a\u00020)2\u0006\u00100\u001a\u000201H\u0016J\u0010\u00102\u001a\u00020)2\u0006\u00103\u001a\u000204H\u0016R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR$\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\u000e8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR$\u0010\u001b\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u001c\u0010\u000b\"\u0004\b\u001d\u0010\rR\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010!R$\u0010\"\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\u00158V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b#\u0010\u0016\"\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b'\u0010\u0016\u00a8\u00065"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/EntityPlayerSPImpl;", "T", "Lnet/minecraft/client/entity/EntityPlayerSP;", "Lnet/ccbluex/liquidbounce/injection/backend/AbstractClientPlayerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "wrapped", "(Lnet/minecraft/client/entity/EntityPlayerSP;)V", PropertyDescriptor.VALUE, "", "horseJumpPower", "getHorseJumpPower", "()F", "setHorseJumpPower", "(F)V", "", "horseJumpPowerCounter", "getHorseJumpPowerCounter", "()I", "setHorseJumpPowerCounter", "(I)V", "isHandActive", "", "()Z", "movementInput", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovementInput;", "getMovementInput", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovementInput;", "renderArmPitch", "getRenderArmPitch", "setRenderArmPitch", "sendQueue", "Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "getSendQueue", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/network/IINetHandlerPlayClient;", "serverSprintState", "getServerSprintState", "setServerSprintState", "(Z)V", "sneaking", "getSneaking", "addChatMessage", "", "component", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "closeScreen", "resetActiveHand", "respawnPlayer", "sendChatMessage", "msg", "", "setActiveHandSP", "Hand", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/EntityPlayerSPImpl.class */
public class EntityPlayerSPImpl extends AbstractClientPlayerImpl implements IEntityPlayerSP {
    @Override // net.ccbluex.liquidbounce.injection.backend.EntityImpl, net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isSneaking() {
        return getSneaking();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EntityPlayerSPImpl(@NotNull EntityPlayerSP wrapped) {
        super((AbstractClientPlayer) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public int getHorseJumpPowerCounter() {
        return getWrapped().field_110320_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public void setHorseJumpPowerCounter(int i) {
        getWrapped().field_110320_a = i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public float getHorseJumpPower() {
        return getWrapped().field_110321_bQ;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public void setHorseJumpPower(float f) {
        getWrapped().field_110321_bQ = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public boolean isHandActive() {
        return getWrapped().func_184587_cr();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    @NotNull
    public IINetHandlerPlayClient getSendQueue() {
        NetHandlerPlayClient netHandlerPlayClient = getWrapped().field_71174_a;
        Intrinsics.checkExpressionValueIsNotNull(netHandlerPlayClient, "wrapped.connection");
        return new INetHandlerPlayClientImpl(netHandlerPlayClient);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    @NotNull
    public IMovementInput getMovementInput() {
        MovementInput movementInput = getWrapped().field_71158_b;
        Intrinsics.checkExpressionValueIsNotNull(movementInput, "wrapped.movementInput");
        return new MovementInputImpl(movementInput);
    }

    @Override // net.ccbluex.liquidbounce.injection.backend.EntityImpl
    public boolean getSneaking() {
        return getWrapped().func_70093_af();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public boolean getServerSprintState() {
        return getWrapped().field_175171_bO;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public void setServerSprintState(boolean z) {
        getWrapped().field_175171_bO = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public float getRenderArmPitch() {
        return getWrapped().field_71155_g;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public void setRenderArmPitch(float f) {
        getWrapped().field_71155_g = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public void sendChatMessage(@NotNull String msg) {
        Intrinsics.checkParameterIsNotNull(msg, "msg");
        getWrapped().func_71165_d(msg);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public void respawnPlayer() {
        getWrapped().func_71004_bE();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public void resetActiveHand() {
        getWrapped().func_184602_cy();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public void setActiveHandSP(@NotNull WEnumHand Hand) {
        EnumHand enumHand;
        Intrinsics.checkParameterIsNotNull(Hand, "Hand");
        EntityPlayerSP wrapped = getWrapped();
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$17[Hand.ordinal()]) {
            case 1:
                enumHand = EnumHand.MAIN_HAND;
                break;
            case 2:
                enumHand = EnumHand.OFF_HAND;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        wrapped.func_184598_c(enumHand);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public void addChatMessage(@NotNull IIChatComponent component) {
        Intrinsics.checkParameterIsNotNull(component, "component");
        getWrapped().func_145747_a(((IChatComponentImpl) component).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP
    public void closeScreen() {
        getWrapped().func_71053_j();
    }
}
