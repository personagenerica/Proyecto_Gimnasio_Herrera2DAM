package com.gimnasio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gimnasio.entity.Actor;
import com.gimnasio.entity.ActorLogin;
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

    @GetMapping("")
    @Operation(summary = "Listar todos los actores")
    public List<Actor> findAll() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener actor por ID")
    public Actor findById(@PathVariable int id) {
        Optional<Actor> oActor = actorService.findById(id);
        return oActor.orElse(null);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo actor")
    public void save(@RequestBody Actor a) {
        actorService.save(a);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un actor existente")
    public void update(@RequestBody Actor a, @PathVariable int id) {
        actorService.update(a, id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un actor por ID")
    public void delete(@PathVariable int id) {
        actorService.delete(id);
    }

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
}
