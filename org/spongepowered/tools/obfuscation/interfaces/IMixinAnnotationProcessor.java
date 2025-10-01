package org.spongepowered.tools.obfuscation.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import org.spongepowered.asm.util.ITokenProvider;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/interfaces/IMixinAnnotationProcessor.class */
public interface IMixinAnnotationProcessor extends IMessagerSuppressible, IOptionProvider {

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/interfaces/IMixinAnnotationProcessor$CompilerEnvironment.class */
    public enum CompilerEnvironment {
        JAVAC,
        JDT
    }

    CompilerEnvironment getCompilerEnvironment();

    ProcessingEnvironment getProcessingEnvironment();

    IObfuscationManager getObfuscationManager();

    ITokenProvider getTokenProvider();

    ITypeHandleProvider getTypeProvider();

    IJavadocProvider getJavadocProvider();
}
