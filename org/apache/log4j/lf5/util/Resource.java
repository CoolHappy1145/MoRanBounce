package org.apache.log4j.lf5.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/* loaded from: L-out.jar:org/apache/log4j/lf5/util/Resource.class */
public class Resource {
    protected String _name;

    public Resource() {
    }

    public Resource(String str) {
        this._name = str;
    }

    public void setName(String str) {
        this._name = str;
    }

    public String getName() {
        return this._name;
    }

    public InputStream getInputStream() {
        return ResourceUtils.getResourceAsStream(this, this);
    }

    public InputStreamReader getInputStreamReader() {
        InputStream resourceAsStream = ResourceUtils.getResourceAsStream(this, this);
        if (resourceAsStream == null) {
            return null;
        }
        return new InputStreamReader(resourceAsStream);
    }

    public URL getURL() {
        return ResourceUtils.getResourceAsURL(this, this);
    }
}
