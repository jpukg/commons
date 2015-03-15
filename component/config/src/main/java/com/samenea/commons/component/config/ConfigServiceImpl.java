package com.samenea.commons.component.config;

import com.samenea.commons.component.config.exception.ConfigNameNotExistsException;
import com.samenea.commons.component.config.exception.ConfigServiceException;
import com.samenea.commons.component.config.exception.DuplicateConfigNameException;
import com.samenea.commons.component.config.exception.PropertyNotExistsException;
import com.samenea.commons.component.config.model.Config;
import com.samenea.commons.component.config.model.Property;
import com.samenea.commons.component.utils.AssertUtil;
import com.samenea.commons.component.utils.FileUtils;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;


public class ConfigServiceImpl implements ConfigService {
    public static final String FILE_SEPARATOR = "/";
    Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

    public enum ConfigFileType {XML, PROPERTIES}

    private static String DEFAULT_CONFIG_ROOT_PATH = System.getProperty("user.home").replace("\\",FILE_SEPARATOR);
    public static final String VALUES_SEPARATOR = ",";

    private Map<String, String> locations;
    private List<Config> configs = new ArrayList<Config>();
    private String configRootPath = DEFAULT_CONFIG_ROOT_PATH;
    private VirtualFile configRootVFS;

    public VirtualFile getConfigRootVFS() {
        if (configRootVFS == null) {
            configRootVFS = VFS.getChild(configRootPath);
        }
        return configRootVFS;
    }


    public ConfigServiceImpl(Map<String, String> locations) {
        AssertUtil.notEmpty(locations, IllegalArgumentException.class);

        setLocations(locations);

    }

    public ConfigServiceImpl(String... locations) {
        AssertUtil.notEmpty(locations, IllegalArgumentException.class);

        this.locations = new HashMap<String, String>();
        setLocations(locations);

    }


    public void init() {
        refresh();
    }

    public Map<String, String> getLocations() {
        return locations;
    }

    public void setConfigRootPath(String configRootPath) {
        if (configRootPath != null && !configRootPath.trim().isEmpty()) {
            this.configRootPath = configRootPath.replace("\\",FILE_SEPARATOR);
        }
    }

    private void setLocations(Map<String, String> locations) {
        this.locations = locations;
    }

    protected void resetConfigs() {
        configs = new ArrayList<Config>();
    }

    private void setLocations(String... locations) {
        for (String location : locations) {
            String key = extractFileNameFromPath(location);
            AssertUtil.isFalse(isConfigNameExist(key), DuplicateConfigNameException.class, key);
            this.locations.put(key, location);
        }
    }

    @Override
    public void addLocation(String location) {
        AssertUtil.hasText(location, IllegalArgumentException.class);
        String key = extractFileNameFromPath(location);
        AssertUtil.isFalse(isConfigNameExist(key), DuplicateConfigNameException.class, key);
        locations.put(key, location);
        refresh();
    }

    @Override
    public void addLocation(String configName, String location) {
        AssertUtil.hasText(configName, IllegalArgumentException.class);
        AssertUtil.hasText(location, IllegalArgumentException.class);
        AssertUtil.isFalse(isConfigNameExist(configName), DuplicateConfigNameException.class, configName);
        locations.put(configName, location);
        refresh();
    }


    @Override
    public boolean removeLocation(String configName) {
        boolean removed = locations.remove(configName) != null;
        if (removed) {
            refresh();
        }
        return removed;

    }

    @Override
    public void addProperty(String configName, String key, List<String> values) {
        AssertUtil.hasText(configName, IllegalArgumentException.class);
        AssertUtil.isTrue(isConfigNameExist(configName), ConfigNameNotExistsException.class, configName);
        getByName(configName).addProperty(new Property(key, values));
        syncToExternalResources();
        refresh();
    }

    @Override
    public void changeProperty(String configName, String key, List<String> values) {
        AssertUtil.hasText(configName, IllegalArgumentException.class);
        AssertUtil.isTrue(isConfigNameExist(configName), ConfigNameNotExistsException.class, configName);
        Config config = getByName(configName);
        boolean removed = config.removeProperty(key);
        if (!removed) {
            throw new PropertyNotExistsException(configName, key);
        }
        getByName(configName).addProperty(new Property(key, values));
        syncToExternalResources();
        refresh();
    }

