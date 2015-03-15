package com.samenea.commons.component.idgenerator.model;

import com.samenea.commons.component.model.Entity;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: javaee
 * Date: 10/30/12
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity
@Table(name = "ID_GENERATOR_MODEL")
@NamedQueries({
        @NamedQuery(name = "findByToken", query = "from IdGeneratorModel where token=:token")
})
@Access(value = AccessType.PROPERTY)
public class IdGeneratorModel extends Entity<Long> {
    public static Long DEFAULT_INIT_ID = 0l;

    private AtomicLong sequenceID;


    private String token;

    protected IdGeneratorModel() {
        this.token = "";
    }

    public IdGeneratorModel(String token, Long initSequenceId) {
        this.token = token;
        this.sequenceID = new AtomicLong(initSequenceId);
    }

    public IdGeneratorModel(String token) {
        this.token = token;
        this.sequenceID = new AtomicLong(DEFAULT_INIT_ID);
    }


    public void incrementId(Integer unit) {
        this.sequenceID.addAndGet(unit);
    }

    @Column(unique = true)
    public String getToken() {
        return token;
    }

    private void setToken(String token) {
        this.token = token;
    }

    @Column
    public Long getSequenceID() {
        return sequenceID == null ? null : sequenceID.longValue();
    }


    private void setSequenceID(long sequenceID) {
        this.sequenceID = new AtomicLong(sequenceID);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdGeneratorModel that = (IdGeneratorModel) o;

        if (sequenceID != null ? !sequenceID.equals(that.sequenceID) : that.sequenceID != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sequenceID != null ? sequenceID.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("IdGeneratorModel");
        sb.append("{sequenceID=").append(sequenceID);
        sb.append(", token=").append(token);
        sb.append('}');
        return sb.toString();
    }
}
