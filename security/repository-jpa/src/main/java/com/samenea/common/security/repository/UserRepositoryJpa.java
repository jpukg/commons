package com.samenea.common.security.repository;

import com.samenea.common.security.model.User;
import com.samenea.commons.model.repository.BasicRepositoryJpa;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.Query;
import javax.security.auth.login.AccountNotFoundException;
import java.util.HashMap;
import java.util.List;


@Repository
public class UserRepositoryJpa extends BasicRepositoryJpa<User, Long> implements UserRepository {


    public UserRepositoryJpa() {
        super(User.class);
    }


    public List<User> getUsers() {
        return getEntityManager().createQuery("from User u order by upper(u.username)").getResultList();
    }


    public User saveUser(User user) throws DataAccessException {
        if (log.isDebugEnabled()) {
            log.debug("user's id: " + user.getId());
        }
        return store(user);

    }




    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Query query = getEntityManager().createQuery("from User where username=:username");
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        if (CollectionUtils.isEmpty(users)) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return users.get(0);
        }
    }

    public List<User> findUsersByRole(String rolesEnum) {
        HashMap hashMap = new HashMap();
        hashMap.put("role", rolesEnum.toString());
        return findByNamedQuery("findByRole", hashMap);

    }


    public String getUserPassword(String username) {
        Query query = getEntityManager().createQuery("select u.password from User u where u.username=:username");
        query.setParameter("username", username);
        List<String> list = query.getResultList();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);

    }

    @Override
    public User getByUserName(String username) {
        Query query = getEntityManager().createQuery("from User where username=:username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }


    public User loadUserByEmail(String email) throws AccountNotFoundException {
        Query query = getEntityManager().createQuery("from User where email=:email");
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        if (users == null || users.isEmpty()) {
            throw new AccountNotFoundException("user with this is email = " + email + "not found");
        } else {
            return users.get(0);
        }
    }

    @Override
    public User store(User object) {
        User stored = super.store(object);
        getEntityManager().flush();
        return stored;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
