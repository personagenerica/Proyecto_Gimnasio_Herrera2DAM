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
        // Ejemplo de validación opcional (puedes eliminarlo si no aplica)
        Optional<Clase> existente = claseRepository.findByNombre(clase.getNombre());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe una clase con ese nombre");
        }

        return claseRepository.save(clase);
    }

    public Clase update(Clase clase, int id) {
        Optional<Clase> optionalClase = claseRepository.findById(id);

        if (optionalClase.isEmpty()) {
            return null;
        } else {
            Clase claseExistente = optionalClase.get();

            // Actualiza los campos necesarios
            claseExistente.setNombre(clase.getNombre());
            claseExistente.setDescripcion(clase.getDescripcion());
            claseExistente.setDuracion(clase.getDuracion());
            claseExistente.setHorario(clase.getHorario());
            claseExistente.setInstructor(clase.getInstructor());
            claseExistente.setCupoMaximo(clase.getCupoMaximo());

            return claseRepository.save(claseExistente);
        }
    }

    public void delete(int id) {
        claseRepository.deleteById(id);
    }
}
