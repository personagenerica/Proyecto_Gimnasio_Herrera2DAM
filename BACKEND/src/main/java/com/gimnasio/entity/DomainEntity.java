package com.gimnasio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // genera automáticamente IDs únicos por tabla
    protected Long id; // Long en vez de int para evitar problemas con Hibernate

    @Version
    private int version;

    public DomainEntity() {
        super();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

 
}
