package org.spongepowered.asm.mixin.gen;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.gen.throwables.InvalidAccessorException;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.struct.SpecialMethodInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.asm.ElementNode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/AccessorInfo.class */
public class AccessorInfo extends SpecialMethodInfo {
    protected final Class annotationClass;
    protected final Type[] argTypes;
    protected final Type returnType;
    protected final boolean isStatic;
    protected final String specifiedName;
    protected final AccessorType type;
    private final Type targetFieldType;
    protected final ITargetSelector target;
    protected FieldNode targetField;
    protected MethodNode targetMethod;
    protected AccessorGenerator generator;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/AccessorInfo$AccessorType.class */
    public enum AccessorType {
        FIELD_GETTER(ImmutableSet.of(PropertyDescriptor.GET, "is")) { // from class: org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType.1
            @Override // org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType
            AccessorGenerator getGenerator(AccessorInfo accessorInfo) {
                return new AccessorGeneratorFieldGetter(accessorInfo);
            }
        },
        FIELD_SETTER(ImmutableSet.of(PropertyDescriptor.SET)) { // from class: org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType.2
            @Override // org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType
            AccessorGenerator getGenerator(AccessorInfo accessorInfo) {
                return new AccessorGeneratorFieldSetter(accessorInfo);
            }
        },
        METHOD_PROXY(ImmutableSet.of("call", "invoke")) { // from class: org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType.3
            @Override // org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType
            AccessorGenerator getGenerator(AccessorInfo accessorInfo) {
                return new AccessorGeneratorMethodProxy(accessorInfo);
            }
        },
        OBJECT_FACTORY(ImmutableSet.of("new", "create")) { // from class: org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType.4
            @Override // org.spongepowered.asm.mixin.gen.AccessorInfo.AccessorType
            AccessorGenerator getGenerator(AccessorInfo accessorInfo) {
                return new AccessorGeneratorObjectFactory(accessorInfo);
            }
        };

        private final Set expectedPrefixes;

        abstract AccessorGenerator getGenerator(AccessorInfo accessorInfo);

        AccessorType(Set set, C05581 c05581) {
            this(set);
        }

        AccessorType(Set set) {
            this.expectedPrefixes = set;
        }

        public boolean isExpectedPrefix(String str) {
            return this.expectedPrefixes.contains(str);
        }

        public Set getExpectedPrefixes() {
            return Collections.unmodifiableSet(this.expectedPrefixes);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/AccessorInfo$AccessorName.class */
    public static final class AccessorName {
        private static final Pattern PATTERN = Pattern.compile("^(" + getPrefixList() + ")(([A-Z])(.*?))(_\\$md.*)?$");
        public final String methodName;
        public final String prefix;
        public final String name;

        private AccessorName(String str, String str2, String str3) {
            this.methodName = str;
            this.prefix = str2;
            this.name = str3;
        }

        public String toString() {
            return super.toString();
        }

        /* renamed from: of */
        public static AccessorName m54of(String str) {
            return m55of(str, true);
        }

        /* renamed from: of */
        public static AccessorName m55of(String str, boolean z) {
            Matcher matcher = PATTERN.matcher(str);
            if (matcher.matches()) {
                String strGroup = matcher.group(1);
                String strGroup2 = matcher.group(2);
                String strGroup3 = matcher.group(3);
                String strGroup4 = matcher.group(4);
                Object[] objArr = new Object[2];
                objArr[0] = toLowerCaseIf(Locale.ROOT, strGroup3, z && !isUpperCase(Locale.ROOT, strGroup2));
                objArr[1] = strGroup4;
                return new AccessorName(str, strGroup, String.format("%s%s", objArr));
            }
            return null;
        }

        private static boolean isUpperCase(Locale locale, String str) {
            return str.toUpperCase(locale).equals(str);
        }

        private static String toLowerCaseIf(Locale locale, String str, boolean z) {
            return z ? str.toLowerCase(locale) : str;
        }

        private static String getPrefixList() {
            ArrayList arrayList = new ArrayList();
            for (AccessorType accessorType : AccessorType.values()) {
                arrayList.addAll(accessorType.getExpectedPrefixes());
            }
            return Joiner.on('|').join(arrayList);
        }
    }

    public AccessorInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode) {
        this(mixinTargetContext, methodNode, Accessor.class);
    }

    protected AccessorInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, Class cls) {
        super(mixinTargetContext, methodNode, Annotations.getVisible(methodNode, cls));
        this.annotationClass = cls;
        this.argTypes = Type.getArgumentTypes(methodNode.desc);
        this.returnType = Type.getReturnType(methodNode.desc);
        this.isStatic = Bytecode.isStatic(methodNode);
        this.specifiedName = (String) Annotations.getValue(this.annotation);
        this.type = initType();
        this.targetFieldType = initTargetFieldType();
        this.target = initTarget();
    }

    protected AccessorType initType() {
        if (this.returnType.equals(Type.VOID_TYPE)) {
            return AccessorType.FIELD_SETTER;
        }
        return AccessorType.FIELD_GETTER;
    }

