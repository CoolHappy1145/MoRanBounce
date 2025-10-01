package net.ccbluex.liquidbounce.p005ui.client.clickgui.newVer;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0006R\u0010\u0010\u0013\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0014\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0015\u0010\u0006\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/clickgui/newVer/IconManager;", "", "()V", "add", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getAdd", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "back", "getBack", "docs", "getDocs", "download", "getDownload", "folder", "getFolder", "online", "getOnline", "reload", "getReload", "removeIcon", "search", "getSearch", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/clickgui/newVer/IconManager.class */
public final class IconManager {
    public static final IconManager INSTANCE = new IconManager();

    @JvmField
    @NotNull
    public static final IResourceLocation removeIcon = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("liquidbounce/loserline/clickgui/error.png");

    @NotNull
    private static final IResourceLocation add = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("liquidbounce/loserline/clickgui/import.png");

    @NotNull
    private static final IResourceLocation back = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("liquidbounce/loserline/clickgui/back.png");

    @NotNull
    private static final IResourceLocation docs = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("liquidbounce/loserline/clickgui/docs.png");

    @NotNull
    private static final IResourceLocation download = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("liquidbounce/loserline/clickgui/download.png");

    @NotNull
    private static final IResourceLocation folder = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("liquidbounce/loserline/clickgui/folder.png");

    @NotNull
    private static final IResourceLocation online = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("liquidbounce/loserline/clickgui/online.png");

    @NotNull
    private static final IResourceLocation reload = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("liquidbounce/loserline/clickgui/reload.png");

    @NotNull
    private static final IResourceLocation search = LiquidBounce.INSTANCE.getWrapper().getClassProvider().createResourceLocation("liquidbounce/loserline/clickgui/search.png");

    private IconManager() {
    }

    @NotNull
    public final IResourceLocation getAdd() {
        return add;
    }

    @NotNull
    public final IResourceLocation getBack() {
        return back;
    }

    @NotNull
    public final IResourceLocation getDocs() {
        return docs;
    }

    @NotNull
    public final IResourceLocation getDownload() {
        return download;
    }

    @NotNull
    public final IResourceLocation getFolder() {
        return folder;
    }

    @NotNull
    public final IResourceLocation getOnline() {
        return online;
    }

    @NotNull
    public final IResourceLocation getReload() {
        return reload;
    }

    @NotNull
    public final IResourceLocation getSearch() {
        return search;
    }
}
