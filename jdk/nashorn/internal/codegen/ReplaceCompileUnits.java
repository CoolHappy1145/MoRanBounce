package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.p001ir.CompileUnitHolder;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ObjectNode;
import jdk.nashorn.internal.p001ir.Splittable;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ReplaceCompileUnits.class */
abstract class ReplaceCompileUnits extends SimpleNodeVisitor {
    static final boolean $assertionsDisabled;

    abstract CompileUnit getReplacement(CompileUnit compileUnit);

    ReplaceCompileUnits() {
    }

    static {
        $assertionsDisabled = !ReplaceCompileUnits.class.desiredAssertionStatus();
    }

    CompileUnit getExistingReplacement(CompileUnitHolder compileUnitHolder) {
        CompileUnit compileUnit = compileUnitHolder.getCompileUnit();
        if (!$assertionsDisabled && compileUnit == null) {
            throw new AssertionError();
        }
        CompileUnit replacement = getReplacement(compileUnit);
        if ($assertionsDisabled || replacement != null) {
            return replacement;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        return functionNode.setCompileUnit(this.f30lc, getExistingReplacement(functionNode));
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveLiteralNode(LiteralNode literalNode) {
        if (literalNode instanceof LiteralNode.ArrayLiteralNode) {
            LiteralNode.ArrayLiteralNode arrayLiteralNode = (LiteralNode.ArrayLiteralNode) literalNode;
            if (arrayLiteralNode.getSplitRanges() == null) {
                return literalNode;
            }
            ArrayList arrayList = new ArrayList();
            for (Splittable.SplitRange splitRange : arrayLiteralNode.getSplitRanges()) {
                arrayList.add(new Splittable.SplitRange(getExistingReplacement(splitRange), splitRange.getLow(), splitRange.getHigh()));
            }
            return arrayLiteralNode.setSplitRanges(this.f30lc, arrayList);
        }
        return literalNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveObjectNode(ObjectNode objectNode) {
        List<Splittable.SplitRange> splitRanges = objectNode.getSplitRanges();
        if (splitRanges != null) {
            ArrayList arrayList = new ArrayList();
            for (Splittable.SplitRange splitRange : splitRanges) {
                arrayList.add(new Splittable.SplitRange(getExistingReplacement(splitRange), splitRange.getLow(), splitRange.getHigh()));
            }
            return objectNode.setSplitRanges(this.f30lc, arrayList);
        }
        return super.leaveObjectNode(objectNode);
    }
}
