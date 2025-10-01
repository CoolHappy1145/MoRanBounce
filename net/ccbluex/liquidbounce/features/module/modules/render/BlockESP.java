package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.apache.log4j.net.SyslogAppender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdZ\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0007J\u0012\u0010\u001d\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/BlockESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blockValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "posList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "radiusValue", "searchTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "tag", "", "getTag", "()Ljava/lang/String;", "thread", "Ljava/lang/Thread;", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "BlockESP", description = "Allows you to see a selected block through walls.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/BlockESP.class */
public final class BlockESP extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "2D"}, "Box");
    private final BlockValue blockValue = new BlockValue("Block", SyslogAppender.LOG_LOCAL5);
    private final IntegerValue radiusValue = new IntegerValue("Radius", 40, 5, 120);
    private final IntegerValue colorRedValue = new IntegerValue("R", 255, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 179, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 72, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);
    private final MSTimer searchTimer = new MSTimer();
    private final List posList = new ArrayList();
    private Thread thread;

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        if (this.searchTimer.hasTimePassed(1000L)) {
            if (this.thread != null) {
                Thread thread = this.thread;
                if (thread == null) {
                    Intrinsics.throwNpe();
                }
                if (thread.isAlive()) {
                    return;
                }
            }
            int iIntValue = ((Number) this.radiusValue.get()).intValue();
            IBlock blockById = MinecraftInstance.functions.getBlockById(((Number) this.blockValue.get()).intValue());
            if (blockById == null || Intrinsics.areEqual(blockById, MinecraftInstance.classProvider.getBlockEnum(BlockType.AIR))) {
                return;
            }
            this.thread = new Thread(new Runnable(this, iIntValue, blockById) { // from class: net.ccbluex.liquidbounce.features.module.modules.render.BlockESP.onUpdate.1
                final BlockESP this$0;
                final int $radius;
                final IBlock $selectedBlock;

                {
                    this.this$0 = this;
                    this.$radius = iIntValue;
                    this.$selectedBlock = blockById;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    ArrayList arrayList = new ArrayList();
                    int i = this.$radius;
                    for (int i2 = -this.$radius; i2 < i; i2++) {
                        int i3 = this.$radius;
                        int i4 = (-this.$radius) + 1;
                        if (i3 >= i4) {
                            while (true) {
                                int i5 = this.$radius;
                                for (int i6 = -this.$radius; i6 < i5; i6++) {
                                    IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    WBlockPos wBlockPos = new WBlockPos(((int) thePlayer.getPosX()) + i2, ((int) thePlayer.getPosY()) + i3, ((int) thePlayer.getPosZ()) + i6);
                                    if (Intrinsics.areEqual(BlockUtils.getBlock(wBlockPos), this.$selectedBlock)) {
                                        arrayList.add(wBlockPos);
                                    }
                                }
                                if (i3 != i4) {
                                    i3--;
                                }
                            }
                        }
                    }
                    this.this$0.searchTimer.reset();
                    synchronized (this.this$0.posList) {
                        this.this$0.posList.clear();
                        this.this$0.posList.addAll(arrayList);
                    }
                }
            }, "BlockESP-BlockFinder");
            Thread thread2 = this.thread;
            if (thread2 == null) {
                Intrinsics.throwNpe();
            }
            thread2.start();
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent render3DEvent) {
        synchronized (this.posList) {
            Color colorRainbow = ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue());
            for (WBlockPos wBlockPos : this.posList) {
                String str = (String) this.modeValue.get();
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case 1650:
                        if (lowerCase.equals("2d")) {
                            int rgb = colorRainbow.getRGB();
                            Color color = Color.BLACK;
                            Intrinsics.checkExpressionValueIsNotNull(color, "Color.BLACK");
                            RenderUtils.draw2D(wBlockPos, rgb, color.getRGB());
                            break;
                        } else {
                            break;
                        }
                    case 97739:
                        if (lowerCase.equals("box")) {
                            RenderUtils.drawBlockBox(wBlockPos, colorRainbow, true);
                            break;
                        } else {
                            break;
                        }
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @NotNull
    public String getTag() {
        return ((Number) this.blockValue.get()).intValue() == 26 ? "Bed" : BlockUtils.getBlockName(((Number) this.blockValue.get()).intValue());
    }
}
