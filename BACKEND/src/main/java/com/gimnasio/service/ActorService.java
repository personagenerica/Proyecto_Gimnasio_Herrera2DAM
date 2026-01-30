package com.gimnasio.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Actor;
import com.gimnasio.repository.ActorRepository;

@Service
public class ActorService implements UserDetailsService {

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
		return actorRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Actor> actorO = this.findByUsername(username);
		if (actorO.isPresent()) {
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(actorO.get().getRol().toString()));
			User user = new User(actorO.get().getUsername(), actorO.get().getPassword(), authorities);
			return user;
		} else {
			throw new UsernameNotFoundException("Username no encontrado");
		}
	}
}
