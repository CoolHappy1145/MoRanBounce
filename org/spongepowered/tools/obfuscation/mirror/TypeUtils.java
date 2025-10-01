package org.spongepowered.tools.obfuscation.mirror;

import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/TypeUtils.class */
public abstract class TypeUtils {
    private static final int MAX_GENERIC_RECURSION_DEPTH = 5;
    private static final String OBJECT_SIG = "java.lang.Object";

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/TypeUtils$Equivalency.class */
    public enum Equivalency {
        NOT_EQUIVALENT,
        EQUIVALENT_BUT_RAW,
        BOUNDS_MISMATCH,
        EQUIVALENT
    }

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/TypeUtils$EquivalencyResult.class */
    public static class EquivalencyResult {
        static final EquivalencyResult EQUIVALENT = new EquivalencyResult(Equivalency.EQUIVALENT, "", 0);
        public final Equivalency type;
        public final String detail;
        public final int rawType;

        EquivalencyResult(Equivalency equivalency, String str, int i) {
            this.type = equivalency;
            this.detail = str;
            this.rawType = i;
        }

        public String toString() {
            return this.detail;
        }

        static EquivalencyResult notEquivalent(String str, Object[] objArr) {
            return new EquivalencyResult(Equivalency.NOT_EQUIVALENT, String.format(str, objArr), 0);
        }

        static EquivalencyResult boundsMismatch(String str, Object[] objArr) {
            return new EquivalencyResult(Equivalency.BOUNDS_MISMATCH, String.format(str, objArr), 0);
        }

        static EquivalencyResult equivalentButRaw(int i) {
            return new EquivalencyResult(Equivalency.EQUIVALENT_BUT_RAW, String.format("Type %d is raw", Integer.valueOf(i)), i);
        }
    }

    private TypeUtils() {
    }

    public static PackageElement getPackage(TypeMirror typeMirror) {
        if (!(typeMirror instanceof DeclaredType)) {
            return null;
        }
        return getPackage(((DeclaredType) typeMirror).asElement());
    }

    public static PackageElement getPackage(TypeElement typeElement) {
        Element element;
        Element enclosingElement = typeElement.getEnclosingElement();
        while (true) {
            element = enclosingElement;
            if (element == null || (element instanceof PackageElement)) {
                break;
            }
            enclosingElement = element.getEnclosingElement();
        }
        return (PackageElement) element;
    }

    public static String getElementType(Element element) {
        if (element instanceof TypeElement) {
            return "TypeElement";
        }
        if (element instanceof ExecutableElement) {
            return "ExecutableElement";
        }
        if (element instanceof VariableElement) {
            return "VariableElement";
        }
        if (element instanceof PackageElement) {
            return "PackageElement";
        }
        if (element instanceof TypeParameterElement) {
            return "TypeParameterElement";
        }
        return element.getClass().getSimpleName();
    }

