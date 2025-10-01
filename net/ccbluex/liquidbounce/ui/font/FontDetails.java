package net.ccbluex.liquidbounce.p005ui.font;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/font/FontDetails.class */
public @interface FontDetails {
    String fontName();

    int fontSize() default -1;
}
