package com.gimnasio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;

@Service
public class ExportarDatosService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    /** EXPORTAR A JSON */
    public void exportarTablaAJson(String nombreTabla, String rutaArchivo) throws Exception {
        List<Map<String, Object>> datos = jdbcTemplate.queryForList("SELECT * FROM " + nombreTabla);

        mapper.writerWithDefaultPrettyPrinter().writeValue(
                new java.io.File(rutaArchivo), datos
        );

        System.out.println("Datos exportados a JSON correctamente: " + rutaArchivo);
    }

    /** EXPORTAR A CSV */
    public void exportarTablaACsv(String nombreTabla, String rutaArchivo) throws Exception {
        List<Map<String, Object>> datos = jdbcTemplate.queryForList("SELECT * FROM " + nombreTabla);

        if (datos.isEmpty()) return;

        FileWriter csvWriter = new FileWriter(rutaArchivo);

        // Cabeceras
        var columnas = datos.get(0).keySet();
        csvWriter.append(String.join(",", columnas)).append("\n");

        // Filas
        for (var fila : datos) {
            csvWriter.append(String.join(",", fila.values().stream().map(v -> v == null ? "" : v.toString()).toList()));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

        System.out.println("Datos exportados a CSV correctamente: " + rutaArchivo);
    }
}
