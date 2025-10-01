package jdk.nashorn.internal.runtime;

import java.security.ProtectionDomain;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/StructureLoader.class */
final class StructureLoader extends NashornLoader {
    private static final String SINGLE_FIELD_PREFIX = Compiler.binaryName(Compiler.SCRIPTS_PACKAGE) + '.' + CompilerConstants.JS_OBJECT_SINGLE_FIELD_PREFIX.symbolName();
    private static final String DUAL_FIELD_PREFIX = Compiler.binaryName(Compiler.SCRIPTS_PACKAGE) + '.' + CompilerConstants.JS_OBJECT_DUAL_FIELD_PREFIX.symbolName();

    StructureLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    private static boolean isDualFieldStructure(String str) {
        return str.startsWith(DUAL_FIELD_PREFIX);
    }

    static boolean isSingleFieldStructure(String str) {
        return str.startsWith(SINGLE_FIELD_PREFIX);
    }

    static boolean isStructureClass(String str) {
        return isDualFieldStructure(str) || isSingleFieldStructure(str);
    }

    @Override // java.lang.ClassLoader
    protected Class findClass(String str) {
        if (isDualFieldStructure(str)) {
            return generateClass(str, str.substring(DUAL_FIELD_PREFIX.length()), true);
        }
        if (isSingleFieldStructure(str)) {
            return generateClass(str, str.substring(SINGLE_FIELD_PREFIX.length()), false);
        }
        return super.findClass(str);
    }

    private Class generateClass(String str, String str2, boolean z) {
        byte[] bArrGenerate = new ObjectClassGenerator(Context.getContextTrusted(), z).generate(str2);
        return defineClass(str, bArrGenerate, 0, bArrGenerate.length, new ProtectionDomain(null, getPermissions(null)));
    }
}
