package kotlin.jvm.internal;

import java.util.List;
import kotlin.SinceKotlin;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KType;

/* loaded from: L-out.jar:kotlin/jvm/internal/ReflectionFactory.class */
public class ReflectionFactory {
    private static final String KOTLIN_JVM_FUNCTIONS = "kotlin.jvm.functions.";

    public KClass createKotlinClass(Class cls) {
        return new ClassReference(cls);
    }

    public KClass createKotlinClass(Class cls, String str) {
        return new ClassReference(cls);
    }

    public KDeclarationContainer getOrCreateKotlinPackage(Class cls, String str) {
        return new PackageReference(cls, str);
    }

    public KClass getOrCreateKotlinClass(Class cls) {
        return new ClassReference(cls);
    }

    public KClass getOrCreateKotlinClass(Class cls, String str) {
        return new ClassReference(cls);
    }

    @SinceKotlin(version = "1.1")
    public String renderLambdaToString(Lambda lambda) {
        return renderLambdaToString((FunctionBase) lambda);
    }

    @SinceKotlin(version = "1.3")
    public String renderLambdaToString(FunctionBase functionBase) {
        String string = functionBase.getClass().getGenericInterfaces()[0].toString();
        return string.startsWith(KOTLIN_JVM_FUNCTIONS) ? string.substring(21) : string;
    }

    @SinceKotlin(version = "1.4")
    public KType typeOf(KClassifier kClassifier, List list, boolean z) {
        return new TypeReference(kClassifier, list, z);
    }
}
