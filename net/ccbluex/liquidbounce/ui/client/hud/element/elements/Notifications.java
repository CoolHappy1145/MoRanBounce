package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(name = "Notifications", single = true)
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\n\u0010\f\u001a\u0004\u0018\u00010\rH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notifications;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "exampleNotification", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Notifications.class */
public final class Notifications extends Element {
    private final Notification exampleNotification;

    public Notifications() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Notifications(double d, double d2, float f, @NotNull Side side) {
        super(d, d2, f, side);
        Intrinsics.checkParameterIsNotNull(side, "side");
        this.exampleNotification = new Notification("Notification", "This is an example notification.", NotifyType.INFO, 0, 0, 24, null);
    }

    public Notifications(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0d : d, (i & 2) != 0 ? 30.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.RIGHT, Side.Vertical.DOWN) : side);
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (Notification notification : LiquidBounce.INSTANCE.getHud().getNotifications()) {
            GL11.glPushMatrix();
            if (notification.drawNotification(i)) {
                arrayList.add(notification);
            }
            GL11.glPopMatrix();
            i++;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            LiquidBounce.INSTANCE.getHud().getNotifications().remove((Notification) it.next());
        }
        if (MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.f157mc.getCurrentScreen())) {
            if (!LiquidBounce.INSTANCE.getHud().getNotifications().contains(this.exampleNotification)) {
                LiquidBounce.INSTANCE.getHud().addNotification(this.exampleNotification);
            }
            this.exampleNotification.setFadeState(FadeState.STAY);
            this.exampleNotification.setDisplayTime(System.currentTimeMillis());
            return new Border(-this.exampleNotification.getWidth(), -this.exampleNotification.getHeight(), 0.0f, 0.0f);
        }
        return null;
    }
}
