package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NoFall.kt */
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u001c\u001a\u00020\u0006J\u0016\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fJ\u0006\u0010!\u001a\u00020\u000bJ\u0006\u0010\"\u001a\u00020\u000bJ\b\u0010#\u001a\u00020$H\u0016J\u0012\u0010%\u001a\u00020$2\b\u0010&\u001a\u0004\u0018\u00010'H\u0007J\u0010\u0010(\u001a\u00020$2\u0006\u0010&\u001a\u00020)H\u0003J\u0010\u0010*\u001a\u00020$2\u0006\u0010&\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020$2\u0006\u0010&\u001a\u00020-H\u0007J\u0012\u0010.\u001a\u00020$2\b\u0010&\u001a\u0004\u0018\u00010/H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0011\u001a\u00020\u00128\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b\u00a8\u00060"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/NoFall;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "currentMlgBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "currentMlgItemIndex", "", "currentMlgRotation", "Lnet/ccbluex/liquidbounce/utils/VecRotation;", "currentState", "fakelag", "", "jumped", "minFallDistance", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "mlgTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "packetmodify", "packets", "Ljava/util/concurrent/LinkedBlockingQueue;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "spartanTimer", "tag", "", "getTag", "()Ljava/lang/String;", "getJumpEffect", "inAir", "height", "", "plus", "inVoid", "isBlockUnder", "onEnable", "", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotionUpdate", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "NoFall", description = "Prevents you from taking fall damage.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/NoFall.class */
public final class NoFall extends Module {
    private int currentState;
    private boolean jumped;
    private VecRotation currentMlgRotation;
    private int currentMlgItemIndex;
    private WBlockPos currentMlgBlock;
    private boolean fakelag;
    private boolean packetmodify;

