package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.Label;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/JumpStatement.class */
public abstract class JumpStatement extends Statement implements JoinPredecessor {
    private static final long serialVersionUID = 1;
    private final String labelName;
    private final LocalVariableConversion conversion;

    abstract String getStatementName();

    public abstract BreakableNode getTarget(LexicalContext lexicalContext);

    abstract Label getTargetLabel(BreakableNode breakableNode);

    abstract JumpStatement createNewJumpStatement(LocalVariableConversion localVariableConversion);

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return setLocalVariableConversion(lexicalContext, localVariableConversion);
    }

    protected JumpStatement(int i, long j, int i2, String str) {
        super(i, j, i2);
        this.labelName = str;
        this.conversion = null;
    }

    protected JumpStatement(JumpStatement jumpStatement, LocalVariableConversion localVariableConversion) {
        super(jumpStatement);
        this.labelName = jumpStatement.labelName;
        this.conversion = localVariableConversion;
    }

    public String getLabelName() {
        return this.labelName;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append(getStatementName());
        if (this.labelName != null) {
            sb.append(' ').append(this.labelName);
        }
    }

    public Label getTargetLabel(LexicalContext lexicalContext) {
        return getTargetLabel(getTarget(lexicalContext));
    }

    public LexicalContextNode getPopScopeLimit(LexicalContext lexicalContext) {
        return getTarget(lexicalContext);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JumpStatement setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        if (this.conversion == localVariableConversion) {
            return this;
        }
        return createNewJumpStatement(localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }
}
