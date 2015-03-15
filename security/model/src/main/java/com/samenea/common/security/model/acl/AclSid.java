package com.samenea.common.security.model.acl;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Maziar Kaveh
 * Email:maziar.kaveh@gmail.com
 * Date: 2/26/12
 * Time: 2:56 PM
 */
@Table(name = "acl_sid" )
@Entity
public class AclSid {
    private long id;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AclSidSeq")
    @SequenceGenerator(name = "AclSidSeq", sequenceName = "ACL_SID_ID_SEQ")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private boolean principal;

    @Column(name = "principal", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
    @Basic
    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    private String sid;

    @Column(name = "sid", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AclSid aclSid = (AclSid) o;

        if (id != aclSid.id) return false;
        if (principal != aclSid.principal) return false;
        if (sid != null ? !sid.equals(aclSid.sid) : aclSid.sid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (principal ? 1 : 0);
        result = 31 * result + (sid != null ? sid.hashCode() : 0);
        return result;
    }
}
