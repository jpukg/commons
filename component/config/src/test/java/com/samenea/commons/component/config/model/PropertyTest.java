package com.samenea.commons.component.config.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PropertyTest {
    private String key;
    private String value1 = "value1";
    private String value2 = "value2";
    private List<String> values;

    @Before
    public void setup() {
        key = "key1";
        value1 = "value1";
        value2 = "value2";

        values = new ArrayList<String>();
        values.add(value1);
        values.add(value2);
    }

    @Test
    public void testConstructor() {
        final String pName = "pName";
        final String pValue = "pValue";
        final String pValue2 = "pValue";
        Property p = new Property(pName, pValue, pValue2);
        Assert.assertEquals(pName, p.getKey());
        Assert.assertEquals(pValue, p.getValues().get(0));
        Assert.assertEquals(pValue2, p.getValues().get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullKeyShouldThrowException() {
        key = null;
        Property property = new Property(key, values);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyKeyShouldThrowException() {
        key = "";
        Property property = new Property(key, values);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyValuesShouldThrowException() {
        List<String> emptyValues = new ArrayList<String>();

        Property property = new Property(key, emptyValues);
    }

    @Test
    public void testToString() {
        final String toStringExpected = "Property{key='key1', values=[value1, value2]}";

        Property property = new Property(key, values);

        String tostring = property.toString();

        Assert.assertEquals("toString method doesn't work properly", toStringExpected, tostring);
    }
}