    public static String stripGenerics(String str) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '<') {
                i++;
            }
            if (i == 0) {
                sb.append(cCharAt);
            } else if (cCharAt == '>') {
                i--;
            }
        }
        return sb.toString();
    }

    public static String getName(VariableElement variableElement) {
        if (variableElement != null) {
            return variableElement.getSimpleName().toString();
        }
        return null;
    }

    public static String getName(ExecutableElement executableElement) {
        if (executableElement != null) {
            return executableElement.getSimpleName().toString();
        }
        return null;
    }

    public static String getJavaSignature(Element element) {
        if (element instanceof ExecutableElement) {
            ExecutableElement executableElement = (ExecutableElement) element;
            StringBuilder sbAppend = new StringBuilder().append("(");
            boolean z = false;
            for (VariableElement variableElement : executableElement.getParameters()) {
                if (z) {
                    sbAppend.append(',');
                }
                sbAppend.append(getTypeName(variableElement.asType()));
                z = true;
            }
            sbAppend.append(')').append(getTypeName(executableElement.getReturnType()));
            return sbAppend.toString();
        }
        return getTypeName(element.asType());
    }

    public static String getJavaSignature(String str) {
        return new SignaturePrinter("", str).setFullyQualified(true).toDescriptor();
    }

    public static String getSimpleName(TypeMirror typeMirror) {
        String typeName = getTypeName(typeMirror);
        int iLastIndexOf = typeName.lastIndexOf(46);
        return iLastIndexOf > 0 ? typeName.substring(iLastIndexOf + 1) : typeName;
    }

    public static String getTypeName(TypeMirror typeMirror) {
        switch (C05791.$SwitchMap$javax$lang$model$type$TypeKind[typeMirror.getKind().ordinal()]) {
            case 1:
                return getTypeName(((ArrayType) typeMirror).getComponentType()) + "[]";
            case 2:
                return getTypeName((DeclaredType) typeMirror);
            case 3:
                return getTypeName(getUpperBound(typeMirror));
            case 4:
                return OBJECT_SIG;
            default:
                return typeMirror.toString();
        }
    }

    public static String getTypeName(DeclaredType declaredType) {
        if (declaredType == null) {
            return OBJECT_SIG;
        }
        return getInternalName(declaredType.asElement()).replace('/', '.');
    }

    public static String getDescriptor(Element element) {
        if (element instanceof ExecutableElement) {
            return getDescriptor((ExecutableElement) element);
        }
        if (element instanceof VariableElement) {
            return getInternalName((VariableElement) element);
        }
        return getInternalName(element.asType());
    }

    public static String getDescriptor(ExecutableElement executableElement) {
        if (executableElement == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = executableElement.getParameters().iterator();
        while (it.hasNext()) {
            sb.append(getInternalName((VariableElement) it.next()));
        }
        return String.format("(%s)%s", sb, getInternalName(executableElement.getReturnType()));
    }

    public static String getInternalName(VariableElement variableElement) {
        if (variableElement == null) {
            return null;
        }
        return getInternalName(variableElement.asType());
    }

    public static String getInternalName(TypeMirror typeMirror) {
        switch (C05791.$SwitchMap$javax$lang$model$type$TypeKind[typeMirror.getKind().ordinal()]) {
            case 1:
                return "[" + getInternalName(((ArrayType) typeMirror).getComponentType());
            case 2:
                return "L" + getInternalName((DeclaredType) typeMirror) + ";";
            case 3:
                return "L" + getInternalName(getUpperBound(typeMirror)) + ";";
            case 4:
                return Constants.OBJECT_DESC;
            case 5:
                return "Z";
            case 6:
                return "B";
            case 7:
                return "C";
            case 8:
                return "D";
            case 9:
                return "F";
            case 10:
                return "I";
            case 11:
                return "J";
            case 12:
                return "S";
            case CharacterType.ALNUM /* 13 */:
                return "V";
            default:
                throw new IllegalArgumentException("Unable to parse type symbol " + typeMirror + " with " + typeMirror.getKind() + " to equivalent bytecode type");
        }
    }

    public static String getInternalName(DeclaredType declaredType) {
        if (declaredType == null) {
            return Constants.OBJECT;
        }
        return getInternalName(declaredType.asElement());
    }

    public static String getInternalName(TypeElement typeElement) {
        if (typeElement == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) typeElement.getSimpleName());
        Element enclosingElement = typeElement.getEnclosingElement();
        while (true) {
            Element element = enclosingElement;
            if (element != null) {
                if (element instanceof TypeElement) {
                    sb.insert(0, ArgsClassGenerator.GETTER_PREFIX).insert(0, (CharSequence) element.getSimpleName());
                } else if (element instanceof PackageElement) {
                    sb.insert(0, "/").insert(0, ((PackageElement) element).getQualifiedName().toString().replace('.', '/'));
                }
                enclosingElement = element.getEnclosingElement();
            } else {
                return sb.toString();
            }
        }
    }

    private static DeclaredType getUpperBound(TypeMirror typeMirror) {
        try {
            return getUpperBound0(typeMirror, 5);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + typeMirror, e);
        } catch (IllegalStateException e2) {
            throw new IllegalArgumentException("Type symbol \"" + typeMirror + "\" is too complex", e2);
        }
    }

    private static DeclaredType getUpperBound0(TypeMirror typeMirror, int i) {
        if (i == 0) {
            throw new IllegalStateException("Generic symbol \"" + typeMirror + "\" is too complex, exceeded 5 iterations attempting to determine upper bound");
        }
        if (typeMirror instanceof DeclaredType) {
            return (DeclaredType) typeMirror;
        }
        if (typeMirror instanceof TypeVariable) {
            try {
                return getUpperBound0(((TypeVariable) typeMirror).getUpperBound(), i - 1);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (IllegalStateException e2) {
                throw e2;
            } catch (Exception unused) {
                throw new IllegalArgumentException("Unable to compute upper bound of type symbol " + typeMirror);
            }
        }
        return null;
    }

    private static String describeGenericBound(TypeMirror typeMirror) {
        if (typeMirror instanceof TypeVariable) {
            StringBuilder sb = new StringBuilder("<");
            TypeVariable typeVariable = (TypeVariable) typeMirror;
            sb.append(typeVariable.toString());
            TypeMirror lowerBound = typeVariable.getLowerBound();
            if (lowerBound.getKind() != TypeKind.NULL) {
                sb.append(" super ").append(lowerBound);
            }
            TypeMirror upperBound = typeVariable.getUpperBound();
            if (upperBound.getKind() != TypeKind.NULL) {
                sb.append(" extends ").append(upperBound);
            }
            return sb.append(">").toString();
        }
        return typeMirror.toString();
    }

    public static boolean isAssignable(ProcessingEnvironment processingEnvironment, TypeMirror typeMirror, TypeMirror typeMirror2) {
        boolean zIsAssignable = processingEnvironment.getTypeUtils().isAssignable(typeMirror, typeMirror2);
        if (!zIsAssignable && (typeMirror instanceof DeclaredType) && (typeMirror2 instanceof DeclaredType)) {
            return processingEnvironment.getTypeUtils().isAssignable(toRawType(processingEnvironment, (DeclaredType) typeMirror), toRawType(processingEnvironment, (DeclaredType) typeMirror2));
        }
        return zIsAssignable;
    }

    public static EquivalencyResult isEquivalentType(ProcessingEnvironment processingEnvironment, TypeMirror typeMirror, TypeMirror typeMirror2) {
        if (typeMirror == null || typeMirror2 == null) {
            return EquivalencyResult.notEquivalent("Invalid types supplied: %s, %s", new Object[]{typeMirror, typeMirror2});
        }
        if (processingEnvironment.getTypeUtils().isSameType(typeMirror, typeMirror2)) {
            return EquivalencyResult.EQUIVALENT;
        }
        if ((typeMirror instanceof TypeVariable) && (typeMirror2 instanceof TypeVariable)) {
            typeMirror = getUpperBound(typeMirror);
            typeMirror2 = getUpperBound(typeMirror2);
            if (processingEnvironment.getTypeUtils().isSameType(typeMirror, typeMirror2)) {
                return EquivalencyResult.EQUIVALENT;
            }
        }
        if ((typeMirror instanceof DeclaredType) && (typeMirror2 instanceof DeclaredType)) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            DeclaredType declaredType2 = (DeclaredType) typeMirror2;
            TypeMirror rawType = toRawType(processingEnvironment, declaredType);
            TypeMirror rawType2 = toRawType(processingEnvironment, declaredType2);
            if (!processingEnvironment.getTypeUtils().isSameType(rawType, rawType2)) {
                return EquivalencyResult.notEquivalent("Base types %s and %s are not compatible", new Object[]{rawType, rawType2});
            }
            List typeArguments = declaredType.getTypeArguments();
            List typeArguments2 = declaredType2.getTypeArguments();
            if (typeArguments.size() != typeArguments2.size()) {
                if (typeArguments.size() == 0) {
                    return EquivalencyResult.equivalentButRaw(1);
                }
                if (typeArguments2.size() == 0) {
                    return EquivalencyResult.equivalentButRaw(2);
                }
                return EquivalencyResult.notEquivalent("Mismatched generic argument counts %s<[%d]> and %s<[%d]>", new Object[]{rawType, Integer.valueOf(typeArguments.size()), rawType2, Integer.valueOf(typeArguments2.size())});
            }
            for (int i = 0; i < typeArguments.size(); i++) {
                TypeMirror typeMirror3 = (TypeMirror) typeArguments.get(i);
                TypeMirror typeMirror4 = (TypeMirror) typeArguments2.get(i);
                if (isEquivalentType(processingEnvironment, typeMirror3, typeMirror4).type != Equivalency.EQUIVALENT) {
                    return EquivalencyResult.boundsMismatch("Generic bounds mismatch between %s and %s", new Object[]{describeGenericBound(typeMirror3), describeGenericBound(typeMirror4)});
                }
            }
            return EquivalencyResult.EQUIVALENT;
        }
        return EquivalencyResult.notEquivalent("%s and %s do not match", new Object[]{typeMirror, typeMirror2});
    }

    private static TypeMirror toRawType(ProcessingEnvironment processingEnvironment, DeclaredType declaredType) {
        if (declaredType.getKind() == TypeKind.INTERSECTION) {
            return declaredType;
        }
        TypeElement typeElement = processingEnvironment.getElementUtils().getTypeElement(declaredType.asElement().getQualifiedName());
        return typeElement != null ? typeElement.asType() : declaredType;
    }

    public static Visibility getVisibility(Element element) {
        if (element == null) {
            return null;
        }
        Iterator it = element.getModifiers().iterator();
        while (it.hasNext()) {
            switch (C05791.$SwitchMap$javax$lang$model$element$Modifier[((Modifier) it.next()).ordinal()]) {
                case 1:
                    return Visibility.PUBLIC;
                case 2:
                    return Visibility.PROTECTED;
                case 3:
                    return Visibility.PRIVATE;
            }
        }
        return Visibility.PACKAGE;
    }

    /* renamed from: org.spongepowered.tools.obfuscation.mirror.TypeUtils$1 */
    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/TypeUtils$1.class */
    static /* synthetic */ class C05791 {
        static final int[] $SwitchMap$javax$lang$model$type$TypeKind;
        static final int[] $SwitchMap$javax$lang$model$element$Modifier = new int[Modifier.values().length];

        static {
            try {
                $SwitchMap$javax$lang$model$element$Modifier[Modifier.PUBLIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$javax$lang$model$element$Modifier[Modifier.PROTECTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$javax$lang$model$element$Modifier[Modifier.PRIVATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $SwitchMap$javax$lang$model$type$TypeKind = new int[TypeKind.values().length];
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.DECLARED.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.TYPEVAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.BYTE.ordinal()] = 6;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.CHAR.ordinal()] = 7;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.DOUBLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.FLOAT.ordinal()] = 9;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.INT.ordinal()] = 10;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.LONG.ordinal()] = 11;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.SHORT.ordinal()] = 12;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$javax$lang$model$type$TypeKind[TypeKind.VOID.ordinal()] = 13;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }
}
