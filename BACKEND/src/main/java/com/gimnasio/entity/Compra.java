package com.gimnasio.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    // Una compra puede tener muchos usuarios
    @OneToMany
    @JoinColumn(name = "compra_id")
    private List<Usuario> usuarios;

    public Compra() {
        super();
    }

    // Constructor corregido con List<Usuario>
    public Compra(@NotBlank String ticket, @Min(0) int cantidad, Producto producto, List<Usuario> usuarios) {
        super();
        this.ticket = ticket;
        this.cantidad = cantidad;
        this.producto = producto;
        this.usuarios = usuarios;
    }

    // Getters y Setters
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
