package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.AnchorNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.BackRefNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.EncloseNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;
import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AnchorType;
import jdk.nashorn.internal.runtime.regexp.joni.constants.NodeType;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.ObjPtr;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/Analyser.class */
final class Analyser extends Parser {
    private static final int GET_CHAR_LEN_VARLEN = -1;
    private static final int GET_CHAR_LEN_TOP_ALT_VARLEN = -2;
    private static final int THRESHOLD_CASE_FOLD_ALT_FOR_EXPANSION = 8;
    private static final int IN_ALT = 1;
    private static final int IN_NOT = 2;
    private static final int IN_REPEAT = 4;
    private static final int IN_VAR_REPEAT = 8;
    private static final int EXPAND_STRING_MAX_LENGTH = 100;
    private static final int MAX_NODE_OPT_INFO_REF_COUNT = 5;

    protected Analyser(ScanEnvironment scanEnvironment, char[] cArr, int i, int i2) {
        super(scanEnvironment, cArr, i, i2);
    }

    protected final void compile() {
        reset();
        this.regex.numMem = 0;
        this.regex.numRepeat = 0;
        this.regex.numNullCheck = 0;
        this.regex.repeatRangeLo = null;
        this.regex.repeatRangeHi = null;
        parse();
        this.root = setupTree(this.root, 0);
        this.regex.captureHistory = this.env.captureHistory;
        this.regex.btMemStart = this.env.btMemStart;
        this.regex.btMemEnd = this.env.btMemEnd;
        if ((this.regex.options & 48) != 0) {
            this.regex.btMemEnd = -1;
        } else {
            this.regex.btMemEnd = this.env.btMemEnd;
            this.regex.btMemEnd |= this.regex.captureHistory;
        }
        this.regex.clearOptimizeInfo();
        setOptimizedInfoFromTree(this.root);
        this.env.memNodes = null;
        if (this.regex.numRepeat != 0 || this.regex.btMemEnd != 0) {
            this.regex.stackPopLevel = 2;
        } else if (this.regex.btMemStart != 0) {
            this.regex.stackPopLevel = 1;
        } else {
            this.regex.stackPopLevel = 0;
        }
    }

    private void swap(Node node, Node node2) {
        node.swap(node2);
        if (this.root == node2) {
            this.root = node;
        } else if (this.root == node) {
            this.root = node2;
        }
    }

    private int quantifiersMemoryInfo(Node node) {
        ConsAltNode consAltNode;
        int iQuantifiersMemoryInfo = 0;
        switch (node.getType()) {
            case 5:
                QuantifierNode quantifierNode = (QuantifierNode) node;
                if (quantifierNode.upper != 0) {
                    iQuantifiersMemoryInfo = quantifiersMemoryInfo(quantifierNode.target);
                    break;
                }
                break;
            case 6:
                EncloseNode encloseNode = (EncloseNode) node;
                switch (encloseNode.type) {
                    case 1:
                        return 2;
                    case 2:
                    case 4:
                        iQuantifiersMemoryInfo = quantifiersMemoryInfo(encloseNode.target);
                        break;
                }
            case 8:
            case 9:
                ConsAltNode consAltNode2 = (ConsAltNode) node;
                do {
                    int iQuantifiersMemoryInfo2 = quantifiersMemoryInfo(consAltNode2.car);
                    if (iQuantifiersMemoryInfo2 > iQuantifiersMemoryInfo) {
                        iQuantifiersMemoryInfo = iQuantifiersMemoryInfo2;
                    }
                    consAltNode = consAltNode2.cdr;
                    consAltNode2 = consAltNode;
                } while (consAltNode != null);
        }
        return iQuantifiersMemoryInfo;
    }

    private int getMinMatchLength(Node node) {
        int i;
        ConsAltNode consAltNode;
        ConsAltNode consAltNode2;
        int minMatchLength = 0;
        switch (node.getType()) {
            case 0:
                minMatchLength = ((StringNode) node).length();
                break;
            case 1:
            case 3:
                minMatchLength = 1;
                break;
            case 2:
                minMatchLength = 1;
                break;
            case 4:
                BackRefNode backRefNode = (BackRefNode) node;
                if (!backRefNode.isRecursion()) {
                    if (backRefNode.backRef > this.env.numMem) {
                        throw new ValueException(ErrorMessages.ERR_INVALID_BACKREF);
                    }
                    minMatchLength = getMinMatchLength(this.env.memNodes[backRefNode.backRef]);
                    break;
                }
                break;
            case 5:
                QuantifierNode quantifierNode = (QuantifierNode) node;
                if (quantifierNode.lower > 0) {
                    int minMatchLength2 = getMinMatchLength(quantifierNode.target);
                    int i2 = quantifierNode.lower;
                    if (i2 == 0) {
                        i = 0;
                    } else if (minMatchLength2 < Integer.MAX_VALUE / i2) {
                        i = minMatchLength2 * i2;
                    } else {
                        i = Integer.MAX_VALUE;
                    }
                    minMatchLength = i;
                    break;
                }
                break;
            case 6:
                EncloseNode encloseNode = (EncloseNode) node;
                switch (encloseNode.type) {
                    case 1:
                        if (encloseNode.isMinFixed()) {
                            minMatchLength = encloseNode.minLength;
                            break;
                        } else {
                            minMatchLength = getMinMatchLength(encloseNode.target);
                            encloseNode.minLength = minMatchLength;
                            encloseNode.setMinFixed();
                            break;
                        }
                    case 2:
                    case 4:
                        minMatchLength = getMinMatchLength(encloseNode.target);
                        break;
                }
            case 8:
                ConsAltNode consAltNode3 = (ConsAltNode) node;
                do {
                    minMatchLength += getMinMatchLength(consAltNode3.car);
                    consAltNode2 = consAltNode3.cdr;
                    consAltNode3 = consAltNode2;
                } while (consAltNode2 != null);
            case 9:
                ConsAltNode consAltNode4 = (ConsAltNode) node;
                do {
                    int minMatchLength3 = getMinMatchLength(consAltNode4.car);
                    if (consAltNode4 == node || minMatchLength > minMatchLength3) {
                        minMatchLength = minMatchLength3;
                    }
                    consAltNode = consAltNode4.cdr;
                    consAltNode4 = consAltNode;
                } while (consAltNode != null);
                break;
        }
        return minMatchLength;
    }

