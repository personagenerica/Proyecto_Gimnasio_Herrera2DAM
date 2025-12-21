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

import com.gimnasio.entity.Compra;
import com.gimnasio.service.CompraService;


@RestController
@RequestMapping("/compra")
public class CompraController {
	@Autowired
	
	private CompraService compraService;
	
	@GetMapping("")
	public List<Compra> findAll(){
		return compraService.findAll();
		
	}
	
	@GetMapping("/{id}")
	public Compra findById(@PathVariable int id) {

		Optional<Compra> oCompra = compraService.findById(id);
		
		if (oCompra.isEmpty()) {
			return null;
		}else {
			return oCompra.get();

		}
		
	}
	
	//Formulario
	@PostMapping
	public void save(@RequestBody Compra compra) {
		compraService.save(compra);
	}
	

	

}
