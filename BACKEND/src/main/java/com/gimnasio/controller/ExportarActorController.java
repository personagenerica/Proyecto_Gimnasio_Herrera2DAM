package com.gimnasio.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gimnasio.ExportarBaseDatos;

@RestController
@RequestMapping("/exportar")
public class ExportarActorController {

    @GetMapping("/actor")
    public String exportarActor() {

        Path ruta = Paths.get(".");

        List<Path> archivos = ExportarBaseDatos.exportarActor(ruta);

        if (archivos.isEmpty()) {
            return "❌ No se pudo exportar la tabla ACTOR";
        }

        return "✅ Tabla ACTOR exportada correctamente en: "
                + archivos.get(0).toAbsolutePath();
    }
}
