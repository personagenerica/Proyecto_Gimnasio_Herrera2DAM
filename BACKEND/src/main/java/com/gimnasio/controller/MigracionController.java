package com.gimnasio.controller;

import com.gimnasio.service.MigracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MigracionController {

    @Autowired
    private MigracionService migracionService;

    @GetMapping("/migracion/firestore-to-postgres")
    public ResponseEntity<String> migrar() {
        try {
            migracionService.migrarUsuarios();
            return ResponseEntity.ok("Migración completa");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en migración: " + e.getMessage());
        }
    }
}
