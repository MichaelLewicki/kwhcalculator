package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.SectorEspecificoDTO;
import com.api.kwhcalculator.dto.SectorGeneralDTO;
import com.api.kwhcalculator.modelos.SectorEspecifico;

import java.util.List;

public interface SectorEspecificoServicio {

    SectorEspecificoDTO crearSectorEspecifico(long idSectorGeneral, SectorEspecificoDTO sectorEspecificoDTO);

    List<SectorEspecificoDTO> obtenerSecEspecificosPorSecGeneralId(long idSectorEspecifico);

    SectorEspecificoDTO obtenerSecEspecificoPorSecGeneralIdSecEspecificoId(long idSectorGeneral, long idSectorEspecifico);

    SectorEspecificoDTO modificarSectorEspecifico(long idSectorGeneral, long idSectorEspecifico, SectorEspecificoDTO sectorEspecificoDTO);

    void eliminarSectorEspecifico(long idSectorGeneral, long idSectorEspecifico);


}
