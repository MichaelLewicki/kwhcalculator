package com.api.kwhcalculator.repositorios;

import com.api.kwhcalculator.modelos.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//fijarse en si en parámetro está bien USUARIO porque quizá deba estar Rol ahí
public interface RolRespositorio extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre (String nombre);

}
