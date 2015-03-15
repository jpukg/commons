package com.samenea.commons.service.exceptions;

import com.samenea.commons.component.utils.exceptions.SamenException;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 6/16/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommonServiceException extends SamenException{
    public CommonServiceException() {
        super();    
    }

    public CommonServiceException(String s) {
        super(s);    
    }

    public CommonServiceException(String s, Throwable throwable) {
        super(s, throwable);    
    }

    public CommonServiceException(Throwable throwable) {
        super(throwable);    
    }
}
