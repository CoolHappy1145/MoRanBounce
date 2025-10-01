package jdk.nashorn.internal.p001ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/SwitchNode.class */
public final class SwitchNode extends BreakableStatement {
    private static final long serialVersionUID = 1;
    private final Expression expression;
    private final List cases;
    private final int defaultCaseIndex;
    private final boolean uniqueInteger;
    private final Symbol tag;

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return super.getLocalVariableConversion();
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return super.setLocalVariableConversion(lexicalContext, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.Labels
    public List getLabels() {
        return super.getLabels();
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.BreakableNode
    public Label getBreakLabel() {
        return super.getBreakLabel();
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableNode
    public boolean isBreakableWithoutLabel() {
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextStatement, jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    public SwitchNode(int i, long j, int i2, Expression expression, List list, CaseNode caseNode) {
        super(i, j, i2, new Label("switch_break"));
        this.expression = expression;
        this.cases = list;
        this.defaultCaseIndex = caseNode == null ? -1 : list.indexOf(caseNode);
        this.uniqueInteger = false;
        this.tag = null;
    }

    private SwitchNode(SwitchNode switchNode, Expression expression, List list, int i, LocalVariableConversion localVariableConversion, boolean z, Symbol symbol) {
        super(switchNode, localVariableConversion);
        this.expression = expression;
        this.cases = list;
        this.defaultCaseIndex = i;
        this.tag = symbol;
        this.uniqueInteger = z;
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableNode
    public Node ensureUniqueLabels(LexicalContext lexicalContext) {
        ArrayList arrayList = new ArrayList();
        for (CaseNode caseNode : this.cases) {
            arrayList.add(new CaseNode(caseNode, caseNode.getTest(), caseNode.getBody(), caseNode.getLocalVariableConversion()));
        }
        return (Node) Node.replaceInLexicalContext(lexicalContext, this, new SwitchNode(this, this.expression, arrayList, this.defaultCaseIndex, this.conversion, this.uniqueInteger, this.tag));
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        if (!this.cases.isEmpty() && this.defaultCaseIndex != -1) {
            Iterator it = this.cases.iterator();
            while (it.hasNext()) {
                if (!((CaseNode) it.next()).isTerminal()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterSwitchNode(this)) {
            return nodeVisitor.leaveSwitchNode(setExpression(lexicalContext, (Expression) this.expression.accept(nodeVisitor)).setCases(lexicalContext, Node.accept(nodeVisitor, this.cases), this.defaultCaseIndex));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append("switch (");
        this.expression.toString(sb, z);
        sb.append(')');
    }

    public CaseNode getDefaultCase() {
        if (this.defaultCaseIndex == -1) {
            return null;
        }
        return (CaseNode) this.cases.get(this.defaultCaseIndex);
    }

    public List getCases() {
        return Collections.unmodifiableList(this.cases);
    }

    public SwitchNode setCases(LexicalContext lexicalContext, List list) {
        return setCases(lexicalContext, list, this.defaultCaseIndex);
    }

    private SwitchNode setCases(LexicalContext lexicalContext, List list, int i) {
        if (this.cases == list) {
            return this;
        }
        return (SwitchNode) Node.replaceInLexicalContext(lexicalContext, this, new SwitchNode(this, this.expression, list, i, this.conversion, this.uniqueInteger, this.tag));
    }

    public SwitchNode setCases(LexicalContext lexicalContext, List list, CaseNode caseNode) {
        return setCases(lexicalContext, list, caseNode == null ? -1 : list.indexOf(caseNode));
    }

    public Expression getExpression() {
        return this.expression;
    }

    public SwitchNode setExpression(LexicalContext lexicalContext, Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return (SwitchNode) Node.replaceInLexicalContext(lexicalContext, this, new SwitchNode(this, expression, this.cases, this.defaultCaseIndex, this.conversion, this.uniqueInteger, this.tag));
    }

    public Symbol getTag() {
        return this.tag;
    }

    public SwitchNode setTag(LexicalContext lexicalContext, Symbol symbol) {
        if (this.tag == symbol) {
            return this;
        }
        return (SwitchNode) Node.replaceInLexicalContext(lexicalContext, this, new SwitchNode(this, this.expression, this.cases, this.defaultCaseIndex, this.conversion, this.uniqueInteger, symbol));
    }

    public boolean isUniqueInteger() {
        return this.uniqueInteger;
    }

    public SwitchNode setUniqueInteger(LexicalContext lexicalContext, boolean z) {
        if (this.uniqueInteger == z) {
            return this;
        }
        return (SwitchNode) Node.replaceInLexicalContext(lexicalContext, this, new SwitchNode(this, this.expression, this.cases, this.defaultCaseIndex, this.conversion, z, this.tag));
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement
    JoinPredecessor setLocalVariableConversionChanged(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return (JoinPredecessor) Node.replaceInLexicalContext(lexicalContext, this, new SwitchNode(this, this.expression, this.cases, this.defaultCaseIndex, localVariableConversion, this.uniqueInteger, this.tag));
    }
}
