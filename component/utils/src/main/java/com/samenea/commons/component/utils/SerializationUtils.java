package com.samenea.commons.component.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.util.SerializationUtils.deserialize;

public class SerializationUtils {
    public static <T extends Serializable> T clone(T userCard) {
        return (T) deserialize(org.springframework.util.SerializationUtils.serialize(userCard));
    }

    public static <T extends Serializable> List<T> cloneList(List<T> list) {
        List<T> ts = CollectionUtils.<T>emptyList();
        for (T t : list) {
            ts.add(clone(t));
        }
        return ts;
    }

    public static <T extends Serializable> Set<T> cloneSet(Set<T> set) {
        Set<T> ts = CollectionUtils.<T>emptySet();
        for (T t : set) {
            ts.add(clone(t));
        }
        return ts;
    }

    public static <K extends Serializable, V extends Serializable> Map<K, V> cloneMap(Map<K, V> map) {
        Map<K, V> kvMap = CollectionUtils.<K, V>emptyMap();
        Set<Map.Entry<K, V>> entries = map.entrySet();
        for (Map.Entry<K, V> ts : entries) {
            kvMap.put(clone(ts.getKey()), clone(ts.getValue()));
        }
        return kvMap;
    }
}
