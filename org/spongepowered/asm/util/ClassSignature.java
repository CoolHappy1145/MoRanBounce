package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;
import org.objectweb.asm.signature.SignatureWriter;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.util.asm.ASM;

/* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature.class */
public class ClassSignature {
    protected static final String OBJECT = "java/lang/Object";
    private final Map types = new LinkedHashMap();
    private Token superClass = new Token("java/lang/Object");
    private final List interfaces = new ArrayList();
    private final Deque rawInterfaces = new LinkedList();

    /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$IToken.class */
    interface IToken {
        public static final String WILDCARDS = "+-";

        String asType();

        String asBound();

        Token asToken();

        IToken setArray(boolean z);

        IToken setWildcard(char c);
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$Lazy.class */
    static class Lazy extends ClassSignature {
        private final String sig;
        private ClassSignature generated;

        Lazy(String str) {
            this.sig = str;
        }

        public ClassSignature wake() {
            if (this.generated == null) {
                this.generated = ClassSignature.m69of(this.sig);
            }
            return this.generated;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$TypeVar.class */
    static class TypeVar implements Comparable {
        private final String originalName;
        private String currentName;

        @Override // java.lang.Comparable
        public int compareTo(Object obj) {
            return compareTo((TypeVar) obj);
        }

        TypeVar(String str) {
            this.originalName = str;
            this.currentName = str;
        }

        public int compareTo(TypeVar typeVar) {
            return this.currentName.compareTo(typeVar.currentName);
        }

        public String toString() {
            return this.currentName;
        }

        String getOriginalName() {
            return this.originalName;
        }

        void rename(String str) {
            this.currentName = str;
        }

        public boolean matches(String str) {
            return this.originalName.equals(str);
        }

        public boolean equals(Object obj) {
            return this.currentName.equals(obj);
        }

        public int hashCode() {
            return this.currentName.hashCode();
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$Token.class */
    static class Token implements IToken {
        static final String SYMBOLS = "+-*";
        private final boolean inner;
        private boolean array;
        private char symbol;
        private String type;
        private List classBound;
        private List ifaceBound;
        private List signature;
        private List suffix;
        private Token tail;

        Token() {
            this(false);
        }

        Token(String str) {
            this(str, false);
        }

        Token(char c) {
            this();
            this.symbol = c;
        }

        Token(boolean z) {
            this(null, z);
        }

        Token(String str, boolean z) {
            this.symbol = (char) 0;
            this.inner = z;
            this.type = str;
        }

        Token setSymbol(char c) {
            if (this.symbol == 0 && SYMBOLS.indexOf(c) > -1) {
                this.symbol = c;
            }
            return this;
        }

        Token setType(String str) {
            if (this.type == null) {
                this.type = str;
            }
            return this;
        }

        boolean hasClassBound() {
            return this.classBound != null;
        }

        boolean hasInterfaceBound() {
            return this.ifaceBound != null;
        }

        @Override // org.spongepowered.asm.util.ClassSignature.IToken
        public IToken setArray(boolean z) {
            this.array |= z;
            return this;
        }

        @Override // org.spongepowered.asm.util.ClassSignature.IToken
        public IToken setWildcard(char c) {
            if (IToken.WILDCARDS.indexOf(c) == -1) {
                return this;
            }
            return setSymbol(c);
        }

        private List getClassBound() {
            if (this.classBound == null) {
                this.classBound = new ArrayList();
            }
            return this.classBound;
        }

        private List getIfaceBound() {
            if (this.ifaceBound == null) {
                this.ifaceBound = new ArrayList();
            }
            return this.ifaceBound;
        }

        private List getSignature() {
            if (this.signature == null) {
                this.signature = new ArrayList();
            }
            return this.signature;
        }

        private List getSuffix() {
            if (this.suffix == null) {
                this.suffix = new ArrayList();
            }
            return this.suffix;
        }

        IToken addTypeArgument(char c) {
            if (this.tail != null) {
                return this.tail.addTypeArgument(c);
            }
            Token token = new Token(c);
            getSignature().add(token);
            return token;
        }

        IToken addTypeArgument(String str) {
            if (this.tail != null) {
                return this.tail.addTypeArgument(str);
            }
            Token token = new Token(str);
            getSignature().add(token);
            return token;
        }

        IToken addTypeArgument(Token token) {
            if (this.tail != null) {
                return this.tail.addTypeArgument(token);
            }
            getSignature().add(token);
            return token;
        }

        IToken addTypeArgument(TokenHandle tokenHandle) {
            if (this.tail != null) {
                return this.tail.addTypeArgument(tokenHandle);
            }
            TokenHandle tokenHandleClone = tokenHandle.clone();
            getSignature().add(tokenHandleClone);
            return tokenHandleClone;
        }

        Token addBound(String str, boolean z) {
            if (z) {
                return addClassBound(str);
            }
            return addInterfaceBound(str);
        }

        Token addClassBound(String str) {
            Token token = new Token(str);
            getClassBound().add(token);
            return token;
        }

        Token addInterfaceBound(String str) {
            Token token = new Token(str);
            getIfaceBound().add(token);
            return token;
        }

        Token addInnerClass(String str) {
            this.tail = new Token(str, true);
            getSuffix().add(this.tail);
            return this.tail;
        }

        public String toString() {
            return asType();
        }

        @Override // org.spongepowered.asm.util.ClassSignature.IToken
        public String asBound() {
            StringBuilder sb = new StringBuilder();
            if (this.type != null) {
                sb.append(this.type);
            }
            if (this.classBound != null) {
                Iterator it = this.classBound.iterator();
                while (it.hasNext()) {
                    sb.append(((Token) it.next()).asType());
                }
            }
            if (this.ifaceBound != null) {
                Iterator it2 = this.ifaceBound.iterator();
                while (it2.hasNext()) {
                    sb.append(':').append(((Token) it2.next()).asType());
                }
            }
            return sb.toString();
        }

        @Override // org.spongepowered.asm.util.ClassSignature.IToken
        public String asType() {
            return asType(false);
        }

        public String asType(boolean z) {
            StringBuilder sb = new StringBuilder();
            if (this.array) {
                sb.append('[');
            }
            if (this.symbol != 0) {
                sb.append(this.symbol);
            }
            if (this.type == null) {
                return sb.toString();
            }
            if (!this.inner) {
                sb.append('L');
            }
            sb.append(this.type);
            if (!z) {
                if (this.signature != null) {
                    sb.append('<');
                    Iterator it = this.signature.iterator();
                    while (it.hasNext()) {
                        sb.append(((IToken) it.next()).asType());
                    }
                    sb.append('>');
                }
                if (this.suffix != null) {
                    Iterator it2 = this.suffix.iterator();
                    while (it2.hasNext()) {
                        sb.append('.').append(((IToken) it2.next()).asType());
                    }
                }
            }
            if (!this.inner) {
                sb.append(';');
            }
            return sb.toString();
        }

        boolean isRaw() {
            return this.signature == null;
        }

        String getClassType() {
            return this.type != null ? this.type : "java/lang/Object";
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$TokenHandle.class */
    class TokenHandle implements IToken {
        final Token token;
        boolean array;
        char wildcard;
        final ClassSignature this$0;

        /* renamed from: clone, reason: collision with other method in class */
        public Object m1936clone() {
            return clone();
        }

        TokenHandle(ClassSignature classSignature) {
            this(classSignature, new Token());
        }

        TokenHandle(ClassSignature classSignature, Token token) {
            this.this$0 = classSignature;
            this.token = token;
        }

        @Override // org.spongepowered.asm.util.ClassSignature.IToken
        public IToken setArray(boolean z) {
            this.array |= z;
            return this;
        }

        @Override // org.spongepowered.asm.util.ClassSignature.IToken
        public IToken setWildcard(char c) {
            if (IToken.WILDCARDS.indexOf(c) > -1) {
                this.wildcard = c;
            }
            return this;
        }

        @Override // org.spongepowered.asm.util.ClassSignature.IToken
        public String asBound() {
            return this.token.asBound();
        }

        @Override // org.spongepowered.asm.util.ClassSignature.IToken
        public String asType() {
            StringBuilder sb = new StringBuilder();
            if (this.wildcard > 0) {
                sb.append(this.wildcard);
            }
            if (this.array) {
                sb.append('[');
            }
            return sb.append(this.this$0.getTypeVar(this)).toString();
        }

        @Override // org.spongepowered.asm.util.ClassSignature.IToken
        public Token asToken() {
            return this.token;
        }

        public String toString() {
            return this.token.toString();
        }

        public TokenHandle clone() {
            return new TokenHandle(this.this$0, this.token);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$SignatureParser.class */
    class SignatureParser extends SignatureVisitor {
        private FormalParamElement param;
        final ClassSignature this$0;

        /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$SignatureParser$SignatureElement.class */
        abstract class SignatureElement extends SignatureVisitor {
            final SignatureParser this$1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public SignatureElement(SignatureParser signatureParser) {
                super(ASM.API_VERSION);
                this.this$1 = signatureParser;
            }
        }

        /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$SignatureParser$TokenElement.class */
        abstract class TokenElement extends SignatureElement {
            protected Token token;
            private boolean array;
            final SignatureParser this$1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            TokenElement(SignatureParser signatureParser) {
                super(signatureParser);
                this.this$1 = signatureParser;
            }

            public Token getToken() {
                if (this.token == null) {
                    this.token = new Token();
                }
                return this.token;
            }

            protected void setArray() {
                this.array = true;
            }

            private boolean getArray() {
                boolean z = this.array;
                this.array = false;
                return z;
            }

            public void visitClassType(String str) {
                getToken().setType(str);
            }

            public SignatureVisitor visitClassBound() {
                getToken();
                return new BoundElement(this.this$1, this, true);
            }

            public SignatureVisitor visitInterfaceBound() {
                getToken();
                return new BoundElement(this.this$1, this, false);
            }

            public void visitInnerClassType(String str) {
                this.token.addInnerClass(str);
            }

            public SignatureVisitor visitArrayType() {
                setArray();
                return this;
            }

            public SignatureVisitor visitTypeArgument(char c) {
                return new TypeArgElement(this.this$1, this, c);
            }

            Token addTypeArgument() {
                return this.token.addTypeArgument('*').asToken();
            }

            IToken addTypeArgument(char c) {
                return this.token.addTypeArgument(c).setArray(getArray());
            }

            IToken addTypeArgument(String str) {
                return this.token.addTypeArgument(str).setArray(getArray());
            }

            IToken addTypeArgument(Token token) {
                return this.token.addTypeArgument(token).setArray(getArray());
            }

            IToken addTypeArgument(TokenHandle tokenHandle) {
                return this.token.addTypeArgument(tokenHandle).setArray(getArray());
            }
        }

        /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$SignatureParser$FormalParamElement.class */
        class FormalParamElement extends TokenElement {
            private final TokenHandle handle;
            final SignatureParser this$1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            FormalParamElement(SignatureParser signatureParser, String str) {
                super(signatureParser);
                this.this$1 = signatureParser;
                this.handle = signatureParser.this$0.getType(str);
                this.token = this.handle.asToken();
            }
        }

        /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$SignatureParser$TypeArgElement.class */
        class TypeArgElement extends TokenElement {
            private final TokenElement type;
            private final char wildcard;
            final SignatureParser this$1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            TypeArgElement(SignatureParser signatureParser, TokenElement tokenElement, char c) {
                super(signatureParser);
                this.this$1 = signatureParser;
                this.type = tokenElement;
                this.wildcard = c;
            }

            @Override // org.spongepowered.asm.util.ClassSignature.SignatureParser.TokenElement
            public SignatureVisitor visitArrayType() {
                this.type.setArray();
                return this;
            }

            public void visitBaseType(char c) {
                this.token = this.type.addTypeArgument(c).asToken();
            }

            public void visitTypeVariable(String str) {
                this.token = this.type.addTypeArgument(this.this$1.this$0.getType(str)).setWildcard(this.wildcard).asToken();
            }

            @Override // org.spongepowered.asm.util.ClassSignature.SignatureParser.TokenElement
            public void visitClassType(String str) {
                this.token = this.type.addTypeArgument(str).setWildcard(this.wildcard).asToken();
            }

            public void visitTypeArgument() {
                this.token.addTypeArgument('*');
            }

            @Override // org.spongepowered.asm.util.ClassSignature.SignatureParser.TokenElement
            public SignatureVisitor visitTypeArgument(char c) {
                return new TypeArgElement(this.this$1, this, c);
            }
        }

        /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$SignatureParser$BoundElement.class */
        class BoundElement extends TokenElement {
            private final TokenElement type;
            private final boolean classBound;
            final SignatureParser this$1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            BoundElement(SignatureParser signatureParser, TokenElement tokenElement, boolean z) {
                super(signatureParser);
                this.this$1 = signatureParser;
                this.type = tokenElement;
                this.classBound = z;
            }

            @Override // org.spongepowered.asm.util.ClassSignature.SignatureParser.TokenElement
            public void visitClassType(String str) {
                this.token = this.type.token.addBound(str, this.classBound);
            }

            public void visitTypeArgument() {
                this.token.addTypeArgument('*');
            }

            @Override // org.spongepowered.asm.util.ClassSignature.SignatureParser.TokenElement
            public SignatureVisitor visitTypeArgument(char c) {
                return new TypeArgElement(this.this$1, this, c);
            }
        }

        /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$SignatureParser$SuperClassElement.class */
        class SuperClassElement extends TokenElement {
            final SignatureParser this$1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            SuperClassElement(SignatureParser signatureParser) {
                super(signatureParser);
                this.this$1 = signatureParser;
            }

            public void visitEnd() {
                this.this$1.this$0.setSuperClass(this.token);
            }
        }

        /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$SignatureParser$InterfaceElement.class */
        class InterfaceElement extends TokenElement {
            final SignatureParser this$1;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            InterfaceElement(SignatureParser signatureParser) {
                super(signatureParser);
                this.this$1 = signatureParser;
            }

            public void visitEnd() {
                this.this$1.this$0.addInterface(this.token);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        SignatureParser(ClassSignature classSignature) {
            super(ASM.API_VERSION);
            this.this$0 = classSignature;
        }

        public void visitFormalTypeParameter(String str) {
            this.param = new FormalParamElement(this, str);
        }

        public SignatureVisitor visitClassBound() {
            return this.param.visitClassBound();
        }

        public SignatureVisitor visitInterfaceBound() {
            return this.param.visitInterfaceBound();
        }

        public SignatureVisitor visitSuperclass() {
            return new SuperClassElement(this);
        }

        public SignatureVisitor visitInterface() {
            return new InterfaceElement(this);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/ClassSignature$SignatureRemapper.class */
    class SignatureRemapper extends SignatureWriter {
        private final Set localTypeVars = new HashSet();
        final ClassSignature this$0;

        SignatureRemapper(ClassSignature classSignature) {
            this.this$0 = classSignature;
        }

        public void visitFormalTypeParameter(String str) {
            this.localTypeVars.add(str);
            super.visitFormalTypeParameter(str);
        }

        public void visitTypeVariable(String str) {
            TypeVar typeVar;
            if (!this.localTypeVars.contains(str) && (typeVar = this.this$0.getTypeVar(str)) != null) {
                super.visitTypeVariable(typeVar.toString());
            } else {
                super.visitTypeVariable(str);
            }
        }
    }

    ClassSignature() {
    }

    private ClassSignature read(String str) {
        if (str != null) {
            try {
                new SignatureReader(str).accept(new SignatureParser(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    protected TypeVar getTypeVar(String str) {
        for (TypeVar typeVar : this.types.keySet()) {
            if (typeVar.matches(str)) {
                return typeVar;
            }
        }
        return null;
    }

    protected TokenHandle getType(String str) {
        for (TypeVar typeVar : this.types.keySet()) {
            if (typeVar.matches(str)) {
                return (TokenHandle) this.types.get(typeVar);
            }
        }
        TokenHandle tokenHandle = new TokenHandle(this);
        this.types.put(new TypeVar(str), tokenHandle);
        return tokenHandle;
    }

    protected String getTypeVar(TokenHandle tokenHandle) {
        for (Map.Entry entry : this.types.entrySet()) {
            TypeVar typeVar = (TypeVar) entry.getKey();
            TokenHandle tokenHandle2 = (TokenHandle) entry.getValue();
            if (tokenHandle == tokenHandle2 || tokenHandle.asToken() == tokenHandle2.asToken()) {
                return "T" + typeVar + ";";
            }
        }
        return tokenHandle.token.asType();
    }

    protected void addTypeVar(TypeVar typeVar, TokenHandle tokenHandle) {
        if (this.types.containsKey(typeVar)) {
            throw new IllegalArgumentException("TypeVar " + typeVar + " is already present on " + this);
        }
        this.types.put(typeVar, tokenHandle);
    }

    protected void setSuperClass(Token token) {
        this.superClass = token;
    }

    public String getSuperClass() {
        return this.superClass.asType(true);
    }

    protected void addInterface(Token token) {
        if (!token.isRaw()) {
            String strAsType = token.asType(true);
            ListIterator listIterator = this.interfaces.listIterator();
            while (listIterator.hasNext()) {
                Token token2 = (Token) listIterator.next();
                if (token2.isRaw() && token2.asType(true).equals(strAsType)) {
                    listIterator.set(token);
                    return;
                }
            }
        }
        this.interfaces.add(token);
    }

    public void addInterface(String str) {
        this.rawInterfaces.add(str);
    }

    protected void addRawInterface(String str) {
        Token token = new Token(str);
        String strAsType = token.asType(true);
        Iterator it = this.interfaces.iterator();
        while (it.hasNext()) {
            if (((Token) it.next()).asType(true).equals(strAsType)) {
                return;
            }
        }
        this.interfaces.add(token);
    }

    public void merge(ClassSignature classSignature) {
        try {
            HashSet hashSet = new HashSet();
            Iterator it = this.types.keySet().iterator();
            while (it.hasNext()) {
                hashSet.add(((TypeVar) it.next()).toString());
            }
            classSignature.conform(hashSet);
            for (Map.Entry entry : classSignature.types.entrySet()) {
                addTypeVar((TypeVar) entry.getKey(), (TokenHandle) entry.getValue());
            }
            Iterator it2 = classSignature.interfaces.iterator();
            while (it2.hasNext()) {
                addInterface((Token) it2.next());
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void conform(Set set) {
        for (TypeVar typeVar : this.types.keySet()) {
            String strFindUniqueName = findUniqueName(typeVar.getOriginalName(), set);
            typeVar.rename(strFindUniqueName);
            set.add(strFindUniqueName);
        }
    }

    private String findUniqueName(String str, Set set) {
        String strFindOffsetName;
        if (!set.contains(str)) {
            return str;
        }
        if (str.length() == 1 && (strFindOffsetName = findOffsetName(str.charAt(0), set)) != null) {
            return strFindOffsetName;
        }
        String strFindOffsetName2 = findOffsetName('T', set, "", str);
        if (strFindOffsetName2 != null) {
            return strFindOffsetName2;
        }
        String strFindOffsetName3 = findOffsetName('T', set, str, "");
        if (strFindOffsetName3 != null) {
            return strFindOffsetName3;
        }
        String strFindOffsetName4 = findOffsetName('T', set, "T", str);
        if (strFindOffsetName4 != null) {
            return strFindOffsetName4;
        }
        String strFindOffsetName5 = findOffsetName('T', set, "", str + "Type");
        if (strFindOffsetName5 != null) {
            return strFindOffsetName5;
        }
        throw new IllegalStateException("Failed to conform type var: " + str);
    }

    private String findOffsetName(char c, Set set) {
        return findOffsetName(c, set, "", "");
    }

    private String findOffsetName(char c, Set set, String str, String str2) {
        String str3 = String.format("%s%s%s", str, Character.valueOf(c), str2);
        if (!set.contains(str3)) {
            return str3;
        }
        if (c > '@' && c < '[') {
            int i = c - '@';
            while (true) {
                int i2 = i;
                if (i2 + 65 != c) {
                    String str4 = String.format("%s%s%s", str, Character.valueOf((char) (i2 + 65)), str2);
                    if (set.contains(str4)) {
                        i = (i2 + 1) % 26;
                    } else {
                        return str4;
                    }
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public SignatureVisitor getRemapper() {
        return new SignatureRemapper(this);
    }

    public String toString() {
        while (this.rawInterfaces.size() > 0) {
            addRawInterface((String) this.rawInterfaces.remove());
        }
        StringBuilder sb = new StringBuilder();
        if (this.types.size() > 0) {
            boolean z = false;
            StringBuilder sb2 = new StringBuilder();
            for (Map.Entry entry : this.types.entrySet()) {
                String strAsBound = ((TokenHandle) entry.getValue()).asBound();
                if (!strAsBound.isEmpty()) {
                    sb2.append(entry.getKey()).append(':').append(strAsBound);
                    z = true;
                }
            }
            if (z) {
                sb.append('<').append((CharSequence) sb2).append('>');
            }
        }
        sb.append(this.superClass.asType());
        Iterator it = this.interfaces.iterator();
        while (it.hasNext()) {
            sb.append(((Token) it.next()).asType());
        }
        return sb.toString();
    }

    /* renamed from: of */
    public static ClassSignature m69of(String str) {
        return new ClassSignature().read(str);
    }

    /* renamed from: of */
    public static ClassSignature m70of(ClassNode classNode) {
        if (classNode.signature != null) {
            return m69of(classNode.signature);
        }
        return generate(classNode);
    }

    public static ClassSignature ofLazy(ClassNode classNode) {
        if (classNode.signature != null) {
            return new Lazy(classNode.signature);
        }
        return generate(classNode);
    }

    private static ClassSignature generate(ClassNode classNode) {
        ClassSignature classSignature = new ClassSignature();
        classSignature.setSuperClass(new Token(classNode.superName != null ? classNode.superName : "java/lang/Object"));
        Iterator it = classNode.interfaces.iterator();
        while (it.hasNext()) {
            classSignature.addInterface(new Token((String) it.next()));
        }
        return classSignature;
    }
}
