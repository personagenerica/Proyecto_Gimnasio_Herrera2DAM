package com.gimnasio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Clase;
import com.gimnasio.repository.ClaseRepository;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    // --- CRUD básico ---

    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

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

        return claseRepository.save(clase);
    }

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
    }
}
