package com.api.kwhcalculator.servicios;
import com.api.kwhcalculator.dto.SectorGeneralDTO;
import java.util.List;

public interface SectorGeneralServicio {

    SectorGeneralDTO crearSectorGeneral(long idUsuario, SectorGeneralDTO sectorGeneralDTO);

    List<SectorGeneralDTO> obtenerSectoresGeneralesPorUsuarioId(long idUsuario);

    SectorGeneralDTO obtenerSectorGeneralPorUsuarioIdSectorGeneralId(long idUsuario, long idSectorGeneral);

    SectorGeneralDTO modificarSectorGeneral(long idUsuario, long idSectorGeneral, SectorGeneralDTO sectorGeneralDTO);

    void eliminarSectorGeneral(long idUsuario, long idSectorGeneral);
}
