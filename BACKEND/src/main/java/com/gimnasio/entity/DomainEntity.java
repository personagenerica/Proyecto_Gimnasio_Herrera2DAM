package com.gimnasio.entity;

import org.springframework.data.annotation.Version;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // IDs únicos
    protected int id; // Usar Long es más seguro con Hibernate
    
    @Version
    private int version;

 
    public DomainEntity() {
        super();
    }


	public Integer getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getVersion() {
		return version;
	}


	public void setVersion(int version) {
		this.version = version;
	}
    
}
