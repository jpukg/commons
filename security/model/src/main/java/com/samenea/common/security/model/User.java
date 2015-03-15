package com.samenea.common.security.model;


import com.samenea.common.security.model.enums.DefaultLang;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Entity
@Table(name = "APP_USER")

@NamedQueries({
        @NamedQuery(name = "findByRole", query = "select u from User u ,in(u.roles) r where r.name=:role ")})


public class User extends com.samenea.commons.component.model.Entity<Long> implements Serializable, UserDetails {

    @Column(nullable = false, length = 50, unique = true, name = "USER_NAME")
    private final String username;
    @Column(nullable = false, name = "PASSWORD")
    private String password;
    @Column(name = "DEFAULT_LANG")
    @Enumerated(EnumType.STRING)
    private DefaultLang defaultLang;
    @Column(name = "FA_FIRST_NAME")
    private String faFirstName;
    @Column(name = "FA_LAST_NAME")
    private String faLastName;
    @Column(name = "EN_FIRST_NAME")
    private String enFirstName;
    @Column(name = "EN_LAST_NAME")
    private String enLastName;
    @Column(name = "PHONE_NO")
    private String phone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ENABLED")
    private Boolean active;
    @Column(name = "UNLOCK_TIME")
    private Date unlockTime;
    @Column(name = "FAILED_ATTEMPTS")
    private Integer failedAttempts;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(
            name = "APP_USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Role> roles = new HashSet<Role>();

    public User(String username) {
        Assert.hasText(username,"User name should not be empty");
        this.username = username;
    }

    public User() {
        username = null;
    }

    public User(String username, String password) {
        Assert.hasText(username,"User name should not be empty");
        Assert.hasText(password,"password should not be empty");

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public DefaultLang getDefaultLang() {
        return defaultLang;
    }


    public String getFaFirstName() {
        return faFirstName;
    }


    public String getFaLastName() {
        return faLastName;
    }


    public String getEnFirstName() {
        return enFirstName;
    }


    public String getEnLastName() {
        return enLastName;
    }

    public String getPhone() {
        return phone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Boolean getActive() {
        return active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
        authorities.addAll(getRoles());
        return authorities;
    }


    @Transient
    public boolean isEnabled() {
        if (active == null) {
            return false;
        }
        return active;
    }


    @Transient
    public boolean isAccountExpired() {
        return false;
    }

    /**
     * @return true if account is still active
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }


    @Transient
    public boolean isAccountNonLocked() {
        return unlockTime == null ? true : Calendar.getInstance().getTime().after(unlockTime);
    }


    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public void setDefaultLang(DefaultLang defaultLang) {
        this.defaultLang = defaultLang;
    }

    public void setFaFirstName(String faFirstName) {
        this.faFirstName = faFirstName;
    }

    public void setFaLastName(String faLastName) {
        this.faLastName = faLastName;
    }

    public void setEnFirstName(String enFirstName) {
        this.enFirstName = enFirstName;
    }

    public void setEnLastName(String enLastName) {
        this.enLastName = enLastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!username.equals(user.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("User");
        sb.append("{id=").append(getId());

        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", defaultLang='").append(defaultLang).append('\'');
        sb.append(", faFirstName='").append(faFirstName).append('\'');
        sb.append(", faLastName='").append(faLastName).append('\'');
        sb.append(", enFirstName='").append(enFirstName).append('\'');
        sb.append(", enLastName='").append(enLastName).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", active=").append(active);

        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }

    /**
     *
     * @param maxAllowedTry
     * @param lockTime
     * @param timeUnit
     */
    public void failedLoginAttempted(int maxAllowedTry, int lockTime, TimeUnit timeUnit) {
        if(failedAttempts==null){
            failedAttempts=0;
        }
        failedAttempts++;
        if (failedAttempts >= maxAllowedTry) {
            lockMe((int) timeUnit.toMillis(lockTime));
        }
    }

    public void successLoginAttempted() {
        failedAttempts = 0;
        unlockTime = null;
    }

    private void lockMe(int l) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MILLISECOND, l);
        unlockTime = instance.getTime();
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(String roleName) {
        Assert.hasText(roleName);
        roles.remove(new Role(roleName));
    }

    public boolean hasRole(String roleName) {
        return roles.contains(new Role(roleName));
    }
}
