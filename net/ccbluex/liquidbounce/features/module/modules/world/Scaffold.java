package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.StatType;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.client.render.entity.IRenderItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacketByINetHandlerPlayServer;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerBlockPlacement;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.ccbluex.liquidbounce.utils.PlaceRotation;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u00be\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010f\u001a\u00020gJ\b\u0010h\u001a\u00020gH\u0002J\u0010\u0010i\u001a\u00020g2\u0006\u0010j\u001a\u00020\u0013H\u0002J\u0006\u0010k\u001a\u00020lJ\b\u0010m\u001a\u00020gH\u0002J\b\u0010n\u001a\u00020gH\u0016J\b\u0010o\u001a\u00020gH\u0016J\u0010\u0010p\u001a\u00020g2\u0006\u0010q\u001a\u00020rH\u0007J\u0010\u0010s\u001a\u00020g2\u0006\u0010q\u001a\u00020tH\u0007J\u0010\u0010u\u001a\u00020g2\u0006\u0010q\u001a\u00020vH\u0007J\u0010\u0010w\u001a\u00020g2\u0006\u0010q\u001a\u00020xH\u0007J\u0012\u0010y\u001a\u00020g2\b\u0010q\u001a\u0004\u0018\u00010zH\u0007J\u0012\u0010{\u001a\u00020g2\b\u0010q\u001a\u0004\u0018\u00010|H\u0007J\u0010\u0010}\u001a\u00020g2\u0006\u0010q\u001a\u00020~H\u0007J\b\u0010\u007f\u001a\u00020gH\u0002J\u0010\u0010\u0080\u0001\u001a\u00020l2\u0007\u0010\u0081\u0001\u001a\u00020lJ\u001b\u0010\u0082\u0001\u001a\u00020\u00132\u0007\u0010\u0083\u0001\u001a\u0002032\u0007\u0010\u0084\u0001\u001a\u00020\u0013H\u0002J\t\u0010\u0085\u0001\u001a\u00020gH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\r\u001a\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0014\u001a\u00020\u00138F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010 \u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010!\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\"\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010#\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010$\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010%\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010&\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010'\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010(\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010)\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010*\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010+\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010,\u001a\u00020-X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010.\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010/\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00100\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00101\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u00104\u001a\u0004\u0018\u000105X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00106\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00107\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00108\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00109\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010:\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010;\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010<\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010=\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010>\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010?\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010@\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010A\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010B\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010C\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010D\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010E\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010F\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010G\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010H\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010I\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010J\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010K\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010L\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010M\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010N\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010O\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010P\u001a\u00020Q8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bR\u0010SR\u0010\u0010T\u001a\u0004\u0018\u00010UX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010V\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010W\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010X\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010Y\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010Z\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010[\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\\\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010]\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010^\u001a\u00020_X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010`\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010a\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010c\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010d\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010e\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0086\u0001"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Scaffold;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "a", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "aacYawValue", "afterPlaceC08", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerBlockPlacement;", "autoBlockValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "b", "barrier", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "blocksAmount", "", "getBlocksAmount", "()I", "blocksToEagleValue", "canSameY", "", "canSprint", "getCanSprint", "()Z", "clickDelay", "", "clickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "constantMotionJumpGroundValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "constantMotionValue", "counterDisplayValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "delay", "delayTimer", "downValue", "eagleSneaking", "eagleValue", "expandLengthValue", "extraClickMaxDelayValue", "extraClickMinDelayValue", "extraClickValue", "g", "hitableCheck", "jumpDelayValue", "jumpGround", "", "jumpMotionValue", "keepLengthValue", "lastGroundY", "lastPlace", "lastPlaceBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "lockRotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "markValue", "maxDelayValue", "maxRotationSpeedValue", "minDelayValue", "minRotationSpeedValue", "motionspeed", "motionspeedstatus", "outline", "placeModeValue", "placeableDelay", "placedBlocksWithoutEagle", "plusMaxMotionValue", "plusMotionValue", "r", "rotationsValue", "safeWalkValue", "sameYValue", "searchValue", "shouldGoDown", "silentRotationValue", "slot", "speedModifierValue", "sprintValue", "stableMotionValue", "stopWhenBlockAbove", "swingValue", "tag", "", "getTag", "()Ljava/lang/String;", "targetPlace", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "teleportDelayValue", "teleportGroundValue", "teleportHeightValue", "teleportNoMotionValue", "timerValue", "towerActiveValue", "towerModeValue", "towerStatus", "towerTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "towerTimerValue", "zitterDirection", "zitterModeValue", "zitterSpeed", "zitterStrength", "zitterTimer", "drawTip", "", "fakeJump", "findBlock", "expand", "getSpeed", "", "move", "onDisable", "onEnable", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "place", "roundYaw", "rYaw", "search", "blockPosition", "checks", "update", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Scaffold", description = "Scaffold", category = ModuleCategory.WORLD, keyBind = OPCode.ANYCHAR)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/Scaffold.class */
public final class Scaffold extends Module {
    private final IntegerValue maxDelayValue;
    private final IntegerValue minDelayValue;
    private final IntegerValue minRotationSpeedValue;
    private final IntegerValue maxRotationSpeedValue;
    private final IntegerValue extraClickMaxDelayValue;
    private final IntegerValue extraClickMinDelayValue;
    private PlaceInfo targetPlace;
    private int lastGroundY;
    private Rotation lockRotation;
    private int slot;
    private boolean zitterDirection;
    private long delay;
    private long clickDelay;
    private int lastPlace;
    private int placedBlocksWithoutEagle;
    private boolean eagleSneaking;
    private boolean shouldGoDown;
    private double jumpGround;
    private boolean towerStatus;
    private boolean canSameY;
    private WBlockPos lastPlaceBlock;
    private ICPacketPlayerBlockPlacement afterPlaceC08;
    private final IItemStack barrier;
    private final ListValue placeableDelay = new ListValue("PlaceableDelay", new String[]{"Normal", "Smart", "Off"}, "Normal");
    private final ListValue autoBlockValue = new ListValue("AutoBlock", new String[]{"Spoof", "LiteSpoof", "Switch", "OFF"}, "LiteSpoof");
    private final ListValue sprintValue = new ListValue("Sprint", new String[]{"Always", "Dynamic", "OnGround", "OffGround", "OFF"}, "Always");
    private final ListValue swingValue = new ListValue("Swing", new String[]{"Normal", "Packet", "None"}, "Normal");
    private final BoolValue searchValue = new BoolValue("Search", true);
    private final BoolValue downValue = new BoolValue("Down", true);
    private final ListValue placeModeValue = new ListValue("PlaceTiming", new String[]{"Pre", "Post"}, "Post");
    private final ListValue eagleValue = new ListValue("Eagle", new String[]{"Slient", "Normal", "OFF"}, "OFF");
    private final IntegerValue blocksToEagleValue = new IntegerValue("BlocksToEagle", 0, 0, 10);
    private final IntegerValue expandLengthValue = new IntegerValue("ExpandLength", 5, 1, 6);
    private final ListValue rotationsValue = new ListValue("Rotations", new String[]{"None", "Vanilla", "AAC", "Test1", "Test2"}, "AAC");
    private final IntegerValue aacYawValue = new IntegerValue("AACYawOffset", 0, 0, 90);
    private final BoolValue silentRotationValue = new BoolValue("SilentRotation", true);
    private final IntegerValue keepLengthValue = new IntegerValue("KeepRotationTick", 0, 0, 20);
    private final ListValue zitterModeValue = new ListValue("ZitterMode", new String[]{"Teleport", "Smooth", "OFF"}, "OFF");
    private final FloatValue zitterSpeed = new FloatValue("ZitterSpeed", 0.13f, 0.1f, 0.3f);
    private final FloatValue zitterStrength = new FloatValue("ZitterStrength", 0.072f, 0.05f, 0.2f);
    private final FloatValue timerValue = new FloatValue("Timer", 1.0f, 0.1f, 5.0f);
    private final BoolValue motionspeedstatus = new BoolValue("MotionSpeedSet", false);
    private final FloatValue motionspeed = new FloatValue("MotionSpeedValue", 0.1f, 0.05f, 1.0f);
    private final FloatValue speedModifierValue = new FloatValue("SpeedModifier", 1.0f, 0.0f, 2.0f);
    private final ListValue towerModeValue = new ListValue("TowerMode", new String[]{"None", "Jump", "Motion", "ConstantMotion", "PlusMotion", "StableMotion", "MotionTP", "Packet", "Teleport", "AAC3.3.9", "AAC3.6.4", "AAC4.4Constant", "AAC4Jump"}, "None");
    private final BoolValue stopWhenBlockAbove = new BoolValue("StopTowerWhenBlockAbove", true);
    private final ListValue towerActiveValue = new ListValue("TowerActivation", new String[]{"Always", "PressSpace", "NoMove", "OFF"}, "PressSpace");
    private final FloatValue towerTimerValue = new FloatValue("TowerTimer", 1.0f, 0.1f, 5.0f);
    private final ListValue sameYValue = new ListValue("SameY", new String[]{"Simple", "AutoJump", "WhenSpeed", "OFF"}, "WhenSpeed");
    private final ListValue safeWalkValue = new ListValue("SafeWalk", new String[]{"Ground", "Air", "OFF"}, "OFF");
    private final ListValue hitableCheck = new ListValue("HitableCheck", new String[]{"Simple", "Strict", "OFF"}, "Simple");
    private final ListValue extraClickValue = new ListValue("ExtraClick", new String[]{"EmptyC08", "AfterPlace", "OFF"}, "OFF");
    private final FloatValue jumpMotionValue = new FloatValue("TowerJumpMotion", 0.42f, 0.3681289f, 0.79f);
    private final IntegerValue jumpDelayValue = new IntegerValue("TowerJumpDelay", 0, 0, 20);
    private final FloatValue stableMotionValue = new FloatValue("TowerStableMotion", 0.42f, 0.1f, 1.0f);
    private final FloatValue plusMotionValue = new FloatValue("TowerPlusMotion", 0.1f, 0.01f, 0.2f);
    private final FloatValue plusMaxMotionValue = new FloatValue("TowerPlusMaxMotion", 0.8f, 0.1f, 2.0f);
    private final FloatValue constantMotionValue = new FloatValue("TowerConstantMotion", 0.42f, 0.1f, 1.0f);
    private final FloatValue constantMotionJumpGroundValue = new FloatValue("TowerConstantMotionJumpGround", 0.79f, 0.76f, 1.0f);
    private final FloatValue teleportHeightValue = new FloatValue("TowerTeleportHeight", 1.15f, 0.1f, 5.0f);
    private final IntegerValue teleportDelayValue = new IntegerValue("TowerTeleportDelay", 0, 0, 20);
    private final BoolValue teleportGroundValue = new BoolValue("TowerTeleportGround", true);
    private final BoolValue teleportNoMotionValue = new BoolValue("TowerTeleportNoMotion", false);
    private final BoolValue counterDisplayValue = new BoolValue("Counter", true);
    private final BoolValue markValue = new BoolValue("Mark", false);

