package com.gimnasio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gimnasio.entity.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra,Integer>{

}
