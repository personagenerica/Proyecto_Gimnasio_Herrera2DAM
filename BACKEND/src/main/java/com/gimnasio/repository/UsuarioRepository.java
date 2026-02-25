package com.gimnasio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gimnasio.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    // Buscar usuario por username
    Optional<Usuario> findByUsername(String username);

}