package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.features.module.modules.combat.NoFriends;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/EntityUtils.class */
public final class EntityUtils extends MinecraftInstance {
    public static boolean targetInvisible = false;
    public static boolean targetPlayer = true;
    public static boolean targetMobs = true;
    public static boolean targetAnimals = false;
    public static boolean targetDead = false;

    public static boolean isSelected(IEntity iEntity, boolean z) {
        if (!classProvider.isEntityLivingBase(iEntity)) {
            return false;
        }
        if ((targetDead || iEntity.isEntityAlive()) && iEntity != null && !iEntity.equals(f157mc.getThePlayer())) {
            if (targetInvisible || !iEntity.isInvisible()) {
                if (!targetPlayer || !classProvider.isEntityPlayer(iEntity)) {
                    return (targetMobs && isMob(iEntity)) || (targetAnimals && isAnimal(iEntity));
                }
                IEntityPlayer iEntityPlayerAsEntityPlayer = iEntity.asEntityPlayer();
                if (z) {
                    if (AntiBot.isBot(iEntityPlayerAsEntityPlayer)) {
                        return false;
                    }
                    if ((isFriend(iEntityPlayerAsEntityPlayer) && !LiquidBounce.moduleManager.getModule(NoFriends.class).getState()) || iEntityPlayerAsEntityPlayer.isSpectator()) {
                        return false;
                    }
                    Teams teams = (Teams) LiquidBounce.moduleManager.getModule(Teams.class);
                    return (teams.getState() && teams.isInYourTeam(iEntityPlayerAsEntityPlayer)) ? false : true;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean isFriend(IEntity iEntity) {
        return classProvider.isEntityPlayer(iEntity) && iEntity.getName() != null && LiquidBounce.fileManager.friendsConfig.isFriend(ColorUtils.stripColor(iEntity.getName()));
    }

    public static boolean isAnimal(IEntity iEntity) {
        return classProvider.isEntityAnimal(iEntity) || classProvider.isEntitySquid(iEntity) || classProvider.isEntityGolem(iEntity) || classProvider.isEntityBat(iEntity);
    }

    public static boolean isMob(IEntity iEntity) {
        return classProvider.isEntityMob(iEntity) || classProvider.isEntityVillager(iEntity) || classProvider.isEntitySlime(iEntity) || classProvider.isEntityGhast(iEntity) || classProvider.isEntityDragon(iEntity) || classProvider.isEntityShulker(iEntity);
    }

    public static String getName(INetworkPlayerInfo iNetworkPlayerInfo) {
        if (iNetworkPlayerInfo.getDisplayName() != null) {
            return iNetworkPlayerInfo.getDisplayName().getFormattedText();
        }
        ITeam playerTeam = iNetworkPlayerInfo.getPlayerTeam();
        String name = iNetworkPlayerInfo.getGameProfile().getName();
        return playerTeam == null ? name : playerTeam.formatString(name);
    }

    public static int getPing(IEntityPlayer iEntityPlayer) {
        INetworkPlayerInfo playerInfo;
        if (iEntityPlayer == null || (playerInfo = f157mc.getNetHandler().getPlayerInfo(iEntityPlayer.getUniqueID())) == null) {
            return 0;
        }
        return playerInfo.getResponseTime();
    }
}
