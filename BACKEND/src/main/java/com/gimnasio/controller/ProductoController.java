package com.gimnasio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gimnasio.entity.Producto;
import com.gimnasio.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	@Autowired
	
	private ProductoService productoService;
	
	@GetMapping("")
	public List<Producto> findAll(){
		return productoService.findAll();
		
	}
	
	@GetMapping("/{id}")
	public Producto findById(@PathVariable int id) {

		Optional<Producto> oProducto = productoService.findById(id);
		
		if (oProducto.isEmpty()) {
			return null;
		}else {
			return oProducto.get();

		}
		
	}
	
	//Formulario
	@PostMapping
	public void save(@RequestBody Producto p) {
		productoService.save(p);
	}
	
	@PutMapping("/{id}")
	public void update(@RequestBody Producto p,@PathVariable int id) {
		productoService.update(p,id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		productoService.delete(id);
	}	
	

}
