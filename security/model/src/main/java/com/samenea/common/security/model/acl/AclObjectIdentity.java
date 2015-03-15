package com.samenea.common.security.model.acl;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Maziar Kaveh
 * Email:maziar.kaveh@gmail.com
 * Date: 2/26/12
 * Time: 2:56 PM
 */
@Table(name = "acl_object_identity" )
@Entity
public class AclObjectIdentity {
    private long id;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AclObjectIdentitySeq")
    @SequenceGenerator(name = "AclObjectIdentitySeq", sequenceName = "ACL_OBJECT_IDENTITY_ID_SEQ")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private AclClass objectIdClass;

    @ManyToOne
    @JoinColumn(name = "object_id_class")
    public AclClass getObjectIdClass() {
        return objectIdClass;
    }

    public void setObjectIdClass(AclClass objectIdClass) {
        this.objectIdClass = objectIdClass;
    }

    private long objectIdIdentity;

    @Column(name = "object_id_identity", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public long getObjectIdIdentity() {
        return objectIdIdentity;
    }

    public void setObjectIdIdentity(long objectIdIdentity) {
        this.objectIdIdentity = objectIdIdentity;
    }

    private AclObjectIdentity parentObject;

    @OneToOne
    @JoinColumn(name = "parent_object")
    public AclObjectIdentity getParentObject() {
        return parentObject;
    }

    public void setParentObject(AclObjectIdentity parentObject) {
        this.parentObject = parentObject;
    }

    private long ownerSid;

    @Column(name = "owner_sid", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public long getOwnerSid() {
        return ownerSid;
    }

    public void setOwnerSid(long ownerSid) {
        this.ownerSid = ownerSid;
    }

    private boolean entriesInheriting;

    @Column(name = "entries_inheriting", nullable = false, insertable = true, updatable = true, length = 0, precision = 0)
    @Basic
    public boolean isEntriesInheriting() {
        return entriesInheriting;
    }

    public void setEntriesInheriting(boolean entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AclObjectIdentity that = (AclObjectIdentity) o;

        if (entriesInheriting != that.entriesInheriting) return false;
        if (id != that.id) return false;
        if (objectIdClass != that.objectIdClass) return false;
        if (objectIdIdentity != that.objectIdIdentity) return false;
        if (ownerSid != that.ownerSid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));

        result = 31 * result + (int) (objectIdIdentity ^ (objectIdIdentity >>> 32));

        result = 31 * result + (int) (ownerSid ^ (ownerSid >>> 32));
        result = 31 * result + (entriesInheriting ? 1 : 0);
        return result;
    }
}