    @JvmField
    @NotNull
    public final ListValue modeValue = new ListValue("Mode", new String[]{"AAC4", "SpoofGround", "NoGround", "Packet", "MLG", "AAC", "LAAC", "AAC3.3.11", "AAC3.3.15", "Spartan", "CubeCraft", "Hypixel"}, "SpoofGround");
    private final FloatValue minFallDistance = new FloatValue("MinMLGHeight", 5.0f, 2.0f, 50.0f);
    private final TickTimer spartanTimer = new TickTimer();
    private final TickTimer mlgTimer = new TickTimer();
    private final LinkedBlockingQueue<IPacket> packets = new LinkedBlockingQueue<>();

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\ufffd\ufffd\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0018\u00010\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u00a2\u0006\u0002\b\u0007"}, m27d2 = {"<anonymous>", "", "p1", "", "Lkotlin/ParameterName;", "name", "obj", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.features.module.modules.player.NoFall$onMove$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/NoFall$onMove$1.class */
    static final /* synthetic */ class C04251 extends FunctionReference implements Function1 {
        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IClassProvider.class);
        }

        C04251(IClassProvider iClassProvider) {
            super(1, iClassProvider);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return Boolean.valueOf(m1591invoke(obj));
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final boolean m1591invoke(@Nullable Object obj) {
            return ((IClassProvider) this.receiver).isBlockLiquid(obj);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\ufffd\ufffd\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0018\u00010\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u00a2\u0006\u0002\b\u0007"}, m27d2 = {"<anonymous>", "", "p1", "", "Lkotlin/ParameterName;", "name", "obj", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.features.module.modules.player.NoFall$onMove$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/NoFall$onMove$2.class */
    static final /* synthetic */ class C04262 extends FunctionReference implements Function1 {
        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IClassProvider.class);
        }

        C04262(IClassProvider iClassProvider) {
            super(1, iClassProvider);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return Boolean.valueOf(m1592invoke(obj));
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final boolean m1592invoke(@Nullable Object obj) {
            return ((IClassProvider) this.receiver).isBlockLiquid(obj);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\ufffd\ufffd\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0018\u00010\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u00a2\u0006\u0002\b\u0007"}, m27d2 = {"<anonymous>", "", "p1", "", "Lkotlin/ParameterName;", "name", "obj", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.features.module.modules.player.NoFall$onUpdate$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/NoFall$onUpdate$1.class */
    static final /* synthetic */ class C04271 extends FunctionReference implements Function1 {
        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IClassProvider.class);
        }

        C04271(IClassProvider iClassProvider) {
            super(1, iClassProvider);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return Boolean.valueOf(m1593invoke(obj));
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final boolean m1593invoke(@Nullable Object obj) {
            return ((IClassProvider) this.receiver).isBlockLiquid(obj);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\ufffd\ufffd\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0018\u00010\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u00a2\u0006\u0002\b\u0007"}, m27d2 = {"<anonymous>", "", "p1", "", "Lkotlin/ParameterName;", "name", "obj", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.features.module.modules.player.NoFall$onUpdate$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/NoFall$onUpdate$2.class */
    static final /* synthetic */ class C04282 extends FunctionReference implements Function1 {
        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IClassProvider.class);
        }

        C04282(IClassProvider iClassProvider) {
            super(1, iClassProvider);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return Boolean.valueOf(m1594invoke(obj));
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final boolean m1594invoke(@Nullable Object obj) {
            return ((IClassProvider) this.receiver).isBlockLiquid(obj);
        }
    }

    public void onEnable() {
        if (StringsKt.equals((String) this.modeValue.get(), "AAC4", true)) {
            this.fakelag = false;
            this.packetmodify = false;
        }
    }

    @EventTarget(ignoreCondition = true)
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.getOnGround()) {
            this.jumped = false;
        }
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer2.getMotionY() > 0) {
            this.jumped = true;
        }
        if (getState()) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(FreeCam.class);
            if (module == null) {
                Intrinsics.throwNpe();
            }
            if (module.getState()) {
                return;
            }
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            if (!BlockUtils.collideBlock(thePlayer3.getEntityBoundingBox(), new C04271(MinecraftInstance.classProvider))) {
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer4 == null) {
                    Intrinsics.throwNpe();
                }
                double maxX = thePlayer4.getEntityBoundingBox().getMaxX();
                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer5 == null) {
                    Intrinsics.throwNpe();
                }
                double maxY = thePlayer5.getEntityBoundingBox().getMaxY();
                IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer6 == null) {
                    Intrinsics.throwNpe();
                }
                double maxZ = thePlayer6.getEntityBoundingBox().getMaxZ();
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                double minX = thePlayer7.getEntityBoundingBox().getMinX();
                IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer8 == null) {
                    Intrinsics.throwNpe();
                }
                double minY = thePlayer8.getEntityBoundingBox().getMinY() - 0.01d;
                IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer9 == null) {
                    Intrinsics.throwNpe();
                }
                if (BlockUtils.collideBlock(iClassProvider.createAxisAlignedBB(maxX, maxY, maxZ, minX, minY, thePlayer9.getEntityBoundingBox().getMinZ()), new C04282(MinecraftInstance.classProvider))) {
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
                        if (lowerCase.equals("spartan")) {
                            this.spartanTimer.update();
                            if (MinecraftInstance.f157mc.getThePlayer() == null) {
                                Intrinsics.throwNpe();
                            }
                            if (r0.getFallDistance() > 1.5d && this.spartanTimer.hasTimePassed(10)) {
                                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer10 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posX = thePlayer10.getPosX();
                                IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer11 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posY = thePlayer11.getPosY() + 10;
                                IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer12 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler.addToSendQueue(iClassProvider2.createCPacketPlayerPosition(posX, posY, thePlayer12.getPosZ(), true));
                                IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider3 = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer13 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer13 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posX2 = thePlayer13.getPosX();
                                IEntityPlayerSP thePlayer14 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer14 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posY2 = thePlayer14.getPosY() - 10;
                                IEntityPlayerSP thePlayer15 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer15 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler2.addToSendQueue(iClassProvider3.createCPacketPlayerPosition(posX2, posY2, thePlayer15.getPosZ(), true));
                                this.spartanTimer.reset();
                                return;
                            }
                            return;
                        }
                        return;
                    case -1031473397:
                        if (lowerCase.equals("cubecraft")) {
                            IEntityPlayerSP thePlayer16 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer16 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (thePlayer16.getFallDistance() > 2.0f) {
                                IEntityPlayerSP thePlayer17 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer17 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer17.setOnGround(false);
                                IEntityPlayerSP thePlayer18 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer18 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer18.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(true));
                                return;
                            }
                            return;
                        }
                        return;
                    case -995865464:
                        if (lowerCase.equals("packet")) {
                            IEntityPlayerSP thePlayer19 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer19 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (thePlayer19.getFallDistance() > 2.0f) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(true));
                                return;
                            }
                            return;
                        }
                        return;
                    case 96323:
                        if (lowerCase.equals("aac")) {
                            IEntityPlayerSP thePlayer20 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer20 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (thePlayer20.getFallDistance() > 2.0f) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(true));
                                this.currentState = 2;
                            } else if (this.currentState == 2) {
                                IEntityPlayerSP thePlayer21 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer21 == null) {
                                    Intrinsics.throwNpe();
                                }
                                if (thePlayer21.getFallDistance() < 2) {
                                    IEntityPlayerSP thePlayer22 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer22 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    thePlayer22.setMotionY(0.1d);
                                    this.currentState = 3;
                                    return;
                                }
                            }
                            switch (this.currentState) {
                                case 3:
                                    IEntityPlayerSP thePlayer23 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer23 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    thePlayer23.setMotionY(0.1d);
                                    this.currentState = 4;
                                    return;
                                case 4:
                                    IEntityPlayerSP thePlayer24 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer24 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    thePlayer24.setMotionY(0.1d);
                                    this.currentState = 5;
                                    return;
                                case 5:
                                    IEntityPlayerSP thePlayer25 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer25 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    thePlayer25.setMotionY(0.1d);
                                    this.currentState = 1;
                                    return;
                                default:
                                    return;
                            }
                        }
                        return;
                    case 3313751:
                        if (!lowerCase.equals("laac") || this.jumped) {
                            return;
                        }
                        IEntityPlayerSP thePlayer26 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer26 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (thePlayer26.getOnGround()) {
                            IEntityPlayerSP thePlayer27 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer27 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (thePlayer27.isOnLadder()) {
                                return;
                            }
                            IEntityPlayerSP thePlayer28 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer28 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (thePlayer28.isInWater()) {
                                return;
                            }
                            IEntityPlayerSP thePlayer29 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer29 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (!thePlayer29.isInWeb()) {
                                IEntityPlayerSP thePlayer30 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer30 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer30.setMotionY(-6);
                                return;
                            }
                            return;
                        }
                        return;
                    case 1492139161:
                        if (lowerCase.equals("aac3.3.11")) {
                            IEntityPlayerSP thePlayer31 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer31 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (thePlayer31.getFallDistance() > 2) {
                                IEntityPlayerSP thePlayer32 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer32 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer32.setMotionZ(0.0d);
                                IEntityPlayerSP thePlayer33 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer33 == null) {
                                    Intrinsics.throwNpe();
                                }
                                IEntityPlayerSP thePlayer34 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer34 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer33.setMotionX(thePlayer34.getMotionZ());
                                IINetHandlerPlayClient netHandler3 = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider4 = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer35 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer35 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posX3 = thePlayer35.getPosX();
                                IEntityPlayerSP thePlayer36 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer36 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posY3 = thePlayer36.getPosY() - 0.001d;
                                IEntityPlayerSP thePlayer37 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer37 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posZ = thePlayer37.getPosZ();
                                IEntityPlayerSP thePlayer38 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer38 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler3.addToSendQueue(iClassProvider4.createCPacketPlayerPosition(posX3, posY3, posZ, thePlayer38.getOnGround()));
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(true));
                                return;
                            }
                            return;
                        }
                        return;
                    case 1492139165:
                        if (lowerCase.equals("aac3.3.15")) {
                            IEntityPlayerSP thePlayer39 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer39 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (thePlayer39.getFallDistance() > 2) {
                                if (!MinecraftInstance.f157mc.isIntegratedServerRunning()) {
                                    IINetHandlerPlayClient netHandler4 = MinecraftInstance.f157mc.getNetHandler();
                                    IClassProvider iClassProvider5 = MinecraftInstance.classProvider;
                                    IEntityPlayerSP thePlayer40 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer40 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    double posX4 = thePlayer40.getPosX();
                                    double naN = DoubleCompanionObject.INSTANCE.getNaN();
                                    IEntityPlayerSP thePlayer41 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer41 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    netHandler4.addToSendQueue(iClassProvider5.createCPacketPlayerPosition(posX4, naN, thePlayer41.getPosZ(), false));
                                }
                                IEntityPlayerSP thePlayer42 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer42 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer42.setFallDistance(-9999);
                                return;
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
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
        if (thePlayer.isInWater()) {
            return;
        }
        IPacket packet = event.getPacket();
        String mode = (String) this.modeValue.get();
        if (StringsKt.equals(mode, "AAC4", true) && MinecraftInstance.classProvider.isCPacketPlayer(packet) && this.fakelag) {
            event.cancelEvent();
            if (this.packetmodify) {
                packet.asCPacketPlayer().setOnGround(true);
                this.packetmodify = false;
            }
            this.packets.add(packet);
        }
        if (MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
            ICPacketPlayer playerPacket = packet.asCPacketPlayer();
            if (StringsKt.equals(mode, "SpoofGround", true)) {
                playerPacket.setOnGround(true);
            }
            if (StringsKt.equals(mode, "NoGround", true)) {
                playerPacket.setOnGround(false);
            }
            if (!StringsKt.equals(mode, "Hypixel", true) || MinecraftInstance.f157mc.getThePlayer() == null) {
                return;
            }
            if (MinecraftInstance.f157mc.getThePlayer() == null) {
                Intrinsics.throwNpe();
            }
            if (r0.getFallDistance() > 1.5d) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                playerPacket.setOnGround(thePlayer2.getTicksExisted() % 2 == 0);
            }
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (BlockUtils.collideBlock(thePlayer.getEntityBoundingBox(), new C04251(MinecraftInstance.classProvider))) {
            return;
        }
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        double maxX = thePlayer2.getEntityBoundingBox().getMaxX();
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        double maxY = thePlayer3.getEntityBoundingBox().getMaxY();
        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer4 == null) {
            Intrinsics.throwNpe();
        }
        double maxZ = thePlayer4.getEntityBoundingBox().getMaxZ();
        IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer5 == null) {
            Intrinsics.throwNpe();
        }
        double minX = thePlayer5.getEntityBoundingBox().getMinX();
        IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer6 == null) {
            Intrinsics.throwNpe();
        }
        double minY = thePlayer6.getEntityBoundingBox().getMinY() - 0.01d;
        IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer7 == null) {
            Intrinsics.throwNpe();
        }
        if (BlockUtils.collideBlock(iClassProvider.createAxisAlignedBB(maxX, maxY, maxZ, minX, minY, thePlayer7.getEntityBoundingBox().getMinZ()), new C04262(MinecraftInstance.classProvider)) || !StringsKt.equals((String) this.modeValue.get(), "laac", true) || this.jumped) {
            return;
        }
        IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer8 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer8.getOnGround()) {
            return;
        }
        IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer9 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer9.isOnLadder()) {
            return;
        }
        IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer10 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer10.isInWater()) {
            return;
        }
        IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer11 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer11.isInWeb()) {
            return;
        }
        IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer12 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer12.getMotionY() < 0.0d) {
            event.setX(0.0d);
            event.setZ(0.0d);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:232:0x04aa A[SYNTHETIC] */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void onMotionUpdate(MotionEvent event) {
        IBlock block;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isInWater()) {
            return;
        }
        if (StringsKt.equals((String) this.modeValue.get(), "AAC4", true)) {
            EventState eventState = event.getEventState();
            if (eventState == EventState.PRE) {
                if (!inVoid()) {
                    if (this.fakelag) {
                        this.fakelag = false;
                        if (this.packets.size() > 0) {
                            Iterator<IPacket> it = this.packets.iterator();
                            while (it.hasNext()) {
                                IPacket packet = it.next();
                                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer2 == null) {
                                    Intrinsics.throwNpe();
                                }
                                IINetHandlerPlayClient sendQueue = thePlayer2.getSendQueue();
                                Intrinsics.checkExpressionValueIsNotNull(packet, "packet");
                                sendQueue.addToSendQueue(packet);
                            }
                            this.packets.clear();
                            return;
                        }
                        return;
                    }
                    return;
                }
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer3.getOnGround() && this.fakelag) {
                    this.fakelag = false;
                    if (this.packets.size() > 0) {
                        Iterator<IPacket> it2 = this.packets.iterator();
                        while (it2.hasNext()) {
                            IPacket packet2 = it2.next();
                            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer4 == null) {
                                Intrinsics.throwNpe();
                            }
                            IINetHandlerPlayClient sendQueue2 = thePlayer4.getSendQueue();
                            Intrinsics.checkExpressionValueIsNotNull(packet2, "packet");
                            sendQueue2.addToSendQueue(packet2);
                        }
                        this.packets.clear();
                        return;
                    }
                    return;
                }
                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer5 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer5.getFallDistance() > 3 && this.fakelag) {
                    this.packetmodify = true;
                    IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer6 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer6.setFallDistance(0.0f);
                }
                if (inAir(4.0d, 1.0d)) {
                    return;
                }
                if (!this.fakelag) {
                    this.fakelag = true;
                }
            }
        }
        if (StringsKt.equals((String) this.modeValue.get(), "MLG", true)) {
            if (event.getEventState() == EventState.PRE) {
                this.currentMlgRotation = (VecRotation) null;
                this.mlgTimer.update();
                if (!this.mlgTimer.hasTimePassed(10)) {
                    return;
                }
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer7.getFallDistance() > ((Number) this.minFallDistance.get()).floatValue()) {
                    IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer8 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posX = thePlayer8.getPosX();
                    IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer9 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posY = thePlayer9.getPosY();
                    IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer10 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posZ = thePlayer10.getPosZ();
                    IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer11 == null) {
                        Intrinsics.throwNpe();
                    }
                    double motionX = thePlayer11.getMotionX();
                    IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer12 == null) {
                        Intrinsics.throwNpe();
                    }
                    double motionY = thePlayer12.getMotionY();
                    IEntityPlayerSP thePlayer13 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer13 == null) {
                        Intrinsics.throwNpe();
                    }
                    double motionZ = thePlayer13.getMotionZ();
                    IEntityPlayerSP thePlayer14 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer14 == null) {
                        Intrinsics.throwNpe();
                    }
                    float rotationYaw = thePlayer14.getRotationYaw();
                    IEntityPlayerSP thePlayer15 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer15 == null) {
                        Intrinsics.throwNpe();
                    }
                    float moveStrafing = thePlayer15.getMoveStrafing();
                    IEntityPlayerSP thePlayer16 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer16 == null) {
                        Intrinsics.throwNpe();
                    }
                    FallingPlayer fallingPlayer = new FallingPlayer(posX, posY, posZ, motionX, motionY, motionZ, rotationYaw, moveStrafing, thePlayer16.getMoveForward());
                    double maxDist = MinecraftInstance.f157mc.getPlayerController().getBlockReachDistance() + 1.5d;
                    IEntityPlayerSP thePlayer17 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer17 == null) {
                        Intrinsics.throwNpe();
                    }
                    FallingPlayer.CollisionResult collision = fallingPlayer.findCollision((int) Math.ceil((1.0d / thePlayer17.getMotionY()) * (-maxDist)));
                    if (collision == null) {
                        return;
                    }
                    IEntityPlayerSP thePlayer18 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer18 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posX2 = thePlayer18.getPosX();
                    IEntityPlayerSP thePlayer19 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer19 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posY2 = thePlayer19.getPosY();
                    if (MinecraftInstance.f157mc.getThePlayer() == null) {
                        Intrinsics.throwNpe();
                    }
                    double eyeHeight = posY2 + r4.getEyeHeight();
                    IEntityPlayerSP thePlayer20 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer20 == null) {
                        Intrinsics.throwNpe();
                    }
                    WVec3 wVec3 = new WVec3(posX2, eyeHeight, thePlayer20.getPosZ());
                    WBlockPos pos = collision.getPos();
                    Intrinsics.checkExpressionValueIsNotNull(pos, "collision.pos");
                    WVec3 this_$iv = new WVec3(pos);
                    boolean ok = wVec3.distanceTo(new WVec3(this_$iv.getXCoord() + 0.5d, this_$iv.getYCoord() + 0.5d, this_$iv.getZCoord() + 0.5d)) < ((double) MinecraftInstance.f157mc.getPlayerController().getBlockReachDistance()) + Math.sqrt(0.75d);
                    IEntityPlayerSP thePlayer21 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer21 == null) {
                        Intrinsics.throwNpe();
                    }
                    double motionY2 = thePlayer21.getMotionY();
                    double y = collision.getPos().getY() + 1;
                    IEntityPlayerSP thePlayer22 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer22 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (motionY2 < y - thePlayer22.getPosY()) {
                        ok = true;
                    }
                    if (!ok) {
                        return;
                    }
                    int index = -1;
                    for (int i = 36; i <= 44; i++) {
                        IEntityPlayerSP thePlayer23 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer23 == null) {
                            Intrinsics.throwNpe();
                        }
                        IItemStack itemStack = thePlayer23.getInventoryContainer().getSlot(i).getStack();
                        if (itemStack != null) {
                            if (!Intrinsics.areEqual(itemStack.getItem(), MinecraftInstance.classProvider.getItemEnum(ItemType.WATER_BUCKET))) {
                                if (MinecraftInstance.classProvider.isItemBlock(itemStack.getItem())) {
                                    IItem item = itemStack.getItem();
                                    if (item != null) {
                                        IItemBlock iItemBlockAsItemBlock = item.asItemBlock();
                                        block = iItemBlockAsItemBlock != null ? iItemBlockAsItemBlock.getBlock() : null;
                                        if (Intrinsics.areEqual(block, MinecraftInstance.classProvider.getBlockEnum(BlockType.WEB))) {
                                            continue;
                                        }
                                    }
                                    if (Intrinsics.areEqual(block, MinecraftInstance.classProvider.getBlockEnum(BlockType.WEB))) {
                                    }
                                } else {
                                    continue;
                                }
                            }
                            index = i - 36;
                            IEntityPlayerSP thePlayer24 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer24 == null) {
                                Intrinsics.throwNpe();
                            }
                            if (thePlayer24.getInventory().getCurrentItem() == index) {
                                break;
                            }
                        }
                    }
                    if (index == -1) {
                        return;
                    }
                    this.currentMlgItemIndex = index;
                    this.currentMlgBlock = collision.getPos();
                    IEntityPlayerSP thePlayer25 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer25 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer25.getInventory().getCurrentItem() != index) {
                        IEntityPlayerSP thePlayer26 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer26 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer26.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(index));
                    }
                    this.currentMlgRotation = RotationUtils.faceBlock(collision.getPos());
                    VecRotation vecRotation = this.currentMlgRotation;
                    if (vecRotation == null) {
                        Intrinsics.throwNpe();
                    }
                    Rotation rotation = vecRotation.getRotation();
                    IEntityPlayerSP thePlayer27 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer27 == null) {
                        Intrinsics.throwNpe();
                    }
                    rotation.toPlayer(thePlayer27);
                    return;
                }
                return;
            }
            if (this.currentMlgRotation != null) {
                IEntityPlayerSP thePlayer28 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer28 == null) {
                    Intrinsics.throwNpe();
                }
                IItemStack stack = thePlayer28.getInventory().getStackInSlot(this.currentMlgItemIndex + 36);
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                if (stack == null) {
                    Intrinsics.throwNpe();
                }
                if (iClassProvider.isItemBucket(stack.getItem())) {
                    IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
                    IEntityPlayerSP thePlayer29 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer29 == null) {
                        Intrinsics.throwNpe();
                    }
                    IEntityPlayerSP iEntityPlayerSP = thePlayer29;
                    IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                    if (theWorld == null) {
                        Intrinsics.throwNpe();
                    }
                    playerController.sendUseItem(iEntityPlayerSP, theWorld, stack);
                } else {
                    MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.UP).getDirectionVec();
                    IPlayerControllerMP playerController2 = MinecraftInstance.f157mc.getPlayerController();
                    IEntityPlayerSP thePlayer30 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer30 == null) {
                        Intrinsics.throwNpe();
                    }
                    IEntityPlayerSP iEntityPlayerSP2 = thePlayer30;
                    IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                    if (theWorld2 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (playerController2.sendUseItem(iEntityPlayerSP2, theWorld2, stack)) {
                        this.mlgTimer.reset();
                    }
                }
                IEntityPlayerSP thePlayer31 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer31 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer31.getInventory().getCurrentItem() != this.currentMlgItemIndex) {
                    IEntityPlayerSP thePlayer32 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer32 == null) {
                        Intrinsics.throwNpe();
                    }
                    IINetHandlerPlayClient sendQueue3 = thePlayer32.getSendQueue();
                    IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                    IEntityPlayerSP thePlayer33 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer33 == null) {
                        Intrinsics.throwNpe();
                    }
                    sendQueue3.addToSendQueue(iClassProvider2.createCPacketHeldItemChange(thePlayer33.getInventory().getCurrentItem()));
                }
            }
        }
    }

    public final boolean isBlockUnder() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.getPosY() < 0.0d) {
            return false;
        }
        int i = 0;
        while (true) {
            int off = i;
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (off >= ((int) thePlayer2.getPosY()) + 2) {
                return false;
            }
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            IAxisAlignedBB bb = thePlayer3.getEntityBoundingBox().offset(0.0d, -off, 0.0d);
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer4 == null) {
                Intrinsics.throwNpe();
            }
            if (!theWorld.getCollidingBoundingBoxes(thePlayer4, bb).isEmpty()) {
                return true;
            }
            i = off + 2;
        }
    }

    public final int getJumpEffect() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (!thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.JUMP))) {
            return 0;
        }
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        IPotionEffect activePotionEffect = thePlayer2.getActivePotionEffect(MinecraftInstance.classProvider.getPotionEnum(PotionType.JUMP));
        if (activePotionEffect == null) {
            Intrinsics.throwNpe();
        }
        return activePotionEffect.getAmplifier() + 1;
    }

    public final boolean inVoid() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.getPosY() < 0) {
            return false;
        }
        int i = 0;
        while (true) {
            int off = i;
            double d = off;
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (d < thePlayer2.getPosY() + 2) {
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                double posX = thePlayer3.getPosX();
                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer4 == null) {
                    Intrinsics.throwNpe();
                }
                double posY = thePlayer4.getPosY();
                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer5 == null) {
                    Intrinsics.throwNpe();
                }
                double posZ = thePlayer5.getPosZ();
                IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer6 == null) {
                    Intrinsics.throwNpe();
                }
                double posX2 = thePlayer6.getPosX();
                double d2 = off;
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                IAxisAlignedBB bb = iClassProvider.createAxisAlignedBB(posX, posY, posZ, posX2, d2, thePlayer7.getPosZ());
                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld == null) {
                    Intrinsics.throwNpe();
                }
                IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer8 == null) {
                    Intrinsics.throwNpe();
                }
                if (!theWorld.getCollidingBoundingBoxes(thePlayer8, bb).isEmpty()) {
                    return true;
                }
                i = off + 2;
            } else {
                return false;
            }
        }
    }

    public final boolean inAir(double height, double plus) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.getPosY() < 0) {
            return false;
        }
        int i = 0;
        while (true) {
            int off = i;
            if (off < height) {
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                double posX = thePlayer2.getPosX();
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                double posY = thePlayer3.getPosY();
                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer4 == null) {
                    Intrinsics.throwNpe();
                }
                double posZ = thePlayer4.getPosZ();
                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer5 == null) {
                    Intrinsics.throwNpe();
                }
                double posX2 = thePlayer5.getPosX();
                IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer6 == null) {
                    Intrinsics.throwNpe();
                }
                double posY2 = thePlayer6.getPosY() - off;
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                IAxisAlignedBB bb = iClassProvider.createAxisAlignedBB(posX, posY, posZ, posX2, posY2, thePlayer7.getPosZ());
                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld == null) {
                    Intrinsics.throwNpe();
                }
                IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer8 == null) {
                    Intrinsics.throwNpe();
                }
                if (!theWorld.getCollidingBoundingBoxes(thePlayer8, bb).isEmpty()) {
                    return true;
                }
                i = off + ((int) plus);
            } else {
                return false;
            }
        }
    }

    @EventTarget(ignoreCondition = true)
    public final void onJump(@Nullable JumpEvent event) {
        this.jumped = true;
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
