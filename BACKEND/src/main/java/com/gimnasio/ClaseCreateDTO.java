package com.gimnasio;

import java.util.Date;

public class ClaseCreateDTO {

    private Date fecha_inicio;
    private Date fecha_fin;
    private int aforo;
    private int monitorId; // solo el ID del monitor

    // Getters y setters
    public Date getFecha_inicio() { return fecha_inicio; }
    public void setFecha_inicio(Date fecha_inicio) { this.fecha_inicio = fecha_inicio; }

    public Date getFecha_fin() { return fecha_fin; }
    public void setFecha_fin(Date fecha_fin) { this.fecha_fin = fecha_fin; }

    public int getAforo() { return aforo; }
    public void setAforo(int aforo) { this.aforo = aforo; }

    public int getMonitorId() { return monitorId; }
    public void setMonitorId(int monitorId) { this.monitorId = monitorId; }
}