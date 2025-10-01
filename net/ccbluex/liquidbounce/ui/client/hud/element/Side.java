package net.ccbluex.liquidbounce.p005ui.client.hud.element;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\r\u0018\ufffd\ufffd \u000f2\u00020\u0001:\u0003\u000f\u0010\u0011B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "", "horizontal", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "vertical", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;)V", "getHorizontal", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "setHorizontal", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;)V", "getVertical", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "setVertical", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;)V", "Companion", "Horizontal", "Vertical", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side.class */
public final class Side {

    @NotNull
    private Horizontal horizontal;

    @NotNull
    private Vertical vertical;
    public static final Companion Companion = new Companion(null);

    @NotNull
    public final Horizontal getHorizontal() {
        return this.horizontal;
    }

    public final void setHorizontal(@NotNull Horizontal horizontal) {
        Intrinsics.checkParameterIsNotNull(horizontal, "<set-?>");
        this.horizontal = horizontal;
    }

    @NotNull
    public final Vertical getVertical() {
        return this.vertical;
    }

    public final void setVertical(@NotNull Vertical vertical) {
        Intrinsics.checkParameterIsNotNull(vertical, "<set-?>");
        this.vertical = vertical;
    }

    public Side(@NotNull Horizontal horizontal, @NotNull Vertical vertical) {
        Intrinsics.checkParameterIsNotNull(horizontal, "horizontal");
        Intrinsics.checkParameterIsNotNull(vertical, "vertical");
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Companion;", "", "()V", "default", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        /* renamed from: default, reason: not valid java name */
        public final Side m1697default() {
            return new Side(Horizontal.LEFT, Vertical.UP);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\ufffd\ufffd \n2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "", "sideName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSideName", "()Ljava/lang/String;", "LEFT", "MIDDLE", "RIGHT", "Companion", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal.class */
    public enum Horizontal {
        LEFT("Left"),
        MIDDLE("Middle"),
        RIGHT("Right");


        @NotNull
        private final String sideName;
        public static final Companion Companion = new Companion(null);

        @JvmStatic
        @Nullable
        public static final Horizontal getByName(@NotNull String str) {
            return Companion.getByName(str);
        }

        @NotNull
        public final String getSideName() {
            return this.sideName;
        }

        Horizontal(String str) {
            this.sideName = str;
        }

        @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal$Companion;", "", "()V", "getByName", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "name", "", LiquidBounce.CLIENT_NAME})
        /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal$Companion.class */
        public static final class Companion {
            private Companion() {
            }

            public Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @JvmStatic
            @Nullable
            public final Horizontal getByName(@NotNull String name) {
                Intrinsics.checkParameterIsNotNull(name, "name");
                for (Horizontal horizontal : Horizontal.values()) {
                    if (Intrinsics.areEqual(horizontal.getSideName(), name)) {
                        return horizontal;
                    }
                }
                return null;
            }
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\ufffd\ufffd \n2\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "", "sideName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSideName", "()Ljava/lang/String;", "UP", "MIDDLE", "DOWN", "Companion", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical.class */
    public enum Vertical {
        UP("Up"),
        MIDDLE("Middle"),
        DOWN("Down");


        @NotNull
        private final String sideName;
        public static final Companion Companion = new Companion(null);

        @JvmStatic
        @Nullable
        public static final Vertical getByName(@NotNull String str) {
            return Companion.getByName(str);
        }

        @NotNull
        public final String getSideName() {
            return this.sideName;
        }

        Vertical(String str) {
            this.sideName = str;
        }

        @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical$Companion;", "", "()V", "getByName", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "name", "", LiquidBounce.CLIENT_NAME})
        /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical$Companion.class */
        public static final class Companion {
            private Companion() {
            }

            public Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @JvmStatic
            @Nullable
            public final Vertical getByName(@NotNull String name) {
                Intrinsics.checkParameterIsNotNull(name, "name");
                for (Vertical vertical : Vertical.values()) {
                    if (Intrinsics.areEqual(vertical.getSideName(), name)) {
                        return vertical;
                    }
                }
                return null;
            }
        }
    }
}
