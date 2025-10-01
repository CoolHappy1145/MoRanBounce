package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.throwables.CompanionPluginError;
import org.spongepowered.asm.service.IMixinService;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/PluginHandle.class */
class PluginHandle {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final MixinConfig parent;
    private final IMixinConfigPlugin plugin;
    private CompatibilityMode mode = CompatibilityMode.NORMAL;
    private Method mdPreApply;
    private Method mdPostApply;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/PluginHandle$CompatibilityMode.class */
    enum CompatibilityMode {
        NORMAL,
        COMPATIBLE,
        FAILED
    }

    PluginHandle(MixinConfig mixinConfig, IMixinService iMixinService, String str) {
        IMixinConfigPlugin iMixinConfigPlugin = null;
        if (!Strings.isNullOrEmpty(str)) {
            try {
                iMixinConfigPlugin = (IMixinConfigPlugin) iMixinService.getClassProvider().findClass(str, true).newInstance();
            } catch (Throwable th) {
                logger.error("Error loading companion plugin class [{}] for mixin config [{}]. The plugin may be out of date: {}:{}", new Object[]{str, mixinConfig, th.getClass().getSimpleName(), th.getMessage(), th});
                iMixinConfigPlugin = null;
            }
        }
        this.parent = mixinConfig;
        this.plugin = iMixinConfigPlugin;
    }

    IMixinConfigPlugin get() {
        return this.plugin;
    }

    boolean isAvailable() {
        return this.plugin != null;
    }

    void onLoad(String str) {
        if (this.plugin != null) {
            this.plugin.onLoad(str);
        }
    }

    String getRefMapperConfig() {
        if (this.plugin != null) {
            return this.plugin.getRefMapperConfig();
        }
        return null;
    }

    List getMixins() {
        if (this.plugin != null) {
            return this.plugin.getMixins();
        }
        return null;
    }

    boolean shouldApplyMixin(String str, String str2) {
        return this.plugin == null || this.plugin.shouldApplyMixin(str, str2);
    }

    public void preApply(String str, ClassNode classNode, String str2, MixinInfo mixinInfo) throws Exception {
        if (this.plugin == null) {
            return;
        }
        if (this.mode == CompatibilityMode.FAILED) {
            throw new IllegalStateException("Companion plugin failure for [" + this.parent + "] plugin [" + this.plugin.getClass() + "]");
        }
        if (this.mode == CompatibilityMode.COMPATIBLE) {
            try {
                applyLegacy(this.mdPreApply, str, classNode, str2, mixinInfo);
                return;
            } catch (Exception e) {
                this.mode = CompatibilityMode.FAILED;
                throw e;
            }
        }
        try {
            this.plugin.preApply(str, classNode, str2, mixinInfo);
        } catch (AbstractMethodError unused) {
            this.mode = CompatibilityMode.COMPATIBLE;
            initReflection();
            preApply(str, classNode, str2, mixinInfo);
        }
    }

    public void postApply(String str, ClassNode classNode, String str2, MixinInfo mixinInfo) throws Exception {
        if (this.plugin == null) {
            return;
        }
        if (this.mode == CompatibilityMode.FAILED) {
            throw new IllegalStateException("Companion plugin failure for [" + this.parent + "] plugin [" + this.plugin.getClass() + "]");
        }
        if (this.mode == CompatibilityMode.COMPATIBLE) {
            try {
                applyLegacy(this.mdPostApply, str, classNode, str2, mixinInfo);
                return;
            } catch (Exception e) {
                this.mode = CompatibilityMode.FAILED;
                throw e;
            }
        }
        try {
            this.plugin.postApply(str, classNode, str2, mixinInfo);
        } catch (AbstractMethodError unused) {
            this.mode = CompatibilityMode.COMPATIBLE;
            initReflection();
            postApply(str, classNode, str2, mixinInfo);
        }
    }

    private void initReflection() {
        if (this.mdPreApply != null) {
            return;
        }
        try {
            Class<?> cls = this.plugin.getClass();
            this.mdPreApply = cls.getMethod("preApply", String.class, org.spongepowered.asm.lib.tree.ClassNode.class, String.class, IMixinInfo.class);
            this.mdPostApply = cls.getMethod("postApply", String.class, org.spongepowered.asm.lib.tree.ClassNode.class, String.class, IMixinInfo.class);
        } catch (Throwable th) {
            logger.catching(th);
        }
    }

    private void applyLegacy(Method method, String str, ClassNode classNode, String str2, IMixinInfo iMixinInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            method.invoke(this.plugin, str, new org.spongepowered.asm.lib.tree.ClassNode(classNode), str2, iMixinInfo);
        } catch (IllegalAccessException e) {
            throw new CompanionPluginError(apiError("Fallback failed [" + e.getMessage() + "]"), e);
        } catch (IllegalArgumentException e2) {
            throw new CompanionPluginError(apiError("Fallback failed [" + e2.getMessage() + "]"), e2);
        } catch (LinkageError e3) {
            throw new CompanionPluginError(apiError("Accessing [" + e3.getMessage() + "]"), e3);
        } catch (InvocationTargetException e4) {
            Throwable cause = e4.getCause() != null ? e4.getCause() : e4;
            throw new CompanionPluginError(apiError("Fallback failed [" + cause.getMessage() + "]"), cause);
        }
    }

    private String apiError(String str) {
        return String.format("Companion plugin attempted to use a deprected API in [%s] plugin [%s]: %s", this.parent, this.plugin.getClass().getName(), str);
    }
}
