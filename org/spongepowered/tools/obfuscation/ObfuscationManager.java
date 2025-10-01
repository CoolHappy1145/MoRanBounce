package org.spongepowered.tools.obfuscation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.service.ObfuscationServices;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/ObfuscationManager.class */
public class ObfuscationManager implements IObfuscationManager {

    /* renamed from: ap */
    private final IMixinAnnotationProcessor f233ap;
    private final IObfuscationDataProvider obfs;
    private final IReferenceManager refs;
    private boolean initDone;
    private final List environments = new ArrayList();
    private final List consumers = new ArrayList();

    public ObfuscationManager(IMixinAnnotationProcessor iMixinAnnotationProcessor) {
        this.f233ap = iMixinAnnotationProcessor;
        this.obfs = new ObfuscationDataProvider(iMixinAnnotationProcessor, this.environments);
        this.refs = new ReferenceManager(iMixinAnnotationProcessor, this.environments);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public void init() {
        if (this.initDone) {
            return;
        }
        this.initDone = true;
        ObfuscationServices.getInstance().initProviders(this.f233ap);
        for (ObfuscationType obfuscationType : ObfuscationType.types()) {
            if (obfuscationType.isSupported()) {
                this.environments.add(obfuscationType.createEnvironment());
            }
        }
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public IObfuscationDataProvider getDataProvider() {
        return this.obfs;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public IReferenceManager getReferenceManager() {
        return this.refs;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public IMappingConsumer createMappingConsumer() {
        Mappings mappings = new Mappings();
        this.consumers.add(mappings);
        return mappings;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public List getEnvironments() {
        return this.environments;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public void writeMappings() {
        Iterator it = this.environments.iterator();
        while (it.hasNext()) {
            ((ObfuscationEnvironment) it.next()).writeMappings(this.consumers);
        }
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager
    public void writeReferences() {
        this.refs.write();
    }
}
