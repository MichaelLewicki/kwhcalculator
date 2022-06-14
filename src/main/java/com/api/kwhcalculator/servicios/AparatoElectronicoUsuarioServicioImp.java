package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.AparatoElectronicoUsuarioDTO;
import com.api.kwhcalculator.excepciones.ResourceNotFoundException;
import com.api.kwhcalculator.modelos.AparatoElectronicoUsuario;
import com.api.kwhcalculator.modelos.SectorEspecifico;
import com.api.kwhcalculator.modelos.SectorGeneral;
import com.api.kwhcalculator.repositorios.AparatoElectronicoUsuarioRepositorio;
import com.api.kwhcalculator.repositorios.SectorEspecificoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AparatoElectronicoUsuarioServicioImp implements AparatoElectronicoUsuarioServicio {

    //inyectar dependencias

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AparatoElectronicoUsuarioRepositorio aparatoElectronicoUsuarioRepositorio;

    @Autowired
    private SectorEspecificoRepositorio sectorEspecificoRepositorio;

    @Override
    public AparatoElectronicoUsuarioDTO crearAparatoElectronicoUsuario(long idSectorEspecifico, AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO) {
        //Convertir entidad a DTO
        AparatoElectronicoUsuario aparatoElectronicoUsuario = mapearEntidad(aparatoElectronicoUsuarioDTO);
        //Buscar sector general al que se le desea asignar el sector específico por el ID del sector general
        SectorEspecifico sectorEspecifico = sectorEspecificoRepositorio.findById(idSectorEspecifico)
                .orElseThrow(() -> new ResourceNotFoundException("SectorEspecifico", "idSectorEspecifico", idSectorEspecifico));
        //Asignar sector específico al aparato Electronico
        aparatoElectronicoUsuario.setSectorEspecifico(sectorEspecifico);
        //guardar aparato electrónico usuario
        AparatoElectronicoUsuario nuevoAparatoElectronicoUsuario = aparatoElectronicoUsuarioRepositorio.save(aparatoElectronicoUsuario);
        return mapearDTO(nuevoAparatoElectronicoUsuario);
    }

    @Override
    public List<AparatoElectronicoUsuarioDTO> obtenerApElectroUsuarioPorSecEspecificoId(long idSectorEspecifico) {
        return null;
    }

    @Override
    public AparatoElectronicoUsuarioDTO obtenerApElectroUsuarioPorSecEspecificoIdApElectroUsuarioId(long idSectorEspecifico, long idAparatoElectronicoUsuario) {
        return null;
    }

    @Override
    public AparatoElectronicoUsuarioDTO modificarAparatoElectronicoUsuario(long idSectorEspecifico, long idAparatoElectronicoUsuario, AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO) {
        return null;
    }

    @Override
    public void eliminarAparatoElectronicoUsuario(long idSectorEspecifico, long idAparatoElectronicoUsuario) {

    }

    //Este método convierte de DTO a entidad
    private AparatoElectronicoUsuario mapearEntidad (AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO) {
        //establecer los valores del objeto de transferencia (DTO) a la entidad (AparatoElectronicoUsuario)
        AparatoElectronicoUsuario aparatoElectronicoUsuario = modelMapper.map(aparatoElectronicoUsuarioDTO, AparatoElectronicoUsuario.class);
        //retornar
        return aparatoElectronicoUsuario;
    }

    //Este método convierte entidad a DTO
    private AparatoElectronicoUsuarioDTO mapearDTO (AparatoElectronicoUsuario aparatoElectronicoUsuario) {
        //establecer los valores de la entidad (AparatoElectronicoUsuario) al objeto de transferencia (DTO)
        AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO = modelMapper.map(aparatoElectronicoUsuario, AparatoElectronicoUsuarioDTO.class);
        //retornar
        return aparatoElectronicoUsuarioDTO;
    }

}
