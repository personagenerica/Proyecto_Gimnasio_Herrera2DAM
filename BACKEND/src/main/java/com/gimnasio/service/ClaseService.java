package com.gimnasio.service;

<<<<<<< HEAD
import java.util.Date;
=======
>>>>>>> 9d360f57af3858fb3ec623b024aa317c49f8b779
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Clase;
import com.gimnasio.repository.ClaseRepository;

<<<<<<< HEAD
import jakarta.validation.Valid;

=======
>>>>>>> 9d360f57af3858fb3ec623b024aa317c49f8b779
@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

<<<<<<< HEAD
    /**
     * Obtiene todas las clases del sistema.
     */
=======
    // --- CRUD básico ---

>>>>>>> 9d360f57af3858fb3ec623b024aa317c49f8b779
    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

<<<<<<< HEAD
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
=======
    public Optional<Clase> findById(Integer id) {
        return claseRepository.findById(id);
    }

    public Clase save(Clase clase) {
        // Validación opcional: fecha_inicio < fecha_fin
        if (clase.getFecha_inicio() != null && clase.getFecha_fin() != null &&
            clase.getFecha_inicio().after(clase.getFecha_fin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        // Validación opcional: aforo no negativo
        if (clase.getAforo() < 0) {
            throw new IllegalArgumentException("El aforo no puede ser negativo");
        }
>>>>>>> 9d360f57af3858fb3ec623b024aa317c49f8b779

        return claseRepository.save(clase);
    }

<<<<<<< HEAD
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
=======
    public Clase update(Clase clase, int id) {
        Optional<Clase> optionalClase = claseRepository.findById(id);

        if (optionalClase.isEmpty()) {
            return null; // o lanza una excepción si prefieres
        }

        Clase claseExistente = optionalClase.get();

        // Actualiza los campos necesarios
        claseExistente.setFecha_inicio(clase.getFecha_inicio());
        claseExistente.setFecha_fin(clase.getFecha_fin());
        claseExistente.setAforo(clase.getAforo());
        claseExistente.setUsuarios(clase.getUsuarios());
        claseExistente.setMonitor(clase.getMonitor());

        return claseRepository.save(claseExistente);
    }

    public void delete(int id) {
        claseRepository.deleteById(id);
>>>>>>> 9d360f57af3858fb3ec623b024aa317c49f8b779
    }
}
