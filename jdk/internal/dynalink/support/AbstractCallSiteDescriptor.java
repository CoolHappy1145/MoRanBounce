package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandles;
import java.util.Objects;
import jdk.internal.dynalink.CallSiteDescriptor;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/AbstractCallSiteDescriptor.class */
public abstract class AbstractCallSiteDescriptor implements CallSiteDescriptor {
    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public String getName() {
        return appendName(new StringBuilder(getNameLength())).toString();
    }

    @Override // jdk.internal.dynalink.CallSiteDescriptor
    public MethodHandles.Lookup getLookup() {
        return MethodHandles.publicLookup();
    }

    public boolean equals(Object obj) {
        return (obj instanceof CallSiteDescriptor) && equals((CallSiteDescriptor) obj);
    }

    public boolean equals(CallSiteDescriptor callSiteDescriptor) {
        if (callSiteDescriptor == null) {
            return false;
        }
        if (callSiteDescriptor == this) {
            return true;
        }
        int nameTokenCount = getNameTokenCount();
        if (nameTokenCount != callSiteDescriptor.getNameTokenCount()) {
            return false;
        }
        int i = nameTokenCount;
        do {
            int i2 = i;
            i--;
            if (i2 <= 0) {
                if (!getMethodType().equals(callSiteDescriptor.getMethodType())) {
                    return false;
                }
                return lookupsEqual(getLookup(), callSiteDescriptor.getLookup());
            }
        } while (Objects.equals(getNameToken(i), callSiteDescriptor.getNameToken(i)));
        return false;
    }

    public int hashCode() {
        MethodHandles.Lookup lookup = getLookup();
        int iHashCode = lookup.lookupClass().hashCode() + (31 * lookup.lookupModes());
        int nameTokenCount = getNameTokenCount();
        for (int i = 0; i < nameTokenCount; i++) {
            iHashCode = (iHashCode * 31) + getNameToken(i).hashCode();
        }
        return (iHashCode * 31) + getMethodType().hashCode();
    }

    public String toString() {
        String string = getMethodType().toString();
        String string2 = getLookup().toString();
        return appendName(new StringBuilder(string2.length() + 1 + string.length() + getNameLength())).append(string).append("@").append(string2).toString();
    }

    private int getNameLength() {
        int nameTokenCount = getNameTokenCount();
        int length = 0;
        for (int i = 0; i < nameTokenCount; i++) {
            length += getNameToken(i).length();
        }
        return (length + nameTokenCount) - 1;
    }

    private StringBuilder appendName(StringBuilder sb) {
        sb.append(getNameToken(0));
        int nameTokenCount = getNameTokenCount();
        for (int i = 1; i < nameTokenCount; i++) {
            sb.append(':').append(getNameToken(i));
        }
        return sb;
    }

    private static boolean lookupsEqual(MethodHandles.Lookup lookup, MethodHandles.Lookup lookup2) {
        if (lookup == lookup2) {
            return true;
        }
        return lookup.lookupClass() == lookup2.lookupClass() && lookup.lookupModes() == lookup2.lookupModes();
    }
}
