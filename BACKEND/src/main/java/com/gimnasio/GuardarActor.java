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

public class GuardarActor {

    public static void main(String[] args) {
    	Path ruta = Paths.get("c:"); 
    	Convertir(ruta);
        
    }
    
   
    
    //Leer datos de BBDD y meterlos a fichero
    public static void Convertir(Path ruta) {
    	String url="jdbc:postgresql://localhost:5432/gimnasio";
    	Connection con = null;
    	
    	String usuario="postgres";
    	String contrasena="password";
    	try {
			
    	con = DriverManager.getConnection(url, usuario, contrasena);
    	//try {
    	//	if (Files.notExists(ruta, null)) 
    		
        		//Files.createFile(ruta, null);
        		
    	//	}
    		
    		
    		//BufferedWriter bw = Files.newBufferedWriter(ruta, null);
    		
    		
				
				
				String leer="SELECT id, nombre, precio, stock, tipo\r\n"
						+ "	FROM public.producto;";
				
				PreparedStatement ps = con.prepareStatement(leer);
				
				ResultSet rs = ps.executeQuery();
				//Recorremos
				System.out.println("Hola");
				while (rs.next()) {

					System.out.println(rs.getInt(1));
					System.out.println(rs.getString(2));
					System.out.println(rs.getDouble(3));
					System.out.println(rs.getInt(4));
					System.out.println(rs.getString(5));
					}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
			
			
			
			
		//} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    	
    }
    
    

