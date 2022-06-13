package com.api.kwhcalculator.controladores;


import com.api.kwhcalculator.dto.SectorEspecificoDTO;
import com.api.kwhcalculator.dto.SectorGeneralDTO;
import com.api.kwhcalculator.modelos.SectorEspecifico;
import com.api.kwhcalculator.servicios.SectorEspecificoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios/{idUsuario}/sectoresgenerales/{idSectorGeneral}")
public class SectorEspecificoControlador {

    @Autowired
    private SectorEspecificoServicio sectorEspecificoServicio;

    //todas las peticiones devuelven DTO, ya que el objeto de transferencia realmente contiene un JSON (objetoDTO = JSON)

    @PostMapping("/sectoresEspecificos")
    public ResponseEntity<SectorEspecificoDTO> crearSectorEspecifico(@PathVariable(value = "idUsuario") long idUsuario, @PathVariable(value = "idSectorGeneral") long idSectorGeneral, @RequestBody SectorEspecificoDTO sectorEspecificoDTO) {
        return new ResponseEntity<>(sectorEspecificoServicio.crearSectorEspecifico(idSectorGeneral, sectorEspecificoDTO), HttpStatus.CREATED);
    }
}
