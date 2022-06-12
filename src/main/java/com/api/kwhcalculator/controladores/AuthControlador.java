package com.api.kwhcalculator.controladores;

import com.api.kwhcalculator.dto.LoginDTO;
import com.api.kwhcalculator.dto.RegistroUsuarioDTO;
import com.api.kwhcalculator.modelos.Rol;
import com.api.kwhcalculator.modelos.Usuario;
import com.api.kwhcalculator.repositorios.RolRespositorio;
import com.api.kwhcalculator.repositorios.UsuarioRepositorio;
import com.api.kwhcalculator.seguridad.JWTAuthResponseDTO;
import com.api.kwhcalculator.seguridad.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

    //inyectar dependencias (todo aquel componente de spring que esté registrado como un Bean se puede inyectar)
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRespositorio rolRespositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    //Si el usuario o email y la contraseña coinciden, se otorgará un Token de autenticación al usuario en su navegador o dispositivo
    @PostMapping("/iniciarSesion")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //obtenemos el token del JwtProvider
        String token = jwtTokenProvider.generarToken(authentication);
        //return new ResponseEntity<>("Ha logrado iniciar sesión exitosamente", HttpStatus.OK); esto era así antes de implementar JWT
        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    //método para registrar un usuario (se le otorgará el rol ADMIN)
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroUsuarioDTO registroUsuarioDTO) {
        if (usuarioRepositorio.existsByUsername(registroUsuarioDTO.getUsername())) {
            return new ResponseEntity<>("El nombre de usuario ya se encuentra registrado", HttpStatus.BAD_REQUEST);
        } else if (usuarioRepositorio.existsByEmail(registroUsuarioDTO.getEmail())) {
            return new ResponseEntity<>("El email ingresado ya se encuentra registrado", HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(registroUsuarioDTO.getUsername());
        usuario.setEmail(registroUsuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroUsuarioDTO.getPassword()));
        Rol roles = rolRespositorio.findByNombre("ROLE_ADMIN").get();
        //se le ingresa al usuario un rol Singleton ya que la instancia al rol son compartidas entre uno y muchos usuarios
        usuario.setRoles(Collections.singleton(roles));
        usuarioRepositorio.save(usuario);
        return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
    }



}
