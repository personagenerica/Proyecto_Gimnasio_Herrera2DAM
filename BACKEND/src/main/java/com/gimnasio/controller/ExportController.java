package com.gimnasio.controller;

import com.gimnasio.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private ExportService exportService;

    // Ejemplo: http://localhost:8080/export/usuarios/csv
    @GetMapping("/usuarios/csv")
    public String exportarUsuariosCSV() {
        exportService.exportarUsuariosCSV();
        return "✅ Exportación completada. Archivo generado en el directorio del proyecto.";
    }
}
