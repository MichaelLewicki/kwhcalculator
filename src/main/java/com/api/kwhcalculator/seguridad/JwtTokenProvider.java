package com.api.kwhcalculator.seguridad;

import com.api.kwhcalculator.excepciones.ApiRestAppException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    //la anotación Value sirve para obtener cualquier valor de alguna propiedad que se quiera indicar
     @Value("${app.jwt-secret}")
    private String jwtSecret;

     @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

     //método para generar un token
    public String generarToken(Authentication authentication) {
        //Asignar atributos
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);
        //construir el token
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(fechaExpiracion)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        //retornar
        return token;
    }

    public String obtenerUsernameDelJWT(String token) {
        //los claims son los datos del token (Ej: usuario, fechaCaducidad, roles, etc)
        //obtener todo el body del token
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        //obtener y retornar de los claims el sujeto (username)
        return claims.getSubject();
    }

    public boolean validarToken(String token) {
        try {
            //validar token con la clave secreta establecida en aplication properties
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "Firma JWT no válida");
        } catch (MalformedJwtException ex) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "Token JWT no válido");
        } catch (ExpiredJwtException ex) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "Token JWT caducado");
        } catch (UnsupportedJwtException ex) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "Token JWT no compatible");
        } catch (IllegalArgumentException ex) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "La cadena claims JWT está vacía");
        }
    }

}
