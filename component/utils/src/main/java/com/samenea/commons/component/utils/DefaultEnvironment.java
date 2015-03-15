package com.samenea.commons.component.utils;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;

public class DefaultEnvironment implements Environment, Serializable {
//    make user UserContext optional for applications which don't need it
    @Autowired(required = false)
    UserContext userContext;
	@Override
	public Date getCurrentDate() {
			return Calendar.getInstance().getTime();
	}

    @Override
    public Principal currentUser() {
        //Informs applications which already was using Environment that a userContext is needed
        if (userContext == null){
            throw new UnsupportedOperationException("A user context implementation bean is not injected," +
                    " maybe there is no bean in jars in class path or the context is not included");
        }
        return userContext.currentUser();
    }
}
