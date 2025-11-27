package com.gimnasio.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LimpiarDuplicadosService {

    public void limpiarDuplicados() throws Exception {
        Firestore db = FirestoreClient.getFirestore();

        // Obtener todos los documentos
        ApiFuture<QuerySnapshot> query = db.collection("usuarios").get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

        System.out.println("Total documentos: " + documents.size());

        // Mapas para agrupar por email y telefono
        Map<String, List<QueryDocumentSnapshot>> emailsMap = new HashMap<>();
        Map<String, List<QueryDocumentSnapshot>> telefonosMap = new HashMap<>();

        for (QueryDocumentSnapshot doc : documents) {
            String email = doc.contains("email") && !((String) doc.get("email")).isBlank() ? ((String) doc.get("email")).trim() : "NO_EMAIL";
            String telefono = doc.contains("telefono") && !((String) doc.get("telefono")).isBlank() ? ((String) doc.get("telefono")).trim() : "NO_TELEFONO";

            emailsMap.computeIfAbsent(email, k -> new ArrayList<>()).add(doc);
            telefonosMap.computeIfAbsent(telefono, k -> new ArrayList<>()).add(doc);
        }

        // Función para eliminar duplicados dejando el más antiguo
        eliminarDuplicados(db, emailsMap, "Email");
        eliminarDuplicados(db, telefonosMap, "Teléfono");

        System.out.println("Proceso de limpieza completado.");
    }

    private void eliminarDuplicados(Firestore db, Map<String, List<QueryDocumentSnapshot>> map, String tipo) throws Exception {
        for (Map.Entry<String, List<QueryDocumentSnapshot>> entry : map.entrySet()) {
            List<QueryDocumentSnapshot> docs = entry.getValue();
            if (docs.size() > 1) {
                // Ordenar por ID ascendente (más antiguo primero)
                docs.sort(Comparator.comparing(QueryDocumentSnapshot::getId));

                System.out.println("=== " + tipo + " duplicado: " + entry.getKey() + " ===");
                System.out.println("Manteniendo ID: " + docs.get(0).getId());

                // Eliminar todos menos el primero
                for (int i = 1; i < docs.size(); i++) {
                    db.collection("usuarios").document(docs.get(i).getId()).delete();
                    System.out.println("Eliminado ID: " + docs.get(i).getId());
                }
            }
        }
    }
}
