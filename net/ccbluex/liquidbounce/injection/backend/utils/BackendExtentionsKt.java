package net.ccbluex.liquidbounce.injection.backend.utils;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.WEnumPlayerModelParts;
import net.ccbluex.liquidbounce.api.minecraft.event.IClickEvent;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketClientStatus;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketResourcePackStatus;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3i;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorldSettings;
import net.ccbluex.liquidbounce.injection.backend.Backend;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.GameType;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0088\u0001\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\r\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0002H\u0086\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\u0086\b\u001a\r\u0010\u0005\u001a\u00020\u0002*\u00020\u0001H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0007*\u00020\bH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\t*\u00020\nH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u000b*\u00020\fH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\r*\u00020\u000eH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u000f*\u00020\u0010H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0011*\u00020\u0012H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0013*\u00020\u0014H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0015*\u00020\u0016H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0017*\u00020\u0018H\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u0019*\u00020\u001aH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u001b*\u00020\u001cH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u001d*\u00020\u001eH\u0086\b\u001a\r\u0010\u0006\u001a\u00020\u001f*\u00020 H\u0086\b\u001a\r\u0010!\u001a\u00020\n*\u00020\tH\u0086\b\u001a\r\u0010!\u001a\u00020\u0010*\u00020\u000fH\u0086\b\u001a\r\u0010!\u001a\u00020\u0012*\u00020\u0011H\u0086\b\u001a\r\u0010!\u001a\u00020\u0016*\u00020\u0015H\u0086\b\u001a\r\u0010!\u001a\u00020\u0018*\u00020\u0017H\u0086\b\u001a\r\u0010!\u001a\u00020\"*\u00020#H\u0086\b\u001a\r\u0010!\u001a\u00020\u001c*\u00020\u001bH\u0086\b\u001a\r\u0010!\u001a\u00020\u001e*\u00020\u001dH\u0086\b\u001a\r\u0010!\u001a\u00020\u001a*\u00020\u0019H\u0086\b\u001a\r\u0010!\u001a\u00020 *\u00020\u001fH\u0086\b\u00a8\u0006$"}, m27d2 = {"toClickType", "Lnet/minecraft/inventory/ClickType;", "", "toEntityEquipmentSlot", "Lnet/minecraft/inventory/EntityEquipmentSlot;", "toInt", "unwrap", "Lnet/minecraft/util/EnumHand;", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", "Lnet/minecraft/entity/player/EnumPlayerModelParts;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/WEnumPlayerModelParts;", "Lnet/minecraft/util/text/event/ClickEvent$Action;", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent$WAction;", "Lnet/minecraft/network/play/client/CPacketClientStatus$State;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus$WEnumState;", "Lnet/minecraft/network/play/client/CPacketEntityAction$Action;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction$WAction;", "Lnet/minecraft/network/play/client/CPacketPlayerDigging$Action;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging$WAction;", "Lnet/minecraft/network/play/client/CPacketResourcePackStatus$Action;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketResourcePackStatus$WAction;", "Lnet/minecraft/network/play/client/CPacketUseEntity$Action;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity$WAction;", "Lnet/minecraft/util/math/BlockPos;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "Lnet/minecraft/util/text/TextFormatting;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "Lnet/minecraft/util/math/Vec3d;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "Lnet/minecraft/util/math/Vec3i;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3i;", "Lnet/minecraft/world/GameType;", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorldSettings$WGameType;", "wrap", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition$WMovingObjectType;", "Lnet/minecraft/util/math/RayTraceResult$Type;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/utils/BackendExtentionsKt.class */
public final class BackendExtentionsKt {

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/utils/BackendExtentionsKt$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[RayTraceResult.Type.values().length];
        public static final int[] $EnumSwitchMapping$1;
        public static final int[] $EnumSwitchMapping$2;
        public static final int[] $EnumSwitchMapping$3;
        public static final int[] $EnumSwitchMapping$4;
        public static final int[] $EnumSwitchMapping$5;
        public static final int[] $EnumSwitchMapping$6;
        public static final int[] $EnumSwitchMapping$7;
        public static final int[] $EnumSwitchMapping$8;
        public static final int[] $EnumSwitchMapping$9;
        public static final int[] $EnumSwitchMapping$10;
        public static final int[] $EnumSwitchMapping$11;
        public static final int[] $EnumSwitchMapping$12;
        public static final int[] $EnumSwitchMapping$13;
        public static final int[] $EnumSwitchMapping$14;
        public static final int[] $EnumSwitchMapping$15;
        public static final int[] $EnumSwitchMapping$16;
        public static final int[] $EnumSwitchMapping$17;

        static {
            $EnumSwitchMapping$0[RayTraceResult.Type.MISS.ordinal()] = 1;
            $EnumSwitchMapping$0[RayTraceResult.Type.BLOCK.ordinal()] = 2;
            $EnumSwitchMapping$0[RayTraceResult.Type.ENTITY.ordinal()] = 3;
            $EnumSwitchMapping$1 = new int[WEnumPlayerModelParts.values().length];
            $EnumSwitchMapping$1[WEnumPlayerModelParts.CAPE.ordinal()] = 1;
            $EnumSwitchMapping$1[WEnumPlayerModelParts.JACKET.ordinal()] = 2;
            $EnumSwitchMapping$1[WEnumPlayerModelParts.LEFT_SLEEVE.ordinal()] = 3;
            $EnumSwitchMapping$1[WEnumPlayerModelParts.RIGHT_SLEEVE.ordinal()] = 4;
            $EnumSwitchMapping$1[WEnumPlayerModelParts.LEFT_PANTS_LEG.ordinal()] = 5;
            $EnumSwitchMapping$1[WEnumPlayerModelParts.RIGHT_PANTS_LEG.ordinal()] = 6;
            $EnumSwitchMapping$1[WEnumPlayerModelParts.HAT.ordinal()] = 7;
            $EnumSwitchMapping$2 = new int[EnumPlayerModelParts.values().length];
            $EnumSwitchMapping$2[EnumPlayerModelParts.CAPE.ordinal()] = 1;
            $EnumSwitchMapping$2[EnumPlayerModelParts.JACKET.ordinal()] = 2;
            $EnumSwitchMapping$2[EnumPlayerModelParts.LEFT_SLEEVE.ordinal()] = 3;
            $EnumSwitchMapping$2[EnumPlayerModelParts.RIGHT_SLEEVE.ordinal()] = 4;
            $EnumSwitchMapping$2[EnumPlayerModelParts.LEFT_PANTS_LEG.ordinal()] = 5;
            $EnumSwitchMapping$2[EnumPlayerModelParts.RIGHT_PANTS_LEG.ordinal()] = 6;
            $EnumSwitchMapping$2[EnumPlayerModelParts.HAT.ordinal()] = 7;
            $EnumSwitchMapping$3 = new int[TextFormatting.values().length];
            $EnumSwitchMapping$3[TextFormatting.BLACK.ordinal()] = 1;
            $EnumSwitchMapping$3[TextFormatting.DARK_BLUE.ordinal()] = 2;
            $EnumSwitchMapping$3[TextFormatting.DARK_GREEN.ordinal()] = 3;
            $EnumSwitchMapping$3[TextFormatting.DARK_AQUA.ordinal()] = 4;
            $EnumSwitchMapping$3[TextFormatting.DARK_RED.ordinal()] = 5;
            $EnumSwitchMapping$3[TextFormatting.DARK_PURPLE.ordinal()] = 6;
            $EnumSwitchMapping$3[TextFormatting.GOLD.ordinal()] = 7;
            $EnumSwitchMapping$3[TextFormatting.GRAY.ordinal()] = 8;
            $EnumSwitchMapping$3[TextFormatting.DARK_GRAY.ordinal()] = 9;
            $EnumSwitchMapping$3[TextFormatting.BLUE.ordinal()] = 10;
            $EnumSwitchMapping$3[TextFormatting.GREEN.ordinal()] = 11;
            $EnumSwitchMapping$3[TextFormatting.AQUA.ordinal()] = 12;
            $EnumSwitchMapping$3[TextFormatting.RED.ordinal()] = 13;
            $EnumSwitchMapping$3[TextFormatting.LIGHT_PURPLE.ordinal()] = 14;
            $EnumSwitchMapping$3[TextFormatting.YELLOW.ordinal()] = 15;
            $EnumSwitchMapping$3[TextFormatting.WHITE.ordinal()] = 16;
            $EnumSwitchMapping$3[TextFormatting.OBFUSCATED.ordinal()] = 17;
            $EnumSwitchMapping$3[TextFormatting.BOLD.ordinal()] = 18;
            $EnumSwitchMapping$3[TextFormatting.STRIKETHROUGH.ordinal()] = 19;
            $EnumSwitchMapping$3[TextFormatting.UNDERLINE.ordinal()] = 20;
            $EnumSwitchMapping$3[TextFormatting.ITALIC.ordinal()] = 21;
            $EnumSwitchMapping$3[TextFormatting.RESET.ordinal()] = 22;
            $EnumSwitchMapping$4 = new int[WEnumChatFormatting.values().length];
            $EnumSwitchMapping$4[WEnumChatFormatting.BLACK.ordinal()] = 1;
            $EnumSwitchMapping$4[WEnumChatFormatting.DARK_BLUE.ordinal()] = 2;
            $EnumSwitchMapping$4[WEnumChatFormatting.DARK_GREEN.ordinal()] = 3;
            $EnumSwitchMapping$4[WEnumChatFormatting.DARK_AQUA.ordinal()] = 4;
            $EnumSwitchMapping$4[WEnumChatFormatting.DARK_RED.ordinal()] = 5;
            $EnumSwitchMapping$4[WEnumChatFormatting.DARK_PURPLE.ordinal()] = 6;
            $EnumSwitchMapping$4[WEnumChatFormatting.GOLD.ordinal()] = 7;
            $EnumSwitchMapping$4[WEnumChatFormatting.GRAY.ordinal()] = 8;
            $EnumSwitchMapping$4[WEnumChatFormatting.DARK_GRAY.ordinal()] = 9;
            $EnumSwitchMapping$4[WEnumChatFormatting.BLUE.ordinal()] = 10;
            $EnumSwitchMapping$4[WEnumChatFormatting.GREEN.ordinal()] = 11;
            $EnumSwitchMapping$4[WEnumChatFormatting.AQUA.ordinal()] = 12;
            $EnumSwitchMapping$4[WEnumChatFormatting.RED.ordinal()] = 13;
            $EnumSwitchMapping$4[WEnumChatFormatting.LIGHT_PURPLE.ordinal()] = 14;
            $EnumSwitchMapping$4[WEnumChatFormatting.YELLOW.ordinal()] = 15;
            $EnumSwitchMapping$4[WEnumChatFormatting.WHITE.ordinal()] = 16;
            $EnumSwitchMapping$4[WEnumChatFormatting.OBFUSCATED.ordinal()] = 17;
            $EnumSwitchMapping$4[WEnumChatFormatting.BOLD.ordinal()] = 18;
            $EnumSwitchMapping$4[WEnumChatFormatting.STRIKETHROUGH.ordinal()] = 19;
            $EnumSwitchMapping$4[WEnumChatFormatting.UNDERLINE.ordinal()] = 20;
            $EnumSwitchMapping$4[WEnumChatFormatting.ITALIC.ordinal()] = 21;
            $EnumSwitchMapping$4[WEnumChatFormatting.RESET.ordinal()] = 22;
            $EnumSwitchMapping$5 = new int[IWorldSettings.WGameType.values().length];
            $EnumSwitchMapping$5[IWorldSettings.WGameType.NOT_SET.ordinal()] = 1;
            $EnumSwitchMapping$5[IWorldSettings.WGameType.SURVIVAL.ordinal()] = 2;
            $EnumSwitchMapping$5[IWorldSettings.WGameType.CREATIVE.ordinal()] = 3;
            $EnumSwitchMapping$5[IWorldSettings.WGameType.ADVENTUR.ordinal()] = 4;
            $EnumSwitchMapping$5[IWorldSettings.WGameType.SPECTATOR.ordinal()] = 5;
            $EnumSwitchMapping$6 = new int[GameType.values().length];
            $EnumSwitchMapping$6[GameType.NOT_SET.ordinal()] = 1;
            $EnumSwitchMapping$6[GameType.SURVIVAL.ordinal()] = 2;
            $EnumSwitchMapping$6[GameType.CREATIVE.ordinal()] = 3;
            $EnumSwitchMapping$6[GameType.ADVENTURE.ordinal()] = 4;
            $EnumSwitchMapping$6[GameType.SPECTATOR.ordinal()] = 5;
            $EnumSwitchMapping$7 = new int[CPacketUseEntity.Action.values().length];
            $EnumSwitchMapping$7[CPacketUseEntity.Action.INTERACT.ordinal()] = 1;
            $EnumSwitchMapping$7[CPacketUseEntity.Action.ATTACK.ordinal()] = 2;
            $EnumSwitchMapping$7[CPacketUseEntity.Action.INTERACT_AT.ordinal()] = 3;
            $EnumSwitchMapping$8 = new int[ICPacketUseEntity.WAction.values().length];
            $EnumSwitchMapping$8[ICPacketUseEntity.WAction.INTERACT.ordinal()] = 1;
            $EnumSwitchMapping$8[ICPacketUseEntity.WAction.ATTACK.ordinal()] = 2;
            $EnumSwitchMapping$8[ICPacketUseEntity.WAction.INTERACT_AT.ordinal()] = 3;
            $EnumSwitchMapping$9 = new int[CPacketPlayerDigging.Action.values().length];
            $EnumSwitchMapping$9[CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK.ordinal()] = 1;
            $EnumSwitchMapping$9[CPacketPlayerDigging.Action.DROP_ALL_ITEMS.ordinal()] = 2;
            $EnumSwitchMapping$9[CPacketPlayerDigging.Action.DROP_ITEM.ordinal()] = 3;
            $EnumSwitchMapping$9[CPacketPlayerDigging.Action.RELEASE_USE_ITEM.ordinal()] = 4;
            $EnumSwitchMapping$9[CPacketPlayerDigging.Action.START_DESTROY_BLOCK.ordinal()] = 5;
            $EnumSwitchMapping$9[CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK.ordinal()] = 6;
            $EnumSwitchMapping$9[CPacketPlayerDigging.Action.SWAP_HELD_ITEMS.ordinal()] = 7;
            $EnumSwitchMapping$10 = new int[ICPacketPlayerDigging.WAction.values().length];
            $EnumSwitchMapping$10[ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK.ordinal()] = 1;
            $EnumSwitchMapping$10[ICPacketPlayerDigging.WAction.ABORT_DESTROY_BLOCK.ordinal()] = 2;
            $EnumSwitchMapping$10[ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK.ordinal()] = 3;
            $EnumSwitchMapping$10[ICPacketPlayerDigging.WAction.DROP_ALL_ITEMS.ordinal()] = 4;
            $EnumSwitchMapping$10[ICPacketPlayerDigging.WAction.DROP_ITEM.ordinal()] = 5;
            $EnumSwitchMapping$10[ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM.ordinal()] = 6;
            $EnumSwitchMapping$10[ICPacketPlayerDigging.WAction.SWAP_HELD_ITEMS.ordinal()] = 7;
            $EnumSwitchMapping$11 = new int[IClickEvent.WAction.values().length];
            $EnumSwitchMapping$11[IClickEvent.WAction.OPEN_URL.ordinal()] = 1;
            $EnumSwitchMapping$12 = new int[ICPacketClientStatus.WEnumState.values().length];
            $EnumSwitchMapping$12[ICPacketClientStatus.WEnumState.PERFORM_RESPAWN.ordinal()] = 1;
            $EnumSwitchMapping$12[ICPacketClientStatus.WEnumState.REQUEST_STATS.ordinal()] = 2;
            $EnumSwitchMapping$12[ICPacketClientStatus.WEnumState.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
            $EnumSwitchMapping$13 = new int[ICPacketResourcePackStatus.WAction.values().length];
            $EnumSwitchMapping$13[ICPacketResourcePackStatus.WAction.SUCCESSFULLY_LOADED.ordinal()] = 1;
            $EnumSwitchMapping$13[ICPacketResourcePackStatus.WAction.DECLINED.ordinal()] = 2;
            $EnumSwitchMapping$13[ICPacketResourcePackStatus.WAction.FAILED_DOWNLOAD.ordinal()] = 3;
            $EnumSwitchMapping$13[ICPacketResourcePackStatus.WAction.ACCEPTED.ordinal()] = 4;
            $EnumSwitchMapping$14 = new int[CPacketEntityAction.Action.values().length];
            $EnumSwitchMapping$14[CPacketEntityAction.Action.START_SNEAKING.ordinal()] = 1;
            $EnumSwitchMapping$14[CPacketEntityAction.Action.STOP_SNEAKING.ordinal()] = 2;
            $EnumSwitchMapping$14[CPacketEntityAction.Action.STOP_SLEEPING.ordinal()] = 3;
            $EnumSwitchMapping$14[CPacketEntityAction.Action.START_SPRINTING.ordinal()] = 4;
            $EnumSwitchMapping$14[CPacketEntityAction.Action.STOP_SPRINTING.ordinal()] = 5;
            $EnumSwitchMapping$14[CPacketEntityAction.Action.OPEN_INVENTORY.ordinal()] = 6;
            $EnumSwitchMapping$14[CPacketEntityAction.Action.START_RIDING_JUMP.ordinal()] = 7;
            $EnumSwitchMapping$14[CPacketEntityAction.Action.STOP_RIDING_JUMP.ordinal()] = 8;
            $EnumSwitchMapping$14[CPacketEntityAction.Action.START_FALL_FLYING.ordinal()] = 9;
            $EnumSwitchMapping$15 = new int[ICPacketEntityAction.WAction.values().length];
            $EnumSwitchMapping$15[ICPacketEntityAction.WAction.START_SNEAKING.ordinal()] = 1;
            $EnumSwitchMapping$15[ICPacketEntityAction.WAction.STOP_SNEAKING.ordinal()] = 2;
            $EnumSwitchMapping$15[ICPacketEntityAction.WAction.STOP_SLEEPING.ordinal()] = 3;
            $EnumSwitchMapping$15[ICPacketEntityAction.WAction.START_SPRINTING.ordinal()] = 4;
            $EnumSwitchMapping$15[ICPacketEntityAction.WAction.STOP_SPRINTING.ordinal()] = 5;
            $EnumSwitchMapping$15[ICPacketEntityAction.WAction.OPEN_INVENTORY.ordinal()] = 6;
            $EnumSwitchMapping$15[ICPacketEntityAction.WAction.START_RIDING_JUMP.ordinal()] = 7;
            $EnumSwitchMapping$15[ICPacketEntityAction.WAction.STOP_RIDING_JUMP.ordinal()] = 8;
            $EnumSwitchMapping$15[ICPacketEntityAction.WAction.START_FALL_FLYING.ordinal()] = 9;
            $EnumSwitchMapping$16 = new int[ClickType.values().length];
            $EnumSwitchMapping$16[ClickType.PICKUP.ordinal()] = 1;
            $EnumSwitchMapping$16[ClickType.QUICK_MOVE.ordinal()] = 2;
            $EnumSwitchMapping$16[ClickType.SWAP.ordinal()] = 3;
            $EnumSwitchMapping$16[ClickType.CLONE.ordinal()] = 4;
            $EnumSwitchMapping$16[ClickType.THROW.ordinal()] = 5;
            $EnumSwitchMapping$16[ClickType.QUICK_CRAFT.ordinal()] = 6;
            $EnumSwitchMapping$16[ClickType.PICKUP_ALL.ordinal()] = 7;
            $EnumSwitchMapping$17 = new int[WEnumHand.values().length];
            $EnumSwitchMapping$17[WEnumHand.MAIN_HAND.ordinal()] = 1;
            $EnumSwitchMapping$17[WEnumHand.OFF_HAND.ordinal()] = 2;
        }
    }

    @NotNull
    public static final Vec3d unwrap(@NotNull WVec3 unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        return new Vec3d(unwrap.getXCoord(), unwrap.getYCoord(), unwrap.getZCoord());
    }

    @NotNull
    public static final Vec3i unwrap(@NotNull WVec3i unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        return new Vec3i(unwrap.getX(), unwrap.getY(), unwrap.getZ());
    }

    @NotNull
    public static final BlockPos unwrap(@NotNull WBlockPos unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        return new BlockPos(unwrap.getX(), unwrap.getY(), unwrap.getZ());
    }

    @NotNull
    public static final WBlockPos wrap(@NotNull BlockPos wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        return new WBlockPos(wrap.func_177958_n(), wrap.func_177956_o(), wrap.func_177952_p());
    }

    @NotNull
    public static final WVec3 wrap(@NotNull Vec3d wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        return new WVec3(wrap.field_72450_a, wrap.field_72448_b, wrap.field_72449_c);
    }

    @NotNull
    public static final WVec3i wrap(@NotNull Vec3i wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        return new WVec3i(wrap.func_177958_n(), wrap.func_177956_o(), wrap.func_177952_p());
    }

    @NotNull
    public static final IMovingObjectPosition.WMovingObjectType wrap(@NotNull RayTraceResult.Type wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        switch (WhenMappings.$EnumSwitchMapping$0[wrap.ordinal()]) {
            case 1:
                return IMovingObjectPosition.WMovingObjectType.MISS;
            case 2:
                return IMovingObjectPosition.WMovingObjectType.BLOCK;
            case 3:
                return IMovingObjectPosition.WMovingObjectType.ENTITY;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final EnumPlayerModelParts unwrap(@NotNull WEnumPlayerModelParts unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        switch (WhenMappings.$EnumSwitchMapping$1[unwrap.ordinal()]) {
            case 1:
                return EnumPlayerModelParts.CAPE;
            case 2:
                return EnumPlayerModelParts.JACKET;
            case 3:
                return EnumPlayerModelParts.LEFT_SLEEVE;
            case 4:
                return EnumPlayerModelParts.RIGHT_SLEEVE;
            case 5:
                return EnumPlayerModelParts.LEFT_PANTS_LEG;
            case 6:
                return EnumPlayerModelParts.RIGHT_PANTS_LEG;
            case 7:
                return EnumPlayerModelParts.HAT;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final WEnumPlayerModelParts wrap(@NotNull EnumPlayerModelParts wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        switch (WhenMappings.$EnumSwitchMapping$2[wrap.ordinal()]) {
            case 1:
                return WEnumPlayerModelParts.CAPE;
            case 2:
                return WEnumPlayerModelParts.JACKET;
            case 3:
                return WEnumPlayerModelParts.LEFT_SLEEVE;
            case 4:
                return WEnumPlayerModelParts.RIGHT_SLEEVE;
            case 5:
                return WEnumPlayerModelParts.LEFT_PANTS_LEG;
            case 6:
                return WEnumPlayerModelParts.RIGHT_PANTS_LEG;
            case 7:
                return WEnumPlayerModelParts.HAT;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final WEnumChatFormatting wrap(@NotNull TextFormatting wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        switch (WhenMappings.$EnumSwitchMapping$3[wrap.ordinal()]) {
            case 1:
                return WEnumChatFormatting.BLACK;
            case 2:
                return WEnumChatFormatting.DARK_BLUE;
            case 3:
                return WEnumChatFormatting.DARK_GREEN;
            case 4:
                return WEnumChatFormatting.DARK_AQUA;
            case 5:
                return WEnumChatFormatting.DARK_RED;
            case 6:
                return WEnumChatFormatting.DARK_PURPLE;
            case 7:
                return WEnumChatFormatting.GOLD;
            case 8:
                return WEnumChatFormatting.GRAY;
            case 9:
                return WEnumChatFormatting.DARK_GRAY;
            case 10:
                return WEnumChatFormatting.BLUE;
            case 11:
                return WEnumChatFormatting.GREEN;
            case 12:
                return WEnumChatFormatting.AQUA;
            case CharacterType.ALNUM /* 13 */:
                return WEnumChatFormatting.RED;
            case 14:
                return WEnumChatFormatting.LIGHT_PURPLE;
            case OPCode.EXACTN_IC /* 15 */:
                return WEnumChatFormatting.YELLOW;
            case 16:
                return WEnumChatFormatting.WHITE;
            case OPCode.CCLASS_MB /* 17 */:
                return WEnumChatFormatting.OBFUSCATED;
            case OPCode.CCLASS_MIX /* 18 */:
                return WEnumChatFormatting.BOLD;
            case OPCode.CCLASS_NOT /* 19 */:
                return WEnumChatFormatting.STRIKETHROUGH;
            case 20:
                return WEnumChatFormatting.UNDERLINE;
            case OPCode.CCLASS_MIX_NOT /* 21 */:
                return WEnumChatFormatting.ITALIC;
            case OPCode.CCLASS_NODE /* 22 */:
                return WEnumChatFormatting.RESET;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final TextFormatting unwrap(@NotNull WEnumChatFormatting unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        switch (WhenMappings.$EnumSwitchMapping$4[unwrap.ordinal()]) {
            case 1:
                return TextFormatting.BLACK;
            case 2:
                return TextFormatting.DARK_BLUE;
            case 3:
                return TextFormatting.DARK_GREEN;
            case 4:
                return TextFormatting.DARK_AQUA;
            case 5:
                return TextFormatting.DARK_RED;
            case 6:
                return TextFormatting.DARK_PURPLE;
            case 7:
                return TextFormatting.GOLD;
            case 8:
                return TextFormatting.GRAY;
            case 9:
                return TextFormatting.DARK_GRAY;
            case 10:
                return TextFormatting.BLUE;
            case 11:
                return TextFormatting.GREEN;
            case 12:
                return TextFormatting.AQUA;
            case CharacterType.ALNUM /* 13 */:
                return TextFormatting.RED;
            case 14:
                return TextFormatting.LIGHT_PURPLE;
            case OPCode.EXACTN_IC /* 15 */:
                return TextFormatting.YELLOW;
            case 16:
                return TextFormatting.WHITE;
            case OPCode.CCLASS_MB /* 17 */:
                return TextFormatting.OBFUSCATED;
            case OPCode.CCLASS_MIX /* 18 */:
                return TextFormatting.BOLD;
            case OPCode.CCLASS_NOT /* 19 */:
                return TextFormatting.STRIKETHROUGH;
            case 20:
                return TextFormatting.UNDERLINE;
            case OPCode.CCLASS_MIX_NOT /* 21 */:
                return TextFormatting.ITALIC;
            case OPCode.CCLASS_NODE /* 22 */:
                return TextFormatting.RESET;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final GameType unwrap(@NotNull IWorldSettings.WGameType unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        switch (WhenMappings.$EnumSwitchMapping$5[unwrap.ordinal()]) {
            case 1:
                return GameType.NOT_SET;
            case 2:
                return GameType.SURVIVAL;
            case 3:
                return GameType.CREATIVE;
            case 4:
                return GameType.ADVENTURE;
            case 5:
                return GameType.SPECTATOR;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final IWorldSettings.WGameType wrap(@NotNull GameType wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        switch (WhenMappings.$EnumSwitchMapping$6[wrap.ordinal()]) {
            case 1:
                return IWorldSettings.WGameType.NOT_SET;
            case 2:
                return IWorldSettings.WGameType.SURVIVAL;
            case 3:
                return IWorldSettings.WGameType.CREATIVE;
            case 4:
                return IWorldSettings.WGameType.ADVENTUR;
            case 5:
                return IWorldSettings.WGameType.SPECTATOR;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final ICPacketUseEntity.WAction wrap(@NotNull CPacketUseEntity.Action wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        switch (WhenMappings.$EnumSwitchMapping$7[wrap.ordinal()]) {
            case 1:
                return ICPacketUseEntity.WAction.INTERACT;
            case 2:
                return ICPacketUseEntity.WAction.ATTACK;
            case 3:
                return ICPacketUseEntity.WAction.INTERACT_AT;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final CPacketUseEntity.Action unwrap(@NotNull ICPacketUseEntity.WAction unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        switch (WhenMappings.$EnumSwitchMapping$8[unwrap.ordinal()]) {
            case 1:
                return CPacketUseEntity.Action.INTERACT;
            case 2:
                return CPacketUseEntity.Action.ATTACK;
            case 3:
                return CPacketUseEntity.Action.INTERACT_AT;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final ICPacketPlayerDigging.WAction wrap(@NotNull CPacketPlayerDigging.Action wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        switch (WhenMappings.$EnumSwitchMapping$9[wrap.ordinal()]) {
            case 1:
                return ICPacketPlayerDigging.WAction.ABORT_DESTROY_BLOCK;
            case 2:
                return ICPacketPlayerDigging.WAction.DROP_ALL_ITEMS;
            case 3:
                return ICPacketPlayerDigging.WAction.DROP_ITEM;
            case 4:
                return ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM;
            case 5:
                return ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK;
            case 6:
                return ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK;
            case 7:
                return ICPacketPlayerDigging.WAction.SWAP_HELD_ITEMS;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final CPacketPlayerDigging.Action unwrap(@NotNull ICPacketPlayerDigging.WAction unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        switch (WhenMappings.$EnumSwitchMapping$10[unwrap.ordinal()]) {
            case 1:
                return CPacketPlayerDigging.Action.START_DESTROY_BLOCK;
            case 2:
                return CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK;
            case 3:
                return CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK;
            case 4:
                return CPacketPlayerDigging.Action.DROP_ALL_ITEMS;
            case 5:
                return CPacketPlayerDigging.Action.DROP_ITEM;
            case 6:
                return CPacketPlayerDigging.Action.RELEASE_USE_ITEM;
            case 7:
                return CPacketPlayerDigging.Action.SWAP_HELD_ITEMS;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final ClickEvent.Action unwrap(@NotNull IClickEvent.WAction unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        switch (WhenMappings.$EnumSwitchMapping$11[unwrap.ordinal()]) {
            case 1:
                return ClickEvent.Action.OPEN_URL;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final CPacketClientStatus.State unwrap(@NotNull ICPacketClientStatus.WEnumState unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        switch (WhenMappings.$EnumSwitchMapping$12[unwrap.ordinal()]) {
            case 1:
                return CPacketClientStatus.State.PERFORM_RESPAWN;
            case 2:
                return CPacketClientStatus.State.REQUEST_STATS;
            case 3:
                Backend backend = Backend.INSTANCE;
                throw new NotImplementedError("1.12.2 doesn't support this feature'");
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final CPacketResourcePackStatus.Action unwrap(@NotNull ICPacketResourcePackStatus.WAction unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        switch (WhenMappings.$EnumSwitchMapping$13[unwrap.ordinal()]) {
            case 1:
                return CPacketResourcePackStatus.Action.SUCCESSFULLY_LOADED;
            case 2:
                return CPacketResourcePackStatus.Action.DECLINED;
            case 3:
                return CPacketResourcePackStatus.Action.FAILED_DOWNLOAD;
            case 4:
                return CPacketResourcePackStatus.Action.ACCEPTED;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final ICPacketEntityAction.WAction wrap(@NotNull CPacketEntityAction.Action wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        switch (WhenMappings.$EnumSwitchMapping$14[wrap.ordinal()]) {
            case 1:
                return ICPacketEntityAction.WAction.START_SNEAKING;
            case 2:
                return ICPacketEntityAction.WAction.STOP_SNEAKING;
            case 3:
                return ICPacketEntityAction.WAction.STOP_SLEEPING;
            case 4:
                return ICPacketEntityAction.WAction.START_SPRINTING;
            case 5:
                return ICPacketEntityAction.WAction.STOP_SPRINTING;
            case 6:
                return ICPacketEntityAction.WAction.OPEN_INVENTORY;
            case 7:
                return ICPacketEntityAction.WAction.START_RIDING_JUMP;
            case 8:
                return ICPacketEntityAction.WAction.STOP_RIDING_JUMP;
            case 9:
                return ICPacketEntityAction.WAction.START_FALL_FLYING;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final CPacketEntityAction.Action unwrap(@NotNull ICPacketEntityAction.WAction unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        switch (WhenMappings.$EnumSwitchMapping$15[unwrap.ordinal()]) {
            case 1:
                return CPacketEntityAction.Action.START_SNEAKING;
            case 2:
                return CPacketEntityAction.Action.STOP_SNEAKING;
            case 3:
                return CPacketEntityAction.Action.STOP_SLEEPING;
            case 4:
                return CPacketEntityAction.Action.START_SPRINTING;
            case 5:
                return CPacketEntityAction.Action.STOP_SPRINTING;
            case 6:
                return CPacketEntityAction.Action.OPEN_INVENTORY;
            case 7:
                return CPacketEntityAction.Action.START_RIDING_JUMP;
            case 8:
                return CPacketEntityAction.Action.STOP_RIDING_JUMP;
            case 9:
                return CPacketEntityAction.Action.START_FALL_FLYING;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public static final EntityEquipmentSlot toEntityEquipmentSlot(int i) {
        switch (i) {
            case 0:
                return EntityEquipmentSlot.FEET;
            case 1:
                return EntityEquipmentSlot.LEGS;
            case 2:
                return EntityEquipmentSlot.CHEST;
            case 3:
                return EntityEquipmentSlot.HEAD;
            case 4:
                return EntityEquipmentSlot.MAINHAND;
            case 5:
                return EntityEquipmentSlot.OFFHAND;
            default:
                throw new IllegalArgumentException("Invalid armorType " + i);
        }
    }

    @NotNull
    public static final ClickType toClickType(int i) {
        switch (i) {
            case 0:
                return ClickType.PICKUP;
            case 1:
                return ClickType.QUICK_MOVE;
            case 2:
                return ClickType.SWAP;
            case 3:
                return ClickType.CLONE;
            case 4:
                return ClickType.THROW;
            case 5:
                return ClickType.QUICK_CRAFT;
            case 6:
                return ClickType.PICKUP_ALL;
            default:
                throw new IllegalArgumentException("Invalid mode " + i);
        }
    }

    public static final int toInt(@NotNull ClickType toInt) {
        Intrinsics.checkParameterIsNotNull(toInt, "$this$toInt");
        switch (WhenMappings.$EnumSwitchMapping$16[toInt.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            default:
                throw new IllegalArgumentException("Invalid mode " + toInt);
        }
    }

    @NotNull
    public static final EnumHand unwrap(@NotNull WEnumHand unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        switch (WhenMappings.$EnumSwitchMapping$17[unwrap.ordinal()]) {
            case 1:
                return EnumHand.MAIN_HAND;
            case 2:
                return EnumHand.OFF_HAND;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
