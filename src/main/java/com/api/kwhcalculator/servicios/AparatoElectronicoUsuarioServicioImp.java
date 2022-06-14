package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.AparatoElectronicoUsuarioDTO;
import com.api.kwhcalculator.excepciones.ApiRestAppException;
import com.api.kwhcalculator.excepciones.ResourceNotFoundException;
import com.api.kwhcalculator.modelos.AparatoElectronicoUsuario;
import com.api.kwhcalculator.modelos.SectorEspecifico;
import com.api.kwhcalculator.modelos.SectorGeneral;
import com.api.kwhcalculator.repositorios.AparatoElectronicoUsuarioRepositorio;
import com.api.kwhcalculator.repositorios.SectorEspecificoRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        List<AparatoElectronicoUsuario> aparatosElectronicosUsuario = aparatoElectronicoUsuarioRepositorio.findBySectorEspecificoId(idSectorEspecifico);
        if (aparatosElectronicosUsuario.isEmpty()) {
            throw new ResourceNotFoundException("SectorEspecifico", "idSectorEspecifico", idSectorEspecifico);
        }
        //retornar una lista de entidades que llegaron de la base de datos mapeadas a DTO
        return aparatosElectronicosUsuario.stream().map(aparatoElectronicoUsuario -> mapearDTO(aparatoElectronicoUsuario)).collect(Collectors.toList());
    }

    @Override
    public AparatoElectronicoUsuarioDTO obtenerApElectroUsuarioPorSecEspecificoIdApElectroUsuarioId(long idSectorEspecifico, long idAparatoElectronicoUsuario) {
        //buscar en la base de datos
        SectorEspecifico sectorEspecifico = sectorEspecificoRepositorio.findById(idSectorEspecifico)
                .orElseThrow(() -> new ResourceNotFoundException("SectorEspecifico", "idSectorEspecifico", idSectorEspecifico));
        AparatoElectronicoUsuario aparatoElectronicoUsuario = aparatoElectronicoUsuarioRepositorio.findById(idAparatoElectronicoUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("AparatoElectronicoUsuario", "idAparatoElectronicoUsuario", idAparatoElectronicoUsuario));
        //si los IDs no coinciden, se lanzará una excepción
        if(!aparatoElectronicoUsuario.getSectorEspecifico().getId().equals(sectorEspecifico.getId())) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "El aparato electrónico no pertenece a este sector específico");
        }
        return mapearDTO(aparatoElectronicoUsuario);
    }

    //modificar un aparato electrónico usuario
    @Override
    public AparatoElectronicoUsuarioDTO modificarAparatoElectronicoUsuario(long idSectorEspecifico, long idAparatoElectronicoUsuario, AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO) {
        //buscar en la base de datos
        SectorEspecifico sectorEspecifico = sectorEspecificoRepositorio.findById(idSectorEspecifico)
                .orElseThrow(() -> new ResourceNotFoundException("SectorEspecifico", "idSectorEspecifico", idSectorEspecifico));
        AparatoElectronicoUsuario aparatoElectronicoUsuario = aparatoElectronicoUsuarioRepositorio.findById(idAparatoElectronicoUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("AparatoElectronicoUsuario", "idAparatoElectronicoUsuario", idAparatoElectronicoUsuario));
        //si los IDs no coinciden, se lanzará una excepción
        if(!aparatoElectronicoUsuario.getSectorEspecifico().getId().equals(sectorEspecifico.getId())) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "El aparato electrónico no pertenece a este sector específico");
        }
        //modificar atributos
        aparatoElectronicoUsuario.setNombreAparato(aparatoElectronicoUsuarioDTO.getNombreAparato());
        aparatoElectronicoUsuario.setCodigo(aparatoElectronicoUsuarioDTO.getCodigo());
        aparatoElectronicoUsuario.setMarca(aparatoElectronicoUsuarioDTO.getMarca());
        aparatoElectronicoUsuario.setWattsConsumo(aparatoElectronicoUsuarioDTO.getWattsConsumo());
        //guardar los cambios en la BD
        AparatoElectronicoUsuario aparatoElectronicoUsuarioActualizado = aparatoElectronicoUsuarioRepositorio.save(aparatoElectronicoUsuario);
        //devolver entidad como objeto de trasferencia
        return mapearDTO(aparatoElectronicoUsuarioActualizado);
    }

    //eliminar aparato electrónico buscándolo por su el ID del sector específico y el ID del aparato electrónico del usuario
    @Override
    public void eliminarAparatoElectronicoUsuario(long idSectorEspecifico, long idAparatoElectronicoUsuario) {
        //buscar en la base de datos
        SectorEspecifico sectorEspecifico = sectorEspecificoRepositorio.findById(idSectorEspecifico)
                .orElseThrow(() -> new ResourceNotFoundException("SectorEspecifico", "idSectorEspecifico", idSectorEspecifico));
        AparatoElectronicoUsuario aparatoElectronicoUsuario = aparatoElectronicoUsuarioRepositorio.findById(idAparatoElectronicoUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("AparatoElectronicoUsuario", "idAparatoElectronicoUsuario", idAparatoElectronicoUsuario));
        //si los IDs no coinciden, se lanzará una excepción
        if(!aparatoElectronicoUsuario.getSectorEspecifico().getId().equals(sectorEspecifico.getId())) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "El aparato electrónico no pertenece a este sector específico");
        }
        aparatoElectronicoUsuarioRepositorio.delete(aparatoElectronicoUsuario);
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
