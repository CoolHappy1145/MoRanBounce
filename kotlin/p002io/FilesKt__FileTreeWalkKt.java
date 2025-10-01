package kotlin.p002io;

import java.io.File;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0014\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u00a8\u0006\u0007"}, m27d2 = {"walk", "Lkotlin/io/FileTreeWalk;", "Ljava/io/File;", "direction", "Lkotlin/io/FileWalkDirection;", "walkBottomUp", "walkTopDown", "kotlin-stdlib"}, m28xs = "kotlin/io/FilesKt")
/* loaded from: L-out.jar:kotlin/io/FilesKt__FileTreeWalkKt.class */
class FilesKt__FileTreeWalkKt extends FilesKt__FileReadWriteKt {
    public static FileTreeWalk walk$default(File file, FileWalkDirection fileWalkDirection, int i, Object obj) {
        if ((i & 1) != 0) {
            fileWalkDirection = FileWalkDirection.TOP_DOWN;
        }
        return FilesKt.walk(file, fileWalkDirection);
    }

    @NotNull
    public static final FileTreeWalk walk(@NotNull File walk, @NotNull FileWalkDirection direction) {
        Intrinsics.checkParameterIsNotNull(walk, "$this$walk");
        Intrinsics.checkParameterIsNotNull(direction, "direction");
        return new FileTreeWalk(walk, direction);
    }

    @NotNull
    public static final FileTreeWalk walkTopDown(@NotNull File walkTopDown) {
        Intrinsics.checkParameterIsNotNull(walkTopDown, "$this$walkTopDown");
        return FilesKt.walk(walkTopDown, FileWalkDirection.TOP_DOWN);
    }

    @NotNull
    public static final FileTreeWalk walkBottomUp(@NotNull File walkBottomUp) {
        Intrinsics.checkParameterIsNotNull(walkBottomUp, "$this$walkBottomUp");
        return FilesKt.walk(walkBottomUp, FileWalkDirection.BOTTOM_UP);
    }
}
