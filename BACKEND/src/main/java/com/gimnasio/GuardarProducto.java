package com.gimnasio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//Crear controlador
public class GuardarProducto {

    public static void main(String[] args) {
        Path ruta = Paths.get(".");
        Path archivo = Convertir(ruta); // crea el archivo desde la base de datos

        //Si el archivo existe lo lee
        if (archivo != null) {
            LeerArchivo(archivo); // luego lo lee y muestra el contenido
        }
    }

    // Leer datos de BBDD y meterlos en fichero
    public static Path Convertir(Path ruta) {
    	/*try {
        		if (Files.notExists(ruta, null)) 
        		
            		Files.createFile(ruta, null);
            		
        		}*/
    	//Url base de datos
        String url = "jdbc:postgresql://localhost:5432/gimnasio";
        String usuario = "postgres";
        String contrasena = "password";

        //Consulta SQL
        String sql = "SELECT id, nombre, precio, stock, tipo FROM public.producto;";
        Path archivo = Paths.get(ruta.toString(), "productos.txt");

        try (
        		//Preparamos conexion con la base de datos
            Connection con = DriverManager.getConnection(url, usuario, contrasena);
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            BufferedWriter writer = Files.newBufferedWriter(archivo)
        ) {
            while (rs.next()) {
            	//Preparamos los datos que vamos a guardar
            	String encabezado="id | nombre | precio | Unidades | Tipo\n"
            			+ "#########################################################\n";
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String tipo = rs.getString("tipo");
                
                //Escribimos en el fichero
                writer.write(encabezado + id + " | " + nombre + " | " + precio + "€ |" + stock + " | Tipo: " + tipo);
                writer.newLine();
            }

            System.out.println("Archivo creado en: " + archivo.toAbsolutePath());
            return archivo; // devuelve la ruta del archivo creado
            //Manejo de errores
        } catch (SQLException e) {
            System.err.println("Error de base de datos:");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de escritura de archivo:");
            e.printStackTrace();
        }

        return null; // en caso de error
    }

    
    // Leer el fichero y mostrarlo por consola
    public static void LeerArchivo(Path archivo) {
        System.out.println("\nContenido de " + archivo.toAbsolutePath() + ":\n");
       
        try (BufferedReader reader = Files.newBufferedReader(archivo)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo:");
            e.printStackTrace();
        }
    }
}
