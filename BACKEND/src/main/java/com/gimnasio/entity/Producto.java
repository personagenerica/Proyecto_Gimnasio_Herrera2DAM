package com.gimnasio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "producto")

public class Producto extends DomainEntity{
	@NotBlank
	private String tipo;
	
	@NotBlank
	private String nombre;
	
	@Min(0)
	private double precio;
	
	@Min(0)
	private int stock;

	public Producto() {
		super();
	}

	public Producto(@NotBlank String tipo, @NotBlank String nombre, @Min(0) double precio, @Min(0) int stock) {
		super();
		this.tipo = tipo;
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
}


