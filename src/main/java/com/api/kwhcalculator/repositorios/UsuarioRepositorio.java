package com.api.kwhcalculator.repositorios;

import com.api.kwhcalculator.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//hereda de JpaRespositoy comportamientos para comunicarse con la BD (nombreClase, tipoDelId)
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByUsernameOrEmail(String username, String email);

    //los siguientes métodos realizarán las validaciones en la base de datos de los métodos anteriores

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
