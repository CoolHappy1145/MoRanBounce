package org.spongepowered.tools.obfuscation.mirror;

import java.io.Serializable;
import javax.annotation.processing.ProcessingEnvironment;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/TypeReference.class */
public class TypeReference implements Serializable, Comparable {
    private static final long serialVersionUID = 1;
    private final String name;
    private TypeHandle handle;

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareTo((TypeReference) obj);
    }

    public TypeReference(TypeHandle typeHandle) {
        this.name = typeHandle.getName();
        this.handle = typeHandle;
    }

    public TypeReference(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public String getClassName() {
        return this.name.replace('/', '.');
    }

    public TypeHandle getHandle(ProcessingEnvironment processingEnvironment) {
        if (this.handle == null) {
            try {
                this.handle = new TypeHandle(processingEnvironment.getElementUtils().getTypeElement(getClassName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.handle;
    }

    public String toString() {
        return String.format("TypeReference[%s]", this.name);
    }

    public int compareTo(TypeReference typeReference) {
        if (typeReference == null) {
            return -1;
        }
        return this.name.compareTo(typeReference.name);
    }

    public boolean equals(Object obj) {
        return (obj instanceof TypeReference) && compareTo((TypeReference) obj) == 0;
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}
