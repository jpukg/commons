package com.samenea.common.security.model.acl;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Maziar Kaveh
 * Email:maziar.kaveh@gmail.com
 * Date: 2/26/12
 * Time: 2:56 PM
 */
@Table(name = "acl_entry" )
@Entity
@NamedQueries({
        @NamedQuery(name = "findByObjectIdentity",query = "from AclEntry where aclObjectIdentity.objectIdIdentity=:objectIdIdentity and aclObjectIdentity.objectIdClass.clazz=:objectClassName order by aceOrder")
})
public class AclEntry {
    private long id;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AclEntrySeq")
    @SequenceGenerator(name = "AclEntrySeq", sequenceName = "ACL_ENTRY_ID_SEQ")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private AclObjectIdentity aclObjectIdentity;

    @ManyToOne
        @JoinColumn(name = "acl_object_identity")
    public AclObjectIdentity getAclObjectIdentity() {
        return aclObjectIdentity;
    }

    public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }

    private int aceOrder;

    @Column(name = "ace_order", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getAceOrder() {
        return aceOrder;
    }

    public void setAceOrder(int aceOrder) {
        this.aceOrder = aceOrder;
    }

    private AclSid sid;

    @ManyToOne
    @JoinColumn(name = "sid")
    public AclSid getSid() {
        return sid;
    }

    public void setSid(AclSid sid) {
        this.sid = sid;
    }

    private int mask;

    @Column(name = "mask", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    private boolean granting;

    @Column(name = "granting", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
    @Basic
    public boolean isGranting() {
        return granting;
    }

    public void setGranting(boolean granting) {
        this.granting = granting;
    }

    private boolean auditSuccess;

    @Column(name = "audit_success", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
    @Basic
    public boolean isAuditSuccess() {
        return auditSuccess;
    }

    public void setAuditSuccess(boolean auditSuccess) {
        this.auditSuccess = auditSuccess;
    }

    private boolean auditFailure;

    @Column(name = "audit_failure", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
    @Basic
    public boolean isAuditFailure() {
        return auditFailure;
    }

    public void setAuditFailure(boolean auditFailure) {
        this.auditFailure = auditFailure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AclEntry aclEntry = (AclEntry) o;

        if (aceOrder != aclEntry.aceOrder) return false;
        if (aclObjectIdentity != aclEntry.aclObjectIdentity) return false;
        if (auditFailure != aclEntry.auditFailure) return false;
        if (auditSuccess != aclEntry.auditSuccess) return false;
        if (granting != aclEntry.granting) return false;
        if (id != aclEntry.id) return false;
        if (mask != aclEntry.mask) return false;
        if (sid != aclEntry.sid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));

        result = 31 * result + aceOrder;

        result = 31 * result + mask;
        result = 31 * result + (granting ? 1 : 0);
        result = 31 * result + (auditSuccess ? 1 : 0);
        result = 31 * result + (auditFailure ? 1 : 0);
        return result;
    }
}
