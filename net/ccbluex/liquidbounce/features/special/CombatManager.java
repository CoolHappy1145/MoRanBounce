package net.ccbluex.liquidbounce.features.special;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EntityKilledEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001d\u001a\u00020\u001eJ\b\u0010\u001f\u001a\u00020\rH\u0016J\u000e\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\nJ\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0007J\u0010\u0010&\u001a\u00020#2\u0006\u0010$\u001a\u00020'H\u0007J\u0010\u0010(\u001a\u00020#2\u0006\u0010$\u001a\u00020)H\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\bR\u001e\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r@BX\u0086\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\"\u0010\u0019\u001a\u0004\u0018\u00010\u00062\b\u0010\f\u001a\u0004\u0018\u00010\u0006@BX\u0086\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006*"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/special/CombatManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "attackedEntityList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getAttackedEntityList", "()Ljava/util/List;", "focusedPlayerList", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "getFocusedPlayerList", "<set-?>", "", "inCombat", "getInCombat", "()Z", "kills", "", "getKills", "()I", "setKills", "(I)V", "lastAttackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "target", "getTarget", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "getNearByEntity", "radius", "", "handleEvents", "isFocusEntity", "entity", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/special/CombatManager.class */
public final class CombatManager extends MinecraftInstance implements Listenable {
    private int kills;
    private boolean inCombat;

    @Nullable
    private IEntityLivingBase target;
    private final MSTimer lastAttackTimer = new MSTimer();

    @NotNull
    private final List attackedEntityList = new ArrayList();

    @NotNull
    private final List focusedPlayerList = new ArrayList();

    public final int getKills() {
        return this.kills;
    }

    public final void setKills(int i) {
        this.kills = i;
    }

    public final boolean getInCombat() {
        return this.inCombat;
    }

    @Nullable
    public final IEntityLivingBase getTarget() {
        return this.target;
    }

    @NotNull
    public final List getAttackedEntityList() {
        return this.attackedEntityList;
    }

    @NotNull
    public final List getFocusedPlayerList() {
        return this.focusedPlayerList;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            return;
        }
        List list = this.attackedEntityList;
        ArrayList<IEntityLivingBase> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((IEntityLivingBase) it.next());
        }
        for (IEntityLivingBase iEntityLivingBase : arrayList) {
            if (iEntityLivingBase.isDead()) {
                LiquidBounce.INSTANCE.getEventManager().callEvent(new EntityKilledEvent(iEntityLivingBase));
                this.kills++;
                this.attackedEntityList.remove(iEntityLivingBase);
            }
        }
        this.inCombat = false;
        if (!this.lastAttackTimer.hasTimePassed(1000L)) {
            this.inCombat = true;
            return;
        }
        if (this.target != null) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            IEntityLivingBase iEntityLivingBase2 = this.target;
            if (iEntityLivingBase2 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getDistanceToEntity(iEntityLivingBase2) <= 7.0f && this.inCombat) {
                IEntityLivingBase iEntityLivingBase3 = this.target;
                if (iEntityLivingBase3 == null) {
                    Intrinsics.throwNpe();
                }
                if (!iEntityLivingBase3.isDead()) {
                    this.inCombat = true;
                    return;
                }
            }
            this.target = (IEntityLivingBase) null;
        }
    }

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntity targetEntity = event.getTargetEntity();
        if (MinecraftInstance.classProvider.isEntityLivingBase(targetEntity) && EntityUtils.isSelected(targetEntity, true)) {
            this.target = (IEntityLivingBase) targetEntity;
            List list = this.attackedEntityList;
            if (targetEntity == null) {
                Intrinsics.throwNpe();
            }
            if (!CollectionsKt.contains(list, targetEntity)) {
                this.attackedEntityList.add(targetEntity);
            }
        }
        this.lastAttackTimer.reset();
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.inCombat = false;
        this.target = (IEntityLivingBase) null;
        this.attackedEntityList.clear();
        this.focusedPlayerList.clear();
    }

    @Nullable
    public final IEntityLivingBase getNearByEntity(float f) {
        IEntityLivingBase iEntityLivingBase;
        try {
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            Collection loadedEntityList = theWorld.getLoadedEntityList();
            ArrayList arrayList = new ArrayList();
            for (Object obj : loadedEntityList) {
                IEntity iEntity = (IEntity) obj;
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer.getDistanceToEntity(iEntity) < f && EntityUtils.isSelected(iEntity, true)) {
                    arrayList.add(obj);
                }
            }
            iEntityLivingBase = (IEntityLivingBase) CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: net.ccbluex.liquidbounce.features.special.CombatManager$getNearByEntity$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj2, Object obj3) {
                    IEntity iEntity2 = (IEntity) obj2;
                    IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer2 == null) {
                        Intrinsics.throwNpe();
                    }
                    Float fValueOf = Float.valueOf(iEntity2.getDistanceToEntity(thePlayer2));
                    IEntity iEntity3 = (IEntity) obj3;
                    IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer3 == null) {
                        Intrinsics.throwNpe();
                    }
                    return ComparisonsKt.compareValues(fValueOf, Float.valueOf(iEntity3.getDistanceToEntity(thePlayer3)));
                }
            }).get(0);
        } catch (Exception unused) {
            iEntityLivingBase = null;
        }
        return iEntityLivingBase;
    }

    public final boolean isFocusEntity(@NotNull IEntityPlayer entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        if (this.focusedPlayerList.isEmpty()) {
            return true;
        }
        return this.focusedPlayerList.contains(entity);
    }
}
