package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardedTypeConversion;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.MethodTypeConversionStrategy;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/TypeConverterFactory.class */
public class TypeConverterFactory {
    private final GuardingTypeConverterFactory[] factories;
    private final ConversionComparator[] comparators;
    private final MethodTypeConversionStrategy autoConversionStrategy;
    private final ClassValue converterMap = new C00191(this);
    private final ClassValue converterIdentityMap = new C00202(this);
    private final ClassValue canConvert = new C00213(this);
    static final MethodHandle IDENTITY_CONVERSION = MethodHandles.identity(Object.class);

    /* renamed from: jdk.internal.dynalink.support.TypeConverterFactory$1 */
    /* loaded from: L-out.jar:jdk/internal/dynalink/support/TypeConverterFactory$1.class */
    class C00191 extends ClassValue {
        final TypeConverterFactory this$0;

        C00191(TypeConverterFactory typeConverterFactory) {
            this.this$0 = typeConverterFactory;
        }

        @Override // java.lang.ClassValue
        protected Object computeValue(Class cls) {
            return computeValue(cls);
        }

        @Override // java.lang.ClassValue
        protected ClassMap computeValue(Class cls) {
            return new ClassMap(this, TypeConverterFactory.getClassLoader(cls), cls) { // from class: jdk.internal.dynalink.support.TypeConverterFactory.1.1
                final Class val$sourceType;
                final C00191 this$1;

                {
                    this.this$1 = this;
                    this.val$sourceType = cls;
                }

                @Override // jdk.internal.dynalink.support.ClassMap
                protected Object computeValue(Class cls2) {
                    return computeValue(cls2);
                }

                @Override // jdk.internal.dynalink.support.ClassMap
                protected MethodHandle computeValue(Class cls2) {
                    try {
                        return this.this$1.this$0.createConverter(this.val$sourceType, cls2);
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e2) {
                        throw new RuntimeException(e2);
                    }
                }
            };
        }
    }

    /* renamed from: jdk.internal.dynalink.support.TypeConverterFactory$2 */
    /* loaded from: L-out.jar:jdk/internal/dynalink/support/TypeConverterFactory$2.class */
    class C00202 extends ClassValue {
        final TypeConverterFactory this$0;

        C00202(TypeConverterFactory typeConverterFactory) {
            this.this$0 = typeConverterFactory;
        }

        @Override // java.lang.ClassValue
        protected Object computeValue(Class cls) {
            return computeValue(cls);
        }

        @Override // java.lang.ClassValue
        protected ClassMap computeValue(Class cls) {
            return new ClassMap(this, TypeConverterFactory.getClassLoader(cls), cls) { // from class: jdk.internal.dynalink.support.TypeConverterFactory.2.1
                final Class val$sourceType;
                final C00202 this$1;

                {
                    this.this$1 = this;
                    this.val$sourceType = cls;
                }

                @Override // jdk.internal.dynalink.support.ClassMap
                protected Object computeValue(Class cls2) {
                    return computeValue(cls2);
                }

                @Override // jdk.internal.dynalink.support.ClassMap
                protected MethodHandle computeValue(Class cls2) {
                    MethodHandle cacheableTypeConverter;
                    if (!TypeConverterFactory.canAutoConvert(this.val$sourceType, cls2) && (cacheableTypeConverter = this.this$1.this$0.getCacheableTypeConverter(this.val$sourceType, cls2)) != TypeConverterFactory.IDENTITY_CONVERSION) {
                        return cacheableTypeConverter;
                    }
                    return TypeConverterFactory.IDENTITY_CONVERSION.asType(MethodType.methodType((Class<?>) cls2, (Class<?>) this.val$sourceType));
                }
            };
        }
    }

    /* renamed from: jdk.internal.dynalink.support.TypeConverterFactory$3 */
    /* loaded from: L-out.jar:jdk/internal/dynalink/support/TypeConverterFactory$3.class */
    class C00213 extends ClassValue {
        final TypeConverterFactory this$0;

        C00213(TypeConverterFactory typeConverterFactory) {
            this.this$0 = typeConverterFactory;
        }

        @Override // java.lang.ClassValue
        protected Object computeValue(Class cls) {
            return computeValue(cls);
        }

