package com.samenea.common.security.service;

import com.samenea.common.security.model.Role;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 9/4/12
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RoleService {
    Role getRoleByName(String roleName);

    List<Role> getAllRoles();
}
