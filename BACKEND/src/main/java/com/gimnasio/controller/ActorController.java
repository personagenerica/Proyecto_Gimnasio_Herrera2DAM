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

import com.gimnasio.entity.Actor;
import com.gimnasio.entity.Producto;
import com.gimnasio.service.ActorService;

@RestController
@RequestMapping("/Actor")
public class ActorController {
	@Autowired
	
	private ActorService actorService;
	
	@GetMapping("")
	public List<Actor> findAll(){
		return actorService.findAll();
		
	}
	
	@GetMapping("/{id}")
	public Actor findById(@PathVariable int id) {

		Optional<Actor> oActor = actorService.findById(id);
		
		if (oActor.isEmpty()) {
			return null;
		}else {
			return oActor.get();

		}
		
	}
	
	//Formulario
	@PostMapping
	public void save(@RequestBody Actor a) {
		actorService.save(a);
	}
	
	@PutMapping("/{id}")
	public void update(@RequestBody Actor a,@PathVariable int id) {
		actorService.update(a,id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		actorService.delete(id);
	}	
	

}
