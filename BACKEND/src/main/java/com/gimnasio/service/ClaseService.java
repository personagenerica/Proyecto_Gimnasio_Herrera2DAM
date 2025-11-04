package com.gimnasio.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Clase;
import com.gimnasio.repository.ClaseRepository;

import jakarta.validation.Valid;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    /**
     * Obtiene todas las clases del sistema.
     */
    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

    /**
     * Busca una clase por su ID.
     */
    public Optional<Clase> findById(Long id) {
        return claseRepository.findById(id);
    }

    /**
     * Crea una nueva clase con validación de fechas.
     */
    public Clase create(@Valid Clase clase) {
        validarFechas(clase);
        return claseRepository.save(clase);
    }

    /**
     * Actualiza una clase existente.
     */
    public Clase update(Long id, @Valid Clase claseDetalles) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));

        validarFechas(claseDetalles);
        
        clase.setFecha_inicio(claseDetalles.getFecha_inicio());
        clase.setFecha_fin(claseDetalles.getFecha_fin());
        clase.setAforo(claseDetalles.getAforo());
        clase.setUsuarios(claseDetalles.getUsuarios());
        clase.setMonitor(claseDetalles.getMonitor());

        return claseRepository.save(clase);
    }

    /**
     * Elimina una clase por su ID.
     */
    public void delete(Long id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));
        claseRepository.delete(clase);
    }

    /**
     * Valida que las fechas sean futuras y coherentes.
     */
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
