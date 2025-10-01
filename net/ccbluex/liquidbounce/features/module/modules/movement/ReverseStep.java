package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007J\u0012\u0010\u000b\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/ReverseStep;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "jumped", "", "motionValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onJump", "", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "ReverseStep", description = "Allows you to step down blocks faster.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/ReverseStep.class */
public final class ReverseStep extends Module {
    private final FloatValue motionValue = new FloatValue("Motion", 1.0f, 0.21f, 1.0f);
    private boolean jumped;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\ufffd\ufffd\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0018\u00010\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u00a2\u0006\u0002\b\u0007"}, m27d2 = {"<anonymous>", "", "p1", "", "Lkotlin/ParameterName;", "name", "obj", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.features.module.modules.movement.ReverseStep$onUpdate$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/ReverseStep$onUpdate$1.class */
    static final /* synthetic */ class C04231 extends FunctionReference implements Function1 {
        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IClassProvider.class);
        }

        C04231(IClassProvider iClassProvider) {
            super(1, iClassProvider);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return Boolean.valueOf(m1584invoke(obj));
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final boolean m1584invoke(@Nullable Object obj) {
            return ((IClassProvider) this.receiver).isBlockLiquid(obj);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\ufffd\ufffd\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0018\u00010\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u00a2\u0006\u0002\b\u0007"}, m27d2 = {"<anonymous>", "", "p1", "", "Lkotlin/ParameterName;", "name", "obj", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.features.module.modules.movement.ReverseStep$onUpdate$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/ReverseStep$onUpdate$2.class */
    static final /* synthetic */ class C04242 extends FunctionReference implements Function1 {
        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IClassProvider.class);
        }

        C04242(IClassProvider iClassProvider) {
            super(1, iClassProvider);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return Boolean.valueOf(m1585invoke(obj));
        }

        /* renamed from: invoke, reason: collision with other method in class */
        public final boolean m1585invoke(@Nullable Object obj) {
            return ((IClassProvider) this.receiver).isBlockLiquid(obj);
        }
    }

    @EventTarget(ignoreCondition = true)
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (thePlayer.getOnGround()) {
                this.jumped = false;
            }
            if (thePlayer.getMotionY() > 0.0d) {
                this.jumped = true;
            }
            if (getState() && !BlockUtils.collideBlock(thePlayer.getEntityBoundingBox(), new C04231(MinecraftInstance.classProvider)) && !BlockUtils.collideBlock(MinecraftInstance.classProvider.createAxisAlignedBB(thePlayer.getEntityBoundingBox().getMaxX(), thePlayer.getEntityBoundingBox().getMaxY(), thePlayer.getEntityBoundingBox().getMaxZ(), thePlayer.getEntityBoundingBox().getMinX(), thePlayer.getEntityBoundingBox().getMinY() - 0.01d, thePlayer.getEntityBoundingBox().getMinZ()), new C04242(MinecraftInstance.classProvider)) && !MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown() && !thePlayer.getOnGround() && !thePlayer.getMovementInput().getJump() && thePlayer.getMotionY() <= 0.0d && thePlayer.getFallDistance() <= 1.0f && !this.jumped) {
                thePlayer.setMotionY(-((Number) this.motionValue.get()).floatValue());
            }
        }
    }

    @EventTarget(ignoreCondition = true)
    public final void onJump(@Nullable JumpEvent jumpEvent) {
        this.jumped = true;
    }
}
