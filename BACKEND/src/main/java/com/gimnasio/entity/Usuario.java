package com.gimnasio.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario extends Actor {

	public Usuario() {
		super();
	}

}
