package net.ccbluex.liquidbounce.utils.misc;

import java.util.Arrays;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/misc/StringUtils.class */
public final class StringUtils {
    public static String toCompleteString(String[] strArr, int i) {
        return strArr.length <= i ? "" : String.join(" ", (CharSequence[]) Arrays.copyOfRange(strArr, i, strArr.length));
    }

    public static String replace(String str, String str2, String str3) {
        if (str.isEmpty() || str2.isEmpty() || str2.equals(str3)) {
            return str;
        }
        if (str3 == null) {
            str3 = "";
        }
        int length = str.length();
        int length2 = str2.length();
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < length; i++) {
            int iIndexOf = sb.indexOf(str2, i);
            if (iIndexOf == -1) {
                if (i == 0) {
                    return str;
                }
                return sb.toString();
            }
            sb.replace(iIndexOf, iIndexOf + length2, str3);
        }
        return sb.toString();
    }
}
