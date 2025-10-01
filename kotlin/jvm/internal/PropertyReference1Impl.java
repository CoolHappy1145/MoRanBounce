package kotlin.jvm.internal;

import kotlin.reflect.KDeclarationContainer;

/* loaded from: L-out.jar:kotlin/jvm/internal/PropertyReference1Impl.class */
public class PropertyReference1Impl extends PropertyReference1 {
    private final KDeclarationContainer owner;
    private final String name;
    private final String signature;

    public PropertyReference1Impl(KDeclarationContainer kDeclarationContainer, String str, String str2) {
        this.owner = kDeclarationContainer;
        this.name = str;
        this.signature = str2;
    }

    @Override // kotlin.jvm.internal.CallableReference
    public KDeclarationContainer getOwner() {
        return this.owner;
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public String getName() {
        return this.name;
    }

    @Override // kotlin.jvm.internal.CallableReference
    public String getSignature() {
        return this.signature;
    }

    @Override // kotlin.reflect.KProperty1
    public Object get(Object obj) {
        return getGetter().call(new Object[]{obj});
    }
}
