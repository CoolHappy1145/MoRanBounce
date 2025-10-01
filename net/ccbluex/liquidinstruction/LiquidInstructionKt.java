package net.ccbluex.liquidinstruction;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JLabel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.TextStreamsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\u001a\u0006\u0010\ufffd\ufffd\u001a\u00020\u0001\u00a8\u0006\u0002"}, m27d2 = {"main", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidinstruction/LiquidInstructionKt.class */
public final class LiquidInstructionKt {
    public static void main(String[] strArr) {
        main();
    }

    public static final void main() {
        JFrame jFrame = new JFrame("LiquidBounce | Installation");
        jFrame.setDefaultCloseOperation(3);
        jFrame.setLayout(new BorderLayout());
        jFrame.setResizable(false);
        jFrame.setAlwaysOnTop(true);
        InputStream resourceAsStream = LiquidBounce.class.getResourceAsStream("/instructions.html");
        Intrinsics.checkExpressionValueIsNotNull(resourceAsStream, "LiquidBounce::class.java\u2026eam(\"/instructions.html\")");
        String text = TextStreamsKt.readText(new InputStreamReader(resourceAsStream, Charsets.UTF_8));
        String string = LiquidBounce.INSTANCE.getClass().getClassLoader().getResource("assets").toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "LiquidBounce.javaClass.c\u2026urce(\"assets\").toString()");
        jFrame.add(new JLabel(StringsKt.replace$default(text, "{assets}", string, false, 4, (Object) null)), "Center");
        jFrame.pack();
        jFrame.setLocationRelativeTo((Component) null);
        jFrame.setVisible(true);
    }
}
