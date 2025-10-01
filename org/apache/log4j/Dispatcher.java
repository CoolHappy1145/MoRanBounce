package org.apache.log4j;

import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.helpers.BoundedFIFO;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/Dispatcher.class */
class Dispatcher extends Thread {

    /* renamed from: bf */
    private BoundedFIFO f185bf;
    private AppenderAttachableImpl aai;
    private boolean interrupted = false;
    AsyncAppender container;

    Dispatcher(BoundedFIFO boundedFIFO, AsyncAppender asyncAppender) {
        this.f185bf = boundedFIFO;
        this.container = asyncAppender;
        this.aai = asyncAppender.aai;
        setDaemon(true);
        setPriority(1);
        setName(new StringBuffer().append("Dispatcher-").append(getName()).toString());
    }

    void close() {
        synchronized (this.f185bf) {
            this.interrupted = true;
            if (this.f185bf.length() == 0) {
                this.f185bf.notify();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0052, code lost:
    
        r0 = r3.container.aai;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005b, code lost:
    
        monitor-enter(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0060, code lost:
    
        if (r3.aai == null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0064, code lost:
    
        if (r0 == null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0067, code lost:
    
        r3.aai.appendLoopOnAppenders(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0071, code lost:
    
        monitor-exit(r0);
     */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        while (true) {
            synchronized (this.f185bf) {
                if (this.f185bf.length() == 0) {
                    if (this.interrupted) {
                        break;
                    } else {
                        try {
                            this.f185bf.wait();
                        } catch (InterruptedException unused) {
                        }
                    }
                    this.aai.removeAllAppenders();
                }
                LoggingEvent loggingEvent = this.f185bf.get();
                if (this.f185bf.wasFull()) {
                    this.f185bf.notify();
                }
            }
            this.aai.removeAllAppenders();
        }
        this.aai.removeAllAppenders();
    }
}