    /* renamed from: r */
    private final IntegerValue f128r = new IntegerValue("MarkRed", 255, 0, 255);

    /* renamed from: g */
    private final IntegerValue f129g = new IntegerValue("MarkGreen", 255, 0, 255);

    /* renamed from: b */
    private final IntegerValue f130b = new IntegerValue("MarkBlue", 255, 0, 255);

    /* renamed from: a */
    private final IntegerValue f131a = new IntegerValue("MarkAlpha", 255, 0, 255);
    private final BoolValue outline = new BoolValue("MarkOutline", false);
    private final MSTimer delayTimer = new MSTimer();
    private final MSTimer zitterTimer = new MSTimer();
    private final MSTimer clickTimer = new MSTimer();
    private final TickTimer towerTimer = new TickTimer();

    public Scaffold() {
        final String str = "MaxDelay";
        final int i = 0;
        final int i2 = 0;
        final int i3 = 1000;
        this.maxDelayValue = new IntegerValue(this, str, i, i2, i3) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold$maxDelayValue$1
            final Scaffold this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i4, int i5) {
                int iIntValue = ((Number) this.this$0.minDelayValue.get()).intValue();
                if (iIntValue > i5) {
                    set((Object) Integer.valueOf(iIntValue));
                }
            }
        };
        final String str2 = "MinDelay";
        final int i4 = 0;
        final int i5 = 0;
        final int i6 = 1000;
        this.minDelayValue = new IntegerValue(this, str2, i4, i5, i6) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold$minDelayValue$1
            final Scaffold this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i7, int i8) {
                int iIntValue = ((Number) this.this$0.maxDelayValue.get()).intValue();
                if (iIntValue < i8) {
                    set((Object) Integer.valueOf(iIntValue));
                }
            }
        };
        final String str3 = "MinRotationSpeed";
        final int i7 = 180;
        final int i8 = 0;
        final int i9 = 180;
        this.minRotationSpeedValue = new IntegerValue(this, str3, i7, i8, i9) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold$minRotationSpeedValue$1
            final Scaffold this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i10, int i11) {
                int iIntValue = ((Number) this.this$0.maxRotationSpeedValue.get()).intValue();
                if (iIntValue < i11) {
                    set((Object) Integer.valueOf(iIntValue));
                }
            }
        };
        final String str4 = "MaxRotationSpeed";
        final int i10 = 180;
        final int i11 = 0;
        final int i12 = 180;
        this.maxRotationSpeedValue = new IntegerValue(this, str4, i10, i11, i12) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold$maxRotationSpeedValue$1
            final Scaffold this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i13, int i14) {
                int iIntValue = ((Number) this.this$0.minRotationSpeedValue.get()).intValue();
                if (iIntValue > i14) {
                    set((Object) Integer.valueOf(iIntValue));
                }
            }
        };
        final String str5 = "ExtraClickMaxDelay";
        final int i13 = 100;
        final int i14 = 20;
        final int i15 = 300;
        this.extraClickMaxDelayValue = new IntegerValue(this, str5, i13, i14, i15) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold$extraClickMaxDelayValue$1
            final Scaffold this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i16, int i17) {
                int iIntValue = ((Number) this.this$0.extraClickMinDelayValue.get()).intValue();
                if (iIntValue > i17) {
                    set((Object) Integer.valueOf(iIntValue));
                }
            }
        };
        final String str6 = "ExtraClickMinDelay";
        final int i16 = 50;
        final int i17 = 20;
        final int i18 = 300;
        this.extraClickMinDelayValue = new IntegerValue(this, str6, i16, i17, i18) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold$extraClickMinDelayValue$1
            final Scaffold this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i19, int i20) {
                int iIntValue = ((Number) this.this$0.extraClickMaxDelayValue.get()).intValue();
                if (iIntValue < i20) {
                    set((Object) Integer.valueOf(iIntValue));
                }
            }
        };
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IItem itemById = MinecraftInstance.functions.getItemById(166);
        if (itemById == null) {
            Intrinsics.throwNpe();
        }
        this.barrier = iClassProvider.createItemStack(itemById, 0, 0);
    }

    public void onEnable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer == null) {
            return;
        }
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        this.lastGroundY = (int) thePlayer2.getPosY();
        this.lastPlace = 2;
        this.clickDelay = TimeUtils.randomDelay(((Number) this.extraClickMinDelayValue.get()).intValue(), ((Number) this.extraClickMaxDelayValue.get()).intValue());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01bf  */
    /* JADX WARN: Type inference failed for: r0v118, types: [net.ccbluex.liquidbounce.features.module.modules.world.Scaffold$onUpdate$1] */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.towerStatus) {
            String str = (String) this.towerModeValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            Intrinsics.checkExpressionValueIsNotNull(str.toLowerCase(), "(this as java.lang.String).toLowerCase()");
            if (!Intrinsics.areEqual(r0, "aac3.3.9")) {
                String str2 = (String) this.towerModeValue.get();
                if (str2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                Intrinsics.checkExpressionValueIsNotNull(str2.toLowerCase(), "(this as java.lang.String).toLowerCase()");
                if (!Intrinsics.areEqual(r0, "aac4.4constant")) {
                    String str3 = (String) this.towerModeValue.get();
                    if (str3 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    Intrinsics.checkExpressionValueIsNotNull(str3.toLowerCase(), "(this as java.lang.String).toLowerCase()");
                    if (!Intrinsics.areEqual(r0, "aac4jump")) {
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(((Number) this.towerTimerValue.get()).floatValue());
                    }
                }
            }
        }
        if (!this.towerStatus) {
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(((Number) this.timerValue.get()).floatValue());
        }
        if (!this.towerStatus) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.isCollidedHorizontally()) {
                this.canSameY = false;
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                this.lastGroundY = (int) thePlayer2.getPosY();
            } else {
                String str4 = (String) this.sameYValue.get();
                if (str4 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str4.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -1067628211:
                        if (!lowerCase.equals("whenspeed")) {
                            this.canSameY = false;
                            break;
                        } else {
                            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
                            if (module == null) {
                                Intrinsics.throwNpe();
                            }
                            this.canSameY = module.getState();
                            break;
                        }
                    case -902286926:
                        if (lowerCase.equals("simple")) {
                            this.canSameY = true;
                            break;
                        }
                        break;
                    case 1439392349:
                        if (lowerCase.equals("autojump")) {
                            this.canSameY = true;
                            if (MovementUtils.isMoving()) {
                                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer3 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer3.jump();
                                break;
                            }
                        }
                        break;
                }
                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer4 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer4.getOnGround()) {
                    IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer5 == null) {
                        Intrinsics.throwNpe();
                    }
                    this.lastGroundY = (int) thePlayer5.getPosY();
                }
            }
        }
        if (this.clickTimer.hasTimePassed(this.clickDelay)) {
            ?? r0 = new Function1(this) { // from class: net.ccbluex.liquidbounce.features.module.modules.world.Scaffold.onUpdate.1
                final Scaffold this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                public Object invoke(Object obj) {
                    invoke((ICPacketPlayerBlockPlacement) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(@NotNull ICPacketPlayerBlockPlacement c08) {
                    Intrinsics.checkParameterIsNotNull(c08, "c08");
                    if (this.this$0.clickDelay < 35) {
                        PacketUtils.sendPacketNoEvent((IPacketByINetHandlerPlayServer) c08);
                    }
                    if (this.this$0.clickDelay < 50) {
                        PacketUtils.sendPacketNoEvent((IPacketByINetHandlerPlayServer) c08);
                    }
                    PacketUtils.sendPacketNoEvent((IPacketByINetHandlerPlayServer) c08);
                }
            };
            String str5 = (String) this.extraClickValue.get();
            if (str5 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase2 = str5.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase2.hashCode()) {
                case 1201132862:
                    if (lowerCase2.equals("emptyc08")) {
                        IClassProvider iClassProvider = MinecraftInstance.classProvider;
                        IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer6 == null) {
                            Intrinsics.throwNpe();
                        }
                        r0.invoke(iClassProvider.createCPacketPlayerBlockPlacement(thePlayer6.getInventory().getStackInSlot(this.slot)));
                        break;
                    }
                    break;
                case 1557863595:
                    if (lowerCase2.equals("afterplace") && this.afterPlaceC08 != null) {
                        IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer7 == null) {
                            Intrinsics.throwNpe();
                        }
                        WBlockPos wBlockPos = this.lastPlaceBlock;
                        if (wBlockPos == null) {
                            Intrinsics.throwNpe();
                        }
                        if (thePlayer7.getDistanceSqToCenter(wBlockPos) >= 10.0d) {
                            this.afterPlaceC08 = (ICPacketPlayerBlockPlacement) null;
                            break;
                        } else {
                            ICPacketPlayerBlockPlacement iCPacketPlayerBlockPlacement = this.afterPlaceC08;
                            if (iCPacketPlayerBlockPlacement == null) {
                                Intrinsics.throwNpe();
                            }
                            r0.invoke(iCPacketPlayerBlockPlacement);
                            break;
                        }
                    }
                    break;
            }
            this.clickDelay = TimeUtils.randomDelay(((Number) this.extraClickMinDelayValue.get()).intValue(), ((Number) this.extraClickMaxDelayValue.get()).intValue());
            this.clickTimer.reset();
        }
        IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer8 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer8.setSprinting(getCanSprint());
        this.shouldGoDown = ((Boolean) this.downValue.get()).booleanValue() && MinecraftInstance.f157mc.getGameSettings().isKeyDown(MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak()) && getBlocksAmount() > 1;
        if (this.shouldGoDown) {
            MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().setPressed(false);
        }
        IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer9 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer9.getOnGround()) {
            if (StringsKt.equals((String) this.zitterModeValue.get(), "smooth", true)) {
                if (!MinecraftInstance.f157mc.getGameSettings().isKeyDown(MinecraftInstance.f157mc.getGameSettings().getKeyBindRight())) {
                    MinecraftInstance.f157mc.getGameSettings().getKeyBindRight().setPressed(false);
                }
                if (!MinecraftInstance.f157mc.getGameSettings().isKeyDown(MinecraftInstance.f157mc.getGameSettings().getKeyBindLeft())) {
                    MinecraftInstance.f157mc.getGameSettings().getKeyBindLeft().setPressed(false);
                }
                if (this.zitterTimer.hasTimePassed(100L)) {
                    this.zitterDirection = !this.zitterDirection;
                    this.zitterTimer.reset();
                }
                if (this.zitterDirection) {
                    MinecraftInstance.f157mc.getGameSettings().getKeyBindRight().setPressed(true);
                    MinecraftInstance.f157mc.getGameSettings().getKeyBindLeft().setPressed(false);
                } else {
                    MinecraftInstance.f157mc.getGameSettings().getKeyBindRight().setPressed(false);
                    MinecraftInstance.f157mc.getGameSettings().getKeyBindLeft().setPressed(true);
                }
            }
            if (!StringsKt.equals((String) this.eagleValue.get(), "off", true) && !this.shouldGoDown) {
                if (this.placedBlocksWithoutEagle >= ((Number) this.blocksToEagleValue.get()).intValue()) {
                    IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                    if (theWorld == null) {
                        Intrinsics.throwNpe();
                    }
                    IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer10 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posX = thePlayer10.getPosX();
                    IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer11 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posY = thePlayer11.getPosY() - 1.0d;
                    IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer12 == null) {
                        Intrinsics.throwNpe();
                    }
                    boolean z = theWorld.getBlockState(new WBlockPos(posX, posY, thePlayer12.getPosZ())).getBlock() == MinecraftInstance.classProvider.getBlockEnum(BlockType.AIR);
                    if (StringsKt.equals((String) this.eagleValue.get(), "slient", true)) {
                        if (this.eagleSneaking != z) {
                            IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                            IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                            IEntityPlayerSP thePlayer13 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer13 == null) {
                                Intrinsics.throwNpe();
                            }
                            netHandler.addToSendQueue(iClassProvider2.createCPacketEntityAction(thePlayer13, z ? ICPacketEntityAction.WAction.START_SNEAKING : ICPacketEntityAction.WAction.STOP_SNEAKING));
                        }
                        this.eagleSneaking = z;
                    } else {
                        MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().setPressed(z);
                    }
                    this.placedBlocksWithoutEagle = 0;
                } else {
                    this.placedBlocksWithoutEagle++;
                }
            }
            if (StringsKt.equals((String) this.zitterModeValue.get(), "teleport", true)) {
                MovementUtils.strafe(((Number) this.zitterSpeed.get()).floatValue());
                if (MinecraftInstance.f157mc.getThePlayer() == null) {
                    Intrinsics.throwNpe();
                }
                double radians = Math.toRadians(r0.getRotationYaw() + (this.zitterDirection ? 90.0d : -90.0d));
                IEntityPlayerSP thePlayer14 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer14 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer14.setMotionX(thePlayer14.getMotionX() - (Math.sin(radians) * ((Number) this.zitterStrength.get()).doubleValue()));
                IEntityPlayerSP thePlayer15 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer15 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer15.setMotionZ(thePlayer15.getMotionZ() + (Math.cos(radians) * ((Number) this.zitterStrength.get()).doubleValue()));
                this.zitterDirection = !this.zitterDirection;
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer == null) {
            return;
        }
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isCPacketHeldItemChange(packet)) {
            this.slot = packet.asCPacketHeldItemChange().getSlotId();
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x009a  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMotion(@NotNull MotionEvent event) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(event, "event");
        EventState eventState = event.getEventState();
        this.towerStatus = false;
        if (((Boolean) this.motionspeedstatus.get()).booleanValue()) {
            MovementUtils.INSTANCE.setMotion(((Number) this.motionspeed.get()).floatValue());
        }
        if (((Boolean) this.stopWhenBlockAbove.get()).booleanValue()) {
            IClassProvider iClassProvider = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            double posX = thePlayer.getPosX();
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            double posY = thePlayer2.getPosY() + 2.0d;
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            z = iClassProvider.isBlockAir(BlockUtils.getBlock(new WBlockPos(posX, posY, thePlayer3.getPosZ())));
        }
        this.towerStatus = z;
        if (this.towerStatus) {
            String str = (String) this.towerActiveValue.get();
            if (str != null) {
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -1414557169:
                        if (lowerCase.equals("always")) {
                            this.towerStatus = MinecraftInstance.f157mc.getGameSettings().getKeyBindLeft().isKeyDown() || MinecraftInstance.f157mc.getGameSettings().getKeyBindRight().isKeyDown() || MinecraftInstance.f157mc.getGameSettings().getKeyBindForward().isKeyDown() || MinecraftInstance.f157mc.getGameSettings().getKeyBindBack().isKeyDown();
                            break;
                        }
                        break;
                    case -1039892206:
                        if (lowerCase.equals("nomove")) {
                            this.towerStatus = (MinecraftInstance.f157mc.getGameSettings().getKeyBindLeft().isKeyDown() || MinecraftInstance.f157mc.getGameSettings().getKeyBindRight().isKeyDown() || MinecraftInstance.f157mc.getGameSettings().getKeyBindForward().isKeyDown() || MinecraftInstance.f157mc.getGameSettings().getKeyBindBack().isKeyDown() || !MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) ? false : true;
                            break;
                        }
                        break;
                    case 109935:
                        if (lowerCase.equals("off")) {
                            this.towerStatus = false;
                            break;
                        }
                        break;
                    case 1889828515:
                        if (lowerCase.equals("pressspace")) {
                            this.towerStatus = MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown();
                            break;
                        }
                        break;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
        }
        if (this.towerStatus) {
            move();
        }
        if ((!Intrinsics.areEqual((String) this.rotationsValue.get(), "None")) && ((Number) this.keepLengthValue.get()).intValue() > 0 && this.lockRotation != null && ((Boolean) this.silentRotationValue.get()).booleanValue()) {
            Rotation rotationLimitAngleChange = RotationUtils.limitAngleChange(RotationUtils.serverRotation, this.lockRotation, getSpeed());
            Intrinsics.checkExpressionValueIsNotNull(rotationLimitAngleChange, "RotationUtils.limitAngle\u2026lockRotation, getSpeed())");
            RotationUtils.setTargetRotation(rotationLimitAngleChange, ((Number) this.keepLengthValue.get()).intValue());
        }
        if (event.isPre()) {
            update();
        }
        if (StringsKt.equals((String) this.placeModeValue.get(), eventState.getStateName(), true)) {
            place();
        }
        if (this.targetPlace == null && !StringsKt.equals((String) this.placeableDelay.get(), "off", true)) {
            if (StringsKt.equals((String) this.placeableDelay.get(), "Smart", true)) {
                if (this.lastPlace == 0) {
                    this.delayTimer.reset();
                    return;
                }
                return;
            }
            this.delayTimer.reset();
        }
    }

    private final void fakeJump() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        thePlayer.setAirBorne(true);
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer2.triggerAchievement(MinecraftInstance.classProvider.getStatEnum(StatType.JUMP_STAT));
    }

    private final void move() {
        String str = (String) this.towerModeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1360201941:
                if (lowerCase.equals("teleport")) {
                    if (((Boolean) this.teleportNoMotionValue.get()).booleanValue()) {
                        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer.setMotionY(0.0d);
                    }
                    IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer2 == null) {
                        Intrinsics.throwNpe();
                    }
                    if ((thePlayer2.getOnGround() || !((Boolean) this.teleportGroundValue.get()).booleanValue()) && this.towerTimer.hasTimePassed(((Number) this.teleportDelayValue.get()).intValue())) {
                        fakeJump();
                        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer3 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer4 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX = thePlayer4.getPosX();
                        IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer5 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY = thePlayer5.getPosY() + ((Number) this.teleportHeightValue.get()).doubleValue();
                        IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer6 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer3.setPositionAndUpdate(posX, posY, thePlayer6.getPosZ());
                        this.towerTimer.reset();
                        return;
                    }
                    return;
                }
                return;
            case -1331973455:
                if (lowerCase.equals("stablemotion")) {
                    IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer7 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer7.setMotionY(((Number) this.stableMotionValue.get()).floatValue());
                    return;
                }
                return;
            case -1068318794:
                if (lowerCase.equals("motion")) {
                    IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer8 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer8.getOnGround()) {
                        fakeJump();
                        IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer9 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer9.setMotionY(0.42d);
                        return;
                    }
                    IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer10 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer10.getMotionY() < 0.1d) {
                        IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer11 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer11.setMotionY(-0.3d);
                        return;
                    }
                    return;
                }
                return;
            case -995865464:
                if (lowerCase.equals("packet")) {
                    IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer12 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer12.getOnGround() && this.towerTimer.hasTimePassed(2)) {
                        fakeJump();
                        IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                        IClassProvider iClassProvider = MinecraftInstance.classProvider;
                        IEntityPlayerSP thePlayer13 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer13 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX2 = thePlayer13.getPosX();
                        IEntityPlayerSP thePlayer14 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer14 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY2 = thePlayer14.getPosY() + 0.42d;
                        IEntityPlayerSP thePlayer15 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer15 == null) {
                            Intrinsics.throwNpe();
                        }
                        netHandler.addToSendQueue(iClassProvider.createCPacketPlayerPosition(posX2, posY2, thePlayer15.getPosZ(), false));
                        IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
                        IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                        IEntityPlayerSP thePlayer16 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer16 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX3 = thePlayer16.getPosX();
                        IEntityPlayerSP thePlayer17 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer17 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY3 = thePlayer17.getPosY() + 0.753d;
                        IEntityPlayerSP thePlayer18 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer18 == null) {
                            Intrinsics.throwNpe();
                        }
                        netHandler2.addToSendQueue(iClassProvider2.createCPacketPlayerPosition(posX3, posY3, thePlayer18.getPosZ(), false));
                        IEntityPlayerSP thePlayer19 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer19 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityPlayerSP thePlayer20 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer20 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX4 = thePlayer20.getPosX();
                        IEntityPlayerSP thePlayer21 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer21 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY4 = thePlayer21.getPosY() + 1.0d;
                        IEntityPlayerSP thePlayer22 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer22 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer19.setPosition(posX4, posY4, thePlayer22.getPosZ());
                        this.towerTimer.reset();
                        return;
                    }
                    return;
                }
                return;
            case -157173582:
                if (lowerCase.equals("motiontp")) {
                    IEntityPlayerSP thePlayer23 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer23 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer23.getOnGround()) {
                        fakeJump();
                        IEntityPlayerSP thePlayer24 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer24 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer24.setMotionY(0.42d);
                        return;
                    }
                    IEntityPlayerSP thePlayer25 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer25 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer25.getMotionY() < 0.23d) {
                        IEntityPlayerSP thePlayer26 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer26 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityPlayerSP thePlayer27 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer27 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX5 = thePlayer27.getPosX();
                        IEntityPlayerSP thePlayer28 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer28 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY5 = thePlayer28.getPosY();
                        IEntityPlayerSP thePlayer29 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer29 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer26.setPosition(posX5, posY5, thePlayer29.getPosZ());
                        return;
                    }
                    return;
                }
                return;
            case 3273774:
                if (lowerCase.equals("jump")) {
                    IEntityPlayerSP thePlayer30 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer30 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer30.getOnGround() && this.towerTimer.hasTimePassed(((Number) this.jumpDelayValue.get()).intValue())) {
                        fakeJump();
                        IEntityPlayerSP thePlayer31 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer31 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer31.setMotionY(((Number) this.jumpMotionValue.get()).floatValue());
                        this.towerTimer.reset();
                        return;
                    }
                    return;
                }
                return;
            case 3387192:
                if (lowerCase.equals("none")) {
                    IEntityPlayerSP thePlayer32 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer32 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer32.getOnGround()) {
                        fakeJump();
                        IEntityPlayerSP thePlayer33 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer33 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer33.setMotionY(0.42d);
                        return;
                    }
                    return;
                }
                return;
            case 236380795:
                if (lowerCase.equals("aac4.4constant")) {
                    IEntityPlayerSP thePlayer34 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer34 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer34.getOnGround()) {
                        fakeJump();
                        IEntityPlayerSP thePlayer35 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer35 == null) {
                            Intrinsics.throwNpe();
                        }
                        this.jumpGround = thePlayer35.getPosY();
                        IEntityPlayerSP thePlayer36 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer36 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer36.setMotionY(0.42d);
                    }
                    IEntityPlayerSP thePlayer37 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer37 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer37.setMotionX(0.0d);
                    IEntityPlayerSP thePlayer38 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer38 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer38.setMotionZ(-1.0E-8d);
                    IEntityPlayerSP thePlayer39 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer39 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer39.setJumpMovementFactor(0.0f);
                    MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.6f);
                    IEntityPlayerSP thePlayer40 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer40 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer40.getPosY() > this.jumpGround + 0.99d) {
                        fakeJump();
                        IEntityPlayerSP thePlayer41 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer41 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityPlayerSP thePlayer42 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer42 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX6 = thePlayer42.getPosX();
                        IEntityPlayerSP thePlayer43 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer43 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY6 = thePlayer43.getPosY() - 0.001335979112146d;
                        IEntityPlayerSP thePlayer44 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer44 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer41.setPosition(posX6, posY6, thePlayer44.getPosZ());
                        IEntityPlayerSP thePlayer45 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer45 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer45.setMotionY(0.42d);
                        IEntityPlayerSP thePlayer46 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer46 == null) {
                            Intrinsics.throwNpe();
                        }
                        this.jumpGround = thePlayer46.getPosY();
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.75f);
                        return;
                    }
                    return;
                }
                return;
            case 325228192:
                if (lowerCase.equals("aac3.3.9")) {
                    IEntityPlayerSP thePlayer47 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer47 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer47.getOnGround()) {
                        fakeJump();
                        IEntityPlayerSP thePlayer48 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer48 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer48.setMotionY(0.4001d);
                    }
                    MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
                    IEntityPlayerSP thePlayer49 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer49 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer49.getMotionY() < 0.0d) {
                        IEntityPlayerSP thePlayer50 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer50 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer50.setMotionY(thePlayer50.getMotionY() - 9.45E-6d);
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.6f);
                        return;
                    }
                    return;
                }
                return;
            case 325231070:
                if (lowerCase.equals("aac3.6.4")) {
                    IEntityPlayerSP thePlayer51 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer51 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer51.getTicksExisted() % 4 == 1) {
                        IEntityPlayerSP thePlayer52 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer52 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer52.setMotionY(0.4195464d);
                        IEntityPlayerSP thePlayer53 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer53 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityPlayerSP thePlayer54 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer54 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX7 = thePlayer54.getPosX() - 0.035d;
                        IEntityPlayerSP thePlayer55 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer55 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY7 = thePlayer55.getPosY();
                        IEntityPlayerSP thePlayer56 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer56 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer53.setPosition(posX7, posY7, thePlayer56.getPosZ());
                        return;
                    }
                    IEntityPlayerSP thePlayer57 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer57 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer57.getTicksExisted() % 4 == 0) {
                        IEntityPlayerSP thePlayer58 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer58 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer58.setMotionY(-0.5d);
                        IEntityPlayerSP thePlayer59 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer59 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityPlayerSP thePlayer60 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer60 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX8 = thePlayer60.getPosX() + 0.035d;
                        IEntityPlayerSP thePlayer61 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer61 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY8 = thePlayer61.getPosY();
                        IEntityPlayerSP thePlayer62 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer62 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer59.setPosition(posX8, posY8, thePlayer62.getPosZ());
                        return;
                    }
                    return;
                }
                return;
            case 328004607:
                if (lowerCase.equals("aac4jump")) {
                    MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.97f);
                    IEntityPlayerSP thePlayer63 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer63 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer63.getOnGround()) {
                        fakeJump();
                        IEntityPlayerSP thePlayer64 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer64 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer64.setMotionY(0.387565d);
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.05f);
                        return;
                    }
                    return;
                }
                return;
            case 792877146:
                if (lowerCase.equals("constantmotion")) {
                    IEntityPlayerSP thePlayer65 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer65 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer65.getOnGround()) {
                        fakeJump();
                        IEntityPlayerSP thePlayer66 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer66 == null) {
                            Intrinsics.throwNpe();
                        }
                        this.jumpGround = thePlayer66.getPosY();
                        IEntityPlayerSP thePlayer67 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer67 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer67.setMotionY(((Number) this.constantMotionValue.get()).floatValue());
                    }
                    IEntityPlayerSP thePlayer68 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer68 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer68.getPosY() > this.jumpGround + ((Number) this.constantMotionJumpGroundValue.get()).doubleValue()) {
                        fakeJump();
                        IEntityPlayerSP thePlayer69 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer69 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityPlayerSP thePlayer70 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer70 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX9 = thePlayer70.getPosX();
                        IEntityPlayerSP thePlayer71 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer71 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY9 = thePlayer71.getPosY();
                        IEntityPlayerSP thePlayer72 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer72 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer69.setPosition(posX9, posY9, thePlayer72.getPosZ());
                        IEntityPlayerSP thePlayer73 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer73 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer73.setMotionY(((Number) this.constantMotionValue.get()).floatValue());
                        IEntityPlayerSP thePlayer74 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer74 == null) {
                            Intrinsics.throwNpe();
                        }
                        this.jumpGround = thePlayer74.getPosY();
                        return;
                    }
                    return;
                }
                return;
            case 1789473232:
                if (lowerCase.equals("plusmotion")) {
                    IEntityPlayerSP thePlayer75 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer75 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer75.setMotionY(thePlayer75.getMotionY() + ((Number) this.plusMotionValue.get()).doubleValue());
                    IEntityPlayerSP thePlayer76 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer76 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer76.getMotionY() >= ((Number) this.plusMaxMotionValue.get()).doubleValue()) {
                        IEntityPlayerSP thePlayer77 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer77 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer77.setMotionY(((Number) this.plusMaxMotionValue.get()).floatValue());
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0097  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void update() {
        boolean z;
        if (StringsKt.equals((String) this.autoBlockValue.get(), "off", true)) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getHeldItem() != null) {
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                IItemStack heldItem = thePlayer2.getHeldItem();
                if (heldItem == null) {
                    Intrinsics.throwNpe();
                }
                if (iClassProvider.isItemBlock(heldItem.getItem())) {
                    IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer3 == null) {
                        Intrinsics.throwNpe();
                    }
                    IItemStack heldItem2 = thePlayer3.getHeldItem();
                    if (heldItem2 == null) {
                        Intrinsics.throwNpe();
                    }
                    IItem item = heldItem2.getItem();
                    if (item == null) {
                        Intrinsics.throwNpe();
                    }
                    z = InventoryUtils.isBlockListBlock(item.asItemBlock());
                }
            }
        } else {
            z = InventoryUtils.findAutoBlockBlock() == -1;
        }
        if (z) {
            return;
        }
        findBlock(((Number) this.expandLengthValue.get()).intValue() > 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void findBlock(boolean z) {
        WBlockPos wBlockPosDown;
        int i;
        int i2;
        if (this.shouldGoDown) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            double posY = thePlayer.getPosY();
            if (MinecraftInstance.f157mc.getThePlayer() == null) {
                Intrinsics.throwNpe();
            }
            if (posY == ((int) r1.getPosY()) + 0.5d) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                double posX = thePlayer2.getPosX();
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                double posY2 = thePlayer3.getPosY() - 0.6d;
                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer4 == null) {
                    Intrinsics.throwNpe();
                }
                wBlockPosDown = new WBlockPos(posX, posY2, thePlayer4.getPosZ());
            } else {
                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer5 == null) {
                    Intrinsics.throwNpe();
                }
                double posX2 = thePlayer5.getPosX();
                IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer6 == null) {
                    Intrinsics.throwNpe();
                }
                double posY3 = thePlayer6.getPosY() - 0.6d;
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                wBlockPosDown = new WBlockPos(posX2, posY3, thePlayer7.getPosZ()).down();
            }
        } else {
            IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer8 == null) {
                Intrinsics.throwNpe();
            }
            double posY4 = thePlayer8.getPosY();
            if (MinecraftInstance.f157mc.getThePlayer() == null) {
                Intrinsics.throwNpe();
            }
            if (posY4 == ((int) r1.getPosY()) + 0.5d && !this.canSameY) {
                IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer9 == null) {
                    Intrinsics.throwNpe();
                }
                wBlockPosDown = new WBlockPos(thePlayer9);
            } else if (this.canSameY) {
                double d = this.lastGroundY;
                IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer10 == null) {
                    Intrinsics.throwNpe();
                }
                if (d <= thePlayer10.getPosY()) {
                    IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer11 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posX3 = thePlayer11.getPosX();
                    double d2 = this.lastGroundY - 1.0d;
                    IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer12 == null) {
                        Intrinsics.throwNpe();
                    }
                    wBlockPosDown = new WBlockPos(posX3, d2, thePlayer12.getPosZ());
                } else {
                    IEntityPlayerSP thePlayer13 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer13 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posX4 = thePlayer13.getPosX();
                    IEntityPlayerSP thePlayer14 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer14 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posY5 = thePlayer14.getPosY();
                    IEntityPlayerSP thePlayer15 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer15 == null) {
                        Intrinsics.throwNpe();
                    }
                    wBlockPosDown = new WBlockPos(posX4, posY5, thePlayer15.getPosZ()).down();
                }
            }
        }
        WBlockPos wBlockPos = wBlockPosDown;
        if (!z) {
            IMaterial material = BlockUtils.getMaterial(wBlockPos);
            if (!(material != null ? material.isReplaceable() : false)) {
                return;
            }
            if (search(wBlockPos, !this.shouldGoDown)) {
                return;
            }
        }
        if (!z) {
            if (((Boolean) this.searchValue.get()).booleanValue()) {
                for (int i3 = -1; i3 <= 1; i3++) {
                    for (int i4 = -1; i4 <= 1; i4++) {
                        if (search(wBlockPos.add(i3, 0, i4), !this.shouldGoDown)) {
                            return;
                        }
                    }
                }
                return;
            }
            return;
        }
        int iIntValue = ((Number) this.expandLengthValue.get()).intValue();
        for (int i5 = 0; i5 < iIntValue; i5++) {
            IEntityPlayerSP thePlayer16 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer16 == null) {
                Intrinsics.throwNpe();
            }
            if (Intrinsics.areEqual(thePlayer16.getHorizontalFacing(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.WEST))) {
                i = -i5;
            } else {
                IEntityPlayerSP thePlayer17 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer17 == null) {
                    Intrinsics.throwNpe();
                }
                i = Intrinsics.areEqual(thePlayer17.getHorizontalFacing(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.EAST)) ? i5 : 0;
            }
            IEntityPlayerSP thePlayer18 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer18 == null) {
                Intrinsics.throwNpe();
            }
            if (Intrinsics.areEqual(thePlayer18.getHorizontalFacing(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.NORTH))) {
                i2 = -i5;
            } else {
                IEntityPlayerSP thePlayer19 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer19 == null) {
                    Intrinsics.throwNpe();
                }
                i2 = Intrinsics.areEqual(thePlayer19.getHorizontalFacing(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.SOUTH)) ? i5 : 0;
            }
            if (search(wBlockPos.add(i, 0, i2), false)) {
                return;
            }
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0235  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void place() {
        if (this.targetPlace == null) {
            if (!StringsKt.equals((String) this.placeableDelay.get(), "Off", true)) {
                if (this.lastPlace == 0 && StringsKt.equals((String) this.placeableDelay.get(), "Smart", true)) {
                    this.delayTimer.reset();
                }
                if (StringsKt.equals((String) this.placeableDelay.get(), "Normal", true)) {
                    this.delayTimer.reset();
                }
                if (this.lastPlace > 0) {
                    this.lastPlace--;
                    return;
                }
                return;
            }
            return;
        }
        if (!this.delayTimer.hasTimePassed(this.delay)) {
            return;
        }
        if (!this.towerStatus && this.canSameY) {
            int i = this.lastGroundY - 1;
            PlaceInfo placeInfo = this.targetPlace;
            if (placeInfo == null) {
                Intrinsics.throwNpe();
            }
            if (i != ((int) placeInfo.getVec3().getYCoord())) {
                return;
            }
        }
        if (!StringsKt.equals((String) this.rotationsValue.get(), "None", true)) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            IMovingObjectPosition iMovingObjectPositionRayTraceWithServerSideRotation = PlayerExtensionKt.rayTraceWithServerSideRotation(thePlayer, 5.0d);
            String str = (String) this.hitableCheck.get();
            if (str != null) {
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -902286926:
                        if (lowerCase.equals("simple")) {
                            if (iMovingObjectPositionRayTraceWithServerSideRotation == null) {
                                Intrinsics.throwNpe();
                            }
                            WBlockPos blockPos = iMovingObjectPositionRayTraceWithServerSideRotation.getBlockPos();
                            if (blockPos == null) {
                                Intrinsics.throwNpe();
                            }
                            PlaceInfo placeInfo2 = this.targetPlace;
                            if (placeInfo2 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (!blockPos.equals(placeInfo2.getBlockPos())) {
                                return;
                            }
                        }
                        break;
                    case -891986231:
                        if (lowerCase.equals("strict")) {
                            if (iMovingObjectPositionRayTraceWithServerSideRotation == null) {
                                Intrinsics.throwNpe();
                            }
                            WBlockPos blockPos2 = iMovingObjectPositionRayTraceWithServerSideRotation.getBlockPos();
                            if (blockPos2 == null) {
                                Intrinsics.throwNpe();
                            }
                            PlaceInfo placeInfo3 = this.targetPlace;
                            if (placeInfo3 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (!blockPos2.equals(placeInfo3.getBlockPos())) {
                                return;
                            }
                            IEnumFacing sideHit = iMovingObjectPositionRayTraceWithServerSideRotation.getSideHit();
                            if (this.targetPlace == null) {
                                Intrinsics.throwNpe();
                            }
                            if (!Intrinsics.areEqual(sideHit, r1.getEnumFacing())) {
                                return;
                            }
                        }
                        break;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
        }
        boolean zEquals = StringsKt.equals((String) this.sprintValue.get(), "dynamic", true);
        int iFindAutoBlockBlock = -1;
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        IItemStack heldItem = thePlayer2.getHeldItem();
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer3.getHeldItem() != null) {
            IClassProvider iClassProvider = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer4 == null) {
                Intrinsics.throwNpe();
            }
            IItemStack heldItem2 = thePlayer4.getHeldItem();
            if (heldItem2 == null) {
                Intrinsics.throwNpe();
            }
            if (iClassProvider.isItemBlock(heldItem2.getItem())) {
                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer5 == null) {
                    Intrinsics.throwNpe();
                }
                IItemStack heldItem3 = thePlayer5.getHeldItem();
                if (heldItem3 == null) {
                    Intrinsics.throwNpe();
                }
                IItem item = heldItem3.getItem();
                if (item == null) {
                    Intrinsics.throwNpe();
                }
                if (InventoryUtils.isBlockListBlock(item.asItemBlock())) {
                    if (StringsKt.equals((String) this.autoBlockValue.get(), "off", true)) {
                        return;
                    }
                    iFindAutoBlockBlock = InventoryUtils.findAutoBlockBlock();
                    if (iFindAutoBlockBlock == -1) {
                        return;
                    }
                    if (StringsKt.equals((String) this.autoBlockValue.get(), "LiteSpoof", true) || StringsKt.equals((String) this.autoBlockValue.get(), "Spoof", true)) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(iFindAutoBlockBlock - 36));
                    } else {
                        IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer6 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer6.getInventory().setCurrentItem(iFindAutoBlockBlock - 36);
                    }
                    IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer7 == null) {
                        Intrinsics.throwNpe();
                    }
                    heldItem = thePlayer7.getInventoryContainer().getSlot(iFindAutoBlockBlock).getStack();
                }
            }
        }
        if (zEquals) {
            IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
            IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer8 == null) {
                Intrinsics.throwNpe();
            }
            netHandler.addToSendQueue(iClassProvider2.createCPacketEntityAction(thePlayer8, ICPacketEntityAction.WAction.STOP_SPRINTING));
        }
        IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
        IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer9 == null) {
            Intrinsics.throwNpe();
        }
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        IItemStack iItemStack = heldItem;
        PlaceInfo placeInfo4 = this.targetPlace;
        if (placeInfo4 == null) {
            Intrinsics.throwNpe();
        }
        WBlockPos blockPos3 = placeInfo4.getBlockPos();
        PlaceInfo placeInfo5 = this.targetPlace;
        if (placeInfo5 == null) {
            Intrinsics.throwNpe();
        }
        IEnumFacing enumFacing = placeInfo5.getEnumFacing();
        PlaceInfo placeInfo6 = this.targetPlace;
        if (placeInfo6 == null) {
            Intrinsics.throwNpe();
        }
        if (playerController.onPlayerRightClick(thePlayer9, theWorld, iItemStack, blockPos3, enumFacing, placeInfo6.getVec3())) {
            this.delay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
            IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer10 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer10.getOnGround()) {
                float fFloatValue = ((Number) this.speedModifierValue.get()).floatValue();
                IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer11 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer11.setMotionX(thePlayer11.getMotionX() * fFloatValue);
                IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer12 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer12.setMotionZ(thePlayer12.getMotionZ() * fFloatValue);
            }
            String str2 = (String) this.swingValue.get();
            if (StringsKt.equals(str2, "packet", true)) {
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketAnimation());
            } else if (StringsKt.equals(str2, "normal", true)) {
                IEntityPlayerSP thePlayer13 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer13 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer13.swingItem();
            }
            this.lastPlace = 2;
            PlaceInfo placeInfo7 = this.targetPlace;
            if (placeInfo7 == null) {
                Intrinsics.throwNpe();
            }
            WBlockPos blockPos4 = placeInfo7.getBlockPos();
            PlaceInfo placeInfo8 = this.targetPlace;
            if (placeInfo8 == null) {
                Intrinsics.throwNpe();
            }
            this.lastPlaceBlock = blockPos4.add(placeInfo8.getEnumFacing().getDirectionVec());
            String str3 = (String) this.extraClickValue.get();
            if (str3 != null) {
                String lowerCase2 = str3.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase2.hashCode()) {
                    case 1557863595:
                        if (lowerCase2.equals("afterplace")) {
                            PlaceInfo placeInfo9 = this.targetPlace;
                            if (placeInfo9 == null) {
                                Intrinsics.throwNpe();
                            }
                            WBlockPos blockPos5 = placeInfo9.getBlockPos();
                            PlaceInfo placeInfo10 = this.targetPlace;
                            if (placeInfo10 == null) {
                                Intrinsics.throwNpe();
                            }
                            WVec3 vec3 = placeInfo10.getVec3();
                            IClassProvider iClassProvider3 = MinecraftInstance.classProvider;
                            PlaceInfo placeInfo11 = this.targetPlace;
                            if (placeInfo11 == null) {
                                Intrinsics.throwNpe();
                            }
                            WBlockPos blockPos6 = placeInfo11.getBlockPos();
                            PlaceInfo placeInfo12 = this.targetPlace;
                            if (placeInfo12 == null) {
                                Intrinsics.throwNpe();
                            }
                            this.afterPlaceC08 = iClassProvider3.createCPacketPlayerBlockPlacement(blockPos6, placeInfo12.getEnumFacing().getIndex(), heldItem, (float) (vec3.getXCoord() - blockPos5.getX()), (float) (vec3.getYCoord() - blockPos5.getY()), (float) (vec3.getZCoord() - blockPos5.getZ()));
                            break;
                        }
                        break;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
        }
        if (zEquals) {
            IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
            IClassProvider iClassProvider4 = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer14 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer14 == null) {
                Intrinsics.throwNpe();
            }
            netHandler2.addToSendQueue(iClassProvider4.createCPacketEntityAction(thePlayer14, ICPacketEntityAction.WAction.START_SPRINTING));
        }
        if (StringsKt.equals((String) this.autoBlockValue.get(), "LiteSpoof", true) && iFindAutoBlockBlock >= 0) {
            IINetHandlerPlayClient netHandler3 = MinecraftInstance.f157mc.getNetHandler();
            IClassProvider iClassProvider5 = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer15 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer15 == null) {
                Intrinsics.throwNpe();
            }
            netHandler3.addToSendQueue(iClassProvider5.createCPacketHeldItemChange(thePlayer15.getInventory().getCurrentItem()));
        }
        this.targetPlace = (PlaceInfo) null;
    }

    public void onDisable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer == null) {
            return;
        }
        if (!MinecraftInstance.f157mc.getGameSettings().isKeyDown(MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak())) {
            MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().setPressed(false);
            if (this.eagleSneaking) {
                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                netHandler.addToSendQueue(iClassProvider.createCPacketEntityAction(thePlayer2, ICPacketEntityAction.WAction.STOP_SNEAKING));
            }
        }
        if (!MinecraftInstance.f157mc.getGameSettings().isKeyDown(MinecraftInstance.f157mc.getGameSettings().getKeyBindRight())) {
            MinecraftInstance.f157mc.getGameSettings().getKeyBindRight().setPressed(false);
        }
        if (!MinecraftInstance.f157mc.getGameSettings().isKeyDown(MinecraftInstance.f157mc.getGameSettings().getKeyBindLeft())) {
            MinecraftInstance.f157mc.getGameSettings().getKeyBindLeft().setPressed(false);
        }
        this.lockRotation = (Rotation) null;
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        this.shouldGoDown = false;
        RotationUtils.reset();
        int i = this.slot;
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        if (i != thePlayer3.getInventory().getCurrentItem()) {
            IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
            IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer4 == null) {
                Intrinsics.throwNpe();
            }
            netHandler2.addToSendQueue(iClassProvider2.createCPacketHeldItemChange(thePlayer4.getInventory().getCurrentItem()));
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (StringsKt.equals((String) this.safeWalkValue.get(), "off", true) || this.shouldGoDown) {
            return;
        }
        if (!StringsKt.equals((String) this.safeWalkValue.get(), "air", true)) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (!thePlayer.getOnGround()) {
                return;
            }
        }
        event.setSafeWalk(true);
    }

    @EventTarget
    public final void onRender2D(@Nullable Render2DEvent render2DEvent) {
        if (((Boolean) this.counterDisplayValue.get()).booleanValue()) {
            drawTip();
        }
    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent render3DEvent) {
        int i;
        int i2;
        if (((Boolean) this.markValue.get()).booleanValue()) {
            int iIntValue = ((Number) this.expandLengthValue.get()).intValue() + 1;
            for (int i3 = 0; i3 < iIntValue; i3++) {
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                double posX = thePlayer.getPosX();
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                if (Intrinsics.areEqual(thePlayer2.getHorizontalFacing(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.WEST))) {
                    i = -i3;
                } else {
                    IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer3 == null) {
                        Intrinsics.throwNpe();
                    }
                    i = Intrinsics.areEqual(thePlayer3.getHorizontalFacing(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.EAST)) ? i3 : 0;
                }
                double d = posX + i;
                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer4 == null) {
                    Intrinsics.throwNpe();
                }
                double posY = thePlayer4.getPosY();
                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer5 == null) {
                    Intrinsics.throwNpe();
                }
                double posY2 = thePlayer5.getPosY();
                IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer6 == null) {
                    Intrinsics.throwNpe();
                }
                double d2 = (posY - (posY2 == ((double) ((int) thePlayer6.getPosY())) + 0.5d ? 0.0d : 1.0d)) - (this.shouldGoDown ? 1.0d : 0.0d);
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                double posZ = thePlayer7.getPosZ();
                IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer8 == null) {
                    Intrinsics.throwNpe();
                }
                if (Intrinsics.areEqual(thePlayer8.getHorizontalFacing(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.NORTH))) {
                    i2 = -i3;
                } else {
                    IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer9 == null) {
                        Intrinsics.throwNpe();
                    }
                    i2 = Intrinsics.areEqual(thePlayer9.getHorizontalFacing(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.SOUTH)) ? i3 : 0;
                }
                WBlockPos wBlockPos = new WBlockPos(d, d2, posZ + i2);
                PlaceInfo placeInfo = PlaceInfo.Companion.get(wBlockPos);
                IMaterial material = BlockUtils.getMaterial(wBlockPos);
                if ((material != null ? material.isReplaceable() : false) && placeInfo != null) {
                    RenderUtils.drawBlockBox2(wBlockPos, new Color(((Number) this.f128r.get()).intValue(), ((Number) this.f129g.get()).intValue(), ((Number) this.f130b.get()).intValue(), ((Number) this.f131a.get()).intValue()), ((Boolean) this.outline.get()).booleanValue(), true, 1.0f);
                    return;
                }
            }
        }
    }

    public final int getBlocksAmount() {
        int stackSize = 0;
        for (int i = 36; i <= 44; i++) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            IItemStack stack = thePlayer.getInventoryContainer().getSlot(i).getStack();
            if (stack != null && MinecraftInstance.classProvider.isItemBlock(stack.getItem())) {
                stackSize += stack.getStackSize();
            }
        }
        return stackSize;
    }

    public final void drawTip() {
        if (getBlocksAmount() < 100 || getBlocksAmount() < 1000) {
        }
        if (getBlocksAmount() < 10 || getBlocksAmount() < 100) {
        }
        if (getBlocksAmount() < 0 || getBlocksAmount() < 10) {
        }
        int iFindAutoBlockBlock = InventoryUtils.findAutoBlockBlock();
        if (iFindAutoBlockBlock == -1) {
            return;
        }
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        IItemStack stack = thePlayer.getInventoryContainer().getSlot(iFindAutoBlockBlock).getStack();
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IMinecraft mc = MinecraftInstance.f157mc;
        Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
        IScaledResolution iScaledResolutionCreateScaledResolution = iClassProvider.createScaledResolution(mc);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        if (theWorld != null) {
            RenderHelper.func_74520_c();
        }
        GlStateManager.func_179094_E();
        GlStateManager.func_179118_c();
        GlStateManager.func_179086_m(256);
        MinecraftInstance.f157mc.getRenderItem().setZLevel(-150.0f);
        IRenderItem renderItem = MinecraftInstance.f157mc.getRenderItem();
        if (stack == null) {
            Intrinsics.throwNpe();
        }
        renderItem.renderItemAndEffectIntoGUI(stack, (iScaledResolutionCreateScaledResolution.getScaledWidth() / 2) - 20, (iScaledResolutionCreateScaledResolution.getScaledHeight() / 2) + 2);
        IFontRenderer iFontRenderer = Fonts.font35;
        String str = String.valueOf(getBlocksAmount()) + " <- Blocks";
        int scaledWidth = iScaledResolutionCreateScaledResolution.getScaledWidth() / 2;
        int scaledHeight = (iScaledResolutionCreateScaledResolution.getScaledHeight() / 2) + 7;
        Color color = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
        iFontRenderer.drawStringWithShadow(str, scaledWidth, scaledHeight, color.getRGB());
        MinecraftInstance.f157mc.getRenderItem().setZLevel(0.0f);
        GlStateManager.func_179084_k();
        GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
        GlStateManager.func_179097_i();
        GlStateManager.func_179140_f();
        GlStateManager.func_179126_j();
        GlStateManager.func_179152_a(2.0f, 2.0f, 2.0f);
        GlStateManager.func_179141_d();
        GlStateManager.func_179121_F();
        GL11.glPopMatrix();
    }

    public final float getSpeed() {
        return (float) ((Math.random() * (((Number) this.maxRotationSpeedValue.get()).intValue() - ((Number) this.minRotationSpeedValue.get()).intValue())) + ((Number) this.minRotationSpeedValue.get()).doubleValue());
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.towerStatus) {
            event.cancelEvent();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0084 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00be A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean getCanSprint() {
        String str = (String) this.sprintValue.get();
        if (str != null) {
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1414557169:
                    if (lowerCase.equals("always")) {
                        return true;
                    }
                    return false;
                case 2002614198:
                    if (lowerCase.equals("offground")) {
                        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer == null) {
                            Intrinsics.throwNpe();
                        }
                        return !thePlayer.getOnGround();
                    }
                    return false;
                case 2077082662:
                    if (lowerCase.equals("onground")) {
                        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        return thePlayer2.getOnGround();
                    }
                    return false;
                case 2124767295:
                    if (lowerCase.equals("dynamic")) {
                    }
                    break;
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
    }

    @NotNull
    public String getTag() {
        return this.towerStatus ? "Tower" : "Normal";
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final boolean search(WBlockPos wBlockPos, boolean z) {
        IMaterial material = BlockUtils.getMaterial(wBlockPos);
        if (!(material != null ? material.isReplaceable() : false)) {
            return false;
        }
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        double posX = thePlayer.getPosX();
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        double minY = thePlayer2.getEntityBoundingBox().getMinY();
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            Intrinsics.throwNpe();
        }
        double eyeHeight = minY + r4.getEyeHeight();
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        WVec3 wVec3 = new WVec3(posX, eyeHeight, thePlayer3.getPosZ());
        PlaceRotation placeRotation = (PlaceRotation) null;
        for (EnumFacingType enumFacingType : EnumFacingType.values()) {
            IEnumFacing enumFacing = MinecraftInstance.classProvider.getEnumFacing(enumFacingType);
            WBlockPos wBlockPosOffset$default = WBlockPos.offset$default(wBlockPos, enumFacing, 0, 2, null);
            if (BlockUtils.canBeClicked(wBlockPosOffset$default)) {
                WVec3 wVec32 = new WVec3(enumFacing.getDirectionVec());
                double d = 0.1d;
                while (true) {
                    double d2 = d;
                    if (d2 < 0.9d) {
                        double d3 = 0.1d;
                        while (true) {
                            double d4 = d3;
                            if (d4 < 0.9d) {
                                double d5 = 0.1d;
                                while (true) {
                                    double d6 = d5;
                                    if (d6 < 0.9d) {
                                        WVec3 wVec33 = new WVec3(wBlockPos);
                                        WVec3 wVec34 = new WVec3(wVec33.getXCoord() + d2, wVec33.getYCoord() + d4, wVec33.getZCoord() + d6);
                                        double xCoord = wVec34.getXCoord() - wVec3.getXCoord();
                                        double yCoord = wVec34.getYCoord() - wVec3.getYCoord();
                                        double zCoord = wVec34.getZCoord() - wVec3.getZCoord();
                                        double d7 = (xCoord * xCoord) + (yCoord * yCoord) + (zCoord * zCoord);
                                        WVec3 wVec35 = new WVec3(wVec32.getXCoord() * 0.5d, wVec32.getYCoord() * 0.5d, wVec32.getZCoord() * 0.5d);
                                        WVec3 wVec36 = new WVec3(wVec34.getXCoord() + wVec35.getXCoord(), wVec34.getYCoord() + wVec35.getYCoord(), wVec34.getZCoord() + wVec35.getZCoord());
                                        if (z) {
                                            double xCoord2 = wVec36.getXCoord() - wVec3.getXCoord();
                                            double yCoord2 = wVec36.getYCoord() - wVec3.getYCoord();
                                            double zCoord2 = wVec36.getZCoord() - wVec3.getZCoord();
                                            if ((xCoord2 * xCoord2) + (yCoord2 * yCoord2) + (zCoord2 * zCoord2) <= 18.0d) {
                                                WVec3 wVec37 = new WVec3(wVec34.getXCoord() + wVec32.getXCoord(), wVec34.getYCoord() + wVec32.getYCoord(), wVec34.getZCoord() + wVec32.getZCoord());
                                                double xCoord3 = wVec37.getXCoord() - wVec3.getXCoord();
                                                double yCoord3 = wVec37.getYCoord() - wVec3.getYCoord();
                                                double zCoord3 = wVec37.getZCoord() - wVec3.getZCoord();
                                                if (d7 <= (xCoord3 * xCoord3) + (yCoord3 * yCoord3) + (zCoord3 * zCoord3)) {
                                                    IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                                                    if (theWorld == null) {
                                                        Intrinsics.throwNpe();
                                                    }
                                                    if (theWorld.rayTraceBlocks(wVec3, wVec36, false, true, false) != null) {
                                                    }
                                                }
                                            }
                                            d5 = d6 + 0.1d;
                                        }
                                        double xCoord4 = wVec36.getXCoord() - wVec3.getXCoord();
                                        double yCoord4 = wVec36.getYCoord() - wVec3.getYCoord();
                                        double zCoord4 = wVec36.getZCoord() - wVec3.getZCoord();
                                        double dSqrt = Math.sqrt((xCoord4 * xCoord4) + (zCoord4 * zCoord4));
                                        float degrees = (((float) Math.toDegrees(Math.atan2(zCoord4, xCoord4))) - 90.0f) % 360.0f;
                                        if (degrees >= 180.0f) {
                                            degrees -= 360.0f;
                                        }
                                        if (degrees < -180.0f) {
                                            degrees += 360.0f;
                                        }
                                        float f = degrees;
                                        float f2 = ((float) (-Math.toDegrees(Math.atan2(yCoord4, dSqrt)))) % 360.0f;
                                        if (f2 >= 180.0f) {
                                            f2 -= 360.0f;
                                        }
                                        if (f2 < -180.0f) {
                                            f2 += 360.0f;
                                        }
                                        Rotation rotation = new Rotation(f, f2);
                                        WVec3 vectorForRotation = RotationUtils.getVectorForRotation(rotation);
                                        WVec3 wVec38 = new WVec3(wVec3.getXCoord() + (vectorForRotation.getXCoord() * 4.0d), wVec3.getYCoord() + (vectorForRotation.getYCoord() * 4.0d), wVec3.getZCoord() + (vectorForRotation.getZCoord() * 4.0d));
                                        IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                                        if (theWorld2 == null) {
                                            Intrinsics.throwNpe();
                                        }
                                        IMovingObjectPosition iMovingObjectPositionRayTraceBlocks = theWorld2.rayTraceBlocks(wVec3, wVec38, false, false, true);
                                        if (iMovingObjectPositionRayTraceBlocks == null) {
                                            Intrinsics.throwNpe();
                                        }
                                        if (iMovingObjectPositionRayTraceBlocks.getTypeOfHit() != IMovingObjectPosition.WMovingObjectType.BLOCK || !Intrinsics.areEqual(iMovingObjectPositionRayTraceBlocks.getBlockPos(), wBlockPosOffset$default)) {
                                            d5 = d6 + 0.1d;
                                        } else {
                                            if (placeRotation == null || RotationUtils.getRotationDifference(rotation) < RotationUtils.getRotationDifference(placeRotation.getRotation())) {
                                                placeRotation = new PlaceRotation(new PlaceInfo(wBlockPosOffset$default, enumFacing.getOpposite(), wVec36), rotation);
                                            }
                                            d5 = d6 + 0.1d;
                                        }
                                    }
                                }
                                d3 = d4 + 0.1d;
                            }
                        }
                        d = d2 + 0.1d;
                    }
                }
            }
        }
        if (placeRotation == null) {
            return false;
        }
        if (!Intrinsics.areEqual((String) this.rotationsValue.get(), "None")) {
            Rotation rotation2 = (Rotation) null;
            String str = (String) this.rotationsValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case 96323:
                    if (lowerCase.equals("aac")) {
                        if (!this.towerStatus) {
                            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer4 == null) {
                                Intrinsics.throwNpe();
                            }
                            float rotationYaw = thePlayer4.getRotationYaw();
                            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer5 == null) {
                                Intrinsics.throwNpe();
                            }
                            rotation2 = new Rotation(rotationYaw + (thePlayer5.getMovementInput().getMoveForward() < 0.0f ? 0 : 180) + ((Number) this.aacYawValue.get()).floatValue(), placeRotation.getRotation().getPitch());
                            break;
                        } else {
                            rotation2 = placeRotation.getRotation();
                            break;
                        }
                    }
                    break;
                case 110251487:
                    if (lowerCase.equals("test1")) {
                        rotation2 = new Rotation(Math.round(placeRotation.getRotation().getYaw() / 45.0f) * 45, placeRotation.getRotation().getPitch());
                        break;
                    }
                    break;
                case 110251488:
                    if (lowerCase.equals("test2")) {
                        rotation2 = new Rotation(((float) ((MovementUtils.getDirection() * 180.0d) / 3.141592653589793d)) + 135.0f, placeRotation.getRotation().getPitch());
                        break;
                    }
                    break;
                case 233102203:
                    if (lowerCase.equals("vanilla")) {
                        rotation2 = placeRotation.getRotation();
                        break;
                    }
                    break;
            }
            if (rotation2 != null) {
                if (((Boolean) this.silentRotationValue.get()).booleanValue()) {
                    Rotation rotationLimitAngleChange = RotationUtils.limitAngleChange(RotationUtils.serverRotation, rotation2, getSpeed());
                    Intrinsics.checkExpressionValueIsNotNull(rotationLimitAngleChange, "RotationUtils.limitAngle\u2026on, rotation, getSpeed())");
                    RotationUtils.setTargetRotation(rotationLimitAngleChange, ((Number) this.keepLengthValue.get()).intValue());
                } else {
                    IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer6 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer6.setRotationYaw(rotation2.getYaw());
                    IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer7 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer7.setRotationPitch(rotation2.getPitch());
                }
            }
            this.lockRotation = rotation2;
        }
        this.targetPlace = placeRotation.getPlaceInfo();
        return true;
    }
}
