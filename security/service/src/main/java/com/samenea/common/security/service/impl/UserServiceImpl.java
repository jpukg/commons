package com.samenea.common.security.service.impl;

import com.samenea.common.security.model.Role;
import com.samenea.common.security.model.User;
import com.samenea.common.security.repository.UserRepository;
import com.samenea.common.security.service.RoleService;
import com.samenea.common.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 9/4/12
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService,UserDetailsService {
    public static final String SALT = "";
    @Autowired
    RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Value("${user.maxAllowedTry}")
    private Integer maxAllowedTry;
    @Value("${user.lockTime}")
    private Integer lockTime;
    @Value("${user.timeUnit}")
    private TimeUnit timeUnit;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersByRole(String role) {
        return userRepository.findUsersByRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUsersByUsername(String username) {
        return (User) userRepository.loadUserByUsername(username);
    }

    @Override
    @Transactional
    public void successAttemptUserLogin(User user) {
        user.successLoginAttempted();
        userRepository.store(user);
    }

    @Override
    @Transactional
    public void failedAttemptUserLogin(String username) {
        User usersByUsername = null;
        try {
            usersByUsername = findUsersByUsername(username);
        } catch (Exception e) {
            return;
        }
        if (usersByUsername == null) {
            return;
        }
        usersByUsername.failedLoginAttempted(maxAllowedTry, lockTime, timeUnit);
        userRepository.store(usersByUsername);
    }

    @Override
    @Transactional
    public User createUser(String userName, String password) {
        User u = new User(userName,passwordEncoder.encodePassword(password,SALT));
        u.setActive(true);
        return userRepository.store(u);
    }

    @Override
    @Transactional
    public void changePassword(String username, String oldPass, String newPass) {
        User user = userRepository.getByUserName(username);
        String oldPassEncoded = passwordEncoder.encodePassword(oldPass, SALT);
        if(user.getPassword() != null && user.getPassword().equals(oldPassEncoded)){
            user.setPassword(passwordEncoder.encodePassword(newPass,SALT));
            userRepository.store(user);
        }else {
            throw new IncorrectPasswordException("Password is incorrect");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Secured({"ROLE_ADMIN"})
    @Override
    @Transactional
    public void resetPassword(String username, String newPass) {
        User user = userRepository.getByUserName(username);
        user.setPassword(passwordEncoder.encodePassword(newPass,SALT));
        userRepository.store(user);
    }

    @Override
    @Transactional
    public User changeUserInfo(String userName, String firstaName, String lastName, String email, String phoneNumber) {
        User user = userRepository.getByUserName(userName);
        user.setEmail(email);
        user.setEnFirstName(firstaName);
        user.setEnLastName(lastName);
        user.setFaFirstName(firstaName);
        user.setFaLastName(lastName);
        user.setPhone(phoneNumber);

        return userRepository.store(user);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void assignRole(String userName, String roleName) {
        User user = userRepository.getByUserName(userName);
        Role roleByName = roleService.getRoleByName(roleName);
        user.addRole(roleByName);
        userRepository.store(user);

    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void removeRole(String userName, String roleName) {
        User user = userRepository.getByUserName(userName);
        user.removeRole(roleName);
        userRepository.store(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.loadUserByUsername(username);
    }
}
