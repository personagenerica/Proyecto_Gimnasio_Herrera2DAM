package com.gimnasio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gimnasio.entity.Clase;

@Repository
public interface ClaseRepository extends JpaRepository<Clase,Integer>{

}