    private int getMaxMatchLength(Node node) {
        int i;
        ConsAltNode consAltNode;
        int i2;
        ConsAltNode consAltNode2;
        int maxMatchLength = 0;
        switch (node.getType()) {
            case 0:
                maxMatchLength = ((StringNode) node).length();
                break;
            case 1:
            case 3:
                maxMatchLength = 1;
                break;
            case 2:
                maxMatchLength = 1;
                break;
            case 4:
                BackRefNode backRefNode = (BackRefNode) node;
                if (backRefNode.isRecursion()) {
                    maxMatchLength = Integer.MAX_VALUE;
                    break;
                } else {
                    if (backRefNode.backRef > this.env.numMem) {
                        throw new ValueException(ErrorMessages.ERR_INVALID_BACKREF);
                    }
                    int maxMatchLength2 = getMaxMatchLength(this.env.memNodes[backRefNode.backRef]);
                    if (0 < maxMatchLength2) {
                        maxMatchLength = maxMatchLength2;
                        break;
                    }
                }
                break;
            case 5:
                QuantifierNode quantifierNode = (QuantifierNode) node;
                if (quantifierNode.upper != 0) {
                    maxMatchLength = getMaxMatchLength(quantifierNode.target);
                    if (maxMatchLength != 0) {
                        if (!(quantifierNode.upper == -1)) {
                            int i3 = quantifierNode.upper;
                            if (i3 == 0) {
                                i = 0;
                            } else if (maxMatchLength < Integer.MAX_VALUE / i3) {
                                i = maxMatchLength * i3;
                            } else {
                                i = Integer.MAX_VALUE;
                            }
                            maxMatchLength = i;
                            break;
                        } else {
                            maxMatchLength = Integer.MAX_VALUE;
                            break;
                        }
                    }
                }
                break;
            case 6:
                EncloseNode encloseNode = (EncloseNode) node;
                switch (encloseNode.type) {
                    case 1:
                        if (encloseNode.isMaxFixed()) {
                            maxMatchLength = encloseNode.maxLength;
                            break;
                        } else {
                            maxMatchLength = getMaxMatchLength(encloseNode.target);
                            encloseNode.maxLength = maxMatchLength;
                            encloseNode.setMaxFixed();
                            break;
                        }
                    case 2:
                    case 4:
                        maxMatchLength = getMaxMatchLength(encloseNode.target);
                        break;
                }
            case 8:
                ConsAltNode consAltNode3 = (ConsAltNode) node;
                do {
                    int maxMatchLength3 = getMaxMatchLength(consAltNode3.car);
                    int i4 = maxMatchLength;
                    if (i4 != Integer.MAX_VALUE && maxMatchLength3 != Integer.MAX_VALUE && i4 <= Integer.MAX_VALUE - maxMatchLength3) {
                        i2 = i4 + maxMatchLength3;
                    } else {
                        i2 = Integer.MAX_VALUE;
                    }
                    maxMatchLength = i2;
                    consAltNode2 = consAltNode3.cdr;
                    consAltNode3 = consAltNode2;
                } while (consAltNode2 != null);
                break;
            case 9:
                ConsAltNode consAltNode4 = (ConsAltNode) node;
                do {
                    int maxMatchLength4 = getMaxMatchLength(consAltNode4.car);
                    if (maxMatchLength < maxMatchLength4) {
                        maxMatchLength = maxMatchLength4;
                    }
                    consAltNode = consAltNode4.cdr;
                    consAltNode4 = consAltNode;
                } while (consAltNode != null);
        }
        return maxMatchLength;
    }

