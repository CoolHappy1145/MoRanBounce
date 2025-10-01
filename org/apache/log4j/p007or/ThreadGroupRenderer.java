package org.apache.log4j.p007or;

import org.apache.log4j.Layout;

/* loaded from: L-out.jar:org/apache/log4j/or/ThreadGroupRenderer.class */
public class ThreadGroupRenderer implements ObjectRenderer {
    @Override // org.apache.log4j.p007or.ObjectRenderer
    public String doRender(Object obj) {
        if (obj instanceof ThreadGroup) {
            StringBuffer stringBuffer = new StringBuffer();
            ThreadGroup threadGroup = (ThreadGroup) obj;
            stringBuffer.append("java.lang.ThreadGroup[name=");
            stringBuffer.append(threadGroup.getName());
            stringBuffer.append(", maxpri=");
            stringBuffer.append(threadGroup.getMaxPriority());
            stringBuffer.append("]");
            Thread[] threadArr = new Thread[threadGroup.activeCount()];
            threadGroup.enumerate(threadArr);
            for (int i = 0; i < threadArr.length; i++) {
                stringBuffer.append(Layout.LINE_SEP);
                stringBuffer.append("   Thread=[");
                stringBuffer.append(threadArr[i].getName());
                stringBuffer.append(",");
                stringBuffer.append(threadArr[i].getPriority());
                stringBuffer.append(",");
                stringBuffer.append(threadArr[i].isDaemon());
                stringBuffer.append("]");
            }
            return stringBuffer.toString();
        }
        try {
            return obj.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }
}
