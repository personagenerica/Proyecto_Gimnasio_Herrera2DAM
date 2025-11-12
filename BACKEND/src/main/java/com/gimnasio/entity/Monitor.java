package com.gimnasio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "monitor")
public class Monitor extends Actor{

	public Monitor() {
		super();
	}

}
