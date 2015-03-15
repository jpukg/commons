package com.samenea.common.security.repository;

import com.samenea.common.security.model.Role;
import com.samenea.commons.model.repository.BasicRepositoryHibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class RoleRepositoryHibernate extends BasicRepositoryHibernate<Role, Long> implements RoleRepository {

    public RoleRepositoryHibernate() {
        super(Role.class);
    }


    public Role getRoleByName(String roleName) {
        Query query = getSession().createQuery("from Role where name=:roleName");
        query.setParameter("roleName", roleName);
        List<Role> roles = query.list();
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
