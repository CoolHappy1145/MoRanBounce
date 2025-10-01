package net.ccbluex.liquidbounce.injection.backend;

import java.util.Collection;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.entity.IEnumCreatureAttribute;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumHand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdp\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\tH\u0016J\u0010\u0010?\u001a\u00020\u001f2\u0006\u0010@\u001a\u00020AH\u0016J\u0012\u0010B\u001a\u0004\u0018\u00010\t2\u0006\u0010C\u001a\u00020DH\u0016J\u0012\u0010E\u001a\u0004\u0018\u00010F2\u0006\u0010G\u001a\u00020\u001bH\u0016J\b\u0010H\u001a\u00020FH\u0016J\u0010\u0010I\u001a\u00020\u001f2\u0006\u0010C\u001a\u00020DH\u0016J\u0010\u0010J\u001a\u00020=2\u0006\u0010K\u001a\u00020\u001bH\u0016J\u0010\u0010L\u001a\u00020=2\u0006\u0010M\u001a\u00020NH\u0016J\b\u0010O\u001a\u00020=H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR$\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\u0010\"\u0004\b\u0019\u0010\u0012R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010 R\u0014\u0010!\u001a\u00020\u001f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b!\u0010 R$\u0010\"\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b#\u0010\u0010\"\u0004\b$\u0010\u0012R\u0014\u0010%\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b&\u0010\u0010R\u0014\u0010'\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010\u0010R\u0014\u0010)\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b*\u0010\u0010R$\u0010+\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b,\u0010\u0010\"\u0004\b-\u0010\u0012R$\u0010.\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b/\u0010\u0010\"\u0004\b0\u0010\u0012R$\u00101\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b2\u0010\u0010\"\u0004\b3\u0010\u0012R\u0014\u00104\u001a\u00020\u001b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b5\u0010\u001dR\u0016\u00106\u001a\u0004\u0018\u0001078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b8\u00109R\u0014\u0010:\u001a\u00020\u001b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b;\u0010\u001d\u00a8\u0006P"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/EntityLivingBaseImpl;", "T", "Lnet/minecraft/entity/EntityLivingBase;", "Lnet/ccbluex/liquidbounce/injection/backend/EntityImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "wrapped", "(Lnet/minecraft/entity/EntityLivingBase;)V", "activePotionEffects", "", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "getActivePotionEffects", "()Ljava/util/Collection;", PropertyDescriptor.VALUE, "", "cameraPitch", "getCameraPitch", "()F", "setCameraPitch", "(F)V", "creatureAttribute", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/IEnumCreatureAttribute;", "getCreatureAttribute", "()Lnet/ccbluex/liquidbounce/api/minecraft/entity/IEnumCreatureAttribute;", "health", "getHealth", "setHealth", "hurtTime", "", "getHurtTime", "()I", "isOnLadder", "", "()Z", "isSwingInProgress", "jumpMovementFactor", "getJumpMovementFactor", "setJumpMovementFactor", "maxHealth", "getMaxHealth", "moveForward", "getMoveForward", "moveStrafing", "getMoveStrafing", "prevRotationYawHead", "getPrevRotationYawHead", "setPrevRotationYawHead", "renderYawOffset", "getRenderYawOffset", "setRenderYawOffset", "rotationYawHead", "getRotationYawHead", "setRotationYawHead", "swingProgressInt", "getSwingProgressInt", "team", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "getTeam", "()Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "totalArmorValue", "getTotalArmorValue", "addPotionEffect", "", "effect", "canEntityBeSeen", "it", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "getActivePotionEffect", "potion", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotion;", "getEquipmentInSlot", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "index", "getHeldItemOffhand", "isPotionActive", "removePotionEffectClient", "id", "setActiveHandBase", "Hand", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", "swingItem", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/EntityLivingBaseImpl.class */
public class EntityLivingBaseImpl extends EntityImpl implements IEntityLivingBase {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EntityLivingBaseImpl(@NotNull EntityLivingBase wrapped) {
        super((Entity) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public float getMaxHealth() {
        return getWrapped().func_110138_aP();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public float getPrevRotationYawHead() {
        return getWrapped().field_70758_at;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public void setPrevRotationYawHead(float f) {
        getWrapped().field_70758_at = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public float getRenderYawOffset() {
        return getWrapped().field_70761_aq;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public void setRenderYawOffset(float f) {
        getWrapped().field_70761_aq = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    @NotNull
    public Collection getActivePotionEffects() {
        Collection collectionFunc_70651_bq = getWrapped().func_70651_bq();
        Intrinsics.checkExpressionValueIsNotNull(collectionFunc_70651_bq, "wrapped.activePotionEffects");
        return new WrappedCollection(collectionFunc_70651_bq, EntityLivingBaseImpl$activePotionEffects$1.INSTANCE, EntityLivingBaseImpl$activePotionEffects$2.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public boolean isSwingInProgress() {
        return getWrapped().field_82175_bq;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public float getCameraPitch() {
        return getWrapped().field_70726_aT;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public void setCameraPitch(float f) {
        getWrapped().field_70726_aT = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    @Nullable
    public ITeam getTeam() {
        Team teamFunc_96124_cp = getWrapped().func_96124_cp();
        if (teamFunc_96124_cp != null) {
            return new TeamImpl(teamFunc_96124_cp);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    @NotNull
    public IEnumCreatureAttribute getCreatureAttribute() {
        EnumCreatureAttribute enumCreatureAttributeFunc_70668_bt = getWrapped().func_70668_bt();
        Intrinsics.checkExpressionValueIsNotNull(enumCreatureAttributeFunc_70668_bt, "wrapped.creatureAttribute");
        return new EnumCreatureAttributeImpl(enumCreatureAttributeFunc_70668_bt);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public int getHurtTime() {
        return getWrapped().field_70737_aN;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public boolean isOnLadder() {
        return getWrapped().func_70617_f_();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public float getJumpMovementFactor() {
        return getWrapped().field_70747_aH;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public void setJumpMovementFactor(float f) {
        getWrapped().field_70747_aH = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public float getMoveStrafing() {
        return getWrapped().field_70702_br;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public float getMoveForward() {
        return getWrapped().field_191988_bg;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public float getHealth() {
        return getWrapped().func_110143_aJ();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public void setHealth(float f) {
        getWrapped().func_70606_j(f);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public float getRotationYawHead() {
        return getWrapped().field_70759_as;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public void setRotationYawHead(float f) {
        getWrapped().field_70759_as = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public int getTotalArmorValue() {
        return getWrapped().func_70658_aO();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public int getSwingProgressInt() {
        return getWrapped().field_110158_av;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public boolean canEntityBeSeen(@NotNull IEntity it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        return getWrapped().func_70685_l(((EntityImpl) it).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public boolean isPotionActive(@NotNull IPotion potion) {
        Intrinsics.checkParameterIsNotNull(potion, "potion");
        return getWrapped().func_70644_a(((PotionImpl) potion).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    @NotNull
    public IItemStack getHeldItemOffhand() {
        ItemStack itemStackFunc_184592_cb = getWrapped().func_184592_cb();
        Intrinsics.checkExpressionValueIsNotNull(itemStackFunc_184592_cb, "wrapped.heldItemOffhand");
        return new ItemStackImpl(itemStackFunc_184592_cb);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public void swingItem() {
        getWrapped().func_184609_a(EnumHand.MAIN_HAND);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    @Nullable
    public IPotionEffect getActivePotionEffect(@NotNull IPotion potion) {
        Intrinsics.checkParameterIsNotNull(potion, "potion");
        PotionEffect potionEffectFunc_70660_b = getWrapped().func_70660_b(((PotionImpl) potion).getWrapped());
        if (potionEffectFunc_70660_b != null) {
            return new PotionEffectImpl(potionEffectFunc_70660_b);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public void setActiveHandBase(@NotNull WEnumHand Hand) {
        EnumHand enumHand;
        Intrinsics.checkParameterIsNotNull(Hand, "Hand");
        EntityLivingBase wrapped = getWrapped();
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$17[Hand.ordinal()]) {
            case 1:
                enumHand = EnumHand.MAIN_HAND;
                break;
            case 2:
                enumHand = EnumHand.OFF_HAND;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        wrapped.func_184598_c(enumHand);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public void removePotionEffectClient(int i) {
        getWrapped().func_184596_c(Potion.func_188412_a(i));
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    public void addPotionEffect(@NotNull IPotionEffect effect) {
        Intrinsics.checkParameterIsNotNull(effect, "effect");
        getWrapped().func_70690_d(((PotionEffectImpl) effect).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase
    @Nullable
    public IItemStack getEquipmentInSlot(int i) {
        EntityEquipmentSlot entityEquipmentSlot;
        EntityLivingBase wrapped = getWrapped();
        switch (i) {
            case 0:
                entityEquipmentSlot = EntityEquipmentSlot.FEET;
                break;
            case 1:
                entityEquipmentSlot = EntityEquipmentSlot.LEGS;
                break;
            case 2:
                entityEquipmentSlot = EntityEquipmentSlot.CHEST;
                break;
            case 3:
                entityEquipmentSlot = EntityEquipmentSlot.HEAD;
                break;
            case 4:
                entityEquipmentSlot = EntityEquipmentSlot.MAINHAND;
                break;
            case 5:
                entityEquipmentSlot = EntityEquipmentSlot.OFFHAND;
                break;
            default:
                throw new IllegalArgumentException("Invalid armorType " + i);
        }
        ItemStack itemStackFunc_184582_a = wrapped.func_184582_a(entityEquipmentSlot);
        Intrinsics.checkExpressionValueIsNotNull(itemStackFunc_184582_a, "wrapped.getItemStackFrom\u2026.toEntityEquipmentSlot())");
        return new ItemStackImpl(itemStackFunc_184582_a);
    }
}
