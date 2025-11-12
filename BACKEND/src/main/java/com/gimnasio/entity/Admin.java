package com.gimnasio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")

public class Admin extends Actor {

    public Admin() {
        super();

    }

}

