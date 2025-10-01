package org.spongepowered.asm.launch.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.launch.platform.IMixinPlatformAgent;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.service.MixinService;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/MixinContainer.class */
public class MixinContainer {
    private static final List agentClasses = new ArrayList();
    private static final Logger logger;
    private final IContainerHandle handle;
    private final List agents = new ArrayList();

    static {
        GlobalProperties.put(GlobalProperties.Keys.AGENTS, agentClasses);
        Iterator it = MixinService.getService().getPlatformAgents().iterator();
        while (it.hasNext()) {
            agentClasses.add((String) it.next());
        }
        agentClasses.add("org.spongepowered.asm.launch.platform.MixinPlatformAgentDefault");
        logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    }

    public MixinContainer(MixinPlatformManager mixinPlatformManager, IContainerHandle iContainerHandle) throws ClassNotFoundException {
        String simpleName;
        IMixinPlatformAgent iMixinPlatformAgent;
        IMixinPlatformAgent.AcceptResult acceptResultAccept;
        this.handle = iContainerHandle;
        Iterator it = agentClasses.iterator();
        while (it.hasNext()) {
            try {
                Class<?> cls = Class.forName((String) it.next());
                simpleName = cls.getSimpleName();
                logger.debug("Instancing new {} for {}", new Object[]{simpleName, this.handle});
                iMixinPlatformAgent = (IMixinPlatformAgent) cls.newInstance();
                acceptResultAccept = iMixinPlatformAgent.accept(mixinPlatformManager, this.handle);
            } catch (InstantiationException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                throw new RuntimeException(cause);
            } catch (ReflectiveOperationException e2) {
                logger.catching(e2);
            }
            if (acceptResultAccept == IMixinPlatformAgent.AcceptResult.ACCEPTED) {
                this.agents.add(iMixinPlatformAgent);
            } else if (acceptResultAccept == IMixinPlatformAgent.AcceptResult.INVALID) {
                it.remove();
            }
            logger.debug("{} {} container {}", new Object[]{simpleName, acceptResultAccept.name().toLowerCase(), this.handle});
        }
    }

    public IContainerHandle getDescriptor() {
        return this.handle;
    }

    public Collection getPhaseProviders() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.agents.iterator();
        while (it.hasNext()) {
            String phaseProvider = ((IMixinPlatformAgent) it.next()).getPhaseProvider();
            if (phaseProvider != null) {
                arrayList.add(phaseProvider);
            }
        }
        return arrayList;
    }

    public void prepare() {
        for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
            logger.debug("Processing prepare() for {}", new Object[]{iMixinPlatformAgent});
            iMixinPlatformAgent.prepare();
        }
    }

    public void initPrimaryContainer() {
        for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
            logger.debug("Processing launch tasks for {}", new Object[]{iMixinPlatformAgent});
            iMixinPlatformAgent.initPrimaryContainer();
        }
    }

    public void inject() {
        for (IMixinPlatformAgent iMixinPlatformAgent : this.agents) {
            logger.debug("Processing inject() for {}", new Object[]{iMixinPlatformAgent});
            iMixinPlatformAgent.inject();
        }
    }
}
