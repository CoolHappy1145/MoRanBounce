package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;

/* loaded from: L-out.jar:org/spongepowered/asm/util/SignaturePrinter.class */
public class SignaturePrinter {
    private final String name;
    private final Type returnType;
    private final Type[] argTypes;
    private final String[] argNames;
    private String modifiers;
    private boolean fullyQualified;

    public SignaturePrinter(MethodNode methodNode) {
        this(methodNode.name, Type.VOID_TYPE, Type.getArgumentTypes(methodNode.desc));
        setModifiers(methodNode);
    }

    public SignaturePrinter(MethodNode methodNode, String[] strArr) {
        this(methodNode.name, Type.VOID_TYPE, Type.getArgumentTypes(methodNode.desc), strArr);
        setModifiers(methodNode);
    }

    public SignaturePrinter(ITargetSelectorByName iTargetSelectorByName) {
        this(iTargetSelectorByName.getName(), iTargetSelectorByName.getDesc());
    }

    public SignaturePrinter(String str, String str2) {
        this(str, Type.getReturnType(str2), Type.getArgumentTypes(str2));
    }

    public SignaturePrinter(Type[] typeArr) {
        this((String) null, (Type) null, typeArr);
    }

    public SignaturePrinter(Type type, Type[] typeArr) {
        this((String) null, type, typeArr);
    }

    public SignaturePrinter(String str, Type type, Type[] typeArr) {
        this.modifiers = "private void";
        this.name = str;
        this.returnType = type;
        this.argTypes = new Type[typeArr.length];
        this.argNames = new String[typeArr.length];
        int i = 0;
        for (int i2 = 0; i2 < typeArr.length; i2++) {
            if (typeArr[i2] != null) {
                this.argTypes[i2] = typeArr[i2];
                int i3 = i;
                i++;
                this.argNames[i2] = "var" + i3;
            }
        }
    }

    public SignaturePrinter(String str, Type type, LocalVariableNode[] localVariableNodeArr) {
        this.modifiers = "private void";
        this.name = str;
        this.returnType = type;
        this.argTypes = new Type[localVariableNodeArr.length];
        this.argNames = new String[localVariableNodeArr.length];
        for (int i = 0; i < localVariableNodeArr.length; i++) {
            if (localVariableNodeArr[i] != null) {
                this.argTypes[i] = Type.getType(localVariableNodeArr[i].desc);
                this.argNames[i] = localVariableNodeArr[i].name;
            }
        }
    }

    public SignaturePrinter(String str, Type type, Type[] typeArr, String[] strArr) {
        this.modifiers = "private void";
        this.name = str;
        this.returnType = type;
        this.argTypes = typeArr;
        this.argNames = strArr;
        if (this.argTypes.length > this.argNames.length) {
            throw new IllegalArgumentException(String.format("Types array length must not exceed names array length! (names=%d, types=%d)", Integer.valueOf(this.argNames.length), Integer.valueOf(this.argTypes.length)));
        }
    }

    public String getFormattedArgs() {
        return appendArgs(new StringBuilder(), true, true).toString();
    }

    public String getReturnType() {
        return getTypeName(this.returnType, false, this.fullyQualified);
    }

    public void setModifiers(MethodNode methodNode) {
        String typeName = getTypeName(Type.getReturnType(methodNode.desc), false, this.fullyQualified);
        if ((methodNode.access & 1) != 0) {
            setModifiers("public " + typeName);
            return;
        }
        if ((methodNode.access & 4) != 0) {
            setModifiers("protected " + typeName);
        } else if ((methodNode.access & 2) != 0) {
            setModifiers("private " + typeName);
        } else {
            setModifiers(typeName);
        }
    }

    public SignaturePrinter setModifiers(String str) {
        this.modifiers = str.replace("${returnType}", getReturnType());
        return this;
    }

    public SignaturePrinter setFullyQualified(boolean z) {
        this.fullyQualified = z;
        return this;
    }

    public boolean isFullyQualified() {
        return this.fullyQualified;
    }

    public String toString() {
        return appendArgs(new StringBuilder().append(this.modifiers).append(" ").append(this.name != null ? this.name : "method"), false, true).toString();
    }

    public String toDescriptor() {
        return appendArgs(new StringBuilder(), true, false).append(getTypeName(this.returnType, false, this.fullyQualified)).toString();
    }

    private StringBuilder appendArgs(StringBuilder sb, boolean z, boolean z2) {
        String str;
        sb.append('(');
        for (int i = 0; i < this.argTypes.length; i++) {
            if (this.argTypes[i] != null) {
                if (i > 0) {
                    sb.append(',');
                    if (z2) {
                        sb.append(' ');
                    }
                }
                if (z) {
                    str = null;
                } else {
                    try {
                        str = Strings.isNullOrEmpty(this.argNames[i]) ? "unnamed" + i : this.argNames[i];
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                appendType(sb, this.argTypes[i], str);
            }
        }
        return sb.append(")");
    }

    private StringBuilder appendType(StringBuilder sb, Type type, String str) {
        switch (type.getSort()) {
            case 9:
                return appendArraySuffix(appendType(sb, type.getElementType(), str), type);
            case 10:
                return appendType(sb, type.getClassName(), str);
            default:
                sb.append(getTypeName(type, false, this.fullyQualified));
                if (str != null) {
                    sb.append(' ').append(str);
                }
                return sb;
        }
    }

    private StringBuilder appendType(StringBuilder sb, String str, String str2) {
        if (!this.fullyQualified) {
            str = str.substring(str.lastIndexOf(46) + 1);
        }
        sb.append(str);
        if (str.endsWith("CallbackInfoReturnable")) {
            sb.append('<').append(getTypeName(this.returnType, true, this.fullyQualified)).append('>');
        }
        if (str2 != null) {
            sb.append(' ').append(str2);
        }
        return sb;
    }

    public static String getTypeName(Type type) {
        return getTypeName(type, false, true);
    }

    public static String getTypeName(Type type, boolean z) {
        return getTypeName(type, z, false);
    }

    public static String getTypeName(Type type, boolean z, boolean z2) {
        if (type == null) {
            return "{null?}";
        }
        switch (type.getSort()) {
            case 0:
                return z ? "Void" : "void";
            case 1:
                return z ? "Boolean" : "boolean";
            case 2:
                return z ? "Character" : "char";
            case 3:
                return z ? "Byte" : "byte";
            case 4:
                return z ? "Short" : "short";
            case 5:
                return z ? "Integer" : "int";
            case 6:
                return z ? "Float" : "float";
            case 7:
                return z ? "Long" : "long";
            case 8:
                return z ? "Double" : "double";
            case 9:
                return getTypeName(type.getElementType(), z, z2) + arraySuffix(type);
            case 10:
                String className = type.getClassName();
                if (!z2) {
                    className = className.substring(className.lastIndexOf(46) + 1);
                }
                return className;
            default:
                return "Object";
        }
    }

    private static String arraySuffix(Type type) {
        return Strings.repeat("[]", type.getDimensions());
    }

    private static StringBuilder appendArraySuffix(StringBuilder sb, Type type) {
        for (int i = 0; i < type.getDimensions(); i++) {
            sb.append("[]");
        }
        return sb;
    }
}
