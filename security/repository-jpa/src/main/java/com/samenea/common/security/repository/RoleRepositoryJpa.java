package com.samenea.common.security.repository;

import com.samenea.common.security.model.Role;
import com.samenea.commons.model.repository.BasicRepositoryJpa;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;


@Repository
public class RoleRepositoryJpa extends BasicRepositoryJpa<Role, Long> implements RoleRepository {

    public RoleRepositoryJpa() {
        super(Role.class);
    }


    public Role getRoleByName(String roleName) {
        Query query = getEntityManager().createQuery("from Role where name=:roleName");
        query.setParameter("roleName", roleName);
        List<Role> roles = query.getResultList();
        if (roles.isEmpty()) {
            return null;
        } else {
            return roles.get(0);
        }
    }


    public void removeRole(String roleName) {
        Role role = getRoleByName(roleName);
        if (role == null) {
            return;
        }
        remove(role.getId());
    }


}
