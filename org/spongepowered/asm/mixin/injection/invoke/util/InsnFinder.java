package org.spongepowered.asm.mixin.injection.invoke.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.Interpreter;
import org.objectweb.asm.tree.analysis.Value;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.injection.struct.Target;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/util/InsnFinder.class */
public class InsnFinder {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/util/InsnFinder$AnalyzerState.class */
    enum AnalyzerState {
        SEARCH,
        ANALYSE,
        COMPLETE
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/util/InsnFinder$AnalysisResultException.class */
    static class AnalysisResultException extends RuntimeException {
        private static final long serialVersionUID = 1;
        private AbstractInsnNode result;

        public AnalysisResultException(AbstractInsnNode abstractInsnNode) {
            this.result = abstractInsnNode;
        }

        public AbstractInsnNode getResult() {
            return this.result;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/util/InsnFinder$PopAnalyzer.class */
    static class PopAnalyzer extends Analyzer {
        protected final AbstractInsnNode node;

        /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/util/InsnFinder$PopAnalyzer$PopFrame.class */
        class PopFrame extends Frame {
            private AbstractInsnNode current;
            private AnalyzerState state;
            private int depth;
            final PopAnalyzer this$0;

            public void push(Value value) {
                push((BasicValue) value);
            }

            /* renamed from: pop, reason: collision with other method in class */
            public Value m1885pop() {
                return pop();
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public PopFrame(PopAnalyzer popAnalyzer, int i, int i2) {
                super(i, i2);
                this.this$0 = popAnalyzer;
                this.state = AnalyzerState.SEARCH;
                this.depth = 0;
            }

            public void execute(AbstractInsnNode abstractInsnNode, Interpreter interpreter) {
                this.current = abstractInsnNode;
                super.execute(abstractInsnNode, interpreter);
            }

            public void push(BasicValue basicValue) {
                if (this.current == this.this$0.node && this.state == AnalyzerState.SEARCH) {
                    this.state = AnalyzerState.ANALYSE;
                    this.depth++;
                } else if (this.state == AnalyzerState.ANALYSE) {
                    this.depth++;
                }
                super.push(basicValue);
            }

            public BasicValue pop() {
                if (this.state == AnalyzerState.ANALYSE) {
                    int i = this.depth - 1;
                    this.depth = i;
                    if (i == 0) {
                        this.state = AnalyzerState.COMPLETE;
                        throw new AnalysisResultException(this.current);
                    }
                }
                return super.pop();
            }
        }

        public PopAnalyzer(AbstractInsnNode abstractInsnNode) {
            super(new BasicInterpreter());
            this.node = abstractInsnNode;
        }

        protected Frame newFrame(int i, int i2) {
            return new PopFrame(this, i, i2);
        }
    }

    public AbstractInsnNode findPopInsn(Target target, AbstractInsnNode abstractInsnNode) {
        try {
            new PopAnalyzer(abstractInsnNode).analyze(target.classNode.name, target.method);
            return null;
        } catch (AnalyzerException e) {
            if (e.getCause() instanceof AnalysisResultException) {
                return ((AnalysisResultException) e.getCause()).getResult();
            }
            logger.catching(e);
            return null;
        }
    }
}
