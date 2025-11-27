package com.gimnasio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Actor;
import com.gimnasio.entity.Admin;
import com.gimnasio.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // --- CRUD básico ---

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Optional<Admin> findById(Integer id) {
        return adminRepository.findById(id);
    }

    public Admin save(Admin admin) {
     

        return adminRepository.save(admin);
    }

    public Admin update(Admin admin, int id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);

        if (optionalAdmin.isEmpty()) {
            throw new IllegalArgumentException("No se encontró un administrador con el ID proporcionado");
        }

        Admin adminExistente = optionalAdmin.get();

        // Actualizamos los campos heredados de Actor
        adminExistente.setNombre(admin.getNombre());
        adminExistente.setApellidos(admin.getApellidos());
        adminExistente.setEmail(admin.getEmail());
        adminExistente.setFotografia(admin.getFotografia());
        adminExistente.setTelefono(admin.getTelefono());
        adminExistente.setEdad(admin.getEdad());

        // Si Admin tiene campos adicionales, actualízalos también:
        adminExistente.setRol(admin.getRol());  // ← ejemplo de campo extra de Admin

        return adminRepository.save(adminExistente);
    }

    public void delete(int id) {
        if (!adminRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: el administrador con ID " + id + " no existe");
        }
        adminRepository.deleteById(id);
    }


	public Optional<Actor> findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
