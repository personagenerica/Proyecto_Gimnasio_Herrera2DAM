package com.gimnasio.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gimnasio.service.FirestoreImportService;

@RestController
@RequestMapping("/importar")
public class FirestoreImportController {

    @Autowired
    private FirestoreImportService importService;

    /**
     * Endpoint para importar usuarios desde Firestore a PostgreSQL.
     * GET http://localhost:8081/importar/usuarios
     */
    @GetMapping("/usuarios")
    public String importarUsuarios() {
        try {
            importService.importarUsuarios();
            return "Usuarios importados correctamente desde Firestore";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al importar usuarios: " + e.getMessage();
        }
    }

}
