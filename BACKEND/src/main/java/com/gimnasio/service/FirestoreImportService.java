package com.gimnasio.service;

import com.gimnasio.entity.Usuario;
import com.gimnasio.repository.UsuarioRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FirestoreImportService {

    private final UsuarioRepository usuarioRepository;
    private final Validator validator;

    public FirestoreImportService(UsuarioRepository usuarioRepository, Validator validator) {
        this.usuarioRepository = usuarioRepository;
        this.validator = validator;
    }

    /**
     * Importa usuarios desde Firestore a PostgreSQL.
     * No modifica manualmente el ID; usa Hibernate para generar IDs únicos.
     * Devuelve un resumen con la cantidad de usuarios importados y los saltados por validación.
     */
    @Transactional
    public String importarUsuarios() throws Exception {
        var db = FirestoreClient.getFirestore();
        CollectionReference usuariosRef = db.collection("usuarios");
        ApiFuture<QuerySnapshot> future = usuariosRef.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        int importados = 0;
        List<String> saltados = new ArrayList<>();

        for (DocumentSnapshot doc : documents) {
            Usuario usuario = new Usuario();

            usuario.setNombre(doc.getString("nombre"));
            usuario.setApellidos(doc.getString("apellido")); // Firestore "apellido"
            usuario.setEmail(doc.getString("email"));
            usuario.setTelefono(doc.getString("telefono"));
            usuario.setRol(doc.getString("role")); // Firestore "role"

            Long edadLong = doc.getLong("edad");
            usuario.setEdad(edadLong != null ? edadLong.intValue() : 0);

            // Guardar el id de Firebase como campo aparte
            usuario.setFirebaseId(doc.getId());

            // Validar antes de guardar
            Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
            if (!violations.isEmpty()) {
                StringBuilder errores = new StringBuilder();
                for (ConstraintViolation<Usuario> v : violations) {
                    errores.append(v.getPropertyPath())
                           .append("=").append(v.getMessage())
                           .append("; ");
                }
                saltados.add(usuario.getNombre() + " " + usuario.getApellidos() + " -> " + errores);
                continue; // Saltar este usuario inválido
            }

            // Guardar usuario válido (Hibernate genera ID automáticamente)
            usuarioRepository.save(usuario);
            importados++;
        }

        StringBuilder resumen = new StringBuilder();
        resumen.append("Usuarios importados: ").append(importados).append("\n");
        if (!saltados.isEmpty()) {
            resumen.append("Usuarios saltados por validación:\n");
            saltados.forEach(s -> resumen.append(" - ").append(s).append("\n"));
        }

        return resumen.toString();
    }
}
