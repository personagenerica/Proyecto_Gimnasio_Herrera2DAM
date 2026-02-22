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
    // REGISTRO ÚNICO (Actor genérico)
    // ============================
    @PostMapping
    @Operation(summary = "Registrar un actor (Admin, Usuario o Monitor) según rol")
    public ResponseEntity<Actor> registrar(@RequestBody Actor actor) {

        if (actor.getRol() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Aquí puedes añadir validaciones extra si quieres:
        // ej: validar email, username, etc.

        Actor guardado = actorService.save(actor);

        return ResponseEntity.ok(guardado);
    }
}