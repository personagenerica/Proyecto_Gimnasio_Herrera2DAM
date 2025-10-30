package com.gimnasio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gimnasio.entity.Admin;
import com.gimnasio.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // --- Obtener todos los administradores ---
    @GetMapping("")
    public List<Admin> findAll() {
        return adminService.findAll();
    }

    // --- Obtener un administrador por ID ---
    @GetMapping("/{id}")
    public ResponseEntity<Admin> findById(@PathVariable int id) {
        Optional<Admin> oAdmin = adminService.findById(id);

        if (oAdmin.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(oAdmin.get());
        }
    }

    // --- Crear nuevo administrador ---
    @PostMapping("")
    public ResponseEntity<Admin> save(@RequestBody Admin admin) {
        try {
            Admin nuevo = adminService.save(admin);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // --- Actualizar un administrador existente ---
    @PutMapping("/{id}")
    public ResponseEntity<Admin> update(@RequestBody Admin admin, @PathVariable int id) {
        try {
            Admin actualizado = adminService.update(admin, id);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // --- Eliminar un administrador por ID ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            adminService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
