package com.samenea.common.security.model.acl;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Maziar Kaveh
 * Email:maziar.kaveh@gmail.com
 * Date: 2/26/12
 * Time: 2:56 PM
 */
@Table(name = "acl_class" )
@Entity
public class AclClass {
    private long id;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AclClassSeq")
    @SequenceGenerator(name = "AclClassSeq", sequenceName = "ACL_CLASS_ID_SEQ")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String clazz;

    @Column(name = "class", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AclClass aclClass = (AclClass) o;

        if (id != aclClass.id) return false;
        if (clazz != null ? !clazz.equals(aclClass.clazz) : aclClass.clazz != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        return result;
    }
}
