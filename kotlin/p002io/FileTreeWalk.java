package kotlin.p002io;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdL\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0006\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\u001a\u001b\u001cB\u0019\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006B\u0089\u0001\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\b\u00128\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\u0002\u0010\u0015J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0017H\u0096\u0002J\u000e\u0010\u0013\u001a\u00020\ufffd\ufffd2\u0006\u0010\u0018\u001a\u00020\u0014J\u001a\u0010\u0007\u001a\u00020\ufffd\ufffd2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t0\bJ \u0010\f\u001a\u00020\ufffd\ufffd2\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000b0\rJ\u001a\u0010\n\u001a\u00020\ufffd\ufffd2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b0\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR@\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001c\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001d"}, m27d2 = {"Lkotlin/io/FileTreeWalk;", "Lkotlin/sequences/Sequence;", "Ljava/io/File;", "start", "direction", "Lkotlin/io/FileWalkDirection;", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;)V", "onEnter", "Lkotlin/Function1;", "", "onLeave", "", "onFail", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "f", "Ljava/io/IOException;", "e", "maxDepth", "", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;I)V", "iterator", "", "depth", "function", "DirectoryState", "FileTreeWalkIterator", "WalkState", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/io/FileTreeWalk.class */
public final class FileTreeWalk implements Sequence {
    private final File start;
    private final FileWalkDirection direction;
    private final Function1 onEnter;
    private final Function1 onLeave;
    private final Function2 onFail;
    private final int maxDepth;

    private FileTreeWalk(File file, FileWalkDirection fileWalkDirection, Function1 function1, Function1 function12, Function2 function2, int i) {
        this.start = file;
        this.direction = fileWalkDirection;
        this.onEnter = function1;
        this.onLeave = function12;
        this.onFail = function2;
        this.maxDepth = i;
    }

