package org.apache.log4j.lf5.util;

import java.io.InputStream;
import java.net.URL;

/* loaded from: L-out.jar:org/apache/log4j/lf5/util/ResourceUtils.class */
public class ResourceUtils {
    public static InputStream getResourceAsStream(Object obj, Resource resource) {
        InputStream systemResourceAsStream;
        ClassLoader classLoader = obj.getClass().getClassLoader();
        if (classLoader != null) {
            systemResourceAsStream = classLoader.getResourceAsStream(resource.getName());
        } else {
            systemResourceAsStream = ClassLoader.getSystemResourceAsStream(resource.getName());
        }
        return systemResourceAsStream;
    }

    public static URL getResourceAsURL(Object obj, Resource resource) {
        URL systemResource;
        ClassLoader classLoader = obj.getClass().getClassLoader();
        if (classLoader != null) {
            systemResource = classLoader.getResource(resource.getName());
        } else {
            systemResource = ClassLoader.getSystemResource(resource.getName());
        }
        return systemResource;
    }
}
