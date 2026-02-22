package com.gimnasio.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.gimnasio.entity.*;
import com.gimnasio.security.JWTUtils;
import com.gimnasio.service.ActorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/actor")
@Tag(name = "Actor", description = "Operaciones sobre actores")
public class ActorController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private ActorService actorService;

    // ============================
    // LOGIN
    // ============================
    @PostMapping("/login")
    @Operation(summary = "Login de actor y generación de JWT")
    public ResponseEntity<Map<String, String>> login(@RequestBody ActorLogin actorLogin) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        actorLogin.getUsername(),
                        actorLogin.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateToken(authentication);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    // ============================
    // REGISTRO SEGÚN ROL
    // ============================
    @PostMapping
    @Operation(summary = "Registrar actor según rol")
    public ResponseEntity<?> registrar(@RequestBody Actor actor) {

        if (actor.getRol() == null) {
            return ResponseEntity.badRequest().body("Debe indicar un rol");
        }

        Actor nuevoActor;

        switch (actor.getRol()) {

            case Admin:
                nuevoActor = new Admin(
                        actor.getNombre(),
                        actor.getUsername(),
                        actor.getApellidos(),
                        actor.getEmail(),
                        actor.getFotografia(),
                        actor.getTelefono(),
                        actor.getEdad(),
                        actor.getRol(),
                        actor.getPassword()
                );
                break;

            case Usuario:
                nuevoActor = new Usuario(
                        actor.getNombre(),
                        actor.getUsername(),
                        actor.getApellidos(),
                        actor.getEmail(),
                        actor.getFotografia(),
                        actor.getTelefono(),
                        actor.getEdad(),
                        actor.getRol(),
                        actor.getPassword()
                );
                break;

            case Monitor:
                nuevoActor = new Monitor(
                        actor.getNombre(),
                        actor.getUsername(),
                        actor.getApellidos(),
                        actor.getEmail(),
                        actor.getFotografia(),
                        actor.getTelefono(),
                        actor.getEdad(),
                        actor.getRol(),
                        actor.getPassword()
                );
                break;

            default:
                return ResponseEntity.badRequest().body("Rol inválido");
        }

        Actor guardado = actorService.save(nuevoActor);

        return ResponseEntity.ok(guardado);
    }
    
    @GetMapping
    @Operation(summary = "Obtener todos los actores (Usuarios, Admins, Monitores)")
    public ResponseEntity<?> getAllActores() {
        try {
            // Llamamos al servicio que trae todos los actores
            return ResponseEntity.ok(actorService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener actores: " + e.getMessage());
        }
    }
}