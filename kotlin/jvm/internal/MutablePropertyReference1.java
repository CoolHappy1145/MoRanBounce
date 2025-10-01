package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty1;

/* loaded from: L-out.jar:kotlin/jvm/internal/MutablePropertyReference1.class */
public abstract class MutablePropertyReference1 extends MutablePropertyReference implements KMutableProperty1 {
    @Override // kotlin.reflect.KMutableProperty, kotlin.reflect.KMutableProperty0
    public KMutableProperty.Setter getSetter() {
        return getSetter();
    }

    @Override // kotlin.reflect.KProperty, kotlin.reflect.KProperty0
    public KProperty.Getter getGetter() {
        return getGetter();
    }

    public MutablePropertyReference1() {
    }

    @SinceKotlin(version = "1.1")
    public MutablePropertyReference1(Object obj) {
        super(obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    protected KCallable computeReflected() {
        return Reflection.mutableProperty1(this);
    }

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object obj) {
        return get(obj);
    }

    @Override // kotlin.reflect.KProperty, kotlin.reflect.KProperty0
    public KProperty1.Getter getGetter() {
        return ((KMutableProperty1) getReflected()).getGetter();
    }

    @Override // kotlin.reflect.KMutableProperty, kotlin.reflect.KMutableProperty0
    public KMutableProperty1.Setter getSetter() {
        return ((KMutableProperty1) getReflected()).getSetter();
    }

    @Override // kotlin.reflect.KProperty1
    @SinceKotlin(version = "1.1")
    public Object getDelegate(Object obj) {
        return ((KMutableProperty1) getReflected()).getDelegate(obj);
    }
}
