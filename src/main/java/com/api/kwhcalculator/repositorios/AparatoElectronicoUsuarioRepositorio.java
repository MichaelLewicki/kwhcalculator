package com.api.kwhcalculator.repositorios;

import com.api.kwhcalculator.modelos.AparatoElectronicoUsuario;
import com.api.kwhcalculator.modelos.SectorEspecifico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AparatoElectronicoUsuarioRepositorio extends JpaRepository<AparatoElectronicoUsuario, Long> {

    List<AparatoElectronicoUsuario> findBySectorEspecificoId(long idAparatoElectronicoUsuario);

}
