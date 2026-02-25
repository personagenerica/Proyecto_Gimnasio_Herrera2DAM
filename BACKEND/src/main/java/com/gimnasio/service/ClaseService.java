package com.gimnasio.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Clase;
import com.gimnasio.entity.Usuario;
import com.gimnasio.repository.ClaseRepository;
import com.gimnasio.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /** Lista todas las clases */
    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

    /** Busca clase por ID */
    public Optional<Clase> findById(Integer id) {
        return claseRepository.findById(id);
    }

    /** Crea nueva clase */
    public Clase create(@Valid Clase clase) {
        validarFechas(clase);
        return claseRepository.save(clase);
    }

    /** Guarda clase existente */
    public Clase save(Clase clase) {
        validarFechas(clase);
        if (clase.getAforo() < 0) {
            throw new IllegalArgumentException("El aforo no puede ser negativo");
        }
        return claseRepository.save(clase);
    }

    /** Actualiza clase existente */
    public Clase update(Clase clase, int id) {
        Clase claseExistente = claseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));

        validarFechas(clase);
        claseExistente.setFecha_inicio(clase.getFecha_inicio());
        claseExistente.setFecha_fin(clase.getFecha_fin());
        claseExistente.setAforo(clase.getAforo());
        claseExistente.setUsuarios(clase.getUsuarios());
        claseExistente.setMonitor(clase.getMonitor());

        return claseRepository.save(claseExistente);
    }

    /** Elimina clase por ID */
    public void delete(int id) {
        if (!claseRepository.existsById(id)) {
            throw new RuntimeException("Clase no encontrada con ID: " + id);
        }
        claseRepository.deleteById(id);
    }

    /** Método para reservar clase por usuario */
    public Clase reservarClase(int claseId, int usuarioId) {
        Clase clase = claseRepository.findById(claseId)
            .orElseThrow(() -> new RuntimeException("Clase no encontrada"));

        // Verificar aforo
        if (clase.getUsuarios().size() >= clase.getAforo()) {
            throw new RuntimeException("Clase llena");
        }

        // Verificar si el usuario ya está inscrito
        for (Usuario u : clase.getUsuarios()) {
            if (u.getId() == usuarioId) {
                throw new RuntimeException("Usuario ya inscrito");
            }
        }

        // Obtener usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Agregar usuario a la clase
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