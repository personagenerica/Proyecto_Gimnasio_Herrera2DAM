package com.gimnasio.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Clase;
import com.gimnasio.entity.Monitor;
import com.gimnasio.entity.Usuario;
import com.gimnasio.repository.ClaseRepository;
import com.gimnasio.repository.MonitorRepository;
import com.gimnasio.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Service
public class ClaseService {
	
    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private MonitorRepository monitorRepository;

    /** Lista todas las clases */
    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

    /** Crear nueva clase */
    public Clase create(@Valid Clase clase) {
        validarFechas(clase);
        if (clase.getUsuarios() == null) {
            clase.setUsuarios(new ArrayList<>()); // inicializar lista
        }
        Monitor monitorReal = monitorRepository.findById(clase.getMonitor().getId())
                .orElseThrow(() -> new RuntimeException("Monitor no encontrado"));
            clase.setMonitor(monitorReal);
        return claseRepository.save(clase);
    }

    /** Guardar clase existente */
    public Clase save(Clase clase) {
        validarFechas(clase);
        if (clase.getAforo() < 0) {
            throw new IllegalArgumentException("El aforo no puede ser negativo");
        }
        if (clase.getUsuarios() == null) {
            clase.setUsuarios(new ArrayList<>());
        }
        return claseRepository.save(clase);
    }

    /** Reservar clase usando username del JWT */
    public Clase reservarClasePorUsername(int claseId, String username) {
        Clase clase = claseRepository.findById(claseId)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Inicializar lista si es null
        if (clase.getUsuarios() == null) {
            clase.setUsuarios(new ArrayList<>());
        }

        // Verificar si ya está inscrito (comparar IDs)
        boolean yaInscrito = clase.getUsuarios().stream()
                .anyMatch(u -> u.getId().equals(usuario.getId()));
        if (yaInscrito) {
            throw new RuntimeException("Usuario ya inscrito");
        }

        // Verificar aforo
        if (clase.getUsuarios().size() >= clase.getAforo()) {
            throw new RuntimeException("Clase llena");
        }

        // Agregar usuario
        clase.getUsuarios().add(usuario);

        return claseRepository.save(clase);
    }

    /** Validación de fechas */
    private void validarFechas(Clase clase) {
        Date ahora = new Date();

        if (clase.getFecha_inicio() == null || clase.getFecha_fin() == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin no pueden ser nulas.");
        }
        if (!clase.getFecha_inicio().after(ahora)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser futura.");
        }
        if (!clase.getFecha_fin().after(clase.getFecha_inicio())) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la de inicio.");
        }
    }
}