    /* renamed from: org.spongepowered.asm.mixin.gen.AccessorInfo$1 */
    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/AccessorInfo$1.class */
    static /* synthetic */ class C05581 {

        /* renamed from: $SwitchMap$org$spongepowered$asm$mixin$gen$AccessorInfo$AccessorType */
        static final int[] f213xd539c0f6 = new int[AccessorType.values().length];

        static {
            try {
                f213xd539c0f6[AccessorType.FIELD_GETTER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f213xd539c0f6[AccessorType.FIELD_SETTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    protected Type initTargetFieldType() {
        switch (C05581.f213xd539c0f6[this.type.ordinal()]) {
            case 1:
                if (this.argTypes.length > 0) {
                    throw new InvalidAccessorException(this.mixin, this + " must take exactly 0 arguments, found " + this.argTypes.length);
                }
                return this.returnType;
            case 2:
                if (this.argTypes.length != 1) {
                    throw new InvalidAccessorException(this.mixin, this + " must take exactly 1 argument, found " + this.argTypes.length);
                }
                return this.argTypes[0];
            default:
                throw new InvalidAccessorException(this.mixin, "Computed unsupported accessor type " + this.type + " for " + this);
        }
    }

    protected ITargetSelector initTarget() {
        MemberInfo memberInfo = new MemberInfo(getTargetName(this.specifiedName), (String) null, this.targetFieldType.getDescriptor());
        this.annotation.visit("target", memberInfo.toString());
        return memberInfo;
    }

    protected String getTargetName(String str) {
        if (Strings.isNullOrEmpty(str)) {
            String strInflectTarget = inflectTarget();
            if (strInflectTarget == null) {
                throw new InvalidAccessorException(this.mixin, String.format("Failed to inflect target name for %s, supported prefixes: %s", this, this.type.getExpectedPrefixes()));
            }
            return strInflectTarget;
        }
        return TargetSelector.parseName(str, this.mixin);
    }

    protected String inflectTarget() {
        return inflectTarget(this.method.name, this.type, toString(), this.mixin, this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE));
    }

    public static String inflectTarget(String str, AccessorType accessorType, String str2, IMixinContext iMixinContext, boolean z) {
        return inflectTarget(AccessorName.m54of(str), accessorType, str2, iMixinContext, z);
    }

    public static String inflectTarget(AccessorName accessorName, AccessorType accessorType, String str, IMixinContext iMixinContext, boolean z) {
        if (accessorName != null) {
            if (!accessorType.isExpectedPrefix(accessorName.prefix) && z) {
                LogManager.getLogger(MixinLaunchPlugin.NAME).warn("Unexpected prefix for {}, found [{}] expecting {}", new Object[]{str, accessorName.prefix, accessorType.getExpectedPrefixes()});
            }
            return TargetSelector.parseName(accessorName.name, iMixinContext);
        }
        return null;
    }

    public final ITargetSelector getTarget() {
        return this.target;
    }

    public final Type getTargetFieldType() {
        return this.targetFieldType;
    }

    public final FieldNode getTargetField() {
        return this.targetField;
    }

    public final MethodNode getTargetMethod() {
        return this.targetMethod;
    }

    public final Type getReturnType() {
        return this.returnType;
    }

    public final Type[] getArgTypes() {
        return this.argTypes;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public String toString() {
        return String.format("%s->@%s[%s]::%s%s", this.mixin, Bytecode.getSimpleName(this.annotation), this.type != null ? this.type.toString() : "UNPARSED_ACCESSOR", this.methodName, this.method.desc);
    }

    public void locate() {
        this.targetField = findTargetField();
    }

    public void validate() {
        this.generator = this.type.getGenerator(this);
        AccessorGenerator accessorGenerator = this.generator;
    }

    public MethodNode generate() {
        MethodNode methodNodeGenerate = this.generator.generate();
        Annotations.merge(this.method, methodNodeGenerate);
        return methodNodeGenerate;
    }

    private FieldNode findTargetField() {
        return (FieldNode) findTarget(ElementNode.fieldList(this.classNode));
    }

    protected Object findTarget(List list) {
        try {
            return TargetSelector.run(this.target.configure(new String[]{"orphan"}), list).getSingleResult(true);
        } catch (IllegalStateException e) {
            throw new InvalidAccessorException(this, e.getMessage() + " matching " + this.target + " in " + this.classNode.name + " for " + this);
        }
    }

    /* renamed from: of */
    public static AccessorInfo m53of(MixinTargetContext mixinTargetContext, MethodNode methodNode, Class cls) {
        if (cls == Accessor.class) {
            return new AccessorInfo(mixinTargetContext, methodNode);
        }
        if (cls == Invoker.class) {
            return new InvokerInfo(mixinTargetContext, methodNode);
        }
        throw new InvalidAccessorException(mixinTargetContext, "Could not parse accessor for unknown type " + cls.getName());
    }
}
