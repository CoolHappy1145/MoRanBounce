package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.misc.Spammer;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/SettingsUtils;", "", "()V", "executeScript", "", "script", "", "generateScript", "values", "", "binds", "states", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/SettingsUtils.class */
public final class SettingsUtils {
    public static final SettingsUtils INSTANCE = new SettingsUtils();

    private SettingsUtils() {
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x0434 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0410 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void executeScript(@NotNull String script) {
        String string;
        Intrinsics.checkParameterIsNotNull(script, "script");
        List listLines = StringsKt.lines(script);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listLines) {
            String str = (String) obj;
            if ((str.length() > 0) && !StringsKt.startsWith$default((CharSequence) str, '#', false, 2, (Object) null)) {
                arrayList.add(obj);
            }
        }
        int i = 0;
        for (Object obj2 : arrayList) {
            int i2 = i;
            i++;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str2 = (String) obj2;
            Object[] array = StringsKt.split$default((CharSequence) str2, new String[]{" "}, false, 0, 6, (Object) null).toArray(new String[0]);
            if (array == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            String[] strArr = (String[]) array;
            if (strArr.length <= 1) {
                ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7cSyntax error at line '" + i2 + "' in setting script.\n\u00a78\u00a7lLine: \u00a77" + str2);
            } else {
                String str3 = strArr[0];
                switch (str3.hashCode()) {
                    case -840716943:
                        if (str3.equals("unchat")) {
                            String completeString = StringUtils.toCompleteString(strArr, 1);
                            Intrinsics.checkExpressionValueIsNotNull(completeString, "StringUtils.toCompleteString(args, 1)");
                            ClientUtils.displayChatMessage(ColorUtils.translateAlternateColorCodes(completeString));
                            break;
                        } else if (strArr.length == 3) {
                            ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7cSyntax error at line '" + i2 + "' in setting script.\n\u00a78\u00a7lLine: \u00a77" + str2);
                            break;
                        } else {
                            String str4 = strArr[0];
                            String str5 = strArr[1];
                            String str6 = strArr[2];
                            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(str4);
                            if (module == null) {
                                ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7cModule \u00a7a\u00a7l" + str4 + "\u00a7c was not found!");
                                break;
                            } else if (StringsKt.equals(str5, "toggle", true)) {
                                module.setState(StringsKt.equals(str6, "true", true));
                                ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7a\u00a7l" + module.getName() + " \u00a77was toggled \u00a7c\u00a7l" + (module.getState() ? "on" : "off") + "\u00a77.");
                                break;
                            } else if (StringsKt.equals(str5, "bind", true)) {
                                module.setKeyBind(Keyboard.getKeyIndex(str6));
                                ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7a\u00a7l" + module.getName() + " \u00a77was bound to \u00a7c\u00a7l" + Keyboard.getKeyName(module.getKeyBind()) + "\u00a77.");
                                break;
                            } else {
                                Value value = module.getValue(str5);
                                if (value == null) {
                                    ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7cValue \u00a7a\u00a7l" + str5 + "\u00a7c don't found in module \u00a7a\u00a7l" + str4 + "\u00a7c.");
                                    break;
                                } else {
                                    try {
                                        if (value instanceof BoolValue) {
                                            ((BoolValue) value).changeValue(Boolean.valueOf(Boolean.parseBoolean(str6)));
                                        } else if (value instanceof FloatValue) {
                                            ((FloatValue) value).changeValue(Float.valueOf(Float.parseFloat(str6)));
                                        } else if (value instanceof IntegerValue) {
                                            ((IntegerValue) value).changeValue(Integer.valueOf(Integer.parseInt(str6)));
                                        } else if (value instanceof TextValue) {
                                            ((TextValue) value).changeValue(str6);
                                        } else if (value instanceof ListValue) {
                                            ((ListValue) value).changeValue(str6);
                                        }
                                        ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7a\u00a7l" + module.getName() + "\u00a77 value \u00a78\u00a7l" + value.getName() + "\u00a77 set to \u00a7c\u00a7l" + str6 + "\u00a77.");
                                        break;
                                    } catch (Exception e) {
                                        ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7a\u00a7l" + e.getClass().getName() + "\u00a77(" + e.getMessage() + ") \u00a7cAn Exception occurred while setting \u00a7a\u00a7l" + str6 + "\u00a7c to \u00a7a\u00a7l" + value.getName() + "\u00a7c in \u00a7a\u00a7l" + module.getName() + "\u00a7c.");
                                        break;
                                    }
                                }
                            }
                        }
                    case -634337326:
                        if (str3.equals("targetPlayer")) {
                            EntityUtils.targetPlayer = StringsKt.equals(strArr[1], "true", true);
                            ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7a\u00a7l" + strArr[0] + "\u00a77 set to \u00a7c\u00a7l" + EntityUtils.targetPlayer + "\u00a77.");
                            break;
                        } else if (strArr.length == 3) {
                        }
                        break;
                    case 3052376:
                        if (str3.equals("chat")) {
                            StringBuilder sbAppend = new StringBuilder().append("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7e");
                            String completeString2 = StringUtils.toCompleteString(strArr, 1);
                            Intrinsics.checkExpressionValueIsNotNull(completeString2, "StringUtils.toCompleteString(args, 1)");
                            ClientUtils.displayChatMessage(sbAppend.append(ColorUtils.translateAlternateColorCodes(completeString2)).toString());
                            break;
                        } else if (strArr.length == 3) {
                        }
                        break;
                    case 3327206:
                        if (str3.equals("load")) {
                            String urlRaw = StringUtils.toCompleteString(strArr, 1);
                            Intrinsics.checkExpressionValueIsNotNull(urlRaw, "urlRaw");
                            if (StringsKt.startsWith$default(urlRaw, "http", false, 2, (Object) null)) {
                                string = urlRaw;
                            } else {
                                StringBuilder sbAppend2 = new StringBuilder().append("https://cloud.liquidbounce.net/LiquidBounce/settings/");
                                String lowerCase = urlRaw.toLowerCase();
                                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                                string = sbAppend2.append(lowerCase).toString();
                            }
                            String url = string;
                            try {
                                ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a77Loading settings from \u00a7a\u00a7l" + url + "\u00a77...");
                                SettingsUtils settingsUtils = INSTANCE;
                                Intrinsics.checkExpressionValueIsNotNull(url, "url");
                                settingsUtils.executeScript(HttpUtils.get(url));
                                ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a77Loaded settings from \u00a7a\u00a7l" + url + "\u00a77.");
                                break;
                            } catch (Exception unused) {
                                ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a77Failed to load settings from \u00a7a\u00a7l" + url + "\u00a77.");
                                break;
                            }
                        } else if (strArr.length == 3) {
                        }
                        break;
                    case 283156764:
                        if (str3.equals("targetInvisible")) {
                            EntityUtils.targetInvisible = StringsKt.equals(strArr[1], "true", true);
                            ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7a\u00a7l" + strArr[0] + "\u00a77 set to \u00a7c\u00a7l" + EntityUtils.targetInvisible + "\u00a77.");
                            break;
                        } else if (strArr.length == 3) {
                        }
                        break;
                    case 486125973:
                        if (str3.equals("targetDead")) {
                            EntityUtils.targetDead = StringsKt.equals(strArr[1], "true", true);
                            ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7a\u00a7l" + strArr[0] + "\u00a77 set to \u00a7c\u00a7l" + EntityUtils.targetDead + "\u00a77.");
                            break;
                        } else if (strArr.length == 3) {
                        }
                        break;
                    case 486403748:
                        if (str3.equals("targetMobs")) {
                            EntityUtils.targetMobs = StringsKt.equals(strArr[1], "true", true);
                            ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7a\u00a7l" + strArr[0] + "\u00a77 set to \u00a7c\u00a7l" + EntityUtils.targetMobs + "\u00a77.");
                            break;
                        } else if (strArr.length == 3) {
                        }
                        break;
                    case 1447011110:
                        if (str3.equals("targetAnimals")) {
                            EntityUtils.targetAnimals = StringsKt.equals(strArr[1], "true", true);
                            ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7a\u00a7l" + strArr[0] + "\u00a77 set to \u00a7c\u00a7l" + EntityUtils.targetAnimals + "\u00a77.");
                            break;
                        } else if (strArr.length == 3) {
                        }
                        break;
                    case 1810379489:
                        if (str3.equals("targetPlayers")) {
                            EntityUtils.targetPlayer = StringsKt.equals(strArr[1], "true", true);
                            ClientUtils.displayChatMessage("\u00a77[\u00a73\u00a7lAutoSettings\u00a77] \u00a7a\u00a7l" + strArr[0] + "\u00a77 set to \u00a7c\u00a7l" + EntityUtils.targetPlayer + "\u00a77.");
                            break;
                        } else if (strArr.length == 3) {
                        }
                        break;
                    default:
                        if (strArr.length == 3) {
                        }
                        break;
                }
            }
        }
        LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
    }

    @NotNull
    public final String generateScript(boolean z, boolean z2, boolean z3) {
        StringBuilder sb = new StringBuilder();
        TreeSet modules = LiquidBounce.INSTANCE.getModuleManager().getModules();
        ArrayList<Module> arrayList = new ArrayList();
        for (Object obj : modules) {
            Module module = (Module) obj;
            if ((module.getCategory() == ModuleCategory.RENDER || (module instanceof NameProtect) || (module instanceof Spammer)) ? false : true) {
                arrayList.add(obj);
            }
        }
        for (Module module2 : arrayList) {
            if (z) {
                for (Value value : module2.getValues()) {
                    sb.append(module2.getName()).append(" ").append(value.getName()).append(" ").append(value.get()).append("\n");
                }
            }
            if (z3) {
                sb.append(module2.getName()).append(" toggle ").append(module2.getState()).append("\n");
            }
            if (z2) {
                sb.append(module2.getName()).append(" bind ").append(Keyboard.getKeyName(module2.getKeyBind())).append("\n");
            }
        }
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "stringBuilder.toString()");
        return string;
    }
}
