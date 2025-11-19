package com.gimnasio.controller;

import com.gimnasio.service.FirestoreExportService;
import com.gimnasio.service.FirestoreImportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestore")
public class FirestoreController {

    @Autowired
    private FirestoreExportService exportService;

    @Autowired
    private FirestoreImportService importService;

    /* =============================
     *   EXPORTAR Firestore → CSV
     * ============================= */
    
    /**
     * GET http://localhost:8080/firestore/export/csv/all
     */
    //Exportamos todas las colecciones a un csv
    @GetMapping("/export/csv/all")
    public String exportarTodasColecciones(
            @RequestParam(defaultValue = "export_todas.csv") String archivo
            //“Toma el parámetro archivo de la URL; si no se da, úsalo como "export_todas.csv"
    ) {
        try {
            exportService.exportarTodasColeccionesACsv(archivo);
            return "CSV con todas las colecciones generado correctamente: " + archivo;
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error al exportar todas las colecciones: " + e.getMessage();
        }
    }


    /* =============================
     *   IMPORTAR Firestore → PostgreSQL
     * ============================= */

    /**
     * GET http://localhost:8080/firestore/import/usuarios
     */
    @GetMapping("/import/usuarios")
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
