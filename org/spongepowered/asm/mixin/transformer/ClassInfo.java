package org.spongepowered.asm.mixin.transformer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.ClassSignature;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassInfo.class */
public final class ClassInfo {
    public static final int INCLUDE_PRIVATE = 2;
    public static final int INCLUDE_STATIC = 8;
    public static final int INCLUDE_ALL = 10;
    public static final int INCLUDE_INITIALISERS = 262144;
    private static final String JAVA_LANG_OBJECT = "java/lang/Object";
    private final String name;
    private final String superName;
    private final String outerName;
    private final boolean isProbablyStatic;
    private final Set interfaces;
    private final Set initialisers;
    private final Set methods;
    private final Set fields;
    private final Set mixins;
    private final Map correspondingTypes;
    private final MixinInfo mixin;
    private final MethodMapper methodMapper;
    private final boolean isMixin;
    private final boolean isInterface;
    private final int access;
    private ClassInfo superClass;
    private ClassInfo outerClass;
    private ClassSignature signature;
    private Set appliedMixins;
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private static final Profiler profiler = MixinEnvironment.getProfiler();
    private static final Map cache = new HashMap();
    private static final ClassInfo OBJECT = new ClassInfo();

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$SearchType.class */
    public enum SearchType {
        ALL_CLASSES,
        SUPER_CLASSES_ONLY
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$TypeLookup.class */
    public enum TypeLookup {
        DECLARED_TYPE,
        ELEMENT_TYPE
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$Traversal.class */
    public enum Traversal {
        NONE(null, false, SearchType.SUPER_CLASSES_ONLY),
        ALL(null, true, SearchType.ALL_CLASSES),
        IMMEDIATE(NONE, true, SearchType.SUPER_CLASSES_ONLY),
        SUPER(ALL, false, SearchType.SUPER_CLASSES_ONLY);

        private final Traversal next;
        private final boolean traverse;
        private final SearchType searchType;

        Traversal(Traversal traversal, boolean z, SearchType searchType) {
            this.next = traversal != null ? traversal : this;
            this.traverse = z;
            this.searchType = searchType;
        }

        public Traversal next() {
            return this.next;
        }

        public boolean canTraverse() {
            return this.traverse;
        }

