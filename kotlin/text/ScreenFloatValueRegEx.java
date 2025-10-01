package kotlin.text;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u00c2\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0005"}, m27d2 = {"Lkotlin/text/ScreenFloatValueRegEx;", "", "()V", PropertyDescriptor.VALUE, "Lkotlin/text/Regex;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/text/ScreenFloatValueRegEx.class */
final class ScreenFloatValueRegEx {

    @JvmField
    @NotNull
    public static final Regex value;
    public static final ScreenFloatValueRegEx INSTANCE = new ScreenFloatValueRegEx();

    private ScreenFloatValueRegEx() {
    }

    static {
        String str = "[eE][+-]?(\\p{Digit}+)";
        value = new Regex("[\\x00-\\x20]*[+-]?(NaN|Infinity|((" + ("((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)(" + str + ")?)|(\\.((\\p{Digit}+))(" + str + ")?)|((" + ("(0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+))") + ")[pP][+-]?(\\p{Digit}+))") + ")[fFdD]?))[\\x00-\\x20]*");
    }
}
