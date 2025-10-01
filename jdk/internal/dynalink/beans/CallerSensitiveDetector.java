package jdk.internal.dynalink.beans;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import sun.reflect.CallerSensitive;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/CallerSensitiveDetector.class */
public class CallerSensitiveDetector {
    private static final DetectionStrategy DETECTION_STRATEGY = getDetectionStrategy();

    static boolean isCallerSensitive(AccessibleObject accessibleObject) {
        return DETECTION_STRATEGY.isCallerSensitive(accessibleObject);
    }

    private static DetectionStrategy getDetectionStrategy() {
        try {
            return new PrivilegedDetectionStrategy(null);
        } catch (Throwable unused) {
            return new UnprivilegedDetectionStrategy(null);
        }
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/CallerSensitiveDetector$DetectionStrategy.class */
    private static abstract class DetectionStrategy {
        abstract boolean isCallerSensitive(AccessibleObject accessibleObject);

        private DetectionStrategy() {
        }

        DetectionStrategy(C00091 c00091) {
            this();
        }
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/CallerSensitiveDetector$PrivilegedDetectionStrategy.class */
    private static class PrivilegedDetectionStrategy extends DetectionStrategy {
        private PrivilegedDetectionStrategy() {
            super(null);
        }

        PrivilegedDetectionStrategy(C00091 c00091) {
            this();
        }

        @Override // jdk.internal.dynalink.beans.CallerSensitiveDetector.DetectionStrategy
        boolean isCallerSensitive(AccessibleObject accessibleObject) {
            return accessibleObject.getAnnotation(CallerSensitive.class) != null;
        }
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/CallerSensitiveDetector$UnprivilegedDetectionStrategy.class */
    private static class UnprivilegedDetectionStrategy extends DetectionStrategy {
        private static final String CALLER_SENSITIVE_ANNOTATION_STRING = "@sun.reflect.CallerSensitive()";

        private UnprivilegedDetectionStrategy() {
            super(null);
        }

        UnprivilegedDetectionStrategy(C00091 c00091) {
            this();
        }

        @Override // jdk.internal.dynalink.beans.CallerSensitiveDetector.DetectionStrategy
        boolean isCallerSensitive(AccessibleObject accessibleObject) {
            for (Annotation annotation : accessibleObject.getAnnotations()) {
                if (String.valueOf(annotation).equals(CALLER_SENSITIVE_ANNOTATION_STRING)) {
                    return true;
                }
            }
            return false;
        }
    }
}
