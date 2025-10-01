package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import jdk.internal.dynalink.CallSiteDescriptor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.slf4j.Marker;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorConstructor;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
import org.spongepowered.asm.mixin.injection.selectors.MatchResult;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.SignaturePrinter;
import org.spongepowered.asm.util.asm.ElementNode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/MemberInfo.class */
public final class MemberInfo implements ITargetSelectorRemappable, ITargetSelectorConstructor {
    private final String owner;
    private final String name;
    private final String desc;
    private final boolean matchAll;
    private final boolean forceField;
    private final String unparsed;

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
    public ITargetSelector validate() {
        return validate();
    }

    public MemberInfo(String str, boolean z) {
        this(str, null, null, z);
    }

    public MemberInfo(String str, String str2, boolean z) {
        this(str, str2, null, z);
    }

    public MemberInfo(String str, String str2, String str3) {
        this(str, str2, str3, false);
    }

    public MemberInfo(String str, String str2, String str3, boolean z) {
        this(str, str2, str3, z, null);
    }

    public MemberInfo(String str, String str2, String str3, boolean z, String str4) {
        if (str2 != null && str2.contains(".")) {
            throw new IllegalArgumentException("Attempt to instance a MemberInfo with an invalid owner format");
        }
        this.owner = str2;
        this.name = str;
        this.desc = str3;
        this.matchAll = z;
        this.forceField = false;
        this.unparsed = str4;
    }

