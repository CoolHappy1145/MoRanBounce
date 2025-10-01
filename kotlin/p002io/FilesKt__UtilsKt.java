package kotlin.p002io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd<\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\f\u001a(\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a(\u0010\r\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a8\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\u001a\b\u0002\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013\u001a&\u0010\u0016\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u000f*\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0002*\u00020\u0002\u001a\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d*\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0002\u00a2\u0006\u0002\b\u001e\u001a\u0011\u0010\u001c\u001a\u00020\u001f*\u00020\u001fH\u0002\u00a2\u0006\u0002\b\u001e\u001a\u0012\u0010 \u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0014\u0010\"\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u001b\u0010)\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002H\u0002\u00a2\u0006\u0002\b*\"\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0004\u00a8\u0006+"}, m27d2 = {"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"}, m28xs = "kotlin/io/FilesKt")
/* loaded from: L-out.jar:kotlin/io/FilesKt__UtilsKt.class */
class FilesKt__UtilsKt extends FilesKt__FileTreeWalkKt {
    public static File createTempDir$default(String str, String str2, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "tmp";
        }
        if ((i & 2) != 0) {
            str2 = (String) null;
        }
        if ((i & 4) != 0) {
            file = (File) null;
        }
        return FilesKt.createTempDir(str, str2, file);
    }

    @NotNull
    public static final File createTempDir(@NotNull String prefix, @Nullable String str, @Nullable File file) throws IOException {
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        File dir = File.createTempFile(prefix, str, file);
        dir.delete();
        if (dir.mkdir()) {
            Intrinsics.checkExpressionValueIsNotNull(dir, "dir");
            return dir;
        }
        throw new IOException("Unable to create temporary directory " + dir + '.');
    }

    public static File createTempFile$default(String str, String str2, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "tmp";
        }
        if ((i & 2) != 0) {
            str2 = (String) null;
        }
        if ((i & 4) != 0) {
            file = (File) null;
        }
        return FilesKt.createTempFile(str, str2, file);
    }

    @NotNull
    public static final File createTempFile(@NotNull String prefix, @Nullable String str, @Nullable File file) throws IOException {
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        File fileCreateTempFile = File.createTempFile(prefix, str, file);
        Intrinsics.checkExpressionValueIsNotNull(fileCreateTempFile, "File.createTempFile(prefix, suffix, directory)");
        return fileCreateTempFile;
    }

    @NotNull
    public static final String getExtension(@NotNull File extension) {
        Intrinsics.checkParameterIsNotNull(extension, "$this$extension");
        String name = extension.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "name");
        return StringsKt.substringAfterLast(name, '.', "");
    }

    @NotNull
    public static final String getInvariantSeparatorsPath(@NotNull File invariantSeparatorsPath) {
        Intrinsics.checkParameterIsNotNull(invariantSeparatorsPath, "$this$invariantSeparatorsPath");
        if (File.separatorChar != '/') {
            String path = invariantSeparatorsPath.getPath();
            Intrinsics.checkExpressionValueIsNotNull(path, "path");
            return StringsKt.replace$default(path, File.separatorChar, '/', false, 4, (Object) null);
        }
        String path2 = invariantSeparatorsPath.getPath();
        Intrinsics.checkExpressionValueIsNotNull(path2, "path");
        return path2;
    }

    @NotNull
    public static final String getNameWithoutExtension(@NotNull File nameWithoutExtension) {
        Intrinsics.checkParameterIsNotNull(nameWithoutExtension, "$this$nameWithoutExtension");
        String name = nameWithoutExtension.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "name");
        return StringsKt.substringBeforeLast$default(name, ".", (String) null, 2, (Object) null);
    }

    @NotNull
    public static final String toRelativeString(@NotNull File toRelativeString, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull(toRelativeString, "$this$toRelativeString");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String relativeStringOrNull$FilesKt__UtilsKt = toRelativeStringOrNull$FilesKt__UtilsKt(toRelativeString, base);
        if (relativeStringOrNull$FilesKt__UtilsKt != null) {
            return relativeStringOrNull$FilesKt__UtilsKt;
        }
        throw new IllegalArgumentException("this and base files have different roots: " + toRelativeString + " and " + base + '.');
    }

    @NotNull
    public static final File relativeTo(@NotNull File relativeTo, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull(relativeTo, "$this$relativeTo");
        Intrinsics.checkParameterIsNotNull(base, "base");
        return new File(FilesKt.toRelativeString(relativeTo, base));
    }

    @NotNull
    public static final File relativeToOrSelf(@NotNull File relativeToOrSelf, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull(relativeToOrSelf, "$this$relativeToOrSelf");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String relativeStringOrNull$FilesKt__UtilsKt = toRelativeStringOrNull$FilesKt__UtilsKt(relativeToOrSelf, base);
        return relativeStringOrNull$FilesKt__UtilsKt != null ? new File(relativeStringOrNull$FilesKt__UtilsKt) : relativeToOrSelf;
    }

    @Nullable
    public static final File relativeToOrNull(@NotNull File relativeToOrNull, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull(relativeToOrNull, "$this$relativeToOrNull");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String relativeStringOrNull$FilesKt__UtilsKt = toRelativeStringOrNull$FilesKt__UtilsKt(relativeToOrNull, base);
        if (relativeStringOrNull$FilesKt__UtilsKt != null) {
            return new File(relativeStringOrNull$FilesKt__UtilsKt);
        }
        return null;
    }

    private static final String toRelativeStringOrNull$FilesKt__UtilsKt(@NotNull File file, File file2) {
        FilePathComponents filePathComponentsNormalize$FilesKt__UtilsKt = normalize$FilesKt__UtilsKt(FilesKt.toComponents(file));
        FilePathComponents filePathComponentsNormalize$FilesKt__UtilsKt2 = normalize$FilesKt__UtilsKt(FilesKt.toComponents(file2));
        if (!Intrinsics.areEqual(filePathComponentsNormalize$FilesKt__UtilsKt.getRoot(), filePathComponentsNormalize$FilesKt__UtilsKt2.getRoot())) {
            return null;
        }
        int size = filePathComponentsNormalize$FilesKt__UtilsKt2.getSize();
        int size2 = filePathComponentsNormalize$FilesKt__UtilsKt.getSize();
        int i = 0;
        int iMin = Math.min(size2, size);
        while (i < iMin && Intrinsics.areEqual((File) filePathComponentsNormalize$FilesKt__UtilsKt.getSegments().get(i), (File) filePathComponentsNormalize$FilesKt__UtilsKt2.getSegments().get(i))) {
            i++;
        }
        int i2 = i;
        StringBuilder sb = new StringBuilder();
        int i3 = size - 1;
        if (i3 >= i2) {
            while (!Intrinsics.areEqual(((File) filePathComponentsNormalize$FilesKt__UtilsKt2.getSegments().get(i3)).getName(), "..")) {
                sb.append("..");
                if (i3 != i2) {
                    sb.append(File.separatorChar);
                }
                if (i3 != i2) {
                    i3--;
                }
            }
            return null;
        }
        if (i2 < size2) {
            if (i2 < size) {
                sb.append(File.separatorChar);
            }
            String str = File.separator;
            Intrinsics.checkExpressionValueIsNotNull(str, "File.separator");
            CollectionsKt.joinTo$default(CollectionsKt.drop(filePathComponentsNormalize$FilesKt__UtilsKt.getSegments(), i2), sb, str, null, null, 0, null, null, 124, null);
        }
        return sb.toString();
    }

    public static File copyTo$default(File file, File file2, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 8192;
        }
        return FilesKt.copyTo(file, file2, z, i);
    }

    @NotNull
    public static final File copyTo(@NotNull File copyTo, @NotNull File target, boolean z, int i) throws FileSystemException {
        Intrinsics.checkParameterIsNotNull(copyTo, "$this$copyTo");
        Intrinsics.checkParameterIsNotNull(target, "target");
        if (!copyTo.exists()) {
            throw new NoSuchFileException(copyTo, null, "The source file doesn't exist.", 2, null);
        }
        if (target.exists()) {
            if (!z) {
                throw new FileAlreadyExistsException(copyTo, target, "The destination file already exists.");
            }
            if (!target.delete()) {
                throw new FileAlreadyExistsException(copyTo, target, "Tried to overwrite the destination, but failed to delete it.");
            }
        }
        if (copyTo.isDirectory()) {
            if (!target.mkdirs()) {
                throw new FileSystemException(copyTo, target, "Failed to create target directory.");
            }
        } else {
            File parentFile = target.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            FileInputStream fileInputStream = new FileInputStream(copyTo);
            Throwable th = (Throwable) null;
            try {
                FileInputStream fileInputStream2 = fileInputStream;
                FileOutputStream fileOutputStream = new FileOutputStream(target);
                Throwable th2 = (Throwable) null;
                try {
                    ByteStreamsKt.copyTo(fileInputStream2, fileOutputStream, i);
                    CloseableKt.closeFinally(fileOutputStream, th2);
                    CloseableKt.closeFinally(fileInputStream, th);
                } catch (Throwable th3) {
                    throw th3;
                }
            } catch (Throwable th4) {
                throw th4;
            }
        }
        return target;
    }

    public static boolean copyRecursively$default(File file, File file2, boolean z, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            function2 = new Function2() { // from class: kotlin.io.FilesKt__UtilsKt.copyRecursively.1
                @Override // kotlin.jvm.functions.Function2
                public Object invoke(Object obj2, Object obj3) {
                    return invoke((File) obj2, (IOException) obj3);
                }

                @NotNull
                public final Void invoke(@NotNull File file3, @NotNull IOException exception) throws IOException {
                    Intrinsics.checkParameterIsNotNull(file3, "<anonymous parameter 0>");
                    Intrinsics.checkParameterIsNotNull(exception, "exception");
                    throw exception;
                }
            };
        }
        return FilesKt.copyRecursively(file, file2, z, function2);
    }

    public static final boolean copyRecursively(@NotNull File copyRecursively, @NotNull File target, boolean z, @NotNull Function2 onError) {
        boolean z2;
        Intrinsics.checkParameterIsNotNull(copyRecursively, "$this$copyRecursively");
        Intrinsics.checkParameterIsNotNull(target, "target");
        Intrinsics.checkParameterIsNotNull(onError, "onError");
        if (!copyRecursively.exists()) {
            return ((OnErrorAction) onError.invoke(copyRecursively, new NoSuchFileException(copyRecursively, null, "The source file doesn't exist.", 2, null))) != OnErrorAction.TERMINATE;
        }
        Iterator it = FilesKt.walkTopDown(copyRecursively).onFail(new Function2(onError) { // from class: kotlin.io.FilesKt__UtilsKt.copyRecursively.2
            final Function2 $onError;

            @Override // kotlin.jvm.functions.Function2
            public Object invoke(Object obj, Object obj2) throws TerminateException {
                invoke((File) obj, (IOException) obj2);
                return Unit.INSTANCE;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
                this.$onError = onError;
            }

            public final void invoke(@NotNull File f, @NotNull IOException e) throws TerminateException {
                Intrinsics.checkParameterIsNotNull(f, "f");
                Intrinsics.checkParameterIsNotNull(e, "e");
                if (((OnErrorAction) this.$onError.invoke(f, e)) == OnErrorAction.TERMINATE) {
                    throw new TerminateException(f);
                }
            }
        }).iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            if (!file.exists()) {
                if (((OnErrorAction) onError.invoke(file, new NoSuchFileException(file, null, "The source file doesn't exist.", 2, null))) == OnErrorAction.TERMINATE) {
                    return false;
                }
            } else {
                File file2 = new File(target, FilesKt.toRelativeString(file, copyRecursively));
                if (file2.exists() && (!file.isDirectory() || !file2.isDirectory())) {
                    if (!z) {
                        z2 = true;
                    } else if (file2.isDirectory()) {
                        z2 = !FilesKt.deleteRecursively(file2);
                    } else {
                        z2 = !file2.delete();
                    }
                    if (z2) {
                        if (((OnErrorAction) onError.invoke(file2, new FileAlreadyExistsException(file, file2, "The destination file already exists."))) == OnErrorAction.TERMINATE) {
                            return false;
                        }
                    }
                }
                if (file.isDirectory()) {
                    file2.mkdirs();
                } else if (FilesKt.copyTo$default(file, file2, z, 0, 4, null).length() != file.length() && ((OnErrorAction) onError.invoke(file, new IOException("Source file wasn't copied completely, length of destination file differs."))) == OnErrorAction.TERMINATE) {
                    return false;
                }
            }
        }
        return true;
    }

    public static final boolean deleteRecursively(@NotNull File deleteRecursively) {
        Intrinsics.checkParameterIsNotNull(deleteRecursively, "$this$deleteRecursively");
        boolean z = true;
        for (File file : FilesKt.walkBottomUp(deleteRecursively)) {
            z = (file.delete() || !file.exists()) && z;
        }
        return z;
    }

    public static final boolean startsWith(@NotNull File startsWith, @NotNull File other) {
        Intrinsics.checkParameterIsNotNull(startsWith, "$this$startsWith");
        Intrinsics.checkParameterIsNotNull(other, "other");
        FilePathComponents components = FilesKt.toComponents(startsWith);
        FilePathComponents components2 = FilesKt.toComponents(other);
        if (!(!Intrinsics.areEqual(components.getRoot(), components2.getRoot())) && components.getSize() >= components2.getSize()) {
            return components.getSegments().subList(0, components2.getSize()).equals(components2.getSegments());
        }
        return false;
    }

    public static final boolean startsWith(@NotNull File startsWith, @NotNull String other) {
        Intrinsics.checkParameterIsNotNull(startsWith, "$this$startsWith");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return FilesKt.startsWith(startsWith, new File(other));
    }

    public static final boolean endsWith(@NotNull File endsWith, @NotNull File other) {
        Intrinsics.checkParameterIsNotNull(endsWith, "$this$endsWith");
        Intrinsics.checkParameterIsNotNull(other, "other");
        FilePathComponents components = FilesKt.toComponents(endsWith);
        FilePathComponents components2 = FilesKt.toComponents(other);
        if (components2.isRooted()) {
            return Intrinsics.areEqual(endsWith, other);
        }
        int size = components.getSize() - components2.getSize();
        if (size < 0) {
            return false;
        }
        return components.getSegments().subList(size, components.getSize()).equals(components2.getSegments());
    }

    public static final boolean endsWith(@NotNull File endsWith, @NotNull String other) {
        Intrinsics.checkParameterIsNotNull(endsWith, "$this$endsWith");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return FilesKt.endsWith(endsWith, new File(other));
    }

    @NotNull
    public static final File normalize(@NotNull File normalize) {
        Intrinsics.checkParameterIsNotNull(normalize, "$this$normalize");
        FilePathComponents components = FilesKt.toComponents(normalize);
        File root = components.getRoot();
        List listNormalize$FilesKt__UtilsKt = normalize$FilesKt__UtilsKt(components.getSegments());
        String str = File.separator;
        Intrinsics.checkExpressionValueIsNotNull(str, "File.separator");
        return FilesKt.resolve(root, CollectionsKt.joinToString$default(listNormalize$FilesKt__UtilsKt, str, null, null, 0, null, null, 62, null));
    }

    private static final FilePathComponents normalize$FilesKt__UtilsKt(@NotNull FilePathComponents filePathComponents) {
        return new FilePathComponents(filePathComponents.getRoot(), normalize$FilesKt__UtilsKt(filePathComponents.getSegments()));
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static final List normalize$FilesKt__UtilsKt(@NotNull List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            String name = file.getName();
            if (name != null) {
                switch (name.hashCode()) {
                    case OPCode.BACKREF_MULTI_IC /* 46 */:
                        if (!name.equals(".")) {
                            break;
                        } else {
                            break;
                        }
                    case 1472:
                        if (!name.equals("..")) {
                            break;
                        } else if (!arrayList.isEmpty() && (!Intrinsics.areEqual(((File) CollectionsKt.last((List) arrayList)).getName(), ".."))) {
                            arrayList.remove(arrayList.size() - 1);
                            break;
                        } else {
                            arrayList.add(file);
                            break;
                        }
                        break;
                }
            }
            arrayList.add(file);
        }
        return arrayList;
    }

    @NotNull
    public static final File resolve(@NotNull File resolve, @NotNull File relative) {
        Intrinsics.checkParameterIsNotNull(resolve, "$this$resolve");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        if (FilesKt.isRooted(relative)) {
            return relative;
        }
        String string = resolve.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "this.toString()");
        return ((string.length() == 0) || StringsKt.endsWith$default((CharSequence) string, File.separatorChar, false, 2, (Object) null)) ? new File(string + relative) : new File(string + File.separatorChar + relative);
    }

    @NotNull
    public static final File resolve(@NotNull File resolve, @NotNull String relative) {
        Intrinsics.checkParameterIsNotNull(resolve, "$this$resolve");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        return FilesKt.resolve(resolve, new File(relative));
    }

    @NotNull
    public static final File resolveSibling(@NotNull File resolveSibling, @NotNull File relative) {
        Intrinsics.checkParameterIsNotNull(resolveSibling, "$this$resolveSibling");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        FilePathComponents components = FilesKt.toComponents(resolveSibling);
        return FilesKt.resolve(FilesKt.resolve(components.getRoot(), components.getSize() == 0 ? new File("..") : components.subPath(0, components.getSize() - 1)), relative);
    }

    @NotNull
    public static final File resolveSibling(@NotNull File resolveSibling, @NotNull String relative) {
        Intrinsics.checkParameterIsNotNull(resolveSibling, "$this$resolveSibling");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        return FilesKt.resolveSibling(resolveSibling, new File(relative));
    }
}
