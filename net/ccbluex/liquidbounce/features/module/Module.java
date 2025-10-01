package net.ccbluex.liquidbounce.features.module;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.features.module.modules.misc.CustomSet;
import net.ccbluex.liquidbounce.injection.backend.Backend;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.script.api.ScriptModule;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.Translate;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdT\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0016\u0018\ufffd\ufffd2\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u000e\u0010K\u001a\u00020\u00152\u0006\u0010L\u001a\u00020\u0005J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u0005J\u000e\u0010\u001c\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u0005J\u0016\u0010N\u001a\b\u0012\u0002\b\u0003\u0018\u00010H2\u0006\u0010O\u001a\u00020\u0015H\u0016J\b\u0010P\u001a\u00020\u0005H\u0016J\b\u0010Q\u001a\u00020RH\u0016J\b\u0010S\u001a\u00020RH\u0016J\u0010\u0010T\u001a\u00020R2\u0006\u00109\u001a\u00020\u0005H\u0016J\u0010\u0010>\u001a\u00020\u00152\u0006\u0010M\u001a\u00020\u0005H\u0016J\u0006\u0010L\u001a\u00020RR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005@FX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u000e\u0010\r\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0019\u0010\u0017\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u001d\u0010\u0017\"\u0004\b\u001e\u0010\u001bR\u001a\u0010\u001f\u001a\u00020 X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020 \u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b&\u0010\"R\u001a\u0010'\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b'\u0010\u0007\"\u0004\b(\u0010\tR$\u0010)\u001a\u00020*2\u0006\u0010)\u001a\u00020*@FX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010/\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b0\u0010\u0017\"\u0004\b1\u0010\u001bR\u001a\u00102\u001a\u00020 X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b3\u0010\"\"\u0004\b4\u0010$R\u001a\u00105\u001a\u00020 X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b6\u0010\"\"\u0004\b7\u0010$R$\u00109\u001a\u00020\u00052\u0006\u00108\u001a\u00020\u0005@FX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b:\u0010\u0007\"\u0004\b;\u0010\tR\u0016\u0010<\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b=\u0010\u0017R\u0011\u0010>\u001a\u00020\u00158F\u00a2\u0006\u0006\u001a\u0004\b?\u0010\u0017R\u0011\u0010@\u001a\u00020A\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\bB\u0010CR\u0011\u0010D\u001a\u00020A\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\bE\u0010CR\u001e\u0010F\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030H0G8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bI\u0010J\u00a8\u0006U"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/Module;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "BreakName", "", "getBreakName", "()Z", "setBreakName", "(Z)V", "array", "getArray", "setArray", "canEnable", "category", "Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "getCategory", "()Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "setCategory", "(Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;)V", "colorlessTagName", "", "getColorlessTagName", "()Ljava/lang/String;", "description", "getDescription", "setDescription", "(Ljava/lang/String;)V", "fakeName", "getFakeName", "setFakeName", "higt", "", "getHigt", "()F", "setHigt", "(F)V", "hue", "getHue", "isSupported", "setSupported", "keyBind", "", "getKeyBind", "()I", "setKeyBind", "(I)V", "name", "getName", "setName", "slide", "getSlide", "setSlide", "slideStep", "getSlideStep", "setSlideStep", PropertyDescriptor.VALUE, "state", "getState", "setState", "tag", "getTag", "tagName", "getTagName", "translate", "Lnet/ccbluex/liquidbounce/utils/Skid/Translate;", "getTranslate", "()Lnet/ccbluex/liquidbounce/utils/Skid/Translate;", "valueTranslate", "getValueTranslate", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "()Ljava/util/List;", "breakname", "toggle", "nameBreak", "getValue", "valueName", "handleEvents", "onDisable", "", "onEnable", "onToggle", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/Module.class */
public class Module extends MinecraftInstance implements Listenable {
    private boolean isSupported;

    @NotNull
    private String fakeName;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private ModuleCategory category;
    private int keyBind;
    private final boolean canEnable;
    private float slideStep;
    private boolean state;
    private final float hue;
    private float slide;
    private boolean BreakName;
    private float higt;

