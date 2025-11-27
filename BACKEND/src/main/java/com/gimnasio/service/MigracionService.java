package com.gimnasio.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.gimnasio.entity.Rol;
import com.gimnasio.entity.Usuario;
import com.gimnasio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MigracionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void migrarUsuarios() throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection("usuarios").get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

        for (QueryDocumentSnapshot doc : documents) {
            try {
                Usuario usuario = new Usuario();

                // Generar valores únicos si faltan o son repetidos
                String docId = doc.getId();

                // Nombre y apellidos
                usuario.setNombre(doc.contains("nombre") && !((String) doc.get("nombre")).isBlank() ? (String) doc.get("nombre") : "SinNombre");
                usuario.setApellidos(doc.contains("apellido") && !((String) doc.get("apellido")).isBlank() ? (String) doc.get("apellido") : "Desconocido");

                // Email único
                usuario.setEmail(doc.contains("email") && !((String) doc.get("email")).isBlank() ? (String) doc.get("email") : "usuario" + docId + "@dummy.com");

                // Telefono único
                usuario.setTelefono(doc.contains("telefono") && !((String) doc.get("telefono")).isBlank() ? (String) doc.get("telefono") : "600000000" + docId);

                usuario.setFotografia(doc.contains("fotografia") && !((String) doc.get("fotografia")).isBlank() ? (String) doc.get("fotografia") : "https://via.placeholder.com/150");
                usuario.setEdad(doc.contains("edad") ? ((Long) doc.get("edad")).intValue() : 0);
                String rolString = doc.contains("role") && !((String) doc.get("role")).isBlank() ? (String) doc.get("role") : "USUARIO";
                usuario.setRol(Rol.valueOf(rolString.toUpperCase())); // Convierte a mayúsculas para coincidir con el Enum
                // Guardar usuario en PostgreSQL
                usuarioRepository.save(usuario);

            } catch (Exception e) {
                System.out.println("Error guardando usuario Firestore ID " + doc.getId() + ": " + e.getMessage());
            }
        }

        System.out.println("Migración completada. IDs de PostgreSQL generados automáticamente.");
    }


}
