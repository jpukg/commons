package com.samenea.common.security.repository;

import com.samenea.common.security.model.Role;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 9/3/12
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoleRepositoryHibernateTest extends SecurityBaseRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetRoleByName() throws Exception {
        Role role_admin = roleRepository.getRoleByName("ROLE_ADMIN");
        Assert.assertNotNull(role_admin);
        Assert.assertEquals(role_admin.getName(), "ROLE_ADMIN");
        Assert.assertEquals(role_admin.getDescription(), "admin role");

    }
    @Test
    public void testRemoveRole() throws Exception {
        roleRepository.removeRole("ROLE_TEST");
        List<Role> all = roleRepository.getAll();

        Assert.assertEquals(all.size(),4);


    }
}
