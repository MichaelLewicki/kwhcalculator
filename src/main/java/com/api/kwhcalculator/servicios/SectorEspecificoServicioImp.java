package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.SectorEspecificoDTO;
import com.api.kwhcalculator.dto.SectorGeneralDTO;
import com.api.kwhcalculator.excepciones.ApiRestAppException;
import com.api.kwhcalculator.excepciones.ResourceNotFoundException;
import com.api.kwhcalculator.modelos.SectorEspecifico;
import com.api.kwhcalculator.modelos.SectorGeneral;
import com.api.kwhcalculator.modelos.Usuario;
import com.api.kwhcalculator.repositorios.SectorEspecificoRepositorio;
import com.api.kwhcalculator.repositorios.SectorGeneralRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SectorEspecificoServicioImp implements SectorEspecificoServicio{

    //inyectar dependencias

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SectorEspecificoRepositorio sectorEspecificoRepositorio;

    @Autowired
    private SectorGeneralRepositorio sectorGeneralRepositorio;

    //implementar métodos

    @Override
    public SectorEspecificoDTO crearSectorEspecifico(long idSectorGeneral, SectorEspecificoDTO sectorEspecificoDTO) {
        //Convertir entidad a DTO
        SectorEspecifico sectorEspecifico = mapearEntidad(sectorEspecificoDTO);
        //Buscar sector general al que se le desea asignar el sector específico por el ID del sector general
        SectorGeneral sectorGeneral = sectorGeneralRepositorio.findById(idSectorGeneral)
                .orElseThrow(() -> new ResourceNotFoundException("SectorGeneral", "idSectorGeneral", idSectorGeneral));
        //Asignar sector general al sector específico
        sectorEspecifico.setSectorGeneral(sectorGeneral);
        //guardar sector
        SectorEspecifico nuevoSectorEspecifico = sectorEspecificoRepositorio.save(sectorEspecifico);
        return mapearDTO(nuevoSectorEspecifico);
    }

    @Override
    public List<SectorEspecificoDTO> obtenerSecEspecificosPorSecGeneralId(long idSectorEspecifico) {
        return null;
    }

    @Override
    public SectorEspecificoDTO obtenerSecEspecificoPorSecGeneralIdSecEspecificoId(long idSectorGeneral, long idSectorEspecifico) {
        return null;
    }

    @Override
    public SectorEspecificoDTO modificarSectorEspecifico(long idSectorGeneral, long idSectorEspecifico, SectorEspecificoDTO sectorEspecificoDTO) {
        return null;
    }

    @Override
    public void eliminarSectorEspecifico(long idSectorGeneral, long idSectorEspecifico) {

    }

    //Este método convierte de DTO a entidad
    private SectorEspecifico mapearEntidad (SectorEspecificoDTO sectorEspecificoDTO) {
        //establecer los valores del objeto de transferencia (DTO) a la entidad (SectorGeneral)
        SectorEspecifico sectorEspecifico = modelMapper.map(sectorEspecificoDTO, SectorEspecifico.class);
        //retornar
        return sectorEspecifico;
    }

    //Este método convierte entidad a DTO
    private SectorEspecificoDTO mapearDTO (SectorEspecifico sectorEspecifico) {
        //establecer los valores de la entidad (SectorGeneral) al objeto de transferencia (DTO)
        SectorEspecificoDTO sectorEspecificoDTO = modelMapper.map(sectorEspecifico, SectorEspecificoDTO.class);
        //retornar
        return sectorEspecificoDTO;
    }
}
