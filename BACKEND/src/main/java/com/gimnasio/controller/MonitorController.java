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

import com.gimnasio.entity.Monitor;
import com.gimnasio.service.MonitorService;

@RestController
@RequestMapping("/monitor")
public class MonitorController {
	@Autowired
	
	private MonitorService monitorService;
	
	@GetMapping("")
	public List<Monitor> findAll(){
		return monitorService.findAll();
		
	}
	
	@GetMapping("/{id}")
	public Monitor findById(@PathVariable int id) {

		Optional<Monitor> oMonitor = monitorService.findById(id);
		
		if (oMonitor.isEmpty()) {
			return null;
		}else {
			return oMonitor.get();

		}
		
	}
	
	//Formulario
	@PostMapping
	public void save(@RequestBody Monitor monitor) {
		monitorService.save(monitor);
	}
	
	@PutMapping("/{id}")
	public void update(@RequestBody Monitor monitor,@PathVariable int id) {
		monitorService.update(monitor,id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		monitorService.delete(id);
	}	
	

}
