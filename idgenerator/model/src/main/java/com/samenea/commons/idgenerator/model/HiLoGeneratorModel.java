package com.samenea.commons.idgenerator.model;

import com.samenea.commons.component.model.Entity;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "HILO_GENERATOR")
public class HiLoGeneratorModel extends Entity<Long> {
    public final static String DEFAULT_NAME = "GENERAL";
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    private long hi;

    protected HiLoGeneratorModel() {
        this.hi = -1;
        this.name = DEFAULT_NAME;
    }

    public HiLoGeneratorModel(String name) {
        this();
        this.name = name;
    }

    public HiLoGeneratorModel(String name, long hi) {
        Assert.notNull(name, "name must not be null");
        Assert.hasText(name, "name must not be empty");

        this.name = name;
        this.hi = hi;
    }

    public HiLoGeneratorModel(long id, String name, long hi) {
        this(name, hi);

        setId(id);
    }

    public String getName() {
        return name;
    }

    public long getHi() {
        return hi;
    }

    public void setHi(long hi) {
        this.hi = hi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HiLoGeneratorModel that = (HiLoGeneratorModel) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
