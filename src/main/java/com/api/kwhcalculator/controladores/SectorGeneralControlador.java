package com.api.kwhcalculator.controladores;

import com.api.kwhcalculator.dto.SectorGeneralDTO;
import com.api.kwhcalculator.servicios.SectorGeneralServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.OPTIONS})
@RequestMapping("/api/usuarios/{idUsuario}")
public class SectorGeneralControlador {

    @Autowired
    private SectorGeneralServicio sectorGeneralServicio;

    //todas las peticiones devuelven DTO, ya que el objeto de transferencia realmente contiene un JSON (objetoDTO = JSON)

    //Crear Sector General
    @PostMapping("/sectoresGenerales")
    public ResponseEntity<SectorGeneralDTO> crearSectorGeneral(@PathVariable(value = "idUsuario") long idUsuario, @RequestBody SectorGeneralDTO sectorGeneralDTO) {
        return new ResponseEntity<>(sectorGeneralServicio.crearSectorGeneral(idUsuario, sectorGeneralDTO), HttpStatus.CREATED);
    }

    //Obtener lista de sectores generales asociados al ID de un usuario
    @GetMapping("/sectoresGenerales")
    public List<SectorGeneralDTO> listarSectoresGeneralesPorUsuarioId(@PathVariable(value = "idUsuario") long idUsuario) {
        return sectorGeneralServicio.obtenerSectoresGeneralesPorUsuarioId(idUsuario);
    }

    //Obtener un sector general buscándolo por el ID del usuario y el ID del sector general
    @GetMapping("/sectoresGenerales/{idSectorGeneral}")
    public ResponseEntity<SectorGeneralDTO> obtenerSectorGeneralPorId(@PathVariable(value = "idUsuario") long idUsuario, @PathVariable(value = "idSectorGeneral") long idSectorGeneral) {
        //retorna una respuesta de una entidad con HTTPStatus.OK
        SectorGeneralDTO sectorGeneralDTO = sectorGeneralServicio.obtenerSectorGeneralPorUsuarioIdSectorGeneralId(idUsuario,idSectorGeneral);
        return new ResponseEntity<>(sectorGeneralDTO, HttpStatus.OK);
    }

    //Modificar un sector general buscándolo por el ID del usuario y el ID del sector general
    @PutMapping("/sectoresGenerales/{idSectorGeneral}")
    public ResponseEntity<SectorGeneralDTO> modificarSectorGeneralPorId(@PathVariable(value = "idUsuario") long idUsuario,
                                                                        @PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                                        @RequestBody SectorGeneralDTO sectorGeneralDTO) {
        SectorGeneralDTO sectorGeneralRespuesta = sectorGeneralServicio.modificarSectorGeneral(idUsuario, idSectorGeneral, sectorGeneralDTO);
        return new ResponseEntity<>(sectorGeneralRespuesta, HttpStatus.OK);
    }

    @DeleteMapping("/sectoresGenerales/{idSectorGeneral}")
    public ResponseEntity<String> eliminarSectorGeneral(@PathVariable(value = "idUsuario") long idUsuario, @PathVariable(value = "idSectorGeneral") long idSectorGeneral) {
        sectorGeneralServicio.eliminarSectorGeneral(idUsuario, idSectorGeneral);
        return new ResponseEntity<>("Sector general eliminado exitosamente", HttpStatus.OK);
    }
}
