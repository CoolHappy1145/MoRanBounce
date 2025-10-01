package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;
import jdk.nashorn.internal.runtime.regexp.joni.constants.RegexState;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/Regex.class */
public final class Regex implements RegexState {
    int[] code;
    int codeLength;
    boolean stackNeeded;
    Object[] operands;
    int operandLength;
    int numMem;
    int numRepeat;
    int numNullCheck;
    int captureHistory;
    int btMemStart;
    int btMemEnd;
    int stackPopLevel;
    int[] repeatRangeLo;
    int[] repeatRangeHi;
    WarnCallback warnings;
    MatcherFactory factory;
    protected Analyser analyser;
    int options;
    final int caseFoldFlag;
    SearchAlgorithm searchAlgorithm;
    int thresholdLength;
    int anchor;
    int anchorDmin;
    int anchorDmax;
    int subAnchor;
    char[] exact;
    int exactP;
    int exactEnd;
    byte[] map;
    int[] intMap;
    int[] intMapBackward;
    int dMin;
    int dMax;
    char[][] templates;
    int templateNum;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Regex.class.desiredAssertionStatus();
    }

    public Regex(CharSequence charSequence) {
        this(charSequence.toString());
    }

    public Regex(String str) {
        this(str.toCharArray(), 0, str.length(), 0);
    }

    public Regex(char[] cArr) {
        this(cArr, 0, cArr.length, 0);
    }

    public Regex(char[] cArr, int i, int i2) {
        this(cArr, i, i2, 0);
    }

    public Regex(char[] cArr, int i, int i2, int i3) {
        this(cArr, i, i2, i3, Syntax.RUBY, WarnCallback.DEFAULT);
    }

    public Regex(char[] cArr, int i, int i2, int i3, Syntax syntax) {
        this(cArr, i, i2, i3, 1073741824, syntax, WarnCallback.DEFAULT);
    }

    public Regex(char[] cArr, int i, int i2, int i3, WarnCallback warnCallback) {
        this(cArr, i, i2, i3, Syntax.RUBY, warnCallback);
    }

    public Regex(char[] cArr, int i, int i2, int i3, Syntax syntax, WarnCallback warnCallback) {
        this(cArr, i, i2, i3, 1073741824, syntax, warnCallback);
    }

    public Regex(char[] cArr, int i, int i2, int i3, int i4, Syntax syntax, WarnCallback warnCallback) {
        int i5;
        if ((i3 & 384) == 384) {
            throw new ValueException(ErrorMessages.ERR_INVALID_COMBINATION_OF_OPTIONS);
        }
        if ((i3 & 64) != 0) {
            i5 = (i3 | syntax.options) & (-9);
        } else {
            i5 = i3 | syntax.options;
        }
        this.options = i5;
        this.caseFoldFlag = i4;
        this.warnings = warnCallback;
        this.analyser = new Analyser(new ScanEnvironment(this, syntax), cArr, i, i2);
        this.analyser.compile();
        this.warnings = null;
    }

    public MatcherFactory compile() {
        if (this.factory == null && this.analyser != null) {
            new ArrayCompiler(this.analyser).compile();
            this.analyser = null;
        }
        if ($assertionsDisabled || this.factory != null) {
            return this.factory;
        }
        throw new AssertionError();
    }

    public Matcher matcher(char[] cArr) {
        return matcher(cArr, 0, cArr.length);
    }

    public Matcher matcher(char[] cArr, int i, int i2) {
        MatcherFactory matcherFactoryCompile = this.factory;
        if (matcherFactoryCompile == null) {
            matcherFactoryCompile = compile();
        }
        return matcherFactoryCompile.create(this, cArr, i, i2);
    }

    public WarnCallback getWarnings() {
        return this.warnings;
    }

    public int numberOfCaptures() {
        return this.numMem;
    }

    void setupBMSkipMap() {
        char[] cArr = this.exact;
        int i = this.exactP;
        int i2 = this.exactEnd - i;
        if (i2 < 256) {
            if (this.map == null) {
                this.map = new byte[256];
            }
            for (int i3 = 0; i3 < 256; i3++) {
                this.map[i3] = (byte) i2;
            }
            for (int i4 = 0; i4 < i2 - 1; i4++) {
                this.map[cArr[i + i4] & '\u00ff'] = (byte) ((i2 - 1) - i4);
            }
            return;
        }
        if (this.intMap == null) {
            this.intMap = new int[256];
        }
        for (int i5 = 0; i5 < i2 - 1; i5++) {
            this.intMap[cArr[i + i5] & '\u00ff'] = (i2 - 1) - i5;
        }
    }

    void setExactInfo(OptExactInfo optExactInfo) {
        if (optExactInfo.length == 0) {
            return;
        }
        this.exact = optExactInfo.chars;
        this.exactP = 0;
        this.exactEnd = optExactInfo.length;
        if (optExactInfo.ignoreCase) {
            this.searchAlgorithm = new SearchAlgorithm.SLOW_IC(this);
        } else if (optExactInfo.length >= 2) {
            setupBMSkipMap();
            this.searchAlgorithm = SearchAlgorithm.f72BM;
        } else {
            this.searchAlgorithm = SearchAlgorithm.SLOW;
        }
        this.dMin = optExactInfo.mmd.min;
        this.dMax = optExactInfo.mmd.max;
        if (this.dMin != Integer.MAX_VALUE) {
            this.thresholdLength = this.dMin + (this.exactEnd - this.exactP);
        }
    }

    void setOptimizeMapInfo(OptMapInfo optMapInfo) {
        this.map = optMapInfo.map;
        this.searchAlgorithm = SearchAlgorithm.MAP;
        this.dMin = optMapInfo.mmd.min;
        this.dMax = optMapInfo.mmd.max;
        if (this.dMin != Integer.MAX_VALUE) {
            this.thresholdLength = this.dMin + 1;
        }
    }

    void setSubAnchor(OptAnchorInfo optAnchorInfo) {
        this.subAnchor |= optAnchorInfo.leftAnchor & 2;
        this.subAnchor |= optAnchorInfo.rightAnchor & 32;
    }

    void clearOptimizeInfo() {
        this.searchAlgorithm = SearchAlgorithm.NONE;
        this.anchor = 0;
        this.anchorDmax = 0;
        this.anchorDmin = 0;
        this.subAnchor = 0;
        this.exact = null;
        this.exactEnd = 0;
        this.exactP = 0;
    }

    public String optimizeInfoToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("optimize: ").append(this.searchAlgorithm.getName()).append("\n");
        sb.append("  anchor:     ").append(OptAnchorInfo.anchorToString(this.anchor));
        if ((this.anchor & 24) != 0) {
            sb.append(MinMaxLen.distanceRangeToString(this.anchorDmin, this.anchorDmax));
        }
        sb.append("\n");
        if (this.searchAlgorithm != SearchAlgorithm.NONE) {
            sb.append("  sub anchor: ").append(OptAnchorInfo.anchorToString(this.subAnchor)).append("\n");
        }
        sb.append("dmin: ").append(this.dMin).append(" dmax: ").append(this.dMax).append("\n");
        sb.append("threshold length: ").append(this.thresholdLength).append("\n");
        if (this.exact != null) {
            sb.append("exact: [").append(this.exact, this.exactP, this.exactEnd - this.exactP).append("]: length: ").append(this.exactEnd - this.exactP).append("\n");
        } else if (this.searchAlgorithm == SearchAlgorithm.MAP) {
            int i = 0;
            for (int i2 = 0; i2 < 256; i2++) {
                if (this.map[i2] != 0) {
                    i++;
                }
            }
            sb.append("map: n = ").append(i).append("\n");
            if (i > 0) {
                int i3 = 0;
                sb.append("[");
                for (int i4 = 0; i4 < 256; i4++) {
                    if (this.map[i4] != 0) {
                        if (i3 > 0) {
                            sb.append(", ");
                        }
                        i3++;
                        sb.append((char) i4);
                    }
                }
                sb.append("]\n");
            }
        }
        return sb.toString();
    }

    public int getOptions() {
        return this.options;
    }

    public String dumpTree() {
        if (this.analyser == null) {
            return null;
        }
        return this.analyser.root.toString();
    }

    public String dumpByteCode() {
        compile();
        return new ByteCodePrinter(this).byteCodeListToString();
    }
}