        @Override // java.lang.ClassValue
        protected ClassMap computeValue(Class cls) {
            return new ClassMap(this, TypeConverterFactory.getClassLoader(cls), cls) { // from class: jdk.internal.dynalink.support.TypeConverterFactory.3.1
                final Class val$sourceType;
                final C00213 this$1;

                {
                    this.this$1 = this;
                    this.val$sourceType = cls;
                }

                @Override // jdk.internal.dynalink.support.ClassMap
                protected Object computeValue(Class cls2) {
                    return computeValue(cls2);
                }

                @Override // jdk.internal.dynalink.support.ClassMap
                protected Boolean computeValue(Class cls2) {
                    try {
                        return Boolean.valueOf(this.this$1.this$0.getTypeConverterNull(this.val$sourceType, cls2) != null);
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e2) {
                        throw new RuntimeException(e2);
                    }
                }
            };
        }
    }

    private static final ClassLoader getClassLoader(Class cls) {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction(cls) { // from class: jdk.internal.dynalink.support.TypeConverterFactory.4
            final Class val$clazz;

            {
                this.val$clazz = cls;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public ClassLoader run() {
                return this.val$clazz.getClassLoader();
            }
        }, ClassLoaderGetterContextProvider.GET_CLASS_LOADER_CONTEXT);
    }

