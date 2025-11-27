package com.gimnasio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Actor;
import com.gimnasio.repository.ActorRepository;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    // --- CRUD básico ---

    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    public Optional<Actor> findById(Integer id) {
        return actorRepository.findById(id);
    }

    public Actor save(Actor actor) {
        // Verificar si el email ya existe
        Optional<Actor> existente = actorRepository.findByEmail(actor.getEmail());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        return actorRepository.save(actor);
    }

    public Actor update(Actor actor, int id) {
        Optional<Actor> optionalActor = actorRepository.findById(id);

        if (optionalActor.isEmpty()) {
            throw new IllegalArgumentException("No se encontró un actor con el ID proporcionado");
        }

        Actor actorExistente = optionalActor.get();

        // Actualiza los campos necesarios
        actorExistente.setNombre(actor.getNombre());
        actorExistente.setApellidos(actor.getApellidos());
        actorExistente.setEmail(actor.getEmail());
        actorExistente.setFotografia(actor.getFotografia());
        actorExistente.setTelefono(actor.getTelefono());
        actorExistente.setEdad(actor.getEdad());

        return actorRepository.save(actorExistente);
    }

    public void delete(int id) {
        if (!actorRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: el actor con ID " + id + " no existe");
        }
        actorRepository.deleteById(id);
    }

	public Optional<Actor> findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserDetails loadUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
