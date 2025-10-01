package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.SwitchPoint;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.linker.NashornGuards;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/SetMethodCreator.class */
final class SetMethodCreator {
    private final ScriptObject sobj;
    private final PropertyMap map;
    private final FindProperty find;
    private final CallSiteDescriptor desc;
    private final Class type;
    private final LinkRequest request;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !SetMethodCreator.class.desiredAssertionStatus();
    }

    SetMethodCreator(ScriptObject scriptObject, FindProperty findProperty, CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        this.sobj = scriptObject;
        this.map = scriptObject.getMap();
        this.find = findProperty;
        this.desc = callSiteDescriptor;
        this.type = callSiteDescriptor.getMethodType().parameterType(1);
        this.request = linkRequest;
    }

    private String getName() {
        return this.desc.getNameToken(2);
    }

    private PropertyMap getMap() {
        return this.map;
    }

    GuardedInvocation createGuardedInvocation(SwitchPoint switchPoint) {
        return createSetMethod(switchPoint).createGuardedInvocation();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/SetMethodCreator$SetMethod.class */
    private class SetMethod {
        private final MethodHandle methodHandle;
        private final Property property;
        static final boolean $assertionsDisabled;
        final SetMethodCreator this$0;

        static {
            $assertionsDisabled = !SetMethodCreator.class.desiredAssertionStatus();
        }

        SetMethod(SetMethodCreator setMethodCreator, MethodHandle methodHandle, Property property) {
            this.this$0 = setMethodCreator;
            if (!$assertionsDisabled && methodHandle == null) {
                throw new AssertionError();
            }
            this.methodHandle = methodHandle;
            this.property = property;
        }

        GuardedInvocation createGuardedInvocation() {
            boolean zExplicitInstanceOfCheck = NashornGuards.explicitInstanceOfCheck(this.this$0.desc, this.this$0.request);
            return new GuardedInvocation(this.methodHandle, NashornGuards.getGuard(this.this$0.sobj, this.property, this.this$0.desc, zExplicitInstanceOfCheck), (SwitchPoint) null, zExplicitInstanceOfCheck ? null : ClassCastException.class);
        }
    }

    private SetMethod createSetMethod(SwitchPoint switchPoint) {
        if (this.find != null) {
            return createExistingPropertySetter();
        }
        checkStrictCreateNewVariable();
        ScriptObject scriptObject = this.sobj;
        if (0 != 0) {
            return createGlobalPropertySetter();
        }
        return createNewPropertySetter(switchPoint);
    }

    private void checkStrictCreateNewVariable() {
        if (NashornCallSiteDescriptor.isScope(this.desc) && NashornCallSiteDescriptor.isStrict(this.desc)) {
            throw ECMAErrors.referenceError("not.defined", new String[]{getName()});
        }
    }

    private SetMethod createExistingPropertySetter() {
        MethodHandle setter;
        MethodHandle methodHandleAddProtoFilter;
        Property property = this.find.getProperty();
        boolean zIsStrict = NashornCallSiteDescriptor.isStrict(this.desc);
        if (NashornCallSiteDescriptor.isDeclaration(this.desc)) {
            if (!$assertionsDisabled && !property.needsDeclaration()) {
                throw new AssertionError();
            }
            PropertyMap map = getMap();
            Property propertyRemoveFlags = property.removeFlags(512);
            PropertyMap propertyMapReplaceProperty = map.replaceProperty(property, propertyRemoveFlags);
            MethodHandle setter2 = this.find.replaceProperty(propertyRemoveFlags).getSetter(this.type, zIsStrict, this.request);
            MethodHandle methodHandleAsType = Lookup.f31MH.insertArguments(ScriptObject.DECLARE_AND_SET, 1, new Object[]{getName()}).asType(setter2.type());
            MethodHandle methodHandleDropArguments = Lookup.f31MH.dropArguments(Lookup.f31MH.insertArguments(ScriptObject.CAS_MAP, 1, new Object[]{map, propertyMapReplaceProperty}), 1, new Class[]{this.type});
            setter = Lookup.f31MH.guardWithTest(Lookup.f31MH.asType(methodHandleDropArguments, methodHandleDropArguments.type().changeParameterType(0, Object.class)), setter2, methodHandleAsType);
        } else {
            setter = this.find.getSetter(this.type, zIsStrict, this.request);
        }
        if (!$assertionsDisabled && setter == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && property == null) {
            throw new AssertionError();
        }
        if (!(property instanceof UserAccessorProperty) && this.find.isInherited()) {
            methodHandleAddProtoFilter = ScriptObject.addProtoFilter(setter, this.find.getProtoChainLength());
        } else {
            methodHandleAddProtoFilter = setter;
        }
        return new SetMethod(this, methodHandleAddProtoFilter, property);
    }

    private SetMethod createGlobalPropertySetter() {
        return new SetMethod(this, Lookup.f31MH.filterArguments(Context.getGlobal().addSpill(this.type, getName()), 0, new MethodHandle[]{ScriptObject.GLOBALFILTER}), null);
    }

    private SetMethod createNewPropertySetter(SwitchPoint switchPoint) {
        SetMethod setMethodCreateNewFieldSetter = this.map.getFreeFieldSlot() > -1 ? createNewFieldSetter(switchPoint) : createNewSpillPropertySetter(switchPoint);
        this.map.propertyAdded(setMethodCreateNewFieldSetter.property, true);
        return setMethodCreateNewFieldSetter;
    }

    private SetMethod createNewSetter(Property property, SwitchPoint switchPoint) {
        property.setBuiltinSwitchPoint(switchPoint);
        PropertyMap map = getMap();
        PropertyMap newMap = getNewMap(property);
        boolean zIsStrict = NashornCallSiteDescriptor.isStrict(this.desc);
        String nameToken = this.desc.getNameToken(2);
        MethodHandle setter = property.getSetter(this.type, newMap);
        MethodHandle methodHandleInsertArguments = Lookup.f31MH.insertArguments(Lookup.f31MH.insertArguments(ScriptObject.SET_SLOW[JSType.getAccessorTypeIndex(this.type)], 3, new Object[]{Integer.valueOf(NashornCallSiteDescriptor.getFlags(this.desc))}), 1, new Object[]{nameToken});
        MethodHandle methodHandleAsType = Lookup.f31MH.asType(methodHandleInsertArguments, methodHandleInsertArguments.type().changeParameterType(0, Object.class));
        if (!$assertionsDisabled && !methodHandleAsType.type().equals(setter.type())) {
            throw new AssertionError("slow=" + methodHandleAsType + " != fast=" + setter);
        }
        MethodHandle methodHandleDropArguments = Lookup.f31MH.dropArguments(Lookup.f31MH.insertArguments(ScriptObject.CAS_MAP, 1, new Object[]{map, newMap}), 1, new Class[]{this.type});
        MethodHandle methodHandleGuardWithTest = Lookup.f31MH.guardWithTest(Lookup.f31MH.asType(methodHandleDropArguments, methodHandleDropArguments.type().changeParameterType(0, Object.class)), setter, methodHandleAsType);
        MethodHandle methodHandleInsertArguments2 = Lookup.f31MH.insertArguments(ScriptObject.EXTENSION_CHECK, 1, new Object[]{Boolean.valueOf(zIsStrict), nameToken});
        return new SetMethod(this, Lookup.f31MH.asType(Lookup.f31MH.guardWithTest(Lookup.f31MH.dropArguments(Lookup.f31MH.asType(methodHandleInsertArguments2, methodHandleInsertArguments2.type().changeParameterType(0, Object.class)), 1, new Class[]{this.type}), methodHandleGuardWithTest, Lookup.f31MH.dropArguments(JSType.VOID_RETURN.methodHandle(), 0, new Class[]{Object.class, this.type})), setter.type()), property);
    }

    private SetMethod createNewFieldSetter(SwitchPoint switchPoint) {
        return createNewSetter(new AccessorProperty(getName(), getFlags(this.sobj), this.sobj.getClass(), getMap().getFreeFieldSlot(), this.type), switchPoint);
    }

    private SetMethod createNewSpillPropertySetter(SwitchPoint switchPoint) {
        return createNewSetter(new SpillProperty(getName(), getFlags(this.sobj), getMap().getFreeSpillSlot(), this.type), switchPoint);
    }

    private PropertyMap getNewMap(Property property) {
        return getMap().addProperty(property);
    }

    private static int getFlags(ScriptObject scriptObject) {
        return scriptObject.useDualFields() ? 2048 : 0;
    }
}
