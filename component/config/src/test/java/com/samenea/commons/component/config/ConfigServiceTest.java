package com.samenea.commons.component.config;

import com.samenea.commons.component.config.exception.*;
import com.samenea.commons.component.config.model.Config;
import com.samenea.commons.component.utils.CollectionUtils;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.jboss.vfs.VirtualFile;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public abstract class ConfigServiceTest {
    protected String config1;
    protected String config2;

    @Before
    public void before() throws IOException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("config1", config1);
        map.put("config2", config2);
        VirtualFile config = new ConfigServiceImpl(map).getConfigRootVFS().getChild("config");
        if(config.exists()){
      //      FileUtils.deleteDirectory(config.getPhysicalFile());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNoLocationThrowsAnException() throws Exception {
        new ConfigServiceImpl();
    }

    @Test(expected = ConfigServiceException.class)
    public void createWithNotExistsLocationThrowsAnException() throws Exception {
        new ConfigServiceImpl("notexist.nowhere").refresh();

    }

    @Test
    public void shouldSetConfigNameCorrectlyIfItIsSetExplicitly() throws Exception {
        // Arrange
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("config1", config1);
        ConfigService configService = new ConfigServiceImpl(map);
        configService.refresh();
        // Act
        List<Config> configs = configService.getConfigs();

        // Assert
        Assert.assertEquals(configs.size(), 1);
        Assert.assertEquals(configs.get(0).getName(), "config1");
    }

    @Test
    public void shouldSetFileNameASConfigNameIfItIsNotSetExplicitly() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config2, config1);
        configService.refresh();
        // Act
        List<Config> configs = configService.getConfigs();

        // Assert
        Assert.assertEquals(configs.size(), 2);
        Assert.assertTrue(configs.get(0).getName().equals("config2") || configs.get(0).getName().equals("config1"));
        Assert.assertTrue(configs.get(1).getName().equals("config2") || configs.get(1).getName().equals("config1"));
    }

    @Test
    public void shouldSetConfigNameEqualsToConfigServiceName() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config2, config1);
        configService.refresh();
        // Act
        List<Config> configs = configService.getConfigs();

        // Assert
        Assert.assertEquals(configs.size(), 2);
        Assert.assertNull(configService.getByName("notgiven"));
        Assert.assertEquals(configService.getByName("config2").getName(), "config2");
        Assert.assertEquals(configService.getByName("config1").getName(), "config1");
    }


    @Test
    public void addNewLocationToConfigService() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.addLocation("config22", config2);
        configService.refresh();
        // Act
        List<Config> configs = configService.getConfigs();

        // Assert
        Assert.assertEquals(configs.size(), 2);

        Assert.assertTrue(configs.get(0).getName().equals("config22") || configs.get(0).getName().equals("config1"));
        Assert.assertTrue(configs.get(1).getName().equals("config22") || configs.get(1).getName().equals("config1"));
    }

    @Test
    public void removeAlreadyExistLocation() {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config2, config1);
        configService.refresh();
        // Act
        boolean removeExistLocation = configService.removeLocation("config2");

        // Assert
        Assert.assertEquals(configService.getConfigs().size(), 1);
        Assert.assertTrue("Can not remove already exist location", removeExistLocation);
    }

    @Test
    public void removeNotExistLocationDoesntHaveAnySideEffect() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config2, config1);
        configService.refresh();
        // Act
        boolean removeNotExistLocation = configService.removeLocation("notGiven");

        // Assert
        Assert.assertEquals(configService.getConfigs().size(), 2);
        Assert.assertFalse("Remove not exist location", removeNotExistLocation);
    }

    @Test
    public void shouldReadPropertiesValuesAlreadyDefinedInConfigFile() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        // Act
        List<Config> configs = configService.getConfigs();
        Config config = configService.getByName("config1");
        List<String> sendIntervalTime = config.findValuesByKey("sendIntervalTime");
        List<String> updateIntervalTime = config.findValuesByKey("updateIntervalTime");

        // Assert
        Assert.assertNotNull(config);
        Assert.assertEquals(1, configs.size());
        Assert.assertNull(config.findValuesByKey("notGiven"));

        Assert.assertNotNull(sendIntervalTime);
        Assert.assertEquals(sendIntervalTime.size(), 3);
        Assert.assertEquals("20000", sendIntervalTime.get(0));
        Assert.assertEquals("98777", sendIntervalTime.get(1));
        Assert.assertEquals("test", sendIntervalTime.get(2));

        Assert.assertNotNull(updateIntervalTime);
        Assert.assertEquals(1, updateIntervalTime.size());
        Assert.assertEquals("40000", updateIntervalTime.get(0));
    }

    @Test(expected = DuplicateConfigNameException.class)
    public void addDuplicateLocationShouldThrowException() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        // Act
        configService.addLocation("config1", config1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullLocationShouldThrowException() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        // Act
        configService.addLocation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addEmptyLocationShouldThrowException() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        // Act
        configService.addLocation("");
    }

    @Test(expected = ConfigNameNotExistsException.class)
    public void shouldThrowExceptionWhileChangingAPropertyFromNotExistConfig() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        // Act
        configService.changeProperty("notGiven", "sds", CollectionUtils.paramsAsList("v1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhileChangingAPropertyWhenConfigNameIsEmpty() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        // Act
        configService.changeProperty(" ", "sds", CollectionUtils.paramsAsList("v1"));
    }

    @Test(expected = ConfigNameNotExistsException.class)
    public void shouldThrowExceptionWhileAddingAPropertyFromNotExistConfig() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        // Act
        configService.addProperty("notGiven", "sds", CollectionUtils.paramsAsList("v1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhileAddingAPropertyWhenConfigNameIsEmpty() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        // Act
        configService.addProperty(" ", "sds", CollectionUtils.paramsAsList("v1"));
    }

    @Test(expected = DuplicatePropertyException.class)
    public void addDuplicatePropertyShouldThrowException() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        String testValue1 = "testValue1";
        String testValue2 = "testValue2";
        List<String> values = CollectionUtils.paramsAsList(testValue1, testValue2);

        // Act
        configService.addProperty("config1", "alreadyExists", values);
    }

    @Test
    @Ignore
    public void addANewProperty() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        String testValue1 = "testValue1";
        String testValue2 = "testValue2";
        List<String> values = CollectionUtils.paramsAsList(testValue1, testValue2);

        // Act
        configService.addProperty("config1", "testProperty", values);
        List<String> valuesByKey = configService.getByName("config1").findValuesByKey("testProperty");

        // Assert
        Assert.assertEquals(valuesByKey.size(), 2);
        Assert.assertEquals(valuesByKey.get(0), testValue1);
        Assert.assertEquals(valuesByKey.get(1), testValue2);

    }

    @Test(expected = PropertyNotExistsException.class)
    public void changeAPropertyThatNotExistsShouldThrowException() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        String testValue1 = "testValue1";
        String testValue2 = "testValue2";
        List<String> values = CollectionUtils.paramsAsList(testValue1, testValue2);

        // Act
        configService.changeProperty("config1", "notGiven", values);
    }

    @Test
    public void changeAPropertyValuesThatAlreadyExists() throws Exception {
        // Arrange
        ConfigService configService = new ConfigServiceImpl(config1);
        configService.refresh();
        String testValue1 = "testValue1";
        String testValue2 = "testValue2";
        List<String> values = CollectionUtils.paramsAsList(testValue1, testValue2);

        // Act
        configService.changeProperty("config1", "modificationkey", CollectionUtils.paramsAsList(testValue1, testValue2));
        List<String> valuesByKey = configService.getByName("config1").findValuesByKey("modificationkey");
        Assert.assertEquals(valuesByKey.size(), 2);
        Assert.assertEquals(valuesByKey.get(0), testValue1);
        Assert.assertEquals(valuesByKey.get(1), testValue2);
    }

}
