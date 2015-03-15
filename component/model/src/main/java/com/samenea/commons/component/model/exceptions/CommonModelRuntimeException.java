package com.samenea.commons.component.model.exceptions;

import com.samenea.commons.component.utils.exceptions.SamenRuntimeException;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 6/16/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommonModelRuntimeException extends SamenRuntimeException{
    public CommonModelRuntimeException() {
        super();
    }

    public CommonModelRuntimeException(String s) {
        super(s);
    }

    public CommonModelRuntimeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CommonModelRuntimeException(Throwable throwable) {
        super(throwable);    
    }
}
