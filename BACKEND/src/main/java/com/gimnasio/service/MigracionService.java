package com.gimnasio.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.gimnasio.entity.Usuario;
import com.gimnasio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
//no funciona crare nueva bbdd para probar
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

                // Guardar el ID original de Firestore en un campo separado
               //usuario.setFirestoreId(doc.getId());

                // Campos obligatorios con valores por defecto si no existen
                usuario.setNombre(doc.contains("nombre") && !((String) doc.get("nombre")).isBlank() ? (String) doc.get("nombre") : "SinNombre");
                usuario.setApellidos(doc.contains("apellido") && !((String) doc.get("apellido")).isBlank() ? (String) doc.get("apellido") : "Desconocido");
                usuario.setEmail(doc.contains("email") && !((String) doc.get("email")).isBlank() ? (String) doc.get("email") : "noemail@dummy.com");
                usuario.setTelefono(doc.contains("telefono") && !((String) doc.get("telefono")).isBlank() ? (String) doc.get("telefono") : "600000000");
                usuario.setFotografia(doc.contains("fotografia") && !((String) doc.get("fotografia")).isBlank() ? (String) doc.get("fotografia") : "https://via.placeholder.com/150");
                usuario.setEdad(doc.contains("edad") ? ((Long) doc.get("edad")).intValue() : 0);
                usuario.setRol(doc.contains("role") && !((String) doc.get("role")).isBlank() ? (String) doc.get("role") : "usuario");

                // Guardar usuario en PostgreSQL; ID se genera automáticamente
                usuarioRepository.save(usuario);

            } catch (Exception e) {
                System.out.println("Error guardando usuario Firestore ID " + doc.getId() + ": " + e.getMessage());
            }
        }

        System.out.println("Migración completada. IDs de PostgreSQL generados automáticamente.");
    }
}