        public SearchType getSearchType() {
            return this.searchType;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$FrameData.class */
    public static class FrameData {
        private static final String[] FRAMETYPES = {"NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1"};
        public final int index;
        public final int type;
        public final int locals;
        public final int size;

        FrameData(int i, int i2, int i3, int i4) {
            this.index = i;
            this.type = i2;
            this.locals = i3;
            this.size = i4;
        }

        FrameData(int i, FrameNode frameNode) {
            this.index = i;
            this.type = frameNode.type;
            this.locals = frameNode.local != null ? frameNode.local.size() : 0;
            this.size = Locals.computeFrameSize(frameNode);
        }

        public String toString() {
            return String.format("FrameData[index=%d, type=%s, locals=%d]", Integer.valueOf(this.index), FRAMETYPES[this.type + 1], Integer.valueOf(this.locals));
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$Member.class */
    static abstract class Member {
        private final Type type;
        private final String memberName;
        private final String memberDesc;
        private final boolean isInjected;
        private final int modifiers;
        private String currentName;
        private String currentDesc;
        private boolean decoratedFinal;
        private boolean decoratedMutable;
        private boolean unique;

        /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$Member$Type.class */
        enum Type {
            METHOD,
            FIELD
        }

        public abstract ClassInfo getOwner();

        protected Member(Member member) {
            this(member.type, member.memberName, member.memberDesc, member.modifiers, member.isInjected);
            this.currentName = member.currentName;
            this.currentDesc = member.currentDesc;
            this.unique = member.unique;
        }

        protected Member(Type type, String str, String str2, int i) {
            this(type, str, str2, i, false);
        }

        protected Member(Type type, String str, String str2, int i, boolean z) {
            this.type = type;
            this.memberName = str;
            this.memberDesc = str2;
            this.isInjected = z;
            this.currentName = str;
            this.currentDesc = str2;
            this.modifiers = i;
        }

        public String getOriginalName() {
            return this.memberName;
        }

        public String getName() {
            return this.currentName;
        }

        public String getOriginalDesc() {
            return this.memberDesc;
        }

        public String getDesc() {
            return this.currentDesc;
        }

        public boolean isInjected() {
            return this.isInjected;
        }

        public boolean isRenamed() {
            return !this.currentName.equals(this.memberName);
        }

        public boolean isRemapped() {
            return !this.currentDesc.equals(this.memberDesc);
        }

        public boolean isPrivate() {
            return (this.modifiers & 2) != 0;
        }

        public boolean isStatic() {
            return (this.modifiers & 8) != 0;
        }

        public boolean isAbstract() {
            return (this.modifiers & 1024) != 0;
        }

        public boolean isFinal() {
            return (this.modifiers & 16) != 0;
        }

        public boolean isSynthetic() {
            return (this.modifiers & 4096) != 0;
        }

        public boolean isUnique() {
            return this.unique;
        }

        public void setUnique(boolean z) {
            this.unique = z;
        }

        public boolean isDecoratedFinal() {
            return this.decoratedFinal;
        }

        public boolean isDecoratedMutable() {
            return this.decoratedMutable;
        }

        protected void setDecoratedFinal(boolean z, boolean z2) {
            this.decoratedFinal = z;
            this.decoratedMutable = z2;
        }

        public boolean matchesFlags(int i) {
            return ((((this.modifiers ^ (-1)) | (i & 2)) & 2) == 0 || (((this.modifiers ^ (-1)) | (i & 8)) & 8) == 0) ? false : true;
        }

        public ClassInfo getImplementor() {
            return getOwner();
        }

        public int getAccess() {
            return this.modifiers;
        }

        public String renameTo(String str) {
            this.currentName = str;
            return str;
        }

        public String remapTo(String str) {
            this.currentDesc = str;
            return str;
        }

        public boolean equals(String str, String str2) {
            return (this.memberName.equals(str) || this.currentName.equals(str)) && (this.memberDesc.equals(str2) || this.currentDesc.equals(str2));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Member)) {
                return false;
            }
            Member member = (Member) obj;
            return (member.memberName.equals(this.memberName) || member.currentName.equals(this.currentName)) && (member.memberDesc.equals(this.memberDesc) || member.currentDesc.equals(this.currentDesc));
        }

        public int hashCode() {
            return toString().hashCode();
        }

        public String toString() {
            return String.format("%s%s", this.memberName, this.memberDesc);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$Method.class */
    public class Method extends Member {
        private final List frames;
        private boolean isAccessor;
        private boolean conformed;
        final ClassInfo this$0;

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String toString() {
            return super.toString();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public int hashCode() {
            return super.hashCode();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean equals(String str, String str2) {
            return super.equals(str, str2);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String remapTo(String str) {
            return super.remapTo(str);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public int getAccess() {
            return super.getAccess();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public ClassInfo getImplementor() {
            return super.getImplementor();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean matchesFlags(int i) {
            return super.matchesFlags(i);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isDecoratedMutable() {
            return super.isDecoratedMutable();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isDecoratedFinal() {
            return super.isDecoratedFinal();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public void setUnique(boolean z) {
            super.setUnique(z);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isUnique() {
            return super.isUnique();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isSynthetic() {
            return super.isSynthetic();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isFinal() {
            return super.isFinal();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isAbstract() {
            return super.isAbstract();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isStatic() {
            return super.isStatic();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isPrivate() {
            return super.isPrivate();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isRemapped() {
            return super.isRemapped();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isRenamed() {
            return super.isRenamed();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isInjected() {
            return super.isInjected();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String getDesc() {
            return super.getDesc();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String getOriginalDesc() {
            return super.getOriginalDesc();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String getName() {
            return super.getName();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String getOriginalName() {
            return super.getOriginalName();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(ClassInfo classInfo, Member member) {
            super(member);
            this.this$0 = classInfo;
            this.frames = member instanceof Method ? ((Method) member).frames : null;
        }

        public Method(ClassInfo classInfo, MethodNode methodNode) {
            this(classInfo, methodNode, false);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(ClassInfo classInfo, MethodNode methodNode, boolean z) {
            super(Member.Type.METHOD, methodNode.name, methodNode.desc, methodNode.access, z);
            this.this$0 = classInfo;
            this.frames = gatherFrames(methodNode);
            setUnique(Annotations.getVisible(methodNode, Unique.class) != null);
            this.isAccessor = Annotations.getSingleVisible(methodNode, new Class[]{Accessor.class, Invoker.class}) != null;
            setDecoratedFinal(Annotations.getVisible(methodNode, Final.class) != null, Annotations.getVisible(methodNode, Mutable.class) != null);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(ClassInfo classInfo, String str, String str2) {
            super(Member.Type.METHOD, str, str2, 1, false);
            this.this$0 = classInfo;
            this.frames = null;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(ClassInfo classInfo, String str, String str2, int i) {
            super(Member.Type.METHOD, str, str2, i, false);
            this.this$0 = classInfo;
            this.frames = null;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Method(ClassInfo classInfo, String str, String str2, int i, boolean z) {
            super(Member.Type.METHOD, str, str2, i, z);
            this.this$0 = classInfo;
            this.frames = null;
        }

        private List gatherFrames(MethodNode methodNode) {
            ArrayList arrayList = new ArrayList();
            ListIterator it = methodNode.instructions.iterator();
            while (it.hasNext()) {
                FrameNode frameNode = (AbstractInsnNode) it.next();
                if (frameNode instanceof FrameNode) {
                    arrayList.add(new FrameData(methodNode.instructions.indexOf(frameNode), frameNode));
                }
            }
            return arrayList;
        }

        public List getFrames() {
            return this.frames;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public ClassInfo getOwner() {
            return this.this$0;
        }

        public boolean isAccessor() {
            return this.isAccessor;
        }

        public boolean isConformed() {
            return this.conformed;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String renameTo(String str) {
            this.conformed = false;
            return super.renameTo(str);
        }

        public String conform(String str) {
            boolean z = !str.equals(getName());
            if (this.conformed && z) {
                throw new IllegalStateException("Method " + this + " was already conformed. Original= " + getOriginalName() + " Current=" + getName() + " New=" + str);
            }
            if (z) {
                renameTo(str);
                this.conformed = true;
            }
            return str;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean equals(Object obj) {
            if (!(obj instanceof Method)) {
                return false;
            }
            return super.equals(obj);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$InterfaceMethod.class */
    public class InterfaceMethod extends Method {
        private final ClassInfo owner;
        final ClassInfo this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InterfaceMethod(ClassInfo classInfo, Member member) {
            super(classInfo, member);
            this.this$0 = classInfo;
            this.owner = member.getOwner();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Method, org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public ClassInfo getOwner() {
            return this.owner;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Method, org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public ClassInfo getImplementor() {
            return this.this$0;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ClassInfo$Field.class */
    public class Field extends Member {
        final ClassInfo this$0;

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String toString() {
            return super.toString();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public int hashCode() {
            return super.hashCode();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean equals(String str, String str2) {
            return super.equals(str, str2);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String remapTo(String str) {
            return super.remapTo(str);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String renameTo(String str) {
            return super.renameTo(str);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public int getAccess() {
            return super.getAccess();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public ClassInfo getImplementor() {
            return super.getImplementor();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean matchesFlags(int i) {
            return super.matchesFlags(i);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isDecoratedMutable() {
            return super.isDecoratedMutable();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isDecoratedFinal() {
            return super.isDecoratedFinal();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public void setUnique(boolean z) {
            super.setUnique(z);
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isUnique() {
            return super.isUnique();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isSynthetic() {
            return super.isSynthetic();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isFinal() {
            return super.isFinal();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isAbstract() {
            return super.isAbstract();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isStatic() {
            return super.isStatic();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isPrivate() {
            return super.isPrivate();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isRemapped() {
            return super.isRemapped();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isRenamed() {
            return super.isRenamed();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean isInjected() {
            return super.isInjected();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String getDesc() {
            return super.getDesc();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String getOriginalDesc() {
            return super.getOriginalDesc();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String getName() {
            return super.getName();
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public String getOriginalName() {
            return super.getOriginalName();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(ClassInfo classInfo, Member member) {
            super(member);
            this.this$0 = classInfo;
        }

        public Field(ClassInfo classInfo, FieldNode fieldNode) {
            this(classInfo, fieldNode, false);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(ClassInfo classInfo, FieldNode fieldNode, boolean z) {
            super(Member.Type.FIELD, fieldNode.name, fieldNode.desc, fieldNode.access, z);
            this.this$0 = classInfo;
            setUnique(Annotations.getVisible(fieldNode, Unique.class) != null);
            if (Annotations.getVisible(fieldNode, Shadow.class) != null) {
                setDecoratedFinal(Annotations.getVisible(fieldNode, Final.class) != null, Annotations.getVisible(fieldNode, Mutable.class) != null);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(ClassInfo classInfo, String str, String str2, int i) {
            super(Member.Type.FIELD, str, str2, i, false);
            this.this$0 = classInfo;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(ClassInfo classInfo, String str, String str2, int i, boolean z) {
            super(Member.Type.FIELD, str, str2, i, z);
            this.this$0 = classInfo;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public ClassInfo getOwner() {
            return this.this$0;
        }

        @Override // org.spongepowered.asm.mixin.transformer.ClassInfo.Member
        public boolean equals(Object obj) {
            if (!(obj instanceof Field)) {
                return false;
            }
            return super.equals(obj);
        }
    }

    static {
        cache.put("java/lang/Object", OBJECT);
    }

    private ClassInfo() {
        this.correspondingTypes = new HashMap();
        this.name = "java/lang/Object";
        this.superName = null;
        this.outerName = null;
        this.isProbablyStatic = true;
        this.initialisers = ImmutableSet.of(new Method(this, Constants.CTOR, "()V"));
        this.methods = ImmutableSet.of(new Method(this, "getClass", "()Ljava/lang/Class;"), new Method(this, "hashCode", "()I"), new Method(this, "equals", "(Ljava/lang/Object;)Z"), new Method(this, "clone", "()Ljava/lang/Object;"), new Method(this, "toString", "()Ljava/lang/String;"), new Method(this, "notify", "()V"), new Method[]{new Method(this, "notifyAll", "()V"), new Method(this, "wait", "(J)V"), new Method(this, "wait", "(JI)V"), new Method(this, "wait", "()V"), new Method(this, "finalize", "()V")});
        this.fields = Collections.emptySet();
        this.isInterface = false;
        this.interfaces = Collections.emptySet();
        this.access = 1;
        this.isMixin = false;
        this.mixin = null;
        this.mixins = Collections.emptySet();
        this.methodMapper = null;
    }

    private ClassInfo(ClassNode classNode) {
        this.correspondingTypes = new HashMap();
        Profiler.Section sectionBegin = profiler.begin(1, "class.meta");
        try {
            this.name = classNode.name;
            this.superName = classNode.superName != null ? classNode.superName : "java/lang/Object";
            this.initialisers = new HashSet();
            this.methods = new HashSet();
            this.fields = new HashSet();
            this.isInterface = (classNode.access & 512) != 0;
            this.interfaces = new HashSet();
            this.access = classNode.access;
            this.isMixin = classNode instanceof MixinInfo.MixinClassNode;
            this.mixin = this.isMixin ? ((MixinInfo.MixinClassNode) classNode).getMixin() : null;
            this.mixins = this.isMixin ? Collections.emptySet() : new HashSet();
            this.interfaces.addAll(classNode.interfaces);
            Iterator it = classNode.methods.iterator();
            while (it.hasNext()) {
                addMethod((MethodNode) it.next(), this.isMixin);
            }
            boolean z = true;
            String strSubstring = classNode.outerClass;
            for (FieldNode fieldNode : classNode.fields) {
                if ((fieldNode.access & 4096) != 0 && fieldNode.name.startsWith("this$")) {
                    z = false;
                    if (strSubstring == null) {
                        strSubstring = fieldNode.desc;
                        if (strSubstring != null && strSubstring.startsWith("L")) {
                            strSubstring = strSubstring.substring(1, strSubstring.length() - 1);
                        }
                    }
                }
                this.fields.add(new Field(this, fieldNode, this.isMixin));
            }
            this.isProbablyStatic = z;
            this.outerName = strSubstring;
            this.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), this);
            this.signature = ClassSignature.ofLazy(classNode);
        } finally {
            sectionBegin.end();
        }
    }

    void addInterface(String str) {
        this.interfaces.add(str);
        getSignature().addInterface(str);
    }

    void addMethod(MethodNode methodNode) {
        addMethod(methodNode, true);
    }

    private void addMethod(MethodNode methodNode, boolean z) {
        if (methodNode.name.startsWith("<")) {
            this.initialisers.add(new Method(this, methodNode, z));
        } else {
            this.methods.add(new Method(this, methodNode, z));
        }
    }

    void addMixin(MixinInfo mixinInfo) {
        if (this.isMixin) {
            throw new IllegalArgumentException("Cannot add target " + this.name + " for " + mixinInfo.getClassName() + " because the target is a mixin");
        }
        this.mixins.add(mixinInfo);
    }

    void addAppliedMixin(MixinInfo mixinInfo) {
        if (this.appliedMixins == null) {
            this.appliedMixins = new HashSet();
        }
        this.appliedMixins.add(mixinInfo);
    }

    Set getMixins() {
        return this.isMixin ? Collections.emptySet() : Collections.unmodifiableSet(this.mixins);
    }

    public Set getAppliedMixins() {
        return this.appliedMixins != null ? Collections.unmodifiableSet(this.appliedMixins) : Collections.emptySet();
    }

    public boolean isMixin() {
        return this.isMixin;
    }

    public boolean isLoadable() {
        return this.mixin != null && this.mixin.isLoadable();
    }

    public boolean isPublic() {
        return (this.access & 1) != 0;
    }

    public boolean isAbstract() {
        return (this.access & 1024) != 0;
    }

    public boolean isSynthetic() {
        return (this.access & 4096) != 0;
    }

    public boolean isProbablyStatic() {
        return this.isProbablyStatic;
    }

    public boolean isInner() {
        return this.outerName != null;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public Set getInterfaces() {
        return Collections.unmodifiableSet(this.interfaces);
    }

    public String toString() {
        return this.name;
    }

    public MethodMapper getMethodMapper() {
        return this.methodMapper;
    }

    public int getAccess() {
        return this.access;
    }

    public String getName() {
        return this.name;
    }

    public String getClassName() {
        return this.name.replace('/', '.');
    }

    public String getSimpleName() {
        int iLastIndexOf = this.name.lastIndexOf(47);
        return iLastIndexOf < 0 ? this.name : this.name.substring(iLastIndexOf + 1);
    }

    public Type getType() {
        return Type.getObjectType(this.name);
    }

    public String getSuperName() {
        return this.superName;
    }

    public ClassInfo getSuperClass() {
        if (this.superClass == null && this.superName != null) {
            this.superClass = forName(this.superName);
        }
        return this.superClass;
    }

    public String getOuterName() {
        return this.outerName;
    }

    public ClassInfo getOuterClass() {
        if (this.outerClass == null && this.outerName != null) {
            this.outerClass = forName(this.outerName);
        }
        return this.outerClass;
    }

    /* JADX WARN: Failed to calculate best type for var: r4v0 ??
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:156)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:133)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:238)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
     */
    /* JADX WARN: Failed to calculate best type for var: r4v0 ??
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:145)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:123)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:101)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:101)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
     */
    /* JADX WARN: Multi-variable type inference failed. Error: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.RegisterArg.getSVar()" because the return value of "jadx.core.dex.nodes.InsnNode.getResult()" is null
    	at jadx.core.dex.visitors.typeinference.AbstractTypeConstraint.collectRelatedVars(AbstractTypeConstraint.java:31)
    	at jadx.core.dex.visitors.typeinference.AbstractTypeConstraint.<init>(AbstractTypeConstraint.java:19)
    	at jadx.core.dex.visitors.typeinference.TypeSearch$1.<init>(TypeSearch.java:376)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.makeMoveConstraint(TypeSearch.java:376)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.makeConstraint(TypeSearch.java:361)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.collectConstraints(TypeSearch.java:341)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:60)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.runMultiVariableSearch(FixTypesVisitor.java:116)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
     */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0004: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r4 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:1302), block:B:2:0x0000 */
    /* JADX WARN: Type inference failed for: r4v0, types: [org.spongepowered.asm.util.ClassSignature] */
    public ClassSignature getSignature() {
        ?? r4;
        ClassSignature classSignature = this.signature;
        return r4;
    }

    List getTargets() {
        if (this.mixin != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this);
            arrayList.addAll(this.mixin.getTargets());
            return arrayList;
        }
        return ImmutableList.of(this);
    }

    public Set getMethods() {
        return Collections.unmodifiableSet(this.methods);
    }

    public Set getInterfaceMethods(boolean z) {
        HashSet hashSet = new HashSet();
        ClassInfo classInfoAddMethodsRecursive = addMethodsRecursive(hashSet, z);
        if (!this.isInterface) {
            while (classInfoAddMethodsRecursive != null && classInfoAddMethodsRecursive != OBJECT) {
                classInfoAddMethodsRecursive = classInfoAddMethodsRecursive.addMethodsRecursive(hashSet, z);
            }
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            if (!((Method) it.next()).isAbstract()) {
                it.remove();
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }

    private ClassInfo addMethodsRecursive(Set set, boolean z) {
        if (this.isInterface) {
            for (Method method : this.methods) {
                if (!method.isAbstract()) {
                    set.remove(method);
                }
                set.add(method);
            }
        } else if (!this.isMixin && z) {
            Iterator it = this.mixins.iterator();
            while (it.hasNext()) {
                ((MixinInfo) it.next()).getClassInfo().addMethodsRecursive(set, z);
            }
        }
        Iterator it2 = this.interfaces.iterator();
        while (it2.hasNext()) {
            forName((String) it2.next()).addMethodsRecursive(set, z);
        }
        return getSuperClass();
    }

    public boolean hasSuperClass(String str) {
        return hasSuperClass(str, Traversal.NONE);
    }

    public boolean hasSuperClass(String str, Traversal traversal) {
        return "java/lang/Object".equals(str) || findSuperClass(str, traversal) != null;
    }

    public boolean hasSuperClass(ClassInfo classInfo) {
        return hasSuperClass(classInfo, Traversal.NONE, false);
    }

    public boolean hasSuperClass(ClassInfo classInfo, Traversal traversal) {
        return hasSuperClass(classInfo, traversal, false);
    }

    public boolean hasSuperClass(ClassInfo classInfo, Traversal traversal, boolean z) {
        return OBJECT == classInfo || findSuperClass(classInfo.name, traversal, z) != null;
    }

    public ClassInfo findSuperClass(String str) {
        return findSuperClass(str, Traversal.NONE);
    }

    public ClassInfo findSuperClass(String str, Traversal traversal) {
        return findSuperClass(str, traversal, false, new HashSet());
    }

    public ClassInfo findSuperClass(String str, Traversal traversal, boolean z) {
        if (OBJECT.name.equals(str)) {
            return null;
        }
        return findSuperClass(str, traversal, z, new HashSet());
    }

    private ClassInfo findSuperClass(String str, Traversal traversal, boolean z, Set set) {
        ClassInfo classInfoFindInterface;
        ClassInfo superClass = getSuperClass();
        if (superClass != null) {
            for (ClassInfo classInfo : superClass.getTargets()) {
                if (str.equals(classInfo.getName())) {
                    return superClass;
                }
                ClassInfo classInfoFindSuperClass = classInfo.findSuperClass(str, traversal.next(), z, set);
                if (classInfoFindSuperClass != null) {
                    return classInfoFindSuperClass;
                }
            }
        }
        if (z && (classInfoFindInterface = findInterface(str)) != null) {
            return classInfoFindInterface;
        }
        if (traversal.canTraverse()) {
            for (MixinInfo mixinInfo : this.mixins) {
                String className = mixinInfo.getClassName();
                if (!set.contains(className)) {
                    set.add(className);
                    ClassInfo classInfo2 = mixinInfo.getClassInfo();
                    if (str.equals(classInfo2.getName())) {
                        return classInfo2;
                    }
                    ClassInfo classInfoFindSuperClass2 = classInfo2.findSuperClass(str, Traversal.ALL, z, set);
                    if (classInfoFindSuperClass2 != null) {
                        return classInfoFindSuperClass2;
                    }
                }
            }
            return null;
        }
        return null;
    }

    private ClassInfo findInterface(String str) {
        for (String str2 : getInterfaces()) {
            ClassInfo classInfoForName = forName(str2);
            if (str.equals(str2)) {
                return classInfoForName;
            }
            ClassInfo classInfoFindInterface = classInfoForName.findInterface(str);
            if (classInfoFindInterface != null) {
                return classInfoFindInterface;
            }
        }
        return null;
    }

    ClassInfo findCorrespondingType(ClassInfo classInfo) {
        if (classInfo == null || !classInfo.isMixin || this.isMixin) {
            return null;
        }
        ClassInfo classInfoFindSuperTypeForMixin = (ClassInfo) this.correspondingTypes.get(classInfo);
        if (classInfoFindSuperTypeForMixin == null) {
            classInfoFindSuperTypeForMixin = findSuperTypeForMixin(classInfo);
            this.correspondingTypes.put(classInfo, classInfoFindSuperTypeForMixin);
        }
        return classInfoFindSuperTypeForMixin;
    }

    private ClassInfo findSuperTypeForMixin(ClassInfo classInfo) {
        ClassInfo superClass = this;
        while (true) {
            ClassInfo classInfo2 = superClass;
            if (classInfo2 != null && classInfo2 != OBJECT) {
                Iterator it = classInfo2.mixins.iterator();
                while (it.hasNext()) {
                    if (((MixinInfo) it.next()).getClassInfo().equals(classInfo)) {
                        return classInfo2;
                    }
                }
                superClass = classInfo2.getSuperClass();
            } else {
                return null;
            }
        }
    }

    public boolean hasMixinInHierarchy() {
        if (!this.isMixin) {
            return false;
        }
        ClassInfo superClass = getSuperClass();
        while (true) {
            ClassInfo classInfo = superClass;
            if (classInfo != null && classInfo != OBJECT) {
                if (classInfo.isMixin) {
                    return true;
                }
                superClass = classInfo.getSuperClass();
            } else {
                return false;
            }
        }
    }

    public boolean hasMixinTargetInHierarchy() {
        if (this.isMixin) {
            return false;
        }
        ClassInfo superClass = getSuperClass();
        while (true) {
            ClassInfo classInfo = superClass;
            if (classInfo != null && classInfo != OBJECT) {
                if (classInfo.mixins.size() > 0) {
                    return true;
                }
                superClass = classInfo.getSuperClass();
            } else {
                return false;
            }
        }
    }

    public Method findMethodInHierarchy(MethodNode methodNode, SearchType searchType) {
        return findMethodInHierarchy(methodNode.name, methodNode.desc, searchType, Traversal.NONE);
    }

    public Method findMethodInHierarchy(MethodNode methodNode, SearchType searchType, Traversal traversal) {
        return findMethodInHierarchy(methodNode.name, methodNode.desc, searchType, traversal, 0);
    }

    public Method findMethodInHierarchy(MethodNode methodNode, SearchType searchType, int i) {
        return findMethodInHierarchy(methodNode.name, methodNode.desc, searchType, Traversal.NONE, i);
    }

    public Method findMethodInHierarchy(MethodNode methodNode, SearchType searchType, Traversal traversal, int i) {
        return findMethodInHierarchy(methodNode.name, methodNode.desc, searchType, traversal, i);
    }

    public Method findMethodInHierarchy(MethodInsnNode methodInsnNode, SearchType searchType) {
        return findMethodInHierarchy(methodInsnNode.name, methodInsnNode.desc, searchType, Traversal.NONE);
    }

    public Method findMethodInHierarchy(MethodInsnNode methodInsnNode, SearchType searchType, int i) {
        return findMethodInHierarchy(methodInsnNode.name, methodInsnNode.desc, searchType, Traversal.NONE, i);
    }

    public Method findMethodInHierarchy(String str, String str2, SearchType searchType) {
        return findMethodInHierarchy(str, str2, searchType, Traversal.NONE);
    }

    public Method findMethodInHierarchy(String str, String str2, SearchType searchType, Traversal traversal) {
        return findMethodInHierarchy(str, str2, searchType, traversal, 0);
    }

    public Method findMethodInHierarchy(String str, String str2, SearchType searchType, Traversal traversal, int i) {
        return (Method) findInHierarchy(str, str2, searchType, traversal, i, Member.Type.METHOD);
    }

    public Field findFieldInHierarchy(FieldNode fieldNode, SearchType searchType) {
        return findFieldInHierarchy(fieldNode.name, fieldNode.desc, searchType, Traversal.NONE);
    }

    public Field findFieldInHierarchy(FieldNode fieldNode, SearchType searchType, int i) {
        return findFieldInHierarchy(fieldNode.name, fieldNode.desc, searchType, Traversal.NONE, i);
    }

    public Field findFieldInHierarchy(FieldInsnNode fieldInsnNode, SearchType searchType) {
        return findFieldInHierarchy(fieldInsnNode.name, fieldInsnNode.desc, searchType, Traversal.NONE);
    }

    public Field findFieldInHierarchy(FieldInsnNode fieldInsnNode, SearchType searchType, int i) {
        return findFieldInHierarchy(fieldInsnNode.name, fieldInsnNode.desc, searchType, Traversal.NONE, i);
    }

    public Field findFieldInHierarchy(String str, String str2, SearchType searchType) {
        return findFieldInHierarchy(str, str2, searchType, Traversal.NONE);
    }

    public Field findFieldInHierarchy(String str, String str2, SearchType searchType, Traversal traversal) {
        return findFieldInHierarchy(str, str2, searchType, traversal, 0);
    }

    public Field findFieldInHierarchy(String str, String str2, SearchType searchType, Traversal traversal, int i) {
        return (Field) findInHierarchy(str, str2, searchType, traversal, i, Member.Type.FIELD);
    }

    private Member findInHierarchy(String str, String str2, SearchType searchType, Traversal traversal, int i, Member.Type type) {
        if (searchType == SearchType.ALL_CLASSES) {
            Member memberFindMember = findMember(str, str2, i, type);
            if (memberFindMember != null) {
                return memberFindMember;
            }
            if (traversal.canTraverse()) {
                Iterator it = this.mixins.iterator();
                while (it.hasNext()) {
                    Member memberFindMember2 = ((MixinInfo) it.next()).getClassInfo().findMember(str, str2, i, type);
                    if (memberFindMember2 != null) {
                        return cloneMember(memberFindMember2);
                    }
                }
            }
        }
        ClassInfo superClass = getSuperClass();
        if (superClass != null) {
            Iterator it2 = superClass.getTargets().iterator();
            while (it2.hasNext()) {
                Member memberFindInHierarchy = ((ClassInfo) it2.next()).findInHierarchy(str, str2, SearchType.ALL_CLASSES, traversal.next(), i & (-3), type);
                if (memberFindInHierarchy != null) {
                    return memberFindInHierarchy;
                }
            }
        }
        if (type != Member.Type.METHOD) {
            return null;
        }
        if (this.isInterface || MixinEnvironment.getCompatibilityLevel().supports(1)) {
            for (String str3 : this.interfaces) {
                ClassInfo classInfoForName = forName(str3);
                if (classInfoForName == null) {
                    logger.debug("Failed to resolve declared interface {} on {}", new Object[]{str3, this.name});
                } else {
                    Member memberFindInHierarchy2 = classInfoForName.findInHierarchy(str, str2, SearchType.ALL_CLASSES, traversal.next(), i & (-3), type);
                    if (memberFindInHierarchy2 != null) {
                        return this.isInterface ? memberFindInHierarchy2 : new InterfaceMethod(this, memberFindInHierarchy2);
                    }
                }
            }
            return null;
        }
        return null;
    }

    private Member cloneMember(Member member) {
        if (member instanceof Method) {
            return new Method(this, member);
        }
        return new Field(this, member);
    }

    public Method findMethod(MethodNode methodNode) {
        return findMethod(methodNode.name, methodNode.desc, methodNode.access);
    }

    public Method findMethod(MethodNode methodNode, int i) {
        return findMethod(methodNode.name, methodNode.desc, i);
    }

    public Method findMethod(MethodInsnNode methodInsnNode) {
        return findMethod(methodInsnNode.name, methodInsnNode.desc, 0);
    }

    public Method findMethod(MethodInsnNode methodInsnNode, int i) {
        return findMethod(methodInsnNode.name, methodInsnNode.desc, i);
    }

    public Method findMethod(String str, String str2, int i) {
        return (Method) findMember(str, str2, i, Member.Type.METHOD);
    }

    public Field findField(FieldNode fieldNode) {
        return findField(fieldNode.name, fieldNode.desc, fieldNode.access);
    }

    public Field findField(FieldInsnNode fieldInsnNode, int i) {
        return findField(fieldInsnNode.name, fieldInsnNode.desc, i);
    }

    public Field findField(String str, String str2, int i) {
        return (Field) findMember(str, str2, i, Member.Type.FIELD);
    }

    private Member findMember(String str, String str2, int i, Member.Type type) {
        for (Member member : type == Member.Type.METHOD ? this.methods : this.fields) {
            if (member.equals(str, str2) && member.matchesFlags(i)) {
                return member;
            }
        }
        if (type == Member.Type.METHOD && (i & 262144) != 0) {
            for (Method method : this.initialisers) {
                if (method.equals(str, str2) && method.matchesFlags(i)) {
                    return method;
                }
            }
            return null;
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ClassInfo)) {
            return false;
        }
        return ((ClassInfo) obj).name.equals(this.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    static ClassInfo fromClassNode(ClassNode classNode) {
        ClassInfo classInfo = (ClassInfo) cache.get(classNode.name);
        if (classInfo == null) {
            classInfo = new ClassInfo(classNode);
            cache.put(classNode.name, classInfo);
        }
        return classInfo;
    }

    public static ClassInfo forName(String str) {
        String strReplace = str.replace('.', '/');
        ClassInfo classInfo = (ClassInfo) cache.get(strReplace);
        if (classInfo == null) {
            try {
                classInfo = new ClassInfo(MixinService.getService().getBytecodeProvider().getClassNode(strReplace));
            } catch (Exception e) {
                logger.catching(Level.TRACE, e);
                logger.warn("Error loading class: {} ({}: {})", new Object[]{strReplace, e.getClass().getName(), e.getMessage()});
            }
            cache.put(strReplace, classInfo);
            logger.trace("Added class metadata for {} to metadata cache", new Object[]{strReplace});
        }
        return classInfo;
    }

    public static ClassInfo forDescriptor(String str, TypeLookup typeLookup) {
        try {
            return forType(Type.getObjectType(str), typeLookup);
        } catch (IllegalArgumentException unused) {
            logger.warn("Error resolving type from descriptor: {}", new Object[]{str});
            return null;
        }
    }

    public static ClassInfo forType(Type type, TypeLookup typeLookup) {
        if (type.getSort() == 9) {
            if (typeLookup == TypeLookup.ELEMENT_TYPE) {
                return forType(type.getElementType(), TypeLookup.ELEMENT_TYPE);
            }
            return OBJECT;
        }
        if (type.getSort() < 9) {
            return null;
        }
        return forName(type.getClassName().replace('.', '/'));
    }

    public static ClassInfo fromCache(String str) {
        return (ClassInfo) cache.get(str.replace('.', '/'));
    }

    public static ClassInfo fromCache(Type type, TypeLookup typeLookup) {
        if (type.getSort() == 9) {
            if (typeLookup == TypeLookup.ELEMENT_TYPE) {
                return fromCache(type.getElementType(), TypeLookup.ELEMENT_TYPE);
            }
            return OBJECT;
        }
        if (type.getSort() < 9) {
            return null;
        }
        return fromCache(type.getClassName());
    }

    public static ClassInfo getCommonSuperClass(String str, String str2) {
        if (str == null || str2 == null) {
            return OBJECT;
        }
        return getCommonSuperClass(forName(str), forName(str2));
    }

    public static ClassInfo getCommonSuperClass(Type type, Type type2) {
        if (type == null || type2 == null || type.getSort() != 10 || type2.getSort() != 10) {
            return OBJECT;
        }
        return getCommonSuperClass(forType(type, TypeLookup.DECLARED_TYPE), forType(type2, TypeLookup.DECLARED_TYPE));
    }

    private static ClassInfo getCommonSuperClass(ClassInfo classInfo, ClassInfo classInfo2) {
        return getCommonSuperClass(classInfo, classInfo2, false);
    }

    public static ClassInfo getCommonSuperClassOrInterface(String str, String str2) {
        if (str == null || str2 == null) {
            return OBJECT;
        }
        return getCommonSuperClassOrInterface(forName(str), forName(str2));
    }

    public static ClassInfo getCommonSuperClassOrInterface(Type type, Type type2) {
        if (type == null || type2 == null || type.getSort() != 10 || type2.getSort() != 10) {
            return OBJECT;
        }
        return getCommonSuperClassOrInterface(forType(type, TypeLookup.DECLARED_TYPE), forType(type2, TypeLookup.DECLARED_TYPE));
    }

    public static ClassInfo getCommonSuperClassOrInterface(ClassInfo classInfo, ClassInfo classInfo2) {
        return getCommonSuperClass(classInfo, classInfo2, true);
    }

    private static ClassInfo getCommonSuperClass(ClassInfo classInfo, ClassInfo classInfo2, boolean z) {
        if (classInfo.hasSuperClass(classInfo2, Traversal.NONE, z)) {
            return classInfo2;
        }
        if (classInfo2.hasSuperClass(classInfo, Traversal.NONE, z)) {
            return classInfo;
        }
        if (classInfo.isInterface() || classInfo2.isInterface()) {
            return OBJECT;
        }
        do {
            classInfo = classInfo.getSuperClass();
            if (classInfo == null) {
                return OBJECT;
            }
        } while (!classInfo2.hasSuperClass(classInfo, Traversal.NONE, z));
        return classInfo;
    }
}
