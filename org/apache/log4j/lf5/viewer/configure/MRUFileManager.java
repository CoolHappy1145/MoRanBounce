package org.apache.log4j.lf5.viewer.configure;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/configure/MRUFileManager.class */
public class MRUFileManager {
    private static final String CONFIG_FILE_NAME = "mru_file_manager";
    private static final int DEFAULT_MAX_SIZE = 3;
    private int _maxSize = 0;
    private LinkedList _mruFileList;

    public MRUFileManager() throws IOException {
        load();
        setMaxSize(3);
    }

    public MRUFileManager(int i) throws IOException {
        load();
        setMaxSize(i);
    }

    public void save() throws IOException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(getFilename())));
            objectOutputStream.writeObject(this._mruFileList);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int size() {
        return this._mruFileList.size();
    }

    public Object getFile(int i) {
        if (i < size()) {
            return this._mruFileList.get(i);
        }
        return null;
    }

    public InputStream getInputStream(int i) {
        if (i < size()) {
            Object file = getFile(i);
            if (file instanceof File) {
                return getInputStream((File) file);
            }
            return getInputStream((URL) file);
        }
        return null;
    }

    public void set(File file) {
        setMRU(file);
    }

    public void set(URL url) {
        setMRU(url);
    }

    public String[] getMRUFileList() {
        if (size() == 0) {
            return null;
        }
        String[] strArr = new String[size()];
        for (int i = 0; i < size(); i++) {
            Object file = getFile(i);
            if (file instanceof File) {
                strArr[i] = ((File) file).getAbsolutePath();
            } else {
                strArr[i] = file.toString();
            }
        }
        return strArr;
    }

    public void moveToTop(int i) {
        this._mruFileList.add(0, this._mruFileList.remove(i));
    }

    public static void createConfigurationDirectory() {
        String property = System.getProperty("user.home");
        File file = new File(new StringBuffer().append(property).append(System.getProperty("file.separator")).append("lf5").toString());
        if (!file.exists()) {
            try {
                file.mkdir();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    protected InputStream getInputStream(File file) {
        return new BufferedInputStream(new FileInputStream(file));
    }

    protected InputStream getInputStream(URL url) {
        return url.openStream();
    }

    protected void setMRU(Object obj) {
        int iIndexOf = this._mruFileList.indexOf(obj);
        if (iIndexOf == -1) {
            this._mruFileList.add(0, obj);
            setMaxSize(this._maxSize);
        } else {
            moveToTop(iIndexOf);
        }
    }

    protected void load() throws IOException {
        createConfigurationDirectory();
        File file = new File(getFilename());
        if (file.exists()) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                this._mruFileList = (LinkedList) objectInputStream.readObject();
                objectInputStream.close();
                Iterator it = this._mruFileList.iterator();
                while (it.hasNext()) {
                    Object next = it.next();
                    if (!(next instanceof File) && !(next instanceof URL)) {
                        it.remove();
                    }
                }
                return;
            } catch (Exception unused) {
                this._mruFileList = new LinkedList();
                return;
            }
        }
        this._mruFileList = new LinkedList();
    }

    protected String getFilename() {
        String property = System.getProperty("user.home");
        String property2 = System.getProperty("file.separator");
        return new StringBuffer().append(property).append(property2).append("lf5").append(property2).append(CONFIG_FILE_NAME).toString();
    }

    protected void setMaxSize(int i) {
        if (i < this._mruFileList.size()) {
            for (int i2 = 0; i2 < this._mruFileList.size() - i; i2++) {
                this._mruFileList.removeLast();
            }
        }
        this._maxSize = i;
    }
}
