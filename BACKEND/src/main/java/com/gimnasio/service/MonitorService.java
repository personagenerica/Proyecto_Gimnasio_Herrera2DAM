package com.gimnasio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Actor;
import com.gimnasio.entity.Monitor;
import com.gimnasio.entity.Rol;
import com.gimnasio.repository.MonitorRepository;

@Service
public class MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;
    @Autowired
	private PasswordEncoder passwordEncoder;
    // --- CRUD básico ---

    public List<Monitor> findAll() {
        return monitorRepository.findAll();
    }

    public Optional<Monitor> findById(Integer id) {
        return monitorRepository.findById(id);
    }

    public Monitor save(Monitor monitor) {
    	monitor.setPassword(passwordEncoder.encode(monitor.getPassword()));
    	monitor.setRol(Rol.Monitor);
        return monitorRepository.save(monitor);
    }

    public Monitor update(Monitor monitor, int id) {
        Optional<Monitor> oMonitor = monitorRepository.findById(id);

        if (oMonitor.isEmpty()) {
            return null;
        }

        Monitor monitorExistente = oMonitor.get();

        // Actualizamos los campos heredados de Actor
        monitorExistente.setNombre(monitor.getNombre());
        monitorExistente.setApellidos(monitor.getApellidos());
        monitorExistente.setEmail(monitor.getEmail());
        monitorExistente.setFotografia(monitor.getFotografia());
        monitorExistente.setTelefono(monitor.getTelefono());
        monitorExistente.setEdad(monitor.getEdad());

        return monitorRepository.save(monitorExistente);
    }

    public void delete(int id) {
        monitorRepository.deleteById(id);
    }

	public Optional<Actor> findByUsername(String username) {
		return monitorRepository.findByUsername(username);
	}
}
