package com.thealtening.utilities;

import java.lang.reflect.Field;

/* loaded from: L-out.jar:com/thealtening/utilities/ReflectionUtility.class */
public class ReflectionUtility {
    private String className;
    private Class clazz;

    public ReflectionUtility(String str) {
        try {
            this.clazz = Class.forName(str);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setStaticField(String str, Object obj) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Field declaredField = this.clazz.getDeclaredField(str);
        declaredField.setAccessible(true);
        Field declaredField2 = Field.class.getDeclaredField("modifiers");
        declaredField2.setAccessible(true);
        declaredField2.setInt(declaredField, declaredField.getModifiers() & (-17));
        declaredField.set(null, obj);
    }
}
