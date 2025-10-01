package org.slf4j.helpers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.IMarkerFactory;
import org.slf4j.Marker;

/* loaded from: L-out.jar:org/slf4j/helpers/BasicMarkerFactory.class */
public class BasicMarkerFactory implements IMarkerFactory {
    private final ConcurrentMap markerMap = new ConcurrentHashMap();

    @Override // org.slf4j.IMarkerFactory
    public Marker getMarker(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Marker name cannot be null");
        }
        Marker basicMarker = (Marker) this.markerMap.get(str);
        if (basicMarker == null) {
            basicMarker = new BasicMarker(str);
            Marker marker = (Marker) this.markerMap.putIfAbsent(str, basicMarker);
            if (marker != null) {
                basicMarker = marker;
            }
        }
        return basicMarker;
    }

    @Override // org.slf4j.IMarkerFactory
    public boolean exists(String str) {
        if (str == null) {
            return false;
        }
        return this.markerMap.containsKey(str);
    }

    @Override // org.slf4j.IMarkerFactory
    public boolean detachMarker(String str) {
        return (str == null || this.markerMap.remove(str) == null) ? false : true;
    }

    @Override // org.slf4j.IMarkerFactory
    public Marker getDetachedMarker(String str) {
        return new BasicMarker(str);
    }
}
