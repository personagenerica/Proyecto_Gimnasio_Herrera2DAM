package com.gimnasio.service;

import com.gimnasio.entity.Usuario;
import com.gimnasio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExportService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void exportarUsuariosCSV() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        // ✅ Crear la ruta en el mismo directorio donde se ejecuta el proyecto
        String rutaFichero = Paths.get(System.getProperty("user.dir"), "usuarios_export.csv").toString();

        try (FileWriter writer = new FileWriter(rutaFichero)) {
            // Cabecera del CSV
            writer.append("ID,Nombre,Apellidos,Email,Telefono,Edad,Rol\n");

            for (Usuario u : usuarios) {
                writer.append(String.format("%d,%s,%s,%s,%s,%d,%s\n",
                        u.getId(),
                        u.getNombre(),
                        u.getApellidos(),
                        u.getEmail(),
                        u.getTelefono(),
                        u.getEdad(),
                        u.getRol()
                ));
            }

            System.out.println("✅ Exportación completada. Archivo creado en: " + rutaFichero);

        } catch (IOException e) {
            System.err.println("❌ Error exportando CSV: " + e.getMessage());
        }
    }
}
