package com.graph.framework.resources;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Create by xiaow on 2018/11/13
 */
public class MapConfig implements Config {

    private static final Map<String, Object> PROPERTIES_MAP = Maps.newHashMap();

    public MapConfig(Map<String, Object> kv) {
        PROPERTIES_MAP.putAll(kv);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        String val = (String) PROPERTIES_MAP.get(key);
        return StringUtils.isNotEmpty(val) ? val : defaultValue;
    }

    @Override
    public Integer getIntProperty(String key, Integer defaultValue) {
        Integer val = (Integer) PROPERTIES_MAP.get(key);
        return null != val ? val : defaultValue;
    }

    @Override
    public Long getLongProperty(String key, Long defaultValue) {
        Long val = (Long) PROPERTIES_MAP.get(key);
        return null != val ? val : defaultValue;
    }

    @Override
    public Short getShortProperty(String key, Short defaultValue) {
        Short val = (Short) PROPERTIES_MAP.get(key);
        return null != val ? val : defaultValue;
    }

    @Override
    public Float getFloatProperty(String key, Float defaultValue) {
        Float val = (Float) PROPERTIES_MAP.get(key);
        return null != val ? val : defaultValue;
    }

    @Override
    public Double getDoubleProperty(String key, Double defaultValue) {
        Double val = (Double) PROPERTIES_MAP.get(key);
        return null != val ? val : defaultValue;
    }

    @Override
    public Byte getByteProperty(String key, Byte defaultValue) {
        Byte val = (Byte) PROPERTIES_MAP.get(key);
        return null != val ? val : defaultValue;
    }

    @Override
    public Boolean getBooleanProperty(String key, Boolean defaultValue) {
        Boolean val = (Boolean) PROPERTIES_MAP.get(key);
        return null != val ? val : defaultValue;
    }

    @Override
    public String[] getArrayProperty(String key, String delimiter, String[] defaultValue) {
        return new String[0];
    }

    @Override
    public Date getDateProperty(String key, Date defaultValue) {
        return null;
    }

    @Override
    public Date getDateProperty(String key, String format, Date defaultValue) {
        return null;
    }

    @Override
    public Date getDateProperty(String key, String format, Locale locale, Date defaultValue) {
        return null;
    }

    @Override
    public <T extends Enum<T>> T getEnumProperty(String key, Class<T> enumType, T defaultValue) {
        return null;
    }

    @Override
    public long getDurationProperty(String key, long defaultValue) {
        return 0;
    }

    @Override
    public Set<String> getPropertyNames() {
        return PROPERTIES_MAP.keySet();
    }
}
