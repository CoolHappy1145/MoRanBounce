package net.ccbluex.liquidbounce.script.remapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.FilesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd4\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010\r\u001a\u00020\fH\u0002J\u001a\u0010\u000e\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u0005J\"\u0010\u0012\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005RR\u0010\u0003\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdRR\u0010\u0007\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/Remapper;", "", "()V", "fields", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "methods", "srgFile", "Ljava/io/File;", "srgName", "loadSrg", "", "parseSrg", "remapField", "clazz", Constants.CLASS_DESC, "name", "remapMethod", "desc", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/remapper/Remapper.class */
public final class Remapper {
    private static final String srgName = "stable_22";
    public static final Remapper INSTANCE = new Remapper();
    private static final File srgFile = new File(LiquidBounce.INSTANCE.getFileManager().dir, "mcp-stable_22.srg");
    private static final HashMap fields = new HashMap();
    private static final HashMap methods = new HashMap();

    private Remapper() {
    }

    public final void loadSrg() throws IOException {
        if (!srgFile.exists()) {
            srgFile.createNewFile();
            ClientUtils.getLogger().info("[Remapper] Downloading stable_22 srg...");
            HttpUtils.download("https://cloud.liquidbounce.net/LiquidBounce/srgs/mcp-stable_22.srg", srgFile);
            ClientUtils.getLogger().info("[Remapper] Downloaded stable_22.");
        }
        ClientUtils.getLogger().info("[Remapper] Loading srg...");
        parseSrg();
        ClientUtils.getLogger().info("[Remapper] Loaded srg.");
    }

    private final void parseSrg() {
        for (String str : FilesKt.readLines$default(srgFile, null, 1, null)) {
            List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{" "}, false, 0, 6, (Object) null);
            if (StringsKt.startsWith$default(str, "FD:", false, 2, (Object) null)) {
                String str2 = (String) listSplit$default.get(1);
                String str3 = (String) listSplit$default.get(2);
                int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) str2, '/', 0, false, 6, (Object) null);
                if (str2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String strSubstring = str2.substring(0, iLastIndexOf$default);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                String strReplace$default = StringsKt.replace$default(strSubstring, '/', '.', false, 4, (Object) null);
                int iLastIndexOf$default2 = StringsKt.lastIndexOf$default((CharSequence) str2, '/', 0, false, 6, (Object) null) + 1;
                if (str2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String strSubstring2 = str2.substring(iLastIndexOf$default2);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.String).substring(startIndex)");
                int iLastIndexOf$default3 = StringsKt.lastIndexOf$default((CharSequence) str3, '/', 0, false, 6, (Object) null) + 1;
                if (str3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String strSubstring3 = str3.substring(iLastIndexOf$default3);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring3, "(this as java.lang.String).substring(startIndex)");
                if (!fields.containsKey(strReplace$default)) {
                    fields.put(strReplace$default, new HashMap());
                }
                Object obj = fields.get(strReplace$default);
                if (obj == null) {
                    Intrinsics.throwNpe();
                }
                Intrinsics.checkExpressionValueIsNotNull(obj, "fields[className]!!");
                ((Map) obj).put(strSubstring3, strSubstring2);
            } else if (StringsKt.startsWith$default(str, "MD:", false, 2, (Object) null)) {
                String str4 = (String) listSplit$default.get(1);
                String str5 = (String) listSplit$default.get(2);
                String str6 = (String) listSplit$default.get(3);
                int iLastIndexOf$default4 = StringsKt.lastIndexOf$default((CharSequence) str4, '/', 0, false, 6, (Object) null);
                if (str4 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String strSubstring4 = str4.substring(0, iLastIndexOf$default4);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring4, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                String strReplace$default2 = StringsKt.replace$default(strSubstring4, '/', '.', false, 4, (Object) null);
                int iLastIndexOf$default5 = StringsKt.lastIndexOf$default((CharSequence) str4, '/', 0, false, 6, (Object) null) + 1;
                if (str4 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String strSubstring5 = str4.substring(iLastIndexOf$default5);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring5, "(this as java.lang.String).substring(startIndex)");
                int iLastIndexOf$default6 = StringsKt.lastIndexOf$default((CharSequence) str6, '/', 0, false, 6, (Object) null) + 1;
                if (str6 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String strSubstring6 = str6.substring(iLastIndexOf$default6);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring6, "(this as java.lang.String).substring(startIndex)");
                if (!methods.containsKey(strReplace$default2)) {
                    methods.put(strReplace$default2, new HashMap());
                }
                Object obj2 = methods.get(strReplace$default2);
                if (obj2 == null) {
                    Intrinsics.throwNpe();
                }
                Intrinsics.checkExpressionValueIsNotNull(obj2, "methods[className]!!");
                ((Map) obj2).put(strSubstring6 + str5, strSubstring5);
            } else {
                continue;
            }
        }
    }

    @NotNull
    public final String remapField(@NotNull Class clazz, @NotNull String name) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");
        if (!fields.containsKey(clazz.getName())) {
            return name;
        }
        Object obj = fields.get(clazz.getName());
        if (obj == null) {
            Intrinsics.throwNpe();
        }
        Object orDefault = ((HashMap) obj).getOrDefault(name, name);
        Intrinsics.checkExpressionValueIsNotNull(orDefault, "fields[clazz.name]!!.getOrDefault(name, name)");
        return (String) orDefault;
    }

    @NotNull
    public final String remapMethod(@NotNull Class clazz, @NotNull String name, @NotNull String desc) {
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(desc, "desc");
        if (!methods.containsKey(clazz.getName())) {
            return name;
        }
        Object obj = methods.get(clazz.getName());
        if (obj == null) {
            Intrinsics.throwNpe();
        }
        Object orDefault = ((HashMap) obj).getOrDefault(name + desc, name);
        Intrinsics.checkExpressionValueIsNotNull(orDefault, "methods[clazz.name]!!.ge\u2026efault(name + desc, name)");
        return (String) orDefault;
    }
}
