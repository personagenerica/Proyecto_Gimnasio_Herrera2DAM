package com.gimnasio.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Compra extends DomainEntity {
	@NotBlank
	private String ticket;
	@Min(0)
	private int cantidad;
	
	@ManyToOne
	private Producto producto;
	
	
	@ManyToMany
	private List<Usuario> usuarios;


	public Compra() {
		super();
	}


	public Compra(@NotBlank String ticket, @Min(0) int cantidad, Producto producto, List<Usuario> usuarios) {
		super();
		this.ticket = ticket;
		this.cantidad = cantidad;
		this.producto = producto;
		this.usuarios = usuarios;
	}


	public String getTicket() {
		return ticket;
	}


	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public List<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	

}
