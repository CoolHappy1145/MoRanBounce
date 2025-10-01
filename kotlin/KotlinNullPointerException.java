package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\ufffd\ufffd2\u00060\u0001j\u0002`\u0002B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0003B\u0011\b\u0016\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lkotlin/KotlinNullPointerException;", "Ljava/lang/NullPointerException;", "Lkotlin/NullPointerException;", "()V", "message", "", "(Ljava/lang/String;)V", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/KotlinNullPointerException.class */
public class KotlinNullPointerException extends NullPointerException {
    public KotlinNullPointerException() {
    }

    public KotlinNullPointerException(@Nullable String str) {
        super(str);
    }
}
