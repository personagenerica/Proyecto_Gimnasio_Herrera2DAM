package com.gimnasio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.*;

public class GuardarProducto {

	public static void main(String[] args) {
		Path ruta = Paths.get(".");
	    Convertir(ruta);
	}

   
    
    //Leer datos de BBDD y meterlos a fichero
    public static void Convertir(Path ruta) {
        //En tu casa es gimnasio_db, cambiar si es necesario
        String url="jdbc:postgresql://localhost:5432/gimnasio_db";
        String usuario="postgres";
        String contrasena="password";

        Connection con = null;

        try {
            con = DriverManager.getConnection(url, usuario, contrasena);

            String leer="SELECT id, nombre, precio, stock, tipo FROM public.producto;";
            PreparedStatement ps = con.prepareStatement(leer);
            ResultSet rs = ps.executeQuery();

            // Crear archivo
            Path archivo = Paths.get(ruta.toString(), "productos.txt");
            BufferedWriter writer = Files.newBufferedWriter(archivo);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String tipo = rs.getString("tipo");

                // Escribir al fichero
                writer.write(
                    id + " | " + nombre + " | " + precio + "€ | Unidades: " + stock + " | Tipo: " + tipo
                );
                writer.newLine();
            }

            writer.close();
            System.out.println("✅ Archivo creado en: " + archivo.toAbsolutePath());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    	
    }
    
    

