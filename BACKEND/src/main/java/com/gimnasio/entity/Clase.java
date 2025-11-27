package com.gimnasio.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;

@Entity
public class Clase extends DomainEntity {

    @Future
    private Date fecha_inicio;

    @Future
    private Date fecha_fin;

    @Min(0)
    private int aforo;

    @ManyToMany
    private List<Usuario> usuarios;

    @ManyToOne
    private Monitor monitor;

    // Constructor con todos los campos (sin id)
    public Clase(@Future Date fecha_inicio, @Future Date fecha_fin, @Min(0) int aforo, List<Usuario> usuarios,
                 Monitor monitor) {
        super();
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.aforo = aforo;
        this.usuarios = usuarios;
        this.monitor = monitor;
    }

    public Clase() {
        super();
    }

    // --- Getters y Setters ---
    public Date getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(Date fecha_inicio) { this.fecha_inicio = fecha_inicio; }

    public Date getFecha_fin() { return fecha_fin; }
    public void setFecha_fin(Date fecha_fin) { this.fecha_fin = fecha_fin; }

    public int getAforo() { return aforo; }
    public void setAforo(int aforo) { this.aforo = aforo; }

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }

    public Monitor getMonitor() { return monitor; }
    public void setMonitor(Monitor monitor) { this.monitor = monitor; }
}
