package com.samenea.commons.service.exceptions;

import com.samenea.commons.component.utils.exceptions.SamenRuntimeException;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 6/16/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommonServiceRuntimeException extends SamenRuntimeException{
    public CommonServiceRuntimeException() {
        super();
    }

    public CommonServiceRuntimeException(String s) {
        super(s);
    }

    public CommonServiceRuntimeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CommonServiceRuntimeException(Throwable throwable) {
        super(throwable);    
    }
}
