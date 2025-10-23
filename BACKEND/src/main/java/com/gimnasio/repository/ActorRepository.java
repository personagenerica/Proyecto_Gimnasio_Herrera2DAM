package com.gimnasio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gimnasio.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer>{
	
    Optional<Actor> findByEmail(String email);

}
	