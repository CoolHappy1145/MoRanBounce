package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import com.google.gson.JsonElement;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;

@ElementInfo(name = "Image")
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0005\u001a\u00020\u000fJ\u0010\u0010\u000e\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0005\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "height", "", "image", "Lnet/ccbluex/liquidbounce/value/TextValue;", "resourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "width", "createElement", "", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "setImage", "Ljava/io/File;", "", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Image.class */
public final class Image extends Element {
    private final TextValue image;
    private final IResourceLocation resourceLocation;
    private int width;
    private int height;
    public static final Companion Companion = new Companion(null);

    public Image() {
        super(0.0d, 0.0d, 0.0f, null, 15, null);
        final String str = "Image";
        final String str2 = "";
        this.image = new TextValue(this, str, str2) { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.Image$image$1
            final Image this$0;

            public void onChanged(Object obj, Object obj2) throws IOException {
                onChanged((String) obj, (String) obj2);
            }

            {
                this.this$0 = this;
            }

            @Override // net.ccbluex.liquidbounce.value.TextValue, net.ccbluex.liquidbounce.value.Value
            public void fromJson(@NotNull JsonElement element) throws IOException {
                Intrinsics.checkParameterIsNotNull(element, "element");
                super.fromJson(element);
                if (!(((CharSequence) get()).length() == 0)) {
                    this.this$0.setImage((String) get());
                }
            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) throws IOException {
                Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
                Intrinsics.checkParameterIsNotNull(newValue, "newValue");
                if (!(((CharSequence) get()).length() == 0)) {
                    this.this$0.setImage((String) get());
                }
            }
        };
        this.resourceLocation = MinecraftInstance.classProvider.createResourceLocation(RandomUtils.INSTANCE.randomNumber(128));
        this.width = 64;
        this.height = 64;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image$Companion;", "", "()V", "default", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image;", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Image$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        /* renamed from: default, reason: not valid java name */
        public final Image m1703default() {
            Image image = new Image();
            image.setX(0.0d);
            image.setY(0.0d);
            return image;
        }
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @NotNull
    public Border drawElement() {
        RenderUtils.drawImage(this.resourceLocation, 0, 0, this.width / 2, this.height / 2);
        return new Border(0.0f, 0.0f, this.width / 2.0f, this.height / 2.0f);
    }

    public boolean createElement() {
        File fileOpenFileChooser = MiscUtils.openFileChooser();
        if (fileOpenFileChooser == null) {
            return false;
        }
        if (!fileOpenFileChooser.exists()) {
            MiscUtils.showErrorPopup("Error", "The file does not exist.");
            return false;
        }
        if (fileOpenFileChooser.isDirectory()) {
            MiscUtils.showErrorPopup("Error", "The file is a directory.");
            return false;
        }
        setImage(fileOpenFileChooser);
        return true;
    }

    private final Image setImage(String str) throws IOException {
        try {
            this.image.changeValue(str);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.getDecoder().decode(str));
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
            byteArrayInputStream.close();
            Intrinsics.checkExpressionValueIsNotNull(bufferedImage, "bufferedImage");
            this.width = bufferedImage.getWidth();
            this.height = bufferedImage.getHeight();
            MinecraftInstance.f157mc.getTextureManager().loadTexture(this.resourceLocation, MinecraftInstance.classProvider.createDynamicTexture(bufferedImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @NotNull
    public final Image setImage(@NotNull File image) {
        Intrinsics.checkParameterIsNotNull(image, "image");
        try {
            String strEncodeToString = Base64.getEncoder().encodeToString(Files.readAllBytes(image.toPath()));
            Intrinsics.checkExpressionValueIsNotNull(strEncodeToString, "Base64.getEncoder().enco\u2026AllBytes(image.toPath()))");
            setImage(strEncodeToString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
