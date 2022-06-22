package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.AparatoElectronicoOficialDTO;

import java.util.List;

public interface AparatoElectronicoOficialServicio {

    AparatoElectronicoOficialDTO crearAparatoElectronicoOficial(AparatoElectronicoOficialDTO aparatoElectronicoOficialDTO);

    List<AparatoElectronicoOficialDTO> obtenerTodosLosAparatosElectronicosOficiales();

    AparatoElectronicoOficialDTO obtenerAparatoElectronicoOficialPorId(long idAparatoElectronicoOficial);

    AparatoElectronicoOficialDTO modificarAparatoElectronicoOficial(AparatoElectronicoOficialDTO aparatoElectronicoOficialDTO, long idAparatoElectronicoOficial);

    void eliminarAparatoElectronicoOficial(long idAparatoElectronicoOficial);

}
