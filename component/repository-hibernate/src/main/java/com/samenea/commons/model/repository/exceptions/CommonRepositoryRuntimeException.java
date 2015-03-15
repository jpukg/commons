package com.samenea.commons.model.repository.exceptions;

import com.samenea.commons.component.utils.exceptions.SamenRuntimeException;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 6/16/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommonRepositoryRuntimeException extends SamenRuntimeException{
    public CommonRepositoryRuntimeException() {
        super();
    }

    public CommonRepositoryRuntimeException(String s) {
        super(s);
    }

    public CommonRepositoryRuntimeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CommonRepositoryRuntimeException(Throwable throwable) {
        super(throwable);    
    }
}
