package com.gimnasio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gimnasio.entity.Usuario;
import com.gimnasio.repository.UsuarioRepository;
import com.gimnasio.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository; // para export

    @GetMapping("")
    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public Usuario findById(@PathVariable int id) {
        return usuarioService.findById(id).orElse(null);
    }

    @PostMapping
    public void save(@RequestBody Usuario user) {
        usuarioService.save(user);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Usuario user, @PathVariable int id) {
        usuarioService.update(user, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        usuarioService.delete(id);
    }

    // Export CSV
    @GetMapping("/export")
    public ResponseEntity<String> exportUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        StringBuilder csv = new StringBuilder();
        csv.append("id,nombre,apellidos,email,telefono,edad,rol,fotografia\n");
        for (Usuario u : usuarios) {
            csv.append(u.getId()).append(",")
               .append(u.getNombre()).append(",")
               .append(u.getApellidos()).append(",")
               .append(u.getEmail()).append(",")
               .append(u.getTelefono()).append(",")
               .append(u.getEdad()).append(",")
               .append(u.getRol()).append(",")
               .append(u.getFotografia()).append("\n");
        }

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"usuarios.csv\"")
                .body(csv.toString());
    }
}
