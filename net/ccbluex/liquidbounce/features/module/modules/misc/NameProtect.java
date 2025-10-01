package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/NameProtect;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "allPlayersValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "fakeNameValue", "Lnet/ccbluex/liquidbounce/value/TextValue;", "skinProtectValue", "onText", "", "event", "Lnet/ccbluex/liquidbounce/event/TextEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "NameProtect", description = "Changes playernames clientside.", category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/NameProtect.class */
public final class NameProtect extends Module {

    @JvmField
    @NotNull
    public final BoolValue allPlayersValue = new BoolValue("AllPlayers", false);

    @JvmField
    @NotNull
    public final BoolValue skinProtectValue = new BoolValue("SkinProtect", true);
    private final TextValue fakeNameValue = new TextValue("FakeName", "&cMe");

    @EventTarget(ignoreCondition = true)
    public final void onText(@NotNull TextEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            String text = event.getText();
            if (text == null) {
                Intrinsics.throwNpe();
            }
            if (StringsKt.contains$default((CharSequence) text, (CharSequence) "\u00a78[\u00a79\u00a7lLiquidSense\u00a78] \u00a73", false, 2, (Object) null)) {
                return;
            }
            FriendsConfig friendsConfig = LiquidBounce.INSTANCE.getFileManager().friendsConfig;
            Intrinsics.checkExpressionValueIsNotNull(friendsConfig, "LiquidBounce.fileManager.friendsConfig");
            for (FriendsConfig.Friend friend : friendsConfig.getFriends()) {
                String text2 = event.getText();
                Intrinsics.checkExpressionValueIsNotNull(friend, "friend");
                String playerName = friend.getPlayerName();
                StringBuilder sb = new StringBuilder();
                String alias = friend.getAlias();
                Intrinsics.checkExpressionValueIsNotNull(alias, "friend.alias");
                event.setText(StringUtils.replace(text2, playerName, sb.append(ColorUtils.translateAlternateColorCodes(alias)).append("\u00a7f").toString()));
            }
            if (!getState()) {
                return;
            }
            event.setText(StringUtils.replace(event.getText(), thePlayer.getName(), ColorUtils.translateAlternateColorCodes((String) this.fakeNameValue.get()) + "\u00a7f"));
            if (((Boolean) this.allPlayersValue.get()).booleanValue()) {
                Iterator it = MinecraftInstance.f157mc.getNetHandler().getPlayerInfoMap().iterator();
                while (it.hasNext()) {
                    event.setText(StringUtils.replace(event.getText(), ((INetworkPlayerInfo) it.next()).getGameProfile().getName(), "Protected User"));
                }
            }
        }
    }
}
