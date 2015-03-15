package com.samenea.commons.component.config.model;

import com.samenea.commons.component.config.exception.DuplicatePropertyException;
import com.samenea.commons.component.model.Entity;
import org.springframework.util.Assert;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Config groups logically a set of properties.
 * It contains a list of Property objects and properties and methods to access them.
 *
 * @author darbandsari
 */
@javax.persistence.Entity
public class Config extends Entity<Long> {
    final private String name;
    final private int priority;

    @ElementCollection
    private List<Property> properties;

    private Config() {
        properties = null;
        name = null;
        priority = Integer.MIN_VALUE;
    }

    public Config(String configName, int priority) {
        Assert.hasText(configName);
        properties = new ArrayList<Property>();
        this.name = configName;
        this.priority = priority;
    }

    public Config(String configName) {
        Assert.hasText(configName);
        properties = new ArrayList<Property>();
        this.name = configName;
        priority = Integer.MIN_VALUE;
    }

    public List<Property> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void addProperty(Property property) {
        Assert.notNull(property);

        if (isKeyExist(property.getKey())) {
            throw new DuplicatePropertyException(property.getKey(), name);
        }
        properties.add(property);
    }

    public void addProperty(String key, String... values) {
        addProperty(new Property(key, values));
    }

    public boolean isKeyExist(String key) {
        return findPropertyByKey(key) != null;
    }

    public Property findPropertyByKey(String key) {
        for (Property property : properties) {
            if (property.getKey().equals(key)) {
                return property;
            }
        }
        return null;
    }

    public boolean removeProperty(String key) {
        return properties.remove(findPropertyByKey(key));
    }

    public List<String> findValuesByKey(String key) {
        Property propertyByKey = findPropertyByKey(key);
        return propertyByKey == null ? null : propertyByKey.getValues();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;


        Config config = (Config) o;

        if (name != null ? !name.equals(config.name) : config.name != null) return false;
        if (properties != null ? !properties.equals(config.properties) : config.properties != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = properties.hashCode();
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Config");
        sb.append("{properties=").append(properties);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
