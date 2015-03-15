package com.samenea.commons.component.config.exception;

public class PropertyNotExistsException extends ConfigServiceException {
    public PropertyNotExistsException(String configName, String key) {
        super(key + " key is not exist " + configName);
    }
}
