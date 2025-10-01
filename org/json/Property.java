package org.json;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

/* loaded from: L-out.jar:org/json/Property.class */
public class Property {
    public static JSONObject toJSONObject(Properties properties) {
        JSONObject jSONObject = new JSONObject(properties == null ? 0 : properties.size());
        if (properties != null && !properties.isEmpty()) {
            Enumeration<?> enumerationPropertyNames = properties.propertyNames();
            while (enumerationPropertyNames.hasMoreElements()) {
                String str = (String) enumerationPropertyNames.nextElement();
                jSONObject.put(str, properties.getProperty(str));
            }
        }
        return jSONObject;
    }

    public static Properties toProperties(JSONObject jSONObject) {
        Properties properties = new Properties();
        if (jSONObject != null) {
            for (Map.Entry entry : jSONObject.entrySet()) {
                Object value = entry.getValue();
                if (!JSONObject.NULL.equals(value)) {
                    properties.put(entry.getKey(), value.toString());
                }
            }
        }
        return properties;
    }
}
