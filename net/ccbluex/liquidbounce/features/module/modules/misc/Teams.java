package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemArmor;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/Teams;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "armorColorValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getArmorColorValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorValue", "gommeSWValue", "scoreboardValue", "isInYourTeam", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Teams", description = "Prevents Killaura from attacking team mates.", category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/Teams.class */
public final class Teams extends Module {
    private final BoolValue scoreboardValue = new BoolValue("ScoreboardTeam", true);
    private final BoolValue colorValue = new BoolValue("Color", true);
    private final BoolValue gommeSWValue = new BoolValue("GommeSW", false);

    @NotNull
    private final BoolValue armorColorValue = new BoolValue("ArmorColor", false);

    @NotNull
    public final BoolValue getArmorColorValue() {
        return this.armorColorValue;
    }

    public final boolean isInYourTeam(@NotNull IEntityLivingBase entity) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            return false;
        }
        if (((Boolean) this.scoreboardValue.get()).booleanValue() && thePlayer.getTeam() != null && entity.getTeam() != null) {
            ITeam team = thePlayer.getTeam();
            if (team == null) {
                Intrinsics.throwNpe();
            }
            ITeam team2 = entity.getTeam();
            if (team2 == null) {
                Intrinsics.throwNpe();
            }
            if (team.isSameTeam(team2)) {
                return true;
            }
        }
        IIChatComponent displayName = thePlayer.getDisplayName();
        if (((Boolean) this.armorColorValue.get()).booleanValue()) {
            IEntityPlayer iEntityPlayerAsEntityPlayer = entity.asEntityPlayer();
            if (thePlayer.getInventory().getArmorInventory().get(3) != null && iEntityPlayerAsEntityPlayer.getInventory().getArmorInventory().get(3) != null) {
                IItemStack iItemStack = (IItemStack) thePlayer.getInventory().getArmorInventory().get(3);
                if (iItemStack == null) {
                    Intrinsics.throwNpe();
                }
                IItem item = iItemStack.getItem();
                if (item == null) {
                    Intrinsics.throwNpe();
                }
                IItemArmor iItemArmorAsItemArmor = item.asItemArmor();
                IItemStack iItemStack2 = (IItemStack) iEntityPlayerAsEntityPlayer.getInventory().getArmorInventory().get(3);
                IItem item2 = iItemStack.getItem();
                if (item2 == null) {
                    Intrinsics.throwNpe();
                }
                IItemArmor iItemArmorAsItemArmor2 = item2.asItemArmor();
                int color = iItemArmorAsItemArmor.getColor(iItemStack);
                if (iItemStack2 == null) {
                    Intrinsics.throwNpe();
                }
                if (color == iItemArmorAsItemArmor2.getColor(iItemStack2)) {
                    return true;
                }
            }
        }
        if (((Boolean) this.gommeSWValue.get()).booleanValue() && displayName != null && entity.getDisplayName() != null) {
            IIChatComponent displayName2 = entity.getDisplayName();
            if (displayName2 == null) {
                Intrinsics.throwNpe();
            }
            String strReplace$default = StringsKt.replace$default(displayName2.getFormattedText(), "\u00a7r", "", false, 4, (Object) null);
            String strReplace$default2 = StringsKt.replace$default(displayName.getFormattedText(), "\u00a7r", "", false, 4, (Object) null);
            if (StringsKt.startsWith$default(strReplace$default, "T", false, 2, (Object) null) && StringsKt.startsWith$default(strReplace$default2, "T", false, 2, (Object) null) && Character.isDigit(strReplace$default.charAt(1)) && Character.isDigit(strReplace$default2.charAt(1))) {
                return strReplace$default.charAt(1) == strReplace$default2.charAt(1);
            }
        }
        if (((Boolean) this.colorValue.get()).booleanValue() && displayName != null && entity.getDisplayName() != null) {
            IIChatComponent displayName3 = entity.getDisplayName();
            if (displayName3 == null) {
                Intrinsics.throwNpe();
            }
            return StringsKt.startsWith$default(StringsKt.replace$default(displayName3.getFormattedText(), "\u00a7r", "", false, 4, (Object) null), new StringBuilder().append('\u00a7').append(StringsKt.replace$default(displayName.getFormattedText(), "\u00a7r", "", false, 4, (Object) null).charAt(1)).toString(), false, 2, (Object) null);
        }
        return false;
    }
}
