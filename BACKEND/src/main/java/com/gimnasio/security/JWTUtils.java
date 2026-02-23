package com.gimnasio.security;

import java.util.Date;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gimnasio.entity.Actor;
import com.gimnasio.service.ActorService;
import com.gimnasio.service.AdminService;
import com.gimnasio.service.MonitorService;
import com.gimnasio.service.UsuarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {

    private static final String JWT_FIRMA = "firma_token";
    private static final long EXTENCION_TOKEN = 86400000; // 24 horas

    @Autowired
    @Lazy
    private ActorService actorService;

    @Autowired
    @Lazy
    private AdminService adminService;
    
    @Autowired
    @Lazy
    private UsuarioService usuarioService;
    
    @Autowired
    @Lazy
    private MonitorService monitorService;

    // Obtener token del header Authorization
    public static String getToken(HttpServletRequest request) {
        String tokenBearer = request.getHeader("Authorization");
        if (StringUtils.hasText(tokenBearer) && tokenBearer.startsWith("Bearer ")) {
            return tokenBearer.substring(7);
        }
        return null;
    }

    // Validar token JWT
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_FIRMA).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException(
                    "JWT ha expirado o no es válido"
            );
        }
    }

    // Generar token JWT
    public static String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + EXTENCION_TOKEN);
        String rol = authentication.getAuthorities().iterator().next().getAuthority();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(fechaActual)
                .setExpiration(fechaExpiracion)
                .claim("rol", rol)
                .signWith(SignatureAlgorithm.HS512, JWT_FIRMA)
                .compact();
    }

    // Obtener username del token
    public static String getUsernameOfToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Obtener usuario logueado
    @SuppressWarnings("unchecked")
    public <T> T userLogin() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        if (!StringUtils.hasText(username)) {
            return null;
        }

        Optional<Actor> actorO = actorService.findByUsername(username);
        if (!actorO.isPresent()) {
            return null;
        }

        Actor actor = actorO.get();
        switch (actor.getRol()) {
        //He creado los finbyusername en sus respectivos service mirar luego
            case Usuario:
                return (T) usuarioService.findByUsername(username).orElse(null);
            case Admin:
                return (T) adminService.findByUsername(username).orElse(null);
            case Monitor:
                return (T) monitorService.findByUsername(username).orElse(null);
            default:
                return null;
        }
    }
}
