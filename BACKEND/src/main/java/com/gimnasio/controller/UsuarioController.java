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

import com.gimnasio.entity.Usuario;
import com.gimnasio.service.UsuarioService;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {
	@Autowired
	
	private UsuarioService usuarioService;
	
	@GetMapping("")
	public List<Usuario> findAll(){
		return usuarioService.findAll();
		
	}
	
	@GetMapping("/{id}")
	public Usuario findById(@PathVariable int id) {

		Optional<Usuario> oUsuario = usuarioService.findById(id);
		
		if (oUsuario.isEmpty()) {
			return null;
		}else {
			return oUsuario.get();

		}
		
	}
	
	//Formulario
	@PostMapping
	public void save(@RequestBody Usuario user) {
		usuarioService.save(user);
	}
	
	@PutMapping("/{id}")
	public void update(@RequestBody Usuario user,@PathVariable int id) {
		usuarioService.update(user,id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		usuarioService.delete(id);
	}	
	

}
