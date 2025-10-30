package com.gimnasio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {

    public static void main(String[] args) {
        // Ruta al escritorio
        String fileName = System.getProperty("user.home") + "/Desktop/usuarios.csv";

        // Datos de ejemplo
        String[][] datos = {
            {"Tatiana", "Domínguez Martín", "tatdommar93@gmail.com"},
            {"Francisco","Domínguez Porras","pacoauxiliadora@hotmail.com"}
        };

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            // Escribir datos
            for (String[] fila : datos) {
                bw.write(String.join(",", fila));
                bw.newLine();
            }

            System.out.println("CSV creado correctamente en: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
