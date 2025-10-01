package jdk.nashorn.api.scripting;

import jdk.nashorn.internal.runtime.JSType;

/* loaded from: L-out.jar:jdk/nashorn/api/scripting/DefaultValueImpl.class */
class DefaultValueImpl {
    private static final String[] DEFAULT_VALUE_FNS_NUMBER = {"valueOf", "toString"};
    private static final String[] DEFAULT_VALUE_FNS_STRING = {"toString", "valueOf"};

    DefaultValueImpl() {
    }

    static Object getDefaultValue(JSObject jSObject, Class cls) {
        boolean z = cls == null || cls == Number.class;
        for (String str : z ? DEFAULT_VALUE_FNS_NUMBER : DEFAULT_VALUE_FNS_STRING) {
            Object member = jSObject.getMember(str);
            if (member instanceof JSObject) {
                JSObject jSObject2 = (JSObject) member;
                if (jSObject2.isFunction()) {
                    Object objCall = jSObject2.call(jSObject, new Object[0]);
                    if (JSType.isPrimitive(objCall)) {
                        return objCall;
                    }
                } else {
                    continue;
                }
            }
        }
        throw new UnsupportedOperationException(z ? "cannot.get.default.number" : "cannot.get.default.string");
    }
}
