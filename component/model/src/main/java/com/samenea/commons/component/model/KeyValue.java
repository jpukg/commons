package com.samenea.commons.component.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.util.Assert;

@Embeddable
public class KeyValue<K,V> extends ValueObject {
    @Column
    private final K key;
    private final V value;

    private KeyValue() {
        this.key = null;
        this.value = null;
    }

    public KeyValue(K key, V value) {
        Assert.notNull(key);
        Assert.notNull(value);
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyValue keyValue = (KeyValue) o;

        if (!key.equals(keyValue.key)) return false;
        if (!value.equals(keyValue.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "KeyValue{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
