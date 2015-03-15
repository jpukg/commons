package com.samenea.commons.component.utils;

import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;

public abstract class AssertUtil {
    public static <T extends Throwable> void isTrue(boolean expression, Class<T> clazz) throws T {
        try {
            Assert.isTrue(expression);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void isTrue(boolean expression, Class<T> clazz, String message) throws T {
        try {
            Assert.isTrue(expression);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void isFalse(boolean expression, Class<T> clazz, String message) throws T {
        isTrue(!expression, clazz, message);
    }

    public static <T extends Throwable> void isFalse(boolean expression, Class<T> clazz) throws T {
        isTrue(!expression, clazz);
    }


    public static <T extends Throwable> void isNull(java.lang.Object object, Class<T> clazz) throws T {
        try {
            Assert.isNull(object);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void isNull(java.lang.Object object, Class<T> clazz, String message) throws T {
        try {
            Assert.isNull(object);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void equals(Object object1, Object object2, Class<T> clazz) throws T {

        if (object1.equals(object2)) {
            return;
        }

        try {
            throw clazz.newInstance();
        } catch (IllegalAccessException throwable) {
            throwable.printStackTrace();
        } catch (InstantiationException throwable) {
            throwable.printStackTrace();
        }

    }

    public static <T extends Throwable> void equals(Object object1, Object object2, Class<T> clazz, String message) throws T {

        if (object1.equals(object2)) {
            return;
        }
        try {
            clazz.getConstructor(String.class);
            throw clazz.getConstructor(String.class).newInstance(message);
        } catch (IllegalAccessException throwable) {
            throwable.printStackTrace();
        } catch (InstantiationException throwable) {
            throwable.printStackTrace();
        } catch (NoSuchMethodException throwable) {
            throwable.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }


    }

    public static <T extends Throwable> void doesNotEqual(Object object1, Object object2, Class<T> clazz) throws T {

        if (!object1.equals(object2)) {
            return;
        }
        try {
            throw clazz.newInstance();
        } catch (IllegalAccessException throwable) {
            throwable.printStackTrace();
        } catch (InstantiationException throwable) {
            throwable.printStackTrace();
        }

    }

    public static <T extends Throwable> void doesNotEqual(Object object1, Object object2, Class<T> clazz, String message) throws T {

        if (!object1.equals(object2)) {
            return;
        }

        try {
            clazz.getConstructor(String.class);
            throw clazz.getConstructor(String.class).newInstance(message);
        } catch (IllegalAccessException throwable) {
            throwable.printStackTrace();
        } catch (InstantiationException throwable) {
            throwable.printStackTrace();
        } catch (NoSuchMethodException throwable) {
            throwable.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }
    }


    public static <T extends Throwable> void notNull(java.lang.Object object, Class<T> clazz) throws T {
        try {
            Assert.notNull(object);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void notNull(java.lang.Object object, Class<T> clazz, String message) throws T {
        try {
            Assert.notNull(object);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }


    public static <T extends Throwable> void hasLength(java.lang.String text, Class<T> clazz) throws T {
        try {
            Assert.hasLength(text);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void hasLength(java.lang.String text, Class<T> clazz, String message) throws T {
        try {
            Assert.hasLength(text);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void hasText(java.lang.String text, Class<T> clazz) throws T {
        try {
            Assert.hasText(text);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void hasText(java.lang.String text, Class<T> clazz, String message) throws T {
        try {
            Assert.hasText(text);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }


    public static <T extends Throwable> void doesNotContain(java.lang.String textToSearch, java.lang.String substring, Class<T> clazz) throws T {
        try {
            Assert.doesNotContain(textToSearch, substring);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void doesNotContain(java.lang.String textToSearch, java.lang.String substring, Class<T> clazz, String message) throws T {
        try {
            Assert.doesNotContain(textToSearch, substring);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }


    public static <T extends Throwable> void notEmpty(java.lang.Object[] array, Class<T> clazz) throws T {
        try {
            Assert.notEmpty(array);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void notEmpty(java.lang.Object[] array, Class<T> clazz, String message) throws T {
        try {
            Assert.notEmpty(array);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }


    public static <T extends Throwable> void noNullElements(java.lang.Object[] array, Class<T> clazz) throws T {
        try {
            Assert.noNullElements(array);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void noNullElements(java.lang.Object[] array, Class<T> clazz, String message) throws T {
        try {
            Assert.noNullElements(array);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }

    }


    public static <T extends Throwable> void notEmpty(java.util.Collection collection, Class<T> clazz) throws T {
        try {
            Assert.notEmpty(collection);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void notEmpty(java.util.Collection collection, Class<T> clazz, String message) throws T {
        try {
            Assert.notEmpty(collection);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }

    }


    public static <T extends Throwable> void notEmpty(java.util.Map map, Class<T> clazz) throws T {
        try {
            Assert.notEmpty(map);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void notEmpty(java.util.Map map, Class<T> clazz, String message) throws T {
        try {
            Assert.notEmpty(map);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }

    }


    public static <T extends Throwable> void isInstanceOf(java.lang.Class type, java.lang.Object obj, Class<T> clazz) throws T {
        try {
            Assert.isInstanceOf(type, obj);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void isInstanceOf(java.lang.Class type, java.lang.Object obj, Class<T> clazz, String message) throws T {
        try {
            Assert.isInstanceOf(type, obj);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }

    }


    public static <T extends Throwable> void isAssignable(java.lang.Class superType, java.lang.Class subType, Class<T> clazz) throws T {
        try {
            Assert.isAssignable(superType, subType);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void isAssignable(java.lang.Class superType, java.lang.Class subType, Class<T> clazz, String message) throws T {
        try {
            Assert.isAssignable(superType, subType);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }

    }


    public static <T extends Throwable> void state(boolean expression, Class<T> clazz) throws T {
        try {
            Assert.state(expression);
        } catch (Exception e) {
            try {
                throw clazz.newInstance();
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static <T extends Throwable> void state(boolean expression, Class<T> clazz, String message) throws T {
        try {
            Assert.state(expression);
        } catch (Exception e) {
            try {
                clazz.getConstructor(String.class);
                throw clazz.getConstructor(String.class).newInstance(message);
            } catch (IllegalAccessException throwable) {
                throwable.printStackTrace();
            } catch (InstantiationException throwable) {
                throwable.printStackTrace();
            } catch (NoSuchMethodException throwable) {
                throwable.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
    }

}