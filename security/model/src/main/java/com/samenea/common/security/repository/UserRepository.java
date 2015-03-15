package com.samenea.common.security.repository;

import com.samenea.common.security.model.User;
import com.samenea.commons.component.model.BasicRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;


public interface UserRepository extends BasicRepository<User, Long>,UserDetailsService {
    List<User> findUsersByRole(String rolesEnum);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User loadUserByEmail(String email) throws AccountNotFoundException;

    List<User> getUsers();

    User saveUser(User user) throws DataAccessException;

    String getUserPassword(String username);

    User getByUserName(String username);
}
