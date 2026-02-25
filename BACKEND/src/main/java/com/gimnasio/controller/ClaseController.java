package com.gimnasio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gimnasio.entity.Clase;
import com.gimnasio.service.ClaseService;

import java.util.List;

@RestController
@RequestMapping("/clase")
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    /** Listar todas las clases */
    @GetMapping
    public List<Clase> listarClases() {
        return claseService.findAll();
    }

    /** Crear nueva clase */
    @PostMapping
    public ResponseEntity<Clase> crearClase(@RequestBody Clase clase) {
        try {
            Clase nuevaClase = claseService.create(clase);
            return ResponseEntity.ok(nuevaClase);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /** Reservar clase */
    @PostMapping("/{id}/reservar")
    public ResponseEntity<Clase> reservarClase(
            @PathVariable int id,
            @RequestParam int usuarioId) {

        try {
            Clase claseReservada = claseService.reservarClase(id, usuarioId);
            return ResponseEntity.ok(claseReservada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}