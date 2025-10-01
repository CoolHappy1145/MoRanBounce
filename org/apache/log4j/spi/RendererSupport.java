package org.apache.log4j.spi;

import org.apache.log4j.p007or.ObjectRenderer;
import org.apache.log4j.p007or.RendererMap;

/* loaded from: L-out.jar:org/apache/log4j/spi/RendererSupport.class */
public interface RendererSupport {
    RendererMap getRendererMap();

    void setRenderer(Class cls, ObjectRenderer objectRenderer);
}
