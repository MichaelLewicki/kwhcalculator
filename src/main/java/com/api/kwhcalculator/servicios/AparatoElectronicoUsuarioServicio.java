package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.AparatoElectronicoUsuarioDTO;

import java.util.List;

public interface AparatoElectronicoUsuarioServicio {

    AparatoElectronicoUsuarioDTO crearAparatoElectronicoUsuario(long idSectorEspecifico, AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO);

    List<AparatoElectronicoUsuarioDTO> obtenerApElectroUsuarioPorSecEspecificoId(long idSectorEspecifico);

    AparatoElectronicoUsuarioDTO obtenerApElectroUsuarioPorSecEspecificoIdApElectroUsuarioId(long idSectorEspecifico, long idAparatoElectronicoUsuario);

    AparatoElectronicoUsuarioDTO modificarAparatoElectronicoUsuario(long idSectorEspecifico, long idAparatoElectronicoUsuario, AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO);

    void eliminarAparatoElectronicoUsuario(long idSectorEspecifico, long idAparatoElectronicoUsuario);

}
