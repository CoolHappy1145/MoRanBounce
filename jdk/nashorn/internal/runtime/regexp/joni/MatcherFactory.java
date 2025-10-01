package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/MatcherFactory.class */
public abstract class MatcherFactory {
    static final MatcherFactory DEFAULT = new MatcherFactory() { // from class: jdk.nashorn.internal.runtime.regexp.joni.MatcherFactory.1
        @Override // jdk.nashorn.internal.runtime.regexp.joni.MatcherFactory
        public Matcher create(Regex regex, char[] cArr, int i, int i2) {
            return new ByteCodeMachine(regex, cArr, i, i2);
        }
    };

    public abstract Matcher create(Regex regex, char[] cArr, int i, int i2);
}
