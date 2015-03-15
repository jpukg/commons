package com.samenea.commons.component.config.exception;

public class DuplicatePropertyException extends ConfigServiceException {
    public DuplicatePropertyException(String configName, String key) {
        super(key + " key id duplicated in " + configName);
    }
}
