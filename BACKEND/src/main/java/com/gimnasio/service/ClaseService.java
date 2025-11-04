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
     * Busca una clase por ID.
     */
    public Optional<Clase> findById(Integer id) {
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
     * Guarda una clase (con validaciones básicas).
     */
    public Clase save(Clase clase) {
        validarFechas(clase);

        if (clase.getAforo() < 0) {
            throw new IllegalArgumentException("El aforo no puede ser negativo");
        }

        return claseRepository.save(clase);
    }

    /**
     * Actualiza una clase existente por ID.
     */
    public Clase update(Clase clase, int id) {
        Optional<Clase> optionalClase = claseRepository.findById(id);

        if (optionalClase.isEmpty()) {
            throw new RuntimeException("Clase no encontrada con ID: " + id);
        }

        validarFechas(clase);

        Clase claseExistente = optionalClase.get();
        claseExistente.setFecha_inicio(clase.getFecha_inicio());
        claseExistente.setFecha_fin(clase.getFecha_fin());
        claseExistente.setAforo(clase.getAforo());
        claseExistente.setUsuarios(clase.getUsuarios());
        claseExistente.setMonitor(clase.getMonitor());

        return claseRepository.save(claseExistente);
    }

    /**
     * Elimina una clase por su ID.
     */
    public void delete(int id) {
        if (!claseRepository.existsById(id)) {
            throw new RuntimeException("Clase no encontrada con ID: " + id);
        }
        claseRepository.deleteById(id);
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