    public MemberInfo(AbstractInsnNode abstractInsnNode) {
        this.matchAll = false;
        this.forceField = false;
        this.unparsed = null;
        if (abstractInsnNode instanceof MethodInsnNode) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            this.owner = methodInsnNode.owner;
            this.name = methodInsnNode.name;
            this.desc = methodInsnNode.desc;
            return;
        }
        if (abstractInsnNode instanceof FieldInsnNode) {
            FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
            this.owner = fieldInsnNode.owner;
            this.name = fieldInsnNode.name;
            this.desc = fieldInsnNode.desc;
            return;
        }
        throw new IllegalArgumentException("insn must be an instance of MethodInsnNode or FieldInsnNode");
    }

    public MemberInfo(IMapping iMapping) {
        this.owner = iMapping.getOwner();
        this.name = iMapping.getSimpleName();
        this.desc = iMapping.getDesc();
        this.matchAll = false;
        this.forceField = iMapping.getType() == IMapping.Type.FIELD;
        this.unparsed = null;
    }

    private MemberInfo(MemberInfo memberInfo, MappingMethod mappingMethod, boolean z) {
        this.owner = z ? mappingMethod.getOwner() : memberInfo.owner;
        this.name = mappingMethod.getSimpleName();
        this.desc = mappingMethod.getDesc();
        this.matchAll = memberInfo.matchAll;
        this.forceField = false;
        this.unparsed = null;
    }

    private MemberInfo(MemberInfo memberInfo, String str) {
        this.owner = str;
        this.name = memberInfo.name;
        this.desc = memberInfo.desc;
        this.matchAll = memberInfo.matchAll;
        this.forceField = memberInfo.forceField;
        this.unparsed = null;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName
    public String getOwner() {
        return this.owner;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName
    public String getName() {
        return this.name;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName
    public String getDesc() {
        return this.desc;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
    public int getMatchCount() {
        return this.matchAll ? Integer.MAX_VALUE : 1;
    }

    public String toString() {
        String str = this.owner != null ? "L" + this.owner + ";" : "";
        String str2 = this.name != null ? this.name : "";
        String str3 = this.matchAll ? Marker.ANY_MARKER : "";
        String str4 = this.desc != null ? this.desc : "";
        String str5 = (str4.startsWith("(") || this.desc == null) ? "" : CallSiteDescriptor.TOKEN_DELIMITER;
        return str + str2 + str3 + str5 + str4;
    }

    @Deprecated
    public String toSrg() {
        if (!isFullyQualified()) {
            throw new MixinException("Cannot convert unqualified reference to SRG mapping");
        }
        if (this.desc.startsWith("(")) {
            return this.owner + "/" + this.name + " " + this.desc;
        }
        return this.owner + "/" + this.name;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName
    public String toDescriptor() {
        if (this.desc == null) {
            return "";
        }
        return new SignaturePrinter(this).setFullyQualified(true).toDescriptor();
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorConstructor
    public String toCtorType() {
        if (this.unparsed == null) {
            return null;
        }
        String returnType = getReturnType();
        if (returnType != null) {
            return returnType;
        }
        if (this.owner != null) {
            return this.owner;
        }
        if (this.name == null || this.desc != null) {
            return this.desc != null ? this.desc : this.unparsed;
        }
        return this.name;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorConstructor
    public String toCtorDesc() {
        return Bytecode.changeDescriptorReturnType(this.desc, "V");
    }

    private String getReturnType() {
        if (this.desc == null || this.desc.indexOf(41) == -1 || this.desc.indexOf(40) != 0) {
            return null;
        }
        String strSubstring = this.desc.substring(this.desc.indexOf(41) + 1);
        if (strSubstring.startsWith("L") && strSubstring.endsWith(";")) {
            return strSubstring.substring(1, strSubstring.length() - 1);
        }
        return strSubstring;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable
    public IMapping asMapping() {
        return isField() ? asFieldMapping() : asMethodMapping();
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable
    public MappingMethod asMethodMapping() {
        if (!isFullyQualified()) {
            throw new MixinException("Cannot convert unqualified reference " + this + " to MethodMapping");
        }
        if (isField()) {
            throw new MixinException("Cannot convert a non-method reference " + this + " to MethodMapping");
        }
        return new MappingMethod(this.owner, this.name, this.desc);
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable
    public MappingField asFieldMapping() {
        if (!isField()) {
            throw new MixinException("Cannot convert non-field reference " + this + " to FieldMapping");
        }
        return new MappingField(this.owner, this.name, this.desc);
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName
    public boolean isFullyQualified() {
        return (this.owner == null || this.name == null || this.desc == null) ? false : true;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName
    public boolean isField() {
        return this.forceField || !(this.desc == null || this.desc.startsWith("("));
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName
    public boolean isConstructor() {
        return Constants.CTOR.equals(this.name);
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName
    public boolean isClassInitialiser() {
        return Constants.CLINIT.equals(this.name);
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName
    public boolean isInitialiser() {
        return isConstructor() || isClassInitialiser();
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
    public MemberInfo validate() {
        if (this.owner != null) {
            if (!this.owner.matches("(?i)^[\\w\\p{Sc}/]+$")) {
                throw new InvalidMemberDescriptorException("Invalid owner: " + this.owner);
            }
            if (this.unparsed != null && this.unparsed.lastIndexOf(46) > 0 && this.owner.startsWith("L")) {
                throw new InvalidMemberDescriptorException("Malformed owner: " + this.owner + " If you are seeing this message unexpectedly and the owner appears to be correct, replace the owner descriptor with formal type L" + this.owner + "; to suppress this error");
            }
        }
        if (this.name != null && !this.name.matches("(?i)^<?[\\w\\p{Sc}]+>?$")) {
            throw new InvalidMemberDescriptorException("Invalid name: " + this.name);
        }
        if (this.desc != null) {
            if (!this.desc.matches("^(\\([\\w\\p{Sc}\\[/;]*\\))?\\[*[\\w\\p{Sc}/;]+$")) {
                throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
            }
            if (isField()) {
                if (!this.desc.equals(Type.getType(this.desc).getDescriptor())) {
                    throw new InvalidMemberDescriptorException("Invalid field type in descriptor: " + this.desc);
                }
            } else {
                try {
                    Type.getArgumentTypes(this.desc);
                    String strSubstring = this.desc.substring(this.desc.indexOf(41) + 1);
                    try {
                        if (!strSubstring.equals(Type.getType(strSubstring).getDescriptor())) {
                            throw new InvalidMemberDescriptorException("Invalid return type \"" + strSubstring + "\" in descriptor: " + this.desc);
                        }
                    } catch (Exception unused) {
                        throw new InvalidMemberDescriptorException("Invalid return type \"" + strSubstring + "\" in descriptor: " + this.desc);
                    }
                } catch (Exception unused2) {
                    throw new InvalidMemberDescriptorException("Invalid descriptor: " + this.desc);
                }
            }
        }
        return this;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
    public MatchResult match(ElementNode elementNode) {
        return elementNode == null ? MatchResult.NONE : matches(elementNode.getOwnerName(), elementNode.getName(), elementNode.getDesc());
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
    public MatchResult match(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode instanceof MethodInsnNode) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            return matches(methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
        }
        if (abstractInsnNode instanceof FieldInsnNode) {
            FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
            return matches(fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
        }
        return MatchResult.NONE;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName
    public MatchResult matches(String str, String str2, String str3) {
        if (this.desc != null && str3 != null && !this.desc.equals(str3)) {
            return MatchResult.NONE;
        }
        if (this.owner != null && str != null && !this.owner.equals(str)) {
            return MatchResult.NONE;
        }
        if (this.name != null && str2 != null) {
            if (this.name.equals(str2)) {
                return MatchResult.EXACT_MATCH;
            }
            if (this.name.equalsIgnoreCase(str2)) {
                return MatchResult.MATCH;
            }
            return MatchResult.NONE;
        }
        return MatchResult.EXACT_MATCH;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ITargetSelectorByName)) {
            return false;
        }
        ITargetSelectorByName iTargetSelectorByName = (ITargetSelectorByName) obj;
        return this.matchAll == (iTargetSelectorByName.getMatchCount() == Integer.MAX_VALUE) && this.forceField == (iTargetSelectorByName instanceof MemberInfo ? ((MemberInfo) iTargetSelectorByName).forceField : iTargetSelectorByName.isField()) && Objects.equal(this.owner, iTargetSelectorByName.getOwner()) && Objects.equal(this.name, iTargetSelectorByName.getName()) && Objects.equal(this.desc, iTargetSelectorByName.getDesc());
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{Boolean.valueOf(this.matchAll), this.owner, this.name, this.desc});
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
    public ITargetSelector configure(String[] strArr) {
        MemberInfo memberInfoMove = this;
        for (String str : strArr) {
            if (str != null) {
                if (str.startsWith("move:")) {
                    memberInfoMove = memberInfoMove.move(Strings.emptyToNull(str.substring(5)));
                } else if (str.startsWith("transform:")) {
                    memberInfoMove = memberInfoMove.transform(Strings.emptyToNull(str.substring(10)));
                } else if ("permissive".equals(str)) {
                    memberInfoMove = memberInfoMove.transform(null);
                } else if ("orphan".equals(str)) {
                    memberInfoMove = memberInfoMove.move(null);
                }
            }
        }
        return memberInfoMove;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelector
    public ITargetSelector attach(IMixinContext iMixinContext) {
        if (this.owner != null && !this.owner.equals(iMixinContext.getTargetClassRef())) {
            throw new TargetNotSupportedException(this.owner);
        }
        return this;
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable
    public ITargetSelectorRemappable move(String str) {
        if ((str == null && this.owner == null) || (str != null && str.equals(this.owner))) {
            return this;
        }
        return new MemberInfo(this, str);
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable
    public ITargetSelectorRemappable transform(String str) {
        if ((str == null && this.desc == null) || (str != null && str.equals(this.desc))) {
            return this;
        }
        return new MemberInfo(this.name, this.owner, str, this.matchAll);
    }

    @Override // org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable
    public ITargetSelectorRemappable remapUsing(MappingMethod mappingMethod, boolean z) {
        return new MemberInfo(this, mappingMethod, z);
    }

    public static MemberInfo parse(String str, IReferenceMapper iReferenceMapper, String str2) {
        String strSubstring = null;
        String strReplace = null;
        String strReplaceAll = Strings.nullToEmpty(str).replaceAll("\\s", "");
        if (iReferenceMapper != null) {
            strReplaceAll = iReferenceMapper.remap(str2, strReplaceAll);
        }
        int iLastIndexOf = strReplaceAll.lastIndexOf(46);
        int iIndexOf = strReplaceAll.indexOf(59);
        if (iLastIndexOf > -1) {
            strReplace = strReplaceAll.substring(0, iLastIndexOf).replace('.', '/');
            strReplaceAll = strReplaceAll.substring(iLastIndexOf + 1);
        } else if (iIndexOf > -1 && strReplaceAll.startsWith("L")) {
            strReplace = strReplaceAll.substring(1, iIndexOf).replace('.', '/');
            strReplaceAll = strReplaceAll.substring(iIndexOf + 1);
        }
        int iIndexOf2 = strReplaceAll.indexOf(40);
        int iIndexOf3 = strReplaceAll.indexOf(58);
        if (iIndexOf2 > -1) {
            strSubstring = strReplaceAll.substring(iIndexOf2);
            strReplaceAll = strReplaceAll.substring(0, iIndexOf2);
        } else if (iIndexOf3 > -1) {
            strSubstring = strReplaceAll.substring(iIndexOf3 + 1);
            strReplaceAll = strReplaceAll.substring(0, iIndexOf3);
        }
        if ((strReplaceAll.indexOf(47) > -1 || strReplaceAll.indexOf(46) > -1) && strReplace == null) {
            strReplace = strReplaceAll;
            strReplaceAll = "";
        }
        boolean zEndsWith = strReplaceAll.endsWith(Marker.ANY_MARKER);
        if (zEndsWith) {
            strReplaceAll = strReplaceAll.substring(0, strReplaceAll.length() - 1);
        }
        if (strReplaceAll.isEmpty()) {
            strReplaceAll = null;
        }
        return new MemberInfo(strReplaceAll, strReplace, strSubstring, zEndsWith, str);
    }

    public static MemberInfo fromMapping(IMapping iMapping) {
        return new MemberInfo(iMapping);
    }
}
