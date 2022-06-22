package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.SectorGeneralDTO;
import com.api.kwhcalculator.excepciones.ApiRestAppException;
import com.api.kwhcalculator.excepciones.EmptyException;
import com.api.kwhcalculator.excepciones.ResourceNotFoundException;
import com.api.kwhcalculator.modelos.AparatoElectronicoUsuario;
import com.api.kwhcalculator.modelos.SectorEspecifico;
import com.api.kwhcalculator.modelos.SectorGeneral;
import com.api.kwhcalculator.modelos.Usuario;
import com.api.kwhcalculator.repositorios.SectorGeneralRepositorio;
import com.api.kwhcalculator.repositorios.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
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
    @Transactional
    public List<SectorGeneralDTO> obtenerSectoresGeneralesPorUsuarioId(long idUsuario) {
        List<SectorGeneral> sectoresGenerales = sectorGeneralRepositorio.findByUsuarioId(idUsuario);
        if (sectoresGenerales.isEmpty()) {
            //throw new ResourceNotFoundException("SectorGeneral", "idUsuario", idUsuario);
            throw new EmptyException(false);
        } else {
            acumularMtrsCuadradosPorUsuarioId(idUsuario);
        }
        //retornar una lista de entidades que llegaron de la base de datos mapeadas a DTO
        return sectoresGenerales.stream().map(sectorGeneral -> mapearDTO(sectorGeneral)).collect(Collectors.toList());
    }

    public void acumularMtrsCuadradosPorUsuarioId(long idUsuario) {
        //buscar en la BD
        List<SectorGeneral> sectoresGenerales = sectorGeneralRepositorio.findByUsuarioId(idUsuario);
        //Declarar acumulador
        double acumMtrsCuadrados = 0;
        double acumTotalConsumoKwhMes = 0;
        double acumTotalPesos = 0;
        //recorrer lista sectoresGenerales
        for (SectorGeneral sectorGeneral : sectoresGenerales) {
            Set<SectorEspecifico> sectoresEspecificos = sectorGeneral.getSectoresEspecificos();
            //recorrer lista de sectoresEspecíficos
            for (SectorEspecifico sectorEspecifico : sectoresEspecificos) {
                acumMtrsCuadrados = acumMtrsCuadrados + sectorEspecifico.getMtrsCuadrados();
                acumTotalConsumoKwhMes = acumTotalConsumoKwhMes + sectorEspecifico.getTotalConsumoW();
                acumTotalPesos = acumTotalPesos + sectorEspecifico.getTotalPesos();
            }
            sectorGeneral.setMtrsCuadrados(acumMtrsCuadrados);
            sectorGeneral.setTotalConsumoW(acumTotalConsumoKwhMes);
            sectorGeneral.setTotalPesos(acumTotalPesos);
            acumMtrsCuadrados = 0;
            acumTotalConsumoKwhMes = 0;
            acumTotalPesos = 0;
            //List<AparatoElectronicoUsuario> aparatosElectronicosUsuarios = null;
            //aparatosElectronicosUsuarios.add((AparatoElectronicoUsuario) sector.getAparatosElectronicosUsuario());
            //Set<AparatoElectronicoUsuario> aparatoElectronicoUsuarios = sector.getAparatosElectronicosUsuario();
            //sector.setTotalPesos(sector.getTotalConsumoW()+);
        }
        //return sectoresEspecificos.stream().map(sectorEspecifico -> mapearDTO(sectorEspecifico)).collect(Collectors.toList());
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
