package jdk.nashorn.internal.runtime.linker;

import java.io.IOException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.DumpBytecode;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/JavaAdapterClassLoader.class */
final class JavaAdapterClassLoader {
    private static final AccessControlContext CREATE_LOADER_ACC_CTXT;
    private static final AccessControlContext GET_CONTEXT_ACC_CTXT;
    private static final Collection VISIBLE_INTERNAL_CLASS_NAMES;
    private final String className;
    private final byte[] classBytes;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !JavaAdapterClassLoader.class.desiredAssertionStatus();
        CREATE_LOADER_ACC_CTXT = ClassAndLoader.createPermAccCtxt(new String[]{"createClassLoader"});
        GET_CONTEXT_ACC_CTXT = ClassAndLoader.createPermAccCtxt(new String[]{Context.NASHORN_GET_CONTEXT});
        VISIBLE_INTERNAL_CLASS_NAMES = Collections.unmodifiableCollection(new HashSet(Arrays.asList(JavaAdapterServices.class.getName(), ScriptObject.class.getName(), ScriptFunction.class.getName(), JSType.class.getName())));
    }

    JavaAdapterClassLoader(String str, byte[] bArr) {
        this.className = str.replace('/', '.');
        this.classBytes = bArr;
    }

    StaticClass generateClass(ClassLoader classLoader, ProtectionDomain protectionDomain) {
        if ($assertionsDisabled || protectionDomain != null) {
            return (StaticClass) AccessController.doPrivileged(new PrivilegedAction(this, classLoader, protectionDomain) { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterClassLoader.1
                final ClassLoader val$parentLoader;
                final ProtectionDomain val$protectionDomain;
                final JavaAdapterClassLoader this$0;

                {
                    this.this$0 = this;
                    this.val$parentLoader = classLoader;
                    this.val$protectionDomain = protectionDomain;
                }

                @Override // java.security.PrivilegedAction
                public Object run() {
                    return run();
                }

                @Override // java.security.PrivilegedAction
                public StaticClass run() {
                    try {
                        return StaticClass.forClass(Class.forName(this.this$0.className, true, this.this$0.createClassLoader(this.val$parentLoader, this.val$protectionDomain)));
                    } catch (ClassNotFoundException e) {
                        throw new AssertionError(e);
                    }
                }
            }, CREATE_LOADER_ACC_CTXT);
        }
        throw new AssertionError();
    }

    private ClassLoader createClassLoader(ClassLoader classLoader, ProtectionDomain protectionDomain) {
        return new SecureClassLoader(this, classLoader, protectionDomain) { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterClassLoader.2
            private final ClassLoader myLoader = getClass().getClassLoader();
            static final boolean $assertionsDisabled;
            final ProtectionDomain val$protectionDomain;
            final JavaAdapterClassLoader this$0;

            {
                this.this$0 = this;
                this.val$protectionDomain = protectionDomain;
            }

            static {
                $assertionsDisabled = !JavaAdapterClassLoader.class.desiredAssertionStatus();
            }

            @Override // java.lang.ClassLoader
            public Class loadClass(String str, boolean z) {
                try {
                    Context.checkPackageAccess(str);
                    return super.loadClass(str, z);
                } catch (SecurityException e) {
                    if (JavaAdapterClassLoader.VISIBLE_INTERNAL_CLASS_NAMES.contains(str)) {
                        return this.myLoader.loadClass(str);
                    }
                    throw e;
                }
            }

            @Override // java.lang.ClassLoader
            protected Class findClass(String str) throws IOException, ClassNotFoundException {
                if (str.equals(this.this$0.className)) {
                    if (!$assertionsDisabled && this.this$0.classBytes == null) {
                        throw new AssertionError("what? already cleared .class bytes!!");
                    }
                    Context context = (Context) AccessController.doPrivileged(new PrivilegedAction(this) { // from class: jdk.nashorn.internal.runtime.linker.JavaAdapterClassLoader.2.1
                        final C02522 this$1;

                        {
                            this.this$1 = this;
                        }

                        @Override // java.security.PrivilegedAction
                        public Object run() {
                            return run();
                        }

                        @Override // java.security.PrivilegedAction
                        public Context run() {
                            return Context.getContext();
                        }
                    }, JavaAdapterClassLoader.GET_CONTEXT_ACC_CTXT);
                    DumpBytecode.dumpBytecode(context.getEnv(), context.getLogger(Compiler.class), this.this$0.classBytes, str);
                    return defineClass(str, this.this$0.classBytes, 0, this.this$0.classBytes.length, this.val$protectionDomain);
                }
                throw new ClassNotFoundException(str);
            }
        };
    }
}
