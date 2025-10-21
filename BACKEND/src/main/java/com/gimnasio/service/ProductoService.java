package com.gimnasio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gimnasio.entity.Producto;
import com.gimnasio.repository.ProductoRepository;

@Service

public class ProductoService {
	@Autowired
	private ProductoRepository productoRepository;
	
	public List<Producto> findAll(){
		return productoRepository.findAll();
	}
	
	
	public Optional<Producto> findById( Integer id) {
		
		return productoRepository.findById(id);
	}
	
	
	public Producto save(Producto p) {
		
		return productoRepository.save(p);
	}
	
	
	public Producto update(Producto p, int id) {
		
		Optional<Producto> oProducto = findById(id);
		
		if (oProducto.isEmpty()) {
			return null;
		}else {
			//Sacamos de la caja el producto
			Producto productoBBDD=oProducto.get();
			
			//Seteamos uno a uno los campos
			productoBBDD.setNombre(p.getNombre());
			productoBBDD.setTipo(p.getTipo());
			productoBBDD.setPrecio(p.getPrecio());
			productoBBDD.setStock(p.getStock());
			
			return productoRepository.save(productoBBDD);

		}
		
		
	}
	
	
	public void delete(int id) {
		 productoRepository.deleteById(id);
	}
	 
	
	
	
	}
