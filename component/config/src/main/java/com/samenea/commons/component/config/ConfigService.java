package com.samenea.commons.component.config;

import com.samenea.commons.component.config.model.Config;

import java.util.List;

/**
 * The ConfigService centralizes the management of and access to applications' configurations.
 * It provides a common interface for accessing and managing applications' properties.
 * <p/>
 * It maintains a list of applications' properties through standard .properties and .xml files.
 *
 * @author darbandsari
 */
public interface ConfigService {
    void addLocation(String configName, String location);

    void addProperty(String configName, String key, List<String> values);

    void changeProperty(String configName, String key, List<String> values);

    void addLocation(String location);

    void refresh();

    void syncToExternalResources();

    List<Config> getConfigs();

    Config getByName(String configName);

    boolean isKeyExist(String configName, String key);

    boolean removeLocation(String configName);
}
