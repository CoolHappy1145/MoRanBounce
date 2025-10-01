package kotlin.p002io;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\ufffd\ufffd\b\u0002\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0096\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\b"}, m27d2 = {"Lkotlin/io/LinesSequence;", "Lkotlin/sequences/Sequence;", "", "reader", "Ljava/io/BufferedReader;", "(Ljava/io/BufferedReader;)V", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/io/LinesSequence.class */
final class LinesSequence implements Sequence {
    private final BufferedReader reader;

    public LinesSequence(@NotNull BufferedReader reader) {
        Intrinsics.checkParameterIsNotNull(reader, "reader");
        this.reader = reader;
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0017\n\ufffd\ufffd\n\u0002\u0010(\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0004*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\u0006\u001a\u00020\u0004H\u0096\u0002J\t\u0010\u0007\u001a\u00020\u0002H\u0096\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0002X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\b"}, m27d2 = {"kotlin/io/LinesSequence$iterator$1", "", "", "done", "", "nextValue", "hasNext", "next", "kotlin-stdlib"})
    /* renamed from: kotlin.io.LinesSequence$iterator$1 */
    /* loaded from: L-out.jar:kotlin/io/LinesSequence$iterator$1.class */
    public static final class C03431 implements Iterator, KMappedMarker {
        private String nextValue;
        private boolean done;
        final LinesSequence this$0;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        @Override // java.util.Iterator
        public Object next() {
            return next();
        }

        C03431(LinesSequence linesSequence) {
            this.this$0 = linesSequence;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.nextValue == null && !this.done) {
                this.nextValue = this.this$0.reader.readLine();
                if (this.nextValue == null) {
                    this.done = true;
                }
            }
            return this.nextValue != null;
        }

        @Override // java.util.Iterator
        @NotNull
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String str = this.nextValue;
            this.nextValue = (String) null;
            if (str == null) {
                Intrinsics.throwNpe();
            }
            return str;
        }
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        return new C03431(this);
    }
}