    @NotNull
    private final Translate translate = new Translate(0.0f, 0.0f);

    @NotNull
    private final Translate valueTranslate = new Translate(0.0f, 0.0f);
    private boolean array = true;

    public Module() {
        Annotation annotation = getClass().getAnnotation(ModuleInfo.class);
        if (annotation == null) {
            Intrinsics.throwNpe();
        }
        ModuleInfo moduleInfo = (ModuleInfo) annotation;
        this.name = moduleInfo.name();
        this.fakeName = ((moduleInfo.fakeName().length() == 0) || (this instanceof ScriptModule)) ? moduleInfo.name() : moduleInfo.fakeName();
        this.description = moduleInfo.description();
        this.category = moduleInfo.category();
        setKeyBind(moduleInfo.keyBind());
        setArray(moduleInfo.array());
        this.canEnable = moduleInfo.canEnable();
        this.isSupported = ArraysKt.contains(moduleInfo.supportedVersions(), Backend.INSTANCE.getREPRESENTED_BACKEND_VERSION());
        this.hue = (float) Math.random();
    }

    public final boolean isSupported() {
        return this.isSupported;
    }

    public final void setSupported(boolean z) {
        this.isSupported = z;
    }

    @NotNull
    public final String getFakeName() {
        return this.fakeName;
    }

    public final void setFakeName(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.fakeName = str;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.name = str;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.description = str;
    }

    @NotNull
    public final Translate getTranslate() {
        return this.translate;
    }

    @NotNull
    public final Translate getValueTranslate() {
        return this.valueTranslate;
    }

    @NotNull
    public final ModuleCategory getCategory() {
        return this.category;
    }

    public final void setCategory(@NotNull ModuleCategory moduleCategory) {
        Intrinsics.checkParameterIsNotNull(moduleCategory, "<set-?>");
        this.category = moduleCategory;
    }

    public final int getKeyBind() {
        return this.keyBind;
    }

    public final void setKeyBind(int i) {
        this.keyBind = i;
        if (!LiquidBounce.INSTANCE.isStarting()) {
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
        }
    }

    public final boolean getArray() {
        return this.array;
    }

    public final void setArray(boolean z) {
        this.array = z;
        if (!LiquidBounce.INSTANCE.isStarting()) {
            LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
        }
    }

    public final float getSlideStep() {
        return this.slideStep;
    }

    public final void setSlideStep(float f) {
        this.slideStep = f;
    }

    public final boolean getState() {
        return this.state;
    }

    public final void setState(boolean z) {
        if (this.state == z) {
            return;
        }
        if (!LiquidBounce.INSTANCE.isStarting()) {
            if (z) {
                Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(CustomSet.class);
                if (module == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.misc.CustomSet");
                }
                ((CustomSet) module).playSound(true);
                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Module", "Enabled " + this.name, NotifyType.SUCCESS, 0, 0, 24, null));
            } else {
                Module module2 = LiquidBounce.INSTANCE.getModuleManager().getModule(CustomSet.class);
                if (module2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.misc.CustomSet");
                }
                ((CustomSet) module2).playSound(false);
                LiquidBounce.INSTANCE.getHud().addNotification(new Notification("Module", "Disabled " + this.name, NotifyType.ERROR, 0, 0, 24, null));
            }
        }
        if (z) {
            if (this.canEnable) {
                this.state = true;
            }
        } else {
            this.state = false;
        }
        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
    }

    public final float getHue() {
        return this.hue;
    }

    public final float getSlide() {
        return this.slide;
    }

    public final void setSlide(float f) {
        this.slide = f;
    }

    public final boolean getBreakName() {
        return this.BreakName;
    }

    public final void setBreakName(boolean z) {
        this.BreakName = z;
    }

    public final float getHigt() {
        return this.higt;
    }

