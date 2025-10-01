package org.spongepowered.asm.lib;

import org.spongepowered.asm.mixin.throwables.CompanionPluginError;

/* loaded from: L-out.jar:org/spongepowered/asm/lib/ClassVisitor.class */
public abstract class ClassVisitor {
    public void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
        throw new CompanionPluginError("ClassVisitor.visit");
    }

    public void visitSource(String str, String str2) {
        throw new CompanionPluginError("ClassVisitor.visitSource");
    }

    public void visitOuterClass(String str, String str2, String str3) {
        throw new CompanionPluginError("ClassVisitor.visitOuterClass");
    }

    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        throw new CompanionPluginError("ClassVisitor.visitAnnotation");
    }

    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        throw new CompanionPluginError("ClassVisitor.visitTypeAnnotation");
    }

    public void visitAttribute(Attribute attribute) {
        throw new CompanionPluginError("ClassVisitor.visitAttribute");
    }

    public void visitInnerClass(String str, String str2, String str3, int i) {
        throw new CompanionPluginError("ClassVisitor.visitInnerClass");
    }

    public FieldVisitor visitField(int i, String str, String str2, String str3, Object obj) {
        throw new CompanionPluginError("ClassVisitor.visitField");
    }

    public MethodVisitor visitMethod(int i, String str, String str2, String str3, String[] strArr) {
        throw new CompanionPluginError("ClassVisitor.visitMethod");
    }

    public void visitEnd() {
        throw new CompanionPluginError("ClassVisitor.visitEnd");
    }
}
