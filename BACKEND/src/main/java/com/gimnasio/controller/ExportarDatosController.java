package com.gimnasio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gimnasio.service.ExportarDatosService;

@RestController
@RequestMapping("/exportar")
public class ExportarDatosController {

    @Autowired
    private ExportarDatosService exportarDatosService;

    @GetMapping("/json/{tabla}")
    public String exportarJson(@PathVariable String tabla) throws Exception {
        exportarDatosService.exportarTablaAJson(tabla, tabla + ".json");
        return "Archivo " + tabla + ".json generado";
    }

    @GetMapping("/csv/{tabla}")
    public String exportarCsv(@PathVariable String tabla) throws Exception {
        exportarDatosService.exportarTablaACsv(tabla, tabla + ".csv");
        return "Archivo " + tabla + ".csv generado";
    }
}
