package com.api.kwhcalculator.repositorios;

import com.api.kwhcalculator.modelos.SectorEspecifico;
import com.api.kwhcalculator.modelos.SectorGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectorEspecificoRepositorio extends JpaRepository<SectorEspecifico, Long> {

    List<SectorEspecifico> findBySectorGeneralId(long idSectorGeneral);

}
