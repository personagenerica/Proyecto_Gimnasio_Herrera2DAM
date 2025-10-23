package com.gimnasio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Actor;
import com.gimnasio.entity.Producto;
import com.gimnasio.repository.ActorRepository;

@Service
public class ActorService {
@Autowired

private ActorRepository actorRepository;

//Creamos el CRUD

public List<Actor> findAll(){
	return actorRepository.findAll();
}

public Optional<Actor> findById(Integer id){
	return actorRepository.findById(id);
}

//dudas

public Actor save(Actor a) {
    // Verificar si el email ya existe
    Optional<Actor> existente = actorRepository.findByEmail(a.getEmail());
    if (existente.isPresent()) {
        throw new IllegalArgumentException("Ya existe un usuario con ese email");
    }

    return actorRepository.save(a);
}

public Actor update(Actor a, int id) {
    Optional<Actor> oActor = actorRepository.findById(id);

    if (oActor.isEmpty()) {
        return null;
    } else {
        Actor actorNuevo = oActor.get();

        // Actualiza los campos necesarios
        actorNuevo.setNombre(a.getNombre());
        actorNuevo.setApellidos(a.getApellidos());
        actorNuevo.setEmail(a.getEmail());
        actorNuevo.setFotografia(a.getFotografia());
        actorNuevo.setTelefono(a.getTelefono());
        actorNuevo.setEdad(a.getEdad());

        return actorRepository.save(actorNuevo);
    }
}

public void delete (int id) {
	actorRepository.deleteById(id);
}
	
}
