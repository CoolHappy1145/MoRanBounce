package org.apache.log4j;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import org.apache.log4j.helpers.LogLog;

/* loaded from: L-out.jar:org/apache/log4j/NDC.class */
public class NDC {

    /* renamed from: ht */
    static Hashtable f187ht = new Hashtable();
    static int pushCounter = 0;
    static final int REAP_THRESHOLD = 5;

    private NDC() {
    }

    private static Stack getCurrentStack() {
        if (f187ht != null) {
            return (Stack) f187ht.get(Thread.currentThread());
        }
        return null;
    }

    public static void clear() {
        Stack currentStack = getCurrentStack();
        if (currentStack != null) {
            currentStack.setSize(0);
        }
    }

    public static Stack cloneStack() {
        Stack currentStack = getCurrentStack();
        if (currentStack == null) {
            return null;
        }
        return (Stack) currentStack.clone();
    }

    public static void inherit(Stack stack) {
        if (stack != null) {
            f187ht.put(Thread.currentThread(), stack);
        }
    }

    public static String get() {
        Stack currentStack = getCurrentStack();
        if (currentStack != null && !currentStack.isEmpty()) {
            return ((DiagnosticContext) currentStack.peek()).fullMessage;
        }
        return null;
    }

    public static int getDepth() {
        Stack currentStack = getCurrentStack();
        if (currentStack == null) {
            return 0;
        }
        return currentStack.size();
    }

    private static void lazyRemove() {
        if (f187ht == null) {
            return;
        }
        synchronized (f187ht) {
            int i = pushCounter + 1;
            pushCounter = i;
            if (i <= 5) {
                return;
            }
            pushCounter = 0;
            int i2 = 0;
            Vector vector = new Vector();
            Enumeration enumerationKeys = f187ht.keys();
            while (enumerationKeys.hasMoreElements() && i2 <= 4) {
                Thread thread = (Thread) enumerationKeys.nextElement();
                if (thread.isAlive()) {
                    i2++;
                } else {
                    i2 = 0;
                    vector.addElement(thread);
                }
            }
            int size = vector.size();
            for (int i3 = 0; i3 < size; i3++) {
                Thread thread2 = (Thread) vector.elementAt(i3);
                LogLog.debug(new StringBuffer().append("Lazy NDC removal for thread [").append(thread2.getName()).append("] (").append(f187ht.size()).append(").").toString());
                f187ht.remove(thread2);
            }
        }
    }

    public static String pop() {
        Stack currentStack = getCurrentStack();
        if (currentStack != null && !currentStack.isEmpty()) {
            return ((DiagnosticContext) currentStack.pop()).message;
        }
        return "";
    }

    public static String peek() {
        Stack currentStack = getCurrentStack();
        if (currentStack != null && !currentStack.isEmpty()) {
            return ((DiagnosticContext) currentStack.peek()).message;
        }
        return "";
    }

    public static void push(String str) {
        Stack currentStack = getCurrentStack();
        if (currentStack != null) {
            if (currentStack.isEmpty()) {
                currentStack.push(new DiagnosticContext(str, null));
                return;
            } else {
                currentStack.push(new DiagnosticContext(str, (DiagnosticContext) currentStack.peek()));
                return;
            }
        }
        DiagnosticContext diagnosticContext = new DiagnosticContext(str, null);
        Stack stack = new Stack();
        f187ht.put(Thread.currentThread(), stack);
        stack.push(diagnosticContext);
    }

    public static void remove() {
        if (f187ht != null) {
            f187ht.remove(Thread.currentThread());
            lazyRemove();
        }
    }

    public static void setMaxDepth(int i) {
        Stack currentStack = getCurrentStack();
        if (currentStack != null && i < currentStack.size()) {
            currentStack.setSize(i);
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/NDC$DiagnosticContext.class */
    private static class DiagnosticContext {
        String fullMessage;
        String message;

        DiagnosticContext(String str, DiagnosticContext diagnosticContext) {
            this.message = str;
            if (diagnosticContext != null) {
                this.fullMessage = new StringBuffer().append(diagnosticContext.fullMessage).append(' ').append(str).toString();
            } else {
                this.fullMessage = str;
            }
        }
    }
}
