package com.api.kwhcalculator.repositorios;

import com.api.kwhcalculator.dto.AparatoElectronicoOficialDTO;
import com.api.kwhcalculator.modelos.AparatoElectronicoOficial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AparatoElectronicoOficialRepositorio extends JpaRepository<AparatoElectronicoOficial, Long> {

}
