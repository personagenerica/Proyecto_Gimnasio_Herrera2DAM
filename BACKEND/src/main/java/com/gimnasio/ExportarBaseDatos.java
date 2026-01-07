package com.gimnasio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExportarBaseDatos {

    private static final String URL = "jdbc:postgresql://localhost:5432/gimnasio";
    private static final String USER = "postgres";
    private static final String PASS = "password";

    // Exporta SOLO la tabla ACTOR
    public static List<Path> exportarActor(Path ruta) {

        List<Path> archivosGenerados = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            Path archivo = exportarTabla(con, "actor", ruta);

            if (archivo != null) {
                archivosGenerados.add(archivo);
            }

        } catch (SQLException e) {
            System.err.println("Error al acceder a la base de datos");
            e.printStackTrace();
        }

        return archivosGenerados;
    }

    // Exporta UNA tabla
    private static Path exportarTabla(Connection con, String tabla, Path ruta) {

        Path archivo = ruta.resolve(tabla + ".txt");
        String sql = "SELECT * FROM " + tabla;

        try (
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            BufferedWriter writer = Files.newBufferedWriter(archivo)
        ) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnas = rsmd.getColumnCount();

            // Encabezados
            for (int i = 1; i <= columnas; i++) {
                writer.write(rsmd.getColumnName(i) + " | ");
            }
            writer.newLine();
            writer.write("==============================================");
            writer.newLine();

            // Datos
            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    writer.write(rs.getString(i) + " | ");
                }
                writer.newLine();
            }

            return archivo;

        } catch (SQLException | IOException e) {
            System.err.println("Error exportando tabla: " + tabla);
            e.printStackTrace();
        }

        return null;
    }
}
