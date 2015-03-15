package com.samenea.commons.component.utils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: javaee
 * Date: 5/27/11
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class CollectionUtils {
    public static <T> List<T> paramsAsList(T... objects) {

        List<T> ts = new ArrayList<T>();
        if(objects==null){
            return ts;
        }
        for (T t : objects) {
            ts.add(t);
        }
        return ts;
    }

    public static <T> List<T> arrayAsList(T[] objects) {
        List<T> ts = new ArrayList<T>();
        if(objects==null){
            return ts;
        }
        for (T t : objects) {
            ts.add(t);
        }
        return ts;
    }

    public static <T> Set<T> paramsAsSet(T... objects) {
        Set<T> ts = new HashSet<T>();
        if(objects==null){
            return ts;
        }
        for (T t : objects) {
            ts.add(t);
        }
        return ts;
    }

    public static <T> Set<T> arrayAsSet(T[] objects) {
        Set<T> ts = new HashSet<T>();
        if(objects==null){
            return ts;
        }
        for (T t : objects) {
            ts.add(t);
        }
        return ts;
    }

    public static <T> Set<T> emptySet() {
        return new HashSet<T>();
    }

    public static <T> List<T> emptyList() {
        return new ArrayList<T>();
    }

    public static <K, V> Map<K, V> emptyMap() {
        return new HashMap<K, V>();
    }


    public static <T> Set<T> collectionAsSet(Collection<T> collection) {
        return new HashSet<T>(collection);
    }

    public static <T> List<T> collectionAsList(Collection<T> collection) {
        return new ArrayList<T>(collection);
    }

    public static <T> Collection<T> union(Collection<T> first, Collection<T> second) {
        Collection<T> tmp = collectionFactory(first);
        if (ObjectUtils.isAnyNull(first, second)) {
            return tmp;
        }
        first.addAll(second);
        return tmp;

    }

    public static <T> Collection<T> intersection(Collection<T> first, Collection<T> second) {
        Collection<T> tmp = collectionFactory(first);
        if (ObjectUtils.isAnyNull(first, second)) {
            return tmp;
        }
        for (T x : first) {
            if (second.contains(x)) tmp.add(x);
        }
        return tmp;

    }

    public static <T> Collection<T> subtract(Collection<T> first, Collection<T> second) {
        Collection<T> tmp = collectionFactory(first);
        if (ObjectUtils.isAnyNull(first, second)) {
            return tmp;
        }
        for (T x : first) {
            if (!second.contains(x)) tmp.add(x);
        }
        return tmp;

    }

    private static <T> Collection<T> collectionFactory(Collection<T> obj) {
        return (obj instanceof Set) ? new HashSet<T>() : new ArrayList<T>();
    }
}