    FileTreeWalk(File file, FileWalkDirection fileWalkDirection, Function1 function1, Function1 function12, Function2 function2, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i2 & 2) != 0 ? FileWalkDirection.TOP_DOWN : fileWalkDirection, function1, function12, function2, (i2 & 32) != 0 ? Integer.MAX_VALUE : i);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FileTreeWalk(@NotNull File start, @NotNull FileWalkDirection direction) {
        this(start, direction, null, null, null, 0, 32, null);
        Intrinsics.checkParameterIsNotNull(start, "start");
        Intrinsics.checkParameterIsNotNull(direction, "direction");
    }

    public FileTreeWalk(File file, FileWalkDirection fileWalkDirection, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, (i & 2) != 0 ? FileWalkDirection.TOP_DOWN : fileWalkDirection);
    }

    @Override // kotlin.sequences.Sequence
    @NotNull
    public Iterator iterator() {
        return new FileTreeWalkIterator(this);
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\b\"\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H&R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, m27d2 = {"Lkotlin/io/FileTreeWalk$WalkState;", "", "root", "Ljava/io/File;", "(Ljava/io/File;)V", "getRoot", "()Ljava/io/File;", "step", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/io/FileTreeWalk$WalkState.class */
    private static abstract class WalkState {

        @NotNull
        private final File root;

        @Nullable
        public abstract File step();

        @NotNull
        public final File getRoot() {
            return this.root;
        }

        public WalkState(@NotNull File root) {
            Intrinsics.checkParameterIsNotNull(root, "root");
            this.root = root;
        }
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\b\"\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, m27d2 = {"Lkotlin/io/FileTreeWalk$DirectoryState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootDir", "Ljava/io/File;", "(Ljava/io/File;)V", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/io/FileTreeWalk$DirectoryState.class */
    private static abstract class DirectoryState extends WalkState {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DirectoryState(@NotNull File rootDir) {
            super(rootDir);
            Intrinsics.checkParameterIsNotNull(rootDir, "rootDir");
            if (_Assertions.ENABLED) {
                boolean zIsDirectory = rootDir.isDirectory();
                if (_Assertions.ENABLED && !zIsDirectory) {
                    throw new AssertionError("rootDir must be verified to be directory beforehand.");
                }
            }
        }
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0082\u0004\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\r\u000e\u000fB\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0002J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0082\u0010R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0010"}, m27d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;", "Lkotlin/collections/AbstractIterator;", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk;)V", "state", "Ljava/util/ArrayDeque;", "Lkotlin/io/FileTreeWalk$WalkState;", "computeNext", "", "directoryState", "Lkotlin/io/FileTreeWalk$DirectoryState;", "root", "gotoNext", "BottomUpDirectoryState", "SingleFileState", "TopDownDirectoryState", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/io/FileTreeWalk$FileTreeWalkIterator.class */
    private final class FileTreeWalkIterator extends AbstractIterator {
        private final ArrayDeque state = new ArrayDeque();
        final FileTreeWalk this$0;

        @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3)
        /* loaded from: L-out.jar:kotlin/io/FileTreeWalk$FileTreeWalkIterator$WhenMappings.class */
        public final class WhenMappings {
            public static final int[] $EnumSwitchMapping$0 = new int[FileWalkDirection.values().length];

            static {
                $EnumSwitchMapping$0[FileWalkDirection.TOP_DOWN.ordinal()] = 1;
                $EnumSwitchMapping$0[FileWalkDirection.BOTTOM_UP.ordinal()] = 2;
            }
        }

        public FileTreeWalkIterator(FileTreeWalk fileTreeWalk) {
            this.this$0 = fileTreeWalk;
            if (fileTreeWalk.start.isDirectory()) {
                this.state.push(directoryState(fileTreeWalk.start));
            } else if (fileTreeWalk.start.isFile()) {
                this.state.push(new SingleFileState(this, fileTreeWalk.start));
            } else {
                done();
            }
        }

        @Override // kotlin.collections.AbstractIterator
        protected void computeNext() {
            File fileGotoNext = gotoNext();
            if (fileGotoNext != null) {
                setNext(fileGotoNext);
            } else {
                done();
            }
        }

        private final DirectoryState directoryState(File file) {
            switch (WhenMappings.$EnumSwitchMapping$0[this.this$0.direction.ordinal()]) {
                case 1:
                    return new TopDownDirectoryState(this, file);
                case 2:
                    return new BottomUpDirectoryState(this, file);
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }

        private final File gotoNext() {
            File fileStep;
            while (true) {
                WalkState walkState = (WalkState) this.state.peek();
                if (walkState == null) {
                    return null;
                }
                fileStep = walkState.step();
                if (fileStep == null) {
                    this.state.pop();
                } else {
                    if (Intrinsics.areEqual(fileStep, walkState.getRoot()) || !fileStep.isDirectory() || this.state.size() >= this.this$0.maxDepth) {
                        break;
                    }
                    this.state.push(directoryState(fileStep));
                }
            }
            return fileStep;
        }

        @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0082\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\n\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0018\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000e"}, m27d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$BottomUpDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "failed", "", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "step", "kotlin-stdlib"})
        /* loaded from: L-out.jar:kotlin/io/FileTreeWalk$FileTreeWalkIterator$BottomUpDirectoryState.class */
        private final class BottomUpDirectoryState extends DirectoryState {
            private boolean rootVisited;
            private File[] fileList;
            private int fileIndex;
            private boolean failed;
            final FileTreeWalkIterator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public BottomUpDirectoryState(@NotNull FileTreeWalkIterator fileTreeWalkIterator, File rootDir) {
                super(rootDir);
                Intrinsics.checkParameterIsNotNull(rootDir, "rootDir");
                this.this$0 = fileTreeWalkIterator;
            }

            @Override // kotlin.io.FileTreeWalk.WalkState
            @Nullable
            public File step() {
                if (!this.failed && this.fileList == null) {
                    Function1 function1 = this.this$0.this$0.onEnter;
                    if (function1 != null && !((Boolean) function1.invoke(getRoot())).booleanValue()) {
                        return null;
                    }
                    this.fileList = getRoot().listFiles();
                    if (this.fileList == null) {
                        Function2 function2 = this.this$0.this$0.onFail;
                        if (function2 != null) {
                        }
                        this.failed = true;
                    }
                }
                if (this.fileList != null) {
                    int i = this.fileIndex;
                    File[] fileArr = this.fileList;
                    if (fileArr == null) {
                        Intrinsics.throwNpe();
                    }
                    if (i < fileArr.length) {
                        File[] fileArr2 = this.fileList;
                        if (fileArr2 == null) {
                            Intrinsics.throwNpe();
                        }
                        int i2 = this.fileIndex;
                        this.fileIndex = i2 + 1;
                        return fileArr2[i2];
                    }
                }
                if (this.rootVisited) {
                    Function1 function12 = this.this$0.this$0.onLeave;
                    if (function12 != null) {
                        return null;
                    }
                    return null;
                }
                this.rootVisited = true;
                return getRoot();
            }
        }

        @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\n\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\r"}, m27d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "", "step", "kotlin-stdlib"})
        /* loaded from: L-out.jar:kotlin/io/FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState.class */
        private final class TopDownDirectoryState extends DirectoryState {
            private boolean rootVisited;
            private File[] fileList;
            private int fileIndex;
            final FileTreeWalkIterator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public TopDownDirectoryState(@NotNull FileTreeWalkIterator fileTreeWalkIterator, File rootDir) {
                super(rootDir);
                Intrinsics.checkParameterIsNotNull(rootDir, "rootDir");
                this.this$0 = fileTreeWalkIterator;
            }

            /* JADX WARN: Code restructure failed: missing block: B:35:0x00af, code lost:
            
                if (r0.length == 0) goto L36;
             */
            @Override // kotlin.io.FileTreeWalk.WalkState
            @Nullable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public File step() {
                if (!this.rootVisited) {
                    Function1 function1 = this.this$0.this$0.onEnter;
                    if (function1 != null && !((Boolean) function1.invoke(getRoot())).booleanValue()) {
                        return null;
                    }
                    this.rootVisited = true;
                    return getRoot();
                }
                if (this.fileList != null) {
                    int i = this.fileIndex;
                    File[] fileArr = this.fileList;
                    if (fileArr == null) {
                        Intrinsics.throwNpe();
                    }
                    if (i >= fileArr.length) {
                        Function1 function12 = this.this$0.this$0.onLeave;
                        if (function12 != null) {
                            return null;
                        }
                        return null;
                    }
                }
                if (this.fileList == null) {
                    this.fileList = getRoot().listFiles();
                    if (this.fileList == null) {
                        Function2 function2 = this.this$0.this$0.onFail;
                        if (function2 != null) {
                        }
                    }
                    if (this.fileList != null) {
                        File[] fileArr2 = this.fileList;
                        if (fileArr2 == null) {
                            Intrinsics.throwNpe();
                        }
                    }
                    Function1 function13 = this.this$0.this$0.onLeave;
                    if (function13 != null) {
                        return null;
                    }
                    return null;
                }
                File[] fileArr3 = this.fileList;
                if (fileArr3 == null) {
                    Intrinsics.throwNpe();
                }
                int i2 = this.fileIndex;
                this.fileIndex = i2 + 1;
                return fileArr3[i2];
            }
        }

        @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\b"}, m27d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$SingleFileState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootFile", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "visited", "", "step", "kotlin-stdlib"})
        /* loaded from: L-out.jar:kotlin/io/FileTreeWalk$FileTreeWalkIterator$SingleFileState.class */
        private final class SingleFileState extends WalkState {
            private boolean visited;
            final FileTreeWalkIterator this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public SingleFileState(@NotNull FileTreeWalkIterator fileTreeWalkIterator, File rootFile) {
                super(rootFile);
                Intrinsics.checkParameterIsNotNull(rootFile, "rootFile");
                this.this$0 = fileTreeWalkIterator;
                if (_Assertions.ENABLED) {
                    boolean zIsFile = rootFile.isFile();
                    if (_Assertions.ENABLED && !zIsFile) {
                        throw new AssertionError("rootFile must be verified to be file beforehand.");
                    }
                }
            }

            @Override // kotlin.io.FileTreeWalk.WalkState
            @Nullable
            public File step() {
                if (this.visited) {
                    return null;
                }
                this.visited = true;
                return getRoot();
            }
        }
    }

    @NotNull
    public final FileTreeWalk onEnter(@NotNull Function1 function) {
        Intrinsics.checkParameterIsNotNull(function, "function");
        return new FileTreeWalk(this.start, this.direction, function, this.onLeave, this.onFail, this.maxDepth);
    }

    @NotNull
    public final FileTreeWalk onLeave(@NotNull Function1 function) {
        Intrinsics.checkParameterIsNotNull(function, "function");
        return new FileTreeWalk(this.start, this.direction, this.onEnter, function, this.onFail, this.maxDepth);
    }

    @NotNull
    public final FileTreeWalk onFail(@NotNull Function2 function) {
        Intrinsics.checkParameterIsNotNull(function, "function");
        return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, function, this.maxDepth);
    }

    @NotNull
    public final FileTreeWalk maxDepth(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("depth must be positive, but was " + i + '.');
        }
        return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, this.onFail, i);
    }
}
