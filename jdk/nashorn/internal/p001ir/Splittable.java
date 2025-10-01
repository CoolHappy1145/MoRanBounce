package jdk.nashorn.internal.p001ir;

import java.io.Serializable;
import java.util.List;
import jdk.nashorn.internal.codegen.CompileUnit;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/Splittable.class */
public interface Splittable {
    List getSplitRanges();

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/Splittable$SplitRange.class */
    public static final class SplitRange implements CompileUnitHolder, Serializable {
        private static final long serialVersionUID = 1;
        private final CompileUnit compileUnit;
        private final int low;
        private final int high;

        public SplitRange(CompileUnit compileUnit, int i, int i2) {
            this.compileUnit = compileUnit;
            this.low = i;
            this.high = i2;
        }

        public int getHigh() {
            return this.high;
        }

        public int getLow() {
            return this.low;
        }

        @Override // jdk.nashorn.internal.p001ir.CompileUnitHolder
        public CompileUnit getCompileUnit() {
            return this.compileUnit;
        }
    }
}
