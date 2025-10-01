package org.spongepowered.asm.service;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.launch.platform.IMixinPlatformAgent;
import org.spongepowered.asm.launch.platform.IMixinPlatformServiceAgent;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.IConsumer;
import org.spongepowered.asm.util.ReEntranceLock;

/* loaded from: L-out.jar:org/spongepowered/asm/service/MixinServiceAbstract.class */
public abstract class MixinServiceAbstract implements IMixinService {
    protected static final String LAUNCH_PACKAGE = "org.spongepowered.asm.launch.";
    protected static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
    protected static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    protected final ReEntranceLock lock = new ReEntranceLock(1);
    private List serviceAgents;
    private String sideName;

    @Override // org.spongepowered.asm.service.IMixinService
    public MixinEnvironment.Phase getInitialPhase() {
        return MixinEnvironment.Phase.PREINIT;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public void init() {
        Iterator it = getServiceAgents().iterator();
        while (it.hasNext()) {
            ((IMixinPlatformServiceAgent) it.next()).init();
        }
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public ReEntranceLock getReEntranceLock() {
        return this.lock;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public Collection getMixinContainers() {
        ImmutableList.Builder builder = ImmutableList.builder();
        getContainersFromAgents(builder);
        return builder.build();
    }

    protected final void getContainersFromAgents(ImmutableList.Builder builder) {
        Iterator it = getServiceAgents().iterator();
        while (it.hasNext()) {
            Collection mixinContainers = ((IMixinPlatformServiceAgent) it.next()).getMixinContainers();
            if (mixinContainers != null) {
                builder.addAll(mixinContainers);
            }
        }
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public final String getSideName() {
        String sideName;
        if (this.sideName != null) {
            return this.sideName;
        }
        Iterator it = getServiceAgents().iterator();
        while (it.hasNext()) {
            try {
                sideName = ((IMixinPlatformServiceAgent) it.next()).getSideName();
            } catch (Exception e) {
                logger.catching(e);
            }
            if (sideName != null) {
                this.sideName = sideName;
                return sideName;
            }
            continue;
        }
        return Constants.SIDE_UNKNOWN;
    }

    private List getServiceAgents() {
        if (this.serviceAgents != null) {
            return this.serviceAgents;
        }
        this.serviceAgents = new ArrayList();
        Iterator it = getPlatformAgents().iterator();
        while (it.hasNext()) {
            try {
                IMixinPlatformAgent iMixinPlatformAgent = (IMixinPlatformAgent) getClassProvider().findClass((String) it.next(), false).newInstance();
                if (iMixinPlatformAgent instanceof IMixinPlatformServiceAgent) {
                    this.serviceAgents.add((IMixinPlatformServiceAgent) iMixinPlatformAgent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.serviceAgents;
    }

    @Deprecated
    public void wire(MixinEnvironment.Phase phase, IConsumer iConsumer) {
        Iterator it = getServiceAgents().iterator();
        while (it.hasNext()) {
            ((IMixinPlatformServiceAgent) it.next()).wire(phase, iConsumer);
        }
    }

    @Deprecated
    public void unwire() {
        Iterator it = getServiceAgents().iterator();
        while (it.hasNext()) {
            ((IMixinPlatformServiceAgent) it.next()).unwire();
        }
    }
}
