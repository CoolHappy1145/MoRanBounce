package org.apache.log4j;

/* loaded from: L-out.jar:org/apache/log4j/NameValue.class */
class NameValue {
    String key;
    String value;

    public NameValue(String str, String str2) {
        this.key = str;
        this.value = str2;
    }

    public String toString() {
        return new StringBuffer().append(this.key).append("=").append(this.value).toString();
    }
}
