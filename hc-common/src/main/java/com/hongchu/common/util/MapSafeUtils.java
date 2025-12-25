package com.hongchu.common.util;

import java.util.List;
import java.util.Map;

public class MapSafeUtils {

    /**
     * 安全获取整数值
     */
    public static Integer safeGetInteger(Map<String, Object> map, String key) {
        return safeGetInteger(map, key, 0);
    }
    
    /**
     * 安全获取整数值，带默认值
     */
    public static Integer safeGetInteger(Map<String, Object> map, String key, Integer defaultValue) {
        if (map == null) return defaultValue;
        Object value = map.get(key);
        if (value == null) return defaultValue;
        try {
            if (value instanceof Number) return ((Number) value).intValue();
            else return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * 安全转换为字符串
     */
    public static String safeToString(Object obj) {
        if (obj == null) return "";
        return obj.toString();
    }
    
    /**
     * 安全转换为字符串，带默认值
     */
    public static String safeToString(Object obj, String defaultValue) {
        if (obj == null) return defaultValue;
        return obj.toString();
    }

    /**
     * 安全获取字符串值，支持多个可能的key
     */
    public static String safeGetString(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            Object value = map.get(key);
            if (value != null) return value.toString();
        }
        return "";
    }

    /**
     * 安全获取Long值
     */
    public static Long safeGetLong(Map<String, Object> map, String key) {
        return safeGetLong(map, key, 0L);
    }

    /**
     * 安全获取Long值，带默认值
     */
    public static Long safeGetLong(Map<String, Object> map, String key, Long defaultValue) {
        if (map == null) return defaultValue;
        Object value = map.get(key);
        if (value == null) return defaultValue;
        try {
            if (value instanceof Number) return ((Number) value).longValue();
            else return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 安全获取List
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> safeGetList(Map<String, Object> map, String key) {
        if (map == null) return null;
        Object value = map.get(key);
        if (value instanceof List) {
            try {
                return (List<T>) value;
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }
}