package com.api.kwhcalculator.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //obtener el token de la solicitud HTTP
        String token = obtenerJWTdeLaSolicitud(request);
        //validar el token
        if (StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
            //si la condición se cumple, obtener el username del token
            String username = jwtTokenProvider.obtenerUsernameDelJWT(token);
            //cargar el usuario asociado al token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //establecemos la seguridad
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
    //Bearer token de acceso (bearer es un formato que nos permite la autorización de un usuario y se usa al trabajar con Token)
    private String obtenerJWTdeLaSolicitud(HttpServletRequest request) {
        //obtener la autorización de la petición HTTP
        String bearerToken = request.getHeader("Authorization");
        //StringUtils tiene que ser de Spring Framework
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            //el primer parámetro es 7 porque hay que contar las letras de la palabra bearer (6 caracteres) + un espacio ( es 1 carácter)
            return bearerToken.substring(7, bearerToken.length());
        }
        //si la condición no se cumple, retorna null
        return null;
    }
}