    public TypeConverterFactory(Iterable iterable, MethodTypeConversionStrategy methodTypeConversionStrategy) {
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            GuardingTypeConverterFactory guardingTypeConverterFactory = (GuardingTypeConverterFactory) it.next();
            linkedList.add(guardingTypeConverterFactory);
            if (guardingTypeConverterFactory instanceof ConversionComparator) {
                linkedList2.add((ConversionComparator) guardingTypeConverterFactory);
            }
        }
        this.factories = (GuardingTypeConverterFactory[]) linkedList.toArray(new GuardingTypeConverterFactory[linkedList.size()]);
        this.comparators = (ConversionComparator[]) linkedList2.toArray(new ConversionComparator[linkedList2.size()]);
        this.autoConversionStrategy = methodTypeConversionStrategy;
    }

    public MethodHandle asType(MethodHandle methodHandle, MethodType methodType) {
        MethodHandle typeConverterNull;
        MethodHandle methodHandleApplyConverters = methodHandle;
        MethodType methodTypeType = methodHandleApplyConverters.type();
        int iParameterCount = methodTypeType.parameterCount();
        if (iParameterCount != methodType.parameterCount()) {
            throw new WrongMethodTypeException("Parameter counts differ: " + methodHandle.type() + " vs. " + methodType);
        }
        int i = 0;
        LinkedList linkedList = new LinkedList();
        for (int i2 = 0; i2 < iParameterCount; i2++) {
            Class<?> clsParameterType = methodType.parameterType(i2);
            Class<?> clsParameterType2 = methodTypeType.parameterType(i2);
            if (canAutoConvert(clsParameterType, clsParameterType2)) {
                methodHandleApplyConverters = applyConverters(methodHandleApplyConverters, i, linkedList);
            } else {
                MethodHandle typeConverterNull2 = getTypeConverterNull(clsParameterType, clsParameterType2);
                if (typeConverterNull2 != null) {
                    if (linkedList.isEmpty()) {
                        i = i2;
                    }
                    linkedList.add(typeConverterNull2);
                } else {
                    methodHandleApplyConverters = applyConverters(methodHandleApplyConverters, i, linkedList);
                }
            }
        }
        MethodHandle methodHandleApplyConverters2 = applyConverters(methodHandleApplyConverters, i, linkedList);
        Class<?> clsReturnType = methodType.returnType();
        Class<?> clsReturnType2 = methodTypeType.returnType();
        if (clsReturnType != Void.TYPE && clsReturnType2 != Void.TYPE && !canAutoConvert(clsReturnType2, clsReturnType) && (typeConverterNull = getTypeConverterNull(clsReturnType2, clsReturnType)) != null) {
            methodHandleApplyConverters2 = MethodHandles.filterReturnValue(methodHandleApplyConverters2, typeConverterNull);
        }
        return (this.autoConversionStrategy != null ? this.autoConversionStrategy.asType(methodHandleApplyConverters2, methodType) : methodHandleApplyConverters2).asType(methodType);
    }

    private static MethodHandle applyConverters(MethodHandle methodHandle, int i, List list) {
        if (list.isEmpty()) {
            return methodHandle;
        }
        MethodHandle methodHandleFilterArguments = MethodHandles.filterArguments(methodHandle, i, (MethodHandle[]) list.toArray(new MethodHandle[list.size()]));
        list.clear();
        return methodHandleFilterArguments;
    }

    public boolean canConvert(Class cls, Class cls2) {
        return canAutoConvert(cls, cls2) || ((Boolean) ((ClassMap) this.canConvert.get(cls)).get(cls2)).booleanValue();
    }

    public ConversionComparator.Comparison compareConversion(Class cls, Class cls2, Class cls3) {
        for (ConversionComparator conversionComparator : this.comparators) {
            ConversionComparator.Comparison comparisonCompareConversion = conversionComparator.compareConversion(cls, cls2, cls3);
            if (comparisonCompareConversion != ConversionComparator.Comparison.INDETERMINATE) {
                return comparisonCompareConversion;
            }
        }
        if (TypeUtilities.isMethodInvocationConvertible(cls, cls2)) {
            if (!TypeUtilities.isMethodInvocationConvertible(cls, cls3)) {
                return ConversionComparator.Comparison.TYPE_1_BETTER;
            }
        } else if (TypeUtilities.isMethodInvocationConvertible(cls, cls3)) {
            return ConversionComparator.Comparison.TYPE_2_BETTER;
        }
        return ConversionComparator.Comparison.INDETERMINATE;
    }

    static boolean canAutoConvert(Class cls, Class cls2) {
        return TypeUtilities.isMethodInvocationConvertible(cls, cls2);
    }

    MethodHandle getCacheableTypeConverterNull(Class cls, Class cls2) {
        MethodHandle cacheableTypeConverter = getCacheableTypeConverter(cls, cls2);
        if (cacheableTypeConverter == IDENTITY_CONVERSION) {
            return null;
        }
        return cacheableTypeConverter;
    }

    MethodHandle getTypeConverterNull(Class cls, Class cls2) {
        try {
            return getCacheableTypeConverterNull(cls, cls2);
        } catch (NotCacheableConverter e) {
            return e.converter;
        }
    }

    MethodHandle getCacheableTypeConverter(Class cls, Class cls2) {
        return (MethodHandle) ((ClassMap) this.converterMap.get(cls)).get(cls2);
    }

    public MethodHandle getTypeConverter(Class cls, Class cls2) {
        try {
            return (MethodHandle) ((ClassMap) this.converterIdentityMap.get(cls)).get(cls2);
        } catch (NotCacheableConverter e) {
            return e.converter;
        }
    }

    MethodHandle createConverter(Class cls, Class cls2) {
        MethodType methodType = MethodType.methodType((Class<?>) cls2, (Class<?>) cls);
        MethodHandle methodHandleAsType = IDENTITY_CONVERSION.asType(methodType);
        MethodHandle methodHandleCompose = methodHandleAsType;
        boolean z = true;
        int length = this.factories.length;
        while (true) {
            int i = length;
            length--;
            if (i <= 0) {
                break;
            }
            GuardedTypeConversion guardedTypeConversionConvertToType = this.factories[length].convertToType(cls, cls2);
            if (guardedTypeConversionConvertToType != null) {
                z = z && guardedTypeConversionConvertToType.isCacheable();
                GuardedInvocation conversionInvocation = guardedTypeConversionConvertToType.getConversionInvocation();
                conversionInvocation.assertType(methodType);
                methodHandleCompose = conversionInvocation.compose(methodHandleCompose);
            }
        }
        if (methodHandleCompose == methodHandleAsType) {
            return IDENTITY_CONVERSION;
        }
        if (z) {
            return methodHandleCompose;
        }
        throw new NotCacheableConverter(methodHandleCompose);
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/support/TypeConverterFactory$NotCacheableConverter.class */
    private static class NotCacheableConverter extends RuntimeException {
        final MethodHandle converter;

        NotCacheableConverter(MethodHandle methodHandle) {
            super("", null, false, false);
            this.converter = methodHandle;
        }
    }
}
