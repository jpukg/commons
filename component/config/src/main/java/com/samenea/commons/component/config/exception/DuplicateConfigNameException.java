package com.samenea.commons.component.config.exception;

public class DuplicateConfigNameException extends ConfigServiceException {
    public DuplicateConfigNameException(String configName) {
        super(configName + "   id duplicated  ");
    }
}
