package jdk.nashorn.internal.p001ir;

import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/BreakableStatement.class */
abstract class BreakableStatement extends LexicalContextStatement implements BreakableNode {
    private static final long serialVersionUID = 1;
    protected final Label breakLabel;
    final LocalVariableConversion conversion;

    abstract JoinPredecessor setLocalVariableConversionChanged(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion);

    protected BreakableStatement(int i, long j, int i2, Label label) {
        super(i, j, i2);
        this.breakLabel = label;
        this.conversion = null;
    }

    protected BreakableStatement(BreakableStatement breakableStatement, LocalVariableConversion localVariableConversion) {
        super(breakableStatement);
        this.breakLabel = new Label(breakableStatement.getBreakLabel());
        this.conversion = localVariableConversion;
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableNode
    public Label getBreakLabel() {
        return this.breakLabel;
    }

    @Override // jdk.nashorn.internal.p001ir.Labels
    public List getLabels() {
        return Collections.unmodifiableList(Collections.singletonList(this.breakLabel));
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        if (this.conversion == localVariableConversion) {
            return this;
        }
        return setLocalVariableConversionChanged(lexicalContext, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }
}
