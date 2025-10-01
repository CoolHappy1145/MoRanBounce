package org.spongepowered.asm.mixin.injection.code;

import com.google.common.base.Strings;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/code/MethodSlice.class */
public final class MethodSlice {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final ISliceContext owner;

    /* renamed from: id */
    private final String f217id;
    private final InjectionPoint from;

    /* renamed from: to */
    private final InjectionPoint f218to;
    private final String name;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/code/MethodSlice$InsnListSlice.class */
    static final class InsnListSlice extends ReadOnlyInsnList {
        private final int start;
        private final int end;

        /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/code/MethodSlice$InsnListSlice$SliceIterator.class */
        static class SliceIterator implements ListIterator {
            private final ListIterator iter;
            private int start;
            private int end;
            private int index;

            @Override // java.util.ListIterator
            public void add(Object obj) {
                add((AbstractInsnNode) obj);
            }

            @Override // java.util.ListIterator
            public void set(Object obj) {
                set((AbstractInsnNode) obj);
            }

            @Override // java.util.ListIterator
            public Object previous() {
                return previous();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public Object next() {
                return next();
            }

            public SliceIterator(ListIterator listIterator, int i, int i2, int i3) {
                this.iter = listIterator;
                this.start = i;
                this.end = i2;
                this.index = i3;
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public boolean hasNext() {
                return this.index <= this.end && this.iter.hasNext();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public AbstractInsnNode next() {
                if (this.index > this.end) {
                    throw new NoSuchElementException();
                }
                this.index++;
                return (AbstractInsnNode) this.iter.next();
            }

            @Override // java.util.ListIterator
            public boolean hasPrevious() {
                return this.index > this.start;
            }

            @Override // java.util.ListIterator
            public AbstractInsnNode previous() {
                if (this.index <= this.start) {
                    throw new NoSuchElementException();
                }
                this.index--;
                return (AbstractInsnNode) this.iter.previous();
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.index - this.start;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return (this.index - this.start) - 1;
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Cannot remove insn from slice");
            }

            public void set(AbstractInsnNode abstractInsnNode) {
                throw new UnsupportedOperationException("Cannot set insn using slice");
            }

            public void add(AbstractInsnNode abstractInsnNode) {
                throw new UnsupportedOperationException("Cannot add insn using slice");
            }
        }

        protected InsnListSlice(InsnList insnList, int i, int i2) {
            super(insnList);
            this.start = i;
            this.end = i2;
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList
        public ListIterator iterator() {
            return iterator(0);
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList
        public ListIterator iterator(int i) {
            return new SliceIterator(super.iterator(this.start + i), this.start, this.end, this.start + i);
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList
        public AbstractInsnNode[] toArray() {
            AbstractInsnNode[] array = super.toArray();
            AbstractInsnNode[] abstractInsnNodeArr = new AbstractInsnNode[size()];
            System.arraycopy(array, this.start, abstractInsnNodeArr, 0, abstractInsnNodeArr.length);
            return abstractInsnNodeArr;
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList
        public int size() {
            return (this.end - this.start) + 1;
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList
        public AbstractInsnNode getFirst() {
            return super.get(this.start);
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList
        public AbstractInsnNode getLast() {
            return super.get(this.end);
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList
        public AbstractInsnNode get(int i) {
            return super.get(this.start + i);
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList
        public boolean contains(AbstractInsnNode abstractInsnNode) {
            for (AbstractInsnNode abstractInsnNode2 : toArray()) {
                if (abstractInsnNode2 == abstractInsnNode) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.spongepowered.asm.mixin.injection.code.ReadOnlyInsnList
        public int indexOf(AbstractInsnNode abstractInsnNode) {
            int iIndexOf = super.indexOf(abstractInsnNode);
            if (iIndexOf < this.start || iIndexOf > this.end) {
                return -1;
            }
            return iIndexOf - this.start;
        }

        public int realIndexOf(AbstractInsnNode abstractInsnNode) {
            return super.indexOf(abstractInsnNode);
        }
    }

    private MethodSlice(ISliceContext iSliceContext, String str, InjectionPoint injectionPoint, InjectionPoint injectionPoint2) {
        if (injectionPoint == null && injectionPoint2 == null) {
            throw new InvalidSliceException(iSliceContext, String.format("%s is redundant. No 'from' or 'to' value specified", this));
        }
        this.owner = iSliceContext;
        this.f217id = Strings.nullToEmpty(str);
        this.from = injectionPoint;
        this.f218to = injectionPoint2;
        this.name = getSliceName(str);
    }

    public String getId() {
        return this.f217id;
    }

    public ReadOnlyInsnList getSlice(MethodNode methodNode) {
        int size = methodNode.instructions.size() - 1;
        int iFind = find(methodNode, this.from, 0, 0, this.name + "(from)");
        int iFind2 = find(methodNode, this.f218to, size, iFind, this.name + "(to)");
        if (iFind > iFind2) {
            throw new InvalidSliceException(this.owner, String.format("%s is negative size. Range(%d -> %d)", describe(), Integer.valueOf(iFind), Integer.valueOf(iFind2)));
        }
        if (iFind < 0 || iFind2 < 0 || iFind > size || iFind2 > size) {
            throw new InjectionError("Unexpected critical error in " + this + ": out of bounds start=" + iFind + " end=" + iFind2 + " lim=" + size);
        }
        if (iFind == 0 && iFind2 == size) {
            return new ReadOnlyInsnList(methodNode.instructions);
        }
        return new InsnListSlice(methodNode.instructions, iFind, iFind2);
    }

    private int find(MethodNode methodNode, InjectionPoint injectionPoint, int i, int i2, String str) {
        if (injectionPoint == null) {
            return i;
        }
        LinkedList linkedList = new LinkedList();
        boolean zFind = injectionPoint.find(methodNode.desc, new ReadOnlyInsnList(methodNode.instructions), linkedList);
        InjectionPoint.Selector selector = injectionPoint.getSelector();
        if (linkedList.size() != 1 && selector == InjectionPoint.Selector.ONE) {
            throw new InvalidSliceException(this.owner, String.format("%s requires 1 result but found %d", describe(str), Integer.valueOf(linkedList.size())));
        }
        if (zFind) {
            return methodNode.instructions.indexOf(selector == InjectionPoint.Selector.FIRST ? (AbstractInsnNode) linkedList.getFirst() : (AbstractInsnNode) linkedList.getLast());
        }
        if (this.owner.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            logger.warn("{} did not match any instructions", new Object[]{describe(str)});
        }
        return i2;
    }

    public String toString() {
        return describe();
    }

    private String describe() {
        return describe(this.name);
    }

    private String describe(String str) {
        return describeSlice(str, this.owner);
    }

    private static String describeSlice(String str, ISliceContext iSliceContext) {
        String simpleName = Bytecode.getSimpleName(iSliceContext.getAnnotation());
        MethodNode method = iSliceContext.getMethod();
        return String.format("%s->%s(%s)::%s%s", iSliceContext.getContext(), simpleName, str, method.name, method.desc);
    }

    private static String getSliceName(String str) {
        return String.format("@Slice[%s]", Strings.nullToEmpty(str));
    }

    public static MethodSlice parse(ISliceContext iSliceContext, Slice slice) {
        String strM65id = slice.m65id();
        InterfaceC0563At interfaceC0563AtFrom = slice.from();
        InterfaceC0563At interfaceC0563AtM66to = slice.m66to();
        return new MethodSlice(iSliceContext, strM65id, interfaceC0563AtFrom != null ? InjectionPoint.parse(iSliceContext, interfaceC0563AtFrom) : null, interfaceC0563AtM66to != null ? InjectionPoint.parse(iSliceContext, interfaceC0563AtM66to) : null);
    }

    public static MethodSlice parse(ISliceContext iSliceContext, AnnotationNode annotationNode) {
        String str = (String) Annotations.getValue(annotationNode, "id");
        AnnotationNode annotationNode2 = (AnnotationNode) Annotations.getValue(annotationNode, "from");
        AnnotationNode annotationNode3 = (AnnotationNode) Annotations.getValue(annotationNode, "to");
        return new MethodSlice(iSliceContext, str, annotationNode2 != null ? InjectionPoint.parse(iSliceContext, annotationNode2) : null, annotationNode3 != null ? InjectionPoint.parse(iSliceContext, annotationNode3) : null);
    }
}
