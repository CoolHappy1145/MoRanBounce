package jdk.nashorn.internal.runtime;

import java.lang.invoke.SwitchPoint;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/SharedPropertyMap.class */
public final class SharedPropertyMap extends PropertyMap {
    private SwitchPoint switchPoint;
    private static final long serialVersionUID = 2166297719721778876L;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !SharedPropertyMap.class.desiredAssertionStatus();
    }

    public SharedPropertyMap(PropertyMap propertyMap) {
        super(propertyMap);
        this.switchPoint = new SwitchPoint();
    }

    @Override // jdk.nashorn.internal.runtime.PropertyMap
    public void propertyAdded(Property property, boolean z) {
        if (z) {
            invalidateSwitchPoint();
        }
        super.propertyAdded(property, z);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyMap
    public void propertyDeleted(Property property, boolean z) {
        if (z) {
            invalidateSwitchPoint();
        }
        super.propertyDeleted(property, z);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyMap
    public void propertyModified(Property property, Property property2, boolean z) {
        if (z) {
            invalidateSwitchPoint();
        }
        super.propertyModified(property, property2, z);
    }

    @Override // jdk.nashorn.internal.runtime.PropertyMap
    boolean isValidSharedProtoMap() {
        return this.switchPoint != null;
    }

    @Override // jdk.nashorn.internal.runtime.PropertyMap
    SwitchPoint getSharedProtoSwitchPoint() {
        return this.switchPoint;
    }

    void invalidateSwitchPoint() {
        if (this.switchPoint != null) {
            if (!$assertionsDisabled && this.switchPoint.hasBeenInvalidated()) {
                throw new AssertionError();
            }
            SwitchPoint.invalidateAll(new SwitchPoint[]{this.switchPoint});
            this.switchPoint = null;
        }
    }
}
