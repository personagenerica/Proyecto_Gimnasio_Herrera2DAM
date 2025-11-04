package com.gimnasio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Compra;
import com.gimnasio.repository.CompraRepository;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    // --- CRUD básico ---

    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    public Optional<Compra> findById(Integer id) {
        return compraRepository.findById(id);
    }

    public Compra save(Compra compra) {
        // Validaciones básicas
        if (compra.getTicket() == null || compra.getTicket().trim().isEmpty()) {
            throw new IllegalArgumentException("El ticket no puede estar vacío");
        }

        if (compra.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        return compraRepository.save(compra);
    }

    public Compra update(Compra compra, int id) {
        Optional<Compra> optionalCompra = compraRepository.findById(id);

        if (optionalCompra.isEmpty()) {
            throw new IllegalArgumentException("No se encontró una compra con el ID proporcionado");
        }

        Compra compraExistente = optionalCompra.get();

        // Actualizamos los campos necesarios
        compraExistente.setTicket(compra.getTicket());
        compraExistente.setCantidad(compra.getCantidad());
        compraExistente.setProducto(compra.getProducto());
        compraExistente.setUsuarios(compra.getUsuarios());

        return compraRepository.save(compraExistente);
    }

    public void delete(int id) {
        if (!compraRepository.existsById(id)) {
            throw new IllegalArgumentException("No se puede eliminar: la compra con ID " + id + " no existe");
        }
        compraRepository.deleteById(id);
    }
}
