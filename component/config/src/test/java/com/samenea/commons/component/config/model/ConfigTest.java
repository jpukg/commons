package com.samenea.commons.component.config.model;

import com.samenea.commons.component.config.exception.DuplicatePropertyException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ConfigTest {
    final String CONFIG_NAME = "configName";
    private Config aConfig;

    @Before
    public void setUp() throws Exception {
        aConfig = new Config(CONFIG_NAME);

        aConfig.addProperty(new Property("name", "value1"));
        aConfig.addProperty(new Property("name2", "value2", "value3"));
        aConfig.addProperty("name3", "value4");
        aConfig.addProperty("name4", "value5", "value6", "value7");
    }

    @Test
    public void createANewConfigWithSpecifiedConfigName() {
        // Act
        Config config = new Config(CONFIG_NAME);

        // Assert
        assertNotNull(config);
        assertEquals(CONFIG_NAME, config.getName());

        assertNotNull(config.getProperties());
        assertEquals(0, config.getProperties().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNullConfigNameShouldThrowException() {
        new Config(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyConfigNameShouldThrowException() {
        new Config(" ");
    }

    @Test
    public void findPropertyValuesByItsKey() {
        List<String> name4 = aConfig.findValuesByKey("name4");

        assertNotNull(name4);
        assertEquals(name4.size(), 3);
        assertEquals(name4.get(0), "value5");
        assertEquals(name4.get(1), "value6");
        assertEquals(name4.get(2), "value7");
    }

    @Test
    public void findPropertyThatDoesNotExistsShouldRetunrNull() {
        List<String> notExistsValues = aConfig.findValuesByKey("notGiven");

        assertNull(notExistsValues);
    }

    @Test
    public void isKeyExistsForAnExistingPropertyShouldReturnTrue() {
        boolean keyExists = aConfig.isKeyExist("name3");

        assertTrue(keyExists);
    }

    @Test
    public void isKeyExistsForNotExistingPropertyShouldReturnFalse() {
        boolean keyExists = aConfig.isKeyExist("notGiven");

        assertFalse(keyExists);
    }

    @Test
    public void addANewProperty() {
        Config config = new Config(CONFIG_NAME);

        config.addProperty(new Property("name", "value"));

        assertEquals(1, config.getProperties().size());
    }

    @Test
    public void removeAnExistingPropertyProperty() {
        // Arrange
        Config config = new Config(CONFIG_NAME);

        // Act
        config.addProperty(new Property("name", "value"));
        config.addProperty(new Property("name2", "value3"));
        boolean removeResult = config.removeProperty("name2");
        List<String> removedValues = config.findValuesByKey("name2");

        // Assert
        assertEquals(1, config.getProperties().size());
        assertTrue(removeResult);
        assertNull(removedValues);
    }

    @Test
    public void removeNotExistingPropertyPropertyHasNoSideEffect() {
        // Arrange
        Config config = new Config(CONFIG_NAME);

        // Act
        config.addProperty(new Property("name", "value"));
        config.addProperty(new Property("name2", "value3"));
        boolean removeResult = config.removeProperty("notexists");

        // Assert
        assertFalse(removeResult);
        assertEquals(2, config.getProperties().size());

    }

    @Test(expected = DuplicatePropertyException.class)
    public void addDuplicatePropertyShouldThrowException() {
        aConfig.addProperty(new Property("name", "value"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullPropertyShouldThrowException() {
        Config config = new Config(CONFIG_NAME);
        config.addProperty(null);
    }
}
