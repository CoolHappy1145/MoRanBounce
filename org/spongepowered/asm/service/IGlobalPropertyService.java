package org.spongepowered.asm.service;

/* loaded from: L-out.jar:org/spongepowered/asm/service/IGlobalPropertyService.class */
public interface IGlobalPropertyService {
    IPropertyKey resolveKey(String str);

    Object getProperty(IPropertyKey iPropertyKey);

    void setProperty(IPropertyKey iPropertyKey, Object obj);

    Object getProperty(IPropertyKey iPropertyKey, Object obj);

    String getPropertyString(IPropertyKey iPropertyKey, String str);
}
