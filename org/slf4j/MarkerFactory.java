package org.slf4j;

import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.helpers.Util;
import org.slf4j.impl.StaticMarkerBinder;

/* loaded from: L-out.jar:org/slf4j/MarkerFactory.class */
public class MarkerFactory {
    static IMarkerFactory MARKER_FACTORY;

    private MarkerFactory() {
    }

    private static IMarkerFactory bwCompatibleGetMarkerFactoryFromBinder() {
        try {
            return StaticMarkerBinder.getSingleton().getMarkerFactory();
        } catch (NoSuchMethodError unused) {
            return StaticMarkerBinder.SINGLETON.getMarkerFactory();
        }
    }

    static {
        try {
            MARKER_FACTORY = bwCompatibleGetMarkerFactoryFromBinder();
        } catch (Exception e) {
            Util.report("Unexpected failure while binding MarkerFactory", e);
        } catch (NoClassDefFoundError unused) {
            MARKER_FACTORY = new BasicMarkerFactory();
        }
    }

    public static Marker getMarker(String str) {
        return MARKER_FACTORY.getMarker(str);
    }

    public static Marker getDetachedMarker(String str) {
        return MARKER_FACTORY.getDetachedMarker(str);
    }

    public static IMarkerFactory getIMarkerFactory() {
        return MARKER_FACTORY;
    }
}
