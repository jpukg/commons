package com.samenea.commons.idgenerator.service;

import com.samenea.commons.component.utils.exceptions.SamenRuntimeException;

public class NoSuchGeneratorException extends SamenRuntimeException{
    public NoSuchGeneratorException() {
    }

    public NoSuchGeneratorException(String s) {
        super(s);
    }

    public NoSuchGeneratorException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NoSuchGeneratorException(Throwable throwable) {
        super(throwable);
    }
}
