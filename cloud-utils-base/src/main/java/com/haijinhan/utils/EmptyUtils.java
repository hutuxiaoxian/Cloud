package com.haijinhan.utils;


import java.util.Collection;
import java.util.List;
import java.util.Map;

public class EmptyUtils {
    public static Boolean isEmpty(Object obj) {
        return obj == null;
    }
    public static Boolean isNotEmpty(Object obj) {
        return obj != null;
    }
    public static<T> Boolean isEmpty(List<T> array) {
        return array == null || array.size() == 0;
    }

    public static<T> Boolean isNotEmpty(List<T> array) {
        return array != null && array.size() > 0;
    }
    public static<K,V> Boolean isEmpty(Map<K,V> map) {
        return map == null || map.isEmpty();
    }

    public static<T> Boolean isEmpty(Collection<T> map) {
        return map == null || map.isEmpty();
    }
    public static<T> Boolean isNotEmpty(Collection<T> map) {
        return map != null && !map.isEmpty();
    }

}
