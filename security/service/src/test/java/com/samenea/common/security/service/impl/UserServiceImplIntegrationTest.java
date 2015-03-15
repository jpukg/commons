package com.samenea.common.security.service.impl;

import com.samenea.common.security.model.User;
import com.samenea.common.security.repository.UserRepository;
import com.samenea.common.security.service.RoleService;
import com.samenea.common.security.service.UserService;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/10/12
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {
        "classpath:applicationContext.xml"
})
public class UserServiceImplIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    public static final String ADMIN = "admin";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    @Autowired
    RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext(unitName = "ApplicationEntityManager")
    EntityManager entityManager;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSuccessAttemptUserLogin() throws Exception {
        User user = userService.findUsersByUsername(ADMIN);
        userService.failedAttemptUserLogin(ADMIN);
        Assert.assertTrue(user.isAccountNonLocked());
        userService.failedAttemptUserLogin(ADMIN);
        Assert.assertFalse(user.isAccountNonLocked());
        userService.successAttemptUserLogin(user);
        Assert.assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void testFailedAttemptUserLogin() throws Exception {
        User user = userService.findUsersByUsername(ADMIN);
        userService.failedAttemptUserLogin(ADMIN);
        Assert.assertTrue(user.isAccountNonLocked());
        userService.failedAttemptUserLogin(ADMIN);
        Assert.assertFalse(user.isAccountNonLocked());
        Thread.sleep(2000);
        user = userService.findUsersByUsername(ADMIN);
        Assert.assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void createUser_should_create_user_enabled(){
        User user = userService.createUser("userName","password");
        assertNotNull(user);
        Assert.assertTrue(user.isEnabled());
        Assert.assertFalse(user.isAccountExpired());
        Assert.assertTrue(user.isAccountNonLocked());
        Assert.assertTrue(user.isAccountNonExpired());
        assertEquals("userName",user.getUsername());
        System.out.printf(user.getPassword());
    }
    @Test(expected = DataIntegrityViolationException.class)
    public void createUser_should_allow_duplicate_username(){
        userService.createUser("userName","password");
        userService.createUser("userName", "password1");
        entityManager.flush();

    }

    @Test
    public void changePassword_should_change_pass(){
        String userName = "userName";
        userService.createUser(userName,"password");
        String newPass = "newPass";

        userService.changePassword(userName,"password", newPass);
        User user = userRepository.getByUserName(userName);
        assertEquals(passwordEncoder.encodePassword(newPass,""),user.getPassword());

    }
    @Test(expected = IncorrectPasswordException.class)
    public void changePassword_should_raise_error_if_old_pass_is_incorrect(){
        String userName = "userName";
        userService.createUser(userName,"password");
        String newPass = "newPass";
        userService.changePassword(userName, "incorrect", newPass);
    }
    @Test
    public void changeUserInfo_should_change_info(){
        String userName = "userName";

        String firstaName = "firstaName";
        String lastName = "lastName";
        String email = "email";
        String phoneNumber = "phoneNumber";
        userService.createUser(userName,"password");
        userService.changeUserInfo(userName, firstaName, lastName, email, phoneNumber);
        User user = userRepository.getByUserName(userName);
        assertEquals(firstaName, user.getEnFirstName());
        assertEquals(lastName,user.getEnLastName());
        assertEquals(email,user.getEmail());
        assertEquals(phoneNumber,user.getPhone());
    }
    @Test
    public void assignRole_should_add_role_to_user(){
        String userName = "userName";
        User user = userService.createUser(userName, "password");

        userService.assignRole(userName,"ROLE_ADMIN");
        User byUserName = userRepository.getByUserName(userName);
        assertEquals(1,byUserName.getRoles().size());
        assertEquals(1,byUserName.getAuthorities().size());
    }
    @Test
    public void removeRole_should_remove_role_from_user(){
        String userName = "userName";
        User user = userService.createUser(userName, "password");
        userService.assignRole(userName,ROLE_ADMIN);
        userService.removeRole(userName,ROLE_ADMIN);
        User byUserName = userRepository.getByUserName(userName);
        assertEquals(0,byUserName.getRoles().size());
        assertEquals(0,byUserName.getAuthorities().size());
    }


}