    @Override
    public boolean isKeyExist(String configName, String key) {
        Config config = getByName(configName);
        if (config == null) {
            return false;
        }
        return config.isKeyExist(key);
    }

    @Override
    public void refresh() {
        resetConfigs();
        for (Map.Entry<String, String> location : getLocations().entrySet()) {
            Properties properties = new Properties();
            try {
                String locationValue = resolveLocationPath(location);
                InputStream stream = openStreamAndCopyFromPathIfNotExist(locationValue);
                AssertUtil.notNull(stream, ConfigServiceException.class, "Can not load " + location);
                ConfigFileType configFileType = resolveFileType(locationValue);

                switch (configFileType) {
                    case XML: {
                        properties.loadFromXML(stream);
                        break;
                    }
                    case PROPERTIES: {
                        properties.load(stream);
                        break;
                    }
                }

                createConfigObject(location.getKey(), properties.entrySet());
            } catch (IOException e) {
                throw new ConfigServiceException("Can not open Stream " + location, e);
            }
        }
    }

    private ConfigFileType resolveFileType(String locationValue) {
        return locationValue.endsWith(".properties") ? ConfigFileType.PROPERTIES : ConfigFileType.XML;
    }


    @Override
    public void syncToExternalResources() {
        List<Config> configs = getConfigs();
        for (Config config : configs) {
            String location = getLocations().get(config.getName());
            Properties properties = new Properties();
            for (Property property : config.getProperties()) {
                properties.setProperty(property.getKey(), valuesAsString(property));
                try {
                    String locationValue = resolveLocationPath(location);
                    VirtualFile configRootVFSChild = getConfigRootVFS().getChild(locationValue);
                    AssertUtil.isTrue(configRootVFSChild.exists(), IOException.class, configRootVFSChild.getPathName() + " not exist");
                    if (log.isInfoEnabled()) {
//                        log.info(path + " is updated");
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(configRootVFSChild.getPhysicalFile());
                    ConfigFileType configFileType = resolveFileType(locationValue);
                    switch (configFileType) {
                        case XML: {
                            properties.storeToXML(fileOutputStream, config.getName());
                            break;
                        }
                        case PROPERTIES: {
                            properties.store(fileOutputStream, config.getName());
                            break;
                        }
                    }

                } catch (IOException e) {
                    throw new ConfigServiceException(e);
                }
            }
        }
    }

    private InputStream openStreamAndCopyFromPathIfNotExist(String locationValue) throws IOException {
        VirtualFile childPath = getConfigRootVFS().getChild(locationValue);

        if (!childPath.exists()) {
            URL resource = getClass().getResource(locationValue);
            AssertUtil.notNull(resource, IOException.class, childPath.getPathName() + " not exist");
            FileUtils.createFile(childPath.getPathName(), resource.openStream());
        }
        return childPath.openStream();
    }

    @Override
    public Config getByName(String configName) {
        for (Config config : configs) {
            if (config.getName().equalsIgnoreCase(configName)) {
                return config;
            }
        }
        return null;
    }

    @Override
    public List<Config> getConfigs() {
        return configs;
    }

    private String extractFileNameFromPath(String filePath) {
        int slashIndex = filePath.lastIndexOf(FILE_SEPARATOR) + 1;
        int dotPropertiesIndex = filePath.lastIndexOf(".");
        return filePath.substring(slashIndex, dotPropertiesIndex);
    }

    private boolean isConfigNameExist(String configName) {
        return this.locations.containsKey(configName);
    }

    protected String resolveLocationPath(Map.Entry<String, String> location) {
        String value = location.getValue();
        return resolveLocationPath(value);
    }

    protected String resolveLocationPath(String value) {
        return value.trim().startsWith(FILE_SEPARATOR) ? value.trim() : FILE_SEPARATOR + value.trim();
    }

    protected String valuesAsString(Property property) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> iterator = property.getValues().iterator();
        while (iterator.hasNext()) {
            stringBuffer.append(iterator.next());
            if (iterator.hasNext()) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    protected void createConfigObject(String fileName, Set<Map.Entry<Object, Object>> entries) {
        Config config = new Config(fileName);
        for (Map.Entry<Object, Object> entry : entries) {
            String key = entry.getKey().toString();
            String[] split = entry.getValue().toString().split(VALUES_SEPARATOR);
            config.addProperty(key, split);
        }
        getConfigs().add(config);
    }
}
