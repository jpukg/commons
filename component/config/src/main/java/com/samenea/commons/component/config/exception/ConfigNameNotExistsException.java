package com.samenea.commons.component.config.exception;

public class ConfigNameNotExistsException extends ConfigServiceException {
    public ConfigNameNotExistsException(String configName) {
        super(configName + "   is not exist  ");
    }
}
