package com.samenea.common.security.service.impl;

import com.samenea.common.security.model.Role;
import com.samenea.common.security.repository.RoleRepository;
import com.samenea.common.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 9/4/12
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String roleName) {
        return roleRepository.getRoleByName(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.getAll();
    }
}
