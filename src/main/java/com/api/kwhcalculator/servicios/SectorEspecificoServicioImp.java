package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.SectorEspecificoDTO;
import com.api.kwhcalculator.excepciones.ApiRestAppException;
import com.api.kwhcalculator.excepciones.EmptyException;
import com.api.kwhcalculator.excepciones.ResourceNotFoundException;
import com.api.kwhcalculator.modelos.AparatoElectronicoUsuario;
import com.api.kwhcalculator.modelos.SectorEspecifico;
import com.api.kwhcalculator.modelos.SectorGeneral;
import com.api.kwhcalculator.repositorios.SectorEspecificoRepositorio;
import com.api.kwhcalculator.repositorios.SectorGeneralRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Transactional
    public List<SectorEspecificoDTO> obtenerSecEspecificosPorSecGeneralId(long idSectorGeneral) {
        List<SectorEspecifico> sectoresEspecificos = sectorEspecificoRepositorio.findBySectorGeneralId(idSectorGeneral);
        if (sectoresEspecificos.isEmpty()) {
            //throw new ResourceNotFoundException("SectorGeneral", "idSectorGeneral", idSectorGeneral);
            throw new EmptyException(false);
        } else {
            acumularWattsPorSectorGeneralId(idSectorGeneral);
        }
        //retornar una lista de entidades que llegaron de la base de datos mapeadas a DTO
        return sectoresEspecificos.stream().map(sectorEspecifico -> mapearDTO(sectorEspecifico)).collect(Collectors.toList());
    }

    //Acumular Metros cuadrados de todos los sectores específicos de un sector general mediante idUsuario
    //@Override
    //@Transactional
    public void acumularWattsPorSectorGeneralId(long idSectorGeneral) {
        //buscar en la BD
        List<SectorEspecifico> sectoresEspecificos = sectorEspecificoRepositorio.findBySectorGeneralId(idSectorGeneral);
        //Declarar acumulador
        double acumConsumoTotalKwhMes = 0;
        int minutosAlDia = 0;
        double cantWatts = 0;
        //recorrer lista sectores
        for (SectorEspecifico sector : sectoresEspecificos) {
            Set<AparatoElectronicoUsuario> aparatoElectronicoUsuarios = sector.getAparatosElectronicosUsuario();
            //recorrer lista de usuarios
            for (AparatoElectronicoUsuario aparato : aparatoElectronicoUsuarios) {
                //consumoTotalW = consumoTotalW + aparato.getWattsConsumo();
                //consumoTotalW = consumoTotalW + aparato.getWattsConsumo();
                LocalTime tiempoUsoDiario = aparato.getTiempoUsoDiario();
                cantWatts = aparato.getWattsConsumo();
                //convertir tiempo a minutos por día
                minutosAlDia = tiempoUsoDiario.get(ChronoField.MINUTE_OF_DAY);
                acumConsumoTotalKwhMes = acumConsumoTotalKwhMes + (cantWatts * minutosAlDia / 60 / 1000);

            }
            sector.setTotalConsumoW(acumConsumoTotalKwhMes);
            //calcular valor en pesos
            double valorKwh = sector.getSectorGeneral().getValorKwh();
            sector.setTotalPesos(acumConsumoTotalKwhMes * valorKwh);
            acumConsumoTotalKwhMes = 0;
        }
    }

    @Override
    public SectorEspecificoDTO obtenerSecEspecificoPorSecGeneralIdSecEspecificoId(long idSectorGeneral, long idSectorEspecifico) {
        //buscar en la base de datos
        SectorGeneral sectorGeneral = sectorGeneralRepositorio.findById(idSectorGeneral)
                .orElseThrow(() -> new ResourceNotFoundException("SectorGeneral", "idSectorGeneral", idSectorGeneral));
        SectorEspecifico sectorEspecifico = sectorEspecificoRepositorio.findById(idSectorEspecifico)
                .orElseThrow(() -> new ResourceNotFoundException("SectorEspecifico", "idSectorEspecifico", idSectorEspecifico));
        //si los IDs no coinciden, se lanzará una excepción
        if(!sectorEspecifico.getSectorGeneral().getId().equals(sectorGeneral.getId())) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "El sector especifico no pertenece a este sector general");
        }
        return mapearDTO(sectorEspecifico);
    }

    @Override
    public SectorEspecificoDTO modificarSectorEspecifico(long idSectorGeneral, long idSectorEspecifico, SectorEspecificoDTO sectorEspecificoDTO) {
        //buscar en la base de datos
        SectorGeneral sectorGeneral = sectorGeneralRepositorio.findById(idSectorGeneral)
                .orElseThrow(() -> new ResourceNotFoundException("SectorGeneral", "idSectorGeneral", idSectorGeneral));
        SectorEspecifico sectorEspecifico = sectorEspecificoRepositorio.findById(idSectorEspecifico)
                .orElseThrow(() -> new ResourceNotFoundException("SectorEspecifico", "idSectorEspecifico", idSectorEspecifico));
        //si los IDs no coinciden, se lanzará una excepción
        if(!sectorEspecifico.getSectorGeneral().getId().equals(sectorGeneral.getId())) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "El sector especifico no pertenece a este sector general");
        }
        //modificar atributos
        sectorEspecifico.setNomSectorEspecifico(sectorEspecificoDTO.getNomSectorEspecifico());
        sectorEspecifico.setMtrsCuadrados(sectorEspecificoDTO.getMtrsCuadrados());
        sectorEspecifico.setTotalConsumoW(sectorEspecificoDTO.getTotalConsumoW());
        sectorEspecifico.setTotalPesos(sectorEspecificoDTO.getTotalPesos());
        //guardar los cambios en la BD
        SectorEspecifico sectorEspecificoActualizado = sectorEspecificoRepositorio.save(sectorEspecifico);
        //devolver entidad como objeto de trasferencia
        return mapearDTO(sectorEspecificoActualizado);
    }

    @Override
    public void eliminarSectorEspecifico(long idSectorGeneral, long idSectorEspecifico) {
        //buscar en la base de datos
        SectorGeneral sectorGeneral = sectorGeneralRepositorio.findById(idSectorGeneral)
                .orElseThrow(() -> new ResourceNotFoundException("SectorGeneral", "idSectorGeneral", idSectorGeneral));
        SectorEspecifico sectorEspecifico = sectorEspecificoRepositorio.findById(idSectorEspecifico)
                .orElseThrow(() -> new ResourceNotFoundException("SectorEspecifico", "idSectorEspecifico", idSectorEspecifico));
        //si los IDs no coinciden, se lanzará una excepción
        if(!sectorEspecifico.getSectorGeneral().getId().equals(sectorGeneral.getId())) {
            throw new ApiRestAppException(HttpStatus.BAD_REQUEST, "El sector especifico no pertenece a este sector general");
        }
        sectorEspecificoRepositorio.delete(sectorEspecifico);
    }

    //Este método convierte de DTO a entidad
    private SectorEspecifico mapearEntidad (SectorEspecificoDTO sectorEspecificoDTO) {
        //establecer los valores del objeto de transferencia (DTO) a la entidad (SectorEspecifico)
        SectorEspecifico sectorEspecifico = modelMapper.map(sectorEspecificoDTO, SectorEspecifico.class);
        //retornar
        return sectorEspecifico;
    }

    //Este método convierte entidad a DTO
    private SectorEspecificoDTO mapearDTO (SectorEspecifico sectorEspecifico) {
        //establecer los valores de la entidad (SectorEspecifico) al objeto de transferencia (DTO)
        SectorEspecificoDTO sectorEspecificoDTO = modelMapper.map(sectorEspecifico, SectorEspecificoDTO.class);
        //retornar
        return sectorEspecificoDTO;
    }
}
