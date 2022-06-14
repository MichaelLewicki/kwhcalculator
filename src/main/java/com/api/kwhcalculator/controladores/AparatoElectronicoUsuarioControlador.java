package com.api.kwhcalculator.controladores;


import com.api.kwhcalculator.dto.AparatoElectronicoUsuarioDTO;
import com.api.kwhcalculator.dto.SectorEspecificoDTO;
import com.api.kwhcalculator.servicios.AparatoElectronicoUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios/{idUsuario}/sectoresGenerales/{idSectorGeneral}/sectoresEspecificos/{idSectorEspecifico}")
public class AparatoElectronicoUsuarioControlador {


    @Autowired
    private AparatoElectronicoUsuarioServicio aparatoElectronicoUsuarioServicio;

    @PostMapping("/aparatosElectronicosUsuario")
    public ResponseEntity<AparatoElectronicoUsuarioDTO> crearAparatoElectronicoUsuario(@PathVariable(value = "idUsuario") long idUsuario,
                                                                                       @PathVariable(value = "idSectorGeneral") long idSectorGeneral,
                                                                                       @PathVariable(value = "idSectorEspecifico") long idSectorEspecifico,
                                                                                       @RequestBody AparatoElectronicoUsuarioDTO aparatoElectronicoUsuarioDTO) {
        return new ResponseEntity<>(aparatoElectronicoUsuarioServicio.crearAparatoElectronicoUsuario(idSectorEspecifico, aparatoElectronicoUsuarioDTO), HttpStatus.CREATED);
    }

}
