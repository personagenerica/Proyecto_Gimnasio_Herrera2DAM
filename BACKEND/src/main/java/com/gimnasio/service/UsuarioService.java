package com.gimnasio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Usuario;
import com.gimnasio.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- CRUD básico ---

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        // Validar que no exista otro usuario con el mismo email
    	
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario, int id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isEmpty()) {
            throw new IllegalArgumentException("No se encontró un usuario con el ID proporcionado");
        }

        Usuario usuarioExistente = optionalUsuario.get();

        // Actualizamos los campos heredados de Actor
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellidos(usuario.getApellidos());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setFotografia(usuario.getFotografia());
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setEdad(usuario.getEdad());

        return usuarioRepository.save(usuarioExistente);
    }

    public void delete(int id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: el usuario con ID " + id + " no existe");
        }
        usuarioRepository.deleteById(id);
    }
}
