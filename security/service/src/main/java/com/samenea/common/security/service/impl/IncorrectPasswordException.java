package com.samenea.common.security.service.impl;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author: Jalal Ashrafi
 * Date: 10/30/13
 */
public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
