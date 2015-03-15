package com.samenea.commons.component.utils;

import java.security.Principal;

/**
 * @author: Jalal Ashrafi
 * Date: 1/22/13
 */
public interface UserContext {
    /**
     * Current logged in user
     * @return current user, null if no user is logged in
     */
    public Principal currentUser();
}
