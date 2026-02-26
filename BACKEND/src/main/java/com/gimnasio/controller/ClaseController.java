package com.gimnasio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.gimnasio.ClaseCreateDTO;
import com.gimnasio.entity.Clase;
import com.gimnasio.entity.Monitor;
import com.gimnasio.repository.ClaseRepository;
import com.gimnasio.repository.MonitorRepository;
import com.gimnasio.security.JWTUtils;
import com.gimnasio.service.ClaseService;

import java.util.List;

@RestController
@RequestMapping("/clase")
public class ClaseController {

	@Autowired
	private ClaseService claseService;

	@Autowired
	private JWTUtils jwtUtils;

	@Autowired
	private MonitorRepository monitorRepository;

	@Autowired
	private ClaseRepository claseRepository;

	/** Listar todas las clases */
	@GetMapping
	public List<Clase> listarClases() {
		return claseService.findAll();
	}

	/** Crear nueva clase (solo monitor) */
	@PostMapping()
	public ResponseEntity<Clase> crearClase(@RequestBody Clase c) {
		// Crear la clase
		Clase clase = new Clase();
		clase.setFecha_inicio(c.getFecha_inicio());
		clase.setFecha_fin(c.getFecha_fin());
		clase.setAforo(c.getAforo());
		clase.setMonitor(jwtUtils.userLogin());

		claseRepository.save(clase);
		return ResponseEntity.ok(clase);
	}

	/** Reservar clase (usuario autenticado con JWT) */
	@PostMapping("/{id}/reservar")
	public ResponseEntity<Clase> reservarClase(@PathVariable int id, Authentication authentication) {

		try {
			String username = authentication.getName(); // usuario desde JWT
			Clase claseReservada = claseService.reservarClasePorUsername(id, username);
			return ResponseEntity.ok(claseReservada);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}