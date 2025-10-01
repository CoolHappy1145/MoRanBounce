package org.spongepowered.asm.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.objectweb.asm.tree.AnnotationNode;
import org.slf4j.Marker;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

/* loaded from: L-out.jar:org/spongepowered/asm/util/ConstraintParser.class */
public final class ConstraintParser {

    /* loaded from: L-out.jar:org/spongepowered/asm/util/ConstraintParser$Constraint.class */
    public static class Constraint {
        public static final Constraint NONE = new Constraint();
        private static final Pattern pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)\\((?:(<|<=|>|>=|=)?([0-9]+)(<|(-)([0-9]+)?|>|(\\+)([0-9]+)?)?)?\\)$");
        private final String expr;
        private String token;
        private String[] constraint;
        private int min;
        private int max;
        private Constraint next;

        Constraint(String str) {
            this.min = Integer.MIN_VALUE;
            this.max = Integer.MAX_VALUE;
            this.expr = str;
            Matcher matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                throw new InvalidConstraintException("Constraint syntax was invalid parsing: " + this.expr);
            }
            this.token = matcher.group(1);
            this.constraint = new String[]{matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8)};
            parse();
        }

        private Constraint() {
            this.min = Integer.MIN_VALUE;
            this.max = Integer.MAX_VALUE;
            this.expr = null;
            this.token = Marker.ANY_MARKER;
            this.constraint = new String[0];
        }

        private void parse() {
            if (!has(1)) {
                return;
            }
            int iVal = val(1);
            this.min = iVal;
            this.max = iVal;
            boolean zHas = has(0);
            if (has(4)) {
                if (zHas) {
                    throw new InvalidConstraintException("Unexpected modifier '" + elem(0) + "' in " + this.expr + " parsing range");
                }
                this.max = val(4);
                if (this.max < this.min) {
                    throw new InvalidConstraintException("Invalid range specified '" + this.max + "' is less than " + this.min + " in " + this.expr);
                }
                return;
            }
            if (has(6)) {
                if (zHas) {
                    throw new InvalidConstraintException("Unexpected modifier '" + elem(0) + "' in " + this.expr + " parsing range");
                }
                this.max = this.min + val(6);
                return;
            }
            if (!zHas) {
                if (has(2)) {
                    if ("<".equals(elem(2))) {
                        this.max = this.min;
                        this.min = Integer.MIN_VALUE;
                        return;
                    } else {
                        this.max = Integer.MAX_VALUE;
                        return;
                    }
                }
                return;
            }
            if (has(3)) {
                throw new InvalidConstraintException("Unexpected trailing modifier '" + elem(3) + "' in " + this.expr);
            }
            String strElem = elem(0);
            if (">".equals(strElem)) {
                this.min++;
                this.max = Integer.MAX_VALUE;
                return;
            }
            if (">=".equals(strElem)) {
                this.max = Integer.MAX_VALUE;
                return;
            }
            if ("<".equals(strElem)) {
                int i = this.min - 1;
                this.min = i;
                this.max = i;
                this.min = Integer.MIN_VALUE;
                return;
            }
            if ("<=".equals(strElem)) {
                this.max = this.min;
                this.min = Integer.MIN_VALUE;
            }
        }

        private boolean has(int i) {
            return this.constraint[i] != null;
        }

        private String elem(int i) {
            return this.constraint[i];
        }

        private int val(int i) {
            if (this.constraint[i] != null) {
                return Integer.parseInt(this.constraint[i]);
            }
            return 0;
        }

        void append(Constraint constraint) {
            if (this.next != null) {
                this.next.append(constraint);
            } else {
                this.next = constraint;
            }
        }

        public String getToken() {
            return this.token;
        }

        public int getMin() {
            return this.min;
        }

        public int getMax() {
            return this.max;
        }

        public void check(ITokenProvider iTokenProvider) throws ConstraintViolationException {
            if (this != NONE) {
                Integer token = iTokenProvider.getToken(this.token);
                if (token == null) {
                    throw new ConstraintViolationException("The token '" + this.token + "' could not be resolved in " + iTokenProvider, this);
                }
                if (token.intValue() < this.min) {
                    throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + token + ") which is less than the minimum value " + this.min + " in " + iTokenProvider, this, token.intValue());
                }
                if (token.intValue() > this.max) {
                    throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + token + ") which is greater than the maximum value " + this.max + " in " + iTokenProvider, this, token.intValue());
                }
            }
            if (this.next != null) {
                this.next.check(iTokenProvider);
            }
        }

        public String getRangeHumanReadable() {
            if (this.min == Integer.MIN_VALUE && this.max == Integer.MAX_VALUE) {
                return "ANY VALUE";
            }
            if (this.min == Integer.MIN_VALUE) {
                return String.format("less than or equal to %d", Integer.valueOf(this.max));
            }
            if (this.max == Integer.MAX_VALUE) {
                return String.format("greater than or equal to %d", Integer.valueOf(this.min));
            }
            if (this.min == this.max) {
                return String.format("%d", Integer.valueOf(this.min));
            }
            return String.format("between %d and %d", Integer.valueOf(this.min), Integer.valueOf(this.max));
        }

        public String toString() {
            return String.format("Constraint(%s [%d-%d])", this.token, Integer.valueOf(this.min), Integer.valueOf(this.max));
        }
    }

    private ConstraintParser() {
    }

    public static Constraint parse(String str) {
        if (str == null || str.length() == 0) {
            return Constraint.NONE;
        }
        Constraint constraint = null;
        for (String str2 : str.replaceAll("\\s", "").toUpperCase(Locale.ROOT).split(";")) {
            Constraint constraint2 = new Constraint(str2);
            if (constraint == null) {
                constraint = constraint2;
            } else {
                constraint.append(constraint2);
            }
        }
        return constraint != null ? constraint : Constraint.NONE;
    }

    public static Constraint parse(AnnotationNode annotationNode) {
        return parse((String) Annotations.getValue(annotationNode, "constraints", ""));
    }
}
