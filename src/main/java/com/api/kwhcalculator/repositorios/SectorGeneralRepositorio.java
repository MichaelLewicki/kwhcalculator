package com.api.kwhcalculator.repositorios;

import com.api.kwhcalculator.modelos.SectorGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SectorGeneralRepositorio extends JpaRepository<SectorGeneral, Long> {

    List<SectorGeneral> findByUsuarioId(long idUsuario);

}