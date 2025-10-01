package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.AnchorNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.BackRefNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.EncloseNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;
import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/Compiler.class */
abstract class Compiler implements ErrorMessages {
    protected final Analyser analyser;
    protected final Regex regex;

    protected abstract void prepare();

    protected abstract void finish();

    protected abstract void compileAltNode(ConsAltNode consAltNode);

    protected abstract void addCompileString(char[] cArr, int i, int i2, boolean z);

    protected abstract void compileCClassNode(CClassNode cClassNode);

    protected abstract void compileAnyCharNode();

    protected abstract void compileBackrefNode(BackRefNode backRefNode);

    protected abstract void compileNonCECQuantifierNode(QuantifierNode quantifierNode);

    protected abstract void compileOptionNode(EncloseNode encloseNode);

    protected abstract void compileEncloseNode(EncloseNode encloseNode);

    protected abstract void compileAnchorNode(AnchorNode anchorNode);

    protected Compiler(Analyser analyser) {
        this.analyser = analyser;
        this.regex = analyser.regex;
    }

    final void compile() {
        prepare();
        compileTree(this.analyser.root);
        finish();
    }

    private void compileStringRawNode(StringNode stringNode) {
        if (stringNode.length() <= 0) {
            return;
        }
        addCompileString(stringNode.chars, stringNode.f86p, stringNode.length(), false);
    }

    private void compileStringNode(StringNode stringNode) {
        if (stringNode.length() <= 0) {
            return;
        }
        boolean zIsAmbig = stringNode.isAmbig();
        int i = stringNode.f86p;
        int i2 = stringNode.end;
        char[] cArr = stringNode.chars;
        int i3 = 1;
        for (int i4 = i + 1; i4 < i2; i4++) {
            i3++;
        }
        addCompileString(cArr, i, i3, zIsAmbig);
    }

    protected final void compileTree(Node node) {
        ConsAltNode consAltNode;
        switch (node.getType()) {
            case 0:
                StringNode stringNode = (StringNode) node;
                if (stringNode.isRaw()) {
                    compileStringRawNode(stringNode);
                    break;
                } else {
                    compileStringNode(stringNode);
                    break;
                }
            case 1:
                compileCClassNode((CClassNode) node);
                break;
            case 2:
            default:
                newInternalException(ErrorMessages.ERR_PARSER_BUG);
                break;
            case 3:
                compileAnyCharNode();
                break;
            case 4:
                compileBackrefNode((BackRefNode) node);
                break;
            case 5:
                compileNonCECQuantifierNode((QuantifierNode) node);
                break;
            case 6:
                EncloseNode encloseNode = (EncloseNode) node;
                if (encloseNode.isOption()) {
                    compileOptionNode(encloseNode);
                    break;
                } else {
                    compileEncloseNode(encloseNode);
                    break;
                }
            case 7:
                compileAnchorNode((AnchorNode) node);
                break;
            case 8:
                ConsAltNode consAltNode2 = (ConsAltNode) node;
                do {
                    compileTree(consAltNode2.car);
                    consAltNode = consAltNode2.cdr;
                    consAltNode2 = consAltNode;
                } while (consAltNode != null);
            case 9:
                compileAltNode((ConsAltNode) node);
                break;
        }
    }

    protected final void compileTreeNTimes(Node node, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            compileTree(node);
        }
    }

    protected void newSyntaxException(String str) {
        throw new SyntaxException(str);
    }

    protected void newInternalException(String str) {
        throw new InternalException(str);
    }
}
