package com.samenea.common.security.repository;

import com.samenea.common.security.model.Role;
import com.samenea.commons.component.model.BasicRepository;


public interface RoleRepository extends BasicRepository<Role, Long> {

    Role getRoleByName(String roleName);


    void removeRole(String roleName);
}
