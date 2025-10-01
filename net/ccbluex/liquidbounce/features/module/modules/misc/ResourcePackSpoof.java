package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.net.URI;
import java.net.URISyntaxException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketResourcePackStatus;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketResourcePackSend;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/ResourcePackSpoof;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "ResourcePackSpoof", description = "Prevents servers from forcing you to download their resource pack.", category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/ResourcePackSpoof.class */
public final class ResourcePackSpoof extends Module {
    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) throws URISyntaxException {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isSPacketResourcePackSend(event.getPacket())) {
            ISPacketResourcePackSend iSPacketResourcePackSendAsSPacketResourcePackSend = event.getPacket().asSPacketResourcePackSend();
            String url = iSPacketResourcePackSendAsSPacketResourcePackSend.getUrl();
            String hash = iSPacketResourcePackSendAsSPacketResourcePackSend.getHash();
            try {
                boolean zAreEqual = Intrinsics.areEqual("level", new URI(url).getScheme());
                if ((!Intrinsics.areEqual("http", r0)) && (!Intrinsics.areEqual("https", r0)) && !zAreEqual) {
                    throw new URISyntaxException(url, "Wrong protocol");
                }
                if (zAreEqual && (StringsKt.contains$default((CharSequence) url, (CharSequence) "..", false, 2, (Object) null) || !StringsKt.endsWith$default(url, "/resources.zip", false, 2, (Object) null))) {
                    throw new URISyntaxException(url, "Invalid levelstorage resourcepack path");
                }
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createICPacketResourcePackStatus(iSPacketResourcePackSendAsSPacketResourcePackSend.getHash(), ICPacketResourcePackStatus.WAction.ACCEPTED));
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createICPacketResourcePackStatus(iSPacketResourcePackSendAsSPacketResourcePackSend.getHash(), ICPacketResourcePackStatus.WAction.SUCCESSFULLY_LOADED));
            } catch (URISyntaxException e) {
                ClientUtils.getLogger().error("Failed to handle resource pack", e);
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createICPacketResourcePackStatus(hash, ICPacketResourcePackStatus.WAction.FAILED_DOWNLOAD));
            }
        }
    }
}
