package com.samenea.common.security.repository;

import com.samenea.common.security.model.User;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 9/3/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRepositoryHibernateTest extends SecurityBaseRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetUsers() throws Exception {
        List<User> users = userRepository.getUsers();
        Assert.assertEquals(users.size(),4);
        for (User user : users) {
            Assert.assertNotNull(user.getId());
        }
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = new User("test","hello");
        user.setEmail("test@test.com");
        User user1 = userRepository.saveUser(user);
        Assert.assertNotNull(user1.getId());
        Assert.assertEquals(user1.getEmail(), user.getEmail());
        Assert.assertEquals(user1.getPassword(), user.getPassword());
        Assert.assertEquals(user1.getUsername(),user.getUsername());

    }



    @Test
    public void testLoadUserByUsername() throws Exception {
        UserDetails admin = userRepository.loadUserByUsername("admin");

        Assert.assertNotNull(admin);

        Assert.assertEquals(admin.getUsername(),"admin");
    }

    @Test
    public void testFindUsersByRole() throws Exception {
        User admin = userRepository.findUsersByRole("ROLE_ADMIN").get(0);

        Assert.assertNotNull(admin);
        Assert.assertEquals(admin.getEmail(), "admin@samenea.com");
        Assert.assertEquals(admin.getUsername(),"admin");
    }

    @Test
    public void testGetUserPassword() throws Exception {
        String admin = userRepository.getUserPassword("admin");
        Assert.assertEquals(admin,"7c4a8d09ca3762af61e59520943dc26494f8941b");
    }

    @Test
    public void testLoadUserByEmail() throws Exception {
        User admin = userRepository.loadUserByEmail("admin@samenea.com");

        Assert.assertNotNull(admin);
        Assert.assertEquals(admin.getEmail(), "admin@samenea.com");
        Assert.assertEquals(admin.getUsername(),"admin");
    }
}
