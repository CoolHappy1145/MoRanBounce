package org.spongepowered.asm.mixin.transformer.debug;

import java.io.File;
import java.lang.Thread;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/debug/RuntimeDecompilerAsync.class */
public class RuntimeDecompilerAsync extends RuntimeDecompiler implements Runnable, Thread.UncaughtExceptionHandler {
    private final BlockingQueue queue;
    private final Thread thread;
    private boolean run;

    public RuntimeDecompilerAsync(File file) {
        super(file);
        this.queue = new LinkedBlockingQueue();
        this.run = true;
        this.thread = new Thread(this, "Decompiler thread");
        this.thread.setDaemon(true);
        this.thread.setPriority(1);
        this.thread.setUncaughtExceptionHandler(this);
        this.thread.start();
    }

    @Override // org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler, org.spongepowered.asm.mixin.transformer.ext.IDecompiler
    public void decompile(File file) {
        if (this.run) {
            this.queue.offer(file);
        } else {
            super.decompile(file);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (this.run) {
            try {
                super.decompile((File) this.queue.take());
            } catch (InterruptedException unused) {
                this.run = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        this.logger.error("Async decompiler encountered an error and will terminate. Further decompile requests will be handled synchronously. {} {}", new Object[]{th.getClass().getName(), th.getMessage()});
        flush();
    }

    private void flush() {
        this.run = false;
        while (true) {
            File file = (File) this.queue.poll();
            if (file != null) {
                decompile(file);
            } else {
                return;
            }
        }
    }
}
