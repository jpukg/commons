package com.samenea.commons.model.repository.mock;


import com.samenea.commons.component.model.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is used for just testing Generic Data access operations. It's scope is just in tests
 * @author: jalal <a href="mailto:jalal.ashrafi@gmail.com">Jalal Ashrafi</a>
 * Date: 6/13/12
 * Time: 2:39 PM
 */

@Entity
@Table
public class MockEntity extends com.samenea.commons.component.model.Entity<Long>{
    @Column(unique=true)
    private final String name;

    @Temporal(TemporalType.TIMESTAMP)
    private final Date registerDate;
    @Column
    private final int age;
    private MockEntity(){
        name = null;
        this.registerDate = null;
        this.age=0;

    }
    public MockEntity(String name,Date registerDate,int age) {
        this.name = name;
        this.registerDate=registerDate;
        this.age=age;
    }
    public MockEntity(String name) {
        this.name = name;
        this.registerDate = null;
//        this.registerDate = Calendar.getInstance().getTime();
        this.age=0;
    }
    public MockEntity(String name,int age) {
        this.name = name;
        this.registerDate=null;
        this.age=age;

    }
    public String getName() {
        return name;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockEntity that = (MockEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
