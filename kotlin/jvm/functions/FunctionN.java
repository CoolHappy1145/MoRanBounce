package kotlin.jvm.functions;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Function;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.FunctionBase;
import org.jetbrains.annotations.NotNull;

@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\bg\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J&\u0010\b\u001a\u00028\ufffd\ufffd2\u0016\u0010\t\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u000b0\n\"\u0004\u0018\u00010\u000bH\u00a6\u0002\u00a2\u0006\u0002\u0010\fR\u0012\u0010\u0004\u001a\u00020\u0005X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\r"}, m27d2 = {"Lkotlin/jvm/functions/FunctionN;", "R", "Lkotlin/Function;", "Lkotlin/jvm/internal/FunctionBase;", "arity", "", "getArity", "()I", "invoke", "args", "", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/jvm/functions/FunctionN.class */
public interface FunctionN extends Function, FunctionBase {
    Object invoke(@NotNull Object[] objArr);

    @Override // kotlin.jvm.internal.FunctionBase
    int getArity();
}
