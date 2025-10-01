package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.objectweb.asm.util.CheckClassAdapter;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.transformers.MixinClassWriter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ext/extensions/ExtensionCheckClass.class */
public class ExtensionCheckClass implements IExtension {

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ext/extensions/ExtensionCheckClass$ValidationFailedException.class */
    public static class ValidationFailedException extends MixinException {
        private static final long serialVersionUID = 1;

        public ValidationFailedException(String str, Throwable th) {
            super(str, th);
        }

        public ValidationFailedException(String str) {
            super(str);
        }

        public ValidationFailedException(Throwable th) {
            super(th);
        }
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtension
    public boolean checkActive(MixinEnvironment mixinEnvironment) {
        return mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERIFY);
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtension
    public void postApply(ITargetClassContext iTargetClassContext) {
        try {
            iTargetClassContext.getClassNode().accept(new CheckClassAdapter(new MixinClassWriter(2)));
        } catch (RuntimeException e) {
            throw new ValidationFailedException(e.getMessage(), e);
        }
    }
}
