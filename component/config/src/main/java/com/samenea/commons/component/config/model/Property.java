package com.samenea.commons.component.config.model;

import com.samenea.commons.component.model.ValueObject;
import com.samenea.commons.component.utils.CollectionUtils;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

/**
 * An element of configuration data representing a property.
 * A property may have single or multiple string values separated with comma.
 * The property is described with a key that uniquely identified it among other properties.
 * Only values are modifiable.
 *
 * @author darbandsari
 */
@Embeddable
public class Property extends ValueObject {
    @Column
    final private String key;

    @ElementCollection
    @CollectionTable(name = "property_values", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "values")
    private final List<String> values;

    public Property(String key, List<String> values) {
        Assert.notNull(key);
        Assert.hasLength(key);
        Assert.notEmpty(values);

        this.key = key;
        this.values = values;
    }

    public Property(String key, String... values) {
        this(key, CollectionUtils.arrayAsList(values));
    }

    public String getKey() {
        return key;
    }

    public List<String> getValues() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        if (key != null ? !key.equals(property.key) : property.key != null) return false;
        if (values != null ? !values.equals(property.values) : property.values != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (values != null ? values.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Property");
        sb.append("{key='").append(key).append('\'');
        sb.append(", values=").append(values);
        sb.append('}');
        return sb.toString();
    }
}
