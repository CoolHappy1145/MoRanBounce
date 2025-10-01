package jdk.nashorn.internal.objects.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkRequest;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: L-out.jar:jdk/nashorn/internal/objects/annotations/SpecializedFunction.class */
public @interface SpecializedFunction {
    String name() default "";

    Class linkLogic() default LinkLogic.Empty.class;

    boolean isConstructor() default false;

    boolean isOptimistic() default false;

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/annotations/SpecializedFunction$LinkLogic.class */
    public static abstract class LinkLogic {
        public static final LinkLogic EMPTY_INSTANCE = new Empty(null);

        public abstract boolean canLink(Object obj, CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest);

        /* loaded from: L-out.jar:jdk/nashorn/internal/objects/annotations/SpecializedFunction$LinkLogic$Empty.class */
        private static final class Empty extends LinkLogic {
            private Empty() {
            }

            Empty(C01981 c01981) {
                this();
            }
        }

        public boolean checkLinkable(Object obj, CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
            return canLink(obj, callSiteDescriptor, linkRequest);
        }
    }
}
