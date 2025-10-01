package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacketByINetHandlerPlayServer;
import net.ccbluex.liquidbounce.api.minecraft.network.handshake.client.ICPacketHandshake;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketChatMessage;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketHeldItemChange;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ISPacketCloseWindow;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketAnimation;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketChat;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntity;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketResourcePackSend;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketSpawnGlobalEntity;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketTabComplete;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketTitle;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketWindowItems;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.network.play.server.SPacketWindowItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0096\u0001\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\b\u0016\u0018\ufffd\ufffd*\f\b\ufffd\ufffd\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\"H\u0016J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020(H\u0016J\b\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020,H\u0016J\b\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u000200H\u0016J\u0013\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u000104H\u0096\u0002R\u0013\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007\u00a8\u00065"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "T", "Lnet/minecraft/network/Packet;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "wrapped", "(Lnet/minecraft/network/Packet;)V", "getWrapped", "()Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/Packet;", "asCPacketChatMessage", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketChatMessage;", "asCPacketCustomPayload", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCustomPayload;", "asCPacketEntityAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction;", "asCPacketHandshake", "Lnet/ccbluex/liquidbounce/api/minecraft/network/handshake/client/ICPacketHandshake;", "asCPacketHeldItemChange", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketHeldItemChange;", "asCPacketPlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayer;", "asCPacketPlayerDigging", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging;", "asCPacketUseEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity;", "asIPacketByINetHandlerPlayServer", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacketByINetHandlerPlayServer;", "asSPacketAnimation", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketAnimation;", "asSPacketChat", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketChat;", "asSPacketCloseWindow", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ISPacketCloseWindow;", "asSPacketEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketEntity;", "asSPacketEntityVelocity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketEntityVelocity;", "asSPacketPosLook", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketPosLook;", "asSPacketResourcePackSend", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketResourcePackSend;", "asSPacketSpawnGlobalEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketSpawnGlobalEntity;", "asSPacketTabComplete", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketTabComplete;", "asSPacketTitle", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketTitle;", "asSPacketWindowItems", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketWindowItems;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/PacketImpl.class */
public class PacketImpl implements IPacket {

    @NotNull
    private final Packet wrapped;

    @NotNull
    public final Packet getWrapped() {
        return this.wrapped;
    }

