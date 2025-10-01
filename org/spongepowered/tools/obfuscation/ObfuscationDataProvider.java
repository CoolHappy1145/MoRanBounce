package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/ObfuscationDataProvider.class */
public class ObfuscationDataProvider implements IObfuscationDataProvider {

    /* renamed from: ap */
    private final IMixinAnnotationProcessor f231ap;
    private final List environments;

    public ObfuscationDataProvider(IMixinAnnotationProcessor iMixinAnnotationProcessor, List list) {
        this.f231ap = iMixinAnnotationProcessor;
        this.environments = list;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfEntryRecursive(ITargetSelectorRemappable iTargetSelectorRemappable) {
        ITargetSelectorRemappable iTargetSelectorRemappableMove = iTargetSelectorRemappable;
        ObfuscationData obfClass = getObfClass(iTargetSelectorRemappableMove.getOwner());
        ObfuscationData obfEntry = getObfEntry(iTargetSelectorRemappableMove);
        while (obfEntry.isEmpty()) {
            try {
                TypeHandle typeHandle = this.f231ap.getTypeProvider().getTypeHandle(iTargetSelectorRemappableMove.getOwner());
                if (typeHandle == null) {
                    return obfEntry;
                }
                TypeHandle superclass = typeHandle.getSuperclass();
                obfEntry = getObfEntryUsing(iTargetSelectorRemappableMove, superclass);
                if (!obfEntry.isEmpty()) {
                    return applyParents(obfClass, obfEntry);
                }
                Iterator it = typeHandle.getInterfaces().iterator();
                while (it.hasNext()) {
                    obfEntry = getObfEntryUsing(iTargetSelectorRemappableMove, (TypeHandle) it.next());
                    if (!obfEntry.isEmpty()) {
                        return applyParents(obfClass, obfEntry);
                    }
                }
                if (superclass == null) {
                    break;
                }
                iTargetSelectorRemappableMove = iTargetSelectorRemappableMove.move(superclass.getName());
            } catch (Exception e) {
                e.printStackTrace();
                return getObfEntry(iTargetSelectorRemappable);
            }
        }
        return obfEntry;
    }

    private ObfuscationData getObfEntryUsing(ITargetSelectorRemappable iTargetSelectorRemappable, TypeHandle typeHandle) {
        return typeHandle == null ? new ObfuscationData() : getObfEntry(iTargetSelectorRemappable.move(typeHandle.getName()));
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfEntry(ITargetSelectorRemappable iTargetSelectorRemappable) {
        if (iTargetSelectorRemappable.isField()) {
            return getObfField(iTargetSelectorRemappable);
        }
        return getObfMethod(iTargetSelectorRemappable.asMethodMapping());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfEntry(IMapping iMapping) {
        if (iMapping != null) {
            if (iMapping.getType() == IMapping.Type.FIELD) {
                return getObfField((MappingField) iMapping);
            }
            if (iMapping.getType() == IMapping.Type.METHOD) {
                return getObfMethod((MappingMethod) iMapping);
            }
        }
        return new ObfuscationData();
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfMethodRecursive(ITargetSelectorRemappable iTargetSelectorRemappable) {
        return getObfEntryRecursive(iTargetSelectorRemappable);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfMethod(ITargetSelectorRemappable iTargetSelectorRemappable) {
        return getRemappedMethod(iTargetSelectorRemappable, iTargetSelectorRemappable.isConstructor());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getRemappedMethod(ITargetSelectorRemappable iTargetSelectorRemappable) {
        return getRemappedMethod(iTargetSelectorRemappable, true);
    }

    private ObfuscationData getRemappedMethod(ITargetSelectorRemappable iTargetSelectorRemappable, boolean z) {
        ObfuscationData obfuscationData = new ObfuscationData();
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MappingMethod obfMethod = obfuscationEnvironment.getObfMethod(iTargetSelectorRemappable);
            if (obfMethod != null) {
                obfuscationData.put(obfuscationEnvironment.getType(), obfMethod);
            }
        }
        if (!obfuscationData.isEmpty() || !z) {
            return obfuscationData;
        }
        return remapDescriptor(obfuscationData, iTargetSelectorRemappable);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfMethod(MappingMethod mappingMethod) {
        return getRemappedMethod(mappingMethod, mappingMethod.isConstructor());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getRemappedMethod(MappingMethod mappingMethod) {
        return getRemappedMethod(mappingMethod, true);
    }

    private ObfuscationData getRemappedMethod(MappingMethod mappingMethod, boolean z) {
        ObfuscationData obfuscationData = new ObfuscationData();
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MappingMethod obfMethod = obfuscationEnvironment.getObfMethod(mappingMethod);
            if (obfMethod != null) {
                obfuscationData.put(obfuscationEnvironment.getType(), obfMethod);
            }
        }
        if (!obfuscationData.isEmpty() || !z) {
            return obfuscationData;
        }
        return remapDescriptor(obfuscationData, new MemberInfo(mappingMethod));
    }

    public ObfuscationData remapDescriptor(ObfuscationData obfuscationData, ITargetSelectorRemappable iTargetSelectorRemappable) {
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            ITargetSelectorRemappable iTargetSelectorRemappableRemapDescriptor = obfuscationEnvironment.remapDescriptor(iTargetSelectorRemappable);
            if (iTargetSelectorRemappableRemapDescriptor != null) {
                obfuscationData.put(obfuscationEnvironment.getType(), iTargetSelectorRemappableRemapDescriptor.asMethodMapping());
            }
        }
        return obfuscationData;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfFieldRecursive(ITargetSelectorRemappable iTargetSelectorRemappable) {
        return getObfEntryRecursive(iTargetSelectorRemappable);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfField(ITargetSelectorRemappable iTargetSelectorRemappable) {
        return getObfField(iTargetSelectorRemappable.asFieldMapping());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfField(MappingField mappingField) {
        ObfuscationData obfuscationData = new ObfuscationData();
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MappingField obfField = obfuscationEnvironment.getObfField(mappingField);
            if (obfField != null) {
                if (obfField.getDesc() == null && mappingField.getDesc() != null) {
                    obfField = obfField.transform(obfuscationEnvironment.remapDescriptor(mappingField.getDesc()));
                }
                obfuscationData.put(obfuscationEnvironment.getType(), obfField);
            }
        }
        return obfuscationData;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfClass(TypeHandle typeHandle) {
        return getObfClass(typeHandle.getName());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider
    public ObfuscationData getObfClass(String str) {
        ObfuscationData obfuscationData = new ObfuscationData(str);
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            String obfClass = obfuscationEnvironment.getObfClass(str);
            if (obfClass != null) {
                obfuscationData.put(obfuscationEnvironment.getType(), obfClass);
            }
        }
        return obfuscationData;
    }

    private static ObfuscationData applyParents(ObfuscationData obfuscationData, ObfuscationData obfuscationData2) {
        Iterator it = obfuscationData2.iterator();
        while (it.hasNext()) {
            ObfuscationType obfuscationType = (ObfuscationType) it.next();
            obfuscationData2.put(obfuscationType, MemberInfo.fromMapping((IMapping) obfuscationData2.get(obfuscationType)).move((String) obfuscationData.get(obfuscationType)).asMapping());
        }
        return obfuscationData2;
    }
}
