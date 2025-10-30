package com.gimnasio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gimnasio.entity.Clase;
import com.gimnasio.service.ClaseService;

@RestController
@RequestMapping("/clase")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    // --- Obtener todas las clases ---
    @GetMapping("")
    public List<Clase> findAll() {
        return claseService.findAll();
    }

    // --- Obtener una clase por ID ---
    @GetMapping("/{id}")
    public ResponseEntity<Clase> findById(@PathVariable int id) {
        Optional<Clase> oClase = claseService.findById(id);

        if (oClase.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(oClase.get());
        }
    }

    // --- Crear nueva clase ---
    @PostMapping("")
    public ResponseEntity<Clase> save(@RequestBody Clase clase) {
        try {
            Clase nuevaClase = claseService.save(clase);
            return ResponseEntity.ok(nuevaClase);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // --- Actualizar una clase existente ---
    @PutMapping("/{id}")
    public ResponseEntity<Clase> update(@RequestBody Clase clase, @PathVariable int id) {
        try {
            Clase actualizado = claseService.update(clase, id);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // --- Eliminar clase ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            claseService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
