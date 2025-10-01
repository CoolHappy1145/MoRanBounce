package org.apache.log4j.spi;

import java.io.PrintWriter;
import java.util.Vector;

/* loaded from: L-out.jar:org/apache/log4j/spi/VectorWriter.class */
class VectorWriter extends PrintWriter {

    /* renamed from: v */
    private Vector f205v;

    VectorWriter() {
        super(new NullWriter());
        this.f205v = new Vector();
    }

    @Override // java.io.PrintWriter
    public void print(Object obj) {
        this.f205v.addElement(String.valueOf(obj));
    }

    @Override // java.io.PrintWriter
    public void print(char[] cArr) {
        this.f205v.addElement(new String(cArr));
    }

    @Override // java.io.PrintWriter
    public void print(String str) {
        this.f205v.addElement(str);
    }

    @Override // java.io.PrintWriter
    public void println(Object obj) {
        this.f205v.addElement(String.valueOf(obj));
    }

    @Override // java.io.PrintWriter
    public void println(char[] cArr) {
        this.f205v.addElement(new String(cArr));
    }

    @Override // java.io.PrintWriter
    public void println(String str) {
        this.f205v.addElement(str);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr) {
        this.f205v.addElement(new String(cArr));
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        this.f205v.addElement(new String(cArr, i, i2));
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String str, int i, int i2) {
        this.f205v.addElement(str.substring(i, i + i2));
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String str) {
        this.f205v.addElement(str);
    }

    public String[] toStringArray() {
        int size = this.f205v.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = (String) this.f205v.elementAt(i);
        }
        return strArr;
    }
}
