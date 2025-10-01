package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.tools.Shell;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ScanEnvironment.class */
public final class ScanEnvironment {
    private static final int SCANENV_MEMNODES_SIZE = 8;
    int option;
    final int caseFoldFlag;
    public final Syntax syntax;
    int captureHistory;
    int btMemStart;
    int btMemEnd;
    int backrefedMem;
    public final Regex reg;
    public int numMem;
    public Node[] memNodes;

    public ScanEnvironment(Regex regex, Syntax syntax) {
        this.reg = regex;
        this.option = regex.options;
        this.caseFoldFlag = regex.caseFoldFlag;
        this.syntax = syntax;
    }

    public void clear() {
        this.captureHistory = 0;
        this.btMemStart = 0;
        this.btMemEnd = 0;
        this.backrefedMem = 0;
        this.numMem = 0;
        this.memNodes = null;
    }

    public int addMemEntry() {
        int i = this.numMem;
        this.numMem = i + 1;
        if (i == 0) {
            this.memNodes = new Node[8];
        } else if (this.numMem >= this.memNodes.length) {
            Node[] nodeArr = new Node[this.memNodes.length << 1];
            System.arraycopy(this.memNodes, 0, nodeArr, 0, this.memNodes.length);
            this.memNodes = nodeArr;
        }
        return this.numMem;
    }

    public void setMemNode(int i, Node node) {
        if (this.numMem >= i) {
            this.memNodes[i] = node;
            return;
        }
        throw new InternalException(ErrorMessages.ERR_PARSER_BUG);
    }

    public int convertBackslashValue(int i) {
        if (this.syntax.opEscControlChars()) {
            switch (i) {
                case 97:
                    return 7;
                case 98:
                    return 8;
                case Shell.COMPILATION_ERROR /* 101 */:
                    return 27;
                case Shell.RUNTIME_ERROR /* 102 */:
                    return 12;
                case 110:
                    return 10;
                case 114:
                    return 13;
                case 116:
                    return 9;
                case 118:
                    if (this.syntax.op2EscVVtab()) {
                        return 11;
                    }
                    break;
            }
        }
        return i;
    }

    void ccEscWarn(String str) {
        if (this.syntax.warnCCOpNotEscaped() && this.syntax.backSlashEscapeInCC()) {
            this.reg.warnings.warn("character class has '" + str + "' without escape");
        }
    }
}