    public PacketImpl(@NotNull Packet wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketChat asSPacketChat() {
        SPacketChat sPacketChat = this.wrapped;
        if (sPacketChat == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketChat");
        }
        return new SPacketChatImpl(sPacketChat);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketAnimation asSPacketAnimation() {
        SPacketAnimation sPacketAnimation = this.wrapped;
        if (sPacketAnimation == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketAnimation");
        }
        return new SPacketAnimationImpl(sPacketAnimation);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketEntity asSPacketEntity() {
        SPacketEntity sPacketEntity = this.wrapped;
        if (sPacketEntity == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketEntity");
        }
        return new SPacketEntityImpl(sPacketEntity);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ICPacketPlayer asCPacketPlayer() {
        CPacketPlayer cPacketPlayer = this.wrapped;
        if (cPacketPlayer == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketPlayer");
        }
        return new CPacketPlayerImpl(cPacketPlayer);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ICPacketUseEntity asCPacketUseEntity() {
        CPacketUseEntity cPacketUseEntity = this.wrapped;
        if (cPacketUseEntity == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketUseEntity");
        }
        return new CPacketUseEntityImpl(cPacketUseEntity);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketEntityVelocity asSPacketEntityVelocity() {
        SPacketEntityVelocity sPacketEntityVelocity = this.wrapped;
        if (sPacketEntityVelocity == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketEntityVelocity");
        }
        return new SPacketEntityVelocityImpl(sPacketEntityVelocity);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ICPacketChatMessage asCPacketChatMessage() {
        CPacketChatMessage cPacketChatMessage = this.wrapped;
        if (cPacketChatMessage == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketChatMessage");
        }
        return new CPacketChatMessageImpl(cPacketChatMessage);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketTitle asSPacketTitle() {
        SPacketTitle sPacketTitle = this.wrapped;
        if (sPacketTitle == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketTitle");
        }
        return new SPacketTitleImpl(sPacketTitle);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketCloseWindow asSPacketCloseWindow() {
        SPacketCloseWindow sPacketCloseWindow = this.wrapped;
        if (sPacketCloseWindow == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketCloseWindow");
        }
        return new SPacketCloseWindowImpl(sPacketCloseWindow);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketTabComplete asSPacketTabComplete() {
        SPacketTabComplete sPacketTabComplete = this.wrapped;
        if (sPacketTabComplete == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketTabComplete");
        }
        return new SPacketTabCompleteImpl(sPacketTabComplete);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public IPacketByINetHandlerPlayServer asIPacketByINetHandlerPlayServer() {
        Packet packet = this.wrapped;
        if (packet == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.Packet<net.minecraft.network.play.INetHandlerPlayServer>");
        }
        return new PacketByINetHandlerPlayServerImpl(packet);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketPosLook asSPacketPosLook() {
        SPacketPlayerPosLook sPacketPlayerPosLook = this.wrapped;
        if (sPacketPlayerPosLook == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketPlayerPosLook");
        }
        return new SPacketPosLookImpl(sPacketPlayerPosLook);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketResourcePackSend asSPacketResourcePackSend() {
        SPacketResourcePackSend sPacketResourcePackSend = this.wrapped;
        if (sPacketResourcePackSend == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketResourcePackSend");
        }
        return new SPacketResourcePackSendImpl(sPacketResourcePackSend);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ICPacketHeldItemChange asCPacketHeldItemChange() {
        CPacketHeldItemChange cPacketHeldItemChange = this.wrapped;
        if (cPacketHeldItemChange == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketHeldItemChange");
        }
        return new CPacketHeldItemChangeImpl(cPacketHeldItemChange);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketWindowItems asSPacketWindowItems() {
        SPacketWindowItems sPacketWindowItems = this.wrapped;
        if (sPacketWindowItems == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketWindowItems");
        }
        return new SPacketWindowItemsImpl(sPacketWindowItems);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ISPacketSpawnGlobalEntity asSPacketSpawnGlobalEntity() {
        SPacketSpawnGlobalEntity sPacketSpawnGlobalEntity = this.wrapped;
        if (sPacketSpawnGlobalEntity == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketSpawnGlobalEntity");
        }
        return new SPacketSpawnGlobalEntityImpl(sPacketSpawnGlobalEntity);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ICPacketEntityAction asCPacketEntityAction() {
        CPacketEntityAction cPacketEntityAction = this.wrapped;
        if (cPacketEntityAction == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketEntityAction");
        }
        return new CPacketEntityActionImpl(cPacketEntityAction);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ICPacketCustomPayload asCPacketCustomPayload() {
        CPacketCustomPayload cPacketCustomPayload = this.wrapped;
        if (cPacketCustomPayload == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketCustomPayload");
        }
        return new CPacketCustomPayloadImpl(cPacketCustomPayload);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ICPacketHandshake asCPacketHandshake() {
        C00Handshake c00Handshake = this.wrapped;
        if (c00Handshake == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.handshake.client.C00Handshake");
        }
        return new CPacketHandshakeImpl(c00Handshake);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.IPacket
    @NotNull
    public ICPacketPlayerDigging asCPacketPlayerDigging() {
        CPacketPlayerDigging cPacketPlayerDigging = this.wrapped;
        if (cPacketPlayerDigging == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.client.CPacketPlayerDigging");
        }
        return new CPacketPlayerDiggingImpl(cPacketPlayerDigging);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && Intrinsics.areEqual(((PacketImpl) obj).wrapped, this.wrapped);
    }
}
