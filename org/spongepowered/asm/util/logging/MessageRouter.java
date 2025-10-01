package org.spongepowered.asm.util.logging;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinLaunchPlugin;

/* loaded from: L-out.jar:org/spongepowered/asm/util/logging/MessageRouter.class */
public final class MessageRouter {
    private static Messager messager;

    /* loaded from: L-out.jar:org/spongepowered/asm/util/logging/MessageRouter$LoggingMessager.class */
    static class LoggingMessager implements Messager {
        private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);

        LoggingMessager() {
        }

        public void printMessage(Diagnostic.Kind kind, CharSequence charSequence) {
            logger.log(messageKindToLoggingLevel(kind), charSequence);
        }

        public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element) {
            logger.log(messageKindToLoggingLevel(kind), charSequence);
        }

        public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror) {
            logger.log(messageKindToLoggingLevel(kind), charSequence);
        }

        public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
            logger.log(messageKindToLoggingLevel(kind), charSequence);
        }

        private static Level messageKindToLoggingLevel(Diagnostic.Kind kind) {
            switch (C05751.$SwitchMap$javax$tools$Diagnostic$Kind[kind.ordinal()]) {
                case 1:
                    return Level.ERROR;
                case 2:
                case 3:
                    return Level.WARN;
                case 4:
                    return Level.INFO;
                case 5:
                default:
                    return Level.DEBUG;
            }
        }
    }

    /* renamed from: org.spongepowered.asm.util.logging.MessageRouter$1 */
    /* loaded from: L-out.jar:org/spongepowered/asm/util/logging/MessageRouter$1.class */
    static /* synthetic */ class C05751 {
        static final int[] $SwitchMap$javax$tools$Diagnostic$Kind = new int[Diagnostic.Kind.values().length];

        static {
            try {
                $SwitchMap$javax$tools$Diagnostic$Kind[Diagnostic.Kind.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$javax$tools$Diagnostic$Kind[Diagnostic.Kind.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$javax$tools$Diagnostic$Kind[Diagnostic.Kind.MANDATORY_WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$javax$tools$Diagnostic$Kind[Diagnostic.Kind.NOTE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$javax$tools$Diagnostic$Kind[Diagnostic.Kind.OTHER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/logging/MessageRouter$DebugInterceptingMessager.class */
    static class DebugInterceptingMessager implements Messager {
        private final Messager wrapped;

        DebugInterceptingMessager(Messager messager) {
            this.wrapped = messager;
        }

        public void printMessage(Diagnostic.Kind kind, CharSequence charSequence) {
            if (kind != Diagnostic.Kind.OTHER) {
                this.wrapped.printMessage(kind, charSequence);
            }
        }

        public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element) {
            if (kind != Diagnostic.Kind.OTHER) {
                this.wrapped.printMessage(kind, charSequence, element);
            }
        }

        public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror) {
            if (kind != Diagnostic.Kind.OTHER) {
                this.wrapped.printMessage(kind, charSequence, element, annotationMirror);
            }
        }

        public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
            if (kind != Diagnostic.Kind.OTHER) {
                this.wrapped.printMessage(kind, charSequence, element, annotationMirror, annotationValue);
            }
        }
    }

    private MessageRouter() {
    }

    public static Messager getMessager() {
        if (messager == null) {
            messager = new LoggingMessager();
        }
        return messager;
    }

    public static void setMessager(Messager messager2) {
        messager = messager2 == null ? null : new DebugInterceptingMessager(messager2);
    }
}
