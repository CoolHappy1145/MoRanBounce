package org.spongepowered.asm.launch.platform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/CommandLineOptions.class */
public final class CommandLineOptions {
    private List configs = new ArrayList();

    private CommandLineOptions() {
    }

    public List getConfigs() {
        return Collections.unmodifiableList(this.configs);
    }

    private void parseArgs(List list) {
        boolean z = false;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (z) {
                this.configs.add(str);
            }
            z = "--mixin".equals(str) || "--mixin.config".equals(str);
        }
    }

    public static CommandLineOptions defaultArgs() {
        return ofArgs(null);
    }

    public static CommandLineOptions ofArgs(List list) {
        String property;
        CommandLineOptions commandLineOptions = new CommandLineOptions();
        if (list == null && (property = System.getProperty("sun.java.command")) != null) {
            list = Arrays.asList(property.split(" "));
        }
        if (list != null) {
            commandLineOptions.parseArgs(list);
        }
        return commandLineOptions;
    }

    /* renamed from: of */
    public static CommandLineOptions m50of(List list) {
        CommandLineOptions commandLineOptions = new CommandLineOptions();
        commandLineOptions.configs.addAll(list);
        return commandLineOptions;
    }
}
