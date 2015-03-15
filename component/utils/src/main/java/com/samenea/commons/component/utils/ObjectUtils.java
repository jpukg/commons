package com.samenea.commons.component.utils;

/**
 * Created by IntelliJ IDEA.
 * User: javaee
 * Date: 5/27/11
 * Time: 1:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectUtils {
    public static boolean isAllNull(Object... objects) {
        for (Object o : objects) {
            if (isNotNull(o)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isExistInArray(Object src, Object... des) {
        for (Object o : des) {
            if (src.equals(o)) {
                return true;
            }
        }
        return false;

    }

    public static boolean isNotExistInArray(Object src, Object... des) {
        return !isExistInArray(src, des);
    }

    public static boolean isAnyNull(Object... objects) {
        for (Object o : objects) {
            if (isNull(o)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean isNotNull(Object o) {
        return o != null;
    }
}
