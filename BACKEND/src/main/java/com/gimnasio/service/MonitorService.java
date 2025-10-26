package com.gimnasio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Monitor;
import com.gimnasio.repository.MonitorRepository;

@Service
public class MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;

    // --- CRUD básico ---

    public List<Monitor> findAll() {
        return monitorRepository.findAll();
    }

    public Optional<Monitor> findById(Integer id) {
        return monitorRepository.findById(id);
    }

    public Monitor save(Monitor monitor) {

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
}
