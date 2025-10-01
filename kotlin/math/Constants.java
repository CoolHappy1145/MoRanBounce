package kotlin.math;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.JvmField;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\b\u00c2\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\ufffd\ufffdX\u0081\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0005\u001a\u00020\u00048\ufffd\ufffdX\u0081\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0006\u001a\u00020\u00048\ufffd\ufffdX\u0081\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0007\u001a\u00020\u00048\ufffd\ufffdX\u0081\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\b\u001a\u00020\u00048\ufffd\ufffdX\u0081\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\t\u001a\u00020\u00048\ufffd\ufffdX\u0081\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\n"}, m27d2 = {"Lkotlin/math/Constants;", "", "()V", "LN2", "", "epsilon", "taylor_2_bound", "taylor_n_bound", "upper_taylor_2_bound", "upper_taylor_n_bound", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/math/Constants.class */
final class Constants {
    public static final Constants INSTANCE = new Constants();

    @JvmField
    public static final double LN2 = Math.log(2.0d);

    @JvmField
    public static final double epsilon = Math.ulp(1.0d);

    @JvmField
    public static final double taylor_2_bound = Math.sqrt(epsilon);

    @JvmField
    public static final double taylor_n_bound = Math.sqrt(taylor_2_bound);

    @JvmField
    public static final double upper_taylor_2_bound = 1.0d / taylor_2_bound;

    @JvmField
    public static final double upper_taylor_n_bound = 1.0d / taylor_n_bound;

    private Constants() {
    }
}
