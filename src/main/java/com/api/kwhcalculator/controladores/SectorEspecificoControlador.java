package com.api.kwhcalculator.controladores;


import com.api.kwhcalculator.dto.SectorEspecificoDTO;
import com.api.kwhcalculator.dto.SectorGeneralDTO;
import com.api.kwhcalculator.modelos.SectorEspecifico;
import com.api.kwhcalculator.servicios.SectorEspecificoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios/{idUsuario}/sectoresgenerales/{idSectorGeneral}")
public class SectorEspecificoControlador {

    @Autowired
    private SectorEspecificoServicio sectorEspecificoServicio;

    //todas las peticiones devuelven DTO, ya que el objeto de transferencia realmente contiene un JSON (objetoDTO = JSON)

    //crear sector específico

    @PostMapping("/sectoresEspecificos")
    public ResponseEntity<SectorEspecificoDTO> crearSectorEspecifico(@PathVariable(value = "idUsuario") long idUsuario,
                                                                     @PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                                     @RequestBody SectorEspecificoDTO sectorEspecificoDTO) {
        return new ResponseEntity<>(sectorEspecificoServicio.crearSectorEspecifico(idSectorGeneral, sectorEspecificoDTO), HttpStatus.CREATED);
    }

    //Obtener lista de sectores especificos asociados al ID de un sector general
    @GetMapping("/sectoresEspecificos")
    public List<SectorEspecificoDTO> listarSectoresEspecificosPorSectorGeneralId(@PathVariable(value = "idUsuario") long idUsuario,
                                                                                 @PathVariable(value = "idSectorGeneral") long idSectorGeneral) {
        return sectorEspecificoServicio.obtenerSecEspecificosPorSecGeneralId(idSectorGeneral);
    }

    //Obtener un sector específico buscándolo por el ID del sector general y el ID del sector específico
    @GetMapping("/sectoresEspecificos/{idSectorEspecifico}")
    public ResponseEntity<SectorEspecificoDTO> obtenerSectorEspecificoPorId(@PathVariable(value = "idUsuario") long idUsuario,
                                                                            @PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                                            @PathVariable(value = "idSectorEspecifico") long idSectorEspecifico) {
        //retorna una respuesta de una entidad con HTTPStatus.OK
        SectorEspecificoDTO sectorEspecificoDTO = sectorEspecificoServicio.obtenerSecEspecificoPorSecGeneralIdSecEspecificoId(idSectorGeneral,idSectorEspecifico);
        return new ResponseEntity<>(sectorEspecificoDTO, HttpStatus.OK);
    }

    //modificar sector específico buscándolo por el ID del sector general y el ID del sector específico
    @PutMapping("/sectoresEspecificos/{idSectorEspecifico}")
    public ResponseEntity<SectorEspecificoDTO> modificarSectorEspecifico(@PathVariable(value = "idUsuario") long idUsuario,
                                                                         @PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                                         @PathVariable(value = "idSectorEspecifico") long idSectorEspecifico,
                                                                         @RequestBody SectorEspecificoDTO sectorEspecificoDTO) {
        SectorEspecificoDTO sectorEspecificoRespuesta = sectorEspecificoServicio.modificarSectorEspecifico(idSectorGeneral, idSectorEspecifico, sectorEspecificoDTO);
        return new ResponseEntity<>(sectorEspecificoRespuesta, HttpStatus.OK);
    }

    //eliminar sector específico
    @DeleteMapping("/sectoresEspecificos/{idSectorEspecifico}")
    public ResponseEntity<String> eliminarSectorEspecifico(@PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                           @PathVariable(value = "idSectorEspecifico") long idSectorEspecifico) {
        sectorEspecificoServicio.eliminarSectorEspecifico(idSectorGeneral, idSectorEspecifico);
        return new ResponseEntity<>("Sector especifico eliminado exitosamente", HttpStatus.OK);
    }
}