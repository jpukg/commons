package com.samenea.common.security.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;


/**
 * This class is used to represent available roles in the database.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Version by Dan Kibler dan@getrolling.com
 *         Extended to implement Acegi GrantedAuthority interface
 *         by David Carter david@carter.net
 */
@Entity
@Table(name = "APP_ROLE")
@NamedQueries({
        @NamedQuery(
                name = "findRoleByName",
                query = "select r from Role r where r.name = :name "
        )
})
public class Role  extends com.samenea.commons.component.model.Entity<Long> implements Serializable, GrantedAuthority {


    @Column(length = 30, name = "ROLE_NAME")

    private final String name;
    @Column(length = 64, name = "DESCRIPTION")
    private String description;

    


    /**
     * Default constructor - creates a new instance with no values set.
     */
    private Role() {
        name = null;
    }

    /**
     * Create a new instance and set the name.
     *
     * @param name name of the role.
     */
    public Role(final String name) {
        Assert.hasText(name,"role name should not be null;");
        this.name = name;
    }



    @Transient
    public String getAuthority() {
        return getName();
    }


    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!name.equals(role.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append(this.name)
                .toString();
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(Object o) {
        return (equals(o) ? 0 : -1);
    }




}
