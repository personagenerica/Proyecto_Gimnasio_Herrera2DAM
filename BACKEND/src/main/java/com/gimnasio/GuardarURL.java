package com.gimnasio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class GuardarURL {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.juntadeandalucia.es/educacion/eaprendizaje/moodle-centros/");

            // Crear el contenido a guardar
            String contenido = ""
                    + "File: " + url.getFile() + "\n"
                    + "Host: " + url.getHost() + "\n"
                    + "Path: " + url.getPath() + "\n"
                    + "Protocolo: " + url.getProtocol() + "\n"
                    + "Puerto: " + url.getPort() + "\n"
                    + "Consulta: " + url.getQuery() + "\n"
                    + "Puerto por defecto: " + url.getDefaultPort() + "\n";

            // Guardar en archivo
            String archivo = "url_info.txt";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                bw.write(contenido);
            }

            System.out.println("Información de la URL guardada en: " + archivo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
