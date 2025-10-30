package com.gimnasio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GuardarActor {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        File archivo = new File("usuarios.txt");
        String lineSep = System.getProperty("line.separator");

        // Pedir datos al usuario
        System.out.print("Ingrese ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine().trim();

        
        System.out.print("Ingrese apellidos: ");
        String apellidos = sc.nextLine().trim();

        System.out.print("Ingrese edad: ");
        String edad = sc.nextLine().trim();

        System.out.print("Ingrese correo: ");
        String correo = sc.nextLine().trim();

        System.out.print("Ingrese rol: ");
        String rol = sc.nextLine().trim();

        // Validaciones básicas
        if (id.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty() || correo.isEmpty() || rol.isEmpty()) {
            System.out.println("Todos los campos son obligatorios.");
            sc.close();
            return;
        }

        // Validar edad como número positivo
        int edadNum;
        try {
            edadNum = Integer.parseInt(edad);
            if (edadNum <= 0) {
                System.out.println("Edad inválida.");
                sc.close();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Edad debe ser un número.");
            sc.close();
            return;
        }

        // Comprobar duplicados
        boolean existe = false;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = br.readLine()) != null) {
            	//Comprobamos que no haya duplicados en las columnas correspondientes
                String[] comprobar = line.split(",");
                if (comprobar[0].equals(id)) {
                    System.out.println("ID ya existe.");
                    existe = true;
                    break;
                }
                if (comprobar[3].equals(correo)) {
                    System.out.println("Correo ya registrado.");
                    existe = true;
                    break;
                }
            }
        } catch (IOException e) {
            // Si no existe el archivo aún, se ignora
        }

        // Guardar datos si no hay duplicados
        if (!existe) {
            String datos = id + "," + nombre + "," + edadNum + "," + correo + "," + rol;
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
                bw.write(datos + lineSep);
                System.out.println("Datos guardados en: " + archivo.getName());
                System.out.println("Ruta completa: " + archivo.getCanonicalPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Mostrar todo el contenido del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            System.out.println("\n=== Contenido de usuarios.txt ===");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}
