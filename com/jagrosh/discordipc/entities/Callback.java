package com.jagrosh.discordipc.entities;

import java.util.function.Consumer;

/* loaded from: L-out.jar:com/jagrosh/discordipc/entities/Callback.class */
public class Callback {
    private final Consumer success;
    private final Consumer failure;

    public Callback() {
        this((Consumer) null, (Consumer) null);
    }

    public Callback(Consumer consumer) {
        this(consumer, (Consumer) null);
    }

    public Callback(Consumer consumer, Consumer consumer2) {
        this.success = consumer;
        this.failure = consumer2;
    }

    @Deprecated
    public Callback(Runnable runnable, Consumer consumer) {
        this((v1) -> {
            lambda$new$0(r1, v1);
        }, consumer);
    }

    private static void lambda$new$0(Runnable runnable, Packet packet) {
        runnable.run();
    }

    @Deprecated
    public Callback(Runnable runnable) {
        this((v1) -> {
            lambda$new$1(r1, v1);
        }, (Consumer) null);
    }

    private static void lambda$new$1(Runnable runnable, Packet packet) {
        runnable.run();
    }

    public boolean isEmpty() {
        return this.success == null && this.failure == null;
    }

    public void succeed(Packet packet) {
        if (this.success != null) {
            this.success.accept(packet);
        }
    }

    public void fail(String str) {
        if (this.failure != null) {
            this.failure.accept(str);
        }
    }
}
