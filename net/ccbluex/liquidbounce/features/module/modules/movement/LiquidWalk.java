package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.MaterialType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdR\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u001aH\u0007J\u0012\u0010\u001b\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u001cH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/LiquidWalk;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aacFlyValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "nextTick", "", "noJumpValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "LiquidWalk", description = "Allows you to walk on water.", category = ModuleCategory.MOVEMENT, keyBind = 36)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/LiquidWalk.class */
public final class LiquidWalk extends Module {

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "NCP", "AAC", "AAC3.3.11", "AACFly", "Spartan", "Dolphin"}, "NCP");
    private final BoolValue noJumpValue = new BoolValue("NoJump", false);
    private final FloatValue aacFlyValue = new FloatValue("AACFlyMotion", 0.5f, 0.1f, 1.0f);
    private boolean nextTick;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\ufffd\ufffd\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0018\u00010\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u00a2\u0006\u0002\b\u0007"}, m27d2 = {"<anonymous>", "", "p1", "", "Lkotlin/ParameterName;", "name", "obj", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk$onBlockBB$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/LiquidWalk$onBlockBB$1.class */
    static final /* synthetic */ class C04201 extends FunctionReference implements Function1 {
        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IClassProvider.class);
        }

        C04201(IClassProvider iClassProvider) {
            super(1, iClassProvider);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return Boolean.valueOf(m1580invoke(obj));
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final boolean m1580invoke(@Nullable Object obj) {
            return ((IClassProvider) this.receiver).isBlockLiquid(obj);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\ufffd\ufffd\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0018\u00010\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u00a2\u0006\u0002\b\u0007"}, m27d2 = {"<anonymous>", "", "p1", "", "Lkotlin/ParameterName;", "name", "obj", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk$onPacket$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/LiquidWalk$onPacket$1.class */
    static final /* synthetic */ class C04211 extends FunctionReference implements Function1 {
        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IClassProvider.class);
        }

        C04211(IClassProvider iClassProvider) {
            super(1, iClassProvider);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return Boolean.valueOf(m1581invoke(obj));
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final boolean m1581invoke(@Nullable Object obj) {
            return ((IClassProvider) this.receiver).isBlockLiquid(obj);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\ufffd\ufffd\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0018\u00010\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u00a2\u0006\u0002\b\u0007"}, m27d2 = {"<anonymous>", "", "p1", "", "Lkotlin/ParameterName;", "name", "obj", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk$onUpdate$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/LiquidWalk$onUpdate$1.class */
    static final /* synthetic */ class C04221 extends FunctionReference implements Function1 {
        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IClassProvider.class);
        }

        C04221(IClassProvider iClassProvider) {
            super(1, iClassProvider);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return Boolean.valueOf(m1582invoke(obj));
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final boolean m1582invoke(@Nullable Object obj) {
            return ((IClassProvider) this.receiver).isBlockLiquid(obj);
        }
    }

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || thePlayer.isSneaking()) {
            return;
        }
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -2011701869:
                if (lowerCase.equals("spartan") && thePlayer.isInWater()) {
                    if (thePlayer.isCollidedHorizontally()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + 0.15d);
                        return;
                    }
                    IBlock block = BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 1.0d, thePlayer.getPosZ()));
                    if (MinecraftInstance.classProvider.isBlockLiquid(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 1.1d, thePlayer.getPosZ())))) {
                        thePlayer.setMotionY(0.1d);
                    } else if (MinecraftInstance.classProvider.isBlockLiquid(block)) {
                        thePlayer.setMotionY(0.0d);
                    }
                    thePlayer.setOnGround(true);
                    thePlayer.setMotionX(thePlayer.getMotionX() * 1.085d);
                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.085d);
                    return;
                }
                return;
            case 96323:
                if (lowerCase.equals("aac")) {
                    WBlockPos wBlockPosDown = thePlayer.getPosition().down();
                    if ((!thePlayer.getOnGround() && Intrinsics.areEqual(BlockUtils.getBlock(wBlockPosDown), MinecraftInstance.classProvider.getBlockEnum(BlockType.WATER))) || thePlayer.isInWater()) {
                        if (!thePlayer.getSprinting()) {
                            thePlayer.setMotionX(thePlayer.getMotionX() * 0.99999d);
                            thePlayer.setMotionY(thePlayer.getMotionY() * 0.0d);
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.99999d);
                            if (thePlayer.isCollidedHorizontally()) {
                                thePlayer.setMotionY(((int) (thePlayer.getPosY() - ((int) (thePlayer.getPosY() - 1.0d)))) / 8.0f);
                            }
                        } else {
                            thePlayer.setMotionX(thePlayer.getMotionX() * 0.99999d);
                            thePlayer.setMotionY(thePlayer.getMotionY() * 0.0d);
                            thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.99999d);
                            if (thePlayer.isCollidedHorizontally()) {
                                thePlayer.setMotionY(((int) (thePlayer.getPosY() - ((int) (thePlayer.getPosY() - 1.0d)))) / 8.0f);
                            }
                        }
                        if (thePlayer.getFallDistance() >= 4.0f) {
                            thePlayer.setMotionY(-0.004d);
                        } else if (thePlayer.isInWater()) {
                            thePlayer.setMotionY(0.09d);
                        }
                    }
                    if (thePlayer.getHurtTime() != 0) {
                        thePlayer.setOnGround(false);
                        return;
                    }
                    return;
                }
                return;
            case 108891:
                if (!lowerCase.equals("ncp")) {
                    return;
                }
                break;
            case 233102203:
                if (!lowerCase.equals("vanilla")) {
                    return;
                }
                break;
            case 1492139161:
                if (lowerCase.equals("aac3.3.11") && thePlayer.isInWater()) {
                    thePlayer.setMotionX(thePlayer.getMotionX() * 1.17d);
                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.17d);
                    if (thePlayer.isCollidedHorizontally()) {
                        thePlayer.setMotionY(0.24d);
                        return;
                    }
                    if (MinecraftInstance.f157mc.getTheWorld() == null) {
                        Intrinsics.throwNpe();
                    }
                    if (!Intrinsics.areEqual(r0.getBlockState(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 1.0d, thePlayer.getPosZ())).getBlock(), MinecraftInstance.classProvider.getBlockEnum(BlockType.AIR))) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + 0.04d);
                        return;
                    }
                    return;
                }
                return;
            case 1837070814:
                if (!lowerCase.equals("dolphin") || !thePlayer.isInWater()) {
                    return;
                }
                thePlayer.setMotionY(thePlayer.getMotionY() + 0.03999999910593033d);
                return;
            default:
                return;
        }
        if (!BlockUtils.collideBlock(thePlayer.getEntityBoundingBox(), new C04221(MinecraftInstance.classProvider)) || !thePlayer.isInsideOfMaterial(MinecraftInstance.classProvider.getMaterialEnum(MaterialType.AIR)) || thePlayer.isSneaking()) {
            return;
        }
        thePlayer.setMotionY(0.08d);
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        if (Intrinsics.areEqual("aacfly", lowerCase)) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.isInWater()) {
                event.setY(((Number) this.aacFlyValue.get()).floatValue());
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer2.setMotionY(((Number) this.aacFlyValue.get()).floatValue());
            }
        }
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.f157mc.getThePlayer() != null && MinecraftInstance.classProvider.isBlockLiquid(event.getBlock())) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (BlockUtils.collideBlock(thePlayer.getEntityBoundingBox(), new C04201(MinecraftInstance.classProvider))) {
                return;
            }
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (!thePlayer2.isSneaking()) {
                String str = (String) this.modeValue.get();
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case 108891:
                        if (!lowerCase.equals("ncp")) {
                            return;
                        }
                        break;
                    case 233102203:
                        if (!lowerCase.equals("vanilla")) {
                            return;
                        }
                        break;
                    default:
                        return;
                }
                event.setBoundingBox(MinecraftInstance.classProvider.createAxisAlignedBB(event.getX(), event.getY(), event.getZ(), event.getX() + 1.0d, event.getY() + 1.0d, event.getZ() + 1.0d));
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && StringsKt.equals((String) this.modeValue.get(), "NCP", true) && MinecraftInstance.classProvider.isCPacketPlayer(event.getPacket())) {
            ICPacketPlayer iCPacketPlayerAsCPacketPlayer = event.getPacket().asCPacketPlayer();
            if (BlockUtils.collideBlock(MinecraftInstance.classProvider.createAxisAlignedBB(thePlayer.getEntityBoundingBox().getMaxX(), thePlayer.getEntityBoundingBox().getMaxY(), thePlayer.getEntityBoundingBox().getMaxZ(), thePlayer.getEntityBoundingBox().getMinX(), thePlayer.getEntityBoundingBox().getMinY() - 0.01d, thePlayer.getEntityBoundingBox().getMinZ()), new C04211(MinecraftInstance.classProvider))) {
                this.nextTick = !this.nextTick;
                if (this.nextTick) {
                    iCPacketPlayerAsCPacketPlayer.setY(iCPacketPlayerAsCPacketPlayer.getY() - 0.001d);
                }
            }
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            IBlock block = BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - 0.01d, thePlayer.getPosZ()));
            if (((Boolean) this.noJumpValue.get()).booleanValue() && MinecraftInstance.classProvider.isBlockLiquid(block)) {
                event.cancelEvent();
            }
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
