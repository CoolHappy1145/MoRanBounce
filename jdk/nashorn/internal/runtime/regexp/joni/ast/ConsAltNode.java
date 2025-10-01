package jdk.nashorn.internal.runtime.regexp.joni.ast;

import java.util.Set;
import jdk.nashorn.internal.runtime.regexp.joni.WarnCallback;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.apache.log4j.helpers.DateLayout;
import org.spongepowered.asm.mixin.transformer.ActivityStack;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/ConsAltNode.class */
public final class ConsAltNode extends Node {
    public Node car;
    public ConsAltNode cdr;
    private int type;

    private ConsAltNode(Node node, ConsAltNode consAltNode, int i) {
        this.car = node;
        if (node != null) {
            node.parent = this;
        }
        this.cdr = consAltNode;
        if (consAltNode != null) {
            consAltNode.parent = this;
        }
        this.type = i;
    }

    public static ConsAltNode newAltNode(Node node, ConsAltNode consAltNode) {
        return new ConsAltNode(node, consAltNode, 9);
    }

    public static ConsAltNode newListNode(Node node, ConsAltNode consAltNode) {
        return new ConsAltNode(node, consAltNode, 8);
    }

    public static ConsAltNode listAdd(ConsAltNode consAltNode, Node node) {
        ConsAltNode consAltNodeNewListNode = newListNode(node, null);
        ConsAltNode consAltNode2 = consAltNode;
        if (consAltNode2 != null) {
            while (consAltNode2.cdr != null) {
                consAltNode2 = consAltNode2.cdr;
            }
            consAltNode2.setCdr(consAltNodeNewListNode);
        }
        return consAltNodeNewListNode;
    }

    public void toListNode() {
        this.type = 8;
    }

    public void toAltNode() {
        this.type = 9;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public int getType() {
        return this.type;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    protected void setChild(Node node) {
        this.car = node;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    protected Node getChild() {
        return this.car;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public void swap(Node node) {
        if (this.cdr != null) {
            this.cdr.parent = node;
            if (node instanceof ConsAltNode) {
                ConsAltNode consAltNode = (ConsAltNode) node;
                consAltNode.cdr.parent = this;
                ConsAltNode consAltNode2 = this.cdr;
                this.cdr = consAltNode.cdr;
                consAltNode.cdr = consAltNode2;
            }
        }
        super.swap(node);
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public void verifyTree(Set set, WarnCallback warnCallback) {
        if (!set.contains(this)) {
            set.add(this);
            if (this.car != null) {
                if (this.car.parent != this) {
                    warnCallback.warn("broken list car: " + getAddressName() + ActivityStack.GLUE_STRING + this.car.getAddressName());
                }
                this.car.verifyTree(set, warnCallback);
            }
            if (this.cdr != null) {
                if (this.cdr.parent != this) {
                    warnCallback.warn("broken list cdr: " + getAddressName() + ActivityStack.GLUE_STRING + this.cdr.getAddressName());
                }
                this.cdr.verifyTree(set, warnCallback);
            }
        }
    }

    public Node setCar(Node node) {
        this.car = node;
        node.parent = this;
        return this.car;
    }

    public ConsAltNode setCdr(ConsAltNode consAltNode) {
        this.cdr = consAltNode;
        consAltNode.parent = this;
        return this.cdr;
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String getName() {
        switch (this.type) {
            case 8:
                return "List";
            case 9:
                return "Alt";
            default:
                throw new InternalException(ErrorMessages.ERR_PARSER_BUG);
        }
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String toString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n  car: " + pad(this.car, i + 1));
        sb.append("\n  cdr: " + (this.cdr == null ? DateLayout.NULL_DATE_FORMAT : this.cdr.toString()));
        return sb.toString();
    }
}
