package org.spongepowered.tools.obfuscation;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/TargetMap.class */
public final class TargetMap extends HashMap {
    private static final long serialVersionUID = 1;
    private final String sessionId;

    private TargetMap() {
        this(String.valueOf(System.currentTimeMillis()));
    }

    private TargetMap(String str) {
        this.sessionId = str;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void registerTargets(AnnotatedMixin annotatedMixin) {
        registerTargets(annotatedMixin.getTargets(), annotatedMixin.getHandle());
    }

    public void registerTargets(List list, TypeHandle typeHandle) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            addMixin((TypeHandle) it.next(), typeHandle);
        }
    }

    public void addMixin(TypeHandle typeHandle, TypeHandle typeHandle2) {
        addMixin(typeHandle.getReference(), typeHandle2.getReference());
    }

    public void addMixin(String str, String str2) {
        addMixin(new TypeReference(str), new TypeReference(str2));
    }

    public void addMixin(TypeReference typeReference, TypeReference typeReference2) {
        getMixinsFor(typeReference).add(typeReference2);
    }

    public Collection getMixinsTargeting(TypeElement typeElement) {
        return getMixinsTargeting(new TypeHandle(typeElement));
    }

    public Collection getMixinsTargeting(TypeHandle typeHandle) {
        return getMixinsTargeting(typeHandle.getReference());
    }

    public Collection getMixinsTargeting(TypeReference typeReference) {
        return Collections.unmodifiableCollection(getMixinsFor(typeReference));
    }

    private Set getMixinsFor(TypeReference typeReference) {
        Set hashSet = (Set) get(typeReference);
        if (hashSet == null) {
            hashSet = new HashSet();
            put(typeReference, hashSet);
        }
        return hashSet;
    }

    public void readImports(File file) {
        if (!file.isFile()) {
            return;
        }
        Iterator it = Files.readLines(file, Charset.defaultCharset()).iterator();
        while (it.hasNext()) {
            String[] strArrSplit = ((String) it.next()).split("\t");
            if (strArrSplit.length == 2) {
                addMixin(strArrSplit[1], strArrSplit[0]);
            }
        }
    }

    public void write(boolean z) {
        ObjectOutputStream objectOutputStream = null;
        try {
            try {
                File sessionFile = getSessionFile(this.sessionId);
                if (z) {
                    sessionFile.deleteOnExit();
                }
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(sessionFile, true));
                objectOutputStream.writeObject(this);
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            }
        } catch (Throwable th) {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    private static TargetMap read(File file) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            try {
                objectInputStream = new ObjectInputStream(new FileInputStream(file));
                TargetMap targetMap = (TargetMap) objectInputStream.readObject();
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return targetMap;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (objectInputStream == null) {
                    return null;
                }
                try {
                    objectInputStream.close();
                    return null;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return null;
                }
            }
        } catch (Throwable th) {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static TargetMap create(String str) {
        TargetMap targetMap;
        if (str != null) {
            File sessionFile = getSessionFile(str);
            if (sessionFile.exists() && (targetMap = read(sessionFile)) != null) {
                return targetMap;
            }
        }
        return new TargetMap();
    }

    private static File getSessionFile(String str) {
        return new File(new File(System.getProperty("java.io.tmpdir")), String.format("mixin-targetdb-%s.tmp", str));
    }
}
