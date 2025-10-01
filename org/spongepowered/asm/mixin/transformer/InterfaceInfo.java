package org.spongepowered.asm.mixin.transformer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.Annotations;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/InterfaceInfo.class */
public final class InterfaceInfo {
    private final MixinInfo mixin;
    private final String prefix;
    private final Type iface;
    private final boolean unique;
    private Set methods;

    private InterfaceInfo(MixinInfo mixinInfo, String str, Type type, boolean z) {
        if (str == null || str.length() < 2 || !str.endsWith(ArgsClassGenerator.GETTER_PREFIX)) {
            throw new InvalidMixinException(mixinInfo, String.format("Prefix %s for iface %s is not valid", str, type.toString()));
        }
        this.mixin = mixinInfo;
        this.prefix = str;
        this.iface = type;
        this.unique = z;
    }

    private void initMethods() {
        this.methods = new HashSet();
        readInterface(this.iface.getInternalName());
    }

    private void readInterface(String str) {
        ClassInfo classInfoForName = ClassInfo.forName(str);
        Iterator it = classInfoForName.getMethods().iterator();
        while (it.hasNext()) {
            this.methods.add(((ClassInfo.Method) it.next()).toString());
        }
        Iterator it2 = classInfoForName.getInterfaces().iterator();
        while (it2.hasNext()) {
            readInterface((String) it2.next());
        }
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Type getIface() {
        return this.iface;
    }

    public String getName() {
        return this.iface.getClassName();
    }

    public String getInternalName() {
        return this.iface.getInternalName();
    }

    public boolean isUnique() {
        return this.unique;
    }

    public boolean renameMethod(MethodNode methodNode) {
        if (this.methods == null) {
            initMethods();
        }
        if (!methodNode.name.startsWith(this.prefix)) {
            if (this.methods.contains(methodNode.name + methodNode.desc)) {
                decorateUniqueMethod(methodNode);
                return false;
            }
            return false;
        }
        String strSubstring = methodNode.name.substring(this.prefix.length());
        if (!this.methods.contains(strSubstring + methodNode.desc)) {
            throw new InvalidMixinException(this.mixin, String.format("%s does not exist in target interface %s", strSubstring, getName()));
        }
        if ((methodNode.access & 1) == 0) {
            throw new InvalidMixinException(this.mixin, String.format("%s cannot implement %s because it is not visible", strSubstring, getName()));
        }
        Annotations.setVisible(methodNode, MixinRenamed.class, new Object[]{"originalName", methodNode.name, "isInterfaceMember", true});
        decorateUniqueMethod(methodNode);
        methodNode.name = strSubstring;
        return true;
    }

    private void decorateUniqueMethod(MethodNode methodNode) {
        if (this.unique && Annotations.getVisible(methodNode, Unique.class) == null) {
            Annotations.setVisible(methodNode, Unique.class, new Object[0]);
            this.mixin.getClassInfo().findMethod(methodNode).setUnique(true);
        }
    }

    static InterfaceInfo fromAnnotation(MixinInfo mixinInfo, AnnotationNode annotationNode) {
        String str = (String) Annotations.getValue(annotationNode, "prefix");
        Type type = (Type) Annotations.getValue(annotationNode, "iface");
        Boolean bool = (Boolean) Annotations.getValue(annotationNode, "unique");
        if (str == null || type == null) {
            throw new InvalidMixinException(mixinInfo, String.format("@Interface annotation on %s is missing a required parameter", mixinInfo));
        }
        return new InterfaceInfo(mixinInfo, str, type, bool != null && bool.booleanValue());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InterfaceInfo interfaceInfo = (InterfaceInfo) obj;
        return this.mixin.equals(interfaceInfo.mixin) && this.prefix.equals(interfaceInfo.prefix) && this.iface.equals(interfaceInfo.iface);
    }

    public int hashCode() {
        return (31 * ((31 * this.mixin.hashCode()) + this.prefix.hashCode())) + this.iface.hashCode();
    }
}
