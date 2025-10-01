package jdk.nashorn.internal.runtime.events;

import java.util.logging.Level;
import jdk.nashorn.internal.runtime.options.Options;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/events/RuntimeEvent.class */
public class RuntimeEvent {
    public static final int RUNTIME_EVENT_QUEUE_SIZE = Options.getIntProperty("nashorn.runtime.event.queue.size", 1024);
    private final Level level;
    private final Object value;

    public RuntimeEvent(Level level, Object obj) {
        this.level = level;
        this.value = obj;
    }

    public final Object getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[').append(this.level).append("] ").append(this.value == null ? Configurator.NULL : getValueClass().getSimpleName()).append(" value=").append(this.value);
        return sb.toString();
    }

    public final Class getValueClass() {
        return this.value.getClass();
    }
}
