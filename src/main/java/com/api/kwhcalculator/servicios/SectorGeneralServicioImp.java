package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.SectorGeneralDTO;
import com.api.kwhcalculator.excepciones.ApiRestAppException;
import com.api.kwhcalculator.excepciones.ResourceNotFoundException;
import com.api.kwhcalculator.modelos.SectorGeneral;
import com.api.kwhcalculator.modelos.Usuario;
import com.api.kwhcalculator.repositorios.SectorGeneralRepositorio;
import com.api.kwhcalculator.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectorGeneralServicioImp implements SectorGeneralServicio {

    @Autowired
    private ModelMapper modelMapper;

    //inyectar repositorio del Sector General
    @Autowired
    private SectorGeneralRepositorio sectorGeneralRepositorio;

    //inyectar repositorio del Usuario
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    //Para crear un sector e ingresarlo a la BD
    //1. Primero hay que pasar el ID del usuario
    //2. Luego hay que pasar el sector que se quiere crear
    @Override
    public SectorGeneralDTO crearSectorGeneral(long idUsuario, SectorGeneralDTO sectorGeneralDTO) {
        //Convertir entidad a DTO
        SectorGeneral sectorGeneral = mapearEntidad(sectorGeneralDTO);
        //Buscar usuario al que se le desea asignar el sector por el ID del usuario
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "idUsuario", idUsuario));
        //Asignar usuario al Sector General
        sectorGeneral.setUsuario(usuario);
        //Asignar fecha del valor del Kwh
        sectorGeneral.setFechaIngresoValorKwh(LocalDate.now());
        //guardar sector
        SectorGeneral nuevoSectorGeneral = sectorGeneralRepositorio.save(sectorGeneral);
        return mapearDTO(nuevoSectorGeneral);
    }

    //método para obtener una lista de sectores generales mediante el ID del usuario
    @Override
    public List<SectorGeneralDTO> obtenerSectoresGeneralesPorUsuarioId(long idUsuario) {
        List<SectorGeneral> sectoresGenerales = sectorGeneralRepositorio.findByUsuarioId(idUsuario);
        if (sectoresGenerales.isEmpty()) {
            throw new ResourceNotFoundException("Usuario", "idUsuario", idUsuario);
        }
        //retornar una lista de entidades que llegaron de la base de datos mapeadas a DTO
        return sectoresGenerales.stream().map(sectorGeneral -> mapearDTO(sectorGeneral)).collect(Collectors.toList());
    }

    //método para obtener sector general mediante ID del usuario y ID del sector general
    @Override
    public SectorGeneralDTO obtenerSectorGeneralPorUsuarioIdSectorGeneralId(long idUsuario, long idSectorGeneral) {
        //buscar en la base de datos
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "idUsuario", idUsuario));
        SectorGeneral sectorGeneral = sectorGeneralRepositorio.findById(idSectorGeneral)
                .orElseThrow(() -> new ResourceNotFoundException("SectorGeneral", "idSectorGeneral", idSectorGeneral));
        //si los IDs no coinciden, se lanzará una excepción
        if(!sectorGeneral.getUsuario().getId().equals(usuario.getId())) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "El sector general no pertenece a este usuario");
        }
        return mapearDTO(sectorGeneral);
    }

    //método para modificar sector general
    @Override
    public SectorGeneralDTO modificarSectorGeneral(long idUsuario, long idSectorGeneral, SectorGeneralDTO sectorGeneralDTO) {
        //buscar en la base de datos
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "idUsuario", idUsuario));
        SectorGeneral sectorGeneral = sectorGeneralRepositorio.findById(idSectorGeneral)
                .orElseThrow(() -> new ResourceNotFoundException("SectorGeneral", "idSectorGeneral", idSectorGeneral));
        //si los IDs no coinciden, se lanzará una excepción
        if(!sectorGeneral.getUsuario().getId().equals(usuario.getId())) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "El sector general no pertenece a este usuario");
        }
        //modificar atributos
        sectorGeneral.setNomSectorGnral(sectorGeneralDTO.getNomSectorGnral());
        sectorGeneral.setValorKwh(sectorGeneralDTO.getValorKwh());
        sectorGeneral.setFechaIngresoValorKwh(sectorGeneralDTO.getFechaIngresoValorKwh());
        sectorGeneral.setMtrsCuadrados(sectorGeneralDTO.getMtrsCuadrados());
        sectorGeneral.setTotalConsumoW(sectorGeneralDTO.getTotalConsumoW());
        sectorGeneral.setTotalPesos(sectorGeneralDTO.getTotalPesos());
        //guardar los cambios en la BD
        SectorGeneral sectorGeneralActualizado = sectorGeneralRepositorio.save(sectorGeneral);
        //devolver entidad como objeto de trasferencia
        return mapearDTO(sectorGeneralActualizado);
    }

    //método para eliminar sector general
    @Override
    public void eliminarSectorGeneral(long idUsuario, long idSectorGeneral) {
        //buscar en la base de datos
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "idUsuario", idUsuario));
        SectorGeneral sectorGeneral = sectorGeneralRepositorio.findById(idSectorGeneral)
                .orElseThrow(() -> new ResourceNotFoundException("SectorGeneral", "idSectorGeneral", idSectorGeneral));
        //si los IDs no coinciden, se lanzará una excepción
        if(!sectorGeneral.getUsuario().getId().equals(usuario.getId())) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "El sector general no pertenece a este usuario");
        }
        sectorGeneralRepositorio.delete(sectorGeneral);
    }

    //Este método convierte de DTO a entidad
    private SectorGeneral mapearEntidad (SectorGeneralDTO sectorGeneralDTO) {
        //establecer los valores del objeto de transferencia (DTO) a la entidad (SectorGeneral)
        SectorGeneral sectorGeneral = modelMapper.map(sectorGeneralDTO, SectorGeneral.class);
        //retornar
        return sectorGeneral;
    }

    //Este método convierte entidad a DTO
    private SectorGeneralDTO mapearDTO (SectorGeneral sectorGeneral) {
        //establecer los valores de la entidad (SectorGeneral) al objeto de transferencia (DTO)
        SectorGeneralDTO sectorGeneralDTO = modelMapper.map(sectorGeneral, SectorGeneralDTO.class);
        //retornar
        return sectorGeneralDTO;
    }
}
