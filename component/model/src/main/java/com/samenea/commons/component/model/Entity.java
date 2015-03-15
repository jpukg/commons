package com.samenea.commons.component.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class Entity<PK extends Serializable> implements Serializable{
////    @GeneratedValue(generator ="samenea_seq" )
////    @GenericGenerator(
////            name = "samenea_seq",
////            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
////            parameters = {
////                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "samenea_seq"),
////                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
////                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "50"),
////                    @org.hibernate.annotations.Parameter(name = "prefer_sequence_per_entity", value = "true")
//            }
//    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @SequenceGenerator(name = "hibernate_sequence", sequenceName = "hibernate_sequence")
    private PK id;
    /**
     * Just for Optimistic locking
     */
    @Version
    private Long version;

	public PK getId() {
		return id;
	}
	

	public void setId(PK id) {
		this.id = id;
	}

	public Long getVersion(){
		return this.version;
	}
	
	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int hashCode();

}
