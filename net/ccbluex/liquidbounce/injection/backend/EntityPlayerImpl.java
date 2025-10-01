package net.ccbluex.liquidbounce.injection.backend;

import com.mojang.authlib.GameProfile;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IInventoryPlayer;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IPlayerCapabilities;
import net.ccbluex.liquidbounce.api.minecraft.inventory.IContainer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.stats.IStatBase;
import net.ccbluex.liquidbounce.api.minecraft.util.IFoodStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u008c\u0001\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0006\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010Q\u001a\u00020R2\u0006\u0010S\u001a\u00020\u0017H\u0016J\u0018\u0010T\u001a\u00020R2\u0006\u0010U\u001a\u00020\b2\u0006\u0010V\u001a\u00020\bH\u0016J\u0010\u0010W\u001a\u00020\b2\u0006\u0010X\u001a\u00020\bH\u0016J\b\u0010Y\u001a\u00020RH\u0016J\u0010\u0010Z\u001a\u00020R2\u0006\u0010S\u001a\u00020\u0017H\u0016J\u0010\u0010[\u001a\u00020R2\u0006\u0010S\u001a\u00020\\H\u0016J\b\u0010]\u001a\u00020RH\u0016J\u0010\u0010^\u001a\u00020R2\u0006\u0010_\u001a\u000205H\u0016J\b\u0010`\u001a\u00020RH\u0016J\u0010\u0010a\u001a\u00020R2\u0006\u0010b\u001a\u00020cH\u0016R$\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0016\u001a\u0004\u0018\u00010\u00178VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010!R\u0016\u0010\"\u001a\u0004\u0018\u00010#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020'8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020+8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\u00020/8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u00100R\u0014\u00101\u001a\u00020/8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b1\u00100R\u0014\u00102\u001a\u00020/8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b2\u00100R\u0016\u00103\u001a\u0004\u0018\u00010#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b4\u0010%R$\u00106\u001a\u0002052\u0006\u0010\u0007\u001a\u0002058V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u0014\u0010;\u001a\u0002058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b<\u00108R\u0016\u0010=\u001a\u0004\u0018\u00010+8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b>\u0010-R\u0014\u0010?\u001a\u00020@8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bA\u0010BR$\u0010C\u001a\u0002052\u0006\u0010\u0007\u001a\u0002058V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bD\u00108\"\u0004\bE\u0010:R$\u0010F\u001a\u00020/2\u0006\u0010\u0007\u001a\u00020/8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bG\u00100\"\u0004\bH\u0010IR\u0014\u0010J\u001a\u00020/8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bK\u00100R$\u0010L\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bM\u0010\u000b\"\u0004\bN\u0010\rR\u0014\u0010O\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bP\u0010\u000b\u00a8\u0006d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/EntityPlayerImpl;", "T", "Lnet/minecraft/entity/player/EntityPlayer;", "Lnet/ccbluex/liquidbounce/injection/backend/EntityLivingBaseImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "wrapped", "(Lnet/minecraft/entity/player/EntityPlayer;)V", PropertyDescriptor.VALUE, "", "cameraYaw", "getCameraYaw", "()F", "setCameraYaw", "(F)V", "capabilities", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IPlayerCapabilities;", "getCapabilities", "()Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IPlayerCapabilities;", "displayNameString", "", "getDisplayNameString", "()Ljava/lang/String;", "fishEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getFishEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "foodStats", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IFoodStats;", "getFoodStats", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IFoodStats;", "gameProfile", "Lcom/mojang/authlib/GameProfile;", "getGameProfile", "()Lcom/mojang/authlib/GameProfile;", "heldItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "getHeldItem", "()Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "inventory", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IInventoryPlayer;", "getInventory", "()Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IInventoryPlayer;", "inventoryContainer", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/IContainer;", "getInventoryContainer", "()Lnet/ccbluex/liquidbounce/api/minecraft/inventory/IContainer;", "isBlocking", "", "()Z", "isPlayerSleeping", "isUsingItem", "itemInUse", "getItemInUse", "", "itemInUseCount", "getItemInUseCount", "()I", "setItemInUseCount", "(I)V", "itemInUseDuration", "getItemInUseDuration", "openContainer", "getOpenContainer", "prevChasingPosY", "", "getPrevChasingPosY", "()D", "sleepTimer", "getSleepTimer", "setSleepTimer", "sleeping", "getSleeping", "setSleeping", "(Z)V", "spectator", "getSpectator", "speedInAir", "getSpeedInAir", "setSpeedInAir", "swingProgress", "getSwingProgress", "attackTargetEntityWithCurrentItem", "", "entity", "fall", "distance", "damageMultiplier", "getCooledAttackStrength", "adjustTicks", "jump", "onCriticalHit", "onEnchantmentCritical", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "resetCooldown", "setItemInUse", "duration", "stopUsingItem", "triggerAchievement", "stat", "Lnet/ccbluex/liquidbounce/api/minecraft/stats/IStatBase;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/EntityPlayerImpl.class */
public class EntityPlayerImpl extends EntityLivingBaseImpl implements IEntityPlayer {
    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public boolean isSpectator() {
        return getSpectator();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EntityPlayerImpl(@NotNull EntityPlayer wrapped) {
        super((EntityLivingBase) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    @NotNull
    public GameProfile getGameProfile() {
        GameProfile gameProfileFunc_146103_bH = getWrapped().func_146103_bH();
        Intrinsics.checkExpressionValueIsNotNull(gameProfileFunc_146103_bH, "wrapped.gameProfile");
        return gameProfileFunc_146103_bH;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    @Nullable
    public IEntity getFishEntity() {
        Entity entity = getWrapped().field_71104_cf;
        if (entity != null) {
            return new EntityImpl(entity);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    @NotNull
    public IFoodStats getFoodStats() {
        FoodStats foodStatsFunc_71024_bL = getWrapped().func_71024_bL();
        Intrinsics.checkExpressionValueIsNotNull(foodStatsFunc_71024_bL, "wrapped.foodStats");
        return new FoodStatsImpl(foodStatsFunc_71024_bL);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public double getPrevChasingPosY() {
        return getWrapped().field_71096_bN;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public int getSleepTimer() {
        return getWrapped().field_71076_b;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void setSleepTimer(int i) {
        getWrapped().field_71076_b = i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public boolean getSleeping() {
        return getWrapped().field_71083_bS;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void setSleeping(boolean z) {
        getWrapped().field_71083_bS = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public boolean isPlayerSleeping() {
        return getWrapped().func_70608_bn();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public float getSpeedInAir() {
        return getWrapped().field_71102_ce;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void setSpeedInAir(float f) {
        getWrapped().field_71102_ce = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public float getCameraYaw() {
        return getWrapped().field_71109_bG;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void setCameraYaw(float f) {
        getWrapped().field_71109_bG = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public float getSwingProgress() {
        return getWrapped().field_70733_aJ;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public boolean isBlocking() {
        return getWrapped().func_184585_cz();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public int getItemInUseCount() {
        return getWrapped().func_184605_cv();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void setItemInUseCount(int i) {
        getWrapped().field_184628_bn = i;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    @Nullable
    public IItemStack getItemInUse() {
        ItemStack itemStackFunc_184607_cu = getWrapped().func_184607_cu();
        if (itemStackFunc_184607_cu != null) {
            return new ItemStackImpl(itemStackFunc_184607_cu);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    @NotNull
    public IPlayerCapabilities getCapabilities() {
        PlayerCapabilities playerCapabilities = getWrapped().field_71075_bZ;
        Intrinsics.checkExpressionValueIsNotNull(playerCapabilities, "wrapped.capabilities");
        return new PlayerCapabilitiesImpl(playerCapabilities);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    @Nullable
    public IItemStack getHeldItem() {
        ItemStack itemStackFunc_184614_ca = getWrapped().func_184614_ca();
        if (itemStackFunc_184614_ca != null) {
            return new ItemStackImpl(itemStackFunc_184614_ca);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public boolean isUsingItem() {
        return getWrapped().func_184587_cr();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    @NotNull
    public IContainer getInventoryContainer() {
        Container container = getWrapped().field_71069_bz;
        Intrinsics.checkExpressionValueIsNotNull(container, "wrapped.inventoryContainer");
        return new ContainerImpl(container);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    @NotNull
    public IInventoryPlayer getInventory() {
        InventoryPlayer inventoryPlayer = getWrapped().field_71071_by;
        Intrinsics.checkExpressionValueIsNotNull(inventoryPlayer, "wrapped.inventory");
        return new InventoryPlayerImpl(inventoryPlayer);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    @Nullable
    public IContainer getOpenContainer() {
        Container container = getWrapped().field_71070_bA;
        if (container != null) {
            return new ContainerImpl(container);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public int getItemInUseDuration() {
        return getWrapped().func_184612_cw();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    @NotNull
    public String getDisplayNameString() {
        String displayNameString = getWrapped().getDisplayNameString();
        Intrinsics.checkExpressionValueIsNotNull(displayNameString, "wrapped.displayNameString");
        return displayNameString;
    }

    public boolean getSpectator() {
        return getWrapped().func_175149_v();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void stopUsingItem() {
        getWrapped().func_184597_cx();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void onCriticalHit(@NotNull IEntity entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        getWrapped().func_71009_b(((EntityImpl) entity).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void setItemInUse(int i) {
        setItemInUseCount(i);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void onEnchantmentCritical(@NotNull IEntityLivingBase entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        getWrapped().func_71047_c((EntityLivingBase) ((EntityLivingBaseImpl) entity).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void attackTargetEntityWithCurrentItem(@NotNull IEntity entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        getWrapped().func_71059_n(((EntityImpl) entity).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void fall(float f, float f2) {
        getWrapped().func_180430_e(f, f2);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void triggerAchievement(@NotNull IStatBase stat) {
        Intrinsics.checkParameterIsNotNull(stat, "stat");
        getWrapped().func_71029_a(((StatBaseImpl) stat).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void jump() {
        getWrapped().func_70664_aZ();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public float getCooledAttackStrength(float f) {
        return getWrapped().func_184825_o(f);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer
    public void resetCooldown() {
        getWrapped().func_184821_cY();
    }
}
