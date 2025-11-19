package com.gimnasio.service;

import java.io.FileWriter;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

@Service
public class FirestoreExportService {
//Exportar todo de Firestore
    private final Firestore db;
    //Inyectamos Firestore para no dar problemas
    public FirestoreExportService(Firestore db) {
        this.db = db;
    }

    /**
     * Exporta todas las colecciones y subcolecciones de Firestore a un CSV.
     * Cada colección y subcolección se separa con un encabezado indicando la ruta.
     *
     * @param archivoCsv Nombre del archivo CSV a generar
     */
    public void exportarTodasColeccionesACsv(String archivoCsv) throws Exception {
        try (FileWriter csvWriter = new FileWriter(archivoCsv)) {

            // Iterable de colecciones raíz
        	//Por cada coleccion escribimos
            Iterable<CollectionReference> colecciones = db.listCollections();
            for (CollectionReference coleccion : colecciones) {
                exportarColeccionRecursivo(coleccion, csvWriter, "");
            }
        }

        System.out.println("CSV generado con todas las colecciones y subcolecciones: " + archivoCsv);
    }

    /**
     * Exporta una colección y sus subcolecciones de forma recursiva.
     *
     * @param coleccion Colección a exportar
     * @param csvWriter FileWriter abierto para escribir
     * @param prefijo   Prefijo para indicar la ruta de la colección (ej. clases/reservas)
     */
    private void exportarColeccionRecursivo(CollectionReference coleccion, FileWriter csvWriter, String prefijo) throws Exception {
    	// Construye la ruta completa de la colección actual. 
    	// Si 'prefijo' está vacío (colección raíz), usa solo el nombre de la colección.
    	// Si hay un prefijo (subcolección), concatena el prefijo con el nombre de la colección actual usando "/"
    	// Ejemplo: 
    	// - Colección raíz: "usuarios" → rutaColeccion = "usuarios"
    	// - Subcolección: prefijo = "clases/doc123", coleccion.getId() = "reservas" → rutaColeccion = "clases/doc123/reservas"
    	String rutaColeccion = prefijo.isEmpty() ? coleccion.getId() : prefijo + "/" + coleccion.getId();
        csvWriter.append("=== Colección: ").append(rutaColeccion).append(" ===\n");

     // Solicita todos los documentos de la colección de Firestore.
     // coleccion.get() devuelve un ApiFuture<QuerySnapshot> (tarea asíncrona).
     // future.get() espera a que se complete la tarea y devuelve el QuerySnapshot real.
     // getDocuments() devuelve una lista de QueryDocumentSnapshot, donde cada uno representa un documento de la colección.
     // Cada documento contiene sus campos y valores, y permite acceder a subcolecciones si existen.
     ApiFuture<QuerySnapshot> future = coleccion.get();
     var documentos = future.get().getDocuments();


        if (documentos.isEmpty()) {
            csvWriter.append("No hay documentos\n\n");
            return;
        }

        // Cabecera: campos del primer documento
        var columnas = documentos.get(0).getData().keySet();
        csvWriter.append(String.join(",", columnas)).append("\n");

        for (DocumentSnapshot doc : documentos) {
            Map<String, Object> datos = doc.getData();

            // Escribir fila de valores
         // Construye una fila del CSV a partir de los campos del documento actual.
         // columnas.stream() recorre cada nombre de columna que definimos previamente.
         // map(col -> { ... }) obtiene el valor de esa columna en el documento:
          //  Object v = datos.get(col);           // obtiene el valor del campo
            //return v == null ? "" : v.toString().replace(",", " "); // si es nulo deja vacío, si tiene comas las reemplaza por espacios para no romper el CSV
         // .toList() convierte el stream en una lista de Strings con los valores de la fila.
         // csvWriter.append(String.join(",", fila)).append("\n"); 
            //Une todos los valores de la fila con comas y los escribe en el CSV, agregando un salto de línea al final.
         var fila = columnas.stream()
                 .map(col -> {
                     Object v = datos.get(col);
                     return v == null ? "" : v.toString().replace(",", " "); // evita romper CSV
                 })
                 .toList();
         csvWriter.append(String.join(",", fila)).append("\n");


            // Detectar subcolecciones (Iterable) y recorrerlas recursivamente
            Iterable<CollectionReference> subcolecciones = doc.getReference().listCollections();
            for (CollectionReference sub : subcolecciones) {
                exportarColeccionRecursivo(sub, csvWriter, rutaColeccion + "/" + doc.getId());
            }
        }

        csvWriter.append("\n"); // Separación visual entre colecciones
    }
}
