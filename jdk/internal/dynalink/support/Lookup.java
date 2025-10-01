package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/Lookup.class */
public class Lookup {
    private final MethodHandles.Lookup lookup;
    public static final Lookup PUBLIC = new Lookup(MethodHandles.publicLookup());

    public Lookup(MethodHandles.Lookup lookup) {
        this.lookup = lookup;
    }

    public MethodHandle unreflect(Method method) {
        return unreflect(this.lookup, method);
    }

    public static MethodHandle unreflect(MethodHandles.Lookup lookup, Method method) {
        try {
            return lookup.unreflect(method);
        } catch (IllegalAccessException e) {
            IllegalAccessError illegalAccessError = new IllegalAccessError("Failed to unreflect method " + method);
            illegalAccessError.initCause(e);
            throw illegalAccessError;
        }
    }

    public MethodHandle unreflectGetter(Field field) {
        try {
            return this.lookup.unreflectGetter(field);
        } catch (IllegalAccessException e) {
            IllegalAccessError illegalAccessError = new IllegalAccessError("Failed to unreflect getter for field " + field);
            illegalAccessError.initCause(e);
            throw illegalAccessError;
        }
    }

    public MethodHandle findGetter(Class cls, String str, Class cls2) {
        try {
            return this.lookup.findGetter(cls, str, cls2);
        } catch (IllegalAccessException e) {
            IllegalAccessError illegalAccessError = new IllegalAccessError("Failed to access getter for field " + cls.getName() + "." + str + " of type " + cls2.getName());
            illegalAccessError.initCause(e);
            throw illegalAccessError;
        } catch (NoSuchFieldException e2) {
            NoSuchFieldError noSuchFieldError = new NoSuchFieldError("Failed to find getter for field " + cls.getName() + "." + str + " of type " + cls2.getName());
            noSuchFieldError.initCause(e2);
            throw noSuchFieldError;
        }
    }

    public MethodHandle unreflectSetter(Field field) {
        try {
            return this.lookup.unreflectSetter(field);
        } catch (IllegalAccessException e) {
            IllegalAccessError illegalAccessError = new IllegalAccessError("Failed to unreflect setter for field " + field);
            illegalAccessError.initCause(e);
            throw illegalAccessError;
        }
    }

    public MethodHandle unreflectConstructor(Constructor constructor) {
        return unreflectConstructor(this.lookup, constructor);
    }

    public static MethodHandle unreflectConstructor(MethodHandles.Lookup lookup, Constructor constructor) {
        try {
            return lookup.unreflectConstructor(constructor);
        } catch (IllegalAccessException e) {
            IllegalAccessError illegalAccessError = new IllegalAccessError("Failed to unreflect constructor " + constructor);
            illegalAccessError.initCause(e);
            throw illegalAccessError;
        }
    }

    public MethodHandle findSpecial(Class cls, String str, MethodType methodType) {
        try {
            return this.lookup.findSpecial(cls, str, methodType, cls);
        } catch (IllegalAccessException e) {
            IllegalAccessError illegalAccessError = new IllegalAccessError("Failed to access special method " + methodDescription(cls, str, methodType));
            illegalAccessError.initCause(e);
            throw illegalAccessError;
        } catch (NoSuchMethodException e2) {
            NoSuchMethodError noSuchMethodError = new NoSuchMethodError("Failed to find special method " + methodDescription(cls, str, methodType));
            noSuchMethodError.initCause(e2);
            throw noSuchMethodError;
        }
    }

    private static String methodDescription(Class cls, String str, MethodType methodType) {
        return cls.getName() + "#" + str + methodType;
    }

    public MethodHandle findStatic(Class cls, String str, MethodType methodType) {
        try {
            return this.lookup.findStatic(cls, str, methodType);
        } catch (IllegalAccessException e) {
            IllegalAccessError illegalAccessError = new IllegalAccessError("Failed to access static method " + methodDescription(cls, str, methodType));
            illegalAccessError.initCause(e);
            throw illegalAccessError;
        } catch (NoSuchMethodException e2) {
            NoSuchMethodError noSuchMethodError = new NoSuchMethodError("Failed to find static method " + methodDescription(cls, str, methodType));
            noSuchMethodError.initCause(e2);
            throw noSuchMethodError;
        }
    }

    public MethodHandle findVirtual(Class cls, String str, MethodType methodType) {
        try {
            return this.lookup.findVirtual(cls, str, methodType);
        } catch (IllegalAccessException e) {
            IllegalAccessError illegalAccessError = new IllegalAccessError("Failed to access virtual method " + methodDescription(cls, str, methodType));
            illegalAccessError.initCause(e);
            throw illegalAccessError;
        } catch (NoSuchMethodException e2) {
            NoSuchMethodError noSuchMethodError = new NoSuchMethodError("Failed to find virtual method " + methodDescription(cls, str, methodType));
            noSuchMethodError.initCause(e2);
            throw noSuchMethodError;
        }
    }

    public static MethodHandle findOwnSpecial(MethodHandles.Lookup lookup, String str, Class cls, Class[] clsArr) {
        return new Lookup(lookup).findOwnSpecial(str, cls, clsArr);
    }

    public MethodHandle findOwnSpecial(String str, Class cls, Class[] clsArr) {
        return findSpecial(this.lookup.lookupClass(), str, MethodType.methodType((Class<?>) cls, (Class<?>[]) clsArr));
    }

    public static MethodHandle findOwnStatic(MethodHandles.Lookup lookup, String str, Class cls, Class[] clsArr) {
        return new Lookup(lookup).findOwnStatic(str, cls, clsArr);
    }

    public MethodHandle findOwnStatic(String str, Class cls, Class[] clsArr) {
        return findStatic(this.lookup.lookupClass(), str, MethodType.methodType((Class<?>) cls, (Class<?>[]) clsArr));
    }
}
