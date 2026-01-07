package com.gimnasio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Compra;
import com.gimnasio.entity.Usuario;
import com.gimnasio.repository.CompraRepository;
import com.gimnasio.security.JWTUtils;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private JWTUtils JWTUtils;

    // --- CRUD básico ---

    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    public Optional<Compra> findById(Integer id) {
        Optional<Compra> res = Optional.empty();
        Optional<Compra> comprao = compraRepository.findById(id);

        if (comprao.isPresent()) {
            Usuario user = JWTUtils.userLogin();

            // Verificamos si la lista de usuarios contiene al usuario actual
            if (comprao.get().getUsuarios() != null && comprao.get().getUsuarios().contains(user)) {
                res = comprao;
            }
        }

        return res;
    }

    public Compra save(Compra compra) {
        Usuario user = JWTUtils.userLogin();

        // Inicializamos la lista si es null
        if (compra.getUsuarios() == null) {
            compra.setUsuarios(new ArrayList<>());
        }

        // Agregamos el usuario actual a la lista
        compra.getUsuarios().add(user);

        // Validaciones básicas
        if (compra.getTicket() == null || compra.getTicket().trim().isEmpty()) {
            throw new IllegalArgumentException("El ticket no puede estar vacío");
        }

        if (compra.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        return compraRepository.save(compra);
    }
}
