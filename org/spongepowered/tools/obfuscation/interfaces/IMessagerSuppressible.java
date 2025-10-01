package org.spongepowered.tools.obfuscation.interfaces;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import org.spongepowered.tools.obfuscation.SuppressedBy;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/interfaces/IMessagerSuppressible.class */
public interface IMessagerSuppressible extends Messager {
    void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, SuppressedBy suppressedBy);

    void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror, SuppressedBy suppressedBy);

    void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue, SuppressedBy suppressedBy);
}
