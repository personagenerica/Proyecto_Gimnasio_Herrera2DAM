package com.gimnasio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gimnasio.entity.Actor;
import com.gimnasio.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer>{

	Optional<Actor> findByUsername(String username);

}