    public final void setHigt(float f) {
        this.higt = f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public final String getTagName() {
        String str;
        new StringBuilder().append(this.name);
        if (0 == 0) {
            str = "";
        } else {
            StringBuilder sbAppend = new StringBuilder().append(" \u00a77");
            append(null).toString();
            str = sbAppend;
        }
        return append(str).toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public final String getColorlessTagName() {
        String str;
        new StringBuilder().append(this.name);
        if (0 == 0) {
            str = "";
        } else {
            StringBuilder sbAppend = new StringBuilder().append(" ");
            append(ColorUtils.stripColor(null)).toString();
            str = sbAppend;
        }
        return append(str).toString();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @NotNull
    public final String breakname(boolean z) {
        String str = this.name;
        if (z) {
            if (str != null) {
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                switch (lowerCase.hashCode()) {
                    case -2117682893:
                        if (lowerCase.equals("antiblind")) {
                            return "Anti Blind";
                        }
                        break;
                    case -2073748310:
                        if (lowerCase.equals("longjump")) {
                            return "Long Jump";
                        }
                        break;
                    case -1958347623:
                        if (lowerCase.equals("autorespawn")) {
                            return "Auto Respawn";
                        }
                        break;
                    case -1940969246:
                        if (lowerCase.equals("potionsaver")) {
                            return "Potion Saver";
                        }
                        break;
                    case -1745954712:
                        if (lowerCase.equals("keepalive")) {
                            return "Keep Alive";
                        }
                        break;
                    case -1684590384:
                        if (lowerCase.equals("highjump")) {
                            return "High Jump";
                        }
                        break;
                    case -1673170764:
                        if (lowerCase.equals("nofriends")) {
                            return "No Friends";
                        }
                        break;
                    case -1650930624:
                        if (lowerCase.equals("midclick")) {
                            return "Mid Click";
                        }
                        break;
                    case -1641764582:
                        if (lowerCase.equals("speedmine")) {
                            return "Speed Mine";
                        }
                        break;
                    case -1618493804:
                        if (lowerCase.equals("liquidchat")) {
                            return "Liquid Chat";
                        }
                        break;
                    case -1617904379:
                        if (lowerCase.equals("liquidwalk")) {
                            return "Liquid Walk";
                        }
                        break;
                    case -1411744210:
                        if (lowerCase.equals("itemrotate")) {
                            return "Item Rotate";
                        }
                        break;
                    case -1217012392:
                        if (lowerCase.equals("hitbox")) {
                            return "Hit Box";
                        }
                        break;
                    case -1205362161:
                        if (lowerCase.equals("anticactus")) {
                            return "Anti Cactus";
                        }
                        break;
                    case -1202220504:
                        if (lowerCase.equals("hytrun")) {
                            return "HYT Run";
                        }
                        break;
                    case -1077118994:
                        if (lowerCase.equals("fastbow")) {
                            return "Fast Bow";
                        }
                        break;
                    case -1077100629:
                        if (lowerCase.equals("fastuse")) {
                            return "Fast Use";
                        }
                        break;
                    case -1040193391:
                        if (lowerCase.equals("noclip")) {
                            return "No Clip";
                        }
                        break;
                    case -1040114500:
                        if (lowerCase.equals("nofall")) {
                            return "No Fall";
                        }
                        break;
                    case -1039716542:
                        if (lowerCase.equals("noslow")) {
                            return "No Slow";
                        }
                        break;
                    case -1018771300:
                        if (lowerCase.equals("icespeed")) {
                            return "Ice Speed";
                        }
                        break;
                    case -1006627833:
                        if (lowerCase.equals("bufferspeed")) {
                            return "Buffer Speed";
                        }
                        break;
                    case -846896572:
                        if (lowerCase.equals("antiafk")) {
                            return "Anti AFK";
                        }
                        break;
                    case -846895323:
                        if (lowerCase.equals("antibot")) {
                            return "Anti Bot";
                        }
                        break;
                    case -815567418:
                        if (lowerCase.equals("targethud")) {
                            return "Target HUD";
                        }
                        break;
                    case -664576555:
                        if (lowerCase.equals("blockesp")) {
                            return "Block ESP";
                        }
                        break;
                    case -664575802:
                        if (lowerCase.equals("blockfly")) {
                            return "Block Fly";
                        }
                        break;
                    case -646312517:
                        if (lowerCase.equals("autobow")) {
                            return "Auto Bow";
                        }
                        break;
                    case -646299066:
                        if (lowerCase.equals("autopot")) {
                            return "Auto Potion";
                        }
                        break;
                    case -604554815:
                        if (lowerCase.equals("killaura")) {
                            return "Kill Aura";
                        }
                        break;
                    case -603799069:
                        if (lowerCase.equals("freecam")) {
                            return "Free Cam";
                        }
                        break;
                    case -584901197:
                        if (lowerCase.equals("abortbreaking")) {
                            return "Abort Breaking";
                        }
                        break;
                    case -483845667:
                        if (lowerCase.equals("antifall")) {
                            return "Anti Fall";
                        }
                        break;
                    case -360334121:
                        if (lowerCase.equals("pingspoof")) {
                            return "Ping Spoof";
                        }
                        break;
                    case -260519514:
                        if (lowerCase.equals("faststairs")) {
                            return "Fast Stairs";
                        }
                        break;
                    case -246859590:
                        if (lowerCase.equals("consolespammer")) {
                            return "Console Spammer";
                        }
                        break;
                    case -60613723:
                        if (lowerCase.equals("invcleaner")) {
                            return "Inv Cleaner";
                        }
                        break;
                    case -58684039:
                        if (lowerCase.equals("lagback")) {
                            return "Lag Back";
                        }
                        break;
                    case -24159709:
                        if (lowerCase.equals("fastbreak")) {
                            return "Fast Break";
                        }
                        break;
                    case -23410727:
                        if (lowerCase.equals("fastclimb")) {
                            return "Fast Climb";
                        }
                        break;
                    case -11412949:
                        if (lowerCase.equals("fastplace")) {
                            return "Fast Place";
                        }
                        break;
                    case 105011699:
                        if (lowerCase.equals("noweb")) {
                            return "No Web";
                        }
                        break;
                    case 257638572:
                        if (lowerCase.equals("bowaimbot")) {
                            return "Bow Aimbot";
                        }
                        break;
                    case 326577179:
                        if (lowerCase.equals("cheststealer")) {
                            return "Chest Stealer";
                        }
                        break;
                    case 375554528:
                        if (lowerCase.equals("targetstrafe")) {
                            return "Target Strafe";
                        }
                        break;
                    case 633626763:
                        if (lowerCase.equals("autoweapon")) {
                            return "Auto Weapon";
                        }
                        break;
                    case 786282275:
                        if (lowerCase.equals("blockoverlay")) {
                            return "Block Overlay";
                        }
                        break;
                    case 865360868:
                        if (lowerCase.equals("chestaura")) {
                            return "Chest Aura";
                        }
                        break;
                    case 873482198:
                        if (lowerCase.equals("blockwalk")) {
                            return "Block Walk";
                        }
                        break;
                    case 1068975772:
                        if (lowerCase.equals("keepcontainer")) {
                            return "Keep Container";
                        }
                        break;
                    case 1271744037:
                        if (lowerCase.equals("pointeresp")) {
                            return "Pointer ESP";
                        }
                        break;
                    case 1424958241:
                        if (lowerCase.equals("memoryfixer")) {
                            return "Memory Fixer";
                        }
                        break;
                    case 1439261831:
                        if (lowerCase.equals("autofish")) {
                            return "Auto Fish";
                        }
                        break;
                    case 1439317007:
                        if (lowerCase.equals("autohead")) {
                            return "Auto Head";
                        }
                        break;
                    case 1439392349:
                        if (lowerCase.equals("autojump")) {
                            return "Auto Jump";
                        }
                        break;
                    case 1439654950:
                        if (lowerCase.equals("autosoup")) {
                            return "Auto Soup";
                        }
                        break;
                    case 1439684551:
                        if (lowerCase.equals("autotool")) {
                            return "Auto Tool";
                        }
                        break;
                    case 1439760376:
                        if (lowerCase.equals("autowalk")) {
                            return "Auto Walk";
                        }
                        break;
                    case 1663088880:
                        if (lowerCase.equals("autoarmor")) {
                            return "Auto Armor";
                        }
                        break;
                    case 1679863214:
                        if (lowerCase.equals("autosword")) {
                            return "Auto Sword";
                        }
                        break;
                    case 1765327574:
                        if (lowerCase.equals("safewalk")) {
                            return "Safe Walk";
                        }
                        break;
                    case 1841525028:
                        if (lowerCase.equals("nametags")) {
                            return "Name Tags";
                        }
                        break;
                    case 1960145730:
                        if (lowerCase.equals("invmove")) {
                            return "Inv Move";
                        }
                        break;
                    case 2096371902:
                        if (lowerCase.equals("playerface")) {
                            return "Player Face";
                        }
                        break;
                    case 2099756966:
                        if (lowerCase.equals("autoclicker")) {
                            return "Auto Clicker";
                        }
                        break;
                    case 2116211087:
                        if (lowerCase.equals("itemesp")) {
                            return "Item ESP";
                        }
                        break;
                    case 2128847325:
                        if (lowerCase.equals("noswing")) {
                            return "No Swing";
                        }
                        break;
                    case 2144066940:
                        if (lowerCase.equals("skinderp")) {
                            return "Skin Derp";
                        }
                        break;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
        }
        return str;
    }

    public final void toggle() {
        setState(!this.state);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public String tagName(boolean z) {
        String str;
        String str2;
        if (!z || StringsKt.contains$default((CharSequence) this.name, (CharSequence) "ScriptModule", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) this.fakeName, (CharSequence) "ScriptModule", false, 2, (Object) null)) {
            new StringBuilder().append(this.name);
            if (0 == 0) {
                str = "";
            } else {
                StringBuilder sbAppend = new StringBuilder().append("\u00a77 - ");
                append(null).toString();
                str = sbAppend;
            }
            return append(str).toString();
        }
        new StringBuilder().append(this.fakeName);
        if (0 == 0) {
            str2 = "";
        } else {
            StringBuilder sbAppend2 = new StringBuilder().append("\u00a77 - ");
            append(null).toString();
            str2 = sbAppend2;
        }
        return append(str2).toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public final String colorlessTagName(boolean z) {
        String str;
        String str2;
        if (z) {
            new StringBuilder().append(this.fakeName);
            if (0 == 0) {
                str2 = "";
            } else {
                StringBuilder sbAppend = new StringBuilder().append("[");
                append(ColorUtils.stripColor(null)).toString();
                str2 = sbAppend;
            }
            return append(str2).toString();
        }
        new StringBuilder().append(this.name);
        if (0 == 0) {
            str = "";
        } else {
            StringBuilder sbAppend2 = new StringBuilder().append("\u00a7[");
            append(ColorUtils.stripColor(null)).toString();
            str = sbAppend2;
        }
        return append(str).toString();
    }

    @NotNull
    public final String fakeName(boolean z) {
        if (z && !StringsKt.contains$default((CharSequence) this.name, (CharSequence) "ScriptModule", false, 2, (Object) null) && !StringsKt.contains$default((CharSequence) this.fakeName, (CharSequence) "ScriptModule", false, 2, (Object) null)) {
            return this.fakeName;
        }
        return this.name;
    }

    @Nullable
    public Value getValue(@NotNull String valueName) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(valueName, "valueName");
        Iterator it = getValues().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            Object next = it.next();
            if (StringsKt.equals(((Value) next).getName(), valueName, true)) {
                obj = next;
                break;
            }
        }
        return (Value) obj;
    }

    @NotNull
    public List getValues() {
        Field[] declaredFields = getClass().getDeclaredFields();
        Intrinsics.checkExpressionValueIsNotNull(declaredFields, "javaClass.declaredFields");
        ArrayList arrayList = new ArrayList(declaredFields.length);
        for (Field valueField : declaredFields) {
            Intrinsics.checkExpressionValueIsNotNull(valueField, "valueField");
            valueField.setAccessible(true);
            arrayList.add(valueField.get(this));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            if (obj instanceof Value) {
                arrayList3.add(obj);
            }
        }
        return arrayList3;
    }

    @Override // net.ccbluex.liquidbounce.event.Listenable
    public boolean handleEvents() {
        return this.state;
    }
}
