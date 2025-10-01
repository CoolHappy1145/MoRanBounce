package org.apache.log4j.p007or;

/* loaded from: L-out.jar:org/apache/log4j/or/DefaultRenderer.class */
class DefaultRenderer implements ObjectRenderer {
    DefaultRenderer() {
    }

    @Override // org.apache.log4j.p007or.ObjectRenderer
    public String doRender(Object obj) {
        try {
            return obj.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }
}