    protected final int getCharLengthTree(Node node) {
        return getCharLengthTree(node, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int getCharLengthTree(Node node, int i) {
        int i2;
        ConsAltNode consAltNode;
        int i3;
        int i4 = i + 1;
        int charLengthTree = 0;
        this.returnCode = 0;
        switch (node.getType()) {
            case 0:
                charLengthTree = ((StringNode) node).length();
                break;
            case 1:
            case 2:
            case 3:
                charLengthTree = 1;
                break;
            case 4:
            default:
                this.returnCode = -1;
                break;
            case 5:
                QuantifierNode quantifierNode = (QuantifierNode) node;
                if (quantifierNode.lower == quantifierNode.upper) {
                    int charLengthTree2 = getCharLengthTree(quantifierNode.target, i4);
                    if (this.returnCode == 0) {
                        int i5 = quantifierNode.lower;
                        if (i5 == 0) {
                            i2 = 0;
                        } else if (charLengthTree2 < Integer.MAX_VALUE / i5) {
                            i2 = charLengthTree2 * i5;
                        } else {
                            i2 = Integer.MAX_VALUE;
                        }
                        charLengthTree = i2;
                        break;
                    }
                } else {
                    this.returnCode = -1;
                    break;
                }
                break;
            case 6:
                EncloseNode encloseNode = (EncloseNode) node;
                switch (encloseNode.type) {
                    case 1:
                        if (encloseNode.isCLenFixed()) {
                            charLengthTree = encloseNode.charLength;
                            break;
                        } else {
                            charLengthTree = getCharLengthTree(encloseNode.target, i4);
                            if (this.returnCode == 0) {
                                encloseNode.charLength = charLengthTree;
                                encloseNode.setCLenFixed();
                                break;
                            }
                        }
                        break;
                    case 2:
                    case 4:
                        charLengthTree = getCharLengthTree(encloseNode.target, i4);
                        break;
                }
            case 7:
                break;
            case 8:
                ConsAltNode consAltNode2 = (ConsAltNode) node;
                do {
                    int charLengthTree3 = getCharLengthTree(consAltNode2.car, i4);
                    if (this.returnCode == 0) {
                        int i6 = charLengthTree;
                        if (i6 != Integer.MAX_VALUE && charLengthTree3 != Integer.MAX_VALUE && i6 <= Integer.MAX_VALUE - charLengthTree3) {
                            i3 = i6 + charLengthTree3;
                        } else {
                            i3 = Integer.MAX_VALUE;
                        }
                        charLengthTree = i3;
                    }
                    if (this.returnCode != 0) {
                        break;
                    } else {
                        consAltNode = consAltNode2.cdr;
                        consAltNode2 = consAltNode;
                    }
                } while (consAltNode != null);
                break;
            case 9:
                ConsAltNode consAltNode3 = (ConsAltNode) node;
                boolean z = false;
                int charLengthTree4 = getCharLengthTree(consAltNode3.car, i4);
                while (this.returnCode == 0) {
                    ConsAltNode consAltNode4 = consAltNode3.cdr;
                    consAltNode3 = consAltNode4;
                    if (consAltNode4 != null) {
                        int charLengthTree5 = getCharLengthTree(consAltNode3.car, i4);
                        if (this.returnCode == 0 && charLengthTree4 != charLengthTree5) {
                            z = true;
                        }
                    } else if (this.returnCode == 0) {
                        if (z) {
                            if (i4 == 1) {
                                this.returnCode = -2;
                                break;
                            } else {
                                this.returnCode = -1;
                                break;
                            }
                        } else {
                            charLengthTree = charLengthTree4;
                            break;
                        }
                    }
                }
                if (this.returnCode == 0) {
                }
                break;
        }
        return charLengthTree;
    }

    private static boolean isNotIncluded(Node node, Node node2) {
        Node node3 = node;
        Node node4 = node2;
        while (true) {
            Node node5 = node4;
            int type = node5.getType();
            switch (node3.getType()) {
                case 0:
                    StringNode stringNode = (StringNode) node3;
                    if (stringNode.length() != 0) {
                        switch (type) {
                            case 0:
                                StringNode stringNode2 = (StringNode) node5;
                                int length = stringNode.length();
                                if (length > stringNode2.length()) {
                                    length = stringNode2.length();
                                }
                                if (stringNode.isAmbig() || stringNode2.isAmbig()) {
                                    return false;
                                }
                                int i = 0;
                                int i2 = stringNode2.f86p;
                                int i3 = stringNode.f86p;
                                while (i < length) {
                                    if (stringNode2.chars[i2] == stringNode.chars[i3]) {
                                        i++;
                                        i2++;
                                        i3++;
                                    } else {
                                        return true;
                                    }
                                }
                                return false;
                            case 1:
                                return !((CClassNode) node5).isCodeInCC(stringNode.chars[stringNode.f86p]);
                            default:
                                return false;
                        }
                    }
                    return false;
                case 1:
                    CClassNode cClassNode = (CClassNode) node3;
                    switch (type) {
                        case 0:
                            Node node6 = node3;
                            node3 = node5;
                            node4 = node6;
                            break;
                        case 1:
                            CClassNode cClassNode2 = (CClassNode) node5;
                            for (int i4 = 0; i4 < 256; i4++) {
                                boolean zM20at = cClassNode.f78bs.m20at(i4);
                                if ((zM20at && !cClassNode.isNot()) || (!zM20at && cClassNode.isNot())) {
                                    boolean zM20at2 = cClassNode2.f78bs.m20at(i4);
                                    if (zM20at2 && !cClassNode2.isNot()) {
                                        return false;
                                    }
                                    if (!zM20at2 && cClassNode2.isNot()) {
                                        return false;
                                    }
                                }
                            }
                            if (cClassNode.mbuf == null && !cClassNode.isNot()) {
                                return true;
                            }
                            if (cClassNode2.mbuf == null && !cClassNode2.isNot()) {
                                return true;
                            }
                            return false;
                        default:
                            return false;
                    }
                case 2:
                    switch (type) {
                        case 0:
                            Node node7 = node3;
                            node3 = node5;
                            node4 = node7;
                            break;
                        case 1:
                            Node node8 = node3;
                            node3 = node5;
                            node4 = node8;
                            break;
                        default:
                            return false;
                    }
                default:
                    return false;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0095  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Node getHeadValueNode(Node node, boolean z) {
        Node headValueNode = null;
        switch (node.getType()) {
            case 0:
                StringNode stringNode = (StringNode) node;
                if (stringNode.end > stringNode.f86p) {
                    if (z && !stringNode.isRaw()) {
                        if (!((this.regex.options & 1) != 0)) {
                        }
                    } else {
                        headValueNode = node;
                        break;
                    }
                }
                break;
            case 1:
            case 2:
                if (!z) {
                    headValueNode = node;
                    break;
                }
                break;
            case 5:
                QuantifierNode quantifierNode = (QuantifierNode) node;
                if (quantifierNode.lower > 0) {
                    if (quantifierNode.headExact != null) {
                        headValueNode = quantifierNode.headExact;
                        break;
                    } else {
                        headValueNode = getHeadValueNode(quantifierNode.target, z);
                        break;
                    }
                }
                break;
            case 6:
                EncloseNode encloseNode = (EncloseNode) node;
                switch (encloseNode.type) {
                    case 1:
                    case 4:
                        headValueNode = getHeadValueNode(encloseNode.target, z);
                        break;
                    case 2:
                        int i = this.regex.options;
                        this.regex.options = encloseNode.option;
                        headValueNode = getHeadValueNode(encloseNode.target, z);
                        this.regex.options = i;
                        break;
                }
            case 7:
                AnchorNode anchorNode = (AnchorNode) node;
                if (anchorNode.type == 1024) {
                    headValueNode = getHeadValueNode(anchorNode.target, z);
                    break;
                }
                break;
            case 8:
                headValueNode = getHeadValueNode(((ConsAltNode) node).car, z);
                break;
        }
        return headValueNode;
    }

    private boolean checkTypeTree(Node node, int i, int i2, int i3) {
        ConsAltNode consAltNode;
        if ((node.getType2Bit() & i) == 0) {
            return true;
        }
        boolean zCheckTypeTree = false;
        switch (node.getType()) {
            case 5:
                zCheckTypeTree = checkTypeTree(((QuantifierNode) node).target, i, i2, i3);
                break;
            case 6:
                EncloseNode encloseNode = (EncloseNode) node;
                if ((encloseNode.type & i2) == 0) {
                    return true;
                }
                zCheckTypeTree = checkTypeTree(encloseNode.target, i, i2, i3);
                break;
            case 7:
                AnchorNode anchorNode = (AnchorNode) node;
                if ((anchorNode.type & i3) == 0) {
                    return true;
                }
                if (anchorNode.target != null) {
                    zCheckTypeTree = checkTypeTree(anchorNode.target, i, i2, i3);
                    break;
                }
                break;
            case 8:
            case 9:
                ConsAltNode consAltNode2 = (ConsAltNode) node;
                do {
                    zCheckTypeTree = checkTypeTree(consAltNode2.car, i, i2, i3);
                    if (zCheckTypeTree) {
                        break;
                    } else {
                        consAltNode = consAltNode2.cdr;
                        consAltNode2 = consAltNode;
                    }
                } while (consAltNode != null);
        }
        return zCheckTypeTree;
    }

    private Node divideLookBehindAlternatives(Node node) {
        ConsAltNode consAltNode;
        AnchorNode anchorNode = (AnchorNode) node;
        int i = anchorNode.type;
        Node node2 = anchorNode.target;
        Node node3 = ((ConsAltNode) node2).car;
        swap(node, node2);
        ((ConsAltNode) node2).setCar(node);
        ((AnchorNode) node).setTarget(node3);
        Node node4 = node2;
        while (true) {
            ConsAltNode consAltNode2 = ((ConsAltNode) node4).cdr;
            node4 = consAltNode2;
            if (consAltNode2 == null) {
                break;
            }
            AnchorNode anchorNode2 = new AnchorNode(i);
            anchorNode2.setTarget(((ConsAltNode) node4).car);
            ((ConsAltNode) node4).setCar(anchorNode2);
        }
        if (i == 8192) {
            Node node5 = node2;
            do {
                ((ConsAltNode) node5).toListNode();
                consAltNode = ((ConsAltNode) node5).cdr;
                node5 = consAltNode;
            } while (consAltNode != null);
        }
        return node2;
    }

    private Node setupLookBehind(Node node) {
        AnchorNode anchorNode = (AnchorNode) node;
        int charLengthTree = getCharLengthTree(anchorNode.target);
        switch (this.returnCode) {
            case -2:
                if (this.syntax.differentLengthAltLookBehind()) {
                    return divideLookBehindAlternatives(node);
                }
                throw new SyntaxException(ErrorMessages.ERR_INVALID_LOOK_BEHIND_PATTERN);
            case -1:
                throw new SyntaxException(ErrorMessages.ERR_INVALID_LOOK_BEHIND_PATTERN);
            case 0:
                anchorNode.charLength = charLengthTree;
                break;
        }
        return node;
    }

    private void nextSetup(Node node, Node node2) {
        Node headValueNode;
        Node headValueNode2;
        Node node3 = node;
        while (true) {
            Node node4 = node3;
            int type = node4.getType();
            if (type == 5) {
                QuantifierNode quantifierNode = (QuantifierNode) node4;
                if (quantifierNode.greedy) {
                    if (quantifierNode.upper == -1) {
                        StringNode stringNode = (StringNode) getHeadValueNode(node2, true);
                        if (stringNode != null && stringNode.chars[stringNode.f86p] != 0) {
                            quantifierNode.nextHeadExact = stringNode;
                        }
                        if (quantifierNode.lower <= 1 && quantifierNode.target.isSimple() && (headValueNode = getHeadValueNode(quantifierNode.target, false)) != null && (headValueNode2 = getHeadValueNode(node2, false)) != null && isNotIncluded(headValueNode, headValueNode2)) {
                            EncloseNode encloseNode = new EncloseNode(4);
                            encloseNode.setStopBtSimpleRepeat();
                            swap(node4, encloseNode);
                            encloseNode.setTarget(node4);
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            if (type == 6) {
                EncloseNode encloseNode2 = (EncloseNode) node4;
                if (encloseNode2.isMemory()) {
                    node3 = encloseNode2.target;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void updateStringNodeCaseFoldMultiByte(StringNode stringNode) {
        char[] cArr = stringNode.chars;
        int i = stringNode.end;
        this.value = stringNode.f86p;
        int i2 = 0;
        while (this.value < i) {
            int i3 = this.value;
            int i4 = this.value;
            this.value = i4 + 1;
            if (cArr[i3] != EncodingHelper.toLowerCase(cArr[i4])) {
                char[] cArr2 = new char[stringNode.length() << 1];
                System.arraycopy(cArr, stringNode.f86p, cArr2, 0, i3 - stringNode.f86p);
                this.value = i3;
                while (this.value < i) {
                    int i5 = this.value;
                    this.value = i5 + 1;
                    char lowerCase = EncodingHelper.toLowerCase(cArr[i5]);
                    if (i2 >= cArr2.length) {
                        char[] cArr3 = new char[cArr2.length << 1];
                        System.arraycopy(cArr2, 0, cArr3, 0, cArr2.length);
                        cArr2 = cArr3;
                    }
                    int i6 = i2;
                    i2++;
                    cArr2[i6] = lowerCase;
                }
                stringNode.set(cArr2, 0, i2);
                return;
            }
            i2++;
        }
    }

    private void updateStringNodeCaseFold(Node node) {
        updateStringNodeCaseFoldMultiByte((StringNode) node);
    }

    private Node expandCaseFoldMakeRemString(char[] cArr, int i, int i2) {
        StringNode stringNode = new StringNode(cArr, i, i2);
        updateStringNodeCaseFold(stringNode);
        stringNode.setAmbig();
        stringNode.setDontGetOptInfo();
        return stringNode;
    }

    private static boolean expandCaseFoldStringAlt(int i, char[] cArr, char[] cArr2, int i2, int i3, int i4, ObjPtr objPtr) {
        ConsAltNode consAltNodeNewAltNode = ConsAltNode.newAltNode(null, null);
        ConsAltNode consAltNode = consAltNodeNewAltNode;
        objPtr.f92p = consAltNodeNewAltNode;
        consAltNode.setCar(new StringNode(cArr2, i2, i2 + i3));
        for (int i5 = 0; i5 < i; i5++) {
            StringNode stringNode = new StringNode();
            stringNode.catCode(cArr[i5]);
            ConsAltNode consAltNodeNewAltNode2 = ConsAltNode.newAltNode(null, null);
            consAltNodeNewAltNode2.setCar(stringNode);
            consAltNode.setCdr(consAltNodeNewAltNode2);
            consAltNode = consAltNodeNewAltNode2;
        }
        return false;
    }

    private Node expandCaseFoldString(Node node) {
        StringNode stringNode = (StringNode) node;
        if (stringNode.isAmbig() || stringNode.length() <= 0) {
            return node;
        }
        char[] cArr = stringNode.chars;
        int i = stringNode.f86p;
        int i2 = stringNode.end;
        int length = 1;
        ConsAltNode consAltNode = null;
        ConsAltNode consAltNode2 = null;
        ObjPtr objPtr = new ObjPtr();
        StringNode stringNode2 = null;
        while (i < i2) {
            char[] cArrCaseFoldCodesByString = EncodingHelper.caseFoldCodesByString(this.regex.caseFoldFlag, cArr[i]);
            if (cArrCaseFoldCodesByString.length == 0) {
                if (stringNode2 == null) {
                    if (consAltNode2 == null && objPtr.f92p != null) {
                        ConsAltNode consAltNodeListAdd = ConsAltNode.listAdd(null, (Node) objPtr.f92p);
                        consAltNode2 = consAltNodeListAdd;
                        consAltNode = consAltNodeListAdd;
                    }
                    StringNode stringNode3 = new StringNode();
                    stringNode2 = stringNode3;
                    objPtr.f92p = stringNode3;
                    if (consAltNode2 != null) {
                        ConsAltNode.listAdd(consAltNode2, stringNode2);
                    }
                }
                stringNode2.cat(cArr, i, i + 1);
            } else {
                length *= cArrCaseFoldCodesByString.length + 1;
                if (length > 8) {
                    break;
                }
                if (consAltNode2 == null && objPtr.f92p != null) {
                    ConsAltNode consAltNodeListAdd2 = ConsAltNode.listAdd(null, (Node) objPtr.f92p);
                    consAltNode2 = consAltNodeListAdd2;
                    consAltNode = consAltNodeListAdd2;
                }
                expandCaseFoldStringAlt(cArrCaseFoldCodesByString.length, cArrCaseFoldCodesByString, cArr, i, 1, i2, objPtr);
                if (consAltNode2 != null) {
                    ConsAltNode.listAdd(consAltNode2, (Node) objPtr.f92p);
                }
                stringNode2 = null;
            }
            i++;
        }
        if (i < i2) {
            Node nodeExpandCaseFoldMakeRemString = expandCaseFoldMakeRemString(cArr, i, i2);
            if (objPtr.f92p != null && consAltNode2 == null) {
                ConsAltNode consAltNodeListAdd3 = ConsAltNode.listAdd(null, (Node) objPtr.f92p);
                consAltNode2 = consAltNodeListAdd3;
                consAltNode = consAltNodeListAdd3;
            }
            if (consAltNode2 == null) {
                objPtr.f92p = nodeExpandCaseFoldMakeRemString;
            } else {
                ConsAltNode.listAdd(consAltNode2, nodeExpandCaseFoldMakeRemString);
            }
        }
        Node node2 = consAltNode != null ? consAltNode : (Node) objPtr.f92p;
        swap(node, node2);
        return node2;
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x0282  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected final Node setupTree(Node node, int i) {
        int i2;
        int i3;
        int i4;
        ConsAltNode consAltNode;
        ConsAltNode consAltNode2;
        Node nodeExpandCaseFoldString = node;
        while (true) {
            switch (nodeExpandCaseFoldString.getType()) {
                case 0:
                    if (((this.regex.options & 1) != 0) && !((StringNode) nodeExpandCaseFoldString).isRaw()) {
                        nodeExpandCaseFoldString = expandCaseFoldString(nodeExpandCaseFoldString);
                        break;
                    }
                    break;
                case 4:
                    BackRefNode backRefNode = (BackRefNode) nodeExpandCaseFoldString;
                    if (backRefNode.backRef > this.env.numMem) {
                        throw new ValueException(ErrorMessages.ERR_INVALID_BACKREF);
                    }
                    ScanEnvironment scanEnvironment = this.env;
                    int i5 = this.env.backrefedMem;
                    int i6 = backRefNode.backRef;
                    if (i6 < 32) {
                        i3 = i5 | (1 << i6);
                    } else {
                        i3 = i5 | 1;
                    }
                    scanEnvironment.backrefedMem = i3;
                    ScanEnvironment scanEnvironment2 = this.env;
                    int i7 = this.env.btMemStart;
                    int i8 = backRefNode.backRef;
                    if (i8 < 32) {
                        i4 = i7 | (1 << i8);
                    } else {
                        i4 = i7 | 1;
                    }
                    scanEnvironment2.btMemStart = i4;
                    ((EncloseNode) this.env.memNodes[backRefNode.backRef]).setMemBackrefed();
                    break;
                case 5:
                    QuantifierNode quantifierNode = (QuantifierNode) nodeExpandCaseFoldString;
                    Node node2 = quantifierNode.target;
                    if ((i & 4) != 0) {
                        quantifierNode.setInRepeat();
                    }
                    if (((quantifierNode.upper == -1) || quantifierNode.lower >= 1) && getMinMatchLength(node2) == 0) {
                        quantifierNode.targetEmptyInfo = 1;
                        int iQuantifiersMemoryInfo = quantifiersMemoryInfo(node2);
                        if (iQuantifiersMemoryInfo > 0) {
                            quantifierNode.targetEmptyInfo = iQuantifiersMemoryInfo;
                        }
                    }
                    int i9 = i | 4;
                    if (quantifierNode.lower != quantifierNode.upper) {
                        i9 |= 8;
                    }
                    Node node3 = setupTree(node2, i9);
                    if (node3.getType() == 0) {
                        if (!(quantifierNode.lower == -1) && quantifierNode.lower == quantifierNode.upper && quantifierNode.lower > 1 && quantifierNode.lower <= 100) {
                            StringNode stringNode = (StringNode) node3;
                            if (stringNode.length() * quantifierNode.lower <= 100) {
                                StringNode stringNodeConvertToString = quantifierNode.convertToString(stringNode.flag);
                                int i10 = quantifierNode.lower;
                                for (int i11 = 0; i11 < i10; i11++) {
                                    stringNodeConvertToString.cat(stringNode.chars, stringNode.f86p, stringNode.end);
                                }
                                break;
                            }
                        }
                    } else if (quantifierNode.greedy && quantifierNode.targetEmptyInfo != 0) {
                        if (node3.getType() == 5) {
                            QuantifierNode quantifierNode2 = (QuantifierNode) node3;
                            if (quantifierNode2.headExact != null) {
                                quantifierNode.headExact = quantifierNode2.headExact;
                                quantifierNode2.headExact = null;
                                break;
                            }
                        } else {
                            quantifierNode.headExact = getHeadValueNode(quantifierNode.target, true);
                            break;
                        }
                    }
                    break;
                case 6:
                    EncloseNode encloseNode = (EncloseNode) nodeExpandCaseFoldString;
                    switch (encloseNode.type) {
                        case 1:
                            if ((i & 11) != 0) {
                                ScanEnvironment scanEnvironment3 = this.env;
                                int i12 = this.env.btMemStart;
                                int i13 = encloseNode.regNum;
                                if (i13 < 32) {
                                    i2 = i12 | (1 << i13);
                                } else {
                                    i2 = i12 | 1;
                                }
                                scanEnvironment3.btMemStart = i2;
                            }
                            setupTree(encloseNode.target, i);
                            break;
                        case 2:
                            int i14 = this.regex.options;
                            this.regex.options = encloseNode.option;
                            setupTree(encloseNode.target, i);
                            this.regex.options = i14;
                            break;
                        case 4:
                            setupTree(encloseNode.target, i);
                            if (encloseNode.target.getType() == 5) {
                                QuantifierNode quantifierNode3 = (QuantifierNode) encloseNode.target;
                                if ((quantifierNode3.upper == -1) && quantifierNode3.lower <= 1 && quantifierNode3.greedy && quantifierNode3.target.isSimple()) {
                                    encloseNode.setStopBtSimpleRepeat();
                                    break;
                                }
                            }
                            break;
                    }
                case 7:
                    AnchorNode anchorNode = (AnchorNode) nodeExpandCaseFoldString;
                    switch (anchorNode.type) {
                        case 1024:
                            setupTree(anchorNode.target, i);
                            break;
                        case 2048:
                            setupTree(anchorNode.target, i | 2);
                            break;
                        case 4096:
                            if (checkTypeTree(anchorNode.target, NodeType.ALLOWED_IN_LB, 1, AnchorType.ALLOWED_IN_LB)) {
                                throw new SyntaxException(ErrorMessages.ERR_INVALID_LOOK_BEHIND_PATTERN);
                            }
                            nodeExpandCaseFoldString = setupLookBehind(nodeExpandCaseFoldString);
                            if (nodeExpandCaseFoldString.getType() == 7) {
                                setupTree(((AnchorNode) nodeExpandCaseFoldString).target, i);
                                break;
                            }
                        case 8192:
                            if (checkTypeTree(anchorNode.target, NodeType.ALLOWED_IN_LB, 1, AnchorType.ALLOWED_IN_LB)) {
                                throw new SyntaxException(ErrorMessages.ERR_INVALID_LOOK_BEHIND_PATTERN);
                            }
                            nodeExpandCaseFoldString = setupLookBehind(nodeExpandCaseFoldString);
                            if (nodeExpandCaseFoldString.getType() == 7) {
                                setupTree(((AnchorNode) nodeExpandCaseFoldString).target, i | 2);
                                break;
                            }
                    }
                    break;
                case 8:
                    ConsAltNode consAltNode3 = (ConsAltNode) nodeExpandCaseFoldString;
                    Node node4 = null;
                    do {
                        setupTree(consAltNode3.car, i);
                        if (node4 != null) {
                            nextSetup(node4, consAltNode3.car);
                        }
                        node4 = consAltNode3.car;
                        consAltNode2 = consAltNode3.cdr;
                        consAltNode3 = consAltNode2;
                    } while (consAltNode2 != null);
                case 9:
                    ConsAltNode consAltNode4 = (ConsAltNode) nodeExpandCaseFoldString;
                    do {
                        setupTree(consAltNode4.car, i | 1);
                        consAltNode = consAltNode4.cdr;
                        consAltNode4 = consAltNode;
                    } while (consAltNode != null);
            }
        }
        return nodeExpandCaseFoldString;
    }

    /* JADX WARN: Removed duplicated region for block: B:91:0x03d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void optimizeNodeLeft(Node node, NodeOptInfo nodeOptInfo, OptEnvironment optEnvironment) {
        int i;
        int i2;
        int i3;
        int length;
        ConsAltNode consAltNode;
        ConsAltNode consAltNode2;
        nodeOptInfo.clear();
        nodeOptInfo.setBoundNode(optEnvironment.mmd);
        switch (node.getType()) {
            case 0:
                StringNode stringNode = (StringNode) node;
                int length2 = stringNode.length();
                if (!stringNode.isAmbig()) {
                    nodeOptInfo.exb.concatStr(stringNode.chars, stringNode.f86p, stringNode.end, stringNode.isRaw());
                    if (length2 > 0) {
                        nodeOptInfo.map.addChar(stringNode.chars[stringNode.f86p]);
                    }
                    nodeOptInfo.length.set(length2, length2);
                } else {
                    if (stringNode.isDontGetOptInfo()) {
                        length = stringNode.length();
                    } else {
                        nodeOptInfo.exb.concatStr(stringNode.chars, stringNode.f86p, stringNode.end, stringNode.isRaw());
                        nodeOptInfo.exb.ignoreCase = true;
                        if (length2 > 0) {
                            nodeOptInfo.map.addCharAmb(stringNode.chars, stringNode.f86p, stringNode.end, optEnvironment.caseFoldFlag);
                        }
                        length = length2;
                    }
                    nodeOptInfo.length.set(length2, length);
                }
                if (nodeOptInfo.exb.length == length2) {
                    nodeOptInfo.exb.reachEnd = true;
                    return;
                }
                return;
            case 1:
                CClassNode cClassNode = (CClassNode) node;
                if (cClassNode.mbuf != null || cClassNode.isNot()) {
                    nodeOptInfo.length.set(1, 1);
                    return;
                }
                for (int i4 = 0; i4 < 256; i4++) {
                    boolean zM20at = cClassNode.f78bs.m20at(i4);
                    if ((zM20at && !cClassNode.isNot()) || (!zM20at && cClassNode.isNot())) {
                        nodeOptInfo.map.addChar(i4);
                    }
                }
                nodeOptInfo.length.set(1, 1);
                return;
            case 2:
            default:
                throw new InternalException(ErrorMessages.ERR_PARSER_BUG);
            case 3:
                nodeOptInfo.length.set(1, 1);
                return;
            case 4:
                BackRefNode backRefNode = (BackRefNode) node;
                if (backRefNode.isRecursion()) {
                    nodeOptInfo.length.set(0, Integer.MAX_VALUE);
                    return;
                } else {
                    Node[] nodeArr = optEnvironment.scanEnv.memNodes;
                    nodeOptInfo.length.set(getMinMatchLength(nodeArr[backRefNode.backRef]), getMaxMatchLength(nodeArr[backRefNode.backRef]));
                    return;
                }
            case 5:
                NodeOptInfo nodeOptInfo2 = new NodeOptInfo();
                QuantifierNode quantifierNode = (QuantifierNode) node;
                optimizeNodeLeft(quantifierNode.target, nodeOptInfo2, optEnvironment);
                if (quantifierNode.lower == 0) {
                    if (quantifierNode.upper == -1) {
                        if (optEnvironment.mmd.max == 0 && quantifierNode.target.getType() == 3 && quantifierNode.greedy) {
                            if ((optEnvironment.options & 4) != 0) {
                                nodeOptInfo.anchor.add(32768);
                            } else {
                                nodeOptInfo.anchor.add(16384);
                            }
                        }
                    }
                } else if (quantifierNode.lower > 0) {
                    nodeOptInfo.copy(nodeOptInfo2);
                    if (nodeOptInfo2.exb.length > 0 && nodeOptInfo2.exb.reachEnd) {
                        int i5 = 2;
                        while (i5 <= quantifierNode.lower && !nodeOptInfo.exb.isFull()) {
                            nodeOptInfo.exb.concat(nodeOptInfo2.exb);
                            i5++;
                        }
                        if (i5 < quantifierNode.lower) {
                            nodeOptInfo.exb.reachEnd = false;
                        }
                    }
                    if (quantifierNode.lower != quantifierNode.upper) {
                        nodeOptInfo.exb.reachEnd = false;
                        nodeOptInfo.exm.reachEnd = false;
                    }
                    if (quantifierNode.lower > 1) {
                        nodeOptInfo.exm.reachEnd = false;
                    }
                }
                int i6 = nodeOptInfo2.length.min;
                int i7 = quantifierNode.lower;
                if (i7 == 0) {
                    i = 0;
                } else if (i6 < Integer.MAX_VALUE / i7) {
                    i = i6 * i7;
                } else {
                    i = Integer.MAX_VALUE;
                }
                int i8 = i;
                if (quantifierNode.upper == -1) {
                    i3 = nodeOptInfo2.length.max > 0 ? Integer.MAX_VALUE : 0;
                } else {
                    int i9 = nodeOptInfo2.length.max;
                    int i10 = quantifierNode.upper;
                    if (i10 == 0) {
                        i2 = 0;
                    } else if (i9 < Integer.MAX_VALUE / i10) {
                        i2 = i9 * i10;
                    } else {
                        i2 = Integer.MAX_VALUE;
                    }
                    i3 = i2;
                }
                nodeOptInfo.length.set(i8, i3);
                return;
            case 6:
                EncloseNode encloseNode = (EncloseNode) node;
                switch (encloseNode.type) {
                    case 1:
                        int i11 = encloseNode.optCount + 1;
                        encloseNode.optCount = i11;
                        if (i11 > 5) {
                            int i12 = 0;
                            int i13 = Integer.MAX_VALUE;
                            if (encloseNode.isMinFixed()) {
                                i12 = encloseNode.minLength;
                            }
                            if (encloseNode.isMaxFixed()) {
                                i13 = encloseNode.maxLength;
                            }
                            nodeOptInfo.length.set(i12, i13);
                            return;
                        }
                        optimizeNodeLeft(encloseNode.target, nodeOptInfo, optEnvironment);
                        if (nodeOptInfo.anchor.isSet(AnchorType.ANYCHAR_STAR_MASK)) {
                            int i14 = optEnvironment.scanEnv.backrefedMem;
                            int i15 = encloseNode.regNum;
                            if ((i15 < 32 ? i14 & (1 << i15) : i14 & 1) != 0) {
                                nodeOptInfo.anchor.remove(AnchorType.ANYCHAR_STAR_MASK);
                                return;
                            }
                            return;
                        }
                        return;
                    case 2:
                        int i16 = optEnvironment.options;
                        optEnvironment.options = encloseNode.option;
                        optimizeNodeLeft(encloseNode.target, nodeOptInfo, optEnvironment);
                        optEnvironment.options = i16;
                        return;
                    case 3:
                    default:
                        return;
                    case 4:
                        optimizeNodeLeft(encloseNode.target, nodeOptInfo, optEnvironment);
                        return;
                }
            case 7:
                AnchorNode anchorNode = (AnchorNode) node;
                switch (anchorNode.type) {
                    case 1:
                    case 2:
                    case 4:
                    case 8:
                    case 16:
                    case 32:
                        nodeOptInfo.anchor.add(anchorNode.type);
                        return;
                    case 1024:
                        NodeOptInfo nodeOptInfo3 = new NodeOptInfo();
                        optimizeNodeLeft(anchorNode.target, nodeOptInfo3, optEnvironment);
                        if (nodeOptInfo3.exb.length > 0) {
                            nodeOptInfo.expr.copy(nodeOptInfo3.exb);
                        } else if (nodeOptInfo3.exm.length > 0) {
                            nodeOptInfo.expr.copy(nodeOptInfo3.exm);
                        }
                        nodeOptInfo.expr.reachEnd = false;
                        if (nodeOptInfo3.map.value > 0) {
                            nodeOptInfo.map.copy(nodeOptInfo3.map);
                            return;
                        }
                        return;
                    case 2048:
                    case 4096:
                    case 8192:
                    default:
                        return;
                }
            case 8:
                OptEnvironment optEnvironment2 = new OptEnvironment();
                NodeOptInfo nodeOptInfo4 = new NodeOptInfo();
                optEnvironment2.copy(optEnvironment);
                ConsAltNode consAltNode3 = (ConsAltNode) node;
                do {
                    optimizeNodeLeft(consAltNode3.car, nodeOptInfo4, optEnvironment2);
                    optEnvironment2.mmd.add(nodeOptInfo4.length);
                    nodeOptInfo.concatLeftNode(nodeOptInfo4);
                    consAltNode2 = consAltNode3.cdr;
                    consAltNode3 = consAltNode2;
                } while (consAltNode2 != null);
                return;
            case 9:
                NodeOptInfo nodeOptInfo5 = new NodeOptInfo();
                ConsAltNode consAltNode4 = (ConsAltNode) node;
                do {
                    optimizeNodeLeft(consAltNode4.car, nodeOptInfo5, optEnvironment);
                    if (consAltNode4 == node) {
                        nodeOptInfo.copy(nodeOptInfo5);
                    } else {
                        nodeOptInfo.altMerge(nodeOptInfo5, optEnvironment);
                    }
                    consAltNode = consAltNode4.cdr;
                    consAltNode4 = consAltNode;
                } while (consAltNode != null);
                return;
        }
    }

    protected final void setOptimizedInfoFromTree(Node node) {
        NodeOptInfo nodeOptInfo = new NodeOptInfo();
        OptEnvironment optEnvironment = new OptEnvironment();
        optEnvironment.options = this.regex.options;
        optEnvironment.caseFoldFlag = this.regex.caseFoldFlag;
        optEnvironment.scanEnv = this.env;
        optEnvironment.mmd.clear();
        optimizeNodeLeft(node, nodeOptInfo, optEnvironment);
        this.regex.anchor = nodeOptInfo.anchor.leftAnchor & 49157;
        this.regex.anchor |= nodeOptInfo.anchor.rightAnchor & 24;
        if ((this.regex.anchor & 24) != 0) {
            this.regex.anchorDmin = nodeOptInfo.length.min;
            this.regex.anchorDmax = nodeOptInfo.length.max;
        }
        if (nodeOptInfo.exb.length > 0 || nodeOptInfo.exm.length > 0) {
            nodeOptInfo.exb.select(nodeOptInfo.exm);
            if (nodeOptInfo.map.value > 0 && nodeOptInfo.exb.compare(nodeOptInfo.map) > 0) {
                this.regex.setOptimizeMapInfo(nodeOptInfo.map);
                this.regex.setSubAnchor(nodeOptInfo.map.anchor);
                return;
            } else {
                this.regex.setExactInfo(nodeOptInfo.exb);
                this.regex.setSubAnchor(nodeOptInfo.exb.anchor);
                return;
            }
        }
        if (nodeOptInfo.map.value > 0) {
            this.regex.setOptimizeMapInfo(nodeOptInfo.map);
            this.regex.setSubAnchor(nodeOptInfo.map.anchor);
            return;
        }
        this.regex.subAnchor |= nodeOptInfo.anchor.leftAnchor & 2;
        if (nodeOptInfo.length.max == 0) {
            this.regex.subAnchor |= nodeOptInfo.anchor.rightAnchor & 32;
        }
    }
}
