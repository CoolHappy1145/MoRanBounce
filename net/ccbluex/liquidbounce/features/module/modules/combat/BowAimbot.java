package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdL\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0006\u0010\u0013\u001a\u00020\u0010J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001aH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/BowAimbot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "markValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "predictSizeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "predictValue", "priorityValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "silentValue", "target", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "throughWallsValue", "getTarget", "throughWalls", "", "priorityMode", "", "hasTarget", "onDisable", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "BowAimbot", description = "Automatically aims at players when using a bow.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/BowAimbot.class */
public final class BowAimbot extends Module {
    private final BoolValue silentValue = new BoolValue("Silent", true);
    private final BoolValue predictValue = new BoolValue("Predict", true);
    private final BoolValue throughWallsValue = new BoolValue("ThroughWalls", false);
    private final FloatValue predictSizeValue = new FloatValue("PredictSize", 2.0f, 0.1f, 5.0f);
    private final ListValue priorityValue = new ListValue("Priority", new String[]{"Health", "Distance", "Direction"}, "Direction");
    private final BoolValue markValue = new BoolValue("Mark", true);
    private IEntity target;

    public void onDisable() {
        this.target = (IEntity) null;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x002e  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onUpdate(@NotNull UpdateEvent event) {
        IItem item;
        IEntity target;
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.target = (IEntity) null;
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            IItemStack itemInUse = thePlayer.getItemInUse();
            item = itemInUse != null ? itemInUse.getItem() : null;
        }
        if (!iClassProvider.isItemBow(item) || (target = getTarget(((Boolean) this.throughWallsValue.get()).booleanValue(), (String) this.priorityValue.get())) == null) {
            return;
        }
        this.target = target;
        RotationUtils.faceBow(this.target, ((Boolean) this.silentValue.get()).booleanValue(), ((Boolean) this.predictValue.get()).booleanValue(), ((Number) this.predictSizeValue.get()).floatValue());
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.target != null && !StringsKt.equals((String) this.priorityValue.get(), "Multi", true) && ((Boolean) this.markValue.get()).booleanValue()) {
            RenderUtils.drawPlatform(this.target, new Color(37, 126, 255, 70));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final IEntity getTarget(boolean z, String str) {
        Object obj;
        Object obj2;
        Object obj3;
        boolean z2;
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        Collection loadedEntityList = theWorld.getLoadedEntityList();
        ArrayList arrayList = new ArrayList();
        for (Object obj4 : loadedEntityList) {
            IEntity iEntity = (IEntity) obj4;
            if (MinecraftInstance.classProvider.isEntityLivingBase(iEntity) && EntityUtils.isSelected(iEntity, true)) {
                if (!z) {
                    IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer.canEntityBeSeen(iEntity)) {
                    }
                }
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                arrayList.add(obj4);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String upperCase = str.toUpperCase();
        Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
        switch (upperCase.hashCode()) {
            case 1071086581:
                if (upperCase.equals("DISTANCE")) {
                    Iterator it = arrayList2.iterator();
                    if (it.hasNext()) {
                        Object next = it.next();
                        if (it.hasNext()) {
                            IEntity iEntity2 = (IEntity) next;
                            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer2 == null) {
                                Intrinsics.throwNpe();
                            }
                            float distanceToEntity = thePlayer2.getDistanceToEntity(iEntity2);
                            do {
                                Object next2 = it.next();
                                IEntity iEntity3 = (IEntity) next2;
                                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer3 == null) {
                                    Intrinsics.throwNpe();
                                }
                                float distanceToEntity2 = thePlayer3.getDistanceToEntity(iEntity3);
                                if (Float.compare(distanceToEntity, distanceToEntity2) > 0) {
                                    next = next2;
                                    distanceToEntity = distanceToEntity2;
                                }
                            } while (it.hasNext());
                            obj3 = next;
                        } else {
                            obj3 = next;
                        }
                    } else {
                        obj3 = null;
                    }
                    return (IEntity) obj3;
                }
                return null;
            case 1824003935:
                if (upperCase.equals("DIRECTION")) {
                    Iterator it2 = arrayList2.iterator();
                    if (it2.hasNext()) {
                        Object next3 = it2.next();
                        if (it2.hasNext()) {
                            double rotationDifference = RotationUtils.getRotationDifference((IEntity) next3);
                            do {
                                Object next4 = it2.next();
                                double rotationDifference2 = RotationUtils.getRotationDifference((IEntity) next4);
                                if (Double.compare(rotationDifference, rotationDifference2) > 0) {
                                    next3 = next4;
                                    rotationDifference = rotationDifference2;
                                }
                            } while (it2.hasNext());
                            obj2 = next3;
                        } else {
                            obj2 = next3;
                        }
                    } else {
                        obj2 = null;
                    }
                    return (IEntity) obj2;
                }
                return null;
            case 2127033948:
                if (upperCase.equals("HEALTH")) {
                    Iterator it3 = arrayList2.iterator();
                    if (it3.hasNext()) {
                        Object next5 = it3.next();
                        if (it3.hasNext()) {
                            float health = ((IEntity) next5).asEntityLivingBase().getHealth();
                            do {
                                Object next6 = it3.next();
                                float health2 = ((IEntity) next6).asEntityLivingBase().getHealth();
                                if (Float.compare(health, health2) > 0) {
                                    next5 = next6;
                                    health = health2;
                                }
                            } while (it3.hasNext());
                            obj = next5;
                        } else {
                            obj = next5;
                        }
                    } else {
                        obj = null;
                    }
                    return (IEntity) obj;
                }
                return null;
            default:
                return null;
        }
    }

    public final boolean hasTarget() {
        if (this.target != null) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            IEntity iEntity = this.target;
            if (iEntity == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.canEntityBeSeen(iEntity)) {
                return true;
            }
        }
        return false;
    }
}
