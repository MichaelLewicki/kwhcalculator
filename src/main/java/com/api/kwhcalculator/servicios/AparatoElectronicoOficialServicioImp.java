package com.api.kwhcalculator.servicios;

import com.api.kwhcalculator.dto.AparatoElectronicoOficialDTO;
import com.api.kwhcalculator.dto.UsuarioDTO;
import com.api.kwhcalculator.excepciones.ResourceNotFoundException;
import com.api.kwhcalculator.modelos.AparatoElectronicoOficial;
import com.api.kwhcalculator.modelos.Usuario;
import com.api.kwhcalculator.repositorios.AparatoElectronicoOficialRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AparatoElectronicoOficialServicioImp implements AparatoElectronicoOficialServicio{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AparatoElectronicoOficialRepositorio aparatoElectronicoOficialRepositorio;

    @Override
    public AparatoElectronicoOficialDTO crearAparatoElectronicoOficial(AparatoElectronicoOficialDTO aparatoElectronicoOficialDTO) {
        //1. Convertimos de DTO a entidad (se convierte a una entidad para guardarlo en la base de datos)
        AparatoElectronicoOficial aparatoElectronicoOficial = mapearEntidad(aparatoElectronicoOficialDTO);
        //2. Guardar en el repositorio (esta linea inserta o persiste un registro en la base de datos)
        AparatoElectronicoOficial nuevoAparatoElectronicoOficial = aparatoElectronicoOficialRepositorio.save(aparatoElectronicoOficial);
        //luego del .save(), procederemos a dar una respuesta en Json
        //3. Convertimos de entidad a DTO (lo que sigue se usa para devuelver una respuesta del registro en Json,
        AparatoElectronicoOficialDTO aparatoElectronicoOficicialRespuesta = mapearDTO(nuevoAparatoElectronicoOficial);
        return aparatoElectronicoOficicialRespuesta;
    }

    @Override
    public List<AparatoElectronicoOficialDTO> obtenerTodosLosAparatosElectronicosOficiales() {
        List<AparatoElectronicoOficial> listaDeAparatosElectronicosOficiales = aparatoElectronicoOficialRepositorio.findAll();
        return listaDeAparatosElectronicosOficiales
                .stream().map(aparatoElectronicoOficial -> mapearDTO(aparatoElectronicoOficial))
                .collect(Collectors.toList());
    }

    @Override
    public AparatoElectronicoOficialDTO obtenerAparatoElectronicoOficialPorId(long idAparatoElectronicoOficial) {
        AparatoElectronicoOficial aparatoElectronicoOficial = aparatoElectronicoOficialRepositorio.findById(idAparatoElectronicoOficial)
                .orElseThrow(() -> new ResourceNotFoundException("AparatoElectronicoOficial", "idAparatoElectronicoOficial", idAparatoElectronicoOficial));
        return mapearDTO(aparatoElectronicoOficial);
    }

    @Override
    public AparatoElectronicoOficialDTO modificarAparatoElectronicoOficial(AparatoElectronicoOficialDTO aparatoElectronicoOficialDTO, long idAparatoElectronicoOficial) {
        //buscar usuario en la BD
        AparatoElectronicoOficial aparatoElectronicoOficial = aparatoElectronicoOficialRepositorio.findById(idAparatoElectronicoOficial)
                .orElseThrow(() -> new ResourceNotFoundException("AparatoElectronicoOficial", "idAparatoElectronicoOficial", idAparatoElectronicoOficial));
        //modificar atributos
        aparatoElectronicoOficial.setNombreAparato(aparatoElectronicoOficialDTO.getNombreAparato());
        aparatoElectronicoOficial.setCodigo(aparatoElectronicoOficialDTO.getCodigo());
        aparatoElectronicoOficial.setMarca(aparatoElectronicoOficialDTO.getMarca());
        aparatoElectronicoOficial.setWattsConsumo(aparatoElectronicoOficial.getWattsConsumo());
        //insertar registro en la base de datos y obtener el resultado de la inserción
        AparatoElectronicoOficial AparatoElectronicoOficialModificado = aparatoElectronicoOficialRepositorio.save(aparatoElectronicoOficial);
        //retornar mediante un objeto de transferencia de datos
        return mapearDTO(aparatoElectronicoOficial);
    }

    @Override
    public void eliminarAparatoElectronicoOficial(long idAparatoElectronicoOficial) {
        AparatoElectronicoOficial aparatoElectronicoOficial = aparatoElectronicoOficialRepositorio.findById(idAparatoElectronicoOficial)
                .orElseThrow(() -> new ResourceNotFoundException("AparatoElectronicoOficial", "idAparatoElectronicoOficial", idAparatoElectronicoOficial));
        //para eliminar, simplemente utilizar el método de JpaRepository y enviar el objeto entidad que se buscó mediante el ID
        aparatoElectronicoOficialRepositorio.delete(aparatoElectronicoOficial);
    }

    private AparatoElectronicoOficialDTO mapearDTO(AparatoElectronicoOficial aparatoElectronicoOficial) {
        //establecer los valores de la entidad (AparatoElectronicoOficial) al objeto de transferencia (DTO)
        AparatoElectronicoOficialDTO aparatoElectronicoOficialDTO = modelMapper.map(aparatoElectronicoOficial, AparatoElectronicoOficialDTO.class);
        return aparatoElectronicoOficialDTO;
    }

    //Este método convierte de DTO a entidad
    private AparatoElectronicoOficial mapearEntidad(AparatoElectronicoOficialDTO aparatoElectronicoOficialDTO) {
        //establecer los valores del objeto de transferencia (DTO) a la entidad (AparatoElectronicoOficial)
        AparatoElectronicoOficial aparatoElectronicoOficial = modelMapper.map(aparatoElectronicoOficialDTO, AparatoElectronicoOficial.class);
        return aparatoElectronicoOficial;
    }
}
