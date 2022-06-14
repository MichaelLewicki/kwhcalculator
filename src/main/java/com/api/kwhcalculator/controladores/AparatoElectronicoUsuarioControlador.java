package com.api.kwhcalculator.controladores;


import com.api.kwhcalculator.dto.AparatoElectronicoUsuarioDTO;
import com.api.kwhcalculator.dto.SectorEspecificoDTO;
import com.api.kwhcalculator.servicios.AparatoElectronicoUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios/{idUsuario}/sectoresGenerales/{idSectorGeneral}/sectoresEspecificos/{idSectorEspecifico}")
public class AparatoElectronicoUsuarioControlador {


    @Autowired
    private AparatoElectronicoUsuarioServicio aparatoElectronicoUsuarioServicio;


    //crear aparato electrónico
    @PostMapping("/aparatosElectronicosUsuario")
    public ResponseEntity<AparatoElectronicoUsuarioDTO> crearAparatoElectronicoUsuario(@PathVariable(value = "idUsuario") long idUsuario,
                                                                                       @PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                                                       @PathVariable(value = "idSectorEspecifico") long idSectorEspecifico,
                                                                                       @RequestBody AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO) {
        return new ResponseEntity<>(aparatoElectronicoUsuarioServicio.crearAparatoElectronicoUsuario(idSectorEspecifico, aparatoElectronicoUsuarioDTO), HttpStatus.CREATED);
    }

    //Obtener lista de aparatos electrónicos usuario asociados al ID de un sector específico
    @GetMapping("/aparatosElectronicosUsuario")
    public List<AparatoElectronicoUsuarioDTO> listarApElectronicosUsuarioPorSectorespecificoId(@PathVariable(value = "idUsuario") long idUsuario,
                                                                                      @PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                                                      @PathVariable(value = "idSectorEspecifico") long idSectorEspecifico) {
        return aparatoElectronicoUsuarioServicio.obtenerApElectroUsuarioPorSecEspecificoId(idSectorEspecifico);
    }

    //Obtener un aparato electrínico usuario buscándolo por el ID del sector específico y el ID del aparato electrónico usuario
    @GetMapping("/aparatosElectronicosUsuario/{idAparatoElectronicoUsuario}")
    public ResponseEntity<AparatoElectronicoUsuarioDTO> obtenerSectorEspecificoPorId(@PathVariable(value = "idUsuario") long idUsuario,
                                                                            @PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                                            @PathVariable(value = "idSectorEspecifico") long idSectorEspecifico,
                                                                            @PathVariable(value = "idAparatoElectronicoUsuario") long idAparatoElectronicoUsuario) {
        //retorna una respuesta de una entidad con HTTPStatus.OK
        AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO = aparatoElectronicoUsuarioServicio.obtenerApElectroUsuarioPorSecEspecificoIdApElectroUsuarioId(idSectorEspecifico,idAparatoElectronicoUsuario);
        return new ResponseEntity<>(aparatoElectronicoUsuarioDTO, HttpStatus.OK);
    }

    @PutMapping("/aparatosElectronicosUsuario/{idAparatoElectronicoUsuario}")
    public ResponseEntity<AparatoElectronicoUsuarioDTO> modificarAparatoElectronicoUsuario(@PathVariable(value = "idUsuario") long idUsuario,
                                                                                  @PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                                                  @PathVariable(value = "idSectorEspecifico") long idSectorEspecifico,
                                                                                  @PathVariable(value = "idAparatoElectronicoUsuario") long idAparatoElectronicoUsuario,
                                                                                  @RequestBody AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO) {
        AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioRespuesta = aparatoElectronicoUsuarioServicio.modificarAparatoElectronicoUsuario(idSectorEspecifico, idAparatoElectronicoUsuario, aparatoElectronicoUsuarioDTO);
        return new ResponseEntity<>(aparatoElectronicoUsuarioRespuesta, HttpStatus.OK);
    }

    //eliminar sector específico
    @DeleteMapping("/aparatosElectronicosUsuario/{idAparatoElectronicoUsuario}")
    public ResponseEntity<String> eliminarSectorEspecifico(@PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                           @PathVariable(value = "idSectorEspecifico") long idSectorEspecifico,
                                                           @PathVariable(value = "idAparatoElectronicoUsuario") long idAparatoElectronicoUsuario) {
        aparatoElectronicoUsuarioServicio.eliminarAparatoElectronicoUsuario(idSectorEspecifico, idAparatoElectronicoUsuario);
        return new ResponseEntity<>("Sector especifico eliminado exitosamente", HttpStatus.OK);
    }
}
