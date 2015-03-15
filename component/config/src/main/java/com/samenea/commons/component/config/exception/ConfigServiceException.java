package com.samenea.commons.component.config.exception;

import com.samenea.commons.component.utils.exceptions.SamenRuntimeException;

public class ConfigServiceException extends SamenRuntimeException {
    public ConfigServiceException() {
        super();
    }

    public ConfigServiceException(String s) {
        super(s);
    }

    public ConfigServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ConfigServiceException(Throwable throwable) {
        super(throwable);
    }
}
