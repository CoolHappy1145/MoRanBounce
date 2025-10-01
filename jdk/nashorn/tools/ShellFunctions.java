package jdk.nashorn.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/* loaded from: L-out.jar:jdk/nashorn/tools/ShellFunctions.class */
public final class ShellFunctions {
    public static final MethodHandle INPUT = findOwnMH("input", Object.class, new Class[]{Object.class, Object.class, Object.class});
    public static final MethodHandle EVALINPUT = findOwnMH("evalinput", Object.class, new Class[]{Object.class, Object.class, Object.class});

    private ShellFunctions() {
    }

    public static Object input(Object obj, Object obj2, Object obj3) throws IOException {
        String string = obj2 != ScriptRuntime.UNDEFINED ? JSType.toString(obj2) : "";
        String string2 = obj3 != ScriptRuntime.UNDEFINED ? JSType.toString(obj3) : ">> ";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (true) {
            System.out.print(string2);
            String line = bufferedReader.readLine();
            if (line == null || line.equals(string)) {
                break;
            }
            sb.append(line);
            sb.append('\n');
        }
        return sb.toString();
    }

    public static Object evalinput(Object obj, Object obj2, Object obj3) {
        return Global.eval(obj, input(obj, obj2, obj3));
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), ShellFunctions.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}
