package com.samenea.common.security.service;

import com.samenea.common.security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 9/4/12
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {
    List<User> getUsers();

    List<User> findUsersByRole(String role);

    User findUsersByUsername(String username);

    void successAttemptUserLogin(User user);

    void failedAttemptUserLogin(String username);

    User createUser(String userName, String password);

    void changePassword(String username, String oldPass, String newPass);

    /**
     * Provided for resetting password for admin
     * @param username
     * @param newPass
     */
    void resetPassword(String username, String newPass);

    User changeUserInfo(String userName, String firstaName, String lastName, String email, String phoneNumber);

    void assignRole(String userName, String roleName);

    void removeRole(String userName, String roleName);
}
