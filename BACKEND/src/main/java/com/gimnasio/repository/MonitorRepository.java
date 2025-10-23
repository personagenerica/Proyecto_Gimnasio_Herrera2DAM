package com.gimnasio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gimnasio.entity.Monitor;


@Repository
public interface MonitorRepository extends JpaRepository<Monitor,Integer>{

}
