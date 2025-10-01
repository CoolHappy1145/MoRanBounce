package net.ccbluex.liquidbounce.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006J\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u000eR(\u0010\u0003\u001a\u001c\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/event/EventManager;", "", "()V", "registry", "Ljava/util/HashMap;", Constants.CLASS_DESC, "Lnet/ccbluex/liquidbounce/event/Event;", "", "Lnet/ccbluex/liquidbounce/event/EventHook;", "callEvent", "", "event", "registerListener", "listener", "Lnet/ccbluex/liquidbounce/event/Listenable;", "unregisterListener", "listenable", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/event/EventManager.class */
public final class EventManager {
    private final HashMap registry = new HashMap();

    public final void registerListener(@NotNull Listenable listener) {
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventTarget.class)) {
                Intrinsics.checkExpressionValueIsNotNull(method, "method");
                if (method.getParameterTypes().length != 1) {
                    continue;
                } else {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    Class<?> cls = method.getParameterTypes()[0];
                    if (cls == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<out net.ccbluex.liquidbounce.event.Event>");
                    }
                    EventTarget eventTarget = (EventTarget) method.getAnnotation(EventTarget.class);
                    Object orDefault = this.registry.getOrDefault(cls, new ArrayList());
                    Intrinsics.checkExpressionValueIsNotNull(orDefault, "registry.getOrDefault(eventClass, ArrayList())");
                    List list = (List) orDefault;
                    Intrinsics.checkExpressionValueIsNotNull(eventTarget, "eventTarget");
                    list.add(new EventHook(listener, method, eventTarget));
                    this.registry.put(cls, list);
                }
            }
        }
    }

    public final void unregisterListener(@NotNull Listenable listenable) {
        Intrinsics.checkParameterIsNotNull(listenable, "listenable");
        for (Map.Entry entry : this.registry.entrySet()) {
            Class cls = (Class) entry.getKey();
            List list = (List) entry.getValue();
            list.removeIf(new Predicate(listenable) { // from class: net.ccbluex.liquidbounce.event.EventManager.unregisterListener.1
                final Listenable $listenable;

                {
                    this.$listenable = listenable;
                }

                @Override // java.util.function.Predicate
                public boolean test(Object obj) {
                    return test((EventHook) obj);
                }

                public final boolean test(@NotNull EventHook it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    return Intrinsics.areEqual(it.getEventClass(), this.$listenable);
                }
            });
            this.registry.put(cls, list);
        }
    }

    public final void callEvent(@NotNull Event event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        List<EventHook> list = (List) this.registry.get(event.getClass());
        if (list != null) {
            Intrinsics.checkExpressionValueIsNotNull(list, "registry[event.javaClass] ?: return");
            for (EventHook eventHook : list) {
                try {
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                if (eventHook.getEventClass().handleEvents() || eventHook.isIgnoreCondition()) {
                    eventHook.getMethod().invoke(eventHook.getEventClass(), event);
                }
            }
        }
    }
}
